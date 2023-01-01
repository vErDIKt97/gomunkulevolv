package com.hacman.gomunkulevolv.abilities;

import com.hacman.gomunkulevolv.object.Creature;
import org.jetbrains.annotations.NotNull;

public class SpikeAbility extends Ability{

    private int spikeDamage = 5;
    public SpikeAbility(int abilityClass, String title) {
        super(abilityClass, title);
    }

    public float onDefense(@NotNull Creature enemy) {
        enemy.takeDamage(this.spikeDamage);
        return 1;
    }

    @Override
    public void lvlUp() {
        super.lvlUp();
        this.spikeDamage *= this.getAbilityLevel();
    }
}
