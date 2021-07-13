package project;

import com.google.gson.Gson;
import project.controller.ControllerManager;
import project.controller.LoginMenuController;
import project.controller.Scoreboard;
import project.model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;

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
            processAsk(parts);
        }
        return "";
    }

    private static String processAsk(String[] parts) {
        switch (parts[1]) {
            case "nickname":
                 return loggedInUsers.get(parts[2]).getNickname();
            case "user":
                return new Gson().toJson(loggedInUsers.get(parts[2]));
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
