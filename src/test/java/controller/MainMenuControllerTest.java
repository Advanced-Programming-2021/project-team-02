package controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.MainMenuView;
import view.Menu;
import view.MenusManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class MainMenuControllerTest {

    private static ByteArrayOutputStream outContent = new ByteArrayOutputStream ();

    @BeforeAll
    static void beforeAll() {
        System.setOut (new PrintStream (outContent));
    }

    @Test
    @DisplayName ("Check \"menu enter main\" command")
    void menuEnterMain() {
        outContent.reset ();
        String expected = "you are in MainMenu!\n";
        MainMenuView.getInstance ().run ("menu enter main");
        Assertions.assertEquals (expected, outContent.toString ());
    }

    @Test
    @DisplayName("Check \"menu enter profile\" command")
    void menuEnterProfile() {
        MainMenuController.getInstance ().menuEnter ("profile");
        assertEquals (MenusManager.getInstance ().getCurrentMenu (), Menu.PROFILE_MENU);
    }

    @Test
    @DisplayName("Check \"menu enter shop\" command")
    void menuEnterShop() {
        MainMenuController.getInstance ().menuEnter ("shop");
        assertEquals (MenusManager.getInstance ().getCurrentMenu (), Menu.SHOP_MENU);
    }

    @Test
    @DisplayName("Check \"menu enter scoreboard\" command")
    void menuEnterScoreboard() {
        MainMenuController.getInstance ().menuEnter ("scoreboard");
        assertEquals (MenusManager.getInstance ().getCurrentMenu (), Menu.SCOREBOARD_MENU);
    }

    @Test
    @DisplayName("Check \"menu enter deck\" command")
    void menuEnterDeck() {
        MainMenuController.getInstance ().menuEnter ("deck");
        assertEquals (MenusManager.getInstance ().getCurrentMenu (), Menu.DECK_MENU);
    }

    @Test
    @DisplayName("Check \"menu enter login\" command")
    void menuEnterLogin() {
        outContent.reset ();
        String expected = "user logged out successfully!\n";
        MainMenuController.getInstance ().menuEnter ("login");;
        Assertions.assertEquals (MenusManager.getInstance ().getCurrentMenu (), Menu.LOGIN_MENU);
        Assertions.assertNull (MenusManager.getInstance ().getLoggedInUser ());
        Assertions.assertEquals (expected, outContent.toString ());
    }

    @Test
    @DisplayName ("invalid command")
    void invalidCommand() {
        outContent.reset ();
        String expected = "invalid command\ninvalid command\n";
        MainMenuController.getInstance ().menuEnter ("salum");
        MainMenuController.getInstance ().menuEnter ("men enter scoreboard");
        Assertions.assertEquals (expected, outContent.toString ());
    }
}