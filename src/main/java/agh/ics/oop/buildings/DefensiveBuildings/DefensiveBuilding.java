package agh.ics.oop.buildings.DefensiveBuildings;

import agh.ics.oop.Attacks.Attack;
import agh.ics.oop.Interfaces.BuyObserver;
import agh.ics.oop.Vector;
import agh.ics.oop.buildings.Building;
import agh.ics.oop.gui.GameScreen;
import agh.ics.oop.gui.Shop;
import javafx.scene.image.Image;


public abstract class DefensiveBuilding extends Building {

    // Defensive parameters
    protected double defence;


    // int widthInTiles, int heightInTiles, Vector position, double health, Image img, GameScreen gs
    protected DefensiveBuilding(int widthInTiles, int heightInTiles, Vector position, double health, Image img, GameScreen gs, double defence) {
        super(widthInTiles, heightInTiles, position, health, img, gs);
        this.defence = defence;
    }

    public double getDefence(){
        return this.defence;
    }

    // Game methods
    @Override
    public void getHit(Attack attack) {
        this.reduceHealth(attack.getStrength()*(1-this.defence));
        if(this.currentHealth <= 0){
            this.destroyBuilding();
        }



    }

    // Method specific for Defensive Buildings
    public void upgradeDefence(int deltaDefence) {
        // Zakładamy, że zarówno castle jak i Wall mogą zostać ulepszone
        this.defence += deltaDefence;

    }

    @Override
    public void upgrade(){
        if(Shop.gold > 5){
            Shop.gold-=5;
            Shop.buyObservers.forEach((BuyObserver b) ->{
                b.reportBuy(5);
            });
            this.level ++;
            switch (this.level) {
                case 2 -> {
                    this.defence += 0.1;
                    this.maxHealth += 100;
                }
                case 3 -> {
                    this.defence += 0.1;
                    this.maxHealth += 200;
                }
                case 4 -> {
                    this.defence += 0.1;
                    this.maxHealth += 300;
                }
                case 5 -> {
                    this.defence += 0.2;
                    this.maxHealth += 1000;
                }
                default -> throw new IllegalArgumentException(this.getName() + " reached maximum level: " + this.level);
            }
            System.out.println("upgraded");
            this.currentHealth = this.maxHealth;
            this.drawHealthBar();
        }



    }

}
