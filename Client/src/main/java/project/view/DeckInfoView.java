package project.view;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import project.controller.DeckMenuController;
import project.controller.MainMenuController;
import project.model.Assets;
import project.model.Deck;
import project.view.messages.DeckMenuMessage;
import project.view.messages.PopUpMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class DeckInfoView {
    public GridPane gridScrollPane;
    public ScrollPane scrollPane;
    public SnapshotParameters parameters = new SnapshotParameters();
    public ArrayList<Button> buttons = new ArrayList<>();
    private String side;
    private int endOFI;
    private int endOFJ;
    private int endOFK;
    private DeckMenuController deckMenuController;
    private Utility utility;
    private String cardName;
    private AudioClip onClick = new AudioClip(Objects.requireNonNull(getClass().getResource("/project/soundEffects/CURSOR.wav")).toString());


    @FXML
    public void initialize() {
        parameters.setFill(Color.TRANSPARENT);
        deckMenuController = DeckMenuController.getInstance();
        cardName = null;
        Button button = deckMenuController.getOpenedDeckButton();
        utility = new Utility();
        utility.addImages();
        showEdit(button.getId());
    }

    private void showEdit(String deckName) {
        scrollPane.setFitToWidth(true);
        ArrayList<Deck> arrayList = Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getAllDecks();
        Deck deck = Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getDeckByDeckName(deckName);
        //for (Deck deck : arrayList) {
            int counter = 0;
            int i, j;
            //if (deck.getName().equals(deckName)) {
                for (i = 0, j = 0; counter < deck.getMainCards().size(); ) {
                    if (i >= 6) {
                        j++;
                        i = 0;
                    }
                    if (utility.getStringImageHashMap().containsKey(deck.getMainCards().get(counter).getName())) {
                        ImageView imageView = new ImageView(utility.getStringImageHashMap().get(deck.getMainCards().get(counter).getName()));
                        imageView.setId(deck.getMainCards().get(counter).getName());
                        imageView.setFitHeight(245);
                        imageView.setFitWidth(175);
                        createClip(imageView);

                        Button button = new Button();
                        button.setGraphic(imageView);
                        button.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
                        buttons.add(button);
                        button.setPadding(new Insets(-3, -3, -3, -3));

                        Tooltip tooltip = new Tooltip();
                        ImageView imageViewForToolTip = new ImageView(utility.getStringImageHashMap().get(deck.getMainCards().get(counter).getName()));
                        imageViewForToolTip.setFitHeight(490);
                        imageViewForToolTip.setPreserveRatio(true);
                        tooltip.setGraphic(imageViewForToolTip);
                        button.setTooltip(tooltip);

                        int finalJ1 = j;
                        int finalI1 = i;
//                        button.setOnMouseClicked(mouseEvent -> {
//                            endOFI = finalI1;
//                            endOFJ = finalJ1;
//                            addOrDeleteCard(button, "i");
//                        });
                        gridScrollPane.add(button, i, j);
                        gridScrollPane.setHgap(25);
                        gridScrollPane.setVgap(25);
                        i++;
                    }
                    counter++;
                }

                Label sideCardsLabel = new Label("Side Cards");
                sideCardsLabel.setStyle("-fx-font-size: 20; -fx-text-fill: #ffd500;");
                gridScrollPane.add(sideCardsLabel, 0, j + 1);
                int counter2 = 0;
                int k, l;
                for (k = 0, l = j + 2; counter2 < deck.getSideCards().size(); ) {
                    if (k >= 6) {
                        l++;
                        k = 0;
                    }
                    if (utility.getStringImageHashMap().containsKey(deck.getSideCards().get(counter2).getName())) {
                        ImageView imageView = new ImageView(utility.getStringImageHashMap().get(deck.getSideCards().get(counter2).getName()));
                        imageView.setId(deck.getSideCards().get(counter2).getName());
                        imageView.setFitHeight(245);
                        imageView.setFitWidth(175);
                        createClip(imageView);

                        Button button = new Button();
                        button.setGraphic(imageView);
                        button.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
                        buttons.add(button);
                        button.setPadding(new Insets(-3, -3, -3, -3));

                        Tooltip tooltip = new Tooltip();
                        ImageView imageViewForToolTip = new ImageView(utility.getStringImageHashMap().get(deck.getSideCards().get(counter2).getName()));
                        imageViewForToolTip.setFitHeight(490);
                        imageViewForToolTip.setPreserveRatio(true);
                        tooltip.setGraphic(imageViewForToolTip);
                        button.setTooltip(tooltip);

                        int finalK2 = k;
//                        button.setOnMouseClicked(mouseEvent -> {
//                            endOFK = finalK2;
//                            addOrDeleteCard(button, "k");
//                        });
                        gridScrollPane.add(button, k, l);
                        k++;
                    }
                    counter2++;
                }
           // }
        //}
    }

    private void createClip(ImageView imageView) {
        Rectangle clip = new Rectangle();
        clip.setHeight(245);
        clip.setWidth(175);
        clip.setArcHeight(30);
        clip.setArcWidth(30);
        imageView.setClip(clip);
        WritableImage wImage = imageView.snapshot(parameters, null);
        imageView.setImage(wImage);
    }

    public void addCards(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() != MouseButton.PRIMARY) return;
        onClick.play();
        Utility.openNewMenu("/project/fxml/edit_deck_menu.fxml");
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() != MouseButton.PRIMARY) return;
        onClick.play();
        Utility.openNewMenu("/project/fxml/deck_menu.fxml");
    }

    public void deleteCard() {
        if (cardName == null) {
            new PopUpMessage(DeckMenuMessage.YOU_DID_NOT_SELECT_ANY_CARD.getAlertType(),
                    DeckMenuMessage.YOU_DID_NOT_SELECT_ANY_CARD.getLabel());
        } else {
            if (side.equals("i")) {
                DeckMenuMessage deckMenuMessage = Objects.requireNonNull(
                        Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).removeCardFromMainDeck((endOFJ) * 6 + endOFI,
                        Objects.requireNonNull(
                                Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getDeckByDeckName(deckMenuController.getOpenedDeckButton().getId()));
                new PopUpMessage(deckMenuMessage.getAlertType(), deckMenuMessage.getLabel());
                gridScrollPane.getChildren().clear();
                showEdit(deckMenuController.getOpenedDeckButton().getId());
            } else if (side.equals("k")) {
                DeckMenuMessage deckMenuMessage = Objects.requireNonNull(
                        Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).removeCardFromSideDeck(endOFK,
                        Objects.requireNonNull(
                                Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getDeckByDeckName(deckMenuController.getOpenedDeckButton().getId()));
                new PopUpMessage(deckMenuMessage.getAlertType(), deckMenuMessage.getLabel());
                gridScrollPane.getChildren().clear();
                showEdit(deckMenuController.getOpenedDeckButton().getId());
            }
        }
    }

    public void addOrDeleteCard(Button input, String sideString) {
        for (Button button : buttons) {
            if (button.equals(input)) button.setId("container");
            else button.setId(null);
        }
        cardName = input.getId();
        side = sideString;
    }
}
