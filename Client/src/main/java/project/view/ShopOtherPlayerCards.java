package project.view;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import project.controller.MainMenuController;
import project.controller.ShopMenuController;
import project.model.Assets;
import project.model.card.Card;
import project.model.card.CardsDatabase;
import project.view.messages.PopUpMessage;
import project.view.messages.ShopMenuMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ShopOtherPlayerCards {
    public GridPane gridPane;
    public ScrollPane scrollPane;
    public Label Coin;
    Utility utility;
    private AudioClip onClick = new AudioClip(Objects.requireNonNull(Utility.class.getResource("/project/soundEffects/CURSOR.wav")).toString());

    @FXML
    public void initialize() {
        Coin.setText(String.valueOf(MainMenuController.getInstance().getLoggedInUserAssets().getCoin()));
        utility = new Utility();
        utility.addImages();

        showCards();
    }

    private void showCards() {
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        //gridPane.setGridLinesVisible(true);
        scrollPane.setPannable(true);
        ArrayList<Card> arrayList = CardsDatabase.getAllCards();

        System.out.println(arrayList.size());
        int i = 0, j = 0;
        for (int k = 52; k < arrayList.size(); k++) {
            if (i >= 12) {
                if (j == 2) break;
                i = 0;
                j++;
            }
            if (utility.getStringImageHashMap().containsKey(arrayList.get(k).getName())) {

                System.out.println(arrayList.get(k).getName() + "     SOPC");

                ImageView imageView = new ImageView(utility.getStringImageHashMap().get(arrayList.get(k).getName()));
                imageView.setId(arrayList.get(k).getName());
                imageView.setFitHeight(200);
                imageView.setPreserveRatio(true);

                Label everything = new Label();
                everything.setText(arrayList.get(k).toString());
                everything.setPrefHeight(300);
                everything.setPrefWidth(150);
                everything.setId("price");
                everything.setPadding(new Insets(10, 10, 10, 10));
                everything.setStyle("-fx-font-size: 15");

                Button buttonBuy = new Button("Buy");
                buttonBuy.setId(arrayList.get(k).getName());
                buttonBuy.setPrefHeight(30);
                buttonBuy.setPrefWidth(80);
                buttonBuy.setOnMouseClicked(mouseEvent -> {
                    onClick.play();
                    ShopMenuMessage shopMenuMessage = ShopMenuController.getInstance().buyCard(buttonBuy.getId());
                    if (shopMenuMessage != ShopMenuMessage.CARD_ADDED)
                        new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
                    Coin.setText(String.valueOf(MainMenuController.getInstance().getLoggedInUserAssets().getCoin()));
                });
                buttonBuy.setStyle("-fx-font-size: 15.0; -fx-background-color: #bb792d; -fx-background-radius: 10; -fx-text-fill: white; -fx-cursor: hand;");

                VBox layout = new VBox(10);
                layout.setPadding(new Insets(10, 10, 10, 10));
                layout.getChildren().addAll(imageView, everything, buttonBuy);
                layout.setAlignment(Pos.CENTER);

                gridPane.add(layout, i, j);
                i++;
            }
        }

        gridPane.setPadding(new Insets(0, 30, -700, 0));
        //gridPane.setPadding(new Insets(100, 10, -1200, 50));
        gridPane.setVgap(15);
//        gridPane.setHgap(100);
    }

    @FXML
    private void back(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() != MouseButton.PRIMARY) return;
        onClick.play();
        Utility.openNewMenu("/project/fxml/shop_menu.fxml");
    }
}
