package com.hacman.gomunkulevolv.game.session;

import com.hacman.gomunkulevolv.abilities.PlayableCharacter;
import com.hacman.gomunkulevolv.controller.Battle;
import com.hacman.gomunkulevolv.controller.EnemyCreator;
import com.hacman.gomunkulevolv.object.Creature;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FightSession {
    public static boolean sessionOver;
    public static final ExecutorService executorService = Executors.newCachedThreadPool();
    private Creature curEnemy;
    private int enemyLevel = 1;
    private float progress = 1;
    private final PlayableCharacter mainCreature;
    private static EnemyCreator enemyCreator;
    private ArrayList<Creature> enemyCreatureList;
    private Future<?> mainCharThread;
    private Future<?> enemyCharThread;
    private int earnedGens = 0;

    public FightSession(PlayableCharacter mainCreature) {
        this.mainCreature = mainCreature;
    }

    public int getEarnedGens() {
        return earnedGens;
    }

    public void Init(ArrayList<Text> enemies, Text enemyText1) {
        if (progress % 5 == 0)
            enemyLevel += 1;
        enemyCreator = new EnemyCreator(enemies, enemyLevel, enemyText1);
        enemyCreatureList = enemyCreator.getCreatures();
        curEnemy = enemyCreatureList.get(0);
        sessionOver = false;
    }

    public PlayableCharacter getMainCreature() {
        return mainCreature;
    }

    public ArrayList<Creature> getEnemyCreatureList() {
        return enemyCreatureList;
    }

    //Метод битвы между существами

    public void fight(Text mainCharText,
                      Text enemyCharText,
                      Text enemyText1,
                      TextArea battleTextArea,
                      ArrayList<Text> enemies,
                      Button fightButton,
                      Button levelUpButton,
                      Text textGens) {
        levelUpButton.setDisable(true);
        changeButtonAvailable(fightButton);
        battleTextArea.setText("");
        Battle.setBattleEnd(false);
        curEnemy = enemyCreatureList.get(0);
        Battle mainBattle = new Battle(mainCreature.getCreature(), curEnemy, battleTextArea, enemyCharThread);
        Battle enemyBattle = new Battle(curEnemy, mainCreature.getCreature(), battleTextArea, mainCharThread);
        mainBattle.setEnemyCharThread(enemyCharThread);
        mainCharThread = executorService.submit(mainBattle);
        enemyCharThread = executorService.submit(enemyBattle);
        new Thread(() -> {
            while (mainCharThread.state() == Future.State.RUNNING && enemyCharThread.state() == Future.State.RUNNING) {
                refreshTextMainAndEnemyChar(mainCharText, mainCreature, enemyCharText, curEnemy, enemyText1, textGens);
            }
            Battle.setBattleEnd(true);
            Platform.runLater(() -> {
                boolean win = Battle.checkWin(mainCreature, curEnemy, battleTextArea);
                battleResult(win, curEnemy, fightButton, levelUpButton);
                refreshTextMainAndEnemyChar(mainCharText, mainCreature, enemyCharText, curEnemy, enemyText1, textGens);
            });
            refreshTextMainAndEnemyChar(mainCharText, mainCreature, enemyCharText, curEnemy, enemyText1, textGens);
            refreshEnemyList(enemies, enemyText1);
            changeButtonAvailable(fightButton);
            changeButtonAvailable(levelUpButton);
        }).start();
    }

    private void battleResult(boolean win, Creature enemy, Button fightButton, Button levelUpButton) {
        if (win) {
            this.earnedGens += enemy.getLevel() * 10;
        } else {
            fightButton.setVisible(false);
            levelUpButton.setVisible(false);
        }
    }

    private void changeButtonAvailable(Button fightButton) {
        fightButton.setDisable(!fightButton.isDisable());
    }

    private void refreshTextMainAndEnemyChar(Text mainCharText,
                                             PlayableCharacter mainCreature,
                                             Text enemyCharText,
                                             Creature enemy,
                                             Text enemyText1, Text textGens) {
        mainCharText.setText(mainCreature.toString());
        enemyCharText.setText(enemy.toString());
        enemyText1.setText(enemy.toString());
        textGens.setText(String.valueOf(earnedGens));
    }

    private void refreshEnemyList(ArrayList<Text> enemyCreatureList, Text enemyText1) {
        if (progress % 5 == 0) {
            enemyLevel += 1;
        }
        progress++;
        enemyCreator.refreshListEnemy(enemyCreatureList, enemyLevel, enemyText1);
    }
}
