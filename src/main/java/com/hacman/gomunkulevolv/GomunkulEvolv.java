package com.hacman.gomunkulevolv;

import com.hacman.gomunkulevolv.gui.LabGUI;
import javafx.application.Application;
import javafx.stage.Stage;

public class GomunkulEvolv extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
//        new FightSessionGUI(stage);
        stage.setWidth(640);
        stage.setHeight(480);
        new LabGUI(stage);
    }

}
