package project.controller;


import project.view.messages.LoginMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class LoginMenuController {
    private static LoginMenuController instance = null;

    private LoginMenuController() {
    }

    public static LoginMenuController getInstance() {
        if (instance == null) instance = new LoginMenuController();
        return instance;
    }

    public LoginMessage createUser(String username, String nickname, String password, String secondPassword) {
        if (username.length() == 0 || password.length() == 0 || secondPassword.length() == 0 || nickname.length() == 0)
            return LoginMessage.EMPTY_FIELD;
        if (!username.matches("\\w+") || !nickname.matches("\\w+") || !password.matches("\\w+"))
            return LoginMessage.INVALID_INPUT;
        if (username.length() < 4) return LoginMessage.SHORT_USERNAME;
        if (password.length() < 4) return LoginMessage.SHORT_PASSWORD;
        if (!password.equals(secondPassword)) return LoginMessage.NONIDENTICAL_PASSWORDS;

        DataOutputStream dataOutputStream = ControllerManager.getInstance().getDataOutputStream();
        DataInputStream dataInputStream = ControllerManager.getInstance().getDataInputStream();
        String result = "";
        try {
            dataOutputStream.writeUTF("login_menu register " + username + " " + nickname + " " + password + " " + secondPassword);
            dataOutputStream.flush();
            result = dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (result) {
            case "success":
                return LoginMessage.SUCCESSFUL_SIGN_UP;
            case "used_username":
                return LoginMessage.TAKEN_USERNAME;
            case "used_nickname":
                return LoginMessage.TAKEN_NICKNAME;
        }
        return LoginMessage.ERROR_OCCURRED;

    }

    public LoginMessage loginUser(String username, String password) {
        if (username.length() == 0 || password.length() == 0) return LoginMessage.EMPTY_FIELD;
        if (!username.matches("\\w+") || !password.matches("\\w+"))
            return LoginMessage.INVALID_INPUT;
        DataOutputStream dataOutputStream = ControllerManager.getInstance().getDataOutputStream();
        DataInputStream dataInputStream = ControllerManager.getInstance().getDataInputStream();
        String result = "";
        try {
            dataOutputStream.writeUTF("login_menu login " + username + " " + password);
            dataOutputStream.flush();
            result = dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] results = result.split(" ");
        switch (results[0]) {
            case "success": {
                try {
                    Socket socket = new Socket("localhost", 8000);
                    ControllerManager.getInstance().setTransferSocket(socket, results[1]);
                    System.out.println("token : "+results[1]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MainMenuController.getInstance().setLoggedInUsernameAndToken(username, results[1]);
                return LoginMessage.SUCCESSFUL_LOGIN;
            }
            case "username_password_dont_match":
                return LoginMessage.INCORRECT_USERNAME_PASSWORD;
        }

        return LoginMessage.SUCCESSFUL_LOGIN;
    }
}