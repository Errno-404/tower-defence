import agh.ics.oop.Attack;
import agh.ics.oop.Vector;
import agh.ics.oop.buildings.Castle;
import agh.ics.oop.buildings.Wall;
import org.junit.jupiter.api.Test;


public class DefensiveBuildingTest {

    @Test
    public void test(){
        Castle castle = new Castle(new Vector(2, 3), 3, 3, 100, 0);
        Wall wall = new Wall(new Vector(5, 6), 10, 1, 50, 0);

        castle.getHit(new Attack(50));
        castle.getHit(new Attack(60));

        wall.upgradeDefence(5);
        wall.getHit(new Attack(50));
        System.out.println(wall.getCurrentHealth());

        wall.getHit(new Attack(80));



    }

}
