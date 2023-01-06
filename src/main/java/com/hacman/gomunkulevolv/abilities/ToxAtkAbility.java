package com.hacman.gomunkulevolv.abilities;

import com.hacman.gomunkulevolv.game.session.FightSession;
import com.hacman.gomunkulevolv.object.Creature;

public class ToxAtkAbility extends Ability{
    private int toxinDamage = 1;

    public ToxAtkAbility(int abilityClass, String title) {
        super(abilityClass, title);
    }

    @Override
    public void onSuccessAttack(Creature creature, Creature enemy) {
        super.onSuccessAttack(creature, enemy);
        FightSession.executorService.submit(() -> intoxicateEnemy(enemy));

    }

    @SuppressWarnings("BusyWait")
    private void intoxicateEnemy(Creature enemy) {
        int count = 0;
        while (enemy.isInBattle() && count < 4){
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
