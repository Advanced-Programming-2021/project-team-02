package controller.playgame;

import model.game.Duel;

import java.util.Random;
import java.util.regex.Matcher;


public class DuelGameController {
    private static final DuelGameController instance;
    private Duel duel;

    static {
        instance = new DuelGameController();
    }

    public static DuelGameController getInstance() {
        return instance;
    }

    public void startDuel(Duel duel) {
        this.duel = duel;
        starterSpecifier();
    }

    public void starterSpecifier() {

    }

    public void checkGameResult() {

    }

    public void updateScoreAndCoin() {

    }

    public void changeCardBetweenDecks(Matcher matcher) {

    }

    private void setStartHandCards() {

    }

    private int flipCoin() {
        Random randomNum = new Random();
        int result = randomNum.nextInt(2);
        int heads = 0;
        int tails = 1;
        return 1;
    }
}