package project.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import project.controller.MainMenuController;
import project.controller.ProfileMenuController;
import project.model.Avatar;
import project.model.Music;
import project.model.gui.Icon;
import project.view.messages.LoginMessage;
import project.view.messages.PopUpMessage;
import project.view.messages.ProfileMenuMessage;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.Objects;

public class ProfileMenuView {
    private final AudioClip onClick = new AudioClip(Objects.requireNonNull(Utility.class.getResource("/project/soundEffects/CURSOR.wav")).toString());
    private static final ProfileMenuController controller = ProfileMenuController.getInstance();
    public static final SnapshotParameters parameters = new SnapshotParameters();
    public Label userNameLabel;
    public Label nickNameLabel;
    public Label passwordLabel;
    public ImageView playPauseMusicButton;
    public ImageView muteUnmuteButton;
    public ImageView exitButton;
    public ListView listView = new ListView();
    @FXML
    PasswordField currentPasswordField = new PasswordField();
    @FXML
    PasswordField newPasswordField = new PasswordField();
    @FXML
    TextField nickNameTextField = new TextField();

    @FXML
    ImageView profileImageView = new ImageView();
    @FXML
    Image image1 = new Image(String.valueOf(Avatar.AVATAR_1.getUrl()));
    @FXML
    Image image2 = new Image(String.valueOf(Avatar.AVATAR_2.getUrl()));
    @FXML
    Image image3 = new Image(String.valueOf(Avatar.AVATAR_3.getUrl()));
    @FXML
    Image image4 = new Image(String.valueOf(Avatar.AVATAR_4.getUrl()));

    static {
        parameters.setFill(Color.TRANSPARENT);
    }

    @FXML
    public void initialize() {
        profileImageView.setImage(new Image(String.valueOf(MainMenuController.getInstance().getLoggedInUser().getAvatarURL())));
        userNameLabel.setText(MainMenuController.getInstance().getLoggedInUser().getUsername());
        nickNameLabel.setText(MainMenuController.getInstance().getLoggedInUser().getNickname());
        passwordLabel.setText("●".repeat(MainMenuController.getInstance().getLoggedInUser().getPassword().length()));

        Music.muteUnmuteButtons.add(muteUnmuteButton);
        if (!Music.isMediaPlayerPaused) playPauseMusicButton.setImage(Icon.PAUSE.getImage());
        else playPauseMusicButton.setImage(Icon.PLAY.getImage());
        if (Music.mediaPlayer.isMute()) muteUnmuteButton.setImage(Icon.MUTE.getImage());
        else muteUnmuteButton.setImage(Icon.UNMUTE.getImage());
    }


    private Button closeButton(Stage window, TextField usernameTextField) {
        Button closeButton = new Button();
        closeButton.setText("Close");
        closeButton.setOnAction(event -> {
            onClick.play();
            window.close();
            usernameTextField.clear();
            PopUpMessage.setStage(LoginMenuView.getStage());
        });
        closeButton.setCursor(Cursor.HAND);
        closeButton.setId("closeButton");
        return closeButton;
    }

