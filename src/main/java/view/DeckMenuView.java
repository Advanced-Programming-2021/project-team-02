package view;

import controller.DeckMenuController;
import view.input.Input;
import view.input.Regex;
import view.messages.Error;
import view.messages.SuccessMessage;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class DeckMenuView {
    private static DeckMenuView instance = null;
    private static final DeckMenuController controller = DeckMenuController.getInstance();
    private DeckMenuView() {

    }

    public static DeckMenuView getInstance() {
        if (instance == null)
            instance = new DeckMenuView();
        return instance;
    }

    public void run(String command) {
        commandRecognition(command);
    }

    public void commandRecognition(String command) {
        Matcher matcher;
        if ((matcher = Regex.getMatcher(Regex.DECK_CREATE, command)).matches()) {

        } else if ((matcher = Regex.getMatcher(Regex.DECK_DELETE, command)).matches()) {

        } else if ((matcher = Regex.getMatcher(Regex.DECK_SET_ACTIVATE, command)).matches()) {

        } else if ((matcher = Regex.getMatcherFromAllPermutations(Regex.DECK_ADD_CARD_TO_MAIN_DECK, command)) != null) {

        } else if ((matcher = Regex.getMatcherFromAllPermutations(Regex.DECK_ADD_CARD_TO_SIDE_DECK, command)) != null) {

        } else if ((matcher = Regex.getMatcherFromAllPermutations(Regex.DECK_REMOVE_CARD_MAIN_DECK, command)) != null) {

        } else if ((matcher = Regex.getMatcherFromAllPermutations(Regex.DECK_REMOVE_CARD_SIDE_DECK, command)) != null) {

        } else if ((matcher = Regex.getMatcher(Regex.DECK_SHOW_ALL_CARDS, command)).matches()) {

        } else if ((matcher = Regex.getMatcher(Regex.DECK_SHOW_MAIN_DECK, command)).matches()) {

        } else if ((matcher = Regex.getMatcherFromAllPermutations(Regex.DECK_SHOW_SIDE_DECK, command)) != null){

        } else if ((matcher = Regex.getMatcher(Regex.DECK_SHOW_ALL_CARDS,command)).matches()){

        } else {
            System.out.println("invalid command");
        }

    }

    public void showError(Error error) {
    }

    public void showSuccessMessage(SuccessMessage message) {

    }


}
