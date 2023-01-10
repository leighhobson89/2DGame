package tile;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Map extends TileManager {

    final GamePanel gp;
    BufferedImage[] worldMap;
    public boolean miniMapOn = false;

    public Map (GamePanel gp) {
        super(gp);
        this.gp = gp;
        createWorldMap();
    }
    public void createWorldMap() {
        worldMap = new BufferedImage[gp.currentMap];
        int worldMapWidth = gp.tileSize * gp.maxWorldCol;
        int worldMapHeight = gp.tileSize * gp.maxWorldRow;

        for (int i = 0; i < gp.currentMap; i++) {
            worldMap[i] = new BufferedImage(worldMapWidth, worldMapHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = worldMap[i].createGraphics();

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                int tileNum = mapTileNum[i][col][row];
                int x = gp.tileSize * col;
                int y = gp.tileSize * row;
                g2.drawImage(tile[tileNum].image, x, y, null);

                col++;
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            g2.dispose();
        }
    }
    public void drawFullMapScreen(Graphics2D g2) {
        //Background Color
        g2.setColor(Color.BLACK);
        g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);

        //Draw Map
        int width = 700;
        int height = 350;
        int x = gp.screenWidth/2 - width/2;
        int y = gp.screenHeight/2 - height/2;
        g2.drawImage(worldMap[gp.currentMap],x, y, width, height, null);

        //Draw Player
        double scaleWidth = (double)(gp.tileSize * gp.maxWorldCol) / width;
        double scaleHeight = (double)(gp.tileSize * gp.maxWorldRow) / height;
        int playerX = (int)(x + gp.player.worldX/scaleWidth);
        int playerY = (int)(y + gp.player.worldY/scaleHeight);
        int playerSize = (int)(gp.tileSize/scaleWidth);
        g2.drawImage(gp.player.down1, playerX, playerY, playerSize, playerSize, null);

        //Hint
        g2.setFont(gp.ui.maruMonica.deriveFont(32f));
        g2.setColor(Color.WHITE);
        g2.drawString("Press \"M\" to close", 700, 500);
    }

    public void drawMiniMap(Graphics2D g2) {
        if(miniMapOn) {
            //Draw Map
            int width = 350;
            int height = 175;
            int x = 50;
            int y = 350;

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
            g2.drawImage(worldMap[gp.currentMap],x, y, width, height, null);

            //Draw Player
            double scaleWidth = (double)(gp.tileSize * gp.maxWorldCol) / width;
            double scaleHeight = (double)(gp.tileSize * gp.maxWorldRow) / height;
            int playerX = (int)(x + gp.player.worldX/scaleWidth);
            int playerY = (int)(y + gp.player.worldY/scaleHeight);
            int playerSize = (int)(gp.tileSize/scaleWidth);
            g2.drawImage(gp.player.down1, playerX, playerY, playerSize, playerSize, null);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }
}
