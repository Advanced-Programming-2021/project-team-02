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
import project.controller.MainMenuController;
import project.model.Assets;
import project.model.card.CardsDatabase;

import java.io.IOException;
import java.util.Objects;

public class ImportExportView {
    public ScrollPane scrollPane;
    public GridPane gridPane;
    Utility utility;


    @FXML
    public void initialize() {
        utility = new Utility();
        utility.addImages();
        showImages();
    }

    private void showImages() {

        scrollPane.setPannable(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setPrefWidth(1525);
        scrollPane.setPrefHeight(860);
        gridPane.setGridLinesVisible(true);


        int k = 0, j = 0;
        for (int i = 51; i <= CardsDatabase.getAllCards().size(); i++) {
            if (k >= 8) {
                j++;
                k = 0;
            }
            if (utility.getStringImageHashMap().containsKey(CardsDatabase.getAllCards().get(i - 1).getName())) {

                javafx.scene.image.ImageView imageView = new ImageView(utility.getStringImageHashMap().get(CardsDatabase.getAllCards().get(i - 1).getName()));
                imageView.setId(CardsDatabase.getAllCards().get(i - 1).getName());
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);

                Label label = new Label();
                label.setText(CardsDatabase.getAllCards().get(i - 1).toString());
                label.setWrapText(true);
                label.setFont(Font.font("Cambria", 10));
                label.setTextFill(Color.web("#0076a3"));
                label.setPrefWidth(100);
                label.setPrefHeight(200);

                Button Json = new Button("Json");
                Json.setId(CardsDatabase.getAllCards().get(i - 1).getName());
                Json.setOnMouseClicked(mouseEvent -> {

                });

                VBox layout = new VBox(10);
                layout.setPadding(new Insets(10, 10, 10, 40));
                layout.setPrefHeight(100);
                layout.setPrefWidth(100);
                layout.setEffect(new DropShadow());

                layout.getChildren().addAll(imageView, label, Json);

                gridPane.add(layout, k, j);
                k++;
            }
        }

        Button button = new Button("Back");
        button.setOnMouseClicked(actionEvent -> {
            try {
                back(actionEvent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        gridPane.add(button, 7 ,7);

        gridPane.setPadding(new Insets(10, 300, 10, -30));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
    }

    private void back(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() != MouseButton.PRIMARY) return;
        Utility.openNewMenu("/project/fxml/main_menu.fxml");
    }
}
