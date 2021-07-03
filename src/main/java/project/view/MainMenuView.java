package project.view;

import animatefx.animation.FadeIn;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import project.model.Music;
import project.model.gui.Icon;
import project.view.messages.LoginMessage;
import project.view.messages.PopUpMessage;

import java.io.IOException;
import java.util.Objects;

public class MainMenuView {
    public ImageView playPauseMusicButton;
    public ImageView muteUnmuteButton;

    @FXML
    public void initialize() {
        if (Music.mediaPlayer.isAutoPlay()) playPauseMusicButton.setImage(Icon.PAUSE.getImage());
        else playPauseMusicButton.setImage(Icon.PLAY.getImage());
        if (Music.mediaPlayer.isMute()) muteUnmuteButton.setImage(Icon.MUTE.getImage());
        else muteUnmuteButton.setImage(Icon.UNMUTE.getImage());
    }

    public void deckMenu(MouseEvent actionEvent) throws IOException {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/project/fxml/duel_start_menu.fxml")));
        Utility.openNewMenu(root, (Node) actionEvent.getSource());
    }

    public void duelMenu(MouseEvent actionEvent) throws Exception {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/project/fxml/duel_start_menu.fxml")));
        Utility.openNewMenu(root, (Node) actionEvent.getSource());
        new FadeIn(root).play();
    }

    public void scoreboardMenu(MouseEvent actionEvent) throws Exception {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/project/fxml/scoreboard_menu.fxml")));
        Utility.openNewMenu(root, (Node) actionEvent.getSource());
        new FadeIn(root).play();
    }

    public void profileMenu(MouseEvent actionEvent) throws Exception {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/project/fxml/profile_menu.fxml")));
        Utility.openNewMenu(root, (Node) actionEvent.getSource());
        new FadeIn(root).play();
    }

    public void shopMenu(MouseEvent actionEvent) throws Exception {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/project/fxml/shop_menu.fxml")));
        Utility.openNewMenu(root, (Node) actionEvent.getSource());
    }

    public void createCard(MouseEvent actionEvent) throws Exception {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
    }

    public void importExportMenu(MouseEvent actionEvent) throws Exception {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
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

    public void logout(MouseEvent actionEvent) throws Exception {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        PopUpMessage popUpMessage = new PopUpMessage(Alert.AlertType.CONFIRMATION, LoginMessage.LOGOUT_CONFIRMATION.getLabel());
        if (popUpMessage.getAlert().getResult().getText().equals("OK")) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/project/fxml/login_menu.fxml")));
            Utility.openNewMenu(root, (Node) actionEvent.getSource());
        } else PopUpMessage.getParent().setEffect(null);
    }

    public void exit(MouseEvent actionEvent) {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        PopUpMessage popUpMessage = new PopUpMessage(Alert.AlertType.CONFIRMATION, LoginMessage.EXIT_CONFIRMATION.getLabel());
        if (popUpMessage.getAlert().getResult().getText().equals("OK")) {
            System.exit(0);
        } else PopUpMessage.getParent().setEffect(null);
    }
}