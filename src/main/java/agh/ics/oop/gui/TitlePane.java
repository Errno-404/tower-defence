package agh.ics.oop.gui;

import agh.ics.oop.Interfaces.WaveStateObserver;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class TitlePane extends VBox implements WaveStateObserver {
    private GameScreen gs;
    private Button startWaveButton;
    private Label currentPhaseLabel;
    public TitlePane(GameScreen gs){
        this.gs = gs;
        gs.waveWanager.addObserver(this);

        this.currentPhaseLabel = new Label("Shopping Phase");
        this.currentPhaseLabel.setFont(new Font(40));

        this.startWaveButton = new Button("Start Wave number: " + this.gs.waveWanager.waveNumber + "!");
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
            this.currentPhaseLabel.setText("Wave number: " + this.gs.waveWanager.waveNumber);
        }
        else{
            this.currentPhaseLabel.setText("Shopping Phase");
            this.startWaveButton.setVisible(true);
        }

}}}

