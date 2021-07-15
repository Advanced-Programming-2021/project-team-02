package project.view;

import java.util.ArrayList;

public class ScoreboardData {
    private String nickname;
    private int score;
    private boolean isOnline;
    private static ArrayList<ScoreboardData> dataArrayList;
    static {
        dataArrayList = new ArrayList<>();
    }
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

    public static ArrayList<ScoreboardData> getDataArrayList() {
        return dataArrayList;
    }

    public static void setDataArrayList(ArrayList<ScoreboardData> dataArrayList) {
        ScoreboardData.dataArrayList = dataArrayList;
    }
}
