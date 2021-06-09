package project.model.card.informationofcards;

public enum TrapEffect {
    MAGIC_CYLINDER_EFFECT,
    MIRROR_FORCE_EFFECT,
    MIND_CRUSH_EFFECT,
    TRAP_HOLE_EFFECT,
    TORRENTIAL_TRIBUTE_EFFECT,
    TIME_SEAL_EFFECT,
    NEGATE_ATTACK_EFFECT,
    SOLEMN_WARNING_EFFECT,
    MAGIC_JAMMER_EFFECT,
    CALL_OF_THE_HAUNTED_EFFECT;

    public static TrapEffect getTrapEffectByName(String trap) {
        switch (trap) {
            case "Wall of Revealing Light":
            case "Vanity's Emptiness":
            case "Trap Hole":
                return TRAP_HOLE_EFFECT;
            case "Mirror Force":
                return MIRROR_FORCE_EFFECT;
            case "Magic Cylinder":
                return MAGIC_CYLINDER_EFFECT;
            case "Mind Crush":
                return MIND_CRUSH_EFFECT;
            case "Torrential Tribute":
                return TORRENTIAL_TRIBUTE_EFFECT;
            case "Time Seal":
                return TIME_SEAL_EFFECT;
            case "Negate Attack":
                return NEGATE_ATTACK_EFFECT;
            case "Solemn Warning":
                return SOLEMN_WARNING_EFFECT;
            case "Magic Jamamer":
                return MAGIC_JAMMER_EFFECT;
            case "Call of The Haunted":
                return CALL_OF_THE_HAUNTED_EFFECT;
        }
        return null;
    }

}
