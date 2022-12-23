package com.hacman.gomunkulevolv.object;

import com.hacman.gomunkulevolv.abilities.Evadable;

import java.util.Random;

public class EvadeMainCreature extends MainCreature implements Evadable {

    private int evadeChance = 10;
    private int evadeIndex = 0;

    public EvadeMainCreature(MainCreature creature) {
        super(creature);
        this.getCurrentAbilityList().add(getPossibleAbilityList().get(evadeIndex));
    }

    @Override
    public boolean isEvaded() {
        return new Random().nextInt(0, 100) < this.getEvadeChance();
    }

    @Override
    public boolean takeDamage(Creature enemy) {
        return tryEvadeDamage(enemy);
    }

    private boolean tryEvadeDamage(Creature enemy) {
        if (!isEvaded()) {
            return super.takeDamage(enemy);
        } else return false;
    }

    public int getEvadeChance() {
        return evadeChance;
    }

    public void setEvadeChance(int evadeChance) {
        this.evadeChance = evadeChance;
    }
}

