package com.hacman.gomunkulevolv.game.lab.ability;

public class ModifyAtkSpeed extends LabAbility {
    private final LabAbilities id = LabAbilities.ATTACK_SPEED;
    public ModifyAtkSpeed() {
        this.setClassLabAbility(1);
        this.setTitle(getId().toString());
        this.setCost(500);
    }

    public LabAbilities getId() {
        return id;
    }
}
