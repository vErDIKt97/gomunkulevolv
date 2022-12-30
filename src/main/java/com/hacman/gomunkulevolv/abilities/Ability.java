package com.hacman.gomunkulevolv.abilities;

import com.hacman.gomunkulevolv.object.Creature;
import org.jetbrains.annotations.NotNull;

public abstract class Ability implements OnSuccessAttack, OnDefense, OnStartBattle, OnAttacked {

    private String title;
    private int abilityClass;
    private int abilityLevel;

    @Override
    public String toString() {
        return title + " " + abilityLevel;
    }

    public Ability(int abilityClass, String title) {
        this.abilityClass = abilityClass;
        this.title = title;
        this.abilityLevel = 0;
    }

    public static Ability newAbility(@NotNull PossibleAbility title) {
        switch (title) {
            case EVADE -> {
                return new EvadeAbility(1, "Evading");
            }
            case SPIKE -> {
                return new SpikeAbility(1,"Spike");
            }
            case TOXIC_ATTACK -> {
                return new ToxAtkAbility(1, "Toxic Attack");
            }
            case TOXIC_SKIN -> {
                return new ToxSkinAbility(1, "Toxic Skin");
            }
            case VAMPIRE -> {
                return new VampireAbility(1, "Vampire");
            }
            case REGENERATION -> {
                return new RegenAbility(1, "Regeneration");
            }
        }
        return null;
    }

    public int getClassAbility() {
        return this.abilityClass;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAbilityClass(int abilityClass) {
        this.abilityClass = abilityClass;
    }

    public int getAbilityLevel() {
        return abilityLevel;
    }

    public void setAbilityLevel(int abilityLevel) {
        this.abilityLevel = abilityLevel;
    }

    public void lvlUp() {
        this.abilityLevel++;
    }

    @Override
    public boolean isAtkSuccess(Creature curCreature, Creature enemy) {
        return true;
    }

    @Override
    public float onDefense(Creature curCreature, Creature enemy) {
        return 1;
    }

    @Override
    public void onSuccessAttack(Creature creature, @NotNull Creature enemy) {

    }

    @Override
    public void onStartBattle(Creature creature) {

    }

    public boolean equals(Ability obj) {
        return this.title.equals(obj.title);
    }


}
