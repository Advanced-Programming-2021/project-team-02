package project.view;

import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import project.Main;
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
        assert fxmlAddress != null;
        Parent root = FXMLLoader.load (fxmlAddress);
        PopUpMessage.setParent(root);
        Scene scene = new Scene (root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setMaximized(true);
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

    public static Stage getStage() {
        return stage;
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
        PopUpMessage popUpMessage = new PopUpMessage (message.getAlertType (), message.getLabel ());
        if (message.getAlertType().equals(Alert.AlertType.INFORMATION)) {
            new MainMenuView().start(stage);
        } else PopUpMessage.getParent().setEffect(null);
        if (!popUpMessage.getAlert ().isShowing()) PopUpMessage.getParent().setEffect(null);
    }

    public void exit() {
        PopUpMessage popUpMessage = new PopUpMessage (Alert.AlertType.CONFIRMATION, LoginMessage.EXIT_CONFIRMATION.getLabel ());
        if (popUpMessage.getAlert ().getResult ().getText ().equals ("OK")) {
            System.exit (0);
        } else PopUpMessage.getParent().setEffect(null);
    }
}