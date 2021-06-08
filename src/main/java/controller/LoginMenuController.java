package controller;


import model.User;
import view.LoginMenuView;
import view.Menu;
import view.MenusManager;
import view.messages.Error;
import view.messages.SuccessMessage;


public class LoginMenuController {
    private static LoginMenuController instance = null;
    private final LoginMenuView view = LoginMenuView.getInstance();

    private LoginMenuController() {
    }

    public static LoginMenuController getInstance() {
        if (instance == null) instance = new LoginMenuController();
        return instance;
    }

    public void createUser(String username, String nickname, String password) {
        if (isUsernameUsed(username)) {
            view.showDynamicError(Error.TAKEN_USERNAME, username);
            return;
        }
        if (isNicknameUsed(nickname)) {
            view.showDynamicError(Error.TAKEN_NICKNAME, nickname);
            return;
        }
        new User(username, password, nickname);
        User.jsonUsers();
        SuccessMessage.showSuccessMessage(SuccessMessage.REGISTER_SUCCESSFUL);
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
        assert User.getUserByUsername(username) != null;
        return User.getUserByUsername(username).getPassword().equals(password);
    }

    public void loginUser(String username, String password) {
        if (!isUsernameUsed(username)) {
            Error.showError(Error.INCORRECT_USERNAME);
            return;
        }
        if (!doesUsernameAndPasswordMatch(username, password)) {
            Error.showError(Error.INCORRECT_PASSWORD);
            return;
        }
        MainMenuController.getInstance().setLoggedInUser(User.getUserByUsername(username));
        assert User.getUserByUsername(username) != null;
        MenusManager.getInstance().setLoggedInUser(User.getUserByUsername(username));
        SuccessMessage.showSuccessMessage(SuccessMessage.LOGIN_SUCCESSFUL);
        MenusManager.getInstance().changeMenu(Menu.MAIN_MENU);
    }
}