package com.hacman.gomunkulevolv.game.lab.ability;

import com.hacman.gomunkulevolv.object.Creature;
import com.hacman.gomunkulevolv.object.MainCreature;

public class ModifyAttack extends LabAbility{
    private final LabAbilities id = LabAbilities.ATTACK;

    public ModifyAttack() {
        this.setClassLabAbility(1);
        this.setTitle(getId().toString());
        this.setCost(50);
    }

    public LabAbilities getId() {
        return id;
    }

    @SuppressWarnings("unused")
    @Override
    public void modifyCreature(MainCreature creature) {
        super.modifyCreature(creature);
        creature.setAbModifyDamage(getLabAbilityLevel());
    }
}
