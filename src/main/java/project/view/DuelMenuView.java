package project.view;

import javafx.application.Application;
import javafx.stage.Stage;
import project.controller.DuelMenuController;
import project.view.input.Regex;
import project.view.messages.Error;

import java.util.Locale;
import java.util.regex.Matcher;

public class DuelMenuView extends Application {
    private static Stage stage;
    private static final DuelMenuController controller = DuelMenuController.getInstance();

    @Override
    public void start(Stage stage) throws Exception {

    }
}
