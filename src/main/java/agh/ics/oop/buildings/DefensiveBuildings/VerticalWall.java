package agh.ics.oop.buildings.DefensiveBuildings;

import agh.ics.oop.Vector;
import agh.ics.oop.buildings.BuildingsName;
import agh.ics.oop.gui.GameScreen;
import javafx.scene.image.Image;

public class VerticalWall extends DefensiveBuilding{
    public VerticalWall(int widthInTiles, int heightInTiles, Vector position, Image img, GameScreen gs, double health, double defence) {
        super(widthInTiles, heightInTiles, position, health, img, gs, defence);
        this.bname = BuildingsName.HORIZONTALWALL;
    }

    @Override
    public void drawHealthBar(){
        this.healthBar.drawVertical(gs.gc, this.hitbox);
    }
}
