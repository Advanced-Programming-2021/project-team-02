package view.gameview;

import controller.playgame.RoundGameController;
import model.Deck;
import model.card.Card;
import model.card.Monster;
import model.card.informationofcards.CardType;
import model.game.board.Cell;
import model.game.board.CellStatus;
import view.DeckMenuView;
import view.input.Input;
import view.input.Regex;
import view.messages.Error;
import view.messages.SuccessMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameView {
    private static GameView instance = null;
    private final RoundGameController controller = RoundGameController.getInstance();

    private GameView() {
    }

    public static GameView getInstance() {
        if (instance == null)
            instance = new GameView();
        return instance;
    }

    public void run(String command) {
        commandRecognition(command);
    }

    public void commandRecognition(String command) {
        Matcher matcher;
        if ((matcher = Regex.getMatcher(Regex.BOARD_GAME_SELECT_MONSTER, command)).matches())
            controller.selectCardInMonsterZone(matcher);
        else if ((matcher = Regex.getMatcherFromAllPermutations(Regex.BOARD_GAME_SELECT_MONSTER_OPPONENT, command)) != null)
            controller.selectOpponentCardMonsterZone(matcher);
        else if ((matcher = Regex.getMatcher(Regex.BOARD_GAME_SELECT_SPELL, command)).matches())
            controller.selectCardInSpellZone(matcher);
        else if ((matcher = Regex.getMatcherFromAllPermutations(Regex.BOARD_GAME_SELECT_SPELL_OPPONENT, command)) != null)
            controller.selectOpponentCardSpellZone(matcher);
        else if (Regex.getMatcher(Regex.BOARD_GAME_SELECT_FIELD, command).matches())
            controller.selectPlayerFieldCard();
        else if ((matcher = Regex.getMatcherFromAllPermutations(Regex.BOARD_GAME_SELECT_FIELD_OPPONENT, command)) != null)
            controller.selectOpponentFieldCard();
        else if (Regex.getMatcher(Regex.BOARD_GAME_SELECT_DESELECT, command).matches())
            controller.deselectCard(1);
        else if ((matcher = Regex.getMatcher(Regex.BOARD_GAME_SELECT_HAND, command)).matches())
            controller.selectCardInHand(matcher);
        else if ((matcher = Regex.getMatcher(Regex.BOARD_GAME_SELECT_HAND, command)).matches())
            controller.selectCardInHand(matcher);
        else if (Regex.getMatcher(Regex.BOARD_GAME_NEXT_PHASE, command).matches())
            controller.nextPhase();
        else if (Regex.getMatcher(Regex.BOARD_GAME_SUMMON, command).matches())
            controller.summonMonster();
        else if (Regex.getMatcher(Regex.GRAVEYARD_SHOW, command).matches())
            instance.showCurrentGraveYard();
        else if (Regex.getMatcher(Regex.CARD_SHOW_SELECTED, command).matches())
            DeckMenuView.getInstance().checkTypeOfCardAndPrintIt(controller.getSelectedCell().getCardInCell());
        else if (Regex.getMatcher(Regex.BOARD_GAME_SUMMON, command).matches())
            controller.summonMonster();
        else if (Regex.getMatcher(Regex.BOARD_GAME_SET_MONSTER, command).matches())
            controller.setMonster();
        else if (Regex.getMatcher(Regex.BOARD_GAME_SET_SPELL, command).matches() || Regex.getMatcher(Regex.BOARD_GAME_SET_TRAP, command).matches())
            controller.setSpellOrTrap();
        else if ((matcher = Regex.getMatcher(Regex.BOARD_GAME_SET_POSITION, command)).matches())
            controller.changeMonsterPosition(matcher);
        else if (Regex.getMatcher(Regex.BOARD_GAME_FLIP_SUMMON, command).matches())
            controller.flipSummon();
        else if ((matcher = Regex.getMatcher(Regex.BOARD_GAME_ATTACK, command)).matches())
            controller.attackToCard(matcher);
        else if (Regex.getMatcher(Regex.BOARD_GAME_ATTACK_DIRECT, command).matches())
            controller.directAttack();
        else if (Regex.getMatcher(Regex.BOARD_GAME_ACTIVATE_EFFECT, command).matches())
            controller.activateEffectOfSpellOrTrap();
        else if ((matcher = Regex.getMatcher(Regex.CHEAT_INCREASE_LP, command)).matches())
            controller.getCurrentPlayer().increaseLP(Integer.parseInt(matcher.group("LPAmount")));
        else if (Regex.getMatcher(Regex.COMMAND_CANCEL, command).matches())
            controller.cancel();
        else if (Regex.getMatcher(Regex.BOARD_GAME_SURRENDER, command).matches())
            controller.surrender();
        else if (Regex.getMatcher(Regex.BOARD_GAME_SHOW_HAND, command).matches()) {
            showHand();
            return;
        } else if (Regex.getMatcher(Regex.BOARD_GAME_SHOW_BOARD, command).matches()) {
            showBoard();
            return;
        } else if (Regex.getMatcher(Regex.COMMAND_HELP, command).matches()) {
            help();
            return;
        } else {
            Error.showError(Error.INVALID_COMMAND);
            return;
        }
        showBoard();
    }

    public void showError(Error error) {
        System.out.println(error.getValue());
    }

    public void showSuccessMessageWithTwoIntegerAndOneString(SuccessMessage successMessage, String winnerUserName, int winnerScore, int loserScore) {
        if (successMessage.equals(SuccessMessage.SURRENDER_MESSAGE))
            System.out.printf(SuccessMessage.SURRENDER_MESSAGE.getValue(), winnerUserName, winnerScore, loserScore);
    }

    public void showSuccessMessageWithTwoIntegerAndOneStringForSeveralWins(SuccessMessage successMessage, String winnerUserName, int winnerScore, int loserScore) {
        if (successMessage.equals(SuccessMessage.SURRENDER_MESSAGE_FOR_HOLE_MATCH))
            System.out.printf(SuccessMessage.SURRENDER_MESSAGE_FOR_HOLE_MATCH.getValue(), winnerUserName, winnerScore, loserScore);
    }

    public void showSuccessMessage(SuccessMessage message) {
        System.out.println(message.getValue());
    }

    public void showSuccessMessageWithAString(SuccessMessage message, String string) {
        if (message.equals(SuccessMessage.PLAYERS_TURN))
            System.out.printf(SuccessMessage.PLAYERS_TURN.getValue(), string);
        else if (message.equals(SuccessMessage.CARD_ADDED_TO_THE_HAND))
            System.out.printf(SuccessMessage.CARD_ADDED_TO_THE_HAND.getValue(), string);
        else if (message.equals(SuccessMessage.SHOW_TURN_WHEN_OPPONENT_WANTS_ACTIVE_TRAP_OR_SPELL_OR_MONSTER))
            System.out.printf(SuccessMessage.SHOW_TURN_WHEN_OPPONENT_WANTS_ACTIVE_TRAP_OR_SPELL_OR_MONSTER.getValue(), string);
        else if (message.equals(SuccessMessage.DH_CARD_BECOMES_DO))
            System.out.printf(SuccessMessage.DH_CARD_BECOMES_DO.getValue(), string);
        else if (message.equals(SuccessMessage.GAME_FINISHED))
            System.out.printf(SuccessMessage.GAME_FINISHED.getValue(), string);
        else if (message.equals(SuccessMessage.ROUND_FINISHED))
            System.out.printf(SuccessMessage.ROUND_FINISHED.getValue(), string);
    }

    public void showSuccessMessageWithAnInteger(SuccessMessage message, int number) {
        if (message.equals(SuccessMessage.OPPONENT_RECEIVE_DAMAGE_AFTER_ATTACK))
            System.out.printf(SuccessMessage.OPPONENT_RECEIVE_DAMAGE_AFTER_ATTACK.getValue(), number);
        else if (message.equals(SuccessMessage.DAMAGE_TO_CURRENT_PLAYER_AFTER_ATTACK_TI_HIGHER_DEFENSIVE_DO_OR_DH_MONSTER))
            System.out.printf(SuccessMessage.DAMAGE_TO_CURRENT_PLAYER_AFTER_ATTACK_TI_HIGHER_DEFENSIVE_DO_OR_DH_MONSTER.getValue(), number);
        else if (message.equals(SuccessMessage.CURRENT_PLAYER_RECEIVE_DAMAGE_AFTER_ATTACK))
            System.out.printf(SuccessMessage.CURRENT_PLAYER_RECEIVE_DAMAGE_AFTER_ATTACK.getValue(), number);
        else if (message.equals(SuccessMessage.OPPONENT_RECEIVE_DAMAGE_AFTER_DIRECT_ATTACK))
            System.out.printf(SuccessMessage.OPPONENT_RECEIVE_DAMAGE_AFTER_DIRECT_ATTACK.getValue(), number);
    }

    public void showBoard() {
        if (controller.getCurrentPlayer().equals(controller.getFirstPlayer())) {
            System.out.println(controller.getSecondPlayer().getNickname() + ":" + controller.getSecondPlayer().getLifePoint());
            if (controller.getSecondPlayerHand().size() <= 6) {
                for (int i = 0; i < 6 - controller.getSecondPlayerHand().size(); i++) System.out.print("\t");
                for (int i = 0; i < controller.getSecondPlayerHand().size(); i++) System.out.print("c\t");
            } else for (int i = 0; i < controller.getSecondPlayerHand().size(); i++) System.out.print("c\t");
            System.out.print("\n");
            System.out.println(controller.getSecondPlayer().getPlayDeck().getMainCards().size());
            System.out.println(
                    "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(4)) +
                            "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(2)) +
                            "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(1)) +
                            "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(3)) +
                            "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(5)));
            System.out.println(
                    "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(4)) +
                            "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(2)) +
                            "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(1)) +
                            "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(3)) +
                            "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(5)));
            System.out.println(controller.getSecondPlayer().getPlayerBoard().returnGraveYard().getGraveYardCards().size()
                    + "\t\t\t\t\t\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().getFieldZone().getFieldCell()));
            System.out.println("\n--------------------------\n");
            System.out.println(getStatusForCell(controller.getFirstPlayer().getPlayerBoard().getFieldZone().getFieldCell())
                    + "\t\t\t\t\t\t" + controller.getFirstPlayer().getPlayerBoard().returnGraveYard().getGraveYardCards().size());
            System.out.println(
                    "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(5)) +
                            "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(3)) +
                            "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(1)) +
                            "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(2)) +
                            "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(4)));
            System.out.println(
                    "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(5)) +
                            "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(3)) +
                            "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(1)) +
                            "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(2)) +
                            "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(4)));
            System.out.println("\t\t\t\t\t\t" + controller.getFirstPlayer().getPlayDeck().getMainCards().size());
            for (int i = 0; i < controller.getFirstPlayerHand().size(); i++) System.out.print("c\t");
            System.out.print("\n");
            System.out.println(controller.getFirstPlayer().getNickname() + ":" + controller.getFirstPlayer().getLifePoint());
        } else {
            System.out.println(controller.getFirstPlayer().getNickname() + ":" + controller.getFirstPlayer().getLifePoint());
            if (controller.getFirstPlayerHand().size() <= 6) {
                for (int i = 0; i < 6 - controller.getFirstPlayerHand().size(); i++) System.out.print("\t");
                for (int i = 0; i < controller.getFirstPlayerHand().size(); i++) System.out.print("c\t");
            } else for (int i = 0; i < controller.getFirstPlayerHand().size(); i++) System.out.print("c\t");
            System.out.print("\n");
            System.out.println(controller.getFirstPlayer().getPlayDeck().getMainCards().size());
            System.out.println(
                    "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(4)) +
                            "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(2)) +
                            "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(1)) +
                            "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(3)) +
                            "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(5)));
            System.out.println(
                    "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(4)) +
                            "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(2)) +
                            "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(1)) +
                            "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(3)) +
                            "\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(5)));
            System.out.println(controller.getFirstPlayer().getPlayerBoard().returnGraveYard().getGraveYardCards().size()
                    + "\t\t\t\t\t\t" + getStatusForCell(controller.getFirstPlayer().getPlayerBoard().getFieldZone().getFieldCell()));
            System.out.println("\n--------------------------\n");
            System.out.println(getStatusForCell(controller.getSecondPlayer().getPlayerBoard().getFieldZone().getFieldCell())
                    + "\t\t\t\t\t\t" + controller.getSecondPlayer().getPlayerBoard().returnGraveYard().getGraveYardCards().size());
            System.out.println(
                    "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(5)) +
                            "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(3)) +
                            "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(1)) +
                            "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(2)) +
                            "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnMonsterZone().getCellWithAddress(4)));
            System.out.println(
                    "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(5)) +
                            "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(3)) +
                            "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(1)) +
                            "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(2)) +
                            "\t" + getStatusForCell(controller.getSecondPlayer().getPlayerBoard().returnSpellZone().getCellWithAddress(4)));
            System.out.println("\t\t\t\t\t\t" + controller.getSecondPlayer().getPlayDeck().getMainCards().size());
            for (int i = 0; i < controller.getSecondPlayerHand().size(); i++) System.out.print("c\t");
            System.out.println();//added by erfan based on what mahdis said
            System.out.println(controller.getSecondPlayer().getNickname() + ":" + controller.getSecondPlayer().getLifePoint());
        }
    }

    public String getStatusForCell(Cell cell) {
        if (cell.getCellStatus().getLabel().equals("E")) return "E";
        if (cell.getCellStatus().getLabel().equals("O")) return "O";
        if (cell.getCellStatus().getLabel().equals("H")) return "H";
        if (cell.getCellStatus().getLabel().equals("DH")) return "DH";
        if (cell.getCellStatus().getLabel().equals("DO")) return "DO";
        if (cell.getCellStatus().getLabel().equals("OH")) return "OH";
        if (cell.getCellStatus().getLabel().equals("OO")) return "OO";
        if (cell.getCellStatus().getLabel().equals("HAND")) return "HAND";
        return "";
    }

    public void showHand() {
        controller.getCurrentPlayerHand().stream().map(card -> card.getName() + " (" + card.getCardType().name() + ")").forEach(System.out::println);
    }

    public void showPhase() {
        System.out.printf(SuccessMessage.PHASE_NAME.getValue(), controller.getCurrentPhase());
    }

    public void showCurrentGraveYard() {
        int counter = 1;
        if (controller.getCurrentPlayer().getPlayerBoard().isGraveYardEmpty())
            instance.showError(Error.EMPTY_GRAVEYARD);
        else {
            for (Card card : controller.getCurrentPlayer().getPlayerBoard().returnGraveYard().getGraveYardCards()) {
                System.out.print(counter + ". " + card.getName() + ":" + card.getDescription());
                if (card.getCardType() == CardType.MONSTER) {
                    System.out.println(" Powers : " + ((Monster) card).getAttackPower() + "," + ((Monster) card).getDefensePower());
                }
                System.out.println();
                counter++;
            }
        }
    }

    public int getTributeAddress() {
        System.out.println("Enter number(address) of monster to tribute Or cancel to finish the process:");
        String command;
        while (true) {
            command = Input.getInput();
            if (command.matches("[1-9]+")) {
                return Integer.parseInt(command);
            } else if (command.equals("cancel")) {
                return -1;
            } else {
                System.out.println(Error.INVALID_COMMAND.getValue());
            }
        }
    }

    public boolean getSummonOrderForRitual() {
        System.out.println("you should right summon order:");
        String input;
        while (true) {
            input = Input.getInput();
            if (input.matches("summon")) return true;
            else System.out.println(Error.INVALID_COMMAND);
        }
    }

    public String[] getMonstersAddressesToBringRitual() {
        System.out.println("write card addresses in this format:\n" +
                "num1-num2-...\n" +
                "(for example : 1-3-5)");
        String input;
        String[] split;
        while (true) {
            input = Input.getInput();
            if (input.matches("([12345-]+)")) {
                split = input.split("-");
                return split;
            } else System.out.println(Error.INVALID_COMMAND);
        }
    }

    public CellStatus getPositionForSetRitualMonster() {
        System.out.println("please enter position of ritual summon in this format\n" +
                "attack\n" +
                "or\n" +
                "defense");
        String input;
        while (true) {
            input = Input.getInput();
            if (input.equals("attack") || input.equals("defense")) {
                if (input.equals("attack")) {
                    return CellStatus.OFFENSIVE_OCCUPIED;
                } else return CellStatus.DEFENSIVE_OCCUPIED;
            } else System.out.println(Error.INVALID_COMMAND);
        }
    }

    public int swordOfDarkDestruction() {
        System.out.println("please enter card address in monsterZone to be equipped");
        while (true) {
            String command = Input.getInput();
            if (command.equals("cancel")) return -1;
            else if (command.matches("[1-9]+")) return Integer.parseInt(command);
            else System.out.println(Error.INVALID_COMMAND);
        }
    }

    public int blackPendant() {
        System.out.println("please enter card address in monsterZone to be equipped");
        while (true) {
            String command = Input.getInput();
            if (command.matches("[1-9]+")) return Integer.parseInt(command);
            else System.out.println(Error.INVALID_COMMAND);
        }
    }

    public int getAddressForTrapOrSpell() {
        System.out.println("enter address(number of cell between 1 to 5) of spell to be activated or write cancel if you dont want to activate anything");
        while (true) {
            String command = Input.getInput();
            if (command.equals("cancel")) {
                return -1;
            } else if (command.matches("[1-9]+")) {
                return Integer.parseInt(command);
            } else System.out.println(Error.INVALID_COMMAND);
        }
    }

    public boolean yesNoQuestion(String question) {
        System.out.println(question);
        if (Input.getInput().equals("yes")) {
            return true;
        }
        return false;
    }

    public int chooseCardInHand(String toShow) {
        System.out.println(toShow);
        return askAddress();
    }

    public int howToSummonBeastKingBarbos() {
        System.out.println("how do you want to summon/set this card : " +
                "1-normal tribute" +
                ", 2-without tribute, 3-with 3 tributes\n" +
                "enter the number please");
        while (true) {
            String input = Input.getInput();
            if (input.matches("[1-3]")) {
                return Integer.parseInt(input);
            } else {
                System.out.println(Error.INVALID_COMMAND.getValue());
            }
        }
    }

    public String ritualCardName() {
        System.out.println("please enter card address (number) or -1 to cancel");
        while (true) {
            String input = Input.getInput();
            if (input.matches("[0-9-]+")) return input;
            else System.out.println(Error.INVALID_COMMAND);
        }
    }

    public int askAddressForManEaterBug() {
        System.out.println("Enter address(number of it in your opponent Monster Zone) of card to be killed");
        return askAddress();
    }

    private int askAddress() {
        String input;
        while (true) {
            input = Input.getInput();
            if (input.matches("[1-9]+")) {
                return Integer.parseInt(input);
            } else if (input.equals("cancel")) {
                return -1;
            } else System.out.println(Error.INVALID_COMMAND);
        }
    }

    public int chooseCardInDeck(Deck deck) {
        System.out.println("Enter number of field spell to be added to you deck or cancel to cancel the command!");
        System.out.println(deck);
        while (true) {
            String input = Input.getInput();
            if (input.matches("[1-9]+")) {
                return Integer.parseInt(input);
            } else if (input.equals("cancel")) {
                return -1;
            } else System.out.println(Error.INVALID_COMMAND);
        }
    }

    public int chooseCardInGraveYard() {
        System.out.println("choose the card, enter the number of it:");
        while (true) {
            String input = Input.getInput();
            if (input.matches("[1-9]+")) {
                return Integer.parseInt(input);
            } else if (input.equals("cancel")) {
                return -1;
            } else System.out.println(Error.INVALID_COMMAND);
        }
    }

    public int twoChoiceQuestions(String message, String choice1, String choice2) {
        System.out.println(message);
        System.out.println("choose one : 1-" + choice1 + ", 2-" + choice2);
        while (true) {
            String string = Input.getInput();
            if (string.equals("cancel")) {
                return -1;
            } else if (string.matches("[1-9]+")) {
                if (string.equals("1") || string.equals("2")) {
                    return Integer.parseInt(string);
                } else showError(Error.INVALID_NUMBER);
            } else showError(Error.INVALID_COMMAND);
        }
    }

    public void showOpponentGraveYard() {
        int counter = 1;
        if (controller.getOpponentPlayer().getPlayerBoard().isGraveYardEmpty())
            instance.showError(Error.EMPTY_GRAVEYARD);
        else {
            for (Card card : controller.getCurrentPlayer().getPlayerBoard().returnGraveYard().getGraveYardCards()) {
                System.out.println(counter + ". " + card.getName() + ":" + card.getDescription());
                counter++;
            }
        }
    }

    public void help() {
        System.out.println("show board\n" +
                "show hand\n" +
                "select --monster <monsterZoneNumber>\n" +
                "select --monster <monsterZoneNumber> --opponent\n" +
                "select -m <monsterZoneNumber> -o\n" +
                "select --spell <spellZoneNumber>\n" +
                "select --spell <spellZoneNumber> --opponent\n" +
                "select -s <spellZoneNumber> -o\n" +
                "select --field\n" +
                "select --field --opponent\n" +
                "select -f -o\n" +
                "select --graveyard\n" +
                "select --graveyard --opponent\n" +
                "select -gy -o\n" +
                "select --hand <cardNumber>\n" +
                "select -d\n" +
                "next phase\n" +
                "summon\n" +
                "set --monster\n" +
                "set --spell\n" +
                "set --trap\n" +
                "set --position <attack|defense>\n" +
                "flip-summon\n" +
                "attack <monsterZoneNumber>\n" +
                "attack direct\n" +
                "activate effect\n" +
                "surrender\n" +
                "show graveyard\n" +
                "card show --selected\n" +
                "cancel\n" +
                "help");
    }
}
