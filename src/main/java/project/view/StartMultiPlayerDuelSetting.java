package project.view;

import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import project.controller.DuelMenuController;
import project.model.Music;
import project.view.messages.PopUpMessage;
import project.view.messages.StartDuelMessage;

import java.io.IOException;

public class StartMultiPlayerDuelSetting {


    public TextField username;
    public ImageView playPauseMusicButton;
    public ImageView muteUnmuteButton;

    public void oneRoundMultiPlayerGame(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() != MouseButton.PRIMARY)
            return;
        if (username.getText().equals("")) {
            new PopUpMessage(StartDuelMessage.FILL_OPPONENT_USERNAME.getAlertType(), StartDuelMessage.FILL_OPPONENT_USERNAME.getLabel());
            return;
        }
        StartDuelMessage message = DuelMenuController.getInstance().startDuelWithOtherPlayer(username.getText(), 1);
        if (message == StartDuelMessage.SUCCESS) {
            Utility.openNewMenu("/project/fxml/flip_coin_view.fxml");
        } else {
            new PopUpMessage(message.getAlertType(), message.getLabel());
        }
    }

    public void matchDuelMultiPlayer(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() != MouseButton.PRIMARY)
            return;
        if (username.getText().equals("")) {
            new PopUpMessage(StartDuelMessage.FILL_OPPONENT_USERNAME.getAlertType(), StartDuelMessage.FILL_OPPONENT_USERNAME.getLabel());
            return;
        }
        StartDuelMessage message = DuelMenuController.getInstance().startDuelWithOtherPlayer(username.getText(), 3);
        if (message == StartDuelMessage.SUCCESS) {
            Utility.openNewMenu("/project/fxml/flip_coin_view.fxml");
        } else {
            new PopUpMessage(message.getAlertType(), message.getLabel());
        }
    }

    public void nextTrack(MouseEvent actionEvent) {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        Music.nextTrack();
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
        Utility.openNewMenu("/project/fxml/duel_start_menu.fxml");
    }
}
