package com.hacman.gomunkulevolv.game;

import com.hacman.gomunkulevolv.abilities.PlayableCharacter;
import com.hacman.gomunkulevolv.controller.Battle;
import com.hacman.gomunkulevolv.controller.EnemyCreator;
import com.hacman.gomunkulevolv.object.Creature;
import com.hacman.gomunkulevolv.object.MainCreature;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class MainGomunkulEvolv {
    private int enemyLevel = 1;
    private float progress = 1;
    private PlayableCharacter mainCreature = new MainCreature(1, "Gomunkul");
    private final EnemyCreator enemyCreator;
    private final ArrayList<Creature> enemyCreatureList;
    private Thread mainCharThread;
    private Thread enemyCharThread;

    public PlayableCharacter getMainCreature() {
        return mainCreature;
    }

    public ArrayList<Creature> getEnemyCreatureList() {
        return enemyCreatureList;
    }

    public void setMainCreature(PlayableCharacter mainCreature) {
        this.mainCreature = mainCreature;
    }

    //Метод битвы между существами
    public void fight(Text mainCharText, Text enemyCharText, Text enemyText1, TextArea battleTextArea, ArrayList<Text> enemies, Button fightButton, Button levelUpButton) {
        levelUpButton.setDisable(true);
        changeButtonAvailable(fightButton);
        battleTextArea.setText("");
        Creature enemy = enemyCreatureList.get(0);
        mainCharThread = new Thread(new Battle(mainCreature.getCreature(),enemy,battleTextArea,enemyCharThread));
        enemyCharThread = new Thread(new Battle(enemy,mainCreature.getCreature(),battleTextArea,mainCharThread));

        mainCharThread.start();
        enemyCharThread.start();
        new Thread(() -> {
            while (mainCharThread.isAlive() && enemyCharThread.isAlive()) {
                refreshTextMainAndEnemyChar(mainCharText, mainCreature, enemyCharText, enemy, enemyText1);
            }
            Battle.checkWin(mainCreature, enemy, battleTextArea);
            refreshTextMainAndEnemyChar(mainCharText, mainCreature, enemyCharText, enemy, enemyText1);
            refreshEnemyList(enemies, enemyText1);
            changeButtonAvailable(fightButton);
            if (mainCreature.getSkillPoint() > 0) {
                changeButtonAvailable(levelUpButton);
            }
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

    public MainGomunkulEvolv(ArrayList<Text> enemies, Text enemyText1) {
        if (progress % 5 == 0)
            enemyLevel += 1;
        enemyCreator = new EnemyCreator(enemies, enemyLevel, enemyText1);
        enemyCreatureList = enemyCreator.getCreatures();
    }
}
