package com.hacman.gomunkulevolv.game.lab.ability;

public class ModifyMaxHealth extends LabAbility {
    private final LabAbilities id = LabAbilities.MAX_HEALTH;
    public ModifyMaxHealth() {
        this.setClassLabAbility(1);
        this.setTitle(getId().toString());
        this.setCost(100);
    }

    public LabAbilities getId() {
        return id;
    }
}
