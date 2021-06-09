package project.view;

import javafx.scene.input.MouseEvent;
import project.controller.LoginMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LoginMenuView extends Application {
    private static final LoginMenuController controller = LoginMenuController.getInstance ();
    private static Stage stage;
    public TextField usernameFieldSignUp;
    public PasswordField passwordFieldSignUp;
    public TextField usernameFieldLogin;
    public PasswordField passwordFieldLogin;
    public PasswordField secondPasswordField;
    public TextField nicknameFieldSignUp;

    @Override
    public void start(Stage stage) throws Exception {
        LoginMenuView.stage = stage;
        URL fxmlAddress = getClass ().getResource ("/project/fxml/login_menu.fxml");
        Parent login = FXMLLoader.load (fxmlAddress);
        Scene scene = new Scene (login);
        stage.setScene(scene);
        stage.setTitle("Yu-Gi-Oh!");
        stage.show ();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void registerUser() {
        controller.createUser (usernameFieldSignUp.getText (), nicknameFieldSignUp.getText (), passwordFieldSignUp.getText (), secondPasswordField.getText ());
    }

    public void loginUser() {
        controller.loginUser (usernameFieldLogin.getText (), passwordFieldLogin.getText ());
    }

    public void exit() {
        System.exit (0);
    }
}