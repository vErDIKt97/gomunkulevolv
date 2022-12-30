package com.hacman.gomunkulevolv.controller;
import com.hacman.gomunkulevolv.object.Creature;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
public class EnemyCreator {
    final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";
    private final ArrayList<Creature> creatures = new ArrayList<>();
    final java.util.Random rand = new java.util.Random();

    private static final int enemyArrayMaxLength = 5;

    // consider using a Map<String,Boolean> to say whether the identifier is being used or not
    final Set<String> identifiers = new HashSet<>();

    public EnemyCreator(ArrayList<Text> enemies, int enemyLevel, Text enemyText1) {
        refreshListEnemy(enemies, enemyLevel, enemyText1);
    }

    public String randomIdentifier() {
        StringBuilder builder = new StringBuilder();
        while (builder.toString().length() == 0) {
            int length = rand.nextInt(5) + 5;
            for (int i = 0; i < length; i++) {
                builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
            }
            if (identifiers.contains(builder.toString())) {
                builder = new StringBuilder();
            }
        }
        return builder.toString();
    }

    public ArrayList<Creature> getCreatures() {
        return creatures;
    }

    public void refreshListEnemy(ArrayList<Text> enemies, int level, Text enemyText1) {
        checkAlive();
        while (creatures.size() < enemyArrayMaxLength) {
            creatures.add(new Creature(level, randomIdentifier()));
        }
        for (int j = 0; j < creatures.size(); j++) {
            enemies.get(j).setText(creatures.get(j).toString());
        }
        enemyText1.setText(creatures.get(0).toString());
    }

    private void checkAlive() {
        creatures.removeIf(creature -> !creature.isAlive());
    }
}
