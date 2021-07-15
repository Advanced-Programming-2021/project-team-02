package project.controller;


import project.ServerMainController;
import project.model.User;



public class ProfileController {



    public String changePassword(String token, String currentPassword, String newPassword) {
        ServerMainController.getLoggedInUsers().get(token).changePassword(newPassword);
        return "success";
    }


    public String changeNickname(String token, String newNickName) {
        if (isNicknameUsed(newNickName))
            return "used_nickname";
        else {
            ServerMainController.getLoggedInUsers().get(token).changeNickname(newNickName);
            return "success";
        }
    }

    public boolean isNicknameUsed(String nickname) {
        for (User user : User.getAllUsers())
            if (user.getNickname().equals(nickname)) return true;
        return false;
    }


}