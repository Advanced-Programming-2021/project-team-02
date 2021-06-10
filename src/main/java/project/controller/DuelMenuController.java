package project.controller;

import project.controller.playgame.DuelGameController;
import project.model.User;
import project.model.game.Duel;
import project.view.DuelMenuView;
import project.view.messages.Error;

public class DuelMenuController {
    private static DuelMenuController instance = null;
    private Duel duel;

    private DuelMenuController() {
    }

    public static DuelMenuController getInstance() {
        if (instance == null)
            instance = new DuelMenuController();
        return instance;
    }


    public void startDuelWithOtherPlayer(String secondPlayerUsername, int roundNumber) throws CloneNotSupportedException {
        if (!isPlayerValidToStartDuel(secondPlayerUsername)) {
            Error.showError(Error.PLAYER_DOES_NOT_EXIST);
        } else if (!areRoundsNumberValid(roundNumber)) {
            Error.showError(Error.WRONG_ROUNDS_NUMBER);
        } else if (arePlayersDecksActive(secondPlayerUsername)) {
            if (arePlayersDecksValid(secondPlayerUsername)) {
//                duel = new Duel(MenusManager.getInstance().getLoggedInUser().getUsername(), secondPlayerUsername, roundNumber);
                DuelGameController.getInstance().startDuel(duel);
            }
        }
    }

    public void startDuelWithAI(int roundNumber) {
        if (!areRoundsNumberValid(roundNumber))
            Error.showError(Error.WRONG_ROUNDS_NUMBER);
    }

    private boolean isPlayerValidToStartDuel(String username) {
        User user = User.getUserByUsername(username);
        return user != null;
    }

    public boolean areRoundsNumberValid(int roundsNumber) {
        return (roundsNumber == 3 || roundsNumber == 1);
    }

    public boolean arePlayersDecksActive(String secondPlayerUserName) {
//        if (!MenusManager.getInstance().getLoggedInUser().getHasActiveDeck()) {
//            project.view.showDynamicErrorForInactiveDeck(Error.INACTIVATED_DECK, MenusManager.getInstance().getLoggedInUser().getUsername());
            return false;
        }
//        User user = Objects.requireNonNull(User.getUserByUsername(secondPlayerUserName));
//        if (!user.getHasActiveDeck()) {
//            project.view.showDynamicErrorForInactiveDeck(Error.INACTIVATED_DECK, secondPlayerUserName);
//            return false;
//        }
//        return true;
//    }

    public boolean arePlayersDecksValid(String secondPlayerUsername) {
//        if (!Objects.requireNonNull(User.getActiveDeck(MenusManager.getInstance().getLoggedInUser().getUsername())).isValidDeck()) {
//            project.view.showDynamicErrorForInactiveDeck(Error.FORBIDDEN_DECK, MenusManager.getInstance().getLoggedInUser().getUsername());
//            return false;
//        } else if (!Objects.requireNonNull(User.getActiveDeck(secondPlayerUsername)).isValidDeck()) {
//            project.view.showDynamicErrorForInactiveDeck(Error.FORBIDDEN_DECK, secondPlayerUsername);
//            return false;
//        }
        return true;
    }

}