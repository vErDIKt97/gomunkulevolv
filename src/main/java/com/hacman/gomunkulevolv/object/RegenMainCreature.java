package com.hacman.gomunkulevolv.object;

public class RegenMainCreature extends MainCreature {

    private final int regenIndex = 1;
    private int regenPerSecond;
    private Thread regen;

    public RegenMainCreature(MainCreature mainCreature) {
        super(mainCreature);
        refreshRegen();
        refreshRegenThread();
        this.getCurrentAbilityList().add(getPossibleAbilityList().get(regenIndex));
    }

    private void refreshRegen() {
        regenPerSecond = getPossibleAbilityList().get(regenIndex).getLevel() * 3;
    }

    @Override
    public synchronized void setInBattle(boolean inBattle) {
        if (inBattle && !regen.isAlive()) {
            regen.start();
        } else if (!inBattle) {
            regen.interrupt();
            refreshRegenThread();
        }
        super.setInBattle(inBattle);
    }

    private void refreshRegenThread() {
        if (regen!=null) {
            regen.interrupt();
        } else {
        }
        regen = null;
        regen = new Thread(() -> {
            while (isAlive()) {
                if (isInBattle()) {
                    this.setCurHealth(this.getCurHealth() + regenPerSecond);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Error after refresh");
                    break;
                }
            }
        });
    }
}
