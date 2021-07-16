package project.view;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import project.controller.ControllerManager;
import project.controller.MainMenuController;
import project.controller.ProfileMenuController;
import project.controller.ScoreboardController;
import project.model.Music;
import project.model.gui.Icon;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

public class ScoreBoardView {
    private final AudioClip onClick = new AudioClip(Objects.requireNonNull(Utility.class.getResource("/project/soundEffects/CURSOR.wav")).toString());
    private final Image green = new Image(getClass().getResource("/project/image/Icon/green.png").toString());
    private final Image red = new Image(getClass().getResource("/project/image/Icon/red.png").toString());
    public VBox secondBox;
    public AnchorPane pane;
    public VBox firstBox;
    public ImageView playPauseMusicButton;
    public ImageView muteUnmuteButton;

    @FXML
    public void initialize() {
        ScoreboardController.getInstance().setScoreBoardView(this);

        ScoreboardController.getInstance().initializeNetworkForScoreboard();
        DataInputStream dataInputStream = ControllerManager.getInstance().getDataInputStream();
        DataOutputStream dataOutputStream = ControllerManager.getInstance().getDataOutputStream();
        String result = "";
        try {
            dataOutputStream.writeUTF("scoreboard");
            dataOutputStream.flush();
            result = dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<ScoreboardData> arrayList = new Gson().fromJson(result, new TypeToken<ArrayList<ScoreboardData>>() {
        }.getType());
        ScoreboardData.setDataArrayList(arrayList);
        Music.muteUnmuteButtons.add(muteUnmuteButton);
        if (!Music.isMediaPlayerPaused) playPauseMusicButton.setImage(Icon.PAUSE.getImage());
        else playPauseMusicButton.setImage(Icon.PLAY.getImage());
        if (Music.mediaPlayer.isMute()) muteUnmuteButton.setImage(Icon.MUTE.getImage());
        else muteUnmuteButton.setImage(Icon.UNMUTE.getImage());

        secondBox.setPadding(new Insets(10, 10, 10, 10));
        showBoard();
    }

    public void showBoard() {
        secondBox.getChildren().clear();
        ArrayList<ScoreboardData> scoreboardData = ScoreboardData.getDataArrayList();
        HBox[] hBoxes = new HBox[Math.min(10, scoreboardData.size())];
        Label[] ranks = new Label[Math.min(10, scoreboardData.size())];
        Label[] nicknames = new Label[Math.min(10, scoreboardData.size())];
        Label[] scores = new Label[Math.min(10, scoreboardData.size())];
        Label[] isOnline = new Label[Math.min(10, scoreboardData.size())];
        for (int i = 0; i < Math.min(10, scoreboardData.size()); i++) {
            hBoxes[i] = new HBox();
            ranks[i] = new Label();
            nicknames[i] = new Label();
            scores[i] = new Label();
            isOnline[i] = new Label();
        }

        int counter = 0;
        for (int i = 0; i < Math.min(10, scoreboardData.size()); i++) {
            ImageView imageView = scoreboardData.get(i).isOnline() ? new ImageView(green) : new ImageView(red);
            imageView.setFitWidth(15);
            imageView.setFitHeight(15);
            if (i != 0 && scoreboardData.get(i).getScore() == scoreboardData.get(i - 1).getScore()) {
                ranks[i].setText(String.valueOf(i - counter));
                nicknames[i].setText(scoreboardData.get(i).getNickname());
                scores[i].setText(String.valueOf(scoreboardData.get(i).getScore()));
                counter++;
            } else {
                counter = 0;
                ranks[i].setText(String.valueOf(i + 1));
                nicknames[i].setText(scoreboardData.get(i).getNickname());
                scores[i].setText(String.valueOf(scoreboardData.get(i).getScore()));
            }
            isOnline[i].setGraphic(imageView);
        }
        HBox hBox1 = new HBox();
        Label rank1 = new Label("Rank");
        Label nickname1 = new Label("Nickname");
        Label score1 = new Label("Score");
        Label label = new Label("Online");
        hBox1.getChildren().add(rank1);
        hBox1.getChildren().add(nickname1);
        hBox1.getChildren().add(score1);
        hBox1.getChildren().add(label);
        hBox1.setId("row");
        hBox1.setSpacing(10);
        hBox1.setPadding(new Insets(5, 5, 5, 10));
        hBox1.setPrefHeight(30);
        hBox1.setPrefWidth(650);

        rank1.setPrefHeight(20);
        rank1.setPrefWidth(75);
        rank1.setId("rank");
        rank1.setStyle("-fx-text-fill: #ffd500");
        rank1.setAlignment(Pos.CENTER);
        rank1.setPadding(new Insets(10, 10, 10, 10));

        nickname1.setPrefHeight(20);
        nickname1.setPrefWidth(325);
        nickname1.setId("nickname");
        nickname1.setStyle("-fx-text-fill: #ffd500");
        nickname1.setPadding(new Insets(10, 10, 10, 10));

        score1.setPrefHeight(20);
        score1.setPrefWidth(100);
        score1.setId("score");
        score1.setStyle("-fx-text-fill: #ffd500");
        score1.setPadding(new Insets(10, 10, 10, 10));
        score1.setAlignment(Pos.CENTER);

        label.setPrefHeight(20);
        label.setPrefWidth(100);
        label.setId("Online");
        label.setStyle("-fx-text-fill: #ffd500");
        label.setPadding(new Insets(10, 10, 10, 10));
        label.setAlignment(Pos.CENTER);
        secondBox.getChildren().add(hBox1);
        for (HBox hBox : hBoxes) secondBox.getChildren().add(hBox);
        for (int i = 0; i < hBoxes.length; i++) {
            hBoxes[i].getChildren().add(ranks[i]);
            hBoxes[i].getChildren().add(nicknames[i]);
            hBoxes[i].getChildren().add(scores[i]);
            hBoxes[i].getChildren().add(isOnline[i]);
            hBoxes[i].setSpacing(10);
            hBoxes[i].setPadding(new Insets(5, 5, 5, 10));
            hBoxes[i].setId("row");
            hBoxes[i].setPrefHeight(30);
            hBoxes[i].setPrefWidth(700);

            ranks[i].setPrefHeight(20);
            ranks[i].setPrefWidth(75);
            ranks[i].setId("rank");
            ranks[i].setPadding(new Insets(10, 10, 10, 10));
            ranks[i].setAlignment(Pos.CENTER);

            nicknames[i].setPrefHeight(20);
            nicknames[i].setPrefWidth(325);
            nicknames[i].setId("nickname");
            nicknames[i].setPadding(new Insets(10, 10, 10, 10));

            scores[i].setPrefHeight(20);
            scores[i].setPrefWidth(100);
            scores[i].setId("score");
            scores[i].setPadding(new Insets(10, 10, 10, 10));
            scores[i].setAlignment(Pos.CENTER);

            isOnline[i].setPrefHeight(20);
            isOnline[i].setPrefWidth(100);
            isOnline[i].setId("Online");
            isOnline[i].setPadding(new Insets(10, 10, 10, 10));
            isOnline[i].setAlignment(Pos.CENTER);
        }
        for (int i = 0; i < nicknames.length; i++) {
            if (MainMenuController.getInstance().getLoggedInUser().getNickname().equals(nicknames[i].getText())) {
                hBoxes[i].setStyle("-fx-scale-x: 1.01; -fx-scale-y: 1.01; -fx-scale-z: 1.01; -fx-border-color: #ffd500; -fx-border-radius: 10;");
                return;
            }
        }
    }

    public void nextTrack(MouseEvent actionEvent) {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        onClick.play();
        Music.nextTrack(playPauseMusicButton, muteUnmuteButton);
    }

    public void playPauseMusic(MouseEvent actionEvent) {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        onClick.play();
        Music.playPauseMusic(playPauseMusicButton);
    }

    public void muteUnmuteMusic(MouseEvent actionEvent) {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        onClick.play();
        Music.muteUnmuteMusic(muteUnmuteButton);
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        if (mouseEvent.getButton() != MouseButton.PRIMARY) return;
        ScoreboardController.getInstance().closeScoreboard();
        onClick.play();
        Utility.openNewMenu("/project/fxml/main_menu.fxml");
    }
}