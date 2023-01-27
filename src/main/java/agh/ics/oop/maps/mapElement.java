package agh.ics.oop.maps;

import agh.ics.oop.Attackers;
import agh.ics.oop.Hitboxes.Hitbox;
import agh.ics.oop.Hitboxes.RectangularHitbox;
import agh.ics.oop.Proejctiles.Projectile;
import agh.ics.oop.buildings.AttackingBuilding;
import agh.ics.oop.gui.CanvasElement;

import java.util.LinkedList;

public class mapElement {
    Integer x;
    Integer y;

    Hitbox border;

    CanvasElement canvasElement; //odpowiadajaca czesc canvasu
    Integer buildingID;

    boolean reachable;
    boolean placeable;

    double flowFieldValue; //do znajdywania najkrotszych sciezek

    LinkedList<Projectile> projectileList; //lista projectili nad danym polem mapy
    LinkedList<Attackers> enemyList; //list przeciwnikow na danym polu

    LinkedList<AttackingBuilding> inRangeOf;
}
