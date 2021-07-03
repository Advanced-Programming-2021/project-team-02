package project.view;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import project.view.messages.PopUpMessage;

public class Utility {
    public static void openNewMenu(Parent root, Node source) {
        Scene scene = new Scene(root);
        PopUpMessage.setParent(root);
        Stage stage = (Stage) source.getScene().getWindow();
        PopUpMessage.setStage(stage);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setFullScreen(true);
        stage.setMaximized(true);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setFullScreenExitHint("");
        stage.show();

    }
}