package hu.bme.iit.blockdodger.model;

import java.awt.*;

public class Block {
    private int x, y, w, h, speed;

    public Block(int x, int y, int w, int h, int speed) {
        this.x = x; this.y = y; this.w = w; this.h = h; this.speed = speed;
    }

    public void moveDown() { y += speed; }
    public boolean offScreen(int panelHeight) { return y > panelHeight; }
    public Rectangle getBounds() { return new Rectangle(x, y, w, h); }
    public void draw(Graphics g) { g.fillRect(x, y, w, h); }

    // for tests
    public int getY() { return y; }
}
