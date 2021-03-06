package project;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import project.controller.*;
import project.model.Assets;
import project.model.Avatar;
import project.model.Shop;
import project.model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerMainController {

    private static HashMap<String, User> loggedInUsers;
    private static HashMap<String, DataOutputStream> dataTransferForShopCards;
    private static HashMap<String, DataOutputStream> dataTransferForAssetsInShop;
    private static HashMap<String, DataOutputStream> dataForChat;
    private static HashMap<String, DataOutputStream> profileDataTransfer;
    private static HashMap<String, DataOutputStream> scoreboardDataTransfer;
    private static boolean isAdminLoggedIn = false;
    private static DataInputStream adminInput;
    private static DataOutputStream adminOutput;
    private static HashMap<String, DataOutputStream> deckDataTransfer;
    private static HashMap<String, DataOutputStream> onlineCounter;

    public static boolean isIsAdminLoggedIn() {
        return isAdminLoggedIn;
    }

    public static HashMap<String, DataOutputStream> getDataTransferForAssetsInShop() {
        return dataTransferForAssetsInShop;
    }

    public static HashMap<String, DataOutputStream> getProfileDataTransfer() {
        return profileDataTransfer;
    }

    public static HashMap<String, DataOutputStream> getScoreboardDataTransfer() {
        return scoreboardDataTransfer;
    }

    public static HashMap<String, DataOutputStream> getDataForChat() {
        return dataForChat;
    }

    public static HashMap<String, User> getLoggedInUsers() {
        return loggedInUsers;
    }

    public static HashMap<String, DataOutputStream> getDataTransferForShopCards() {
        return dataTransferForShopCards;
    }

    public static HashMap<String, DataOutputStream> getDeckDataTransfer() {
        return deckDataTransfer;
    }

    public static HashMap<String, DataOutputStream> getOnlineCounter() {
        return onlineCounter;
    }

    public static void run() {
        loggedInUsers = new HashMap<>();
        dataTransferForShopCards = new HashMap<>();
        dataForChat = new HashMap<>();
        profileDataTransfer = new HashMap<>();
        scoreboardDataTransfer = new HashMap<>();
        dataTransferForAssetsInShop = new HashMap<>();
        deckDataTransfer = new HashMap<>();
        onlineCounter = new HashMap<>();
        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            while (true) {
                Socket socket = serverSocket.accept();
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                new Thread(() -> {
                    try {
                        String in = dataInputStream.readUTF();
                        if (in.equals("request")) {
                            startThreadForRequestSocket(socket, dataOutputStream, dataInputStream);
                        } else if (in.matches("data_transfer_scoreboard .+")) {
                            in = in.replaceFirst("data_transfer_scoreboard ", "");
                            scoreboardDataTransfer.put(in, dataOutputStream);
                        } else if (in.matches("data_transfer_shop .+")) {
                            in = in.replaceFirst("data_transfer_shop ", "");
                            dataTransferForShopCards.put(in, dataOutputStream);
                        } else if (in.matches("data_transfer_asset .+")) {
                            in = in.replaceFirst("data_transfer_asset ", "");
                            dataTransferForAssetsInShop.put(in, dataOutputStream);
                        } else if (in.matches("data_transfer_profile .+")) {
                            in = in.replaceFirst("data_transfer_profile ", "");
                            profileDataTransfer.put(in, dataOutputStream);
                        } else if (in.matches("Chat_Socket_Read .+")) {
                            in = in.replaceFirst("Chat_Socket_Read ", "");
                            dataForChat.put(in, dataOutputStream);
                        } else if (in.matches("chat_send_socket .+")) {
                            String token = in.replaceFirst("chat_send_socket ", "");
                            startThreadForChatSocket(socket, dataOutputStream, dataInputStream, token);
                            dataOutputStream.writeUTF("success " + loggedInUsers.keySet().size());
                        } else if (in.equals("admin")) {
                            if (!isAdminLoggedIn) {
                                isAdminLoggedIn = true;
                                adminInput = dataInputStream;
                                adminOutput = dataOutputStream;
                                dataOutputStream.writeUTF("success");
                                startThreadForAdmin();
                            } else dataOutputStream.writeUTF("admin is logged in!");
                        } else if (in.matches("data_transfer_deck .+")) {
                            in = in.replaceFirst("data_transfer_deck ", "");
                            deckDataTransfer.put(in, dataOutputStream);
                        } else if (in.matches("chat_online_member_counter .+")) {
                            in = in.replaceFirst("chat_online_member_counter ", "");
                            onlineCounter.put(in, dataOutputStream);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startThreadForAdmin() {
        new Thread(() -> {
            while (true) {
                try {
                    String in = adminInput.readUTF();
                    if (in.equals("close")) {
                        adminOutput.writeUTF("close");
                        break;
                    }
                    AdminController.getInstance().updateAdminData(in);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            try {
                adminOutput.close();
                adminInput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            isAdminLoggedIn = false;
        }).start();
    }

    public static DataInputStream getAdminInput() {
        return adminInput;
    }

    public static DataOutputStream getAdminOutput() {
        return adminOutput;
    }

    private static void startThreadForRequestSocket(Socket socket, DataOutputStream dataOutputStream, DataInputStream dataInputStream) {
        new Thread(() -> {
            try {
                getInputAndProcess(dataInputStream, dataOutputStream);
                dataInputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void startThreadForChatSocket(Socket socket, DataOutputStream dataOutputStream, DataInputStream dataInputStream, String token) {
        new Thread(() -> {
            try {
                while (true) {
                    String message;
                    message = dataInputStream.readUTF();
                    if (message.equals("close_chat_socket")) {
                        System.out.println("chat closed");
                        synchronized (getDataForChat()) {
                            getDataForChat().get(token).writeUTF("close");
                            dataOutputStream.flush();
                            getDataForChat().remove(token);
                        }
                        synchronized (getOnlineCounter()) {
                            getOnlineCounter().get(token).writeUTF("close");
                            getOnlineCounter().get(token).flush();
                            getOnlineCounter().remove(token);
                        }
                        break;
                    } else if (message.matches("user_data .+")) {
                        message = message.replaceFirst("user_data ", "");
                        User user = User.getUserByUsername(message);
                        dataOutputStream.writeUTF(user.getUsername() + " " + user.getNickname() + " " + user.getScore());
                        dataOutputStream.flush();
                    } else {
                        String result = ChatMenuController.getInstance().sendMessage(token, message);
                        dataOutputStream.writeUTF(result);
                        dataOutputStream.flush();
                    }
                }
                dataOutputStream.close();
                dataInputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void getInputAndProcess(DataInputStream dataInputStream, DataOutputStream dataOutputStream) throws IOException {
        while (true) {
            String input;
            try {
                input = dataInputStream.readUTF();
            } catch (SocketException e) {
                break;
            }
            if (input.equals("close"))
                break;
            String result = process(input);
            dataOutputStream.writeUTF(result);
            dataOutputStream.flush();
        }
    }

    private static String process(String input) {
        String[] parts = input.split(" ");
        if (parts[0].equals("login_menu")) {
            String result = processLoginMenu(parts);
            System.out.println(result);
            return result;
        } else if (parts[0].equals("scoreboard")) {
            if (input.matches("scoreboard close .+")) {
                try {
                    synchronized (getScoreboardDataTransfer()) {
                        scoreboardDataTransfer.get(parts[2]).writeUTF("close");
                        System.out.println("scoreboard closed");
                        scoreboardDataTransfer.get(parts[2]).flush();
                        scoreboardDataTransfer.remove(parts[2]);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "success";
            } else
                return Scoreboard.scoreboardData();
        } else if (parts[0].equals("")) {
            return "";
        } else if (parts[0].startsWith("ask")) {
            return processAsk(parts);
        } else if (parts[0].equals("shop")) {
            return processShopCommand(input);
        } else if (parts[0].equals("logout")) {
            System.out.println(input);
            return processLogout(parts[1]);
        } else if (parts[0].equals("profile")) {
            return processProfileMenu(parts, input);
        } else if (parts[0].equals("deck")) {
            return processDeckMenu(parts, input);
        }
        return "";
    }

    private static String processDeckMenu(String[] parts, String input) {
        Pattern pattern = Pattern.compile("deck create <(?<deckName>.+)> (?<token>.+)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            String deckName = matcher.group("deckName");
            String token = matcher.group("token");
            return DeckMenuController.getInstance().createDeck(deckName, token);
        }
        pattern = Pattern.compile("deck delete <(?<deckName>.+)> (?<token>.+)");
        matcher = pattern.matcher(input);
        if (matcher.find()) {
            String deckName = matcher.group("deckName");
            String token = matcher.group("token");
            return DeckMenuController.getInstance().deleteDeck(deckName, token);
        }
        pattern = Pattern.compile("deck activate <(?<deckName>.+) (?<token>.+)");
        matcher = pattern.matcher(input);
        if (matcher.find()) {
            String deckName = matcher.group("deckName");
            String token = matcher.group("token");
            return DeckMenuController.getInstance().activateDeck(deckName, token);
        }
        return "";
    }

    private static String processShopCommand(String input) {
        Pattern pattern = Pattern.compile("shop close (?<token>.+)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            String token = matcher.group("token");
            try {
                synchronized (getDataTransferForAssetsInShop()) {
                    getDataTransferForAssetsInShop().get(token).writeUTF("close");
                    getDataTransferForAssetsInShop().get(token).flush();
                    getDataTransferForAssetsInShop().remove(token);
                }
                synchronized (getDataTransferForShopCards()) {
                    getDataTransferForShopCards().get(token).writeUTF("close");
                    getDataTransferForShopCards().get(token).flush();
                    getDataTransferForShopCards().remove(token);
                }
                return "success";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        pattern = Pattern.compile("shop buy <(?<cardName>.+)> (?<token>.+)");
        matcher = pattern.matcher(input);
        if (matcher.find()) {
            String cardName = matcher.group("cardName");
            String token = matcher.group("token");
            return ShopController.getInstance().buyCard(cardName, loggedInUsers.get(token).getUsername(), token);
        }
        pattern = Pattern.compile("shop sell <(?<cardName>.+)> (?<token>.+)");
        matcher = pattern.matcher(input);
        if (matcher.find()) {
            String cardName = matcher.group("cardName");
            String token = matcher.group("token");
            return ShopController.getInstance().sellCard(cardName, loggedInUsers.get(token).getUsername(), token);
        }
        return "failed";
    }

    private static String processAsk(String[] parts) {
        switch (parts[1]) {
            case "user":
                String gson = new Gson().toJson(loggedInUsers.get(parts[2]));
                System.out.println(gson);
                return gson;
            case "asset":
                String assetGson = new Gson().toJson(Assets.getAssetsByUsername(parts[2]));
                System.out.println(assetGson);
                return assetGson;
            case "shop":
                return new Gson().toJson(Shop.getInstance().getCardsWithNumberOfThem(), new TypeToken<LinkedHashMap<String, Integer>>() {
                }.getType());

        }
        return "";
    }

    private static String processProfileMenu(String[] parts, String input) {
        Pattern pattern = Pattern.compile("profile close (?<token>.+)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            String token = matcher.group("token");
            try {
                profileDataTransfer.get(token).writeUTF("close");
                profileDataTransfer.get(token).flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            profileDataTransfer.remove(token);
            return "success";
        }
        System.out.println(parts[1]);
        //change_photo
        pattern = Pattern.compile("profile change_photo <(?<token>.+)> (?<url>\\d+)");
        matcher = pattern.matcher(input);
        if (matcher.find()) {
            String token = matcher.group("token");
            int url = Integer.parseInt(matcher.group("url"));
            System.out.println("avatar : " + url);
            URL avatar = Avatar.getAvatarByNumber(url);
            return new ProfileController().changePhoto(token, avatar);
        }
        if (parts[1].equals("change_password")) {
            return new ProfileController().changePassword(parts[4], parts[3]);
        } else if (parts[1].equals("change_nickname")) {
            return new ProfileController().changeNickname(parts[3], parts[2]);
        } else return "";
    }


    private static String processLoginMenu(String[] parts) {
        LoginMenuController loginMenuController = new LoginMenuController();
        if (parts[1].equals("register")) {
            return loginMenuController.createUser(parts[2], parts[3], parts[4]);
        } else if (parts[1].equals("login")) {
            return loginMenuController.loginUser(parts[2], parts[3]);
        }
        return "";
    }

    private static String processLogout(String token) {
        return MainMenuController.getInstance().logout(token);
    }

}
