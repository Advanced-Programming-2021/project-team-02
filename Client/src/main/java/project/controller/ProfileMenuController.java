package project.controller;

import project.model.User;
import project.view.messages.ProfileMenuMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ProfileMenuController {
    private static ProfileMenuController instance = null;

    private ProfileMenuController() {
    }

    public static ProfileMenuController getInstance() {
        if (instance == null) instance = new ProfileMenuController();
        return instance;
    }


    public ProfileMenuMessage changeNickname(String newNickname) {
        if (newNickname.length() == 0) return ProfileMenuMessage.FILL_THE_FIELDS;
        DataOutputStream dataOutputStream = ControllerManager.getInstance().getDataOutputStream();
        DataInputStream dataInputStream = ControllerManager.getInstance().getDataInputStream();
        String result = "";
        try {
            dataOutputStream.writeUTF("profile change_nickname " + newNickname + " "  + MainMenuController.getInstance().getLoggedInUserToken());
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
        switch (result) {
            case "success":
                MainMenuController.getInstance().getLoggedInUser().changePassword(newPassword);
                return ProfileMenuMessage.PASSWORD_CHANGED;
            case "failed":
                return ProfileMenuMessage.ERROR_OCCURRED;
        }

        return ProfileMenuMessage.PASSWORD_CHANGED;
    }

    public boolean isPasswordCorrect(String password) {
        return MainMenuController.getInstance().getLoggedInUser().getPassword().equals(password);
    }
}