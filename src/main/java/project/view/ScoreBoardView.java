package project.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import project.controller.MainMenuController;
import project.model.Music;
import project.model.User;
import project.model.gui.Icon;
import project.view.messages.PopUpMessage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class ScoreBoardView {
    public VBox secondBox;
    public AnchorPane pane ;
    public VBox firstBox;
    public ImageView playPauseMusicButton;
    public ImageView muteUnmuteButton;


    @FXML
    public void initialize() {
        ArrayList<User> allUsers = User.sortAllUsers();
        secondBox.setPadding(new Insets(10, 10, 10, 10));
        HBox[] hBoxes = new HBox[Math.min(10, allUsers.size())];
        Label[] ranks = new Label[Math.min(10, allUsers.size())];
        Label[] nicknames = new Label[Math.min(10, allUsers.size())];
        Label[] scores = new Label[Math.min(10, allUsers.size())];

        for (int i = 0; i < Math.min(10, allUsers.size()); i++) {
            hBoxes[i] = new HBox();
            ranks[i] = new Label();
            nicknames[i] = new Label();
            scores[i] = new Label();
        }

        int counter = 0;
        for (int i = 0; i < Math.min(10, allUsers.size()); i++) {
            if (i != 0 && allUsers.get(i).getScore() == allUsers.get(i - 1).getScore()) {
                ranks[i].setText(String.valueOf(i - counter));
                nicknames[i].setText(allUsers.get(i).getNickname());
                scores[i].setText(String.valueOf(allUsers.get(i).getScore()));
                counter++;
            } else {
                counter = 0;
                ranks[i].setText(String.valueOf(i + 1));
                nicknames[i].setText(allUsers.get(i).getNickname());
                scores[i].setText(String.valueOf(allUsers.get(i).getScore()));
            }
        }
        HBox hBox1 = new HBox();
        Label rank1 = new Label("Rank");
        Label nickname1 = new Label("Nickname");
        Label score1 = new Label("Score");
        hBox1.getChildren().add(rank1);
        hBox1.getChildren().add(nickname1);
        hBox1.getChildren().add(score1);
        hBox1.setId("row");
        hBox1.setSpacing(10);
        hBox1.setPadding(new Insets(5, 5, 5, 10));
        hBox1.setPrefHeight(30);
        hBox1.setPrefWidth(650);

        rank1.setPrefHeight(20);
        rank1.setPrefWidth(80);
        rank1.setId("rank");
        rank1.setStyle("-fx-text-fill: #ffd500");
        rank1.setAlignment(Pos.CENTER);
        rank1.setPadding(new Insets(10, 10, 10, 10));

        nickname1.setPrefHeight(20);
        nickname1.setPrefWidth(425);
        nickname1.setId("nickname");
        nickname1.setStyle("-fx-text-fill: #ffd500");
        nickname1.setPadding(new Insets(10, 10, 10, 10));

        score1.setPrefHeight(20);
        score1.setPrefWidth(100);
        score1.setId("score");
        score1.setStyle("-fx-text-fill: #ffd500");
        score1.setPadding(new Insets(10, 10, 10, 10));
        score1.setAlignment(Pos.CENTER);

        secondBox.getChildren().add(hBox1);
        for (HBox hBox : hBoxes) secondBox.getChildren().add(hBox);
        for (int i = 0; i < hBoxes.length; i++) {
            hBoxes[i].getChildren().add(ranks[i]);
            hBoxes[i].getChildren().add(nicknames[i]);
            hBoxes[i].getChildren().add(scores[i]);
            hBoxes[i].setSpacing(10);
            hBoxes[i].setPadding(new Insets(5, 5, 5, 10));
            hBoxes[i].setId("row");
            hBoxes[i].setPrefHeight(30);
            hBoxes[i].setPrefWidth(650);

            ranks[i].setPrefHeight(20);
            ranks[i].setPrefWidth(80);
            ranks[i].setId("rank");
            ranks[i].setPadding(new Insets(10, 10, 10, 10));
            ranks[i].setAlignment(Pos.CENTER);

            nicknames[i].setPrefHeight(20);
            nicknames[i].setPrefWidth(425);
            nicknames[i].setId("nickname");
            nicknames[i].setPadding(new Insets(10, 10, 10, 10));

            scores[i].setPrefHeight(20);
            scores[i].setPrefWidth(100);
            scores[i].setId("score");
            scores[i].setPadding(new Insets(10, 10, 10, 10));
            scores[i].setAlignment(Pos.CENTER);
        }
        for (int i = 0; i < nicknames.length; i++) {
            if (MainMenuController.getInstance().getLoggedInUser().getNickname().equals(nicknames[i].getText())) {
                hBoxes[i].setStyle("-fx-scale-x: 1.01; -fx-scale-y: 1.01; -fx-scale-z: 1.01; -fx-border-color: #ffd500; -fx-border-radius: 10;");
                return;
            }
        }
    }

    public void back(MouseEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/project/fxml/main_menu.fxml")));
        Utility.openNewMenu(root, (Node) actionEvent.getSource());
    }

    public void playPauseMusic() {
        if (playPauseMusicButton.getImage().equals(Icon.PAUSE.getImage())) {
            playPauseMusicButton.setImage(Icon.PLAY.getImage());
            Music.mediaPlayer.pause();
        } else {
            playPauseMusicButton.setImage(Icon.PAUSE.getImage());
            Music.mediaPlayer.play();
        }
    }

    public void muteUnmuteMusic() {
        if (Music.mediaPlayer.isMute()) {
            muteUnmuteButton.setImage(Icon.UNMUTE.getImage());
            Music.mediaPlayer.setMute(false);
        } else {
            muteUnmuteButton.setImage(Icon.MUTE.getImage());
            Music.mediaPlayer.setMute(true);
        }
    }
}