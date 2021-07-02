package project.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
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
import javafx.scene.control.Button;
import project.model.card.Card;
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
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck
                (Card.getCardByName("Leotron"),
                        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck
                (Card.getCardByName("Leotron"),
                        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck
                (Card.getCardByName("Leotron"),
                        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));

//        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).createDeck("test1");
//        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).createDeck("test2");
//        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).createDeck("test3");
//        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).createDeck("test4");
//        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).createDeck("test5");
//        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).createDeck("test6");
//        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).createDeck("test1");
//        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).createDeck("test2");
//        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).createDeck("test3");
//        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).createDeck("test4");
//        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).createDeck("test5");
//        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).createDeck("test6");

        showDecks(mahdi);
    }

    private void showDecks(User user) {
        ArrayList<Deck> deckArrayList = Objects.requireNonNull(Assets.getAssetsByUsername(user.getUsername())).getAllDecks();
        ArrayList<Label> labelsOfDeckName = new ArrayList<>();
        ArrayList<Button> buttonsForDelete = new ArrayList<>();
        ArrayList<Button> buttonsForEdit = new ArrayList<>();

        int counterJ = 0;
        int counterSize = 0;
        for (int i = 0, j = 0; counterSize < Objects.requireNonNull(Assets.getAssetsByUsername(user.getUsername())).getAllDecks().size(); i++) {
            if (counterJ == 6) {
                j++;
                i = 0;
                counterJ = 0;
            }

            Label labelDeckName = new Label(deckArrayList.get(counterSize).getName());
            labelsOfDeckName.add(labelDeckName);
            labelDeckName.setFont(Font.font("Cambria", 30));
            labelDeckName.setTextFill(Color.web("#0076a3"));
            labelDeckName.setPrefHeight(30);
            labelDeckName.setPrefWidth(100);
            labelDeckName.setOnMouseClicked(event -> {
                showDeckInfo(labelDeckName);
            });

            ArrayList<Card> sideCards = deckArrayList.get(counterSize).getSideCards();
            ArrayList<Card> mainCards = deckArrayList.get(counterSize).getMainCards();
            Label labelNumberOfCardsMain = new Label("Main cards : " + mainCards.size());
            labelNumberOfCardsMain.setFont(Font.font("Cambria", 10));
            labelNumberOfCardsMain.setTextFill(Color.web("#0076a3"));
            labelNumberOfCardsMain.setPrefWidth(100);
            labelNumberOfCardsMain.setPrefHeight(30);

            Label labelNumberOfCardsSide = new Label(" Side cards : " + sideCards.size());
            labelNumberOfCardsSide.setFont(Font.font("Cambria", 10));
            labelNumberOfCardsSide.setTextFill(Color.web("#0076a3"));
            labelNumberOfCardsSide.setPrefWidth(100);
            labelNumberOfCardsSide.setPrefHeight(30);


            Button buttonDelete = new Button("delete deck");
            buttonDelete.setId(deckArrayList.get(i).getName());
            buttonDelete.setPrefHeight(30);
            buttonDelete.setPrefWidth(80);
            buttonsForDelete.add(buttonDelete);
            buttonDelete.setOnAction(event -> {
                checkDelete(buttonDelete);
            });

            Button buttonEdit = new Button("edit deck");
            buttonEdit.setId(deckArrayList.get(i).getName());
            buttonEdit.setPrefHeight(30);
            buttonEdit.setPrefWidth(80);
            buttonsForEdit.add(buttonEdit);

            VBox layout = new VBox(10);
            layout.setPadding(new Insets(30, 10, 10, 10));
            layout.setPrefHeight(100);
            layout.setPrefWidth(100);
            layout.setEffect(new DropShadow());

            layout.getChildren().addAll(labelDeckName, buttonEdit, buttonDelete, labelNumberOfCardsMain, labelNumberOfCardsSide);


            GridPane.add(layout, i, j);
            counterJ++;
            counterSize++;
        }

        GridPane.setPadding(new Insets(50, 10, 10, 50));
        GridPane.setVgap(100);
        GridPane.setHgap(100);
    }

    private void showDeckInfo(Label labelDeckName) {
    }

    private void checkDelete(Button button) {
        ArrayList<Deck> arrayList = Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getAllDecks();
        for (Deck deck : arrayList) {
            if (button.getId().equals(deck.getName())) System.out.println(deck.getName());
        }
    }
}