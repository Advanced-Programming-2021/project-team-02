package project.controller;

import project.model.User;

public class MainMenuController {
    private static MainMenuController instance = null;
    private String loggedInUserUsername;
    private String loggedInUserToken;
    private User loggedInUser;

    private MainMenuController() {
    }

    public static MainMenuController getInstance() {
        if (instance == null) instance = new MainMenuController();
        return instance;
    }

    public void setLoggedInUser(String loggedInUser, String token) {
        this.loggedInUserUsername = loggedInUser;
        this.loggedInUserToken = token;
    }

    public User getLoggedInUser() {
        if (loggedInUser == null)
            loggedInUser = ControllerManager.getInstance().askForLoggedInUser();
        return loggedInUser;
    }

    public String getLoggedInUserToken() {
        return loggedInUserToken;
    }
}