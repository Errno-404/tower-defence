import agh.ics.oop.Hitboxes.CircularHitbox;
import agh.ics.oop.Hitboxes.RectangularHitbox;
import agh.ics.oop.Vector;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TestOfEverything {

    @Test
    public void collisionsTest(){


        System.out.println("================================ Collisions tests ===============================");
        RectangularHitbox h1 = new RectangularHitbox(new Vector(1.0, 2.0), new Vector(3.0, 4.0));
        RectangularHitbox h2 = new RectangularHitbox(new Vector(2.0, 3.0), new Vector(4.0, 5.0));

        System.out.println("Test 1:");


        assertTrue(h1.collidesWith(h2));
        assertTrue(h2.collidesWith(h1));
        System.out.println("Test zaliczony!");

        System.out.println("Test 2:");
        RectangularHitbox h3 = new RectangularHitbox(new Vector(2.0, 0.0), new Vector(4.0,2.0));
        RectangularHitbox h4 = new RectangularHitbox(new Vector(0.0, 2.0), new Vector(2.0, 4.0));

        assertTrue(h3.collidesWith(h4));
        assertTrue(h4.collidesWith(h3));
        System.out.println("Test zaliczony!");

        System.out.println("Test 3:");
        RectangularHitbox h5 = new RectangularHitbox(new Vector(0.0, 0.0), new Vector(4.0, 4.0));
        RectangularHitbox h6 = new RectangularHitbox(new Vector(2.0, 2.0), new Vector(3.0, 3.0));

        assertTrue(h5.collidesWith(h6));
        assertTrue(h6.collidesWith(h5));
        assertTrue(h5.collidesWith(h5));
        System.out.println("Test zaliczony!");


        System.out.println("Test 4:");

        RectangularHitbox h7 = new RectangularHitbox(new Vector(0.0, 0.0), new Vector(4.0, 4.0));
        RectangularHitbox h8 = new RectangularHitbox(new Vector(3.0, 5.0), new Vector(8.0, 7.0));

        assertFalse(h7.collidesWith(h8));
        assertFalse(h8.collidesWith(h7));

        System.out.println("Test zaliczony!");
    }

    @Test
    public void hitboxTest(){
        RectangularHitbox hitbox = new RectangularHitbox(new Vector(0.0, 0.0), new Vector(1.0 , 1.0));
        hitbox.moveAlongVector(new Vector(3.0, 4.0));
        System.out.println(hitbox.getUpperLeft());

    }
}
