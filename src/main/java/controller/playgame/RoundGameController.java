package controller.playgame;

import model.card.Card;
import model.game.DuelPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class RoundGameController {
    private static RoundGameController instance;
    private DuelPlayer firstPlayer;
    private DuelPlayer secondPlayer;
    private Card selectedCard;
    private Phase currentPhase;
    private List<Card> firstPlayerHand = new ArrayList<>();
    private List<Card> secondPlayerHand = new ArrayList<>();
    private int turn = 1; // 1 : firstPlayer, 2 : secondPlayer
    private DuelGameController duelGameController = DuelGameController.getInstance();

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

    public Phase getCurrentPhase() {
        return currentPhase;
    }

    public void setRoundInfo(DuelPlayer firstPlayer, DuelPlayer secondPlayer) {

    }

    private void changeTurn() {
        selectedCard = null;
        turn = (turn == 1) ? 2 : 1;
    }

    public void selectCard(Matcher matcher) {

    }

    private void selectCardInHand() {

    }

    public void deselectCard() {

    }

    public void summonMonster() {

    }

    public void setMonster() {

    }

    public void changeCardPosition() {

    }

    public void setSpellOrTrap() {

    }

    public void faceUpSpellOrTrap() {

    }

    public void activateEffect() {

    }

    public void monsterEffect(Card card) {

    }

    public void spellEffect(Card card) {

    }

    public void attackToCard(Matcher matcher) {

    }

    public void drawCardFromDeck() {
        DuelPlayer currentPlayer = getCurrentPlayer();
        Card card;
        if ((card = currentPlayer.getPlayDeck().getMainCards().get(0)) != null) {
            currentPlayer.getPlayDeck().getMainCards().remove(0);
            addCardToFirstPlayerHand(card);
        } else {
            duelGameController.checkGameResult(currentPlayer);// no card so this is loser!
        }
    }

    public void flipSummon() {

    }

    public void directAttack() {

    }

    public void killCard() {

    }

    public void nextPhase() {
        if (currentPhase.equals(Phase.DRAW_PHASE)) {
            currentPhase = Phase.STAND_BY_PHASE;
        } else if (currentPhase.equals(Phase.STAND_BY_PHASE)) {
            currentPhase = Phase.MAIN_PHASE_1;
        } else if (currentPhase == Phase.MAIN_PHASE_1) {
            currentPhase = Phase.BATTLE_PHASE;
        } else if (currentPhase == Phase.BATTLE_PHASE) {
            currentPhase = Phase.MAIN_PHASE_2;
        } else if (currentPhase == Phase.MAIN_PHASE_2) {
            currentPhase = Phase.DRAW_PHASE;
            changeTurn();
        }
    }

    public List<Card> getFirstPlayerHand() {
        return firstPlayerHand;
    }

    public List<Card> getSecondPlayerHand() {
        return secondPlayerHand;
    }

    public void addCardToFirstPlayerHand(Card card) {
        firstPlayerHand.add(card);
    }

    public void addCardToSecondPlayerHand(Card card) {
        secondPlayerHand.add(card);
    }

    private DuelPlayer getCurrentPlayer() {
        if (getTurn() == 1)
            return firstPlayer;
        return secondPlayer;
    }
}