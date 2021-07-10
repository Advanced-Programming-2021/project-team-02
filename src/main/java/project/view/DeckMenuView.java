package project.view;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import project.controller.DeckMenuController;
import project.controller.MainMenuController;
import project.model.Assets;
import project.model.Deck;
import project.model.Music;
import project.model.User;
import project.model.card.Card;
import project.view.messages.DeckMenuMessage;
import project.view.messages.PopUpMessage;
import project.view.messages.ProfileMenuMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class DeckMenuView {
    private static final DeckMenuController controller = DeckMenuController.getInstance();
    public ImageView playPauseMusicButton;
    public ImageView muteUnmuteButton;
    HashMap<String, Image> imageHashMap;
    @FXML
    public GridPane gridPaneAsli;

    public static Label getLabel() {
        return labelDeck;
    }

    @FXML
    public static Label labelDeck;
    public static final Image deckImage = new Image(String.valueOf(DeckMenuView.class.getResource("/project/image/GamePictures/DeckPicture.jpg")));
    public static final Image editDeckImage = new Image(String.valueOf(DeckMenuView.class.getResource("/project/image/Icon/edit.png")));
    public static final Image deleteDeckImage = new Image(String.valueOf(DeckMenuView.class.getResource("/project/image/Icon/remove.png")));
    public static final Image activateImage = new Image(String.valueOf(DeckMenuView.class.getResource("/project/image/Icon/checked.png")));
    public static final Image deactivateImage = new Image(String.valueOf(DeckMenuView.class.getResource("/project/image/Icon/cancel.png")));
    public static final ArrayList<ImageView> activateImageViews = new ArrayList<>();
    public static final User user = MainMenuController.getInstance().getLoggedInUser();
    public static final ArrayList<Deck> deckArrayList = Objects.requireNonNull(Assets.getAssetsByUsername(user.getUsername())).getAllDecks();

    @FXML
    public void initialize() {
        Utility utility = new Utility();
        utility.addImages();
        imageHashMap = utility.getStringImageHashMap();
        showDecks();
    }

    private void showDecks() {
        int maximumOfJ = 0;
        int counterSize = 0;
        for (int i = 0, j = 0; counterSize < deckArrayList.size(); i++) {
            if (maximumOfJ == 3) {
                j++;
                i = 0;
                maximumOfJ = 0;
            }
            Label labelDeckName = new Label(deckArrayList.get(counterSize).getName());
            labelDeckName.setContentDisplay(ContentDisplay.CENTER);
            labelDeckName.setStyle("-fx-text-fill: #ffd500; -fx-font-size: 18; -fx-cursor: hand;");

            ImageView deckIcon = new ImageView(deckImage);
            deckIcon.setFitWidth(150);
            deckIcon.setFitHeight(150);
            labelDeckName.setGraphic(deckIcon);

            ArrayList<Card> sideCards = deckArrayList.get(counterSize).getSideCards();
            ArrayList<Card> mainCards = deckArrayList.get(counterSize).getMainCards();

            Label labelNumberOfCardsMain = new Label("Main cards\t" + mainCards.size());
            labelNumberOfCardsMain.setPrefHeight(30);
            labelNumberOfCardsMain.setStyle("-fx-text-fill: white");

            Label labelNumberOfCardsSide = new Label("Side cards\t" + sideCards.size());
            labelNumberOfCardsSide.setPrefHeight(30);
            labelNumberOfCardsSide.setStyle("-fx-text-fill: white");

            ImageView activateImageView = new ImageView();
            activateImageViews.add(activateImageView);
            activateImageView.setId(deckArrayList.get(counterSize).getName());
            activateImageView.setFitHeight(30);
            activateImageView.setFitWidth(30);
            if (deckArrayList.get(counterSize).isActivated()) activateImageView.setImage(activateImage);
            else activateImageView.setImage(deactivateImage);

            Button activateButton = new Button();
            activateButton.setGraphic(activateImageView);
            activateButton.setId(deckArrayList.get(counterSize).getName());
            activateButton.setStyle("-fx-cursor: hand; -fx-background-color: transparent; -fx-background-radius: 20");
            activateButton.setTooltip(new Tooltip("Activate/Deactivate Deck"));
            activateButton.setOnMouseClicked(event -> {
                if (event.getButton() != MouseButton.PRIMARY) return;
                activateDeck(activateButton);
            });

            ImageView deleteImageView = new ImageView(deleteDeckImage);
            deleteImageView.setFitHeight(30);
            deleteImageView.setFitWidth(30);

            Button deleteButton = new Button();
            deleteButton.setGraphic(deleteImageView);
            deleteButton.setId(deckArrayList.get(counterSize).getName());
            deleteButton.setStyle("-fx-cursor: hand; -fx-background-color: transparent; -fx-background-radius: 20");
            deleteButton.setTooltip(new Tooltip("Delete Deck"));
            deleteButton.setOnMouseClicked(event -> {
                if (event.getButton() != MouseButton.PRIMARY) return;
                checkDelete(deleteButton);
            });

            ImageView editImageView = new ImageView(editDeckImage);
            editImageView.setFitHeight(25);
            editImageView.setFitWidth(25);

            Button editButton = new Button();
            editButton.setGraphic(editImageView);
            editButton.setId(deckArrayList.get(counterSize).getName());
            editButton.setStyle("-fx-cursor: hand; -fx-background-color: transparent; -fx-background-radius: 20");
            editButton.setTooltip(new Tooltip("Edit Deck"));
            editButton.setOnMouseClicked(event -> {
                try {
                    if (event.getButton() != MouseButton.PRIMARY) return;
                    editDeck(event, editButton);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            VBox deckContainer = new VBox(10);
            deckContainer.setPadding(new Insets(10, 10, 10, 10));
            deckContainer.setAlignment(Pos.CENTER);
            deckContainer.getChildren().addAll(labelDeckName, labelNumberOfCardsMain, labelNumberOfCardsSide);

            VBox controlsContainer = new VBox(10);
            controlsContainer.setPadding(new Insets(10, 10, 10, 10));
            controlsContainer.setStyle("-fx-border-width: 0 0 0 1; -fx-border-color: white;");
            controlsContainer.setAlignment(Pos.CENTER);
            controlsContainer.getChildren().addAll(activateButton, editButton, deleteButton);

            HBox layout = new HBox();
            layout.setStyle("-fx-border-radius: 10; -fx-border-color: white; -fx-background-color: #323c46; -fx-background-radius: 10;");
            layout.getChildren().addAll(deckContainer, controlsContainer);

            gridPaneAsli.add(layout, i, j);
            maximumOfJ++;
            counterSize++;
        }

        gridPaneAsli.setPadding(new Insets(10, 10, 10, 10));
        gridPaneAsli.setVgap(25);
        gridPaneAsli.setHgap(25);
    }

    private void activateDeck(Button button) {
        DeckMenuMessage deckMenuMessage = controller.activateDeck(button.getId());
        new PopUpMessage(deckMenuMessage.getAlertType(), deckMenuMessage.getLabel());
        for (ImageView activateImageView : activateImageViews) {
            if (activateImageView.getId().equals(button.getId())) activateImageView.setImage(activateImage);
            else activateImageView.setImage(deactivateImage);
        }
    }

    public void editDeck(javafx.scene.input.MouseEvent actionEvent, Button button) throws IOException {
        controller.setOpenedDeckButton(button);
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        Utility.openNewMenu("/project/fxml/edit_deck_menu.fxml");
    }

    public void addDeck() {
        if (Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getAllDecks().size() == 6) {
            new PopUpMessage(DeckMenuMessage.DECK_MAXIMUM_NUMBER.getAlertType(), DeckMenuMessage.DECK_MAXIMUM_NUMBER.getLabel());
        } else {
            Stage window = new Stage();
            window.initModality(Modality.WINDOW_MODAL);
            window.initOwner(LoginMenuView.getStage());
            window.initStyle(StageStyle.UNDECORATED);
            window.initStyle(StageStyle.TRANSPARENT);
            PopUpMessage.setStage(window);

            Label title = new Label("New Deck");
            title.setId("windowTitle");

            Button addDeckButton = new Button("Create Deck");
            addDeckButton.setId("windowButton");

            TextField textField = new TextField();
            textField.setPromptText("Enter Deck Name");
            textField.setId("windowField");

            addDeckButton.setOnMouseClicked(event -> {
                if (textField.getText().length() == 0) {
                    new PopUpMessage(ProfileMenuMessage.INVALID_INPUT.getAlertType(),
                            ProfileMenuMessage.INVALID_INPUT.getLabel());
                } else {
                    if (Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getAllDecks().size() == 6) {
                        new PopUpMessage(DeckMenuMessage.DECK_MAXIMUM_NUMBER.getAlertType(), DeckMenuMessage.DECK_MAXIMUM_NUMBER.getLabel());
                    } else {
                        DeckMenuMessage deckMenuMessage = controller.createDeck(textField.getText());
                        new PopUpMessage(deckMenuMessage.getAlertType(), deckMenuMessage.getLabel());
                        try {
                            loadAddCard(event);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        textField.clear();
                    }
                }
            });

            Button closeButton = new Button();
            closeButton.setText("Close");
            closeButton.setOnAction(event -> {
                window.close();
                textField.clear();
                PopUpMessage.setStage(LoginMenuView.getStage());
            });
            closeButton.setCursor(Cursor.HAND);
            closeButton.setId("windowCloseButton");

            VBox layout = new VBox(20);
            layout.setPadding(new Insets(20, 20, 20, 20));
            layout.getChildren().addAll(title, textField, addDeckButton, closeButton);
            layout.setAlignment(Pos.CENTER);
            layout.setId("window");

            Scene scene = new Scene(layout, 300, 250);
            scene.getStylesheets().add(String.valueOf(getClass().getResource("/project/CSS/deck_menu.css")));
            scene.setFill(Color.TRANSPARENT);
            window.setScene(scene);
            window.setResizable(true);
            window.showAndWait();
        }
    }

    private void loadAddCard(MouseEvent mouseEvent) throws IOException {
        showDecks();
    }

    private void checkDelete(Button button) {
        DeckMenuMessage deckMenuMessage = controller.deleteDeck(button.getId());
        new PopUpMessage(deckMenuMessage.getAlertType(), deckMenuMessage.getLabel());
        if (deckMenuMessage == DeckMenuMessage.DECK_DELETED)
            gridPaneAsli.getChildren().clear();
        showDecks();
    }

    public void nextTrack(MouseEvent actionEvent) {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        Music.nextTrack(playPauseMusicButton, muteUnmuteButton);
    }

    public void playPauseMusic(MouseEvent actionEvent) {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        Music.playPauseMusic(playPauseMusicButton);
    }

    public void muteUnmuteMusic(MouseEvent actionEvent) {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        Music.muteUnmuteMusic(muteUnmuteButton);
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() != MouseButton.PRIMARY) return;
        Utility.openNewMenu("/project/fxml/main_menu.fxml");
    }
}