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

    public static DuelGameController getInstance() {
        if (instance == null) instance = new DuelGameController();
        return instance;
    }

    public void startDuel(Duel duel) {
        this.duel = duel;
        setStartHandCards(duel.getPlayer1(), duel.getPlayer2());
        starterSpecifier();

        MenusManager.getInstance().changeMenu(Menu.ONGOING_GAME);
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

    public void checkGameResult(DuelPlayer winner, DuelPlayer loser, GameResult resultType) {
        if (resultType == GameResult.NO_LP) {
            if (duel.getNumberOfRounds() == 1) {
                if (isPlayerDead(winner, loser)) {
                    if (duel.getPlayer1() == winner)
                        updateScoreAndCoinForOneRound(duel.getPlayer1(), duel.getPlayer2());
                    else updateScoreAndCoinForOneRound(duel.getPlayer2(), duel.getPlayer1());
                }
            } else if (duel.getNumberOfRounds() == 3) {
                if (isPlayerDead(winner, loser)) {
                    if (duel.getPlayer1() == winner)
                        updateScoreAndCoinForThreeRounds(duel.getPlayer1(), duel.getPlayer2());
                    else updateScoreAndCoinForThreeRounds(duel.getPlayer2(), duel.getPlayer1());
                }
            }
        } else if (resultType == GameResult.SURRENDER || resultType == GameResult.NO_CARDS_TO_DRAW) {
            if (duel.getNumberOfRounds() == 1) {
                if (duel.getPlayer1() == winner)
                    updateScoreAndCoinForOneRound(duel.getPlayer1(), duel.getPlayer2());
                else updateScoreAndCoinForOneRound(duel.getPlayer2(), duel.getPlayer1());
            } else {
                if (duel.getPlayer1() == winner)
                    updateScoreAndCoinForThreeRounds(duel.getPlayer1(), duel.getPlayer2());
                else updateScoreAndCoinForThreeRounds(duel.getPlayer2(), duel.getPlayer1());
            }
        }

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
        view.showSuccessMessageWithTwoIntegerAndOneString(SuccessMessage.SURRENDER_MESSAGE, Objects.requireNonNull(User.getUserByNickName(winner.getNickname()))
                        .getUsername(), Objects.requireNonNull(User.getUserByNickName(winner.getNickname())).getScore(),
                Objects.requireNonNull(User.getUserByNickName(loser.getNickname())).getScore());
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
        //TODO deckFirstPlayer.shuffleDeck(); commented them because of test
        Deck deckSecondPlayer = duelPlayer2.getPlayDeck();
        //TODO deckSecondPlayer.shuffleDeck(); commented them because of test
        RoundGameController roundGameController = RoundGameController.getInstance();
        for (int i = 0; i < 5; i++) {
            roundGameController.addCardToFirstPlayerHand(deckFirstPlayer.getMainCards().get(i));
            deckFirstPlayer.getMainCards().remove(i);
            roundGameController.addCardToSecondPlayerHand(deckSecondPlayer.getMainCards().get(i));
            deckSecondPlayer.getMainCards().remove(i);
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
}