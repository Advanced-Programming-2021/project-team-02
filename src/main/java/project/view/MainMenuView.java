package project.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import project.model.Music;
import project.model.gui.Icon;
import project.view.messages.LoginMessage;
import project.view.messages.PopUpMessage;

import java.net.URL;
import java.util.Objects;

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

    public void duelMenu(MouseEvent actionEvent) throws Exception {
        if (actionEvent.getButton() != MouseButton.PRIMARY)
            return;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/project/fxml/duel_start_menu.fxml")));
        Utility.openNewMenu(root, (Node) actionEvent.getSource());
    }

    public void scoreboardMenu(MouseEvent actionEvent) throws Exception {
        if (actionEvent.getButton() != MouseButton.PRIMARY)
            return;
        new ScoreBoardView().start(stage);
    }

    public void profileMenu(MouseEvent actionEvent) throws Exception {
        if (actionEvent.getButton() != MouseButton.PRIMARY)
            return;
        new ProfileMenuView().start(stage);
    }

    public void shopMenu(MouseEvent actionEvent) throws Exception {
        if (actionEvent.getButton() != MouseButton.PRIMARY)
            return;
        new ShopMenuView().start(stage);
    }

    public void createCard(MouseEvent actionEvent) throws Exception {
        if (actionEvent.getButton() != MouseButton.PRIMARY)
            return;
    }

    public void importExportMenu(MouseEvent actionEvent) throws Exception {
        if (actionEvent.getButton() != MouseButton.PRIMARY)
            return;
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

    public void exit(MouseEvent actionEvent) {
        if (actionEvent.getButton() != MouseButton.PRIMARY)
            return;
        PopUpMessage popUpMessage = new PopUpMessage(Alert.AlertType.CONFIRMATION, LoginMessage.EXIT_CONFIRMATION.getLabel());
        if (popUpMessage.getAlert().getResult().getText().equals("OK")) {
            System.exit(0);
        } else PopUpMessage.getParent().setEffect(null);
    }
}