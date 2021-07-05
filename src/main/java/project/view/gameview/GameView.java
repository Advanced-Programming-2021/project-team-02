package project.view.gameview;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import project.controller.playgame.RoundGameController;
import project.model.Deck;
import project.model.card.Card;
import project.model.card.Monster;
import project.model.card.informationofcards.CardType;
import project.model.game.board.Cell;
import project.model.game.board.CellStatus;
import project.view.Utility;
import project.view.input.Input;
import project.view.input.Regex;
import project.view.messages.Error;
import project.view.messages.GameViewMessage;
import project.view.messages.PopUpMessage;
import project.view.messages.SuccessMessage;

import javafx.geometry.Point2D;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;

public class GameView {
    private final RoundGameController controller = RoundGameController.getInstance();
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
    public Pane currentPlayerFieldPane;
    public Pane currentPlayerGraveYardPane;
    public Pane opponentFieldPane;
    public Pane opponentGraveYardPane;
    public Button SetButton;
    public Button SummonOrActivateButton;
    public Pane monster1;
    public Pane monster4;
    public Pane monster3;
    public Pane monster2;
    public Pane monster5;
    public AnchorPane cardBoardPane;
    private ArrayList<Pane> currentMonsterZonePanes;
    private Image backCardImage = new Image(getClass().getResource("/project/image/GamePictures/Card Back.png").toString());

