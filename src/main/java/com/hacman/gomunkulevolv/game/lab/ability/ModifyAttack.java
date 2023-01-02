package com.hacman.gomunkulevolv.game.lab.ability;

import com.hacman.gomunkulevolv.object.Creature;

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
    public void modifyCreature(Creature creature) {
        super.modifyCreature(creature);
        creature.setDamage(creature.getDamage()+getLabAbilityLevel());
    }
}
