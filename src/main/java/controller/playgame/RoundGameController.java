package controller.playgame;

import model.card.Card;
import model.card.Monster;
import model.card.informationofcards.MonsterActionType;
import model.game.DuelPlayer;
import model.game.board.Cell;
import model.game.board.CellStatus;
import model.game.board.Zone;
import view.gameview.GameView;
import view.messages.Error;
import view.messages.SuccessMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class RoundGameController {
    private static RoundGameController instance;
    private final GameView view = GameView.getInstance();
    private DuelPlayer firstPlayer;
    private DuelPlayer secondPlayer;
    private Cell selectedCell = null;
    private Zone selectedCellZone = Zone.NONE;
    private boolean isSummonOrSetOfMonsterUsed = false;
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
        selectedCell = null;
        turn = (turn == 1) ? 2 : 1;
    }

    public void selectCard(Matcher matcher) {

    }

    private void selectCardInHand(Matcher matcher) {
        int address = Integer.parseInt(matcher.group("address"));
        //errors to check
        ArrayList<Card> hand = (ArrayList<Card>) (getTurn() == 1 ? firstPlayerHand : secondPlayerHand);
        selectedCellZone = Zone.HAND;
        selectedCell.setCardInCell(hand.get(address));
        selectedCell.setCellStatus(CellStatus.IN_HAND);
        view.showSuccessMessage(SuccessMessage.CARD_SELECTED);
        //???????????????????????//
    }

    private void selectCardInMonsterZone(Matcher matcher) {
        int address = Integer.parseInt(matcher.group("address"));
        //errors to check
        selectedCellZone = Zone.MONSTER_ZONE;
        selectedCell = getCurrentPlayer().getPlayerBoard().getACellOfBoard(selectedCellZone, address);
        view.showSuccessMessage(SuccessMessage.CARD_SELECTED);
    }

    private void selectCardInSpellZone(Matcher matcher) {
        int address = Integer.parseInt(matcher.group("address"));
        //errors to check
        selectedCellZone = Zone.SPELL_ZONE;
        selectedCell = getCurrentPlayer().getPlayerBoard().getACellOfBoard(selectedCellZone, address);
        view.showSuccessMessage(SuccessMessage.CARD_SELECTED);
    }

    public void deselectCard() {
        if (selectedCell == null) {
            view.showError(Error.NO_CARD_SELECTED_YET);
            return;
        }
        selectedCell = null;
        view.showSuccessMessage(SuccessMessage.CARD_DESELECTED);

    }

    public void summonMonster() {
        if (!isValidSelectionForSummonOrSet()) {
            return;
        }
        if ((!selectedCellZone.equals(Zone.HAND)) || (selectedCell.getCellStatus().equals(CellStatus.EMPTY))) {
            view.showError(Error.CAN_NOT_SUMMON);
            return;
        }
        if (!currentPhase.equals(Phase.BATTLE_PHASE)) {
            view.showError(Error.ACTION_NOT_ALLOWED);
            return;
        }
        Monster monster = ((Monster) selectedCell.getCardInCell());
        if (monster.getMonsterActionType().equals(MonsterActionType.RITUAL)) {
            ritualSummon();
            return;
        }
        if (monster.getLevel() > 4) {
            tributeSummon();
            return;
        }
        normalSummon();
    }

    private void normalSummon() {
        getCurrentPlayer().getPlayerBoard().addMonsterToBoard((Monster) selectedCell.getCardInCell(), CellStatus.OFFENSIVE_OCCUPIED);
        isSummonOrSetOfMonsterUsed = true;
        view.showSuccessMessage(SuccessMessage.SUMMONED_SUCCESSFULLY);
    }

    private void tributeSummon() {

    }

    private void ritualSummon() {

    }

    public void setMonster() {
        if (!isValidSelectionForSummonOrSet()) {
            return;
        }
        if ((!selectedCellZone.equals(Zone.HAND)) || (selectedCell.getCellStatus().equals(CellStatus.EMPTY))) {
            view.showError(Error.CAN_NOT_SET);
            return;
        }
        if (!(currentPhase.equals(Phase.MAIN_PHASE_1) || currentPhase.equals(Phase.MAIN_PHASE_2))) {
            view.showError(Error.ACTION_NOT_ALLOWED);
            return;
        }
        Monster monster = (Monster) selectedCell.getCardInCell();
        getCurrentPlayer().getPlayerBoard().addMonsterToBoard(monster, CellStatus.DEFENSIVE_HIDDEN);
    }

    private boolean isValidSelectionForSummonOrSet() {
        if (selectedCellZone.equals(Zone.NONE)) {
            view.showError(Error.NO_CARD_SELECTED_YET);
            return false;
        } else if (getCurrentPlayer().getPlayerBoard().isMonsterZoneFull()) {
            view.showError(Error.MONSTER_ZONE_IS_FULL);
            return false;
        } else if (isSummonOrSetOfMonsterUsed) {
            view.showError(Error.ALREADY_SUMMONED_OR_SET);
            return false;
        }
        return true;
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
            view.showSuccessMessageWithAString(SuccessMessage.CARD_ADDED_TO_THE_HAND, card.getName());
        } else {
            //a error is needed i guess! because he couldn't draw card ...
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
            view.showSuccessMessageWithAString(SuccessMessage.PLAYERS_TURN, getCurrentPlayer().getNickname());
            isSummonOrSetOfMonsterUsed = false;
            selectedCell = null;
            selectedCellZone = Zone.NONE;
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