package view;

import controller.LoginMenuController;
import model.User;
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
    @DisplayName ("Check \"user created successfully!\" message")
    void checkUserCreatedSuccessfully() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream ();
        System.setOut (new PrintStream (outContent));
        String expected = "user created successfully!\n";
        LoginMenuView.getInstance ().run ("user create --username mhdsdt --nickname mahdis --password ramz");
        Assertions.assertEquals (expected, outContent.toString ());
        Assertions.assertNotNull (User.getUserByUsername ("mhdsdt"));
        Assertions.assertNotNull (User.getUserByNickName ("mahdis"));
    }

    @Test
    @DisplayName ("Check \"user with username <username> already exists\" error")
    void userAlreadyExistsWithDuplicateUsername() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream ();
        System.setOut (new PrintStream (outContent));
        System.setErr (new PrintStream (outContent));
        String expected = "user created successfully!\nuser with username mhdsdt already exists\n";
        LoginMenuView.getInstance ().run ("user create --username mhdsdt --password ramz --nickname mahdis");
        LoginMenuView.getInstance ().run ("user create --nickname ali --password ramz --username mhdsdt");
        Assertions.assertEquals (expected, outContent.toString ());
    }

    @Test
    @DisplayName ("Check \"user with username <nickname> already exists\" error")
    void userAlreadyExistsWithDuplicateNickname() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream ();
        System.setOut (new PrintStream (outContent));
        System.setErr (new PrintStream (outContent));
        String expected = "user created successfully!\nuser with nickname mahdis already exists\n";
        LoginMenuView.getInstance ().run ("user create -u mhdsdt -n mahdis -p ramz");
        LoginMenuView.getInstance ().run ("user create -u ali -p ramz -n mahdis");
        Assertions.assertEquals (expected, outContent.toString ());
    }

    @Test
    @DisplayName ("Check \"please login first\" error")
    void pleaseLoginFirst() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream ();
        System.setErr (new PrintStream (outContent));
        String expected = "please login first\n";
        LoginMenuView.getInstance ().run ("menu enter Main");
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