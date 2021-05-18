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
    void userCreatedSuccessfully() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream ();
        System.setOut (new PrintStream (outContent));
        String expected = "user created successfully!\n";
        LoginMenuView.getInstance ().run ("user create --username mmd --nickname taghi --password ramz");
        Assertions.assertEquals (expected, outContent.toString ());
        Assertions.assertNotNull (User.getUserByUsername ("mmd"));
        Assertions.assertNotNull (User.getUserByNickName ("taghi"));
    }

    @Test
    @DisplayName ("Check \"user with username <username> already exists\" error")
    void userAlreadyExistsWithDuplicateUsername() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream ();
        System.setOut (new PrintStream (outContent));
        String expected = "user created successfully!\nuser with username erfan already exists\n";
        LoginMenuView.getInstance ().run ("user create --username erfan --password ramz --nickname mojibi");
        LoginMenuView.getInstance ().run ("user create --nickname ali --password ramz --username erfan");
        Assertions.assertEquals (expected, outContent.toString ());
    }

    @Test
    @DisplayName ("Check \"user with username <nickname> already exists\" error")
    void userAlreadyExistsWithDuplicateNickname() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream ();
        System.setOut (new PrintStream (outContent));
        String expected = "user created successfully!\nuser with nickname mahdis already exists\n";
        LoginMenuView.getInstance ().run ("user create -u mhdsdt -n mahdis -p ramz");
        LoginMenuView.getInstance ().run ("user create -u ali -p ramz -n mahdis");
        Assertions.assertEquals (expected, outContent.toString ());
    }

    @Test
    @DisplayName ("Check \"user logged in successfully!\" message")
    void userLoggedInSuccessfully() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream ();
        System.setOut (new PrintStream (outContent));
        String expected = "user created successfully!\nuser logged in successfully!\n";
        LoginMenuView.getInstance ().run ("user create -u naghio -n mmdo -p ramz");
        LoginMenuView.getInstance ().run ("user login -u naghio -p ramz");
        Assertions.assertEquals (expected, outContent.toString ());

    }

    @Test
    @DisplayName ("\"show current menu\" message")
    void showCurrentMenu() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream ();
        System.setOut (new PrintStream (outContent));
        String expected = "Login Menu\n";
        LoginMenuView.getInstance ().run ("menu show-current");
        Assertions.assertEquals (expected, outContent.toString ());
        assertEquals (MenusManager.getInstance ().getCurrentMenu (), Menu.MAIN_MENU);
    }

    @Test
    @DisplayName ("Check \"menu exit\" command")
    void exitCommand() {
        LoginMenuView.getInstance ().run ("menu exit");
        assertEquals (MenusManager.getInstance ().getCurrentMenu (), Menu.EXIT);
    }

    @Test
    @DisplayName ("Check \"please login first\" error")
    void pleaseLoginFirst() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream ();
        System.setOut (new PrintStream (outContent));
        String expected = "please login first\n";
        LoginMenuView.getInstance ().run ("menu enter Main");
        Assertions.assertEquals (expected, outContent.toString ());
    }

    @Test
    @DisplayName ("invalid command")
    void invalidCommand() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream ();
        System.setOut (new PrintStream (outContent));
        String expected = "invalid command\ninvalid command\ninvalid command\n";
        LoginMenuView.getInstance ().run ("salum");
        LoginMenuView.getInstance ().run ("user create -y mhdsdt -n mahdis -p ramz");
        LoginMenuView.getInstance ().run ("use create --username erfan --password ramz --nickname mojibi");
        Assertions.assertEquals (expected, outContent.toString ());
    }

    @Test
    @DisplayName ("help command")
    void helpCommand() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream ();
        System.setOut (new PrintStream (outContent));
        String expected = "menu show-current\n" +
                "user create --username <username> --nickname <nickname> --password <password>\n" +
                "user create -u <username> -n <nickname> -p <password>\n" +
                "user login --username <username> --password <password>\n" +
                "user login -u <username> -p <password>\n" +
                "menu exit\n" +
                "help\n";
        LoginMenuView.getInstance ().run ("help");
        Assertions.assertEquals (expected, outContent.toString ());
    }
}