package project.controller;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

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
        return new ArrayList<>(dataArrayList.subList(0, Math.min(10, dataArrayList.size())));
    }

    public static void setOffline(String nickname) {
        for (ScoreboardData data : dataArrayList) {
            if (data.nickname.equals(nickname))
                data.isOnline = false;
        }
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

    public static void setOnline(String nickname) {
        for (ScoreboardData data : dataArrayList) {
            if (data.nickname.equals(nickname))
                data.isOnline = true;
        }

    }

}
