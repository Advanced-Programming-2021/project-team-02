package project.view;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.scene.control.Button;
import project.controller.ProfileMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.model.User;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.Objects;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import project.view.messages.PopUpMessage;
import project.view.messages.ProfileMenuMessage;



public class ProfileMenuView extends Application {
    private static Stage stage;
    private static ProfileMenuController controller = null;
    @FXML
    public Label userNameLabel;
    @FXML
    public Label nickNameLabel;
    @FXML
    public AnchorPane anchorPane = new AnchorPane();
    @FXML
    TextField currentPasswordField = new TextField();
    @FXML
    TextField newPasswordField = new TextField();
    @FXML
    TextField nickNameTextField = new TextField();
    @FXML
    ImageView profileImageView = new ImageView();

    private final User user = new User("mahdi", "12345", "test");

    @Override
    public void start(Stage stage) throws Exception {
        ProfileMenuView.stage = stage;
        URL urlMain = getClass().getResource("/project/fxml/profile_menu.fxml");
        Parent root = FXMLLoader.load(Objects.requireNonNull(urlMain));
        stage.setScene(new Scene(root));
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setFullScreenExitHint("");
        stage.show();
    }

    @FXML
    public void initialize() {
        controller = ProfileMenuController.getInstance();
        Image profileImage = new Image(String.valueOf(getClass().getResource("/project/image/ProfileMenuPictures/1.jpg")));
        profileImageView.setImage(profileImage);
        userNameLabel.setText("Username : " + user.getUsername());
        nickNameLabel.setText("Nickname : " + user.getNickname());
    }

    public void back() throws Exception {
        new MainMenuView().start(stage);
    }

    public void changePassword() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initOwner(stage);
        PopUpMessage.setStage(window);
        window.setTitle("Change Password");
        Label currentPasswordLabel = new Label();
        currentPasswordLabel.setText("current password :");
        currentPasswordLabel.setId("CP");
        Label newPasswordLabel = new Label();
        newPasswordLabel.setText("new password :");
        Button changePasswordButton = new Button();
        changePasswordButton.setText("Change Password");

        cssForChangePassword(currentPasswordLabel, newPasswordLabel, changePasswordButton);

