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
        //TODO
        HashMap<String, User> loggedInUsers = ServerMainController.getLoggedInUsers();
        HashMap<String, DataOutputStream> dataTransfer = ServerMainController.getDataTransferForShopCards();
        User user = loggedInUsers.get(token);
        long loggedInNumber = loggedInUsers.values().stream().filter(user1 -> user1.getNickname().equals(user.getNickname())).count();
        if (loggedInNumber == 1) {
            ScoreboardData.setOffline(user.getNickname());
        }
        loggedInUsers.remove(token);
        dataTransfer.remove(token);
        synchronized (ServerMainController.getOnlineCounter()) {
            int count = ServerMainController.getLoggedInUsers().keySet().size();
            for (String s : ServerMainController.getOnlineCounter().keySet()) {
                try {
                    ServerMainController.getOnlineCounter().get(s).writeUTF(String.valueOf(count));
                    ServerMainController.getOnlineCounter().get(s).flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        sendScoreboardData();
        return "success logout";
    }

    public void sendScoreboardData() {
        ArrayList<ScoreboardData> scoreboardData = ScoreboardData.getDataArrayList();
        synchronized (ServerMainController.getScoreboardDataTransfer()) {
            for (String s : ServerMainController.getScoreboardDataTransfer().keySet()) {
                try {
                    ServerMainController.getScoreboardDataTransfer().get(s).writeUTF(new Gson().toJson(scoreboardData));
                    ServerMainController.getScoreboardDataTransfer().get(s).flush();
                    System.out.println("sent");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
