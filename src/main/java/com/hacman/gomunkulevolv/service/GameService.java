package com.hacman.gomunkulevolv.service;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class GameService {
    private static final String filename = "src/main/resources/stageSettings/settings.xml";
    private static double stageHeight;
    private static double stageWidth;
    private static boolean stageFullScreen;
    private static KeyCombination stageFullScreenExitKeyCombination;

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
        fos = new FileOutputStream(filename);
        properties.storeToXML(fos, "Settings", StandardCharsets.UTF_8);
        fos.close();
    }

    public static void loadSettings() {
        Properties properties = new Properties();
        FileInputStream fis;
        try {
            fis = new FileInputStream(filename);
            properties.loadFromXML(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            stageHeight = Double.parseDouble(properties.getProperty("getHeight"));
            stageWidth = Double.parseDouble(properties.getProperty("getWidth"));
            stageFullScreen = Boolean.parseBoolean(properties.getProperty("isFullScreen"));
            stageFullScreenExitKeyCombination = KeyCombination.NO_MATCH;
        }
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
