package com.hacman.gomunkulevolv.gui;

import com.hacman.gomunkulevolv.abilities.Ability;
import com.hacman.gomunkulevolv.abilities.PossibleAbility;
import com.hacman.gomunkulevolv.game.session.FightSession;
import com.hacman.gomunkulevolv.object.MainCreature;
import com.hacman.gomunkulevolv.service.GameService;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;

public class FightSessionGUI {

    public final ArrayList<Text> enemies = new ArrayList<>();
    private final Stage prevScene;
    private Scene lvlUpScene;
    private Text enemyText5;
    private VBox enemy5;
    private Text enemyText4;
    private VBox enemy4;
    private Text enemyText3;
    private VBox enemy3;
    private Text enemyText2;
    private VBox enemy2;
    private Text enemyText1;
    private VBox enemy1;
    private GridPane enemiesPane;
    private TextArea battleTextArea;
    private HBox buttonBox;
    private BorderPane topBorderPane;
    private BorderPane mainBorderPane;
    private Button buttonFightNext;
    private final FightSession fightSession;
    private Text mainCharText;
    private Text enemyCharText;
    private GridPane mainCharPane;
    private GridPane enemyCharPane;
    private HBox centre;
    private Button buttonLevelUp;
    private Stage lvlUpWindow;
    private BorderPane lvlUpBorderPane;
    private GridPane abilityGridPane;
    private VBox lvlUpInformPanel;
    private Scene mainScene;
    private Button buttonReturn;
    private Label labelGens;
    private Text textGens;

    public FightSessionGUI(Stage prevScene, FightSession fightSession) {
        this.fightSession = fightSession;
        Stage stage = new Stage();
        buildMainWindow(stage);
        this.prevScene = prevScene;
    }

