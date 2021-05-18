package view;

import controller.LoginMenuController;
import model.card.CardsDatabase;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class LoginMenuViewTest {

    @BeforeAll
    static void beforeAll() throws IOException {
        LoginMenuView instance = LoginMenuView.getInstance ();
        LoginMenuController controller = LoginMenuController.getInstance ();
        CardsDatabase.getInstance().readAndMakeCards();
    }

    @Test
    void run() {

    }

    @Test
    void commandRecognition() {

    }

    @Test
    void showDynamicError() {
    }

    @Test
    void showCurrentMenu() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream ();
        System.setOut (new PrintStream (outContent));
        String expected = "Login Menu\n";
        LoginMenuView.getInstance ().showCurrentMenu ();
        Assertions.assertEquals (expected, outContent.toString ());
    }

    @Test
    void help() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream ();
        System.setOut (new PrintStream (outContent));
        String expected = "menu show-current\n" +
                "user create --username <username> --nickname <nickname> --password <password>\n" +
                "user create -u <username> -n <nickname> -p <password>\n" +
                "user login --username <username> --password <password>\n" +
                "user login -u <username> -p <password>\n" +
                "menu exit\n" +
                "help\n";
        LoginMenuView.getInstance ().help ();
        Assertions.assertEquals (expected, outContent.toString ());
    }
}