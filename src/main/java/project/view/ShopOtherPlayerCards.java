package project.view;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import project.controller.ShopMenuController;
import project.model.card.Card;
import project.model.card.CardsDatabase;
import project.view.messages.PopUpMessage;
import project.view.messages.ShopMenuMessage;

import java.io.IOException;
import java.util.ArrayList;

public class ShopOtherPlayerCards {
    public GridPane gridPane;
    public ScrollPane scrollPane;
    Utility utility;

    @FXML
    public void initialize() {
        utility = new Utility();
        utility.addImages();

        showCards();
    }

    private void showCards() {
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPrefWidth(1000000000);
        scrollPane.setPrefWidth(1500);
        scrollPane.setPrefHeight(860);
        //gridPane.setGridLinesVisible(true);
        scrollPane.setContent(gridPane);
        scrollPane.setPannable(true);
        ArrayList<Card> arrayList = CardsDatabase.getAllCards();

        System.out.println(arrayList.size());
        int i = 0, j = 0;
        for (int k = 50; k < arrayList.size(); k++) {
            if (i >= 12) {
                if (j == 2) break;
                i = 0;
                j++;
            }
            if (utility.getStringImageHashMap().containsKey(arrayList.get(k).getName())) {

                System.out.println(arrayList.get(k).getName() + "     SOPC");

                ImageView imageView = new ImageView(utility.getStringImageHashMap().get(arrayList.get(k).getName()));
                imageView.setId(arrayList.get(k).getName());
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);

                Label everything = new Label();
                everything.setText(arrayList.get(k).toString());
                everything.setFont(Font.font("Cambria", 10));
                everything.setTextFill(Color.web("#0076a3"));
                everything.setPrefHeight(300);
                everything.setPrefWidth(100);

                Button buttonBuy = new Button("Buy");
                buttonBuy.setId(arrayList.get(k).getName());
                buttonBuy.setPrefHeight(30);
                buttonBuy.setPrefWidth(80);
                buttonBuy.setOnMouseClicked(mouseEvent -> {
                    ShopMenuMessage shopMenuMessage = ShopMenuController.getInstance().buyCard(buttonBuy.getId());
                    new PopUpMessage(shopMenuMessage.getAlertType(), shopMenuMessage.getLabel());
                });

                VBox layout = new VBox(10);
                layout.setPadding(new Insets(30, 10, 10, 10));
                layout.setPrefHeight(200);
                layout.setPrefWidth(100);
                layout.setEffect(new DropShadow());
                layout.getChildren().addAll(imageView, everything, buttonBuy);

                gridPane.add(layout, i, j);
                i++;
            }
        }

        Button backButton = new Button("Back");
        backButton.setPrefHeight(30);
        backButton.setPrefWidth(80);
        backButton.setOnMouseClicked(mouseEvent -> {
            try {
                back(mouseEvent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        gridPane.add(backButton, 0, j+2);
        gridPane.setPadding(new Insets(0, 30, -700, 0));
        //gridPane.setPadding(new Insets(100, 10, -1200, 50));
        gridPane.setVgap(15);
//        gridPane.setHgap(100);
    }

    private void back(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() != MouseButton.PRIMARY) return;
        Utility.openNewMenu("/project/fxml/shop_menu.fxml");
    }
}
