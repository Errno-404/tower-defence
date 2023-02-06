package agh.ics.oop.gui;

import agh.ics.oop.Interfaces.EnemyKilledObserver;
import agh.ics.oop.Interfaces.ShopSelectionObserver;
import agh.ics.oop.buildings.BuildingsName;

import java.util.HashMap;

public class Shop implements EnemyKilledObserver {

    private double gold;
    ShopSelectionObserver obs;

    public Shop(ShopSelectionObserver o){
        this.obs = o;
        this.gold = 5000;

    }

    private final static HashMap<BuildingsName, Integer> shopList = new HashMap<>(){
        {
            put(BuildingsName.TOWER, 5);
            put(BuildingsName.HORIZONTALWALL,5);
            put(BuildingsName.VERTICALWALL,5);
        }
    };

    public void buy(BuildingsName building){
        if(this.gold >= shopList.get(building)){
            this.obs.updateSelected(building);
            this.gold-=shopList.get(building);
        }
    }

    public void addGold(Integer n){
        this.gold+=n;
    }
}
