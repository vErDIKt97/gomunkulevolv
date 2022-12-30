package com.hacman.gomunkulevolv.abilities;

public enum PossibleAbility {
    EVADE {
        @Override
        public String toString() {
            return "Evading";
        }
    },
    REGENERATION {
        @Override
        public String toString() {
            return "Regeneration";
        }
    },
    SPIKE {
        @Override
        public String toString() {
            return "Spike";
        }
    },
    VAMPIRE {
        @Override
        public String toString() {
            return "Vampire";
        }
    },
    TOXIC_ATTACK {
        @Override
        public String toString() {
            return "Toxic Attack";
        }
    },
    TOXIC_SKIN {
        @Override
        public String toString() {
            return "Toxic Skin";
        }
    };
    public static PossibleAbility getAbilityByTitle (String title) {
        for (PossibleAbility ability :
                PossibleAbility.values()) {
            if (title.equals(ability.toString())) return ability;
        }
        return null;
    }
}