    public void changeNickName() {
        Stage window = new Stage();
        window.initOwner(LoginMenuView.getStage());
        window.initStyle(StageStyle.UNDECORATED);
        PopUpMessage.setStage(window);

        Label title = new Label("Change Nickname");
        title.setId("title");

        nickNameTextField.setPromptText("Enter new Nickname");
        nickNameTextField.setId("field");

        Button changeNicknameButton = new Button();
        changeNicknameButton.setText("Change");
        changeNicknameButton.setOnAction(event -> {
            onClick.play();
            ProfileMenuMessage profileMenuMessage = controller.changeNickname(nickNameTextField.getText());
            new PopUpMessage(profileMenuMessage.getAlertType(), profileMenuMessage.getLabel());
            nickNameLabel.setText(MainMenuController.getInstance().getLoggedInUser().getNickname());
            nickNameTextField.clear();
        });
        changeNicknameButton.setCursor(Cursor.HAND);
        changeNicknameButton.setId("button");

        Button closeButton = closeButton(window, nickNameTextField);

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20, 50, 20, 50));
        layout.getChildren().addAll(title, nickNameTextField, changeNicknameButton, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 350, 225);
        layout.getScene().setFill(Color.TRANSPARENT);
        window.initStyle(StageStyle.TRANSPARENT);
        window.setScene(scene);
        window.setResizable(false);
        window.getScene().getStylesheets().add(String.valueOf(getClass().getResource("/project/CSS/profile_menu_windows.css")));
        window.showAndWait();
    }

    public void changePassword() {
        Stage window = new Stage();
        window.initOwner(LoginMenuView.getStage());
        window.initStyle(StageStyle.UNDECORATED);
        PopUpMessage.setStage(window);

        Label title = new Label("Change Password");
        title.setId("title");

        currentPasswordField.setPromptText("Enter current password");
        newPasswordField.setPromptText("Enter new password");
        currentPasswordField.setId("field");
        newPasswordField.setId("field");

        Button changePasswordButton = new Button();
        changePasswordButton.setText("Change");
        changePasswordButton.setOnAction(event -> {
            onClick.play();
            if (newPasswordField.getText().length() == 0 || currentPasswordField.getText().length() == 0) {
                new PopUpMessage(ProfileMenuMessage.INVALID_INPUT.getAlertType(),
                        ProfileMenuMessage.INVALID_INPUT.getLabel());
            } else {
                ProfileMenuMessage profileMenuMessage = controller.changePassword(currentPasswordField.getText(), newPasswordField.getText());
                new PopUpMessage(profileMenuMessage.getAlertType(), profileMenuMessage.getLabel());
                passwordLabel.setText("●".repeat(MainMenuController.getInstance().getLoggedInUser().getPassword().length()));
                if (profileMenuMessage.getAlertType().equals(Alert.AlertType.INFORMATION)) window.close();
            }
        });
        changePasswordButton.setCursor(Cursor.HAND);
        changePasswordButton.setId("button");

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
        layout.getChildren().addAll(title, currentPasswordField, newPasswordField, changePasswordButton, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 350, 300);
        layout.getScene().setFill(Color.TRANSPARENT);
        window.initStyle(StageStyle.TRANSPARENT);
        window.setScene(scene);
        window.setResizable(false);
        window.getScene().getStylesheets().add(String.valueOf(getClass().getResource("/project/CSS/profile_menu_windows.css")));
        window.showAndWait();
    }

    public void changeProfilePicture() {
        Stage window = new Stage();
        window.initOwner(LoginMenuView.getStage());
        window.initStyle(StageStyle.UNDECORATED);
        window.initModality(Modality.WINDOW_MODAL);
        PopUpMessage.setStage(window);

        Label title = new Label("Profile Pictures");
        title.setId("title");

        ImageView imageView1 = new ImageView(image1);
        ImageView imageView2 = new ImageView(image2);
        ImageView imageView3 = new ImageView(image3);
        ImageView imageView4 = new ImageView(image4);

        imageView1.setFitWidth(200);
        imageView1.setFitHeight(200);
        imageView2.setFitHeight(200);
        imageView2.setFitWidth(200);
        imageView3.setFitHeight(200);
        imageView3.setFitWidth(200);
        imageView4.setFitHeight(200);
        imageView4.setFitWidth(200);

        imageView1.setCursor(Cursor.HAND);
        imageView2.setCursor(Cursor.HAND);
        imageView3.setCursor(Cursor.HAND);
        imageView4.setCursor(Cursor.HAND);

        createClip(imageView1);
        createClip(imageView2);
        createClip(imageView3);
        createClip(imageView4);

        VBox vBox1 = new VBox();
        VBox vBox2 = new VBox();
        VBox vBox3 = new VBox();
        VBox vBox4 = new VBox();

        vBox1.getChildren().add(imageView1);
        vBox2.getChildren().add(imageView2);
        vBox3.getChildren().add(imageView3);
        vBox4.getChildren().add(imageView4);

        if (profileImageView.getImage().getUrl().equals(image1.getUrl())) vBox1.setId("container");
        else if (profileImageView.getImage().getUrl().equals(image2.getUrl())) vBox2.setId("container");
        else if (profileImageView.getImage().getUrl().equals(image3.getUrl())) vBox3.setId("container");
        else if (profileImageView.getImage().getUrl().equals(image4.getUrl())) vBox4.setId("container");

        imageView1.setOnMouseClicked(event -> {
            onClick.play();
            vBox1.setId("container");
            vBox2.setId(null);
            vBox3.setId(null);
            vBox4.setId(null);
            profileImageView.setImage(image1);
            MainMenuController.getInstance().getLoggedInUser().setAvatarURL(Avatar.AVATAR_1.getUrl());
        });

        imageView2.setOnMouseClicked(event -> {
            onClick.play();
            vBox2.setId("container");
            vBox1.setId(null);
            vBox3.setId(null);
            vBox4.setId(null);
            profileImageView.setImage(image2);
            MainMenuController.getInstance().getLoggedInUser().setAvatarURL(Avatar.AVATAR_2.getUrl());
        });
        imageView3.setOnMouseClicked(event -> {
            onClick.play();
            vBox3.setId("container");
            vBox2.setId(null);
            vBox1.setId(null);
            vBox4.setId(null);
            profileImageView.setImage(image3);
            MainMenuController.getInstance().getLoggedInUser().setAvatarURL(Avatar.AVATAR_3.getUrl());
        });

        imageView4.setOnMouseClicked(event -> {
            onClick.play();
            vBox4.setId("container");
            vBox2.setId(null);
            vBox3.setId(null);
            vBox1.setId(null);
            profileImageView.setImage(image4);
            MainMenuController.getInstance().getLoggedInUser().setAvatarURL(Avatar.AVATAR_4.getUrl());
        });

        Button closeButton = new Button();
        closeButton.setText("Close");
        closeButton.setOnAction(event -> {
            onClick.play();
            window.close();
            PopUpMessage.setStage(LoginMenuView.getStage());
        });
        closeButton.setCursor(Cursor.HAND);
        closeButton.setId("closeButton");

        HBox topBar = new HBox(25);
        topBar.getChildren().addAll(vBox1, vBox2);
        topBar.setAlignment(Pos.CENTER);

        HBox bottomBar = new HBox(25);
        bottomBar.getChildren().addAll(vBox3, vBox4);
        bottomBar.setAlignment(Pos.CENTER);

        VBox mainLayout = new VBox(25);
        mainLayout.setPadding(new Insets(20, 50, 20, 50));
        mainLayout.getChildren().addAll(title, topBar, bottomBar, closeButton);
        mainLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(mainLayout, 600, 600);
        mainLayout.getScene().setFill(Color.TRANSPARENT);
        window.initStyle(StageStyle.TRANSPARENT);
        window.setScene(scene);
        window.setResizable(false);
        window.getScene().getStylesheets().add(String.valueOf(getClass().getResource("/project/CSS/profile_menu_windows.css")));
        window.showAndWait();
    }

    private void createClip(ImageView imageView) {
        Rectangle clip = new Rectangle();
        clip.setWidth(200.0f);
        clip.setHeight(200.0f);
        clip.setArcHeight(30);
        clip.setArcWidth(30);
//        clip.setStroke(Color.BLACK);
        imageView.setClip(clip);
        WritableImage wImage = imageView.snapshot(ProfileMenuView.parameters, null);
//        imageView.setClip(null);
//        imageView.setEffect(new DropShadow(20, Color.WHITE));
        imageView.setImage(wImage);
    }

    public void nextTrack(MouseEvent actionEvent) {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        onClick.play();
        Music.nextTrack(playPauseMusicButton, muteUnmuteButton);
    }

    public void playPauseMusic(MouseEvent actionEvent) {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        onClick.play();
        Music.playPauseMusic(playPauseMusicButton);
    }

    public void muteUnmuteMusic(MouseEvent actionEvent) {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        onClick.play();
        Music.muteUnmuteMusic(muteUnmuteButton);
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        if (mouseEvent.getButton() != MouseButton.PRIMARY) return;
        onClick.play();
        Utility.openNewMenu("/project/fxml/main_menu.fxml");
    }

    public void exit(MouseEvent actionEvent) {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        onClick.play();
        PopUpMessage popUpMessage = new PopUpMessage(Alert.AlertType.CONFIRMATION, LoginMessage.EXIT_CONFIRMATION.getLabel());
        if (popUpMessage.getAlert().getResult().getText().equals("OK")) System.exit(0);
    }

    public void chooseProfilePicture(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG, PNG, JEPG Files", "*.jpg", "*.png", "*.jepg"));
        File selectedFile = fc.showOpenDialog(LoginMenuView.getStage());
        if (selectedFile != null) {
            listView.getItems().add(selectedFile.getAbsoluteFile());
            String fileName = String.valueOf(listView.getItems());
            fileName = fileName.substring(1, fileName.length() - 1);
            System.out.println(fileName);
            try {
                MainMenuController.getInstance().getLoggedInUser().setAvatarURL(Paths.get(fileName).toUri().toURL());
                profileImageView.setImage(new Image(String.valueOf(Paths.get(fileName).toUri().toURL())));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            listView.getItems().clear();

        } else {
            System.out.println("file is not valid");
        }
    }
}