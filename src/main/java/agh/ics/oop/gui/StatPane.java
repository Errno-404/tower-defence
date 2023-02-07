package agh.ics.oop.gui;

import agh.ics.oop.Interfaces.BuildingDestroyedObserver;
import agh.ics.oop.Interfaces.DamageTakenObserver;
import agh.ics.oop.Interfaces.ShopSelectionObserver;
import agh.ics.oop.Interfaces.UnitSelectionObserver;
import agh.ics.oop.buildings.AttackingBuildings.AttackingBuilding;
import agh.ics.oop.buildings.Building;
import agh.ics.oop.buildings.Castle;
import agh.ics.oop.buildings.DefensiveBuildings.DefensiveBuilding;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class StatPane implements UnitSelectionObserver, DamageTakenObserver, BuildingDestroyedObserver {

    Building currentlySelected = null;


    private Button upgradeButton;
    private Button sellButton;
    private Label currentLevelLabel;

    private Label healthLabel;

    private Label defenseLabel;


    private Label attackPowerLabel;
    private Label attackSpeedLabel;

    Canvas healthBarCanvas;
    GraphicsContext gc;

    HBox all;


    public StatPane(){


        this.upgradeButton = new Button(" Upgrade ");
        this.upgradeButton.setFont(new Font(20));
        this.upgradeButton.setMaxWidth(Double.MAX_VALUE);
        this.upgradeButton.setOnMouseClicked(event -> {
            if (this.currentlySelected != null && this.currentlySelected.getLevel() <5) {
                currentlySelected.upgrade();
                this.updateStats();
            }
        });

        this.healthBarCanvas = new Canvas(100,10);
        this.gc = healthBarCanvas.getGraphicsContext2D();

        this.sellButton = new Button("Sell");
        this.sellButton.setFont(new Font(20));
        this.sellButton.setMaxWidth(Double.MAX_VALUE);
        this.sellButton.setOnMouseClicked(event -> {
            if (this.currentlySelected != null && !(this.currentlySelected instanceof Castle)) {
                currentlySelected.sell();
                this.currentlySelected = null;
                this.updateStats();
            }
        });


        this.currentLevelLabel = new Label();
        this.healthLabel = new Label();
        this.defenseLabel = new Label();
        this.attackPowerLabel = new Label();
        this.attackSpeedLabel = new Label();

        VBox attackThings = new VBox();
        attackThings.getChildren().addAll(this.attackPowerLabel, this.attackSpeedLabel);

        VBox defenseStuff = new VBox();
        defenseStuff.getChildren().addAll(this.defenseLabel, this.healthLabel, this.healthBarCanvas);

        this.all = new HBox();
        all.getChildren().addAll(this.upgradeButton, this.sellButton, defenseStuff, attackThings);

    }

    public void updateStats(){
        if(this.currentlySelected == null){
            this.gc.setFill(Color.WHITE);
            this.gc.fillRect(0,0,100,100);

            this.attackSpeedLabel.setText("");
            this.healthLabel.setText("");
            this.attackPowerLabel.setText("");
            this.defenseLabel.setText("");
            this.currentLevelLabel.setText("");

        }
        else{
            if(this.currentlySelected.getLevel() < 5){
                this.upgradeButton.setText(" Upgrade ");
            }
            else{
                this.upgradeButton.setText("Max Level");
            }
            this.currentLevelLabel.setText(Integer.toString(this.currentlySelected.getLevel()));
            this.healthLabel.setText(Double.toString(this.currentlySelected.getCurrentHealth()) + " / " + Double.toString(this.currentlySelected.getMaxHealth()));
            this.currentlySelected.drawHealthBarFixed(this.gc);

            if(this.currentlySelected instanceof DefensiveBuilding d1){
                this.defenseLabel.setText("Defense: " + d1.getDefence());
            }
            else{
                this.defenseLabel.setText("           ");
            }

            if(this.currentlySelected instanceof AttackingBuilding a1){
                this.attackPowerLabel.setText("Attack power: "+ a1.getAttackStrength());
                this.attackSpeedLabel.setText("Attack speed: "+a1.getAttackSpeed());
            }
            else{
                this.attackPowerLabel.setText("Attack power: ");
                this.attackSpeedLabel.setText("Attack speed: ");
            }
        }



    }


    @Override
    public void changeSelectedUnit(Building b) {
        if(this.currentlySelected != null){
            this.currentlySelected.removeDamageObs(this);
            this.currentlySelected.removeDestroyedObserver(this);
        }
        this.currentlySelected = b;
        this.updateStats();
        b.addDamageObs(this);
        b.addDestroyedObserver(this);
    }

    @Override
    public void damageTaken() {
        if(this.currentlySelected != null){
            this.currentlySelected.drawHealthBarFixed(this.gc);
            this.healthLabel.setText(Double.toString(this.currentlySelected.getCurrentHealth()) + " / " + Double.toString(this.currentlySelected.getMaxHealth()));
        }
        else{
            this.updateStats();
        }
    }

    @Override
    public void reportBuildingDestroyed(Building b) {
        this.currentlySelected = null;
        this.updateStats();
    }
}
