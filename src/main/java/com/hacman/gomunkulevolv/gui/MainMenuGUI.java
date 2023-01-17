package com.hacman.gomunkulevolv.gui;

import com.hacman.gomunkulevolv.service.GameService;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainMenuGUI {

    private BorderPane mainBorderPane;
    private VBox menuSelect;
    private Button newGameButton;
    private Button optionsButton;
    private Button exitButton;
    private BackgroundImage mainBackground;
    private TabPane optionsTabPane;
    private Stage optionsStage;
    private MenuButton resolution;

    public MainMenuGUI(Stage stage) {
        GameService.applyStageSettings(stage);
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
        optionsStage = new Stage();
        optionsStage.initOwner(stage);
        optionsStage.initModality(Modality.WINDOW_MODAL);
        optionsStage.setWidth(stage.getWidth()/3);
        optionsStage.setHeight(stage.getHeight()/3);
        optionsStage.initStyle(StageStyle.TRANSPARENT);
        BorderPane optionsBorderPane = new BorderPane();
        Button exit = new Button("Exit");
        StackPane bottom = new StackPane(exit);
        exit.addEventHandler(MouseEvent.MOUSE_CLICKED,mouseEvent -> getClose(optionsStage, stage));
        bottom.setPadding(new Insets(optionsStage.getHeight()/100*10));
        optionsBorderPane.setBottom(bottom);
        optionsTabPane = new TabPane();
        Tab graphic = new Tab("Graphic");
        graphic.setClosable(false);
        optionsTabPane.getTabs().add(0, graphic);
        resolution = new MenuButton((int)stage.getWidth() + "*" + (int)stage.getHeight());
        MenuItem resolution1 = new MenuItem("1024*768");
        resolution1.addEventHandler(ActionEvent.ACTION, actionEvent -> changeResolution(stage, actionEvent, resolution));
        resolution.getItems().add(0, resolution1);
        MenuItem resolution2 = new MenuItem("1280*960");
        resolution2.addEventHandler(ActionEvent.ACTION, actionEvent -> changeResolution(stage, actionEvent, resolution));
        resolution.getItems().add(1, resolution2);
        MenuItem resolution3 = new MenuItem("1440*1080");
        resolution3.addEventHandler(ActionEvent.ACTION,actionEvent -> changeResolution(stage,actionEvent, resolution));
        resolution.getItems().add(2, resolution3);
        VBox box = new VBox();
        box.getChildren().add(0,resolution);
        CheckBox checkFullScreen = new CheckBox();
        checkFullScreen.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        checkFullScreen.setText("FullScreen");
        checkFullScreen.addEventHandler(ActionEvent.ACTION,actionEvent -> setFullScreen(stage, actionEvent));
        box.getChildren().add(1, checkFullScreen);
        box.setPadding(new Insets(stage.getHeight()/20));
        optionsTabPane.getTabs().get(0).setContent(box);
        optionsBorderPane.setCenter(optionsTabPane);
        optionsStage.setScene(new Scene(optionsBorderPane));
    }

    private void getClose(Stage optionsStage, Stage mainStage) {
        optionsStage.close();
        try {
            GameService.saveSettings(mainStage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getClose(Stage optionsStage) {
        optionsStage.close();
    }


    private void setFullScreen(Stage stage, ActionEvent actionEvent) {
        CheckBox checkBox = (CheckBox) actionEvent.getSource();
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setFullScreen(checkBox.selectedProperty().get());
        resolution.setDisable((checkBox.selectedProperty().get()));
    }

    private void changeResolution(Stage stage, Event event, MenuButton resolution) {
        String[] settings = ((MenuItem)event.getSource()).getText().split("\\*",2);
        stage.setWidth(Double.parseDouble(settings[0]));
        stage.setHeight(Double.parseDouble(settings[1]));
        stage.centerOnScreen();
        resolution.setText(((MenuItem) event.getSource()).getText());

    }

    private void createMainWindowObjects(Stage stage) {
        mainBackground = new BackgroundImage(new Image(String.valueOf(getClass().getResource("/MainStage/photo_2023-01-10_21-21-25.png"))), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        mainBorderPane = new BorderPane();
        newGameButton = new Button("New Game");
        optionsButton = new Button("Options");
        exitButton = new Button("Exit");
        menuSelect = new VBox(newGameButton, optionsButton, exitButton);
        optionsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> openOptionsWindow());
        exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> getClose(stage));
        newGameButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> newGameStart(stage));
        menuSelect.setAlignment(Pos.CENTER);
        menuSelect.setSpacing(10);
        menuSelect.setPadding(new Insets(20));
        stage.setScene(new Scene(mainBorderPane));
    }

    private void openOptionsWindow() {
        optionsStage.show();
    }

    private void newGameStart(Stage stage) {
        new LabGUI(stage);
        stage.getScene().getWindow().hide();
    }
}
