package project.model.card.informationofcards;

public enum SpellEffect {
    MONSTER_REBORN_EFFECT,
    TERRAFORMING_EFFECT,
    POT_OF_GREED_EFFECT,
    RAIGEKI_EFFECT,
    CHANGE_OF_HEART_EFFECT,
    HARPIES_FEATHER_DUSTER_EFFECT,
    SWORDS_OF_REVEALING_LIGHT_EFFECT,
    DARK_HOLE_EFFECT,
    SUPPLY_SQUAD_EFFECT,
    SPELL_ABSORPTION_EFFECT,
    MESSENGER_OF_PEACE_EFFECT,
    TWIN_TWISTERS_EFFECT,
    MYSTICAL_SPACE_TYPHOON_EFFECT,
    RING_OF_DEFENSE_EFFECT,
    YAMI_EFFECT,
    FOREST_EFFECT,
    CLOSED_FOREST_EFFECT,
    UMIIRUKA_EFFECT,
    SWORD_OF_DARK_DESTRUCTION_EFFECT,
    BLACK_PENDANT_EFFECT,
    UNITED_WE_STAND_EFFECT,
    MAGNUM_SHIELD_EFFECT,
    RITUAL;

    public static SpellEffect getSpellByName(String spell) {
        switch (spell) {
            case "Advanced Ritual Art":
                return RITUAL;
//            case "Magnum Shield":
//                return MAGNUM_SHIELD_EFFECT;
//            case "United We Stand":
//                return UNITED_WE_STAND_EFFECT;
            case "Black Pendant":
                return BLACK_PENDANT_EFFECT;
            case "Sword of dark destruction":
                return SWORD_OF_DARK_DESTRUCTION_EFFECT;
            case "Umiiruka":
                return UMIIRUKA_EFFECT;
            case "Closed Forest":
                return CLOSED_FOREST_EFFECT;
            case "Forest":
                return FOREST_EFFECT;
            case "Yami":
                return YAMI_EFFECT;
//            case "Ring of defense":
//                return RING_OF_DEFENSE_EFFECT;
//            case "Mystical space typhoon":
//                return MYSTICAL_SPACE_TYPHOON_EFFECT;
//            case "Twin Twisters":
//                return TWIN_TWISTERS_EFFECT;
//            case "Messenger of peace":
//                return MESSENGER_OF_PEACE_EFFECT;
//            case "Spell Absorption":
//                return SPELL_ABSORPTION_EFFECT;
//            case "Supply Squad":
//                return SUPPLY_SQUAD_EFFECT;
            case "Dark Hole":
                return DARK_HOLE_EFFECT;
//            case "Change of Heart":
//                return CHANGE_OF_HEART_EFFECT;
//            case "Swords of Revealing Light":
//                return SWORDS_OF_REVEALING_LIGHT_EFFECT;
            case "Harpie's Feather Duster":
                return HARPIES_FEATHER_DUSTER_EFFECT;
            case "Raigeki":
                return RAIGEKI_EFFECT;
            case "Pot of Greed":
                return POT_OF_GREED_EFFECT;
            case "Terraforming":
                return TERRAFORMING_EFFECT;
            case "Monster Reborn":
                return MONSTER_REBORN_EFFECT;
        }
        return null;
    }

}
