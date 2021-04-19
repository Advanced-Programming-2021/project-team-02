package view;

import controller.MainMenuController;
import view.input.Regex;
import view.messages.Error;
import view.messages.SuccessMessage;

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
            MenusManager.getInstance().changeMenu(Menu.LOGIN_MENU);
        } else if (Regex.getMatcher (Regex.MENU_SHOW_CURRENT, command).matches ()) {
            showCurrentMenu ();
        } else if ((matcher = Regex.getMatcher (Regex.MENU_ENTER, command)).matches ()) {
            controller.menuEnter (matcher);
        } else if (Regex.getMatcher (Regex.USER_LOGOUT, command).matches ()) {
            showSuccessMessage (SuccessMessage.LOGOUT);
            MenusManager.getInstance().changeMenu(Menu.LOGIN_MENU);
        } else showError (Error.INVALID_COMMAND);
    }

    public void showError(Error error) {
        System.out.println (error.getValue ());
    }

    public void showSuccessMessage(SuccessMessage message) {
        System.out.println (message.getValue ());
    }

    public void showCurrentMenu() {
        System.out.println ("Main Menu");
    }
}