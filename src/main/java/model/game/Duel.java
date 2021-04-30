package model.game;

import model.User;

public class Duel {
    private int numberOfRounds;
    private DuelPlayer player1;
    private DuelPlayer player2;
    private int[] lifePointsPlayer1;
    private int[] lifePointsPlayer2;

    public Duel(String username1, String username2, int numberOfRounds) {
        User user1 = User.getUserByUsername(username1);
        User user2 = User.getUserByUsername(username2);

        this.numberOfRounds = numberOfRounds;
        lifePointsPlayer1 = new int[numberOfRounds];
        lifePointsPlayer2 = new int[numberOfRounds];
    }
    public void saveLifePoints(int lifePointPlayer1,int lifePointPlayer2){

    }
}