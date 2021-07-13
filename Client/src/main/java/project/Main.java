package project;

import project.controller.ControllerManager;
import project.model.card.CardsDatabase;
import project.view.LoginMenuView;

import java.io.IOException;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8000);
        ControllerManager.getInstance().setSocket(socket);
        LoginMenuView.main(args);
    }
}
