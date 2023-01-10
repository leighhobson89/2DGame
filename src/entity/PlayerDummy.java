package entity;

import main.GamePanel;

public class PlayerDummy extends Entity {
    public static final String npcName = "PlayerDummy";

    public PlayerDummy(GamePanel gp) {
        super(gp);
        name = npcName;
        getImage(gp.ui.colorOutfit);
    }

    public void getImage(String colorOutfit) {
        up1 = setup("/player/mum_up1_" + colorOutfit, gp.tileSize, gp.tileSize);
        up2 = setup("/player/mum_up2_" + colorOutfit, gp.tileSize, gp.tileSize);
        down1 = setup("/player/mum_down1_" + colorOutfit, gp.tileSize, gp.tileSize);
        down2 = setup("/player/mum_down2_" + colorOutfit, gp.tileSize, gp.tileSize);
        left1 = setup("/player/mum_left1_" + colorOutfit, gp.tileSize, gp.tileSize);
        left2 = setup("/player/mum_left2_" + colorOutfit, gp.tileSize, gp.tileSize);
        right1 = setup("/player/mum_right1_" + colorOutfit, gp.tileSize, gp.tileSize);
        right2 = setup("/player/mum_right2_" + colorOutfit, gp.tileSize, gp.tileSize);
    }
}
