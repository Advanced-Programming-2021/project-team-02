package project.controller;

import com.google.gson.Gson;
import project.ServerMainController;
import project.model.User;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MainMenuController {
    private static MainMenuController instance = null;

    private MainMenuController() {

    }

    public static MainMenuController getInstance() {
        return instance == null ? new MainMenuController() : instance;
    }

    public String logout(String token) {
        HashMap<String, User> loggedInUsers = ServerMainController.getLoggedInUsers();
        HashMap<String, DataOutputStream> dataTransfer = ServerMainController.getDataTransfer();
        User user = loggedInUsers.get(token);
        long loggedInNumber = loggedInUsers.values().stream().filter(user1 -> user1.getNickname().equals(user.getNickname())).count();
        if (loggedInNumber == 1) {
            ScoreboardData.setOffline(user.getNickname());
        }
        loggedInUsers.remove(token);
        dataTransfer.remove(token);
        sendScoreboardDate();
        return "success logout";
    }

    public void sendScoreboardDate() {
        ArrayList<ScoreboardData> scoreboardData = ScoreboardData.getDataArrayList();
        for (String s : ServerMainController.getDataTransfer().keySet()) {
            try {
                ServerMainController.getDataTransfer().get(s).writeUTF("scoreboard " + new Gson().toJson(scoreboardData));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}