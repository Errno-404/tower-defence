package agh.ics.oop.gui;

import agh.ics.oop.Interfaces.WaveStateObserver;
import agh.ics.oop.Interfaces.EnemyKilledObserver;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.util.concurrent.Callable;


public class TitlePane extends GridPane implements WaveStateObserver, EnemyKilledObserver {
    private final GameScreen gs;
    private final Button startWaveButton;
    private final Label currentPhaseLabel;

    private final ProgressBar waveProgress;

    public TitlePane(GameScreen gs) {
        this.gs = gs;
        gs.waveManager.addObserver(this);
        gs.gameEngine.addEnemyKilledObserver(this);


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


//        GridPane.setHalignment(moneyLabel, HPos.CENTER);


        // GridPane settings
        this.setGridLinesVisible(true);
        this.setStyle("-fx-background-color: #8fe3be");


        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(10);

        for (int i = 0; i < 10; i++) {
            this.getColumnConstraints().add(columnConstraints);
        }

        GridPane.setHalignment(currentPhaseLabel, HPos.CENTER);
        GridPane.setHalignment(startWaveButton, HPos.CENTER);
        this.add(this.currentPhaseLabel, 2, 0, 5, 1);
        this.add(this.startWaveButton, 2, 1, 5, 1);


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
        this.waveProgress.setProgress(1.0 - (double) this.gs.waveManager.currentlyKilled / this.gs.waveManager.totalToSpawn);
    }
}

