package project.controller;

import project.ServerMainController;

import java.io.IOException;

public class ChatMenuController {
    private static ChatMenuController instance = null;

    private ChatMenuController() {
    }

    public static ChatMenuController getInstance() {
        if (instance == null) instance = new ChatMenuController();
        return instance;
    }

    public String sendMessage(String token, String message) {

        synchronized (ServerMainController.getDataForChat()) {
            for (String s : ServerMainController.getDataForChat().keySet()) {
                try {
//                    if (s.equals(token))
//                        continue;
                    System.out.println("message : "+ message);
                    ServerMainController.getDataForChat().get(s).writeUTF(message);
                    ServerMainController.getDataForChat().get(s).flush();
                } catch (IOException e) {
                    System.out.println("ya hagh");
                    return "failed";
                }
            }
        }
        return "success";
    }
}

