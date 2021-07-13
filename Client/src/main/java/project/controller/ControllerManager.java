package project.controller;

import com.google.gson.Gson;
import project.model.Assets;
import project.model.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ControllerManager {
    private static ControllerManager instance = null;
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    private ControllerManager() {

    }

    public static ControllerManager getInstance() {
        if (instance == null)
            instance = new ControllerManager();
        return instance;
    }

    public void setSocket(Socket socket) {
        try {
            this.socket = socket;
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public String askServerNickname() {
        try {
            dataOutputStream.writeUTF("ask nickname " + MainMenuController.getInstance().getLoggedInUserToken());
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public User askForLoggedInUser() {
        try {
            dataOutputStream.writeUTF("ask user " + MainMenuController.getInstance().getLoggedInUserToken());
            dataOutputStream.flush();
            String gson = dataInputStream.readUTF();
            System.out.println(gson);
            User user = new Gson().fromJson(gson, User.class);
            System.out.println(user);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public Assets getAUserAssets(String username) {
        DataOutputStream dataOutputStream = ControllerManager.getInstance().getDataOutputStream();
        try {
            System.out.println(username + " to get data of asset");
            dataOutputStream.writeUTF("ask asset " + username);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DataInputStream dataInputStream = ControllerManager.getInstance().getDataInputStream();
        Assets assets = null;
        try {
            String gson = dataInputStream.readUTF();
            System.out.println(gson);
            assets = new Gson().fromJson(gson, Assets.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return assets;
    }
}
