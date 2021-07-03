package project.view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import project.controller.LoginMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.model.Music;
import project.model.card.CardsDatabase;
import project.model.gui.Icon;
import project.view.messages.LoginMessage;
import project.view.messages.PopUpMessage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class LoginMenuView extends Application {
    private static final LoginMenuController controller = LoginMenuController.getInstance ();
    private static Stage stage;
    public TextField usernameFieldSignUp;
    public PasswordField passwordFieldSignUp;
    public TextField usernameFieldLogin;
    public PasswordField passwordFieldLogin;
    public PasswordField secondPasswordField;
    public TextField nicknameFieldSignUp;
    public ImageView playPauseMusicButton;
    public ImageView muteUnmuteButton;

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
        stage.getIcons().add(Icon.YU_GI_OH.getImage());
        stage.show ();
    }

    public static void main(String[] args) throws IOException {
        launch(args);
        CardsDatabase.getInstance().readAndMakeCards();
    }

    @FXML
    public void initialize() {
        if (Music.mediaPlayer.isAutoPlay()) playPauseMusicButton.setImage(Icon.PAUSE.getImage());
        else playPauseMusicButton.setImage(Icon.PLAY.getImage());
        if (Music.mediaPlayer.isMute()) muteUnmuteButton.setImage(Icon.MUTE.getImage());
        else muteUnmuteButton.setImage(Icon.UNMUTE.getImage());
    }


    public void registerUser() {
        LoginMessage message = controller.createUser (usernameFieldSignUp.getText (), nicknameFieldSignUp.getText (), passwordFieldSignUp.getText (), secondPasswordField.getText ());
        new PopUpMessage (message.getAlertType (), message.getLabel ());
        usernameFieldSignUp.clear();
        nicknameFieldSignUp.clear();
        passwordFieldSignUp.clear();
        secondPasswordField.clear();
    }

    public void loginUser(MouseEvent actionEvent) throws Exception {
        LoginMessage message = controller.loginUser (usernameFieldLogin.getText (), passwordFieldLogin.getText ());
        new PopUpMessage (message.getAlertType (), message.getLabel ());
        if (message.getAlertType().equals(Alert.AlertType.INFORMATION)) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/project/fxml/main_menu.fxml")));
            Utility.openNewMenu(root, (Node) actionEvent.getSource());
            PopUpMessage.getParent().setEffect(new GaussianBlur(20));
        } else PopUpMessage.getParent().setEffect(null);
    }

    public void nextTrack() {
        Music.nextTrack();
    }

    public void playPauseMusic() {
        Music.playPauseMusic(playPauseMusicButton);
    }

    public void muteUnmuteMusic() {
        Music.muteUnmuteMusic(muteUnmuteButton);
    }

    public void exit() {
        PopUpMessage popUpMessage = new PopUpMessage (Alert.AlertType.CONFIRMATION, LoginMessage.EXIT_CONFIRMATION.getLabel ());
        if (popUpMessage.getAlert ().getResult ().getText ().equals ("OK")) {
            System.exit (0);
        } else PopUpMessage.getParent().setEffect(null);
    }
}