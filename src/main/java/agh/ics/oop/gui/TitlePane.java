package agh.ics.oop.gui;

import agh.ics.oop.Interfaces.WaveStateObserver;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;



public class TitlePane extends VBox implements WaveStateObserver {
    private final GameScreen gs;
    private final Button startWaveButton;
    private final Label currentPhaseLabel;
    public TitlePane(GameScreen gs){
        this.gs = gs;
        gs.waveWanager.addObserver(this);

        this.currentPhaseLabel = new Label("Shopping Phase");
        this.currentPhaseLabel.setFont(new Font(40));

        this.startWaveButton = new Button("Start wave 1");
        this.startWaveButton.setMaxWidth(Double.MAX_VALUE);
        this.startWaveButton.setOnMouseClicked(event -> {
            if (!gs.isWaveStarted) {
                gs.waveWanager.startNewWave();
            }
        });

        this.setSpacing(20);
        this.setPadding(new Insets(0, 0, 20, 0));

        startWaveButton.setFont(new Font(20));
        currentPhaseLabel.setContentDisplay(ContentDisplay.CENTER);
        this.setAlignment(Pos.CENTER);






        startWaveButton.setStyle("");
      this.getChildren().addAll(currentPhaseLabel, startWaveButton);
    }

    @Override
    public void changeWaveState() {
       { if (this.gs.isWaveStarted) {

            this.startWaveButton.setVisible(false);
            this.currentPhaseLabel.setText("Wave " + this.gs.waveWanager.waveNumber);
        }
        else{
            this.startWaveButton.setText("Start wave " + this.gs.waveWanager.waveNumber);
            this.currentPhaseLabel.setText("Shopping Phase");
            this.startWaveButton.setVisible(true);
        }

}}}

