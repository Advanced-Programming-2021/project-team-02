package view;

import view.messages.Error;
import view.messages.SuccessMessage;

public class MainMenuView {
    private static final MainMenuView instance;

    static {
        instance = new MainMenuView();
    }

    public static MainMenuView getInstance() {
        return instance;
    }

    private MainMenuView() {

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