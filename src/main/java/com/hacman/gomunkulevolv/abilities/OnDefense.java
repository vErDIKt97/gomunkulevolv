package com.hacman.gomunkulevolv.abilities;

import com.hacman.gomunkulevolv.object.Creature;

public interface OnDefense {
    /**
     * @param curCreature current Creature
     * @param enemy the creature with which the fight is going on
     * @return Damage Ratio
     */
    float onDefense(Creature curCreature, Creature enemy);
}
