package agh.ics.oop.gui;

import agh.ics.oop.buildings.Building;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class TowerPane extends VBox {

    ArrayList<Building> buildingList;
    GameScreen gs;

    public TowerPane(GameScreen gs ,ArrayList<Building> blist){
        this.gs = gs;
        this.buildingList = blist;
        //TODO Make list of buttons, on button click call GameScreen.setSelectedListBuilding

        this.setSpacing(20);
        this.
        createButtons(this.buildingList);



    }


    private void createButtons(ArrayList<Building> buildingList){
        for(Building building : buildingList){
            Image img = building.getImage();
            ImageView imgView = new ImageView(img);

            imgView.setOnMouseClicked(event -> this.gs.setSelectedListBuilding(2));


            this.getChildren().add(imgView);
        }
    }
}
