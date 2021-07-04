package project.controller.playgame;

public enum Phase {
    DRAW_PHASE,
    STAND_BY_PHASE,
    MAIN_PHASE_1,
    BATTLE_PHASE,
    MAIN_PHASE_2;

    @Override
    public String toString() {
        switch (this) {
            case DRAW_PHASE:
                return "Draw";
            case STAND_BY_PHASE:
                return "Stand By";
            case MAIN_PHASE_1:
                return "Main 1";
            case BATTLE_PHASE:
                return "Battle";
            case MAIN_PHASE_2:
                return "Main 2";
            default:
                return null;
        }

    }
}