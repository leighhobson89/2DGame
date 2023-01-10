package entity;

import main.GamePanel;

import java.util.Objects;

public class Projectile extends Entity {

    Entity user;

    public Projectile (GamePanel gp) {
        super(gp);
    }

    public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.stressLevel = 0;
    }

    public void update () {

        int projX = ((worldX + solidArea.x)/gp.tileSize);
        int projY = ((worldY + solidArea.y)/gp.tileSize);
        if(Objects.equals(this.name, "Pip's Bone")) {
            gp.aSetter.boneX = (worldX + solidArea.x)/gp.tileSize;
            gp.aSetter.boneY = (worldY + solidArea.y)/gp.tileSize;
        } else if (Objects.equals(this.name, "Chopped Chicken")) {
            gp.aSetter.choppedChickenX = (worldX + solidArea.x)/gp.tileSize;
            gp.aSetter.choppedChickenY = (worldY + solidArea.y)/gp.tileSize;
        }

        if (user == gp.player) {
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            boolean tileState = gp.cChecker.checkTile(this);
            int objIndex = gp.cChecker.checkEntity(this, gp.obj);

            if (objIndex != 999) {
                gp.aSetter.setObjectAfterStart(gp.player.itemToThrow, gp.currentMap, projX, projY, true);
                switch (this.name) {
                    case "Chopped Chicken" -> gp.playSFX(31);
                    case "Pip's Bone" -> gp.playSFX(20);
                }
                alive = false;
            }

            if (monsterIndex != 999) {
                gp.aSetter.setObjectAfterStart(gp.player.itemToThrow, gp.currentMap, projX, projY, true);
                gp.player.damageMonster(monsterIndex, this, attack, knockBackPower);
                generateParticle(this, gp.monster[gp.currentMap][monsterIndex]);
                switch (this.name) {
                    case "Chopped Chicken" -> gp.playSFX(31);
                    case "Pip's Bone" -> gp.playSFX(20);
                }
                alive = false;
            }
            if (npcIndex != 999) {
                gp.aSetter.setObjectAfterStart(gp.player.itemToThrow, gp.currentMap, projX, projY, true);
                gp.gameState = gp.dialogueState;
                if (Objects.equals(gp.npc[gp.currentMap][npcIndex].name, "Dad")) {
                    gp.npc[gp.currentMap][npcIndex].startDialogue(gp.npc[gp.currentMap][npcIndex], 62);
                } else if (Objects.equals(gp.npc[gp.currentMap][npcIndex].name, "Phoebe") || Objects.equals(gp.npc[gp.currentMap][npcIndex].name, "Pip")) {
                    gp.npc[gp.currentMap][npcIndex].startDialogue(gp.npc[gp.currentMap][npcIndex], 7);
                } else if (Objects.equals(gp.npc[gp.currentMap][npcIndex].name, "Merchant")) {
                    gp.npc[gp.currentMap][npcIndex].startDialogue(gp.npc[gp.currentMap][npcIndex], 1);
                }
                switch (this.name) {
                    case "Chopped Chicken" -> gp.playSFX(31);
                    case "Pip's Bone" -> gp.playSFX(20);
                }
                alive = false;
            }
            if (tileState) {
                gp.aSetter.setObjectAfterStart(gp.player.itemToThrow, gp.currentMap, projX, projY, true);
                switch (this.name) {
                    case "Chopped Chicken" -> gp.playSFX(31);
                    case "Pip's Bone" -> gp.playSFX(20);
                }
                alive = false;
            }
        }
        if (user != gp.player) {
            boolean contactPlayer = gp.cChecker.checkPlayer(this);
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            boolean tileState = gp.cChecker.checkTile(this);
            if (!gp.player.invincible && contactPlayer) {
                damagePlayer(attack);
//                generateParticle(user.projectile, user.projectile); //ONLY IF NPC OR MONSTER THROWS OBJECT
                gp.gameState = gp.dialogueState;
                startDialogue(gp.player, 11);
                gp.playSFX(8);
                alive = false;
            }
            if (monsterIndex != 999) {
                gp.player.damageMonster(monsterIndex, this, attack, knockBackPower);
                gp.playSFX(21);
                alive = false;
            }
            if (tileState) {
                gp.playSFX(21);
                alive = false;
            }
        }

        switch (direction) {
            case "up" -> worldY -= speed;
            case "down" -> worldY += speed;
            case "left" -> worldX -= speed;
            case "right" -> worldX += speed;
        }

        stressLevel++;

        if(stressLevel >= maxStress) { //if throwing item reaches end of throwing range
            gp.aSetter.setObjectAfterStart(gp.player.itemToThrow, gp.currentMap, projX, projY, true);
            alive = false;
            switch (this.name) {
                case "Chopped Chicken" -> gp.playSFX(31);
                case "Pip's Bone" -> gp.playSFX(20);
            }
        }

        spriteCounter++;
        if(spriteCounter > 12) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public boolean haveResource(Entity user) {
        return false;
    }

    public void subtractResource(Entity user) {
    }
}
