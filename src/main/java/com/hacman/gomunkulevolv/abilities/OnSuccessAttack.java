package com.hacman.gomunkulevolv.abilities;

import com.hacman.gomunkulevolv.object.Creature;
import org.jetbrains.annotations.NotNull;

public interface OnSuccessAttack {
    /**
     * called after success attack
     */
    void onSuccessAttack(Creature creature, @NotNull Creature enemy);
}
