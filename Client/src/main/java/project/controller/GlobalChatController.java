package project.controller;

import javafx.application.Platform;
import project.view.GlobalChatView;
import project.view.messages.GlobalChatMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class GlobalChatController {
    private static GlobalChatController instance = null;
    public String textToAppend;
    private GlobalChatView view;
    private Socket socket;
    private DataInputStream dataInputStreamChat;
    private DataOutputStream dataOutputStreamChat;
    private Socket readerSocket;
    private DataInputStream readerInPutStream;
    private DataOutputStream readerOutPutStream;

    private GlobalChatController() {
    }

    public static GlobalChatController getInstance() {
        if (instance == null) instance = new GlobalChatController();
        return instance;
    }

    public String getTextToAppend() {
        return textToAppend;
    }

    public void initializeNetworkToSend() {
        try {
            socket = new Socket("localhost", 8000);
            dataInputStreamChat = new DataInputStream(socket.getInputStream());
            dataOutputStreamChat = new DataOutputStream(socket.getOutputStream());
            dataOutputStreamChat.writeUTF("chat_send_socket " + MainMenuController.getInstance().getLoggedInUserToken());
            dataOutputStreamChat.flush();
            dataInputStreamChat.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeNetworkToReceive() {
        try {
            readerSocket = new Socket("localhost", 8000);
            readerOutPutStream = new DataOutputStream(readerSocket.getOutputStream());
            readerInPutStream = new DataInputStream(readerSocket.getInputStream());
            readerOutPutStream.writeUTF("Chat_Socket_Read " + MainMenuController.getInstance().getLoggedInUserToken());
            readerOutPutStream.flush();
            startReceiverThreadForChat();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void startReceiverThreadForChat() {
        new Thread(() -> {
            try {
                while (true) {
                    String chatResult = readerInPutStream.readUTF();
                    if (chatResult.equals("close"))
                        break;
                    textToAppend = chatResult;
                    Platform.runLater(view::setMessageForTextArea);
                }
                readerInPutStream.close();
                readerOutPutStream.close();
                readerSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public GlobalChatMessage sendChatMessage(String message) {
        try {
            dataOutputStreamChat.writeUTF(message);
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

    public void setView(GlobalChatView view) {
        this.view = view;
    }

    public void close() {
        try {
            dataOutputStreamChat.writeUTF("close_chat_socket");
            dataOutputStreamChat.flush();
            dataOutputStreamChat.close();
            dataInputStreamChat.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
