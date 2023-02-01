package agh.ics.oop.Interfaces;

import agh.ics.oop.buildings.Building;

public interface BuildingDestroyedObserver {
    public void reportBuildingDestroyed(Building b);
}
