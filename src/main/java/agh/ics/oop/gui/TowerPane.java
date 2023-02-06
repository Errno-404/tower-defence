package agh.ics.oop.gui;

import agh.ics.oop.buildings.Building;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class TowerPane extends VBox {

    ArrayList<Building> buildingList;
    GameScreen gs;

    public TowerPane(GameScreen gs ,ArrayList<Building> blist){
        this.gs = gs;
        this.buildingList = blist;
        //TODO Make list of buttons, on button click call GameScreen.setSelectedListBuilding


        for(int i = 0; i < 5; i ++){
            Button button = new Button("Click me");
            this.getChildren().add(button);
        }


    }


    private void createButtons(ArrayList<Building> buildingList){
        for(Building building : buildingList){
            Image img = building.getImage();
            ImageView imgView = new ImageView(img);
            this.getChildren().add(imgView);
        }
    }
}
