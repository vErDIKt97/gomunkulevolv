package com.hacman.gomunkulevolv.game.lab.ability;

import com.hacman.gomunkulevolv.object.MainCreature;

public class ModifyDefence extends LabAbility {
    private final LabAbilities id = LabAbilities.DEFENSE;
    public ModifyDefence() {
        this.setClassLabAbility(1);
        this.setTitle(getId().toString());
        this.setCost(150);
    }

    @Override
    public void modifyCreature(MainCreature creature) {
        super.modifyCreature(creature);
        creature.setDefence(creature.getDefence() + getLabAbilityLevel()*2);
    }

    public LabAbilities getId() {
        return id;
    }
}
