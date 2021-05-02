package view.gameview;

import view.input.Input;
import view.messages.Error;
import view.messages.SuccessMessage;

import java.util.regex.Matcher;

public class RoundGameView {
    private static final RoundGameView instance;

    static {
        instance = new RoundGameView();
    }

    public static RoundGameView getInstance() {
        return instance;
    }

    public void run() {
    }

    public void getCommand() {

    }

    public void commandRecognition(String command) {

    }

    public void showError(Error error) {

    }

    public void showDynamicError(Error error, Matcher matcher) {

    }

    public void showSuccessMessage(SuccessMessage message) {

    }

    public void showBoard() {

    }

    public void showPhase() {

    }

    public void showGraveYard() {

    }

    public void showCard() {

    }
}
