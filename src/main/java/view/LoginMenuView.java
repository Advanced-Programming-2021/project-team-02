package view;

import com.google.gson.Gson;
import controller.LoginMenuController;
import view.input.Regex;
import view.messages.Error;

import java.util.regex.Matcher;

public class LoginMenuView {
    private static LoginMenuView instance = null;
    private static final LoginMenuController controller = LoginMenuController.getInstance ();

    private LoginMenuView() {

    }

    public static LoginMenuView getInstance() {
        if (instance == null) instance = new LoginMenuView ();
        return instance;
    }

    public void run(String command) {
        commandRecognition (command);
    }

    public void commandRecognition(String command) {
        Matcher matcher;
        if (Regex.getMatcher (Regex.MENU_ENTER, command).matches ()) {
            Error.showError (Error.ENTER_MENU_BEFORE_LOGIN);
        } else if (Regex.getMatcher (Regex.MENU_EXIT, command).matches ()) {
            MenusManager.getInstance ().changeMenu (Menu.EXIT);
        } else if (Regex.getMatcher (Regex.MENU_SHOW_CURRENT, command).matches ()) {
            showCurrentMenu ();
        } else if ((matcher = Regex.getMatcherFromAllPermutations (Regex.USER_CREATE, command)) != null) {
            String username = matcher.group("username");
            String nickname = matcher.group("nickname");
            String password = matcher.group("password");
            controller.createUser (username, nickname, password);
        } else if ((matcher = Regex.getMatcherFromAllPermutations (Regex.USER_LOGIN, command)) != null) {
            String username = matcher.group("username");
            String password = matcher.group("password");
            controller.loginUser (username, password);
        } else if (Regex.getMatcher (Regex.COMMAND_HELP, command).matches ()) {
            help ();
        } else Error.showError (Error.INVALID_COMMAND);
    }

    public void showDynamicError(Error error, String string) {
        if (error.equals (Error.TAKEN_USERNAME)) {
            System.out.printf (Error.TAKEN_USERNAME.getValue (), string);
        } else if (error.equals (Error.TAKEN_NICKNAME))
            System.out.printf (Error.TAKEN_NICKNAME.getValue (), string);
    }

    public void showCurrentMenu() {
        System.out.println ("Login Menu");
    }

    public void help() {
        System.out.println ("menu show-current\n" +
                "user create --username <username> --nickname <nickname> --password <password>\n" +
                "user create -u <username> -n <nickname> -p <password>\n" +
                "user login --username <username> --password <password>\n" +
                "user login -u <username> -p <password>\n" +
                "menu exit\n" +
                "help");
    }
}