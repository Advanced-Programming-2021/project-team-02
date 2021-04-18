package controller;

import model.User;
import view.ProfileMenuView;
import view.messages.Error;
import view.messages.SuccessMessage;

import java.util.regex.Matcher;

public class ProfileMenuController {
    private User loggedInUser;
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
        return loggedInUser.getPassword ().equals (password);
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void changeNickname(Matcher matcher) {
        String newNickname = matcher.group ("nickname");
        if (isNicknameUsed (newNickname)) {
            view.showDynamicError (Error.TAKEN_NICKNAME, matcher);
            return;
        }
        loggedInUser.changeNickname (newNickname);
        view.showSuccessMessage (SuccessMessage.NICKNAME_CHANGED);
    }

    public void changePassword(Matcher matcher) {
        String currentPassword = matcher.group ("currentPassword");
        String newPassword = matcher.group ("newPassword");
        if (!isPasswordCorrect (currentPassword)) {
            view.showError (Error.INVALID_CURRENT_PASSWORD);
            return;
        }
        if (currentPassword.equals (newPassword)) {
            view.showError (Error.SAME_PASSWORD);
            return;
        }
        loggedInUser.changePassword (newPassword);
        view.showSuccessMessage (SuccessMessage.PASSWORD_CHANGED);
    }
}
