package project.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.swing.text.Element;

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

    private static Stage stage;
    private final User user = new User("mahdi", "12345", "test");

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        URL urlMain = getClass().getResource("/project/fxml/ProfileMenuView.fxml");
        System.out.println(urlMain);
        Parent root = FXMLLoader.load(Objects.requireNonNull(urlMain));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void initialize() {
         controller = ProfileMenuController.getInstance();
        userNameLabel.setText("Username : " + user.getUsername());
        nickNameLabel.setText("Nickname : " + user.getNickname());
    }

    public void back() {
        System.exit(0);
    }

    public void changePassword(ActionEvent actionEvent) {
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
            if (controller == null) System.out.println("nulle baba");
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

    public void changeNickName(ActionEvent actionEvent) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Change Nickname");
        Label currentNickNameLabel = new Label();
        currentNickNameLabel.setText("New nickname :");
        Button changeNicknameButton = new Button();
        changeNicknameButton.setText("Change Nickname");
        changeNicknameButton.setOnAction(event -> {
            controller.changeNickname(nickNameTextField.getText());
        });
        VBox layout = new VBox(10);
        layout.setMinSize(200, 200);
        layout.getChildren().addAll(currentNickNameLabel, nickNameTextField);
        layout.getChildren().add(changeNicknameButton);
        layout.setAlignment(Pos.BASELINE_LEFT);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public void changeProfilePicture(ActionEvent actionEvent) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Change profile Picture");
        Pane pane = new HBox(15);
        Image image = new Image("/project/image/ProfileMenuPictures/2.jpg");
        pane.getChildren().add(new javafx.scene.image.ImageView(image));
        ImageView imageView1 = new ImageView(image);
        pane.getChildren().add(imageView1);
        Label label = new Label();
        label.setText("Profile Pictures :");
        Scene scene = new Scene(pane);
        window.setScene(scene);
        window.showAndWait();


    }
}