        changePasswordButton.setOnAction(event -> {
            if (newPasswordField.getText().length() == 0 || currentPasswordField.getText().length() == 0) {
                new PopUpMessage(ProfileMenuMessage.INVALID_INPUT.getAlertType(),
                        ProfileMenuMessage.INVALID_INPUT.getLabel());
            } else {
                ProfileMenuMessage profileMenuMessage = controller.changePassword(currentPasswordField.getText(), newPasswordField.getText());
                new PopUpMessage(profileMenuMessage.getAlertType(), profileMenuMessage.getLabel());
            }
        });
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10, 50, 50, 50));
        layout.setMinSize(200, 200);
        layout.getChildren().addAll(currentPasswordLabel, currentPasswordField,
                newPasswordLabel, newPasswordField);
        layout.getChildren().add(changePasswordButton);
        layout.setAlignment(Pos.BASELINE_LEFT);
        layout.setStyle(String.valueOf(Color.web("#81c483")));
        Scene scene = new Scene(layout, 300, 300);
        scene.getStylesheets().add(String.valueOf(getClass().getResource("/project/CSS/ChangePassword.css")));
        window.setScene(scene);
        window.setResizable(false);
        window.showAndWait();
    }

    public void cssForChangePassword(Label currentPasswordLabel, Label newPasswordLabel, Button changePasswordButton) {
        currentPasswordLabel.setFont(Font.font("Cambria", 20));
        currentPasswordLabel.setTextFill(Color.web("#0076a3"));
        currentPasswordLabel.setWrapText(true);
        newPasswordLabel.setFont(Font.font("Cambria", 20));
        newPasswordLabel.setTextFill(Color.web("#0076a3"));
        newPasswordLabel.setWrapText(true);
        currentPasswordField.setMaxSize(200, 60);
        currentPasswordField.setStyle("-fx-background-insets: 0, 0 0 1 0 ;" +
                " -fx-background-color: grey; -fx-text-box-border, -fx-background ;");
        newPasswordField.setMaxSize(200,60);
        newPasswordField.setStyle("-fx-background-insets: 0, 0 0 1 0 ;" +
                " -fx-background-color: transparent;" +
                " -fx-background-color: grey; -fx-text-box-border, -fx-background ;");
        changePasswordButton.setPrefHeight(30);
        changePasswordButton.setStyle("-fx-border-color: red; -fx-text-fill: blue; -fx-border- " +
                "width: 3px; -fx-font-size: 15px;");
    }

    public void changeNickName() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initOwner(stage);
        PopUpMessage.setStage(window);
        window.setTitle("Change Nickname");
        Label currentNickNameLabel = new Label();
        currentNickNameLabel.setText("New nickname :");
        currentNickNameLabel.setFont(Font.font("Cambria", 20));
        currentNickNameLabel.setTextFill(Color.web("#0076a3"));
        Button changeNicknameButton = new Button();
        changeNicknameButton.setPrefHeight(30);
        changeNicknameButton.setStyle("-fx-border-color: red; -fx-text-fill: blue; -fx-border- " +
                "width: 3px; -fx-font-size: 15px;");
        changeNicknameButton.setText("Change Nickname");
        nickNameTextField.setMaxSize(200, 60);
        nickNameTextField.setStyle("-fx-background-insets: 0, 0 0 1 0 ;" +
                " -fx-background-color: grey; -fx-text-box-border, -fx-background ;");
        changeNicknameButton.setOnAction(event -> {
            if (nickNameTextField.getText().length() == 0) {
                new PopUpMessage(ProfileMenuMessage.INVALID_INPUT.getAlertType(),
                        ProfileMenuMessage.INVALID_INPUT.getLabel());
            } else {
                ProfileMenuMessage profileMenuMessage = controller.changeNickname(nickNameTextField.getText());
                new PopUpMessage(profileMenuMessage.getAlertType(), profileMenuMessage.getLabel());
            }
        });
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10, 50, 50, 50));
        layout.setMinSize(200, 200);
        layout.getChildren().addAll(currentNickNameLabel, nickNameTextField);
        layout.getChildren().add(changeNicknameButton);
        layout.setAlignment(Pos.BASELINE_LEFT);
        Scene scene = new Scene(layout, 300, 300);
        window.setScene(scene);
        scene.getStylesheets().add(String.valueOf(getClass().getResource("/project/CSS/ChangePassword.css")));
        window.setResizable(false);
        window.showAndWait();
    }

    public void changeProfilePicture() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initOwner(stage);
        PopUpMessage.setStage(window);
        window.setTitle("Change profile Picture");
        AnchorPane pane = new AnchorPane();

        Image firstImage = new Image(String.valueOf(getClass().getResource("/project/image/ProfileMenuPictures/1.jpg")));
        Image secondImage = new Image(String.valueOf(getClass().getResource("/project/image/ProfileMenuPictures/2.jpg")));
        Image thirdImage = new Image(String.valueOf(getClass().getResource("/project/image/ProfileMenuPictures/3.jpg")));

        ImageView firstPictureImageView = new ImageView(firstImage);
        ImageView secondPictureImageView = new ImageView(secondImage);
        ImageView thirdPictureImageView = new ImageView(thirdImage);

        firstPictureImageView.setFitWidth(200);
        firstPictureImageView.setFitHeight(200);
        secondPictureImageView.setFitHeight(200);
        secondPictureImageView.setFitWidth(200);
        thirdPictureImageView.setFitHeight(200);
        thirdPictureImageView.setFitWidth(200);

        firstPictureImageView.setLayoutY(0);
        firstPictureImageView.setLayoutX(400);
        secondPictureImageView.setLayoutX(200);
        secondPictureImageView.setLayoutY(0);

        Button firstPictureButton = new Button("Select");
        firstPictureButton.setOnAction(event -> profileImageView.setImage(firstImage));
        firstPictureButton.setLayoutX(480);
        firstPictureButton.setLayoutY(200);
        firstPictureButton.setStyle("-fx-border-color: red; -fx-text-fill: blue; -fx-border- " +
                "width: 3px; -fx-font-size: 12px;");

        Button secondPictureButton = new Button("Select");
        secondPictureButton.setOnAction(event -> profileImageView.setImage(secondImage));
        secondPictureButton.setLayoutX(270);
        secondPictureButton.setLayoutY(200);
        secondPictureButton.setStyle("-fx-border-color: red; -fx-text-fill: blue; -fx-border- " +
                "width: 3px; -fx-font-size: 12px;");

        Button thirdPictureButton = new Button("Select");
        thirdPictureButton.setOnAction(event -> profileImageView.setImage(thirdImage));
        thirdPictureButton.setLayoutY(200);
        thirdPictureButton.setLayoutX(75);
        thirdPictureButton.setStyle("-fx-border-color: red; -fx-text-fill: blue; -fx-border- " +
                "width: 3px; -fx-font-size: 12px;");

        pane.getChildren().add(firstPictureImageView);
        pane.getChildren().add(secondPictureImageView);
        pane.getChildren().add(thirdPictureImageView);
        pane.getChildren().add(firstPictureButton);
        pane.getChildren().add(secondPictureButton);
        pane.getChildren().add(thirdPictureButton);

        Label label = new Label();
        label.setText("Profile Pictures :");
        Scene scene = new Scene(pane, 600, 600);
        window.setScene(scene);
        window.showAndWait();


    }
}