package project.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import project.controller.LoginMenuController;
import project.model.Music;
import project.model.card.CardsDatabase;
import project.model.gui.Icon;
import project.view.messages.GamePopUpMessage;
import project.view.messages.LoginMessage;
import project.view.messages.PopUpMessage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class LoginMenuView extends Application {
    private static final LoginMenuController controller = LoginMenuController.getInstance();
    private static Stage stage;
    public TextField usernameFieldSignUp;
    public PasswordField passwordFieldSignUp;
    public TextField usernameFieldLogin;
    public PasswordField passwordFieldLogin;
    public PasswordField secondPasswordField;
    public TextField nicknameFieldSignUp;
    public ImageView playPauseMusicButton;
    public ImageView muteUnmuteButton;
    public BorderPane mainPane;
    public Pane secondPane;
    public ImageView exitButton;
    private AudioClip onClick = new AudioClip(Objects.requireNonNull(Utility.class.getResource("/project/soundEffects/CURSOR.wav")).toString());

    public static void main(String[] args) throws IOException {
        CardsDatabase.getInstance().readAndMakeCards();
        launch(args);
    }

    public static Stage getStage() {
        return stage;
    }

    @Override
    public void start(Stage stage) throws Exception {
        LoginMenuView.stage = stage;
        stage.setOnCloseRequest(event -> {
            PopUpMessage popUpMessage = new PopUpMessage(LoginMessage.EXIT_CONFIRMATION.getAlertType(), LoginMessage.EXIT_CONFIRMATION.getLabel());
            if (popUpMessage.getAlert().getResult().getText().equals("OK")) System.exit(0);
        });
        PopUpMessage.setStage(stage);
        GamePopUpMessage.setStage(stage);
        URL fxmlAddress = getClass().getResource("/project/fxml/login_menu.fxml");
        assert fxmlAddress != null;
        BorderPane root = FXMLLoader.load(fxmlAddress);
        Utility.setMainPane(root);
        Utility.setSecondPane(secondPane);
        PopUpMessage.setParent(root);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setMaximized(true);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setTitle("Yu-Gi-Oh!");
        stage.getIcons().add(Icon.YU_GI_OH.getImage());
        stage.show();
    }

    @FXML
    public void initialize() {
        Music.muteUnmuteButtons.add(muteUnmuteButton);
        if (!Music.isMediaPlayerPaused) playPauseMusicButton.setImage(Icon.PAUSE.getImage());
        else playPauseMusicButton.setImage(Icon.PLAY.getImage());
        if (Music.mediaPlayer.isMute()) muteUnmuteButton.setImage(Icon.MUTE.getImage());
        else muteUnmuteButton.setImage(Icon.UNMUTE.getImage());

        usernameFieldSignUp.setOnKeyPressed(k -> {
            if (k.getCode().equals(KeyCode.ENTER)) registerUser();
        });
        nicknameFieldSignUp.setOnKeyPressed(k -> {
            if (k.getCode().equals(KeyCode.ENTER)) registerUser();
        });
        passwordFieldSignUp.setOnKeyPressed(k -> {
            if (k.getCode().equals(KeyCode.ENTER)) registerUser();
        });
        secondPasswordField.setOnKeyPressed(k -> {
            if (k.getCode().equals(KeyCode.ENTER)) registerUser();
        });
        usernameFieldLogin.setOnKeyPressed(k -> {
            if (k.getCode().equals(KeyCode.ENTER)) {
                try {
                    loginUser();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        passwordFieldLogin.setOnKeyPressed(k -> {
            if (k.getCode().equals(KeyCode.ENTER)) {
                try {
                    loginUser();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void registerUser() {
        LoginMessage message = controller.createUser(usernameFieldSignUp.getText(), nicknameFieldSignUp.getText(), passwordFieldSignUp.getText(), secondPasswordField.getText());
        onClick.play();
        new PopUpMessage(message.getAlertType(), message.getLabel());
        usernameFieldSignUp.clear();
        nicknameFieldSignUp.clear();
        passwordFieldSignUp.clear();
        secondPasswordField.clear();
    }

    public void loginUser() throws Exception {
        LoginMessage message = controller.loginUser(usernameFieldLogin.getText(), passwordFieldLogin.getText());
        new PopUpMessage(message.getAlertType(), message.getLabel());
        if (message.getAlertType().equals(Alert.AlertType.INFORMATION)) {
            onClick.play();
            Utility.openNewMenu("/project/fxml/main_menu.fxml");
        }
    }

    public void nextTrack(MouseEvent actionEvent) {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        Music.nextTrack(playPauseMusicButton, muteUnmuteButton);
    }

    public void playPauseMusic(MouseEvent actionEvent) {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        Music.playPauseMusic(playPauseMusicButton);
    }

    public void muteUnmuteMusic(MouseEvent actionEvent) {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        Music.muteUnmuteMusic(muteUnmuteButton);
    }

    public void exit(MouseEvent actionEvent) {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        PopUpMessage popUpMessage = new PopUpMessage(Alert.AlertType.CONFIRMATION, LoginMessage.EXIT_CONFIRMATION.getLabel());
        if (popUpMessage.getAlert().getResult().getText().equals("OK")) {
            LoginMessage message = LoginMenuController.getInstance().close();
            if (message == LoginMessage.SUCCESS)
                System.exit(0);
        }
    }
}