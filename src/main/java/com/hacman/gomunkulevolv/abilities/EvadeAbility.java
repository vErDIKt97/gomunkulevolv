package com.hacman.gomunkulevolv.abilities;

import com.hacman.gomunkulevolv.object.Creature;

import java.util.Random;

public class EvadeAbility extends Ability {
    private int evadeChance = 10;

    public EvadeAbility(int abilityClass, String title) {
        super(abilityClass, title);
    }

    boolean isEvaded() {
        return new Random().nextInt(0, 100) <= this.getEvadeChance();
    }

    private int getEvadeChance() {
        return evadeChance;
    }

    @Override
    public void lvlUp() {
        super.lvlUp();
        evadeChance = this.evadeChance * this.getAbilityLevel();
    }

    @Override
    public boolean isAtkSuccess(Creature enemy) {
        return !isEvaded();
    }

    @Override
    public float onDefense(Creature curCreature, Creature enemy) {
        return super.onDefense(curCreature, enemy);
    }
}
