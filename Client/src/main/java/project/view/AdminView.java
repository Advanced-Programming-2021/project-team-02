package project.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.AudioClip;
import project.controller.AdminController;
import project.controller.ControllerManager;
import project.controller.MainMenuController;
import project.controller.ShopMenuController;
import project.model.Shop;
import project.view.messages.AdminPanelMessage;
import project.view.messages.PopUpMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

public class AdminView {
    private final AudioClip onClick = new AudioClip(Objects.requireNonNull(Utility.class.getResource("/project/soundEffects/CURSOR.wav")).toString());
    public Label priceLabel;
    public ImageView selectedCardImage;
    public Label availabilityLabel;
    public GridPane shopGrid;
    public Label pageLabel;
    public RadioButton availableButton;
    public RadioButton forbidCardButton;
    public Button addButton;
    public Button decreaseButton;
    public HBox addOrRemoveButtonBox;
    private Utility utility;
    private int pageCount;
    private LinkedHashMap<String, Integer> cardsWithPrice;
    private LinkedHashMap<String, Integer> cardsWithNumber;
    private String selectedCardName;
    private ToggleGroup toggleGroup = new ToggleGroup();

    @FXML
    public void initialize() throws IOException {
        ControllerManager.getInstance().getLastShopData();
        AdminController.getInstance().setView(this);
        AdminController.getInstance().initializeNetworkForAdmin();
        availableButton.setToggleGroup(toggleGroup);
        forbidCardButton.setToggleGroup(toggleGroup);
        utility = new Utility();
        utility.addImages();

        pageCount = 1;
        pageLabel.setText(String.valueOf(pageCount));
        //TODO controller = ShopMenuController.getInstance();
        priceLabel.setText("");
        availabilityLabel.setText("");
        setCards();
        addButton.setCursor(Cursor.HAND);
        decreaseButton.setCursor(Cursor.HAND);
    }

