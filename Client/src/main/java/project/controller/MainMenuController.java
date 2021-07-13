package project.controller;

import animatefx.animation.SlideOutUp;
import com.google.gson.Gson;
import project.model.Assets;
import project.model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MainMenuController {
    private static MainMenuController instance = null;
    private String loggedInUserUsername;
    private String loggedInUserToken;
    private User loggedInUser;
    private Assets loggedInUserAssets;

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
            System.out.println("entered");
            loggedInUser = ControllerManager.getInstance().askForLoggedInUser();
        }
        return loggedInUser;
    }

    public String getLoggedInUserToken() {
        return loggedInUserToken;
    }

    public void logout() {
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
}