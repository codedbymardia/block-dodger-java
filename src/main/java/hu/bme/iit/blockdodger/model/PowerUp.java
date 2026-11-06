package hu.bme.iit.blockdodger.model;

import java.awt.*;

public class PowerUp {
    private int x, y, size;
    private int timeLeftMs;
    private int speed;

    public PowerUp(int x, int y, int size, int ttlMs, int speed) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.timeLeftMs = ttlMs;
        this.speed = speed;
    }

    public void moveDown() {
        y += speed;
    }

    public void tick(int dtMs) {
        timeLeftMs -= dtMs;
    }

    public boolean isExpired() {
        return timeLeftMs <= 0;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }

    public int getY() {
        return y;
    }

    public void draw(Graphics g) {
        g.fillRect(x, y, size, size);
    }
}
