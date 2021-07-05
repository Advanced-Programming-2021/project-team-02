package project.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import project.controller.DeckMenuController;
import project.controller.MainMenuController;
import project.model.Assets;
import project.model.User;
import project.model.card.Card;
import project.view.messages.DeckMenuMessage;
import project.view.messages.PopUpMessage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class DeckMenuAddCard {
    public ScrollPane scrollPane;
    DeckMenuController deckMenuController;
    Utility utility;
    private String cardName = null;
    public GridPane gridPaneAddCard;

    @FXML
    public void initialize() {
        deckMenuController = DeckMenuController.getInstance();
        utility = new Utility();
        utility.addImages();
        User mahdi = new User("mahdi", "12345", "test");
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Haniwa"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Horn Imp"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Wattaildragon"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Wattkid"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Raigeki"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Negate Attack"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Trap Hole"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Sword of dark destruction"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Umiiruka"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Haniwa"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Horn Imp"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Wattaildragon"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Wattkid"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Raigeki"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Negate Attack"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Trap Hole"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Sword of dark destruction"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Umiiruka"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Haniwa"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Horn Imp"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Wattaildragon"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Wattkid"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Raigeki"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Negate Attack"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Trap Hole"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Sword of dark destruction"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Umiiruka"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Haniwa"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Horn Imp"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Wattaildragon"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Wattkid"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Raigeki"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Negate Attack"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Trap Hole"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Sword of dark destruction"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Umiiruka"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Horn Imp"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Wattaildragon"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Wattkid"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Raigeki"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Negate Attack"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Trap Hole"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Sword of dark destruction"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Umiiruka"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Haniwa"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Horn Imp"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Wattaildragon"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Wattkid"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Raigeki"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Negate Attack"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Trap Hole"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Sword of dark destruction"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Umiiruka"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Horn Imp"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Wattaildragon"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Wattkid"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Raigeki"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Negate Attack"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Trap Hole"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Sword of dark destruction"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Umiiruka"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Haniwa"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Horn Imp"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Wattaildragon"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Wattkid"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Raigeki"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Negate Attack"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Trap Hole"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Sword of dark destruction"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Umiiruka"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Umiiruka"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Haniwa"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Horn Imp"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Wattaildragon"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Wattkid"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Raigeki"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Negate Attack"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Trap Hole"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Sword of dark destruction"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Umiiruka"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Umiiruka"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Haniwa"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Horn Imp"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Wattaildragon"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Wattkid"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Raigeki"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Negate Attack"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Trap Hole"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Sword of dark destruction"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Umiiruka"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Umiiruka"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Haniwa"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Horn Imp"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Wattaildragon"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Wattkid"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Raigeki"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Negate Attack"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Trap Hole"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Sword of dark destruction"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Umiiruka"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Umiiruka"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Umiiruka"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Haniwa"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Horn Imp"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Wattaildragon"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Wattkid"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Raigeki"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Negate Attack"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Trap Hole"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Sword of dark destruction"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Umiiruka"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Umiiruka"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Umiiruka"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Haniwa"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Horn Imp"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Wattaildragon"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Wattkid"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Raigeki"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Negate Attack"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Trap Hole"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Sword of dark destruction"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Umiiruka"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Horn Imp"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Wattaildragon"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Wattkid"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Raigeki"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Negate Attack"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Trap Hole"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Sword of dark destruction"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Umiiruka"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Umiiruka"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Umiiruka"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Haniwa"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Horn Imp"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Wattaildragon"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Wattkid"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Raigeki"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Negate Attack"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Trap Hole"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Sword of dark destruction"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Umiiruka"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Horn Imp"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Wattaildragon"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Wattkid"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Raigeki"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Negate Attack"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Trap Hole"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Sword of dark destruction"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Umiiruka"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Umiiruka"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Umiiruka"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Haniwa"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Horn Imp"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Wattaildragon"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Wattkid"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Raigeki"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Negate Attack"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Trap Hole"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Sword of dark destruction"));
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Umiiruka"));
        showCardsToAdd(deckMenuController.getOpenedDeckButton().getId());
    }

    private void showCardsToAdd(String deckName) {
        //scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPrefWidth(1000000000);
        HashMap<Card, Integer> hashMapAllCards = Objects.requireNonNull(
                Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getAllUserCards();
        int i = 0, j = 3;
        for (Card card : hashMapAllCards.keySet()) {
            if (utility.getStringImageHashMap().containsKey(card.getName())) {
                System.out.println(hashMapAllCards.get(card));
                if (i >= 10) {
                    j++;
                    i = 0;
                }
                int x = 0;
                while (x < Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getNumberOfCards(card)) {
                    if (i >= 10) {
                        j++;
                        i = 0;
                    }
                    x++;
                    ImageView imageView = new ImageView(utility.getStringImageHashMap().get(card.getName()));
                    imageView.setId(card.getName());
                    imageView.setFitHeight(100);
                    imageView.setFitWidth(100);
                    imageView.setOnMouseClicked(mouseEvent -> {
                        cardName = imageView.getId();
                    });

                    Label label = new Label("Number : " + Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getNumberOfCards(card));
                    label.setId(card.getName());
                    label.setFont(Font.font("Cambria", 10));
                    label.setTextFill(Color.web("#0076a3"));
                    label.setPrefWidth(100);
                    label.setPrefHeight(30);

                    VBox layout = new VBox(10);
                    layout.setPadding(new Insets(10, 10, 10, 40));
                    layout.setPrefHeight(100);
                    layout.setPrefWidth(100);
                    layout.setEffect(new DropShadow());

                    layout.getChildren().addAll(imageView, label);

                    gridPaneAddCard.add(layout, i, j);
                    i++;
                }
            }
        }

        //V1 : fasele ofoghi : har chi bishtar fasele kamtar
        //V2 fasele amoodi : harchi kamtar fasele bishtar
        //v : fasele as bala : bishtar bashtar

        Button button = new Button("Back");
        button.setOnMouseClicked(actionEvent -> {
            try {
                back(actionEvent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        gridPaneAddCard.add(button, 0, j + 5);

        Button addSideButton = new Button("add Card to side Deck");
        addSideButton.setOnMouseClicked(mouseEvent -> {
            addCardsSide(deckName);
        });
        gridPaneAddCard.add(addSideButton, 3, j + 2);

        Button addMainButton = new Button("add Card to main deck");
        addMainButton.setOnMouseClicked(mouseEvent -> {
            addCardsMain(deckName);
        });
        gridPaneAddCard.add(addMainButton, 6, j + 2);

        gridPaneAddCard.setPadding(new Insets(50, 0, 400, 0));
        gridPaneAddCard.setVgap(30);
    }

    private void addCardsSide(String DeckName) {
        if (cardName == null) {
            new PopUpMessage(DeckMenuMessage.YOU_DID_NOT_SELECT_ANY_CARD.getAlertType(),
                    DeckMenuMessage.YOU_DID_NOT_SELECT_ANY_CARD.getLabel());
        } else {
            DeckMenuMessage deckMenuMessage = deckMenuController.addCardToSideDeck(DeckName ,cardName);
            new PopUpMessage(deckMenuMessage.getAlertType(), deckMenuMessage.getLabel());
        }
    }

    private void addCardsMain(String DeckName) {
        if (cardName == null) {
            new PopUpMessage(DeckMenuMessage.YOU_DID_NOT_SELECT_ANY_CARD.getAlertType(),
                    DeckMenuMessage.YOU_DID_NOT_SELECT_ANY_CARD.getLabel());
        } else {
            DeckMenuMessage deckMenuMessage = deckMenuController.addCardToMainDeck(DeckName, cardName);
            new PopUpMessage(deckMenuMessage.getAlertType(), deckMenuMessage.getLabel());
        }
    }

    private void back(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() != MouseButton.PRIMARY) return;
        Utility.openNewMenu("/project/fxml/edit_deck_menu.fxml");
    }
}

