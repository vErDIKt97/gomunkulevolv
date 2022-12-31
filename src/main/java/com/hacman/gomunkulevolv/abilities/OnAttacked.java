package com.hacman.gomunkulevolv.abilities;

import com.hacman.gomunkulevolv.object.Creature;

public interface OnAttacked {
    @SuppressWarnings("unused")
    boolean isAtkSuccess(Creature enemy);
}
