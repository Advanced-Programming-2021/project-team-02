package project.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import project.controller.ControllerManager;
import project.controller.MainMenuController;
import project.controller.ShopMenuController;
import project.model.Shop;

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
    }

    public void setCards() {
        cardsWithPrice = (LinkedHashMap<String, Integer>) Shop.getInstance().getCardsWithPrices();
        cardsWithNumber = Shop.getInstance().getCardsWithNumberOfThem();
        ArrayList<String> cards = new ArrayList<>(cardsWithPrice.keySet());
        shopGrid.getChildren().clear();
        int firstIndex = pageCount == 1 ? 0 : (pageCount == 2 ? 24 : 48);
        int limit = firstIndex == 0 ? 24 : (firstIndex == 24 ? 24 : 4);
        System.out.println(firstIndex + "  limit : " + limit);
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
                    } else if (availability == 0) {
                        availabilityLabel.setText("Not available");
                        forbidCardButton.setSelected(false);
                        availableButton.setSelected(true);
                    } else {
                        availabilityLabel.setText("Available : " + availability);
                        forbidCardButton.setSelected(false);
                        availableButton.setSelected(true);
                    }
                    availableButton.setOnAction(actionEvent -> {
                        availableButton.setSelected(true);
                        forbidCardButton.setSelected(false);
                        cardsWithNumber.replace(cardName, 0);
                    });
                    forbidCardButton.setOnAction(actionEvent -> {
                        availableButton.setSelected(false);
                        forbidCardButton.setSelected(true);
                        cardsWithNumber.replace(cardName, -1);
                    });
                });
                if (selectedCardName != null && selectedCardName.equals(cardName)) {
                    int availability = cardsWithNumber.get(cardName);
                    selectedCardImage.setImage(imageView.getImage());
                    priceLabel.setText("Price : " + price);
                    if (availability == -1) {
                        availabilityLabel.setText("Forbidden Card");
                        forbidCardButton.setSelected(true);
                        availableButton.setSelected(false);
                    } else if (availability == 0) {
                        availabilityLabel.setText("Not available");
                        forbidCardButton.setSelected(false);
                        availableButton.setSelected(true);
                    } else {
                        availabilityLabel.setText("Available : " + availability);
                        forbidCardButton.setSelected(false);
                        availableButton.setSelected(true);
                    }
                }

                imageView.setCursor(Cursor.HAND);
                shopGrid.add(imageView, j, i);
            }
        }
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() != MouseButton.PRIMARY)
            return;
        onClick.play();
        Utility.openNewMenu("/project/fxml/main_menu.fxml");
    }

    public void nextPage(MouseEvent mouseEvent) {
        //TODO
    }

    public void previousPage(MouseEvent mouseEvent) {
        //TODO
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
