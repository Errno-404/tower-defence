import agh.ics.oop.Vector;
import agh.ics.oop.maps.Plains;
import org.junit.jupiter.api.Test;


public class GeneralTest {

    @Test
    public void test(){
        Plains plains = new Plains(3, 6, 2.5);

        plains.canPlace(new Vector(3.0, 2.0), 2, 2);
    }

}
