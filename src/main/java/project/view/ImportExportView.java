package project.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import project.controller.ImportExportController;
import project.controller.MainMenuController;
import project.model.Assets;
import project.model.card.Card;
import project.model.card.CardsDatabase;
import project.view.messages.ImportExportMessages;
import project.view.messages.PopUpMessage;

import java.io.*;
import java.security.cert.Extension;
import java.util.List;
import java.util.Objects;

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
        PopUpMessage.setStage(window);
        window.setWidth(1525);
        window.setHeight(860);

        scrollPane.setPannable(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
       // gridPane.setGridLinesVisible(true);


        int k = 0, j = 0;
        for (int i = 53; i <= CardsDatabase.getAllCards().size(); i++) {
            if (k >= 18) {
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

                Button gson = new Button("Json");
                gson.setId(CardsDatabase.getAllCards().get(i - 1).getName());
                gson.setOnMouseClicked(mouseEvent -> {
                    ImportExportMessages importExportMessages = ImportExportController.getInstance().exportCard(gson.getId());
                    new PopUpMessage(importExportMessages.getAlertType(), importExportMessages.getLabel());
                });

                Button csv = new Button("csv");
                csv.setId(CardsDatabase.getAllCards().get(i - 1).getName());
                csv.setOnMouseClicked(mouseEvent -> {
                    ImportExportMessages importExportMessages = ImportExportController.getInstance().SaveToCSV(gson.getId());
                    new PopUpMessage(importExportMessages.getAlertType(), importExportMessages.getLabel());
                });

                VBox layout = new VBox(10);
                layout.setPadding(new Insets(10, 10, -450, 40));
                layout.setPrefHeight(300);
                layout.setEffect(new DropShadow());
                layout.getChildren().addAll(imageView, label, gson, csv);

                gridPane.add(layout, k, j);
                k++;
            }
        }

        Button button = new Button("Exit");
        button.setOnMouseClicked(actionEvent -> {
            try {
                back(actionEvent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        gridPane.add(button, 0, j + 2);

        Button importButton = new Button("import");
        importButton.setOnAction(this::chooseFile);

        gridPane.add(importButton, 1, j + 2);



     //   gridPane.add(listView, 3, j + 2);

        gridPane.setPadding(new Insets(10, 300, 10, 10));
        gridPane.setVgap(0);
        gridPane.setHgap(15);

        scrollPane.setContent(gridPane);

        Scene scene = new Scene(scrollPane);
        window.setScene(scene);
        window.setResizable(true);
        window.showAndWait();
    }


    private void chooseFile(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Json and CSV Files", "*.json", "*.csv"));
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
            listView.getItems().add(selectedFile.getAbsoluteFile());
            String fileName = String.valueOf(listView.getItems());
            fileName = fileName.substring(1, fileName.length() - 1);

            System.out.println(fileName);

            if (fileName.contains(".json")) {
                ImportExportMessages importExportMessages = ImportExportController.getInstance().importCard(fileName);
                new PopUpMessage(importExportMessages.getAlertType(), importExportMessages.getLabel());
            } else if (fileName.contains(".csv")) {
                ImportExportMessages importExportMessages = ImportExportController.getInstance().readCSV(fileName);
                new PopUpMessage(importExportMessages.getAlertType(), importExportMessages.getLabel());
            }
            listView.getItems().clear();
            for (Card card:CardsDatabase.getAllCards()) {
                System.out.println(card.getName());
            }
        } else {
            System.out.println("file is not valid");
        }

    }

    private void back(MouseEvent mouseEvent) throws IOException {
        System.exit(0);
    }
}
