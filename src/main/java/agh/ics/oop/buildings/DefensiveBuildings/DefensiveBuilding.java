package agh.ics.oop.buildings.DefensiveBuildings;

import agh.ics.oop.Attacks.Attack;
import agh.ics.oop.Vector;
import agh.ics.oop.buildings.Building;
import agh.ics.oop.gui.GameScreen;
import javafx.scene.image.Image;


public abstract class DefensiveBuilding extends Building {

    // Defensive parameters
    private double defence;


    // int widthInTiles, int heightInTiles, Vector position, double health, Image img, GameScreen gs
    protected DefensiveBuilding(int widthInTiles, int heightInTiles, Vector position, double health, Image img, GameScreen gs, double defence) {
        super(widthInTiles, heightInTiles, position, health, img, gs);
        this.defence = defence;
    }



    // Game methods
    @Override
    public void getHit(Attack attack) {
        double damage = attack.getStrength() - this.defence;
        if(damage > this.currentHealth){
            this.currentHealth = 0;
        }
        else{
            this.currentHealth -= (attack.getStrength() - this.defence);
        }


    }

    // Method specific for Defensive Buildings
    public void upgradeDefence(int deltaDefence) {
        // Zakładamy, że zarówno castle jak i Wall mogą zostać ulepszone
        this.defence += deltaDefence;

    }
}