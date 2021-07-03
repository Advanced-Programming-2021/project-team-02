package project.view;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import project.model.Assets;
import project.model.Deck;
import project.model.User;
import project.model.card.Card;

import java.util.ArrayList;
import java.util.Objects;

public class ContinueDeckMenuView {
    @FXML
    public GridPane gridPaneInfo;

    @FXML
    public void initialize() {
        Utility utility = new Utility();
        utility.addImages();
        for (String s : utility.getStringImageHashMap().keySet()) {
            System.out.println(s);
        }
        User mahdi = new User("mahdi", "12345", "test");
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).createDeck("test1");
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).createDeck("test2");
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).createDeck("test3");
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).createDeck("test4");
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).createDeck("test5");
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).createDeck("test6");
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Haniwa"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Haniwa"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Haniwa"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Haniwa"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Haniwa"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Haniwa"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Haniwa"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Haniwa"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Haniwa"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Haniwa"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Haniwa"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Haniwa"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Haniwa"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Haniwa"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Haniwa"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Haniwa"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));

        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToSideDeck(Card.getCardByName("Haniwa"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToSideDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToSideDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToSideDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToSideDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToSideDeck(Card.getCardByName("Haniwa"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToSideDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToSideDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToSideDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToSideDeck(Card.getCardByName("Haniwa"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToSideDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToSideDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToSideDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToSideDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToSideDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
        showDeckInfo(utility);
    }

    public void showDeckInfo(Utility utility) {
        ArrayList<Deck> arrayList = Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getAllDecks();
        for (Deck deck : arrayList) {
            int counter = 0;
            int i, j;
            if (deck.getName().equals("test1")) {

                for (i = 0, j = 0; counter < deck.getMainCards().size(); ) {
                    if (i >= 15) {
                        j++;
                        i = 0;
                    }
                    if (utility.getStringImageHashMap().containsKey(deck.getMainCards().get(counter).getName())) {
                        ImageView imageView = new ImageView(utility.getStringImageHashMap().get(deck.getMainCards().get(counter).getName()));
                        imageView.setFitHeight(100);
                        imageView.setFitWidth(100);
                        gridPaneInfo.add(imageView, i, j);
                        i++;
                    }
                    counter++;
                }
                //V1 : fasele ofoghi : har chi nishtar fasele kamtar
                //V2 fasele amoodi : harchi kamtar fasele bishtar
                gridPaneInfo.setPadding(new Insets(0, 0, 150, 0));

                Label label = new Label("Side Cards : ");
                label.setFont(Font.font("Cambria", 18));
                label.setTextFill(Color.web("#0076a3"));
                label.setPrefWidth(100);
                label.setPrefHeight(100);
                gridPaneInfo.add(label, 0, j + 1);
                int counter2 = 0;
                int k, l;
                for (k = 0, l = j + 2; counter2 < deck.getSideCards().size(); ) {
                    if (k >= 15) {
                        l++;
                        k = 0;
                    }

                    if (utility.getStringImageHashMap().containsKey(deck.getSideCards().get(counter2).getName())) {
                        ImageView imageView = new ImageView(utility.getStringImageHashMap().get(deck.getSideCards().get(counter2).getName()));
                        imageView.setFitHeight(100);
                        imageView.setFitWidth(100);
                        gridPaneInfo.add(imageView, k, l);
                        k++;
                    }
                    counter2++;
                }
                Button button = new Button("Back");
                button.setOnAction(actionEvent -> System.exit(0));
                gridPaneInfo.add(button,0 , k+1);
                Button button1 = new Button("Set Deck Active");
                button1.setOnAction(actionEvent -> System.exit(0));
                gridPaneInfo.add(button1, 0, k+2);
            }
        }
    }
}
