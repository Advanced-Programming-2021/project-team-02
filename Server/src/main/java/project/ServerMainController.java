package project;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import project.controller.ControllerManager;
import project.controller.LoginMenuController;
import project.controller.Scoreboard;
import project.controller.ShopController;
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

    public static HashMap<String, User> getLoggedInUsers() {
        return loggedInUsers;
    }

    public static void run() {
        loggedInUsers = new HashMap<>();
        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            while (true) {
                Socket socket = serverSocket.accept();
                startThread(serverSocket, socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startThread(ServerSocket serverSocket, Socket socket) {
        new Thread(() -> {
            try {
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
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
        }
        return "";
    }

    private static String processShopCommand(String input) {
        Pattern pattern = Pattern.compile("shop buy <(?<cardName>.+)> (?<token>.+)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            String cardName = matcher.group("cardName");
            String token = matcher.group("token");
            return ShopController.getInstance().buyCard(cardName, loggedInUsers.get(token).getUsername());
        }
        pattern = Pattern.compile("shop sell <(?<cardName>.+)> (?<token>.+)");
        matcher = pattern.matcher(input);
        if (matcher.find()) {
            String cardName = matcher.group("cardName");
            String token = matcher.group("token");
            return ShopController.getInstance().sellCard(cardName,loggedInUsers.get(token).getUsername());
        }
        return "failed";
    }

    private static String processAsk(String[] parts) {
        switch (parts[1]) {
            case "nickname":
                return loggedInUsers.get(parts[2]).getNickname();
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


    private static String processLoginMenu(String[] parts) {
        LoginMenuController loginMenuController = new LoginMenuController();
        if (parts[1].equals("register")) {
            return loginMenuController.createUser(parts[2], parts[3], parts[4]);
        } else if (parts[1].equals("login")) {
            return loginMenuController.loginUser(parts[2], parts[3]);
        }
        return "";
    }


}
