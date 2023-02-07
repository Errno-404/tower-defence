package agh.ics.oop.gui;

import agh.ics.oop.Interfaces.BuyObserver;
import agh.ics.oop.Interfaces.EnemyKilledObserver;
import agh.ics.oop.Interfaces.ShopSelectionObserver;
import agh.ics.oop.Interfaces.WaveStateObserver;
import agh.ics.oop.buildings.Building;
import agh.ics.oop.buildings.BuildingsName;

import java.util.ArrayList;
import java.util.HashMap;

public class Shop implements EnemyKilledObserver, WaveStateObserver {

    public static double gold;
    ShopSelectionObserver obs;

    private boolean isWaveStarted;

    public static ArrayList<BuyObserver> buyObservers = new ArrayList<>();

    static int currentGoldBlocked = 0;

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
            put(BuildingsName.SPREADSHOOTING,5);
        }
    };

    public void buy(BuildingsName building){
        if(!this.isWaveStarted && gold >= shopList.get(building) && currentGoldBlocked == 0){
            this.obs.updateSelected(building);
            currentGoldBlocked+=shopList.get(building);
        }
        else if(currentGoldBlocked != 0){
            this.obs.updateSelected(building);
            currentGoldBlocked+=shopList.get(building);
        }
        //this.addGold(0);


    }

    public static void commitTransaction(){
        gold-=currentGoldBlocked;
        buyObservers.forEach(buyObserver -> buyObserver.reportBuy(currentGoldBlocked));
        currentGoldBlocked=0;
    }

    public static void sellTower(BuildingsName b, int level){
        int sellValue = (int) shopList.get(b)/2 + 5*(level-1);
        gold += sellValue;
        buyObservers.forEach(buyObserver -> buyObserver.reportBuy(currentGoldBlocked));

    }
    @Override
    public void addGold(Integer n){
        gold+=n;

    }
    @Override
    public void changeWaveState(){
        this.isWaveStarted = !isWaveStarted;
    }

    public double getGold(){
        return gold;
    }

    public void addBuyObserver(BuyObserver buyObserver){
        buyObservers.add(buyObserver);
    }


}
