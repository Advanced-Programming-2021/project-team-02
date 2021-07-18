package project.controller;

import javafx.application.Platform;
import project.view.GlobalChatView;
import project.view.messages.GlobalChatMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GlobalChatController {
    private static GlobalChatController instance = null;
    public String textToAppend;
    private int onlineCount;
    private GlobalChatView view;
    private Socket socket;
    private DataInputStream dataInputStreamChat;
    private DataOutputStream dataOutputStreamChat;
    private Socket readerSocket;
    private DataInputStream readerInPutStream;
    private DataOutputStream readerOutPutStream;
    private Socket onlineSocket;
    private DataInputStream onlineReceiver;
    private DataOutputStream onlineOutput;
    private String avatarToAppend;

    private GlobalChatController() {
    }

    public static GlobalChatController getInstance() {
        if (instance == null) instance = new GlobalChatController();
        return instance;
    }

    public String getAvatarToAppend() {
        return avatarToAppend;
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
            String string = dataInputStreamChat.readUTF();
            String count = string.replace("success ", "");
            onlineCount = Integer.parseInt(count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeNetworkToReceive() {
        try {
            String token = MainMenuController.getInstance().getLoggedInUserToken();
            readerSocket = new Socket("localhost", 8000);
            readerOutPutStream = new DataOutputStream(readerSocket.getOutputStream());
            readerInPutStream = new DataInputStream(readerSocket.getInputStream());
            readerOutPutStream.writeUTF("Chat_Socket_Read " + token);
            readerOutPutStream.flush();
            startReceiverThreadForChat();

            onlineSocket = new Socket("localhost", 8000);
            onlineOutput = new DataOutputStream(onlineSocket.getOutputStream());
            onlineReceiver = new DataInputStream(onlineSocket.getInputStream());
            onlineOutput.writeUTF("chat_online_member_counter " + token);
            onlineOutput.flush();
            startThreadForOnlineCountInChat();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void startThreadForOnlineCountInChat() {
        new Thread(() -> {
            try {
                while (true) {
                    String chatResult = onlineReceiver.readUTF();
                    if (chatResult.equals("close"))
                        break;
                    System.out.println("online : "+chatResult);
                    onlineCount = Integer.parseInt(chatResult);
                    System.out.println("online : " + onlineCount);
                    Platform.runLater(view::showOnlineCount);
                }
                readerInPutStream.close();
                readerOutPutStream.close();
                readerSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void startReceiverThreadForChat() {
        new Thread(() -> {
            try {
                while (true) {
                    String chatResult = readerInPutStream.readUTF();
                    if (chatResult.equals("close"))
                        break;
                    Pattern pattern = Pattern.compile("\\((?<url>.+)\\) (?<message>.+)");
                    Matcher matcher = pattern.matcher(chatResult);
                    if (matcher.find()) {
                        textToAppend = matcher.group("message");
                        avatarToAppend = matcher.group("url");
                        Platform.runLater(view::addMessage);
                    }
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
            if (string.matches("success")) {

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

    public int getOnlineCount() {
        return onlineCount;
    }
}
