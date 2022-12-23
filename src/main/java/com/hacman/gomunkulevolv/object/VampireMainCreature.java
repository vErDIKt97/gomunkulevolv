package com.hacman.gomunkulevolv.object;

import com.hacman.gomunkulevolv.abilities.Vampireble;

public class VampireMainCreature extends MainCreature implements Vampireble {

    private double vampirePercent = 0.1;
    private final int vampireIndex = 3;

    public VampireMainCreature(MainCreature creature) {
        super(creature);
        this.getCurrentAbilityList().add(getPossibleAbilityList().get(vampireIndex));
    }

    public VampireMainCreature(int level, String name) {
        super(level, name);
    }
    @Override
    public void vampiric(int damage) {
        this.setCurHealth((int) (this.getCurHealth() + (damage * vampirePercent)));
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
