package com.hacman.gomunkulevolv.abilities;

import com.hacman.gomunkulevolv.object.Creature;

public class VampireAbility extends Ability{

    @SuppressWarnings("FieldCanBeLocal")
    private final double vampireRate = 0.1;

    public VampireAbility(int abilityClass, String title) {
        super(abilityClass, title);
    }

    public void onSuccessAttack(Creature creature, Creature enemy) {
        restoreHealthByVampiring(creature);
    }

    private void restoreHealthByVampiring(Creature creature) {
        int curHealth = creature.getCurHealth();
        int damage = creature.getDamage();
        double restoredHealth = curHealth + damage * vampireRate;
        creature.setCurHealth((int) restoredHealth);
        System.out.println("vampire - " + damage * vampireRate + " health");
    }

}
