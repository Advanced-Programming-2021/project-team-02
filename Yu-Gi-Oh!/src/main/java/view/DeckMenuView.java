package view;

import view.input.Input;
import view.messages.Error;
import view.messages.SuccessMessage;

public class DeckMenuView {
    private static DeckMenuView instance = null;

    private DeckMenuView(){

    }

    public static DeckMenuView getInstance() {
        if (instance == null)
            instance = new DeckMenuView();
        return instance;
    }

    public void run(String command) {
        commandRecognition(command);
    }

    public void commandRecognition(String command) {

    }

    public void showError(Error error) {
    }

    public void showSuccessMessage(SuccessMessage message) {

    }
}
