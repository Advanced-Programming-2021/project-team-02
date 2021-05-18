package view;

import controller.ProfileMenuController;
import view.input.Regex;
import view.messages.Error;
import view.messages.SuccessMessage;

import java.util.Locale;
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
        if ((matcher = Regex.getMatcher (Regex.MENU_ENTER, command)).matches ()) {
            if (matcher.group ("menuName").toLowerCase(Locale.ROOT).equals ("profile"))
                showDynamicError (Error.BEING_ON_CURRENT_MENU, matcher);
            else Error.showError (Error.BEING_ON_A_MENU);
        } else if (Regex.getMatcher (Regex.MENU_EXIT, command).matches ()) {
            MenusManager.getInstance().changeMenu(Menu.MAIN_MENU);
        } else if ((Regex.getMatcher (Regex.MENU_SHOW_CURRENT, command)).matches ()) {
            showCurrentMenu ();
        } else if ((matcher = Regex.getMatcher (Regex.PROFILE_CHANGE_NICKNAME, command)).matches ()) {
            controller.changeNickname (matcher);
        } else if ((matcher = Regex.getMatcherFromAllPermutations (Regex.PROFILE_CHANGE_PASSWORD, command)) != null) {
            controller.changePassword (matcher);
        } else if (Regex.getMatcher (Regex.COMMAND_HELP, command).matches ()) {
            help ();
        } else Error.showError (Error.INVALID_COMMAND);
    }

    public void showDynamicError(Error error, Matcher matcher) {
        if (error.equals (Error.TAKEN_USERNAME))
            System.out.printf (Error.TAKEN_USERNAME.getValue (), matcher.group ("username"));
        else if (error.equals (Error.TAKEN_NICKNAME))
            System.out.printf (Error.TAKEN_NICKNAME.getValue (), matcher.group ("nickname"));
        else if (error.equals (Error.BEING_ON_CURRENT_MENU))
            System.out.printf (error.getValue (), Menu.PROFILE_MENU.getValue ());
    }

    public void showCurrentMenu() {
        System.out.println ("Profile Menu");
    }

    public void help() {
        System.out.println ("menu show-current\n" +
                "profile change --nickname <nickname>\n" +
                "profile change -n <nickname>\n" +
                "profile change --password --current <currentPassword> --new <newPassword>\n" +
                "profile change -p -c <currentPassword> -n <newPassword>\n" +
                "menu exit\n" +
                "help");
    }
}