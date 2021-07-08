package project.controller;

public class ImportExportController {
    private static ImportExportController instance = null;

    private ImportExportController() {
    }

    public static ImportExportController getInstance() {
        if (instance == null) instance = new ImportExportController();
        return instance;
    }

}
