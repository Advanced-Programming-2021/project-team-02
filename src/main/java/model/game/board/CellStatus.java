package model.game.board;

public enum CellStatus {
    EMPTY("E"),
    DEFENSIVE_HIDDEN("DH"),
    DEFENSIVE_OCCUPIED("DO"),
    OFFENSIVE_HIDDEN("OH"),
    OFFENSIVE_OCCUPIED("OO"),
    HIDDEN("H"),
    OCCUPIED("O"),
    IN_HAND("HAND");
    private final String label;

    CellStatus(String label) {
        this.label = label;
    }


}
