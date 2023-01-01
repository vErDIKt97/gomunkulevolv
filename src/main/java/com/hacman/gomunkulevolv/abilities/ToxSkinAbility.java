package com.hacman.gomunkulevolv.abilities;

import com.hacman.gomunkulevolv.game.session.MainGomunkulEvolv;
import com.hacman.gomunkulevolv.object.Creature;

public class ToxSkinAbility extends Ability{
    private int toxinDamage = 1;

    public ToxSkinAbility(int abilityClass, String title) {
        super(abilityClass, title);
    }

    @Override
    public float onDefense(Creature enemy) {
        MainGomunkulEvolv.executorService.submit(() -> intoxicateEnemy(enemy));
        return super.onDefense(enemy);
    }

    @SuppressWarnings("BusyWait")
    private void intoxicateEnemy(Creature enemy) {
        int count = 0;
        while (enemy.isAlive()&& count<4) {
            enemy.takeDamage(toxinDamage);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            count++;
        }
    }

    @Override
    public void lvlUp() {
        super.lvlUp();
        this.toxinDamage *=2;
    }
}
