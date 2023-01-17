package com.hacman.gomunkulevolv.service;

import com.hacman.gomunkulevolv.game.lab.ability.LabAbilities;
import com.hacman.gomunkulevolv.game.lab.ability.LabAbility;
import com.hacman.gomunkulevolv.game.session.LabSession;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class Loader {
    private static final String loadPath = "src/main/resources/saves/";

    private static Properties saveGame;
    public static LabSession loadGame() {
        LabSession labSession = new LabSession();
        labSession.setGensCount(Integer.parseInt(saveGame.getProperty("getGensCount")));
        labSession.setCurLabAbility(getMapLabAbilityFromSave(saveGame.getProperty("getCurrentLabAbilityMap")));
        return  labSession;
    }

    private static HashMap<LabAbilities, LabAbility> getMapLabAbilityFromSave(String abilities) {
        HashMap<LabAbilities, LabAbility> abilityHashMap = new HashMap<>();
        abilities = abilities.replace("{", "");
        abilities = abilities.replace("}", "");
        String[] keyAndObj = abilities.split(", ");
        for (String keyObj:
             keyAndObj) {
            String[] splitObj = keyObj.split("=");
            LabAbilities key = LabAbilities.getLabAbilityByTitle(splitObj[1]);
            LabAbility obj = LabAbility.newLabAbility(key);
            String[] ability = splitObj[1].split(" ");
            if (ability.length==5) {
                obj.setAbilityLevel(Integer.parseInt(ability[2]));
            } else obj.setAbilityLevel(Integer.parseInt(ability[1]));
            abilityHashMap.put(key, obj);
        }
        return abilityHashMap;
    }

    public static String readSaves() {
        saveGame = new Properties();
        FileInputStream fis;
        try {
            fis = new FileInputStream(loadPath + "saveGame.xml");
            saveGame.loadFromXML(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "MySave " + saveGame.getProperty("saveTime");
    }
}
