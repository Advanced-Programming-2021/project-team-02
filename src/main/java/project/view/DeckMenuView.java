package project.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import project.controller.DeckMenuController;
import project.model.Assets;
import project.model.Deck;
import project.model.User;
import javafx.scene.control.Label;
import project.view.messages.PopUpMessage;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class DeckMenuView extends Application {
    private static final DeckMenuController controller = DeckMenuController.getInstance();
    private static Stage stageMain;
    User mahdi = new User("mahdi", "12345", "test");
    @FXML
    public GridPane GridPane;

    @Override
    public void start(Stage stage) throws Exception {
        stageMain = stage;
        URL urlMain = getClass().getResource("/project/fxml/deck_menu.fxml");
        System.out.println(urlMain);
        Parent root = FXMLLoader.load(Objects.requireNonNull(urlMain));
        stage.setScene(new Scene(root));
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setMaximized(true);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.show();
    }

    @FXML
    public void initialize() {
        User mahdi = new User("mahdi", "12345", "test");
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).createDeck("test1");
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).createDeck("test2");
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).createDeck("test3");
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).createDeck("test4");
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).createDeck("test5");
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).createDeck("test6");

        showDecks(mahdi);
    }

    private void showDecks(User user) {
        ArrayList<Deck> deckArrayList = Objects.requireNonNull(Assets.getAssetsByUsername(user.getUsername())).getAllDecks();
        //Image decksImage = new Image(String.valueOf(getClass().getResource("/project/image/DeckMenuPictures/DeckPicture.jpg")));
        ArrayList<Label> labels = new ArrayList<>();
        for (int i = 0, j = 0; i < Objects.requireNonNull(Assets.getAssetsByUsername(user.getUsername())).getAllDecks().size(); i++) {
            Label label = new Label(deckArrayList.get(i).getName());
            labels.add(label);
            label.setFont(Font.font("Cambria", 40));
            label.setTextFill(Color.web("#0076a3"));
            label.setMinSize(40, 40);
            GridPane.add(label, i, j);

        }
        GridPane.setPadding(new Insets(20,20,20,20));
        GridPane.setVgap(100);
        GridPane.setHgap(100);
        for (Label label : labels) {
            if (label.getText().equals("test1")) System.out.println("finally");
        }
    }
}