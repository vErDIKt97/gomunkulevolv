package com.hacman.gomunkulevolv.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainMenuGUI {

    private BorderPane mainBorderPane;
    private VBox menuSelect;
    private Button newGameButton;
    private Button optionsButton;
    private Button exitButton;
    private BackgroundImage mainBackground;
    private TabPane optionsTabPane;

    public MainMenuGUI(Stage stage) {
//        setting = new GameSettings();
        createMainWindowObjects(stage);
        createOptionsWindowObjects(stage);
        addMainWindowObjects(stage);
        addOptionsWindowObjects(stage);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    private void addOptionsWindowObjects(Stage stage) {

    }

    private void addMainWindowObjects(Stage stage) {
        mainBorderPane.setBackground(new Background(mainBackground));
        mainBorderPane.setBottom(menuSelect);
    }

    private void createOptionsWindowObjects(Stage stage) {
        optionsTabPane = new TabPane();
    }

    private void createMainWindowObjects(Stage stage) {
        mainBackground = new BackgroundImage(new Image(String.valueOf(getClass().getResource("/MainStage/photo_2023-01-10_21-21-25.png"))), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        mainBorderPane = new BorderPane();
        newGameButton = new Button("New Game");
        optionsButton = new Button("Options");
        exitButton = new Button( "Exit");
        menuSelect = new VBox(newGameButton,optionsButton,exitButton);
        exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED,mouseEvent -> stage.close());
        newGameButton.addEventHandler(MouseEvent.MOUSE_CLICKED,mouseEvent -> newGameStart(stage));
        menuSelect.setAlignment(Pos.CENTER);
        menuSelect.setSpacing(10);
        menuSelect.setPadding(new Insets(20));
        stage.setScene(new Scene(mainBorderPane));
    }

    private void newGameStart(Stage stage) {
        new LabGUI(stage);
        stage.getScene().getWindow().hide();
    }
}
