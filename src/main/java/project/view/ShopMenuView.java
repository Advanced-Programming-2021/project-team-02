package project.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import project.controller.ShopMenuController;
import javafx.scene.control.ScrollPane;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import project.model.Assets;
import project.model.User;
import project.model.card.Card;

public class ShopMenuView extends Application {
    private static ShopMenuController controller = null;
    public static HashMap<Button, Image> allImages;
    @FXML
    public Label C1;
    @FXML
    public Label C2;
    @FXML
    public Label C3;
    @FXML
    public Label C4;
    @FXML
    public Label C5;
    @FXML
    public Label C6;
    @FXML
    public Label C7;
    @FXML
    public Label C8;
    @FXML
    public Label C9;
    @FXML
    public Label C10;
    @FXML
    public Label C11;
    @FXML
    public Label C12;
    @FXML
    public Label C13;
    @FXML
    public Label C14;
    @FXML
    public Label C15;
    @FXML
    public Label C16;
    @FXML
    public Label C17;
    @FXML
    public Label C18;
    @FXML
    public Label C19;
    @FXML
    public Label C20;
    @FXML
    public Label C21;
    @FXML
    public Label C22;
    @FXML
    public Label C23;
    @FXML
    public Label C24;
    @FXML
    public Label C25;
    @FXML
    public Label C26;
    @FXML
    public Label C27;
    @FXML
    public Label C28;
    @FXML
    public Label C29;
    @FXML
    public Label C30;
    @FXML
    public Label C31;
    @FXML
    public Label C32;
    @FXML
    public Label C33;
    @FXML
    public Label C34;
    @FXML
    public Label C35;
    @FXML
    public Label C36;
    @FXML
    public Label C37;
    @FXML
    public Label C38;
    @FXML
    public Label C39;
    @FXML
    public Label C40;
    @FXML
    public Label C41;
    @FXML
    public Label C42;
    @FXML
    public Label C43;
    @FXML
    public Label C44;
    @FXML
    public Label C45;
    @FXML
    public Label C46;
    @FXML
    public Label C47;
    @FXML
    public Label C48;
    @FXML
    public Label C49;
    @FXML
    public Label C50;



    @Override
    public void start(Stage stage) throws Exception {
        URL urlMain = getClass().getResource("/project/fxml/ShopMenuView.fxml");
        System.out.println(urlMain);
        Parent root = FXMLLoader.load(Objects.requireNonNull(urlMain));
        stage.setScene(new Scene(root));
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.show();
    }

    @FXML
    public void initialize() {
        new User("mahdi", "123456", "test");
        C1.setText("hello");
        HashMap<Card, Integer> allUserCards = Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getAllUserCards();
        for (Card card : allUserCards.keySet()) {
            if (card.getName().equals("Alexandrite Dragon")) C1.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Axe Raider")) C2.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Baby dragon")) C3.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Battle OX")) C4.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Battle warrior")) C5.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Beast King Barbaros")) C6.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Bitron")) C7.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Blue-Eyes white dragon")) C8.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Crab Turtle")) C9.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Crawling dragon")) C10.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Curtain of the dark ones")) C11.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Dark Blade")) C12.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Dark magician")) C13.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Exploder Dragon")) C14.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Feral Imp")) C15.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Fireyarou")) C16.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Flame manipulator")) C17.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Gate Guardian")) C18.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Haniwa")) C19.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Hero of the east")) C20.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Horn Imp")) C21.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Leotron")) C22.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Man-Eater Bug")) C23.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Silver Fang")) C24.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Skull Guardian")) C25.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Slot Machine")) C26.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Spiral Serpent")) C27.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("The Tricky")) C28.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Warrior Dai Grepher")) C29.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Wattaildragon")) C30.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Wattkid")) C31.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Yomi Ship")) C32.setText(String.valueOf(allUserCards.get(card)));

            if (card.getName().equals("Advanced Ritual Art")) C33.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Black Pendant")) C34.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Closed Forest")) C35.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Dark Hole")) C36.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Forest")) C37.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Harpie's Feather Duster")) C38.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Monster Reborn")) C39.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Pot of Greed")) C40.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Raigeki")) C41.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Sword of dark destruction")) C42.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Terraforming")) C43.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Umiiruka")) C44.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Yami")) C45.setText(String.valueOf(allUserCards.get(card)));

            if (card.getName().equals("Magic Cylinder")) C46.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Mirror Force")) C47.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Negate Attack")) C48.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Torrential Tribute")) C49.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Trap Hole")) C50.setText(String.valueOf(allUserCards.get(card)));

        }
    }
}