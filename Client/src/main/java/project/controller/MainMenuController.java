package project.controller;

import project.model.User;

public class MainMenuController {
    private User loggedInUser;
    private static MainMenuController instance = null;

    private MainMenuController (){}

    public static MainMenuController getInstance() {
        if (instance == null) instance = new MainMenuController ();
        return instance;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}