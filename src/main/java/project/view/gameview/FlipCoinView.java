package project.view.gameview;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import project.controller.playgame.DuelGameController;
import project.model.game.Duel;
import project.model.game.DuelPlayer;
import project.view.Utility;

import java.io.IOException;
import java.util.Objects;

public class FlipCoinView {
    public ImageView coinImage;
    public ImageView secondPlayerCoin;
    public Label secondPlayerNickname;
    public Label firstPlayerNickname;
    public ImageView firstPlayerCoin;
    public AnchorPane pane;
    public Button flipCoinButton;
    public VBox vBox;
    Image image5 = new Image(Objects.requireNonNull(getClass().getResource("/project/image/Coin/Silver_5.png")).toString());
    Image image6 = new Image(Objects.requireNonNull(getClass().getResource("/project/image/Coin/Silver_6.png")).toString());
    Image image7 = new Image(Objects.requireNonNull(getClass().getResource("/project/image/Coin/Silver_7.png")).toString());
    Image image8 = new Image(Objects.requireNonNull(getClass().getResource("/project/image/Coin/Silver_8.png")).toString());
    Image image9 = new Image(Objects.requireNonNull(getClass().getResource("/project/image/Coin/Silver_9.png")).toString());
    Image image10 = new Image(Objects.requireNonNull(getClass().getResource("/project/image/Coin/Silver_10.png")).toString());
    Image image27 = new Image(Objects.requireNonNull(getClass().getResource("/project/image/Coin/Silver_27.png")).toString());
    Image image28 = new Image(Objects.requireNonNull(getClass().getResource("/project/image/Coin/Silver_28.png")).toString());
    Image image29 = new Image(Objects.requireNonNull(getClass().getResource("/project/image/Coin/Silver_29.png")).toString());
    Image image30 = new Image(Objects.requireNonNull(getClass().getResource("/project/image/Coin/Silver_30.png")).toString());
    Image image21 = new Image(Objects.requireNonNull(getClass().getResource("/project/image/Coin/Silver_21.png")).toString());
    Image image22 = new Image(Objects.requireNonNull(getClass().getResource("/project/image/Coin/Silver_22.png")).toString());
    Image image23 = new Image(Objects.requireNonNull(getClass().getResource("/project/image/Coin/Silver_23.png")).toString());
    Image image24 = new Image(Objects.requireNonNull(getClass().getResource("/project/image/Coin/Silver_24.png")).toString());
    Image image25 = new Image(Objects.requireNonNull(getClass().getResource("/project/image/Coin/Silver_25.png")).toString());
    Image image26 = new Image(Objects.requireNonNull(getClass().getResource("/project/image/Coin/Silver_26.png")).toString());
    Image image1 = new Image(Objects.requireNonNull(getClass().getResource("/project/image/Coin/Silver_1.png")).toString());
    Image image2 = new Image(Objects.requireNonNull(getClass().getResource("/project/image/Coin/Silver_2.png")).toString());
    Image image3 = new Image(Objects.requireNonNull(getClass().getResource("/project/image/Coin/Silver_3.png")).toString());
    Image image4 = new Image(Objects.requireNonNull(getClass().getResource("/project/image/Coin/Silver_4.png")).toString());
    private Timeline flipCoinTimeLine;

    public void initialize() {
        Duel duel = DuelGameController.getInstance().getDuel();
        DuelPlayer player1 = duel.getPlayer1();
        firstPlayerNickname.setText(player1.getNickname());
        DuelPlayer player2 = duel.getPlayer2();
        secondPlayerNickname.setText(player2.getNickname());

//setting images
        Image imageOne = new Image(Objects.requireNonNull(getClass().getResource("/project/image/Coin/Silver_5.png")).toString());
        Image imageTwo = new Image(Objects.requireNonNull(getClass().getResource("/project/image/Coin/Silver_21.png")).toString());

        firstPlayerCoin.setImage(imageOne);
        secondPlayerCoin.setImage(imageTwo);
        coinImage.setImage(imageOne);
    }

    public void flipCoin(MouseEvent mouseEvent) {
        int x = (int) flipCoinButton.getLayoutX();
        int y = (int) flipCoinButton.getLayoutY();
        if (mouseEvent.getButton() != MouseButton.PRIMARY)
            return;
        String starterName;
        vBox.getChildren().remove(flipCoinButton);
        int randomNum = DuelGameController.getInstance().flipCoinAndSetStarter();
        if (randomNum == 3) {
            threeTimeline();
            starterName = firstPlayerNickname.getText();
        } else {
            fourTimeline();
            starterName = secondPlayerNickname.getText();
        }
        flipCoinTimeLine.setCycleCount(1);
        flipCoinTimeLine.play();
        flipCoinTimeLine.setOnFinished((EventHandler) -> {
            Label label = new Label(starterName + " starts Game!");
            label.setLayoutX(x);
            label.setLayoutY(y);
            label.getStylesheets().add(getClass().getResource("/project/CSS/flip_coin_style.css").toString());
            label.setId("label");
            vBox.getChildren().add(label);

            Button button = new Button("Start Game!");
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/project/fxml/round_view.fxml"));
                        Utility.openNewMenu(root, (Node) actionEvent.getSource());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            button.getStylesheets().add(getClass().getResource("/project/CSS/flip_coin_style.css").toString());
            button.setId("button");
            vBox.getChildren().add(button);
        });
    }

