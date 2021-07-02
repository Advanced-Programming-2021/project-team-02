package project.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import project.controller.DuelMenuController;
import javafx.scene.control.Button;
import project.view.messages.PopUpMessage;
import project.view.messages.StartDuelMessage;

import java.io.IOException;
import java.util.Objects;

public class DuelMenuView {
    private static final DuelMenuController controller = DuelMenuController.getInstance();
    private static Stage stage;
    public Button singlePlayerButton;
    public AnchorPane pane;

    public void initialize() {

    }

    public void openSinglePlayerChoices(MouseEvent actionEvent) {
        if (actionEvent.getButton() != MouseButton.PRIMARY)
            return;
        Button oneRoundButton = new Button("One Round Duel");
        final StartDuelMessage[] message = new StartDuelMessage[1];
        oneRoundButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                message[0] = DuelMenuController.getInstance().startDuelWithAI(1);
                if (message[0] != StartDuelMessage.SUCCESS)
                    new PopUpMessage(message[0].getAlertType(), message[0].getLabel());
                //TODO else start game
            }
        });
        Button matchButton = new Button("Duel Match");
        matchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                message[0] = DuelMenuController.getInstance().startDuelWithAI(3);
                if (message[0] != StartDuelMessage.SUCCESS)
                    new PopUpMessage(message[0].getAlertType(), message[0].getLabel());
                //TODO else start game

            }
        });
        VBox box = new VBox(oneRoundButton, matchButton);
        box.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/project/CSS/duel_start_vbox.css")).toExternalForm());
        box.setLayoutX(singlePlayerButton.getLayoutX() + 200);
        box.setLayoutY(singlePlayerButton.getLayoutY());
        pane.getChildren().add(box);

    }

    public void openMultiPlayerChoices(MouseEvent actionEvent) {
        if (actionEvent.getButton() != MouseButton.PRIMARY)
            return;
    }

    public void back(MouseEvent actionEvent) throws IOException {
        if (actionEvent.getButton() != MouseButton.PRIMARY)
            return;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/project/fxml/main_menu.fxml")));
        Utility.openNewMenu(root, (Node) actionEvent.getSource());
    }


}
