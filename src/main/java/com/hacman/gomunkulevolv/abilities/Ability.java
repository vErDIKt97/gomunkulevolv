package com.hacman.gomunkulevolv.abilities;

public class Ability {
    private String title;
    private int abilityClass;

    private int abilityLevel;

    public static final String vampireAbility = "Vampiring";
    public static final String regenerationAbility = "Regeneration";
    public static final String evadeAbility = "Evading";
    public static final String spikeAbility = "Spike";
    public static final String toxicAttackAbility = "Toxin Attack";
    public static final String toxicSkinAbility = "Toxic Skin";

    @Override
    public String toString() {
        return title + " " + abilityLevel;
    }

    public Ability(int abilityClass, String title) {
        this.abilityClass = abilityClass;
        this.title = title;
    }

    public Ability(String title) {
        switch (title) {
            case evadeAbility -> {
                this.abilityClass = 1;
                this.title = evadeAbility;
            }
            case spikeAbility -> {
                this.abilityClass = 1;
                this.title = spikeAbility;
            }
            case toxicAttackAbility -> {
                this.abilityClass = 1;
                this.title = toxicAttackAbility;
            }
            case toxicSkinAbility -> {
                this.abilityClass = 1;
                this.title = toxicSkinAbility;
            }
            case vampireAbility -> {
                this.abilityClass = 1;
                this.title = vampireAbility;
            }
            case regenerationAbility -> {
                this.abilityClass = 1;
                this.title = regenerationAbility;
            }
        }
        this.abilityLevel = 0;
    }

    public int getLevel() {
        return this.abilityClass;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAbilityClass() {
        return abilityClass;
    }

    public void setAbilityClass(int abilityClass) {
        this.abilityClass = abilityClass;
    }

    public int getAbilityLevel() {
        return abilityLevel;
    }

    public void setAbilityLevel(int abilityLevel) {
        this.abilityLevel = abilityLevel;
    }

    public void lvlUp() {
        this.abilityLevel++;
    }
}
