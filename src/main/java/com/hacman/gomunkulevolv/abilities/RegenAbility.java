package com.hacman.gomunkulevolv.abilities;

import com.hacman.gomunkulevolv.game.session.MainGomunkulEvolv;
import com.hacman.gomunkulevolv.object.Creature;
import javafx.concurrent.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegenAbility extends Ability {
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private int regenPerSecond = 2;

    private Creature mainCreature;

    public RegenAbility(int abilityClass, String title) {
        super(abilityClass, title);
    }

    public void onStartBattle(Creature creature) {
        this.mainCreature = creature;
        MainGomunkulEvolv.executorService.submit(this::regeneration);
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
