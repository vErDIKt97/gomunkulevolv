package com.hacman.gomunkulevolv.gui;

import com.hacman.gomunkulevolv.game.lab.ability.LabAbilities;
import com.hacman.gomunkulevolv.game.lab.ability.LabAbility;
import com.hacman.gomunkulevolv.game.session.LabSession;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.Map;

public class LabGUI {
    private LabSession labSession;
    private BorderPane mainBorderPane;
    private GridPane labBox;
    private Text mainCharText;
    private Label labPointsLabel;
    private Button spendLabGensButton;
    private StackPane sessionBox;
    private Button newSessionButton;
    private StackPane centreLabPointsPane;
    private Stage abUpWindow;
    private BorderPane abUpBorderPane;
    private Scene abUpScene;
    private VBox abUpInformPanel;
    private GridPane labAbilityGridPane;
    private Scene mainScene;
    private Stage prevStage;
    private Image mainCharImage;

    public LabGUI(Stage prevStage) {
        buildGameWindow(prevStage);
    }

    private void buildGameWindow(Stage prevStage) {
        this.prevStage = prevStage;
        Stage stage = new Stage();
        stage.setHeight(prevStage.getHeight());
        stage.setWidth(prevStage.getWidth());
        labSession = new LabSession();
        createMainWindowObjects(stage);
        createLabAbilityWindow(stage);
        addMainWindowChildren(stage, labSession);
        addLabAbilityWindowChildren(stage, labSession);
        stylishMainWindow();
        refreshMainWindow();
        stage.show();
        mainScene.getWindow().addEventHandler(WindowEvent.WINDOW_SHOWING, windowEvent -> refreshMainWindow());
    }

    private void stylishMainWindow() {

    }

