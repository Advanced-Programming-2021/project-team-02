package project.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import project.view.messages.LoginMessage;
import project.view.messages.PopUpMessage;

import java.net.URL;

public class MainMenuView extends Application {
    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        MainMenuView.stage = stage;
        PopUpMessage.setStage(stage);
        URL fxmlAddress = getClass().getResource("/project/fxml/main_menu.fxml");
        assert fxmlAddress != null;
        Parent main = FXMLLoader.load(fxmlAddress);
        Scene scene = new Scene(main);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setFullScreenExitHint("");
        stage.show();
    }

    public void deckMenu() throws Exception {
        new DeckMenuView().start(stage);
    }

    public void duelMenu() throws Exception {
        new DuelMenuView().start(stage);
    }

    public void scoreboardMenu() throws Exception {
        new ScoreBoardView().start(stage);
    }

    public void profileMenu() throws Exception {
        new ProfileMenuView().start(stage);
    }

    public void shopMenu() throws Exception {
    }

    public void createCard(MouseEvent mouseEvent) {
    }

    public void importExportMenu() throws Exception {
    }

    public void logout() throws Exception {
        PopUpMessage popUpMessage = new PopUpMessage(Alert.AlertType.CONFIRMATION, LoginMessage.LOGOUT_CONFIRMATION.getLabel());
        if (popUpMessage.getAlert().getResult().getText().equals("OK")) new LoginMenuView().start(stage);
    }

    public void exit() {
        PopUpMessage popUpMessage = new PopUpMessage(Alert.AlertType.CONFIRMATION, LoginMessage.EXIT_CONFIRMATION.getLabel());
        if (popUpMessage.getAlert().getResult().getText().equals("OK")) System.exit(0);
    }
}