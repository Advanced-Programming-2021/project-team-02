package project.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import project.controller.DeckMenuController;
import project.controller.MainMenuController;
import project.controller.playgame.BetweenRoundController;
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

public class AddCardToDeckView {
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
    private DataFormat sideDeckGridPaneFormat;
    private DataFormat mainDeckGridPaneFormat;
    private DataFormat allCardsPaneFormat;
    private Utility utility;
    private User user;
    private Deck deck;
    private ArrayList<Card> mainDeck;
    private ArrayList<Card> sideDeck;
    private HashMap<Card, Integer> hashMapAllCards;
    private int selectedCardRowInAllCards;
    private int selectedCardColumnInAllCards;
    private int selectedCardRowInMain;
    private int selectedCardColumnInMain;
    private int selectedCardRowInSide;
    private int selectedCardColumnInSide;
    private Assets assets;

    public void initialize() {

        sideDeckGridPaneFormat = Utility.getSideDeckGridPaneFormat();
        mainDeckGridPaneFormat = Utility.getMainDeckGridPaneFormat();
        allCardsPaneFormat =Utility.getAllCardsPaneFormat();
        utility = new Utility();
        utility.addImages();
        user = MainMenuController.getInstance().getLoggedInUser();
        assets = Assets.getAssetsByUsername(user.getUsername());
        hashMapAllCards = Objects.requireNonNull(
                Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getAllUserCards();
        String deckName = DeckMenuController.getInstance().getOpenedDeckButton().getId();
        deck = Assets.getAssetsByUsername(user.getUsername()).getDeckByDeckName(deckName);
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
                Card card = getHashMapKey(selectedCardRowInAllCards);
                DeckMenuMessage message = DeckMenuController.getInstance().addCardToSideDeck(deckName, card.getName().toLowerCase(Locale.ROOT));
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
                Card card = getHashMapKey(selectedCardRowInAllCards);
                DeckMenuMessage message = DeckMenuController.getInstance().addCardToMainDeck(deckName, card.getName().toLowerCase(Locale.ROOT));
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
    }


    private void blur() {
        ColorAdjust adj = new ColorAdjust(0, -0.9, -0.5, 0);
        GaussianBlur blur = new GaussianBlur(55);
        adj.setInput(blur);
        mainPane.setEffect(adj);
    }

    private void loadAllCards() {
        int i = 0;
        allCardsVbox.getChildren().clear();
        allCardsVbox.setSpacing(145);
        allCardsVbox.setAlignment(Pos.CENTER);
        for (Card card : hashMapAllCards.keySet()) {
            Label label = new Label();
            label.setStyle("-fx-font-size: 10.0; -fx-background-color: #9e376c; -fx-background-radius: 10; -fx-text-fill: white;");
            label.setPrefWidth(20);
            label.setPrefHeight(20);
            label.setAlignment(Pos.CENTER);
            label.setText(String.valueOf(hashMapAllCards.get(card) - deck.getNumberOfCardInDeck(card)));
            Pane pane = new Pane();
            ImageView imageView = new ImageView(getCardImageByName(card.getName()));
            imageView.setFitHeight(140);
            imageView.setFitWidth(116);
            pane.getChildren().add(imageView);
            pane.getChildren().add(label);
            allCardsVbox.getChildren().add(pane);
            int finalI = i;
            pane.setCursor(Cursor.HAND);
            pane.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton()!= MouseButton.PRIMARY)
                    return;
                selectedCardImageView.setImage(getCardImageByName(card.getName()));
                selectedCardLabel.setText(card.toString());
                selectedCardColumnInAllCards = 0;
                selectedCardRowInAllCards = finalI;

            });
            pane.setOnDragDetected(e -> {
                selectedCardImageView.setImage(getCardImageByName(card.getName()));
                selectedCardLabel.setText(card.toString());
                selectedCardRowInAllCards = finalI;
                selectedCardColumnInAllCards = 0;
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
                    if (mouseEvent.getButton()!=MouseButton.PRIMARY)
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
                if (mouseEvent.getButton()!=MouseButton.PRIMARY)
                    return;
                selectedCardImageView.setImage(getCardImageByName(card.getName()));
                selectedCardLabel.setText(card.toString());
                selectedCardColumnInSide = finalI;
                selectedCardRowInSide = 0;
            });

            pane.setOnDragDetected(e -> {
                selectedCardColumnInSide = finalI;
                selectedCardImageView.setImage(getCardImageByName(card.getName()));
                selectedCardLabel.setText(card.toString());
                selectedCardRowInSide = 0;

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

    private synchronized Node getNodeInGridPane(GridPane gridPane, int row, int column) {
        synchronized (gridPane) {
            for (Node child : gridPane.getChildren()) {
                if (child != null)
                    if (GridPane.getRowIndex(child) == row && GridPane.getColumnIndex(child) == column)
                        return child;
            }
            return null;
        }
    }

    private Card getHashMapKey(int number) {
        int i = 0;
        for (Card card : hashMapAllCards.keySet()) {
            if (i == number) {
                System.out.println(card.getName());
                return card;
            }
            i++;
        }
        return null;
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton()!=MouseButton.PRIMARY)
            return;
        Utility.openNewMenu("/project/fxml/edit_deck_menu.fxml");
    }
}
