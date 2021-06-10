package project.view;

import project.controller.ProfileMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.view.input.Regex;
import project.view.messages.Error;

import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;

public class ProfileMenuView extends Application {
    private static final ProfileMenuController controller = ProfileMenuController.getInstance ();
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        URL urlMain = getClass().getResource("project/fxml/ProfileMenuView.fxml");
        System.out.println(urlMain);
        Parent root = FXMLLoader.load(Objects.requireNonNull(urlMain));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}