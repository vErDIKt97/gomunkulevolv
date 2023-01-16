package com.hacman.gomunkulevolv.gui;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenuGUI {

    private BorderPane mainBorderPane;
    private VBox menuSelect;
    private Button newGameButton;
    private Button optionsButton;
    private Button exitButton;

    public MainMenuGUI(Stage stage) {
//        setting = new GameSettings();
        createMainWindowObjects(stage);
        createOptionsWindowObjects(stage);
        addMainWindowJbjects(stage);
        addOptionsWindowObjects(stage);
        stage.show();
    }

    private void addOptionsWindowObjects(Stage stage) {

    }

    private void addMainWindowJbjects(Stage stage) {

    }

    private void createOptionsWindowObjects(Stage stage) {

    }

    private void createMainWindowObjects(Stage stage) {
        mainBorderPane = new BorderPane();
        menuSelect = new VBox();
        newGameButton = new Button("New Game");
        optionsButton = new Button("Options");
        exitButton = new Button( "Exit");
    }
}
