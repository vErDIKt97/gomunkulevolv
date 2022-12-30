package com.hacman.gomunkulevolv.abilities;

import com.hacman.gomunkulevolv.object.Creature;
import org.jetbrains.annotations.NotNull;

public class ToxAtkAbility extends Ability{
    public ToxAtkAbility(int abilityClass, String title) {
        super(abilityClass, title);
    }

    @Override
    public void onSuccessAttack(Creature creature, @NotNull Creature enemy) {

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
        return  true;
    }
}
