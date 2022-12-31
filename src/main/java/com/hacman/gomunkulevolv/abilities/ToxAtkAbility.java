package com.hacman.gomunkulevolv.abilities;

import com.hacman.gomunkulevolv.object.Creature;

public class ToxAtkAbility extends Ability{
    public ToxAtkAbility(int abilityClass, String title) {
        super(abilityClass, title);
    }

    @Override
    public float onDefense(Creature curCreature, Creature enemy) {
        return 1;
    }

    @Override
    public boolean isAtkSuccess(Creature enemy) {
        return  true;
    }
}
