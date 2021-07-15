package project.view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;
import project.controller.ControllerManager;
import project.controller.MainMenuController;
import project.controller.ShopMenuController;
import project.model.Assets;
import project.model.Shop;
import project.view.messages.PopUpMessage;
import project.view.messages.ShopMenuMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

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
    private LinkedHashMap<String, Integer> cardsWithPrice;
    private LinkedHashMap<String, Integer> cardsWithNumber;
    private int pageCount;
    private Utility utility;
    private String selectedCardName;

    @FXML
    public void initialize() throws IOException {
        ControllerManager.getInstance().getLastShopData();
        ShopMenuController.getInstance().setView(this);
        ShopMenuController.getInstance().initializeNetWorkForTransferShopData();
        utility = new Utility();
        utility.addImages();
        coinsLabel.setText("Coins : " + MainMenuController.getInstance().getLoggedInUserAssets().getCoin());
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
                    if (availability == -1)
                        availabilityLabel.setText("Forbidden Card");
                    else if (availability == 0)
                        availabilityLabel.setText("Not available");
                    else
                        availabilityLabel.setText("Available : " + availability);
                    int loggedInStock = MainMenuController.getInstance().getLoggedInUserAssets().getAllUserCards().get(selectedCardName) == null ? 0 : MainMenuController.getInstance().getLoggedInUserAssets().getAllUserCards().get(selectedCardName);
                    stockLabel.setText("Your stock : " + loggedInStock);
                    setButtonsStatus(availability, loggedInStock, price, cardName);
                });
                if (selectedCardName != null && selectedCardName.equals(cardName)) {
                    int availability = cardsWithNumber.get(cardName);
                    int loggedInStock = MainMenuController.getInstance().getLoggedInUserAssets().getAllUserCards().get(selectedCardName) == null ? 0 : MainMenuController.getInstance().getLoggedInUserAssets().getAllUserCards().get(selectedCardName);
                    setButtonsStatus(availability, loggedInStock, price, cardName);
                    selectedCardImage.setImage(imageView.getImage());
                    priceLabel.setText("Price : " + price);
                    if (availability == -1)
                        availabilityLabel.setText("Forbidden Card");
                    else if (availability == 0)
                        availabilityLabel.setText("Not available");
                    else
                        availabilityLabel.setText("Available : " + availability);
                    stockLabel.setText("Your stock : " + loggedInStock);
                }
                imageView.setCursor(Cursor.HAND);
                shopGrid.add(imageView, j, i);
            }
        }
    }

    private void setButtonsStatus(int availability, int loggedInStock, int price, String cardName) {
        if (price < MainMenuController.getInstance().getLoggedInUserAssets().getCoin() && availability > 0) {
            buyButton.setStyle("-fx-background-color: #bb792d;");
            buyButton.setCursor(Cursor.HAND);
            buyButton.setOnMouseClicked(mouseEvent2 -> {
                if (mouseEvent2.getButton() != MouseButton.PRIMARY)
                    return;
                System.out.println("the card : " + cardName);
                ShopMenuMessage menuMessage = controller.buyCard(cardName);
                coinsLabel.setText("Coins : " + MainMenuController.getInstance().getLoggedInUserAssets().getCoin());
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
        if (loggedInStock > 0) {
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
        setChangePageStuff();
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

    private void setChangePageStuff() {
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
        ShopMenuController.getInstance().closeShop();
        Utility.openNewMenu("/project/fxml/main_menu.fxml");
    }

    public void openAdminPanel(MouseEvent mouseEvent) throws IOException {
    if (mouseEvent.getButton()!=MouseButton.PRIMARY)
        return;
        onClick.play();
        Utility.openNewMenu("/project/fxml/admin_view.fxml");
    }
}