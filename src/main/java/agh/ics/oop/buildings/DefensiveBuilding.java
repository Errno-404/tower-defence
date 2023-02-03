package agh.ics.oop.buildings;

import Attacks.Attack;
import agh.ics.oop.Vector;
import agh.ics.oop.gui.GameScreen;
import javafx.scene.image.Image;


public abstract class DefensiveBuilding extends Building {

    // Defensive parameters
    private int defence;


    protected DefensiveBuilding(Vector position, int width, int height, int health, int defence) {
        super(width, height, position, health);
        this.defence = defence;
    }

    protected DefensiveBuilding(int width, int height, int px, int py, int health, Image img, GameScreen gs) {
        super(width, height, px, py, health, img, gs);
        //this.defence = defence;
    }

    // Game methods
    @Override
    public void getHit(Attack attack) {
        // Chwilowo nie mam lepszego pomysłu na zrobienie defense, bo floatów się boję :/

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
