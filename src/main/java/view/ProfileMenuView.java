package view;

import controller.ProfileMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.input.Regex;
import view.messages.Error;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;

public class ProfileMenuView extends Application {
    private static ProfileMenuView instance = null;
    private static final ProfileMenuController controller = ProfileMenuController.getInstance ();
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        URL urlMain = getClass().getResource("fxml/ProfileMenuView.fxml");
        System.out.println(urlMain);
        Parent root = FXMLLoader.load(Objects.requireNonNull(urlMain));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }



    public static ProfileMenuView getInstance() {
        if (instance == null) instance = new ProfileMenuView ();
        return instance;
    }

    public void run(String command) {
        commandRecognition(command);
    }

    public void commandRecognition(String command) {
        Matcher matcher;
        if ((matcher = Regex.getMatcher (Regex.MENU_ENTER, command)).matches ()) {
            if (matcher.group ("menuName").toLowerCase(Locale.ROOT).equals ("profile"))
                showDynamicError (Error.BEING_ON_CURRENT_MENU, Menu.PROFILE_MENU.getValue ());
            else Error.showError (Error.BEING_ON_A_MENU);
        } else if (Regex.getMatcher (Regex.MENU_EXIT, command).matches ()) {
            MenusManager.getInstance().changeMenu(Menu.MAIN_MENU);
        } else if ((Regex.getMatcher (Regex.MENU_SHOW_CURRENT, command)).matches ()) {
            showCurrentMenu ();
        } else if ((matcher = Regex.getMatcher (Regex.PROFILE_CHANGE_NICKNAME, command)).matches ()) {
            controller.changeNickname (matcher.group ("nickname"));
        } else if ((matcher = Regex.getMatcherFromAllPermutations (Regex.PROFILE_CHANGE_PASSWORD, command)) != null) {
            controller.changePassword (matcher.group ("currentPassword"), matcher.group ("newPassword"));
        } else if (Regex.getMatcher (Regex.COMMAND_HELP, command).matches ()) {
            help ();
        } else Error.showError (Error.INVALID_COMMAND);
    }

    public void showDynamicError(Error error, String string) {
        if (error.equals (Error.TAKEN_USERNAME))
            System.out.printf (Error.TAKEN_USERNAME.getValue (), string);
        else if (error.equals (Error.TAKEN_NICKNAME))
            System.out.printf (Error.TAKEN_NICKNAME.getValue (), string);
        else if (error.equals (Error.BEING_ON_CURRENT_MENU))
            System.out.printf (error.getValue (), string);
    }

    public void showCurrentMenu() {
        System.out.println ("Profile Menu");
    }

    public void help() {
        System.out.println ("menu show-current\n" +
                "profile change --nickname <nickname>\n" +
                "profile change --password --current <currentPassword> --new <newPassword>\n" +
                "profile change -p -c <currentPassword> -n <newPassword>\n" +
                "menu exit\n" +
                "help");
    }
}