    private void createMainWindowObjects(Stage stage) {
        mainBorderPane = new BorderPane();
        labBox = new GridPane();
        mainCharImage = new Image(String.valueOf((getClass().getResource("/MainChar/slime.png"))));
        mainCharText = new Text();
        centreLabPointsPane = new StackPane();
        labPointsLabel = new Label("Total gen's: " + labSession.getGensCount());
        spendLabGensButton = new Button("Spend gen's");
        sessionBox = new StackPane();
        newSessionButton = new Button("Go to Battle!");
        newSessionButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> startNewSession(mouseEvent, stage));
        mainScene = new Scene(mainBorderPane);
        stage.setScene(mainScene);
        stage.setTitle("Game");
        stage.initStyle(StageStyle.TRANSPARENT);
    }

    private void addMainWindowChildren(Stage stage, LabSession labSession) {
        mainBorderPane.setCenter(labBox);
        labBox.add(getGridPane(new ImageView(mainCharImage)),0,0);
        labBox.add((getGridPane(new StackPane(mainCharText))),1,0);
        labBox.add(getGridPane(centreLabPointsPane),2,0);
        labBox.add(getGridPane(new StackPane(spendLabGensButton)),3,0);
        GridPane.setValignment(labBox, VPos.CENTER);
        GridPane.setHalignment(labBox, HPos.CENTER);
        labBox.setAlignment(Pos.CENTER);
        centreLabPointsPane.getChildren().add(labPointsLabel);
        spendLabGensButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> openLabAbilityWindow(mouseEvent, labSession));
        mainBorderPane.setBottom(sessionBox);
        sessionBox.setPadding(new Insets(30));
        sessionBox.getChildren().add(newSessionButton);
        stage.setTitle("Game");
    }

    @NotNull
    private GridPane getGridPane(Node node) {
        GridPane gridPane = new GridPane();
        gridPane.add(new StackPane(node),0,0);
        GridPane.setHgrow(gridPane,Priority.ALWAYS);
        gridPane.setAlignment(Pos.CENTER);
        return gridPane;
    }

    private void createLabAbilityWindow(Stage stage) {
        abUpWindow = new Stage();
        abUpWindow.setHeight(stage.getHeight());
        abUpWindow.setWidth(stage.getWidth());
        abUpBorderPane = new BorderPane();
        abUpScene = new Scene(abUpBorderPane);
        abUpWindow.setTitle("Ability's");
        abUpInformPanel = new VBox();
        labAbilityGridPane = new GridPane();
    }

    private void addLabAbilityWindowChildren(Stage stage, LabSession labSession) {
        //lvlUpWindow
        abUpBorderPane.setCenter(labAbilityGridPane);
        abUpWindow.setScene(abUpScene);
        abUpScene.getWindow().addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, windowEvent -> abUpWindowOnClose(stage));
        abUpBorderPane.setRight(abUpInformPanel);
        fillAbilityPane(labAbilityGridPane, labSession);

    }

    private void refreshMainWindow() {
        if (labSession.getCurFightSession() != null) {
            labSession.refreshMainCreature();
            labSession.getGensFromCurFightSession();
            labSession.clearCurFightSession();
        }
        labPointsLabel.setText("Total gen's: " + labSession.getGensCount());
        mainCharText.setText(labSession.getMainCreature().toString());
    }

    private void startNewSession(MouseEvent mouseEvent, Stage stage) {
        ((Node) (mouseEvent.getSource())).getScene().getWindow().hide();
        new FightSessionGUI(stage, labSession.startNewFightSession());
    }

    private void openLabAbilityWindow(EventObject mouseEvent, LabSession labSession) {
        fillAbilityPane(labAbilityGridPane, labSession);
        refreshLabAbilityAvailable();
        abUpWindow.show();
        ((Node) (mouseEvent.getSource())).getScene().getWindow().hide();
    }

    private void abUpWindowOnClose(Stage stage) {
        stage.show();
    }

    private void fillAbilityPane(GridPane labAbilityGridPane, LabSession labSession) {
        addButtonForEachAbility(labAbilityGridPane, labSession, getOrganizeAbilityList(labSession));
    }

    public static @NotNull ArrayList<ArrayList<LabAbility>> getOrganizeAbilityList(LabSession labSession) {
        ArrayList<ArrayList<LabAbility>> organizeAbilityList = new ArrayList<>();
        for (Map.Entry<LabAbilities, LabAbility> entry : labSession.getPossibleAbilityMap().entrySet()) {
            LabAbility ability = entry.getValue();
            LabAbilities abilityName = entry.getKey();
            while (organizeAbilityList.size() < ability.getClassLabAbility()) {
                organizeAbilityList.add(new ArrayList<>());
            }
            organizeAbilityList.get(ability.getClassLabAbility() - 1).add(ability);
            LabAbility abilityFromCur = labSession.getCurrentLabAbilityMap().get(abilityName);
            if ((abilityFromCur != null) && (ability.equals(abilityFromCur))) {
                ability.setAbilityLevel(abilityFromCur.getLabAbilityLevel());
            }
        }
        return organizeAbilityList;
    }

    private void addButtonForEachAbility(GridPane abilityGridPane, LabSession labSession, @NotNull ArrayList<ArrayList<LabAbility>> organizeAbilityList) {
        Button abilityButton;
        for (int i = 0; i < organizeAbilityList.size(); i++) {
            for (int j = 0; j < organizeAbilityList.get(i).size(); j++) {
                abilityButton = new Button(organizeAbilityList.get(i).get(j).toString());
                Button finalAbilityButton = abilityButton;
                abilityButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> abilityClick(labSession, finalAbilityButton.getText()));
                abilityGridPane.add(abilityButton, j, i);
            }
        }
    }

    private void refreshLvlUpAbilityPane(LabSession labSession) {
        labAbilityGridPane = new GridPane();
        fillAbilityPane(labAbilityGridPane, labSession);
        abUpBorderPane.setCenter(labAbilityGridPane);
        refreshLabAbilityAvailable();
    }

    private void abilityClick(LabSession labSession, String title) {
        labSession.spendGens(title);
        labSession.appendAbilityModify();
        refreshLvlUpAbilityPane(labSession);
    }

    private void refreshLabAbilityAvailable() {
        for (Node button :
                labAbilityGridPane.getChildren()) {
            button.setDisable(labSession.getGensCount() < labSession.getPossibleAbilityMap().get(LabAbilities.getLabAbilityByTitle(((Button) button).getText())).getCost());
        }
    }

}
