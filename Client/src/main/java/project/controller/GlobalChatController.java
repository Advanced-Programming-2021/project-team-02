package project.controller;

import javafx.application.Platform;
import project.view.GlobalChatView;
import project.view.ShopMenuView;
import project.view.messages.GlobalChatMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class GlobalChatController {
    private static GlobalChatController instance = null;
    private GlobalChatView view = new GlobalChatView();
    private GlobalChatController() {
    }

    public static GlobalChatController getInstance() {
        if (instance == null) instance = new GlobalChatController();
        return instance;
    }

    public void readChats() {
        Socket socketChat = null;
        try {
            socketChat = new Socket("localhost", 8000);
            System.out.println("Chat is going");
            DataOutputStream dataOutputStreamChat = new DataOutputStream(socketChat.getOutputStream());
            DataInputStream dataInputStreamChat = new DataInputStream(socketChat.getInputStream());
            dataOutputStreamChat.writeUTF("Chat_Socket_Read " + MainMenuController.getInstance().getLoggedInUserToken());
            dataOutputStreamChat.flush();
            System.out.println("payam raft");
            startThreadForChat(dataInputStreamChat);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void startThreadForChat(DataInputStream dataInputStreamChat) {
        new Thread(() -> {
            try {
                while (true) {
                    String chatResult = dataInputStreamChat.readUTF();
                    System.out.println(chatResult);
                    GlobalChatView globalChatView = new GlobalChatView();
                    globalChatView.setTextToAppend(chatResult);
                    Platform.runLater(globalChatView::setMessageForTextArea);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public GlobalChatMessage sendChatMessage(String message) {
        Socket socketChat = null;
        try {
            socketChat = new Socket("localhost", 8000);
            System.out.println("Chat is going to send");
            DataOutputStream dataOutputStreamChat = new DataOutputStream(socketChat.getOutputStream());
            DataInputStream dataInputStreamChat = new DataInputStream(socketChat.getInputStream());
            dataOutputStreamChat.writeUTF("Chat_Sending " + MainMenuController.getInstance().getLoggedInUserToken() + " " + message);
            dataOutputStreamChat.flush();
            String string = dataInputStreamChat.readUTF();
            if (string.equals("success")) {
                return GlobalChatMessage.MESSAGE_SENT;
            } else {
                return GlobalChatMessage.MESSAGE_DID_NOT_SEND;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return GlobalChatMessage.MESSAGE_DID_NOT_SEND;
    }
}
