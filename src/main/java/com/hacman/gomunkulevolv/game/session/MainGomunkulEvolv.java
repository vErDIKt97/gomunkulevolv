package com.hacman.gomunkulevolv.game.session;

import com.hacman.gomunkulevolv.abilities.PlayableCharacter;
import com.hacman.gomunkulevolv.controller.Battle;
import com.hacman.gomunkulevolv.controller.EnemyCreator;
import com.hacman.gomunkulevolv.object.Creature;
import com.hacman.gomunkulevolv.object.MainCreature;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainGomunkulEvolv {

    @SuppressWarnings("unused")
    public static boolean sessionOver;
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private Creature curEnemy;
    private int enemyLevel = 1;
    private float progress = 1;
    private final PlayableCharacter mainCreature = new MainCreature(1, "Hero");
    private final EnemyCreator enemyCreator;
    private final ArrayList<Creature> enemyCreatureList;
    private Future<?> mainCharThread;
    private Future<?> enemyCharThread;

    public MainGomunkulEvolv(ArrayList<Text> enemies, Text enemyText1) {
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

    public void fight(Text mainCharText, Text enemyCharText, Text enemyText1, TextArea battleTextArea, ArrayList<Text> enemies, Button fightButton, Button levelUpButton) {
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
                refreshTextMainAndEnemyChar(mainCharText, mainCreature, enemyCharText, curEnemy, enemyText1);
            }
            Battle.setBattleEnd(true);
            Platform.runLater(() -> {
                Battle.checkWin(mainCreature, curEnemy, battleTextArea);
                refreshTextMainAndEnemyChar(mainCharText, mainCreature, enemyCharText, curEnemy, enemyText1);
            });
            refreshTextMainAndEnemyChar(mainCharText, mainCreature, enemyCharText, curEnemy, enemyText1);
            refreshEnemyList(enemies, enemyText1);
            changeButtonAvailable(fightButton);
            changeButtonAvailable(levelUpButton);
        }).start();
    }

    private void changeButtonAvailable(Button fightButton) {
        fightButton.setDisable(!fightButton.isDisable());
    }

    private void refreshTextMainAndEnemyChar(Text mainCharText, PlayableCharacter mainCreature, Text enemyCharText, Creature enemy, Text enemyText1) {
        mainCharText.setText(mainCreature.toString());
        enemyCharText.setText(enemy.toString());
        enemyText1.setText(enemy.toString());
    }

    private void refreshEnemyList(ArrayList<Text> enemyCreatureList, Text enemyText1) {
        if (progress % 5 == 0) {
            enemyLevel += 1;
        }
        progress++;
        this.enemyCreator.refreshListEnemy(enemyCreatureList, enemyLevel, enemyText1);
    }
}