    public void initialize() {
        currentMonsterZonePanes = new ArrayList<>();
        currentMonsterZonePanes.add(monster1);
        currentMonsterZonePanes.add(monster2);
        currentMonsterZonePanes.add(monster3);
        currentMonsterZonePanes.add(monster4);
        currentMonsterZonePanes.add(monster5);
        currentPlayerLP.setText("LP : 8000");
        currentPlayerNickname.setText("Nickname : " + RoundGameController.getInstance().getCurrentPlayer().getNickname());
        opponentPlayerLP.setText("LP : 8000");
        opponentPlayerNickname.setText("Nickname : " + RoundGameController.getInstance().getOpponentPlayer().getNickname());
        currentPlayerAvatar.setImage(new Image(RoundGameController.getInstance().getCurrentPlayer().getAvatar().getUrl().toString()));
        opponentPlayerAvatar.setImage(new Image(RoundGameController.getInstance().getOpponentPlayer().getAvatar().getUrl().toString()));
        selectedCardImageView.setImage(backCardImage);
        selectedCardDescriptionLabel.setText("No card selected");
        RoundGameController.getInstance().setView(this);
        //loadHandCards();
        currentPlayerDeckPane.setCursor(Cursor.HAND);
        currentPlayerDeckPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() != MouseButton.PRIMARY)
                    return;
                selectedCardImageView.setImage(backCardImage);
                selectedCardDescriptionLabel.setText("Deck!");
            }
        });
        currentPlayerGraveYardPane.setCursor(Cursor.HAND);
        //TODO currentPlayerGraveYardPane.setOnMouseClicked
        opponentGraveYardPane.setCursor(Cursor.HAND);
        currentPlayerDeckPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() != MouseButton.PRIMARY)
                    return;
                RoundGameController.getInstance().drawCardFromDeck();
            }
        });
        phaseLabel.setText("Current Phase : Draw");
        currentDeckLabel.setCursor(Cursor.HAND);
        updateCurrentDeckLabel();

    }

    public Image getCardImageByName(String cardName) {
        Utility util = new Utility();
        util.addImages();
        HashMap<String, Image> stringImageHashMap = util.getStringImageHashMap();
        for (String name : stringImageHashMap.keySet()) {
            if (name.equals(cardName)) {
                return stringImageHashMap.get(name);
            }
        }
        return null;
    }

    public void loadHandCards() {
        ArrayList<Card> currentPlayerHandList = (ArrayList<Card>) RoundGameController.getInstance().getCurrentPlayerHand();
        int counter = 0;
        for (Card card : currentPlayerHandList) {
            ImageView cardImageView = new ImageView(getCardImageByName(card.getName()));
            cardImageView.setFitHeight(160);
            cardImageView.setFitWidth(116);
            cardImageView.setCursor(Cursor.HAND);

            int finalCounter = counter;
            cardImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (mouseEvent.getButton() != MouseButton.PRIMARY)
                        return;
                    System.out.println("the selected card : " + card.getName());
                    System.out.println("address! : " + (finalCounter + 1) + "\n");
                    selectedCardImageView.setImage(getCardImageByName(card.getName()));
                    selectedCardDescriptionLabel.setText(card.toString());
                    RoundGameController.getInstance().selectCardInHand(finalCounter + 1);
                }
            });
            //TODO
            cardImageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    cardImageView.setStyle("-fx-border-color: #66973b");
                    cardImageView.hoverProperty();
                }
            });
            currentHand.add(cardImageView, counter, 0);
            counter++;
        }
        counter = 0;
        ArrayList<Card> opponentHandList = (ArrayList<Card>) RoundGameController.getInstance().getOpponentPlayerHand();
        for (Card card : opponentHandList) {
            ImageView cardImageView = new ImageView(backCardImage);
            cardImageView.setFitHeight(160);
            cardImageView.setFitWidth(116);
            cardImageView.setCursor(Cursor.HAND);
            cardImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (mouseEvent.getButton() != MouseButton.PRIMARY)
                        return;
                    selectedCardImageView.setImage(backCardImage);
                    selectedCardDescriptionLabel.setText("Opponent Hand Card!");
                }
            });
            opponentHand.add(cardImageView, counter, 0);
            counter++;

        }
        opponentDeckLabel.setText(String.valueOf(RoundGameController.getInstance().getOpponentPlayer().getPlayDeck().getMainCards().size()));
        currentDeckLabel.setText(String.valueOf(RoundGameController.getInstance().getCurrentPlayer().getPlayDeck().getMainCards().size()));
    }

    public void drawCardFromDeckAnimation(String cardName, boolean isCurrent) {
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
        cardImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() != MouseButton.PRIMARY)
                    return;
                selectedCardImageView.setImage(cardImageView.getImage());
                selectedCardDescriptionLabel.setText(Card.getCardByName(cardName).toString());

            }
        });

        Node node = getNodeInGridPane(handPane, 0, addressOfAddInGrid - 1);
        Point2D nodePoint = node.localToScene(new Point2D(0, 0));
        translateTransition.setNode(cardImageView);
        translateTransition.setFromX(deck.getX());
        translateTransition.setFromY(deck.getY());
        translateTransition.setToX(nodePoint.getX() + 120);
        translateTransition.setToY(nodePoint.getY());
        translateTransition.setDuration(Duration.millis(2000));
        translateTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                translateTransition.setNode(null);
                ImageView imageView = new ImageView(getCardImageByName(cardName));
                imageView.setFitHeight(160);
                imageView.setFitWidth(116);
                imageView.setCursor(Cursor.HAND);
                imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (mouseEvent.getButton() != MouseButton.PRIMARY)
                            return;
                        selectedCardImageView.setImage(cardImageView.getImage());
                        selectedCardDescriptionLabel.setText(Card.getCardByName(cardName).toString());
                        RoundGameController.getInstance().selectCardInHand(addressOfAddInGrid + 1);
                    }
                });
                handPane.add(imageView, addressOfAddInGrid, 0);
                mainGamePane.getChildren().remove(cardImageView);
            }
        });


        mainGamePane.getChildren().add(cardImageView);
        translateTransition.play();
        updateCurrentDeckLabel();

    }

    private Node getNodeInGridPane(GridPane gridPane, int row, int column) {
        System.out.println(row + "   " + column);
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

    private void showSetHandAnimation() {

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
                commandRecognition(command);
            }

        }
    }

    public void run(String command) {
        commandRecognition(command);
    }

    public void commandRecognition(String command) {
        Matcher matcher;
        if ((matcher = Regex.getMatcher(Regex.BOARD_GAME_SELECT_MONSTER, command)).matches()) ;
            //controller.selectCardInMonsterZone(matcher);
        else if ((matcher = Regex.getMatcherFromAllPermutations(Regex.BOARD_GAME_SELECT_MONSTER_OPPONENT, command)) != null)
            controller.selectOpponentCardMonsterZone(matcher);
        else if ((matcher = Regex.getMatcher(Regex.BOARD_GAME_SELECT_SPELL, command)).matches())
            controller.selectCardInSpellZone(matcher);
        else if ((matcher = Regex.getMatcherFromAllPermutations(Regex.BOARD_GAME_SELECT_SPELL_OPPONENT, command)) != null)
            controller.selectOpponentCardSpellZone(matcher);
        else if (Regex.getMatcher(Regex.BOARD_GAME_SELECT_FIELD, command).matches())
            controller.selectPlayerFieldCard();
        else if ((matcher = Regex.getMatcherFromAllPermutations(Regex.BOARD_GAME_SELECT_FIELD_OPPONENT, command)) != null)
            controller.selectOpponentFieldCard();
        else if (Regex.getMatcher(Regex.BOARD_GAME_SELECT_DESELECT, command).matches())
            controller.deselectCard(1);
        else if ((matcher = Regex.getMatcher(Regex.BOARD_GAME_SELECT_HAND, command)).matches())
            controller.selectCardInHand(Integer.parseInt(matcher.group("cardNumber")));
        else if ((matcher = Regex.getMatcher(Regex.BOARD_GAME_SELECT_HAND, command)).matches())
            controller.selectCardInHand(Integer.parseInt(matcher.group("cardNumber")));
        else if (Regex.getMatcher(Regex.BOARD_GAME_NEXT_PHASE, command).matches())
            controller.nextPhase();
        else if (Regex.getMatcher(Regex.BOARD_GAME_SUMMON, command).matches())
            controller.summonMonster();
        else if (Regex.getMatcher(Regex.GRAVEYARD_SHOW, command).matches())
            showCurrentGraveYard(true);
        else if (Regex.getMatcher(Regex.CARD_SHOW_SELECTED, command).matches()) {
            if (controller.getSelectedCell() == null) {
                showError(Error.NO_CARD_SELECTED_YET);
                return;
            }
            //TODO DeckMenuView.getInstance().checkTypeOfCardAndPrintIt(controller.getSelectedCell().getCardInCell());
        } else if (Regex.getMatcher(Regex.BOARD_GAME_SUMMON, command).matches())
            controller.summonMonster();
        else if (Regex.getMatcher(Regex.BOARD_GAME_SET, command).matches())
            controller.setCrad();
        else if ((matcher = Regex.getMatcher(Regex.BOARD_GAME_SET_POSITION, command)).matches())
            controller.changeMonsterPosition(matcher);
        else if (Regex.getMatcher(Regex.BOARD_GAME_FLIP_SUMMON, command).matches())
            controller.flipSummon();
        else if ((matcher = Regex.getMatcher(Regex.BOARD_GAME_ATTACK, command)).matches())
            controller.attackToCard(Integer.parseInt(matcher.group("monsterZoneNumber")));
        else if (Regex.getMatcher(Regex.BOARD_GAME_ATTACK_DIRECT, command).matches())
            controller.directAttack();
        else if (Regex.getMatcher(Regex.BOARD_GAME_ACTIVATE_EFFECT, command).matches())
            controller.activateEffectOfSpellOrTrap();
        else if ((matcher = Regex.getMatcher(Regex.CHEAT_INCREASE_LP, command)).matches())
            controller.getCurrentPlayer().increaseLP(Integer.parseInt(matcher.group("LPAmount")));
        else if ((matcher = Regex.getMatcher(Regex.CHEAT_DUEL_SET_WINNER, command)).matches()) {
            controller.setWinnerCheat(matcher.group("winnerNickName"));
        } else if (Regex.getMatcher(Regex.COMMAND_CANCEL, command).matches())
            controller.cancel();
        else if (Regex.getMatcher(Regex.BOARD_GAME_SURRENDER, command).matches())
            controller.surrender();
        else if (Regex.getMatcher(Regex.BOARD_GAME_SHOW_HAND, command).matches()) {
            showHand();
        } else if (Regex.getMatcher(Regex.BOARD_GAME_SHOW_BOARD, command).matches()) {
            showBoard();
        } else if (Regex.getMatcher(Regex.COMMAND_HELP, command).matches()) {
            help();
        } else {
            Error.showError(Error.INVALID_COMMAND);
        }
    }

    public void showError(Error error) {
        System.out.println(error.getValue());
    }

    public void showSuccessMessageWithTwoIntegerAndOneString(SuccessMessage successMessage, String winnerUserName, int winnerScore, int loserScore) {
        System.out.printf(SuccessMessage.WIN_MESSAGE_ROUND_MATCH.getValue(), winnerUserName, winnerScore, loserScore);
    }

    public void showSuccessMessageWithTwoIntegerAndOneStringForSeveralWins(SuccessMessage successMessage, String winnerUserName, int winnerScore, int loserScore) {
        if (successMessage.equals(SuccessMessage.WIN_MESSAGE_FOR_HOLE_MATCH))
            System.out.printf(SuccessMessage.WIN_MESSAGE_FOR_HOLE_MATCH.getValue(), winnerUserName, winnerScore, loserScore);
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
            System.out.println();//added by erfan based on what mahdis said
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

    public void showHand() {
        controller.getCurrentPlayerHand().stream().map(card -> card.getName() + " (" + card.getCardType().name() + ")").forEach(System.out::println);
    }

    public void showPhase() {
        System.out.printf(SuccessMessage.PHASE_NAME.getValue(), controller.getCurrentPhase());
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

    public int getTributeAddress() {
        System.out.println("Enter number(address) of monster to tribute Or cancel to finish the process:");
        String command;
        while (true) {
            command = Input.getInput();
            if (command.matches("[1-9]+")) {
                return Integer.parseInt(command);
            } else if (command.equals("cancel")) {
                return -1;
            } else {
                System.out.println(Error.INVALID_COMMAND.getValue());
            }
        }
    }

    public boolean getSummonOrderForRitual() {
        System.out.println("you should right summon order:");
        String input;
        while (true) {
            input = Input.getInput();
            if (input.matches("summon")) return true;
            else System.out.println(Error.INVALID_COMMAND);
        }
    }

    public String[] getMonstersAddressesToBringRitual() {
        System.out.println("write card addresses in this format:\n" +
                "num1-num2-...\n" +
                "(for example : 1-3-5)");
        String input;
        String[] split;
        while (true) {
            input = Input.getInput();
            if (input.matches("([12345-]+)")) {
                split = input.split("-");
                return split;
            } else System.out.println(Error.INVALID_COMMAND);
        }
    }

    public CellStatus getPositionForSetRitualMonster() {
        System.out.println("please enter position of ritual summon in this format\n" +
                "attack\n" +
                "or\n" +
                "defense");
        String input;
        while (true) {
            input = Input.getInput();
            if (input.equals("attack") || input.equals("defense")) {
                if (input.equals("attack")) {
                    return CellStatus.OFFENSIVE_OCCUPIED;
                } else return CellStatus.DEFENSIVE_OCCUPIED;
            } else System.out.println(Error.INVALID_COMMAND);
        }
    }

    public int swordOfDarkDestruction() {
        System.out.println("please enter card address in monsterZone to be equipped");
        while (true) {
            String command = Input.getInput();
            if (command.equals("cancel")) return -1;
            else if (command.matches("[1-9]+")) return Integer.parseInt(command);
            else System.out.println(Error.INVALID_COMMAND);
        }
    }

    public int blackPendant() {
        System.out.println("please enter card address in monsterZone to be equipped");
        while (true) {
            String command = Input.getInput();
            if (command.matches("[1-9]+")) return Integer.parseInt(command);
            else System.out.println(Error.INVALID_COMMAND);
        }
    }

    public int getAddressForTrapOrSpell() {
        System.out.println("enter address(number of cell between 1 to 5) of spell to be activated or write cancel if you dont want to activate anything");
        while (true) {
            String command = Input.getInput();
            if (command.equals("cancel")) {
                return -1;
            } else if (command.matches("[1-9]+")) {
                return Integer.parseInt(command);
            } else System.out.println(Error.INVALID_COMMAND);
        }
    }

    public boolean yesNoQuestion(String question) {
        System.out.println(question);
        if (Input.getInput().equals("yes")) {
            return true;
        }
        return false;
    }

    public int chooseCardInHand(String toShow) {
        System.out.println(toShow);
        return askAddress();
    }

    public int howToSummonBeastKingBarbos() {
        System.out.println("how do you want to summon/set this card : " +
                "1-normal tribute" +
                ", 2-without tribute, 3-with 3 tributes\n" +
                "enter the number please");
        while (true) {
            String input = Input.getInput();
            if (input.matches("[1-3]")) {
                return Integer.parseInt(input);
            } else {
                System.out.println(Error.INVALID_COMMAND.getValue());
            }
        }
    }

    public String ritualCardName() {
        System.out.println("please enter card address (number) or -1 to cancel");
        while (true) {
            String input = Input.getInput();
            if (input.matches("[0-9-]+")) return input;
            else System.out.println(Error.INVALID_COMMAND);
        }
    }

    public int askAddressForManEaterBug() {
        System.out.println("Enter address(number of it in your opponent Monster Zone) of card to be killed");
        return askAddress();
    }

    private int askAddress() {
        String input;
        while (true) {
            input = Input.getInput();
            if (input.matches("[1-9]+")) {
                return Integer.parseInt(input);
            } else if (input.equals("cancel")) {
                return -1;
            } else System.out.println(Error.INVALID_COMMAND);
        }
    }

    public int chooseCardInDeck(Deck deck) {
        System.out.println("Enter number of field spell to be added to you deck or cancel to cancel the command!");
        System.out.println(deck);
        while (true) {
            String input = Input.getInput();
            if (input.matches("[1-9]+")) {
                return Integer.parseInt(input);
            } else if (input.equals("cancel")) {
                return -1;
            } else System.out.println(Error.INVALID_COMMAND);
        }
    }

    public int chooseCardInGraveYard() {
        System.out.println("choose the card, enter the number of it:");
        while (true) {
            String input = Input.getInput();
            if (input.matches("[1-9]+")) {
                return Integer.parseInt(input);
            } else if (input.equals("cancel")) {
                return -1;
            } else System.out.println(Error.INVALID_COMMAND);
        }
    }

    public int twoChoiceQuestions(String message, String choice1, String choice2) {
        System.out.println(message);
        System.out.println("choose one : 1-" + choice1 + ", 2-" + choice2);
        while (true) {
            String string = Input.getInput();
            if (string.equals("cancel")) {
                return -1;
            } else if (string.matches("[1-9]+")) {
                if (string.equals("1") || string.equals("2")) {
                    return Integer.parseInt(string);
                } else showError(Error.INVALID_NUMBER);
            } else showError(Error.INVALID_COMMAND);
        }
    }

    public void showOpponentGraveYard() {
        int counter = 1;
        if (controller.getOpponentPlayer().getPlayerBoard().isGraveYardEmpty())
            showError(Error.EMPTY_GRAVEYARD);
        else {
            for (Card card : controller.getCurrentPlayer().getPlayerBoard().returnGraveYard().getGraveYardCards()) {
                System.out.println(counter + ". " + card.getName() + ":" + card.getDescription());
                counter++;
            }
        }
        while (!Input.getInput().equals("back")) {
            System.out.println(Error.INVALID_COMMAND);
        }
    }

    public void help() {
        System.out.println("show board\n" +
                "show hand\n" +
                "select --monster <monsterZoneNumber>\n" +
                "select --monster <monsterZoneNumber> --opponent\n" +
                "select -m <monsterZoneNumber> -o\n" +
                "select --spell <spellZoneNumber>\n" +
                "select --spell <spellZoneNumber> --opponent\n" +
                "select -s <spellZoneNumber> -o\n" +
                "select --field\n" +
                "select --field --opponent\n" +
                "select -f -o\n" +
                "select --hand <cardNumber>\n" +
                "select -d\n" +
                "next phase\n" +
                "summon\n" +
                "set \n" +
                "set --position <attack|defense>\n" +
                "flip-summon\n" +
                "attack <monsterZoneNumber>\n" +
                "attack direct\n" +
                "activate effect\n" +
                "surrender\n" +
                "show graveyard\n" +
                "card show --selected\n" +
                "cancel\n" +
                "help");
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton() != MouseButton.PRIMARY) return;
        Utility.openNewMenu("/project/fxml/duel_start_menu.fxml");
    }

    public void nextPhase(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() != MouseButton.PRIMARY)
            return;
        RoundGameController.getInstance().nextPhase();
        phaseLabel.setText("Current Phase : " + RoundGameController.getInstance().getCurrentPhase().toString());
    }

    public void summonOrActivate(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() != MouseButton.PRIMARY)
            return;
        GameViewMessage message = RoundGameController.getInstance().summonOrActivate();
        if (message != GameViewMessage.SUCCESS)
            new PopUpMessage(message.getAlertType(), message.getLabel());
    }

    public void set(MouseEvent mouseEvent) {

    }

    public void showSummon(int addressInMonsterZone, int addressInHand, String cardName) {
        ImageView fakeCardImageView = new ImageView(getCardImageByName(cardName));
        fakeCardImageView.setFitWidth(94);
        fakeCardImageView.setFitHeight(130);
        GridPane handPane = currentHand;
        //Translate transition :
        TranslateTransition translateTransition = new TranslateTransition();
        int addressOfAddInMonsterZoneGrid = addressInMonsterZone - 1;//zero based!
        int addressInHandGrid = addressInHand - 1;
        Node inHandNode = getNodeInGridPane(handPane, 0, addressInHandGrid);
        Point2D inHandPoint = inHandNode.localToScene(new Point2D(0, 0));
        Node inZoneNode;
        inZoneNode = getNodeInGridPane(currentPlayerMonsterZone, 0, addressOfAddInMonsterZoneGrid);
        Point2D zonePoint = null;
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
        translateTransition.setDuration(Duration.millis(2000));
        translateTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ImageView imageView = new ImageView(getCardImageByName(cardName));
                imageView.setFitHeight(130);
                imageView.setFitWidth(94);
                imageView.setCursor(Cursor.HAND);
                imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (mouseEvent.getButton() != MouseButton.PRIMARY)
                            return;
                        selectedCardImageView.setImage(getCardImageByName(cardName));
                        selectedCardDescriptionLabel.setText(Card.getCardByName(cardName).toString());
                        RoundGameController.getInstance().selectCardInMonsterZone(addressInMonsterZone);
                        cardBoardPane.getChildren().remove(fakeCardImageView);
                    }
                });
                ((Pane) inZoneNode).getChildren().add(imageView);
                reloadCurrentHand();
            }
        });
