package view;

import controller.LoginMenuController;
import view.input.Input;
import view.input.Regex;
import view.messages.Error;
import view.messages.SuccessMessage;

import java.util.regex.Matcher;

public class LoginMenuView {
    private static LoginMenuView instance = null;
    private static final LoginMenuController controller = LoginMenuController.getInstance();

    private LoginMenuView() {

    }

    public static LoginMenuView getInstance() {
        if (instance == null) instance = new LoginMenuView();
        return instance;
    }

    public void run(String command) {
        commandRecognition(command);
    }

    public void commandRecognition(String command) {
        Matcher matcher;
        if (Regex.getMatcher(Regex.MENU_ENTER, command).matches()) {
            showError(Error.ENTER_MENU_BEFORE_LOGIN);
        } else if (Regex.getMatcher(Regex.MENU_EXIT, command).matches()) {
            MenusManager.getInstance().changeMenu(Menu.EXIT);
        } else if (Regex.getMatcher(Regex.MENU_SHOW_CURRENT, command).matches()) {
            showCurrentMenu();
        } else if ((matcher = Regex.getMatcherFromAllPermutations (Regex.USER_CREATE, command)) != null) {
            controller.createUser(matcher);
        } else if ((matcher = Regex.getMatcherFromAllPermutations (Regex.USER_LOGIN, command)) != null) {
            controller.loginUser(matcher);
        } else showError(Error.INVALID_COMMAND);
    }

    public void showError(Error error) {
        System.out.println(error.getValue());
    }

    public void showDynamicError(Error error, Matcher matcher) {
        if (error.equals(Error.TAKEN_USERNAME)) {
            System.out.printf(Error.TAKEN_USERNAME.getValue(), matcher.group("username"));
        } else if (error.equals(Error.TAKEN_NICKNAME))
            System.out.printf(Error.TAKEN_NICKNAME.getValue(), matcher.group("nickname"));
    }

    public void showSuccessMessage(SuccessMessage message) {
        System.out.println(message.getValue());
    }

    public void showCurrentMenu() {
        System.out.println("Login Menu");
    }
}