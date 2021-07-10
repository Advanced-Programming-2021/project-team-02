package project.view;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import project.controller.DeckMenuController;
import project.controller.MainMenuController;
import project.model.Assets;
import project.model.card.Card;
import project.view.messages.DeckMenuMessage;
import project.view.messages.PopUpMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class DeckMenuAddCard {
    public static final ArrayList<Button> buttons = new ArrayList<>();
    public ScrollPane scrollPane;
    public Button addCardToMainDeckButton;
    public Button addCardToSideDeckButton;
    DeckMenuController deckMenuController;
    Utility utility;
    private String cardName;
    public GridPane gridPaneAddCard;

    @FXML
    public void initialize() {
        cardName = null;
        deckMenuController = DeckMenuController.getInstance();
        utility = new Utility();
        utility.addImages();
        showCardsToAdd(deckMenuController.getOpenedDeckButton().getId());
    }

    private void showCardsToAdd(String deckName) {
        scrollPane.setFitToWidth(true);
        HashMap<Card, Integer> hashMapAllCards = Objects.requireNonNull(
                Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getAllUserCards();
        int i = 0, j = 3;
        for (Card card : hashMapAllCards.keySet()) {
            if (utility.getStringImageHashMap().containsKey(card.getName())) {
                System.out.println(hashMapAllCards.get(card));
                if (i >= 6) {
                    j++;
                    i = 0;
                }
                int x = 0;
                while (x < Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getNumberOfCards(card)) {
                    if (i >= 6) {
                        j++;
                        i = 0;
                    }
                    x++;

                    ImageView imageView = new ImageView(utility.getStringImageHashMap().get(card.getName()));
                    imageView.setId(card.getName());
                    imageView.setFitHeight(245);
                    imageView.setFitWidth(175);
                    createClip(imageView);

                    Label label = new Label(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getNumberOfCards(card)));
                    label.setId(card.getName());
                    label.setStyle("-fx-font-size: 15.0; -fx-background-color: #9e376c; -fx-background-radius: 10; -fx-text-fill: white;");
                    label.setPrefWidth(30);
                    label.setPrefHeight(30);
                    label.setAlignment(Pos.CENTER);

                    Button button = new Button();
                    button.setGraphic(imageView);
                    button.setId(card.getName());
                    button.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
                    buttons.add(button);
                    button.setPadding(new Insets(-3, -3, -3, -3));
                    button.setOnMouseClicked(mouseEvent -> {
                        for (Button b : buttons) {
                            if (b.equals(button)) b.setId(card.getName());
                            else b.setId(null);
                        }
                        cardName = button.getId();
                    });

                    Tooltip tooltip = new Tooltip();
                    ImageView imageViewForToolTip = new ImageView(utility.getStringImageHashMap().get(card.getName()));
                    imageViewForToolTip.setFitHeight(490);
                    imageViewForToolTip.setPreserveRatio(true);
                    tooltip.setGraphic(imageViewForToolTip);
                    button.setTooltip(tooltip);

                    VBox layout = new VBox();
                    layout.setPadding(new Insets(7, 7, 7, 7));
                    layout.getChildren().addAll(button, label);
                    VBox.setMargin(label, new Insets(-30, 0, 0, 0));
                    layout.setAlignment(Pos.TOP_RIGHT);

                    gridPaneAddCard.add(layout, i, j);
                    i++;
                }
            }
        }
        addCardToMainDeckButton.setOnMouseClicked(mouseEvent -> {
            //if (mouseEvent.getButton() != MouseButton.PRIMARY) {
                addCardsMain(deckName);
            //}
        });

        addCardToSideDeckButton.setOnMouseClicked(mouseEvent -> {
//            if (mouseEvent.getButton() != MouseButton.PRIMARY) {
                addCardsSide(deckName);
            //}
        });
        gridPaneAddCard.setVgap(25);
        gridPaneAddCard.setHgap(25);
    }

    private void createClip(ImageView imageView) {
        Rectangle clip = new Rectangle();
        clip.setHeight(245);
        clip.setWidth(175);
        clip.setArcHeight(30);
        clip.setArcWidth(30);
        imageView.setClip(clip);
        WritableImage wImage = imageView.snapshot(DeckInfoView.parameters, null);
        imageView.setImage(wImage);
    }

    public void addCardsSide(String DeckName) {
        if (cardName == null) {
            new PopUpMessage(DeckMenuMessage.YOU_DID_NOT_SELECT_ANY_CARD.getAlertType(),
                    DeckMenuMessage.YOU_DID_NOT_SELECT_ANY_CARD.getLabel());
        } else {
            DeckMenuMessage deckMenuMessage = deckMenuController.addCardToSideDeck(DeckName, cardName);
            new PopUpMessage(deckMenuMessage.getAlertType(), deckMenuMessage.getLabel());
        }
    }

    public void addCardsMain(String DeckName) {
        if (cardName == null) {
            new PopUpMessage(DeckMenuMessage.YOU_DID_NOT_SELECT_ANY_CARD.getAlertType(),
                    DeckMenuMessage.YOU_DID_NOT_SELECT_ANY_CARD.getLabel());
        } else {
            DeckMenuMessage deckMenuMessage = deckMenuController.addCardToMainDeck(DeckName, cardName);
            new PopUpMessage(deckMenuMessage.getAlertType(), deckMenuMessage.getLabel());
        }
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() != MouseButton.PRIMARY) return;
        Utility.openNewMenu("/project/fxml/deck_info_view.fxml");
    }
}

