package project.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
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

import java.awt.*;
import java.net.URL;
import java.util.Objects;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ProfileMenuView extends Application {
    private static final ProfileMenuController controller = ProfileMenuController.getInstance();
    @FXML
    public Label userNameLabel;
    @FXML
    public Label nickNameLabel;
    @FXML
    public AnchorPane anchorPane = new AnchorPane();

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
        System.out.println("gfsgd");
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
        TextField currentPasswordField = new TextField();
        Label newPasswordLabel = new Label();
        newPasswordLabel.setText("current password :");
        TextField newPasswordField = new TextField();
        Button changePasswordButton = new Button();
        changePasswordButton.setText("Change Password");
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
}