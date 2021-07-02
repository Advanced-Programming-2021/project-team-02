package project.controller;


import project.model.User;
import project.view.messages.Error;
import project.view.messages.LoginMessage;
import project.view.messages.SuccessMessage;


public class LoginMenuController {
    private static LoginMenuController instance = null;

    private LoginMenuController() {
    }

    public static LoginMenuController getInstance() {
        if (instance == null) instance = new LoginMenuController();
        return instance;
    }

    public LoginMessage createUser(String username, String nickname, String password, String secondPassword) {
        if (username.length () == 0 || password.length () == 0 || secondPassword.length () == 0 || nickname.length () == 0) return LoginMessage.EMPTY_FIELD;
        if (username.length () < 6) return LoginMessage.SHORT_USERNAME;
        if (password.length () < 8) return LoginMessage.SHORT_PASSWORD;
        if (isUsernameUsed(username)) return LoginMessage.TAKEN_USERNAME;
        if (isNicknameUsed(nickname)) return LoginMessage.TAKEN_NICKNAME;
        if (!password.equals(secondPassword)) return LoginMessage.NONIDENTICAL_PASSWORDS;
        new User(username, password, nickname);
        //TODO User.jsonUsers();
        return LoginMessage.SUCCESSFUL_SIGN_UP;
    }

    public boolean isUsernameUsed(String username) {
        return User.getUserByUsername(username) != null;
    }

    public boolean isNicknameUsed(String nickname) {
        for (User user : User.getAllUsers())
            if (user.getNickname().equals(nickname)) return true;
        return false;
    }

    public boolean doesUsernameAndPasswordMatch(String username, String password) {
        return User.getUserByUsername(username).getPassword().equals(password);
    }

    public LoginMessage loginUser(String username, String password) {
        if (username.length () == 0 || password.length () == 0) return LoginMessage.EMPTY_FIELD;
        if (!isUsernameUsed(username)) return LoginMessage.INCORRECT_USERNAME_PASSWORD;
        if (!doesUsernameAndPasswordMatch(username, password)) return LoginMessage.INCORRECT_USERNAME_PASSWORD;
        MainMenuController.getInstance().setLoggedInUser(User.getUserByUsername(username));
        return LoginMessage.SUCCESSFUL_LOGIN;
    }
}