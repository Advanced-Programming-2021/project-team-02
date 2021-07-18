package project.controller;


import com.google.gson.Gson;
import project.ServerMainController;
import project.model.User;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;


public class ProfileController {


    public String changePhoto(String token, URL avatar) {
        ServerMainController.getLoggedInUsers().get(token).setAvatarURL(avatar);
        System.out.println("change url : "+ ServerMainController.getLoggedInUsers().get(token).getAvatarURL());
        return "success";

    }

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
        HashMap<String, DataOutputStream> profileData = ServerMainController.getProfileDataTransfer();
        for (String s : loggedInUsers.keySet()) {
            if (loggedInUsers.get(s).getUsername().equals(user.getUsername())) {
                try {
                    profileData.get(s).writeUTF(new Gson().toJson(user));
                    profileData.get(s).flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}