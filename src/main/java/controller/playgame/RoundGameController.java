package controller.playgame;

import model.card.Card;
import model.card.Monster;
import model.card.Spell;
import model.card.Trap;
import model.card.informationofcards.*;
import model.card.informationofcards.CardType;
import model.card.informationofcards.MonsterActionType;
import model.card.informationofcards.TrapEffect;
import model.game.DuelPlayer;
import model.game.PlayerBoard;
import model.game.board.*;
import view.gameview.GameView;
import view.messages.Error;
import view.messages.SuccessMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;

public class RoundGameController {
    private static RoundGameController instance = null;
    private final GameView view = GameView.getInstance();
    private DuelPlayer firstPlayer;
    private DuelPlayer secondPlayer;
    private Cell selectedCell = null;
    private Zone selectedCellZone = Zone.NONE;
    private int selectedCellAddress;
    private boolean isSummonOrSetOfMonsterUsed = false;
    private Phase currentPhase;
    private List<Card> firstPlayerHand = new ArrayList<>();
    private List<Card> secondPlayerHand = new ArrayList<>();
    private int turn = 1; // 1 : firstPlayer, 2 : secondPlayer
    private DuelGameController duelGameController = DuelGameController.getInstance();
    private List<Integer> usedCellsToAttackNumbers = new ArrayList<>();
    private List<Integer> changedPositionCards = new ArrayList<>();
    private Spell fieldZoneSpell = null;
    private ArrayList<Card> fieldEffectedCards = new ArrayList<>();
    private ArrayList<Integer> fieldEffectedCardsAddress = new ArrayList<>();
    private int isFieldActivated = 0; // 0 : no - 1 : firstPlayed activated it- 2 : secondPlayer activated it
    private Cell opponentSelectedCell;
    private HashMap<Card, Monster> firstPlayerHashmapForEquipSpells = new HashMap<>();
    private HashMap<Card, Monster> secondPlayerHashmapForEquipSpells = new HashMap<>();

    private RoundGameController() {

    }

    public static RoundGameController getInstance() {
        if (instance == null) instance = new RoundGameController();
        return instance;
    }

    public DuelPlayer getFirstPlayer() {
        return firstPlayer;
    }

    public DuelPlayer getSecondPlayer() {
        return secondPlayer;
    }

    public int getTurn() {
        return turn;
    }

    public Cell getSelectedCell() {
        return selectedCell;
    }

    public Phase getCurrentPhase() {
        return currentPhase;
    }

    public void setRoundInfo(DuelPlayer firstPlayer, DuelPlayer secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        firstPlayer.setLifePoint(8000);
        secondPlayer.setLifePoint(8000);
    }

    public void changeTurn() {
        selectedCell = null;
        turn = (turn == 1) ? 2 : 1;
    }

    public void selectCardInHand(Matcher matcher) {
        int address = Integer.parseInt(matcher.group("cardNumber"));
        if (address > getCurrentPlayerHand().size()) {
            view.showError(Error.INVALID_SELECTION);
            return;
        }
        ArrayList<Card> hand = (ArrayList<Card>) (getCurrentPlayerHand());
        selectedCell = new Cell();
        selectedCellZone = Zone.HAND;
        selectedCell.setCardInCell(hand.get(address));
        selectedCell.setCellStatus(CellStatus.IN_HAND);
        view.showSuccessMessage(SuccessMessage.CARD_SELECTED);
        opponentSelectedCell = null;
    }

