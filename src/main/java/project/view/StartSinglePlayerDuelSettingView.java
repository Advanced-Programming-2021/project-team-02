package project.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import project.controller.DuelMenuController;
import project.model.Music;
import project.model.gui.Icon;
import project.view.gameview.FlipCoinView;
import project.view.messages.PopUpMessage;
import project.view.messages.StartDuelMessage;

import java.io.IOException;
import java.util.Objects;

public class StartSinglePlayerDuelSettingView {
    public ImageView playPauseMusicButton;
    public ImageView muteUnmuteButton;
    private AudioClip onClick = new AudioClip(Objects.requireNonNull(Utility.class.getResource("/project/soundEffects/CURSOR.wav")).toString());


    @FXML
    public void initialize() {
        Music.muteUnmuteButtons.add(muteUnmuteButton);
        if (!Music.isMediaPlayerPaused) playPauseMusicButton.setImage(Icon.PAUSE.getImage());
        else playPauseMusicButton.setImage(Icon.PLAY.getImage());
        if (Music.mediaPlayer.isMute()) muteUnmuteButton.setImage(Icon.MUTE.getImage());
        else muteUnmuteButton.setImage(Icon.UNMUTE.getImage());
    }

    public void oneRoundGameWithAi(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() != MouseButton.PRIMARY) return;
        onClick.play();
        StartDuelMessage message = DuelMenuController.getInstance().startDuelWithAI(1);
        if (message == StartDuelMessage.SUCCESS) {
            FlipCoinView flipCoinView = (FlipCoinView) Utility.openMenuAndReturnController("/project/fxml/flip_coin_view.fxml");
            flipCoinView.setAi(true);
        }
        else {
            new PopUpMessage(message.getAlertType(), message.getLabel());
        }
    }

    public void matchDuelWithAi(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() != MouseButton.PRIMARY) return;
        onClick.play();
        StartDuelMessage message = DuelMenuController.getInstance().startDuelWithAI(3);
        new PopUpMessage(Alert.AlertType.INFORMATION,"This ability is locked for you !");
    }

    public void nextTrack(MouseEvent actionEvent) {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        onClick.play();
        Music.nextTrack(playPauseMusicButton, muteUnmuteButton);
    }

    public void playPauseMusic(MouseEvent actionEvent) {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        onClick.play();
        Music.playPauseMusic(playPauseMusicButton);
    }

    public void muteUnmuteMusic(MouseEvent actionEvent) {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        onClick.play();
        Music.muteUnmuteMusic(muteUnmuteButton);
    }

    public void back(MouseEvent actionEvent) throws IOException {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        onClick.play();
        Utility.openNewMenu("/project/fxml/duel_start_menu.fxml");
    }
}
