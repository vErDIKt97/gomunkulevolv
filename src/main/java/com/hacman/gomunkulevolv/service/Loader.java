package com.hacman.gomunkulevolv.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Loader {
    private static final String loadPath = "src/main/resources/saves/";

    public static void loadGame() {

    }

    public static String readSaves() {
        Properties properties = new Properties();
        FileInputStream fis;
        try {
            fis = new FileInputStream(loadPath);
            properties.loadFromXML(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "MySave " + properties.getProperty("saveTime");
    }
}
