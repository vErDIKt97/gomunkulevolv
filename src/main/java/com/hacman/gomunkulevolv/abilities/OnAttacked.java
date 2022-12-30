package com.hacman.gomunkulevolv.abilities;

import com.hacman.gomunkulevolv.object.Creature;

public interface OnAttacked {
    boolean isAtkSuccess(Creature curCreature, Creature enemy);
}
