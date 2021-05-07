package view.gameview;

import controller.playgame.RoundGameController;
import view.input.Input;
import view.messages.Error;
import view.messages.SuccessMessage;

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

    public Matcher getTributeAddress() {
        //here you should get an address and check it if it is for monster address, else you show invalid command and take input again
        Matcher matcher;
        return null;
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

    public boolean yesNoQuestion() {
        if (Input.getInput().equals("yes")) {
            return true;
        }
        return false;
    }
}
