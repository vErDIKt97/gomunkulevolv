package com.hacman.gomunkulevolv.service;

import com.hacman.gomunkulevolv.Main;
import com.hacman.gomunkulevolv.game.session.LabSession;
import com.hacman.gomunkulevolv.object.MainCreature;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Properties;

public class Saver {
    private static final String savePath = "/saves/";
    private static final String fileName = "saveGame.xml";

/*    public static void saveGame(FightSession fightSession) {
        Properties saveGame = new Properties();
        MainCreature mainCreature = fightSession.getMainCreature();
        saveCreature(mainCreature, saveGame);
        for (int i = 0; i < fightSession.getEnemyCreatureList().size(); i++) {
            saveCreature("enemy" + i, fightSession.getEnemyCreatureList().get(i), saveGame);
        }
    }*/

    public static void saveGame(LabSession labSession) {
        Properties saveGame = new Properties();
        MainCreature mainCreature = labSession.getMainCreature();
//        saveCreature(mainCreature,saveGame);
        saveGame.setProperty("getGensCount", String.valueOf(labSession.getGensCount()));
        saveGame.setProperty("getCurrentLabAbilityMap", String.valueOf(labSession.getCurrentLabAbilityMap()));
        saveGame.setProperty("saveTime", String.valueOf(Calendar.getInstance().getTime()));
        try {
            FileOutputStream fos = new FileOutputStream(GameService.getPath(savePath, fileName));
            saveGame.storeToXML(fos, "SaveGame", String.valueOf(StandardCharsets.UTF_8));
            fos.close();
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    private static void saveCreature(String creatureName, Creature creature, Properties saveGame) {
        saveGame.setProperty(creatureName + " getName", creature.getName());
        saveGame.setProperty(creatureName + " getLevel", String.valueOf(creature.getLevel()));
        saveGame.setProperty(creatureName + " getCurHealth", String.valueOf(creature.getCurHealth()));
        saveGame.setProperty(creatureName + " getMaxHealth", String.valueOf(creature.getMaxHealth()));
        saveGame.setProperty(creatureName + " getDamage", String.valueOf(creature.getDamage()));
        saveGame.setProperty(creatureName + " getAtkSpeed", String.valueOf(creature.getAtkSpeed()));
    }

    private static void saveCreature(MainCreature creature, Properties saveGame) {
        saveCreature("mainCreature", creature, saveGame);
        saveGame.setProperty("mainCreature" + " getCurrentAbilityList", String.valueOf(creature.getCurrentAbilityList()));
        saveGame.setProperty("mainCreature" + " getCurExp", String.valueOf(creature.getCurExp()));
        saveGame.setProperty("mainCreature" + " getLvlGate", String.valueOf(creature.getLvlGate()));
    }*/
}
