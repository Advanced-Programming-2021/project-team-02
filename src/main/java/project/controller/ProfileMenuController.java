package project.controller;

import project.model.User;
import project.view.messages.LoginMessage;
import project.view.messages.ProfileMenuMessage;

public class ProfileMenuController {
    private static ProfileMenuController instance = null;

    private ProfileMenuController() {
    }

    public static ProfileMenuController getInstance() {
        if (instance == null) instance = new ProfileMenuController();
        return instance;
    }

    public ProfileMenuMessage changeUsername(String newUsername) {
        if (newUsername.length () < 6) return ProfileMenuMessage.SHORT_USERNAME;
        if (isUsernameUsed(newUsername)) return ProfileMenuMessage.USERNAME_TAKEN;
        MainMenuController.getInstance().getLoggedInUser().changeUsername(newUsername);
        return ProfileMenuMessage.USERNAME_CHANGED;
    }

    public boolean isUsernameUsed(String username) {
        return User.getUserByUsername(username) != null;
    }

    public boolean isNicknameUsed(String nickname) {
        for (User user : User.getAllUsers())
            if (user.getNickname().equals(nickname)) return true;
        return false;
    }

    public boolean isPasswordCorrect(String password) {
        return MainMenuController.getInstance().getLoggedInUser().getPassword().equals(password);
    }

    public ProfileMenuMessage changeNickname(String newNickname) {
        if (isNicknameUsed(newNickname)) return ProfileMenuMessage.NICKNAME_TAKEN;
        MainMenuController.getInstance().getLoggedInUser().changeNickname(newNickname);
        return ProfileMenuMessage.NICKNAME_CHANGED;
    }

    public ProfileMenuMessage changePassword(String currentPassword, String newPassword) {
        if (!isPasswordCorrect(currentPassword)) return ProfileMenuMessage.CURRENT_PASSWORD;
        if (currentPassword.equals(newPassword)) return ProfileMenuMessage.SAME_PASSWORD;
        MainMenuController.getInstance().getLoggedInUser().changePassword(newPassword);
        return ProfileMenuMessage.PASSWORD_CHANGED;
    }
}