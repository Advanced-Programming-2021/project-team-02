package project;

import project.controller.ControllerManager;
import project.view.LoginMenuView;

import java.io.IOException;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        //Socket socket = new Socket("localhost", 8000);
        Socket socket = new Socket("2.tcp.ngrok.io", 18536);
        ControllerManager.getInstance().setReqSocket(socket);
        LoginMenuView.main(args);
    }
}