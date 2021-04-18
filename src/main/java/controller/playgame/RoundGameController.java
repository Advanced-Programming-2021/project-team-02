package controller.playgame;

import model.card.Card;
import model.game.DuelPlayer;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class RoundGameController {
    private static final RoundGameController instance;
    private DuelPlayer firstPlayer;
    private DuelPlayer secondPlayer;
    private Card selectedCard;
    private Phase phase;
    private ArrayList<Card> firstPlayerHand;
    private ArrayList<Card> secondPlayerHand;
    private int turn = 1; // 1 : firstPlayer, 2 : secondPlayer

    static {
        instance = new RoundGameController();
    }

    public int getTurn() {
        return turn;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setRoundInfo(DuelPlayer firstPlayer, DuelPlayer secondPlayer) {

    }

    public void changeTurn() {

    }

    public void selectCard(Matcher matcher) {

    }

    public void summonMonster() {

    }

    public void setMonster() {

    }

    public void changeCardPosition() {

    }

    public void setSpellOrTrap() {

    }

    public void attackSpellOrTrap() {

    }

    public void activateEffect() {

    }

    public void monsterEffect(Card card) {

    }

    public void spellEffect(Card card) {

    }

    public void attackToCard(Matcher matcher) {

    }

    public void drawCardFromHand() {

    }


    public void flipSummon() {

    }

    public void directAttack() {

    }

    public void killCard() {

    }

    public void changePhase() {

    }
}
