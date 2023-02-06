package agh.ics.oop.gui;

import agh.ics.oop.buildings.BuildingFactory;
import agh.ics.oop.buildings.BuildingsName;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class TowerPane extends VBox {

    BuildingsName[] buildingList;
    GameScreen gs;

    Shop shop;

    public TowerPane(GameScreen gs){
        this.buildingList = new ArrayList<>();
        for(BuildingsName bn: BuildingsName.values()){
            buildingList.add(BuildingFactory.getBuildingById(bn,0,0,gs,gs.gameEngine));
        }

        this.gs = gs;

        this.setSpacing(20);
        this.createBuildings();

        this.shop = new Shop(gs);
        this.gs.gameEngine.setEnemyKilledObserver(this.shop);
    }


    private void createBuildings(){
        for(BuildingsName building : buildingList){
            Image img = building.getImage();
            ImageView imgView = new ImageView(img);

            imgView.setOnMouseClicked(event -> this.shop.buy(building));


            this.getChildren().add(imgView);


        }
    }

    public Shop getShop(){
        return this.shop;
    }
}
