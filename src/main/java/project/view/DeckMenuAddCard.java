package project.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import project.controller.DeckMenuController;
import project.model.Assets;
import project.model.User;
import project.model.card.Card;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class DeckMenuAddCard {
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
        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Umiiruka"));Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).addCard(Card.getCardByName("Haniwa"));
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
        HashMap<Card, Integer> hashMapAllCards = Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getAllUserCards();
        int i = 0, j = 0;
        for (Card card : hashMapAllCards.keySet()) {
            if (utility.getStringImageHashMap().containsKey(card.getName())) {
                System.out.println(hashMapAllCards.get(card));
                if (i >= 15) {
                    j++;
                    i = 0;
                }
                ImageView imageView = new ImageView(utility.getStringImageHashMap().get(card.getName()));
                imageView.setId(card.getName());
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);
                imageView.setOnMouseClicked(mouseEvent -> {
                    cardName = imageView.getId();
                });
                gridPaneAddCard.add(imageView, i, j);
                i++;
            }
        }

        //V1 : fasele ofoghi : har chi nishtar fasele kamtar
        //V2 fasele amoodi : harchi kamtar fasele bishtar

        Button button = new Button("Back");
        button.setOnMouseClicked(actionEvent -> {
            try {
                back(actionEvent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        gridPaneAddCard.add(button, 0, 6);

        Button addButton = new Button("add Card");
        addButton.setOnMouseClicked(mouseEvent -> {

        });
        gridPaneAddCard.add(addButton, 0, 8);

        gridPaneAddCard.setPadding(new Insets(0, 0, 100, 0));
    }

    private void addCards(MouseEvent mouseEvent) {
    }

    private void back(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() != MouseButton.PRIMARY) return;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/project/fxml/edit_deck_menu.fxml")));
        Utility.openNewMenu(root, (Node) mouseEvent.getSource());
    }
}

