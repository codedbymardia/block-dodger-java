package hu.bme.iit.blockdodger.model;

import java.awt.*;

public class Player {
    private int x, y, w, h, speed;
    private final int panelWidth;

    public Player(int x, int y, int w, int h, int speed, int panelWidth) {
        this.x = x; this.y = y; this.w = w; this.h = h;
        this.speed = speed; this.panelWidth = panelWidth;
    }

    public void moveLeft() { x = Math.max(0, x - speed); }
    public void moveRight() { x = Math.min(panelWidth - w, x + speed); }
    public Rectangle getBounds() { return new Rectangle(x, y, w, h); }
    public void draw(Graphics g) { g.fillRect(x, y, w, h); }

//for test
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return w; }
    public int getHeight() { return h; }
}
