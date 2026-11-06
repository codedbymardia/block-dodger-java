package hu.bme.iit.blockdodger.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BlockTest {
    @Test void moveDownIncreasesY() {
        Block b = new Block(0, 0, 20, 20, 3);
        b.moveDown();
        assertEquals(3, b.getY());
    }
    @Test void offScreenWhenBeyondHeight() {
        Block b = new Block(0, 601, 20, 20, 3);
        assertTrue(b.offScreen(600));
    }
    @Test void intersectsWithPlayerBounds() {
        Block b = new Block(10, 10, 20, 20, 1);
        Player p = new Player(15, 15, 40, 20, 6, 200);
        assertTrue(p.getBounds().intersects(b.getBounds()));
    }
}
