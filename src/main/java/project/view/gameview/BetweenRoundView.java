package project.view.gameview;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import project.controller.playgame.BetweenRoundController;
import project.controller.playgame.DuelGameController;
import project.controller.playgame.RoundGameController;
import project.model.card.Card;
import project.model.game.DuelPlayer;
//import project.view.Menu;
//import project.view.MenusManager;
import project.view.LoginMenuView;
import project.view.Utility;
import project.view.input.Regex;
import project.view.messages.Error;
import project.view.messages.PopUpMessage;
import project.view.messages.SuccessMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;

public class BetweenRoundView {
    private static BetweenRoundView instance = null;
    public GridPane sideDeckGridPane;
    public GridPane mainDeckGridPane;
    public Label sideDeckLabel;
    public Label mainDeckLabel;
    public Label selectedCardDescriptionLabel;
    public ImageView selectedCardImage;
    public AnchorPane mainPane;
    public Pane draggingPane;
    public Label playerLabel;
    private BetweenRoundController controller;
    private DuelPlayer player1;
    private DuelPlayer player2;
    private DuelPlayer currentPlayer;
    private int turn = 1;
    private boolean isAI;
    private DataFormat mainDeckPaneFormat = new DataFormat("MainPane");
    private DataFormat sideDeckPaneFormat = new DataFormat("SidePane");
    private int selectedCardRowInMain, selectedCardColumnInMain;
    private int selectedCardRowInSide, selectedCardColumnInSide;
    private Utility utility;

