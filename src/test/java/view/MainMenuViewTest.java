package view;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class MainMenuViewTest {

    private static ByteArrayOutputStream outContent = new ByteArrayOutputStream ();

    @BeforeAll
    static void beforeAll() {
        System.setOut (new PrintStream (outContent));
    }

    @Test
    @DisplayName ("Check \"menu enter profile\" command")
    void menuEnterProfile() {
        MainMenuView.getInstance ().run ("menu enter profile");
        assertEquals (MenusManager.getInstance ().getCurrentMenu (), Menu.PROFILE_MENU);
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
    @DisplayName ("Check \"user logout\" command")
    void userLogout() {
        outContent.reset ();
        String expected = "user logged out successfully!\n";
        MainMenuView.getInstance ().run ("user logout");
        Assertions.assertEquals (MenusManager.getInstance ().getCurrentMenu (), Menu.LOGIN_MENU);
        Assertions.assertNull (MenusManager.getInstance ().getLoggedInUser ());
        Assertions.assertEquals (expected, outContent.toString ());
    }

    @Test
    @DisplayName("\"show current menu\" message")
    void showCurrentMenu() {
        outContent.reset ();
        String expected = "Main Menu\n";
        MainMenuView.getInstance ().run ("menu show-current");
        Assertions.assertEquals (expected, outContent.toString ());
    }

    @Test
    @DisplayName ("help command")
    void helpCommand() {
        outContent.reset ();
        String expected = "menu show-current\n" +
                "menu enter <menuName>\n" +
                "user logout\n" +
                "menu exit\n" +
                "help\n";
        MainMenuView.getInstance ().run ("help");
        Assertions.assertEquals (expected, outContent.toString ());
    }

    @Test
    @DisplayName ("Check \"menu exit\" command")
    void exitCommand() {
        outContent.reset ();
        String expected = "user logged out successfully!\n";
        MainMenuView.getInstance ().run ("menu exit");
        Assertions.assertEquals (MenusManager.getInstance ().getCurrentMenu (), Menu.LOGIN_MENU);
        Assertions.assertNull (MenusManager.getInstance ().getLoggedInUser ());
        Assertions.assertEquals (expected, outContent.toString ());
    }

    @Test
    @DisplayName ("invalid command")
    void invalidCommand() {
        outContent.reset ();
        String expected = "invalid command\ninvalid command\n";
        MainMenuView.getInstance ().run ("salum");
        MainMenuView.getInstance ().run ("men enter scoreboard");
        Assertions.assertEquals (expected, outContent.toString ());
    }
}