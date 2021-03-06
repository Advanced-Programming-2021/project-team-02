package project.view;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import project.Main;
import project.controller.DeckMenuController;
import project.controller.MainMenuController;
import project.model.Assets;
import project.model.Deck;
import project.model.User;
import project.model.card.Card;
import project.view.messages.DeckMenuMessage;
import project.view.messages.PopUpMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class EditDeckView {
    private final AudioClip onClick = new AudioClip(Objects.requireNonNull(getClass().getResource("/project/soundEffects/CURSOR.wav")).toString());
    public Label mainDeckLabel;
    public Label sideDeckLabel;
    public GridPane sideDeckGridPane;
    public GridPane mainDeckGridPane;
    public AnchorPane mainPane;
    public Pane draggingPane;
    public VBox allCardsVbox;
    public ScrollPane scrollPane;
    public AnchorPane anchorPane;
    public ImageView selectedCardImageView;
    public Label selectedCardLabel;
    public Label yourCardsLabel;
    private DataFormat sideDeckGridPaneFormat;
    private DataFormat mainDeckGridPaneFormat;
    private DataFormat allCardsPaneFormat;
    private Utility utility;
    private User user;
    private Deck deck;
    private ArrayList<Card> mainDeck;
    private ArrayList<Card> sideDeck;
    private HashMap<String, Integer> hashMapAllCards;
    private int selectedCardRowInAllCards;
    private int selectedCardRowInMain;
    private int selectedCardColumnInMain;
    private int selectedCardColumnInSide;
    private Assets assets;
    private int size;

    public void initialize() {

        sideDeckGridPaneFormat = Utility.getSideDeckGridPaneFormat();
        mainDeckGridPaneFormat = Utility.getMainDeckGridPaneFormat();
        allCardsPaneFormat = Utility.getAllCardsPaneFormat();
        utility = new Utility();
        utility.addImages();
        user = MainMenuController.getInstance().getLoggedInUser();
        assets = MainMenuController.getInstance().getLoggedInUserAssets();
        hashMapAllCards = Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getAllUserCards();
        String deckName = DeckMenuController.getInstance().getOpenedDeckButton().getId();
        deck = Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getDeckByDeckName(deckName);
        mainDeck = deck.getMainCards();
        sideDeck = deck.getSideCards();
        draggingPane = null;
        allCardsVbox.setOnDragOver(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasContent(mainDeckGridPaneFormat) && draggingPane != null) {
                e.acceptTransferModes(TransferMode.MOVE);
            } else if (db.hasContent(sideDeckGridPaneFormat) && draggingPane != null) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
        });
        allCardsVbox.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasContent(mainDeckGridPaneFormat)) {
                DeckMenuMessage message = assets.removeCardFromMainDeck(selectedCardRowInMain * 12 + selectedCardColumnInMain, deck);
                if (message != DeckMenuMessage.CARD_DELETED)
                    new PopUpMessage(message.getAlertType(), message.getLabel());
                loadSideDeck();
                loadMainDeck();
                loadAllCards();
                System.out.println("done");

            } else if (db.hasContent(sideDeckGridPaneFormat)) {
                DeckMenuMessage message = assets.removeCardFromSideDeck(selectedCardColumnInSide, deck);
                if (message != DeckMenuMessage.CARD_DELETED)
                    new PopUpMessage(message.getAlertType(), message.getLabel());
                loadSideDeck();
                loadMainDeck();
                loadAllCards();
            } else System.out.println("here");
        });
        sideDeckGridPane.setOnDragOver(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasContent(allCardsPaneFormat) || db.hasContent(mainDeckGridPaneFormat) && draggingPane != null) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
        });

        sideDeckGridPane.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasContent(allCardsPaneFormat)) {
                String cardname = getHashMapKey(selectedCardRowInAllCards);
                Card card = Card.getCardByName(cardname);
                DeckMenuMessage message = DeckMenuController.getInstance().addCardToSideDeck(deckName, Objects.requireNonNull(card).getName().toLowerCase(Locale.ROOT));
                if (message != DeckMenuMessage.CARD_ADDED_TO_SIDE)
                    new PopUpMessage(message.getAlertType(), message.getLabel());
                loadSideDeck();
                loadMainDeck();
                loadAllCards();
                System.out.println("done");
            } else if (db.hasContent(mainDeckGridPaneFormat)) {
                Card card = mainDeck.get(selectedCardRowInMain * 12 + selectedCardColumnInMain);
                DeckMenuMessage message1 = assets.removeCardFromMainDeck(selectedCardRowInMain * 12 + selectedCardColumnInMain, deck);
                DeckMenuMessage message = DeckMenuController.getInstance().addCardToSideDeck(deckName, card.getName());

                if (message != DeckMenuMessage.CARD_ADDED_TO_SIDE)
                    new PopUpMessage(message.getAlertType(), message.getLabel());
                if (message1 != DeckMenuMessage.CARD_DELETED)
                    new PopUpMessage(message1.getAlertType(), message1.getLabel());
                loadSideDeck();
                loadMainDeck();
                loadAllCards();
                System.out.println("done");
            }
        });
        mainDeckGridPane.setOnDragOver(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasContent(allCardsPaneFormat) || db.hasContent(sideDeckGridPaneFormat) && draggingPane != null) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
        });

        mainDeckGridPane.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasContent(allCardsPaneFormat)) {
                String cardname = getHashMapKey(selectedCardRowInAllCards);
                Card card = Card.getCardByName(cardname);
                DeckMenuMessage message = DeckMenuController.getInstance().addCardToMainDeck(deckName, Objects.requireNonNull(card).getName().toLowerCase(Locale.ROOT));
                if (message != DeckMenuMessage.CARD_ADDED_TO_MAIN)
                    new PopUpMessage(message.getAlertType(), message.getLabel());
                loadSideDeck();
                loadMainDeck();
                loadAllCards();
                System.out.println("done");

            } else if (db.hasContent(sideDeckGridPaneFormat)) {
                Card card = sideDeck.get(selectedCardColumnInSide);
                DeckMenuMessage message1 = assets.removeCardFromSideDeck(selectedCardColumnInSide, deck);
                DeckMenuMessage message = DeckMenuController.getInstance().addCardToMainDeck(deckName, card.getName());

                if (message != DeckMenuMessage.CARD_ADDED_TO_MAIN)
                    new PopUpMessage(message.getAlertType(), message.getLabel());
                if (message1 != DeckMenuMessage.CARD_DELETED)
                    new PopUpMessage(message1.getAlertType(), message1.getLabel());
                loadSideDeck();
                loadMainDeck();
                loadAllCards();
                System.out.println("done");
            }
        });
        loadAllCards();
        loadMainDeck();
        loadSideDeck();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setContent(allCardsVbox);

        selectedCardImageView.setImage(getCardImageByName("Back Image"));
        selectedCardLabel.setText("No card selected");
        selectedCardLabel.setWrapText(true);
        countAllCardsSize();
        yourCardsLabel.setText("All Your Cards : " + size);
    }

    private void countAllCardsSize() {
        int sum = 0;
        for (String card : hashMapAllCards.keySet()) {
            sum += hashMapAllCards.get(card);
        }
        this.size = sum;
    }

    private void loadAllCards() {
        int i = 0;
        allCardsVbox.getChildren().clear();
        allCardsVbox.setSpacing(145);
        allCardsVbox.setAlignment(Pos.CENTER);
        for (String card : hashMapAllCards.keySet()) {
            Label label = new Label();
            label.setStyle("-fx-font-size: 10.0; -fx-background-color: #9e376c; -fx-background-radius: 10; -fx-text-fill: white;");
            label.setPrefWidth(20);
            label.setPrefHeight(20);
            label.setAlignment(Pos.CENTER);
            label.setText(String.valueOf(hashMapAllCards.get(card) - deck.getNumberOfCardInDeck(Card.getCardByName(card))));
            Pane pane = new Pane();
            ImageView imageView = new ImageView(getCardImageByName(card));
            imageView.setFitHeight(143);
            imageView.setFitWidth(133);
            pane.getChildren().add(imageView);
            pane.getChildren().add(label);
            allCardsVbox.getChildren().add(pane);
            allCardsVbox.setAlignment(Pos.CENTER);
            int finalI = i;
            pane.setCursor(Cursor.HAND);
            pane.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() != MouseButton.PRIMARY)
                    return;
                selectedCardImageView.setImage(getCardImageByName(card));
                selectedCardLabel.setText(Card.getCardByName(card).toString());
                selectedCardRowInAllCards = finalI;

            });
            pane.setOnDragDetected(e -> {
                selectedCardImageView.setImage(getCardImageByName(card));
                selectedCardLabel.setText(Card.getCardByName(card).toString());
                selectedCardRowInAllCards = finalI;
                Dragboard db = imageView.startDragAndDrop(TransferMode.MOVE);
                db.setDragView(imageView.snapshot(null, null));
                ClipboardContent cc = new ClipboardContent();
                cc.put(allCardsPaneFormat, " ");
                db.setContent(cc);
                draggingPane = pane;

            });
            i++;
        }
        allCardsVbox.getChildren().add(new Pane());
        //scrollPane.setMinViewportHeight(allCardsVbox.getHeight()+145);

    }

    public void loadMainDeck() {
        int size = mainDeck.size();
        mainDeckGridPane.getChildren().clear();
        outer:
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 12; j++) {
                if ((i * 12 + j) == size)
                    break outer;
                Pane pane = new Pane();
                Card card = mainDeck.get(i * 12 + j);
                Image image = getCardImageByName(card.getName());
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(85);
                imageView.setFitHeight(110);
                imageView.setCursor(Cursor.HAND);
                pane.getChildren().add(imageView);
                mainDeckGridPane.add(pane, j, i);
                int finalJ = j;
                int finalI = i;
                pane.setCursor(Cursor.HAND);
                pane.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton() != MouseButton.PRIMARY)
                        return;
                    selectedCardImageView.setImage(getCardImageByName(card.getName()));
                    selectedCardLabel.setText(card.toString());
                    selectedCardColumnInMain = finalI;
                    selectedCardRowInMain = finalI;
                });
                pane.setOnDragDetected(e -> {
                    selectedCardColumnInMain = finalJ;
                    selectedCardRowInMain = finalI;
                    selectedCardImageView.setImage(getCardImageByName(card.getName()));
                    selectedCardLabel.setText(card.toString());
                    Dragboard db = imageView.startDragAndDrop(TransferMode.MOVE);
                    db.setDragView(imageView.snapshot(null, null));
                    ClipboardContent cc = new ClipboardContent();
                    cc.put(mainDeckGridPaneFormat, " ");
                    db.setContent(cc);
                    draggingPane = pane;
                });
            }
        }
        mainDeckLabel.setText("Main Deck : " + size);
    }

    public void loadSideDeck() {
        ArrayList<Card> side = sideDeck;
        int size = side.size();
        int i = 0;
        sideDeckGridPane.getChildren().clear();
        for (Card card : side) {
            Pane pane = new Pane();
            Image image = getCardImageByName(card.getName());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(82);
            imageView.setFitHeight(110);
            pane.getChildren().add(imageView);
            sideDeckGridPane.add(pane, i, 0);
            int finalI = i;
            imageView.setCursor(Cursor.HAND);
            pane.setCursor(Cursor.HAND);
            pane.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() != MouseButton.PRIMARY)
                    return;
                selectedCardImageView.setImage(getCardImageByName(card.getName()));
                selectedCardLabel.setText(card.toString());
                selectedCardColumnInSide = finalI;
            });

            pane.setOnDragDetected(e -> {
                selectedCardColumnInSide = finalI;
                selectedCardImageView.setImage(getCardImageByName(card.getName()));
                selectedCardLabel.setText(card.toString());

                Dragboard db = imageView.startDragAndDrop(TransferMode.MOVE);
                db.setDragView(imageView.snapshot(null, null));
                ClipboardContent cc = new ClipboardContent();

                cc.put(sideDeckGridPaneFormat, " ");
                db.setContent(cc);
                draggingPane = pane;
            });
            i++;
        }
        sideDeckLabel.setText("Side Deck : " + size);
    }

    public Image getCardImageByName(String cardName) {
        HashMap<String, Image> stringImageHashMap = utility.getStringImageHashMap();
        for (String name : stringImageHashMap.keySet()) {
            if (name.equals(cardName)) {
                return stringImageHashMap.get(name);
            }
        }
        return null;
    }

    private String getHashMapKey(int number) {
        int i = 0;
        for (String card : hashMapAllCards.keySet()) {
            if (i == number) {
                return card;
            }
            i++;
        }
        return null;
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() != MouseButton.PRIMARY)
            return;
        onClick.play();
        Utility.openNewMenu("/project/fxml/deck_info_view.fxml");
    }
}
