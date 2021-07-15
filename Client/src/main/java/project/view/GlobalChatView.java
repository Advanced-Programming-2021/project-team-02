package project.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import project.controller.MainMenuController;
import project.view.messages.GlobalChatMessage;
import project.view.messages.PopUpMessage;

public class GlobalChatView {
    public ImageView sendButton;
    public TextField textPlace;
    public ScrollPane scrollPage;
    public GridPane gridPane;
    public int j = 0;
    public ListView listview;
    public TextArea textArea;

    @FXML
    public void initialize() {
        textArea.setEditable(false);
        textPlace.setPromptText("Type your message ...");
    }

    public void sendButtonClicked(MouseEvent mouseEvent) {
        if (textPlace.getLength() == 0) {
            new PopUpMessage(GlobalChatMessage.WRITE_FIRST.getAlertType(), GlobalChatMessage.WRITE_FIRST.getLabel());
        } else {
            VBox layout = new VBox(10);
            Label label = new Label();
            label.setText(MainMenuController.getInstance().getLoggedInUser().getUsername() + " : " + textPlace.getText());
            layout.getChildren().add(label);
            textArea.setWrapText(true);
            textArea.setEditable(false);
            textArea.appendText(label.getText() + "\n");
            textPlace.clear();

        }
    }
}