//        fakeCardImageView.setLayoutX(inHandPoint.getX());
//        fakeCardImageView.setLayoutY(inHandPoint.getY());
        mainGamePane.getChildren().add(fakeCardImageView);
        //mainGamePane.getChildren().add(fakeCardImageView);
        currentHand.getChildren().remove(inHandNode);
        translateTransition.play();
    }

    private void reloadCurrentHand() {
        currentHand.getChildren().clear();
        ArrayList<Card> currentPlayerHandList = (ArrayList<Card>) RoundGameController.getInstance().getCurrentPlayerHand();
        int counter = 0;
        for (Card card : currentPlayerHandList) {
            ImageView cardImageView = new ImageView(getCardImageByName(card.getName()));
            cardImageView.setFitHeight(160);
            cardImageView.setFitWidth(116);
            cardImageView.setCursor(Cursor.HAND);
            int finalCounter = counter;
            cardImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (mouseEvent.getButton() != MouseButton.PRIMARY)
                        return;
                    System.out.println(card.getName() + " selected " + "in address  " + (finalCounter + 1));
                    selectedCardImageView.setImage(getCardImageByName(card.getName()));
                    selectedCardDescriptionLabel.setText(card.toString());
                    RoundGameController.getInstance().selectCardInHand(finalCounter + 1);
                }
            });
            //TODO
            cardImageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                }
            });
            currentHand.add(cardImageView, counter, 0);
            counter++;
        }
    }
}