    private void threeTimeline() {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(50), (ActionEvent) -> coinImage.setImage(image5)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100), (ActionEvent) -> coinImage.setImage(image6)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(150), (ActionEvent) -> coinImage.setImage(image7)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(200), (ActionEvent) -> coinImage.setImage(image8)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(250), (ActionEvent) -> coinImage.setImage(image9)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(300), (ActionEvent) -> coinImage.setImage(image10)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(350), (ActionEvent) -> coinImage.setImage(image27)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(400), (ActionEvent) -> coinImage.setImage(image28)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(450), (ActionEvent) -> coinImage.setImage(image29)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(500), (ActionEvent) -> coinImage.setImage(image30)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(550), (ActionEvent) -> coinImage.setImage(image21)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(600), (ActionEvent) -> coinImage.setImage(image22)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(650), (ActionEvent) -> coinImage.setImage(image23)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(700), (ActionEvent) -> coinImage.setImage(image24)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(750), (ActionEvent) -> coinImage.setImage(image25)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(800), (ActionEvent) -> coinImage.setImage(image26)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(850), (ActionEvent) -> coinImage.setImage(image4)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(900), (ActionEvent) -> coinImage.setImage(image3)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(950), (ActionEvent) -> coinImage.setImage(image2)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000), (ActionEvent) -> coinImage.setImage(image1)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1100), (ActionEvent) -> coinImage.setImage(image5)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1200), (ActionEvent) -> coinImage.setImage(image6)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1300), (ActionEvent) -> coinImage.setImage(image7)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1400), (ActionEvent) -> coinImage.setImage(image8)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1500), (ActionEvent) -> coinImage.setImage(image9)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1600), (ActionEvent) -> coinImage.setImage(image10)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1700), (ActionEvent) -> coinImage.setImage(image27)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1800), (ActionEvent) -> coinImage.setImage(image28)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1900), (ActionEvent) -> coinImage.setImage(image29)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(2000), (ActionEvent) -> coinImage.setImage(image30)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(2100), (ActionEvent) -> coinImage.setImage(image21)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(2200), (ActionEvent) -> coinImage.setImage(image22)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(2300), (ActionEvent) -> coinImage.setImage(image23)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(2400), (ActionEvent) -> coinImage.setImage(image24)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(2500), (ActionEvent) -> coinImage.setImage(image25)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(2600), (ActionEvent) -> coinImage.setImage(image26)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(2700), (ActionEvent) -> coinImage.setImage(image4)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(2800), (ActionEvent) -> coinImage.setImage(image3)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(2900), (ActionEvent) -> coinImage.setImage(image2)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(3000), (ActionEvent) -> coinImage.setImage(image1)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(3150), (ActionEvent) -> coinImage.setImage(image5)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(3300), (ActionEvent) -> coinImage.setImage(image6)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(3450), (ActionEvent) -> coinImage.setImage(image7)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(3600), (ActionEvent) -> coinImage.setImage(image8)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(3750), (ActionEvent) -> coinImage.setImage(image9)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(3850), (ActionEvent) -> coinImage.setImage(image10)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(4000), (ActionEvent) -> coinImage.setImage(image27)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(4200), (ActionEvent) -> coinImage.setImage(image28)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(4400), (ActionEvent) -> coinImage.setImage(image29)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(4600), (ActionEvent) -> coinImage.setImage(image30)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(4800), (ActionEvent) -> coinImage.setImage(image21)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(5000), (ActionEvent) -> coinImage.setImage(image22)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(5300), (ActionEvent) -> coinImage.setImage(image23)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(5600), (ActionEvent) -> coinImage.setImage(image24)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(5900), (ActionEvent) -> coinImage.setImage(image25)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(6200), (ActionEvent) -> coinImage.setImage(image26)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(6550), (ActionEvent) -> coinImage.setImage(image4)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(6900), (ActionEvent) -> coinImage.setImage(image3)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(7250), (ActionEvent) -> coinImage.setImage(image2)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(7600), (ActionEvent) -> coinImage.setImage(image1)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(7800), (ActionEvent) -> coinImage.setImage(image5)));
        flipCoinTimeLine = timeline;
    }

    private void fourTimeline() {

        Timeline timeline = new Timeline();

        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(50), (ActionEvent) -> coinImage.setImage(image5)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100), (ActionEvent) -> coinImage.setImage(image6)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(150), (ActionEvent) -> coinImage.setImage(image7)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(200), (ActionEvent) -> coinImage.setImage(image8)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(250), (ActionEvent) -> coinImage.setImage(image9)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(300), (ActionEvent) -> coinImage.setImage(image10)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(350), (ActionEvent) -> coinImage.setImage(image27)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(400), (ActionEvent) -> coinImage.setImage(image28)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(450), (ActionEvent) -> coinImage.setImage(image29)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(500), (ActionEvent) -> coinImage.setImage(image30)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(550), (ActionEvent) -> coinImage.setImage(image21)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(600), (ActionEvent) -> coinImage.setImage(image22)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(650), (ActionEvent) -> coinImage.setImage(image23)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(700), (ActionEvent) -> coinImage.setImage(image24)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(750), (ActionEvent) -> coinImage.setImage(image25)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(800), (ActionEvent) -> coinImage.setImage(image26)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(850), (ActionEvent) -> coinImage.setImage(image4)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(900), (ActionEvent) -> coinImage.setImage(image3)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(950), (ActionEvent) -> coinImage.setImage(image2)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000), (ActionEvent) -> coinImage.setImage(image1)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1050), (ActionEvent) -> coinImage.setImage(image5)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1100), (ActionEvent) -> coinImage.setImage(image6)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1150), (ActionEvent) -> coinImage.setImage(image7)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1200), (ActionEvent) -> coinImage.setImage(image8)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1300), (ActionEvent) -> coinImage.setImage(image9)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1350), (ActionEvent) -> coinImage.setImage(image10)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1400), (ActionEvent) -> coinImage.setImage(image27)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1450), (ActionEvent) -> coinImage.setImage(image28)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1500), (ActionEvent) -> coinImage.setImage(image29)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1550), (ActionEvent) -> coinImage.setImage(image30)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1600), (ActionEvent) -> coinImage.setImage(image21)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1650), (ActionEvent) -> coinImage.setImage(image22)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1750), (ActionEvent) -> coinImage.setImage(image23)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1800), (ActionEvent) -> coinImage.setImage(image24)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1900), (ActionEvent) -> coinImage.setImage(image25)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(2000), (ActionEvent) -> coinImage.setImage(image26)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(2100), (ActionEvent) -> coinImage.setImage(image4)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(2200), (ActionEvent) -> coinImage.setImage(image3)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(2300), (ActionEvent) -> coinImage.setImage(image2)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(2400), (ActionEvent) -> coinImage.setImage(image1)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(2500), (ActionEvent) -> coinImage.setImage(image5)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(2600), (ActionEvent) -> coinImage.setImage(image6)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(2700), (ActionEvent) -> coinImage.setImage(image7)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(2800), (ActionEvent) -> coinImage.setImage(image8)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(2900), (ActionEvent) -> coinImage.setImage(image9)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(3000), (ActionEvent) -> coinImage.setImage(image10)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(3100), (ActionEvent) -> coinImage.setImage(image27)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(3200), (ActionEvent) -> coinImage.setImage(image28)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(3300), (ActionEvent) -> coinImage.setImage(image29)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(3400), (ActionEvent) -> coinImage.setImage(image30)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(3500), (ActionEvent) -> coinImage.setImage(image21)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(3600), (ActionEvent) -> coinImage.setImage(image22)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(3700), (ActionEvent) -> coinImage.setImage(image23)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(3800), (ActionEvent) -> coinImage.setImage(image24)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(3900), (ActionEvent) -> coinImage.setImage(image25)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(4000), (ActionEvent) -> coinImage.setImage(image26)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(4150), (ActionEvent) -> coinImage.setImage(image4)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(4300), (ActionEvent) -> coinImage.setImage(image3)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(4400), (ActionEvent) -> coinImage.setImage(image2)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(4500), (ActionEvent) -> coinImage.setImage(image1)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(4600), (ActionEvent) -> coinImage.setImage(image5)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(4750), (ActionEvent) -> coinImage.setImage(image6)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(4900), (ActionEvent) -> coinImage.setImage(image7)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(5050), (ActionEvent) -> coinImage.setImage(image8)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(5200), (ActionEvent) -> coinImage.setImage(image9)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(5350), (ActionEvent) -> coinImage.setImage(image10)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(5500), (ActionEvent) -> coinImage.setImage(image27)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(5650), (ActionEvent) -> coinImage.setImage(image28)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(5800), (ActionEvent) -> coinImage.setImage(image29)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(6000), (ActionEvent) -> coinImage.setImage(image30)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(6200), (ActionEvent) -> coinImage.setImage(image21)));

        flipCoinTimeLine = timeline;
    }


}
