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
import project.controller.GlobalChatController;
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
    public String textToAppend;

    public void setTextToAppend(String textToAppend) {
        this.textToAppend = textToAppend;
    }

    @FXML
    public void initialize() {
        GlobalChatController.getInstance().setView(this);
        GlobalChatController.getInstance().readChats();
        textArea.setEditable(false);
        textPlace.setPromptText("Type your message ...");
    }

    public void sendButtonClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() != MouseButton.PRIMARY)
            return;
        sendMessge();
    }

    private void sendMessge() {
        if (textPlace.getLength() == 0) {
            new PopUpMessage(GlobalChatMessage.WRITE_FIRST.getAlertType(), GlobalChatMessage.WRITE_FIRST.getLabel());
        } else {
            GlobalChatMessage globalChatMessage = GlobalChatController.getInstance().sendChatMessage(textPlace.getText());
            if (globalChatMessage != GlobalChatMessage.MESSAGE_SENT) {
                new PopUpMessage(globalChatMessage.getAlertType(), globalChatMessage.getLabel());
                textPlace.clear();
                return;
            }
            textArea.appendText("<" + MainMenuController.getInstance().getLoggedInUser().getUsername() + "> : " + textPlace.getText() + "\n")
            ;
            textPlace.clear();


        }
    }

    public void enterToSendMessage(javafx.event.ActionEvent event) {
        sendMessge();
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() != MouseButton.PRIMARY)
            return;
        onClick.play();
        Utility.openNewMenu("/project/fxml/main_menu.fxml");
    }

    public void setMessageForTextArea() {
        textArea.appendText(GlobalChatController.getInstance().getTextToAppend() + "\n");
    }
}
