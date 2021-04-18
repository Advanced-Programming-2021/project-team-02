package controller.playgame;

import model.game.Duel;



public class DuelGameController {
    private static final DuelGameController instance;
    private Duel duel;
    static {
        instance = new DuelGameController();
    }

    public static DuelGameController getInstance() {
        return instance;
    }

    public void startDuel(Duel duel) {
        this.duel = duel;
        starterSpecifier();

    }

    public void starterSpecifier() {

    }
    public void checkGameResult(){

    }
    public void updateScores(){

    }
}
