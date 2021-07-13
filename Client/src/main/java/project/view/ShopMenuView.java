package project.view;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import project.controller.MainMenuController;
import project.controller.ShopMenuController;
import project.view.messages.PopUpMessage;
import project.view.messages.ShopMenuMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ShopMenuView {
    public static final ArrayList<Button> buttons = new ArrayList<>();
    public static final ArrayList<ImageView> imageViews = new ArrayList<>();
    private static ShopMenuController controller = null;
    private final AudioClip onClick = new AudioClip(Objects.requireNonNull(Utility.class.getResource("/project/soundEffects/CURSOR.wav")).toString());
    @FXML
    public Label C1;
    @FXML
    public Label C2;
    @FXML
    public Label C3;
    @FXML
    public Label C4;
    @FXML
    public Label C5;
    @FXML
    public Label C6;
    @FXML
    public Label C7;
    @FXML
    public Label C8;
    @FXML
    public Label C9;
    @FXML
    public Label C10;
    @FXML
    public Label C11;
    @FXML
    public Label C12;
    @FXML
    public Label C13;
    @FXML
    public Label C14;
    @FXML
    public Label C15;
    @FXML
    public Label C16;
    @FXML
    public Label C17;
    @FXML
    public Label C18;
    @FXML
    public Label C19;
    @FXML
    public Label C20;
    @FXML
    public Label C21;
    @FXML
    public Label C22;
    @FXML
    public Label C23;
    @FXML
    public Label C24;
    @FXML
    public Label C25;
    @FXML
    public Label C26;
    @FXML
    public Label C27;
    @FXML
    public Label C28;
    @FXML
    public Label C29;
    @FXML
    public Label C30;
    @FXML
    public Label C31;
    @FXML
    public Label C32;
    @FXML
    public Label C33;
    @FXML
    public Label C34;
    @FXML
    public Label C35;
    @FXML
    public Label C36;
    @FXML
    public Label C37;
    @FXML
    public Label C38;
    @FXML
    public Label C39;
    @FXML
    public Label C40;
    @FXML
    public Label C41;
    @FXML
    public Label C42;
    @FXML
    public Label C43;
    @FXML
    public Label C44;
    @FXML
    public Label C45;
    @FXML
    public Label C46;
    @FXML
    public Label C47;
    @FXML
    public Label C48;
    @FXML
    public Label C49;
    @FXML
    public Label C50;
    @FXML
    public Label Coin;
    public Button seeOtherCards;
    public ScrollPane scrollPane;
    public HBox row1;
    public HBox row2;
    public HBox row3;
    public HBox row4;
    public HBox row5;
    public HBox row6;
    public HBox row7;
    public Label C52;
    public Label C51;
    public GridPane shopGrid;
    HashMap<String, Integer> allUserCards;

    @FXML
    public void initialize() throws IOException {
        controller = ShopMenuController.getInstance();
        Coin.setText(String.valueOf(MainMenuController.getInstance().getLoggedInUserAssets().getCoin()));
        allUserCards = Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets().getAllUserCards());
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPrefWidth(1525);
        scrollPane.setPrefHeight(860);
        showNumber();
        getImageViewsAndCreateButtons(row1);
        getImageViewsAndCreateButtons(row2);
        getImageViewsAndCreateButtons(row3);
        getImageViewsAndCreateButtons(row4);
        getImageViewsAndCreateButtons(row5);
        getImageViewsAndCreateButtons(row6);
        getImageViewsAndCreateButtons(row7);
    }

    public void getImageViewsAndCreateButtons(HBox hBox) {
        ArrayList<VBox> vBoxes = new ArrayList<>();
        for (Node child : hBox.getChildren()) vBoxes.add((VBox) child);
        int counter = 0;
        for (VBox vBox : vBoxes) {
            imageViews.add((ImageView) vBox.getChildren().get(0));

            Button button = new Button();
            button.setGraphic(vBox.getChildren().get(0));
            buttons.add(button);
            button.setStyle("-fx-background-color: transparent;");
            button.setPadding(new Insets(-3, -3, -3, -3));
            button.setOnMouseClicked(mouseEvent -> onClick.play());
            vBox.getChildren().add(0, button);

            Tooltip tooltip = new Tooltip();
            ImageView imageViewForToolTip = new ImageView(imageViews.get(counter).getImage());
            imageViewForToolTip.setFitHeight(490);
            imageViewForToolTip.setPreserveRatio(true);
            tooltip.setGraphic(imageViewForToolTip);
            button.setTooltip(tooltip);
            counter++;
        }
    }

    public void showNumber() {
        allUserCards = Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getAllUserCards();
        for (String card : Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getAllUserCards().keySet()) {
            if (card.equals("Alexandrite Dragon")) C1.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Axe Raider")) C2.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Baby dragon")) C3.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Battle OX")) C4.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Battle warrior")) C5.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Beast King Barbaros")) C6.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Bitron")) C7.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Blue-Eyes white dragon")) C8.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Crab Turtle")) C9.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Crawling dragon")) C10.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Curtain of the dark ones")) C11.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Dark Blade")) C12.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Dark magician")) C13.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Exploder Dragon")) C14.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Feral Imp")) C15.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Fireyarou")) C16.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Flame manipulator")) C17.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Gate Guardian")) C18.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Haniwa")) C19.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Hero of the east")) C20.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Horn Imp")) C21.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Leotron ")) C22.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Man-Eater Bug")) C23.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Silver Fang")) C24.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Skull Guardian")) C25.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Slot Machine")) C26.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Spiral Serpent")) C27.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("The Tricky")) C28.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Warrior Dai Grepher")) C29.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Wattaildragon")) C30.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Wattkid")) C31.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Yomi Ship")) C32.setText(String.valueOf(allUserCards.get(card)));

            if (card.equals("Advanced Ritual Art")) C33.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Black Pendant")) C34.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Closed Forest")) C35.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Dark Hole")) C36.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Forest")) C37.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Harpie's Feather Duster")) C38.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Monster Reborn")) C39.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Pot of Greed")) C40.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Raigeki")) C41.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Sword of dark destruction")) C42.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Terraforming")) C43.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Umiiruka")) C44.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Yami")) C45.setText(String.valueOf(allUserCards.get(card)));

            if (card.equals("Magic Cylinder")) C46.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Mirror Force")) C47.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Negate Attack")) C48.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Torrential Tribute")) C49.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Trap Hole")) C50.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Call of The Haunted")) C51.setText(String.valueOf(allUserCards.get(card)));
            if (card.equals("Time Seal")) C52.setText(String.valueOf(allUserCards.get(card)));

        }
    }

    public void BEWD() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Blue-Eyes white dragon");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void AR() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Axe Raider");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void BD() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Baby dragon");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void BO() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Battle OX");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void BKB() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Beast King Barbaros");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void BW() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Battle warrior");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void CT() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Crab Turtle");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void CD() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Crawling dragon");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void DB() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Dark Blade");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void COTDO() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Curtain of the dark ones");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void AD() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Alexandrite Dragon");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void DM() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Dark magician");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void GG() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Gate Guardian");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void FM() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Flame manipulator");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void FI() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Feral Imp");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void ED() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Exploder Dragon");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void HOTE() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Hero of the east");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void HI() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Horn Imp");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void SF() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Silver Fang");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void MEB() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Man-Eater Bug");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void SG() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Skull Guardian");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void SS() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Spiral Serpent");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void WDG() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Warrior Dai Grepher");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void TT() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("The Tricky");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void SM() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Slot Machine");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void YS() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Yomi Ship");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void ARA() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Advanced Ritual Art");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void CF() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Closed Forest");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void Forest() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Forest");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void BP() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Black Pendant");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void DH() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Dark Hole");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void MR() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Monster Reborn");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void POG() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Pot of Greed");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void SODD() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Sword of dark destruction");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void HFD() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Harpie's Feather Duster");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void MF() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Mirror Force");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void TH() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Trap Hole");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void NA() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Negate Attack");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void MC() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Magic Cylinder");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void BBBBB() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Bitron");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void FFFFF() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Fireyarou");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void LLLLL() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Leotron ");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void WWWWW() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Wattaildragon");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void WWWW() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Wattkid");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void RRRRR() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Raigeki");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void YYYYY() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Yami");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void UUUUU() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Umiiruka");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void TTTTT() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Terraforming");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void HHHHH() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Haniwa");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void TTTT() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Torrential Tribute");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() != MouseButton.PRIMARY) return;
        onClick.play();
        Utility.openNewMenu("/project/fxml/main_menu.fxml");
    }

    public void seeOtherCardsCreated(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() != MouseButton.PRIMARY) return;
        Utility.openNewMenu("/project/fxml/shop_other_player_cards.fxml");
    }

    public void COTH() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Call of The Haunted");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }

    public void TS() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Time Seal");
        if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
            new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(MainMenuController.getInstance().getLoggedInUserAssets()).getCoin()));
        showNumber();
    }
}