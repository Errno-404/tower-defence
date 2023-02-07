package agh.ics.oop.gui;

import agh.ics.oop.Interfaces.BuyObserver;
import agh.ics.oop.Interfaces.EnemyKilledObserver;
import agh.ics.oop.Interfaces.ShopSelectionObserver;
import agh.ics.oop.Interfaces.WaveStateObserver;
import agh.ics.oop.buildings.BuildingsName;

import java.util.ArrayList;
import java.util.HashMap;

public class Shop implements EnemyKilledObserver, WaveStateObserver {

    private double gold;
    ShopSelectionObserver obs;

    private boolean isWaveStarted;

    private ArrayList<BuyObserver> buyObservers = new ArrayList<>();

    public Shop(ShopSelectionObserver o){
        this.obs = o;
        gold = 5000;

    }

    private final static HashMap<BuildingsName, Integer> shopList = new HashMap<>(){
        {
            put(BuildingsName.TOWER, 5);
            put(BuildingsName.HORIZONTALWALL,5);
            put(BuildingsName.VERTICALWALL,5);
            put(BuildingsName.CIRCLEBUILDING,5);
        }
    };

    public void buy(BuildingsName building){
        if(gold >= shopList.get(building)){
            this.obs.updateSelected(building);
            gold-=shopList.get(building);
        }
        this.addGold(0);

        this.buyObservers.forEach(buyObserver -> buyObserver.reportBuy(building));
    }
    @Override
    public void addGold(Integer n){
        gold+=n;
        System.out.println("buy");
    }
    @Override
    public void changeWaveState(){
        this.isWaveStarted = !isWaveStarted;
    }

    public double getGold(){
        return gold;
    }

    public void addBuyObserver(BuyObserver buyObserver){
        this.buyObservers.add(buyObserver);
    }


}
