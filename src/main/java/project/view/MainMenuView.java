package project.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.util.Duration;
import project.view.messages.LoginMessage;
import project.view.messages.PopUpMessage;

import java.net.URL;

public class MainMenuView extends Application {
    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        MainMenuView.stage = stage;
        URL fxmlAddress = getClass().getResource("/project/fxml/main_menu.fxml");
        Parent login = FXMLLoader.load(fxmlAddress);
        Scene scene = new Scene(login);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setScene(scene);
    }

    public void deckMenu() {
    }

    public void duelMenu() {
    }

    public void scoreboardMenu() {
    }

    public void profileMenu() {
    }

    public void shopMenu() {
    }

    public void importExportMenu() {
    }

    public void logout() throws Exception {
        PopUpMessage popUpMessage = new PopUpMessage(Alert.AlertType.CONFIRMATION, LoginMessage.EXIT_CONFIRMATION.getLabel());
        if (popUpMessage.getAlert().getResult().getText().equals("OK")) new LoginMenuView().start(stage);
    }

    public void exit() {
        PopUpMessage popUpMessage = new PopUpMessage(Alert.AlertType.CONFIRMATION, LoginMessage.EXIT_CONFIRMATION.getLabel());
        if (popUpMessage.getAlert().getResult().getText().equals("OK")) System.exit(0);
    }
}