package com.hacman.gomunkulevolv.game.lab.ability;

import com.hacman.gomunkulevolv.object.MainCreature;

public class ModifyAtkSpeed extends LabAbility {
    private final LabAbilities id = LabAbilities.ATTACK_SPEED;
    public ModifyAtkSpeed() {
        this.setClassLabAbility(1);
        this.setTitle(getId().toString());
        this.setCost(500);
    }

    @Override
    public void modifyCreature(MainCreature creature) {
        super.modifyCreature(creature);
        double modificationAtkSpeed = creature.getAtkSpeed() - this.getLabAbilityLevel()*0.2;
        creature.setAtkSpeed(modificationAtkSpeed);
    }

    public LabAbilities getId() {
        return id;
    }
}
