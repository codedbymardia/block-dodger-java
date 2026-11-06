package hu.bme.iit.blockdodger.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PowerUpTest {
    @Test void powerUpExpiresAfterTick() {
        PowerUp pu = new PowerUp(0, 0, 16, 10, 2);
        pu.tick(10);
        assertTrue(pu.isExpired());
    }
    @Test void moveDownChangesY() {
        PowerUp pu = new PowerUp(0, 0, 16, 100, 2);
        pu.moveDown();
        assertTrue(pu.getY() > 0);
    }
    @Test
void powerUpNotExpiredTooEarly() {
    PowerUp pu = new PowerUp(0, 0, 16, 100, 2);
    pu.tick(50); // half the time
    // it should not expire yet
    assertFalse(pu.isExpired());
}

}
