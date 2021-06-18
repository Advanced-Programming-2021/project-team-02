package controller;

import model.game.Duel;
import view.ImportExportMenuView;

public class ImportExportMenuController {
    private static ImportExportMenuController instance = null;
    private final ImportExportMenuView view = ImportExportMenuView.getInstance();
    private Duel duel;

    private ImportExportMenuController() {
    }

    public static ImportExportMenuController getInstance() {
        if (instance == null)
            instance = new ImportExportMenuController();
        return instance;
    }
}
