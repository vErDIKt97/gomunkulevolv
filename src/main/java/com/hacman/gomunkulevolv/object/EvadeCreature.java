package com.hacman.gomunkulevolv.object;

import com.hacman.gomunkulevolv.abilities.Evadable;

import java.util.Random;

public class EvadeCreature extends Creature implements Evadable {

    private int evadeChance = 10;

    public EvadeCreature(Creature creature) {
        super(creature);
    }

    @Override
    public boolean isEvaded() {
        return new Random().nextInt(0, 100) < this.getEvadeChance();
    }

    @Override
    public boolean takeDamage(Creature enemy) {
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
