package com.hacman.gomunkulevolv.abilities;

import com.hacman.gomunkulevolv.object.Creature;
import org.jetbrains.annotations.NotNull;

public class VampireAbility extends Ability{

    private double vampireRate = 0.1;

    public VampireAbility(int abilityClass, String title) {
        super(abilityClass, title);
    }

    @Override
    public void onSuccessAttack(Creature creature, @NotNull Creature enemy) {
        restoreHealthByVampiring(creature);
    }

    private void restoreHealthByVampiring(Creature creature) {
        int curHealth = creature.getCurHealth();
        int damage = creature.getDamage();
        double restoredHealth = curHealth + damage * vampireRate;
        creature.setCurHealth((int) restoredHealth);
        System.out.println("vampire - " + damage * vampireRate + " health");
    }

    @Override
    public float onDefense(Creature curCreature, Creature enemy) {
        return 1;
    }

    @Override
    public void onStartBattle(Creature creature) {

    }

    @Override
    public boolean isAtkSuccess(Creature curCreature, Creature enemy) {
        return true;
    }
}
