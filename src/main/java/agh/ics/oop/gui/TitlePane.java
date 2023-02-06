package agh.ics.oop.gui;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;




public class TitlePane extends VBox {
    private GameScreen gs;
    public TitlePane(GameScreen gs){
        this.gs = gs;
        Button startWaveButton = new Button("Start Wave!");
        startWaveButton.setMaxWidth(Double.MAX_VALUE);
        startWaveButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!gs.isWaveStarted){
                    gs.waveWanager.startNewWave();
                }
            }
        });
        this.getChildren().add(startWaveButton);
    }
}
