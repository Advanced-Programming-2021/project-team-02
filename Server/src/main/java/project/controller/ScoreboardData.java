package project.controller;

import java.util.ArrayList;

public class ScoreboardData {
    private static ArrayList<ScoreboardData> dataArrayList;

    static {
        dataArrayList = new ArrayList<>();
    }

    private String nickname;
    private int score;
    private boolean isOnline;

    public ScoreboardData(String nickname, int score, boolean isOnline) {

        this.score = score;
        this.nickname = nickname;
        this.isOnline = isOnline;
        dataArrayList.add(this);
    }

    public static ArrayList<ScoreboardData> getDataArrayList() {
        return dataArrayList;
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

    public static boolean mustBeAdded(String nickname) {
        if (dataArrayList.stream().filter(scoreboardData -> scoreboardData.nickname.equals(nickname)).count() != 0)
            return false;
        return true;
    }
}
