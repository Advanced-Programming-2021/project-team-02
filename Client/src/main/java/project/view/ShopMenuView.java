package project.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import project.controller.ControllerManager;
import project.controller.MainMenuController;
import project.controller.ShopMenuController;
import project.model.Assets;
import project.model.Shop;
import project.model.User;
import project.model.card.Card;
import project.view.messages.PopUpMessage;
import project.view.messages.ShopMenuMessage;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ShopMenuView {
    public static final ArrayList<Button> buttons = new ArrayList<>();
    public static final ArrayList<ImageView> imageViews = new ArrayList<>();
    private static ShopMenuController controller = null;
    private static ShopMenuView instance;
    private final AudioClip onClick = new AudioClip(Objects.requireNonNull(Utility.class.getResource("/project/soundEffects/CURSOR.wav")).toString());

    public GridPane shopGrid;
    public Label availabilityLabel;
    public Label priceLabel;
    public Button sellButton;
    public Button buyButton;
    public ImageView selectedCardImage;
    public Label pageLabel;
    public Label stockLabel;
    public Label coinsLabel;
    private HashMap<String, Integer> allUserCards;
    private LinkedHashMap<String, Integer> cardsWithPrice;
    private LinkedHashMap<String, Integer> cardsWithNumber;
    private int pageCount;
    private Utility utility;
    private Assets assets;
    private String selectedCardName;

    public static ShopMenuView getInstance() {
        return instance;
    }

    @FXML
    public void initialize() throws IOException {
        ControllerManager.getInstance().getLastShopData();
        ShopMenuController.getInstance().setView(this);
        instance = this;
        assets = MainMenuController.getInstance().getLoggedInUserAssets();
        utility = new Utility();
        utility.addImages();
        coinsLabel.setText("Coins : " + String.valueOf(MainMenuController.getInstance().getLoggedInUserAssets().getCoin()));
        stockLabel.setText("");
        pageCount = 1;
        pageLabel.setText(String.valueOf(pageCount));
        controller = ShopMenuController.getInstance();
        buyButton.setStyle("-fx-background-color: #323c46");
        sellButton.setStyle("-fx-background-color: #323c46");
        priceLabel.setText("");
        availabilityLabel.setText("");
        setCards();
    }

    public void setCards() {
        System.out.println("runned!");
        cardsWithPrice = (LinkedHashMap<String, Integer>) Shop.getInstance().getCardsWithPrices();
        cardsWithNumber = Shop.getInstance().getCardsWithNumberOfThem();

        ArrayList<String> cards = (ArrayList<String>) new ArrayList<>(cardsWithPrice.keySet());
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
                    if (availability == -1)
                        availabilityLabel.setText("Forbidden Card");
                    else if (availability == 0)
                        availabilityLabel.setText("Not available");
                    else
                        availabilityLabel.setText("Available : " + availability);
                    int number = MainMenuController.getInstance().getLoggedInUserAssets().getAllUserCards().get(selectedCardName) == null ? 0 : MainMenuController.getInstance().getLoggedInUserAssets().getAllUserCards().get(selectedCardName);
                    stockLabel.setText("Your stock : " + number);
                    if (price < MainMenuController.getInstance().getLoggedInUserAssets().getCoin()) {
                        buyButton.setStyle("-fx-background-color: #bb792d;");
                        buyButton.setCursor(Cursor.HAND);
                        buyButton.setOnMouseClicked(mouseEvent2 -> {
                            if (mouseEvent2.getButton() != MouseButton.PRIMARY)
                                return;
                            System.out.println("the card : " + cardName);
                            ShopMenuMessage menuMessage = controller.buyCard(cardName);
                            coinsLabel.setText("Coins : " + String.valueOf(MainMenuController.getInstance().getLoggedInUserAssets().getCoin()));
                            if (menuMessage != ShopMenuMessage.CARD_ADDED)
                                new PopUpMessage(menuMessage.getAlertType(), menuMessage.getLabel());
                            else setCards();
                        });
                    } else {
                        buyButton.setStyle("-fx-background-color: #323c46");
                        buyButton.setCursor(Cursor.DEFAULT);
                        buyButton.setOnMouseClicked(mouseEvent1 -> {
                        });
                    }
                    if (number != 0) {
                        sellButton.setStyle(" -fx-background-color: #bb792d;");
                        sellButton.setCursor(Cursor.HAND);
                        sellButton.setOnMouseClicked(mouseEvent1 -> {
                            if (mouseEvent1.getButton() != MouseButton.PRIMARY)
                                return;
                            ShopMenuMessage menuMessage = controller.sellCard(cardName);
                            if (menuMessage != ShopMenuMessage.SUCCESS)
                                new PopUpMessage(menuMessage.getAlertType(), menuMessage.getLabel());
                            else setCards();
                        });
                    } else {
                        sellButton.setStyle("-fx-background-color: #323c46");
                        sellButton.setCursor(Cursor.DEFAULT);
                        sellButton.setOnMouseClicked(mouseEvent1 -> {
                        });
                    }


                });
                if (selectedCardName != null && selectedCardName.equals(cardName)) {
                    selectedCardImage.setOnMouseClicked(imageView.getOnMouseClicked());
                    selectedCardImage.setImage(imageView.getImage());
                    priceLabel.setText("Price : " + price);
                    int availability = cardsWithNumber.get(cardName);
                    if (availability == -1)
                        availabilityLabel.setText("Forbidden Card");
                    else if (availability == 0)
                        availabilityLabel.setText("Not available");
                    else
                        availabilityLabel.setText("Available : " + availability);
                    int number = MainMenuController.getInstance().getLoggedInUserAssets().getAllUserCards().get(selectedCardName) == null ? 0 : MainMenuController.getInstance().getLoggedInUserAssets().getAllUserCards().get(selectedCardName);
                    stockLabel.setText("Your stock : " + number);
                }
                imageView.setCursor(Cursor.HAND);
                shopGrid.add(imageView, j, i);
            }
        }
    }

    private void updateSelectedCard(LinkedHashMap<String, Integer> cardsWithNumber) {
        if (selectedCardName == null)
            return;
        int availability = cardsWithNumber.get(selectedCardName);
        if (availability == -1)
            availabilityLabel.setText("Forbidden Card");
        else if (availability == 0)
            availabilityLabel.setText("Not available");
        else
            availabilityLabel.setText("Available : " + availability);
        int number = MainMenuController.getInstance().getLoggedInUserAssets().getAllUserCards().get(selectedCardName) == null ? 0 : assets.getAllUserCards().get(selectedCardName);
        stockLabel.setText("Your stock : " + number);
        coinsLabel.setText("Coins : " + assets.getCoin());
        String[] price = priceLabel.getText().split(" ");
        if (MainMenuController.getInstance().getLoggedInUserAssets().getCoin() < Integer.parseInt(price[2]) || cardsWithNumber.get(selectedCardName) == 0) {
            buyButton.setStyle("-fx-background-color: #323c46");
            buyButton.setCursor(Cursor.DEFAULT);
            buyButton.setOnMouseClicked(mouseEvent1 -> {
            });
        } else {
            buyButton.setStyle("-fx-background-color: #bb792d;");
            buyButton.setCursor(Cursor.HAND);
            buyButton.setOnMouseClicked(mouseEvent2 -> {
                if (mouseEvent2.getButton() != MouseButton.PRIMARY)
                    return;
                System.out.println("the card : " + selectedCardName);
                ShopMenuMessage menuMessage = controller.buyCard(selectedCardName);
                coinsLabel.setText("Coins : " + String.valueOf(MainMenuController.getInstance().getLoggedInUserAssets().getCoin()));
                if (menuMessage != ShopMenuMessage.CARD_ADDED)
                    new PopUpMessage(menuMessage.getAlertType(), menuMessage.getLabel());
                else setCards();
            });
        }
        if (number == 0) {
            sellButton.setStyle("-fx-background-color: #323c46");
            sellButton.setCursor(Cursor.DEFAULT);
            sellButton.setOnMouseClicked(mouseEvent1 -> {
            });
        } else {
            sellButton.setCursor(Cursor.HAND);
            sellButton.setStyle(" -fx-background-color: #bb792d;");
            sellButton.setOnMouseClicked(mouseEvent1 -> {
                if (mouseEvent1.getButton() != MouseButton.PRIMARY)
                    return;
                ShopMenuMessage menuMessage = controller.sellCard(selectedCardName);
                if (menuMessage != ShopMenuMessage.SUCCESS)
                    new PopUpMessage(menuMessage.getAlertType(), menuMessage.getLabel());
                else setCards();
            });
        }
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


    public void previousPage(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() != MouseButton.PRIMARY)
            return;
        if (pageCount == 1)
            return;
        else if (pageCount == 2)
            pageCount = 1;
        else if (pageCount == 3)
            pageCount = 2;
        pageLabel.setText(String.valueOf(pageCount));
        priceLabel.setText("");
        availabilityLabel.setText("");
        selectedCardImage.setImage(null);
        buyButton.setStyle("-fx-background-color: #323c46");
        sellButton.setStyle("-fx-background-color: #323c46");
        selectedCardName = null;
        stockLabel.setText("");
        setCards();
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
        pageLabel.setText(String.valueOf(pageCount));
        priceLabel.setText("");
        availabilityLabel.setText("");
        selectedCardImage.setImage(null);
        buyButton.setStyle("-fx-background-color: #323c46");
        sellButton.setStyle("-fx-background-color: #323c46");
        selectedCardName = null;
        stockLabel.setText("");
        setCards();
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() != MouseButton.PRIMARY)
            return;
        onClick.play();
        Utility.openNewMenu("/project/fxml/main_menu.fxml");
    }

}