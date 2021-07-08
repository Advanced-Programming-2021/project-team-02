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
import project.model.card.Card;
import project.model.game.DuelPlayer;
//import project.view.Menu;
//import project.view.MenusManager;
import project.view.LoginMenuView;
import project.view.Utility;
import project.view.input.Regex;
import project.view.messages.Error;
import project.view.messages.SuccessMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
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
    private BetweenRoundController controller;
    private DuelPlayer player1;
    private DuelPlayer player2;
    private DuelPlayer currentPlayer;
    private int turn = 1;
    private boolean isAI;
    private DataFormat paneFormat = new DataFormat("pane");
    private int selectedCardRowInMain, selectedCardColumnInMain;
    private int selectedCardRowInSide, selectedCardColumnInSide;

    public void initialize() {
        controller = BetweenRoundController.getInstance();
        controller.setView(this);
        player1 = controller.getPlayer1();
        player2 = controller.getPlayer2();
        currentPlayer = player1;
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
            if (db.hasContent(paneFormat) && draggingPane != null) {
                e.acceptTransferModes(TransferMode.MOVE);

            }
        });

        sideDeckGridPane.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();

            if (db.hasContent(paneFormat)) {
                if (sideCards.size() < 15) {
                    ((Pane) getNodeInGridPane(mainDeckGridPane, selectedCardRowInMain, selectedCardColumnInMain).getParent()).getChildren().clear();
                    sideDeckGridPane.getChildren().add(draggingPane);
                    e.setDropCompleted(true);
                    draggingPane = null;
                    System.out.println("done");
                } else return;
            }
        });
        mainDeckGridPane.setOnDragOver(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasContent(paneFormat) && draggingPane != null) {
                e.acceptTransferModes(TransferMode.MOVE);

            }
        });

        mainDeckGridPane.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasContent(paneFormat)) {
                if (mainCards.size() < 60) {
                    BetweenRoundController.getInstance().addCardToMainFromSide(selectedCardColumnInSide, currentPlayer);
                    int rowInGrid = mainCards.size() / 12 - 1;
                    ;
                    int columnInGrid = mainCards.size() - (rowInGrid+1) * 12;
                    System.out.println("column : " + columnInGrid + " row :" + rowInGrid);
                    mainDeckGridPane.add(getNodeInGridPane(sideDeckGridPane, 0, selectedCardColumnInSide), columnInGrid, rowInGrid + 1);
                    removeNodeInGridPane(sideDeckGridPane, 0, selectedCardColumnInSide);
                    e.setDropCompleted(true);
                    draggingPane = null;
                    System.out.println("done");
                } else return;
            }
        });

    }

    public void startAndLoadBetweenRound() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Welcome to between rounds menu");
        alert.initOwner(LoginMenuView.getStage());
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initStyle(StageStyle.TRANSPARENT);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setHeaderText(null);
        dialogPane.setGraphic(null);
        dialogPane.setStyle("-fx-border-radius: 10; -fx-border-color: #bb792d; -fx-border-width: 7; -fx-background-radius: 14; -fx-font-family: \"Matrix II Regular\"; -fx-background-color: #103188;");
        dialogPane.lookup(".content.label").setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-line-spacing: 5px;");
        dialogPane.getScene().setFill(Color.TRANSPARENT);
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Ok");
        ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
        buttonBar.getButtons().forEach(b -> b.setStyle("-fx-background-radius: 10; -fx-background-color: #bb792d; -fx-font-size: 16; -fx-text-fill: white;"));
        buttonBar.getButtons().forEach(b -> b.setCursor(Cursor.HAND));
        blur();
        alert.showAndWait();
        loadMainDeck();
        loadSideDeck();
        mainPane.setEffect(null);
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
                    selectedCardRowInMain = finalJ;
                });
                pane.setOnDragDetected(e -> {
                    selectedCardImage.setImage(image);
                    selectedCardDescriptionLabel.setText(card.toString());
                    selectedCardColumnInMain = finalI;
                    selectedCardRowInMain = finalJ;
                    Dragboard db = imageView.startDragAndDrop(TransferMode.MOVE);
                    db.setDragView(imageView.snapshot(null, null));
                    ClipboardContent cc = new ClipboardContent();
                    cc.put(paneFormat, " ");
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
            i++;
            imageView.setCursor(Cursor.HAND);
            pane.setOnMouseClicked(mouseEvent -> {
                selectedCardImage.setImage(image);
                selectedCardDescriptionLabel.setText(card.toString());
            });
            int finalI = i;
            pane.setOnDragDetected(e -> {
                selectedCardImage.setImage(image);
                selectedCardDescriptionLabel.setText(card.toString());
                selectedCardColumnInMain = finalI;
                selectedCardRowInSide = 0;
                Dragboard db = imageView.startDragAndDrop(TransferMode.MOVE);
                db.setDragView(imageView.snapshot(null, null));
                ClipboardContent cc = new ClipboardContent();
                cc.put(paneFormat, " ");
                db.setContent(cc);
                draggingPane = pane;
            });
        }
        sideDeckLabel.setText("Side Deck : " + size);
    }

    public Image getCardImageByName(String cardName) {
        Utility util = new Utility();
        util.addImages();
        HashMap<String, Image> stringImageHashMap = util.getStringImageHashMap();
        for (String name : stringImageHashMap.keySet()) {
            if (name.equals(cardName)) {
                return stringImageHashMap.get(name);
            }
        }
        return null;
    }

    private synchronized Node getNodeInGridPane(GridPane gridPane, int row, int column) {
        System.out.println(row + "   " + column);
        synchronized (gridPane) {
            for (Node child : gridPane.getChildren()) {
                if (child != null)
                    if (GridPane.getRowIndex(child) == row && GridPane.getColumnIndex(child) == column)
                        return child;
            }
            return null;
        }
    }

    private synchronized Node removeNodeInGridPane(GridPane gridPane, int row, int column) {
        System.out.println(row + "   " + column);
        synchronized (gridPane) {
            gridPane.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == column && GridPane.getRowIndex(node) == row);
            return null;
        }
    }

    public void run(String command) {
        commandRecognition(command);
    }

    private void commandRecognition(String command) {
        BetweenRoundController.getInstance().setView(this);
        Matcher matcher;
        if ((matcher = Regex.getMatcher(Regex.CHANGE_CARD_BETWEEN_ROUNDS, command)).matches()) {
            DuelPlayer player = (turn == 1 ? player1 : player2);
            controller.changeCard(Integer.parseInt(matcher.group("cardAddressInMainDeck")), Integer.parseInt(matcher.group("cardAddressInSideDeck")), player);
        } else if (command.equals("start")) {
            if (isAI) {
                DuelGameController.getInstance().startNextRound();
                //TODO MenusManager.getInstance().changeMenu(Menu.ONGOING_GAME_WITH_AI);
                return;
            }
            if (turn == 1) {
                turn = 2;
                System.out.println("now next player can change cards;");
            } else {
                turn = 1;
                DuelGameController.getInstance().startNextRound();
                //TODO MenusManager.getInstance().changeMenu(Menu.ONGOING_GAME);
            }
        } else if (command.equals("show deck")) {
            DuelPlayer player = (turn == 1 ? player1 : player2);
            showDeck(player);
        } else if (command.equals("help")) {
            DuelPlayer player = (turn == 1 ? player1 : player2);
            System.out.println(player.getNickname() + " turn to change card");
            help();
        } else System.out.println(Error.INVALID_COMMAND);
    }

    private void help() {
        System.out.println("start" +
                "\nhelp\nshow deck\nchange card <cardAddressInMainDeck> with <cardAddressInSideDeck>");
    }

    public void setPlayer1(DuelPlayer player, boolean isAI) {
        this.player1 = player;
        this.isAI = isAI;
    }

    public void setPlayer2(DuelPlayer player) {
        this.player2 = player;
    }

    public void showError(Error error) {
        System.out.println(error.getValue());
    }

    public void showMessage(SuccessMessage message) {
        System.out.println(message.getValue());
    }

    public void showDeck(DuelPlayer player) {
        List<Card> mainCards = player.getPlayDeck().getMainCards();
        List<Card> sideCards = player.getPlayDeck().getSideCards();
        int counter = 1;
        for (Card mainCard : mainCards) {
            System.out.println(counter + "-" + mainCard.getName() + " : " + mainCard.getCardType());
            counter++;
        }
        counter = 1;
        for (Card sideCard : sideCards) {
            System.out.println(counter + "-" + sideCard.getName() + " : " + sideCard.getCardType());
            counter++;
        }
    }
}
