package project.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import project.controller.DuelMenuController;
import project.view.messages.PopUpMessage;
import project.view.messages.StartDuelMessage;

import java.io.IOException;
import java.util.Objects;

public class StartSinglePlayerDuelSettingView {
    public void oneRoundGameWithAi(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() != MouseButton.PRIMARY)
            return;
        StartDuelMessage message = DuelMenuController.getInstance().startDuelWithAI(1);
        if (message == StartDuelMessage.SUCCESS) {
//TODO
        } else {
            new PopUpMessage(message.getAlertType(), message.getLabel());
        }
    }

    public void matchDuelWithAi(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() != MouseButton.PRIMARY)
            return;
        StartDuelMessage message = DuelMenuController.getInstance().startDuelWithAI(3);
        if (message == StartDuelMessage.SUCCESS) {
//TODO
        } else {
            new PopUpMessage(message.getAlertType(), message.getLabel());
        }
    }

    public void back(MouseEvent actionEvent) throws IOException {
        if (actionEvent.getButton() != MouseButton.PRIMARY)
            return;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/project/fxml/duel_start_menu.fxml")));
        Utility.openNewMenu(root, (Node) actionEvent.getSource());
    }
}
