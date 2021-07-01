package project.view;

import project.controller.ImportExportMenuController;
import project.view.input.Regex;
import project.view.messages.Error;
import project.view.messages.SuccessMessage;

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
            if (matcher.group ("menuName").toLowerCase(Locale.ROOT).equals ("importexport"))
                showDynamicError(Error.BEING_ON_CURRENT_MENU, Menu.IMPORT_EXPORT_MENU.getValue());
            else Error.showError (Error.BEING_ON_A_MENU);
        } else if (Regex.getMatcher (Regex.MENU_EXIT, command).matches ()) {
            MenusManager.getInstance().changeMenu(Menu.MAIN_MENU);
        } else if ((Regex.getMatcher (Regex.MENU_SHOW_CURRENT, command)).matches ()) {
            showCurrentMenu();
        } else if ((matcher = Regex.getMatcher(Regex.IMPORT, command)).matches()) {
            controller.importCard(matcher.group("fileName"));
        } else if ((matcher = Regex.getMatcher(Regex.EXPORT, command)).matches()) {
            controller.exportCard(matcher.group("cardName"));
        } else if (Regex.getMatcher(Regex.COMMAND_HELP, command).matches()) {
            help ();
        } else Error.showError(Error.INVALID_COMMAND);
    }

    public void showDynamicError(Error error, String string) {
        System.out.printf(error.getValue(), string);
    }

    public void showSuccessMessage(SuccessMessage successMessage) {
        System.out.println(successMessage.getValue());
    }

    private void showCurrentMenu() {
        System.out.println ("Import/Export Menu");
    }

    public void showError(Error error) {
        System.out.println(error.getValue());
    }


    public void help() {
        System.out.println("import card <fileName>\n" +
                "export card <cardName>\n" +
                "menu show-current\n" +
                "menu exit");
    }

}
