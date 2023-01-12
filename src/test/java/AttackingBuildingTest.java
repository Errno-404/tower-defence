import agh.ics.oop.Attack;
import agh.ics.oop.Vector;
import agh.ics.oop.buildings.Tower;
import org.junit.jupiter.api.Test;

public class AttackingBuildingTest {

    @Test
    public void test(){
        Tower tower = new Tower(2,2,new Vector(5, 6), 30,new Attack(40));

        tower.getHit(new Attack(20));
        tower.getHit(new Attack(30));
    }

}
