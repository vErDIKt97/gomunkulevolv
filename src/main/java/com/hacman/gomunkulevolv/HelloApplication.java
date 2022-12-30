package com.hacman.gomunkulevolv;

import com.hacman.gomunkulevolv.abilities.Ability;
import com.hacman.gomunkulevolv.abilities.PossibleAbility;
import com.hacman.gomunkulevolv.game.session.MainGomunkulEvolv;
import com.hacman.gomunkulevolv.object.MainCreature;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

public class HelloApplication extends Application {

    private Button buttonStart;
    private Scene mainScene;
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
    public ArrayList<Text> enemies = new ArrayList<>();
    private MainGomunkulEvolv gomunkulEvolv;
    private Text mainCharText;
    private Text enemyCharText;
    private GridPane mainCharPane;
    private GridPane enemyCharPane;
    private GridPane centre;
    private Button levelUpButton;
    private Stage lvlUpWindow;
    private BorderPane lvlUpBorderPane;
    private GridPane abilityGridPane;
    private VBox lvlUpInformPanel;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        buildMainWindow(stage);
    }

    private void buildMainWindow(Stage stage) {
        createObjects(stage);
        addChildren(stage);
        setAlignment();

        GridPane.setHgrow(enemy1, Priority.ALWAYS);
        BorderPane.setMargin(battleTextArea, new Insets(20, 10, 10, 10));
        GridPane.setHgrow(enemy2, Priority.ALWAYS);
        GridPane.setHgrow(enemy3, Priority.ALWAYS);
        GridPane.setHgrow(enemy4, Priority.ALWAYS);
        GridPane.setHgrow(enemy5, Priority.ALWAYS);
        stage.show();
    }

    private void createObjects(Stage stage) {

        CreateMainWindow(stage);
        CreateLvlUpWindow();
    }

    private void CreateLvlUpWindow() {
        levelUpButton = new Button("+");
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
        buttonStart = new Button();
        buttonFightNext = new Button();
        mainCharPane = new GridPane();
        enemyCharPane = new GridPane();
        abilityGridPane = new GridPane();
        buttonStart.setText("Start");
        buttonFightNext.setText("Fight next!");
        stage.setScene(mainScene);
        stage.setTitle("GomunkulEvolv");
    }

    private void addChildren(Stage stage) {
        // MainWindow
        mainCharPane.add(mainCharText, 0, 0);
        enemyCharPane.add(enemyCharText, 0, 0);
        buttonStart.addEventHandler(MouseEvent.MOUSE_CLICKED, getHandlerStart(stage));
        buttonFightNext.addEventHandler(MouseEvent.MOUSE_CLICKED, getHandlerFightNext());
        levelUpButton.addEventHandler(MouseEvent.MOUSE_CLICKED, getHandlerLvlUp());
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
        mainBorderPane.setCenter(buttonStart);

        //lvlUpWindow
        lvlUpBorderPane.setCenter(abilityGridPane);
        lvlUpWindow.setScene(lvlUpScene);
        lvlUpScene.getWindow().addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, getHandlerWinLvlUpOnClose(stage));
        lvlUpBorderPane.setRight(lvlUpInformPanel);

    }

    private EventHandler<WindowEvent> getHandlerWinLvlUpOnClose(Stage stage) {
        return windowEvent -> stage.show();
    }

    private EventHandler<MouseEvent> getHandlerLvlUp() {
        return mouseEvent -> {
            lvlUpWindow.show();
            ((Node) (mouseEvent.getSource())).getScene().getWindow().hide();
            refreshAbilityAvaleble();
        };
    }

    private void refreshAbilityAvaleble() {
        for (Node button :
                abilityGridPane.getChildren()) {
            button.setDisable(gomunkulEvolv.getMainCreature().getSkillPoint() <= 0);
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

    private EventHandler<MouseEvent> getHandlerFightNext() {
        return mouseEvent -> gomunkulEvolv.fight(mainCharText, enemyCharText, enemyText1, battleTextArea, enemies, buttonFightNext, levelUpButton);
    }

    private EventHandler<MouseEvent> getHandlerStart(Stage stage) {
        return event -> {
            buttonStart.setVisible(false);
            mainBorderPane.setCenter(battleTextArea);
            buttonBox.getChildren().add(buttonFightNext);
            buttonBox.getChildren().add(levelUpButton);
            stage.setWidth(640);
            stage.setHeight(480);
            battleTextArea.setVisible(true);
            buttonFightNext.setVisible(true);
            enemies.add(enemyText1);
            enemies.add(enemyText2);
            enemies.add(enemyText3);
            enemies.add(enemyText4);
            enemies.add(enemyText5);
            gomunkulEvolv = new MainGomunkulEvolv(enemies, enemyText1);
            mainCharText.setText(gomunkulEvolv.getMainCreature().toString());
            enemyCharText.setText(gomunkulEvolv.getEnemyCreatureList().get(0).toString());
            fillAbilityPane(abilityGridPane, gomunkulEvolv);
        };
    }

    private void fillAbilityPane(GridPane abilityGridPane, MainGomunkulEvolv mainGomunkulEvolv) {
        ArrayList<ArrayList<Ability>> organizeAbilityList = getOrganizeAbilityList(mainGomunkulEvolv);
        addButtonForEachAbility(abilityGridPane, mainGomunkulEvolv, organizeAbilityList);
    }

    private void addButtonForEachAbility(GridPane abilityGridPane, MainGomunkulEvolv mainGomunkulEvolv, ArrayList<ArrayList<Ability>> organizeAbilityList) {
        Button abilityButton;
        for (int i = 0; i < organizeAbilityList.size(); i++) {
            for (int j = 0; j < organizeAbilityList.get(i).size(); j++) {
                abilityGridPane.add(new Button(organizeAbilityList.get(i).get(j).toString()), j, i);
                abilityButton = (Button) getNodeByRowColumnIndex(j, i, abilityGridPane);
                abilityButton.addEventHandler(MouseEvent.MOUSE_CLICKED, getHandlerAddAbility(mainGomunkulEvolv, abilityButton.getText()));
            }
        }
    }

    @NotNull
    private ArrayList<ArrayList<Ability>> getOrganizeAbilityList(MainGomunkulEvolv mainGomunkulEvolv) {
        ArrayList<ArrayList<Ability>> organizeAbilityList = new ArrayList<>();
        for (Map.Entry<PossibleAbility, Ability> entry : MainCreature.getPossibleAbilityList().entrySet()) {
            Ability ability = entry.getValue();
            PossibleAbility abilityName = entry.getKey();
            while (organizeAbilityList.size() < ability.getClassAbility()) {
                organizeAbilityList.add(new ArrayList<>());
            }
            organizeAbilityList.get(ability.getClassAbility() - 1).add(ability);
            Ability abilityFromCur = mainGomunkulEvolv.getMainCreature().getCurrentAbilityList().get(abilityName);
            if (abilityFromCur != null) {
                if (ability.equals(abilityFromCur)) {
                    ability.setAbilityLevel(abilityFromCur.getAbilityLevel());
                }
            }
        }
        return organizeAbilityList;
    }

    private EventHandler<MouseEvent> getHandlerAddAbility(MainGomunkulEvolv mainGomunkulEvolv, String title) {
        return mouseEvent -> {
            mainGomunkulEvolv.getMainCreature().spendSkillPoint(title);
            refreshLvlUpAbilityPane(mainGomunkulEvolv);
        };
    }

    private void refreshLvlUpAbilityPane(MainGomunkulEvolv mainGomunkulEvolv) {
        abilityGridPane = new GridPane();
        fillAbilityPane(abilityGridPane, mainGomunkulEvolv);
        lvlUpBorderPane.setCenter(abilityGridPane);
        refreshAbilityAvaleble();
    }

    public Node getNodeByRowColumnIndex(final int column, final int row, GridPane gridPane) {
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

}