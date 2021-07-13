package project.view;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import project.controller.ControllerManager;
import project.controller.MainMenuController;
import project.model.Music;
import project.model.User;
import project.model.gui.Icon;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ScoreBoardView {
    private final AudioClip onClick = new AudioClip(Objects.requireNonNull(Utility.class.getResource("/project/soundEffects/CURSOR.wav")).toString());
    public VBox secondBox;
    public AnchorPane pane;
    public VBox firstBox;
    public ImageView playPauseMusicButton;
    public ImageView muteUnmuteButton;

    @FXML
    public void initialize() {
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
        String[] userdata = result.split("/");
        ArrayList<ScoreboardData> scoreboardData = new ArrayList<>();
        for (String s : userdata) {
            String[] parts = s.split(":");
            scoreboardData.add(new ScoreboardData(parts[0], Integer.parseInt(parts[1]), parts[2].equals("ture")));
        }
        Music.muteUnmuteButtons.add(muteUnmuteButton);
        if (!Music.isMediaPlayerPaused) playPauseMusicButton.setImage(Icon.PAUSE.getImage());
        else playPauseMusicButton.setImage(Icon.PLAY.getImage());
        if (Music.mediaPlayer.isMute()) muteUnmuteButton.setImage(Icon.MUTE.getImage());
        else muteUnmuteButton.setImage(Icon.UNMUTE.getImage());

        secondBox.setPadding(new Insets(10, 10, 10, 10));
        HBox[] hBoxes = new HBox[Math.min(10, scoreboardData.size())];
        Label[] ranks = new Label[Math.min(10, scoreboardData.size())];
        Label[] nicknames = new Label[Math.min(10, scoreboardData.size())];
        Label[] scores = new Label[Math.min(10, scoreboardData.size())];

        for (int i = 0; i < Math.min(10, scoreboardData.size()); i++) {
            hBoxes[i] = new HBox();
            ranks[i] = new Label();
            nicknames[i] = new Label();
            scores[i] = new Label();
        }

        int counter = 0;
        for (int i = 0; i < Math.min(10, userdata.length); i++) {
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
        onClick.play();
        Utility.openNewMenu("/project/fxml/main_menu.fxml");
    }
}