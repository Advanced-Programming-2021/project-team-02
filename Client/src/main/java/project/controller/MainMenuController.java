package project.controller;

import animatefx.animation.SlideOutUp;
import com.google.gson.Gson;
import project.model.Assets;
import project.model.User;
import project.view.ProfileMenuView;
import project.view.ScoreBoardView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MainMenuController {
    private static MainMenuController instance = null;
    private String loggedInUserUsername;
    private String loggedInUserToken;
    private User loggedInUser;
    private Assets loggedInUserAssets;
    private ScoreBoardView scoreBoardView;
    private ProfileMenuView profileMenuView;
    private MainMenuController() {
    }

    public static MainMenuController getInstance() {
        if (instance == null) instance = new MainMenuController();
        return instance;
    }

    public void setLoggedInUsernameAndToken(String loggedInUser, String token) {
        this.loggedInUserUsername = loggedInUser;
        this.loggedInUserToken = token;
    }

    public User getLoggedInUser() {
        if (loggedInUser == null) {
            loggedInUser = ControllerManager.getInstance().askForLoggedInUser();
        }
        return loggedInUser;
    }

    public void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    public String getLoggedInUserToken() {
        return loggedInUserToken;
    }

    public void logout() {

        DataOutputStream dataOutputStream = ControllerManager.getInstance().getDataOutputStream();
        DataInputStream dataInputStream = ControllerManager.getInstance().getDataInputStream();
        try {
            System.out.println("logout "+loggedInUserToken);
            dataOutputStream.writeUTF("logout " + loggedInUserToken);
            dataOutputStream.flush();
            dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loggedInUser = null;
        loggedInUserAssets = null;
        loggedInUserToken = "";
        loggedInUserUsername = "";
    }

    public Assets getLoggedInUserAssets() {
        if (loggedInUserAssets == null)
            loggedInUserAssets = ControllerManager.getInstance().getAUserAssets(loggedInUserUsername);
        return loggedInUserAssets;
    }

    public void updateLoggedInAsset(Assets assets) {
        loggedInUserAssets = assets;
    }

    public ScoreBoardView getScoreBoard() {
        return scoreBoardView;
    }

    public void setScoreBoardView(ScoreBoardView scoreBoardView) {
        this.scoreBoardView = scoreBoardView;
    }

    public void setProfileMenuView(ProfileMenuView profileMenuView) {
        this.profileMenuView = profileMenuView;
    }

    public ProfileMenuView getProfileMenuView() {
        return profileMenuView;
    }
}