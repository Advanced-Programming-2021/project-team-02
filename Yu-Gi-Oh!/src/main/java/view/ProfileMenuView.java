package view;

import controller.ProfileMenuController;
import view.input.Input;
import view.input.Regex;
import view.messages.Error;
import view.messages.SuccessMessage;

import java.util.regex.Matcher;

public class ProfileMenuView {
    private static ProfileMenuView instance = null;
    private static final ProfileMenuController controller = ProfileMenuController.getInstance ();

    private ProfileMenuView(){

    }

    public static ProfileMenuView getInstance() {
        if (instance == null) instance = new ProfileMenuView ();
        return instance;
    }

    public void run(String command) {
        commandRecognition(command);
    }

    public void commandRecognition(String command) {
        Matcher matcher;
        if (Regex.getMatcher (Regex.MENU_ENTER, command).matches ()) {
            showError (Error.BEING_ON_A_MENU);
        } else if (Regex.getMatcher (Regex.MENU_EXIT, command).matches ()) {
            MenusManager.getInstance().changeMenu(Menu.MAIN_MENU);
        } else if ((Regex.getMatcher (Regex.MENU_SHOW_CURRENT, command)).matches ()) {
            showCurrentMenu ();
        } else if ((matcher = Regex.getMatcher (Regex.PROFILE_CHANGE_NICKNAME, command)).matches ()) {
            controller.changeNickname (matcher);
        } else if ((matcher = checkAllPermutationsOfChangePassword (command)) != null) {
            controller.changePassword (matcher);
        } else showError (Error.INVALID_COMMAND);
    }

    public Matcher checkAllPermutationsOfChangePassword(String command) {
        Matcher matcher;
        for (String regex : Regex.PROFILE_CHANGE_PASSWORD)
            if ((matcher = Regex.getMatcher (regex, command)).matches ()) return matcher;
        return null;
    }

    public void showError(Error error) {
        System.out.println (error.getValue ());
    }

    public void showDynamicError(Error error, Matcher matcher) {
        if (error.equals (Error.TAKEN_USERNAME)) {
            System.out.printf (Error.TAKEN_USERNAME.getValue (), matcher.group ("username"));
        } else if (error.equals (Error.TAKEN_NICKNAME))
            System.out.printf (Error.TAKEN_NICKNAME.getValue (), matcher.group ("nickname"));
    }

    public void showSuccessMessage(SuccessMessage message) {
        System.out.println (message.getValue ());
    }

    public void showCurrentMenu() {
        System.out.println ("Profile Menu");
    }
}