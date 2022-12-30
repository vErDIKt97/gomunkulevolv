package com.hacman.gomunkulevolv.abilities;

import com.hacman.gomunkulevolv.object.Creature;
import javafx.concurrent.Task;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegenAbility extends Ability {
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private int regenPerSecond = 2;

    private Creature mainCreature;

    public RegenAbility(int abilityClass, String title) {
        super(abilityClass, title);
    }

    @Override
    public void onSuccessAttack(Creature creature, @NotNull Creature enemy) {

    }

    @Override

    public float onDefense(Creature curCreature, Creature enemy) {
        return 1;
    }

    @Override
    public void onStartBattle(Creature creature) {
        this.mainCreature = creature;
        executorService.submit(new Task<>() {
            @Override
            protected Object call() {
                regeneration();
                return null;
            }
        });
    }

    @Override
    public boolean isAtkSuccess(Creature curCreature, Creature enemy) {
        return true;
    }


    public void regeneration() {
        while (mainCreature.isInBattle()) {
            mainCreature.setCurHealth(mainCreature.getCurHealth() + regenPerSecond);
            System.out.println("regen -" + regenPerSecond + " health");
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
