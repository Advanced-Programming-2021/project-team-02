package view;

import model.User;
import view.input.Input;
import view.input.Regex;
import view.messages.Error;

import java.util.ArrayList;

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
        if (Regex.getMatcher(Regex.MENU_ENTER, command).matches()) {
            showError(Error.BEING_ON_A_MENU);
        } else if (Regex.getMatcher(Regex.MENU_EXIT, command).matches()) {
            MenusManager.getInstance().changeMenu(Menu.MAIN_MENU);
        } else if (Regex.getMatcher(Regex.MENU_SHOW_CURRENT, command).matches()) {
            showCurrentMenu();
        } else if (Regex.getMatcher(Regex.SCOREBOARD_SHOW, command).matches()) {
            showScoreBoard();
        } else showError(Error.INVALID_COMMAND);
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

    public void showError(Error error) {
        System.out.println(error.getValue());
    }

    public void showCurrentMenu() {
        System.out.println("Login Menu");
    }
}