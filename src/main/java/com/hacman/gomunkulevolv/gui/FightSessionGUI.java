package com.hacman.gomunkulevolv.gui;

import com.hacman.gomunkulevolv.abilities.Ability;
import com.hacman.gomunkulevolv.abilities.PossibleAbility;
import com.hacman.gomunkulevolv.game.session.FightSession;
import com.hacman.gomunkulevolv.object.MainCreature;
import com.hacman.gomunkulevolv.service.GameService;
import com.hacman.gomunkulevolv.service.Saver;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
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
    private StackPane centre;
    private Button buttonLevelUp;
    private Stage lvlUpWindow;
    private BorderPane lvlUpBorderPane;
    private GridPane abilityGridPane;
    private VBox lvlUpInformPanel;
    private Scene mainScene;
    private Button buttonReturn;
    private String labelGens;
    private String textGens = "0";
    private Stage defEnemyStage;
    private ImageView mainCharImage;
    private Timeline mainCharAttack;
    private ImageView enemyCharImage;
    private Timeline enemyCharAttack;
    private Text containerGens;

    public FightSessionGUI(Stage prevScene, FightSession fightSession) {
        this.fightSession = fightSession;
        Stage stage = new Stage();
        GameService.applyStageSettings(stage);
        stage.setHeight(prevScene.getHeight());
        stage.setWidth(prevScene.getWidth());
        buildMainWindow(stage);
        this.prevScene = prevScene;
    }

    private void buildMainWindow(Stage stage) {
        createObjects(stage);
        addChildrenMainPane(stage);
        addChildrenLvlUpPane(stage);
        setAlignment(stage);
        stage.show();
        addHandlerAfterShow();
        createDefEnemyWindow(stage);
    }

    private void createObjects(Stage stage) {
        CreateMainWindow(stage);
        CreateLvlUpWindow(stage);
    }

    private void CreateMainWindow(Stage stage) {
        mainBorderPane = new BorderPane();
        topBorderPane = new BorderPane();
        buttonBox = new HBox();
        centre = new StackPane();
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
        labelGens = "Earned gens: ";
        containerGens = new Text(labelGens + " " + textGens);
        buttonFightNext.setText("Fight next!");
        buttonReturn.setText("Return in Lab");
        mainScene.addEventHandler(WindowEvent.WINDOW_SHOWING, windowEvent -> refreshTextMainCreature());
        stage.setScene(mainScene);
        stage.setTitle("GomunkulEvolv");
        createMainCharImage();
        createEnemyCharImage();
    }

    private void CreateLvlUpWindow(Stage stage) {
        buttonLevelUp = new Button("+");
        lvlUpWindow = new Stage();
        lvlUpWindow.initModality(Modality.WINDOW_MODAL);
        lvlUpWindow.initOwner(stage);
        lvlUpBorderPane = new BorderPane();
        lvlUpScene = new Scene(lvlUpBorderPane);
        lvlUpWindow.setTitle("Ability's");
        lvlUpInformPanel = new VBox();
    }

    private void addChildrenMainPane(Stage stage) {
        // MainWindow

        mainCharPane.add(mainCharText, 1, 0);
        mainCharPane.add(mainCharImage,0,0);
        enemyCharPane.add(enemyCharImage,1,0);
        enemyCharPane.add(enemyCharText, 0, 0);
        buttonFightNext.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> fightSession.fight(mainCharText,
                enemyCharText,
                enemyText1,
                battleTextArea,
                enemies,
                buttonFightNext,
                buttonLevelUp
        ));
        buttonFightNext.addEventHandler(GameService.MyEvent.DEFEAT_ENEMY, event -> enemyDefeat());
        buttonLevelUp.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> lvlUpClick());
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
        buttonBox.getChildren().add(buttonFightNext);
        buttonBox.getChildren().add(buttonLevelUp);
        buttonBox.getChildren().add(buttonReturn);
        centre.getChildren().add(containerGens);
        enemies.add(enemyText1);
        enemies.add(enemyText2);
        enemies.add(enemyText3);
        enemies.add(enemyText4);
        enemies.add(enemyText5);
        fightSession.Init(enemies, enemyText1);
        mainCharText.setText(fightSession.getMainCreature().toString());
        enemyCharText.setText(fightSession.getEnemyCreatureList().get(0).toString());
        textGens = String.valueOf(fightSession.getEarnedGens());
        fillAbilityPane(abilityGridPane, fightSession);
    }

    private void addChildrenLvlUpPane(Stage stage) {
        //lvlUpWindow
        lvlUpBorderPane.setCenter(abilityGridPane);
        lvlUpWindow.setScene(lvlUpScene);
        lvlUpScene.getWindow().addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, windowEvent -> onCloseAbWindow(stage));
        lvlUpBorderPane.setRight(lvlUpInformPanel);
    }

    private void createDefEnemyWindow(Stage stage) {
        defEnemyStage = new Stage();
        defEnemyStage.initModality(Modality.WINDOW_MODAL);
        defEnemyStage.initStyle(StageStyle.TRANSPARENT);
        defEnemyStage.initOwner(stage.getScene().getWindow());
        defEnemyStage.setWidth(stage.getWidth()/10*3);
        defEnemyStage.setHeight(stage.getHeight()/10*2);
        BorderPane defBorderPane = new BorderPane();
        defBorderPane.setBorder(Border.stroke(Paint.valueOf("black")));
        HBox defEnemyHBox = new HBox();
        Button devourButton = new Button("Devour");
        devourButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::getDevourEnemy);
        Button consumeButton = new Button("Consume");
        consumeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::getAddEarnedGens);
        Label label = new Label("What do you do with a defeated opponent?");
        label.setPadding(new Insets(defEnemyStage.getHeight()/100*10));
        label.setAlignment(Pos.CENTER);
        defBorderPane.setCenter(defEnemyHBox);
        defBorderPane.setTop(new StackPane(label));
        defEnemyHBox.getChildren().add(devourButton);
        defEnemyHBox.getChildren().add(consumeButton);
        defEnemyHBox.setSpacing(defEnemyStage.getWidth()/4);
        defEnemyHBox.setAlignment(Pos.CENTER);
        defEnemyHBox.setPadding(new Insets(stage.getHeight()/100*5));
        defEnemyStage.setScene(new Scene(defBorderPane));
    }

    private void addHandlerAfterShow() {
        mainScene.getWindow().addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, this::returnOnPrevWindow);
        battleTextArea.addEventHandler(GameService.MyEvent.MAIN_CREATURE_ATTACK, myEvent -> getPlayMainAttack());
        battleTextArea.addEventHandler(GameService.MyEvent.ENEMY_CREATURE_ATTACK, myEvent -> getPlayEnemyAttack());
    }

    private void getPlayEnemyAttack() {
        enemyCharAttack.play();
    }

    private void getPlayMainAttack() {
        mainCharAttack.play();
    }

    private void createEnemyCharImage() {
        Image stateEnemyCharImage = new Image(String.valueOf(getClass().getResource("/enemyChar/enemyChar1/imageState.png")));
        enemyCharImage = new ImageView(stateEnemyCharImage);
        Image[] attack = new Image[4];
        for (int i = 0; i < 4; i++) {
            attack[i] = new Image(String.valueOf(getClass().getResource("/enemyChar/enemyChar1/imageAttack" + (i+1) + ".png")));
        }
        enemyCharAttack = new Timeline();
        enemyCharAttack.getKeyFrames().add(new KeyFrame(Duration.millis(40), actionEvent -> enemyCharImage.setImage(attack[0])));
        enemyCharAttack.getKeyFrames().add(new KeyFrame(Duration.millis(80), actionEvent -> enemyCharImage.setImage(attack[1])));
        enemyCharAttack.getKeyFrames().add(new KeyFrame(Duration.millis(120), actionEvent -> enemyCharImage.setImage(attack[2])));
        enemyCharAttack.getKeyFrames().add(new KeyFrame(Duration.millis(160), actionEvent -> enemyCharImage.setImage(attack[3])));
        enemyCharAttack.getKeyFrames().add(new KeyFrame(Duration.millis(500), actionEvent -> enemyCharImage.setImage(stateEnemyCharImage)));

    }

    private void createMainCharImage() {
        Image stateMainCharImage = new Image(String.valueOf(getClass().getResource("/MainChar/slime.png")));
        mainCharImage = new ImageView(stateMainCharImage);
        Image[] attack = new Image[4];
        for (int i = 0; i < 4; i++) {
            attack[i] = new Image(String.valueOf(getClass().getResource("/MainChar/slimeAttack" + (i+1) + ".png")));
        }
        mainCharAttack = new Timeline();
        mainCharAttack.getKeyFrames().add(new KeyFrame(Duration.millis(40), actionEvent -> mainCharImage.setImage(attack[0])));
        mainCharAttack.getKeyFrames().add(new KeyFrame(Duration.millis(80), actionEvent -> mainCharImage.setImage(attack[1])));
        mainCharAttack.getKeyFrames().add(new KeyFrame(Duration.millis(120), actionEvent -> mainCharImage.setImage(attack[2])));
        mainCharAttack.getKeyFrames().add(new KeyFrame(Duration.millis(160), actionEvent -> mainCharImage.setImage(attack[3])));
        mainCharAttack.getKeyFrames().add(new KeyFrame(Duration.millis(500), actionEvent -> mainCharImage.setImage(stateMainCharImage)));
    }

    private void getAddEarnedGens(Event event) {
        fightSession.addEarnedGens(fightSession.getMainCreature().getGensForConsume(fightSession.getEnemyCreatureList().get(0)));
        ((Node) (event.getSource())).getScene().getWindow().hide();
        textGens = String.valueOf(fightSession.getEarnedGens());
        containerGens.setText(labelGens + " " + textGens);
        refreshTextMainCreature();
    }

    private void getDevourEnemy(Event event) {
        fightSession.getMainCreature().devourEnemy(fightSession.getEnemyCreatureList().get(0));
        ((Node) (event.getSource())).getScene().getWindow().hide();
        refreshTextMainCreature();
    }

    private void refreshTextMainCreature() {
        mainCharText.setText(fightSession.getMainCreature().toString());
    }

    private void enemyDefeat() {
        defEnemyStage.show();
    }

    private void lvlUpClick() {
        lvlUpWindow.show();
        refreshAbilityAvailable();
    }

    private void returnOnPrevWindow(Event event) {
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED)
            ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
        prevScene.show();
    }

    private void onCloseAbWindow(Stage stage) {
        stage.show();
    }

    private void refreshAbilityAvailable() {
        for (Node button :
                abilityGridPane.getChildren()) {
            button.setDisable(fightSession.getMainCreature().getSkillPoint() <= 0);
        }
    }

    private void setAlignment(Stage stage) {
        mainBorderPane.setPadding(new Insets(stage.getHeight()/100*4));
        buttonBox.setAlignment(Pos.CENTER);
        enemy1.setAlignment(Pos.CENTER);
        enemy2.setAlignment(Pos.CENTER);
        enemy3.setAlignment(Pos.CENTER);
        enemy4.setAlignment(Pos.CENTER);
        enemy5.setAlignment(Pos.CENTER);
        enemyText1.setTextAlignment(TextAlignment.CENTER);
        enemyText2.setTextAlignment(TextAlignment.CENTER);
        enemyText3.setTextAlignment(TextAlignment.CENTER);
        enemyText4.setTextAlignment(TextAlignment.CENTER);
        enemyText5.setTextAlignment(TextAlignment.CENTER);
        buttonBox.setPadding(new Insets(10));
        GridPane.setHgrow(enemy1, Priority.ALWAYS);
        BorderPane.setMargin(battleTextArea, new Insets(20, 10, 10, 10));
        GridPane.setHgrow(enemy2, Priority.ALWAYS);
        GridPane.setHgrow(enemy3, Priority.ALWAYS);
        GridPane.setHgrow(enemy4, Priority.ALWAYS);
        GridPane.setHgrow(enemy5, Priority.ALWAYS);
    }

    private void abilityClick(FightSession fightSession, String title) {
        fightSession.getMainCreature().spendSkillPoint(title);
        refreshLvlUpAbilityPane(fightSession);
        refreshTextMainCreature();
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
            Ability abilityFromCur = fightSession.getMainCreature().getCurrentAbilityList().get(abilityName);
            if (abilityFromCur != null) {
                if (ability.equals(abilityFromCur)) {
                    ability = abilityFromCur;
                }
            }
            organizeAbilityList.get(ability.getClassAbility() - 1).add(ability);
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