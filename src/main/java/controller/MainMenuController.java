package controller;

import model.User;

import java.util.regex.Matcher;

public class MainMenuController {
    private User loggedInUser;
    private static final MainMenuController instance;
    static {
        instance = new MainMenuController();
    }

    public static MainMenuController getInstance() {
        return instance;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
    public void menuEnter(Matcher matcher){

    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}