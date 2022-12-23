package com.hacman.gomunkulevolv.object;

public class SpikeMainCreature extends  MainCreature {

    int spikeIndex = 2;
    int spikeDamage = getPossibleAbilityList().get(spikeIndex).getLevel() * 5;
    public SpikeMainCreature(MainCreature mainCreature) {
        super(mainCreature);
        this.getCurrentAbilityList().add(getPossibleAbilityList().get(spikeIndex));
    }

    @Override
    public boolean takeDamage(Creature enemy) {
            return (super.takeDamage(enemy) && returnDamage(enemy));

    }

    private boolean returnDamage(Creature enemy) {
        return enemy.takeDamage(spikeDamage);
    }
}
