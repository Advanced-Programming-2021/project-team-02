package controller;

import model.User;
import model.game.Duel;

import java.util.regex.Matcher;

public class DuelMenuController {
    private static final DuelMenuController instance;
    private User loggedInUser;
    private Duel duel;
    static {
        instance = new DuelMenuController();
    }

    public static DuelMenuController getInstance() {
        return instance;
    }



    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void startDuelWithOtherPlayer(Matcher matcher) {

    }
    public void startDuelWithAI(Matcher matcher){

    }
    private boolean isPlayerValidToStartDuel(String username){
        return true;
    }
}
