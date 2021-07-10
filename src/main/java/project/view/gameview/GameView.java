package project.view.gameview;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import project.controller.playgame.Phase;
import project.controller.playgame.RoundGameController;
import project.model.Deck;
import project.model.Music;
import project.model.card.Card;
import project.model.card.Monster;
import project.model.card.Spell;
import project.model.card.informationofcards.*;
import project.model.game.DuelPlayer;
import project.model.game.board.*;
import project.model.game.board.Cell;
import project.model.gui.Icon;
import project.view.LoginMenuView;
import project.view.Utility;
import project.view.input.Input;
import project.view.input.Regex;
import project.view.messages.*;
import project.view.messages.Error;

import java.io.IOException;
import java.io.PipedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class GameView {
    private final RoundGameController controller = RoundGameController.getInstance();
    private final Image backCardImage = new Image(Objects.requireNonNull(getClass().getResource("/project/image/GamePictures/Card Back.png")).toString());
    public Label currentPlayerLP;
    public Label currentPlayerNickname;
    public Label opponentPlayerNickname;
    public Label opponentPlayerLP;
    public GridPane currentPlayerMonsterZone;
    public GridPane currentPlayerSpellZone;
    public GridPane opponentPlayerMonsterZone;
    public GridPane opponentPlayerSpellZone;
    public ImageView opponentPlayerAvatar;
    public ImageView currentPlayerAvatar;
    public Pane currentPlayerDeckPane;
    public Pane opponentPlayerDeckPane;
    public Label opponentDeckLabel;
    public Label currentDeckLabel;
    public ImageView selectedCardImageView;
    public Label selectedCardDescriptionLabel;
    public GridPane opponentHand;
    public GridPane currentHand;
    public AnchorPane mainGamePane;
    public Label phaseLabel;
    public Pane currentPlayerFieldSpellPane;
    public Pane currentPlayerGraveYardPane;
    public Pane opponentPlayerFieldSpellPane;
    public Pane opponentGraveYardPane;
    public Button setButton;
    public Button summonOrActivateButton;
    public AnchorPane cardBoardPane;
    public Button changePositionButton;
    public Button attackButton;
    public Button nextPhaseButton;
    public ImageView playPauseMusicButton;
    public ImageView muteUnmuteButton;
    public ImageView settingButton;
    public ProgressBar currentPlayerLPBar;
    public ProgressBar opponentPlayerLPBar;
    private Utility utility;
    private AudioClip onClick = new AudioClip(Objects.requireNonNull(getClass().getResource("/project/soundEffects/CURSOR.wav")).toString());

    public void initialize() {
        utility = new Utility();
        utility.addImages();
        if (!Music.isMediaPlayerPaused) playPauseMusicButton.setImage(Icon.PAUSE.getImage());
        else playPauseMusicButton.setImage(Icon.PLAY.getImage());
        if (Music.mediaPlayer.isMute()) muteUnmuteButton.setImage(Icon.MUTE.getImage());
        else muteUnmuteButton.setImage(Icon.UNMUTE.getImage());

        currentPlayerLP.setText("LP : " + RoundGameController.getInstance().getOpponentPlayer().getLifePoint());
        currentPlayerNickname.setText(RoundGameController.getInstance().getCurrentPlayer().getNickname());
        opponentPlayerLP.setText("LP : " + RoundGameController.getInstance().getOpponentPlayer().getLifePoint());
        opponentPlayerNickname.setText(RoundGameController.getInstance().getOpponentPlayer().getNickname());

        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);

        createClip(currentPlayerAvatar, parameters);
        createClip(opponentPlayerAvatar, parameters);

        currentPlayerAvatar.setImage(new Image(RoundGameController.getInstance().getCurrentPlayer().getAvatarURL().toString()));
        opponentPlayerAvatar.setImage(new Image(RoundGameController.getInstance().getOpponentPlayer().getAvatarURL().toString()));
        selectedCardImageView.setImage(backCardImage);
        selectedCardDescriptionLabel.setText("No card selected");
        RoundGameController.getInstance().setView(this);
        currentPlayerDeckPane.setCursor(Cursor.HAND);
        currentPlayerDeckPane.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.PRIMARY)
                return;
            selectedCardImageView.setImage(backCardImage);
            selectedCardDescriptionLabel.setText("Deck!");
        });
        currentPlayerGraveYardPane.setCursor(Cursor.HAND);
        currentPlayerGraveYardPane.setOnMouseClicked(mouseEvent -> showCurrentGraveYardToSee(true));
        opponentGraveYardPane.setCursor(Cursor.HAND);
        opponentGraveYardPane.setOnMouseClicked(mouseEvent -> showCurrentGraveYardToSee(false));
        currentPlayerDeckPane.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.PRIMARY)
                return;
            RoundGameController.getInstance().drawCardFromDeck();
        });
        phaseLabel.setText("Draw");
        currentDeckLabel.setCursor(Cursor.HAND);
        updateCurrentDeckLabel();
        showButtonBasedOnPhase(Phase.DRAW_PHASE);
        selectedCardDescriptionLabel.setWrapText(true);
        currentPlayerFieldSpellPane.getChildren().clear();
        ImageView baseImage = new ImageView();
        baseImage.setImage(new Image(Objects.requireNonNull(getClass().getResource("/project/image/GamePictures/field.png")).toString()));
        baseImage.setFitWidth(103.0);
        baseImage.setFitHeight(126.0); //fitHeight="126.0" fitWidth="103.0"
        opponentPlayerFieldSpellPane.getChildren().clear();
        ImageView baseImage2 = new ImageView();
        baseImage2.setImage(new Image(Objects.requireNonNull(getClass().getResource("/project/image/GamePictures/field.png")).toString()));
        baseImage2.setFitWidth(103.0);
        baseImage2.setFitHeight(126.0); //fitHeight="126.0" fitWidth="103.0"
        currentPlayerFieldSpellPane.getChildren().add(baseImage);
        opponentPlayerFieldSpellPane.getChildren().add(baseImage2);
        opponentPlayerFieldSpellPane.setRotate(180);
        opponentPlayerLPBar.setProgress(1);
        currentPlayerLPBar.setProgress(1);
    }

    private void createClip(ImageView imageView, SnapshotParameters parameters) {
        Rectangle clip = new Rectangle();
        clip.setWidth(135);
        clip.setHeight(135);
        clip.setArcHeight(20);
        clip.setArcWidth(20);
        imageView.setClip(clip);
        WritableImage wImage = imageView.snapshot(parameters, null);
        imageView.setImage(wImage);
    }

    public Image getCardImageByName(String cardName) {

        HashMap<String, Image> stringImageHashMap = utility.getStringImageHashMap();
        for (String name : stringImageHashMap.keySet()) {
            if (name.equals(cardName)) {
                return stringImageHashMap.get(name);
            }
        }
        return null;
    }

    public void startGameAndLoadHand() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Ready to Go?");
        alert.initOwner(LoginMenuView.getStage());
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initStyle(StageStyle.TRANSPARENT);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setHeaderText(null);
        dialogPane.setGraphic(null);
        dialogPane.setStyle("-fx-border-radius: 10; -fx-border-color: #bb792d; -fx-border-width: 7; -fx-background-radius: 14; -fx-font-family: \"Matrix II Regular\"; -fx-background-color: #103188;");
        dialogPane.lookup(".content.label").setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-line-spacing: 5px;");
        dialogPane.getScene().setFill(Color.TRANSPARENT);
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Go!");
        ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
        buttonBar.getButtons().forEach(b -> b.setStyle("-fx-background-radius: 10; -fx-background-color: #bb792d; -fx-font-size: 16; -fx-text-fill: white;"));
        buttonBar.getButtons().forEach(b -> b.setCursor(Cursor.HAND));
        AudioClip audioClip = new AudioClip(Objects.requireNonNull(getClass().getResource("/project/soundEffects/its-time-to-duel.wav")).toString());
        audioClip.play();
        blur();
        alert.showAndWait();
        setHandBasedOnTurn();
        mainGamePane.setEffect(null);
        opponentDeckLabel.setText(String.valueOf(RoundGameController.getInstance().getOpponentPlayer().getPlayDeck().getMainCards().size()));
        currentDeckLabel.setText(String.valueOf(RoundGameController.getInstance().getCurrentPlayer().getPlayDeck().getMainCards().size()));
    }

    public TranslateTransition drawCardFromDeckAnimation(String cardName, boolean isCurrent) {
        Pane deckPane = isCurrent ? currentPlayerDeckPane : opponentPlayerDeckPane;
        GridPane handPane = isCurrent ? currentHand : opponentHand;
        Point2D deck = deckPane.localToScene(new Point2D(0, 0));
        ImageView cardImageView = new ImageView();
        cardImageView.setCursor(Cursor.HAND);

        cardImageView.setFitHeight(160);
        cardImageView.setFitWidth(116);
        cardImageView.setImage(getCardImageByName(cardName));

        //Translate transition :
        TranslateTransition translateTransition = new TranslateTransition();
        int addressOfAddInGrid = RoundGameController.getInstance().getCurrentPlayerHand().size() - 1;//zero based!
        cardImageView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.PRIMARY)
                return;
            selectedCardImageView.setImage(cardImageView.getImage());
            selectedCardDescriptionLabel.setText(Objects.requireNonNull(Card.getCardByName(cardName)).toString());


        });

        Node node = getNodeInGridPane(handPane, 0, addressOfAddInGrid - 1);
        Point2D nodePoint;
        if (node != null) {
            nodePoint = node.localToScene(new Point2D(0, 0));
        } else {
            nodePoint = new Point2D(306.0, 694.0);
        }
        translateTransition.setNode(cardImageView);
        translateTransition.setFromX(deck.getX());
        translateTransition.setFromY(deck.getY());
        translateTransition.setToX(nodePoint.getX() + 120);
        translateTransition.setToY(nodePoint.getY());
        translateTransition.setDuration(Duration.millis(500));
        translateTransition.setOnFinished(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                translateTransition.setNode(null);
                ImageView imageView = new ImageView(getCardImageByName(cardName));
                imageView.setFitHeight(160);
                imageView.setFitWidth(116);
                imageView.setCursor(Cursor.HAND);
                imageView.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton() != MouseButton.PRIMARY)
                        return;
                    selectedCardImageView.setImage(cardImageView.getImage());
                    selectedCardDescriptionLabel.setText(Objects.requireNonNull(Card.getCardByName(cardName)).toString());
                    RoundGameController.getInstance().selectCardInHand(addressOfAddInGrid + 1);
                });
                reloadCurrentAndOpponentMonsterZone(); //TODO not sure of here!
                handPane.add(imageView, addressOfAddInGrid, 0);
                mainGamePane.getChildren().remove(cardImageView);
            }
        });


        mainGamePane.getChildren().add(cardImageView);

        updateCurrentDeckLabel();
        return translateTransition;
    }

    private synchronized Node getNodeInGridPane(GridPane gridPane, int row, int column) {

        for (Node child : gridPane.getChildren()) {
            if (child != null)
                if (GridPane.getRowIndex(child) == row && GridPane.getColumnIndex(child) == column)
                    return child;
        }
        return null;
    }

    private void updateCurrentDeckLabel() {
        currentDeckLabel.setText(String.valueOf(RoundGameController.getInstance().getCurrentPlayer().getPlayDeck().getMainCards().size()));
    }

    public void runGameWithAi() {
        String command = "";
        while (true) {
            if (controller.isFinishedGame() || controller.isFinishedRound()) {
                return;
            }
            if (controller.getCurrentPlayer().getNickname().equals("ai")) {
                controller.aiTurn();
            } else {
                command = Input.getInput();
                //commandRecognition(command);
            }

        }
    }


    public void showError(Error error) {
        System.out.println(error.getValue());
    }

    public void showSuccessMessage(SuccessMessage message) {
        System.out.println(message.getValue());
    }

    public void showSuccessMessageWithAString(SuccessMessage message, String string) {
        if (message.equals(SuccessMessage.PLAYERS_TURN))
            System.out.printf(SuccessMessage.PLAYERS_TURN.getValue(), string);
        else if (message.equals(SuccessMessage.CARD_ADDED_TO_THE_HAND))
            System.out.printf(SuccessMessage.CARD_ADDED_TO_THE_HAND.getValue(), string);
        else if (message.equals(SuccessMessage.SHOW_TURN_WHEN_OPPONENT_WANTS_ACTIVE_TRAP_OR_SPELL_OR_MONSTER))
            System.out.printf(SuccessMessage.SHOW_TURN_WHEN_OPPONENT_WANTS_ACTIVE_TRAP_OR_SPELL_OR_MONSTER.getValue(), string);
        else if (message.equals(SuccessMessage.DH_CARD_BECOMES_DO))
            System.out.printf(SuccessMessage.DH_CARD_BECOMES_DO.getValue(), string);
        else if (message.equals(SuccessMessage.GAME_FINISHED))
            System.out.printf(SuccessMessage.GAME_FINISHED.getValue(), string);
        else if (message.equals(SuccessMessage.ROUND_FINISHED))
            System.out.printf(SuccessMessage.ROUND_FINISHED.getValue(), string);
        else if (message == SuccessMessage.WIN_MESSAGE_ROUND_MATCH)
            System.out.printf(SuccessMessage.WIN_MESSAGE_ROUND_MATCH.getValue(), string);
    }

    public void showSuccessMessageWithAnInteger(SuccessMessage message, int number) {
        if (message.equals(SuccessMessage.OPPONENT_RECEIVE_DAMAGE_AFTER_ATTACK))
            System.out.printf(SuccessMessage.OPPONENT_RECEIVE_DAMAGE_AFTER_ATTACK.getValue(), number);
        else if (message.equals(SuccessMessage.DAMAGE_TO_CURRENT_PLAYER_AFTER_ATTACK_TI_HIGHER_DEFENSIVE_DO_OR_DH_MONSTER))
            System.out.printf(SuccessMessage.DAMAGE_TO_CURRENT_PLAYER_AFTER_ATTACK_TI_HIGHER_DEFENSIVE_DO_OR_DH_MONSTER.getValue(), number);
        else if (message.equals(SuccessMessage.CURRENT_PLAYER_RECEIVE_DAMAGE_AFTER_ATTACK))
            System.out.printf(SuccessMessage.CURRENT_PLAYER_RECEIVE_DAMAGE_AFTER_ATTACK.getValue(), number);
        else if (message.equals(SuccessMessage.OPPONENT_RECEIVE_DAMAGE_AFTER_DIRECT_ATTACK))
            System.out.printf(SuccessMessage.OPPONENT_RECEIVE_DAMAGE_AFTER_DIRECT_ATTACK.getValue(), number);
    }

    public void showBoard() {
        if (controller.getCurrentPlayer().equals(controller.getFirstPlayer())) {
            System.out.println(controller.getSecondPlayer().getNickname() + ":" + controller.getSecondPlayer().getLifePoint());
            if (controller.getSecondPlayerHand().size() <= 6) {
                for (int i = 0; i < 6 - controller.getSecondPlayerHand().size(); i++) System.out.print("\t");
                for (int i = 0; i < controller.getSecondPlayerHand().size(); i++) System.out.print("c\t");
            } else for (int i = 0; i < controller.getSecondPlayerHand().size(); i++) System.out.print("c\t");
            System.out.print("\n");
            System.out.println(controller.getSecondPlayer().getPlayDeck().getMainCards().size());
            System.out.println(
                    "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(4)) +
                            "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(2)) +
                            "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(1)) +
                            "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(3)) +
                            "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(5)));
            System.out.println(
                    "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(4)) +
                            "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(2)) +
                            "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(1)) +
                            "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(3)) +
                            "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(5)));
            System.out.println(controller.getSecondPlayer().getPlayerBoard().returnGraveYard().getGraveYardCards().size()
                    + "\t\t\t\t\t\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().getFieldZone().getFieldCell()));
            System.out.println("\n--------------------------\n");
            System.out.println(getStatusForCell(controller.getFirstPlayer().getPlayerBoard().getFieldZone().getFieldCell())
                    + "\t\t\t\t\t\t" + controller.getFirstPlayer().getPlayerBoard().returnGraveYard().getGraveYardCards().size());
            System.out.println(
                    "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(5)) +
                            "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(3)) +
                            "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(1)) +
                            "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(2)) +
                            "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(4)));
            System.out.println(
                    "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(5)) +
                            "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(3)) +
                            "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(1)) +
                            "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(2)) +
                            "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(4)));
            System.out.println("\t\t\t\t\t\t" + controller.getFirstPlayer().getPlayDeck().getMainCards().size());
            for (int i = 0; i < controller.getFirstPlayerHand().size(); i++) System.out.print("c\t");
            System.out.print("\n");
            System.out.println(controller.getFirstPlayer().getNickname() + ":" + controller.getFirstPlayer().getLifePoint());
        } else {
            System.out.println(controller.getFirstPlayer().getNickname() + ":" + controller.getFirstPlayer().getLifePoint());
            if (controller.getFirstPlayerHand().size() <= 6) {
                for (int i = 0; i < 6 - controller.getFirstPlayerHand().size(); i++) System.out.print("\t");
                for (int i = 0; i < controller.getFirstPlayerHand().size(); i++) System.out.print("c\t");
            } else for (int i = 0; i < controller.getFirstPlayerHand().size(); i++) System.out.print("c\t");
            System.out.print("\n");
            System.out.println(controller.getFirstPlayer().getPlayDeck().getMainCards().size());
            System.out.println(
                    "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(4)) +
                            "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(2)) +
                            "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(1)) +
                            "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(3)) +
                            "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(5)));
            System.out.println(
                    "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(4)) +
                            "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(2)) +
                            "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(1)) +
                            "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(3)) +
                            "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(5)));
            System.out.println(controller.getFirstPlayer().getPlayerBoard().returnGraveYard().getGraveYardCards().size()
                    + "\t\t\t\t\t\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().getFieldZone().getFieldCell()));
            System.out.println("\n--------------------------\n");
            System.out.println(getStatusForCell(controller.getSecondPlayer().getPlayerBoard().getFieldZone().getFieldCell())
                    + "\t\t\t\t\t\t" + controller.getSecondPlayer().getPlayerBoard().returnGraveYard().getGraveYardCards().size());
            System.out.println(
                    "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(5)) +
                            "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(3)) +
                            "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(1)) +
                            "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(2)) +
                            "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(4)));
            System.out.println(
                    "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(5)) +
                            "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(3)) +
                            "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(1)) +
                            "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(2)) +
                            "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(4)));
            System.out.println("\t\t\t\t\t\t" + controller.getSecondPlayer().getPlayDeck().getMainCards().size());
            for (int i = 0; i < controller.getSecondPlayerHand().size(); i++) System.out.print("c\t");
            System.out.println();
            System.out.println(controller.getSecondPlayer().getNickname() + ":" + controller.getSecondPlayer().getLifePoint());
        }
    }

    public String getStatusForCell(Cell cell) {
        if (cell.getCellStatus().getLabel().equals("E")) return "E";
        if (cell.getCellStatus().getLabel().equals("O")) return "O";
        if (cell.getCellStatus().getLabel().equals("H")) return "H";
        if (cell.getCellStatus().getLabel().equals("DH")) return "DH";
        if (cell.getCellStatus().getLabel().equals("DO")) return "DO";
        if (cell.getCellStatus().getLabel().equals("OH")) return "OH";
        if (cell.getCellStatus().getLabel().equals("OO")) return "OO";
        if (cell.getCellStatus().getLabel().equals("HAND")) return "HAND";
        return "";
    }


    public void showCurrentGraveYard(boolean userAskedForGraveYard) {
        int counter = 1;
        if (controller.getCurrentPlayer().getPlayerBoard().isGraveYardEmpty())
            showError(Error.EMPTY_GRAVEYARD);
        else {
            for (Card card : controller.getCurrentPlayer().getPlayerBoard().returnGraveYard().getGraveYardCards()) {
                System.out.print(counter + ". " + card.getName() + ":" + card.getDescription());
                if (card.getCardType() == CardType.MONSTER) {
                    System.out.print("\n Powers : " + ((Monster) card).getAttackPower() + "," + ((Monster) card).getDefensePower());
                }
                System.out.println();
                System.out.println();
                counter++;
            }
        }
        if (userAskedForGraveYard)
            while (!Input.getInput().equals("back")) {
                System.out.println(Error.INVALID_COMMAND.getValue());
            }
    }

    public ArrayList<Integer> getTributeAddress(int numberOfTributeNeeded) {
        ArrayList<Integer> tributeAddress = new ArrayList<>();
        Stage window = new Stage();
        window.initOwner(LoginMenuView.getStage());
        window.initStyle(StageStyle.UNDECORATED);
        GamePopUpMessage.setStage(window);

        Label title = new Label("Choose Tribute Cards");
        title.setId("title");

        Button doneButton = new Button();
        doneButton.setText("OK");
        doneButton.setStyle("-fx-background-color: #bb792d;\n" +
                "-fx-background-radius: 10;\n" +
                "-fx-text-fill: white;\n" +
                "-fx-font-size: 16;");
        doneButton.setOnAction(event -> {
            onClick.play();
            if (tributeAddress.size() == numberOfTributeNeeded) {
                window.close();
            } else new GamePopUpMessage(Alert.AlertType.ERROR, "Please select!!!");
        });

        GridPane gridPane = getOnlyMonsterZoneGridPaneToSelect(tributeAddress, numberOfTributeNeeded, RoundGameController.getInstance().getCurrentPlayer().getPlayerBoard().returnMonsterZone());

        Button resetChoicesButton = new Button();
        resetChoicesButton.setText("Reset");
        resetChoicesButton.setCursor(Cursor.HAND);
        resetChoicesButton.setStyle("-fx-background-color: #bb792d;\n" +
                "-fx-background-radius: 10;\n" +
                "-fx-text-fill: white;\n" +
                "-fx-font-size: 16;");
        resetChoicesButton.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.PRIMARY)
                return;
            onClick.play();
            for (Integer address : tributeAddress) {
                ((Pane) Objects.requireNonNull(getNodeInGridPane(gridPane, 0, address - 1))).setBorder(null);
            }
            tributeAddress.clear();

        });
        Label label = new Label("Tribute Summon: please choose " + numberOfTributeNeeded + " of your monsters to tribute");
        label.setStyle("-fx-text-fill: white");
        HBox buttonBox = new HBox(doneButton, resetChoicesButton);
        VBox mainBox = new VBox(label, gridPane, buttonBox);
        buttonBox.setSpacing(20);
        buttonBox.setAlignment(Pos.CENTER);
        doneButton.setCursor(Cursor.HAND);
        mainBox.setSpacing(10);
        mainBox.setStyle("-fx-background-color: #103188;");
        mainBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(mainBox, 700, 250);
        mainBox.getScene().setFill(Color.TRANSPARENT);
        window.initStyle(StageStyle.TRANSPARENT);
        window.setScene(scene);
        window.setResizable(false);
        window.setX(500);
        window.setY(300);
        window.initModality(Modality.WINDOW_MODAL);
        window.getScene().getStylesheets().add(String.valueOf(getClass().getResource("/project/CSS/in_game_window.css")));
        window.showAndWait();
        return tributeAddress;
    }

    private GridPane getOnlyMonsterZoneGridPaneToSelect(ArrayList<Integer> listOfSelected, int size, MonsterZone monsterZone) {
        GridPane gridPane = new GridPane();
        ArrayList<Card> inZoneCards = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Cell cell = monsterZone.getCellWithAddress(i);
            if (cell.getCellStatus() != CellStatus.EMPTY) {
                inZoneCards.add(cell.getCardInCell());
            }
        }
        int i = 0;
        for (Card zoneCard : inZoneCards) {
            Pane pane = new Pane();
            pane.setPrefHeight(135);
            pane.setPrefWidth(99);
            gridPane.add(pane, i, 0);
            ImageView cardImageView = new ImageView(getCardImageByName(zoneCard.getName()));
            cardImageView.setFitHeight(130);
            cardImageView.setFitWidth(94);

            pane.getChildren().add(cardImageView);
            cardImageView.setLayoutX(cardImageView.getLayoutX() + 3);
            cardImageView.setLayoutY(cardImageView.getLayoutY() + 3);
            int finalI = i;
            pane.setCursor(Cursor.HAND);
            pane.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() != MouseButton.PRIMARY)
                    return;
                onClick.play();
                if (listOfSelected.size() != size) {
                    listOfSelected.add(finalI + 1);
                    pane.setBorder(new Border(new BorderStroke(Color.YELLOW,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THICK)));
                }
            });
            i++;
        }
        gridPane.setHgap(5);
        return gridPane;
    }


    public ArrayList<Integer> getMonstersAddressesToBringRitual(int sumOfLevel) {
        ArrayList<Integer> tributeAddress = new ArrayList<>();
        Stage window = new Stage();
        window.initOwner(LoginMenuView.getStage());
        window.initStyle(StageStyle.UNDECORATED);
        GamePopUpMessage.setStage(window);

        Button doneButton = new Button();
        doneButton.setText("OK");
        doneButton.setStyle("-fx-background-color: #bb792d;\n" +
                "-fx-background-radius: 10;\n" +
                "-fx-text-fill: white;\n" +
                "-fx-font-size: 16;");
        doneButton.setOnAction(event -> {
            onClick.play();
            if (tributeAddress.size() != 0) {
                var ref = new Object() {
                    int sum = 0;
                };
                MonsterZone monsterZone = RoundGameController.getInstance().getCurrentPlayer().getPlayerBoard().returnMonsterZone();
                tributeAddress.forEach(integer -> ref.sum += ((Monster) monsterZone.getCellWithAddress(integer).getCardInCell()).getLevel());
                if (ref.sum == sumOfLevel) {
                    window.close();
                } else {
                    System.out.println("sum needed :" + sumOfLevel + " ref : " + ref.sum);
                    GamePopUpMessage.setStage(window);
                    new GamePopUpMessage(Alert.AlertType.ERROR, "Invalid selection");
                }
            } else {
                GamePopUpMessage.setStage(window);
                new GamePopUpMessage(Alert.AlertType.ERROR, "Please select!!!");
            }
        });

        GridPane gridPane = getOnlyMonsterZoneGridPaneToSelect(tributeAddress, RoundGameController.getInstance().getCurrentPlayerHand().size(), RoundGameController.getInstance().getCurrentPlayer().getPlayerBoard().returnMonsterZone());
        Button resetChoicesButton = new Button();
        resetChoicesButton.setText("Reset");
        resetChoicesButton.setCursor(Cursor.HAND);
        resetChoicesButton.setStyle("-fx-background-color: #bb792d;\n" +
                "-fx-background-radius: 10;\n" +
                "-fx-text-fill: white;\n" +
                "-fx-font-size: 16;");
        resetChoicesButton.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.PRIMARY)
                return;
            onClick.play();
            for (Integer address : tributeAddress) {
                ((Pane) Objects.requireNonNull(getNodeInGridPane(gridPane, 0, address - 1))).setBorder(null);
            }
            tributeAddress.clear();
        });
        Label label = new Label("Tribute Summon: please choose some of your monsters that makes " + sumOfLevel + " to tribute");
        label.setStyle("-fx-text-fill: white");
        HBox buttonBox = new HBox(doneButton, resetChoicesButton);
        VBox mainBox = new VBox(label, gridPane, buttonBox);
        buttonBox.setSpacing(20);
        buttonBox.setAlignment(Pos.CENTER);
        doneButton.setCursor(Cursor.HAND);
        mainBox.setSpacing(10);
        mainBox.setStyle("-fx-background-color: #103188;");
        mainBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(mainBox, 700, 250);
        mainBox.getScene().setFill(Color.TRANSPARENT);
        window.initStyle(StageStyle.TRANSPARENT);
        window.setScene(scene);
        window.setResizable(false);
        window.setX(500);
        window.setY(300);
        window.initModality(Modality.WINDOW_MODAL);
        window.getScene().getStylesheets().add(String.valueOf(getClass().getResource("/project/CSS/in_game_window.css")));

        window.showAndWait();
        return tributeAddress;
    }


    public int swordOfDarkDestruction() {
        ArrayList<Integer> equipAddress = new ArrayList<>();
        Stage window = new Stage();
        window.initOwner(LoginMenuView.getStage());
        window.initStyle(StageStyle.UNDECORATED);
        GamePopUpMessage.setStage(window);

        Button doneButton = new Button();
        doneButton.setText("OK");
        doneButton.setStyle("-fx-background-color: #bb792d;\n" +
                "-fx-background-radius: 10;\n" +
                "-fx-text-fill: white;\n" +
                "-fx-font-size: 16;");
        doneButton.setOnAction(event -> {
            onClick.play();
            if (equipAddress.size() == 1) {
                MonsterZone monsterZone = RoundGameController.getInstance().getCurrentPlayer().getPlayerBoard().returnMonsterZone();
                Monster monster = (Monster) monsterZone.getCellWithAddress(equipAddress.get(0)).getCardInCell();
                if (monster.getMonsterType() == MonsterType.FIEND || monster.getMonsterType() == MonsterType.SPELLCASTER) {
                    window.close();
                } else {
                    GamePopUpMessage.setStage(window);
                    new GamePopUpMessage(Alert.AlertType.ERROR, "Cards Type is not valid for sword of dark destruction");
                }
            } else {
                GamePopUpMessage.setStage(window);
                new GamePopUpMessage(Alert.AlertType.ERROR, "Please select!!!");
            }
        });

        GridPane gridPane = getOnlyMonsterZoneGridPaneToSelect(equipAddress, 1, RoundGameController.getInstance().getCurrentPlayer().getPlayerBoard().returnMonsterZone());
        Button resetChoicesButton = new Button();
        resetChoicesButton.setText("Reset");
        resetChoicesButton.setCursor(Cursor.HAND);
        resetChoicesButton.setStyle("-fx-background-color: #bb792d;\n" +
                "-fx-background-radius: 10;\n" +
                "-fx-text-fill: white;\n" +
                "-fx-font-size: 16;");
        resetChoicesButton.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.PRIMARY || equipAddress.size() == 0)
                return;
            onClick.play();
            for (Integer address : equipAddress) {
                ((Pane) Objects.requireNonNull(getNodeInGridPane(gridPane, 0, address - 1))).setBorder(null);
            }
            equipAddress.clear();

        });
        Label label = new Label("Select monster to be equipped");
        label.setStyle("-fx-text-fill: white");
        HBox buttonBox = new HBox(doneButton, resetChoicesButton);
        VBox mainBox = new VBox(label, gridPane, buttonBox);
        buttonBox.setSpacing(20);
        buttonBox.setAlignment(Pos.CENTER);
        doneButton.setCursor(Cursor.HAND);
        mainBox.setSpacing(10);
        mainBox.setStyle("-fx-background-color: #103188;");
        mainBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(mainBox, 700, 250);
        mainBox.getScene().setFill(Color.TRANSPARENT);
        window.initStyle(StageStyle.TRANSPARENT);
        window.setScene(scene);
        window.setResizable(false);
        window.setX(500);
        window.setY(300);
        window.initModality(Modality.WINDOW_MODAL);
        window.getScene().getStylesheets().add(String.valueOf(getClass().getResource("/project/CSS/in_game_window.css")));

        window.showAndWait();
        return equipAddress.get(0);
    }

    public int blackPendant() {
        ArrayList<Integer> equipAddress = new ArrayList<>();
        Stage window = new Stage();
        window.initOwner(LoginMenuView.getStage());
        window.initStyle(StageStyle.UNDECORATED);
        GamePopUpMessage.setStage(window);

        Button doneButton = new Button();
        doneButton.setText("OK");
        doneButton.setStyle("-fx-background-color: #bb792d;\n" +
                "-fx-background-radius: 10;\n" +
                "-fx-text-fill: white;\n" +
                "-fx-font-size: 16;");
        doneButton.setOnAction(event -> {
            onClick.play();
            if (equipAddress.size() == 1) {
                window.close();
            } else {
                GamePopUpMessage.setStage(window);
                new GamePopUpMessage(Alert.AlertType.ERROR, "Please select!!!");
            }
        });

        GridPane gridPane = getOnlyMonsterZoneGridPaneToSelect(equipAddress, 1, RoundGameController.getInstance().getCurrentPlayer().getPlayerBoard().returnMonsterZone());
        Button resetChoicesButton = new Button();
        resetChoicesButton.setText("Reset");
        resetChoicesButton.setCursor(Cursor.HAND);
        resetChoicesButton.setStyle("-fx-background-color: #bb792d;\n" +
                "-fx-background-radius: 10;\n" +
                "-fx-text-fill: white;\n" +
                "-fx-font-size: 16;");
        resetChoicesButton.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.PRIMARY)
                return;
            onClick.play();
            for (Integer address : equipAddress) {
                ((Pane) Objects.requireNonNull(getNodeInGridPane(gridPane, 0, address - 1))).setBorder(null);
            }
            equipAddress.clear();
        });
        Label label = new Label("Select monster to be equipped");
        label.setStyle("-fx-text-fill: white");
        HBox buttonBox = new HBox(doneButton, resetChoicesButton);
        VBox mainBox = new VBox(label, gridPane, buttonBox);
        buttonBox.setSpacing(20);
        buttonBox.setAlignment(Pos.CENTER);
        doneButton.setCursor(Cursor.HAND);
        mainBox.setSpacing(10);
        mainBox.setStyle("-fx-background-color: #103188;");
        mainBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(mainBox, 700, 250);
        mainBox.getScene().setFill(Color.TRANSPARENT);
        window.initStyle(StageStyle.TRANSPARENT);
        window.setScene(scene);
        window.setResizable(false);
        window.setX(500);
        window.setY(300);
        window.initModality(Modality.WINDOW_MODAL);
        window.getScene().getStylesheets().add(String.valueOf(getClass().getResource("/project/CSS/in_game_window.css")));

        window.showAndWait();
        return equipAddress.get(0);
    }

    public int getAddressForTrapOrSpell(SpellZone spellZone, String situation) {
        ArrayList<Integer> toActivateAddress = new ArrayList<>();
        Stage window = new Stage();
        window.initOwner(LoginMenuView.getStage());
        window.initStyle(StageStyle.UNDECORATED);
        GamePopUpMessage.setStage(window);

        GridPane gridPane = getOnlySpellZoneGridPaneToSelect(toActivateAddress, 1, spellZone);

        Button doneButton = new Button();
        doneButton.setText("OK");
        doneButton.setStyle("-fx-background-color: #bb792d;\n" +
                "-fx-background-radius: 10;\n" +
                "-fx-text-fill: white;\n" +
                "-fx-font-size: 16;");

        doneButton.setOnAction(event -> {
            onClick.play();
            if (toActivateAddress.size() == 1) {
                {
                    switch (situation) {
                        case "Attack":
                            if (RoundGameController.getInstance().isValidTrapToActivateInAttackSituationByOpponent(spellZone.getCellWithAddress(toActivateAddress.get(0)).getCardInCell())) {
                                window.close();
                            } else {
                                GamePopUpMessage.setStage(window);
                                new GamePopUpMessage(Alert.AlertType.ERROR, "Invalid choice to activate!");
                            }
                            break;
                        case "Summon current":
                            if (RoundGameController.getInstance().isValidTrapToActivateInSummonByCurrent(spellZone.getCellWithAddress(toActivateAddress.get(0)).getCardInCell())) {
                                window.close();
                            } else {
                                for (Integer address : toActivateAddress) {
                                    ((Pane) Objects.requireNonNull(getNodeInGridPane(gridPane, 0, address - 1))).setBorder(null);
                                }
                                toActivateAddress.clear();
                                GamePopUpMessage.setStage(window);
                                new GamePopUpMessage(Alert.AlertType.ERROR, "Invalid choice to activate!");
                            }

                        case "Summon opponent":
                            if (RoundGameController.getInstance().isValidTrapToActivateInSummonByOpponent(spellZone.getCellWithAddress(toActivateAddress.get(0)).getCardInCell())) {

                                window.close();
                            } else {
                                for (Integer address : toActivateAddress) {
                                    ((Pane) Objects.requireNonNull(getNodeInGridPane(gridPane, 0, address - 1))).setBorder(null);
                                }
                                toActivateAddress.clear();
                                GamePopUpMessage.setStage(window);
                                new GamePopUpMessage(Alert.AlertType.ERROR, "Invalid choice to activate!");
                            }

                        default:
                            break;
                    }

                }
            } else new GamePopUpMessage(Alert.AlertType.ERROR, "Please select!!!");
        });

        Button cancel = new Button();
        cancel.setCursor(Cursor.HAND);
        cancel.setStyle("-fx-background-color: #bb792d;\n" +
                "-fx-background-radius: 10;\n" +
                "-fx-text-fill: white;\n" +
                "-fx-font-size: 16;");
        cancel.setText("Cancel");
        cancel.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.PRIMARY)
                return;
            onClick.play();
            toActivateAddress.clear();
            toActivateAddress.add(-1);
            window.close();
        });
        Button resetChoicesButton = new Button();
        resetChoicesButton.setText("Reset");
        resetChoicesButton.setCursor(Cursor.HAND);
        resetChoicesButton.setStyle("-fx-background-color: #bb792d;\n" +
                "-fx-background-radius: 10;\n" +
                "-fx-text-fill: white;\n" +
                "-fx-font-size: 16;");
        resetChoicesButton.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.PRIMARY)
                return;
            onClick.play();
            for (Integer address : toActivateAddress) {
                ((Pane) Objects.requireNonNull(getNodeInGridPane(gridPane, 0, address - 1))).setBorder(null);
            }
            toActivateAddress.clear();

        });
        Label label = new Label("please choose one of your spells to activate");
        label.setStyle("-fx-text-fill: white");
        HBox buttonBox = new HBox(doneButton, resetChoicesButton, cancel);
        VBox mainBox = new VBox(label, gridPane, buttonBox);
        buttonBox.setSpacing(20);
        buttonBox.setAlignment(Pos.CENTER);
        doneButton.setCursor(Cursor.HAND);
        mainBox.setSpacing(10);
        mainBox.setStyle("-fx-background-color: #103188;");
        mainBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(mainBox, 700, 250);
        mainBox.getScene().setFill(Color.TRANSPARENT);
        window.initStyle(StageStyle.TRANSPARENT);
        window.setScene(scene);
        window.setResizable(false);
        window.setX(500);
        window.setY(300);
        window.initModality(Modality.WINDOW_MODAL);
        window.getScene().getStylesheets().add(String.valueOf(getClass().getResource("/project/CSS/in_game_window.css")));

        window.showAndWait();
        return toActivateAddress.get(0);
    }

    private GridPane getOnlySpellZoneGridPaneToSelect(ArrayList<Integer> listOfSelected, int size, SpellZone spellZone) {
        GridPane gridPane = new GridPane();
        ArrayList<Card> inZoneCards = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Cell cell = spellZone.getCellWithAddress(i);
            if (cell.getCellStatus() != CellStatus.EMPTY) {
                inZoneCards.add(cell.getCardInCell());
            }
        }
        int i = 0;
        for (Card zoneCard : inZoneCards) {
            Pane pane = new Pane();
            pane.setPrefHeight(135);
            pane.setPrefWidth(99);
            gridPane.add(pane, i, 0);
            ImageView cardImageView = new ImageView(getCardImageByName(zoneCard.getName()));
            cardImageView.setFitHeight(130);
            cardImageView.setFitWidth(94);

            pane.getChildren().add(cardImageView);
            cardImageView.setLayoutX(cardImageView.getLayoutX() + 3);
            cardImageView.setLayoutY(cardImageView.getLayoutY() + 3);
            int finalI = i;
            pane.setCursor(Cursor.HAND);
            pane.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() != MouseButton.PRIMARY)
                    return;
                onClick.play();
                if (listOfSelected.size() != size) {
                    listOfSelected.add(finalI + 1);
                    pane.setBorder(new Border(new BorderStroke(Color.YELLOW,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THICK)));
                }
            });
            i++;
        }
        gridPane.setHgap(5);
        return gridPane;
    }

    public boolean yesNoQuestion(String question) {
        GamePopUpMessage.setStage(LoginMenuView.getStage());
        GamePopUpMessage message = new GamePopUpMessage(Alert.AlertType.CONFIRMATION, question);
        return message.getAlert().getResult().getButtonData().isDefaultButton();
    }

    public int chooseCardInHand(int thisAddress) {
        int[] selectedAddress = new int[1];
        boolean[] isSelected = new boolean[1];
        Stage window = new Stage();
        window.initOwner(LoginMenuView.getStage());
        window.initStyle(StageStyle.UNDECORATED);
        GamePopUpMessage.setStage(window);
        Button doneButton = new Button();
        doneButton.setText("OK");
        doneButton.setStyle("-fx-background-color: #bb792d;\n" +
                "-fx-background-radius: 10;\n" +
                "-fx-text-fill: white;\n" +
                "-fx-font-size: 16;");
        doneButton.setOnAction(event -> {
            onClick.play();
            if (isSelected[0]) {
                window.close();
            } else new GamePopUpMessage(Alert.AlertType.ERROR, "Please select!!!");
        });

        GridPane gridPane = getOnlyCurrentHandGridPaneToSelect(isSelected, selectedAddress, thisAddress);

        Button resetChoicesButton = new Button();
        resetChoicesButton.setCursor(Cursor.HAND);
        resetChoicesButton.setText("Reset");
        resetChoicesButton.setStyle("-fx-background-color: #bb792d;\n" +
                "-fx-background-radius: 10;\n" +
                "-fx-text-fill: white;\n" +
                "-fx-font-size: 16;");
        resetChoicesButton.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.PRIMARY)
                return;
            onClick.play();
            if (isSelected[0]) {
                ((Pane) Objects.requireNonNull(getNodeInGridPane(gridPane, 0, selectedAddress[0] - 1))).setBorder(null);
                isSelected[0] = false;
            }
        });
        Label label = new Label("Tribute Summon : please choose card in hand to tribute");
        label.setStyle("-fx-text-fill: white");
        HBox buttonBox = new HBox(doneButton, resetChoicesButton);
        VBox mainBox = new VBox(label, gridPane, buttonBox);
        buttonBox.setSpacing(20);
        buttonBox.setAlignment(Pos.CENTER);
        doneButton.setCursor(Cursor.HAND);
        mainBox.setSpacing(10);
        mainBox.setStyle("-fx-background-color: #103188;");
        mainBox.setAlignment(Pos.CENTER);
        window.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(mainBox, 700, 250);
        mainBox.getScene().setFill(Color.TRANSPARENT);
        window.initStyle(StageStyle.TRANSPARENT);
        window.setScene(scene);
        window.setResizable(false);
        window.setX(500);
        window.setY(300);
        window.getScene().getStylesheets().add(String.valueOf(getClass().getResource("/project/CSS/in_game_window.css")));
        window.showAndWait();

        return selectedAddress[0];
    }

    private GridPane getOnlyCurrentHandGridPaneToSelect(boolean[] isSelected, int[] selectedAddress, int thisAddress) {
        GridPane gridPane = new GridPane();
        ArrayList<Card> inHandCards = (ArrayList<Card>) RoundGameController.getInstance().getCurrentPlayerHand();
        for (int i = 0; i < inHandCards.size(); i++) {
            if (i == thisAddress - 1)
                continue;
            Pane pane = new Pane();
            pane.setPrefHeight(135);
            pane.setPrefWidth(99);
            gridPane.add(pane, i, 0);
            ImageView cardImageView = new ImageView(getCardImageByName(inHandCards.get(i).getName()));
            cardImageView.setFitHeight(130);
            cardImageView.setFitWidth(94);

            pane.getChildren().add(cardImageView);
            cardImageView.setLayoutX(cardImageView.getLayoutX() + 3);
            cardImageView.setLayoutY(cardImageView.getLayoutY() + 3);
            int finalI = i;
            pane.setCursor(Cursor.HAND);
            pane.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() != MouseButton.PRIMARY)
                    return;
                onClick.play();
                if (!isSelected[0]) {
                    selectedAddress[0] = finalI + 1;
                    pane.setBorder(new Border(new BorderStroke(Color.YELLOW,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THICK)));
                    isSelected[0] = true;
                }
            });
        }
        gridPane.setHgap(5);
        return gridPane;
    }


    public int howToSummonBeastKingBarbos() {
        //1-normal tribute, 2-without tribute, 3-with 3 tributes
        int[] howToSummon = new int[1];
        Stage window = new Stage();
        window.initOwner(LoginMenuView.getStage());
        window.initStyle(StageStyle.UNDECORATED);
        GamePopUpMessage.setStage(window);

        GridPane gridPane = new GridPane();
        Button button1 = new Button("Normal");
        Button button2 = new Button("Special1");
        Button button3 = new Button("Special2");
        button1.setStyle("-fx-background-color: #9e376c;\n" +
                "-fx-background-radius: 10;\n" +
                "-fx-text-fill: white;\n" +
                "-fx-font-size: 16;");
        button2.setStyle("-fx-background-color: #9e376c;\n" +
                "-fx-background-radius: 10;\n" +
                "-fx-text-fill: white;\n" +
                "-fx-font-size: 16;");
        button3.setStyle("-fx-background-color: #9e376c;\n" +
                "-fx-background-radius: 10;\n" +
                "-fx-text-fill: white;\n" +
                "-fx-font-size: 16;");
        Tooltip tooltip1 = new Tooltip("Normal Tribute Summon");
        Tooltip tooltip2 = new Tooltip("Summon, without tribute and power loss");
        Tooltip tooltip3 = new Tooltip("Summon, with 3 tribute and effect");
        button1.setTooltip(tooltip1);
        button2.setTooltip(tooltip2);
        button3.setTooltip(tooltip3);
        button1.setCursor(Cursor.HAND);
        button1.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.PRIMARY)
                return;
            onClick.play();
            howToSummon[0] = 1;
            window.close();
        });
        button2.setCursor(Cursor.HAND);
        button2.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.PRIMARY)
                return;
            onClick.play();
            howToSummon[0] = 2;
            window.close();
        });
        button3.setCursor(Cursor.HAND);
        button3.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.PRIMARY)
                return;
            onClick.play();
            howToSummon[0] = 3;
            window.close();
        });
        HBox boxOfChoices = new HBox(button1, button2, button3);
        boxOfChoices.setSpacing(10);
        boxOfChoices.setAlignment(Pos.CENTER);
        Button cancelButton = new Button();
        cancelButton.setCursor(Cursor.HAND);
        cancelButton.setText("Cancel");
        cancelButton.setStyle("-fx-background-color: #bb792d;\n" +
                "-fx-background-radius: 10;\n" +
                "-fx-text-fill: white;\n" +
                "-fx-font-size: 16;");
        cancelButton.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.PRIMARY)
                return;
            onClick.play();
            howToSummon[0] = -1;
            window.close();
        });
        //
        HBox buttonBox = new HBox(cancelButton);
        VBox mainBox = new VBox(boxOfChoices, gridPane, buttonBox);
        buttonBox.setSpacing(20);
        buttonBox.setAlignment(Pos.CENTER);
        mainBox.setSpacing(10);
        mainBox.setStyle("-fx-background-color: #103188;");
        mainBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(mainBox, 400, 150);
        mainBox.getScene().setFill(Color.TRANSPARENT);
        window.initStyle(StageStyle.TRANSPARENT);
        window.setScene(scene);
        window.setResizable(false);
        window.setX(600);
        window.setY(300);
        window.initModality(Modality.WINDOW_MODAL);
        window.getScene().getStylesheets().add(String.valueOf(getClass().getResource("/project/CSS/in_game_window.css")));
        window.showAndWait();
        return howToSummon[0];
    }

    public int getRitualMonsterAddress(int thisAddress) {
        int[] selectedAddress = new int[1];
        boolean[] isSelected = new boolean[1];
        Stage window = new Stage();
        window.initOwner(LoginMenuView.getStage());
        window.initStyle(StageStyle.UNDECORATED);
        GamePopUpMessage.setStage(window);
        Button doneButton = new Button();
        doneButton.setText("OK");
        doneButton.setStyle("-fx-background-color: #bb792d;\n" +
                "-fx-background-radius: 10;\n" +
                "-fx-text-fill: white;\n" +
                "-fx-font-size: 16;");
        doneButton.setOnAction(event -> {
            onClick.play();
            if (isSelected[0]) {
                if (RoundGameController.getInstance().getCurrentPlayerHand().get(selectedAddress[0] - 1).getCardType() == CardType.MONSTER) {
                    if (((Monster) RoundGameController.getInstance().getCurrentPlayerHand().get(selectedAddress[0] - 1)).getMonsterActionType() == MonsterActionType.RITUAL) {
                        window.close();
                    } else {
                        GamePopUpMessage.setStage(window);
                        new GamePopUpMessage(Alert.AlertType.ERROR, "Invalid selection");
                    }
                } else {
                    GamePopUpMessage.setStage(window);
                    new GamePopUpMessage(Alert.AlertType.ERROR, "Invalid selection");
                }
            } else new GamePopUpMessage(Alert.AlertType.ERROR, "Please select!!!");
        });

        GridPane gridPane = getOnlyCurrentHandGridPaneToSelect(isSelected, selectedAddress, thisAddress);
        Button resetChoicesButton = new Button();
        resetChoicesButton.setCursor(Cursor.HAND);
        resetChoicesButton.setText("Reset");
        resetChoicesButton.setStyle("-fx-background-color: #bb792d;\n" +
                "-fx-background-radius: 10;\n" +
                "-fx-text-fill: white;\n" +
                "-fx-font-size: 16;");
        resetChoicesButton.setCursor(Cursor.HAND);
        resetChoicesButton.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.PRIMARY)
                return;
            onClick.play();
            if (isSelected[0]) {
                ((Pane) Objects.requireNonNull(getNodeInGridPane(gridPane, 0, selectedAddress[0] - 1))).setBorder(null);
                isSelected[0] = false;
            }
        });
        Button cancel = new Button();
        cancel.setCursor(Cursor.HAND);
        cancel.setText("Cancel");
        cancel.setStyle("-fx-background-color: #bb792d;\n" +
                "-fx-background-radius: 10;\n" +
                "-fx-text-fill: white;\n" +
                "-fx-font-size: 16;");
        cancel.setCursor(Cursor.HAND);
        cancel.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.PRIMARY)
                return;
            onClick.play();
            if (isSelected[0]) {
                ((Pane) Objects.requireNonNull(getNodeInGridPane(gridPane, 0, selectedAddress[0] - 1))).setBorder(null);
                isSelected[0] = false;
                selectedAddress[0] = -1;
                window.close();
            }
        });
        Label label = new Label("Please choose the ritual monster in the hand");
        label.setStyle("-fx-text-fill: white");
        HBox buttonBox = new HBox(doneButton, resetChoicesButton, cancel);
        VBox mainBox = new VBox(label, gridPane, buttonBox);
        buttonBox.setSpacing(20);
        buttonBox.setAlignment(Pos.CENTER);
        doneButton.setCursor(Cursor.HAND);
        mainBox.setSpacing(10);
        mainBox.setStyle("-fx-background-color: #103188;");
        mainBox.setAlignment(Pos.CENTER);
        window.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(mainBox, 700, 250);
        mainBox.getScene().setFill(Color.TRANSPARENT);
        window.initStyle(StageStyle.TRANSPARENT);
        window.setScene(scene);
        window.setResizable(false);
        window.setX(500);
        window.setY(300);
        window.getScene().getStylesheets().add(String.valueOf(getClass().getResource("/project/CSS/in_game_window.css")));
        window.showAndWait();

        return selectedAddress[0];
    }

    public int askAddressForManEaterBug(MonsterZone monsterZone) {

        ArrayList<Integer> address = new ArrayList<>();
        Stage window = new Stage();
        window.initOwner(LoginMenuView.getStage());
        window.initStyle(StageStyle.UNDECORATED);
        GamePopUpMessage.setStage(window);

        Label title = new Label("Choose Tribute Cards");
        title.setId("title");

        Button doneButton = new Button();
        doneButton.setText("OK");
        doneButton.setStyle("-fx-background-color: #bb792d;\n" +
                "-fx-background-radius: 10;\n" +
                "-fx-text-fill: white;\n" +
                "-fx-font-size: 16;");
        doneButton.setOnAction(event -> {
            onClick.play();
            if (address.size() == 1) {
                window.close();
            } else new GamePopUpMessage(Alert.AlertType.ERROR, "Please select!!!");
        });

        GridPane gridPane = getOnlyMonsterZoneGridPaneToSelect(address, 1, monsterZone);

        Button resetChoicesButton = new Button();
        resetChoicesButton.setText("Reset");
        resetChoicesButton.setCursor(Cursor.HAND);
        resetChoicesButton.setStyle("-fx-background-color: #bb792d;\n" +
                "-fx-background-radius: 10;\n" +
                "-fx-text-fill: white;\n" +
                "-fx-font-size: 16;");
        resetChoicesButton.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.PRIMARY)
                return;
            onClick.play();

            for (Integer integer : address) {
                ((Pane) Objects.requireNonNull(getNodeInGridPane(gridPane, 0, integer - 1))).setBorder(null);
            }
            address.clear();

        });
        Label label = new Label("Man eater bug effect: please choose " + 1 + " of opponent monsters to tribute");
        label.setStyle("-fx-text-fill: white");
        HBox buttonBox = new HBox(doneButton, resetChoicesButton);
        VBox mainBox = new VBox(label, gridPane, buttonBox);
        buttonBox.setSpacing(20);
        buttonBox.setAlignment(Pos.CENTER);
        doneButton.setCursor(Cursor.HAND);
        mainBox.setSpacing(10);
        mainBox.setStyle("-fx-background-color: #103188;");
        mainBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(mainBox, 700, 250);
        mainBox.getScene().setFill(Color.TRANSPARENT);
        window.initStyle(StageStyle.TRANSPARENT);
        window.setScene(scene);
        window.setResizable(false);
        window.setX(500);
        window.setY(300);
        window.initModality(Modality.WINDOW_MODAL);
        window.getScene().getStylesheets().add(String.valueOf(getClass().getResource("/project/CSS/in_game_window.css")));
        window.showAndWait();
        return address.get(0);
    }

    public int chooseCardInDeck(Deck deck) {
        ArrayList<Card> allCards = deck.getMainCards();
        ArrayList<Card> cards = (ArrayList<Card>) allCards.stream().filter(card -> card.getCardType() == CardType.SPELL && ((Spell) card).getSpellType() == SpellType.FIELD).collect(Collectors.toList());
        Stage window = new Stage();
        int[] selectedAddress = new int[1];
        boolean[] isSelected = new boolean[1];
        window.initOwner(LoginMenuView.getStage());
        window.initStyle(StageStyle.UNDECORATED);
        GamePopUpMessage.setStage(window);
        Button doneButton = new Button();
        doneButton.setText("OK");
        doneButton.setStyle("-fx-background-color: #bb792d;\n" +
                "-fx-background-radius: 10;\n" +
                "-fx-text-fill: white;\n" +
                "-fx-font-size: 16;");
        doneButton.setOnAction(event -> {
            onClick.play();
            if (isSelected[0]) {
                window.close();
            } else new GamePopUpMessage(Alert.AlertType.ERROR, "Please select!!!");
        });

        GridPane graveYardGridPane = getDeckGridPaneToSelect(cards, isSelected, selectedAddress);

        Button resetChoicesButton = new Button();
        resetChoicesButton.setText("Reset");
        resetChoicesButton.setCursor(Cursor.HAND);
        resetChoicesButton.setStyle("-fx-background-color: #bb792d;\n" +
                "-fx-background-radius: 10;\n" +
                "-fx-text-fill: white;\n" +
                "-fx-font-size: 16;");
        resetChoicesButton.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.PRIMARY)
                return;
            onClick.play();
            if (isSelected[0]) {
                ((Pane) Objects.requireNonNull(getNodeInGridPane(graveYardGridPane, 0, selectedAddress[0] - 1))).setBorder(null);
                isSelected[0] = false;
            }
        });
        Label label = new Label("Choose field card to activate");
        label.setStyle("-fx-text-fill: white");
        HBox hBox = new HBox(doneButton, resetChoicesButton);
        VBox vBox = new VBox(label, graveYardGridPane, hBox);
        hBox.setSpacing(20);
        hBox.setAlignment(Pos.CENTER);
        doneButton.setCursor(Cursor.HAND);
        vBox.setSpacing(10);
        vBox.setStyle("-fx-background-color: #103188;");
        vBox.setAlignment(Pos.CENTER);
        Scene scene2 = new Scene(vBox, 700, 250);
        vBox.getScene().setFill(Color.TRANSPARENT);
        window.initStyle(StageStyle.TRANSPARENT);
        window.setScene(scene2);
        window.setResizable(false);
        window.setX(500);
        window.setY(300);
        window.initModality(Modality.WINDOW_MODAL);
        window.getScene().getStylesheets().add(String.valueOf(getClass().getResource("/project/CSS/in_game_window.css")));
        window.showAndWait();
        for (int i = 0; i < allCards.size(); i++) {
            if (allCards.get(i) == cards.get(selectedAddress[0])) {
                selectedAddress[0] = i + 1;
                break;
            }
        }
        return selectedAddress[0];
    }

    private GridPane getOnlyGraveYardGridPaneToSelect(ArrayList<Card> graveYard, boolean[] isSelected, int[] selectedAddress) {
        GridPane gridPane = new GridPane();

        for (int i = 0; i < graveYard.size(); i++) {
            Pane pane = new Pane();
            pane.setPrefHeight(135);
            pane.setPrefWidth(99);
            gridPane.add(pane, i, 0);
            ImageView cardImageView = new ImageView(getCardImageByName(graveYard.get(i).getName()));
            cardImageView.setFitHeight(130);
            cardImageView.setFitWidth(94);

            pane.getChildren().add(cardImageView);
            cardImageView.setLayoutX(cardImageView.getLayoutX() + 3);
            cardImageView.setLayoutY(cardImageView.getLayoutY() + 3);
            int finalI = i;
            pane.setCursor(Cursor.HAND);
            pane.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() != MouseButton.PRIMARY)
                    return;
                onClick.play();
                if (!isSelected[0]) {
                    selectedAddress[0] = finalI + 1;
                    pane.setBorder(new Border(new BorderStroke(Color.YELLOW,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THICK)));
                    isSelected[0] = true;
                }
            });
        }
        gridPane.setHgap(5);
        return gridPane;
    }


    public int chooseCardInGraveYard(ArrayList<Card> monstersInGraveyard, ArrayList<Card> graveyard) {

        Stage window = new Stage();
        int[] selectedAddress = new int[1];
        boolean[] isSelected = new boolean[1];
        window.initOwner(LoginMenuView.getStage());
        window.initStyle(StageStyle.UNDECORATED);
        GamePopUpMessage.setStage(window);
        Button doneButton = new Button();
        doneButton.setText("OK");
        doneButton.setStyle("-fx-background-color: #bb792d;\n" +
                "-fx-background-radius: 10;\n" +
                "-fx-text-fill: white;\n" +
                "-fx-font-size: 16;");
        doneButton.setOnAction(event -> {
            onClick.play();
            if (isSelected[0]) {
                window.close();
            } else new GamePopUpMessage(Alert.AlertType.ERROR, "Please select!!!");
        });

        GridPane gridPane = getOnlyGraveYardGridPaneToSelect(monstersInGraveyard, isSelected, selectedAddress);

        Button resetChoicesButton = new Button();
        resetChoicesButton.setText("Reset");
        resetChoicesButton.setCursor(Cursor.HAND);
        resetChoicesButton.setStyle("-fx-background-color: #bb792d;\n" +
                "-fx-background-radius: 10;\n" +
                "-fx-text-fill: white;\n" +
                "-fx-font-size: 16;");
        resetChoicesButton.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.PRIMARY)
                return;
            onClick.play();
            if (isSelected[0]) {
                ((Pane) Objects.requireNonNull(getNodeInGridPane(gridPane, 0, selectedAddress[0] - 1))).setBorder(null);
                isSelected[0] = false;
            }
        });
        Label label = new Label("Monster Reborn: please choose one card in this graveyard to summon");
        label.setStyle("-fx-text-fill: white");
        HBox hBox = new HBox(doneButton, resetChoicesButton);
        VBox vBox = new VBox(label, gridPane, hBox);
        hBox.setSpacing(20);
        hBox.setAlignment(Pos.CENTER);
        doneButton.setCursor(Cursor.HAND);
        vBox.setSpacing(10);
        vBox.setStyle("-fx-background-color: #103188;");
        vBox.setAlignment(Pos.CENTER);
        Scene scene2 = new Scene(vBox, 700, 250);
        vBox.getScene().setFill(Color.TRANSPARENT);
        window.initStyle(StageStyle.TRANSPARENT);
        window.setScene(scene2);
        window.setResizable(false);
        window.setX(500);
        window.setY(300);
        window.initModality(Modality.WINDOW_MODAL);
        window.getScene().getStylesheets().add(String.valueOf(getClass().getResource("/project/CSS/in_game_window.css")));
        window.showAndWait();
        for (int i = 0; i < graveyard.size(); i++) {
            if (graveyard.get(i) == monstersInGraveyard.get(selectedAddress[0] - 1)) {
                selectedAddress[0] = i + 1;
                break;
            }
        }
        return selectedAddress[0];
    }

    private GridPane getDeckGridPaneToSelect(ArrayList<Card> deck, boolean[] isSelected, int[] selectedAddress) {
        GridPane gridPane = new GridPane();

        for (int i = 0; i < deck.size(); i++) {
            Pane pane = new Pane();
            pane.setPrefHeight(135);
            pane.setPrefWidth(99);
            gridPane.add(pane, i, 0);
            ImageView cardImageView = new ImageView(getCardImageByName(deck.get(i).getName()));
            cardImageView.setFitHeight(130);
            cardImageView.setFitWidth(94);

            pane.getChildren().add(cardImageView);
            cardImageView.setLayoutX(cardImageView.getLayoutX() + 3);
            cardImageView.setLayoutY(cardImageView.getLayoutY() + 3);
            int finalI = i;
            pane.setCursor(Cursor.HAND);
            pane.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() != MouseButton.PRIMARY)
                    return;
                onClick.play();
                if (!isSelected[0]) {
                    selectedAddress[0] = finalI + 1;
                    pane.setBorder(new Border(new BorderStroke(Color.YELLOW,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THICK)));
                    isSelected[0] = true;
                }
            });
        }
        gridPane.setHgap(5);
        return gridPane;
    }

    public int twoChoiceQuestions(String message, String choice1, String choice2) {
        int[] choice = new int[1];
        Stage window1 = new Stage();
        window1.initOwner(LoginMenuView.getStage());
        window1.initStyle(StageStyle.UNDECORATED);
        GamePopUpMessage.setStage(window1);
        Button button1 = new Button(choice1);
        Button button2 = new Button(choice2);
        button1.setStyle("-fx-background-color: #9e376c;\n" +
                "-fx-background-radius: 10;\n" +
                "-fx-text-fill: white;\n" +
                "-fx-font-size: 16;");
        button2.setStyle("-fx-background-color: #9e376c;\n" +
                "-fx-background-radius: 10;\n" +
                "-fx-text-fill: white;\n" +
                "-fx-font-size: 16;");

        button1.setCursor(Cursor.HAND);
        button1.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.PRIMARY)
                return;
            onClick.play();
            choice[0] = 1;
            window1.close();
        });
        button2.setCursor(Cursor.HAND);
        button2.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.PRIMARY)
                return;
            onClick.play();
            choice[0] = 2;
            window1.close();
        });
        HBox boxOfChoices;
        if (choice1.equals(""))
            boxOfChoices = new HBox(button2);
        else if (choice2.equals("")) {
            boxOfChoices = new HBox(button1);
        } else {
            boxOfChoices = new HBox(button1, button2);
        }
        boxOfChoices.setSpacing(10);
        boxOfChoices.setAlignment(Pos.CENTER);
        Label label = new Label(message);
        label.setStyle("-fx-text-fill: white");
        VBox mainBox = new VBox(boxOfChoices);
        mainBox.setSpacing(10);
        mainBox.setStyle("-fx-background-color: #103188;");
        mainBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(mainBox, 400, 150);
        mainBox.getScene().setFill(Color.TRANSPARENT);
        window1.initStyle(StageStyle.TRANSPARENT);
        window1.setScene(scene);
        window1.setResizable(false);
        window1.setX(600);
        window1.setY(300);
        window1.initModality(Modality.WINDOW_MODAL);
        window1.getScene().getStylesheets().add(String.valueOf(getClass().getResource("/project/CSS/in_game_window.css")));
        window1.showAndWait();
        return choice[0];
    }


    private void showCurrentGraveYardToSee(boolean isCurrent) {
        ArrayList<Card> currentGraveYard = isCurrent ? RoundGameController.getInstance().getCurrentPlayer().getPlayerBoard().returnGraveYard().getGraveYardCards() :
                RoundGameController.getInstance().getOpponentPlayer().getPlayerBoard().returnGraveYard().getGraveYardCards();
        ScrollPane scrollPane = new ScrollPane();
        GridPane cardsPane = new GridPane();
        int i = 0;
        for (Card card : currentGraveYard) {
            ImageView imageView = new ImageView();
            imageView.setImage(getCardImageByName(card.getName()));
            imageView.setFitWidth(94);
            imageView.setFitHeight(130);
            cardsPane.add(imageView, i, 0);
            i++;
        }
        cardsPane.setHgap(10);
        scrollPane.setContent(cardsPane);
        Stage window = new Stage();
        window.initOwner(LoginMenuView.getStage());
        window.initStyle(StageStyle.UNDECORATED);
        GamePopUpMessage.setStage(window);

        Button backButton = new Button();
        backButton.setText("Back");
        backButton.setStyle("-fx-background-color: #bb792d;\n" +
                "-fx-background-radius: 10;\n" +
                "-fx-text-fill: white;\n" +
                "-fx-font-size: 16;");
        backButton.setOnAction(event -> {
            onClick.play();
            window.close();
        });
        scrollPane.getContent().setStyle("-fx-background-color: #00062b;-fx-color: #00062b;");

        scrollPane.getStylesheets().add(getClass().getResource("/project/CSS/graveyard.css").toString());
        scrollPane.setPrefHeight(150);
        String owner = isCurrent ? "Your" : "Opponent";
        Label label = new Label(owner + " GraveYard");
        label.setStyle("-fx-text-fill: white");
        HBox buttonBox = new HBox(backButton);
        VBox mainBox = new VBox(label, scrollPane, buttonBox);
        buttonBox.setSpacing(20);
        buttonBox.setAlignment(Pos.CENTER);
        backButton.setCursor(Cursor.HAND);
        mainBox.setSpacing(10);
        mainBox.setStyle("-fx-background-color: #103188;");
        mainBox.setAlignment(Pos.CENTER);
        window.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(mainBox, 700, 250);
        mainBox.getScene().setFill(Color.TRANSPARENT);
        window.initStyle(StageStyle.TRANSPARENT);
        window.setScene(scene);
        window.setResizable(false);
        window.setX(500);
        window.setY(300);
        window.getScene().getStylesheets().add(String.valueOf(getClass().getResource("/project/CSS/in_game_window.css")));
        window.showAndWait();


    }

    public void back(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() != MouseButton.PRIMARY) return;
        Utility.openNewMenu("/project/fxml/duel_start_menu.fxml");
    }

    public void nextPhase(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() != MouseButton.PRIMARY)
            return;
        AudioClip onClick = new AudioClip(Objects.requireNonNull(getClass().getResource("/project/soundEffects/CURSOR.wav")).toString());
        onClick.play();
        GameViewMessage message = RoundGameController.getInstance().nextPhase();
        Cell cell = RoundGameController.getInstance().getSelectedCell();
        if (message != GameViewMessage.SUCCESS) {
            GamePopUpMessage.setStage(LoginMenuView.getStage());
            new GamePopUpMessage(message.getAlertType(), message.getLabel());
            if (cell == null) {
                selectedCardImageView.setImage(getCardImageByName("Back Image"));
                selectedCardDescriptionLabel.setText("No card selected");
            }
            return;
        }
        Phase currentPhase = RoundGameController.getInstance().getCurrentPhase();
        phaseLabel.setText(String.valueOf(currentPhase));
        showButtonBasedOnPhase(currentPhase);
        cell = RoundGameController.getInstance().getSelectedCell();
        if (cell == null) {
            selectedCardImageView.setImage(getCardImageByName("Back Image"));
            selectedCardDescriptionLabel.setText("No card selected");
        }
    }

    public void changeTurn() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Change turn!");
        alert.initOwner(LoginMenuView.getStage());
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initStyle(StageStyle.TRANSPARENT);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setHeaderText(null);
        dialogPane.setGraphic(null);
        dialogPane.setStyle("-fx-border-radius: 10; -fx-border-color: #bb792d; -fx-border-width: 7; -fx-background-radius: 14; -fx-font-family: \"Matrix II Regular\"; -fx-background-color: #103188;");
        dialogPane.lookup(".content.label").setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-line-spacing: 5px;");
        dialogPane.getScene().setFill(Color.TRANSPARENT);
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("OK!");
        ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
        buttonBar.getButtons().forEach(b -> b.setStyle("-fx-background-radius: 10; -fx-background-color: #bb792d; -fx-font-size: 16; -fx-text-fill: white;"));
        buttonBar.getButtons().forEach(b -> b.setCursor(Cursor.HAND));
        blur();
        alert.showAndWait();
        RoundGameController.getInstance().deselectCard(0);
        selectedCardImageView.setImage(getCardImageByName("Back Image"));
        selectedCardDescriptionLabel.setText("No Card Selected");
        setInformationOfPlayersBasedOnTurn();
        setHandBasedOnTurn();
        setZonesBasedOnTurn();
        mainGamePane.setEffect(null);
    }

    private void blur() {
        ColorAdjust adj = new ColorAdjust(0, -0.9, -0.5, 0);
        GaussianBlur blur = new GaussianBlur(55);
        adj.setInput(blur);
        mainGamePane.setEffect(adj);
    }

    private void setInformationOfPlayersBasedOnTurn() {
        DuelPlayer curr = RoundGameController.getInstance().getCurrentPlayer();
        DuelPlayer opp = RoundGameController.getInstance().getOpponentPlayer();
        currentPlayerNickname.setText(curr.getNickname());
        updateLPLabels();
        currentPlayerAvatar.setImage(new Image(curr.getAvatarURL().toString()));
        opponentPlayerNickname.setText(opp.getNickname());
        opponentPlayerAvatar.setImage(new Image(opp.getAvatarURL().toString()));
    }

    private void setHandBasedOnTurn() {
        ArrayList<Card> currentPlayerHandList = (ArrayList<Card>) RoundGameController.getInstance().getCurrentPlayerHand();
        int counter = 0;
        currentHand.getChildren().clear();
        for (Card card : currentPlayerHandList) {
            ImageView cardImageView = new ImageView(getCardImageByName(card.getName()));
            cardImageView.setFitHeight(160);
            cardImageView.setFitWidth(116);
            cardImageView.setCursor(Cursor.HAND);

            int finalCounter = counter;
            cardImageView.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() != MouseButton.PRIMARY)
                    return;
                selectedCardImageView.setImage(getCardImageByName(card.getName()));
                selectedCardDescriptionLabel.setText(card.toString());
                RoundGameController.getInstance().selectCardInHand(finalCounter + 1);
            });
            currentHand.add(cardImageView, counter, 0);
            counter++;
        }
        counter = 0;
        ArrayList<Card> opponentHandList = (ArrayList<Card>) RoundGameController.getInstance().getOpponentPlayerHand();
        opponentHand.getChildren().clear();
        for (Card ignored : opponentHandList) {
            ImageView cardImageView = new ImageView(backCardImage);
            cardImageView.setFitHeight(160);
            cardImageView.setFitWidth(116);
            cardImageView.setCursor(Cursor.HAND);
            cardImageView.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() != MouseButton.PRIMARY)
                    return;
                selectedCardImageView.setImage(backCardImage);
                selectedCardDescriptionLabel.setText("Opponent Hand Card!");
            });
            opponentHand.add(cardImageView, counter, 0);
            counter++;

        }
        opponentDeckLabel.setText(String.valueOf(RoundGameController.getInstance().getOpponentPlayer().getPlayDeck().getMainCards().size()));
        currentDeckLabel.setText(String.valueOf(RoundGameController.getInstance().getCurrentPlayer().getPlayDeck().getMainCards().size()));
    }

    private void setZonesBasedOnTurn() {
        MonsterZone currentMonsterZone = RoundGameController.getInstance().getCurrentPlayer().getPlayerBoard().returnMonsterZone();
        SpellZone currentSpellZone = RoundGameController.getInstance().getCurrentPlayer().getPlayerBoard().returnSpellZone();
        System.out.println("current : "+RoundGameController.getInstance().getCurrentPlayer().getNickname());
        System.out.println("opponent : "+RoundGameController.getInstance().getOpponentPlayer().getNickname());
        //monster zone
        opponentPlayerMonsterZone.setRotate(180);
        reloadCurrentAndOpponentMonsterZone();
//        for (int i = 1; i <= 5; i++) {
//            Pane pane = (Pane) getNodeInGridPane(currentPlayerMonsterZone, 0, i - 1);
//            pane.getChildren().clear();
//            Cell monsterCell = currentMonsterZone.getCellWithAddress(i);
//            ImageView imageView = new ImageView();
//            imageView.setCursor(Cursor.HAND);
//            if (monsterCell.getCellStatus() == CellStatus.EMPTY) {
//                pane.setCursor(Cursor.DEFAULT);
//                pane.setOnMouseClicked(mouseEvent -> {
//                });
//                continue;
//            } else if (monsterCell.getCellStatus() == CellStatus.OFFENSIVE_OCCUPIED) {
//                imageView.setImage(getCardImageByName(monsterCell.getCardInCell().getName()));
//                pane.getChildren().add(imageView);
//                imageView.setFitHeight(130);
//                imageView.setFitWidth(94);
//                System.out.println("here is reched! current : , monster : "+ monsterCell.getCardInCell().getName()+ "  type : "+monsterCell.getCardInCell().getCardType() );
//            } else if (monsterCell.getCellStatus() == CellStatus.DEFENSIVE_HIDDEN) {
//                imageView.setImage(getCardImageByName("Card Back Set"));
//                pane.getChildren().add(imageView);
//                imageView.setFitWidth(130);
//                imageView.setFitHeight(94);
//                imageView.setLayoutY(imageView.getLayoutY() + 20);
//                imageView.setLayoutX(imageView.getLayoutX() - 19);
//            } else if (monsterCell.getCellStatus() == CellStatus.DEFENSIVE_OCCUPIED) {
//                imageView.setImage(getCardImageByName(monsterCell.getCardInCell().getName()));
//                imageView.setRotate(90);
//                pane.getChildren().add(imageView);
//                imageView.setFitWidth(94);
//                imageView.setFitHeight(130);
//            }
//
//            int finalI = i;
//            imageView.setOnMouseClicked(mouseEvent -> {
//                if (mouseEvent.getButton() != MouseButton.PRIMARY)
//                    return;
//                selectedCardImageView.setImage(getCardImageByName(monsterCell.getCardInCell().getName()));
//                selectedCardDescriptionLabel.setText(monsterCell.getCardInCell().toString());
//                RoundGameController.getInstance().selectCardInMonsterZone(finalI);
//
//            });
//        }
        //spellzone
        for (int i = 1; i <= 5; i++) {
            Pane pane = (Pane) getNodeInGridPane(currentPlayerSpellZone, 0, i - 1);
            pane.getChildren().clear();
            Cell spellCell = currentSpellZone.getCellWithAddress(i);
            ImageView imageView = new ImageView();
            imageView.setCursor(Cursor.HAND);
            if (spellCell.getCellStatus() == CellStatus.EMPTY) {
                pane.setCursor(Cursor.DEFAULT);
                pane.setOnMouseClicked(mouseEvent -> {
                });
                continue;
            } else if (spellCell.getCellStatus() == CellStatus.OCCUPIED) {
                imageView.setImage(getCardImageByName(spellCell.getCardInCell().getName()));
                pane.getChildren().add(imageView);
                imageView.setFitHeight(130);
                imageView.setFitWidth(94);
            } else if (spellCell.getCellStatus() == CellStatus.HIDDEN) {
                imageView.setImage(getCardImageByName("Back Image"));
                pane.getChildren().add(imageView);
                imageView.setFitWidth(94);
                imageView.setFitHeight(130);
            }
            int finalI = i;
            imageView.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() != MouseButton.PRIMARY)
                    return;
                selectedCardImageView.setImage(getCardImageByName(spellCell.getCardInCell().getName()));
                selectedCardDescriptionLabel.setText(spellCell.getCardInCell().toString());
                RoundGameController.getInstance().selectCardInSpellZone(finalI);
            });
        }
        //fieldzone

        FieldZone fieldZone = RoundGameController.getInstance().getCurrentPlayer().getPlayerBoard().getFieldZone();
        if (RoundGameController.getInstance().isFieldActivated()) {
            currentPlayerFieldSpellPane.getChildren().clear();
            if (fieldZone.getCellStatus() == CellStatus.EMPTY) {
                ImageView baseImage = new ImageView();
                baseImage.setImage(new Image(Objects.requireNonNull(getClass().getResource("/project/image/GamePictures/field.png")).toString()));
                baseImage.setFitWidth(103.0);
                baseImage.setFitHeight(126.0); //fitHeight="126.0" fitWidth="103.0"
                currentPlayerFieldSpellPane.getChildren().add(baseImage);
                currentPlayerFieldSpellPane.setCursor(Cursor.DEFAULT);
                currentPlayerFieldSpellPane.setOnMouseClicked(mouseEvent -> {
                });
            } else {
                Cell cell = fieldZone.getFieldCell();
                ImageView baseImage = new ImageView();
                baseImage.setImage(new Image(Objects.requireNonNull(getClass().getResource("/project/image/GamePictures/field.png")).toString()));
                baseImage.setFitWidth(103.0);
                baseImage.setFitHeight(126.0); //fitHeight="126.0" fitWidth="103.0"
                currentPlayerFieldSpellPane.getChildren().add(baseImage);
                ImageView imageView = new ImageView(getCardImageByName(cell.getCardInCell().getName()));
                imageView.setFitHeight(130);
                imageView.setFitWidth(105);
                currentPlayerFieldSpellPane.getChildren().add(imageView);
                currentPlayerFieldSpellPane.setCursor(Cursor.HAND);
                currentPlayerFieldSpellPane.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton() != MouseButton.PRIMARY)
                        return;
                    selectedCardImageView.setImage(getCardImageByName(cell.getCardInCell().getName()));
                    selectedCardDescriptionLabel.setText(cell.getCardInCell().toString());
                    RoundGameController.getInstance().deselectCard(0);
                });
            }
        } else {
            currentPlayerFieldSpellPane.getChildren().clear();
            ImageView baseImage = new ImageView();
            baseImage.setImage(new Image(Objects.requireNonNull(getClass().getResource("/project/image/GamePictures/field.png")).toString()));
            baseImage.setFitWidth(103.0);
            baseImage.setFitHeight(126.0); //fitHeight="126.0" fitWidth="103.0"
            currentPlayerFieldSpellPane.getChildren().add(baseImage);
            currentPlayerFieldSpellPane.setCursor(Cursor.DEFAULT);
            currentPlayerFieldSpellPane.setOnMouseClicked(mouseEvent -> {
            });
        }

        //opponent
        //monster
        MonsterZone opponentMonsterZone = RoundGameController.getInstance().getOpponentPlayer().getPlayerBoard().returnMonsterZone();
        SpellZone opponentSpellZone = RoundGameController.getInstance().getOpponentPlayer().getPlayerBoard().returnSpellZone();
        opponentPlayerMonsterZone.setRotate(180);
        opponentPlayerSpellZone.setRotate(180);
        for (int i = 1; i <= 5; i++) {
            Pane pane = (Pane) getNodeInGridPane(opponentPlayerMonsterZone, 0, i - 1);
            pane.getChildren().clear();
            Cell monsterCell = opponentMonsterZone.getCellWithAddress(i);
            ImageView imageView = new ImageView();
            imageView.setCursor(Cursor.HAND);
            if (monsterCell.getCellStatus() == CellStatus.EMPTY) {
                    pane.setCursor(Cursor.DEFAULT);
                    pane.setOnMouseClicked(mouseEvent -> {
                    });
                    continue;
            } else if (monsterCell.getCellStatus() == CellStatus.OFFENSIVE_OCCUPIED) {
                imageView.setImage(getCardImageByName(monsterCell.getCardInCell().getName()));
                imageView.setFitHeight(130);
                imageView.setFitWidth(94);
                pane.getChildren().add(imageView);
                System.out.println("here is reached! opponent, monster : "+ monsterCell.getCardInCell().getName()+ "  type : "+monsterCell.getCardInCell().getCardType() );

            } else if (monsterCell.getCellStatus() == CellStatus.DEFENSIVE_HIDDEN) {
                imageView.setImage(getCardImageByName("Card Back Set"));
                imageView.setFitWidth(130);
                imageView.setFitHeight(94);
                pane.getChildren().add(imageView);
                imageView.setLayoutY(imageView.getLayoutY() + 20);
                imageView.setLayoutX(imageView.getLayoutX() - 19);
                imageView.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton() != MouseButton.PRIMARY)
                        return;
                    selectedCardImageView.setImage(getCardImageByName("Back Image"));
                    selectedCardDescriptionLabel.setText("you can't see this card");
                });
                continue;
            } else if (monsterCell.getCellStatus() == CellStatus.DEFENSIVE_OCCUPIED) {
                imageView.setRotate(90);
                imageView.setImage(getCardImageByName(monsterCell.getCardInCell().getName()));
                imageView.setFitHeight(130);
                imageView.setFitWidth(94);
                pane.getChildren().add(imageView);
            }
            imageView.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() != MouseButton.PRIMARY)
                    return;
                selectedCardImageView.setImage(getCardImageByName(monsterCell.getCardInCell().getName()));
                selectedCardDescriptionLabel.setText(monsterCell.getCardInCell().toString());
            });
        }
        //spellzone
        for (int i = 1; i <= 5; i++) {
            Pane pane = (Pane) getNodeInGridPane(opponentPlayerSpellZone, 0, i - 1);
            pane.getChildren().clear();
            Cell spellCell = opponentSpellZone.getCellWithAddress(i);
            ImageView imageView = new ImageView();
            imageView.setCursor(Cursor.HAND);
            if (spellCell.getCellStatus() == CellStatus.EMPTY) {
                pane.setCursor(Cursor.DEFAULT);
                pane.setOnMouseClicked(mouseEvent -> {
                });
            } else if (spellCell.getCellStatus() == CellStatus.OCCUPIED) {
                imageView.setImage(getCardImageByName(spellCell.getCardInCell().getName()));
                pane.getChildren().add(imageView);
                imageView.setFitWidth(94);
                imageView.setFitHeight(130);
                imageView.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton() != MouseButton.PRIMARY)
                        return;
                    selectedCardImageView.setImage(getCardImageByName(spellCell.getCardInCell().getName()));
                    selectedCardDescriptionLabel.setText(spellCell.getCardInCell().toString());
                });
            } else if (spellCell.getCellStatus() == CellStatus.HIDDEN) {
                imageView.setImage(getCardImageByName("Back Image"));
                pane.getChildren().add(imageView);
                imageView.setFitWidth(94);
                imageView.setFitHeight(130);
                imageView.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton() != MouseButton.PRIMARY)
                        return;
                    selectedCardImageView.setImage(getCardImageByName("Back Image"));
                    selectedCardDescriptionLabel.setText("you can't see this card");
                });
            }

        }
        opponentPlayerFieldSpellPane.setRotate(180);
        fieldZone = RoundGameController.getInstance().getOpponentPlayer().getPlayerBoard().getFieldZone();
        if (RoundGameController.getInstance().isFieldActivated()) {
            opponentPlayerFieldSpellPane.getChildren().clear();

            if (fieldZone.getCellStatus() == CellStatus.EMPTY) {
                ImageView baseImage = new ImageView();
                baseImage.setImage(new Image(Objects.requireNonNull(getClass().getResource("/project/image/GamePictures/field.png")).toString()));
                baseImage.setFitWidth(103.0);
                baseImage.setFitHeight(126.0); //fitHeight="126.0" fitWidth="103.0"
                opponentPlayerFieldSpellPane.getChildren().add(baseImage);
                opponentPlayerFieldSpellPane.setCursor(Cursor.DEFAULT);
                opponentPlayerFieldSpellPane.setOnMouseClicked(mouseEvent -> {
                });
            } else {
                Cell cell = fieldZone.getFieldCell();
                ImageView baseImage = new ImageView();
                baseImage.setImage(new Image(Objects.requireNonNull(getClass().getResource("/project/image/GamePictures/field.png")).toString()));
                baseImage.setFitWidth(103.0);
                baseImage.setFitHeight(126.0); //fitHeight="126.0" fitWidth="103.0"
                opponentPlayerFieldSpellPane.getChildren().add(baseImage);
                ImageView imageView = new ImageView(getCardImageByName(cell.getCardInCell().getName()));
                imageView.setFitHeight(130);
                imageView.setFitWidth(105);
                opponentPlayerFieldSpellPane.getChildren().add(imageView);
                opponentPlayerFieldSpellPane.setCursor(Cursor.HAND);
                opponentPlayerFieldSpellPane.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton() != MouseButton.PRIMARY)
                        return;
                    selectedCardImageView.setImage(getCardImageByName(cell.getCardInCell().getName()));
                    selectedCardDescriptionLabel.setText(cell.getCardInCell().toString());
                    RoundGameController.getInstance().deselectCard(0);
                });
            }
        } else {
            opponentPlayerFieldSpellPane.getChildren().clear();
            ImageView baseImage = new ImageView();
            baseImage.setImage(new Image(Objects.requireNonNull(getClass().getResource("/project/image/GamePictures/field.png")).toString()));
            baseImage.setFitWidth(103.0);
            baseImage.setFitHeight(126.0); //fitHeight="126.0" fitWidth="103.0"
            opponentPlayerFieldSpellPane.getChildren().add(baseImage);
            opponentPlayerFieldSpellPane.setCursor(Cursor.DEFAULT);
            opponentPlayerFieldSpellPane.setOnMouseClicked(mouseEvent -> {
            });

        }

    }

    public void showButtonBasedOnPhase(Phase currentPhase) {
        nextPhaseButton.setCursor(Cursor.HAND);
        if (currentPhase == Phase.DRAW_PHASE || currentPhase == Phase.STAND_BY_PHASE) {
            summonOrActivateButton.setOnMouseClicked((Event) -> {
            });
            setButton.setOnMouseClicked((Event) -> {
            });
            attackButton.setOnMouseClicked((Event) -> {
            });
            changePositionButton.setOnMouseClicked((Event) -> {
            });
            summonOrActivateButton.setCursor(Cursor.DEFAULT);
            setButton.setCursor(Cursor.DEFAULT);
            attackButton.setCursor(Cursor.DEFAULT);
            changePositionButton.setCursor(Cursor.DEFAULT);
            summonOrActivateButton.setStyle("-fx-background-color: #323c46");
            setButton.setStyle("-fx-background-color: #323c46");
            attackButton.setStyle("-fx-background-color: #323c46");
            changePositionButton.setStyle("-fx-background-color: #323c46");
            changePositionButton.setCursor(Cursor.DEFAULT);
        } else if (currentPhase == Phase.MAIN_PHASE_1 || currentPhase == Phase.MAIN_PHASE_2) {
            attackButton.setOnMouseClicked((Event) -> {
            });
            attackButton.setCursor(Cursor.DEFAULT);
            attackButton.setStyle("-fx-background-color: #323c46");

            summonOrActivateButton.setCursor(Cursor.HAND);
            summonOrActivateButton.setOnMouseClicked(this::summonOrActivate);
            summonOrActivateButton.setStyle("-fx-background-color: #bb792d");
            setButton.setCursor(Cursor.HAND);
            setButton.setOnMouseClicked(this::set);
            setButton.setStyle("-fx-background-color: #bb792d");
            changePositionButton.setCursor(Cursor.HAND);
            changePositionButton.setOnMouseClicked(mouseEvent -> RoundGameController.getInstance().changeMonsterPosition());
            changePositionButton.setStyle("-fx-background-color: #bb792d");
        } else if (currentPhase == Phase.BATTLE_PHASE) {
            summonOrActivateButton.setOnMouseClicked((Event) -> {
            });
            setButton.setOnMouseClicked((Event) -> {
            });
            changePositionButton.setOnMouseClicked((Event) -> {
            });
            summonOrActivateButton.setCursor(Cursor.DEFAULT);
            setButton.setCursor(Cursor.DEFAULT);
            changePositionButton.setCursor(Cursor.DEFAULT);
            summonOrActivateButton.setStyle("-fx-background-color: #323c46");
            setButton.setStyle("-fx-background-color: #323c46");
            changePositionButton.setStyle("-fx-background-color: #323c46");
            attackButton.setCursor(Cursor.HAND);
            attackButton.setOnMouseClicked(this::attack);
            attackButton.setStyle("-fx-background-color: #bb792d");
        }
    }

    private void attack(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() != MouseButton.PRIMARY)
            return;
        GameViewMessage gameViewMessage = RoundGameController.getInstance().attack();
        if (gameViewMessage == GameViewMessage.SUCCESS || gameViewMessage == GameViewMessage.NONE)
            return;
        new PopUpMessage(gameViewMessage.getAlertType(), gameViewMessage.getLabel());
    }

    public void askAttackAddress() {
        for (int i = 1; i <= 5; i++) {
            Pane zonePane = (Pane) getNodeInGridPane(opponentPlayerMonsterZone, 0, i - 1);
            MonsterZone monsterZone = RoundGameController.getInstance().getOpponentPlayer().getPlayerBoard().returnMonsterZone();
            if (monsterZone.getCellWithAddress(i).getCellStatus() != CellStatus.EMPTY) {
                int finalI = i;
                Objects.requireNonNull(zonePane).setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton() != MouseButton.PRIMARY)
                        return;
                    RoundGameController.getInstance().attackToCard(finalI);
                    if (RoundGameController.getInstance().isFinishedRound() || RoundGameController.getInstance().isFinishedGame())
                        return;
                    activateAgainButtons();
                });
            } else Objects.requireNonNull(zonePane).setOnMouseClicked(mouseEvent -> {
            });
        }
        deActiveOtherThings();
    }

    public void updateLPLabels() {
        int currLP = RoundGameController.getInstance().getCurrentPlayer().getLifePoint();
        currentPlayerLP.setText("LP : " + currLP);
        currentPlayerLPBar.setProgress(1.0 * currLP / 8000);
        int oppLP = RoundGameController.getInstance().getOpponentPlayer().getLifePoint();
        opponentPlayerLP.setText("LP : " + oppLP);
        opponentPlayerLPBar.setProgress(1.0 * oppLP / 8000);
    }

    private void deActiveOtherThings() {
        nextPhaseButton.setOnMouseClicked((Event) -> {
        });
        summonOrActivateButton.setOnMouseClicked((Event) -> {
        });
        setButton.setOnMouseClicked((Event) -> {
        });
        attackButton.setOnMouseClicked((Event) -> {
        });
        changePositionButton.setOnMouseClicked((Event) -> {
        });
        summonOrActivateButton.setCursor(Cursor.DEFAULT);
        setButton.setCursor(Cursor.DEFAULT);
        attackButton.setCursor(Cursor.DEFAULT);
        changePositionButton.setCursor(Cursor.DEFAULT);
        summonOrActivateButton.setStyle("-fx-background-color: #323c46");
        setButton.setStyle("-fx-background-color: #323c46");
        attackButton.setStyle("-fx-background-color: #323c46");
        changePositionButton.setStyle("-fx-background-color: #323c46");
        changePositionButton.setCursor(Cursor.DEFAULT);
        nextPhaseButton.setCursor(Cursor.DEFAULT);
        nextPhaseButton.setStyle("-fx-background-color: #323c46");
        for (int i = 1; i <= 5; i++) {
            Pane zonePane = (Pane) getNodeInGridPane(opponentPlayerSpellZone, 0, i - 1);
            Objects.requireNonNull(zonePane).setOnMouseClicked(mouseEvent -> {
            });
            zonePane = (Pane) getNodeInGridPane(currentPlayerSpellZone, 0, i - 1);
            Objects.requireNonNull(zonePane).setOnMouseClicked(mouseEvent -> {
            });
        }
        for (int i = 0; i < currentHand.getChildren().size(); i++) {
            Node node = getNodeInGridPane(currentHand, 0, i);
            if (node != null)
                node.setOnMouseClicked((Event) -> {
                });
        }
        for (int i = 0; i < opponentHand.getChildren().size(); i++) {
            Node node = getNodeInGridPane(opponentHand, 0, i);
            if (node != null)
                node.setOnMouseClicked((Event) -> {
                });
        }
    }

    private void activateAgainButtons() {
        selectedCardDescriptionLabel.setText("No card selected");
        selectedCardImageView.setImage(getCardImageByName("Back Image"));
        attackButton.setOnMouseClicked(this::attack);
        nextPhaseButton.setOnMouseClicked(this::nextPhase);
        nextPhaseButton.setCursor(Cursor.HAND);
        attackButton.setCursor(Cursor.HAND);
        nextPhaseButton.setStyle("-fx-background-color: #bb792d");
        attackButton.setStyle("-fx-background-color: #bb792d");
        for (int i = 1; i <= 5; i++) {
            Pane zonePane = (Pane) getNodeInGridPane(opponentPlayerMonsterZone, 0, i - 1);
            MonsterZone monsterZone = RoundGameController.getInstance().getOpponentPlayer().getPlayerBoard().returnMonsterZone();
            Cell cell;
            if ((cell = monsterZone.getCellWithAddress(i)).getCellStatus() != CellStatus.EMPTY) {
                Cell finalCell1 = cell;
                zonePane.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton() != MouseButton.PRIMARY)
                        return;
                    if (finalCell1.getCellStatus() == CellStatus.HIDDEN)
                        return;
                    selectedCardImageView.setImage(getCardImageByName(finalCell1.getCardInCell().getName()));
                    selectedCardDescriptionLabel.setText(finalCell1.getCardInCell().toString());
                });
            }
            zonePane = (Pane) getNodeInGridPane(currentPlayerMonsterZone, 0, i - 1);
            int finalI = i;
            monsterZone = RoundGameController.getInstance().getCurrentPlayer().getPlayerBoard().returnMonsterZone();
            if ((cell = monsterZone.getCellWithAddress(i)).getCellStatus() != CellStatus.EMPTY) {
                Cell finalCell = cell;
                Objects.requireNonNull(zonePane).setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton() != MouseButton.PRIMARY)
                        return;
                    selectedCardImageView.setImage(getCardImageByName(finalCell.getCardInCell().getName()));
                    selectedCardDescriptionLabel.setText(finalCell.getCardInCell().toString());
                    RoundGameController.getInstance().selectCardInMonsterZone(finalI);
                });
            }
        }
        for (int i = 1; i <= 5; i++) {
            Pane zonePane = (Pane) getNodeInGridPane(opponentPlayerSpellZone, 0, i - 1);
            SpellZone spellZone = RoundGameController.getInstance().getOpponentPlayer().getPlayerBoard().returnSpellZone();
            Cell cell;
            if ((cell = spellZone.getCellWithAddress(i)).getCellStatus() != CellStatus.EMPTY) {
                Cell finalCell1 = cell;
                Objects.requireNonNull(zonePane).setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton() != MouseButton.PRIMARY)
                        return;
                    if (finalCell1.getCellStatus() == CellStatus.HIDDEN)
                        return;
                    selectedCardImageView.setImage(getCardImageByName(finalCell1.getCardInCell().getName()));
                    selectedCardDescriptionLabel.setText(finalCell1.getCardInCell().toString());
                });
            }
            zonePane = (Pane) getNodeInGridPane(currentPlayerSpellZone, 0, i - 1);
            spellZone = RoundGameController.getInstance().getCurrentPlayer().getPlayerBoard().returnSpellZone();
            if ((cell = spellZone.getCellWithAddress(i)).getCellStatus() != CellStatus.EMPTY) {
                int finalI = i;
                Cell finalCell = cell;
                zonePane.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton() != MouseButton.PRIMARY)
                        return;
                    selectedCardImageView.setImage(getCardImageByName(finalCell.getCardInCell().getName()));
                    selectedCardDescriptionLabel.setText(finalCell.getCardInCell().toString());
                    RoundGameController.getInstance().selectCardInSpellZone(finalI);
                });
            }
        }
        for (int i = 0; i < currentHand.getChildren().size(); i++) {
            Node node = getNodeInGridPane(currentHand, 0, i);
            ArrayList<Card> hand = (ArrayList<Card>) RoundGameController.getInstance().getCurrentPlayerHand();
            Card card = hand.get(i);
            if (node != null) {
                int finalI = i + 1;
                node.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton() != MouseButton.PRIMARY)
                        return;
                    selectedCardImageView.setImage(getCardImageByName(card.getName()));
                    selectedCardDescriptionLabel.setText(card.toString());
                    RoundGameController.getInstance().selectCardInHand(finalI);
                });
            }
        }
        for (int i = 0; i < opponentHand.getChildren().size(); i++) {
            Node node = getNodeInGridPane(opponentHand, 0, i);
            if (node != null)
                node.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton() != MouseButton.PRIMARY)
                        return;
                    selectedCardImageView.setImage(getCardImageByName("Back Image"));
                    selectedCardDescriptionLabel.setText("Cant see this card");
                });
        }
    }

    public void summonOrActivate(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() != MouseButton.PRIMARY)
            return;
        AudioClip onClick = new AudioClip(Objects.requireNonNull(getClass().getResource("/project/soundEffects/CURSOR.wav")).toString());
        onClick.play();
        GameViewMessage message = RoundGameController.getInstance().summonOrActivate();
        if (message != GameViewMessage.SUCCESS && message != null && message != GameViewMessage.NONE) {
            GamePopUpMessage.setStage(LoginMenuView.getStage());
            new GamePopUpMessage(message.getAlertType(), message.getLabel());
        }
    }

    public void set(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() != MouseButton.PRIMARY)
            return;
        AudioClip onClick = new AudioClip(Objects.requireNonNull(getClass().getResource("/project/soundEffects/CURSOR.wav")).toString());
        onClick.play();
        GameViewMessage message = RoundGameController.getInstance().setCard();
        if (message == GameViewMessage.NONE || message == null)
            return;
        else if (message != GameViewMessage.SUCCESS)
            new GamePopUpMessage(message.getAlertType(), message.getLabel());

    }

    public TranslateTransition showSummon(int addressInMonsterZone, int addressInHand, String cardName) {
        ImageView fakeCardImageView = new ImageView(getCardImageByName(cardName));
        fakeCardImageView.setFitWidth(94);
        fakeCardImageView.setFitHeight(130);
        GridPane handPane = currentHand;
        //Translate transition :
        TranslateTransition translateTransition = new TranslateTransition();
        int addressOfAddInMonsterZoneGrid = addressInMonsterZone - 1;//zero based!
        int addressInHandGrid = addressInHand - 1;
        Node inHandNode = getNodeInGridPane(handPane, 0, addressInHandGrid);
        if (inHandNode == null) {
            inHandNode = getNodeInGridPane(handPane, 0, addressInHandGrid + 1);
        }
        Point2D inHandPoint;
        if (inHandNode == null)
            inHandPoint = new Point2D(306.0, 694.0);
        else inHandPoint = inHandNode.localToScene(new Point2D(0, 0));
        Node inZoneNode;
        inZoneNode = getNodeInGridPane(currentPlayerMonsterZone, 0, addressOfAddInMonsterZoneGrid);
        Point2D zonePoint;
        if (inZoneNode == null) {
            zonePoint = new Point2D(0, 0);
        } else {
            zonePoint = inZoneNode.localToScene(new Point2D(0, 0));
        }
        translateTransition.setNode(fakeCardImageView);
        translateTransition.setFromX(inHandPoint.getX());
        translateTransition.setFromY(inHandPoint.getY());
        translateTransition.setToX(zonePoint.getX());
        translateTransition.setToY(zonePoint.getY());
        translateTransition.setDuration(Duration.millis(800));
        translateTransition.setOnFinished(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ImageView imageView = new ImageView(getCardImageByName(cardName));
                imageView.setFitHeight(130);
                imageView.setFitWidth(94);
                imageView.setCursor(Cursor.HAND);
                reloadCurrentAndOpponentMonsterZone();
                cardBoardPane.getChildren().remove(fakeCardImageView);
                mainGamePane.getChildren().remove(fakeCardImageView);
                ((Pane) inZoneNode).getChildren().add(imageView);
                reloadCurrentHand();
            }
        });

        mainGamePane.getChildren().add(fakeCardImageView);
        currentHand.getChildren().remove(inHandNode);
        return translateTransition;
    }

    public void reloadCurrentHand() {
        currentHand.getChildren().clear();
        ArrayList<Card> currentPlayerHandList = (ArrayList<Card>) RoundGameController.getInstance().getCurrentPlayerHand();
        int counter = 0;
        for (Card card : currentPlayerHandList) {
            ImageView cardImageView = new ImageView(getCardImageByName(card.getName()));
            cardImageView.setFitHeight(160);
            cardImageView.setFitWidth(116);
            cardImageView.setCursor(Cursor.HAND);
            int finalCounter = counter;
            cardImageView.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() != MouseButton.PRIMARY)
                    return;
                selectedCardImageView.setImage(getCardImageByName(card.getName()));
                selectedCardDescriptionLabel.setText(card.toString());
                RoundGameController.getInstance().selectCardInHand(finalCounter + 1);
            });
            currentHand.add(cardImageView, counter, 0);
            counter++;
        }
    }

    public TranslateTransition showSetMonsterTransition(int addressInMonsterZone, int addressInHand) {
        ImageView fakeCardImageView = new ImageView(getCardImageByName("Card Back Set"));
        fakeCardImageView.setFitHeight(94);
        fakeCardImageView.setFitWidth(130);
        GridPane handPane = currentHand;
        //Translate transition :
        TranslateTransition translateTransition = new TranslateTransition();
        int addressOfAddInMonsterZoneGrid = addressInMonsterZone - 1;//zero based!
        int addressInHandGrid = addressInHand - 1;
        Node inHandNode = getNodeInGridPane(handPane, 0, addressInHandGrid);
        Point2D inHandPoint;
        if (inHandNode != null) {
            inHandPoint = inHandNode.localToScene(new Point2D(0, 0));
        } else inHandPoint = new Point2D(306.0, 694.0);
        Node inZoneNode;
        inZoneNode = getNodeInGridPane(currentPlayerMonsterZone, 0, addressOfAddInMonsterZoneGrid);
        Point2D zonePoint;
        if (inZoneNode == null) {
            zonePoint = new Point2D(0, 0);
        } else {
            zonePoint = inZoneNode.localToScene(new Point2D(0, 0));
        }
        translateTransition.setNode(fakeCardImageView);
        translateTransition.setFromX(inHandPoint.getX());
        translateTransition.setFromY(inHandPoint.getY());
        translateTransition.setToX(zonePoint.getX() - 19);
        translateTransition.setToY(zonePoint.getY() + 20);
        translateTransition.setDuration(Duration.millis(800));
        translateTransition.setOnFinished(actionEvent -> {
            ImageView imageView = new ImageView(getCardImageByName("Card Back Set"));
            imageView.setFitHeight(94);
            imageView.setFitWidth(130);
            imageView.setCursor(Cursor.HAND);
            cardBoardPane.getChildren().remove(fakeCardImageView);
            mainGamePane.getChildren().remove(fakeCardImageView);
            ((Pane) Objects.requireNonNull(inZoneNode)).getChildren().add(imageView);
            imageView.setLayoutY(imageView.getLayoutY() + 20);
            imageView.setLayoutX(imageView.getLayoutX() - 19);
            reloadCurrentAndOpponentMonsterZone();
            reloadCurrentHand();
        });

        mainGamePane.getChildren().add(fakeCardImageView);
        currentHand.getChildren().remove(inHandNode);
        return translateTransition;
    }

    public synchronized void playAnimation(Animation animation, String cardName, int addressInZone, int addressInHand, int addressInOppZone, boolean isCurrent) {
        TranslateTransition tt;
        switch (animation) {
            case DRAW_CARD:
                tt = drawCardFromDeckAnimation(cardName, isCurrent);
                tt.play();
                break;
            case SUMMON_MONSTER:
                tt = showSummon(addressInZone, addressInHand, cardName);
                tt.play();
                break;
            case SET_MONSTER:
                tt = showSetMonsterTransition(addressInZone, addressInHand);
                tt.play();
                break;
            case MONSTER_ZONE_TO_GRAVEYARD:
                showAddToGraveYardFromMonsterZoneAnimation(addressInZone, isCurrent, cardName);
                break;
            case HAND_TO_GRAVEYARD:
                showAddToGraveYardFromHandAnimation();
                break;
            case NORMAL_ATTACK:
                showNormalAttack(addressInZone, addressInOppZone, isCurrent);
                break;
            case DIRECT_ATTACK:
                showDirectAttack(addressInZone);
                break;
            case SPELL_TO_GRAVEYARD:
                showSpellToGraveYard();
                break;
            case SET_SPELL:
                showSetSpell(addressInZone, addressInHand);
                break;
            case FIELD_SPELL:
                showActivateFieldSpell(cardName, addressInHand);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + animation);
        }
    }

    public void showActivateFieldSpell(String cardName, int addressInHand) {
        ImageView fakeCardImageView = new ImageView(getCardImageByName(cardName));
        fakeCardImageView.setFitWidth(94);
        fakeCardImageView.setFitHeight(130);
        GridPane handPane = currentHand;
        //Translate transition :
        TranslateTransition translateTransition = new TranslateTransition();
        int addressInHandGrid = addressInHand - 1;
        Node inHandNode = getNodeInGridPane(handPane, 0, addressInHandGrid);
        Point2D inHandPoint;
        if (inHandNode == null) {
            inHandPoint = new Point2D(306.0, 694.0);
        } else inHandPoint = inHandNode.localToScene(new Point2D(0, 0));
        Point2D zonePoint;
        zonePoint = currentPlayerFieldSpellPane.localToScene(new Point2D(0, 0));
        translateTransition.setNode(fakeCardImageView);
        translateTransition.setFromX(inHandPoint.getX());
        translateTransition.setFromY(inHandPoint.getY());
        translateTransition.setToX(zonePoint.getX());
        translateTransition.setToY(zonePoint.getY());
        translateTransition.setDuration(Duration.millis(500));
        translateTransition.setOnFinished(actionEvent -> {
            ImageView imageView = new ImageView(getCardImageByName(cardName));
            imageView.setFitHeight(130);
            imageView.setFitWidth(94);
            Objects.requireNonNull(currentPlayerFieldSpellPane).getChildren().add(imageView);
            cardBoardPane.getChildren().remove(fakeCardImageView);
            mainGamePane.getChildren().remove(fakeCardImageView);
            reloadCurrentHand();
            reloadCurrentAndOpponentSpellZone();
        });

        mainGamePane.getChildren().add(fakeCardImageView);
        currentHand.getChildren().remove(inHandNode);
        translateTransition.play();
    }

    private void showSetSpell(int addressInSpellZone, int addressInHand) {
        ImageView fakeCardImageView = new ImageView(getCardImageByName("Back Image"));
        fakeCardImageView.setFitWidth(94);
        fakeCardImageView.setFitHeight(130);
        GridPane handPane = currentHand;
        //Translate transition :
        TranslateTransition translateTransition = new TranslateTransition();
        int addressOfAddInSpellZoneGrid = addressInSpellZone - 1;//zero based!
        int addressInHandGrid = addressInHand - 1;
        Node inHandNode = getNodeInGridPane(handPane, 0, addressInHandGrid);
        Point2D inHandPoint;
        if (inHandNode == null) {
            inHandPoint = new Point2D(306.0, 694.0);
        } else
            inHandPoint = inHandNode.localToScene(new Point2D(0, 0));
        Node inZoneNode = getNodeInGridPane(currentPlayerSpellZone, 0, addressOfAddInSpellZoneGrid);
        Point2D zonePoint;
        if (inZoneNode == null) {
            zonePoint = new Point2D(0, 0);
        } else {
            zonePoint = inZoneNode.localToScene(new Point2D(0, 0));
        }
        translateTransition.setNode(fakeCardImageView);
        translateTransition.setFromX(inHandPoint.getX());
        translateTransition.setFromY(inHandPoint.getY());
        translateTransition.setToX(zonePoint.getX());
        translateTransition.setToY(zonePoint.getY());
        translateTransition.setDuration(Duration.millis(500));
        translateTransition.setOnFinished(actionEvent -> {
            ImageView imageView = new ImageView(getCardImageByName("Back Image"));
            imageView.setFitHeight(130);
            imageView.setFitWidth(94);
            ((Pane) Objects.requireNonNull(inZoneNode)).getChildren().add(imageView);
            cardBoardPane.getChildren().remove(fakeCardImageView);
            mainGamePane.getChildren().remove(fakeCardImageView);
            reloadCurrentHand();
            reloadCurrentAndOpponentSpellZone();
        });

        mainGamePane.getChildren().add(fakeCardImageView);
        currentHand.getChildren().remove(inHandNode);
        translateTransition.play();

    }

    private void showDirectAttack(int addressInMonsterZone) {
        ImageView imageView = new ImageView(Objects.requireNonNull(getClass().getResource("/project/image/GamePictures/sword.png")).toString());
        imageView.setFitHeight(120);
        imageView.setFitWidth(74);
        imageView.setRotate(180);
        TranslateTransition translateTransition = new TranslateTransition();
        int addressInMonsterZoneGrid = addressInMonsterZone - 1;//zero based!
        Node inZoneNode = getNodeInGridPane(currentPlayerMonsterZone, 0, addressInMonsterZoneGrid);
        Point2D zonePoint;
        if (inZoneNode == null) {
            zonePoint = new Point2D(0, 0);
        } else {
            zonePoint = inZoneNode.localToScene(new Point2D(0, 0));
        }
        translateTransition.setNode(imageView);
        translateTransition.setFromX(zonePoint.getX());
        translateTransition.setFromY(zonePoint.getY());
        translateTransition.setToX(zonePoint.getX());
        translateTransition.setToY(5);
        translateTransition.setDuration(Duration.millis(700));
        mainGamePane.getChildren().add(imageView);
        translateTransition.setOnFinished(actionEvent -> {
            mainGamePane.getChildren().remove(imageView);
            updateLPLabels();
        });
        translateTransition.play();
    }

    private void showNormalAttack(int addressInMonsterZone, int addressInOpponentZone, boolean isCurrent) {
        ImageView imageView = new ImageView(Objects.requireNonNull(getClass().getResource("/project/image/GamePictures/sword.png")).toString());
        imageView.setFitHeight(110);
        imageView.setFitWidth(60);
        GridPane startGrid = isCurrent ? currentPlayerMonsterZone : opponentPlayerMonsterZone;
        GridPane destGrid = isCurrent ? opponentPlayerMonsterZone : currentPlayerMonsterZone;
        TranslateTransition tt = new TranslateTransition();
        int addressInCurrentMonsterZoneGrid = addressInMonsterZone - 1;//zero based!
        int addressInOpponentMonsterZoneGrid = addressInOpponentZone - 1;//zero based
        Node inCurrZoneNode = getNodeInGridPane(startGrid, 0, addressInCurrentMonsterZoneGrid);
        Node inOpponentZoneNode = getNodeInGridPane(destGrid, 0, addressInOpponentMonsterZoneGrid);
        Point2D currentZonePoint = inCurrZoneNode.localToScene(new Point2D(0, 0));
        Point2D oppZonePoint = inOpponentZoneNode.localToScene(new Point2D(0, 0));

        tt.setNode(imageView);
        tt.setFromX(currentZonePoint.getX());
        tt.setFromY(currentZonePoint.getY());
        tt.setToX(oppZonePoint.getX() - 90);
        tt.setToY(oppZonePoint.getY() - 110);
        double rotate;
        try {
            rotate = Math.toDegrees(Math.atan(((oppZonePoint.getX() - 70 - currentZonePoint.getX()) / (oppZonePoint.getY() - 110 - currentZonePoint.getY()))));
        } catch (ArithmeticException e) {
            rotate = 0;
        }
        imageView.setRotate(180 + (-rotate));
        tt.setDuration(Duration.millis(700));
        mainGamePane.getChildren().add(imageView);
        tt.setOnFinished(actionEvent -> {
            mainGamePane.getChildren().remove(imageView);
            reloadCurrentAndOpponentMonsterZone();
            updateLPLabels();
        });
        tt.play();
    }

    public void showActivateEffectOfSpellFromHand(int addressInSpellZone, int addressInHand, String cardName) {
        ImageView fakeCardImageView = new ImageView(getCardImageByName(cardName));
        fakeCardImageView.setFitWidth(94);
        fakeCardImageView.setFitHeight(130);
        GridPane handPane = currentHand;
        //Translate transition :
        TranslateTransition translateTransition = new TranslateTransition();
        int addressOfAddInSpellZoneGrid = addressInSpellZone - 1;//zero based!
        int addressInHandGrid = addressInHand - 1;

        Node inHandNode = getNodeInGridPane(handPane, 0, addressInHandGrid);
        Point2D inHandPoint;
        if (inHandNode == null) {
            inHandPoint = new Point2D(306.0, 694.0);
        } else inHandPoint = inHandNode.localToScene(new Point2D(0, 0));
        Node inZoneNode = getNodeInGridPane(currentPlayerSpellZone, 0, addressOfAddInSpellZoneGrid);
        Point2D zonePoint;
        if (inZoneNode == null) {
            zonePoint = new Point2D(0, 0);
        } else {
            zonePoint = inZoneNode.localToScene(new Point2D(0, 0));
        }
        translateTransition.setNode(fakeCardImageView);
        translateTransition.setFromX(inHandPoint.getX());
        translateTransition.setFromY(inHandPoint.getY());
        translateTransition.setToX(zonePoint.getX());
        translateTransition.setToY(zonePoint.getY());
        translateTransition.setDuration(Duration.millis(500));
        translateTransition.setOnFinished(actionEvent -> {
            ImageView imageView = new ImageView(getCardImageByName(cardName));
            imageView.setFitHeight(130);
            imageView.setFitWidth(94);
            ((Pane) Objects.requireNonNull(inZoneNode)).getChildren().add(imageView);
            cardBoardPane.getChildren().remove(fakeCardImageView);
            mainGamePane.getChildren().remove(fakeCardImageView);
            reloadCurrentHand();
            reloadCurrentAndOpponentSpellZone();
        });

        mainGamePane.getChildren().add(fakeCardImageView);
        currentHand.getChildren().remove(inHandNode);
        translateTransition.play();
    }

    public void showActivateEffectOfSpellInZone() {
        reloadCurrentSpellZone();
    }

    public void reloadCurrentSpellZone() {
        SpellZone spellZone = RoundGameController.getInstance().getCurrentPlayer().getPlayerBoard().returnSpellZone();
        for (int i = 1; i <= 5; i++) {
            Pane zonePane = (Pane) getNodeInGridPane(currentPlayerSpellZone, 0, i - 1);

            Cell cell;
            Objects.requireNonNull(zonePane).getChildren().clear();
            if ((cell = spellZone.getCellWithAddress(i)).getCellStatus() != CellStatus.EMPTY) {
                int finalI = i;
                ImageView imageView = new ImageView(getCardImageByName(cell.getCardInCell().getName()));
                if (cell.getCellStatus() == CellStatus.HIDDEN) {
                    imageView.setImage(getCardImageByName("Back Image"));
                    imageView.setFitWidth(94);
                    imageView.setFitHeight(130);
                } else {
                    imageView.setImage(getCardImageByName(cell.getCardInCell().getName()));
                    imageView.setFitHeight(130);
                    imageView.setFitWidth(94);
                }
                zonePane.getChildren().add(imageView);
                zonePane.setCursor(Cursor.HAND);
                zonePane.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton() != MouseButton.PRIMARY)
                        return;
                    selectedCardImageView.setImage(getCardImageByName(cell.getCardInCell().getName()));
                    selectedCardDescriptionLabel.setText(cell.getCardInCell().toString());
                    RoundGameController.getInstance().selectCardInSpellZone(finalI);
                });
            } else {
                zonePane.getChildren().clear();
                zonePane.setOnMouseClicked(mouseEvent -> {
                });
                zonePane.setCursor(Cursor.DEFAULT);
            }
        }
        FieldZone fieldZone = RoundGameController.getInstance().getCurrentPlayer().getPlayerBoard().getFieldZone();
        if (RoundGameController.getInstance().isFieldActivated()) {
            currentPlayerFieldSpellPane.getChildren().clear();
            ImageView baseImage = new ImageView();
            baseImage.setImage(new Image(Objects.requireNonNull(getClass().getResource("/project/image/GamePictures/field.png")).toString()));
            baseImage.setFitWidth(103.0);
            baseImage.setFitHeight(126.0);
            currentPlayerFieldSpellPane.getChildren().add(baseImage);
            if (fieldZone.getCellStatus() != CellStatus.EMPTY) {
                Cell cell = fieldZone.getFieldCell();
                ImageView imageView = new ImageView(getCardImageByName(cell.getCardInCell().getName()));
                imageView.setFitHeight(130);
                imageView.setFitWidth(105);
                currentPlayerFieldSpellPane.getChildren().add(imageView);
                currentPlayerFieldSpellPane.setCursor(Cursor.HAND);
                currentPlayerFieldSpellPane.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton() != MouseButton.PRIMARY)
                        return;
                    selectedCardImageView.setImage(getCardImageByName(cell.getCardInCell().getName()));
                    selectedCardDescriptionLabel.setText(cell.getCardInCell().toString());
                    RoundGameController.getInstance().deselectCard(0);
                });
            } else {
                currentPlayerFieldSpellPane.setCursor(Cursor.DEFAULT);
                currentPlayerFieldSpellPane.setOnMouseClicked(mouseEvent -> {
                });
            }
        } else {
            currentPlayerFieldSpellPane.getChildren().clear();
            ImageView baseImage = new ImageView();
            baseImage.setImage(new Image(Objects.requireNonNull(getClass().getResource("/project/image/GamePictures/field.png")).toString()));
            baseImage.setFitWidth(103.0);
            baseImage.setFitHeight(126.0);
            currentPlayerFieldSpellPane.getChildren().add(baseImage);
        }
    }

    public void reloadOpponentSpellZone() {
        for (int i = 1; i <= 5; i++) {
            Pane zonePane = (Pane) getNodeInGridPane(opponentPlayerSpellZone, 0, i - 1);
            SpellZone spellZone = RoundGameController.getInstance().getOpponentPlayer().getPlayerBoard().returnSpellZone();
            Cell cell;
            Objects.requireNonNull(zonePane).getChildren().clear();
            if ((cell = spellZone.getCellWithAddress(i)).getCellStatus() != CellStatus.EMPTY) {
                ImageView imageView = new ImageView(getCardImageByName(cell.getCardInCell().getName()));

                if (cell.getCellStatus() == CellStatus.HIDDEN) {
                    imageView.setImage(getCardImageByName("Back Image"));
                    imageView.setFitWidth(94);
                    imageView.setFitHeight(130);
                    zonePane.setCursor(Cursor.HAND);
                    zonePane.setOnMouseClicked(mouseEvent -> {
                        if (mouseEvent.getButton() != MouseButton.PRIMARY)
                            return;
                        selectedCardImageView.setImage(getCardImageByName("Back Image"));
                        selectedCardDescriptionLabel.setText("you can't see this card");
                    });
                    zonePane.getChildren().add(imageView);
                    continue;
                } else {
                    imageView.setFitHeight(130);
                    imageView.setFitWidth(94);
                }
                zonePane.getChildren().add(imageView);
                zonePane.setCursor(Cursor.HAND);
                zonePane.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton() != MouseButton.PRIMARY)
                        return;
                    selectedCardImageView.setImage(getCardImageByName(cell.getCardInCell().getName()));
                    selectedCardDescriptionLabel.setText(cell.getCardInCell().toString());
                    RoundGameController.getInstance().deselectCard(0);
                });
            } else {
                zonePane.getChildren().clear();
                zonePane.setOnMouseClicked(mouseEvent -> {
                });
                zonePane.setCursor(Cursor.DEFAULT);
            }
        }
        FieldZone fieldZone = RoundGameController.getInstance().getOpponentPlayer().getPlayerBoard().getFieldZone();
        opponentPlayerFieldSpellPane.setRotate(180);
        if (RoundGameController.getInstance().isFieldActivated()) {
            opponentPlayerFieldSpellPane.getChildren().clear();
            ImageView baseImage = new ImageView();
            baseImage.setImage(new Image(Objects.requireNonNull(getClass().getResource("/project/image/GamePictures/field.png")).toString()));
            baseImage.setFitWidth(103.0);
            baseImage.setFitHeight(126.0);
            opponentPlayerFieldSpellPane.getChildren().add(baseImage);
            if (fieldZone.getCellStatus() != CellStatus.EMPTY) {
                Cell cell = fieldZone.getFieldCell();
                ImageView imageView = new ImageView(getCardImageByName(cell.getCardInCell().getName()));
                imageView.setFitHeight(130);
                imageView.setFitWidth(105);
                opponentPlayerFieldSpellPane.getChildren().add(imageView);
                opponentPlayerFieldSpellPane.setCursor(Cursor.HAND);
                opponentPlayerFieldSpellPane.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton() != MouseButton.PRIMARY)
                        return;
                    selectedCardImageView.setImage(getCardImageByName(cell.getCardInCell().getName()));
                    selectedCardDescriptionLabel.setText(cell.getCardInCell().toString());
                });
            } else {
                opponentPlayerFieldSpellPane.setCursor(Cursor.DEFAULT);
                opponentPlayerFieldSpellPane.setOnMouseClicked(mouseEvent -> {
                });
            }
        } else {
            opponentPlayerFieldSpellPane.getChildren().clear();
            ImageView baseImage = new ImageView();
            baseImage.setImage(new Image(Objects.requireNonNull(getClass().getResource("/project/image/GamePictures/field.png")).toString()));
            baseImage.setFitWidth(103.0);
            baseImage.setFitHeight(126.0);
            opponentPlayerFieldSpellPane.getChildren().add(baseImage);
        }
    }

    public void showSpellToGraveYard() {
        reloadCurrentAndOpponentSpellZone();
    }

    private void reloadCurrentAndOpponentSpellZone() {
        reloadCurrentSpellZone();
        reloadOpponentSpellZone();
    }

    public void showAddToGraveYardFromMonsterZoneAnimation(int address, boolean isCurrentPlayer, String cardName) {
        ImageView fakeCardImageView = new ImageView(getCardImageByName(cardName));
        fakeCardImageView.setFitWidth(94);
        fakeCardImageView.setFitHeight(130);

        GridPane zonePane = isCurrentPlayer ? currentPlayerMonsterZone : opponentPlayerMonsterZone;

        Pane monsterZoneNode = (Pane) getNodeInGridPane(zonePane, 0, address - 1);
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(2000));
        fade.setFromValue(100);
        fade.setToValue(0.0);
        fade.setNode(Objects.requireNonNull(monsterZoneNode).getChildren().get(0));
        fade.setOnFinished(actionEvent -> reloadCurrentAndOpponentMonsterZone());
        fade.play();
    }

    public void reloadCurrentAndOpponentMonsterZone() {
        for (int i = 1; i <= 5; i++) {
            Pane zonePane = (Pane) getNodeInGridPane(currentPlayerMonsterZone, 0, i - 1);
            MonsterZone monsterZone = RoundGameController.getInstance().getCurrentPlayer().getPlayerBoard().returnMonsterZone();
            Cell cell;
            Objects.requireNonNull(zonePane).getChildren().clear();
            if ((cell = monsterZone.getCellWithAddress(i)).getCellStatus() != CellStatus.EMPTY) {
                int finalI = i;
                Cell finalCell1 = cell;
                ImageView imageView = new ImageView(getCardImageByName(cell.getCardInCell().getName()));

                if (cell.getCellStatus() == CellStatus.DEFENSIVE_OCCUPIED) {
                    imageView.setImage(getCardImageByName(cell.getCardInCell().getName()));
                    imageView.setRotate(90);
                    imageView.setFitWidth(94);
                    imageView.setFitHeight(130);
                } else if (cell.getCellStatus() == CellStatus.DEFENSIVE_HIDDEN) {
                    imageView.setImage(getCardImageByName("Card Back Set"));
                    imageView.setFitWidth(130);
                    imageView.setFitHeight(94);
                    imageView.setLayoutX(imageView.getLayoutX() - 20);
                    imageView.setLayoutY(imageView.getLayoutY() + 19);
                } else {
                    imageView.setFitHeight(130);
                    imageView.setFitWidth(94);
                }
                zonePane.getChildren().add(imageView);
                zonePane.setCursor(Cursor.HAND);
                zonePane.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton() != MouseButton.PRIMARY)
                        return;
                    selectedCardImageView.setImage(getCardImageByName(finalCell1.getCardInCell().getName()));
                    selectedCardDescriptionLabel.setText(finalCell1.getCardInCell().toString());
                    RoundGameController.getInstance().selectCardInMonsterZone(finalI);
                });
            } else {
                zonePane.setOnMouseClicked(mouseEvent -> {
                });
                zonePane.setCursor(Cursor.DEFAULT);
                zonePane.getChildren().clear();
            }
            //opponent
            zonePane = (Pane) getNodeInGridPane(opponentPlayerMonsterZone, 0, i - 1);
            monsterZone = RoundGameController.getInstance().getOpponentPlayer().getPlayerBoard().returnMonsterZone();
            Objects.requireNonNull(zonePane).getChildren().clear();
            if ((cell = monsterZone.getCellWithAddress(i)).getCellStatus() != CellStatus.EMPTY) {
                Cell finalCell = cell;
                ImageView imageView = new ImageView(getCardImageByName(cell.getCardInCell().getName()));
                imageView.setFitHeight(130);
                imageView.setFitWidth(94);
                if (cell.getCellStatus() == CellStatus.DEFENSIVE_OCCUPIED) {
                    imageView.setRotate(90);
                } else if (cell.getCellStatus() == CellStatus.DEFENSIVE_HIDDEN) {
                    imageView.setImage(getCardImageByName("Card Back Set"));
                    imageView.setFitWidth(130);
                    imageView.setFitHeight(94);
                    imageView.setLayoutX(imageView.getLayoutX() - 19);
                    imageView.setLayoutY(imageView.getLayoutY() + 20);
                    zonePane.setOnMouseClicked(mouseEvent -> {
                        if (mouseEvent.getButton() != MouseButton.PRIMARY)
                            return;
                        selectedCardImageView.setImage(getCardImageByName("Back Image"));
                        selectedCardDescriptionLabel.setText("Can't show this card to you");
                    });
                    zonePane.setCursor(Cursor.HAND);
                    zonePane.getChildren().add(imageView);
                    continue;
                }
                zonePane.getChildren().add(imageView);
                zonePane.setCursor(Cursor.HAND);
                zonePane.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton() != MouseButton.PRIMARY)
                        return;
                    if (finalCell.getCellStatus() == CellStatus.DEFENSIVE_HIDDEN)
                        return;
                    selectedCardImageView.setImage(getCardImageByName(finalCell.getCardInCell().getName()));
                    selectedCardDescriptionLabel.setText(finalCell.getCardInCell().toString());
                });
            } else {
                zonePane.setOnMouseClicked(mouseEvent -> {

                });
                zonePane.setCursor(Cursor.DEFAULT);
                zonePane.getChildren().clear();
            }
        }
    }

    public void showAddToGraveYardFromHandAnimation() {
        reloadCurrentHand();
    }

    public void nextTrack(MouseEvent actionEvent) {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        Music.nextTrack(playPauseMusicButton, muteUnmuteButton);
    }

    public void playPauseMusic(MouseEvent actionEvent) {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        Music.playPauseMusic(playPauseMusicButton);
    }

    public void muteUnmuteMusic(MouseEvent actionEvent) {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        Music.muteUnmuteMusic(muteUnmuteButton);
    }

    public void setting(MouseEvent actionEvent) {
        if (actionEvent.getButton() != MouseButton.PRIMARY) return;
        boolean[] surrender = new boolean[1];
        Stage window = new Stage();
        window.initOwner(LoginMenuView.getStage());
        window.initStyle(StageStyle.UNDECORATED);
        GamePopUpMessage.setStage(window);
        Button resumeButton = new Button();
        resumeButton.setText("Resume");
        resumeButton.setStyle("-fx-background-color: #bb792d;\n" +
                "-fx-background-radius: 10;\n" +
                "-fx-text-fill: white;\n" +
                "-fx-font-size: 16;");
        resumeButton.setOnAction(event -> {
            onClick.play();
            window.close();
        });
        Button surrenderButton = new Button("Surender");
        surrenderButton.setCursor(Cursor.HAND);
        surrenderButton.setStyle("-fx-background-color: #bb792d;\n" +
                "-fx-background-radius: 10;\n" +
                "-fx-text-fill: white;\n" +
                "-fx-font-size: 16;");
        surrenderButton.setOnAction(actionEvent1 -> {
            onClick.play();
            surrender[0] = true;
            window.close();
        });
        Button exitButton = new Button("Exit");
        exitButton.setCursor(Cursor.HAND);
        exitButton.setStyle("-fx-background-color: #bb792d;\n" +
                "-fx-background-radius: 10;\n" +
                "-fx-text-fill: white;\n" +
                "-fx-font-size: 16;");
        exitButton.setOnMouseClicked(mouseEvent -> {
            onClick.play();
            window.close();
            try {
                Utility.openNewMenu("/project/fxml/main_menu.fxml");
                RoundGameController.getInstance().clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Label label = new Label("Game is paused.");
        label.setStyle("-fx-text-fill: white");
        VBox mainBox = new VBox(label, resumeButton, surrenderButton, exitButton);
        mainBox.setPadding(new Insets(0, 20, 0, 20));
        resumeButton.setCursor(Cursor.HAND);
        mainBox.setSpacing(10);
        mainBox.setStyle("-fx-background-color: #103188;");
        mainBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(mainBox, 200, 200);
        mainBox.getScene().setFill(Color.TRANSPARENT);
        window.initStyle(StageStyle.TRANSPARENT);
        window.setScene(scene);
        window.setResizable(false);
        window.setX(700);
        window.setY(300);
        window.initModality(Modality.WINDOW_MODAL);
        blur();
        window.getScene().getStylesheets().add(String.valueOf(getClass().getResource("/project/CSS/in_game_window.css")));
        window.showAndWait();
        mainGamePane.setEffect(null);
        if (surrender[0]) {
            RoundGameController.getInstance().surrender();
        }
    }

    public void cheat() {
        Stage window = new Stage();
        window.initOwner(LoginMenuView.getStage());
        window.initStyle(StageStyle.UNDECORATED);
        GamePopUpMessage.setStage(window);
        TextField textField = new TextField();
        Button doneButton = new Button();
        doneButton.setText("OK");
        doneButton.setStyle("-fx-background-color: #bb792d;\n" +
                "-fx-background-radius: 10;\n" +
                "-fx-text-fill: white;\n" +
                "-fx-font-size: 16;");
        doneButton.setOnAction(event -> {
            onClick.play();
            window.close();
            processCheat(textField.getText());
        });


        Button cancel = new Button("Cancel");
        cancel.setCursor(Cursor.HAND);
        cancel.setStyle("-fx-background-color: #bb792d;\n" +
                "-fx-background-radius: 10;\n" +
                "-fx-text-fill: white;\n" +
                "-fx-font-size: 16;");
        cancel.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.PRIMARY)
                return;
            onClick.play();
            window.close();
        });

        Label label = new Label("You can cheat here : |");
        label.setStyle("-fx-text-fill: white");
        HBox buttonBox = new HBox(doneButton, cancel);
        VBox mainBox = new VBox(label, textField, buttonBox);
        mainBox.setPadding(new Insets(0, 20, 0, 20));
        buttonBox.setSpacing(20);
        buttonBox.setAlignment(Pos.CENTER);
        doneButton.setCursor(Cursor.HAND);
        mainBox.setSpacing(10);
        mainBox.setStyle("-fx-background-color: #103188;");
        mainBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(mainBox, 700, 250);
        mainBox.getScene().setFill(Color.TRANSPARENT);
        window.initStyle(StageStyle.TRANSPARENT);
        window.setScene(scene);
        window.setResizable(false);
        window.setX(500);
        window.setY(300);
        window.initModality(Modality.WINDOW_MODAL);
        blur();
        window.getScene().getStylesheets().add(String.valueOf(getClass().getResource("/project/CSS/in_game_window.css")));
        window.showAndWait();
        mainGamePane.setEffect(null);
    }

    private void processCheat(String text) {
        Matcher matcher;
        if ((matcher = Regex.getMatcher(Regex.CHEAT_INCREASE_LP, text)).matches()) {
            controller.getCurrentPlayer().increaseLP(Integer.parseInt(matcher.group("LPAmount")));
            updateLPLabels();
        } else if ((matcher = Regex.getMatcher(Regex.CHEAT_DUEL_SET_WINNER, text)).matches()) {
            RoundGameController.getInstance().setWinnerCheat(matcher.group("winnerNickName"));
        }
    }

    public void showFinishRoundPopUpMessageAndCloseGameView(String name) {
        mainGamePane.setEffect(null);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Round finished! " + name + " won the game!");
        alert.initOwner(LoginMenuView.getStage());
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initStyle(StageStyle.TRANSPARENT);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setHeaderText(null);
        dialogPane.setGraphic(null);
        dialogPane.setStyle("-fx-border-radius: 10; -fx-border-color: #bb792d; -fx-border-width: 7; -fx-background-radius: 14; -fx-font-family: \"Matrix II Regular\"; -fx-background-color: #103188;");
        dialogPane.lookup(".content.label").setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-line-spacing: 5px;");
        dialogPane.getScene().setFill(Color.TRANSPARENT);
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("OK");
        ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
        buttonBar.getButtons().forEach(b -> b.setStyle("-fx-background-radius: 10; -fx-background-color: #bb792d; -fx-font-size: 16; -fx-text-fill: white;"));
        buttonBar.getButtons().forEach(b -> b.setCursor(Cursor.HAND));
        alert.showAndWait();
        mainGamePane.setEffect(null);
        try {
            Utility.openNewMenu("/project/fxml/main_menu.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showFinishRoundOfMatchPopUpMessageAndCloseGameView(String name) {
        mainGamePane.setEffect(null);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Round finished! " + name + " won the round!");
        alert.initOwner(LoginMenuView.getStage());
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initStyle(StageStyle.TRANSPARENT);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setHeaderText(null);
        dialogPane.setGraphic(null);
        dialogPane.setStyle("-fx-border-radius: 10; -fx-border-color: #bb792d; -fx-border-width: 7; -fx-background-radius: 14; -fx-font-family: \"Matrix II Regular\"; -fx-background-color: #103188;");
        dialogPane.lookup(".content.label").setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-line-spacing: 5px;");
        dialogPane.getScene().setFill(Color.TRANSPARENT);
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("OK");
        ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
        buttonBar.getButtons().forEach(b -> b.setStyle("-fx-background-radius: 10; -fx-background-color: #bb792d; -fx-font-size: 16; -fx-text-fill: white;"));
        buttonBar.getButtons().forEach(b -> b.setCursor(Cursor.HAND));
        alert.showAndWait();
        mainGamePane.setEffect(null);
        try {
            BetweenRoundView betweenRoundView = (BetweenRoundView) Utility.openMenuAndReturnController("/project/fxml/between_rounds_view.fxml");
            betweenRoundView.startAndLoadBetweenRound();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showFinishMatchAndCloseGameView(String message) {
        mainGamePane.setEffect(null);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.initOwner(LoginMenuView.getStage());
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initStyle(StageStyle.TRANSPARENT);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setHeaderText(null);
        dialogPane.setGraphic(null);
        dialogPane.setStyle("-fx-border-radius: 10; -fx-border-color: #bb792d; -fx-border-width: 7; -fx-background-radius: 14; -fx-font-family: \"Matrix II Regular\"; -fx-background-color: #103188;");
        dialogPane.lookup(".content.label").setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-line-spacing: 5px;");
        dialogPane.getScene().setFill(Color.TRANSPARENT);
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("OK");
        ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
        buttonBar.getButtons().forEach(b -> b.setStyle("-fx-background-radius: 10; -fx-background-color: #bb792d; -fx-font-size: 16; -fx-text-fill: white;"));
        buttonBar.getButtons().forEach(b -> b.setCursor(Cursor.HAND));
        alert.showAndWait();
        mainGamePane.setEffect(null);
        try {
            Utility.openNewMenu("/project/fxml/main_menu.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean askNewPosition(String position) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to change position ? " + position + " ?");
        alert.initOwner(LoginMenuView.getStage());
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initStyle(StageStyle.TRANSPARENT);
        DialogPane dialogPane = alert.getDialogPane();
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");
        dialogPane.setHeaderText(null);
        dialogPane.setGraphic(null);
        dialogPane.setStyle("-fx-border-radius: 10; -fx-border-color: #bb792d; -fx-border-width: 7; -fx-background-radius: 14; -fx-font-family: \"Matrix II Regular\"; -fx-background-color: #103188;");
        dialogPane.lookup(".content.label").setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-line-spacing: 5px;");
        dialogPane.getScene().setFill(Color.TRANSPARENT);
        ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
        buttonBar.getButtons().forEach(b -> b.setStyle("-fx-background-radius: 10; -fx-background-color: #bb792d; -fx-font-size: 16; -fx-text-fill: white;"));
        buttonBar.getButtons().forEach(b -> b.setCursor(Cursor.HAND));
        alert.showAndWait();
        return alert.getResult().getButtonData().isDefaultButton();
    }

    public void showPopUpMessageForEffect(String spellEffectDescription, CardType cardType) {
        GamePopUpMessage.setStage(LoginMenuView.getStage());
        new GamePopUpMessage(Alert.AlertType.INFORMATION, spellEffectDescription);
        if (cardType == CardType.SPELL) {
            reloadCurrentSpellZone();
            reloadOpponentSpellZone();
        }

    }

    public void updateDeckLabels() {
        currentDeckLabel.setText(String.valueOf(RoundGameController.getInstance().getCurrentPlayer().getPlayDeck().getMainCards().size()));
        opponentDeckLabel.setText(String.valueOf(RoundGameController.getInstance().getOpponentPlayer().getPlayDeck().getMainCards().size()));
    }

    public void middleGameTurnChange() {
        blur();
        GamePopUpMessage.setStage(LoginMenuView.getStage());
        new GamePopUpMessage(Alert.AlertType.INFORMATION, "now your opponent can activate effect, please change turn");

    }

    public void endOfMiddleGameTurnChange() {
        mainGamePane.setEffect(null);
    }

}
