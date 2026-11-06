package hu.bme.iit.blockdodger;

import hu.bme.iit.blockdodger.io.FileHandler;
import hu.bme.iit.blockdodger.model.Block;
import hu.bme.iit.blockdodger.model.Player;
import hu.bme.iit.blockdodger.model.PowerUp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    private enum State { IDLE, PLAYING, GAME_OVER }

    private State state = State.IDLE;

    private final int W, H;
    private final Timer timer;
    private final Random rnd = new Random();

    private Player player;
    private final ArrayList<Block> blocks = new ArrayList<>();
    private final ArrayList<PowerUp> powerUps = new ArrayList<>();

    private boolean left, right;
    private int score = 0;
    private int highScore = 0;
    private List<Integer> topScores = new ArrayList<>();

    private boolean invincible = false;
    private int invMsLeft = 0;

    private int blockSpawnCd = 0;
    private int powerSpawnCd = 3000;

    private final JButton startButton;

    public GamePanel(int width, int height) {
        this.W = width;
        this.H = height;
        setPreferredSize(new Dimension(W, H));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        setLayout(null); //layout for the start button

        timer = new Timer(16, this);

        // load scores on startup
        topScores = FileHandler.loadTopScoresSafe();
        if (!topScores.isEmpty()) {
            highScore = topScores.get(0);
        }

        // center "Start Game" button
        startButton = new JButton("Start Game");
        int bw = 120;
        int bh = 30;
        startButton.setBounds(W / 2 - bw / 2, H / 2 + 50, bw, bh);
        startButton.addActionListener(e -> startFromMenu());
        add(startButton);
        startButton.setVisible(true);
    }

