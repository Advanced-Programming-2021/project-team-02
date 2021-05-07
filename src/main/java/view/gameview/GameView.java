package view.gameview;

import controller.playgame.RoundGameController;
import view.input.Input;
import view.input.Regex;
import view.messages.Error;
import view.messages.SuccessMessage;

import java.util.Map;
import java.util.regex.Matcher;

public class GameView {
    private static final GameView instance;

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
}
