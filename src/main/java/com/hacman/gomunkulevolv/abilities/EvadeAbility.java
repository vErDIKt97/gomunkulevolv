package com.hacman.gomunkulevolv.abilities;

import java.util.Random;

public class EvadeAbility extends Ability {
    private double evadeChance = 10;

    public EvadeAbility(int abilityClass, String title) {
        super(abilityClass, title);
    }

    boolean isEvaded() {
        return new Random().nextDouble(0, 100) <= this.getEvadeChance();
    }

    private double getEvadeChance() {
        return evadeChance;
    }

    @Override
    public void lvlUp() {
        super.lvlUp();
        this.evadeChance = ((0.22 * this.getAbilityLevel()) / (1 + 0.22 * this.getAbilityLevel()));
    }

    public boolean isAtkSuccess() {
        return !isEvaded();
    }

}
