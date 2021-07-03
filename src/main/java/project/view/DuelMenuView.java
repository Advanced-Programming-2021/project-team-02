package project.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import project.controller.DuelMenuController;
import project.controller.MainMenuController;
import project.view.messages.PopUpMessage;
import project.view.messages.ProfileMenuMessage;
import project.view.messages.StartDuelMessage;

import java.io.IOException;
import java.net.URI;
import java.util.Objects;

public class DuelMenuView {
    public AnchorPane pane;

    public void initialize() {

    }

    public void singlePlayer(MouseEvent actionEvent) throws IOException {
        if (actionEvent.getButton() != MouseButton.PRIMARY)
            return;
        Parent loader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/project/fxml/start_single_player_duel_setting_menu.fxml")));
        Utility.openNewMenu(loader, (Node) actionEvent.getSource());
    }

    public void multiPlayer(MouseEvent actionEvent) throws IOException {
        if (actionEvent.getButton() != MouseButton.PRIMARY)
            return;
        Parent loader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/project/fxml/start_multi_player_duel_setting_menu.fxml")));
        Utility.openNewMenu(loader, (Node) actionEvent.getSource());

    }

    public void back(MouseEvent actionEvent) throws IOException {
        if (actionEvent.getButton() != MouseButton.PRIMARY)
            return;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/project/fxml/main_menu.fxml")));
        Utility.openNewMenu(root, (Node) actionEvent.getSource());
    }


}
