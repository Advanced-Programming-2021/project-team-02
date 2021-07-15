package project;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import project.controller.*;
import project.model.Assets;
import project.model.Shop;
import project.model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerMainController {

    private static HashMap<String, User> loggedInUsers;
    private static HashMap<String, DataOutputStream> dataTransfer;

    public static HashMap<String, DataOutputStream> getDataForChat() {
        return dataForChat;
    }

    private static HashMap<String, DataOutputStream> dataForChat;

    public static HashMap<String, User> getLoggedInUsers() {
        return loggedInUsers;
    }

    public static HashMap<String, DataOutputStream> getDataTransfer() {
        return dataTransfer;
    }

    public static void run() {
        loggedInUsers = new HashMap<>();
        dataTransfer = new HashMap<>();
        dataForChat = new HashMap<>();
        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            while (true) {
                Socket socket = serverSocket.accept();
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                String in = dataInputStream.readUTF();
                if (in.equals("request")) {
                    startThread(serverSocket, socket, dataOutputStream, dataInputStream);
                } else if (in.matches("data_transfer .+")) {
                    in = in.replaceFirst("data_transfer ", "");
                    System.out.println("token : " + in);
                    dataTransfer.put(in, dataOutputStream);
                } else if (in.matches("Chat_Socket_Read .+")) {
                    String[] split = in.split("\\s+");
                    dataForChat.put(split[1], dataOutputStream);
                } else if (in.matches("Chat_Sending .+")) {
                    String[] split = in.split("\\s+");
                    ChatMenuController.getInstance().sendMessage(split[1], split[2]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startThread(ServerSocket serverSocket, Socket socket, DataOutputStream dataOutputStream, DataInputStream dataInputStream) {
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

    private static void getInputAndProcess(DataInputStream dataInputStream, DataOutputStream dataOutputStream) throws IOException {
        while (true) {
            String input;
            try {
                input = dataInputStream.readUTF();
            } catch (SocketException e) {
                break;
            }
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
        } else if (parts[0].equals("profile"))
            return processProfileMenu(parts);
        return "";
    }

    private static String processShopCommand(String input) {
        Pattern pattern = Pattern.compile("shop buy <(?<cardName>.+)> (?<token>.+)");
        Matcher matcher = pattern.matcher(input);
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

    private static String processProfileMenu(String[] parts) {
        System.out.println(parts[1]);
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
