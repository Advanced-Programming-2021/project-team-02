package project.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import project.controller.MainMenuController;
import project.controller.ShopMenuController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import javafx.scene.control.Label;
import project.model.Assets;
import project.model.card.Card;
import project.view.messages.PopUpMessage;
import project.view.messages.ShopMenuMessage;

public class ShopMenuView {
    private static ShopMenuController controller = null;
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
    public static final ArrayList<Button> buttons = new ArrayList<>();
    public static final ArrayList<ImageView> imageViews = new ArrayList<>();
    public HBox row1;
    public HBox row2;
    public HBox row3;
    public HBox row4;
    public HBox row5;
    public HBox row6;
    public HBox row7;
    public Label C52;
    public Label C51;

    HashMap<Card, Integer> allUserCards;

    @FXML
    public void initialize() throws IOException {
        controller = ShopMenuController.getInstance();
        Coin.setText(String.valueOf(Objects.requireNonNull(
                Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        allUserCards = Objects.requireNonNull(
                Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getAllUserCards();
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
        allUserCards = Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getAllUserCards();
        for (Card card : Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getAllUserCards().keySet()) {
            if (card.getName().equals("Alexandrite Dragon")) C1.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Axe Raider")) C2.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Baby dragon")) C3.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Battle OX")) C4.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Battle warrior")) C5.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Beast King Barbaros")) C6.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Bitron")) C7.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Blue-Eyes white dragon")) C8.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Crab Turtle")) C9.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Crawling dragon")) C10.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Curtain of the dark ones")) C11.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Dark Blade")) C12.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Dark magician")) C13.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Exploder Dragon")) C14.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Feral Imp")) C15.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Fireyarou")) C16.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Flame manipulator")) C17.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Gate Guardian")) C18.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Haniwa")) C19.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Hero of the east")) C20.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Horn Imp")) C21.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Leotron ")) C22.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Man-Eater Bug")) C23.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Silver Fang")) C24.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Skull Guardian")) C25.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Slot Machine")) C26.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Spiral Serpent")) C27.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("The Tricky")) C28.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Warrior Dai Grepher")) C29.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Wattaildragon")) C30.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Wattkid")) C31.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Yomi Ship")) C32.setText(String.valueOf(allUserCards.get(card)));

            if (card.getName().equals("Advanced Ritual Art")) C33.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Black Pendant")) C34.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Closed Forest")) C35.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Dark Hole")) C36.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Forest")) C37.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Harpie's Feather Duster")) C38.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Monster Reborn")) C39.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Pot of Greed")) C40.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Raigeki")) C41.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Sword of dark destruction")) C42.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Terraforming")) C43.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Umiiruka")) C44.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Yami")) C45.setText(String.valueOf(allUserCards.get(card)));

            if (card.getName().equals("Magic Cylinder")) C46.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Mirror Force")) C47.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Negate Attack")) C48.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Torrential Tribute")) C49.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Trap Hole")) C50.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Call of The Haunted")) C51.setText(String.valueOf(allUserCards.get(card)));
            if (card.getName().equals("Time Seal")) C52.setText(String.valueOf(allUserCards.get(card)));

        }
    }

    public void BEWD() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Blue-Eyes white dragon");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void AR() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Axe Raider");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void BD() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Baby dragon");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void BO() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Battle OX");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void BKB() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Beast King Barbaros");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void BW() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Battle warrior");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void CT() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Crab Turtle");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void CD() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Crawling dragon");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void DB() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Dark Blade");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void COTDO() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Curtain of the dark ones");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void AD() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Alexandrite Dragon");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void DM() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Dark magician");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void GG() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Gate Guardian");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void FM() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Flame manipulator");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void FI() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Feral Imp");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void ED() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Exploder Dragon");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void HOTE() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Hero of the east");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void HI() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Horn Imp");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void SF() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Silver Fang");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void MEB() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Man-Eater Bug");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void SG() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Skull Guardian");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void SS() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Spiral Serpent");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void WDG() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Warrior Dai Grepher");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void TT() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("The Tricky");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void SM() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Slot Machine");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void YS() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Yomi Ship");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void ARA() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Advanced Ritual Art");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void CF() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Closed Forest");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void Forest() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Forest");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void BP() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Black Pendant");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void DH() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Dark Hole");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void MR() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Monster Reborn");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void POG() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Pot of Greed");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void SODD() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Sword of dark destruction");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void HFD() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Harpie's Feather Duster");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void MF() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Mirror Force");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void TH() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Trap Hole");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void NA() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Negate Attack");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void MC() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Magic Cylinder");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void BBBBB() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Bitron");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void FFFFF() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Fireyarou");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void LLLLL() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Leotron ");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void WWWWW() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Wattaildragon");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void WWWW() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Wattkid");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void RRRRR() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Raigeki");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void YYYYY() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Yami");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void UUUUU() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Umiiruka");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void TTTTT() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Terraforming");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void HHHHH() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Haniwa");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void TTTT() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Torrential Tribute");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() != MouseButton.PRIMARY) return;
        Utility.openNewMenu("/project/fxml/main_menu.fxml");
    }

    public void seeOtherCardsCreated(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() != MouseButton.PRIMARY) return;
        Utility.openNewMenu("/project/fxml/shop_other_player_cards.fxml");
    }

    public void COTH() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Call of The Haunted");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }

    public void TS() {
        ShopMenuMessage shopMenuMessage = controller.buyCard("Time Seal");
        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
        Coin.setText(String.valueOf(Objects.requireNonNull(Assets.getAssetsByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).getCoin()));
        showNumber();
    }
}