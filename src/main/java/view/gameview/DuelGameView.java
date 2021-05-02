package view.gameview;

import view.input.Input;
import view.messages.Error;
import view.messages.SuccessMessage;

public class DuelGameView {
    private static final DuelGameView instance;


    static {
        instance = new DuelGameView();
    }

    public static DuelGameView getInstance() {
        return instance;
    }

    public void run() {
    }

    public void commandRecognition(String command) {

    }

    public void showError(Error error) {

    }

    public void showSuccessMessage(SuccessMessage message) {

    }
}
