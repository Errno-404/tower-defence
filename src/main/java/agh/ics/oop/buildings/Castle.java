package agh.ics.oop.buildings;

import agh.ics.oop.Attack;
import agh.ics.oop.Vector;
import agh.ics.oop.gui.GameScreen;
import javafx.scene.image.Image;

public class Castle extends DefensiveBuilding {



    public Castle(Vector position, int width, int height, int health, int defence) {
        super(position, width, height, health, defence);
    }

    public Castle(int width, int height, int px, int py, int health, Image img, GameScreen gs){
        super(width, height, px, py, health, img, gs);

    }
    @Override
    public void getHit(Attack attack){
        super.getHit(attack);
        if(this.currentHealth == 0){
            System.out.println("Zamek zniszczony!");
        }
    }
}
