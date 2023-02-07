package agh.ics.oop.gui;

import agh.ics.oop.Interfaces.BuyObserver;
import agh.ics.oop.Interfaces.WaveStateObserver;
import agh.ics.oop.Interfaces.EnemyKilledObserver;
import agh.ics.oop.buildings.BuildingsName;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.scene.text.Font;


public class TitlePane extends GridPane implements WaveStateObserver, EnemyKilledObserver, BuyObserver {
    private final GameScreen gs;
    private final Button startWaveButton;
    private final Label currentPhaseLabel;

    private final ProgressBar waveProgress;

    private Label moneyLabel;

    private Label moneyValueLabel;

    private TowerPane towerPane;


    public TitlePane(GameScreen gs, TowerPane towerPane) {
        this.gs = gs;
        gs.waveManager.addObserver(this);
        gs.gameEngine.addEnemyKilledObserver(this);



        this.towerPane = towerPane;
        towerPane.shop.addBuyObserver(this);

        // Phase Label
        this.currentPhaseLabel = new Label("Shopping Phase");
        this.currentPhaseLabel.setFont(new Font(40));


        // Start Button
        this.startWaveButton = new Button("Start wave 1");
        this.startWaveButton.setFont(new Font(20));
        this.startWaveButton.setMaxWidth(Double.MAX_VALUE);
        this.startWaveButton.setOnMouseClicked(event -> {
            if (!gs.isWaveStarted) {
                gs.waveManager.startNewWave();
            }
        });

        // Progress Bar
        this.waveProgress = new ProgressBar();
        this.waveProgress.setMaxWidth(Double.MAX_VALUE);
        this.waveProgress.setMinHeight(44);

        // Money Label
        this.moneyLabel = new Label("Money");

        // MoneyValueLabel

        this.moneyValueLabel = new Label(Double.toString(this.towerPane.shop.getGold()));

//        GridPane.setHalignment(moneyLabel, HPos.CENTER);


        // GridPane settings
//        this.setGridLinesVisible(true);
//        this.setStyle("-fx-background-color: #8fe3be");
        this.setVgap(5);


        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(10);

        for (int i = 0; i < 10; i++) {
            this.getColumnConstraints().add(columnConstraints);
        }

        GridPane.setHalignment(currentPhaseLabel, HPos.CENTER);
        GridPane.setHalignment(startWaveButton, HPos.CENTER);
        GridPane.setHalignment(moneyLabel, HPos.CENTER);
        GridPane.setValignment(moneyLabel, VPos.BOTTOM);

        GridPane.setHalignment(moneyValueLabel, HPos.CENTER);
        GridPane.setValignment(moneyValueLabel, VPos.TOP);
        this.add(this.currentPhaseLabel, 2, 0, 5, 1);
        this.add(this.startWaveButton, 2, 1, 5, 1);
        this.add(this.moneyLabel, 0, 0, 1, 1);
        this.add(this.moneyValueLabel, 0, 1, 1, 1);
    }

    @Override
    public void changeWaveState() {
        {
            if (this.gs.isWaveStarted) {
                this.getChildren().remove(startWaveButton);


                this.currentPhaseLabel.setText("Wave " + this.gs.waveManager.waveNumber);
                this.waveProgress.setProgress(this.gs.waveManager.totalToSpawn);
                this.add(waveProgress, 2, 1, 5, 1);

            } else {
                this.startWaveButton.setText("Start wave " + this.gs.waveManager.waveNumber);
                this.currentPhaseLabel.setText("Shopping Phase");
                this.getChildren().remove(waveProgress);
                this.add(startWaveButton, 2, 1, 5, 1);

            }

        }
    }

    @Override
    public void addGold(Integer n) {
        double gold = this.towerPane.shop.getGold();
        String goldStr = Double.toString(gold);
        this.moneyValueLabel.setText(goldStr);
        this.waveProgress.setProgress(1.0 - (double) this.gs.waveManager.currentlyKilled / this.gs.waveManager.totalToSpawn);
    }


    @Override
    public void reportBuy(Integer gold) {
        double gold1 = this.towerPane.shop.getGold();
        String goldStr = Double.toString(gold1);
        this.moneyValueLabel.setText(goldStr);
    }
}

