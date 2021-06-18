package view;

import controller.ImportExportMenuController;
import view.input.Regex;
import view.messages.Error;

import java.util.Locale;
import java.util.regex.Matcher;

public class ImportExportMenuView {
    private static ImportExportMenuView instance = null;
    private static final ImportExportMenuController controller = ImportExportMenuController.getInstance();

    private ImportExportMenuView() {

    }

    public static ImportExportMenuView getInstance() {
        if (instance == null) instance = new ImportExportMenuView();
        return instance;
    }

    public void run(String command) throws CloneNotSupportedException {
        commandRecognition(command);
    }

    private void commandRecognition(String command) {
        Matcher matcher;
        if ((matcher = Regex.getMatcher (Regex.MENU_ENTER, command)).matches ()) {
            if (matcher.group ("menuName").toLowerCase(Locale.ROOT).equals ("profile"))
                showDynamicError (Error.BEING_ON_CURRENT_MENU, Menu.PROFILE_MENU.getValue ());
            else Error.showError (Error.BEING_ON_A_MENU);
        } else if (Regex.getMatcher (Regex.MENU_EXIT, command).matches ()) {
            MenusManager.getInstance().changeMenu(Menu.MAIN_MENU);
        } else if ((Regex.getMatcher (Regex.MENU_SHOW_CURRENT, command)).matches ()) {
            showCurrentMenu();
        }
    }

    private void showCurrentMenu() {
        System.out.println ("Import/Export Menu");
    }

    private void showDynamicError(Error beingOnCurrentMenu, String value) {
    }

}
