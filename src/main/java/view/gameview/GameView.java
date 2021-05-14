package view.gameview;

import controller.playgame.RoundGameController;
import model.card.Card;
import model.card.Monster;
import model.card.Spell;
import model.card.Trap;
import model.card.informationofcards.CardType;
import model.game.board.GraveYard;
import view.input.Input;
import view.input.Regex;
import view.messages.Error;
import view.messages.SuccessMessage;

import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameView {
    private static final GameView instance;
    private final RoundGameController controller = RoundGameController.getInstance ();

    static {
        instance = new GameView();
    }

    public static GameView getInstance() {
        return instance;
    }

    public void run(String command) {
        commandRecognition(command);
    }

    public void commandRecognition(String command) {
        Matcher matcher;
        if ((matcher = Regex.getMatcher (Regex.BOARD_GAME_SELECT_MONSTER, command)).matches ())
            controller.selectCardInMonsterZone (matcher);
        else if ((Objects.requireNonNull (matcher = Regex.getMatcherFromAllPermutations (Regex.BOARD_GAME_SELECT_MONSTER_OPPONENT, command))).matches ())
            controller.selectOpponentCardMonsterZone (matcher);
        else if ((matcher = Regex.getMatcher (Regex.BOARD_GAME_SELECT_SPELL, command)).matches ())
            controller.selectCardInSpellZone (matcher);
        else if ((Objects.requireNonNull (matcher = Regex.getMatcherFromAllPermutations (Regex.BOARD_GAME_SELECT_SPELL_OPPONENT, command))).matches ())
            controller.selectOpponentCardSpellZone (matcher);
        else if (Regex.getMatcher (Regex.BOARD_GAME_SELECT_FIELD, command).matches ())
            controller.selectPlayerFieldCard ();
        else if (Objects.requireNonNull (Regex.getMatcherFromAllPermutations (Regex.BOARD_GAME_SELECT_FIELD_OPPONENT, command)).matches ())
            controller.selectOpponentFieldCard ();
        else if (Regex.getMatcher (Regex.BOARD_GAME_SELECT_DESELECT, command).matches ())
            controller.deselectCard (1);
        else if (Regex.getMatcher (Regex.BOARD_GAME_SELECT_GRAVEYARD, command).matches ())
            controller.selectPlayerGraveYard();
        else if (Objects.requireNonNull (Regex.getMatcherFromAllPermutations (Regex.BOARD_GAME_SELECT_GRAVEYARD_OPPONENT, command)).matches ())
            controller.selectOpponentGraveYard ();
        else if ((matcher = Regex.getMatcher (Regex.BOARD_GAME_SELECT_HAND, command)).matches ())
            controller.selectCardInHand (matcher);
        else if ((matcher = Regex.getMatcher (Regex.BOARD_GAME_SELECT_HAND, command)).matches ())
            controller.selectCardInHand (matcher);
        else if (Regex.getMatcher (Regex.BOARD_GAME_NEXT_PHASE, command).matches ())
            controller.nextPhase ();
        else if (Regex.getMatcher (Regex.BOARD_GAME_SUMMON, command).matches ())
            controller.summonMonster ();
        else if (Regex.getMatcher (Regex.GRAVEYARD_SHOW, command).matches ())
            instance.showGraveYard ();
        else if (Regex.getMatcher (Regex.CARD_SHOW_SELECTED, command).matches ())
            instance.showCard (controller.getSelectedCell ().getCardInCell ());
        else if (Regex.getMatcher (Regex.BOARD_GAME_SUMMON, command).matches ())
            controller.summonMonster ();
        else if (Regex.getMatcher (Regex.BOARD_GAME_SET_MONSTER, command).matches ())
            controller.setMonster ();
        else if (Regex.getMatcher (Regex.BOARD_GAME_SET_SPELL, command).matches () || Regex.getMatcher (Regex.BOARD_GAME_SET_TRAP, command).matches ())
            controller.setSpellOrTrap ();
        else if ((matcher = Regex.getMatcher (Regex.BOARD_GAME_SET_POSITION, command)).matches ())
            controller.changeMonsterPosition (matcher);
        else if (Regex.getMatcher (Regex.BOARD_GAME_FLIP_SUMMON, command).matches ())
            controller.flipSummon ();
        else if ((matcher = Regex.getMatcher (Regex.BOARD_GAME_ATTACK, command)).matches ())
            controller.attackToCard (matcher);
        else if (Regex.getMatcher (Regex.BOARD_GAME_ATTACK_DIRECT, command).matches ())
            controller.directAttack ();
        else if (Regex.getMatcher (Regex.BOARD_GAME_ACTIVATE_EFFECT, command).matches ())
            controller.activateEffectOfSpellOrTrap ();
//        else if (Regex.getMatcher (Regex.BOARD_GAME_SURRENDER, command).matches ())
//            controller.
//        else if ((matcher = Regex.getMatcher (Regex.CHEAT_INCREASE_LP, command)).matches ())
//            controller.
        else if (Regex.getMatcher (Regex.COMMAND_CANCEL, command).matches ())
            controller.cancel ();
    }

    public void showError(Error error) {
        System.out.println(error.getValue());
    }

    public void showDynamicError(Error error, Matcher matcher) {

    }

    public void showDynamicErrorWithAString(Error error, String string) {

    }

    public void showSuccessMessageWithTwoIntegerAndOneString(SuccessMessage successMessage, String winnerUserName, int winnerScore, int loserScore) {

    }

    public void showSuccessMessageWithTwoIntegerAndOneStringForSeveralWins(SuccessMessage successMessage, String winnerUserName, int winnerScore, int loserScore) {

    }

    public void showSuccessMessage(SuccessMessage message) {
        System.out.println(message.getValue());
    }

    public void showSuccessMessageWithAString(SuccessMessage message, String string) {
        if (message.equals (SuccessMessage.PLAYERS_TURN)) System.out.printf (SuccessMessage.PLAYERS_TURN.getValue (), string);
    }

    public void showSuccessMessageWithAnInteger(SuccessMessage message, int number) {

    }

    public void showBoard() {
        if (controller.getTurn () == 1) {
            System.out.println (controller.getSecondPlayer ().getNickname () + ":" + controller.getSecondPlayer ().getLifePoint ());
            if (controller.getSecondPlayerHand ().size () <= 6) {
                for (int i = 0; i < 6 - controller.getSecondPlayerHand ().size (); i++) System.out.print ("\t");
                for (int i = 0; i < controller.getSecondPlayerHand ().size (); i++) System.out.print ("c\t");
            } else for (int i = 0; i < controller.getSecondPlayerHand ().size (); i++) System.out.print ("c\t");
            System.out.print ("\n");
            controller.getSecondPlayer ().getPlayDeck ();
        } else {
            System.out.println (controller.getFirstPlayer ().getNickname () + ":" + controller.getFirstPlayer ().getLifePoint ());
        }
    }

    public void showPhase() {
        System.out.printf (SuccessMessage.PHASE_NAME.getValue (), controller.getCurrentPhase ());
    }

    public void showGraveYard() {
        int counter = 1;
        if (controller.getCurrentPlayer().getPlayerBoard().isGraveYardEmpty ()) instance.showError (Error.EMPTY_GRAVEYARD);
        else {
            for (Card card : controller.getCurrentPlayer ().getPlayerBoard ().returnGraveYard ().getGraveYardCards ()) {
                System.out.println (counter + ". " + card.getName () + ":" + card.getDescription ());
                counter++;
            }
        }
    }

    public void showCard(Card card) {
        assert card != null;
        if (card.getCardType().equals(CardType.MONSTER)) {
            Monster monster = (Monster) card;
            System.out.println(monster);
        } else if (card.getCardType().equals(CardType.SPELL)) {
            Spell spell = (Spell) card;
            System.out.println(spell);
        } else {
            Trap trap = (Trap) card;
            System.out.println(trap);
        }
    }

    public int getTributeAddress() {
        System.out.println("Enter number of monster to tribute Or cancel to finish the process:");
        String command;
        while (true) {
            command = Input.getInput();
            if (command.matches("[1-9]+")) {
                return Integer.parseInt(command);
            }else if (command.equals("cancel")){
                return -1;
            }else {
                System.out.println(Error.INVALID_COMMAND.getValue());
            }
        }
    }

    public Matcher getSummonOrderForRitual() {
        //here you should make him to write summon a ritual monster
        Matcher matcher;
        return null;
    }

    public Matcher getMonstersAddressesToBringRitual() {
        // here you should tell him to write addresses for summon ritual monster with (next line)
        // one integer and then space one integer and then space .... except the last integer
        Matcher matcher;
        return null;
    }

    public Matcher getPositionForSetRitualMonster() {
        // you should ask him about position of ritual summon (can be only OO or DO)
        Matcher matcher;
        return null;
    }

    public Matcher monsterReborn() {
        System.out.println("Enter message in format and then select card:" +
                "if you want from your grave yard :  card_name card_position(only DO or OO)" +
                "if you want from opponent grave yard :opponent card_name card_position(only DO or OO)");
        while (true) {
            String command = Input.getInput();
            Pattern pattern = Pattern.compile("( |opponent) ([A-Za-z ',-]+?) (DO|OO)");
            Matcher matcher = pattern.matcher(command);
            if (command.equals("cancel")) return null;
            else if (matcher.matches()) return matcher;
            else System.out.println(Error.INVALID_COMMAND);
        }
    }

    public String getCardNameForTerraForming() {
        System.out.println("please enter field_spell name and then select card");
        return Input.getInput();
    }

//    public Matcher getCardNameForChangeOfHeart() {
//        System.out.println("please enter monster name and position for capturing this turn in this format: and then select card" +
//                "card_name card_position(only OO or DO)");
//        while (true) {
//            String command = Input.getInput();
//            Pattern pattern = Pattern.compile("([A-Za-z ',-]+?) (DO|OO)");
//            Matcher matcher = pattern.matcher(command);
//            if (command.equals("cancel")) return null;
//            else if (matcher.matches()) return matcher;
//            else System.out.println(Error.INVALID_COMMAND);
//        }
//    }

//    public Matcher getCardsNameTwinTwister() {
//        System.out.println("enter names in this format :" +
//                "your_card_name opponent_card_name opponent_card_name");
//        while (true) {
//            String command = Input.getInput();
//            Pattern pattern = Pattern.compile("([A-Za-z ',-]+?) ([A-Za-z ',-]+?) ([A-Za-z ',-]+?)");
//            Matcher matcher = pattern.matcher(command);
//            if (command.equals("cancel")) return null;
//            if (matcher.matches()) return matcher;
//            else System.out.println(Error.INVALID_COMMAND);
//        }
//    }

//    public int mysticalSpaceTyphoon() {
//        System.out.println("please enter address of spell or trap and then select card");
//        while (true) {
//            String command = Input.getInput();
//            if (command.equals("cancel")) return -1;
//            else if (command.matches("([12345])")) return Integer.parseInt(command);
//            else System.out.println(Error.INVALID_COMMAND);
//        }
//    }

//    public String swordOfDarkDestruction() {
//        System.out.println("please enter card name (Card position should be OO or DO) and then select card:");
//        while (true) {
//            String command = Input.getInput();
//            if (command.equals("cancel")) return null;
//            else if (command.matches("([A-Za-z ',-]+?)")) return command;
//            else System.out.println(Error.INVALID_COMMAND);
//        }
//    }

    public String blackPendant() {
        System.out.println("please enter card name and then select card:");
        while (true) {
            String command = Input.getInput();
            if (command.equals("cancel")) return null;
            else if (command.matches("([A-Za-z ',-]+?)")) return command;
            else System.out.println(Error.INVALID_COMMAND);
        }
    }

//    public String unitedWeStand() {
//        System.out.println("please enter card name and then select card:");
//        while (true) {
//            String command = Input.getInput();
//            if (command.equals("cancel")) return null;
//            else if (command.matches("([A-Za-z ',-]+?)")) return command;
//            else System.out.println(Error.INVALID_COMMAND);
//        }
//    }

//    public String magnumShield() {
//        System.out.println("please enter card name and then select card:");
//        while (true) {
//            String command = Input.getInput();
//            if (command.equals("cancel")) return null;
//            else if (command.matches("([A-Za-z ',-]+?)")) return command;
//            else System.out.println(Error.INVALID_COMMAND);
//        }
//    }

//    public String quickSpellsSwitchCase() {
//        System.out.println("please enter card name in this format:" +
//                "card_name");;
//        String command;
//        while (true) {
//            command = Input.getInput();
//            if (command.equals("cancel")) return null;
//            else if (command.matches("([A-Za-z ',-]+?)")) return command;
//            else System.out.println(Error.INVALID_COMMAND);
//        }
//    }

    public int getAddressForTrapOrSpell() {
        System.out.println("enter address(number of cell between 1 to 5) of spell to be activated or write cancel if you dont want to activate anything");
        while (true) {
            String command = Input.getInput();
            if (command.equals("cancel")) {
                return -1;
            } else if (command.matches("[^-]\\d")) {
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

//    public String magicJammer() {
//        System.out.println("enter card name to remove from your hand");
//        while (true) {
//            String command = Input.getInput();
//            if (command.equals("cancel")) {
//                return null;
//            } else if (command.matches("([A-Za-z ',-]+?)")) {
//                return command;
//            } else System.out.println(Error.INVALID_COMMAND);
//        }
//    }

    public String askCardName() {
        return Input.getInput();
    }

    public int getNumberOfCardForCallOfTheHaunted() {
        System.out.println("enter number of the monster to be special summoned");
        while (true) {
            String command = Input.getInput();
            if (command.matches("\\d")) {
                return Integer.parseInt(command);
            } else System.out.println(Error.INVALID_COMMAND.getValue());
        }
    }
    public int howToSummonBeastKingBarbos(){
        System.out.println("how do you want to summon this card : " +
                "1-normal tribute" +
                ", 2-without tribute, 3-with 3 tributes\n" +
                "enter the numebr please");
        while (true){
            String input = Input.getInput();
            if (input.matches("[1-3]")){
                return Integer.parseInt(input);
            } else {
                System.out.println(Error.INVALID_COMMAND.getValue());
            }
        }
    }
    public int chooseCardInHand(){
        System.out.println("Enter address(number of it in your hand) of card to be set");
        String input;
        while (true){
            input = Input.getInput();
            if (input.matches("[1-9]+")){
                return Integer.parseInt(input);
            } else System.out.println(Error.INVALID_COMMAND);
        }
    }
}
