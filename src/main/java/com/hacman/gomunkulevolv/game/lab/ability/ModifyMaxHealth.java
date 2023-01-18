package com.hacman.gomunkulevolv.game.lab.ability;

import com.hacman.gomunkulevolv.object.MainCreature;

public class ModifyMaxHealth extends LabAbility {
    private final LabAbilities id = LabAbilities.MAX_HEALTH;
    public ModifyMaxHealth() {
        this.setClassLabAbility(1);
        this.setTitle(getId().toString());
        this.setCost(100);
    }

    @Override
    public void modifyCreature(MainCreature creature) {
        super.modifyCreature(creature);
        double modificationMaxHealth = creature.getMaxHealth() + this.getLabAbilityLevel()*50;
        creature.setMaxHealth(modificationMaxHealth);
        creature.setCurHealth(modificationMaxHealth);
    }

    public LabAbilities getId() {
        return id;
    }
}
