package view;

import model.game.Duel;
import view.input.Input;
import view.messages.Error;
import view.messages.SuccessMessage;

import java.util.regex.Matcher;

public class DuelMenuView {
    private static DuelMenuView instance = null;

    private DuelMenuView() {

    }

    public static DuelMenuView getInstance() {
        if (instance == null)
            instance = new DuelMenuView();
        return instance;
    }

    public void run() {

    }

    public void getCommand() {
        while (true) {
            Input.getInput();
        }
    }

    public void commandRecognition(String command) {

    }

    public void showError(Error error) {

    }

    public void showDynamicError(Error error, String dynamicPart) {

    }

    public void showSuccessMessage(SuccessMessage message) {

    }
}
