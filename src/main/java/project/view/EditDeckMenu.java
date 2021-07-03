package project.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import project.controller.DeckMenuController;
import project.model.Assets;
import project.model.Deck;
import project.model.User;
import project.model.card.Card;
import project.view.messages.DeckMenuMessage;
import project.view.messages.PopUpMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class EditDeckMenu {
    public GridPane gridScrollPane;
    private String side;
    private int endOFI;
    private int endOFJ;
    private int endOFK;
    private static final DeckMenuController deckMenuController = DeckMenuController.getInstance();
    private static Utility utility;
    private String cardName = null;

    @FXML
    public void initialize() {
        Button button = deckMenuController.getOpenedDeckButton();
        utility = new Utility();
        utility.addImages();
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

        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToSideDeck(Card.getCardByName("Horn Imp"),
                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test2"));
        showEdit(button.getId());
    }

    private void showEdit(String deckName) {

        ArrayList<Deck> arrayList = Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getAllDecks();
        for (Deck deck : arrayList) {
            int counter = 0;
            int i, j;
            if (deck.getName().equals(deckName)) {

                for (i = 0, j = 0; counter < deck.getMainCards().size(); ) {
                    if (i >= 15) {
                        j++;
                        i = 0;
                    }
                    if (utility.getStringImageHashMap().containsKey(deck.getMainCards().get(counter).getName())) {
                        ImageView imageView = new ImageView(utility.getStringImageHashMap().get(deck.getMainCards().get(counter).getName()));
                        imageView.setId(deck.getMainCards().get(counter).getName());
                        imageView.setFitHeight(100);
                        imageView.setFitWidth(100);
                        int finalJ1 = j;
                        int finalI1 = i;
                        imageView.setOnMouseClicked(mouseEvent -> {
                            endOFI = finalI1;
                            endOFJ = finalJ1;
                            System.out.println(endOFI + " " + endOFJ);
                            addOrDeleteCard(imageView, "i");
                        });
                        gridScrollPane.add(imageView, i, j);
                        i++;
                    }
                    counter++;
                }

                //V1 : fasele ofoghi : har chi nishtar fasele kamtar
                //V2 fasele amoodi : harchi kamtar fasele bishtar

                Label label = new Label("Side Cards : ");
                label.setFont(Font.font("Cambria", 18));
                label.setTextFill(Color.web("#0076a3"));
                label.setPrefWidth(100);
                label.setPrefHeight(100);
                gridScrollPane.add(label, 0, j + 1);
                int counter2 = 0;
                int k, l;
                for (k = 0, l = j + 2; counter2 < deck.getSideCards().size(); ) {
                    if (k >= 15) {
                        l++;
                        k = 0;
                    }

                    if (utility.getStringImageHashMap().containsKey(deck.getSideCards().get(counter2).getName())) {
                        ImageView imageView = new ImageView(utility.getStringImageHashMap().get(deck.getSideCards().get(counter2).getName()));
                        imageView.setId(deck.getSideCards().get(counter2).getName());
                        imageView.setFitHeight(100);
                        imageView.setFitWidth(100);
                        int finalK2 = k;
                        imageView.setOnMouseClicked(mouseEvent -> {
                            endOFK = finalK2;
                            addOrDeleteCard(imageView, "k");
                        });
                        gridScrollPane.add(imageView, k, l);
                        k++;
                    }
                    counter2++;
                }

                Button button = new Button("Back");
                button.setOnAction(actionEvent -> System.exit(0));
                gridScrollPane.add(button, 0, 6);

                Button deleteButton = new Button("delete");
                deleteButton.setOnAction(event -> deleteCard(deckMenuController));
                gridScrollPane.add(deleteButton, 0, 7);

                Button addButton = new Button("add");
                addButton.setOnMouseClicked(mouseEvent -> {
                    try {
                        back(mouseEvent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                gridScrollPane.add(addButton, 0, 8);

                gridScrollPane.setPadding(new Insets(0, 0, -700, 0));
            }
        }
    }

    private void back(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() != MouseButton.PRIMARY) return;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/project/fxml/deck_menu.fxml")));
        Utility.openNewMenu(root, (Node) mouseEvent.getSource());
    }

    private void deleteCard(DeckMenuController deckMenuController) {
        if (cardName == null) {
            new PopUpMessage(DeckMenuMessage.YOU_DID_NOT_SELECT_ANY_CARD.getAlertType(),
                    DeckMenuMessage.YOU_DID_NOT_SELECT_ANY_CARD.getLabel());
        } else {
            if (side.equals("i")) {
                DeckMenuMessage deckMenuMessage = Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).removeCardFromMainDeck((endOFJ) * 15 + endOFI,
                        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName(deckMenuController.getOpenedDeckButton().getId()));
                PopUpMessage popUpMessage = new PopUpMessage(deckMenuMessage.getAlertType(), deckMenuMessage.getLabel());
                if (popUpMessage.getAlert().getResult().getText().equals("OK")) {
                    gridScrollPane.getChildren().clear();
                    showEdit(deckMenuController.getOpenedDeckButton().getId());
                }
                PopUpMessage.getParent().setEffect(null);

            } else if (side.equals("k")) {
                DeckMenuMessage deckMenuMessage = Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).removeCardFromSideDeck(endOFK,
                        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName(deckMenuController.getOpenedDeckButton().getId()));
                PopUpMessage popUpMessage = new PopUpMessage(deckMenuMessage.getAlertType(), deckMenuMessage.getLabel());
                if (popUpMessage.getAlert().getResult().getText().equals("OK")) {
                    gridScrollPane.getChildren().clear();
                    showEdit(deckMenuController.getOpenedDeckButton().getId());
                }
                PopUpMessage.getParent().setEffect(null);
            }
        }
    }

    private void addOrDeleteCard(ImageView imageView, String sidee) {
        cardName = imageView.getId();
        side = sidee;
    }
}
