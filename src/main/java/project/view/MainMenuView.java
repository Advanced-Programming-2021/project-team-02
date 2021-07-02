package project.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import project.model.Music;
import project.model.gui.Icon;
import project.view.messages.LoginMessage;
import project.view.messages.PopUpMessage;

import java.net.URL;

public class MainMenuView extends Application {
    private static Stage stage;
    public ImageView playPauseMusicButton;
    public ImageView muteUnmuteButton;

    @Override
    public void start(Stage stage) throws Exception {
        MainMenuView.stage = stage;
        PopUpMessage.setStage(stage);
        URL fxmlAddress = getClass().getResource("/project/fxml/main_menu.fxml");
        assert fxmlAddress != null;
        Parent root = FXMLLoader.load(fxmlAddress);
        PopUpMessage.setParent(root);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setFullScreen(true);
        stage.setMaximized(true);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setFullScreenExitHint("");
        stage.show();
    }

    public void deckMenu() throws Exception {
        new DeckMenuView().start(stage);
    }

    public void duelMenu() throws Exception {
        new DuelMenuView().start(stage);
    }

    public void scoreboardMenu() throws Exception {
        new ScoreBoardView().start(stage);
    }

    public void profileMenu() throws Exception {
        new ProfileMenuView().start(stage);
    }

    public void shopMenu() throws Exception {
        new ShopMenuView().start(stage);
    }

    public void createCard() throws Exception{
    }

    public void importExportMenu() throws Exception {
    }

    public void playPauseMusic() {
        if (playPauseMusicButton.getImage().equals(Icon.PAUSE.getImage())) {
            playPauseMusicButton.setImage(Icon.PLAY.getImage());
            Music.mediaPlayer.pause();
        } else {
            playPauseMusicButton.setImage(Icon.PAUSE.getImage());
            Music.mediaPlayer.play();
        }
    }

    public void muteUnmuteMusic() {
        if (Music.mediaPlayer.isMute()) {
            muteUnmuteButton.setImage(Icon.UNMUTE.getImage());
            Music.mediaPlayer.setMute(false);
        } else {
            muteUnmuteButton.setImage(Icon.MUTE.getImage());
            Music.mediaPlayer.setMute(true);
        }
    }

    public void logout() throws Exception {
        PopUpMessage popUpMessage = new PopUpMessage(Alert.AlertType.CONFIRMATION, LoginMessage.LOGOUT_CONFIRMATION.getLabel());
        if (popUpMessage.getAlert().getResult().getText().equals("OK")) {
            new LoginMenuView().start(stage);
        } else PopUpMessage.getParent().setEffect(null);
    }

    public void exit() {
        PopUpMessage popUpMessage = new PopUpMessage(Alert.AlertType.CONFIRMATION, LoginMessage.EXIT_CONFIRMATION.getLabel());
        if (popUpMessage.getAlert().getResult().getText().equals("OK")) {
            System.exit(0);
        } else PopUpMessage.getParent().setEffect(null);
    }
}