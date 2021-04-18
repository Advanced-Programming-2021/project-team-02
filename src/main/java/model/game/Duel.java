package model.game;

public class Duel {
    private int numberOfRounds;
    private DuelPlayer player1;
    private DuelPlayer player2;
    private int[] lifePointsPlayer1;
    private int[] lifePointsPlayer2;

    public Duel(String nicknamePlayer1, String nickNamePlayer2, int numberOfRounds) {
        player1 = new DuelPlayer(nicknamePlayer1);
        player2 = new DuelPlayer(nickNamePlayer2);
        this.numberOfRounds = numberOfRounds;
        lifePointsPlayer1 = new int[numberOfRounds];
        lifePointsPlayer2 = new int[numberOfRounds];
    }
    public void saveLifePoints(int lifePointPlayer1,int lifePointPlayer2){

    }
}