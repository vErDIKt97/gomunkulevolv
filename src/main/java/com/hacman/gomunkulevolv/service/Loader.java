package com.hacman.gomunkulevolv.service;

import com.hacman.gomunkulevolv.game.lab.ability.LabAbilities;
import com.hacman.gomunkulevolv.game.lab.ability.LabAbility;
import com.hacman.gomunkulevolv.game.session.LabSession;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Properties;

public class Loader {
    private static final String loadPath = "/saves/";
    private static final String filename = "saveGame.xml";

    private static Properties saveGame;

    public static LabSession loadGame() {
        LabSession labSession = new LabSession();
        labSession.setGensCount(Integer.parseInt(saveGame.getProperty("getGensCount")));
        labSession.setCurLabAbility(getMapLabAbilityFromSave(saveGame.getProperty("getCurrentLabAbilityMap")));
        return labSession;
    }

    private static HashMap<LabAbilities, LabAbility> getMapLabAbilityFromSave(String abilities) {
        HashMap<LabAbilities, LabAbility> abilityHashMap = new HashMap<>();
        if (!abilities.equals("{}")) {
            abilities = abilities.replace("{", "");
            abilities = abilities.replace("}", "");
            String[] keyAndObj = abilities.split(", ");
            for (String keyObj :
                    keyAndObj) {
                String[] splitObj = keyObj.split("=");
                LabAbilities key = LabAbilities.getLabAbilityByTitle(splitObj[1]);
                LabAbility obj = LabAbility.newLabAbility(key);
                String[] ability = splitObj[1].split(" ");
                if (ability.length == 5) {
                    obj.setAbilityLevel(Integer.parseInt(ability[2]));
                } else obj.setAbilityLevel(Integer.parseInt(ability[1]));
                abilityHashMap.put(key, obj);
            }
        }
        return abilityHashMap;
    }

    public static String readSaves() {
        saveGame = new Properties();
        try {
            File file = new File(GameService.getPath(loadPath, filename));
            if (file.exists()) {
                saveGame.loadFromXML(new FileInputStream(file));
                return "MySave " + saveGame.getProperty("saveTime");
            }
        } catch (RuntimeException | IOException | URISyntaxException e) {
            System.out.println("File not exist!");
        }
        return "No saves";
    }


}
