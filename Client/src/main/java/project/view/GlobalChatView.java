package project.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import project.controller.MainMenuController;
import project.view.messages.GlobalChatMessage;
import project.view.messages.PopUpMessage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Objects;

public class GlobalChatView {
    private final AudioClip onClick = new AudioClip(Objects.requireNonNull(Utility.class.getResource("/project/soundEffects/CURSOR.wav")).toString());
    public ImageView sendButton;
    public TextField textPlace;
    public ScrollPane scrollPage;
    public GridPane gridPane;
    public int j = 0;
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

    public void enterToSendMessage(javafx.event.ActionEvent event) {
        if (textPlace.getLength() == 0) {
            new PopUpMessage(GlobalChatMessage.WRITE_FIRST.getAlertType(), GlobalChatMessage.WRITE_FIRST.getLabel());
        } else {
            VBox layout = new VBox(10);
            Label label = new Label();
            label.setText("<" + MainMenuController.getInstance().getLoggedInUser().getUsername() + ">" + " : " + textPlace.getText());
            layout.getChildren().add(label);
            textArea.setWrapText(true);
            textArea.setEditable(false);
            textArea.appendText(label.getText() + "\n");
            textPlace.clear();

        }
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() != MouseButton.PRIMARY)
            return;
        onClick.play();
        Utility.openNewMenu("/project/fxml/main_menu.fxml");
    }
}
