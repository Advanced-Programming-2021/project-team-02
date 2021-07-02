package project.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import project.controller.DeckMenuController;
import project.model.Assets;
import project.model.Deck;
import project.model.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class DeckMenuView extends Application {
    private static final DeckMenuController controller = DeckMenuController.getInstance ();
    private static Stage stageMain;

    @Override
    public void start(Stage stage) throws Exception {
        stageMain = stage;
        URL urlMain = getClass().getResource("/project/fxml/deck_menu.fxml");
        System.out.println(urlMain);
        Parent root = FXMLLoader.load(Objects.requireNonNull(urlMain));
        stage.setScene(new Scene(root));
        //stage.show();
        //stage.setFullScreen(true);
//        stage.setResizable(false);
//        stage.setMaximized(true);
//        stage.setFullScreenExitHint("");
       // stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        //stage.show();
    }

    @FXML
    public void initialize() {
        User mahdi = new User("mahdi", "12345", "test");
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).createDeck("test1");
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).createDeck("test2");
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).createDeck("test3");
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).createDeck("test4");

        showDecks(mahdi);
    }

   private void showDecks(User user) {
        GridPane gridPane = new GridPane();
        ArrayList<Deck> deckArrayList = Objects.requireNonNull(Assets.getAssetsByUsername(user.getUsername())).getAllDecks();
       System.out.println(String.valueOf(getClass().getResource("/project/image/DeckMenuPictures/DeckPicture.jpg")));
        Image decksImage = new Image(String.valueOf(getClass().getResource("/project/image/DeckMenuPictures/DeckPicture.jpg")));
        //Image firstImage = new Image(String.valueOf(getClass().getResource("/project/image/ProfileMenuPictures/1.jpg")));
        ImageView deckImageView = new ImageView(decksImage);

        for (int i = 0, j = 0; i < Objects.requireNonNull(Assets.getAssetsByUsername(user.getUsername())).getAllDecks().size(); i++) {
            gridPane.addRow(i, deckImageView);
        }
        Scene scene = new Scene(gridPane);
        stageMain.setScene(scene);
        stageMain.show();
    }
}