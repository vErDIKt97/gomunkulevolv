package com.hacman.gomunkulevolv.abilities;

import com.hacman.gomunkulevolv.object.Creature;

public class SpikeAbility extends Ability{

    private int spikeDamage = 5;
    public SpikeAbility(int abilityClass, String title) {
        super(abilityClass, title);
    }

    @Override
    public float onDefense(Creature curCreature, Creature enemy) {
        enemy.takeDamage(this.spikeDamage);
        System.out.println("return - " + this.spikeDamage + " damage");
        return 1;
    }

    @Override
    public boolean isAtkSuccess(Creature enemy) {
        return true;
    }

    @Override
    public void lvlUp() {
        super.lvlUp();
        this.spikeDamage *= this.getAbilityLevel();
    }
}
