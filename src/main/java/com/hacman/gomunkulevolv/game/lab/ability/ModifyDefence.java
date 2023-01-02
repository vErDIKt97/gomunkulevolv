package com.hacman.gomunkulevolv.game.lab.ability;

public class ModifyDefence extends LabAbility {
    private final LabAbilities id = LabAbilities.DEFENSE;
    public ModifyDefence() {
        this.setClassLabAbility(1);
        this.setTitle(getId().toString());
        this.setCost(150);
    }

    public LabAbilities getId() {
        return id;
    }
}
