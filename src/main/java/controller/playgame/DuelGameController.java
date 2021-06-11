package controller.playgame;

import model.Assets;
import model.Deck;
import model.User;
import model.card.Card;
import model.game.Duel;
import model.game.DuelPlayer;
import view.Menu;
import view.MenusManager;
import view.gameview.GameView;
import view.messages.Error;
import view.messages.SuccessMessage;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;


public class DuelGameController {
    private static DuelGameController instance = null;
    private final GameView view = GameView.getInstance();

    private Duel duel;
    private String specifier;

    private DuelGameController() {

    }

    public Duel getDuel() {
        return duel;
    }

    public static DuelGameController getInstance() {
        if (instance == null) instance = new DuelGameController();
        return instance;
    }

    public void startDuel(Duel duel) {
        this.duel = duel;
        duel.setCurrentRound(1);
        setStartHandCards(duel.getPlayer1(), duel.getPlayer2());
        starterSpecifier();
        MenusManager.getInstance().changeMenu(Menu.ONGOING_GAME);
    }
    public void startNextRound(){

    }
    public void starterSpecifier() {
//        if (flipCoin() == 1) { TODO remove comment ... i commented them because of testing
        setSpecifier(duel.getPlayer1().getNickname());
        RoundGameController.getInstance().setRoundInfo(duel.getPlayer1(), duel.getPlayer2(), view, instance);
//        } else {
//            setSpecifier(duel.getPlayer2().getNickname());
//            RoundGameController.getInstance().setRoundInfo(duel.getPlayer2(), duel.getPlayer1(), view, instance);
//        }
    }

    public GameResult checkGameResult(DuelPlayer winner, DuelPlayer loser, GameResultToCheck resultType) {
        if (resultType == GameResultToCheck.NO_LP) {
            if (duel.getNumberOfRounds() == 1) {
                if (isPlayerDead(winner, loser)) {
                    if (duel.getPlayer1() == winner) {
                        updateScoreAndCoinForOneRound(duel.getPlayer1(), duel.getPlayer2());
                    } else updateScoreAndCoinForOneRound(duel.getPlayer2(), duel.getPlayer1());
                    return GameResult.GAME_FINISHED;
                }
                return GameResult.CONTINUE;
            } else if (duel.getNumberOfRounds() == 3) {
                if (isPlayerDead(winner, loser)) {
                    if (duel.getCurrentRound() == 3) {
                        updateScoreAndCoinForThreeRounds(winner, loser);
                        return GameResult.GAME_FINISHED;
                    } else {
                        duel.addLifePointOfPlayer1(duel.getPlayer1().getLifePoint());
                        duel.addLifePointOfPlayer2(duel.getPlayer2().getLifePoint());
                        return GameResult.ROUND_FINISHED;
                    }

                }
            }
        } else if (resultType == GameResultToCheck.SURRENDER) {
            if (duel.getNumberOfRounds() == 1) {
                if (duel.getPlayer1() == winner)
                    updateScoreAndCoinForOneRound(duel.getPlayer1(), duel.getPlayer2());
                else updateScoreAndCoinForOneRound(duel.getPlayer2(), duel.getPlayer1());
            } else {
                if (duel.getPlayer1() == winner)
                    updateScoreAndCoinForThreeRounds(duel.getPlayer1(), duel.getPlayer2());
                else updateScoreAndCoinForThreeRounds(duel.getPlayer2(), duel.getPlayer1());
            }
            view.showSuccessMessageWithTwoIntegerAndOneString(SuccessMessage.SURRENDER_MESSAGE, Objects.requireNonNull(User.getUserByNickName(winner.getNickname()))
                            .getUsername(), Objects.requireNonNull(User.getUserByNickName(winner.getNickname())).getScore(),
                    Objects.requireNonNull(User.getUserByNickName(loser.getNickname())).getScore());
            return GameResult.GAME_FINISHED;
        } else if (resultType == GameResultToCheck.NO_CARDS_TO_DRAW) {
            if (duel.getNumberOfRounds() == 1) {
                if (duel.getPlayer1() == winner) {
                    updateScoreAndCoinForOneRound(duel.getPlayer1(), duel.getPlayer2());
                } else updateScoreAndCoinForOneRound(duel.getPlayer2(), duel.getPlayer1());
                return GameResult.GAME_FINISHED;
            } else {
                if (duel.getCurrentRound() != 3) {
                    if (duel.getPlayer1() == winner)
                        updateScoreAndCoinForThreeRounds(duel.getPlayer1(), duel.getPlayer2());
                    else updateScoreAndCoinForThreeRounds(duel.getPlayer2(), duel.getPlayer1());
                    duel.addLifePointOfPlayer1(duel.getPlayer1().getLifePoint());
                    duel.addLifePointOfPlayer2(duel.getPlayer2().getLifePoint());
                    setNextRoundDetail();
                    return GameResult.ROUND_FINISHED;
                } else {
                    updateScoreAndCoinForThreeRounds(winner, loser);
                    return GameResult.GAME_FINISHED;
                }
            }
        }
        return null;
    }

    private boolean isPlayerDead(DuelPlayer winner, DuelPlayer loser) {
        return winner.getLifePoint() > 0 && loser.getLifePoint() == 0;
    }

