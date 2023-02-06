package agh.ics.oop.gui;

import agh.ics.oop.buildings.Building;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class TowerPane extends VBox {

    ArrayList<Building> buildingList;
    GameScreen gs;

    public TowerPane(GameScreen gs ,ArrayList<Building> blist){
        this.gs = gs;
        this.buildingList = blist;

        this.setSpacing(20);
        this.createBuildings(this.buildingList);



    }


    private void createBuildings(ArrayList<Building> buildingList){
        for(Building building : buildingList){
            Image img = building.getImage();
            ImageView imgView = new ImageView(img);

            imgView.setOnMouseClicked(event -> this.gs.setSelectedListBuilding(2));


            this.getChildren().add(imgView);
        }
    }
}
