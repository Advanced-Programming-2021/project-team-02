package controller.playgame;

import model.card.Card;
import model.game.DuelPlayer;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class RoundGameController {
    private static RoundGameController instance;
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

    public static RoundGameController getInstance() {
        if (instance == null) instance = new RoundGameController();
        return instance;
    }

    public void run() {

    }

    public int getTurn() {
        return turn;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setRoundInfo(DuelPlayer firstPlayer, DuelPlayer secondPlayer) {

    }

    private void changeTurn() {

    }

    private void selectCard(Matcher matcher) {

    }

    private void deselectCard() {

    }

    private void summonMonster() {

    }

    private void setMonster() {

    }

    private void changeCardPosition() {

    }

    private void setSpellOrTrap() {

    }

    private void faceUpSpellOrTrap() {

    }

    private void activateEffect() {

    }

    private void monsterEffect(Card card) {

    }

    private void spellEffect(Card card) {

    }

    private void attackToCard(Matcher matcher) {

    }

    private void drawCardFromDeck() {

    }

    private void flipSummon() {

    }

    private void directAttack() {

    }

    private void killCard() {

    }

    private void changePhase() {

    }

    public ArrayList<Card> getFirstPlayerHand() {
        return firstPlayerHand;
    }

    public ArrayList<Card> getSecondPlayerHand() {
        return secondPlayerHand;
    }

    public void setFirstPlayerHand(Card card) {
        firstPlayerHand.add(card);
    }

    public void setSecondPlayerHand(Card card) {
        secondPlayerHand.add(card);
    }
}