    public void initialize() {
        utility = new Utility();
        utility.addImages();
        controller = BetweenRoundController.getInstance();
        controller.setView(this);
        player1 = controller.getPlayer1();
        player2 = controller.getPlayer2();
        currentPlayer = player1;
        isAI = BetweenRoundController.getInstance().isWithAi();
        ArrayList<Card> mainCards = currentPlayer.getPlayDeck().getMainCards();
        ArrayList<Card> sideCards = currentPlayer.getPlayDeck().getSideCards();
        selectedCardImage.setImage(getCardImageByName("Back Image"));
        selectedCardDescriptionLabel.setText("No card selected");
        selectedCardDescriptionLabel.setWrapText(true);
        //---------------------------------------------------------------------------------------
        //---------------------------------------------------------------------------------------
        draggingPane = null;
        sideDeckGridPane.setOnDragOver(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasContent(mainDeckPaneFormat) && draggingPane != null) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
        });

        sideDeckGridPane.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();

            if (db.hasContent(mainDeckPaneFormat)) {
                BetweenRoundController.getInstance().addCardToSideFromMain(selectedCardRowInMain * 12 + selectedCardColumnInMain, currentPlayer);
                loadSideDeck();
                loadMainDeck();
                System.out.println("done");
            } else return;
        });
        mainDeckGridPane.setOnDragOver(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasContent(sideDeckPaneFormat) && draggingPane != null) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
        });

        mainDeckGridPane.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasContent(sideDeckPaneFormat)) {
                BetweenRoundController.getInstance().addCardToMainFromSide(selectedCardColumnInSide, currentPlayer);
                loadSideDeck();
                loadMainDeck();
                System.out.println("done");

            }
        });
        playerLabel.setText("Player : " + currentPlayer.getNickname());
    }

    public void startAndLoadBetweenRound() {
        loadMainDeck();
        loadSideDeck();
    }

    private void blur() {
        ColorAdjust adj = new ColorAdjust(0, -0.9, -0.5, 0);
        GaussianBlur blur = new GaussianBlur(55);
        adj.setInput(blur);
        mainPane.setEffect(adj);
    }

    public void loadMainDeck() {
        ArrayList<Card> main = currentPlayer.getPlayDeck().getMainCards();
        int size = main.size();
        mainDeckGridPane.getChildren().clear();
        outer:
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 12; j++) {
                if ((i * 12 + j) == size)
                    break outer;
                Pane pane = new Pane();
                Card card = main.get(i * 12 + j);
                Image image = getCardImageByName(card.getName());
                ImageView imageView = new ImageView(image);
                ;
                imageView.setFitWidth(85);
                imageView.setFitHeight(110);
                imageView.setCursor(Cursor.HAND);
                pane.getChildren().add(imageView);
                mainDeckGridPane.add(pane, j, i);
                int finalJ = j;
                int finalI = i;
                pane.setOnMouseClicked(mouseEvent -> {
                    selectedCardImage.setImage(image);
                    selectedCardDescriptionLabel.setText(card.toString());
                    selectedCardColumnInMain = finalI;
                    selectedCardRowInMain = finalI;
                });
                pane.setOnDragDetected(e -> {
                    selectedCardImage.setImage(image);
                    selectedCardDescriptionLabel.setText(card.toString());
                    selectedCardColumnInMain = finalJ;
                    selectedCardRowInMain = finalI;
                    Dragboard db = imageView.startDragAndDrop(TransferMode.MOVE);
                    db.setDragView(imageView.snapshot(null, null));
                    ClipboardContent cc = new ClipboardContent();
                    cc.put(mainDeckPaneFormat, " ");
                    db.setContent(cc);
                    draggingPane = pane;
                });
            }
        }
        mainDeckLabel.setText("Main Deck : " + size);
    }

    public void loadSideDeck() {
        ArrayList<Card> side = currentPlayer.getPlayDeck().getSideCards();
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

            imageView.setCursor(Cursor.HAND);
            pane.setOnMouseClicked(mouseEvent -> {
                selectedCardImage.setImage(image);
                selectedCardDescriptionLabel.setText(card.toString());
            });
            int finalI = i;
            pane.setOnDragDetected(e -> {
                selectedCardImage.setImage(image);
                selectedCardDescriptionLabel.setText(card.toString());
                selectedCardColumnInSide = finalI;
                selectedCardRowInSide = 0;
                Dragboard db = imageView.startDragAndDrop(TransferMode.MOVE);
                db.setDragView(imageView.snapshot(null, null));
                ClipboardContent cc = new ClipboardContent();
                cc.put(sideDeckPaneFormat, " ");
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


    public void continueToNext(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() != MouseButton.PRIMARY) {
            return;
        }
        if (turn == 1) {
            if (controller.canChangeTurn()) {
                turn = 2;
                currentPlayer = player2;
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Change turn!");
                alert.initOwner(LoginMenuView.getStage());
                alert.initModality(Modality.WINDOW_MODAL);
                alert.initStyle(StageStyle.TRANSPARENT);
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.setHeaderText(null);
                dialogPane.setGraphic(null);
                dialogPane.setStyle("-fx-border-radius: 10; -fx-border-color: #bb792d; -fx-border-width: 7; -fx-background-radius: 14; -fx-font-family: \"Matrix II Regular\"; -fx-background-color: #103188;");
                dialogPane.lookup(".content.label").setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-line-spacing: 5px;");
                dialogPane.getScene().setFill(Color.TRANSPARENT);
                ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("OK!");
                ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
                buttonBar.getButtons().forEach(b -> b.setStyle("-fx-background-radius: 10; -fx-background-color: #bb792d; -fx-font-size: 16; -fx-text-fill: white;"));
                buttonBar.getButtons().forEach(b -> b.setCursor(Cursor.HAND));
                //((Window)LoginMenuView.getStage()).setEffect;
                blur();
                alert.showAndWait();
                selectedCardImage.setImage(getCardImageByName("Back Image"));
                selectedCardDescriptionLabel.setText("No Card Selected");

                loadMainDeck();
                loadMainDeck();
                playerLabel.setText("Player : " + currentPlayer.getNickname());
                mainPane.setEffect(null);
            } else new PopUpMessage(Alert.AlertType.ERROR, "Decks Size mustn't change");
        } else {
            turn = 1;
            DuelGameController.getInstance().startNextRound();
            try {
                GameView gameView = (GameView) Utility.openMenuAndReturnController("/project/fxml/round_view.fxml");
                RoundGameController.getInstance().setView(gameView);
                gameView.startGameAndLoadHand();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
