package entity;

import main.GamePanel;

import java.awt.*;

public class Particle  extends Entity {

    final Entity generator;
    final Color color;
    final int size;
    final int xd;
    int yd;

    public Particle(GamePanel gp, Entity generator, Color color, int size, int speed, int maxStress, int xd, int yd) {
        super(gp);

        this.generator = generator;
        this.color = color;
        this.size = size;
        this.speed = speed;
        this.maxStress = maxStress;
        this.xd = xd;
        this.yd = yd;

        stressLevel = 0;
        int offset = (gp.tileSize/2) - (size/2);
        worldX = generator.worldX + offset;
        worldY = generator.worldY + offset;
    }
    public void update() {

        stressLevel++;

        if (stressLevel > (double) maxStress/1.5) {
            yd++;
        }

        worldX += xd * speed;
        worldY += yd * speed;

        if (stressLevel == maxStress) {
            alive = false;
        }
    }
    public void draw(Graphics2D g2) {

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        g2.setColor(color);
        g2.fillRect(screenX, screenY, size, size); //drawImage method if you want sprites instead of rectangle
    }
}
