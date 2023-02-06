
package agh.ics.oop.gui;

import agh.ics.oop.Interfaces.ShopSelectionObserver;
import agh.ics.oop.buildings.Building;
import agh.ics.oop.buildings.BuildingFactory;
import agh.ics.oop.buildings.BuildingsName;
import agh.ics.oop.buildings.Castle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class TowerPane extends VBox {

    BuildingsName[] buildingList;
    GameScreen gs;

    Shop shop;

    public TowerPane(GameScreen gs) {
        this.buildingList = BuildingsName.values();
        this.gs = gs;

        this.setSpacing(20);
        this.createBuildings();

        this.shop = new Shop(gs);
        this.gs.gameEngine.setEnemyKilledObserver(this.shop);
    }


    private void createBuildings() {
        for (BuildingsName building : buildingList) {

            if(building.convert() == 0){
                continue;
            }
            Image img = building.getImage();
            ImageView imgView = new ImageView(img);

            imgView.setOnMouseClicked(event -> this.shop.buy(building));


            this.getChildren().add(imgView);
        }
    }

    public Shop getShop() {
        return this.shop;
    }
}
