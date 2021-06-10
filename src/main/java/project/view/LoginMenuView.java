package project.view;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.StageStyle;
import project.controller.LoginMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.view.messages.LoginMessage;
import project.view.messages.PopUpMessage;

import java.net.URL;

public class LoginMenuView extends Application {
    private static final String yuGiOhIconPath = "/project/image/Yu-Gi-Oh_icon.jpg";
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
        PopUpMessage.setStage(stage);
        URL fxmlAddress = getClass ().getResource ("/project/fxml/login_menu.fxml");
        Parent login = FXMLLoader.load (fxmlAddress);
        Scene scene = new Scene (login);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setTitle("Yu-Gi-Oh!");
        Image yuGiOhIcon = new Image (String.valueOf (getClass ().getResource (yuGiOhIconPath)));
        stage.getIcons().add(yuGiOhIcon);
        stage.show ();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void registerUser() {
        LoginMessage message = controller.createUser (usernameFieldSignUp.getText (), nicknameFieldSignUp.getText (), passwordFieldSignUp.getText (), secondPasswordField.getText ());
        new PopUpMessage (message.getAlertType (), message.getLabel ());
        usernameFieldSignUp.clear();
        nicknameFieldSignUp.clear();
        passwordFieldSignUp.clear();
        secondPasswordField.clear();
    }

    public void loginUser() throws Exception {
        LoginMessage message = controller.loginUser (usernameFieldLogin.getText (), passwordFieldLogin.getText ());
        new PopUpMessage (message.getAlertType (), message.getLabel ());
        if (message.getAlertType().equals(Alert.AlertType.INFORMATION)) new MainMenuView().start(stage);
    }

    public void exit() {
        PopUpMessage popUpMessage = new PopUpMessage (Alert.AlertType.CONFIRMATION, LoginMessage.EXIT_CONFIRMATION.getLabel ());
        if (popUpMessage.getAlert ().getResult ().getText ().equals ("OK")) System.exit (0);
    }
}