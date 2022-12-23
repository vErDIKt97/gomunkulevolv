package com.hacman.gomunkulevolv.object;

import com.hacman.gomunkulevolv.abilities.Vampireble;

public class VampireCreature extends Creature implements Vampireble {

    private double vampirePercent = 0.1;
    public VampireCreature(Creature creature) {
        super(creature);
    }

    @Override
    public void vampiric(int damage) {
        this.setCurHealth((int) (this.getCurHealth()+(damage*vampirePercent)));
    }
    @Override
    public boolean attack(Creature enemy) {
        boolean successAttack = enemy.takeDamage(this);
        if (successAttack) {
            vampiric(this.getDamage());
        }
        return successAttack;
    }
}
