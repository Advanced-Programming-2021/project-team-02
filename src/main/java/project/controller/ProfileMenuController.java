package project.controller;
import project.model.User;
import project.view.messages.ProfileMenuMessage;

public class ProfileMenuController {
    private static ProfileMenuController instance = null;
    //private final ProfileMenuView view = ProfileMenuView.getInstance ();

    private ProfileMenuController() {
    }

    public static ProfileMenuController getInstance() {
        if (instance == null) instance = new ProfileMenuController();
        return instance;
    }

    public boolean isNicknameUsed(String nickname) {
        for (User user : User.getAllUsers())
            if (user.getNickname().equals(nickname)) return true;
        return false;
    }

    public boolean isPasswordCorrect(String password) {
        return password.equals("12345");
        //return MainMenuController.getInstance ().getLoggedInUser ().getPassword ().equals (password);
    }

    public ProfileMenuMessage changeNickname(String newNickname) {
        if (isNicknameUsed(newNickname)) {
            return ProfileMenuMessage.USERNAME_TAKEN;
        }
        //MainMenuController.getInstance ().getLoggedInUser ().changeNickname (newNickname);
        return ProfileMenuMessage.CHANGED_SUCCESSFULLY;
    }

    public ProfileMenuMessage changePassword(String currentPassword, String newPassword) {
        if (!isPasswordCorrect(currentPassword)) {
            return ProfileMenuMessage.CURRENT_PASSWORD;
        }
        if (currentPassword.equals(newPassword)) {
            return ProfileMenuMessage.SAME_PASSWORD;
        }
//        MainMenuController.getInstance ().getLoggedInUser ().changePassword (newPassword);
        return ProfileMenuMessage.PASSWORD_CHANGED;
    }
}