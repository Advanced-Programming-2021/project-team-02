package project.controller;


import com.google.gson.Gson;
import project.ServerMainController;
import project.model.User;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;


public class ProfileController {


    public String changePassword(String token, String newPassword) {
        ServerMainController.getLoggedInUsers().get(token).changePassword(newPassword);
        sendUserData(token);
        return "success";
    }


    public String changeNickname(String token, String newNickName) {
        if (isNicknameUsed(newNickName))
            return "used_nickname";
        else {
            ScoreboardData.changeNickname(ServerMainController.getLoggedInUsers().get(token).getNickname(),newNickName);
            ServerMainController.getLoggedInUsers().get(token).changeNickname(newNickName);

            sendUserData(token);
            return "success";
        }
    }

    public boolean isNicknameUsed(String nickname) {
        for (User user : User.getAllUsers())
            if (user.getNickname().equals(nickname)) return true;
        return false;
    }

    private void sendUserData(String token) {
        HashMap<String, User> loggedInUsers = ServerMainController.getLoggedInUsers();
        User user = loggedInUsers.get(token);
        HashMap<String, DataOutputStream> dataTransfer = ServerMainController.getDataTransfer();
        for (String s : loggedInUsers.keySet()) {
            if (loggedInUsers.get(s).getUsername().equals(user.getUsername())) {
                try {
                    dataTransfer.get(s).writeUTF("profile " + new Gson().toJson(user));
                    dataTransfer.get(s).flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}