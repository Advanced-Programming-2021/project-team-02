package project.controller;

import project.model.User;
import project.view.MainMenuView;
import project.view.messages.Error;
import project.view.messages.SuccessMessage;

import java.util.Locale;

public class MainMenuController {
    private User loggedInUser;
    private static MainMenuController instance = null;
    private final MainMenuView view = MainMenuView.getInstance ();

    private MainMenuController (){}

    public static MainMenuController getInstance() {
        if (instance == null) instance = new MainMenuController ();
        return instance;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void menuEnter(String menuName) {
        menuName = menuName.toLowerCase(Locale.ROOT);
        switch (menuName) {
            case "login":
                SuccessMessage.showSuccessMessage (SuccessMessage.LOGOUT);
//                MenusManager.getInstance ().setLoggedInUser (null);
//                MenusManager.getInstance ().changeMenu (Menu.LOGIN_MENU);
                break;
            case "deck":
//                MenusManager.getInstance ().changeMenu (Menu.DECK_MENU);
                break;
            case "scoreboard":
//                MenusManager.getInstance ().changeMenu (Menu.SCOREBOARD_MENU);
                break;
            case "shop":
//                MenusManager.getInstance ().changeMenu (Menu.SHOP_MENU);
                break;
            case "profile":
//                MenusManager.getInstance ().changeMenu (Menu.PROFILE_MENU);
                break;
            case "duel":
//                MenusManager.getInstance ().changeMenu (Menu.DUEL_MENU);
                break;
            default: Error.showError (Error.INVALID_COMMAND);
        }
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}