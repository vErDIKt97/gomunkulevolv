package com.hacman.gomunkulevolv.controller;

import com.hacman.gomunkulevolv.abilities.PlayableCharacter;
import com.hacman.gomunkulevolv.object.Creature;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class Battle implements Runnable {
    private Creature mainCreature;
    private Creature enemy;
    private TextArea battleTextArea;
    private Thread enemyCharThread;

    public Battle(Creature mainCreature, Creature enemy, TextArea battleTextArea, Thread enemyCharThread) {
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

    public static void checkWin(PlayableCharacter mainCreature, Creature enemy, TextArea battleTextArea) {
        if (mainCreature.getCreature().isAlive()) {
            battleTextArea.setText(addBattleLine(battleTextArea.getText(), mainCreature.getCreature().getName() + " WIN!"));
            mainCreature.defEnemy(enemy);
        } else {
            battleTextArea.setText(addBattleLine(battleTextArea.getText(), "YOU LOSE!"));
        }
        setCaretOnEnd(battleTextArea);
    }

    @Override
    public void run() {
        while (enemy.isAlive() && mainCreature.isAlive()) {
            Platform.runLater(() -> {
                battleTextArea.setText(addBattleLine(battleTextArea.getText(), getAttack()));
                setCaretOnEnd(battleTextArea);
            });
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
        this.mainCreature.setInBattle(false);
        this.enemy.setInBattle(false);
        try {
            enemyCharThread.interrupt();
        } catch (Exception e) {
            System.out.println("Nothing to interrupt");
        }
    }

    private String getAttack() {
        if (successAttack(mainCreature, enemy))
            return mainCreature.getName() + " attack " + enemy.getName() + " on " + mainCreature.getDamage();
        else return mainCreature.getName() + " fault attack " + enemy.getName();
    }

    public static boolean successAttack(Creature mainCreature, Creature enemy) {
        return mainCreature.attack(enemy);
    }

    private static String addBattleLine(String firstLine, String secondLine) {
        return firstLine + "\n" + secondLine;
    }


}

