package project.controller;

import project.model.User;

import java.util.ArrayList;
import java.util.Comparator;
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
        ArrayList<ScoreboardData> scoreboardData = new ArrayList<>(dataArrayList.subList(0, Math.min(10, dataArrayList.size())));
        Comparator<ScoreboardData> comparator = Comparator.comparingInt(ScoreboardData::getScore).reversed().thenComparing(ScoreboardData::getNickname);
        scoreboardData.sort(comparator);
        return scoreboardData;
    }

    public static void setOffline(String nickname) {
        for (ScoreboardData data : dataArrayList) {
            if (data.nickname.equals(nickname))
                data.isOnline = false;
        }
    }

    public static void changeNickname(String nickname, String newNickName) {
        for (ScoreboardData data : dataArrayList) {
            if (data.getNickname().equals(nickname))
                data.nickname = newNickName;
        }
        MainMenuController.getInstance().sendScoreboardDate();
    }

    public static void changeScore(String nickname, int score) {
        for (ScoreboardData data : dataArrayList) {
            if (data.getNickname().equals(nickname))
                data.score = score;
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