// called from menu and button
    public void startFromMenu() {
        if (state == State.IDLE || state == State.GAME_OVER) {
            startGame();
        }
    }

 // menu option to show high scores
    public void showHighScores() {
        List<Integer> scores = FileHandler.loadTopScoresSafe();
        if (scores.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No scores yet.",
                    "High Scores",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
    }

    StringBuilder sb = new StringBuilder("=== TOP 3 SCORES ===\n");
        for (int i = 0; i < scores.size(); i++) {
            sb.append(i + 1).append(". ").append(scores.get(i)).append("\n");
    }

        JOptionPane.showMessageDialog(this,
                sb.toString(),
                "High Scores",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void startGame() {
        state = State.PLAYING;

        player = new Player(W / 2 - 20, H - 60, 40, 20, 6, W);
        blocks.clear();
        powerUps.clear();

        score = 0;
        invincible = false;
        invMsLeft = 0;

        blockSpawnCd = 0;
        powerSpawnCd = 2000;

        startButton.setVisible(false);
        timer.start();
        repaint();
    }

    private void goToIdleAfterGameOver() {
        state = State.IDLE;
        timer.stop();
        startButton.setVisible(true);
        repaint();
    }

    private void gameOver() {
        state = State.GAME_OVER;
        timer.stop();

        // updating stored scores using collections
        FileHandler.saveHighScoreSafe(score);
        topScores = FileHandler.loadTopScoresSafe();
        if (!topScores.isEmpty()) {
            highScore = topScores.get(0);
        }

        Object[] options = {"Restart", "Exit"};
        int choice = JOptionPane.showOptionDialog(this,
                "GAME OVER\nScore: " + score + "\nHigh Score: " + highScore,
                "Game Over",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                "Restart");

        if (choice == JOptionPane.YES_OPTION) {
            startGame();
        } else if (choice == JOptionPane.NO_OPTION) {
            System.exit(0);
        } else {
            // user closed the popup then screen goes back to idle screen
            goToIdleAfterGameOver();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (state != State.PLAYING) return;

        score++;

        if (left) player.moveLeft();
        if (right) player.moveRight();

        if (invincible) {
            invMsLeft -= 16;
            if (invMsLeft <= 0) {
                invincible = false;
            }
        }

        // spawn blocks
        blockSpawnCd -= 16;
        if (blockSpawnCd <= 0) {
            spawnBlock();
            blockSpawnCd = Math.max(150, 800 - score / 10);
        }

        // spawn power-ups
        powerSpawnCd -= 16;
        if (powerSpawnCd <= 0) {
            spawnPowerUp();
            powerSpawnCd = 3000 + rnd.nextInt(2000);
        }

        // move blocks
        Iterator<Block> it = blocks.iterator();
        while (it.hasNext()) {
            Block b = it.next();
            b.moveDown();
            if (b.offScreen(H)) it.remove();
        }

        // move power-ups
        Iterator<PowerUp> pit = powerUps.iterator();
        while (pit.hasNext()) {
            PowerUp p = pit.next();
            p.moveDown();
            p.tick(16);
            if (p.isExpired() || p.getY() > H) pit.remove();
        }

        // collisions
        Rectangle pr = player.getBounds();

        for (Block b : blocks) {
            if (!invincible && pr.intersects(b.getBounds())) {
                gameOver();
                return;
            }
        }

        Iterator<PowerUp> hit = powerUps.iterator();
        while (hit.hasNext()) {
            PowerUp p = hit.next();
            if (pr.intersects(p.getBounds())) {
                invincible = true;
                invMsLeft = 4000;
                hit.remove();
            }
        }

        repaint();
    }

    private void spawnBlock() {
        int w = 20 + rnd.nextInt(30);
        int h = 15 + rnd.nextInt(25);
        int x = rnd.nextInt(Math.max(1, W - w));
        int speed = 3 + rnd.nextInt(3) + score / 400;
        blocks.add(new Block(x, -h, w, h, speed));
    }

    private void spawnPowerUp() {
        int size = 16;
        int x = rnd.nextInt(Math.max(1, W - size));

        // power-up a bit slower than blocks, and increases slowly
        int speed = 2 + Math.min(3, score / 600); 
        powerUps.add(new PowerUp(x, -size, size, 9000, speed));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // background
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, W, H);

        if (state == State.IDLE) {
            g.setColor(Color.WHITE);
            g.setFont(g.getFont().deriveFont(Font.BOLD, 20f));
            int centerY = H / 2 - 60;

            g.setFont(g.getFont().deriveFont(Font.BOLD, 20f));
            g.drawString("BLOCK DODGER", W / 2 - 80, centerY);

            g.setFont(g.getFont().deriveFont(Font.PLAIN, 12f));
            g.drawString("Use the Game menu or the Start button to begin", W / 2 - 150, centerY + 30);
            g.drawString("Move with LEFT and RIGHT arrows to dodge blocks and grab power-ups.", W / 2 - 220, centerY + 50);

            return;
        }

        if (state == State.GAME_OVER) {
            g.setColor(Color.WHITE);
            g.drawString("GAME OVER", W / 2 - 30, H / 2 - 20);
            g.drawString("Score: " + score, W / 2 - 30, H / 2);
            g.drawString("High Score: " + highScore, W / 2 - 40, H / 2 + 20);
            g.drawString("You can restart from the popup or start again from the Game menu.", W / 2 - 190, H / 2 + 40);
            return;
        }

        // PLAYING
        if (invincible) {
            g.setColor(new Color(255, 215, 0));
            g.fillRect(player.getX() - 2, player.getY() - 2,
                    player.getWidth() + 4, player.getHeight() + 4);
        }

        g.setColor(Color.CYAN);
        player.draw(g);

        g.setColor(Color.RED);
        for (Block b : blocks) {
            b.draw(g);
        }

        g.setColor(Color.GREEN);
        for (PowerUp p : powerUps) {
            p.draw(g);
        }

        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 18);
        g.drawString("High: " + highScore, 100, 18);
        if (invincible) {
            g.drawString("INVINCIBLE", 200, 18);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (state != State.PLAYING) return;

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = true;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (state != State.PLAYING) return;

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = false;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }
}
