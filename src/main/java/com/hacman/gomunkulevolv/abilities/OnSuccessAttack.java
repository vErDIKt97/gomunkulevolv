package com.hacman.gomunkulevolv.abilities;

import com.hacman.gomunkulevolv.object.Creature;
import org.jetbrains.annotations.NotNull;

public interface OnSuccessAttack {
    /**
     * called after success attack
     */
    @SuppressWarnings("unused")
    void onSuccessAttack(Creature creature, @NotNull Creature enemy);
}
