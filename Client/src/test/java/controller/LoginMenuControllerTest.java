//package controller;
//
//import model.User;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import project.view.input.Regex;
//
//import java.io.ByteArrayOutputStream;
//import java.io.PrintStream;
//import java.util.regex.Matcher;
//
//class LoginMenuControllerTest {
//    private static ByteArrayOutputStream outContent = new ByteArrayOutputStream ();
//
//    @BeforeAll
//    static void beforeAll() {
//        System.setOut (new PrintStream (outContent));
//        outContent.reset ();
//    }
//
//    @Test
//    @DisplayName ("Check \"user with username <username> already exists\" error")
//    void userAlreadyExistsWithDuplicateUsername() {
//        String expected = "user created successfully!\nuser with username erfan already exists\n";
//        Matcher matcher = Regex.getMatcherFromAllPermutations (Regex.USER_CREATE,
//                "user create --username erfan --password ramz --nickname mojibi");
//        assert matcher != null;
//        LoginMenuController.getInstance ().createUser (matcher.group("username"), matcher.group("nickname"), matcher.group("password"));
//        matcher = Regex.getMatcherFromAllPermutations (Regex.USER_CREATE,
//                "user create --nickname ali --password ramz --username erfan");
//        assert matcher != null;
//        LoginMenuController.getInstance ().createUser (matcher.group("username"), matcher.group("nickname"), matcher.group("password"));
//        Assertions.assertEquals (expected, outContent.toString ());
//    }
//
//    @Test
//    @DisplayName ("Check \"user with username <nickname> already exists\" error")
//    void userAlreadyExistsWithDuplicateNickname() {
//        String expected = "user created successfully!\nuser with nickname mahdis already exists\n";
//        Matcher matcher = Regex.getMatcherFromAllPermutations (Regex.USER_CREATE,
//                "user create -u mhdsdt -n mahdis -p ramz");
//        assert matcher != null;
//        LoginMenuController.getInstance ().createUser (matcher.group("username"), matcher.group("nickname"), matcher.group("password"));
//        matcher = Regex.getMatcherFromAllPermutations (Regex.USER_CREATE,
//                "user create -u ali -p ramz -n mahdis");
//        assert matcher != null;
//        LoginMenuController.getInstance ().createUser (matcher.group("username"), matcher.group("nickname"), matcher.group("password"));
//        Assertions.assertEquals (expected, outContent.toString ());
//    }
//
//    @Test
//    @DisplayName ("Check \"user logged in successfully!\" message")
//    void userLoggedInSuccessfully() {
//        String expected = "user created successfully!\nuser logged in successfully!\n";
//        Matcher matcher = Regex.getMatcherFromAllPermutations (Regex.USER_CREATE,
//                "user create -u safa -n kabir -p ramz");
//        assert matcher != null;
//        LoginMenuController.getInstance ().createUser (matcher.group("username"), matcher.group("nickname"), matcher.group("password"));
//        matcher = Regex.getMatcherFromAllPermutations (Regex.USER_LOGIN,
//                "user login -u safa -p ramz");
//        assert matcher != null;
//        LoginMenuController.getInstance ().loginUser (matcher.group("username"), matcher.group("password"));
//        Assertions.assertEquals (expected, outContent.toString ());
//        Assertions.assertEquals (MenusManager.getInstance ().getLoggedInUser (), User.getUserByUsername ("safa"));
//        Assertions.assertEquals (MainMenuController.getInstance ().getLoggedInUser (), User.getUserByUsername ("safa"));
//        Assertions.assertEquals (MenusManager.getInstance ().getCurrentMenu (), Menu.MAIN_MENU);
//    }
//
//    @Test
//    @DisplayName ("wrong password error")
//    void wrongPasswordError() {
//        String expected = "user created successfully!\nUsername and password didn't match!\n";
//        Matcher matcher = Regex.getMatcherFromAllPermutations (Regex.USER_CREATE,
//                "user create -u naghio -n mmdo -p ramz");
//        assert matcher != null;
//        LoginMenuController.getInstance ().createUser (matcher.group("username"), matcher.group("nickname"), matcher.group("password"));
//        matcher = Regex.getMatcherFromAllPermutations (Regex.USER_LOGIN,
//                "user login -u naghio -p pass");
//        assert matcher != null;
//        LoginMenuController.getInstance ().loginUser (matcher.group("username"), matcher.group("password"));
//        Assertions.assertEquals (expected, outContent.toString ());
//    }
//
//    @Test
//    @DisplayName ("wrong username error")
//    void wrongUsernameError() {
//        String expected = "user created successfully!\nUsername and password didn't match!\n";
//        Matcher matcher = Regex.getMatcherFromAllPermutations (Regex.USER_CREATE,
//                "user create -u alis -n hakir -p ramz");
//        assert matcher != null;
//        LoginMenuController.getInstance ().createUser (matcher.group("username"), matcher.group("nickname"), matcher.group("password"));
//        matcher = Regex.getMatcherFromAllPermutations (Regex.USER_LOGIN,
//                "user login -u davud -p ramz");
//        assert matcher != null;
//        LoginMenuController.getInstance ().loginUser (matcher.group("username"), matcher.group("password"));
//        Assertions.assertEquals (expected, outContent.toString ());
//    }
//}