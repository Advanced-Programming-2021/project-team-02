package project.controller;

import java.net.ServerSocket;

public class ControllerManager {
    private static ControllerManager instance = null;
    private ServerSocket serverSocket;

    private ControllerManager() {

    }

    public static ControllerManager getInstance() {
        if (instance == null)
            instance = new ControllerManager();
        return instance;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
}
