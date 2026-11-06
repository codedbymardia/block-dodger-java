package hu.bme.iit.blockdodger;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
         JFrame f = new JFrame("Block Dodger");
         f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);

         GamePanel panel = new GamePanel(800, 600);
        JMenuBar bar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");

        JMenuItem startItem = new JMenuItem("Start Game");
        startItem.addActionListener(e -> panel.startFromMenu());

         JMenuItem showHighItem = new JMenuItem("Show High Scores");
            showHighItem.addActionListener(e -> panel.showHighScores());

        JMenuItem exitItem = new JMenuItem("Exit");
            exitItem.addActionListener(e -> System.exit(0));

            gameMenu.add(startItem);
            gameMenu.add(showHighItem);
            gameMenu.add(exitItem);
            bar.add(gameMenu);

            f.setJMenuBar(bar);
            f.setContentPane(panel);
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}
