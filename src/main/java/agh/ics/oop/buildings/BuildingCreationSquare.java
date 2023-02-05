package agh.ics.oop.buildings;

import agh.ics.oop.Constants;
import agh.ics.oop.Hitboxes.RectangularHitbox;
import agh.ics.oop.Vector;
import agh.ics.oop.maps.GameMap;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BuildingCreationSquare {
    GameMap map;
    int width;
    int height;

    int anchorX;
    int anchorY;

    private ImageView canPlaceImage;
    private ImageView cantPlaceImage;

    RectangularHitbox hitbox;

    public boolean validPosition;

    public BuildingCreationSquare(int width, int height, int x, int y,GameMap map){
        this.width = width;
        this.height = height;
        this.anchorX = x;
        this.anchorY = y;

        this.hitbox = new RectangularHitbox(new Vector(x,y), new Vector(x+width, y+height) );
        try {
            this.canPlaceImage = new ImageView(new Image(new FileInputStream("src/main/resources/greenRect.png")));
            this.cantPlaceImage = new ImageView(new Image(new FileInputStream("src/main/resources/redRect.png")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        this.map = map;
    }

    public void draw(){
        boolean canPlace = this.map.canPlace(this.hitbox);
        this.validPosition = this.map.canPlace(this.hitbox);
        for(int i = this.anchorX; i<Math.min(Constants.numberOfTiles,this.anchorX+this.width); i++){
            for(int j = this.anchorY; j<Math.min(Constants.numberOfTiles,this.anchorY+this.height); j++){
                if(this.map.map[i][j].canvasElement != null) {
                    if (canPlace) {
                        this.map.map[i][j].canvasElement.setTemporaryView(this.canPlaceImage);
                    } else {
                        this.map.map[i][j].canvasElement.setTemporaryView(this.cantPlaceImage);
                    }
                }
            }
        }
    }

    public void move(int newX, int newY){
        for(int i = this.anchorX; i<Math.min(Constants.numberOfTiles,this.anchorX+this.width); i++){
            for(int j = this.anchorY; j<Math.min(Constants.numberOfTiles,this.anchorY+this.height); j++){
                if(this.map.map[i][j].canvasElement != null){
                    this.map.map[i][j].canvasElement.revertTemporaryView();
                }

            }
        }

        this.anchorX = newX;
        this.anchorY = newY;

        this.hitbox.upperLeft = new Vector(newX*Constants.tileSize, newY*Constants.tileSize);
        this.hitbox.lowerRight = new Vector((newX + width)*Constants.tileSize, (newY + height)*Constants.tileSize);

        this.draw();
    }

    public void remove(){
        for(int i = this.anchorX; i<Math.min(Constants.numberOfTiles,this.anchorX+this.width); i++){
            for(int j = this.anchorY; j<Math.min(Constants.numberOfTiles,this.anchorY+this.height); j++){
                if(this.map.map[i][j].canvasElement != null){
                    this.map.map[i][j].canvasElement.revertTemporaryView();
                }

            }
        }
    }


}
