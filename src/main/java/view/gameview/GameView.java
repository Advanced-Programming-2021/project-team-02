package view.gameview;

import view.messages.Error;
import view.messages.SuccessMessage;

import java.util.regex.Matcher;

public class GameView {
    private static final GameView instance;

    static {
        instance = new GameView();
    }

    public static GameView getInstance() {
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
    public void showDynamicErrorWithAString(Error error, String string) {

    }

    public void showSuccessMessage(SuccessMessage message) {

    }
    public void showSuccessMessageWithAString(SuccessMessage message, String string){

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
