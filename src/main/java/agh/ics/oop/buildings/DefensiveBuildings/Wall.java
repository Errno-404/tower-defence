package agh.ics.oop.buildings.DefensiveBuildings;

import agh.ics.oop.Attacks.Attack;
import agh.ics.oop.Vector;
import agh.ics.oop.buildings.BuildingsName;
import agh.ics.oop.gui.GameScreen;
import javafx.scene.image.Image;

public class Wall extends DefensiveBuilding {
    public Wall(int widthInTiles, int heightInTiles, Vector position, Image img, GameScreen gs, double health, double defence) {
        super(widthInTiles, heightInTiles, position, health, img, gs, defence);
        this.bname = BuildingsName.HORIZONTALWALL;
    }


   /* @Override
    public void getHit(Attack attack) {
        super.getHit(attack);
        if(this.currentHealth == 0){
            System.out.println("Mur zniszczony!");
        }
    }*/

    @Override
    public void getHit(Attack a) {

    }
}
