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

        for (String s: ServerMainController.getDataForChat().keySet()) {
            try {
                ServerMainController.getDataTransfer().get(s).writeUTF(ServerMainController.getLoggedInUsers().get(s).getUsername()
                        + " : " + message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

