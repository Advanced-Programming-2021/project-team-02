package project.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import project.controller.GlobalChatController;
import project.controller.MainMenuController;
import project.view.messages.GlobalChatMessage;
import project.view.messages.PopUpMessage;

import java.io.IOException;
import java.util.Objects;

public class GlobalChatView {
    private final AudioClip onClick = new AudioClip(Objects.requireNonNull(Utility.class.getResource("/project/soundEffects/CURSOR.wav")).toString());
    public ImageView sendButton;
    public TextField textPlace;
    public GridPane gridPane;
    public int j = 0;
    public TextArea textArea;
    public String textToAppend;
    public String sendData;
    public TextArea input;
    public Label onlineLabel;


    public VBox chatVBox;
    public TextField messageText;
    public Label onlineNumberLabel;
    public ScrollPane scrollPane;

    @FXML
    public void initialize() {
        GlobalChatController.getInstance().setView(this);
        GlobalChatController.getInstance().initializeNetworkToSend();
        GlobalChatController.getInstance().initializeNetworkToReceive();
        scrollPane.setContent(chatVBox);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        chatVBox.setSpacing(7);
    }


    public void back(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() != MouseButton.PRIMARY)
            return;
        GlobalChatController.getInstance().close();
        onClick.play();
        Utility.openNewMenu("/project/fxml/main_menu.fxml");
    }

    public void enter(KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("ENTER")) {
            keyEvent.consume();
            sendMessage();
        }
    }

    private void sendMessage() {
        sendData = "(" + MainMenuController.getInstance().getLoggedInUser().getAvatarURL() + ")" + " <" + MainMenuController.getInstance().getLoggedInUser().getUsername() + "> : " + messageText.getText();
        GlobalChatMessage globalChatMessage = GlobalChatController.getInstance().sendChatMessage(sendData);
        if (globalChatMessage != GlobalChatMessage.MESSAGE_SENT) {
            new PopUpMessage(globalChatMessage.getAlertType(), globalChatMessage.getLabel());
            messageText.clear();
            return;
        }
        messageText.clear();
    }

    public void sendByClick(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() != MouseButton.PRIMARY)
            return;
        sendMessage();
    }

    public void addMessage() {
        String avatar = GlobalChatController.getInstance().getAvatarToAppend();
        String message = GlobalChatController.getInstance().getTextToAppend();
        ImageView imageView = new ImageView(avatar);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        HBox hBox = new HBox();
        Label label = new Label(message);
        label.getStylesheets().add(getClass().getResource("/project/CSS/global_chat_view.css").toString());
        label.setId("label");
        label.setWrapText(true);
        hBox.getChildren().addAll(imageView, label);
        chatVBox.getChildren().add(hBox);
    }
}