    public void selectCardInMonsterZone(Matcher matcher) {
        int address = Integer.parseInt(matcher.group("monsterZoneNumber"));
        if (address > 5 || address < 1) {
            view.showError(Error.INVALID_SELECTION);
            return;
        } else if (getCurrentPlayer().getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, address).getCellStatus().equals(CellStatus.EMPTY)) {
            view.showError(Error.CARD_NOT_FOUND);
            return;
        }
        selectedCellZone = Zone.MONSTER_ZONE;
        selectedCell = getCurrentPlayer().getPlayerBoard().getACellOfBoard(selectedCellZone, address);
        selectedCellAddress = address;
        view.showSuccessMessage(SuccessMessage.CARD_SELECTED);
        opponentSelectedCell = null;
    }

    public void selectCardInSpellZone(Matcher matcher) {
        int address = Integer.parseInt(matcher.group("spellZoneNumber"));
        if (address > 5 || address < 1) {
            view.showError(Error.INVALID_SELECTION);
            return;
        } else if (getCurrentPlayer().getPlayerBoard().getACellOfBoard(Zone.SPELL_ZONE, address).getCellStatus().equals(CellStatus.EMPTY)) {
            view.showError(Error.CARD_NOT_FOUND);
            return;
        }
        selectedCellZone = Zone.SPELL_ZONE;
        selectedCell = getCurrentPlayer().getPlayerBoard().getACellOfBoard(selectedCellZone, address);
        view.showSuccessMessage(SuccessMessage.CARD_SELECTED);
        opponentSelectedCell = null;
    }

    public void deselectCard(int code) {
        if (code == 1) {
            if (selectedCell == null) {
                view.showError(Error.NO_CARD_SELECTED_YET);
                return;
            }
            view.showSuccessMessage(SuccessMessage.CARD_DESELECTED);
        }
        selectedCell = null;
        selectedCellZone = Zone.NONE;
        selectedCellAddress = 0;
    }

    public void selectPlayerFieldCard() {
        if (getCurrentPlayer().getPlayerBoard().isFieldZoneEmpty()) {
            view.showError(Error.CARD_NOT_FOUND);
            return;
        }
        selectedCell = getCurrentPlayer().getPlayerBoard().getFieldZone().getFieldCell();
        view.showSuccessMessage(SuccessMessage.CARD_SELECTED);
        opponentSelectedCell = null;
    }

    public void selectOpponentFieldCard() {
        if (getOpponentPlayer().getPlayerBoard().isFieldZoneEmpty()) {
            view.showError(Error.CARD_NOT_FOUND);
            return;
        }
        opponentSelectedCell = getOpponentPlayer().getPlayerBoard().getFieldZone().getFieldCell();
        view.showSuccessMessage(SuccessMessage.CARD_SELECTED);
    }

    public void selectOpponentCardMonsterZone(Matcher matcher) {
        int address = Integer.parseInt(matcher.group("monsterZoneNumber"));
        if (address > 5 || address < 1) {
            view.showError(Error.INVALID_SELECTION);
            return;
        } else if (getOpponentPlayer().getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, address).getCellStatus().equals(CellStatus.EMPTY)) {
            view.showError(Error.CARD_NOT_FOUND);
            return;
        }
        //TODO? selectedCellZone = Zone.MONSTER_ZONE;
        opponentSelectedCell = getOpponentPlayer().getPlayerBoard().getACellOfBoard(selectedCellZone, address);
        //TODO? selectedCellAddress = address;
        view.showSuccessMessage(SuccessMessage.CARD_SELECTED);
    }

    public void selectOpponentCardSpellZone(Matcher matcher) {
        int address = Integer.parseInt(matcher.group("spellZoneNumber"));
        if (address > 5 || address < 1) {
            view.showError(Error.INVALID_SELECTION);
            return;
        } else if (getOpponentPlayer().getPlayerBoard().getACellOfBoard(Zone.SPELL_ZONE, address).getCellStatus().equals(CellStatus.EMPTY)) {
            view.showError(Error.CARD_NOT_FOUND);
            return;
        }
        //TODO? selectedCellZone = Zone.SPELL_ZONE;
        opponentSelectedCell = getOpponentPlayer().getPlayerBoard().getACellOfBoard(selectedCellZone, address);
        view.showSuccessMessage(SuccessMessage.CARD_SELECTED);
    }

    public void showSelectedCard() {
        if (selectedCell == null) {
            if (opponentSelectedCell != null) {
                view.showCard(opponentSelectedCell.getCardInCell());
            } else {
                view.showError(Error.NO_CARD_SELECTED_YET);
                return;
            }
        } else {
            view.showCard(selectedCell.getCardInCell());
        }
    }


    private void torrentialTributeTrapEffect() {
        for (int i = 1; i <= 5; i++) {
            if (!getCurrentPlayer().getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, i).getCellStatus().equals(CellStatus.EMPTY))
                addCardToGraveYard(Zone.MONSTER_ZONE, i, getCurrentPlayer());
            if (!getOpponentPlayer().getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, i).getCellStatus().equals(CellStatus.EMPTY))
                addCardToGraveYard(Zone.MONSTER_ZONE, i, getOpponentPlayer());
        }
    }

    private void trapHoleTrapEffect() {
        if (isValidSituationForTrapHoleTrapEffect()) {
            addCardToGraveYard(Zone.MONSTER_ZONE, selectedCellAddress, getCurrentPlayer());
            deselectCard(0);
        }
    }


    public void setSpellOrTrap() {
        if (selectedCell == null) {
            view.showError(Error.NO_CARD_SELECTED_YET);
        } else if (!selectedCellZone.equals(Zone.HAND)) {
            view.showError(Error.CAN_NOT_SET);
        } else if (!(selectedCell.getCardInCell().getCardType() == CardType.SPELL &&
                (currentPhase == Phase.MAIN_PHASE_1 || currentPhase == Phase.MAIN_PHASE_2))) {
            view.showError(Error.ACTION_CAN_NOT_WORK_IN_THIS_PHASE);
        } else if (getCurrentPlayer().getPlayerBoard().isSpellZoneFull()) {
            view.showError(Error.SPELL_ZONE_IS_FULL);
        } else if (((Spell) selectedCell.getCardInCell()).getSpellType().equals(SpellType.FIELD)) {//TODO

        } else { // we can change place of this for ,,, you know...
            SpellZone spellZone = getCurrentPlayer().getPlayerBoard().returnSpellZone();
            for (int i = 1; i <= 5; i++) {
                if (spellZone.getCellWithAddress(i).getCellStatus() == CellStatus.EMPTY) {
                    //TODO complete it
                }
            }
            view.showSuccessMessage(SuccessMessage.SET_SUCCESSFULLY);
        }
        deselectCard(0);
    }

    public List<Card> getCurrentPlayerHand() {
        if (getCurrentPlayer() == firstPlayer) {
            return getFirstPlayerHand();
        } else if (getCurrentPlayer() == secondPlayer) {
            return getSecondPlayerHand();
        }
        return null;
    }

    public void activateEffectOfSpellOrTrap() {
        if (selectedCell == null) {
            view.showError(Error.NO_CARD_SELECTED_YET);
            return;
        }
        if (!selectedCellZone.equals(Zone.SPELL_ZONE) && !selectedCellZone.equals(Zone.HAND)) {
            view.showError(Error.ONLY_SPELL_CAN_ACTIVE);
            return;
        }
        if (!currentPhase.equals(Phase.MAIN_PHASE_1) && !currentPhase.equals(Phase.MAIN_PHASE_2)) {
            view.showError(Error.ACTION_CAN_NOT_WORK_IN_THIS_PHASE);
            return;
        }
        if (selectedCellZone.equals(Zone.SPELL_ZONE)) {
            if (selectedCell.getCellStatus().equals(CellStatus.OCCUPIED)) {
                view.showError(Error.CARD_ALREADY_ACTIVATED);
                return;
            } else if (getCurrentPlayer().getPlayerBoard().isSpellZoneFull()) {
                view.showError(Error.SPELL_ZONE_IS_FULL);
                return;
            }
        }
        if (!isSpellOrTrapReadyToActivate()) {
            view.showError(Error.PREPARATIONS_IS_NOT_DONE);
            return;
        }
        if (selectedCell.getCardInCell().getCardType() == CardType.SPELL) {
            if (!((Spell) selectedCell.getCardInCell()).getSpellType().equals(SpellType.FIELD))
                normalSpellActivate(((Spell) selectedCell.getCardInCell()).getSpellEffect());
            else fieldZoneSpellActivate();
        } else
            normalTrapActivate();
    }

    private void normalSpellActivate(SpellEffect spellEffect) {
        switch (spellEffect) {
            case MONSTER_REBORN_EFFECT:
                monsterRebornSpell();
                break;
            case TERRAFORMING_EFFECT:
                terraFormingSpell();
                break;
            case POT_OF_GREED_EFFECT:
                potOfGreedSpell();
                break;
            case RAIGEKI_EFFECT:
                raigekiSpell();
                break;
            case HARPIES_FEATHER_DUSTER_EFFECT:
                harpiesFeatherDusterSpell();
                break;
            case DARK_HOLE_EFFECT:
                darkHoleSpell();
                break;
            case SWORD_OF_DARK_DESTRUCTION_EFFECT:
                swordOfDarkDestructionSpell();
                break;
            case BLACK_PENDANT_EFFECT:
                blackPendantSpell();
                break;
        }
    }

    private void fieldZoneSpellActivate() {
        if (fieldZoneSpell == null) {
            if (turn == 1) {
                isFieldActivated = 1;
            } else {
                isFieldActivated = 2;
            }
        } else {
            reversePreviousFieldZoneSpellEffectAndRemoveIt();
            isFieldActivated = getTurn();
        }
        fieldZoneSpell = (Spell) selectedCell.getCardInCell();
        deselectCard(0);
        findAndActivateFieldCard();
    }

    private void reversePreviousFieldZoneSpellEffectAndRemoveIt() {
        switch (fieldZoneSpell.getSpellEffect()) {
            case YAMI_EFFECT:
                yamiFieldEffectReverse();
                break;
            case FOREST_EFFECT:
                forestFieldEffectReverse();
                break;
            case CLOSED_FOREST_EFFECT:
                closedForestFieldEffectReverse();
                break;
            case UMIIRUKA_EFFECT:
                umirukaEffectReverse();
        }
        fieldZoneSpell = null;
        if (isFieldActivated == 1) {
            firstPlayer.getPlayerBoard().removeFieldSpell();
        } else {
            secondPlayer.getPlayerBoard().removeFieldSpell();
        }
    }

    private void yamiFieldEffectReverse() {
        for (Card card : fieldEffectedCards) {
            if (((Monster) card).getMonsterType().equals(MonsterType.FIEND) || ((Monster) card).getMonsterType().equals(MonsterType.SPELLCASTER)) {
                ((Monster) card).changeAttackPower(-200);
                ((Monster) card).changeDefensePower(-200);
            } else {
                ((Monster) card).changeAttackPower(+200);
                ((Monster) card).changeDefensePower(+200);
            }
        }
        fieldEffectedCards.clear();
    }

    private void forestFieldEffectReverse() {
        for (Card card : fieldEffectedCards) {
            if (((Monster) card).getMonsterType().equals(MonsterType.INSECT) || ((Monster) card).getMonsterType().equals(MonsterType.BEAST) || ((Monster) card).getMonsterType().equals(MonsterType.BEAST_WARRIOR)) {
                ((Monster) card).changeAttackPower(-200);
                ((Monster) card).changeDefensePower(-200);
            }
        }
        fieldEffectedCards.clear();
    }

    private void closedForestFieldEffectReverse() {
        for (Card card : fieldEffectedCards) {
            if (((Monster) card).getMonsterType().equals(MonsterType.BEAST_WARRIOR)) {
                ((Monster) card).changeAttackPower(-100);
            }
        }
        fieldEffectedCards.clear();
    }

    private void umirukaEffectReverse() {
        for (Card card : fieldEffectedCards) {
            if (((Monster) card).getMonsterType().equals(MonsterType.AQUA)) {
                ((Monster) card).changeAttackPower(-500);
                ((Monster) card).changeDefensePower(400);
            }
        }
        fieldEffectedCards.clear();
    }

    private void findAndActivateFieldCard() {
        switch (fieldZoneSpell.getSpellEffect()) {
            case YAMI_EFFECT:
                yamiFieldEffect();
                break;
            case FOREST_EFFECT:
                forestFieldEffect();
                break;
            case CLOSED_FOREST_EFFECT:
                closedForestFieldEffect();
                break;
            case UMIIRUKA_EFFECT:
                umiirukaFieldEffect();
                break;
        }
    }

    private void yamiFieldEffect() {
        addYamiFieldCardsToBeEffected();
        for (Card card : fieldEffectedCards) {
            if (((Monster) card).getMonsterType().equals(MonsterType.FIEND) || ((Monster) card).getMonsterType().equals(MonsterType.SPELLCASTER)) {
                ((Monster) card).changeAttackPower(200);
                ((Monster) card).changeDefensePower(200);
            } else if (((Monster) card).getMonsterType().equals(MonsterType.FAIRY)) {
                ((Monster) card).changeDefensePower(-200);
                ((Monster) card).changeAttackPower(-200);
            }
        }
    }

    private void addYamiFieldCardsToBeEffected() {
        for (int i = 1; i <= 5; i++) {
            Cell cell = getCurrentPlayer().getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, i);
            if (((Monster) cell.getCardInCell()).getMonsterType().equals(MonsterType.FIEND) || ((Monster) cell.getCardInCell()).getMonsterType().equals(MonsterType.SPELLCASTER) && !fieldEffectedCardsAddress.contains(10 + i))
                fieldEffectedCardsAddress.add(10 + i);
        }
        for (int i = 1; i <= 5; i++) {
            Cell cell = getCurrentPlayer().getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, i);
            if (((Monster) cell.getCardInCell()).getMonsterType().equals(MonsterType.FIEND) || ((Monster) cell.getCardInCell()).getMonsterType().equals(MonsterType.SPELLCASTER) && !fieldEffectedCardsAddress.contains(20 + i))
                fieldEffectedCardsAddress.add(20 + i);
        }
        addFoundCardsToBeEffectedByFieldCardToArrayList();
    }

    private void addFoundCardsToBeEffectedByFieldCardToArrayList() {
        for (int i = fieldEffectedCardsAddress.size() - 1; i >= 0; i--) {
            if (i > 20) {
                if (!getOpponentPlayer().getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, i - 20).getCellStatus().equals(CellStatus.EMPTY))
                    fieldEffectedCards.add(getOpponentPlayer().getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, i - 20).getCardInCell());

            } else {
                if (!getOpponentPlayer().getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, i - 10).getCellStatus().equals(CellStatus.EMPTY))
                    fieldEffectedCards.add(getOpponentPlayer().getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, i - 10).getCardInCell());
            }
        }
    }

    private void forestFieldEffect() {
        addForestFieldCardsToBeEffected();
        for (Card card : fieldEffectedCards) {
            if (((Monster) card).getMonsterType().equals(MonsterType.INSECT) || ((Monster) card).getMonsterType().equals(MonsterType.BEAST) || ((Monster) card).getMonsterType().equals(MonsterType.BEAST_WARRIOR)) {
                ((Monster) card).changeAttackPower(200);
                ((Monster) card).changeDefensePower(200);
            }
        }
    }

    private void addForestFieldCardsToBeEffected() {
        for (int i = 1; i <= 5; i++) {
            Cell cell = getCurrentPlayer().getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, i);
            if (((Monster) cell.getCardInCell()).getMonsterType().equals(MonsterType.INSECT) ||
                    ((Monster) cell.getCardInCell()).getMonsterType().equals(MonsterType.BEAST) ||
                    ((Monster) cell.getCardInCell()).getMonsterType().equals(MonsterType.BEAST_WARRIOR) && !fieldEffectedCardsAddress.contains(10 + i))
                fieldEffectedCardsAddress.add(10 + i);
        }
        for (int i = 1; i <= 5; i++) {
            Cell cell = getCurrentPlayer().getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, i);
            if (((Monster) cell.getCardInCell()).getMonsterType().equals(MonsterType.FIEND) && !fieldEffectedCardsAddress.contains(20 + i))
                fieldEffectedCardsAddress.add(20 + i);
        }
        addFoundCardsToBeEffectedByFieldCardToArrayList();
    }

    private void closedForestFieldEffect() {//TODO CHECK WHAT BEAST TYPE MEAN
        addClosedForestFieldCardsToBeEffected();
        for (Card card : fieldEffectedCards) {
            if (((Monster) card).getMonsterType().equals(MonsterType.BEAST)) {
                ((Monster) card).changeAttackPower(100);
            }
        }
    }

    private void addClosedForestFieldCardsToBeEffected() {
        for (int i = 1; i <= 5; i++) {
            Cell cell = getCurrentPlayer().getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, i);
            if (((Monster) cell.getCardInCell()).getMonsterType().equals(MonsterType.BEAST) && !fieldEffectedCardsAddress.contains(10 + i))
                fieldEffectedCardsAddress.add(10 + i);
        }
        addFoundCardsToBeEffectedByFieldCardToArrayList();
    }

    private void umiirukaFieldEffect() {
        addUmiirukaFieldCardsToBeEffected();
        for (Card card : fieldEffectedCards) {
            if (((Monster) card).getMonsterType().equals(MonsterType.AQUA)) {
                ((Monster) card).changeAttackPower(500);
                ((Monster) card).changeDefensePower(-400);
            }
        }
    }

    private void addUmiirukaFieldCardsToBeEffected() {
        for (int i = 1; i <= 5; i++) {
            Cell cell = getCurrentPlayer().getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, i);
            if (((Monster) cell.getCardInCell()).getMonsterType().equals(MonsterType.AQUA) &&
                    !fieldEffectedCardsAddress.contains(10 + i))
                fieldEffectedCardsAddress.add(10 + i);
        }
        addFoundCardsToBeEffectedByFieldCardToArrayList();
    }

    private void normalTrapActivate() {
        switch (((Trap) selectedCell.getCardInCell()).getTrapEffect()) {
            case MIND_CRUSH_EFFECT:
                mindCrushTrapEffect();
                return;
            default:
                view.showError(Error.PREPARATIONS_IS_NOT_DONE);
        }
    }

    private void mindCrushTrapEffect() {
        boolean happened = false;
        while (true) {
            String cardName = view.askCardName();
            if (Card.getCardByName(cardName) == null) {
                view.showError(Error.WRONG_CARD_NAME);
            } else {
                Card card = Card.getCardByName(cardName);
                for (Card handCard : getOpponentHand()) {
                    if (handCard.getName().equals(cardName)) {
                        happened = true;
                        getOpponentHand().remove(handCard);
                    }
                }
                if (!happened) {
                    getCurrentPlayerHand().remove(0);
                }
                view.showSuccessMessage(SuccessMessage.TRAP_ACTIVATED);
                addCardToGraveYard(Zone.SPELL_ZONE, selectedCellAddress, getCurrentPlayer());
                return;

            }
        }
    }


    private boolean isSpellOrTrapReadyToActivate() {
        if (selectedCell.getCardInCell().getCardType().equals(CardType.SPELL)) {

        } else {

        }
        return false;
    }


    private void TrapMagicCylinderEffect() {
        getCurrentPlayer().decreaseLP(((Monster) selectedCell.getCardInCell()).getAttackPower());
        addCardToGraveYard(Zone.MONSTER_ZONE, selectedCellAddress, getCurrentPlayer());
    }

    private void TrapMirrorForceEffect() {
        for (int i = 1; i <= 5; i++) {
            if (getCurrentPlayer().getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, i).getCellStatus().equals(CellStatus.OFFENSIVE_OCCUPIED))
                addCardToGraveYard(Zone.MONSTER_ZONE, i, getCurrentPlayer());
        }
        view.showSuccessMessage(SuccessMessage.TRAP_ACTIVATED);
    }

    private void TrapNegateAttackEffect() {
        deselectCard(0);
        nextPhase();
    }

    private boolean hasCardUsedItsAttack() {
        for (Integer cell : usedCellsToAttackNumbers) {
            if (cell == selectedCellAddress)
                return true;
        }
        return false;
    }

    private void attackUsed() {
        usedCellsToAttackNumbers.add(selectedCellAddress);
    }

    private boolean hasCardChangedPosition() {
        for (Integer positionCard : changedPositionCards) {
            if (positionCard.equals(selectedCellAddress))
                return true;
        }
        return false;
    }

    private void changePositionUsed() {
        usedCellsToAttackNumbers.add(selectedCellAddress);
    }

    public void drawCardFromDeck() {//TODO TOUSE
        DuelPlayer currentPlayer = getCurrentPlayer();
        Card card;
        if ((card = currentPlayer.getPlayDeck().getMainCards().get(0)) != null) {
            currentPlayer.getPlayDeck().getMainCards().remove(0);
            addCardToFirstPlayerHand(card);
            view.showSuccessMessageWithAString(SuccessMessage.CARD_ADDED_TO_THE_HAND, card.getName());
        } else {
            //TODO a error is needed i guess! because he couldn't draw card ...
            duelGameController.checkGameResult(currentPlayer);// no card so this is loser!
        }
    }


    public void directAttack() { // probably no effect ...
        if (selectedCell == null) {
            view.showError(Error.NO_CARD_SELECTED_YET);
            return;
        }
        if (!selectedCellZone.equals(Zone.MONSTER_ZONE)) {
            view.showError(Error.CAN_NOT_ATTACK);
            return;
        }
        if (!selectedCell.getCellStatus().equals(CellStatus.OFFENSIVE_OCCUPIED)) { // u sure ?
            view.showError(Error.CAN_NOT_ATTACK);
            return;
        }
        if (!getCurrentPhase().equals(Phase.BATTLE_PHASE)) {
            view.showError(Error.ACTION_CAN_NOT_WORK_IN_THIS_PHASE);
            return;
        }
        if (!getCurrentPlayer().getPlayerBoard().isMonsterZoneEmpty()) {
            view.showError(Error.CANT_DIRECT_ATTACK);
            return;
        }
        if (hasCardUsedItsAttack()) {
            view.showError(Error.ALREADY_ATTACKED);
            return;
        }
        Monster monster = (Monster) selectedCell.getCardInCell();
        getOpponentPlayer().decreaseLP(monster.getAttackPower());
        view.showSuccessMessageWithAnInteger(SuccessMessage.OPPONENT_RECEIVE_DAMAGE_AFTER_DIRECT_ATTACK, monster.getAttackPower());
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
            opponentSelectedCell = null;
            usedCellsToAttackNumbers.clear();
            changedPositionCards.clear();
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

    public DuelPlayer getCurrentPlayer() {
        if (turn == 1)
            return firstPlayer;
        return secondPlayer;
    }

    private DuelPlayer getOpponentPlayer() {
        if (turn == 1)
            return secondPlayer;
        return firstPlayer;
    }

    public void cancel() {
        selectedCell = null;
        selectedCellZone = Zone.NONE;
    }

    public void monsterRebornSpell() {
        Matcher matcher;
        while (true) {
            matcher = view.monsterReborn();
            if (matcher == null) {
                cancel();
                return;
            } else if (Card.getCardByName(matcher.group(2)) == null)
                view.showError(Error.WRONG_CARD_NAME);
            else if (!(matcher.group(3).equals("OO") || matcher.group(3).equals("DO")))
                view.showError(Error.DH_POSITION);
            else break;
        }
        ArrayList<Card> currentPlayer = getCurrentPlayer().getPlayerBoard().returnGraveYard().getGraveYardCards();
        ArrayList<Card> opponentPlayer = getOpponentPlayer().getPlayerBoard().returnGraveYard().getGraveYardCards();
        if (matcher.group(1).equals("opponent") && selectedCellZone == Zone.GRAVEYARD) {
            for (Card card : opponentPlayer) {
                if (card.getName().equals(matcher.group(2))) {
                    if (matcher.group(3).equals("OO"))
                        specialSummon(selectedCell.getCardInCell(), CellStatus.OFFENSIVE_OCCUPIED);
                    else if (matcher.group(3).equals("DO"))
                        specialSummon(selectedCell.getCardInCell(), CellStatus.DEFENSIVE_OCCUPIED);
                }
            }
        } else {
            for (Card card : currentPlayer) {
                if (card.getName().equals(matcher.group(2))) {
                    if (matcher.group(3).equals("OO"))
                        specialSummon(selectedCell.getCardInCell(), CellStatus.OFFENSIVE_OCCUPIED);
                    else if (matcher.group(3).equals("DO"))
                        specialSummon(selectedCell.getCardInCell(), CellStatus.DEFENSIVE_OCCUPIED);
                }
            }
        }
        SpellZone spellZone = getCurrentPlayer().getPlayerBoard().returnSpellZone();
        int i = 0;
        while (spellZone.getCellWithAddress(i).getCellStatus() != CellStatus.EMPTY || i >= 5) {
            if (spellZone.getCellWithAddress(i).getCardInCell().getName().equals("Monster Reborn"))
                addCardToGraveYard(Zone.SPELL_ZONE, i, getCurrentPlayer());
            i++;
        }
    }

    private void terraFormingSpell() {
        // TODO show deck
        String cardName;
        while (true) {
            cardName = view.getCardNameForTerraForming();
            if (cardName.equals("cancel")) {
                cancel();
                return;
            } else if (Card.getCardByName(cardName) == null) {
                view.showError(Error.WRONG_CARD_NAME);
            } else if (SpellType.getSpellTypeByTypeName(selectedCell.getCardInCell().getName()) != SpellType.FIELD)
                view.showError(Error.CHOOSE_FIELD_SPELL);
            else break;
        }
        if (SpellType.getSpellTypeByTypeName(selectedCell.getCardInCell().getName()) == SpellType.FIELD) {
            getCurrentPlayerHand().add(selectedCell.getCardInCell());
            SpellZone spellZone = getCurrentPlayer().getPlayerBoard().returnSpellZone();
            int i = 0;
            while (spellZone.getCellWithAddress(i).getCellStatus() != CellStatus.EMPTY || i >= 5) {
                if (spellZone.getCellWithAddress(i).getCardInCell().getName().equals("Terraforming"))
                    addCardToGraveYard(Zone.SPELL_ZONE, i, getCurrentPlayer());
                i++;
            }
            getCurrentPlayer().getPlayDeck().shuffleDeck();
        }
    }

    private void potOfGreedSpell() {
        List<Card> deckCards = getCurrentPlayer().getPlayDeck().getMainCards();
        getSecondPlayerHand().add(deckCards.get(1));
        getSecondPlayerHand().add(deckCards.get(2));
        SpellZone spellZone = getCurrentPlayer().getPlayerBoard().returnSpellZone();
        int i = 0;
        while (spellZone.getCellWithAddress(i).getCellStatus() != CellStatus.EMPTY || i >= 5) {
            if (spellZone.getCellWithAddress(i).getCardInCell().getName().equals("Pot of Greed"))
                addCardToGraveYard(Zone.SPELL_ZONE, i, getCurrentPlayer());
            i++;
        }
    }

    private void raigekiSpell() {
        MonsterZone monsterZone = getOpponentPlayer().getPlayerBoard().returnMonsterZone();
        int i = 0;
        while (monsterZone.getCellWithAddress(i).getCellStatus() != CellStatus.EMPTY || i >= 5) {
            addCardToGraveYard(Zone.MONSTER_ZONE, i, getOpponentPlayer());
            i++;
        }
        SpellZone spellZone = getCurrentPlayer().getPlayerBoard().returnSpellZone();
        i = 0;
        while (spellZone.getCellWithAddress(i).getCellStatus() != CellStatus.EMPTY || i >= 5) {
            if (spellZone.getCellWithAddress(i).getCardInCell().getName().equals("Raigeki"))
                addCardToGraveYard(Zone.SPELL_ZONE, i, getCurrentPlayer());
            i++;
        }
    }

    private void harpiesFeatherDusterSpell() {
        int i = 0;
        while (getOpponentPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(i).getCellStatus() != CellStatus.EMPTY) {
            addCardToGraveYard(Zone.SPELL_ZONE, i, getOpponentPlayer());
            i++;
        }
        SpellZone spellZone = getCurrentPlayer().getPlayerBoard().returnSpellZone();
        i = 0;
        while (spellZone.getCellWithAddress(i).getCellStatus() != CellStatus.EMPTY || i >= 5) {
            if (spellZone.getCellWithAddress(i).getCardInCell().getName().equals("Harpie’s Feather Duster"))
                addCardToGraveYard(Zone.SPELL_ZONE, i, getCurrentPlayer());
            i++;
        }
    }

    public void darkHoleSpell() {
        int i = 0;
        while (getOpponentPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(i).getCellStatus() != CellStatus.EMPTY || i >= 5) {
            addCardToGraveYard(Zone.MONSTER_ZONE, i, getOpponentPlayer());
            i++;
        }
        i = 0;
        while (getCurrentPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(i).getCellStatus() != CellStatus.EMPTY || i >= 5) {
            addCardToGraveYard(Zone.MONSTER_ZONE, i, getCurrentPlayer());
            i++;
        }
        SpellZone spellZone = getCurrentPlayer().getPlayerBoard().returnSpellZone();
        i = 0;
        while (spellZone.getCellWithAddress(i).getCellStatus() != CellStatus.EMPTY || i >= 5) {
            if (spellZone.getCellWithAddress(i).getCardInCell().getName().equals("darkHole"))
                addCardToGraveYard(Zone.SPELL_ZONE, i, getCurrentPlayer());
            i++;
        }
    }

    private void swordOfDarkDestructionSpell() {
        Card spellCard = selectedCell.getCardInCell();
        Monster monster;
        String cardName;
        while (true) {
            cardName = view.swordOfDarkDestruction();
            if (cardName == null) {
                cancel();
                return;
            } else if (selectedCell.getCellStatus() != CellStatus.DEFENSIVE_OCCUPIED ||
                    selectedCell.getCellStatus() != CellStatus.OFFENSIVE_OCCUPIED) {
                view.showError(Error.DH_POSITION);
            } else if (selectedCellZone != Zone.MONSTER_ZONE) {
                view.showError(Error.CHOOSE_MONSTER_FROM_MONSTER_ZONE);
            } else if (Card.getCardByName(cardName) == null) view.showError(Error.WRONG_CARD_NAME);
            else {
                monster = (Monster) Card.getCardByName(cardName);
                if (Objects.requireNonNull(monster).getMonsterType() == MonsterType.FIEND || monster.getMonsterType() == MonsterType.SPELLCASTER)
                    break;
            }
            view.showError(Error.TYPE_FIEND_OT_SPELL_CASTER);
        }
        monster.setAttackPower(monster.getAttackPower() + 400);
        monster.setDefensePower(monster.getDefensePower() - 200);
        if (getCurrentPlayer() == firstPlayer) {
            firstPlayerHashmapForEquipSpells.put(spellCard, monster);
        } else secondPlayerHashmapForEquipSpells.put(spellCard, monster);
    }

    public void removeSwordOfDarkDestruction(Card card) {
        Monster monster = (Monster) card;
        monster.setAttackPower(monster.getAttackPower() - 400);
        monster.setDefensePower(monster.getDefensePower() + 200);
    }

    public void blackPendantSpell() {
        Card spellCard = selectedCell.getCardInCell();
        String cardName;
        while (true) {
            cardName = view.blackPendant();
            if (cardName == null) {
                cancel();
                return;
            } else if (selectedCell.getCellStatus() != CellStatus.DEFENSIVE_OCCUPIED ||
                    selectedCell.getCellStatus() != CellStatus.OFFENSIVE_OCCUPIED) {
                view.showError(Error.DH_POSITION);
            } else if (selectedCellZone != Zone.MONSTER_ZONE) {
                view.showError(Error.CHOOSE_MONSTER_FROM_MONSTER_ZONE);
            } else if (Card.getCardByName(cardName) == null) view.showError(Error.WRONG_CARD_NAME);
            else break;
        }
        Monster monster = (Monster) Card.getCardByName(cardName);
        Objects.requireNonNull(monster).setAttackPower((monster.getAttackPower()));
        if (getCurrentPlayer() == firstPlayer) {
            firstPlayerHashmapForEquipSpells.put(spellCard, monster);
        } else secondPlayerHashmapForEquipSpells.put(spellCard, monster);
    }

    public void removeBlackPendant(Card card) {
        Monster monster = (Monster) card;
        monster.setAttackPower(monster.getAttackPower() - 500);
    }


    public void surrender() {
        if (getCurrentPlayer() == firstPlayer) {
            duelGameController.checkGameResult(secondPlayer);
        } else duelGameController.checkGameResult(firstPlayer);
    }

    public void specialSummon(Card card, CellStatus cellStatus) {
        MonsterZone monsterZone = getCurrentPlayer().getPlayerBoard().returnMonsterZone();
        monsterZone.addCard((Monster) card, cellStatus);
    }

    private List<Card> getOpponentHand() {
        if (turn == 1) {
            return secondPlayerHand;
        }
        return firstPlayerHand;
    }


    private void addCardToGraveYard(Zone fromZone, int address, DuelPlayer player) {
        if (fromZone.equals(Zone.MONSTER_ZONE)) {
            Cell cell = player.getPlayerBoard().getACellOfBoard(fromZone, address);
            if (cell.getCellStatus().equals(CellStatus.EMPTY))
                return;
            checkDeathOfUnderFieldEffectCard((Monster) cell.getCardInCell());
            player.getPlayerBoard().removeMonsterFromBoardAndAddToGraveYard(address);
            if (turn == 1) {
                MonsterZone monsterZone = getFirstPlayer().getPlayerBoard().returnMonsterZone();
                for (Card card : firstPlayerHashmapForEquipSpells.keySet()) {
                    if (monsterZone.getCellWithAddress(address).getCardInCell().getName().equals(firstPlayerHashmapForEquipSpells.get(card).getName())) {
                        SpellZone spellZone = getFirstPlayer().getPlayerBoard().returnSpellZone();
                        int i = 0;
                        while (spellZone.getCellWithAddress(i).getCellStatus() != CellStatus.EMPTY || i >= 5) {
                            if (spellZone.getCellWithAddress(i).getCardInCell() == card) {
                                addCardToGraveYard(Zone.SPELL_ZONE, i, firstPlayer);
                            }
                            i++;
                        }
                    }
                }
            } else {
                MonsterZone monsterZone = getSecondPlayer().getPlayerBoard().returnMonsterZone();
                for (Card card : secondPlayerHashmapForEquipSpells.keySet()) {
                    if (monsterZone.getCellWithAddress(address).getCardInCell().getName().equals(secondPlayerHashmapForEquipSpells.get(card).getName())) {
                        SpellZone spellZone = getSecondPlayer().getPlayerBoard().returnSpellZone();
                        int i = 0;
                        while (spellZone.getCellWithAddress(i).getCellStatus() != CellStatus.EMPTY || i >= 5) {
                            if (spellZone.getCellWithAddress(i).getCardInCell() == card) {
                                addCardToGraveYard(Zone.SPELL_ZONE, i, secondPlayer);
                            }
                            i++;
                        }
                    }
                }
            }
        } else if (fromZone.equals(Zone.SPELL_ZONE)) {
            Cell cell = player.getPlayerBoard().getACellOfBoard(fromZone, address);
            //TODO check related things
            if (cell.getCellStatus().equals(CellStatus.EMPTY))
                return;
            player.getPlayerBoard().removeSpellOrTrapFromBoard(address);
        } else if (fromZone.equals(Zone.HAND)) {
            if (player.getNickname().equals(getCurrentPlayer().getNickname())) {
                Card card = getCardInHand(address);
                player.getPlayerBoard().addCardToGraveYard(card);
                getCurrentPlayerHand().remove(address);
            } else {
                Card card = getCardInHand(address);
                if (card == null)
                    return;
                player.getPlayerBoard().addCardToGraveYard(card);
                getOpponentHand().remove(address);
            }
        }
    }

    private void checkDeathOfUnderFieldEffectCard(Monster monster) {
        if (fieldZoneSpell == null)
            return;
        switch (fieldZoneSpell.getSpellEffect()) {
            case YAMI_EFFECT:
                reverseYamiFieldEffectOnOneCard(monster);
                break;
            case FOREST_EFFECT:
                reverseForestFieldEffectOnOneCard(monster);
                break;
            case CLOSED_FOREST_EFFECT:
                reverseClosedForestFieldEffecOnOneCard(monster);
                break;
            case UMIIRUKA_EFFECT:
                reverseUmiirukaFieldEffectOnOneCard(monster);
                break;
        }
    }

    private void checkNewCardToBeBeUnderEffectOfFieldCard(Monster monster) {
        if (fieldZoneSpell == null)
            return;
        switch (fieldZoneSpell.getSpellEffect()) {
            case YAMI_EFFECT:
                yamiFieldEffectOnOneCard(monster);
                break;
            case FOREST_EFFECT:
                forestFieldEffectOnOneCard(monster);
                break;
            case CLOSED_FOREST_EFFECT:
                closedForestFieldEffectOnOneCard(monster);
                break;
            case UMIIRUKA_EFFECT:
                umiirukaFieldEffectOnOneCard(monster);
                break;
        }
    }

    private void yamiFieldEffectOnOneCard(Monster monster) {
        if (monster.getMonsterType().equals(MonsterType.INSECT) || monster.getMonsterType().equals(MonsterType.BEAST)
                || monster.getMonsterType().equals(MonsterType.BEAST_WARRIOR)) {
            monster.changeAttackPower(200);
            monster.changeDefensePower(200);
        } else if (monster.getMonsterType().equals(MonsterType.FAIRY)) {
            monster.changeDefensePower(-200);
            monster.changeAttackPower(-200);
        }
    }

    private void forestFieldEffectOnOneCard(Monster monster) {
        if (monster.getMonsterType().equals(MonsterType.INSECT) ||
                monster.getMonsterType().equals(MonsterType.BEAST) ||
                monster.getMonsterType().equals(MonsterType.BEAST_WARRIOR)) {
            monster.changeAttackPower(200);
            monster.changeDefensePower(200);
        }
    }

    private void closedForestFieldEffectOnOneCard(Monster monster) {
        if (monster.getMonsterType().equals(MonsterType.BEAST)) {
            monster.changeAttackPower(100);
        }
    }

    private void umiirukaFieldEffectOnOneCard(Monster monster) {
        if (monster.getMonsterType().equals(MonsterType.AQUA)) {
            monster.changeAttackPower(500);
            monster.changeDefensePower(-400);
        }
    }

    private void reverseYamiFieldEffectOnOneCard(Monster monster) {
        if (monster.getMonsterType().equals(MonsterType.INSECT) || monster.getMonsterType().equals(MonsterType.BEAST)
                || monster.getMonsterType().equals(MonsterType.BEAST_WARRIOR)) {
            monster.changeAttackPower(-200);
            monster.changeDefensePower(-200);
        } else if (monster.getMonsterType().equals(MonsterType.FAIRY)) {
            monster.changeDefensePower(200);
            monster.changeAttackPower(200);
        }
    }

    private void reverseForestFieldEffectOnOneCard(Monster monster) {
        if (monster.getMonsterType().equals(MonsterType.INSECT) ||
                monster.getMonsterType().equals(MonsterType.BEAST) ||
                monster.getMonsterType().equals(MonsterType.BEAST_WARRIOR)) {
            monster.changeAttackPower(-200);
            monster.changeDefensePower(-200);
        }
    }

    private void reverseClosedForestFieldEffecOnOneCard(Monster monster) {
        if (monster.getMonsterType().equals(MonsterType.BEAST)) {
            monster.changeAttackPower(100);
        }
    }

    private void reverseUmiirukaFieldEffectOnOneCard(Monster monster) {
        if (monster.getMonsterType().equals(MonsterType.AQUA)) {
            monster.changeAttackPower(-500);
            monster.changeDefensePower(400);
        }
    }

    private Card getCardInHand(int address) {
        Card card;
        if (address > getCurrentPlayerHand().size())
            return null;
        if ((card = getCurrentPlayerHand().get(address)) == null) {
            return null;
        } else return card;
    }

    //MONSTER RELATED CODES :
    public void summonMonster() { //TODO might have effect
        if (selectedCell == null && opponentSelectedCell != null) {
            view.showError(Error.ONLY_CAN_SHOW_OPPONENT_CARD);
            return;
        }
        if (!isValidSelectionForSummonOrSet()) {
            return;
        }
        if (!isValidSummon()) {
            return;
        }
        //check special summon
        Monster monster = ((Monster) selectedCell.getCardInCell());
        if (selectedCell.getCardInCell().getCardType().equals(CardType.MONSTER)) {
            if (monster.getMonsterEffect().equals(MonsterEffect.GATE_GUARDIAN_EFFECT)) {
                gateGaurdianEffect(CellStatus.OFFENSIVE_OCCUPIED);
                return;
            } else if (monster.getMonsterEffect().equals(MonsterEffect.BEAST_KING_BARBAROS_EFFECT)) {
                if (beastKingBarbosEffect(CellStatus.OFFENSIVE_OCCUPIED))
                    return;
            } else if (monster.getMonsterEffect().equals(MonsterEffect.THE_TRICKY_EFFECT)) {
                if (theTrickyEffect(CellStatus.OFFENSIVE_OCCUPIED)) {
                    return;
                }
            }
        }
        // check haven't summoned more than once
        if (!isValidToNormalSummonOrSet()) {
            return;
        }
        if (monster.getMonsterActionType().equals(MonsterActionType.RITUAL)) {
            ritualSummon();
            return;
        }
        if (monster.getLevel() > 4 && monster.getLevel() <= 10) {
            tributeSummon();
            return;
        }
        normalSummon();
    }

    private boolean isValidSummon() {
        if ((!selectedCellZone.equals(Zone.HAND)) || (!selectedCell.getCardInCell().getCardType().equals(CardType.MONSTER))) {
            view.showError(Error.CAN_NOT_SUMMON);
            return false;
        }
        if (!currentPhase.equals(Phase.MAIN_PHASE_1) && !currentPhase.equals(Phase.MAIN_PHASE_2)) {
            view.showError(Error.ACTION_NOT_ALLOWED);
            return false;
        }
        return true;
    }

    private boolean isValidToNormalSummonOrSet() {
        if (isSummonOrSetOfMonsterUsed) {
            view.showError(Error.ALREADY_SUMMONED_OR_SET);
            return false;
        }
        return true;
    }

    private void gateGaurdianEffect(CellStatus status) {
        if (view.yesNoQuestion("do you want to tribute for GateGaurdian Special Summon?")) {
            if (didTribute(3, getCurrentPlayer())) {
                specialSummon(selectedCell.getCardInCell(), status);
            }
        }
    }

    private boolean beastKingBarbosEffect(CellStatus status) {
        int howToSummon = view.howToSummonBeastKingBarbos();//1-normal tribute, 2-without tribute, 3-with 3 tributes\n"
        if (howToSummon == 1) {
            return false;
        } else if (howToSummon == 2) {
            ((Monster) selectedCell.getCardInCell()).changeAttackPower(-1900);
            if (status.equals(CellStatus.OFFENSIVE_OCCUPIED)) {
                summon();
            } else set();
            return true;
        } else {
            if (didTribute(3, getCurrentPlayer())) {
                beastKingBarbosSpecialSummonEffect(status);
                return true;
            }
        }
        return false;
    }

    private void beastKingBarbosSpecialSummonEffect(CellStatus status) {
        for (int i = 1; i <= 5; i++) {
            addCardToGraveYard(Zone.MONSTER_ZONE, i, getOpponentPlayer());
        }
        for (int i = 1; i <= 5; i++) {
            addCardToGraveYard(Zone.SPELL_ZONE, i, getOpponentPlayer());
        }
        if (fieldZoneSpell != null) {
            reversePreviousFieldZoneSpellEffectAndRemoveIt();
        }
        specialSummon(selectedCell.getCardInCell(), status);
        deselectCard(0);
    }

    private boolean theTrickyEffect(CellStatus status) {
        if (view.yesNoQuestion("do you want to special summon/set it with a tribute in hand ?")) {
            while (true) {
                int address = view.chooseCardInHand();
                if (address == -1) {
                    return false;
                } else if (getCardInHand(address) == null) {
                    view.showError(Error.INVALID_SELECTION);
                } else {
                    addCardToGraveYard(Zone.HAND, address, getCurrentPlayer());
                    specialSummon(selectedCell.getCardInCell(), status);
                    deselectCard(0);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isCurrentPlayerTrapToBeActivatedInSummonSituation() {
        PlayerBoard board = getCurrentPlayer().getPlayerBoard();
        for (int i = 1; i <= 5; i++) {
            Cell cell = board.getACellOfBoard(Zone.SPELL_ZONE, i);
            if (cell.getCellStatus().equals(CellStatus.EMPTY)) {
                continue;
            } else if (cell.getCardInCell().getCardType().equals(CardType.SPELL)) {
                continue;
            }
            Trap trap = (Trap) cell.getCardInCell();
            if (trap.getTrapEffect().equals(TrapEffect.TORRENTIAL_TRIBUTE_EFFECT)) {
                return view.yesNoQuestion("do you want to activate your trap and spell?");
            }
        }
        return false;
    }

    private boolean isOpponentTrapToBeActivatedInSummonSituation() { // TODO haven't added it to flip summon , special summon and ritual summon yet
        PlayerBoard board = getOpponentPlayer().getPlayerBoard();
        for (int i = 1; i <= 5; i++) {
            Cell cell = board.getACellOfBoard(Zone.SPELL_ZONE, i);
            if (cell.getCellStatus().equals(CellStatus.EMPTY)) {
                continue;
            } else if (cell.getCardInCell().getCardType().equals(CardType.SPELL)) {
                continue;
            }
            Trap trap = (Trap) cell.getCardInCell();
            switch (trap.getTrapEffect()) {
                //case SOLEMN_WARNING_EFFECT:
                case TORRENTIAL_TRIBUTE_EFFECT:
                    view.showSuccessMessageWithAString(SuccessMessage.SHOW_TURN_WHEN_OPPONENT_WANTS_ACTIVE_TRAP_OR_SPELL_OR_MONSTER, getOpponentPlayer().getNickname());
                    if (view.yesNoQuestion("do you want to activate your trap and spell?")) {
                        return true;
                    } else {
                        view.showSuccessMessageWithAString(SuccessMessage.SHOW_TURN_WHEN_OPPONENT_WANTS_ACTIVE_TRAP_OR_SPELL_OR_MONSTER, getCurrentPlayer().getNickname());
                        return false;
                    }
                case TRAP_HOLE_EFFECT:
                    if (isValidSituationForTrapHoleTrapEffect()) {
                        view.showSuccessMessageWithAString(SuccessMessage.SHOW_TURN_WHEN_OPPONENT_WANTS_ACTIVE_TRAP_OR_SPELL_OR_MONSTER, getOpponentPlayer().getNickname());
                        if (view.yesNoQuestion("do you want to activate your trap and spell?")) {
                            return true;
                        } else {
                            view.showSuccessMessageWithAString(SuccessMessage.SHOW_TURN_WHEN_OPPONENT_WANTS_ACTIVE_TRAP_OR_SPELL_OR_MONSTER, getCurrentPlayer().getNickname());
                            return false;
                        }
                    }
            }
        }
        return false;
    }


    private boolean isValidSituationForTrapHoleTrapEffect() {
        return ((Monster) selectedCell.getCardInCell()).getAttackPower() > 1000;
    }


    private boolean isOpponentTrapInSummonSituationActivated() {
        while (true) {
            int address = view.getAddressForTrapOrSpell();
            if (address == -1) {
                return false;
            } else if (address >= 1 && address <= 5) {
                Cell cell = getOpponentPlayer().getPlayerBoard().getACellOfBoard(Zone.SPELL_ZONE, address);
                if (checkTrapCellToBeActivatedForOpponentInSummonSituation(address, cell))
                    return true;
                else {
                    //TODO check it
                }
            } else
                view.showError(Error.INVALID_NUMBER);
        }
    }

    private boolean checkTrapCellToBeActivatedForOpponentInSummonSituation(int address, Cell cell) {
        if (cell.getCardInCell().getCardType().equals(CardType.SPELL)) {
            view.showError(Error.ACTION_NOT_ALLOWED); //ritght error ?
        } else {
            Trap trap = (Trap) cell.getCardInCell();
            if (isValidActivateTrapEffectInSummonSituationForOpponentToDo(trap.getTrapEffect())) {
                view.showSuccessMessage(SuccessMessage.TRAP_ACTIVATED);
                addCardToGraveYard(Zone.SPELL_ZONE, address, getOpponentPlayer()); //CHECK correctly removed ?
                return true;
            }
        }
        return false;
    }

    private boolean isTrapOfCurrentPlayerInSummonSituationActivated() {
        while (true) {
            int address = view.getAddressForTrapOrSpell();
            if (address == -1) {
                return false;
            } else if (address >= 1 && address <= 5) {
                Cell cell = getCurrentPlayer().getPlayerBoard().getACellOfBoard(Zone.SPELL_ZONE, address);
                if (checkTrapCellToBeActivatedForCurrentPlayerInSummonSituation(address, cell))
                    return true;
                else {
                    //TODO check it
                }
            } else
                view.showError(Error.INVALID_NUMBER);
        }
    }

    private boolean checkTrapCellToBeActivatedForCurrentPlayerInSummonSituation(int address, Cell cell) {
        if (cell.getCardInCell().getCardType().equals(CardType.SPELL)) {
            view.showError(Error.ACTION_NOT_ALLOWED); //ritght error ?
        } else {
            Trap trap = (Trap) cell.getCardInCell();
            if (isValidActivateTrapEffectInSummonSituationForCuurentPlayerToDo(trap.getTrapEffect())) {
                view.showSuccessMessage(SuccessMessage.TRAP_ACTIVATED);
                addCardToGraveYard(Zone.SPELL_ZONE, address, getCurrentPlayer()); //CHECK correctly removed ?
                return true;
            }
        }
        return false;
    }

    private boolean isValidActivateTrapEffectInSummonSituationForOpponentToDo(TrapEffect trapEffect) {
        switch (trapEffect) {
            case TORRENTIAL_TRIBUTE_EFFECT:
                torrentialTributeTrapEffect();
                return true;
            case TRAP_HOLE_EFFECT:
                trapHoleTrapEffect();
                return true;
            default:
                view.showError(Error.PREPARATIONS_IS_NOT_DONE);
                return false;
        }
    }

    private boolean isValidActivateTrapEffectInSummonSituationForCuurentPlayerToDo(TrapEffect trapEffect) {
        switch (trapEffect) {
            case TORRENTIAL_TRIBUTE_EFFECT:
                torrentialTributeTrapEffect();
                return true;
            default:
                view.showError(Error.PREPARATIONS_IS_NOT_DONE);
                return false;
        }
    }

    public void yomiShipMonster(Card attacker) {
        MonsterZone monsterZone = getCurrentPlayer().getPlayerBoard().returnMonsterZone();
        MonsterZone enemyMonsterZone = getOpponentPlayer().getPlayerBoard().returnMonsterZone();
        for (int i = 0; monsterZone.getCellWithAddress(i).getCellStatus() != CellStatus.EMPTY; i++) {
            if (monsterZone.getCellWithAddress(i).getCardInCell() == attacker)
                addCardToGraveYard(Zone.MONSTER_ZONE, i, getCurrentPlayer());
        }
        for (int i = 0; enemyMonsterZone.getCellWithAddress(i).getCellStatus() != CellStatus.EMPTY; i++) {
            if (enemyMonsterZone.getCellWithAddress(i).getCardInCell().getName().equals("Yomi Ship"))
                addCardToGraveYard(Zone.MONSTER_ZONE, i, getOpponentPlayer());
        }
    }

    public void alexandriteDragonMonster() {
        int sumOfLevels = 0;
        Monster monster;
        MonsterZone monsterZone = getCurrentPlayer().getPlayerBoard().returnMonsterZone();
        for (int i = 0; monsterZone.getCellWithAddress(i).getCellStatus() != CellStatus.EMPTY; i++) {
            if (monsterZone.getCellWithAddress(i).getCellStatus() == CellStatus.DEFENSIVE_OCCUPIED ||
                    monsterZone.getCellWithAddress(i).getCellStatus() == CellStatus.OFFENSIVE_OCCUPIED) {
                monster = (Monster) monsterZone.getCellWithAddress(i).getCardInCell();
                sumOfLevels += monster.getLevel();
            }
        }
        monster = (Monster) selectedCell.getCardInCell();
        monster.setAttackPower(monster.getAttackPower() + sumOfLevels * 300);
    }

    private void normalSummon() {
        isSummonOrSetOfMonsterUsed = true;
        summon();
        deselectCard(0);
    }

    private void summon() {
        getCurrentPlayer().getPlayerBoard().addMonsterToBoard((Monster) selectedCell.getCardInCell(), CellStatus.OFFENSIVE_OCCUPIED);
        view.showSuccessMessage(SuccessMessage.SUMMONED_SUCCESSFULLY);
        if (isCurrentPlayerTrapToBeActivatedInSummonSituation()) {
            if (isTrapOfCurrentPlayerInSummonSituationActivated()) {
                view.showSuccessMessageWithAString(SuccessMessage.SHOW_TURN_WHEN_OPPONENT_WANTS_ACTIVE_TRAP_OR_SPELL_OR_MONSTER, getCurrentPlayer().getNickname());
            }
        }
        if (isOpponentTrapToBeActivatedInSummonSituation()) {
            if (isOpponentTrapInSummonSituationActivated()) {
                view.showSuccessMessageWithAString(SuccessMessage.SHOW_TURN_WHEN_OPPONENT_WANTS_ACTIVE_TRAP_OR_SPELL_OR_MONSTER, getCurrentPlayer().getNickname());
            }
        }
        checkNewCardToBeBeUnderEffectOfFieldCard((Monster) selectedCell.getCardInCell());
    }

    private void tributeSummon() {
        if (((Monster) selectedCell.getCardInCell()).getLevel() >= 7) {
            if (didTribute(2, getCurrentPlayer())) {
                normalSummon();
                return;
            }
        } else if (((Monster) selectedCell.getCardInCell()).getLevel() >= 5) {
            if (didTribute(1, getCurrentPlayer())) {
                normalSummon();
                return;
            }
        }
        deselectCard(0);
    }

    private boolean didTribute(int number, DuelPlayer player) {
        //TODO check :
        // view.showSuccessMessage(SuccessMessage.TRIBUTE_SUMMON_ENTER_ADDRESS);
        ArrayList<Integer> address = new ArrayList<>();
        if (!isThereEnoughMonsterToTribute(number, player)) {
            view.showError(Error.NOT_ENOUGH_CARDS_TO_TRIBUTE);
            return false;
        }
        int i = 0;
        while (true) {
            int input = view.getTributeAddress();
            if (input == -1) {
                cancel();
                return false;
            } else if (input > 5) {
                view.showError(Error.INVALID_NUMBER);
            } else if (player.getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, input).getCellStatus().equals(CellStatus.EMPTY)) {
                view.showError(Error.WRONG_MONSTER_ADDRESS);
            } else {
                i++;
                address.add(input);
                if (i == number)
                    break;
            }
        }
        for (Integer integer : address) {
            addCardToGraveYard(Zone.MONSTER_ZONE, integer, player);
        }
        return true;
    }

    private boolean isThereEnoughMonsterToTribute(int number, DuelPlayer player) {
        int counter = 0;
        for (int i = 0; i <= 4; i++) {
            if (!player.getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, i).getCellStatus().equals(CellStatus.EMPTY)) {
                counter++;
            }
            if (i == 4) {
                if (counter >= number) {
                    break;
                } else {
                    view.showError(Error.NOT_ENOUGH_CARDS_TO_TRIBUTE);
                    return false;
                }
            }
        }
        return true;
    }

    private void ritualSummon() {
        List<Card> currentPlayerHand = getCurrentPlayerHand();
        if (!isRitualCardInHand()) {
            view.showError(Error.CAN_NOT_RITUAL_SUMMON);
        } else if (!sumOfSubsequences("cardName")) {
            view.showError(Error.CAN_NOT_RITUAL_SUMMON);
        } else {
            while (true) {
                Monster monster = (Monster) selectedCell.getCardInCell();
                if (Objects.requireNonNull(monster).getMonsterActionType() == MonsterActionType.RITUAL) break;
                else {
                    view.showError(Error.RITUAL_SUMMON_NOW);
                    view.getSummonOrderForRitual();
                }
            }
            while (true) {
                if (areCardsLevelsEnoughToSummonRitualMonster()) break;
                else view.showError(Error.LEVEL_DOES_NOT_MATCH);
            }
            Matcher matcherOfPosition = view.getPositionForSetRitualMonster();
            setRitualMonster(matcherOfPosition);
        }
        deselectCard(0);
    }

    public void setRitualMonster(Matcher matcherOfPosition) {
        MonsterZone monsterZone = getCurrentPlayer().getPlayerBoard().returnMonsterZone();
        if (matcherOfPosition.group().equals("attack")) {
            for (int i = 1; i <= 5; i++) {
                if (monsterZone.getCellWithAddress(i).getCellStatus() == CellStatus.EMPTY) {
                    Card card = Card.getCardByName(selectedCell.getCardInCell().getName());
                    monsterZone.getCellWithAddress(i).setCardInCell(card);
                    monsterZone.getCellWithAddress(i).setCellStatus(CellStatus.OFFENSIVE_OCCUPIED);
                }
            }
            selectedCell.setCellStatus(CellStatus.OFFENSIVE_OCCUPIED);
        } else if (matcherOfPosition.group().equals("defense")) {
            for (int i = 1; i <= 5; i++) {
                if (monsterZone.getCellWithAddress(i).getCellStatus() == CellStatus.EMPTY) {
                    Card card = Card.getCardByName(selectedCell.getCardInCell().getName());
                    monsterZone.getCellWithAddress(i).setCardInCell(card);
                    monsterZone.getCellWithAddress(i).setCellStatus(CellStatus.DEFENSIVE_OCCUPIED);
                }
            }
            selectedCell.setCellStatus(CellStatus.DEFENSIVE_OCCUPIED);
        }
        view.showSuccessMessage(SuccessMessage.SUMMONED_SUCCESSFULLY);
    }

    public boolean areCardsLevelsEnoughToSummonRitualMonster() {
        Matcher addresses = view.getMonstersAddressesToBringRitual();
        String[] split = addresses.pattern().split("\\s+");
        int sum = 0;
        MonsterZone monsterZone = getCurrentPlayer().getPlayerBoard().returnMonsterZone();
        for (String s : split) {
            Monster monster = (Monster) monsterZone.getCellWithAddress(Integer.parseInt(s)).getCardInCell();
            sum += monster.getLevel();
        }
        Monster monster = (Monster) selectedCell.getCardInCell();
        return sum == monster.getLevel();
    }

    private boolean isRitualCardInHand() {
        List<Card> currentPlayerHand = getCurrentPlayerHand();
        for (Card card : currentPlayerHand) {
            if (card.getCardType() == CardType.MONSTER) {
                if (MonsterActionType.getActionTypeByName(card.getName()) == MonsterActionType.RITUAL) return true;
            }
        }
        return false;
    }

    public boolean sumOfSubsequences(String cardName) {
        MonsterZone monsterZone = getCurrentPlayer().getPlayerBoard().returnMonsterZone();
        int sum = 0;
        int[] levels = new int[5];
        for (int i = 0; i < 5; i++) {
            Monster cardNumberI = (Monster) monsterZone.getCellWithAddress(i).getCardInCell();
            levels[i] = cardNumberI.getLevel();
        }
        ArrayList<Integer> sumOfSubset = new ArrayList<>();
        sumOfSubset = subsetSums(levels, 0, levels.length - 1, 0, sumOfSubset);
        Monster ritualCard = (Monster) Card.getCardByName(cardName);
        for (int i = 0; i < sumOfSubset.size(); i++) {
            if (sumOfSubset.get(i) == Objects.requireNonNull(ritualCard).getLevel()) return true;
        }
        return false;
    }

    public ArrayList<Integer> subsetSums(int[] arr, int l, int r, int sum, ArrayList<Integer> sumOfSubsets) {//TODO check --- pashmam ina che  esmaiye : |
        if (l > r) {
            sumOfSubsets.add(sum);
            return sumOfSubsets;
        }
        subsetSums(arr, l + 1, r, sum + arr[l], sumOfSubsets);
        subsetSums(arr, l + 1, r, sum, sumOfSubsets);
        return sumOfSubsets;
    }


    public void setMonster() {
        if (!isValidSelectionForSummonOrSet()) {
            return;
        }
        if ((!selectedCellZone.equals(Zone.HAND))) {
            view.showError(Error.CAN_NOT_SET);
            return;
        } else if (selectedCell.getCardInCell().getCardType().equals(CardType.SPELL) || selectedCell.getCardInCell().getCardType().equals(CardType.TRAP)) {
            view.showError(Error.INVALID_COMMAND);//TODO change this error
            return;
        }
        if (!(currentPhase.equals(Phase.MAIN_PHASE_1) || currentPhase.equals(Phase.MAIN_PHASE_2))) {
            view.showError(Error.ACTION_NOT_ALLOWED);
            return;
        }
        //check special Set
        Monster monster = ((Monster) selectedCell.getCardInCell());
        if (monster.getMonsterEffect().equals(MonsterEffect.GATE_GUARDIAN_EFFECT)) {
            gateGaurdianEffect(CellStatus.DEFENSIVE_HIDDEN);
            return;
        } else if (monster.getMonsterEffect().equals(MonsterEffect.BEAST_KING_BARBAROS_EFFECT)) {
            if (beastKingBarbosEffect(CellStatus.DEFENSIVE_HIDDEN))
                return;
        } else if (monster.getMonsterEffect().equals(MonsterEffect.THE_TRICKY_EFFECT)) {
            if (theTrickyEffect(CellStatus.DEFENSIVE_HIDDEN)) {
                return;
            }
        }
        if (!isValidToNormalSummonOrSet()) {
            return;
        }
        if (monster.getMonsterActionType().equals(MonsterActionType.RITUAL)) {
            //TODO davood
            //setRitualMonster();
            return;
        }
        if (monster.getLevel() >= 4 && monster.getLevel() <= 10) {
            tributeSet();
            return;
        }
        normalSet();
    }

    private void normalSet() {
        isSummonOrSetOfMonsterUsed = true;
        set();
        deselectCard(0);
    }

    private void set() {
        getCurrentPlayer().getPlayerBoard().addMonsterToBoard((Monster) selectedCell.getCardInCell(), CellStatus.DEFENSIVE_HIDDEN);
        checkNewCardToBeBeUnderEffectOfFieldCard((Monster) selectedCell.getCardInCell());
    }

    private void tributeSet() {
        if (((Monster) selectedCell.getCardInCell()).getLevel() >= 7) {
            if (didTribute(2, getCurrentPlayer())) {
                normalSet();
                return;
            }
        } else if (((Monster) selectedCell.getCardInCell()).getLevel() >= 5) {
            if (didTribute(1, getCurrentPlayer())) {
                normalSet();
                return;
            }
        }
        deselectCard(0);
    }

    private boolean isValidSelectionForSummonOrSet() {
        if (selectedCellZone.equals(Zone.NONE)) {
            view.showError(Error.NO_CARD_SELECTED_YET);
            return false;
        } else if (getCurrentPlayer().getPlayerBoard().isMonsterZoneFull()) {
            view.showError(Error.MONSTER_ZONE_IS_FULL);
            return false;
        }
        return true;
    }

    public void changeMonsterPosition(Matcher matcher) {
        if (selectedCell == null) {
            view.showError(Error.NO_CARD_SELECTED_YET);
        } else if (matcher == null) {
            cancel();
            return;
        } else if (!(selectedCellZone == Zone.MONSTER_ZONE)) {
            view.showError(Error.CAN_NOT_CHANGE_POSITION);
        } else if (!(currentPhase == Phase.MAIN_PHASE_1 || currentPhase == Phase.MAIN_PHASE_2)) {
            view.showError(Error.ACTION_CAN_NOT_WORK_IN_THIS_PHASE);
        } else if (!(matcher.group("position").equals("attack") && selectedCell.getCellStatus() == CellStatus.DEFENSIVE_OCCUPIED ||
                matcher.group("position").equals("defense") && selectedCell.getCellStatus() == CellStatus.OFFENSIVE_OCCUPIED)) {
            if (selectedCell.getCellStatus() == CellStatus.DEFENSIVE_HIDDEN) view.showError(Error.DH_POSITION);
            else view.showError(Error.CURRENTLY_IN_POSITION);
        } else if (hasCardChangedPosition()) {
            view.showError(Error.ALREADY_CHANGED_POSITION);
        } else {
            if (matcher.group("position").equals("attack")) selectedCell.setCellStatus(CellStatus.OFFENSIVE_OCCUPIED);
            else if (matcher.group("position").equals("defense"))
                selectedCell.setCellStatus(CellStatus.DEFENSIVE_OCCUPIED);
            view.showSuccessMessage(SuccessMessage.POSITION_CHANGED_SUCCESSFULLY);
            changePositionUsed();
        }
        deselectCard(0);
    }

    public void attackToCard(Matcher matcher) {
        if (!isValidAttack(matcher))
            return;
        int toBeAttackedCardAddress = Integer.parseInt(matcher.group("monsterZoneNumber"));
        CellStatus opponentCellStatus = getOpponentPlayer().getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, toBeAttackedCardAddress).getCellStatus();
        if (opponentCellStatus.equals(CellStatus.DEFENSIVE_HIDDEN)) {
            attackToDHCard(getOpponentPlayer().getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, toBeAttackedCardAddress), toBeAttackedCardAddress);
        } else if (opponentCellStatus.equals(CellStatus.DEFENSIVE_OCCUPIED)) {
            attackToDOCard(getOpponentPlayer().getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, toBeAttackedCardAddress), toBeAttackedCardAddress);
        } else {
            attackToOOCard(getOpponentPlayer().getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, toBeAttackedCardAddress), toBeAttackedCardAddress);
        }
        attackUsed();
        deselectCard(0);
    }

    private void attackToDHCard(Cell opponentCellToBeAttacked, int toBeAttackedCardAddress) { //TODO might have effect
        Monster opponentCard = (Monster) opponentCellToBeAttacked.getCardInCell();
        view.showSuccessMessageWithAString(SuccessMessage.DH_CARD_BECOMES_DO, opponentCard.getName());
        opponentCellToBeAttacked.changeCellStatus(CellStatus.DEFENSIVE_OCCUPIED);
        attackToDOCard(opponentCellToBeAttacked, toBeAttackedCardAddress);
        //TODO am i sure? i changed it to DO and just used attack to DO card
        DOFlipSummon();
        //TODO TO CHECK ... important for trap hole
        if (isTrapToBeActivatedInAttackSituation()) {
            if (isTrapOrSpellInAttackSituationActivated()) {
                view.showSuccessMessageWithAString(SuccessMessage.SHOW_TURN_WHEN_OPPONENT_WANTS_ACTIVE_TRAP_OR_SPELL_OR_MONSTER, getCurrentPlayer().getNickname());
                return;
            }
        }
        Monster playerCard = (Monster) selectedCell.getCardInCell();
        view.showSuccessMessageWithAString(SuccessMessage.SHOW_TURN_WHEN_OPPONENT_WANTS_ACTIVE_TRAP_OR_SPELL_OR_MONSTER, getCurrentPlayer().getNickname());
        int damage = playerCard.getAttackPower() - opponentCard.getAttackPower();
        if (damage > 0) {
            view.showSuccessMessageWithAnInteger(SuccessMessage.OPPONENT_RECEIVE_DAMAGE_AFTER_ATTACK, damage);
            getOpponentPlayer().decreaseLP(damage);
            checkForYomiShipOrExploderDragonEffect(toBeAttackedCardAddress, opponentCard);
            checkForManEaterBugAttacked();
            addCardToGraveYard(Zone.MONSTER_ZONE, toBeAttackedCardAddress, getOpponentPlayer());
        } else if (damage < 0) {
            view.showSuccessMessageWithAnInteger(SuccessMessage.CURRENT_PLAYER_RECEIVE_DAMAGE_AFTER_ATTACK, damage);
            checkForManEaterBugAttacked();
            getCurrentPlayer().decreaseLP(-damage);
            getCurrentPlayer().getPlayerBoard().addCardToGraveYard(opponentCellToBeAttacked.getCardInCell());
            addCardToGraveYard(Zone.MONSTER_ZONE, selectedCellAddress, getCurrentPlayer());
        } else {
            view.showSuccessMessage(SuccessMessage.NO_DAMAGE_TO_ANYONE);
            checkForManEaterBugAttacked();
            addCardToGraveYard(Zone.MONSTER_ZONE, selectedCellAddress, getCurrentPlayer());
            addCardToGraveYard(Zone.MONSTER_ZONE, toBeAttackedCardAddress, getOpponentPlayer());
        }
    }

    private void checkForManEaterBugAttacked() {
        view.showSuccessMessage(SuccessMessage.SHOW_TURN_WHEN_OPPONENT_WANTS_ACTIVE_TRAP_OR_SPELL_OR_MONSTER);
        if (view.yesNoQuestion("do you want to activate man eater bug effect?")) {
            while (true) {
                int address = view.askAddressForManEaterBug();
                if (address == -1) {
                    return;
                } else if (address >= 1 && address <= 5) {
                    if (getCurrentPlayer().getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, address).getCellStatus().equals(CellStatus.EMPTY)) {
                        view.showError(Error.INVALID_SELECTION);
                    } else {
                        addCardToGraveYard(Zone.MONSTER_ZONE, address, getCurrentPlayer());
                    }
                } else view.showError(Error.INVALID_NUMBER);
            }
        }
    }

    private void DOFlipSummon() {
        //TODO
    }

    private void attackToDOCard(Cell opponentCellToBeAttacked, int toBeAttackedCardAddress) { //TODO might have effect
        Monster playerCard = (Monster) selectedCell.getCardInCell();
        Monster opponentCard = (Monster) opponentCellToBeAttacked.getCardInCell();
        int damage = playerCard.getAttackPower() - opponentCard.getDefensePower();
        if (damage > 0) {
            view.showSuccessMessage(SuccessMessage.DEFENSIVE_MONSTER_DESTROYED);
            checkForYomiShipOrExploderDragonEffect(toBeAttackedCardAddress, opponentCard);
        } else if (damage < 0) {
            view.showSuccessMessageWithAnInteger(SuccessMessage.DAMAGE_TO_CURRENT_PLAYER_AFTER_ATTACK_TI_HIGHER_DEFENSIVE_DO_OR_DH_MONSTER, damage);
            getCurrentPlayer().decreaseLP(-damage);
        } else {
            view.showSuccessMessage(SuccessMessage.NO_CARD_DESTROYED);
        }
    }

    private void attackToOOCard(Cell opponentCellToBeAttacked, int toBeAttackedCardAddress) { // might have effect
        Monster playerCard = (Monster) selectedCell.getCardInCell();
        Monster opponentCard = (Monster) opponentCellToBeAttacked.getCardInCell();
        if (isTrapToBeActivatedInAttackSituation()) {
            if (isTrapOrSpellInAttackSituationActivated()) {
                view.showSuccessMessageWithAString(SuccessMessage.SHOW_TURN_WHEN_OPPONENT_WANTS_ACTIVE_TRAP_OR_SPELL_OR_MONSTER, getCurrentPlayer().getNickname());
                return;
            }
        }
        view.showSuccessMessageWithAString(SuccessMessage.SHOW_TURN_WHEN_OPPONENT_WANTS_ACTIVE_TRAP_OR_SPELL_OR_MONSTER, getCurrentPlayer().getNickname());
        int damage = playerCard.getAttackPower() - opponentCard.getAttackPower();
        if (damage > 0) {
            view.showSuccessMessageWithAnInteger(SuccessMessage.OPPONENT_RECEIVE_DAMAGE_AFTER_ATTACK, damage);
            getOpponentPlayer().decreaseLP(damage);
            checkForYomiShipOrExploderDragonEffect(toBeAttackedCardAddress, opponentCard);
        } else if (damage < 0) {
            view.showSuccessMessageWithAnInteger(SuccessMessage.CURRENT_PLAYER_RECEIVE_DAMAGE_AFTER_ATTACK, damage);
            getCurrentPlayer().decreaseLP(-damage);
            getCurrentPlayer().getPlayerBoard().addCardToGraveYard(opponentCellToBeAttacked.getCardInCell());
            addCardToGraveYard(Zone.MONSTER_ZONE, selectedCellAddress, getOpponentPlayer());
        } else {
            view.showSuccessMessage(SuccessMessage.NO_DAMAGE_TO_ANYONE);
            addCardToGraveYard(Zone.MONSTER_ZONE, selectedCellAddress, getOpponentPlayer());
            addCardToGraveYard(Zone.MONSTER_ZONE, toBeAttackedCardAddress, getOpponentPlayer());
        }
    }

    private void checkForYomiShipOrExploderDragonEffect(int toBeAttackedCardAddress, Monster opponentCard) {
        if (opponentCard.getMonsterEffect().equals(MonsterEffect.YOMI_SHIP_EFFECT)) {//yomi ship effect
            addCardToGraveYard(Zone.MONSTER_ZONE, selectedCellAddress, getCurrentPlayer());
        } else if (opponentCard.getMonsterEffect().equals(MonsterEffect.EXPLODER_DRAGON_EFFECT)) {//exploder dragon effect
            addCardToGraveYard(Zone.MONSTER_ZONE, selectedCellAddress, getCurrentPlayer());
            addCardToGraveYard(Zone.MONSTER_ZONE, toBeAttackedCardAddress, getOpponentPlayer());
            return;
        }
        addCardToGraveYard(Zone.MONSTER_ZONE, toBeAttackedCardAddress, getOpponentPlayer());
    }

    private boolean isValidAttack(Matcher matcher) {
        int address = Integer.parseInt(matcher.group("monsterZoneNumber"));
        if (address > 5 || address < 1) {
            view.showError(Error.INVALID_NUMBER);
            return false;
        }
        if (selectedCell == null) {
            view.showError(Error.NO_CARD_SELECTED_YET);
            return false;
        } else if (!selectedCellZone.equals(Zone.MONSTER_ZONE)) {
            view.showError(Error.CAN_NOT_ATTACK);
            return false;
        }
        if (!selectedCell.getCellStatus().equals(CellStatus.OFFENSIVE_OCCUPIED)) { // u sure ?
            view.showError(Error.CAN_NOT_ATTACK);
            return false;
        } else if (!currentPhase.equals(Phase.BATTLE_PHASE)) {
            view.showError(Error.ACTION_NOT_ALLOWED);
            return false;
        }
        if (hasCardUsedItsAttack()) {
            view.showError(Error.ALREADY_ATTACKED);
            return false;
        }
        Cell opponentCell = getOpponentPlayer().getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, address);
        if (opponentCell.getCellStatus().equals(CellStatus.EMPTY)) {
            view.showError(Error.NO_CARD_TO_BE_ATTACKED);
            return false;
        }
        return true;
    }

    private boolean isTrapToBeActivatedInAttackSituation() {
        for (int i = 1; i <= 5; i++) {
            Cell cell = getOpponentPlayer().getPlayerBoard().getACellOfBoard(Zone.SPELL_ZONE, i);
            if (cell.getCellStatus().equals(CellStatus.EMPTY)) {
                continue;
            } else if (cell.getCardInCell().getCardType().equals(CardType.SPELL)) {
                continue;
            }
            Trap trap = (Trap) cell.getCardInCell();
            switch (trap.getTrapEffect()) {
                case MAGIC_CYLINDER_EFFECT:
                case MIRROR_FORCE_EFFECT:
                case NEGATE_ATTACK_EFFECT:
                    view.showSuccessMessageWithAString(SuccessMessage.SHOW_TURN_WHEN_OPPONENT_WANTS_ACTIVE_TRAP_OR_SPELL_OR_MONSTER, getOpponentPlayer().getNickname());
                    if (view.yesNoQuestion("do you want to activate your trap and spell?")) {
                        return true;
                    } else {
                        view.showSuccessMessageWithAString(SuccessMessage.SHOW_TURN_WHEN_OPPONENT_WANTS_ACTIVE_TRAP_OR_SPELL_OR_MONSTER, getCurrentPlayer().getNickname());
                        return false;
                    }
            }
        }
        return false;
    }

    private boolean isTrapOrSpellInAttackSituationActivated() {
        while (true) {
            int address = view.getAddressForTrapOrSpell();
            if (address == -1) {
                return false;
            } else if (address >= 1 && address <= 5) {
                Cell cell = getOpponentPlayer().getPlayerBoard().getACellOfBoard(Zone.SPELL_ZONE, address);
                if (cell.getCardInCell().getCardType().equals(CardType.TRAP)) {
                    Trap trap = (Trap) cell.getCardInCell();
                    if (activateTrapEffectInAttackSituation(trap.getTrapEffect())) {
                        view.showSuccessMessage(SuccessMessage.TRAP_ACTIVATED);
                        addCardToGraveYard(Zone.SPELL_ZONE, address, getOpponentPlayer()); //CHECK correctly removed ?
                    }
                    return true;
                }
            } else
                view.showError(Error.INVALID_NUMBER);
        }
    }


    private boolean activateTrapEffectInAttackSituation(TrapEffect trapEffect) {
        switch (trapEffect) {
            case MAGIC_CYLINDER_EFFECT:
                TrapMagicCylinderEffect();
                return true;
            case MIRROR_FORCE_EFFECT:
                TrapMirrorForceEffect();
                return true;
            case NEGATE_ATTACK_EFFECT:
                TrapNegateAttackEffect();
                return true;
            default:
                view.showError(Error.PREPARATIONS_IS_NOT_DONE);
                return false;
        }
    }

    public void flipSummon() {//TODO might have effect
        if (selectedCell == null) {
            view.showError(Error.NO_CARD_SELECTED_YET);
        } else if (!(selectedCellZone == Zone.MONSTER_ZONE)) {
            view.showError(Error.CAN_NOT_CHANGE_POSITION);
        } else if (!(currentPhase == Phase.MAIN_PHASE_1 || currentPhase == Phase.MAIN_PHASE_2)) {
            view.showError(Error.ACTION_CAN_NOT_WORK_IN_THIS_PHASE);
        } else if (selectedCell.getCellStatus() == CellStatus.DEFENSIVE_HIDDEN) {
            view.showError(Error.FLIP_SUMMON_NOT_ALLOWED);
        } else {
            if (!((Monster) selectedCell.getCardInCell()).getMonsterEffect().equals(MonsterEffect.MAN_EATER_BUG_EFFECT)) {
                selectedCell.setCellStatus(CellStatus.OFFENSIVE_OCCUPIED);
                view.showSuccessMessage(SuccessMessage.FLIP_SUMMON_SUCCESSFUL);
                deselectCard(0);
            } else {
                manEaterBugMonsterEffectAndFlipSummon(getCurrentPlayer(), getOpponentPlayer());
            }
        }
    }

    private void manEaterBugMonsterEffectAndFlipSummon(DuelPlayer current, DuelPlayer opponent) {
        if (view.yesNoQuestion("do you want to activate man eater bug effect?")) {
            while (true) {
                int address = view.askAddressForManEaterBug();
                if (address == -1) {
                    return;
                } else if (address >= 1 && address <= 5) {
                    if (opponent.getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, address).getCellStatus().equals(CellStatus.EMPTY)) {
                        view.showError(Error.INVALID_SELECTION);
                    } else {
                        addCardToGraveYard(Zone.MONSTER_ZONE, address, opponent);
                        deselectCard(0);
                    }
                } else view.showError(Error.INVALID_NUMBER);
            }
        }
        selectedCell.setCellStatus(CellStatus.OFFENSIVE_OCCUPIED);
        view.showSuccessMessage(SuccessMessage.FLIP_SUMMON_SUCCESSFUL);
    }
}