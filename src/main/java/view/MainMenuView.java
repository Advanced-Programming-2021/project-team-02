package view;

import controller.MainMenuController;
import view.input.Regex;
import view.messages.Error;
import view.messages.SuccessMessage;

import java.util.Locale;
import java.util.regex.Matcher;

public class MainMenuView {
    private static MainMenuView instance = null;
    private static final MainMenuController controller = MainMenuController.getInstance ();

    private MainMenuView() {
    }

    public static MainMenuView getInstance() {
        if (instance == null) instance = new MainMenuView ();
        return instance;
    }

    public void run(String command) {
        commandRecognition (command);
    }


    public void commandRecognition(String command) {
        Matcher matcher;
        if (Regex.getMatcher (Regex.MENU_EXIT, command).matches ()) {
            SuccessMessage.showSuccessMessage (SuccessMessage.LOGOUT);
            MenusManager.getInstance ().setLoggedInUser (null);
            MenusManager.getInstance ().changeMenu (Menu.LOGIN_MENU);
        } else if (Regex.getMatcher (Regex.MENU_SHOW_CURRENT, command).matches ()) {
            showCurrentMenu ();
        } else if ((matcher = Regex.getMatcher (Regex.MENU_ENTER, command)).matches ()) {
            String menuName = matcher.group ("menuName");
            if (menuName.toLowerCase (Locale.ROOT).equals ("main"))
                showDynamicError (Error.BEING_ON_CURRENT_MENU);
            else controller.menuEnter (menuName);
        } else if (Regex.getMatcher (Regex.USER_LOGOUT, command).matches ()) {
            SuccessMessage.showSuccessMessage (SuccessMessage.LOGOUT);
            MenusManager.getInstance ().setLoggedInUser (null);
            MenusManager.getInstance ().changeMenu (Menu.LOGIN_MENU);
        } else if (Regex.getMatcher (Regex.COMMAND_HELP, command).matches ()) {
            help ();
        } else Error.showError (Error.INVALID_COMMAND);
    }

    public void showDynamicError(Error error) {
        System.out.printf (error.getValue (), Menu.MAIN_MENU.getValue ());
    }

    public void showCurrentMenu() {
        System.out.println ("view.Main Menu");
    }

    public void help() {
        System.out.println ("menu show-current\n" +
                "menu enter <menuName>\n" +
                "user logout\n" +
                "menu exit\n" +
                "help");
    }
}