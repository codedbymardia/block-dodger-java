package hu.bme.iit.blockdodger.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    @Test void moveLeftStopsAtZero() {
        Player p = new Player(0, 0, 40, 20, 6, 200);
        p.moveLeft();
        assertEquals(0, p.getX());
    }
    @Test void moveRightStopsAtEdge() {
        Player p = new Player(180, 0, 40, 20, 6, 200);
        p.moveRight();
        assertEquals(160, p.getX()); // 200 - width
    }
    @Test void boundsWidthHeightCorrect() {
        Player p = new Player(10, 10, 40, 20, 6, 200);
        assertEquals(40, p.getBounds().width);
        assertEquals(20, p.getBounds().height);
    }
}
