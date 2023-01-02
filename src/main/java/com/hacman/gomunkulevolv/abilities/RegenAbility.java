package com.hacman.gomunkulevolv.abilities;

import com.hacman.gomunkulevolv.game.session.FightSession;
import com.hacman.gomunkulevolv.object.Creature;

public class RegenAbility extends Ability {
    private int regenPerSecond = 2;

    private Creature mainCreature;

    public RegenAbility(int abilityClass, String title) {
        super(abilityClass, title);
    }

    public void onStartBattle(Creature creature) {
        this.mainCreature = creature;
        FightSession.executorService.submit(this::regeneration);
    }


    @SuppressWarnings("BusyWait")
    public void regeneration() {
        while (mainCreature.isInBattle()) {
            mainCreature.setCurHealth(mainCreature.getCurHealth() + regenPerSecond);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Error while regen");
            }
        }
    }

    @Override
    public void lvlUp() {
        super.lvlUp();
        this.regenPerSecond *= this.getAbilityLevel();
    }
}
