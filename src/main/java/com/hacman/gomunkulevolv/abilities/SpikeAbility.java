package com.hacman.gomunkulevolv.abilities;

import com.hacman.gomunkulevolv.object.Creature;
import org.jetbrains.annotations.NotNull;

public class SpikeAbility extends Ability{

    private int spikeDamage = 5;
    public SpikeAbility(int abilityClass, String title) {
        super(abilityClass, title);
    }

    @Override
    public void onSuccessAttack(Creature creature, @NotNull Creature enemy) {

    }

    @Override
    public float onDefense(Creature curCreature, Creature enemy) {
        enemy.takeDamage(this.spikeDamage);
        System.out.println("return - " + this.spikeDamage + " damage");
        return 1;
    }

    @Override
    public void onStartBattle(Creature creature) {

    }

    @Override
    public boolean isAtkSuccess(Creature curCreature, Creature enemy) {
        return true;
    }

    @Override
    public void lvlUp() {
        super.lvlUp();
        this.spikeDamage *= this.getAbilityLevel();
    }
}
