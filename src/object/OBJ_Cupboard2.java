package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Cupboard2 extends Entity {

    final GamePanel gp;
    public static final String OBJ_NAME = "Cupboard2";

    public OBJ_Cupboard2(GamePanel gp) {

        super(gp);
        this.gp = gp;

        isUpdateable = false;
        type = type_obstacle;
        name = OBJ_NAME;
        displayName = "Cupboard2";
        image = setup("/objects/cupboard2", gp.tileSize, gp.tileSize);
        down1 = image;
        direction = "down";
        collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDialogue();
    }

    public void setDialogue() {
        dialogueText[0][0] = "This cardigan is nice and warm\nI should put this on!";
        dialogueText[1][0] = "The cupboard is empty!";
    }

    public void interact() {
        if (!opened) {
            gp.playSFX(4);
            startDialogue(this, 0);
            opened = true;
            gp.eHandler.cupboardHall();
        } else {
            startDialogue(this, 1);
        }
        gp.keyH.enterPressed = false;
    }
}
