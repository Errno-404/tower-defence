
package agh.ics.oop.gui;

import agh.ics.oop.buildings.BuildingsName;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

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
        this.gs.gameEngine.addEnemyKilledObserver(this.shop);
        gs.waveManager.addObserver(this.shop);
    }


    private void createBuildings() {
        for (BuildingsName building : buildingList) {

            if(building.convert() == 0){
                continue;
            }
            Image img = building.getImage();
            ImageView imgView = new ImageView(img);

            // TODO zmienić buy na kliknięcie na mapie!

            imgView.setOnMouseClicked(event -> {
                if(!this.gs.isWaveStarted)  this.shop.buy(building);
            });


            this.getChildren().add(imgView);
        }
    }

    public Shop getShop() {
        return this.shop;
    }
}
