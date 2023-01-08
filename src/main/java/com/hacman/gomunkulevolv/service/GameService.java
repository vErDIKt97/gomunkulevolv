package com.hacman.gomunkulevolv.service;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class GameService {
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

    public static class MyEvent extends Event {

        public static final EventType<MyEvent> DEFEAT_ENEMY = new EventType<>(Event.ANY, "DEFEAT_ENEMY");
        public static final EventType<MyEvent> MAIN_CREATURE_ATTACK = new EventType<>(ANY, "MAIN_CREATURE_ATTACK");

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
