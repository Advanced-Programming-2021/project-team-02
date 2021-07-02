package project.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
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

    public void openSinglePlayerChoices(ActionEvent actionEvent) {

        Button oneRoundButton = new Button("One Round Duel");
        final StartDuelMessage[] message = new StartDuelMessage[1];
        oneRoundButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                message[0] = DuelMenuController.getInstance().startDuelWithAI(1);
                new PopUpMessage(message[0].getAlertType(), message[0].getLabel());
            }
        });
        Button matchButton = new Button("Duel Match");
        matchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                message[0] = DuelMenuController.getInstance().startDuelWithAI(3);
                new PopUpMessage(message[0].getAlertType(), message[0].getLabel());
            }
        });
        VBox box = new VBox(oneRoundButton, matchButton);
        box.setLayoutX(singlePlayerButton.getLayoutX() + 200);
        box.setLayoutY(singlePlayerButton.getLayoutY());
        pane.getChildren().add(box);

    }

    public void openMultiPlayerChoices(ActionEvent actionEvent) {

    }

    public void back(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/project/fxml/main_menu.fxml")));
        Utility.openNewMenu(root, (Node) actionEvent.getSource());
    }


}
