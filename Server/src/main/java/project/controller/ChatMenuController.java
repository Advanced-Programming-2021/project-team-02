package project.controller;

import com.google.gson.Gson;
import project.ServerMainController;
import project.model.User;

import java.io.IOException;

public class ChatMenuController {
    private static ChatMenuController instance = null;

    private ChatMenuController() {
    }

    public static ChatMenuController getInstance() {
        if (instance == null) instance = new ChatMenuController();
        return instance;
    }

    public void sendMessage(String token, String message) {

        for (String s : ServerMainController.getDataForChat().keySet()) {
            try {
                if (s.equals(token))
                    continue;
                ServerMainController.getDataForChat().get(s).writeUTF(ServerMainController.getLoggedInUsers().get(s).getUsername()
                        + " : " + message);
                ServerMainController.getDataForChat().get(s).flush();
                System.out.println(message + " sent");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