    public void setCards() {
        cardsWithPrice = (LinkedHashMap<String, Integer>) Shop.getInstance().getCardsWithPrices();
        cardsWithNumber = Shop.getInstance().getCardsWithNumberOfThem();
        ArrayList<String> cards = new ArrayList<>(cardsWithPrice.keySet());
        shopGrid.getChildren().clear();
        int firstIndex = pageCount == 1 ? 0 : (pageCount == 2 ? 24 : 48);
        int limit = firstIndex == 0 ? 24 : (firstIndex == 24 ? 24 : 4);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                if (i * 6 + j == limit)
                    return;
                String cardName = cards.get(firstIndex + 6 * i + j);
                ImageView imageView = new ImageView(getCardImageByName(cardName));
                imageView.setFitHeight(170);
                imageView.setFitWidth(120);
                imageView.setOnMouseEntered(mouseEvent -> {
                    imageView.setScaleX(1.2);
                    imageView.setScaleY(1.2);
                });
                imageView.setOnMouseExited(mouseEvent -> {
                    imageView.setScaleX(1);
                    imageView.setScaleY(1);
                });
                int price = cardsWithPrice.get(cardName);
                imageView.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton() != MouseButton.PRIMARY)
                        return;
                    selectedCardName = cardName;
                    selectedCardImage.setImage(getCardImageByName(cardName));
                    priceLabel.setText("Price : " + price);
                    int availability = cardsWithNumber.get(cardName);
                    if (availability == -1) {
                        availabilityLabel.setText("Forbidden Card");
                        forbidCardButton.setSelected(true);
                        availableButton.setSelected(false);
                        addOrRemoveButtonBox.getChildren().clear();
                    } else if (availability == 0) {
                        availabilityLabel.setText("Not available");
                        forbidCardButton.setSelected(false);
                        availableButton.setSelected(true);
                        addOrRemoveButtonBox.getChildren().clear();
                        addOrRemoveButtonBox.getChildren().addAll(addButton, decreaseButton);
                    } else {
                        availabilityLabel.setText("Available : " + availability);
                        forbidCardButton.setSelected(false);
                        availableButton.setSelected(true);
                        addOrRemoveButtonBox.getChildren().clear();
                        addOrRemoveButtonBox.getChildren().addAll(addButton, decreaseButton);
                    }
                    availableButton.setOnAction(actionEvent -> {
                        if (cardsWithNumber.get(cardName) > 0)
                            return;
                        availableButton.setSelected(true);
                        forbidCardButton.setSelected(false);
//                        cardsWithNumber.replace(cardName, 0);
                        availabilityLabel.setText("Not available");
                        addOrRemoveButtonBox.getChildren().clear();
                        addOrRemoveButtonBox.getChildren().addAll(addButton, decreaseButton);
                        AdminPanelMessage message = AdminController.getInstance().availableCard(cardName);
                        PopUpMessage.setStage(LoginMenuView.getStage());
                        new PopUpMessage(message.getAlertType(), message.getLabel());

                    });
                    addButton.setOnMouseClicked(mouseEvent2 -> {
                        if (mouseEvent2.getButton() != MouseButton.PRIMARY)
                            return;
                        AdminPanelMessage message1 = AdminController.getInstance().increaseCardInventory(cardName);
                        PopUpMessage.setStage(LoginMenuView.getStage());

                        new PopUpMessage(message1.getAlertType(), message1.getLabel());

                    });
                    decreaseButton.setOnMouseClicked(mouseEvent3 -> {
                        if (mouseEvent3.getButton() != MouseButton.PRIMARY)
                            return;
                        AdminPanelMessage message = AdminController.getInstance().decreaseCardInventory(cardName);
                        PopUpMessage.setStage(LoginMenuView.getStage());

                        new PopUpMessage(message.getAlertType(), message.getLabel());
                    });
                    forbidCardButton.setOnAction(actionEvent -> {
                        if (cardsWithNumber.get(cardName) == -1)
                            return;
                        availableButton.setSelected(false);
                        forbidCardButton.setSelected(true);
//                        cardsWithNumber.replace(cardName, -1);
                        addOrRemoveButtonBox.getChildren().clear();
                        availabilityLabel.setText("Forbidden Card");
                        AdminPanelMessage message = AdminController.getInstance().forbidCard(cardName);
                        PopUpMessage.setStage(LoginMenuView.getStage());
                        new PopUpMessage(message.getAlertType(), message.getLabel());
                    });
                    // setLabels(cardName, imageView, price);
                });
                setLabels(cardName, imageView, price);

                imageView.setCursor(Cursor.HAND);
                shopGrid.add(imageView, j, i);
            }
        }
    }

    private void setLabels(String cardName, ImageView imageView, int price) {
        if (selectedCardName != null && selectedCardName.equals(cardName)) {
            int availability = cardsWithNumber.get(cardName);
            selectedCardImage.setImage(imageView.getImage());
            priceLabel.setText("Price : " + price);
            if (availability == -1) {
                availabilityLabel.setText("Forbidden Card");
                addOrRemoveButtonBox.getChildren().clear();
            } else if (availability == 0) {
                availabilityLabel.setText("Not available");
                addOrRemoveButtonBox.getChildren().clear();
                addOrRemoveButtonBox.getChildren().addAll(addButton, decreaseButton);
            } else {
                availabilityLabel.setText("Available : " + availability);
                addOrRemoveButtonBox.getChildren().clear();
                addOrRemoveButtonBox.getChildren().addAll(addButton, decreaseButton);
            }
        }
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() != MouseButton.PRIMARY)
            return;
        onClick.play();
        AdminController.getInstance().close();
        Utility.openNewMenu("/project/fxml/main_menu.fxml");
    }

    public void nextPage(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() != MouseButton.PRIMARY)
            return;
        if (pageCount == 1)
            pageCount = 2;
        else if (pageCount == 2)
            pageCount = 3;
        else if (pageCount == 3)
            return;
        setChangePageStuff();
    }

    public void previousPage(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() != MouseButton.PRIMARY)
            return;
        if (pageCount == 1)
            return;
        else if (pageCount == 2)
            pageCount = 1;
        else if (pageCount == 3)
            pageCount = 2;
        setChangePageStuff();
    }

    private void setChangePageStuff() {
        addOrRemoveButtonBox.getChildren().clear();
        availableButton.setSelected(false);
        forbidCardButton.setSelected(false);
        pageLabel.setText(String.valueOf(pageCount));
        priceLabel.setText("");
        availabilityLabel.setText("");
        selectedCardImage.setImage(null);
        selectedCardName = null;
        setCards();
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

}
