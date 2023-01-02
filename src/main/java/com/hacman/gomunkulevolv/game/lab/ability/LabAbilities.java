package com.hacman.gomunkulevolv.game.lab.ability;

public enum LabAbilities {
    ATTACK {
        @Override
        public String toString() {
            return "Attack";
        }
    },
    DEFENSE {
        @Override
        public String toString() {
            return "Defense";
        }
    },
    ATTACK_SPEED {
        @Override
        public String toString() {
            return "Attack speed";
        }
    },
    MAX_HEALTH {
        @Override
        public String toString() {
            return "Max health";
        }
    };

    public static LabAbilities getLabAbilityByTitle (String title) {
        String[] words = title.split(" ", 5);
        if (words.length == 5)
            title = words[0] + " " + words[1];
        else title = words[0];
        for (LabAbilities ability :
                LabAbilities.values()) {
            if (title.equals(ability.toString())) return ability;
        }
        throw new IllegalStateException("Unexpected value: " + title);
    }
}

