package agh.ics.oop.gui;

import agh.ics.oop.Interfaces.WaveStateObserver;
import agh.ics.oop.Interfaces.EnemyKilledObserver;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;



public class TitlePane extends VBox implements WaveStateObserver, EnemyKilledObserver {
    private final GameScreen gs;
    private final Button startWaveButton;
    private final Label currentPhaseLabel;

    private final ProgressBar waveProgress;

    public TitlePane(GameScreen gs){
        this.gs = gs;
        gs.waveManager.addObserver(this);
        gs.gameEngine.addEnemyKilledObserver(this);

        this.currentPhaseLabel = new Label("Shopping Phase");
        this.currentPhaseLabel.setFont(new Font(40));

        this.startWaveButton = new Button("Start wave 1");
        this.startWaveButton.setMaxWidth(Double.MAX_VALUE);
        this.startWaveButton.setOnMouseClicked(event -> {
            if (!gs.isWaveStarted) {
                gs.waveManager.startNewWave();
            }
        });

        this.setSpacing(20);
        this.setPadding(new Insets(0, 0, 20, 0));

        startWaveButton.setFont(new Font(20));
        currentPhaseLabel.setContentDisplay(ContentDisplay.CENTER);
        this.setAlignment(Pos.CENTER);






        startWaveButton.setStyle("");
      this.getChildren().addAll(currentPhaseLabel, startWaveButton);

      this.waveProgress = new ProgressBar();
      this.waveProgress.setMaxWidth(Double.MAX_VALUE);
      this.waveProgress.setMinHeight(44);

    }

    @Override
    public void changeWaveState() {
       { if (this.gs.isWaveStarted) {

            this.startWaveButton.setVisible(false);
            this.getChildren().remove(1);
            this.currentPhaseLabel.setText("Wave " + this.gs.waveManager.waveNumber);
            this.waveProgress.setProgress(this.gs.waveManager.totalToSpawn);
            this.getChildren().add(waveProgress);
        }
        else{
            this.startWaveButton.setText("Start wave " + this.gs.waveManager.waveNumber);
            this.currentPhaseLabel.setText("Shopping Phase");
            this.getChildren().remove(1);
            this.getChildren().add(startWaveButton);
            this.startWaveButton.setVisible(true);
        }

}}

    @Override
    public void addGold(Integer n) {
        this.waveProgress.setProgress(1.0 - (double)this.gs.waveManager.currentlyKilled / this.gs.waveManager.totalToSpawn);
    }
}

