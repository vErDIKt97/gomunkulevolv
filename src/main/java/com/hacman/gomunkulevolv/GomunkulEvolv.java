package com.hacman.gomunkulevolv;

import com.hacman.gomunkulevolv.gui.MainMenuGUI;
import com.hacman.gomunkulevolv.service.GameService;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class GomunkulEvolv extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        new MainMenuGUI(stage);
    }

}
