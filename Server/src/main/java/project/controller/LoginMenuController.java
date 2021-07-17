package project.controller;


import com.google.gson.Gson;
import project.ServerMainController;
import project.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;


public class LoginMenuController {
//    private static LoginMenuController instance = null;
//
//    private LoginMenuController() {
//    }
//
//    public static LoginMenuController getInstance() {
//        if (instance == null) instance = new LoginMenuController();
//        return instance;
//    }

    public String createUser(String username, String nickname, String password) {
        if (isUsernameUsed(username)) return "used_username";
        if (isNicknameUsed(nickname)) return "used_nickname";
        User user = new User(username, password, nickname);
        new ScoreboardData(user.getNickname(), user.getScore(), false);
        sendScoreboardDate();
        return "success";
    }

    public void sendScoreboardDate() {
        //TODO SCOREBOARD
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

    public boolean isUsernameUsed(String username) {
        return User.getUserByUsername(username) != null;
    }

    public boolean isNicknameUsed(String nickname) {
        ArrayList<User> allUsers = User.getAllUsers();
        synchronized (allUsers) {
            for (User user : allUsers)
                if (user.getNickname().equals(nickname)) return true;
            return false;
        }
    }

    public boolean doesUsernameAndPasswordMatch(String username, String password) {
        System.out.println(username + "  " + password);
        return User.getUserByUsername(username).getPassword().equals(password);
    }

    public String loginUser(String username, String password) {
        if (!isUsernameUsed(username)) return "username_password_dont_match";
        if (!doesUsernameAndPasswordMatch(username, password)) return "username_password_dont_match";
        for (String s : ServerMainController.getLoggedInUsers().keySet()) {
            if (ServerMainController.getLoggedInUsers().get(s).getUsername().equals(username))
                return "logged_in_before";
        }
        String token = UUID.randomUUID().toString();
        User user = User.getUserByUsername(username);
        System.out.println(user + " logged in ");
        ServerMainController.getLoggedInUsers().put(token, user);
        ScoreboardData.setOnline(user.getNickname());
        sendScoreboardDate();
        return "success " + token;
    }
}