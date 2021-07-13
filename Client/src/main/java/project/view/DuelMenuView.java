package project.view;

import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import project.model.Music;
import project.model.gui.Icon;
import project.view.messages.LoginMessage;
import project.view.messages.PopUpMessage;

import java.io.IOException;
import java.util.Objects;

public class DuelMenuView {
    public AnchorPane pane;
    public ImageView playPauseMusicButton;
    public ImageView muteUnmuteButton;
    public ImageView exitButton;
    private AudioClip onClick = new AudioClip(Objects.requireNonNull(Utility.class.getResource("/project/soundEffects/CURSOR.wav")).toString());

    public void initialize() {
        Music.muteUnmuteButtons.add(muteUnmuteButton);
        if (!Music.isMediaPlayerPaused) playPauseMusicButton.setImage(Icon.PAUSE.getImage());
        else playPauseMusicButton.setImage(Icon.PLAY.getImage());
        if (Music.mediaPlayer.isMute()) muteUnmuteButton.setImage(Icon.MUTE.getImage());
        else muteUnmuteButton.setImage(Icon.UNMUTE.getImage());
    }

    public void singlePlayer(MouseEvent actionEvent) throws IOException {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        onClick.play();
        Utility.openNewMenu("/project/fxml/start_single_player_duel_setting_menu.fxml");
    }

    public void multiPlayer(MouseEvent actionEvent) throws IOException {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        onClick.play();
        Utility.openNewMenu("/project/fxml/start_multi_player_duel_setting_menu.fxml");
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

    public void back(MouseEvent actionEvent) throws IOException {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        Utility.openNewMenu("/project/fxml/main_menu.fxml");
    }

    public void exit(MouseEvent actionEvent) {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        PopUpMessage popUpMessage = new PopUpMessage(Alert.AlertType.CONFIRMATION, LoginMessage.EXIT_CONFIRMATION.getLabel());
        if (popUpMessage.getAlert().getResult().getText().equals("OK")) System.exit(0);
    }
}
