package view;

import controller.MainMenuController;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class ProfileMenuViewTest {

    private static ByteArrayOutputStream outContent = new ByteArrayOutputStream ();

    @BeforeAll
    static void beforeAll() {
        System.setOut (new PrintStream (outContent));
        outContent.reset ();
    }

    @Test
    @DisplayName("Check \"menu enter profile\" command")
    void menuEnterProfile() {
        String expected = "you are in ProfileMenu!\n";
        ProfileMenuView.getInstance ().run ("menu enter profile");
        Assertions.assertEquals (expected, outContent.toString ());
    }

    @Test
    @DisplayName ("Check \"menu enter shop\" command")
    void menuEnterShop() {
        String expected = "menu navigation is not possible\n";
        ProfileMenuView.getInstance ().run ("menu enter shop");
        Assertions.assertEquals (expected, outContent.toString ());
    }

    @Test
    @DisplayName ("\"change nickname\" command")
    void changeNickname() {
        String expected = "nickname changed successfully!\n";
        User user = new User ("mhdsdt", "pass", "mahdis");
        MenusManager.getInstance ().setLoggedInUser (user);
        MainMenuController.getInstance ().setLoggedInUser (user);
        ProfileMenuView.getInstance ().run ("profile change --nickname machi");
        Assertions.assertEquals (expected, outContent.toString ());
    }

    @Test
    @DisplayName ("\"change password\" command")
    void changePassword() {
        String expected = "password changed successfully!\n";
        User user = new User ("mhdsdt", "pass", "mahdis");
        MenusManager.getInstance ().setLoggedInUser (user);
        MainMenuController.getInstance ().setLoggedInUser (user);
        ProfileMenuView.getInstance ().run ("profile change -p -c pass -n ramz");
        Assertions.assertEquals (expected, outContent.toString ());
    }

    @Test
    @DisplayName("\"show current menu\" message")
    void showCurrentMenu() {
        String expected = "Profile Menu\n";
        ProfileMenuView.getInstance ().run ("menu show-current");
        Assertions.assertEquals (expected, outContent.toString ());
    }

    @Test
    @DisplayName("help command")
    void helpCommand() {
        String expected = "menu show-current\n" +
                "profile change --nickname <nickname>\n" +
                "profile change --password --current <currentPassword> --new <newPassword>\n" +
                "profile change -p -c <currentPassword> -n <newPassword>\n" +
                "menu exit\n" +
                "help\n";
        ProfileMenuView.getInstance ().run ("help");
        Assertions.assertEquals (expected, outContent.toString ());
    }

    @Test
    @DisplayName ("Check \"menu exit\" command")
    void exitCommand() {
        ProfileMenuView.getInstance ().run ("menu exit");
        Assertions.assertEquals (MenusManager.getInstance ().getCurrentMenu (), Menu.MAIN_MENU);
    }

    @Test
    @DisplayName ("invalid command")
    void invalidCommand() {
        String expected = "invalid command\ninvalid command\n";
        MainMenuView.getInstance ().run ("salum");
        MainMenuView.getInstance ().run ("profile change --n mmd");
        Assertions.assertEquals (expected, outContent.toString ());
    }

}