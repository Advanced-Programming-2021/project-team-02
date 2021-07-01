package project.view;

import project.controller.ProfileMenuController;
import project.view.input.Regex;
import project.view.messages.Error;

import java.util.Locale;
import java.util.regex.Matcher;

public class ProfileMenuView {
    private static ProfileMenuView instance = null;
    private static final ProfileMenuController controller = ProfileMenuController.getInstance();

    private ProfileMenuView() {

    }

    public static ProfileMenuView getInstance() {
        if (instance == null) instance = new ProfileMenuView();
        return instance;
    }

    public void run(String command) {
        commandRecognition(command);
    }

    public void commandRecognition(String command) {
        Matcher matcher;
        if ((matcher = Regex.getMatcher(Regex.MENU_ENTER, command)).matches()) {
            if (matcher.group("menuName").toLowerCase(Locale.ROOT).equals("profile"))
                showDynamicError(Error.BEING_ON_CURRENT_MENU, Menu.PROFILE_MENU.getValue());
            else Error.showError(Error.BEING_ON_A_MENU);
        } else if (Regex.getMatcher(Regex.MENU_EXIT, command).matches()) {
            MenusManager.getInstance().changeMenu(Menu.MAIN_MENU);
        } else if ((Regex.getMatcher(Regex.MENU_SHOW_CURRENT, command)).matches()) {
            showCurrentMenu();
        } else if ((matcher = Regex.getMatcherFromAllPermutations(Regex.PROFILE_CHANGE_NICKNAME, command)) != null) {
            controller.changeNickname(matcher.group("nickname"));
        } else if ((matcher = Regex.getMatcherFromAllPermutations(Regex.PROFILE_CHANGE_USERNAME, command)) != null) {
            controller.changeUsername(matcher.group("username"));
        } else if ((matcher = Regex.getMatcherFromAllPermutations(Regex.PROFILE_CHANGE_PASSWORD, command)) != null) {
            controller.changePassword(matcher.group("currentPassword"), matcher.group("newPassword"));
        } else if (Regex.getMatcher(Regex.COMMAND_HELP, command).matches()) {
            help();
        } else Error.showError(Error.INVALID_COMMAND);
    }

    public void showDynamicError(Error error, String string) {
        if (error.equals(Error.TAKEN_USERNAME))
            System.out.printf(Error.TAKEN_USERNAME.getValue(), string);
        else if (error.equals(Error.TAKEN_NICKNAME))
            System.out.printf(Error.TAKEN_NICKNAME.getValue(), string);
        else if (error.equals(Error.BEING_ON_CURRENT_MENU))
            System.out.printf(error.getValue(), string);
    }

    public void showCurrentMenu() {
        System.out.println("Profile Menu");
    }

    public void help() {
        System.out.println("menu show-current\n" +
                "profile change --username <username>\n" +
                "profile change -u <username>\n" +
                "profile change --nickname <nickname>\n" +
                "profile change -n <nickname>\n" +
                "profile change --password --current <currentPassword> --new <newPassword>\n" +
                "profile change -p -c <currentPassword> -n <newPassword>\n" +
                "menu exit\n" +
                "help");
    }
}