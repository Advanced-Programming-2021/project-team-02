package controller;

import model.User;
import view.ProfileMenuView;
import view.messages.Error;
import view.messages.SuccessMessage;

public class ProfileMenuController {
    private static ProfileMenuController instance = null;
    private final ProfileMenuView view = ProfileMenuView.getInstance ();

    private ProfileMenuController() {}

    public static ProfileMenuController getInstance() {
        if (instance == null) instance = new ProfileMenuController ();
        return instance;
    }

    public boolean isNicknameUsed(String nickname) {
        for (User user : User.getAllUsers ())
            if (user.getNickname ().equals (nickname)) return true;
        return false;
    }

    public boolean isPasswordCorrect(String password) {
        return MainMenuController.getInstance ().getLoggedInUser ().getPassword ().equals (password);
    }

    public void changeNickname(String newNickname) {
        if (isNicknameUsed (newNickname)) {
            view.showDynamicError (Error.TAKEN_NICKNAME, newNickname);
            return;
        }
        MainMenuController.getInstance ().getLoggedInUser ().changeNickname (newNickname);
        SuccessMessage.showSuccessMessage (SuccessMessage.NICKNAME_CHANGED);
    }

    public void changePassword(String currentPassword, String newPassword) {
        if (!isPasswordCorrect (currentPassword)) {
            Error.showError (Error.INVALID_CURRENT_PASSWORD);
            return;
        }
        if (currentPassword.equals (newPassword)) {
            Error.showError (Error.SAME_PASSWORD);
            return;
        }
        MainMenuController.getInstance ().getLoggedInUser ().changePassword (newPassword);
        SuccessMessage.showSuccessMessage (SuccessMessage.PASSWORD_CHANGED);
    }
}