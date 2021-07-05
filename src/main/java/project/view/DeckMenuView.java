package project.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import project.controller.DeckMenuController;
import project.controller.MainMenuController;
import project.model.Assets;
import project.model.Deck;
import project.model.User;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import project.model.card.Card;
import project.model.card.CardsDatabase;
import project.view.messages.DeckMenuMessage;
import project.view.messages.PopUpMessage;
import project.view.messages.ProfileMenuMessage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class DeckMenuView {
    private static final DeckMenuController controller = DeckMenuController.getInstance();

    HashMap<String, Image> imageHashMap;
    @FXML
    public GridPane gridPaneAsli;

    public static Label getLabel() {
        return labelDeck;
    }

    @FXML
    public static Label labelDeck;


    @FXML
    public void initialize() {
        User user = MainMenuController.getInstance().getLoggedInUser();
        CardsDatabase cardsDatabase = CardsDatabase.getInstance();
        try {
            cardsDatabase.readAndMakeCards();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        User mahdi = new User("mahdi", "12345", "test");
//        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).createDeck("test1");
//        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).createDeck("test2");
//        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).createDeck("test3");
//        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).createDeck("test4");
//        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).createDeck("test5");
//        Objects.requireNonNull(Assets.getAssetsByUsername(mahdi.getUsername())).createDeck("test6");
//        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Haniwa"),
//                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
//        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Leotron "),
//                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));
//        Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).addCardToMainDeck(Card.getCardByName("Horn Imp"),
//                Objects.requireNonNull(Assets.getAssetsByUsername("mahdi")).getDeckByDeckName("test1"));


        Utility utility = new Utility();
        utility.addImages();
        imageHashMap = utility.getStringImageHashMap();
        showDecks(user);
    }

    private void showDecks(User user) {
        ArrayList<Deck> deckArrayList = Objects.requireNonNull(Assets.getAssetsByUsername(user.getUsername())).getAllDecks();

        System.out.println(Objects.requireNonNull(Assets.getAssetsByUsername(user.getUsername())).getAllDecks().size());
        int counterJ = 0;
        int counterSize = 0;
        for (int i = 0, j = 0; counterSize < Objects.requireNonNull(Assets.getAssetsByUsername(user.getUsername())).getAllDecks().size(); i++) {
            if (counterJ == 6) {
                j++;
                i = 0;
                counterJ = 0;
            }
            System.out.println(deckArrayList.get(counterSize).getName());
            Label labelDeckName = new Label(deckArrayList.get(counterSize).getName());
            labelDeckName.setFont(Font.font("Cambria", 30));
            labelDeckName.setTextFill(Color.web("#0076a3"));
            labelDeckName.setPrefHeight(30);
            labelDeckName.setPrefWidth(100);
            labelDeckName.setOnMouseClicked(event -> {
                try {
                    showDeckInfoAsli(labelDeckName, event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            ArrayList<Card> sideCards = deckArrayList.get(counterSize).getSideCards();
            ArrayList<Card> mainCards = deckArrayList.get(counterSize).getMainCards();
            Label labelNumberOfCardsMain = new Label("Main cards : " + mainCards.size());
            labelNumberOfCardsMain.setFont(Font.font("Cambria", 10));
            labelNumberOfCardsMain.setTextFill(Color.web("#0076a3"));
            labelNumberOfCardsMain.setPrefWidth(100);
            labelNumberOfCardsMain.setPrefHeight(30);

            Label labelNumberOfCardsSide = new Label(" Side cards : " + sideCards.size());
            labelNumberOfCardsSide.setFont(Font.font("Cambria", 10));
            labelNumberOfCardsSide.setTextFill(Color.web("#0076a3"));
            labelNumberOfCardsSide.setPrefWidth(100);
            labelNumberOfCardsSide.setPrefHeight(30);


            Button buttonDelete = new Button("delete deck");
            buttonDelete.setId(deckArrayList.get(i).getName());
            buttonDelete.setPrefHeight(30);
            buttonDelete.setPrefWidth(80);
            buttonDelete.setOnAction(event -> checkDelete(buttonDelete));

            Button buttonEdit = new Button("edit deck");
            buttonEdit.setId(deckArrayList.get(i).getName());
            buttonEdit.setPrefHeight(30);
            buttonEdit.setPrefWidth(80);
            buttonEdit.setOnMouseClicked(event -> {
                try {
                    editDeck(event, buttonEdit);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            VBox layout = new VBox(10);
            layout.setPadding(new Insets(30, 10, 10, 10));
            layout.setPrefHeight(100);
            layout.setPrefWidth(100);
            layout.setEffect(new DropShadow());

            layout.getChildren().addAll(labelDeckName, buttonEdit, buttonDelete, labelNumberOfCardsMain, labelNumberOfCardsSide);


            gridPaneAsli.add(layout, i, j);
            counterJ++;
            counterSize++;
        }

        Button createDeck = new Button("Create Deck");
        createDeck.setOnAction(actionEvent -> addDeck());
        gridPaneAsli.add(createDeck, 0, 7);

        Button back = new Button("Back");
        back.setOnMouseClicked(actionEvent -> {
            try {
                backToMain(actionEvent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        gridPaneAsli.add(back, 3, 7);

        gridPaneAsli.setPadding(new Insets(50, 10, 10, 50));
        gridPaneAsli.setVgap(100);
        gridPaneAsli.setHgap(100);
    }

    private void backToMain(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() != MouseButton.PRIMARY) return;
        Utility.openNewMenu("/project/fxml/main_menu.fxml");
    }

    private void editDeck(javafx.scene.input.MouseEvent actionEvent, Button button) throws IOException {
        controller.setOpenedDeckButton(button);
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        URL urlMain = getClass().getResource("/project/fxml/deck_menu_info.fxml");
        System.out.println(urlMain);
        Utility.openNewMenu("/project/fxml/edit_deck_menu.fxml");
    }

    private void addDeck() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UNDECORATED);
        PopUpMessage.setStage(window);
        window.setTitle("Add Deck");
        Label currentNickNameLabel = new Label();
        currentNickNameLabel.setText("New Deck Name :");
        currentNickNameLabel.setFont(Font.font("Cambria", 20));
        currentNickNameLabel.setTextFill(Color.web("#0076a3"));
        Button addDeckButton = new Button();
        addDeckButton.setPrefHeight(30);
        addDeckButton.setStyle("-fx-border-color: red; -fx-text-fill: blue; -fx-font-size: 15px;");
        addDeckButton.setText("Create Deck");
        TextField textField = new TextField();
        textField.setMaxSize(200, 60);
        textField.setStyle("-fx-background-insets: 0, 0 0 1 0 ;" +
                " -fx-background-color: grey;");
        addDeckButton.setOnMouseClicked(event -> {
            if (textField.getText().length() == 0) {
                new PopUpMessage(ProfileMenuMessage.INVALID_INPUT.getAlertType(),
                        ProfileMenuMessage.INVALID_INPUT.getLabel());
            } else {
                DeckMenuMessage deckMenuMessage = controller.createDeck(textField.getText());
                new PopUpMessage(deckMenuMessage.getAlertType(), deckMenuMessage.getLabel());
                try {
                    loadAddCard(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10, 50, 50, 50));
        layout.setMinSize(200, 200);
        layout.getChildren().addAll(currentNickNameLabel, textField);
        layout.getChildren().add(addDeckButton);
        layout.setAlignment(Pos.BASELINE_LEFT);
        Scene scene = new Scene(layout, 300, 300);
        window.setScene(scene);
        window.setResizable(true);
        window.showAndWait();
    }

    private void showDeckInfoAsli(Label labelDeckName, javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        controller.setOpenedDeck(labelDeckName);
        if (mouseEvent.getButton() != MouseButton.PRIMARY) return;
        URL urlMain = getClass().getResource("/project/fxml/deck_menu_info.fxml");
        System.out.println(urlMain);
        Utility.openNewMenu("/project/fxml/deck_menu_info.fxml");
    }

    private void loadAddCard(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() != MouseButton.PRIMARY) return;
        Utility.openNewMenu("/project/fxml/deck_menu.fxml");
    }

    private void checkDelete(Button button) {
        ArrayList<Deck> arrayList = Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getAllDecks();
        for (Deck deck : arrayList) {
            if (button.getId().equals(deck.getName())) {
                DeckMenuMessage deckMenuMessage = controller.deleteDeck(button.getId());
                new PopUpMessage(deckMenuMessage.getAlertType(), deckMenuMessage.getLabel());
                gridPaneAsli.getChildren().clear();
               // Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).deleteDeck(button.getId());
                showDecks(MainMenuController.getInstance().getLoggedInUser());
            }
        }
    }
}