package main;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {

    public static JFrame window;
    public static void main(String[] args) throws IOException, FontFormatException {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D Game");
        new Main().setIcon();

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        //gamePanel.config.loadConfig();
        if (gamePanel.fullScreenOn) {
            window.setUndecorated(true);
        }

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        //gamePanel.setupGame();
        gamePanel.startGameThread();
    }

    public void setIcon() {
        //ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("player/gun.png"));
        //window.setIconImage(icon.getImage());
    }
}
