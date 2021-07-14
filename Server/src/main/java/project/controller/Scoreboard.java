package project.controller;

import com.google.gson.Gson;
import project.ServerMainController;
import project.model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class Scoreboard {
    public static String scoreboardData() {

        return new Gson().toJson(ScoreboardData.getDataArrayList());

    }

    private static boolean isOnline(User user) {
        HashMap<String, User> loggedInUsers = ServerMainController.getLoggedInUsers();
        for (String s : loggedInUsers.keySet()) {
            if (loggedInUsers.get(s).getUsername().equals(user.getUsername()))
                return true;
        }
        return false;
    }
}
