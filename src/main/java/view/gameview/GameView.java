package view.gameview;

import controller.playgame.RoundGameController;
import model.card.Card;
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
            controller.selectPlayerGraveYard ();
        else if (Objects.requireNonNull (Regex.getMatcherFromAllPermutations (Regex.BOARD_GAME_SELECT_GRAVEYARD_OPPONENT, command)).matches ())
            controller.selectOpponentGraveYard ();
        else if ((matcher = Regex.getMatcher (Regex.BOARD_GAME_SELECT_HAND, command)).matches ())
            controller.selectCardInHand (matcher);
        else if ((matcher = Regex.getMatcher (Regex.BOARD_GAME_SELECT_HAND, command)).matches ())
            controller.selectCardInHand (matcher);

    }

    public void showError(Error error) {

    }

    public void showDynamicError(Error error, Matcher matcher) {

    }

    public void showDynamicErrorWithAString(Error error, String string) {

    }

    public void showSuccessMessage(SuccessMessage message) {

    }

    public void showSuccessMessageWithAString(SuccessMessage message, String string) {

    }

    public void showSuccessMessageWithAnInteger(SuccessMessage message, int number) {

    }

    public void showBoard() {

    }

    public void showPhase() {

    }

    public void showGraveYard() {

    }

    public void showCard() {

    }

    public int getTributeAddress() {
        System.out.println("Enter 2 numbers of monsters to tribute in 2 lines:");
        String command;
        while (true) {
            command = Input.getInput();
            if (command.matches("\\d")) {
                return Integer.parseInt(command);
            } else {
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
        System.out.println("Enter message in format :" +
                "if you want from your grave yard :  card_name" +
                "if you want from opponent grave yard :opponent card_name");
        while (true) {
            String command = Input.getInput();
            Pattern pattern = Pattern.compile("( |opponent) ([A-Za-z ',-]+?)");
            Matcher matcher = pattern.matcher(command);
            if (command.equals("cancel")) return null;
            else if (matcher.matches()) return matcher;
            else System.out.println(Error.INVALID_COMMAND);
        }
    }

    public String getCardNameForTerraForming() {
        System.out.println("please enter field_spell name");
        return Input.getInput();
    }

    public String getCardNameForChangeOfHeart() {
        System.out.println("please enter monster name for capturing this turn: ");
        return Input.getInput();
    }

    public Matcher getCardsNameTwinTwister() {
        System.out.println("enter names in this format :" +
                "your_card_name opponent_card_name opponent_card_name");
        while (true) {
            String command = Input.getInput();
            Pattern pattern = Pattern.compile("([A-Za-z ',-]+?) ([A-Za-z ',-]+?) ([A-Za-z ',-]+?)");
            Matcher matcher = pattern.matcher(command);
            if (command.equals("cancel")) return null;
            if (matcher.matches()) return matcher;
            else System.out.println(Error.INVALID_COMMAND);
        }
    }

    public int mysticalSpaceTyphoon() {
        System.out.println("please enter address of spell or trap");
        while (true) {
            String command = Input.getInput();
            if (command.equals("cancel")) return -1;
            else if (command.matches("([12345])")) return Integer.parseInt(command);
            else System.out.println(Error.INVALID_COMMAND);
        }
    }

    public String swordOfDarkDestruction() {
        System.out.println("please enter card name :");
        while (true) {
            String command = Input.getInput();
            if (command.equals("cancel")) return null;
            else if (command.matches("([A-Za-z ',-]+?)")) return command;
            else System.out.println(Error.INVALID_COMMAND);
        }
    }

    public String blackPendant() {
        System.out.println("please enter card name :");
        while (true) {
            String command = Input.getInput();
            if (command.equals("cancel")) return null;
            else if (command.matches("([A-Za-z ',-]+?)")) return command;
            else System.out.println(Error.INVALID_COMMAND);
        }
    }

    public String unitedWeStand() {
        System.out.println("please enter card name :");
        while (true) {
            String command = Input.getInput();
            if (command.equals("cancel")) return null;
            else if (command.matches("([A-Za-z ',-]+?)")) return command;
            else System.out.println(Error.INVALID_COMMAND);
        }
    }

    public String magnumShield() {
        System.out.println("please enter card name :");
        while (true) {
            String command = Input.getInput();
            if (command.equals("cancel")) return null;
            else if (command.matches("([A-Za-z ',-]+?)")) return command;
            else System.out.println(Error.INVALID_COMMAND);
        }
    }

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

    public String magicJammer() {
        System.out.println("enter card name to remove from your hand");
        while (true) {
            String command = Input.getInput();
            if (command.equals("cancel")) {
                return null;
            } else if (command.matches("([A-Za-z ',-]+?)")) {
                return command;
            } else System.out.println(Error.INVALID_COMMAND);
        }
    }

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
}
