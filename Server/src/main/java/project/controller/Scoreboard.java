package project.controller;

import com.google.gson.Gson;

public class Scoreboard {
    public static String scoreboardData() {

        return new Gson().toJson(ScoreboardData.getDataArrayList());

    }

}
