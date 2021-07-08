package project.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import project.model.Music;
import project.model.gui.Icon;
import project.view.messages.LoginMessage;
import project.view.messages.PopUpMessage;

import java.io.IOException;

public class MainMenuView {
    public ImageView playPauseMusicButton;
    public ImageView muteUnmuteButton;
    public ImageView exitButton;

    @FXML
    public void initialize() {
        Music.muteUnmuteButtons.add(muteUnmuteButton);
        if (!Music.isMediaPlayerPaused) playPauseMusicButton.setImage(Icon.PAUSE.getImage());
        else playPauseMusicButton.setImage(Icon.PLAY.getImage());
        if (Music.mediaPlayer.isMute()) muteUnmuteButton.setImage(Icon.MUTE.getImage());
        else muteUnmuteButton.setImage(Icon.UNMUTE.getImage());
    }

    public void deckMenu(MouseEvent actionEvent) throws IOException {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        Utility.openNewMenu("/project/fxml/deck_menu.fxml");
    }

    public void duelMenu(MouseEvent actionEvent) throws Exception {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        Utility.openNewMenu("/project/fxml/duel_start_menu.fxml");
    }

    public void scoreboardMenu(MouseEvent actionEvent) throws Exception {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        Utility.openNewMenu("/project/fxml/scoreboard_menu.fxml");
    }

    public void profileMenu(MouseEvent actionEvent) throws Exception {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        Utility.openNewMenu("/project/fxml/profile_menu.fxml");
    }

    public void shopMenu(MouseEvent actionEvent) throws Exception {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        Utility.openNewMenu("/project/fxml/shop_menu.fxml");
    }

    public void createCard(MouseEvent actionEvent) throws Exception {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        Utility.openNewMenu("/project/fxml/create_card.fxml");

    }

    public void importExportMenu(MouseEvent actionEvent) throws Exception {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        Utility.openNewMenu("/project/fxml/import_export_menu.fxml");
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

    public void logout(MouseEvent actionEvent) throws Exception {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        PopUpMessage popUpMessage = new PopUpMessage(Alert.AlertType.CONFIRMATION, LoginMessage.LOGOUT_CONFIRMATION.getLabel());
        if (popUpMessage.getAlert().getResult().getText().equals("OK")) Utility.openNewMenu("/project/fxml/login_menu.fxml");

    }

    public void exit(MouseEvent actionEvent) {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        PopUpMessage popUpMessage = new PopUpMessage(Alert.AlertType.CONFIRMATION, LoginMessage.EXIT_CONFIRMATION.getLabel());
        if (popUpMessage.getAlert().getResult().getText().equals("OK")) System.exit(0);
    }
}