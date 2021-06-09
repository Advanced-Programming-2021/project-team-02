package project.controller.playgame;

import project.model.Deck;
import project.model.card.Card;
import project.model.card.Monster;
import project.model.card.Spell;
import project.model.card.Trap;
import project.model.card.informationofcards.*;
import project.model.game.DuelPlayer;
import project.model.game.PlayerBoard;
import project.model.game.board.*;
import project.view.gameview.GameView;
import project.view.messages.Error;
import project.view.messages.SuccessMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;

import static project.model.card.informationofcards.CardType.SPELL;
import static project.model.card.informationofcards.CardType.TRAP;

public class RoundGameController {
    private static RoundGameController instance = null;
    private GameView view;
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
    private DuelGameController duelGameController;
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

    public void setRoundInfo(DuelPlayer firstPlayer, DuelPlayer secondPlayer, GameView view, DuelGameController duelGameController) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        firstPlayer.setLifePoint(8000);
        secondPlayer.setLifePoint(8000);
        this.view = view;
        this.duelGameController = duelGameController;
        currentPhase = Phase.DRAW_PHASE;
        drawCardFromDeck();
    }

    public void changeTurn() {
        selectedCell = null;
        turn = (turn == 1) ? 2 : 1;
    }

    public void selectCardInHand(Matcher matcher) {
        int address = Integer.parseInt(matcher.group("cardNumber"));
        if (address > getCurrentPlayerHand().size()) {
            Error.showError(Error.INVALID_SELECTION);
            return;
        }
        ArrayList<Card> hand = (ArrayList<Card>) (getCurrentPlayerHand());
        selectedCell = new Cell();
        selectedCellZone = Zone.HAND;
        selectedCell.setCardInCell(hand.get(address - 1));
        selectedCell.setCellStatus(CellStatus.IN_HAND);
        selectedCellAddress = address;
        //getCurrentPlayerHand().remove(selectedCellAddress - 1);//TODO NOT GOOD!
        view.showSuccessMessage(SuccessMessage.CARD_SELECTED);
        opponentSelectedCell = null;
    }

    public void selectCardInMonsterZone(Matcher matcher) {
        int address = Integer.parseInt(matcher.group("monsterZoneNumber"));
        if (address > 5 || address < 1) {
            Error.showError(Error.INVALID_SELECTION);
            return;
        } else if (getCurrentPlayer().getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, address).getCellStatus().equals(CellStatus.EMPTY)) {
            Error.showError(Error.CARD_NOT_FOUND);
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
            Error.showError(Error.INVALID_SELECTION);
            return;
        } else if (getCurrentPlayer().getPlayerBoard().getACellOfBoard(Zone.SPELL_ZONE, address).getCellStatus().equals(CellStatus.EMPTY)) {
            Error.showError(Error.CARD_NOT_FOUND);
            return;
        }
        selectedCellZone = Zone.SPELL_ZONE;
        selectedCell = getCurrentPlayer().getPlayerBoard().getACellOfBoard(selectedCellZone, address);
        selectedCellAddress = address;
        view.showSuccessMessage(SuccessMessage.CARD_SELECTED);
        opponentSelectedCell = null;
    }

    public void deselectCard(int code) {
        if (code == 1) {
            if (selectedCell == null) {
                Error.showError(Error.NO_CARD_SELECTED_YET);
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
            Error.showError(Error.CARD_NOT_FOUND);
            return;
        }
        selectedCell = getCurrentPlayer().getPlayerBoard().getFieldZone().getFieldCell();
        view.showSuccessMessage(SuccessMessage.CARD_SELECTED);
        opponentSelectedCell = null;
    }

    public void selectOpponentFieldCard() {
        if (getOpponentPlayer().getPlayerBoard().isFieldZoneEmpty()) {
            Error.showError(Error.CARD_NOT_FOUND);
            return;
        }
        opponentSelectedCell = getOpponentPlayer().getPlayerBoard().getFieldZone().getFieldCell();
        view.showSuccessMessage(SuccessMessage.CARD_SELECTED);
    }

    public void selectOpponentCardMonsterZone(Matcher matcher) {
        int address = Integer.parseInt(matcher.group("monsterZoneNumber"));
        if (address > 5 || address < 1) {
            Error.showError(Error.INVALID_SELECTION);
            return;
        } else if (getOpponentPlayer().getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, address).getCellStatus().equals(CellStatus.EMPTY)) {
            Error.showError(Error.CARD_NOT_FOUND);
            return;
        }
        //TODO? selectedCellZone = Zone.MONSTER_ZONE;
        opponentSelectedCell = getOpponentPlayer().getPlayerBoard().getACellOfBoard(selectedCellZone, address);
        selectedCell = opponentSelectedCell;
        //TODO? selectedCellAddress = address;
        view.showSuccessMessage(SuccessMessage.CARD_SELECTED);
    }

    public void selectOpponentCardSpellZone(Matcher matcher) {
        int address = Integer.parseInt(matcher.group("spellZoneNumber"));
        if (address > 5 || address < 1) {
            Error.showError(Error.INVALID_SELECTION);
            return;
        } else if (getOpponentPlayer().getPlayerBoard().getACellOfBoard(Zone.SPELL_ZONE, address).getCellStatus().equals(CellStatus.EMPTY)) {
            Error.showError(Error.CARD_NOT_FOUND);
            return;
        }
        //TODO? selectedCellZone = Zone.SPELL_ZONE;
        opponentSelectedCell = getOpponentPlayer().getPlayerBoard().getACellOfBoard(selectedCellZone, address);
        selectedCell = opponentSelectedCell;
        view.showSuccessMessage(SuccessMessage.CARD_SELECTED);
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
            int address = 0;//TODO ! risk here!
            for (int i = 1; i <= 5; i++) {
                Cell cell = getCurrentPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(i);
                if (cell.getCardInCell() == selectedCell.getCardInCell())
                    address = i;
            }
            addCardToGraveYard(Zone.MONSTER_ZONE, address, getCurrentPlayer());
            deselectCard(0);
        } else
            view.showError(Error.PREPARATIONS_IS_NOT_DONE);//TODO NOT SURE
    }

    public List<Card> getCurrentPlayerHand() {
        if (getCurrentPlayer() == firstPlayer) {
            return getFirstPlayerHand();
        } else if (getCurrentPlayer() == secondPlayer) {
            return getSecondPlayerHand();
        }
        return null;
    }


    private void trapMagicCylinderEffect() {
        getCurrentPlayer().decreaseLP(((Monster) selectedCell.getCardInCell()).getAttackPower());
        duelGameController.checkGameResult(getOpponentPlayer(), getCurrentPlayer(), GameResult.NO_LP);
        addCardToGraveYard(Zone.MONSTER_ZONE, selectedCellAddress, getCurrentPlayer());
        deselectCard(0);
    }

    private void trapMirrorForceEffect() {
        for (int i = 1; i <= 5; i++) {
            if (getCurrentPlayer().getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, i).getCellStatus().equals(CellStatus.OFFENSIVE_OCCUPIED))
                addCardToGraveYard(Zone.MONSTER_ZONE, i, getCurrentPlayer());
        }
        view.showSuccessMessage(SuccessMessage.TRAP_ACTIVATED);
        deselectCard(0);
    }

    private void trapNegateAttackEffect() {
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

    public void drawCardFromDeck() {
        DuelPlayer currentPlayer = getCurrentPlayer();
        Card card;
        if (currentPlayer.getPlayDeck().getMainCards().size() != 0) {
            card = currentPlayer.getPlayDeck().getMainCards().get(currentPlayer.getPlayDeck().getMainCards().size() - 1);
            if (turn == 1) {
                addCardToFirstPlayerHand(card);
            } else {
                addCardToSecondPlayerHand(card);
            }
            currentPlayer.getPlayDeck().getMainCards().remove(currentPlayer.getPlayDeck().getMainCards().size() - 1);
            view.showSuccessMessageWithAString(SuccessMessage.CARD_ADDED_TO_THE_HAND, card.getName());
        } else {
            duelGameController.checkGameResult(currentPlayer, getOpponentPlayer(), GameResult.NO_CARDS_TO_DRAW);// no card so this is loser!
        }
    }


    public void directAttack() { // probably no effect ...
        if (selectedCell == null) {
            Error.showError(Error.NO_CARD_SELECTED_YET);
            return;
        }
        if (!selectedCellZone.equals(Zone.MONSTER_ZONE)) {
            Error.showError(Error.CAN_NOT_ATTACK);
            return;
        }
        if (!selectedCell.getCellStatus().equals(CellStatus.OFFENSIVE_OCCUPIED)) { // u sure ?
            Error.showError(Error.CAN_NOT_ATTACK);
            return;
        }
        if (!getCurrentPhase().equals(Phase.BATTLE_PHASE)) {
            Error.showError(Error.ACTION_CAN_NOT_WORK_IN_THIS_PHASE);
            return;
        }
        if (!getOpponentPlayer().getPlayerBoard().isMonsterZoneEmpty()) {
            Error.showError(Error.CANT_DIRECT_ATTACK);
            return;
        }
        if (hasCardUsedItsAttack()) {
            Error.showError(Error.ALREADY_ATTACKED);
            return;
        }
        Monster monster = (Monster) selectedCell.getCardInCell();
        getOpponentPlayer().decreaseLP(monster.getAttackPower());
        duelGameController.checkGameResult(getCurrentPlayer(), getOpponentPlayer(), GameResult.NO_LP);
        view.showSuccessMessageWithAnInteger(SuccessMessage.OPPONENT_RECEIVE_DAMAGE_AFTER_DIRECT_ATTACK, monster.getAttackPower());
    }

    public void nextPhase() {
        if (currentPhase.equals(Phase.DRAW_PHASE)) {
            currentPhase = Phase.STAND_BY_PHASE;
            System.out.println(currentPhase);//TODO REMOVE
        } else if (currentPhase.equals(Phase.STAND_BY_PHASE)) {
            currentPhase = Phase.MAIN_PHASE_1;
            System.out.println(currentPhase);//TODO REMOVE
        } else if (currentPhase == Phase.MAIN_PHASE_1) {
            currentPhase = Phase.BATTLE_PHASE;
            System.out.println(currentPhase);//TODO REMOVE
        } else if (currentPhase == Phase.BATTLE_PHASE) {
            currentPhase = Phase.MAIN_PHASE_2;
            System.out.println(currentPhase);//TODO REMOVE
        } else if (currentPhase == Phase.MAIN_PHASE_2) {
            currentPhase = Phase.DRAW_PHASE;
            view.showSuccessMessageWithAString(SuccessMessage.PLAYERS_TURN, getOpponentPlayer().getNickname());
            isSummonOrSetOfMonsterUsed = false;
            //if (selectedCellZone.equals(Zone.HAND)) {
            //    getCurrentPlayerHand().add(getCurrentPlayerHand().size() - selectedCellAddress + 1, selectedCell.getCardInCell());
            //}
            selectedCell = null;
            selectedCellZone = Zone.NONE;
            opponentSelectedCell = null;
            usedCellsToAttackNumbers.clear();
            changedPositionCards.clear();
            changeTurn();
            drawCardFromDeck();
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

    public DuelPlayer getOpponentPlayer() {
        if (turn == 1)
            return secondPlayer;
        return firstPlayer;
    }

    public void cancel() {
        selectedCell = null;
        selectedCellZone = Zone.NONE;
    }

    public void monsterRebornSpell() {
        GraveYard graveYard;
        int choice = view.twoChoiceQuestions("to summon card from, which you choose :", "your graveyard", "opponent graveyard");
        if (choice == -1) {
            cancel();
            return;
        }
        if (choice == 2) {
            view.showOpponentGraveYard();
            graveYard = getOpponentPlayer().getPlayerBoard().returnGraveYard();
        } else {
            view.showCurrentGraveYard();
            graveYard = getCurrentPlayer().getPlayerBoard().returnGraveYard();
        }
        while (true) {
            Card card;
            int address = view.chooseCardInGraveYard();
            if (address == -1) {
                cancel();
                return;
            } else if (address - 1 > graveYard.getGraveYardCards().size()) {
                view.showError(Error.INVALID_NUMBER);
            } else if ((card = graveYard.getGraveYardCards().get(address - 1)).getCardType().equals(CardType.MONSTER)) {
                int summonChoice = view.twoChoiceQuestions("choose what to do:", "summon", "set");
                if (summonChoice == -1) {
                    cancel();
                    return;
                } else if (summonChoice == 1) {
                    specialSummon(card, CellStatus.OFFENSIVE_OCCUPIED);
                    deselectCard(0);
                    addCardToGraveYard(Zone.SPELL_ZONE, selectedCellAddress, getCurrentPlayer());
                    return;
                } else {
                    specialSummon(card, CellStatus.DEFENSIVE_HIDDEN);
                    deselectCard(0);
                    addCardToGraveYard(Zone.SPELL_ZONE, selectedCellAddress, getCurrentPlayer());
                    return;
                }
            } else view.showError(Error.INVALID_SELECTION);
        }

    }

    private void terraFormingSpell() {
        Deck deck = getCurrentPlayer().getPlayDeck();
        ArrayList<Card> cards = deck.getMainCards();
        boolean flagOfExistence = false;
        for (Card card : cards) {
            if (card.getCardType() == CardType.SPELL) {
                if (((Spell) card).getSpellType() == SpellType.FIELD) {
                    flagOfExistence = true;
                }
            }
        }
        if (!flagOfExistence) {
            view.showError(Error.PREPARATIONS_IS_NOT_DONE);//No Field Card
            return;
        }
        while (true) {
            int address = view.chooseCardInDeck(deck);
            if (address == -1) {
                return;
            } else if (address - 1 > deck.getMainCards().size() || address - 1 < 0 || deck.getMainCards().get(address - 1) == null) {
                view.showError(Error.INVALID_NUMBER);
            } else if (!deck.getMainCards().get(address - 1).getCardType().equals(CardType.SPELL)) {
                view.showError(Error.INVALID_SELECTION);
            } else {
                if (((Spell) deck.getMainCards().get(address - 1)).getSpellType().equals(SpellType.FIELD)) {
                    addCardToFirstPlayerHand(((Spell) deck.getMainCards().get(address - 1)));
                    deck.getMainCards().remove(address - 1);
                    break;
                } else view.showError(Error.INVALID_SELECTION);
            }
        }
        deck.shuffleDeck();
        addCardToGraveYard(Zone.SPELL_ZONE, selectedCellAddress, getCurrentPlayer());
        System.out.println("TerraForming effect"); //TODO somewhere else
    }


    private void potOfGreedSpell() {
        List<Card> deckCards = getCurrentPlayer().getPlayDeck().getMainCards();
        int size = deckCards.size();
        if (size < 2) {
            view.showError(Error.PREPARATIONS_IS_NOT_DONE);
            return;
        }
        getCurrentPlayerHand().add(deckCards.get(size - 1));
        deckCards.remove(size - 1);
        size = deckCards.size();
        getCurrentPlayerHand().add(deckCards.get(size - 1));
        deckCards.remove(size - 1);
        addCardToGraveYard(Zone.SPELL_ZONE, selectedCellAddress, getCurrentPlayer());
        System.out.println("pot of greed effect");//TODO somewhere else
        deselectCard(0);
    }

    private void raigekiSpell() {
        MonsterZone monsterZone = getOpponentPlayer().getPlayerBoard().returnMonsterZone();
        int i = 1;
        int counter = 1;
        while (counter <= 5) {
            if (monsterZone.getCellWithAddress(i).getCellStatus() != CellStatus.EMPTY)
                addCardToGraveYard(Zone.MONSTER_ZONE, i, getOpponentPlayer());
            i++;
            counter++;
        }
        addCardToGraveYard(Zone.SPELL_ZONE, selectedCellAddress, getCurrentPlayer());
        System.out.println("raigekiSpell effect"); //TODO Somewhere else
        deselectCard(0);
    }

    private void harpiesFeatherDusterSpell() {
        int i = 1;
        while (getOpponentPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(i).getCellStatus() != CellStatus.EMPTY) {
            addCardToGraveYard(Zone.SPELL_ZONE, i, getOpponentPlayer());
            i++;
        }
        if (isFieldActivated == 2 && turn == 1) {
            reversePreviousFieldZoneSpellEffectAndRemoveIt();
        } else if (isFieldActivated == 1 && turn == 2) {
            reversePreviousFieldZoneSpellEffectAndRemoveIt();
        }
        addCardToGraveYard(Zone.SPELL_ZONE, selectedCellAddress, getCurrentPlayer());
        System.out.println("harpiesFeatherDuster effect");
        deselectCard(0);
    }

    public void darkHoleSpell() {
        int i = 1;
        while (getOpponentPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(i).getCellStatus() != CellStatus.EMPTY || i >= 5) {
            addCardToGraveYard(Zone.MONSTER_ZONE, i, getOpponentPlayer());
            i++;
        }
        i = 1;
        while (getCurrentPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(i).getCellStatus() != CellStatus.EMPTY || i >= 5) {
            addCardToGraveYard(Zone.MONSTER_ZONE, i, getCurrentPlayer());
            i++;
        }
        addCardToGraveYard(Zone.SPELL_ZONE, selectedCellAddress, getCurrentPlayer());
        System.out.println("dark hole effect");
        deselectCard(0);
    }

    private void swordOfDarkDestructionSpell() {
        Card spellCard = selectedCell.getCardInCell();
        Monster monsterCard;
        int address;
        while (true) {
            address = view.swordOfDarkDestruction();
            if (!isValidAddress(address)) {
                return;
            }
            Cell cell = getCurrentPlayer().getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, address);
            if (cell.getCellStatus().equals(CellStatus.EMPTY)) {
                view.showError(Error.INVALID_SELECTION);
                continue;
            } else {
                monsterCard = (Monster) cell.getCardInCell();
                if (monsterCard.getMonsterType().equals(MonsterType.FIEND) || monsterCard.getMonsterType().equals(MonsterType.SPELLCASTER)) {
                    view.showError(Error.TYPE_FIEND_OT_SPELL_CASTER);
                } else break;
            }
        }
        monsterCard.changeAttackPower(400);
        monsterCard.changeDefensePower(-200);
        if (turn == 1) {
            firstPlayerHashmapForEquipSpells.put(spellCard, monsterCard);
        } else secondPlayerHashmapForEquipSpells.put(spellCard, monsterCard);
    }

    public void removeSwordOfDarkDestruction(Card card) {//TODO func zadi estefade nakardi
        Monster monster = (Monster) card;
        monster.changeAttackPower(-400);
        monster.changeDefensePower(+200);
    }

    public void blackPendantSpell() {
        Card spellCard = selectedCell.getCardInCell();
        Monster monsterCard;
        int address;
        while (true) {
            address = view.blackPendant();
            if (!isValidAddress(address)) return;
            Cell cell = getCurrentPlayer().getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, address);
            if (cell.getCellStatus().equals(CellStatus.EMPTY)) {
                view.showError(Error.INVALID_SELECTION);
            } else {
                monsterCard = (Monster) cell.getCardInCell();
                break;
            }
        }
        monsterCard.changeAttackPower(400);
        monsterCard.changeDefensePower(-200);
        if (turn == 1) {
            firstPlayerHashmapForEquipSpells.put(spellCard, monsterCard);
        } else secondPlayerHashmapForEquipSpells.put(spellCard, monsterCard);
    }

    private boolean isValidAddress(int address) {
        if (address == -1) {
            cancel();
            return false;
        }
        if (address > 5 || address < 1) {
            view.showError(Error.INVALID_NUMBER);
            return false;
        }
        return true;
    }

    public void removeBlackPendant(Card card) {//TODO func zadi estefade nakardi
        Monster monster = (Monster) card;
        monster.setAttackPower(monster.getAttackPower() - 500);
    }


    public void surrender() {
        if (getCurrentPlayer() == firstPlayer) {
            duelGameController.checkGameResult(secondPlayer, firstPlayer, GameResult.SURRENDER);
        } else duelGameController.checkGameResult(firstPlayer, secondPlayer, GameResult.SURRENDER);
    }

    public void specialSummon(Card card, CellStatus cellStatus) {
        MonsterZone monsterZone = getCurrentPlayer().getPlayerBoard().returnMonsterZone();
        monsterZone.addCard((Monster) card, cellStatus);
        view.showBoard();
        //TODO not sure!!!
        if (isCurrentPlayerTrapToBeActivatedInSummonSituation()) {
            if (isTrapOfCurrentPlayerInSummonSituationActivated()) {
                return;
            }
        }
        if (isOpponentTrapToBeActivatedInSummonSituation()) {
            view.showSuccessMessageWithAString(SuccessMessage.SHOW_TURN_WHEN_OPPONENT_WANTS_ACTIVE_TRAP_OR_SPELL_OR_MONSTER, getOpponentPlayer().getNickname());
            if (isOpponentTrapInSummonSituationActivated()) {
                return;
            }
        }
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
                            if (spellZone.getCellWithAddress(i).getCardInCell().getName().equals(card.getName())) {
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
                            if (spellZone.getCellWithAddress(i).getCardInCell().getName().equals(card.getName())) {
                                addCardToGraveYard(Zone.SPELL_ZONE, i, secondPlayer);
                            }
                            i++;
                        }
                    }
                }
            }
        } else if (fromZone.equals(Zone.FIELD_ZONE)) {
            player.getPlayerBoard().removeFieldSpell();
        } else if (fromZone.equals(Zone.SPELL_ZONE)) {
            Cell cell = player.getPlayerBoard().getACellOfBoard(fromZone, address);
            //TODO anything to check?
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


    private Card getCardInHand(int address) {
        Card card;
        if (address > getCurrentPlayerHand().size())
            return null;
        if ((card = getCurrentPlayerHand().get(address)) == null) {
            return null;
        } else return card;
    }


    //MONSTER RELATED CODES :
    public void summonMonster() {
        if (selectedCell == null && opponentSelectedCell != null) {
            Error.showError(Error.ONLY_CAN_SHOW_OPPONENT_CARD);
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
                gateGuardianEffect(CellStatus.OFFENSIVE_OCCUPIED);
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
            Error.showError(Error.CAN_NOT_SUMMON);
            return false;
        }
        if (!currentPhase.equals(Phase.MAIN_PHASE_1) && !currentPhase.equals(Phase.MAIN_PHASE_2)) {
            Error.showError(Error.ACTION_NOT_ALLOWED);
            return false;
        }
        return true;
    }

    private boolean isValidToNormalSummonOrSet() {
        if (isSummonOrSetOfMonsterUsed) {
            Error.showError(Error.ALREADY_SUMMONED_OR_SET);
            return false;
        }
        return true;
    }

    private void gateGuardianEffect(CellStatus status) {
        if (view.yesNoQuestion("do you want to tribute for GateGuardian Special Summon?")) {
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
                int address = view.chooseCardInHand("Enter address(number of it in your hand) of card to be set");
                if (address == -1) {
                    return false;
                } else if (getCardInHand(address) == null) {
                    Error.showError(Error.INVALID_SELECTION);
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
            if (trap.getTrapEffect() == null)//TODO pshmam inja null khordim no idea
                continue;//TODO pshmam inja null khordim no idea
            switch (trap.getTrapEffect()) {
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
            } else
                Error.showError(Error.INVALID_NUMBER);
        }
    }

    private boolean checkTrapCellToBeActivatedForOpponentInSummonSituation(int address, Cell cell) {
        if (cell.getCardInCell().getCardType().equals(CardType.SPELL)) {
            Error.showError(Error.ACTION_NOT_ALLOWED); //right error ?
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

            } else
                Error.showError(Error.INVALID_NUMBER);
        }
    }

    private boolean checkTrapCellToBeActivatedForCurrentPlayerInSummonSituation(int address, Cell cell) {
        if (cell.getCardInCell().getCardType().equals(CardType.SPELL)) {
            Error.showError(Error.ACTION_NOT_ALLOWED); //right error ?
        } else {
            Trap trap = (Trap) cell.getCardInCell();
            if (isValidActivateTrapEffectInSummonSituationForCurentPlayerToDo(trap.getTrapEffect())) {
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
                Error.showError(Error.PREPARATIONS_IS_NOT_DONE);
                return false;
        }
    }

    private boolean isValidActivateTrapEffectInSummonSituationForCurentPlayerToDo(TrapEffect trapEffect) {
        if (trapEffect == TrapEffect.TORRENTIAL_TRIBUTE_EFFECT) {
            torrentialTributeTrapEffect();
            return true;
        }
        Error.showError(Error.PREPARATIONS_IS_NOT_DONE);
        return false;
    }

    public void yomiShipMonster(Card attacker) {
        MonsterZone monsterZone = getCurrentPlayer().getPlayerBoard().returnMonsterZone();
        MonsterZone enemyMonsterZone = getOpponentPlayer().getPlayerBoard().returnMonsterZone();
        for (int i = 1; monsterZone.getCellWithAddress(i).getCellStatus() != CellStatus.EMPTY; i++) {
            if (monsterZone.getCellWithAddress(i).getCardInCell() == attacker)
                addCardToGraveYard(Zone.MONSTER_ZONE, i, getCurrentPlayer());
        }
        for (int i = 1; enemyMonsterZone.getCellWithAddress(i).getCellStatus() != CellStatus.EMPTY; i++) {
            if (enemyMonsterZone.getCellWithAddress(i).getCardInCell().getName().equals("Yomi Ship"))
                addCardToGraveYard(Zone.MONSTER_ZONE, i, getOpponentPlayer());
        }
    }

    private void normalSummon() {
        isSummonOrSetOfMonsterUsed = true;
        summon();
        deselectCard(0);
    }

    private void summon() {
        getCurrentPlayer().getPlayerBoard().addMonsterToBoard((Monster) selectedCell.getCardInCell(), CellStatus.OFFENSIVE_OCCUPIED);
        view.showSuccessMessage(SuccessMessage.SUMMONED_SUCCESSFULLY);
        getCurrentPlayerHand().remove(selectedCellAddress - 1);
        view.showBoard();
        if (isCurrentPlayerTrapToBeActivatedInSummonSituation()) {
            if (isTrapOfCurrentPlayerInSummonSituationActivated()) {
                return;
            }
        }
        if (isOpponentTrapToBeActivatedInSummonSituation()) {
            view.showSuccessMessageWithAString(SuccessMessage.SHOW_TURN_WHEN_OPPONENT_WANTS_ACTIVE_TRAP_OR_SPELL_OR_MONSTER, getOpponentPlayer().getNickname());
            if (isOpponentTrapInSummonSituationActivated()) {
                return;
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
    }

    private boolean didTribute(int number, DuelPlayer player) {
        //TODO check :
        // project.view.showSuccessMessage(SuccessMessage.TRIBUTE_SUMMON_ENTER_ADDRESS);
        ArrayList<Integer> address = new ArrayList<>();
        if (!isThereEnoughMonsterToTribute(number, player)) {
            Error.showError(Error.NOT_ENOUGH_CARDS_TO_TRIBUTE);
            return false;
        }
        int i = 0;
        while (true) {
            int input = view.getTributeAddress();
            if (input == -1) {
                cancel();
                return false;
            } else if (input > 5) {
                Error.showError(Error.INVALID_NUMBER);
            } else if (player.getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, input).getCellStatus().equals(CellStatus.EMPTY)) {
                Error.showError(Error.WRONG_MONSTER_ADDRESS);
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
        for (int i = 1; i <= 5; i++) {
            if (!player.getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, i).getCellStatus().equals(CellStatus.EMPTY)) {
                counter++;
            }
            if (i == 5) {
                if (counter >= number) {
                    break;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    private void ritualSummon() {
        List<Card> currentPlayerHand = getCurrentPlayerHand();
        String cardName;
        Monster monster;
        while (true) {
            cardName = view.ritualCardName();
            monster = (Monster) Card.getCardByName(cardName);
            if (!isRitualCardInHand()) {
                Error.showError(Error.CAN_NOT_RITUAL_SUMMON);
            } else if (Card.getCardByName(cardName) == null) {
                view.showError(Error.WRONG_CARD_NAME);
            } else if (Objects.requireNonNull(monster).getMonsterActionType() != MonsterActionType.RITUAL) {
                view.showError(Error.WRONG_CARD_NAME);
                view.showError(Error.RITUAL_SUMMON_NOW);
            } else if (!sumOfSubsequences(cardName)) {
                Error.showError(Error.CAN_NOT_RITUAL_SUMMON);
            } else {
                if (view.getSummonOrderForRitual()) {
                    while (true) {
                        if (areCardsLevelsEnoughToSummonRitualMonster()) break;
                        else Error.showError(Error.LEVEL_DOES_NOT_MATCH);
                    }
                }
            }
        }
    }

    public void setRitualMonster() {
        String monsterPosition = view.getPositionForSetRitualMonster();
        MonsterZone monsterZone = getCurrentPlayer().getPlayerBoard().returnMonsterZone();
        if (monsterPosition.equals("attack")) {
            for (int i = 1; i <= 5; i++) {
                if (monsterZone.getCellWithAddress(i).getCellStatus() == CellStatus.EMPTY) {
                    Card card = Card.getCardByName(selectedCell.getCardInCell().getName());
                    monsterZone.getCellWithAddress(i).setCardInCell(card);
                    monsterZone.getCellWithAddress(i).setCellStatus(CellStatus.OFFENSIVE_OCCUPIED);
                }
            }
            selectedCell.setCellStatus(CellStatus.OFFENSIVE_OCCUPIED);
        } else if (monsterPosition.equals("defense")) {
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
        String[] addresses = view.getMonstersAddressesToBringRitual();
        int sum = 0;
        MonsterZone monsterZone = getCurrentPlayer().getPlayerBoard().returnMonsterZone();
        for (String s : addresses) {
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
        for (int i = 1; i <= 5; i++) {
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
            Error.showError(Error.CAN_NOT_SET);
            return;
        } else if (selectedCell.getCardInCell().getCardType().equals(CardType.SPELL) || selectedCell.getCardInCell().getCardType().equals(TRAP)) {
            Error.showError(Error.INVALID_COMMAND);//TODO change this error
            return;
        }
        if (!(currentPhase.equals(Phase.MAIN_PHASE_1) || currentPhase.equals(Phase.MAIN_PHASE_2))) {
            Error.showError(Error.ACTION_NOT_ALLOWED);
            return;
        }
        //check special Set
        Monster monster = ((Monster) selectedCell.getCardInCell());
        if (monster.getMonsterEffect().equals(MonsterEffect.GATE_GUARDIAN_EFFECT)) {
            gateGuardianEffect(CellStatus.DEFENSIVE_HIDDEN);
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
        if (monster.getLevel() >= 5 && monster.getLevel() <= 10) {
            tributeSet();
            return;
        }
        normalSet();
    }

    private boolean isTorrentialTributeTrapToActivateInSet(DuelPlayer player) {
        for (int i = 1; i <= 5; i++) {
            Cell cell = player.getPlayerBoard().getACellOfBoard(Zone.SPELL_ZONE, i);
            if (cell.getCellStatus().equals(CellStatus.EMPTY))
                continue;
            if (cell.getCardInCell().getCardType().equals(CardType.SPELL))
                continue;
            if (cell.getCardInCell().getCardType().equals(TRAP)) {
                if (((Trap) cell.getCardInCell()).getTrapEffect().equals(TrapEffect.TORRENTIAL_TRIBUTE_EFFECT)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void normalSet() {
        isSummonOrSetOfMonsterUsed = true;
        set();
        view.showSuccessMessage(SuccessMessage.SET_SUCCESSFULLY);
        deselectCard(0);
    }

    private void set() {
        getCurrentPlayer().getPlayerBoard().addMonsterToBoard((Monster) selectedCell.getCardInCell(), CellStatus.DEFENSIVE_HIDDEN);
        getCurrentPlayerHand().remove(selectedCellAddress - 1);
        view.showBoard();
        //TRAPS :
        if (isTorrentialTributeTrapToActivateInSet(getCurrentPlayer())) {
            if (isTrapOfCurrentPlayerInSummonSituationActivated()) {
                if (isTrapOfCurrentPlayerInSummonSituationActivated()) {
                    deselectCard(0);
                    return;
                }
            }
        } else if (isTorrentialTributeTrapToActivateInSet(getOpponentPlayer())) {
            view.showSuccessMessageWithAString(SuccessMessage.SHOW_TURN_WHEN_OPPONENT_WANTS_ACTIVE_TRAP_OR_SPELL_OR_MONSTER, getOpponentPlayer().getNickname());
            if (view.yesNoQuestion("do you want to activate trap")) {
                while (true) {
                    Trap trap;
                    int address = view.getAddressForTrapOrSpell();
                    if (address == -1) {
                        cancel();
                        view.showSuccessMessageWithAString(SuccessMessage.SHOW_TURN_WHEN_OPPONENT_WANTS_ACTIVE_TRAP_OR_SPELL_OR_MONSTER, getCurrentPlayer().getNickname());
                        return;
                    } else if (address > 5 || address < 1) {
                        view.showError(Error.INVALID_NUMBER);
                    } else if (getOpponentPlayer().getPlayerBoard().getACellOfBoard(Zone.SPELL_ZONE, address).getCardInCell().getCardType().equals(TRAP)) {
                        trap = (Trap) getOpponentPlayer().getPlayerBoard().getACellOfBoard(Zone.SPELL_ZONE, address).getCardInCell();
                        if (trap.getTrapEffect().equals(TrapEffect.TORRENTIAL_TRIBUTE_EFFECT)) {
                            torrentialTributeTrapEffect();
                            addCardToGraveYard(Zone.SPELL_ZONE, address, getOpponentPlayer());
                            return;
                        }
                    } else view.showError(Error.INVALID_SELECTION);
                }
            }
        }
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

    }

    private boolean isValidSelectionForSummonOrSet() {
        if (selectedCellZone.equals(Zone.NONE)) {
            Error.showError(Error.NO_CARD_SELECTED_YET);
            return false;
        } else if (getCurrentPlayer().getPlayerBoard().isMonsterZoneFull()) {
            Error.showError(Error.MONSTER_ZONE_IS_FULL);
            return false;
        }
        return true;
    }

    public void changeMonsterPosition(Matcher matcher) {
        if (selectedCell == null) {
            Error.showError(Error.NO_CARD_SELECTED_YET);
        } else if (matcher == null) {
            cancel();
            return;
        } else if (!(selectedCellZone == Zone.MONSTER_ZONE)) {
            Error.showError(Error.CAN_NOT_CHANGE_POSITION);
        } else if (!(currentPhase == Phase.MAIN_PHASE_1 || currentPhase == Phase.MAIN_PHASE_2)) {
            Error.showError(Error.ACTION_CAN_NOT_WORK_IN_THIS_PHASE);
        } else if (!(matcher.group("position").equals("attack") && selectedCell.getCellStatus() == CellStatus.DEFENSIVE_OCCUPIED ||
                matcher.group("position").equals("defense") && selectedCell.getCellStatus() == CellStatus.OFFENSIVE_OCCUPIED)) {
            if (selectedCell.getCellStatus() == CellStatus.DEFENSIVE_HIDDEN) Error.showError(Error.DH_POSITION);
            else Error.showError(Error.CURRENTLY_IN_POSITION);
        } else if (hasCardChangedPosition()) {
            Error.showError(Error.ALREADY_CHANGED_POSITION);
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
        //DOFlipSummon();
        //TODO TO CHECK ... important for trap hole
        if (isTrapToBeActivatedInAttackSituation()) {
            if (isTrapOrSpellInAttackSituationActivated()) {
                view.showSuccessMessageWithAString(SuccessMessage.SHOW_TURN_WHEN_OPPONENT_WANTS_ACTIVE_TRAP_OR_SPELL_OR_MONSTER, getCurrentPlayer().getNickname());
                return;
            }
        }
        Monster playerCard = (Monster) selectedCell.getCardInCell();
        int damage = playerCard.getAttackPower() - opponentCard.getAttackPower();
        if (damage > 0) {
            if (!checkForYomiShipOrExploderDragonEffect(toBeAttackedCardAddress, opponentCard)) { // exploder stops damage
                checkForManEaterBugAttacked();
                getOpponentPlayer().decreaseLP(damage);
                view.showSuccessMessageWithAnInteger(SuccessMessage.OPPONENT_RECEIVE_DAMAGE_AFTER_ATTACK, damage);
                duelGameController.checkGameResult(getCurrentPlayer(), getOpponentPlayer(), GameResult.NO_LP);
                addCardToGraveYard(Zone.MONSTER_ZONE, toBeAttackedCardAddress, getOpponentPlayer());
            } else {
                System.out.println("exploder effect");//TODO somwhere else

            }
        } else if (damage < 0) {
            checkForManEaterBugAttacked();
            view.showSuccessMessageWithAnInteger(SuccessMessage.CURRENT_PLAYER_RECEIVE_DAMAGE_AFTER_ATTACK, damage);
            getCurrentPlayer().decreaseLP(-damage);
            duelGameController.checkGameResult(getOpponentPlayer(), getCurrentPlayer(), GameResult.NO_LP);
            getCurrentPlayer().getPlayerBoard().addCardToGraveYard(opponentCellToBeAttacked.getCardInCell());
            addCardToGraveYard(Zone.MONSTER_ZONE, selectedCellAddress, getCurrentPlayer());
        } else {
            checkForManEaterBugAttacked();
            view.showSuccessMessage(SuccessMessage.NO_DAMAGE_TO_ANYONE);
            addCardToGraveYard(Zone.MONSTER_ZONE, selectedCellAddress, getCurrentPlayer());
            addCardToGraveYard(Zone.MONSTER_ZONE, toBeAttackedCardAddress, getOpponentPlayer());
        }
    }

    private void checkForManEaterBugAttacked() {
        view.showSuccessMessageWithAString(SuccessMessage.SHOW_TURN_WHEN_OPPONENT_WANTS_ACTIVE_TRAP_OR_SPELL_OR_MONSTER, getCurrentPlayer().getNickname());
        if (view.yesNoQuestion("do you want to activate man eater bug effect?(yes or no!)")) {
            while (true) {
                int address = view.askAddressForManEaterBug();
                if (address == -1) {
                    return;
                } else if (address >= 1 && address <= 5) {
                    if (getCurrentPlayer().getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, address).getCellStatus().equals(CellStatus.EMPTY)) {
                        Error.showError(Error.INVALID_SELECTION);
                    } else {
                        addCardToGraveYard(Zone.MONSTER_ZONE, address, getCurrentPlayer());
                        System.out.println("Man eater bug effect : destroy " + address + "card of you!");//TODO somewhere else
                        break;
                    }
                } else Error.showError(Error.INVALID_NUMBER);
            }
        }
    }

    /*private void DOFlipSummon() {
        //TODO is it needed ?
    }*/

    private void attackToDOCard(Cell opponentCellToBeAttacked, int toBeAttackedCardAddress) { //TODO might have effect
        Monster playerCard = (Monster) selectedCell.getCardInCell();
        Monster opponentCard = (Monster) opponentCellToBeAttacked.getCardInCell();
        int damage = playerCard.getAttackPower() - opponentCard.getDefensePower();
        if (isTrapToBeActivatedInAttackSituation()) {
            if (isTrapOrSpellInAttackSituationActivated()) {
                view.showSuccessMessageWithAString(SuccessMessage.SHOW_TURN_WHEN_OPPONENT_WANTS_ACTIVE_TRAP_OR_SPELL_OR_MONSTER, getCurrentPlayer().getNickname());
                return;
            }
        }
        if (damage > 0) {
            view.showSuccessMessage(SuccessMessage.DEFENSIVE_MONSTER_DESTROYED);
            checkForYomiShipOrExploderDragonEffect(toBeAttackedCardAddress, opponentCard);
        } else if (damage < 0) {
            view.showSuccessMessageWithAnInteger(SuccessMessage.DAMAGE_TO_CURRENT_PLAYER_AFTER_ATTACK_TI_HIGHER_DEFENSIVE_DO_OR_DH_MONSTER, damage);
            getCurrentPlayer().decreaseLP(-damage);
            duelGameController.checkGameResult(getOpponentPlayer(), getCurrentPlayer(), GameResult.NO_LP);
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
            view.showSuccessMessageWithAString(SuccessMessage.SHOW_TURN_WHEN_OPPONENT_WANTS_ACTIVE_TRAP_OR_SPELL_OR_MONSTER, getCurrentPlayer().getNickname());
        }
        int damage = playerCard.getAttackPower() - opponentCard.getAttackPower();
        if (damage > 0) {
            if (!checkForYomiShipOrExploderDragonEffect(toBeAttackedCardAddress, opponentCard)) {
                view.showSuccessMessageWithAnInteger(SuccessMessage.OPPONENT_RECEIVE_DAMAGE_AFTER_ATTACK, damage);
                addCardToGraveYard(Zone.MONSTER_ZONE, toBeAttackedCardAddress, getOpponentPlayer());
                getOpponentPlayer().decreaseLP(damage);
                duelGameController.checkGameResult(getCurrentPlayer(), getOpponentPlayer(), GameResult.NO_LP);
            } else {
                System.out.println("exploder effect");//TODO somwhere else
            }
        } else if (damage < 0) {
            view.showSuccessMessageWithAnInteger(SuccessMessage.CURRENT_PLAYER_RECEIVE_DAMAGE_AFTER_ATTACK, damage);
            getCurrentPlayer().decreaseLP(-damage);
            addCardToGraveYard(Zone.MONSTER_ZONE, selectedCellAddress, getCurrentPlayer());
            duelGameController.checkGameResult(getCurrentPlayer(), getOpponentPlayer(), GameResult.NO_LP);
        } else {
            view.showSuccessMessage(SuccessMessage.NO_DAMAGE_TO_ANYONE);
            addCardToGraveYard(Zone.MONSTER_ZONE, selectedCellAddress, getCurrentPlayer());
            addCardToGraveYard(Zone.MONSTER_ZONE, toBeAttackedCardAddress, getOpponentPlayer());
        }
    }

    private boolean checkForYomiShipOrExploderDragonEffect(int toBeAttackedCardAddress, Monster opponentCard) {
        if (opponentCard.getMonsterEffect().equals(MonsterEffect.YOMI_SHIP_EFFECT)) {//yomi ship effect
            System.out.println("yomi ship effect");
            addCardToGraveYard(Zone.MONSTER_ZONE, selectedCellAddress, getCurrentPlayer());
        } else if (opponentCard.getMonsterEffect().equals(MonsterEffect.EXPLODER_DRAGON_EFFECT)) {//exploder dragon effect
            addCardToGraveYard(Zone.MONSTER_ZONE, selectedCellAddress, getCurrentPlayer());
            addCardToGraveYard(Zone.MONSTER_ZONE, toBeAttackedCardAddress, getOpponentPlayer());
            return true;//to stop damage decreasing
        }
        addCardToGraveYard(Zone.MONSTER_ZONE, toBeAttackedCardAddress, getOpponentPlayer());
        return false;
    }

    private boolean isValidAttack(Matcher matcher) {
        int address = Integer.parseInt(matcher.group("monsterZoneNumber"));
        if (address > 5 || address < 1) {
            Error.showError(Error.INVALID_NUMBER);
            return false;
        }
        if (selectedCell == null) {
            Error.showError(Error.NO_CARD_SELECTED_YET);
            return false;
        } else if (!selectedCellZone.equals(Zone.MONSTER_ZONE)) {
            Error.showError(Error.CAN_NOT_ATTACK);
            return false;
        }
        if (!selectedCell.getCellStatus().equals(CellStatus.OFFENSIVE_OCCUPIED)) { // u sure ?
            Error.showError(Error.CAN_NOT_ATTACK);
            return false;
        } else if (!currentPhase.equals(Phase.BATTLE_PHASE)) {
            Error.showError(Error.ACTION_NOT_ALLOWED);
            return false;
        }
        if (hasCardUsedItsAttack()) {
            Error.showError(Error.ALREADY_ATTACKED);
            return false;
        }
        Cell opponentCell = getOpponentPlayer().getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, address);
        if (opponentCell.getCellStatus().equals(CellStatus.EMPTY)) {
            Error.showError(Error.NO_CARD_TO_BE_ATTACKED);
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
                if (cell.getCardInCell().getCardType().equals(TRAP)) {
                    Trap trap = (Trap) cell.getCardInCell();
                    if (activateTrapEffectInAttackSituation(trap.getTrapEffect())) {
                        view.showSuccessMessage(SuccessMessage.TRAP_ACTIVATED);
                        addCardToGraveYard(Zone.SPELL_ZONE, address, getOpponentPlayer()); //CHECK correctly removed ?
                    }
                    return true;
                }
            } else
                Error.showError(Error.INVALID_NUMBER);
        }
    }


    private boolean activateTrapEffectInAttackSituation(TrapEffect trapEffect) {
        switch (trapEffect) {
            case MAGIC_CYLINDER_EFFECT:
                trapMagicCylinderEffect();
                return true;
            case MIRROR_FORCE_EFFECT:
                trapMirrorForceEffect();
                return true;
            case NEGATE_ATTACK_EFFECT:
                trapNegateAttackEffect();
                return true;
            default:
                Error.showError(Error.PREPARATIONS_IS_NOT_DONE);
                return false;
        }
    }

    public void flipSummon() {//TODO might have effect
        if (selectedCell == null) {
            Error.showError(Error.NO_CARD_SELECTED_YET);
        } else if (!(selectedCellZone == Zone.MONSTER_ZONE)) {
            Error.showError(Error.CAN_NOT_CHANGE_POSITION);
        } else if (!(currentPhase == Phase.MAIN_PHASE_1 || currentPhase == Phase.MAIN_PHASE_2)) {
            Error.showError(Error.ACTION_CAN_NOT_WORK_IN_THIS_PHASE);
        } else if (selectedCell.getCellStatus() != CellStatus.DEFENSIVE_HIDDEN) {
            Error.showError(Error.FLIP_SUMMON_NOT_ALLOWED);
        } else {
            if (!((Monster) selectedCell.getCardInCell()).getMonsterEffect().equals(MonsterEffect.MAN_EATER_BUG_EFFECT)) {
                selectedCell.setCellStatus(CellStatus.OFFENSIVE_OCCUPIED);
                view.showSuccessMessage(SuccessMessage.FLIP_SUMMON_SUCCESSFUL);
                deselectCard(0);
            } else {
                manEaterBugMonsterEffectAndFlipSummon(getCurrentPlayer(), getOpponentPlayer());
            }
            if (isCurrentPlayerTrapToBeActivatedInSummonSituation()) {
                if (isTrapOfCurrentPlayerInSummonSituationActivated()) {
                    return;
                }
            }
            if (isOpponentTrapToBeActivatedInSummonSituation()) {
                view.showSuccessMessageWithAString(SuccessMessage.SHOW_TURN_WHEN_OPPONENT_WANTS_ACTIVE_TRAP_OR_SPELL_OR_MONSTER, getOpponentPlayer().getNickname());
                if (isOpponentTrapInSummonSituationActivated()) {
                    return;
                }
            }

        }

    }


    private void manEaterBugMonsterEffectAndFlipSummon(DuelPlayer current, DuelPlayer opponent) {
        if (view.yesNoQuestion("do you want to activate man eater bug effect? yes or no")) {
            while (true) {
                int address = view.askAddressForManEaterBug();
                if (address == -1) {
                    break;
                } else if (address >= 1 && address <= 5) {
                    if (opponent.getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, address).getCellStatus().equals(CellStatus.EMPTY)) {
                        Error.showError(Error.INVALID_SELECTION);
                    } else {
                        addCardToGraveYard(Zone.MONSTER_ZONE, address, opponent);
                        System.out.println("Man eater bug effect : destroy " + address + "card of opponent!");//TODO somewhere else
                        break;
                    }
                } else Error.showError(Error.INVALID_NUMBER);
            }
        }
        selectedCell.setCellStatus(CellStatus.OFFENSIVE_OCCUPIED);
        deselectCard(0);
        view.showSuccessMessage(SuccessMessage.FLIP_SUMMON_SUCCESSFUL);
    }


    // SPELL RELATED
    public void setSpellOrTrap() {
        if (selectedCell == null) {
            Error.showError(Error.NO_CARD_SELECTED_YET);
        } else if (!selectedCellZone.equals(Zone.HAND)) {
            Error.showError(Error.CAN_NOT_SET);
        } else if (selectedCell.getCardInCell().getCardType() == CardType.MONSTER) {
            Error.showError(Error.INVALID_COMMAND);
            return;
        } else if (!(currentPhase == Phase.MAIN_PHASE_1 || currentPhase == Phase.MAIN_PHASE_2)) {
            Error.showError(Error.ACTION_CAN_NOT_WORK_IN_THIS_PHASE);
        } else if (getCurrentPlayer().getPlayerBoard().isSpellZoneFull()) {
            Error.showError(Error.SPELL_ZONE_IS_FULL);
        } else if (selectedCell.getCardInCell().getCardType().equals(SPELL)) {//TODO
            if (((Spell) selectedCell.getCardInCell()).getSpellType().equals(SpellType.FIELD)) {
                setFieldCard();
                return;
            } else {
                SpellZone spellZone = getCurrentPlayer().getPlayerBoard().returnSpellZone();
                spellZone.addCard(selectedCell.getCardInCell(), CellStatus.HIDDEN);
                getCurrentPlayerHand().remove(selectedCellAddress - 1);
                view.showSuccessMessage(SuccessMessage.SET_SUCCESSFULLY);

            }
            deselectCard(0);
        } else {
            SpellZone spellZone = getCurrentPlayer().getPlayerBoard().returnSpellZone();
            spellZone.addCard(selectedCell.getCardInCell(), CellStatus.HIDDEN);
            getCurrentPlayerHand().remove(selectedCellAddress - 1);
            view.showSuccessMessage(SuccessMessage.SET_SUCCESSFULLY);
            deselectCard(0);
        }

    }

    private void setFieldCard() {
        getCurrentPlayer().getPlayerBoard().setFieldSpell((Spell) selectedCell.getCardInCell());
        view.showSuccessMessage(SuccessMessage.SET_SUCCESSFULLY);
        getCurrentPlayerHand().remove(selectedCellAddress - 1);
        deselectCard(0);
    }

    public void activateEffectOfSpellOrTrap() {
        if (opponentSelectedCell != null && selectedCell == null) {
            Error.showError(Error.ONLY_CAN_SHOW_OPPONENT_CARD);
            return;
        }
        if (selectedCell == null) {
            Error.showError(Error.NO_CARD_SELECTED_YET);
            return;
        }
        if (!selectedCellZone.equals(Zone.SPELL_ZONE) && !selectedCellZone.equals(Zone.HAND)) {
            Error.showError(Error.ONLY_SPELL_CAN_ACTIVE);
            return;
        }
        if (!currentPhase.equals(Phase.MAIN_PHASE_1) && !currentPhase.equals(Phase.MAIN_PHASE_2)) {
            Error.showError(Error.ACTION_CAN_NOT_WORK_IN_THIS_PHASE);
            return;
        }
        if (selectedCellZone.equals(Zone.SPELL_ZONE)) {
            if (selectedCell.getCellStatus().equals(CellStatus.OCCUPIED)) {
                Error.showError(Error.CARD_ALREADY_ACTIVATED);
                return;
            }
        }
        if (selectedCellZone.equals(Zone.HAND)) {
            if (getCurrentPlayer().getPlayerBoard().isSpellZoneFull()) {
                Error.showError(Error.SPELL_ZONE_IS_FULL);
                return;
            }
        }
        if (selectedCell.getCardInCell().getCardType() == CardType.SPELL) {
            if (selectedCellZone == Zone.HAND) {
                getCurrentPlayerHand().remove(selectedCellAddress - 1);
            }
            if (!((Spell) selectedCell.getCardInCell()).getSpellType().equals(SpellType.FIELD)) {
                normalSpellActivate(((Spell) selectedCell.getCardInCell()).getSpellEffect());
            } else fieldZoneSpellActivate();
        } else
            Error.showError(Error.PREPARATIONS_IS_NOT_DONE);
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
            default:
                Error.showError(Error.PREPARATIONS_IS_NOT_DONE);
        }
    }

    private void fieldZoneSpellActivate() {
        if (fieldZoneSpell == null) {
            if (turn == 1) {
                isFieldActivated = 1;
                firstPlayer.getPlayerBoard().faceUpActiveFieldSpell((Spell) selectedCell.getCardInCell());
            } else {
                isFieldActivated = 2;
                secondPlayer.getPlayerBoard().faceUpActiveFieldSpell((Spell) selectedCell.getCardInCell());
            }
        } else {
            reversePreviousFieldZoneSpellEffectAndRemoveIt();
            isFieldActivated = getTurn();
            if (isFieldActivated == 1) {
                firstPlayer.getPlayerBoard().faceUpActiveFieldSpell((Spell) selectedCell.getCardInCell());
            } else {
                secondPlayer.getPlayerBoard().faceUpActiveFieldSpell((Spell) selectedCell.getCardInCell());
            }
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
        addCardToGraveYard(Zone.FIELD_ZONE, 0, isFieldActivated == 1 ? firstPlayer : secondPlayer);
        isFieldActivated = 0;
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
            if (cell.getCellStatus() != CellStatus.EMPTY) {
                if (((Monster) cell.getCardInCell()).getMonsterType().equals(MonsterType.FIEND) || ((Monster) cell.getCardInCell()).getMonsterType().equals(MonsterType.SPELLCASTER) && !fieldEffectedCardsAddress.contains(10 + i))
                    fieldEffectedCardsAddress.add(10 + i);
            }
        }
        for (int i = 1; i <= 5; i++) {
            Cell cell = getOpponentPlayer().getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, i);
            if (cell.getCellStatus() != CellStatus.EMPTY) {
                if (((Monster) cell.getCardInCell()).getMonsterType().equals(MonsterType.FIEND) || ((Monster) cell.getCardInCell()).getMonsterType().equals(MonsterType.SPELLCASTER) && !fieldEffectedCardsAddress.contains(20 + i))
                    fieldEffectedCardsAddress.add(20 + i);
            }
        }
        addFoundCardsToBeEffectedByFieldCardToArrayList();
    }

    private void addFoundCardsToBeEffectedByFieldCardToArrayList() {
        for (Integer address : fieldEffectedCardsAddress) {

            if (address > 20) {
                if (!getOpponentPlayer().getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, address - 20).getCellStatus().equals(CellStatus.EMPTY))
                    fieldEffectedCards.add(getOpponentPlayer().getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, address - 20).getCardInCell());

            } else {
                if (!getCurrentPlayer().getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, address - 10).getCellStatus().equals(CellStatus.EMPTY))
                    fieldEffectedCards.add(getCurrentPlayer().getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, address - 10).getCardInCell());
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
            if (cell.getCellStatus() != CellStatus.EMPTY) {
                if (((Monster) cell.getCardInCell()).getMonsterType().equals(MonsterType.INSECT) ||
                        ((Monster) cell.getCardInCell()).getMonsterType().equals(MonsterType.BEAST) ||
                        ((Monster) cell.getCardInCell()).getMonsterType().equals(MonsterType.BEAST_WARRIOR) && !fieldEffectedCardsAddress.contains(10 + i))
                    fieldEffectedCardsAddress.add(10 + i);
            }
        }
        for (int i = 1; i <= 5; i++) {
            Cell cell = getOpponentPlayer().getPlayerBoard().getACellOfBoard(Zone.MONSTER_ZONE, i);
            if (cell.getCellStatus() != CellStatus.EMPTY) {
                if (((Monster) cell.getCardInCell()).getMonsterType().equals(MonsterType.FIEND) ||
                        ((Monster) cell.getCardInCell()).getMonsterType().equals(MonsterType.BEAST) ||
                        ((Monster) cell.getCardInCell()).getMonsterType().equals(MonsterType.BEAST_WARRIOR) && !fieldEffectedCardsAddress.contains(20 + i))
                    fieldEffectedCardsAddress.add(20 + i);
            }
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
            if (cell.getCellStatus() != CellStatus.EMPTY) {
                if (((Monster) cell.getCardInCell()).getMonsterType().equals(MonsterType.BEAST) && !fieldEffectedCardsAddress.contains(10 + i))
                    fieldEffectedCardsAddress.add(10 + i);
            }
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
            if (cell.getCellStatus() != CellStatus.EMPTY) {
                if (((Monster) cell.getCardInCell()).getMonsterType().equals(MonsterType.AQUA) &&
                        !fieldEffectedCardsAddress.contains(10 + i))
                    fieldEffectedCardsAddress.add(10 + i);
            }
        }
        addFoundCardsToBeEffectedByFieldCardToArrayList();
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
                reverseClosedForestFieldEffectOnOneCard(monster);
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

    private void reverseClosedForestFieldEffectOnOneCard(Monster monster) {
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

    public static void aIDeck() {
        Deck aIDeck = new Deck("aIDeck");
        ArrayList<Monster> allMonsters = Monster.getAllMonsters();
        for (int i = 0; i < 3; i++) {
            for (Monster monster : allMonsters) {
                if (monster.getMonsterActionType() == MonsterActionType.NORMAL && monster.getLevel() <= 4)
                    aIDeck.addCardToMainDeck(Card.getCardByName(monster.getName()));
            }
        }
    }
}