package view;

import javax.swing.*;

public class GameFrame extends JFrame {
    public GameFrame(){

        GamePanel gamePanel = new GamePanel();

        gamePanel.menu();

        while (!gamePanel.verified) {
            this.add(gamePanel);
            this.setTitle("Java Game");
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setResizable(false);
            this.pack();
            this.setVisible(true);
            this.setLocationRelativeTo(null);
            if (gamePanel.verified) {
                this.setSize(gamePanel.SCREEN_WIDTH, gamePanel.SCREEN_HEIGHT);
                gamePanel.startGame();
            }
        }
    }
}