    private void buildMainWindow(Stage stage) {
        createObjects(stage);
        addChildrenMainPane(stage);
        addChildrenLvlUpPane(stage);
        setAlignment();

        GridPane.setHgrow(enemy1, Priority.ALWAYS);
        BorderPane.setMargin(battleTextArea, new Insets(20, 10, 10, 10));
        GridPane.setHgrow(enemy2, Priority.ALWAYS);
        GridPane.setHgrow(enemy3, Priority.ALWAYS);
        GridPane.setHgrow(enemy4, Priority.ALWAYS);
        GridPane.setHgrow(enemy5, Priority.ALWAYS);
        stage.show();
        mainScene.getWindow().addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, this::returnOnPrevWindow);
    }

    private void createObjects(Stage stage) {
        CreateMainWindow(stage);
        CreateLvlUpWindow();
    }

    private void CreateLvlUpWindow() {
        buttonLevelUp = new Button("+");
        lvlUpWindow = new Stage();
        lvlUpBorderPane = new BorderPane();
        lvlUpScene = new Scene(lvlUpBorderPane);
        lvlUpWindow.setTitle("Ability's");
        lvlUpInformPanel = new VBox();
    }

    private void CreateMainWindow(Stage stage) {
        mainBorderPane = new BorderPane();
        topBorderPane = new BorderPane();
        buttonBox = new HBox();
        centre = new HBox();
        battleTextArea = new TextArea();
        enemiesPane = new GridPane();
        enemy1 = new VBox();
        enemyText1 = new Text();
        enemy2 = new VBox();
        enemyText2 = new Text();
        enemy3 = new VBox();
        enemyText3 = new Text();
        enemy4 = new VBox();
        enemyText4 = new Text();
        enemy5 = new VBox();
        enemyText5 = new Text();
        mainScene = new Scene(mainBorderPane);
        mainCharText = new Text();
        enemyCharText = new Text();
        buttonFightNext = new Button();
        buttonReturn = new Button();
        buttonReturn = new Button();
        mainCharPane = new GridPane();
        enemyCharPane = new GridPane();
        abilityGridPane = new GridPane();
        labelGens = new Label("Earned gens: ");
        textGens = new Text();
        buttonFightNext.setText("Fight next!");
        buttonReturn.setText("Return in Lab");
        stage.setScene(mainScene);
        stage.setTitle("GomunkulEvolv");
    }

    private void addChildrenMainPane(Stage stage) {
        // MainWindow
        mainCharPane.add(mainCharText, 0, 0);
        enemyCharPane.add(enemyCharText, 0, 0);
        buttonFightNext.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> fightSession.fight(mainCharText,
                enemyCharText,
                enemyText1,
                battleTextArea,
                enemies,
                buttonFightNext,
                buttonLevelUp,
                textGens));
        buttonLevelUp.addEventHandler(MouseEvent.MOUSE_CLICKED, this::lvlUpClick);
        buttonReturn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::returnOnPrevWindow);
        enemy1.getChildren().add(enemyText1);
        enemy2.getChildren().add(enemyText2);
        enemy3.getChildren().add(enemyText3);
        enemy4.getChildren().add(enemyText4);
        enemy5.getChildren().add(enemyText5);
        enemiesPane.add(enemy1, 0, 0);
        enemiesPane.add(enemy2, 1, 0);
        enemiesPane.add(enemy3, 2, 0);
        enemiesPane.add(enemy4, 3, 0);
        enemiesPane.add(enemy5, 4, 0);
        topBorderPane.setLeft(mainCharPane);
        topBorderPane.setRight(enemyCharPane);
        topBorderPane.setBottom(enemiesPane);
        topBorderPane.setCenter(centre);
        mainBorderPane.setTop(topBorderPane);
        mainBorderPane.setBottom(buttonBox);
        mainBorderPane.setCenter(battleTextArea);
        mainBorderPane.setCenter(battleTextArea);
        buttonBox.getChildren().add(buttonFightNext);
        buttonBox.getChildren().add(buttonLevelUp);
        buttonBox.getChildren().add(buttonReturn);
        centre.getChildren().add(labelGens);
        centre.getChildren().add(textGens);
        enemies.add(enemyText1);
        enemies.add(enemyText2);
        enemies.add(enemyText3);
        enemies.add(enemyText4);
        enemies.add(enemyText5);
        stage.setWidth(640);
        stage.setHeight(480);
        fightSession.Init(enemies, enemyText1);
        mainCharText.setText(fightSession.getMainCreature().toString());
        enemyCharText.setText(fightSession.getEnemyCreatureList().get(0).toString());
        textGens.setText(String.valueOf(fightSession.getEarnedGens()));
        fillAbilityPane(abilityGridPane, fightSession);
    }

    private void lvlUpClick(MouseEvent mouseEvent) {
        lvlUpWindow.show();
        ((Node) (mouseEvent.getSource())).getScene().getWindow().hide();
        refreshAbilityAvailable();
    }

    private void returnOnPrevWindow(Event event) {
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED)
            ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();

        prevScene.show();
    }

    private void addChildrenLvlUpPane(Stage stage) {
        //lvlUpWindow
        lvlUpBorderPane.setCenter(abilityGridPane);
        lvlUpWindow.setScene(lvlUpScene);
        lvlUpScene.getWindow().addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, windowEvent -> stage.show());
        lvlUpBorderPane.setRight(lvlUpInformPanel);
    }

    private void refreshAbilityAvailable() {
        for (Node button :
                abilityGridPane.getChildren()) {
            button.setDisable(fightSession.getMainCreature().getSkillPoint() <= 0);
        }
    }

    private void setAlignment() {
        enemyCharText.setTextAlignment(TextAlignment.CENTER);
        enemyCharPane.setAlignment(Pos.CENTER_LEFT);
        enemyCharPane.setPadding(new Insets(10, 25, 10, 10));
        BorderPane.setAlignment(enemyCharPane, Pos.CENTER);
        buttonBox.setAlignment(Pos.CENTER);
        enemy1.setAlignment(Pos.CENTER);
        enemy2.setAlignment(Pos.CENTER);
        enemy3.setAlignment(Pos.CENTER);
        enemy4.setAlignment(Pos.CENTER);
        enemy5.setAlignment(Pos.CENTER);
        GridPane.setValignment(enemiesPane, VPos.CENTER);
        enemyText1.setTextAlignment(TextAlignment.CENTER);
        enemyText2.setTextAlignment(TextAlignment.CENTER);
        enemyText3.setTextAlignment(TextAlignment.CENTER);
        enemyText4.setTextAlignment(TextAlignment.CENTER);
        enemyText5.setTextAlignment(TextAlignment.CENTER);
        buttonBox.setPadding(new Insets(10, 10, 10, 10));
    }

    private void abilityClick(FightSession fightSession, String title) {
        fightSession.getMainCreature().spendSkillPoint(title);
        refreshLvlUpAbilityPane(fightSession);
    }

    private void fillAbilityPane(GridPane abilityGridPane, FightSession fightSession) {
        addButtonForEachAbility(abilityGridPane, fightSession, getOrganizeAbilityList(fightSession));
    }

    private void addButtonForEachAbility(GridPane abilityGridPane, FightSession fightSession, @NotNull ArrayList<ArrayList<Ability>> organizeAbilityList) {
        Button abilityButton;
        for (int i = 0; i < organizeAbilityList.size(); i++) {
            for (int j = 0; j < organizeAbilityList.get(i).size(); j++) {
                abilityGridPane.add(new Button(organizeAbilityList.get(i).get(j).toString()), j, i);
                abilityButton = (Button) GameService.getNodeByRowColumnIndex(j, i, abilityGridPane);
                Button finalAbilityButton = abilityButton;
                abilityButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> abilityClick(fightSession, finalAbilityButton.getText()));
            }
        }
    }
    public static @NotNull ArrayList<ArrayList<Ability>> getOrganizeAbilityList(FightSession fightSession) {
        ArrayList<ArrayList<Ability>> organizeAbilityList = new ArrayList<>();
        for (Map.Entry<PossibleAbility, Ability> entry : MainCreature.getPossibleAbilityList().entrySet()) {
            Ability ability = entry.getValue();
            PossibleAbility abilityName = entry.getKey();
            while (organizeAbilityList.size() < ability.getClassAbility()) {
                organizeAbilityList.add(new ArrayList<>());
            }
            organizeAbilityList.get(ability.getClassAbility() - 1).add(ability);
            Ability abilityFromCur = fightSession.getMainCreature().getCurrentAbilityList().get(abilityName);
            if (abilityFromCur != null) {
                if (ability.equals(abilityFromCur)) {
                    ability.setAbilityLevel(abilityFromCur.getAbilityLevel());
                }
            }
        }
        return organizeAbilityList;
    }

    private void refreshLvlUpAbilityPane(FightSession fightSession) {
        abilityGridPane = new GridPane();
        fillAbilityPane(abilityGridPane, fightSession);
        lvlUpBorderPane.setCenter(abilityGridPane);
        refreshAbilityAvailable();
    }

}