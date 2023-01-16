package com.hacman.gomunkulevolv;

import com.hacman.gomunkulevolv.gui.LabGUI;
import com.hacman.gomunkulevolv.gui.MainMenuGUI;
import javafx.application.Application;
import javafx.stage.Stage;

public class GomunkulEvolv extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
//        new FightSessionGUI(stage);
        stage.setWidth(1024);
        stage.setHeight(768);
//        new LabGUI(stage);
        new MainMenuGUI(stage);
    }

}
