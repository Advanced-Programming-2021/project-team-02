package project.controller;

import com.google.gson.Gson;
import project.model.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ControllerManager {
    private static ControllerManager instance = null;
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

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
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
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
            User user = new Gson().fromJson(dataInputStream.readUTF(), User.class);
        } catch (Exception e) {

        }
        return null;
    }
}
