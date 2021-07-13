package project.controller;

import project.ServerMainController;
import project.model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class Scoreboard {
    public static String scoreboardData() {
        ArrayList<User> userArrayList = User.sortAllUsers();
        StringBuilder stringBuilder = new StringBuilder();
        for (User user : userArrayList) {
            stringBuilder.append(user.getNickname() + ":" + user.getScore() + ":" + isOnline(user) + "/");
        }
        return stringBuilder.toString();

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
