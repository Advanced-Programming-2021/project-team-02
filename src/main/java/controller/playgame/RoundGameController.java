package controller.playgame;

import model.Deck;
import model.User;
import model.card.Card;
import model.card.Monster;
import model.card.Spell;
import model.card.Trap;
import model.card.informationofcards.*;

import model.game.DuelPlayer;
import model.game.PlayerBoard;
import model.game.board.*;
import view.Menu;
import view.MenusManager;
import view.gameview.BetweenRoundView;
import view.gameview.GameView;
import view.messages.Error;
import view.messages.SuccessMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;

import static model.card.informationofcards.CardType.*;

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
    private boolean isWithAi;
    private boolean isFinishedGame = false;
    private boolean isFinishedRound = false;
    private boolean cantDrawCardBecauseOfTimeSeal = false;
    private int addressOfTimeSealToRemove;

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

    public void setRoundInfo(DuelPlayer firstPlayer, DuelPlayer secondPlayer, GameView view, DuelGameController duelGameController, boolean isWithAi) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        firstPlayer.setLifePoint(8000);
        secondPlayer.setLifePoint(8000);
        this.view = view;
        this.duelGameController = duelGameController;
        currentPhase = Phase.DRAW_PHASE;
        this.isWithAi = isWithAi;
        isFinishedRound = false;
        isFinishedGame = false;
        view.showSuccessMessageWithAString(SuccessMessage.PLAYERS_TURN, getCurrentPlayer().getNickname());
        duelGameController.setStartHandCards();
        drawCardFromDeck();
    }

    public void changeTurn() {
        selectedCell = null;
        turn = (turn == 1) ? 2 : 1;

    }

    public void selectCardInHand(int selectAddress) {
        if (selectAddress > getCurrentPlayerHand().size()) {
            Error.showError(Error.INVALID_SELECTION);
            return;
        }
        ArrayList<Card> hand = (ArrayList<Card>) (getCurrentPlayerHand());
        selectedCell = new Cell();
        selectedCellZone = Zone.HAND;
        selectedCell.setCardInCell(hand.get(selectAddress - 1));
        selectedCell.setCellStatus(CellStatus.IN_HAND);
        selectedCellAddress = selectAddress;
        if (!getCurrentPlayer().getNickname().equals("ai"))
            view.showSuccessMessage(SuccessMessage.CARD_SELECTED);
        opponentSelectedCell = null;
    }

    public void selectCardInMonsterZone(Matcher matcher) {
        int address = Integer.parseInt(matcher.group("monsterZoneNumber"));
        if (address > 5 || address < 1) {
            Error.showError(Error.INVALID_SELECTION);
            return;
        } else if (getCurrentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.MONSTER_ZONE, address).getCellStatus().equals(CellStatus.EMPTY)) {
            Error.showError(Error.CARD_NOT_FOUND);
            return;
        }
        selectedCellZone = Zone.MONSTER_ZONE;
        selectedCell = getCurrentPlayer().getPlayerBoard().getACellOfBoardWithAddress(selectedCellZone, address);
        selectedCellAddress = address;
        view.showSuccessMessage(SuccessMessage.CARD_SELECTED);
        opponentSelectedCell = null;
    }

    public void selectCardInSpellZone(Matcher matcher) {
        int address = Integer.parseInt(matcher.group("spellZoneNumber"));
        if (address > 5 || address < 1) {
            Error.showError(Error.INVALID_SELECTION);
            return;
        } else if (getCurrentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.SPELL_ZONE, address).getCellStatus().equals(CellStatus.EMPTY)) {
            Error.showError(Error.CARD_NOT_FOUND);
            return;
        }
        selectedCellZone = Zone.SPELL_ZONE;
        selectedCell = getCurrentPlayer().getPlayerBoard().getACellOfBoardWithAddress(selectedCellZone, address);
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
        selectedCell = opponentSelectedCell;
        selectedCellZone = Zone.NONE;
        view.showSuccessMessage(SuccessMessage.CARD_SELECTED);
    }

    public void selectOpponentCardMonsterZone(Matcher matcher) {
        int address = Integer.parseInt(matcher.group("monsterZoneNumber"));
        if (address > 5 || address < 1) {
            Error.showError(Error.INVALID_SELECTION);
            return;
        } else if (getOpponentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.MONSTER_ZONE, address).getCellStatus().equals(CellStatus.EMPTY)) {
            Error.showError(Error.CARD_NOT_FOUND);
            return;
        }
        opponentSelectedCell = getOpponentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.MONSTER_ZONE, address);
        selectedCell = opponentSelectedCell;
        selectedCellZone = Zone.NONE;
        view.showSuccessMessage(SuccessMessage.CARD_SELECTED);
    }

    public void selectOpponentCardSpellZone(Matcher matcher) {
        int address = Integer.parseInt(matcher.group("spellZoneNumber"));
        if (address > 5 || address < 1) {
            Error.showError(Error.INVALID_SELECTION);
            return;
        } else if (getOpponentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.SPELL_ZONE, address).getCellStatus().equals(CellStatus.EMPTY)) {
            Error.showError(Error.CARD_NOT_FOUND);
            return;
        }
        opponentSelectedCell = getOpponentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.SPELL_ZONE, address);
        selectedCell = opponentSelectedCell;
        selectedCellZone = Zone.NONE;
        view.showSuccessMessage(SuccessMessage.CARD_SELECTED);
    }

    private void torrentialTributeTrapEffect() {
        for (int i = 1; i <= 5; i++) {
            if (!getCurrentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.MONSTER_ZONE, i).getCellStatus().equals(CellStatus.EMPTY))
                addCardToGraveYard(Zone.MONSTER_ZONE, i, getCurrentPlayer());
            if (!getOpponentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.MONSTER_ZONE, i).getCellStatus().equals(CellStatus.EMPTY))
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
            view.showError(Error.PREPARATIONS_IS_NOT_DONE);
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
        addCardToGraveYard(Zone.MONSTER_ZONE, selectedCellAddress, getCurrentPlayer());
        deselectCard(0);
        GameResult result = duelGameController.checkGameResult(getOpponentPlayer(), getCurrentPlayer(), GameResultToCheck.NO_LP);
        if (result == GameResult.GAME_FINISHED) {
            finishGame(getOpponentPlayer());
        } else if (result == GameResult.ROUND_FINISHED) {
            finishRound(getOpponentPlayer());
        }
    }

    private void trapMirrorForceEffect() {
        for (int i = 1; i <= 5; i++) {
            if (getCurrentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.MONSTER_ZONE, i).getCellStatus().equals(CellStatus.OFFENSIVE_OCCUPIED))
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
        if (cantDrawCardBecauseOfTimeSeal) {
            System.out.println("cant draw card because of time seal!");
            addCardToGraveYard(Zone.SPELL_ZONE, addressOfTimeSealToRemove, getOpponentPlayer());
            cantDrawCardBecauseOfTimeSeal = false;
            nextPhase();
        }
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
            if (!getCurrentPlayer().getNickname().equals("ai"))
                view.showSuccessMessageWithAString(SuccessMessage.CARD_ADDED_TO_THE_HAND, card.getName());
        } else {
            GameResult result = duelGameController.checkGameResult(currentPlayer, getOpponentPlayer(), GameResultToCheck.NO_CARDS_TO_DRAW);// no card so this is loser!
            if (result == GameResult.GAME_FINISHED) {
                finishGame(currentPlayer);
            } else if (result == GameResult.ROUND_FINISHED) {
                finishRound(currentPlayer);
            }
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
        attackUsed();
        if (!getCurrentPlayer().getNickname().equals("ai"))
            view.showSuccessMessageWithAnInteger(SuccessMessage.OPPONENT_RECEIVE_DAMAGE_AFTER_DIRECT_ATTACK, monster.getAttackPower());
        view.showBoard();
        GameResult result = duelGameController.checkGameResult(getCurrentPlayer(), getOpponentPlayer(), GameResultToCheck.NO_LP);

        if (result == GameResult.GAME_FINISHED) {
            finishGame(getCurrentPlayer());
        } else if (result == GameResult.ROUND_FINISHED) {
            finishRound(getCurrentPlayer());
        }
    }

    public void nextPhase() {
        if (currentPhase.equals(Phase.DRAW_PHASE)) {
            currentPhase = Phase.STAND_BY_PHASE;
            System.out.println(currentPhase);
        } else if (currentPhase.equals(Phase.STAND_BY_PHASE)) {
            currentPhase = Phase.MAIN_PHASE_1;
            System.out.println(currentPhase);
        } else if (currentPhase == Phase.MAIN_PHASE_1) {
            currentPhase = Phase.BATTLE_PHASE;
            System.out.println(currentPhase);
        } else if (currentPhase == Phase.BATTLE_PHASE) {
            currentPhase = Phase.MAIN_PHASE_2;
            System.out.println(currentPhase);
        } else if (currentPhase == Phase.MAIN_PHASE_2) {
            currentPhase = Phase.DRAW_PHASE;
            view.showSuccessMessageWithAString(SuccessMessage.PLAYERS_TURN, getOpponentPlayer().getNickname());
            isSummonOrSetOfMonsterUsed = false;
            selectedCell = null;
            selectedCellZone = Zone.NONE;
            opponentSelectedCell = null;
            usedCellsToAttackNumbers.clear();
            changedPositionCards.clear();
            changeTurn();
            currentPhase = Phase.DRAW_PHASE;
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
        if (getCurrentPlayer().getPlayerBoard().isGraveYardEmpty() && getOpponentPlayer().getPlayerBoard().isGraveYardEmpty()) {
            view.showError(Error.PREPARATIONS_IS_NOT_DONE);
            return;
        }
        int addressOfAdd;
        if (selectedCellZone == Zone.HAND) {
            getCurrentPlayerHand().remove(selectedCellAddress - 1);
            addressOfAdd = getCurrentPlayer().getPlayerBoard().addSpellOrTrapToBoard(selectedCell.getCardInCell(), CellStatus.OCCUPIED);

        } else {
            getCurrentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.SPELL_ZONE, selectedCellAddress).setCellStatus(CellStatus.OCCUPIED);
            addressOfAdd = selectedCellAddress;
        }
        view.showBoard();
        int choice = view.twoChoiceQuestions("to summon card from, which you choose :", "your graveyard", "opponent graveyard");
        if (choice == 2) {
            view.showOpponentGraveYard();
            graveYard = getOpponentPlayer().getPlayerBoard().returnGraveYard();
        } else {
            view.showCurrentGraveYard(false);
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
                    addCardToGraveYard(Zone.SPELL_ZONE, addressOfAdd, getCurrentPlayer());
                    return;
                } else {
                    specialSummon(card, CellStatus.DEFENSIVE_HIDDEN);
                    deselectCard(0);
                    addCardToGraveYard(Zone.SPELL_ZONE, addressOfAdd, getCurrentPlayer());
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
        int addressOfAdd;
        if (selectedCellZone == Zone.HAND) {
            getCurrentPlayerHand().remove(selectedCellAddress - 1);
            addressOfAdd = getCurrentPlayer().getPlayerBoard().addSpellOrTrapToBoard(selectedCell.getCardInCell(), CellStatus.OCCUPIED);
        } else {
            getCurrentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.SPELL_ZONE, selectedCellAddress).setCellStatus(CellStatus.OCCUPIED);
            addressOfAdd = selectedCellAddress;
        }
        view.showBoard();
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
                    addCardToFirstPlayerHand(deck.getMainCards().get(address - 1));
                    deck.getMainCards().remove(address - 1);
                    break;
                } else view.showError(Error.INVALID_SELECTION);
            }
        }
        deck.shuffleDeck();
        addCardToGraveYard(Zone.SPELL_ZONE, addressOfAdd, getCurrentPlayer());
        System.out.println("TerraForming effect");
    }


    private void potOfGreedSpell() {
        List<Card> deckCards = getCurrentPlayer().getPlayDeck().getMainCards();
        int size = deckCards.size();
        if (size < 2) {
            view.showError(Error.PREPARATIONS_IS_NOT_DONE);
            return;
        }
        int addressOfAdd;
        if (selectedCellZone == Zone.HAND) {
            getCurrentPlayerHand().remove(selectedCellAddress - 1);
            addressOfAdd = getCurrentPlayer().getPlayerBoard().addSpellOrTrapToBoard(selectedCell.getCardInCell(), CellStatus.OCCUPIED);
        } else {
            getCurrentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.SPELL_ZONE, selectedCellAddress).setCellStatus(CellStatus.OCCUPIED);
            addressOfAdd = selectedCellAddress;
        }
        view.showBoard();
        getCurrentPlayerHand().add(deckCards.get(size - 1));
        deckCards.remove(size - 1);
        size = deckCards.size();
        getCurrentPlayerHand().add(deckCards.get(size - 1));
        deckCards.remove(size - 1);
        addCardToGraveYard(Zone.SPELL_ZONE, addressOfAdd, getCurrentPlayer());
        System.out.println("pot of greed effect");
        deselectCard(0);
    }

    private void raigekiSpell() {
        MonsterZone monsterZone = getOpponentPlayer().getPlayerBoard().returnMonsterZone();
        if (selectedCellZone == Zone.HAND) {
            getCurrentPlayerHand().remove(selectedCellAddress - 1);
        }
        int addressOfAdd;
        if (selectedCellZone == Zone.HAND) {
            getCurrentPlayerHand().remove(selectedCellAddress - 1);
            addressOfAdd = getCurrentPlayer().getPlayerBoard().addSpellOrTrapToBoard(selectedCell.getCardInCell(), CellStatus.OCCUPIED);
        } else {
            getCurrentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.SPELL_ZONE, selectedCellAddress).setCellStatus(CellStatus.OCCUPIED);
            addressOfAdd = selectedCellAddress;
        }
        view.showBoard();
        int i = 1;
        int counter = 1;
        while (counter <= 5) {
            if (monsterZone.getCellWithAddress(i).getCellStatus() != CellStatus.EMPTY)
                addCardToGraveYard(Zone.MONSTER_ZONE, i, getOpponentPlayer());
            i++;
            counter++;
        }
        addCardToGraveYard(Zone.SPELL_ZONE, addressOfAdd, getCurrentPlayer());
        System.out.println("raigekiSpell effect");
        deselectCard(0);
    }

    private void harpiesFeatherDusterSpell() {
        int i = 1;
        int addressOfAdd;
        if (selectedCellZone == Zone.HAND) {
            getCurrentPlayerHand().remove(selectedCellAddress - 1);
            addressOfAdd = getCurrentPlayer().getPlayerBoard().addSpellOrTrapToBoard(selectedCell.getCardInCell(), CellStatus.OCCUPIED);
        } else {
            getCurrentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.SPELL_ZONE, selectedCellAddress).setCellStatus(CellStatus.OCCUPIED);
            addressOfAdd = selectedCellAddress;
        }
        view.showBoard();
        while (getOpponentPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(i).getCellStatus() != CellStatus.EMPTY) {
            addCardToGraveYard(Zone.SPELL_ZONE, i, getOpponentPlayer());
            i++;
        }
        if (isFieldActivated == 2 && turn == 1) {
            reversePreviousFieldZoneSpellEffectAndRemoveIt();
        } else if (isFieldActivated == 1 && turn == 2) {
            reversePreviousFieldZoneSpellEffectAndRemoveIt();
        }
        addCardToGraveYard(Zone.SPELL_ZONE, addressOfAdd, getCurrentPlayer());
        System.out.println("harpiesFeatherDuster effect");
        deselectCard(0);
    }

    public void darkHoleSpell() {
        int i = 1;
        int addressOfAdd;
        if (selectedCellZone == Zone.HAND) {
            getCurrentPlayerHand().remove(selectedCellAddress - 1);
            addressOfAdd = getCurrentPlayer().getPlayerBoard().addSpellOrTrapToBoard(selectedCell.getCardInCell(), CellStatus.OCCUPIED);
        } else {
            getCurrentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.SPELL_ZONE, selectedCellAddress).setCellStatus(CellStatus.OCCUPIED);
            addressOfAdd = selectedCellAddress;
        }
        view.showBoard();
        while (getOpponentPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(i).getCellStatus() != CellStatus.EMPTY || i >= 5) {
            addCardToGraveYard(Zone.MONSTER_ZONE, i, getOpponentPlayer());
            i++;
        }
        i = 1;
        while (getCurrentPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(i).getCellStatus() != CellStatus.EMPTY || i >= 5) {
            addCardToGraveYard(Zone.MONSTER_ZONE, i, getCurrentPlayer());
            i++;
        }
        addCardToGraveYard(Zone.SPELL_ZONE, addressOfAdd, getCurrentPlayer());
        System.out.println("dark hole effect");
        deselectCard(0);
    }

    private void swordOfDarkDestructionSpell() {
        if (getCurrentPlayer().getPlayerBoard().isMonsterZoneEmpty() || !isFiendOrSpellCasterOnMap()) {
            view.showError(Error.PREPARATIONS_IS_NOT_DONE);
            return;
        }
        if (selectedCellZone == Zone.HAND) {
            getCurrentPlayer().getPlayerBoard().addSpellOrTrapToBoard(selectedCell.getCardInCell(), CellStatus.OCCUPIED);
            getCurrentPlayerHand().remove(selectedCellAddress - 1);
        } else {
            getCurrentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.SPELL_ZONE, selectedCellAddress).setCellStatus(CellStatus.OCCUPIED);
        }
        Card spellCard = selectedCell.getCardInCell();
        Monster monsterCard;
        int address;
        while (true) {
            address = view.swordOfDarkDestruction();
            if (!isValidAddress(address)) {
                return;
            }
            Cell cell = getCurrentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.MONSTER_ZONE, address);
            if (cell.getCellStatus().equals(CellStatus.EMPTY)) {
                view.showError(Error.INVALID_SELECTION);
            } else {
                monsterCard = (Monster) cell.getCardInCell();
                if (!monsterCard.getMonsterType().equals(MonsterType.FIEND) && !monsterCard.getMonsterType().equals(MonsterType.SPELLCASTER)) {
                    view.showError(Error.TYPE_FIEND_OR_SPELL_CASTER);
                } else break;
            }
        }
        monsterCard.changeAttackPower(400);
        monsterCard.changeDefensePower(-200);
        if (turn == 1) {
            firstPlayerHashmapForEquipSpells.put(spellCard, monsterCard);
        } else secondPlayerHashmapForEquipSpells.put(spellCard, monsterCard);
    }

    private boolean isFiendOrSpellCasterOnMap() {
        for (int i = 1; i < 5; i++) {
            Cell cell = getCurrentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.MONSTER_ZONE, i);
            if (cell.getCellStatus() != CellStatus.EMPTY) {
                if (((Monster) cell.getCardInCell()).getMonsterType() == MonsterType.FIEND || ((Monster) cell.getCardInCell()).getMonsterType() == MonsterType.SPELLCASTER)
                    return true;
            }
        }
        return false;
    }

    public void removeSwordOfDarkDestruction(Card card) {
        Monster monster = (Monster) card;
        monster.changeAttackPower(-400);
        monster.changeDefensePower(+200);
    }

    public void blackPendantSpell() {
        if (getCurrentPlayer().getPlayerBoard().isMonsterZoneEmpty()) {
            view.showError(Error.PREPARATIONS_IS_NOT_DONE);
            return;
        }
        if (selectedCellZone == Zone.HAND) {
            getCurrentPlayer().getPlayerBoard().addSpellOrTrapToBoard(selectedCell.getCardInCell(), CellStatus.OCCUPIED);
            getCurrentPlayerHand().remove(selectedCellAddress - 1);
        } else {
            getCurrentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.SPELL_ZONE, selectedCellAddress).setCellStatus(CellStatus.OCCUPIED);
        }
        Card spellCard = selectedCell.getCardInCell();
        Monster monsterCard;
        int address;
        while (true) {
            address = view.blackPendant();
            if (!isValidAddress(address)) return;
            Cell cell = getCurrentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.MONSTER_ZONE, address);
            if (cell.getCellStatus().equals(CellStatus.EMPTY)) {
                view.showError(Error.INVALID_SELECTION);
            } else {
                monsterCard = (Monster) cell.getCardInCell();
                break;
            }
        }
        monsterCard.changeAttackPower(500);

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

    public void removeBlackPendant(Card card) {
        Monster monster = (Monster) card;
        monster.setAttackPower(monster.getAttackPower() - 500);
    }


    public void surrender() {
        GameResult result;
        DuelPlayer winner;
        if (getCurrentPlayer() == firstPlayer) {
            result = duelGameController.checkGameResult(secondPlayer, firstPlayer, GameResultToCheck.SURRENDER);
            winner = secondPlayer;
        } else {
            result = duelGameController.checkGameResult(firstPlayer, secondPlayer, GameResultToCheck.SURRENDER);
            winner = firstPlayer;
        }
        if (result == GameResult.GAME_FINISHED) {
            finishGame(winner);
        } else if (result == GameResult.ROUND_FINISHED) {
            finishRound(winner);
        }
    }

    public void specialSummon(Card card, CellStatus cellStatus) {
        MonsterZone monsterZone = getCurrentPlayer().getPlayerBoard().returnMonsterZone();
        monsterZone.addCard((Monster) card, cellStatus);
        view.showBoard();
        checkNewCardToBeBeUnderEffectOfFieldCard((Monster) card);
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
            Cell cell = player.getPlayerBoard().getACellOfBoardWithAddress(fromZone, address);
            if (cell.getCellStatus().equals(CellStatus.EMPTY))
                return;
            checkDeathOfUnderFieldEffectCard((Monster) cell.getCardInCell());
            MonsterZone monsterZone = player.getPlayerBoard().returnMonsterZone();
            if (player == firstPlayer) {
                removeEquipSpell(address, monsterZone, firstPlayerHashmapForEquipSpells, firstPlayer);
            } else {
                removeEquipSpell(address, monsterZone, secondPlayerHashmapForEquipSpells, secondPlayer);
            }
            if (((Monster) cell.getCardInCell()).getMonsterEffect() == MonsterEffect.BEAST_KING_BARBAROS_EFFECT) {
                Monster monster = ((Monster) cell.getCardInCell());
                monster.setAttackPower(3000);
            }
            player.getPlayerBoard().removeMonsterFromBoardAndAddToGraveYard(address);
            view.showBoard();
        } else if (fromZone.equals(Zone.FIELD_ZONE)) {
            player.getPlayerBoard().removeFieldSpell();
            view.showBoard();
        } else if (fromZone.equals(Zone.SPELL_ZONE)) {
            Cell cell = player.getPlayerBoard().getACellOfBoardWithAddress(fromZone, address);
            if (cell.getCellStatus().equals(CellStatus.EMPTY))
                return;
            player.getPlayerBoard().removeSpellOrTrapFromBoard(address);
            view.showBoard();
        } else if (fromZone.equals(Zone.HAND)) {
            if (player.getNickname().equals(getCurrentPlayer().getNickname())) {
                Card card = getCardInHand(address);
                player.getPlayerBoard().addCardToGraveYardDirectly(card);
                getCurrentPlayerHand().remove(address);
                view.showBoard();
            } else {
                Card card = getCardInHand(address);
                if (card == null)
                    return;
                player.getPlayerBoard().addCardToGraveYardDirectly(card);
                getOpponentHand().remove(address);
                view.showBoard();
            }
        }
    }

    private void removeEquipSpell(int address, MonsterZone monsterZone, HashMap<Card, Monster> map, DuelPlayer player) {
        for (Card card : map.keySet()) {
            if (monsterZone.getCellWithAddress(address).getCardInCell() == map.get(card)) {
                SpellZone spellZone = player.getPlayerBoard().returnSpellZone();
                int i = 1;
                while (i <= 5) {
                    Spell spell;
                    if (spellZone.getCellWithAddress(i).getCellStatus() != CellStatus.EMPTY) {
                        if ((spell = (Spell) spellZone.getCellWithAddress(i).getCardInCell()) == card) {
                            if (spell.getSpellEffect() == SpellEffect.BLACK_PENDANT_EFFECT) {
                                removeBlackPendant(map.get(card));
                            } else {
                                removeSwordOfDarkDestruction(map.get(card));
                            }
                            player.getPlayerBoard().removeSpellOrTrapFromBoard(i);
                        }
                    }
                    i++;
                }
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
        if (selectedCellZone == Zone.NONE && opponentSelectedCell != null) {
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
            view.showError(Error.CAN_NOT_RITUAL_SUMMON);
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
            Cell cell = board.getACellOfBoardWithAddress(Zone.SPELL_ZONE, i);
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

    private boolean isOpponentTrapToBeActivatedInSummonSituation() {
        PlayerBoard board = getOpponentPlayer().getPlayerBoard();
        for (int i = 1; i <= 5; i++) {
            Cell cell = board.getACellOfBoardWithAddress(Zone.SPELL_ZONE, i);
            if (cell.getCellStatus().equals(CellStatus.EMPTY)) {
                continue;
            } else if (cell.getCardInCell().getCardType().equals(CardType.SPELL)) {
                continue;
            }
            Trap trap = (Trap) cell.getCardInCell();
            if (trap.getTrapEffect() == null)//TODO inja null khordim no idea
                continue;
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
                Cell cell = getOpponentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.SPELL_ZONE, address);
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
            if (isValidActivateTrapEffectInSummonSituationForOpponentToDo(trap.getTrapEffect(), cell)) {
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
                Cell cell = getCurrentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.SPELL_ZONE, address);
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
            if (isValidActivateTrapEffectInSummonSituationForCurentPlayerToDo(trap.getTrapEffect(), cell)) {

                addCardToGraveYard(Zone.SPELL_ZONE, address, getCurrentPlayer()); //CHECK correctly removed ?
                return true;
            }
        }
        return false;
    }

    private boolean isValidActivateTrapEffectInSummonSituationForOpponentToDo(TrapEffect trapEffect, Cell cell) {
        switch (trapEffect) {
            case TORRENTIAL_TRIBUTE_EFFECT:
                cell.setCellStatus(CellStatus.OCCUPIED);
                view.showSuccessMessage(SuccessMessage.TRAP_ACTIVATED);
                view.showBoard();
                torrentialTributeTrapEffect();
                return true;
            case TRAP_HOLE_EFFECT:
                cell.setCellStatus(CellStatus.OCCUPIED);
                view.showSuccessMessage(SuccessMessage.TRAP_ACTIVATED);
                view.showBoard();
                trapHoleTrapEffect();
                return true;
            default:
                Error.showError(Error.PREPARATIONS_IS_NOT_DONE);
                return false;
        }
    }

    private boolean isValidActivateTrapEffectInSummonSituationForCurentPlayerToDo(TrapEffect trapEffect, Cell cell) {
        if (trapEffect == TrapEffect.TORRENTIAL_TRIBUTE_EFFECT) {
            cell.setCellStatus(CellStatus.OCCUPIED);
            view.showSuccessMessage(SuccessMessage.TRAP_ACTIVATED);
            view.showBoard();
            torrentialTributeTrapEffect();
            return true;
        }
        Error.showError(Error.PREPARATIONS_IS_NOT_DONE);
        return false;
    }

    private void normalSummon() {
        isSummonOrSetOfMonsterUsed = true;
        summon();
        deselectCard(0);
    }

    private void summon() {
        getCurrentPlayer().getPlayerBoard().addMonsterToBoard((Monster) selectedCell.getCardInCell(), CellStatus.OFFENSIVE_OCCUPIED);
        if (!isWithAi) view.showSuccessMessage(SuccessMessage.SUMMONED_SUCCESSFULLY);
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
            }
        } else if (((Monster) selectedCell.getCardInCell()).getLevel() >= 5) {
            if (didTribute(1, getCurrentPlayer())) {
                normalSummon();
            }
        }
    }

    private boolean didTribute(int number, DuelPlayer player) {
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
            } else if (player.getPlayerBoard().getACellOfBoardWithAddress(Zone.MONSTER_ZONE, input).getCellStatus().equals(CellStatus.EMPTY)) {
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
            if (!player.getPlayerBoard().getACellOfBoardWithAddress(Zone.MONSTER_ZONE, i).getCellStatus().equals(CellStatus.EMPTY)) {
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

    private void activateRitualSpell() {
        String cardAddress;
        Monster monster;
        if (!isRitualCardInHand()) {
            Error.showError(Error.CAN_NOT_RITUAL_SUMMON);
            return;
        }
        while (true) {
            cardAddress = view.ritualCardName();
            if (Integer.parseInt(cardAddress) > getCurrentPlayerHand().size()) {
                view.showError(Error.INVALID_NUMBER);
                continue;
            }
            monster = (Monster) getCurrentPlayerHand().get(Integer.parseInt(cardAddress) - 1);
            if (Objects.requireNonNull(monster).getMonsterActionType() != MonsterActionType.RITUAL) {
                view.showError(Error.WRONG_CARD_NAME);
                view.showError(Error.RITUAL_SUMMON_NOW);
            } else if (!sumOfSubsequences(monster)) {
                Error.showError(Error.CAN_NOT_RITUAL_SUMMON);
            } else {
                if (view.getSummonOrderForRitual()) {
                    if (areCardsLevelsEnoughToSummonRitualMonster(monster)) {
                        int addAddress;
                        if (selectedCellZone == Zone.HAND) {
                            addAddress = getCurrentPlayer().getPlayerBoard().addSpellOrTrapToBoard(selectedCell.getCardInCell(), CellStatus.OCCUPIED);
                            getCurrentPlayerHand().remove(selectedCellAddress - 1);
                        } else {
                            getCurrentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.SPELL_ZONE, selectedCellAddress).setCellStatus(CellStatus.OCCUPIED);
                            addAddress = selectedCellAddress;
                        }
                        view.showBoard();
                        CellStatus status = view.getPositionForSetRitualMonster();
                        getCurrentPlayer().getPlayerBoard().addMonsterToBoard(monster, status);
                        view.showBoard();
                        addCardToGraveYard(Zone.SPELL_ZONE, addAddress, getCurrentPlayer());
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
                        break;
                    } else {
                        return;
                    }
                }
            }
        }
    }


    public boolean areCardsLevelsEnoughToSummonRitualMonster(Monster ritualMonster) {
        while (true) {
            String[] addressesString = view.getMonstersAddressesToBringRitual();
            if (addressesString[0].equals("cancel")) {
                cancel();
                return false;
            }
            int[] addresses = new int[addressesString.length];
            for (int i = 0; i < addressesString.length; i++) {
                addresses[i] = Integer.parseInt(addressesString[i]);
            }
            int sum = 0;
            MonsterZone monsterZone = getCurrentPlayer().getPlayerBoard().returnMonsterZone();
            for (Integer address : addresses) {
                Monster monster = (Monster) monsterZone.getCellWithAddress(address).getCardInCell();
                sum += monster.getLevel();
            }

            if (sum == ritualMonster.getLevel()) {
                for (Integer address : addresses) {
                    addCardToGraveYard(Zone.MONSTER_ZONE, address, getCurrentPlayer());
                }
                return true;
            } else {
                view.showError(Error.LEVEL_DOES_NOT_MATCH);
                continue;
            }
        }
    }

    private boolean isRitualCardInHand() {
        List<Card> currentPlayerHand = getCurrentPlayerHand();
        for (Card card : currentPlayerHand) {
            if (card.getCardType() == CardType.MONSTER) {
                if (((Monster) card).getMonsterActionType() == MonsterActionType.RITUAL) return true;
            }
        }
        return false;
    }

    public boolean sumOfSubsequences(Monster monster) {
        MonsterZone monsterZone = getCurrentPlayer().getPlayerBoard().returnMonsterZone();
        int sum = 0;
        int[] levels = new int[5];
        for (int i = 1; i <= 5; i++) {
            Cell cell = monsterZone.getCellWithAddress(i);
            if (cell.getCellStatus() != CellStatus.EMPTY) {
                Monster cardNumberI = (Monster) cell.getCardInCell();
                levels[i] = cardNumberI.getLevel();
            }
        }
        ArrayList<Integer> sumOfSubset = new ArrayList<>();
        sumOfSubset = subsetSums(levels, 0, levels.length - 1, 0, sumOfSubset);
        for (Integer level : sumOfSubset) {
            if (level == Objects.requireNonNull(monster).getLevel()) return true;
        }
        return false;
    }

    public ArrayList<Integer> subsetSums(int[] arr, int l, int r, int sum, ArrayList<Integer> sumOfSubsets) {
        if (l > r) {
            sumOfSubsets.add(sum);
            return sumOfSubsets;
        }
        subsetSums(arr, l + 1, r, sum + arr[l], sumOfSubsets);
        subsetSums(arr, l + 1, r, sum, sumOfSubsets);
        return sumOfSubsets;
    }

    public void setCrad() {
        if (selectedCell == null) {
            view.showError(Error.NO_CARD_SELECTED_YET);
            return;
        }
        if (selectedCellZone == Zone.NONE && opponentSelectedCell != null) {
            view.showError(Error.ONLY_CAN_SHOW_OPPONENT_CARD);
            return;
        }
        if (selectedCell.getCardInCell().getCardType() == MONSTER) {
            setMonster();
        } else {
            setSpellOrTrap();
        }
    }

    public void setMonster() {
        if (selectedCellZone == Zone.NONE && opponentSelectedCell != null) {
            Error.showError(Error.ONLY_CAN_SHOW_OPPONENT_CARD);
            return;
        }
        if (!isValidSelectionForSummonOrSet()) {
            return;
        }
        if ((!selectedCellZone.equals(Zone.HAND))) {
            Error.showError(Error.CAN_NOT_SET);
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
            view.showError(Error.CAN_NOT_RITUAL_SUMMON);
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
            Cell cell = player.getPlayerBoard().getACellOfBoardWithAddress(Zone.SPELL_ZONE, i);
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
                    } else if (getOpponentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.SPELL_ZONE, address).getCardInCell().getCardType().equals(TRAP)) {
                        trap = (Trap) getOpponentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.SPELL_ZONE, address).getCardInCell();
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
            }
        } else if (((Monster) selectedCell.getCardInCell()).getLevel() >= 5) {
            if (didTribute(1, getCurrentPlayer())) {
                normalSet();
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
            view.showBoard();
            deselectCard(0);
        }

    }

    public void attackToCard(int attackedAddress) {
        if (!isValidAttack(attackedAddress))
            return;
        CellStatus opponentCellStatus = getOpponentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.MONSTER_ZONE, attackedAddress).getCellStatus();
        if (opponentCellStatus.equals(CellStatus.DEFENSIVE_HIDDEN)) {
            attackToDHCard(getOpponentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.MONSTER_ZONE, attackedAddress), attackedAddress);
        } else if (opponentCellStatus.equals(CellStatus.DEFENSIVE_OCCUPIED)) {
            attackToDOCard(getOpponentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.MONSTER_ZONE, attackedAddress), attackedAddress);
        } else {
            attackToOOCard(getOpponentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.MONSTER_ZONE, attackedAddress), attackedAddress);
        }
        attackUsed();
        deselectCard(0);
    }

    private void attackToDHCard(Cell opponentCellToBeAttacked, int toBeAttackedCardAddress) {
        GameResult result = null;
        DuelPlayer probableWinner = null;
        Monster opponentCard = (Monster) opponentCellToBeAttacked.getCardInCell();
        view.showSuccessMessageWithAString(SuccessMessage.DH_CARD_BECOMES_DO, opponentCard.getName());
        opponentCellToBeAttacked.changeCellStatus(CellStatus.DEFENSIVE_OCCUPIED);
        attackToDOCard(opponentCellToBeAttacked, toBeAttackedCardAddress);

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
                if (!getCurrentPlayer().getNickname().equals("ai"))
                    view.showSuccessMessageWithAnInteger(SuccessMessage.OPPONENT_RECEIVE_DAMAGE_AFTER_ATTACK, damage);
                addCardToGraveYard(Zone.MONSTER_ZONE, toBeAttackedCardAddress, getOpponentPlayer());
                result = duelGameController.checkGameResult(getCurrentPlayer(), getOpponentPlayer(), GameResultToCheck.NO_LP);
                probableWinner = getCurrentPlayer();
            } else {
                System.out.println("exploder effect");

            }
        } else if (damage < 0) {
            checkForManEaterBugAttacked();
            if (!getCurrentPlayer().getNickname().equals("ai"))
                view.showSuccessMessageWithAnInteger(SuccessMessage.CURRENT_PLAYER_RECEIVE_DAMAGE_AFTER_ATTACK, damage);
            getCurrentPlayer().decreaseLP(-damage);
            getCurrentPlayer().getPlayerBoard().addCardToGraveYardDirectly(opponentCellToBeAttacked.getCardInCell());
            addCardToGraveYard(Zone.MONSTER_ZONE, selectedCellAddress, getCurrentPlayer());
            result = duelGameController.checkGameResult(getOpponentPlayer(), getCurrentPlayer(), GameResultToCheck.NO_LP);
            probableWinner = getOpponentPlayer();
        } else {
            checkForManEaterBugAttacked();
            if (!getCurrentPlayer().getNickname().equals("ai"))
                view.showSuccessMessage(SuccessMessage.NO_DAMAGE_TO_ANYONE);
            addCardToGraveYard(Zone.MONSTER_ZONE, selectedCellAddress, getCurrentPlayer());
            addCardToGraveYard(Zone.MONSTER_ZONE, toBeAttackedCardAddress, getOpponentPlayer());
        }
        if (result == GameResult.GAME_FINISHED) {
            finishGame(probableWinner);
        } else if (result == GameResult.ROUND_FINISHED) {
            finishRound(probableWinner);
        }
    }

    private void checkForManEaterBugAttacked() {
        PlayerBoard board = getOpponentPlayer().getPlayerBoard();
        boolean flag = false;
        for (int i = 1; i <= 5; i++) {
            Cell cell = board.getACellOfBoardWithAddress(Zone.MONSTER_ZONE, i);
            if (cell.getCellStatus() == CellStatus.EMPTY) {
                continue;
            }
            Monster monster = ((Monster) cell.getCardInCell());
            if (monster.getMonsterEffect() == MonsterEffect.MAN_EATER_BUG_EFFECT) {
                flag = true;
            }
        }
        if (!flag) {
            return;
        }
        view.showSuccessMessageWithAString(SuccessMessage.SHOW_TURN_WHEN_OPPONENT_WANTS_ACTIVE_TRAP_OR_SPELL_OR_MONSTER, getCurrentPlayer().getNickname());
        if (view.yesNoQuestion("do you want to activate man eater bug effect?(yes or no!)")) {
            while (true) {
                int address = view.askAddressForManEaterBug();
                if (address == -1) {
                    return;
                } else if (address >= 1 && address <= 5) {
                    if (getCurrentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.MONSTER_ZONE, address).getCellStatus().equals(CellStatus.EMPTY)) {
                        Error.showError(Error.INVALID_SELECTION);
                    } else {
                        addCardToGraveYard(Zone.MONSTER_ZONE, address, getCurrentPlayer());
                        System.out.println("Man eater bug effect : destroy " + address + "card of you!");
                        break;
                    }
                } else Error.showError(Error.INVALID_NUMBER);
            }
        }
    }


    private void attackToDOCard(Cell opponentCellToBeAttacked, int toBeAttackedCardAddress) {
        GameResult result = null;
        DuelPlayer probableWinner = null;
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
            if (!getCurrentPlayer().getNickname().equals("ai"))
                view.showSuccessMessage(SuccessMessage.DEFENSIVE_MONSTER_DESTROYED);
            checkForYomiShipOrExploderDragonEffect(toBeAttackedCardAddress, opponentCard);
        } else if (damage < 0) {
            if (!getCurrentPlayer().getNickname().equals("ai"))
                view.showSuccessMessageWithAnInteger(SuccessMessage.DAMAGE_TO_CURRENT_PLAYER_AFTER_ATTACK_TI_HIGHER_DEFENSIVE_DO_OR_DH_MONSTER, damage);
            getCurrentPlayer().decreaseLP(-damage);
            result = duelGameController.checkGameResult(getOpponentPlayer(), getCurrentPlayer(), GameResultToCheck.NO_LP);
            probableWinner = getOpponentPlayer();
        } else {
            if (!getCurrentPlayer().getNickname().equals("ai"))
                view.showSuccessMessage(SuccessMessage.NO_CARD_DESTROYED);
        }
        if (result == GameResult.GAME_FINISHED) {
            finishGame(probableWinner);
        } else if (result == GameResult.ROUND_FINISHED) {
            finishRound(probableWinner);
        }
    }

    private void attackToOOCard(Cell opponentCellToBeAttacked, int toBeAttackedCardAddress) { // might have effect
        GameResult result = null;
        DuelPlayer probableWinner = null;
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
                if (!getCurrentPlayer().getNickname().equals("ai"))
                    view.showSuccessMessageWithAnInteger(SuccessMessage.OPPONENT_RECEIVE_DAMAGE_AFTER_ATTACK, damage);
                addCardToGraveYard(Zone.MONSTER_ZONE, toBeAttackedCardAddress, getOpponentPlayer());
                getOpponentPlayer().decreaseLP(damage);
                result = duelGameController.checkGameResult(getCurrentPlayer(), getOpponentPlayer(), GameResultToCheck.NO_LP);
                probableWinner = getCurrentPlayer();
            } else {
                System.out.println("exploder effect");
            }
        } else if (damage < 0) {
            if (!getCurrentPlayer().getNickname().equals("ai"))
                view.showSuccessMessageWithAnInteger(SuccessMessage.CURRENT_PLAYER_RECEIVE_DAMAGE_AFTER_ATTACK, damage);
            getCurrentPlayer().decreaseLP(-damage);
            addCardToGraveYard(Zone.MONSTER_ZONE, selectedCellAddress, getCurrentPlayer());
            result = duelGameController.checkGameResult(getOpponentPlayer(), getCurrentPlayer(), GameResultToCheck.NO_LP);
            probableWinner = getOpponentPlayer();
        } else {
            if (!getCurrentPlayer().getNickname().equals("ai"))
                view.showSuccessMessage(SuccessMessage.NO_DAMAGE_TO_ANYONE);
            addCardToGraveYard(Zone.MONSTER_ZONE, selectedCellAddress, getCurrentPlayer());
            addCardToGraveYard(Zone.MONSTER_ZONE, toBeAttackedCardAddress, getOpponentPlayer());
        }
        if (result == GameResult.GAME_FINISHED) {
            finishGame(probableWinner);
        } else if (result == GameResult.ROUND_FINISHED) {
            finishRound(probableWinner);
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

    private boolean isValidAttack(int attackAddress) {
        if (attackAddress > 5 || attackAddress < 1) {
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
        Cell opponentCell = getOpponentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.MONSTER_ZONE, attackAddress);
        if (opponentCell.getCellStatus().equals(CellStatus.EMPTY)) {
            Error.showError(Error.NO_CARD_TO_BE_ATTACKED);
            return false;
        }
        return true;
    }

    private boolean isTrapToBeActivatedInAttackSituation() {
        for (int i = 1; i <= 5; i++) {
            Cell cell = getOpponentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.SPELL_ZONE, i);
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
                Cell cell = getOpponentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.SPELL_ZONE, address);
                if (cell.getCardInCell().getCardType().equals(TRAP)) {
                    Trap trap = (Trap) cell.getCardInCell();
                    if (activateTrapEffectInAttackSituation(trap.getTrapEffect(), cell)) {

                        addCardToGraveYard(Zone.SPELL_ZONE, address, getOpponentPlayer()); //CHECK correctly removed ?
                    }
                    return true;
                }
            } else
                Error.showError(Error.INVALID_NUMBER);
        }
    }


    private boolean activateTrapEffectInAttackSituation(TrapEffect trapEffect, Cell cell) {
        switch (trapEffect) {
            case MAGIC_CYLINDER_EFFECT:
                cell.setCellStatus(CellStatus.OCCUPIED);
                view.showSuccessMessage(SuccessMessage.TRAP_ACTIVATED);
                view.showBoard();
                trapMagicCylinderEffect();
                return true;
            case MIRROR_FORCE_EFFECT:
                cell.setCellStatus(CellStatus.OCCUPIED);
                view.showSuccessMessage(SuccessMessage.TRAP_ACTIVATED);
                view.showBoard();
                trapMirrorForceEffect();
                return true;
            case NEGATE_ATTACK_EFFECT:
                cell.setCellStatus(CellStatus.OCCUPIED);
                view.showSuccessMessage(SuccessMessage.TRAP_ACTIVATED);
                view.showBoard();
                trapNegateAttackEffect();
                return true;
            default:
                Error.showError(Error.PREPARATIONS_IS_NOT_DONE);
                return false;
        }
    }

    public void flipSummon() {
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
                isOpponentTrapInSummonSituationActivated();
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
                    if (opponent.getPlayerBoard().getACellOfBoardWithAddress(Zone.MONSTER_ZONE, address).getCellStatus().equals(CellStatus.EMPTY)) {
                        Error.showError(Error.INVALID_SELECTION);
                    } else {
                        addCardToGraveYard(Zone.MONSTER_ZONE, address, opponent);
                        System.out.println("Man eater bug effect : destroy " + address + "card of opponent!");
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
        if (selectedCellZone == Zone.NONE && opponentSelectedCell != null) {
            Error.showError(Error.ONLY_CAN_SHOW_OPPONENT_CARD);
            return;
        }
        if (selectedCell == null) {
            Error.showError(Error.NO_CARD_SELECTED_YET);
        } else if (!selectedCellZone.equals(Zone.HAND)) {
            Error.showError(Error.CAN_NOT_SET);
        } else if (selectedCell.getCardInCell().getCardType() == CardType.MONSTER) {
            Error.showError(Error.INVALID_COMMAND);
        } else if (!(currentPhase == Phase.MAIN_PHASE_1 || currentPhase == Phase.MAIN_PHASE_2)) {
            Error.showError(Error.ACTION_CAN_NOT_WORK_IN_THIS_PHASE);
        } else if (getCurrentPlayer().getPlayerBoard().isSpellZoneFull()) {
            Error.showError(Error.SPELL_ZONE_IS_FULL);
        } else if (selectedCell.getCardInCell().getCardType().equals(SPELL)) {
            if (((Spell) selectedCell.getCardInCell()).getSpellType().equals(SpellType.FIELD)) {
                setFieldCard();
                return;
            } else {
                SpellZone spellZone = getCurrentPlayer().getPlayerBoard().returnSpellZone();
                spellZone.addCard(selectedCell.getCardInCell(), CellStatus.HIDDEN);
                getCurrentPlayerHand().remove(selectedCellAddress - 1);
                view.showSuccessMessage(SuccessMessage.SET_SUCCESSFULLY);
                view.showBoard();
            }
            deselectCard(0);
        } else {
            SpellZone spellZone = getCurrentPlayer().getPlayerBoard().returnSpellZone();
            spellZone.addCard(selectedCell.getCardInCell(), CellStatus.HIDDEN);
            getCurrentPlayerHand().remove(selectedCellAddress - 1);
            view.showSuccessMessage(SuccessMessage.SET_SUCCESSFULLY);
            view.showBoard();
            deselectCard(0);
        }

    }

    private void setFieldCard() {
        getCurrentPlayer().getPlayerBoard().setFieldSpell((Spell) selectedCell.getCardInCell());
        view.showSuccessMessage(SuccessMessage.SET_SUCCESSFULLY);
        getCurrentPlayerHand().remove(selectedCellAddress - 1);
        view.showBoard();
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
        if (selectedCell.getCardInCell().getCardType() == MONSTER) {
            view.showError(Error.ONLY_SPELL_CAN_ACTIVE);
            return;
        }
        if (!selectedCellZone.equals(Zone.SPELL_ZONE) && !selectedCellZone.equals(Zone.HAND) && selectedCellZone.equals(Zone.FIELD_ZONE)) {
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
            Spell spell = (Spell) selectedCell.getCardInCell();
            if (spell.getSpellType() == SpellType.RITUAL) {
                activateRitualSpell();
                return;
            }
            if (!((Spell) selectedCell.getCardInCell()).getSpellType().equals(SpellType.FIELD)) {
                normalSpellActivate(((Spell) selectedCell.getCardInCell()).getSpellEffect());
            } else fieldZoneSpellActivate();
        } else if (selectedCell.getCardInCell().getCardType() == TRAP) {
            if (selectedCellZone == Zone.SPELL_ZONE) {
                normalTrapActivate(((Trap) selectedCell.getCardInCell()).getTrapEffect());
            } else {
                view.showError(Error.CAN_NOT_ACTIVE_EFFECT);
            }
        }
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

    private void normalTrapActivate(TrapEffect trapEffect) {
        switch (trapEffect) {
            case CALL_OF_THE_HAUNTED_EFFECT:
                callOfTheHauntedTrap();
                break;
            case TIME_SEAL_EFFECT:
                timeSealTrap();
                break;
            default:
                view.showError(Error.CAN_NOT_ACTIVE_EFFECT);
                break;
        }
    }

    private void callOfTheHauntedTrap() {
        if (getCurrentPlayer().getPlayerBoard().isGraveYardEmpty() || getCurrentPlayer().getPlayerBoard().isMonsterZoneFull()) {
            view.showError(Error.PREPARATIONS_IS_NOT_DONE);
            return;
        }
        GraveYard graveYard = getCurrentPlayer().getPlayerBoard().returnGraveYard();
        ArrayList<Card> cards = graveYard.getGraveYardCards();
        boolean flag = false;
        for (Card card : cards) {
            if (card.getCardType() == MONSTER) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            view.showError(Error.PREPARATIONS_IS_NOT_DONE);
            return;
        }
        view.showCurrentGraveYard(false);
        view.showSuccessMessage(SuccessMessage.TRAP_ACTIVATED);
        selectedCell.setCellStatus(CellStatus.OCCUPIED);
        view.showBoard();
        while (true) {
            Card card;
            int address = view.chooseCardInGraveYard();
            if (address == -1) {
                cancel();
                return;
            } else if (address - 1 > graveYard.getGraveYardCards().size()) {
                view.showError(Error.INVALID_NUMBER);
            } else if ((card = graveYard.getGraveYardCards().get(address - 1)).getCardType().equals(CardType.MONSTER)) {
                specialSummon(card, CellStatus.OFFENSIVE_OCCUPIED);
                addCardToGraveYard(Zone.SPELL_ZONE, selectedCellAddress, getCurrentPlayer());
            } else view.showError(Error.INVALID_SELECTION);
        }
    }

    private void timeSealTrap() {
        cantDrawCardBecauseOfTimeSeal = true;
        selectedCell.setCellStatus(CellStatus.OCCUPIED);
        view.showSuccessMessage(SuccessMessage.TRAP_ACTIVATED);
        view.showBoard();
        addressOfTimeSealToRemove = selectedCellAddress;
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
            view.showBoard();
        } else {
            reversePreviousFieldZoneSpellEffectAndRemoveIt();
            isFieldActivated = getTurn();
            if (isFieldActivated == 1) {
                firstPlayer.getPlayerBoard().faceUpActiveFieldSpell((Spell) selectedCell.getCardInCell());
            } else {
                secondPlayer.getPlayerBoard().faceUpActiveFieldSpell((Spell) selectedCell.getCardInCell());
            }
            view.showBoard();
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
            Cell cell = getCurrentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.MONSTER_ZONE, i);
            if (cell.getCellStatus() != CellStatus.EMPTY) {
                if (((Monster) cell.getCardInCell()).getMonsterType().equals(MonsterType.FIEND) || ((Monster) cell.getCardInCell()).getMonsterType().equals(MonsterType.SPELLCASTER) && !fieldEffectedCardsAddress.contains(10 + i))
                    fieldEffectedCardsAddress.add(10 + i);
            }
        }
        for (int i = 1; i <= 5; i++) {
            Cell cell = getOpponentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.MONSTER_ZONE, i);
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
                if (!getOpponentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.MONSTER_ZONE, address - 20).getCellStatus().equals(CellStatus.EMPTY))
                    fieldEffectedCards.add(getOpponentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.MONSTER_ZONE, address - 20).getCardInCell());
            } else {
                if (!getCurrentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.MONSTER_ZONE, address - 10).getCellStatus().equals(CellStatus.EMPTY))
                    fieldEffectedCards.add(getCurrentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.MONSTER_ZONE, address - 10).getCardInCell());
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
            Cell cell = getCurrentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.MONSTER_ZONE, i);
            if (cell.getCellStatus() != CellStatus.EMPTY) {
                if (((Monster) cell.getCardInCell()).getMonsterType().equals(MonsterType.INSECT) ||
                        ((Monster) cell.getCardInCell()).getMonsterType().equals(MonsterType.BEAST) ||
                        ((Monster) cell.getCardInCell()).getMonsterType().equals(MonsterType.BEAST_WARRIOR) && !fieldEffectedCardsAddress.contains(10 + i))
                    fieldEffectedCardsAddress.add(10 + i);
            }
        }
        for (int i = 1; i <= 5; i++) {
            Cell cell = getOpponentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.MONSTER_ZONE, i);
            if (cell.getCellStatus() != CellStatus.EMPTY) {
                if (((Monster) cell.getCardInCell()).getMonsterType().equals(MonsterType.FIEND) ||
                        ((Monster) cell.getCardInCell()).getMonsterType().equals(MonsterType.BEAST) ||
                        ((Monster) cell.getCardInCell()).getMonsterType().equals(MonsterType.BEAST_WARRIOR) && !fieldEffectedCardsAddress.contains(20 + i))
                    fieldEffectedCardsAddress.add(20 + i);
            }
        }
        addFoundCardsToBeEffectedByFieldCardToArrayList();
    }

    private void closedForestFieldEffect() {
        addClosedForestFieldCardsToBeEffected();
        for (Card card : fieldEffectedCards) {
            if (((Monster) card).getMonsterType().equals(MonsterType.BEAST)) {
                ((Monster) card).changeAttackPower(100);
            }
        }
    }

    private void addClosedForestFieldCardsToBeEffected() {
        for (int i = 1; i <= 5; i++) {
            Cell cell = getCurrentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.MONSTER_ZONE, i);
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
            Cell cell = getCurrentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.MONSTER_ZONE, i);
            if (cell.getCellStatus() != CellStatus.EMPTY) {
                if (((Monster) cell.getCardInCell()).getMonsterType().equals(MonsterType.AQUA) &&
                        !fieldEffectedCardsAddress.contains(10 + i))
                    fieldEffectedCardsAddress.add(10 + i);
            }
        }
        for (int i = 1; i <= 5; i++) {
            Cell cell = getOpponentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.MONSTER_ZONE, i);
            if (cell.getCellStatus() != CellStatus.EMPTY) {
                if (((Monster) cell.getCardInCell()).getMonsterType().equals(MonsterType.AQUA) &&
                        !fieldEffectedCardsAddress.contains(10 + i))
                    fieldEffectedCardsAddress.add(20 + i);
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
        if (monster.getMonsterType().equals(MonsterType.FIEND) || monster.getMonsterType().equals(MonsterType.BEAST)
                || monster.getMonsterType().equals(MonsterType.SPELLCASTER)) {
            monster.changeAttackPower(200);
            monster.changeDefensePower(200);
            fieldEffectedCards.add(monster);
        } else if (monster.getMonsterType().equals(MonsterType.FAIRY)) {
            monster.changeDefensePower(-200);
            monster.changeAttackPower(-200);
            fieldEffectedCards.add(monster);
        }
    }

    private void forestFieldEffectOnOneCard(Monster monster) {
        if (monster.getMonsterType().equals(MonsterType.INSECT) ||
                monster.getMonsterType().equals(MonsterType.BEAST) ||
                monster.getMonsterType().equals(MonsterType.BEAST_WARRIOR)) {
            monster.changeAttackPower(200);
            monster.changeDefensePower(200);
            fieldEffectedCards.add(monster);
        }
    }

    private void closedForestFieldEffectOnOneCard(Monster monster) {
        if (monster.getMonsterType().equals(MonsterType.BEAST)) {
            monster.changeAttackPower(100);
            fieldEffectedCards.add(monster);
        }
    }

    private void umiirukaFieldEffectOnOneCard(Monster monster) {
        if (monster.getMonsterType().equals(MonsterType.AQUA)) {
            monster.changeAttackPower(500);
            monster.changeDefensePower(-400);
            fieldEffectedCards.add(monster);
        }
    }

    private void reverseYamiFieldEffectOnOneCard(Monster monster) {
        if (monster.getMonsterType().equals(MonsterType.INSECT) || monster.getMonsterType().equals(MonsterType.BEAST)
                || monster.getMonsterType().equals(MonsterType.BEAST_WARRIOR)) {
            monster.changeAttackPower(-200);
            monster.changeDefensePower(-200);
            fieldEffectedCards.remove(monster);
        } else if (monster.getMonsterType().equals(MonsterType.FAIRY)) {
            monster.changeDefensePower(200);
            monster.changeAttackPower(200);
            fieldEffectedCards.remove(monster);
        }
    }

    private void reverseForestFieldEffectOnOneCard(Monster monster) {
        if (monster.getMonsterType().equals(MonsterType.INSECT) ||
                monster.getMonsterType().equals(MonsterType.BEAST) ||
                monster.getMonsterType().equals(MonsterType.BEAST_WARRIOR)) {
            monster.changeAttackPower(-200);
            monster.changeDefensePower(-200);
            fieldEffectedCards.remove(monster);
        }
    }

    private void reverseClosedForestFieldEffectOnOneCard(Monster monster) {
        if (monster.getMonsterType().equals(MonsterType.BEAST)) {
            monster.changeAttackPower(100);
            fieldEffectedCards.remove(monster);
        }
    }

    private void reverseUmiirukaFieldEffectOnOneCard(Monster monster) {
        if (monster.getMonsterType().equals(MonsterType.AQUA)) {
            monster.changeAttackPower(-500);
            monster.changeDefensePower(400);
            fieldEffectedCards.remove(monster);
        }
    }

    public void aiTurn() {
        if (getCurrentPlayer().getPlayerBoard().isMonsterZoneEmpty()) {
            selectCardInHand(1);
            currentPhase = Phase.MAIN_PHASE_1;
            summonMonster();
        } else {
            selectedCell = getCurrentPlayer().getPlayerBoard().getACellOfBoardWithAddress(Zone.MONSTER_ZONE, 1);
            selectedCellAddress = 1;
            selectedCellZone = Zone.MONSTER_ZONE;
            currentPhase = Phase.BATTLE_PHASE;
            if (!getOpponentPlayer().getPlayerBoard().isMonsterZoneEmpty()) {
                MonsterZone opponentZone = getOpponentPlayer().getPlayerBoard().returnMonsterZone();
                for (int i = 1; i <= 5; i++) {
                    Cell cell = opponentZone.getCellWithAddress(i);
                    if (cell.getCellStatus() != CellStatus.EMPTY) {
                        attackToCard(i);
                        break;
                    }
                }
            } else {
                directAttack();
            }
        }
        currentPhase = Phase.MAIN_PHASE_2;
        nextPhase();
    }

    public void setWinnerCheat(String winnerNickName) {
        DuelPlayer winner;
        DuelPlayer loser;
        if (firstPlayer.getNickname().equals(winnerNickName)) {
            winner = firstPlayer;
            loser = secondPlayer;
        } else {
            winner = secondPlayer;
            loser = firstPlayer;
        }
        if (duelGameController.getDuel().getNumberOfRounds() == 3)
            duelGameController.updateScoreAndCoinForThreeRounds(winner, loser, 2);
        else duelGameController.updateScoreAndCoinForOneRound(winner, loser);
        finishGame(winner);
    }

    private void finishGame(DuelPlayer winner) {
        isFinishedGame = true;
        MenusManager.getInstance().changeMenu(Menu.MAIN_MENU);
        clear();
    }

    private void finishRound(DuelPlayer winner) {
        duelGameController.getDuel().finishRound();
        isFinishedRound = true;
        view.showSuccessMessageWithAString(SuccessMessage.ROUND_FINISHED, winner.getNickname());
        MenusManager.getInstance().changeMenu(Menu.BETWEEN_ROUNDS);
        DuelPlayer player1 = DuelGameController.getInstance().getDuel().getPlayer1();
        DuelPlayer player2 = DuelGameController.getInstance().getDuel().getPlayer2();
        player1.setPlayDeck(User.getActiveDeckByUsername(Objects.requireNonNull(User.getUserByNickName(player1.getNickname())).getUsername()));
        player2.setPlayDeck(User.getActiveDeckByUsername(Objects.requireNonNull(User.getUserByNickName(player2.getNickname())).getUsername()));

        if (player1.getNickname().equals("ai"))
            BetweenRoundView.getInstance().setPlayer1(player2, true);
        else if (player2.getNickname().equals("ai"))
            BetweenRoundView.getInstance().setPlayer1(player1, true);
        else {
            BetweenRoundView.getInstance().setPlayer1(player1, false);
            BetweenRoundView.getInstance().setPlayer2(player2);
        }

        DuelGameController.getInstance().setSpecifier(winner.getNickname());
        clear();
    }

    private void clear() {
        selectedCell = null;
        selectedCellZone = Zone.NONE;

        isSummonOrSetOfMonsterUsed = false;
        currentPhase = Phase.DRAW_PHASE;
        firstPlayerHand = new ArrayList<>();
        secondPlayerHand = new ArrayList<>();
        turn = 1; // 1 : firstPlayer, 2 : secondPlayer
        usedCellsToAttackNumbers = new ArrayList<>();
        changedPositionCards = new ArrayList<>();
        fieldZoneSpell = null;
        fieldEffectedCards = new ArrayList<>();
        fieldEffectedCardsAddress = new ArrayList<>();
        isFieldActivated = 0; // 0 : no - 1 : firstPlayed activated it- 2 : secondPlayer activated it
        opponentSelectedCell = null;
        firstPlayerHashmapForEquipSpells = new HashMap<>();
        secondPlayerHashmapForEquipSpells = new HashMap<>();
    }

    public boolean isFinishedRound() {
        return isFinishedRound;
    }

    public boolean isFinishedGame() {
        return isFinishedGame;
    }
}