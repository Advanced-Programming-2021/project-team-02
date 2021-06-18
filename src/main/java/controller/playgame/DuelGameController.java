package controller.playgame;

import model.Assets;
import model.Deck;
import model.User;
import model.game.Duel;
import model.game.DuelPlayer;
import view.Menu;
import view.MenusManager;
import view.gameview.GameView;
import view.messages.SuccessMessage;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;


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
        view.showSuccessMessage(SuccessMessage.GAME_STARTED);
        this.duel = duel;
        duel.setCurrentRound(1);
        setStartHandCards(duel.getPlayer1(), duel.getPlayer2());
        starterSpecifier();
        if (duel.isWithAi()) {
            MenusManager.getInstance().changeMenu(Menu.ONGOING_GAME_WITH_AI);
            return;
        }
        MenusManager.getInstance().changeMenu(Menu.ONGOING_GAME);
    }

    public void startNextRound() {
        DuelPlayer first;
        DuelPlayer second;
        if (duel.getPlayer1().getNickname().equals(specifier)) {
            first = duel.getPlayer2();
            second = duel.getPlayer1();
        } else {
            first = duel.getPlayer1();
            second = duel.getPlayer2();
        }
        view.showSuccessMessage(SuccessMessage.GAME_STARTED);
        setStartHandCards(first, second);
        RoundGameController.getInstance().setRoundInfo(first, second, GameView.getInstance(), this, duel.isWithAi());


    }

    public void starterSpecifier() {
        if (flipCoin() == 1) {
            setSpecifier(duel.getPlayer1().getNickname());
            RoundGameController.getInstance().setRoundInfo(duel.getPlayer1(), duel.getPlayer2(), view, instance, duel.isWithAi());
        } else {
            setSpecifier(duel.getPlayer2().getNickname());
            RoundGameController.getInstance().setRoundInfo(duel.getPlayer2(), duel.getPlayer1(), view, instance, duel.isWithAi());
        }
    }

    public GameResult checkGameResult(DuelPlayer winner, DuelPlayer loser, GameResultToCheck resultType) {
        if (resultType == GameResultToCheck.NO_LP) {
            if (duel.getNumberOfRounds() == 1) {

                if (isPlayerDead(winner, loser)) {
                    duel.setWinnerAndLoser(winner, loser);
                    if (duel.getPlayer1() == winner) {
                        updateScoreAndCoinForOneRound(duel.getPlayer1(), duel.getPlayer2());
                    } else updateScoreAndCoinForOneRound(duel.getPlayer2(), duel.getPlayer1());
                    return GameResult.GAME_FINISHED;
                }
                return GameResult.CONTINUE;
            } else if (duel.getNumberOfRounds() == 3) {
                if (isPlayerDead(winner, loser)) {
                    return matchResultCheck(winner, loser);
                }
            }
        } else if (resultType == GameResultToCheck.SURRENDER) {
            if (duel.getNumberOfRounds() == 1) {
                duel.setWinnerAndLoser(winner, loser);
                if (duel.getPlayer1() == winner)
                    updateScoreAndCoinForOneRound(duel.getPlayer1(), duel.getPlayer2());
                else updateScoreAndCoinForOneRound(duel.getPlayer2(), duel.getPlayer1());
            } else {
                return matchResultCheck(winner, loser);
            }
            return GameResult.GAME_FINISHED;
        } else if (resultType == GameResultToCheck.NO_CARDS_TO_DRAW) {
            if (duel.getNumberOfRounds() == 1) {
                duel.setWinnerAndLoser(winner, loser);
                if (duel.getPlayer1() == winner) {
                    updateScoreAndCoinForOneRound(duel.getPlayer1(), duel.getPlayer2());
                } else updateScoreAndCoinForOneRound(duel.getPlayer2(), duel.getPlayer1());
                return GameResult.GAME_FINISHED;
            } else {
                if (duel.getCurrentRound() != 3) {
                    duel.addLifePointOfPlayer1(duel.getPlayer1().getLifePoint());
                    duel.addLifePointOfPlayer2(duel.getPlayer2().getLifePoint());
                    duel.setWinners(winner.getNickname());
                    duel.setCurrentRound(duel.getCurrentRound() + 1);
                    return GameResult.ROUND_FINISHED;
                } else {
                    updateScoreAndCoinForThreeRounds(winner, loser);
                    return GameResult.GAME_FINISHED;
                }
            }
        }
        return null;
    }

    private GameResult matchResultCheck(DuelPlayer winner, DuelPlayer loser) {
        if (duel.getCurrentRound() == 3) {
            duel.addLifePointOfPlayer1(duel.getPlayer1().getLifePoint());
            duel.addLifePointOfPlayer2(duel.getPlayer2().getLifePoint());
            duel.setWinners(winner.getNickname());
            findWinnerOfMatch();
            updateScoreAndCoinForThreeRounds(duel.getWinner(), duel.getLoser());
            return GameResult.GAME_FINISHED;
        } else {
            duel.addLifePointOfPlayer1(duel.getPlayer1().getLifePoint());
            duel.addLifePointOfPlayer2(duel.getPlayer2().getLifePoint());
            duel.setWinners(winner.getNickname());
            if (duel.getCurrentRound() == 2) {
                if (isMatchFinished()) {
                    updateScoreAndCoinForThreeRounds(duel.getWinner(), duel.getLoser());
                    return GameResult.GAME_FINISHED;
                }
            }
            duel.setCurrentRound(duel.getCurrentRound() + 1);
            return GameResult.ROUND_FINISHED;
        }
    }

    private void findWinnerOfMatch() {
        ArrayList<String> winners = duel.getWinners();
        String name1 = winners.get(0);
        String name2 = winners.get(1);
        String name3 = winners.get(2);
        if (name1.equals(name3)) {
            if (name1.equals(duel.getPlayer1().getNickname()))
                duel.setWinnerAndLoser(duel.getPlayer1(), duel.getPlayer2());
            else duel.setWinnerAndLoser(duel.getPlayer2(), duel.getPlayer1());

        } else {
            if (name2.equals(duel.getPlayer1().getNickname())) {
                duel.setWinnerAndLoser(duel.getPlayer1(), duel.getPlayer2());
            } else duel.setWinnerAndLoser(duel.getPlayer2(), duel.getPlayer1());
        }
    }

    private boolean isMatchFinished() {
        ArrayList<String> winners = duel.getWinners();
        String name1 = winners.get(0);
        String name2 = winners.get(1);
        if (name1.equals(name2)) {
            if (name1.equals(duel.getPlayer1().getNickname()))
                duel.setWinnerAndLoser(duel.getPlayer1(), duel.getPlayer2());
            else duel.setWinnerAndLoser(duel.getPlayer2(), duel.getPlayer1());
            return true;
        }
        return false;
    }

    private boolean isPlayerDead(DuelPlayer winner, DuelPlayer loser) {
        return winner.getLifePoint() > 0 && loser.getLifePoint() == 0;
    }

    public void updateScoreAndCoinForOneRound(DuelPlayer winner, DuelPlayer loser) {
        if (winner == duel.getPlayer1()) {
            Objects.requireNonNull(Assets.getAssetsByUsername(Objects.requireNonNull(User.getUserByNickName(winner.getNickname()))
                    .getUsername())).increaseCoin(1000 + winner.getLifePoint());
            Objects.requireNonNull(Assets.getAssetsByUsername(Objects.requireNonNull(User.getUserByNickName(loser.getNickname()))
                    .getUsername())).increaseCoin(100);

        } else if (winner == duel.getPlayer2()) {
            Objects.requireNonNull(Assets.getAssetsByUsername(Objects.requireNonNull(User.getUserByNickName(winner.getNickname()))
                    .getUsername())).increaseCoin(1000 + winner.getLifePoint());
            Objects.requireNonNull(Assets.getAssetsByUsername(Objects.requireNonNull(User.getUserByNickName(loser.getNickname()))
                    .getUsername())).increaseCoin(100);
        }
        Objects.requireNonNull(User.getUserByNickName(winner.getNickname())).increaseScore(1000);
        view.showSuccessMessageWithAString(SuccessMessage.SURRENDER_MESSAGE, Objects.requireNonNull(User.getUserByNickName(winner.getNickname()))
                .getUsername());

    }

    public void updateScoreAndCoinForThreeRounds(DuelPlayer winner, DuelPlayer loser) {
        Objects.requireNonNull(User.getUserByNickName(winner.getNickname())).increaseScore(3000);
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
        ArrayList<String> roundsWinner = duel.getWinners();
        if (roundsWinner.get(0).equals(roundsWinner.get(1))) {
            view.showSuccessMessageWithTwoIntegerAndOneStringForSeveralWins(SuccessMessage.SURRENDER_MESSAGE_FOR_HOLE_MATCH,
                    Objects.requireNonNull(Objects.requireNonNull(User.getUserByNickName(winner.getNickname())).getUsername()),
                    Objects.requireNonNull(User.getUserByNickName(winner.getNickname())).getScore(),
                    Objects.requireNonNull(User.getUserByNickName(loser.getNickname())).getScore());
        } else
            view.showSuccessMessageWithAString(SuccessMessage.SURRENDER_MESSAGE, Objects.requireNonNull(User.getUserByNickName(winner.getNickname()))
                    .getUsername());
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

}