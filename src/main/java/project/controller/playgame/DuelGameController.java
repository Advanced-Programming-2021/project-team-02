package project.controller.playgame;

import project.model.Assets;
import project.model.Deck;
import project.model.User;
import project.model.game.Duel;
import project.model.game.DuelPlayer;
//import project.view.Menu;
//import project.view.MenusManager;
import project.view.gameview.GameView;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;


public class DuelGameController {
    private static DuelGameController instance = null;
    private GameView view;//= GameView.getInstance();

    private Duel duel;
    private String specifier;

    private DuelGameController() {

    }

    public static DuelGameController getInstance() {
        if (instance == null) instance = new DuelGameController();
        return instance;
    }

    public Duel getDuel() {
        return duel;
    }

    public void startDuel(Duel duel) {
        this.duel = duel;
        duel.setCurrentRound(1);
        if (duel.isWithAi()) {
            //TODO MenusManager.getInstance().changeMenu(Menu.ONGOING_GAME_WITH_AI);
            return;
        }
        //TODO MenusManager.getInstance().changeMenu(Menu.ONGOING_GAME);
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
        //view.showSuccessMessage(SuccessMessage.GAME_STARTED);
        RoundGameController.getInstance().setRoundInfo(first, second, instance,  duel.isWithAi());

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
                return matchResultCheck(winner, loser);
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
            updateScoreAndCoinForThreeRounds(duel.getWinner(), duel.getLoser(), 3);
            return GameResult.GAME_FINISHED;
        } else {
            duel.addLifePointOfPlayer1(duel.getPlayer1().getLifePoint());
            duel.addLifePointOfPlayer2(duel.getPlayer2().getLifePoint());
            duel.setWinners(winner.getNickname());
            if (duel.getCurrentRound() == 2) {
                if (isMatchFinished()) {
                    updateScoreAndCoinForThreeRounds(duel.getWinner(), duel.getLoser(), 2);
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
        int winnerLife = 0;
        int loserLife = 0;
        if (winner == duel.getPlayer1()) {
            Objects.requireNonNull(Assets.getAssetsByUsername(Objects.requireNonNull(User.getUserByNickName(winner.getNickname()))
                    .getUsername())).increaseCoin(1000 + winner.getLifePoint());
            Objects.requireNonNull(Assets.getAssetsByUsername(Objects.requireNonNull(User.getUserByNickName(loser.getNickname()))
                    .getUsername())).increaseCoin(100);
            winnerLife = duel.getPlayer1().getLifePoint();
            loserLife = duel.getPlayer2().getLifePoint();

        } else if (winner == duel.getPlayer2()) {
            Objects.requireNonNull(Assets.getAssetsByUsername(Objects.requireNonNull(User.getUserByNickName(winner.getNickname()))
                    .getUsername())).increaseCoin(1000 + winner.getLifePoint());
            Objects.requireNonNull(Assets.getAssetsByUsername(Objects.requireNonNull(User.getUserByNickName(loser.getNickname()))
                    .getUsername())).increaseCoin(100);
            loserLife = duel.getPlayer1().getLifePoint();
            winnerLife = duel.getPlayer2().getLifePoint();
        }
        System.out.println("inja nmeorese");
        Objects.requireNonNull(User.getUserByNickName(winner.getNickname())).increaseScore(1000);
        RoundGameController.getInstance().getView().showFinishRoundPopUpMessageAndCloseGameView(winner.getNickname());
        //view.showSuccessMessageWithTwoIntegerAndOneString(SuccessMessage.WIN_MESSAGE_ROUND_MATCH, Objects.requireNonNull(User.getUserByNickName(winner.getNickname()))
        //        .getUsername(), winnerLife, loserLife);

    }

    public void updateScoreAndCoinForThreeRounds(DuelPlayer winner, DuelPlayer loser, int endRoundNum) {
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
        int lost;
        if (endRoundNum == 2)
            lost = 0;
        else lost = 1;
        RoundGameController.getInstance().getView().showFinishMatchAndCloseGameView(winner.getNickname() + " won game : 2-"+lost);
        //view.showSuccessMessageWithTwoIntegerAndOneStringForSeveralWins(SuccessMessage.WIN_MESSAGE_FOR_HOLE_MATCH,
        //        Objects.requireNonNull(Objects.requireNonNull(User.getUserByNickName(winner.getNickname())).getUsername()),
        //        2, lost
        //);

    }

    public void setStartHandCards() {
        Deck deckFirstPlayer = RoundGameController.getInstance().getFirstPlayer().getPlayDeck();
        deckFirstPlayer.shuffleDeck();
        Deck deckSecondPlayer = RoundGameController.getInstance().getSecondPlayer().getPlayDeck();
       deckSecondPlayer.shuffleDeck();
        RoundGameController roundGameController = RoundGameController.getInstance();
        for (int i = 0; i < 5; i++) {
            roundGameController.addCardToFirstPlayerHand(deckFirstPlayer.getMainCards().get(0));
            deckFirstPlayer.getMainCards().remove(0);
            roundGameController.addCardToSecondPlayerHand(deckSecondPlayer.getMainCards().get(0));
            deckSecondPlayer.getMainCards().remove(0);
        }
    }

    public int flipCoinAndSetStarter() {
        Random random = new Random();
        int randomNum = random.nextInt() % 2;
        randomNum = randomNum + 3;
        if (randomNum == 3) {
            RoundGameController.getInstance().setRoundInfo(duel.getPlayer1(), duel.getPlayer2(),  instance, duel.isWithAi());
        } else {
            RoundGameController.getInstance().setRoundInfo(duel.getPlayer2(), duel.getPlayer1(),  instance, duel.isWithAi());
        }
        return randomNum;
    }

    public String getSpecifier() {
        return specifier;
    }

    public void setSpecifier(String specifier) {
        this.specifier = specifier;
    }

}