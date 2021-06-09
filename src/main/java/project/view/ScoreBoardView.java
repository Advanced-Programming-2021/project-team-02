package project.view;

import project.model.User;
import project.view.input.Regex;
import project.view.messages.Error;

import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;

public class ScoreBoardView {
    private static ScoreBoardView instance = null;

    private ScoreBoardView() {
    }


    public static ScoreBoardView getInstance() {
        if (instance == null)
            instance = new ScoreBoardView();
        return instance;
    }

    public void run(String command) {
        commandRecognition(command);
    }


    public void commandRecognition(String command) {
        Matcher matcher;
        if ((matcher = Regex.getMatcher(Regex.MENU_ENTER, command)).matches()) {
            if (matcher.group ("menuName").toLowerCase (Locale.ROOT).equals ("scoreboard"))
                showDynamicError (Error.BEING_ON_CURRENT_MENU);
            else Error.showError(Error.BEING_ON_A_MENU);
        } else if (Regex.getMatcher(Regex.MENU_EXIT, command).matches()) {
//            MenusManager.getInstance().changeMenu(Menu.MAIN_MENU);
        } else if (Regex.getMatcher(Regex.MENU_SHOW_CURRENT, command).matches()) {
            showCurrentMenu();
        } else if (Regex.getMatcher(Regex.SCOREBOARD_SHOW, command).matches()) {
            showScoreBoard();
        } else if (Regex.getMatcher (Regex.COMMAND_HELP, command).matches ()) {
            help ();
        } else Error.showError(Error.INVALID_COMMAND);
    }

    public void showScoreBoard() {
        ArrayList<User> allUsers = User.sortAllUsers();
        int counter = 0;
        for (int i = 0; i < allUsers.size(); i++) {
            if (i != 0 && allUsers.get(i).getScore() == allUsers.get(i - 1).getScore()) {
                System.out.println(i - counter + "- " + allUsers.get(i).getNickname() + ": " + allUsers.get(i).getScore());
                counter++;
            } else {
                counter = 0;
                System.out.println(i + 1 + "- " + allUsers.get(i).getNickname() + ": " + allUsers.get(i).getScore());
            }
        }
    }

    public void showDynamicError(Error error) {
//        System.out.printf (error.getValue (), Menu.SCOREBOARD_MENU.getValue ());
    }

    public void showCurrentMenu() {
        System.out.println("Scoreboard Menu");
    }

    public void help() {
        System.out.println ("menu show-current\n" +
                "scoreboard show\n" +
                "menu exit\n" +
                "help");
    }
}