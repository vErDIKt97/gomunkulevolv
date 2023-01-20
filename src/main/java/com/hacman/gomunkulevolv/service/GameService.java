package com.hacman.gomunkulevolv.service;

import com.hacman.gomunkulevolv.Main;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public class GameService {
    private static final String path = "/stageSettings/";
    private static final String filename = "settings.xml";
    private static double stageHeight = 768;
    private static double stageWidth = 1024;
    private static boolean stageFullScreen = false;
    private static KeyCombination stageFullScreenExitKeyCombination = KeyCombination.NO_MATCH;

    public static Node getNodeByRowColumnIndex(final int column, final int row, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> children = gridPane.getChildren();

        for (Node node : children) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }

    public static String getFormatDouble(Double i) {
        return String.format("%.2f", i);
    }

    public static void saveSettings(Stage stage) throws IOException {
        Properties properties = new Properties();
        FileOutputStream fos;
        properties.setProperty("getWidth", String.valueOf(stage.getWidth()));
        properties.setProperty("getHeight", String.valueOf(stage.getHeight()));
        properties.setProperty("isFullScreen", String.valueOf(stage.isFullScreen()));
        try {
            fos = new FileOutputStream(getPath(path ,filename));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        properties.storeToXML(fos, "Settings", String.valueOf(StandardCharsets.UTF_8));
        fos.close();
    }

    public static String loadSettings() {
        Properties properties = new Properties();
        try {
            properties.loadFromXML(new FileInputStream(getPath(path,filename)));
            stageHeight = Double.parseDouble(properties.getProperty("getHeight"));
            stageWidth = Double.parseDouble(properties.getProperty("getWidth"));
            stageFullScreen = Boolean.parseBoolean(properties.getProperty("isFullScreen"));
            stageFullScreenExitKeyCombination = KeyCombination.NO_MATCH;
            return "Success";
        } catch (IOException | URISyntaxException e) {
            return "Error";
        }
    }

    @NotNull
    public static String getPath(String path, String filename) throws URISyntaxException {
        StringBuilder result;
        result = new StringBuilder(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        result = new StringBuilder(result.toString().replace("[", ""));
        result = new StringBuilder(result.toString().replace("]", ""));
        ArrayList<String> split = new ArrayList<>(Arrays.asList(result.toString().split("/")));
        split.remove(split.size() - 1);
        split.remove(split.size() - 1);
        result = new StringBuilder(split.get(0)).append(split.get(1));
        for (int i = 2; i < split.size(); i++) {
            result.append("/").append(split.get(i));
        }
        File file = new File(result + path);
        if (!file.exists())
            file.mkdir();
        return result + path + filename;
    }

    public static void applyStageSettings(Stage stage) {
        stage.setWidth(stageWidth);
        stage.setHeight(stageHeight);
        stage.setFullScreen(stageFullScreen);
        stage.setFullScreenExitKeyCombination(stageFullScreenExitKeyCombination);
    }

    public static class MyEvent extends Event {

        public static final EventType<MyEvent> DEFEAT_ENEMY = new EventType<>(Event.ANY, "DEFEAT_ENEMY");
        public static final EventType<MyEvent> MAIN_CREATURE_ATTACK = new EventType<>(ANY, "MAIN_CREATURE_ATTACK");
        public static final EventType<MyEvent> ENEMY_CREATURE_ATTACK = new EventType<>(ANY, "ENEMY_CREATURE_ATTACK");

        @SuppressWarnings("unchecked")
        public MyEvent(EventType eventType) {
            super(eventType);
        }

        @SuppressWarnings("unchecked")
        @Override
        public EventType<? extends MyEvent> getEventType() {
            return (EventType<? extends MyEvent>) super.getEventType();
        }

    }
}
