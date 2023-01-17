package com.hacman.gomunkulevolv.controller;

import com.hacman.gomunkulevolv.abilities.PlayableCharacter;
import com.hacman.gomunkulevolv.object.Creature;
import com.hacman.gomunkulevolv.object.MainCreature;
import com.hacman.gomunkulevolv.service.GameService;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.util.concurrent.Future;

public class Battle implements Runnable {
    private final Creature mainCreature;
    private final Creature enemy;
    private final TextArea battleTextArea;

    public static boolean battleEnd;

    public void setEnemyCharThread(Future<?> enemyCharThread) {
        this.enemyCharThread = enemyCharThread;
    }

    private Future<?> enemyCharThread;

    @SuppressWarnings("ClassEscapesDefinedScope")
    public Battle(Creature mainCreature, Creature enemy, TextArea battleTextArea, Future<?> enemyCharThread) {
        this.mainCreature = mainCreature;
        this.enemy = enemy;
        this.battleTextArea = battleTextArea;
        this.enemyCharThread = enemyCharThread;
        this.enemy.setInBattle(true);
        this.mainCreature.setInBattle(true);
    }

    private static void setCaretOnEnd(TextArea battleTextArea) {
        battleTextArea.positionCaret(battleTextArea.getText().length());
    }


    @SuppressWarnings("ClassEscapesDefinedScope")
    public static boolean checkWin(PlayableCharacter mainCreature, TextArea battleTextArea) {
        boolean result = false;
        if (mainCreature.getCreature().isAlive()) {
            battleTextArea.setText(addBattleLine(battleTextArea.getText(), mainCreature.getCreature().getName() + " WIN!"));
            result = true;
        } else {
            battleTextArea.setText(addBattleLine(battleTextArea.getText(), "YOU LOSE!"));
        }
        setCaretOnEnd(battleTextArea);
        return result;
    }

    @Override
    public void run() {
        while (enemy.isAlive() && mainCreature.isAlive()) {
            attackDelay();
            Platform.runLater(() -> {
                if (!isBattleEnd()) {
                    battleTextArea.setText(addBattleLine(battleTextArea.getText(), getAttack()));
                    setCaretOnEnd(battleTextArea);
                }
            });
        }
        this.mainCreature.setInBattle(false);
        this.enemy.setInBattle(false);
        try {
            enemyCharThread.cancel(true);
        } catch (Exception e) {
            System.out.println(enemyCharThread.isCancelled());
        }
    }

    private void attackDelay() {
        try {
            Thread.sleep(mainCreature.getAtkSpeed());
        } catch (InterruptedException e) {
            System.out.println("Thread Dead");
            mainCreature.setInBattle(false);
            enemy.setInBattle(false);
        } catch (NullPointerException e) {
            System.out.println("Nothing to kill");
        }
    }

    private String getAttack() {
        if (mainCreature.getClass().equals(MainCreature.class)) {
            battleTextArea.fireEvent(new GameService.MyEvent(GameService.MyEvent.MAIN_CREATURE_ATTACK));
        }else if (mainCreature.getClass().equals(Creature.class))
            battleTextArea.fireEvent(new GameService.MyEvent(GameService.MyEvent.ENEMY_CREATURE_ATTACK));
        double oldHealth = enemy.getCurHealth();
        if (successAttack(mainCreature, enemy))
            return mainCreature.getName() + " attack " + enemy.getName() + " on " + String.format("%.2f",oldHealth - enemy.getCurHealth());
        else return mainCreature.getName() + " fault attack " + enemy.getName();
    }

    @SuppressWarnings("ClassEscapesDefinedScope")
    public static boolean successAttack(Creature mainCreature, Creature enemy) {
        return mainCreature.attack(enemy);
    }

    private static String addBattleLine(String firstLine, String secondLine) {
        return firstLine + "\n" + secondLine;
    }

    public static boolean isBattleEnd() {
        return battleEnd;
    }

    public static void setBattleEnd(boolean battleEnd) {
        Battle.battleEnd = battleEnd;
    }
}

