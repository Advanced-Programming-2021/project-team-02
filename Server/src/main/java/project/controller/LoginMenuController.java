package project.controller;


import com.google.gson.Gson;
import project.ServerMainController;
import project.model.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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
        saveToDataBase(username, nickname, password);
        return "success";
    }

    public void saveToDataBase(String username1, String nickname1, String password1) {
        String url = "jdbc:mysql://localhost:7000/user";
        String usernameLH = "mahdi";
        String passwordLH = "test1234";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, usernameLH, passwordLH);
            System.out.println("Connection was successful : " + url);
            String query = "Insert into information(username,nickname,password) values(" + "'" + username1 + "'" + "," + "'" + nickname1 + "'" + "," + "'" + password1 + "'" + ")";
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
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