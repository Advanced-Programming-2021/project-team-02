package project.controller;

import project.controller.playgame.DuelGameController;
import project.model.User;
import project.model.game.Duel;
import project.view.DuelMenuView;
//import project.view.MenusManager;
import project.view.messages.Error;
import project.view.messages.StartDuelMessage;

import java.util.Objects;

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
                //TODO duel = new Duel(MenusManager.getInstance().getLoggedInUser().getUsername(), secondPlayerUsername, roundNumber, false);
                DuelGameController.getInstance().startDuel(duel);
            }
        }
    }

    public StartDuelMessage startDuelWithAI(int roundNumber)  {
        if (arePlayersDecksActive("ai")) {
            if (arePlayersDecksValid("ai")) {
                try {
                    duel = new Duel(MainMenuController.getInstance().getLoggedInUser().getUsername(), "ai", roundNumber, true);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                DuelGameController.getInstance().startDuel(duel);
            } else return StartDuelMessage.INVALID_DECK;
        } else return StartDuelMessage.INACTIVE_DECK;
        return StartDuelMessage.SUCCESS;
    }

    private boolean isPlayerValidToStartDuel(String username) {
        User user = User.getUserByUsername(username);
        return user != null;
    }

    public boolean areRoundsNumberValid(int roundsNumber) {
        return (roundsNumber == 3 || roundsNumber == 1);
    }

    public boolean arePlayersDecksActive(String secondPlayerUserName) {
        if (!MainMenuController.getInstance().getLoggedInUser().getHasActiveDeck()) {
            return false;
        }
        User user = Objects.requireNonNull(User.getUserByUsername(secondPlayerUserName));
        return user.getHasActiveDeck();
    }

    public boolean arePlayersDecksValid(String secondPlayerUsername) {
        if (!Objects.requireNonNull(User.getActiveDeckByUsername(MainMenuController.getInstance().getLoggedInUser().getUsername())).isValidDeck()) {
            //view.showDynamicErrorForInactiveDeck(Error.FORBIDDEN_DECK, MainMenuController.getInstance().getLoggedInUser().getUsername());
            return false;
        } else if (!Objects.requireNonNull(User.getActiveDeckByUsername(secondPlayerUsername)).isValidDeck()) {
            //view.showDynamicErrorForInactiveDeck(Error.FORBIDDEN_DECK, secondPlayerUsername);
            return false;
        }
        return true;
    }

}