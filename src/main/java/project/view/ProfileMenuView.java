package project.view;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
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


public class ProfileMenuView extends Application {
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
    public void start(Stage primaryStage) throws Exception {
        URL urlMain = getClass().getResource("/project/fxml/ProfileMenuView.fxml");
        System.out.println(urlMain);
        Parent root = FXMLLoader.load(Objects.requireNonNull(urlMain));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @FXML
    public void initialize() {
        controller = ProfileMenuController.getInstance();
        Image profileImage = new Image(String.valueOf(getClass().getResource("/project/image/ProfileMenuPictures/1.jpg")));
        profileImageView.setImage(profileImage);
        userNameLabel.setText("Username : " + user.getUsername());
        nickNameLabel.setText("Nickname : " + user.getNickname());
    }

    public void back() {
        System.exit(0);
    }

    public void changePassword() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Change Password");
        Label currentPasswordLabel = new Label();
        currentPasswordLabel.setText("current password :");
        Label newPasswordLabel = new Label();
        newPasswordLabel.setText("new password :");
        Button changePasswordButton = new Button();
        changePasswordButton.setText("Change Password");
        changePasswordButton.setOnAction(event -> {
            System.out.println(currentPasswordField.getText());
            System.out.println(newPasswordField.getText());
            controller.changePassword(currentPasswordField.getText(), newPasswordField.getText());
        });
        VBox layout = new VBox(10);
        layout.setMinSize(200, 200);
        layout.getChildren().addAll(currentPasswordLabel, currentPasswordField,
                newPasswordLabel, newPasswordField);
        layout.getChildren().add(changePasswordButton);
        layout.setAlignment(Pos.BASELINE_LEFT);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public void changeNickName() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Change Nickname");
        Label currentNickNameLabel = new Label();
        currentNickNameLabel.setText("New nickname :");
        Button changeNicknameButton = new Button();
        changeNicknameButton.setText("Change Nickname");
        changeNicknameButton.setOnAction(event -> controller.changeNickname(nickNameTextField.getText()));
        VBox layout = new VBox(10);
        layout.setMinSize(200, 200);
        layout.getChildren().addAll(currentNickNameLabel, nickNameTextField);
        layout.getChildren().add(changeNicknameButton);
        layout.setAlignment(Pos.BASELINE_LEFT);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public void changeProfilePicture() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
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

        Button secondPictureButton = new Button("Select");
        secondPictureButton.setOnAction(event -> profileImageView.setImage(secondImage));
        secondPictureButton.setLayoutX(280);
        secondPictureButton.setLayoutY(200);

        Button thirdPictureButton = new Button("Select");
        thirdPictureButton.setOnAction(event -> profileImageView.setImage(thirdImage));
        thirdPictureButton.setLayoutY(200);
        thirdPictureButton.setLayoutX(80);

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