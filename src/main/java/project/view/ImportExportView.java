package project.view;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import project.controller.ImportExportController;
import project.model.card.Card;
import project.model.card.CardsDatabase;
import project.view.messages.ImportExportMessages;
import project.view.messages.PopUpMessage;

import java.io.*;

public class ImportExportView {
    public ScrollPane scrollPane;
    public GridPane gridPane;
    Utility utility;
    public ImageView imageViewMain = new ImageView();
    ListView listView = new ListView();

    public void showImages() {
        scrollPane = new ScrollPane();
        gridPane = new GridPane();
        utility = new Utility();
        utility.addImages();

        Stage window = new Stage();
        window.initOwner(LoginMenuView.getStage());
        window.initStyle(StageStyle.UNDECORATED);
        window.initModality(Modality.WINDOW_MODAL);
        PopUpMessage.setStage(window);
        window.setWidth(1200);
        window.setHeight(675);

        Label title = new Label("Import/Export");
        title.setId("title");
        title.setLayoutX(525);
        title.setLayoutY(50);

//        scrollPane.setPannable(true);
//        scrollPane.setFitToWidth(true);
//        scrollPane.setFitToHeight(true);
//        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        // gridPane.setGridLinesVisible(true);


        int k = 0, j = 0;
        for (int i = 53; i <= CardsDatabase.getAllCards().size(); i++) {
            if (k >= 18) {
                j++;
                k = 0;
            }
            if (utility.getStringImageHashMap().containsKey(CardsDatabase.getAllCards().get(i - 1).getName())) {

                ImageView imageView = new ImageView(utility.getStringImageHashMap().get(CardsDatabase.getAllCards().get(i - 1).getName()));
                imageView.setId(CardsDatabase.getAllCards().get(i - 1).getName());
                imageView.setFitHeight(100);
                imageView.setPreserveRatio(true);

                Label label = new Label();
                label.setText(CardsDatabase.getAllCards().get(i - 1).toString());
                label.setWrapText(true);
                label.setStyle("-fx-text-fill: white; -fx-font-family: \"Matrix II Regular\";");

                Button gson = new Button("Json");
                gson.setId(CardsDatabase.getAllCards().get(i - 1).getName());
                gson.setOnMouseClicked(mouseEvent -> {
                    ImportExportMessages importExportMessages = ImportExportController.getInstance().exportCard(gson.getId());
                    new PopUpMessage(importExportMessages.getAlertType(), importExportMessages.getLabel());
                });
                gson.setStyle("-fx-background-color: #bb792d; -fx-background-radius: 10; -fx-text-fill: white; -fx-font-size: 14;");

                Button csv = new Button("CSV");
                csv.setId(CardsDatabase.getAllCards().get(i - 1).getName());
                csv.setOnMouseClicked(mouseEvent -> {
                    ImportExportMessages importExportMessages = ImportExportController.getInstance().SaveToCSV(gson.getId());
                    new PopUpMessage(importExportMessages.getAlertType(), importExportMessages.getLabel());
                });
                csv.setStyle("-fx-background-color: #bb792d; -fx-background-radius: 10; -fx-text-fill: white; -fx-font-size: 14;");

                VBox layout = new VBox(10);
                layout.setPadding(new Insets(20, 20, 20, 20));
                layout.getChildren().addAll(imageView, label, gson, csv);

                gridPane.add(layout, k, j);
                k++;
            }
        }

        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(20);
        gridPane.setHgap(20);

        scrollPane.setContent(gridPane);
        scrollPane.setStyle("-fx-background-color: #103188");
        scrollPane.setPrefWidth(1100);
        scrollPane.setPrefHeight(425);
        scrollPane.setMaxHeight(425);
        scrollPane.setLayoutY(100);
        scrollPane.setLayoutX(50);

        Button importButton = new Button("Import");
        importButton.setOnAction(this::chooseFile);
        importButton.setId("button");
        importButton.setLayoutY(550);
        importButton.setLayoutX(569);

        Button closeButton = closeButton(window);
        closeButton.setLayoutY(600);
        closeButton.setLayoutX(575);

        AnchorPane pane = new AnchorPane();
        pane.getChildren().addAll(title, scrollPane, importButton, closeButton);

        Scene scene = new Scene(pane);
        pane.getScene().setFill(Color.TRANSPARENT);
        window.initStyle(StageStyle.TRANSPARENT);
        window.setScene(scene);
        window.setResizable(false);
        window.getScene().getStylesheets().add(String.valueOf(getClass().getResource("/project/CSS/profile_menu_windows.css")));
        window.showAndWait();
    }

    private Button closeButton(Stage window) {
        Button closeButton = new Button();
        closeButton.setText("Close");
        closeButton.setOnAction(event -> {
            window.close();
            PopUpMessage.setStage(LoginMenuView.getStage());
        });
        closeButton.setCursor(Cursor.HAND);
        closeButton.setId("closeButton");
        return closeButton;
    }


    private void chooseFile(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Json Files", "*.json"));
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
            listView.getItems().add(selectedFile.getAbsoluteFile());
            String fileName = String.valueOf(listView.getItems());
            fileName = fileName.substring(1, fileName.length() - 1);

            System.out.println(fileName);

            ImportExportMessages importExportMessages = ImportExportController.getInstance().importCard(fileName);
            new PopUpMessage(importExportMessages.getAlertType(), importExportMessages.getLabel());

            listView.getItems().clear();
            for (Card card : CardsDatabase.getAllCards()) {
                System.out.println(card.getName());
            }
        } else {
            System.out.println("file is not valid");
        }
    }
}
