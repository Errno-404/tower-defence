package agh.ics.oop.Interfaces;

import agh.ics.oop.Attackers;
import agh.ics.oop.Vector;

public interface EnemyObserver {
    public void reportNewIndexEnemy(Vector old, Vector newpos, Attackers a);
}