    public void updateScoreAndCoinForOneRound(DuelPlayer winner, DuelPlayer loser) {
        if (winner == duel.getPlayer1()) {
            Objects.requireNonNull(Assets.getAssetsByUsername(Objects.requireNonNull(User.getUserByNickName(winner.getNickname()))
                    .getUsername())).increaseCoin(1000 + duel.maximumNumberOfLifePointsPlayer1());
            Objects.requireNonNull(Assets.getAssetsByUsername(Objects.requireNonNull(User.getUserByNickName(loser.getNickname()))
                    .getUsername())).increaseCoin(100);
        } else if (winner == duel.getPlayer2()) {
            Objects.requireNonNull(Assets.getAssetsByUsername(Objects.requireNonNull(User.getUserByNickName(winner.getNickname()))
                    .getUsername())).increaseCoin(1000 + duel.maximumNumberOfLifePointsPlayer2());
            Objects.requireNonNull(Assets.getAssetsByUsername(Objects.requireNonNull(User.getUserByNickName(loser.getNickname()))
                    .getUsername())).increaseCoin(100);
        }

    }

    public void updateScoreAndCoinForThreeRounds(DuelPlayer winner, DuelPlayer loser) {
        if (winner == duel.getPlayer1()) {
            Objects.requireNonNull(Assets.getAssetsByUsername(Objects.requireNonNull(User.getUserByNickName(winner.getNickname()))
                    .getUsername())).increaseCoin(3000 + 3 * duel.maximumNumberOfLifePointsPlayer1());
            Objects.requireNonNull(Assets.getAssetsByUsername(Objects.requireNonNull(User.getUserByNickName(loser.getNickname()))
                    .getUsername())).increaseCoin(300);
        } else if (winner == duel.getPlayer2()) {
            Objects.requireNonNull(Assets.getAssetsByUsername(Objects.requireNonNull(User.getUserByNickName(winner.getNickname()))
                    .getUsername())).increaseCoin(3000 + 3 * duel.maximumNumberOfLifePointsPlayer2());
            Objects.requireNonNull(Assets.getAssetsByUsername(Objects.requireNonNull(User.getUserByNickName(loser.getNickname()))
                    .getUsername())).increaseCoin(300);
        }
        ArrayList<String> roundsWinner = duel.getWinner();
        if (roundsWinner.get(0).equals(roundsWinner.get(1))) {
            view.showSuccessMessageWithTwoIntegerAndOneStringForSeveralWins(SuccessMessage.SURRENDER_MESSAGE_FOR_HOLE_MATCH,
                    Objects.requireNonNull(Objects.requireNonNull(User.getUserByNickName(winner.getNickname())).getUsername()),
                    Objects.requireNonNull(User.getUserByNickName(winner.getNickname())).getScore(),
                    Objects.requireNonNull(User.getUserByNickName(loser.getNickname())).getScore());
        } else
            view.showSuccessMessageWithTwoIntegerAndOneString(SuccessMessage.SURRENDER_MESSAGE, Objects.requireNonNull(User.getUserByNickName(winner.getNickname()))
                            .getUsername(), Objects.requireNonNull(User.getUserByNickName(winner.getNickname())).getScore(),
                    Objects.requireNonNull(User.getUserByNickName(loser.getNickname())).getScore());
    }

    public void changeCardBetweenDecks(Matcher matcher) {
        DuelPlayer player = duel.getPlayer1(); // we dont know who! now just for example player 1
        if (duel.getNumberOfRounds() != 3) view.showError(Error.CHANGE_CARDS_IN_ONE_ROUND_DUEL);
        else {
            if (!player.getPlayDeck().containsMainCard(matcher.group("cardNameInMainDeck"))) {
                view.showError(Error.CARD_IS_NOT_IN_MAIN_DECK_TO_CHANGE);
            } else if (player.getPlayDeck().containsSideCard(matcher.group("cardNameInSideDeck"))) {
                view.showError(Error.CARD_IS_NOT_IN_SIDE_DECK_TO_CHANGE);
            } else {
                duel.getPlayer1().getPlayDeck().addCardToSideDeck(Card.getCardByName(matcher.group("cardNameInMainDeck")));
                duel.getPlayer1().getPlayDeck().addCardToMainDeck(Card.getCardByName(matcher.group("cardNameInSideDeck")));
                duel.getPlayer1().getPlayDeck().removeCardFromMainDeck(Card.getCardByName(matcher.group("cardNameInMainDeck")));
                duel.getPlayer1().getPlayDeck().removeCardFromSideDeck(Card.getCardByName(matcher.group("cardNameInSideDeck")));
            }
        }
    }

    private void setStartHandCards(DuelPlayer duelPlayer1, DuelPlayer duelPlayer2) {
        Deck deckFirstPlayer = duelPlayer1.getPlayDeck();
        deckFirstPlayer.shuffleDeck();
        Deck deckSecondPlayer = duelPlayer2.getPlayDeck();
        deckSecondPlayer.shuffleDeck();
        RoundGameController roundGameController = RoundGameController.getInstance();
        for (int i = 0; i < 5; i++) {
            roundGameController.addCardToFirstPlayerHand(deckFirstPlayer.getMainCards().get(0));
            deckFirstPlayer.getMainCards().remove(0);
            roundGameController.addCardToSecondPlayerHand(deckSecondPlayer.getMainCards().get(0));
            deckSecondPlayer.getMainCards().remove(0);

        }
    }

    private int flipCoin() {
        Random randomNum = new Random();
        return randomNum.nextInt(2);
    }

    public String getSpecifier() {
        return specifier;
    }

    public void setSpecifier(String specifier) {
        this.specifier = specifier;
    }

    private void setNextRoundDetail() {
        duel.setCurrentRound(duel.getCurrentRound() + 1);
    }

    private void clearEveryThing() {

    }
}