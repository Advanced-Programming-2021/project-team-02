package controller;

import model.User;
import model.game.Duel;
import view.DuelMenuView;
import view.ProfileMenuView;
import view.messages.Error;

import java.util.regex.Matcher;

public class DuelMenuController {
    private static final DuelMenuController instance;
    private final DuelMenuView view = DuelMenuView.getInstance ();
    private User loggedInUser;
    private Duel duel;

    static {
        instance = new DuelMenuController ();
    }

    public static DuelMenuController getInstance() {
        return instance;
    }


    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void startDuelWithOtherPlayer(Matcher matcher) {
        String secondPlayerUsername = matcher.group ("secondPlayerUsername");
        int numberOfRounds = Integer.parseInt (matcher.group ("roundNumber"));
        if (!checkExistenceOfSecondPlayer (secondPlayerUsername)) {
            view.showError (Error.PLAYER_DOES_NOT_EXIST);
            return;
        }
        if (!instance.loggedInUser.getHasActiveDeck ()) {
            view.showDynamicErrorForInactiveDeck (Error.INACTIVATED_DECK, instance.loggedInUser.getUsername ());
            return;
        }
        if (!User.getUserByUsername (secondPlayerUsername).getHasActiveDeck ()) {
            view.showDynamicErrorForInactiveDeck (Error.INACTIVATED_DECK, secondPlayerUsername);
            return;
        }
        if (numberOfRounds != 1 && numberOfRounds != 3) view.showError (Error.WRONG_ROUNDS_NUMBER);
    }

    public void startDuelWithAI(Matcher matcher) {

    }

    private boolean isPlayerValidToStartDuel(String username) {
        return true;
    }

    public boolean checkExistenceOfSecondPlayer(String username) {
        return User.getUserByUsername(username) != null;
    }
}