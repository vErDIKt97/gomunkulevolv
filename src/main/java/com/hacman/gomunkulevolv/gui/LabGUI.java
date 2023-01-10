package com.hacman.gomunkulevolv.gui;

import com.hacman.gomunkulevolv.game.lab.ability.LabAbilities;
import com.hacman.gomunkulevolv.game.lab.ability.LabAbility;
import com.hacman.gomunkulevolv.game.session.LabSession;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.Map;

public class LabGUI {
    private LabSession labSession;
    private BorderPane mainBorderPane;
    private HBox labBox;
    private Text mainCharText;
    private Label labPointsLabel;
    private Label countLabGensLabel;
    private Button spendLabGensButton;
    private VBox sessionBox;
    private Button newSessionButton;
    private HBox centreLabPointsPane;
    private Stage abUpWindow;
    private BorderPane abUpBorderPane;
    private Scene abUpScene;
    private VBox abUpInformPanel;
    private GridPane labAbilityGridPane;
    private Scene mainScene;

    public LabGUI(Stage stage) {
        buildGameWindow(stage);
    }

    private void buildGameWindow(Stage stage) {
        labSession = new LabSession();
        createMainWindowObjects(stage);
        createLabAbilityWindow();
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
        labBox = new HBox();
        Image mainCharImage = new Image(String.valueOf((getClass().getResource("/MainChar/slime.png"))));
        labBox.getChildren().add(new ImageView(mainCharImage));
        mainCharText = new Text();
        centreLabPointsPane = new HBox();
        labPointsLabel = new Label("Total gen's: ");
        countLabGensLabel = new Label(String.valueOf(labSession.getGensCount()));
        spendLabGensButton = new Button("Spend gen's");
        sessionBox = new VBox();
        newSessionButton = new Button("Go to Battle!");
        newSessionButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> startNewSession(mouseEvent, stage));
        mainScene = new Scene(mainBorderPane);
        stage.setScene(mainScene);
        stage.setTitle("Game");
//        stage.initStyle(StageStyle.TRANSPARENT);
    }

    private void createLabAbilityWindow() {
        abUpWindow = new Stage();
        abUpBorderPane = new BorderPane();
        abUpScene = new Scene(abUpBorderPane);
        abUpWindow.setTitle("Ability's");
        abUpInformPanel = new VBox();
        labAbilityGridPane = new GridPane();
    }

    private void addMainWindowChildren(Stage stage, LabSession labSession) {
        mainBorderPane.setCenter(labBox);
        labBox.getChildren().add(mainCharText);
        labBox.getChildren().add(centreLabPointsPane);
        centreLabPointsPane.getChildren().add(labPointsLabel);
        centreLabPointsPane.getChildren().add(countLabGensLabel);
        labBox.getChildren().add(spendLabGensButton);
        spendLabGensButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> openLabAbilityWindow(mouseEvent, labSession));
        mainBorderPane.setBottom(sessionBox);
        sessionBox.getChildren().add(newSessionButton);
        stage.setTitle("Game");
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
        countLabGensLabel.setText(String.valueOf(labSession.getGensCount()));
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
