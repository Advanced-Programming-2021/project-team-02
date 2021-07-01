package project.view;

import project.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class ScoreBoardViewTest {

    private static ByteArrayOutputStream outContent = new ByteArrayOutputStream ();

    @BeforeAll
    static void beforeAll() {
        System.setOut (new PrintStream(outContent));
        outContent.reset ();
    }

    @Test
    @DisplayName("Check \"menu enter profile\" command")
    void menuEnterProfile() {
        String expected = "menu navigation is not possible\n";
        ScoreBoardView.getInstance ().run ("menu enter profile");
        Assertions.assertEquals (expected, outContent.toString ());
    }

    @Test
    @DisplayName ("Check \"menu enter scoreboard\" command")
    void menuEnterScoreboard() {
        String expected = "you are in ScoreboardMenu!\n";
        ScoreBoardView.getInstance ().run ("menu enter scoreboard");
        Assertions.assertEquals (expected, outContent.toString ());
    }

    @Test
    @DisplayName("\"scoreboard show\" message")
    void showScoreBoard() {
        String expected = "1- mahdis: 100\n" +
                "2- 3rfan: 90\n" +
                "2- alis: 90\n" +
                "4- davood: 70\n";
        User mahdi = new User("mhdsdt", "ramz", "mahdis");
        User erfan = new User("mojibi", "pass", "3rfan");
        User davud = new User("lover", "ramz", "davood");
        User ali = new User("mister", "mioo", "alis");
        mahdi.setScore(100);
        erfan.setScore(90);
        ali.setScore(90);
        davud.setScore(70);
        ScoreBoardView.getInstance().run("scoreboard show");
        Assertions.assertEquals (expected, outContent.toString ());
    }

    @Test
    @DisplayName("\"show current menu\" message")
    void showCurrentMenu() {
        String expected = "Scoreboard Menu\n";
        ScoreBoardView.getInstance ().run ("menu show-current");
        Assertions.assertEquals (expected, outContent.toString ());
    }

    @Test
    @DisplayName("help command")
    void helpCommand() {
        String expected = "menu show-current\n" +
                "scoreboard show\n" +
                "menu exit\n" +
                "help\n";
        ScoreBoardView.getInstance ().run ("help");
        Assertions.assertEquals (expected, outContent.toString ());
    }

    @Test
    @DisplayName ("Check \"menu exit\" command")
    void exitCommand() {
        ScoreBoardView.getInstance ().run ("menu exit");
        Assertions.assertEquals (MenusManager.getInstance ().getCurrentMenu (), Menu.MAIN_MENU);
    }

    @Test
    @DisplayName ("invalid command")
    void invalidCommand() {
        String expected = "invalid command\ninvalid command\n";
        ScoreBoardView.getInstance ().run ("salum");
        ScoreBoardView.getInstance ().run ("show scoboard");
        Assertions.assertEquals (expected, outContent.toString ());
    }
}