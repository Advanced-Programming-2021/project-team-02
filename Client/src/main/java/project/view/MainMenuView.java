package project.view;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import project.controller.AdminController;
import project.controller.MainMenuController;
import project.model.Music;
import project.model.gui.Icon;
import project.view.messages.AdminPanelMessage;
import project.view.messages.LoginMessage;
import project.view.messages.PopUpMessage;
import project.view.messages.ProfileMenuMessage;

import java.io.IOException;
import java.util.Objects;

public class MainMenuView {
    public ImageView playPauseMusicButton;
    public ImageView muteUnmuteButton;
    public ImageView exitButton;
    private AudioClip onClick = new AudioClip(Objects.requireNonNull(Utility.class.getResource("/project/soundEffects/CURSOR.wav")).toString());

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
        onClick.play();
        Utility.openNewMenu("/project/fxml/deck_menu.fxml");
    }

    public void duelMenu(MouseEvent actionEvent) throws Exception {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        onClick.play();
        Utility.openNewMenu("/project/fxml/duel_start_menu.fxml");
    }

    public void scoreboardMenu(MouseEvent actionEvent) throws Exception {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        onClick.play();
        Utility.openNewMenu("/project/fxml/scoreboard_menu.fxml");
    }

    public void profileMenu(MouseEvent actionEvent) throws Exception {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        onClick.play();
        Utility.openNewMenu("/project/fxml/profile_menu.fxml");
    }

    public void shopMenu(MouseEvent actionEvent) throws Exception {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        onClick.play();
        Utility.openNewMenu("/project/fxml/new_shop.fxml");
    }

    public void createCard(MouseEvent actionEvent) throws Exception {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        onClick.play();
        Utility.openNewMenu("/project/fxml/create_card.fxml");

    }

    public void importExportMenu(MouseEvent actionEvent) throws Exception {
//        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
//        Utility.openNewMenu("/project/fxml/import_export_menu.fxml");
        ImportExportView importExportView = new ImportExportView();
        importExportView.showImages();
    }

    public void nextTrack(MouseEvent actionEvent) {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        Music.nextTrack(playPauseMusicButton, muteUnmuteButton);
        onClick.play();
    }

    public void playPauseMusic(MouseEvent actionEvent) {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        Music.playPauseMusic(playPauseMusicButton);
        onClick.play();
    }

    public void muteUnmuteMusic(MouseEvent actionEvent) {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        Music.muteUnmuteMusic(muteUnmuteButton);
        onClick.play();
    }

    public void logout(MouseEvent actionEvent) throws Exception {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        onClick.play();
        PopUpMessage popUpMessage = new PopUpMessage(Alert.AlertType.CONFIRMATION, LoginMessage.LOGOUT_CONFIRMATION.getLabel());
        MainMenuController.getInstance().logout();
        if (popUpMessage.getAlert().getResult().getText().equals("OK"))
            Utility.openNewMenu("/project/fxml/login_menu.fxml");

    }

    public void exit(MouseEvent actionEvent) {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        PopUpMessage popUpMessage = new PopUpMessage(Alert.AlertType.CONFIRMATION, LoginMessage.EXIT_CONFIRMATION.getLabel());
        if (popUpMessage.getAlert().getResult().getText().equals("OK")) System.exit(0);
    }

    public void adminPanel(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() != MouseButton.PRIMARY) return;
        onClick.play();
        AdminPanelMessage message = AdminController.getInstance().initializeNetworkForAdmin();
        if (message != AdminPanelMessage.SUCCESS) {
            new PopUpMessage(message.getAlertType(), message.getLabel());
            return;
        }
        if (getAdminPassAndUsername()) {
            Utility.openNewMenu("/project/fxml/admin_view.fxml");
        }
    }

    private boolean getAdminPassAndUsername() {
        final Boolean[] flag = {false};
        Stage window = new Stage();
        window.initOwner(LoginMenuView.getStage());
        window.initStyle(StageStyle.UNDECORATED);
        PopUpMessage.setStage(window);

        Label title = new Label("Open Admin Menu");
        title.setId("title");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter admin username");
        usernameField.setId("field");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter admin password");
        passwordField.setId("field");

        Button okButton = new Button();
        okButton.setText("OK");
        okButton.setOnAction(event -> {
            onClick.play();
            if (passwordField.getText().length() == 0 || usernameField.getText().length() == 0) {
                new PopUpMessage(ProfileMenuMessage.FILL_THE_FIELDS.getAlertType(),
                        ProfileMenuMessage.FILL_THE_FIELDS.getLabel());
            } else {
                if (usernameField.getText().equals("admin1") && passwordField.getText().equals("1admin")) {
                    flag[0] = true;
                    window.close();
                } else new PopUpMessage(Alert.AlertType.ERROR, "Wrong username or password!");


            }
        });
        okButton.setCursor(Cursor.HAND);
        okButton.setId("button");

        Button closeButton = new Button();
        closeButton.setText("Close");
        closeButton.setOnAction(event -> {
            onClick.play();
            window.close();
            PopUpMessage.setStage(LoginMenuView.getStage());
        });
        closeButton.setCursor(Cursor.HAND);
        closeButton.setId("closeButton");

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20, 50, 20, 50));
        layout.getChildren().addAll(title, usernameField, passwordField, okButton, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 350, 300);
        layout.getScene().setFill(Color.TRANSPARENT);
        window.initStyle(StageStyle.TRANSPARENT);
        window.setScene(scene);
        window.setResizable(false);
        window.getScene().getStylesheets().add(String.valueOf(getClass().getResource("/project/CSS/profile_menu_windows.css")));
        window.showAndWait();
        return flag[0];
    }

    public void openChat(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() != MouseButton.PRIMARY) return;
        onClick.play();
        Utility.openNewMenu("/project/fxml/global_chat.fxml");
    }
}