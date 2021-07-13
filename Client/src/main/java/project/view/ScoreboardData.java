package project.view;

public class ScoreboardData {
    private String nickname;
    private int score;
    private boolean isOnline;

    public ScoreboardData(String nickname, int score, boolean isOnline) {
        this.score = score;
        this.nickname = nickname;
        this.isOnline = isOnline;
    }

    public int getScore() {
        return score;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isOnline() {
        return isOnline;
    }
}
