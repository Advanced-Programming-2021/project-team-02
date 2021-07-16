package project.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import project.model.Shop;
import project.model.User;
import project.view.ProfileMenuView;
import project.view.ScoreBoardView;
import project.view.ScoreboardData;
import project.view.messages.ProfileMenuMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ProfileMenuController {
    private static ProfileMenuController instance = null;
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private ProfileMenuView profileMenuView;

    private ProfileMenuController() {
    }

    public static ProfileMenuController getInstance() {
        if (instance == null) instance = new ProfileMenuController();
        return instance;
    }

    public void initializeNetworkForDataTransfer() {
        try {
            socket = new Socket("localhost", 8000);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("data_transfer_profile " + MainMenuController.getInstance().getLoggedInUserToken());
            dataOutputStream.flush();
            startReceiverThreadForProfile(dataInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReceiverThreadForProfile(DataInputStream dataInputStreamChat) {
        new Thread(() -> {
            try {
                while (true) {
                    String in = dataInputStreamChat.readUTF();
                    if (in.equals("close"))
                        break;
                    User user = new Gson().fromJson(in, User.class);
                    MainMenuController.getInstance().setLoggedInUser(user);
                    if (profileMenuView != null)
                        Platform.runLater(profileMenuView::setProfileData);
                }
                    dataOutputStream.close();
                    dataInputStream.close();
                    socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }

    public ProfileMenuMessage changeNickname(String newNickname) {
        if (newNickname.length() == 0) return ProfileMenuMessage.FILL_THE_FIELDS;
        DataOutputStream dataOutputStream = ControllerManager.getInstance().getDataOutputStream();
        DataInputStream dataInputStream = ControllerManager.getInstance().getDataInputStream();
        String result = "";
        try {
            dataOutputStream.writeUTF("profile change_nickname " + newNickname + " " + MainMenuController.getInstance().getLoggedInUserToken());
            dataOutputStream.flush();
            result = dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (result) {
            case "success":
                MainMenuController.getInstance().getLoggedInUser().changeNickname(newNickname);
                return ProfileMenuMessage.NICKNAME_CHANGED;
            case "used_nickname":
                return ProfileMenuMessage.NICKNAME_TAKEN;
        }

        return ProfileMenuMessage.NICKNAME_CHANGED;
    }

    public ProfileMenuMessage changePassword(String currentPassword, String newPassword) {
        if (!isPasswordCorrect(currentPassword)) return ProfileMenuMessage.CURRENT_PASSWORD;
        if (!newPassword.matches("\\w+")) return ProfileMenuMessage.INVALID_INPUT;
        if (newPassword.length() < 4) return ProfileMenuMessage.SHORT_PASSWORD;
        if (currentPassword.equals(newPassword)) return ProfileMenuMessage.SAME_PASSWORD;
        DataOutputStream dataOutputStream = ControllerManager.getInstance().getDataOutputStream();
        DataInputStream dataInputStream = ControllerManager.getInstance().getDataInputStream();
        String result = "";
        try {
            dataOutputStream.writeUTF("profile change_password " + currentPassword + " " + newPassword + " " + MainMenuController.getInstance().getLoggedInUserToken());
            dataOutputStream.flush();
            result = dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("change pass result : " + result);
        switch (result) {
            case "success":
                MainMenuController.getInstance().getLoggedInUser().changePassword(newPassword);
                return ProfileMenuMessage.PASSWORD_CHANGED;
            case "failed":
                return ProfileMenuMessage.ERROR_OCCURRED;
        }

        return ProfileMenuMessage.ERROR_OCCURRED;
    }

    public boolean isPasswordCorrect(String password) {
        return MainMenuController.getInstance().getLoggedInUser().getPassword().equals(password);
    }

    public void setProfileMenuView(ProfileMenuView profileMenuView) {
        this.profileMenuView = profileMenuView;
    }

    public void closeProfile() {
        try {
            ControllerManager.getInstance().getDataOutputStream().writeUTF("profile close " + MainMenuController.getInstance().getLoggedInUserToken());
            ControllerManager.getInstance().getDataOutputStream().flush();
            String result = ControllerManager.getInstance().getDataInputStream().readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}