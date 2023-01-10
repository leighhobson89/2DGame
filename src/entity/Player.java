package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Player extends Entity {
    final KeyHandler keyH;
    final UtilityTool uTool = new UtilityTool();
    public final int STRESS_LEVEL_NEEDED_TO_CONSUME_PILLS = 0; //4
    final int MAX_SPEED_UNDER_INFLUENCE = 5;
    final int LENGTH_OF_SHOWER = 510;

    public boolean usedItemOrNot = false;
    public final int screenX;
    public final int screenY;
    public int standCounter;
    public boolean attackCanceled;
    public boolean lightUpdated;

    int randSet;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2); //put player in center of screen
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle(4, 16,32,32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        goesTransparentWhenHit = true;

        //setDefaultValues();
    }

    public void countDownTimerForItemEffect(String effect) {
        if ("Pills".equals(effect)) {
            pillsInProcess = true;
            dizzyFlag = true;
        } else if ("LightPills".equals(effect)) {
            lightUpdated = true;
            gp.eManager.lighting.update();
            lightPillsInProcess = true;
        }
    }

    public void cleanMissionList() {
        missionList = new ArrayList<>(); //clear missionList
//        missionList.add(MissionStates.BETWEEN_MISSIONS);
//        missionState = MissionStates.WEEDING_MISSION;
        missionSubstate = 0;
    }

//    public void setDefaultValues() {
//        worldX = gp.tileSize * 19; //19
//        worldY = gp.tileSize * 17;
//        defaultSpeed = 2;
//        boostSpeed = 4;
//        speed = defaultSpeed;
//        direction = "up";
//        spiderCount = 1;
//        weedCount = 0;
//        dizzyFlag = false;
//        speedBoost = false;
//        timesPassedOut = 0;
//        pillsConsumableNow = false;
//        firstTimePickUpBone = true;
//        randomCounter = setRandomCounter();
//        buzzCounter = 0;
//        inLivingRoom = true;
//        dadOption = 1;
//        dadHasGuitar = false;
//        dadPlayingGuitar = false;
//        hasOutsideDoorsKey = false;
//
//        //PLAYER STATUS
//        level = 1;
//        strength = 1; //more strength the more damage he gives
//        dexterity = 1; //more dexterity, less damage receives
//        exp = 0;
//        nextLevelExp = 6;
//        coin = 100;
//        maxStress = 10;
//        maxMana = 5; //max number of things to throw
//        mana = maxMana;
//        boneCount = 0;
//        choppedChickenCount = 0;
//        stressLevel = 0;
//        currentWeapon = null;
//        currentArmour = null;
//        currentLight = null;
//         // projectile = new OBJ_PipsBone(gp); activate this for projectile that doesn't affect toys in ui when wanting to add bone that can be picked up after throwing or something : Change sound too if needed
//        attack = 0;
//        defense = 0;
//
//        getImage(gp.ui.colorOutfit, false);
//        //GUARD
//        //getGuardImage();
//        setItems();
//        setDialogue();
//    }

    public void setDefaultPositions() {
        gp.currentMap = 0;
        worldX = gp.tileSize * 19; //19
        worldY = gp.tileSize * 17;
        direction = "up";
    }

    public void restoreStatus() {
        speed = defaultSpeed;
        stressLevel = 0;
        mana = maxMana;
        invincible = false;
        transparent= false;
        attacking = false;
        guarding = false;
        knockBack = false;
        lightUpdated = true;
    }

    public void setItems() {
        inventory.clear();
    }

    public int getAttack() {
        if (currentWeapon != null) {
            attackArea = currentWeapon.attackArea;
            motion1_duration = currentWeapon.motion1_duration;
            motion2_duration = currentWeapon.motion2_duration;
            return attack = strength * currentWeapon.attackValue;
        } else {
            return 0;
        }
    }

    public int getCurrentWeaponSlot() {
        int currentWeaponSlot = 0;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).isWeapon && Objects.equals(inventory.get(i).name, gp.player.currentWeapon.name)) {
                currentWeaponSlot = i;
            }
        }
        return currentWeaponSlot;
    }

    public int getCurrentArmourSlot() {
        int currentArmourSlot = 0;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).isArmour && Objects.equals(inventory.get(i).name, gp.player.currentArmour.name)) {
                currentArmourSlot = i;
            }
        }
        return currentArmourSlot;
    }

    public int getCurrentProjectileSlot() {
        int currentProjectileSlot = 0;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).isProjectile && Objects.equals(inventory.get(i).name, gp.player.currentProjectile.name)) {
                currentProjectileSlot = i;
            }
        }
        return currentProjectileSlot;
    }

    public int getDefense() {
        if (currentArmour != null) {
            return defense = dexterity * currentArmour.defenseValue;
        } else {
            return 0;
        }
    }

    public void setProjectile(Entity object) {
//        if (object != null) {
//            if (Objects.equals(object.name, "Pip's Bone")) {
//                projectile = new OBJ_PipsBone(gp);
//            } else if (Objects.equals(object.name, "Chopped Chicken")) {
//                projectile = new OBJ_ChoppedChicken(gp);
//            }
//        }
    }

    public void getImage(String colorOutfit, boolean loadGame) {
        //MENU
        down1 = setup("/player/mum_down1_brown", gp.tileSize, gp.tileSize);
        down1_red = setup("/player/mum_down1_red", gp.tileSize, gp.tileSize);
        down1_purple = setup("/player/mum_down1_purple", gp.tileSize, gp.tileSize);
        left1 = setup("/player/mum_left1_red", gp.tileSize, gp.tileSize);
        right2 = setup("/player/mum_right2_purple", gp.tileSize, gp.tileSize);
        dadDown1 = setup("/NPC/dad_down1", gp.tileSize, gp.tileSize);
        phoebeLeft1 = setup("/NPC/phoebe_left1", gp.tileSize, gp.tileSize);
        phoebeRight2 = setup("/NPC/phoebe_right2", gp.tileSize, gp.tileSize);

        if (gp.gameState == gp.playState || loadGame) {
            up1 = setup("/player/mum_up1_" + colorOutfit, gp.tileSize, gp.tileSize); //16 x 16 images
            up2 = setup("/player/mum_up2_" + colorOutfit, gp.tileSize, gp.tileSize);
            down1 = setup("/player/mum_down1_" + colorOutfit, gp.tileSize, gp.tileSize);
            down2 = setup("/player/mum_down2_" + colorOutfit, gp.tileSize, gp.tileSize);
            left1 = setup("/player/mum_left1_" + colorOutfit, gp.tileSize, gp.tileSize);
            left2 = setup("/player/mum_left2_" + colorOutfit, gp.tileSize, gp.tileSize);
            right1 = setup("/player/mum_right1_" + colorOutfit, gp.tileSize, gp.tileSize);
            right2 = setup("/player/mum_right2_" + colorOutfit, gp.tileSize, gp.tileSize);
        }
    }

    public void getAttackImage(String colorOutfit) {

        if (currentWeapon != null) {
            if (currentWeapon.type == type_short_weapon) {
                attackUp1 = setup("/player/mum_attack_up1_" + colorOutfit, gp.tileSize, gp.tileSize * 2); //16 x 32 images
                attackUp2 = setup("/player/mum_attack_up2_" + colorOutfit, gp.tileSize, gp.tileSize * 2);
                attackDown1 = setup("/player/mum_attack_down1_" + colorOutfit, gp.tileSize, gp.tileSize * 2);
                attackDown2 = setup("/player/mum_attack_down2_" + colorOutfit, gp.tileSize, gp.tileSize * 2);
                attackLeft1 = setup("/player/mum_attack_left1_" + colorOutfit, gp.tileSize * 2, gp.tileSize); //32 x 16 images
                attackLeft2 = setup("/player/mum_attack_left2_" + colorOutfit, gp.tileSize * 2, gp.tileSize);
                attackRight1 = setup("/player/mum_attack_right1_" + colorOutfit, gp.tileSize * 2, gp.tileSize);
                attackRight2 = setup("/player/mum_attack_right2_" + colorOutfit, gp.tileSize * 2, gp.tileSize);
            }
            if (currentWeapon.type == type_axe ||currentWeapon.type == type_long_weapon) {
                attackUp1 = setup("/player/mum_spatula_up1_" + colorOutfit, gp.tileSize, gp.tileSize * 2); //16 x 32 images
                attackUp2 = setup("/player/mum_spatula_up2_" + colorOutfit, gp.tileSize, gp.tileSize * 2);
                attackDown1 = setup("/player/mum_spatula_down1_" + colorOutfit, gp.tileSize, gp.tileSize * 2);
                attackDown2 = setup("/player/mum_spatula_down2_" + colorOutfit, gp.tileSize, gp.tileSize * 2);
                attackLeft1 = setup("/player/mum_spatula_left1_" + colorOutfit, gp.tileSize * 2, gp.tileSize); //32 x 16 images
                attackLeft2 = setup("/player/mum_spatula_left2_" + colorOutfit, gp.tileSize * 2, gp.tileSize);
                attackRight1 = setup("/player/mum_spatula_right1_" + colorOutfit, gp.tileSize * 2, gp.tileSize);
                attackRight2 = setup("/player/mum_spatula_right2_" + colorOutfit, gp.tileSize * 2, gp.tileSize);
            }
            if (currentWeapon.type == type_gardeningShovel) {
                attackUp1 = setup("/player/mum_shovel_up1_" + colorOutfit, gp.tileSize, gp.tileSize * 2); //16 x 32 images
                attackUp2 = setup("/player/mum_shovel_up2_" + colorOutfit, gp.tileSize, gp.tileSize * 2);
                attackDown1 = setup("/player/mum_shovel_down1_" + colorOutfit, gp.tileSize, gp.tileSize * 2);
                attackDown2 = setup("/player/mum_shovel_down2_" + colorOutfit, gp.tileSize, gp.tileSize * 2);
                attackLeft1 = setup("/player/mum_shovel_left1_" + colorOutfit, gp.tileSize * 2, gp.tileSize); //32 x 16 images
                attackLeft2 = setup("/player/mum_shovel_left2_" + colorOutfit, gp.tileSize * 2, gp.tileSize);
                attackRight1 = setup("/player/mum_shovel_right1_" + colorOutfit, gp.tileSize * 2, gp.tileSize);
                attackRight2 = setup("/player/mum_shovel_right2_" + colorOutfit, gp.tileSize * 2, gp.tileSize);
            }
            if (currentWeapon.type == type_tv_remote) {
                attackUp1 = setup("/player/mum_remote_up1_" + colorOutfit, gp.tileSize, gp.tileSize * 2); //16 x 32 images
                attackUp2 = setup("/player/mum_remote_up2_" + colorOutfit, gp.tileSize, gp.tileSize * 2);
                attackDown1 = setup("/player/mum_remote_up1_" + colorOutfit, gp.tileSize, gp.tileSize * 2);
                attackDown2 = setup("/player/mum_remote_up2_" + colorOutfit, gp.tileSize, gp.tileSize * 2);
                attackLeft1 = setup("/player/mum_remote_up1_" + colorOutfit, gp.tileSize * 2, gp.tileSize); //32 x 16 images
                attackLeft2 = setup("/player/mum_remote_up2_" + colorOutfit, gp.tileSize * 2, gp.tileSize);
                attackRight1 = setup("/player/mum_remote_up1_" + colorOutfit, gp.tileSize * 2, gp.tileSize);
                attackRight2 = setup("/player/mum_remote_up2_" + colorOutfit, gp.tileSize * 2, gp.tileSize);
            }
            if (currentWeapon.type == type_mop) {
                attackUp1 = setup("/player/mum_mop_up1_" + colorOutfit, gp.tileSize, gp.tileSize * 2); //16 x 32 images
                attackUp2 = setup("/player/mum_mop_up2_" + colorOutfit, gp.tileSize, gp.tileSize * 2);
                attackDown1 = setup("/player/mum_mop_down1_" + colorOutfit, gp.tileSize, gp.tileSize * 2);
                attackDown2 = setup("/player/mum_mop_down2_" + colorOutfit, gp.tileSize, gp.tileSize * 2);
                attackLeft1 = setup("/player/mum_mop_left1_" + colorOutfit, gp.tileSize * 2, gp.tileSize); //32 x 16 images
                attackLeft2 = setup("/player/mum_mop_left2_" + colorOutfit, gp.tileSize * 2, gp.tileSize);
                attackRight1 = setup("/player/mum_mop_right1_" + colorOutfit, gp.tileSize * 2, gp.tileSize);
                attackRight2 = setup("/player/mum_mop_right2_" + colorOutfit, gp.tileSize * 2, gp.tileSize);
            }
            if (currentWeapon.type == type_flaming_aerosol) {
                attackUp1 = setup("/player/mum_flamer_up1_" + colorOutfit, gp.tileSize, gp.tileSize * 2); //16 x 32 images
                attackUp2 = setup("/player/mum_flamer_up2_" + colorOutfit, gp.tileSize, gp.tileSize * 2);
                attackDown1 = setup("/player/mum_flamer_down1_" + colorOutfit, gp.tileSize, gp.tileSize * 2);
                attackDown2 = setup("/player/mum_flamer_down2_" + colorOutfit, gp.tileSize, gp.tileSize * 2);
                attackLeft1 = setup("/player/mum_flamer_left1_" + colorOutfit, gp.tileSize * 2, gp.tileSize); //32 x 16 images
                attackLeft2 = setup("/player/mum_flamer_left2_" + colorOutfit, gp.tileSize * 2, gp.tileSize);
                attackRight1 = setup("/player/mum_flamer_right1_" + colorOutfit, gp.tileSize * 2, gp.tileSize);
                attackRight2 = setup("/player/mum_flamer_right2_" + colorOutfit, gp.tileSize * 2, gp.tileSize);
            }
        }
    }

//    public void getGuardImage() {
//        guardUp = setup("/player/mum_guard_up", gp.tileSize, gp.tileSize);
//        guardDown = setup("/player/mum_guard_down", gp.tileSize, gp.tileSize);
//        guardLeft = setup("/player/mum_guard_left", gp.tileSize, gp.tileSize);
//        guardRight = setup("/player/mum_guard_right", gp.tileSize, gp.tileSize);
//    }

    public void update() {
        if (pillsInProcess) {
            pillsCounter++;
            if (pillsCounter > 1200) { //20 secs of effect
                pillsCounter = 0;
                pillsInProcess = false;
                dizzyFlag = false;
            }
        }

        if (lightPillsInProcess) {
            lightPillsCounter++;
            if (lightPillsCounter > 3600) { //60 secs of effect
                lightPillsCounter = 0;
                lightPillsInProcess = false;
                lightUpdated = true;
                currentLight = null;
                gp.eManager.lighting.update();

            }
        }

        if (knockBack) {

            collisionOn = false;
            gp.cChecker.checkTile(this);
            gp.cChecker.checkObject(this, true);
            gp.cChecker.checkEntity(this, gp.npc);
            gp.cChecker.checkEntity(this, gp.monster);
            gp.cChecker.checkEntity(this, gp.iTile);

            if(collisionOn) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            } else {
                switch (knockBackDirection) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            knockBackCounter++;
            if (knockBackCounter == 10) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
        } else if (attacking) {
            //attacking();
        } else if (keyH.guardAltPressed) {
            guarding = true;
            guardCounter++;
        }
        else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.spacePressed) {
            if (keyH.upPressed && !dizzyFlag) {
                direction = "up";
            } else if (keyH.downPressed && !dizzyFlag) {
                direction = "down";
            } else if (keyH.leftPressed && !dizzyFlag) {
                direction = "left";
            } else if (keyH.rightPressed && !dizzyFlag) {
                direction = "right";
            } else if (keyH.upPressed) {
                direction = "down";
            } else if (keyH.downPressed) {
                direction = "up";
            } else if (keyH.leftPressed) {
                direction = "right";
            } else if (keyH.rightPressed) {
                direction = "left";
            }

            if (speedBoost && !dizzyFlag) {
                speed = boostSpeed;
            } else if (dizzyFlag) {
                if (pillsCounter % 4 == 0) { //change speed every 2 seconds
                    speed = (int) (Math.random() * MAX_SPEED_UNDER_INFLUENCE) + 1;
                }
            } else {
                speed = defaultSpeed;
            }

            //CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            //CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            //CHECK MONSTER COLLISION
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            //CHECK INTERACTIVE TILE COLLISION
            gp.cChecker.checkEntity(this, gp.iTile);

            // CHECK EVENT
            gp.eHandler.checkEvent(this);

            //IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (!collisionOn && !keyH.spacePressed) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            if (keyH.spacePressed && !attackCanceled && currentWeapon != null) {
                if (currentWeapon.type == type_short_weapon) {
                    gp.playSFX(5);
                } else if (currentWeapon.type == type_long_weapon || currentWeapon.type == type_gardeningShovel) {
                    gp.playSFX(19);
                } else if (currentWeapon.type == type_axe) {
                    gp.playSFX(30);
                } else if (currentWeapon.type == type_mop) {
                    gp.playSFX(31);
                } else if (currentWeapon.type == type_flaming_aerosol) {
                    gp.playSFX(36);
                }

                attacking = true;
                spriteCounter = 0;
            }

            attackCanceled = false;
            gp.keyH.spacePressed = false;
            guarding = false;
            guardCounter = 0;

            spriteCounter++;
            if (spriteCounter > 12) { //walking speed of animation
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        else {
            standCounter++;
            if (standCounter == 30) {
                standCounter = 0;
                spriteNum = 1;
            }
            guarding = false;
            guardCounter = 0;
        }

        if (projectile != null) {
            if (gp.keyH.throwKeyPressed && !projectile.alive && shotAvailableCounter == 30) { // && projectile.haveResource(this)
                //SET DEFAULT COORDINATES, DIRECTION AND USER
                projectile.set(worldX, worldY, direction, true, this);

                //SUBTRACT THE COST FROM RESOURCES (TOYS ETC)
                projectile.subtractResource(this);
                if (Objects.equals(projectile.name, "Pip's Bone") && gp.player.chickenIndex > gp.player.boneIndex) {
                    gp.player.inventory.remove(boneIndex);
                    chickenIndex--;
                    itemToThrow = "Pip's Bone";
                } else if (Objects.equals(projectile.name, "Pip's Bone")) {
                    gp.player.inventory.remove(boneIndex);
                    itemToThrow = "Pip's Bone";
                }
                else if (Objects.equals(projectile.name, "Chopped Chicken")) {
                    thrownChickenCount++;
                    if (gp.player.inventory.get(chickenIndex).amount < 2 && gp.player.boneIndex > gp.player.chickenIndex) {
                        boneIndex--;
                        gp.player.inventory.remove(chickenIndex);
                    } else if (gp.player.inventory.get(chickenIndex).amount < 2) {
                        gp.player.inventory.remove(chickenIndex);
                    } else {
                        gp.player.inventory.get(chickenIndex).amount--;
                    }
                    if (thrownChickenCount == 1) {
                        itemToThrow = "Chopped Chicken Phoebe";
                    } else if (thrownChickenCount > 1) {
                        itemToThrow = "Chopped Chicken Pip";
                    }

                }
                gp.player.currentProjectile = null;


                //CHECK VACANCY
                for (int i= 0; i < gp.projectile[1].length; i++) {
                    if (gp.projectile[gp.currentMap][i] == null) {
                        gp.projectile[gp.currentMap][i] = projectile;
                        break;
                    }
                }
                projectile = null;

                shotAvailableCounter = 0;
            }
        }

        //This needs to be outside of main throw if statement!
        if (invincible) {
            invincibleCounter++;
            if(invincibleCounter > 120) {
                invincible = false;
                transparent = false;
                invincibleCounter = 0;
            }
        }
        if(shotAvailableCounter < 30) { //bug fix for close encounter projectile duplication
            shotAvailableCounter++;
        }
        if (stressLevel < 0) {
            stressLevel = 0;
        }

        if(currentWeapon != null && currentWeapon.type == type_tv_remote) {
            if (tvIsOff && attacking) {
                tvIsOff = false;
                for (int i = 0; i < gp.obj[1].length; i++) {
                    if (gp.obj[gp.currentMap][i] != null && Objects.equals(gp.obj[gp.currentMap][i].name, "TV_Lounge")) {
                        gp.obj[gp.currentMap][i].down1 = gp.obj[gp.currentMap][i].image2;
                    }
                }
            } else if (!tvIsOff && attacking) {
                tvIsOff = true;
                for (int i = 0; i < gp.obj[1].length; i++) {
                    if (gp.obj[gp.currentMap][i] != null && Objects.equals(gp.obj[gp.currentMap][i].name, "TV_Lounge")) {
                        gp.obj[gp.currentMap][i].down1 = gp.obj[gp.currentMap][i].image;
                    }
                }
            }
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {

            if (gp.obj[gp.currentMap][i].type == type_pickupOnly) {
                //PICKUP ONLY ITEMS
                gp.obj[gp.currentMap][i].use(this);
                gp.obj[gp.currentMap][i] = null;

            }  //OBSTACLE
            else if (gp.obj[gp.currentMap][i].type == type_closeable_door || gp.obj[gp.currentMap][i].type == type_music_device_dad || gp.obj[gp.currentMap][i].type == type_obstacle || gp.obj[gp.currentMap][i].type == type_hut) { //for doors and other obstacles that need to use objects to pass them
                if (keyH.enterPressed) {
                    attackCanceled = true;
                    gp.obj[gp.currentMap][i].interact();
                }
            } else {
                //INVENTORY ITEMS
                String text = "";
                int selectSfx;

                if (canObtainItem(gp.obj[gp.currentMap][i], this)) {
                    if (gp.obj[gp.currentMap][i].isWeapon) {
                        if (currentWeapon == null) {
                            currentWeapon = gp.obj[gp.currentMap][i];
                            attack = getAttack();
                            getAttackImage(gp.ui.outfitChosen);
                        }
                    } else if (gp.obj[gp.currentMap][i].isArmour) {
                        if (currentArmour == null) {
                            currentArmour = gp.obj[gp.currentMap][i];
                            defense = getDefense();
                        }
                    } else if (gp.obj[gp.currentMap][i].isProjectile) {
                        if (currentProjectile == null) {
                            currentProjectile = gp.obj[gp.currentMap][i];
                            setProjectile(currentProjectile);
                        }
                    } else if (gp.obj[gp.currentMap][i].isWeapon && gp.obj[gp.currentMap][i] == currentWeapon) {
                        currentWeapon = null;
                    } else if (gp.obj[gp.currentMap][i].isArmour && gp.obj[gp.currentMap][i] == currentArmour) {
                        currentArmour = null;
                    } else if (gp.obj[gp.currentMap][i].isProjectile && gp.obj[gp.currentMap][i] == currentProjectile) {
                        currentProjectile = null;
                    }

                    if (Objects.equals(gp.obj[gp.currentMap][i].name, "Pip's Bone")) {
                        for (int j = 0; j < gp.npc[gp.currentMap].length; j++) {
                            if (gp.npc[gp.currentMap][j] != null && Objects.equals(gp.npc[gp.currentMap][j].name, "Pip")) {
                                if (gp.npc[gp.currentMap][j].offMap) {
                                    gp.npc[gp.currentMap][j].offMap = false;
                                    gp.npc[gp.currentMap][j].callingNPCBack = true;
                                }
                            }
                        }
                        if (firstTimePickUpBone) {
                            gp.gameState = gp.dialogueState;
                            this.startDialogue(this, 0);
                            firstTimePickUpBone= false;
                        }
                        boneCount = 1;
                        gp.player.boneIndex = gp.player.inventory.size()-1;
                    }

                    if (Objects.equals(gp.obj[gp.currentMap][i].name, "Chopped Chicken")) {
                        for (int j = 0; j < gp.npc[gp.currentMap].length; j++) {
                            if (gp.npc[gp.currentMap][j] != null && (Objects.equals(gp.npc[gp.currentMap][j].name, "Phoebe") || Objects.equals(gp.npc[gp.currentMap][j].name, "Pip"))) {
                                if (gp.npc[gp.currentMap][j].offMap) {
                                    gp.npc[gp.currentMap][j].offMap = false;
                                    gp.npc[gp.currentMap][j].callingNPCBack = true;
                                }
                            }
                        }
                        choppedChickenCount++;
                        gp.player.chickenIndex = gp.player.inventory.size()-1;
                    }

                    if (Objects.equals(gp.obj[gp.currentMap][i].name, "Garden Shovel")) {
                        for (Entity entity : inventory) {
                            if (Objects.equals(entity.name, "Garden Shovel")) {
                                entity.down1 = entity.image2;
                            }
                        }
                    }

                    selectSfx = selectSfx(gp.obj[gp.currentMap][i].name);
                    gp.playSFX(selectSfx);
                    text = "Picked up " + gp.obj[gp.currentMap][i].displayName + "!";
                    gp.obj[gp.currentMap][i] = null;
                } else if (inventory.size() >= maxInventorySize) {
                    text = "You cannot carry any more!";
                }
                gp.ui.addMessage(text);
            }
        }
    }

    public void setDialogue() {
        dialogueText[0][0] = "I can throw the bone for Pip.\nI need to find an open area and press 'T'!";
        dialogueText[1][0] = "You have levelled up!\nNow you are level " + level + "!\n\nYou feel more able to cope with stress!";
        dialogueText[2][0] = "Wow I found hundred quid!\nNice reward for doing the weeding!";
        dialogueText[3][0] = "Yelp!";
        dialogueText[4][0] = "Stop that right now!";
        dialogueText[5][0] = "Hey what the bloody hell are\nya doin'??";
        dialogueText[6][0] = "I'll let that go...ONCE!!";
        dialogueText[7][0] = "What yer playin' at?!";
        dialogueText[8][0] = "Why you hittin' me wi that?!";
        dialogueText[9][0] = "You pass out from the stress!";
        dialogueText[10][0] = "Nice to have a rest, I feel less stressed\nstraight away!\n(Game Saved!)";
        dialogueText[11][0] = "Oww ya stupid sod, be careful will yer!";
        dialogueText[12][0] = "Aaagh a bloody big spider!";
        dialogueText[13][0] = "Aaagh another bloody spider!\nThat's " + uTool.parseNumberString(this.spiderCount)  + " in one day, sick of it!";
        dialogueText[14][0] = "So many bloody spiders today, Peter will you\nsort this bloody garden out?";
        dialogueText[15][0] = "I'm fine, I don't need to\nrelax at the moment!\n(Game Saved!)";
        dialogueText[16][0] = "These light pills will help my dodgy eye\nfor a bit, phew!";
        dialogueText[17][0] = "Bloody pills, I can't think straight!\nWhat am I doing up here??\nThe stress has gone at least!";
        dialogueText[18][0] = "Phew! He gets absolutely furious if water\ngets left on the floor!";
        dialogueText[18][1] = "If he would just finish the house, we wouldn't\nhave these problems!";
        dialogueText[19][0] = "Bloody scarpered with my money!\nIdiots that tip giving it him, for God's sake!";
        dialogueText[20][0] = "Just gotta use this remote now!";
        dialogueText[20][1] = "(You will find it in your inventory)";
        dialogueText[21][0] = "Turn that bloody telly off when you're not using it\nI'm not made o' money!";
        dialogueText[22][0] = "Right enough of that, time to get something done!";
        dialogueText[23][0] = "There it's burning!";
        dialogueText[23][1] = "Oooohh Jeeze...Bloody house is catching\nfire! Quick where's the bloody water??\nOh Goodddd!";
        dialogueText[24][0] = "It's a bucket of water! Fancy that!";
        dialogueText[25][0] = "It's an empty bucket! Fancy that!";
    }

    public void checkIfPassOutFromStress() {
        if (gp.player.stressLevel >= gp.player.maxStress) {
            gp.player.insideHouse = true;
            update();
            knockBack = false;
            timesPassedOut++;
            gp.gameState = gp.dialogueState;
            startDialogue(this, 9);
            if (timesPassedOut <= 2) { //change to be able to pass out this many times before passing out
                gp.playSFX(25);
                gp.player.worldX = gp.tileSize*19;
                gp.player.worldY = gp.tileSize*17;
                gp.player.stressLevel = 0;
                gp.gameState = gp.sleepState;
            } else {
                gp.gameState = gp.gameOverState;
                gp.stopMusic();
                gp.playSFX(24);
            }
        }
    }

    private int selectSfx(String object) {
        return switch (object) {
            case "FrontBackDoorKey" -> 1;
            case "Tube of Pills", "Anti Brightness Pills" -> 2;
            case "Bin_Blue", "Lavender Crocs" -> 14;
            case "Acoustic Guitar" -> 17;
            case "Electric Guitar" -> 16;
            case "InsideDoor", "InsideDoorSideways", "BackGate", "KitchenPantry" -> 4;
            case "FrontDoor" -> 3;
            case "Old Cardigan" -> 18;
            case "Spatula" -> 19;
            case "Hatchet" -> 30;
            default -> 10;
        };
    }

    public void interactNPC(int i) {

        if (i != 999) {
            gp.npc[gp.currentMap][i].move(direction);
        }

        if (gp.keyH.enterPressed) {
            if (i != 999) {
                gp.npc[gp.currentMap][i].speak();
                keyH.enterPressed = false;
            }
        }
        if (gp.keyH.spacePressed) {
            if (i != 999) {
                //attacking();
                keyH.spacePressed = false;
            }
        }
    }

    public void contactMonster(int i) {
        if (i != 999) {
            if (!invincible && !gp.monster[gp.currentMap][i].dying) {

                int damage = gp.monster[gp.currentMap][i].attack - defense;
                if (damage > 0) {
                    gp.ui.addMessage("The " + gp.monster[gp.currentMap][i].name + " got you! Your stress increases by " + damage + "!");
                    stressLevel+=1;
                    gp.player.pillsConsumableNow = gp.player.stressLevel >= STRESS_LEVEL_NEEDED_TO_CONSUME_PILLS;
                }
                if (damage <= 0) {
                    gp.ui.addMessage("The " + this.name + " got you but it can barely stress you at this level");
                    damage = 1;
                    gp.ui.addMessage("Stress increased by " + damage + "!");
                }
                if (Objects.equals(this.name, "Spider")) {
                    gp.playSFX(6);
                } else if (Objects.equals(this.name, "WaspSwarm")) {
                    gp.playSFX(26);
                }
                gp.player.transparent = true;
                setKnockBack(gp.player, this, knockBackPower);

                gp.player.stressLevel += damage;
                gp.player.pillsConsumableNow = gp.player.stressLevel >= gp.player.STRESS_LEVEL_NEEDED_TO_CONSUME_PILLS;
                invincible = true;
                transparent = true;
                checkIfPassOutFromStress();
            }
        }
    }

    public void hitNPC(int i) {
        if (i != 999) {
            gp.gameState = gp.dialogueState;
            if (Objects.equals(gp.npc[gp.currentMap][i].name, "Dad")) {
                randSet = gp.player.chooseRandomDialogueFromSet(gp.npc[gp.currentMap][i].name, "HitWithWeaponOrProjectile");
                startDialogue(this, randSet);
            } else if (Objects.equals(gp.npc[gp.currentMap][i].name, "Phoebe") || Objects.equals(gp.npc[gp.currentMap][i].name, "Pip")) {
                startDialogue(this, 3);
            }

            attacking = false;
        }
    }

    public void damageMonster(int i, Entity attacker, int attack, int knockBackPower) {
        if (i != 999) {
            if (!gp.monster[gp.currentMap][i].invincible) {
                if (Objects.equals(gp.monster[gp.currentMap][i].name, "Spider")) {
                    gp.playSFX(6);
                } else if (Objects.equals(gp.monster[gp.currentMap][i].name, "WaspSwarm")) {
                    gp.playSFX(26);
                }

                if(knockBackPower > 0) {
                    setKnockBack(gp.monster[gp.currentMap][i], attacker, knockBackPower);
                }

//                if (gp.monster[gp.currentMap][i].offBalance) {
//                    //todo if introduce monsters with weapons and parry ability
//                }

                int damage = attack - gp.monster[gp.currentMap][i].defense;
                if (damage < 0) {
                    damage = 0;
                }
                gp.monster[gp.currentMap][i].stressLevel += damage;
                gp.ui.addMessage(gp.monster[gp.currentMap][i].name + " receives " + damage + " damage!");
                damageJustReceived = true;
                gp.monster[gp.currentMap][i].invincible = true;
                gp.monster[gp.currentMap][i].damageReaction(); //makes monster run away if player hits it


                if (gp.monster[gp.currentMap][i].stressLevel >= gp.monster[gp.currentMap][i].monsterMaxStress) {
                    gp.monster[gp.currentMap][i].dying = true;
                    gp.playSFX(7);
                    gp.playSFX(13);
                    gp.ui.addMessage("You killed the " + gp.monster[gp.currentMap][i].name + ", phew!");
                    gp.ui.addMessage("Exp +" + gp.monster[gp.currentMap][i].exp);
                    exp+= gp.monster[gp.currentMap][i].exp;
                    checkLevelUp();
                }
            }
        }
    }
    public void switchInteractiveTile(int i) {
        if (i != 999) { //might need to add condition if player has walked off square to stop constant switching
            if (gp.iTile[gp.currentMap][i].type == gp.player.type_switchable_interactive_tile && gp.iTile[gp.currentMap][i].worldX == worldX && gp.iTile[gp.currentMap][i].worldY == worldY && gp.player.bookHutState == 1) {
                gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].switchForm();
            }
        }
    }

    public void damageInteractiveTile(int i) {

        if (i != 999 && gp.iTile[gp.currentMap][i].destructible && gp.iTile[gp.currentMap][i].isCorrectItem(this) && !gp.iTile[gp.currentMap][i].invincible) {
            gp.iTile[gp.currentMap][i].stressLevel++;
            if (Objects.equals(gp.iTile[gp.currentMap][i].name, "IT_Weed")) {
                if (gp.iTile[gp.currentMap][i].stressLevel >= gp.iTile[gp.currentMap][i].maxStress) {
                    if (gp.player.weedCount > 1) {
                        gp.player.weedCount--;
                        gp.iTile[1][i+gp.aSetter.mapNumTotal] = gp.iTile[1][i+gp.aSetter.mapNumTotal].switchForm(); //remove weed from upstairs view - **only works if interactive tiles in same location on all maps**
                    } else if (gp.player.weedCount == 1 && gp.player.missionSubstate == 1) { //end of weeding mission
                        gp.player.weedCount--;
                        gp.gameState = gp.dialogueState;
                        startDialogue(this, 2);
                        gp.aSetter.setObjectAfterStart("HundredQuid", gp.currentMap, gp.iTile[gp.currentMap][i].worldX/gp.tileSize, gp.iTile[gp.currentMap][i].worldY/gp.tileSize, false); //place supercoin where last weed dug up as reward
                        //gp.misStat.endMissionTasks(MissionStates.WEEDING_MISSION, true);
                    }
                }
                gp.iTile[gp.currentMap][i].playSfx();
                gp.iTile[gp.currentMap][i].invincible = true;

                //GENERATE PARTICLE
                generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);

                if (gp.iTile[gp.currentMap][i].stressLevel >= gp.iTile[gp.currentMap][i].maxStress && gp.iTile[gp.currentMap][i].type !=gp.player.type_switchable_interactive_tile) {
                    int rand = new Random().nextInt(100);
                    gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].switchForm();
                    if (rand > 95) { //DEBUG - change to increase likelihood of spider appearing after destroying a tile
                        int playerX = gp.player.worldX/gp.tileSize;
                        int playerY = gp.player.worldY/ gp.tileSize;
                        gp.player.spiderCount = gp.eHandler.spiderEvent(playerX+2, playerY+2, gp.dialogueState, gp.player.spiderCount, false, true);
                    }
                }
            }
        }
    }

    public void damageProjectile(int i) {
        if (i != 999) {
            Entity projectile = gp.projectile[gp.currentMap][i];
            projectile.alive = false;
            generateParticle(projectile, projectile);
        }
    }

//    public void damageObject(int i) {
//        int damage;
//        if (i != 999) {
//
////            if (Objects.equals(gp.obj[gp.currentMap][i].name, "BlockOfWood") && Objects.equals(gp.player.currentWeapon.name, "Hatchet") && gp.player.missionState == MissionStates.CHOP_CHICKEN_FOR_DOGS && gp.obj[gp.currentMap][i].down1 == gp.obj[gp.currentMap][i].image2) { //chop chicken mission
////                damage = 1;
////            }
////            else { //add further conditions when required
////                damage = 0;
////            }
//
//            if (!gp.obj[gp.currentMap][i].invincible) {
////                if (Objects.equals(gp.obj[gp.currentMap][i].name, "BlockOfWood") && gp.player.missionState == MissionStates.CHOP_CHICKEN_FOR_DOGS && gp.obj[gp.currentMap][i].down1 == gp.obj[gp.currentMap][i].image2) {
////                    gp.playSFX(31); //play chicken squelch sound
////                }
//                gp.obj[gp.currentMap][i].stressLevel += damage;
//                gp.obj[gp.currentMap][i].invincible = true;
//
//                if (gp.obj[gp.currentMap][i].stressLevel >= gp.obj[gp.currentMap][i].monsterMaxStress && Objects.equals(gp.obj[gp.currentMap][i].name, "BlockOfWood")) {
//                    gp.obj[gp.currentMap][i].down1 = gp.obj[gp.currentMap][i].image3;
//                    gp.player.blockWoodState = 3; //for upstairs correct image
//                    if (gp.player.missionState == MissionStates.CHOP_CHICKEN_FOR_DOGS) {
//                        gp.ui.addMessage("You chopped the chicken!");
//                        gp.aSetter.setObjectAfterStart("Chopped Chicken", gp.currentMap, 35, 11, false);
//                        gp.aSetter.setObjectAfterStart("Chopped Chicken", gp.currentMap, 35, 10, false);
//                    }
//                }
//                if (gp.obj[gp.currentMap][i].stressLevel >= gp.obj[gp.currentMap][i].monsterMaxStress && Objects.equals(gp.obj[gp.currentMap][i].name, "WaspNest")) {
//                    gp.obj[gp.currentMap][i].down1 = gp.obj[gp.currentMap][i].image2;
//                    gp.player.waspNestState = 1;
//                    if (gp.player.missionState == MissionStates.DESTROY_WASP_NEST) {
//                        gp.ui.addMessage("You set fire to the Wasp Nest!");
//                        gp.aSetter.monsterNumber = gp.aSetter.setMonster("WaspSwarm", gp.aSetter.monsterNumber, 17, 16, gp.currentMap, false);
//                        gp.aSetter.monsterNumber = gp.aSetter.setMonster("WaspSwarm", gp.aSetter.monsterNumber, 17, 17, gp.currentMap, false);
//                        gp.player.missionSubstate = 3;
//                        startDialogue(this, 23);
//                    }
//                }
//            }
//        }
//    }

    public void checkLevelUp() {
        if (exp >= nextLevelExp) {
            level++;
            nextLevelExp = nextLevelExp*2;
//            maxStress += 2; need to draw subdialogue bigger if use this
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();

            gp.playSFX(9);
            gp.gameState = gp.dialogueState;
            setDialogue();
            startDialogue(this, 1);
        }
    }

    public void selectItem() {
        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);

        if (itemIndex < inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);

            if (selectedItem.type == type_bucket) {
                if (gp.player.bucketFull) {
                    startDialogue(this, 24);
                } else {
                    startDialogue(this, 25);
                }
            }

            if ((selectedItem.type == type_flaming_aerosol || selectedItem.type == type_tv_remote || selectedItem.type == type_axe || selectedItem.type == type_short_weapon || selectedItem.type == type_long_weapon || selectedItem.type == type_gardeningShovel || selectedItem.type == type_mop) && selectedItem != currentWeapon) {
                currentWeapon = selectedItem;
                attack = getAttack();
                getAttackImage(gp.ui.outfitChosen);
                gp.playSFX(11);
            } else if (selectedItem.type == type_flaming_aerosol || selectedItem.type == type_tv_remote || selectedItem.type == type_axe || selectedItem.type == type_short_weapon || selectedItem.type == type_long_weapon || selectedItem.type == type_gardeningShovel || selectedItem.type == type_mop) {
                if (selectedItem.type == type_tv_remote) {
                   gp.player.worldY = gp.player.worldY - gp.tileSize;
                   if (!tvIsOff) {
                       startDialogue(this, 21);
                   } else {
                       startDialogue(this, 22);
                   }
                   for (int i = 0; i < gp.obj[1].length; i++) {
                       if (gp.obj[gp.currentMap][i] != null && Objects.equals(gp.obj[gp.currentMap][i].name, "TV_Lounge")) {
                           gp.obj[gp.currentMap][i].down1 = gp.obj[gp.currentMap][i].image;
                           tvIsOff = true;
                       }
                   }
                   inventory.remove(gp.player.tvRemoteIndex);
                }
                currentWeapon = null;
                attack = getAttack();
                gp.playSFX(11);
            } else if (selectedItem.isProjectile && selectedItem != currentProjectile && currentProjectile == null) {
                currentProjectile = selectedItem;
                if (Objects.equals(selectedItem.name, "Pip's Bone")) {
                    for (int j = 0; j < gp.npc[gp.currentMap].length; j++) {
                        if (gp.npc[gp.currentMap][j] != null && (Objects.equals(gp.npc[gp.currentMap][j].name, "Pip"))) {
                            if (gp.npc[gp.currentMap][j].offMap) {
                                gp.npc[gp.currentMap][j].offMap = false;
                                gp.npc[gp.currentMap][j].callingNPCBack = true;
                            }
                        }
                    }
                    //projectile = new OBJ_PipsBone(gp);
                } else if (Objects.equals(selectedItem.name, "Chopped Chicken")) {
                    for (int j = 0; j < gp.npc[gp.currentMap].length; j++) {
                        if (gp.npc[gp.currentMap][j] != null && (Objects.equals(gp.npc[gp.currentMap][j].name, "Phoebe") || Objects.equals(gp.npc[gp.currentMap][j].name, "Pip"))) {
                            if (gp.npc[gp.currentMap][j].offMap) {
                                gp.npc[gp.currentMap][j].offMap = false;
                                gp.npc[gp.currentMap][j].callingNPCBack = true;
                            }
                        }
                    }
                    //projectile = new OBJ_ChoppedChicken(gp);
                }
                gp.playSFX(11);
            } else if (selectedItem.isProjectile) {
                currentProjectile = null;
                projectile = null;
                gp.playSFX(11);
            }
            if (selectedItem.type == type_armour && selectedItem != currentArmour) {
                currentArmour = selectedItem;
                defense = getDefense();
                gp.playSFX(11);
            } else if (selectedItem.type == type_armour) {
                currentArmour = null;
                defense = getDefense();
                gp.playSFX(11);
            }
            if (selectedItem.type == type_light && !Objects.equals(selectedItem.name, "Anti Brightness Pills")) {
                if (currentLight == selectedItem) {
                    currentLight = null;
                } else {
                    currentLight = selectedItem;
                }
                gp.playSFX(11);
                lightUpdated = true;
            } else if (selectedItem.type == type_light && Objects.equals(selectedItem.name, "Anti Brightness Pills")) {
                gp.playSFX(2);
                currentLight = selectedItem;
                selectedItem.use(this);
                if (usedItemOrNot) {
                    if(selectedItem.amount > 1) {
                        selectedItem.amount--;
                    } else {
                        inventory.remove(itemIndex);
                    }
                    usedItemOrNot = false;
                }
            }
            if (selectedItem.type == type_consumable) {
                if(selectedItem.use(this)) {
                    if (boneCount > 0 && itemIndex < boneIndex) {
                        boneIndex--;
                    }
                    if (choppedChickenCount > 0 && itemIndex < chickenIndex) {
                        chickenIndex--;
                    }
                    if(selectedItem.amount > 1) {
                        selectedItem.amount--;
                    } else {
                        inventory.remove(itemIndex);
                    }
                }
            }
        }
    }

    public int searchItemInInventory(String itemName) {
        int itemIndex = 999;

        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).name.equals(itemName)) {
                itemIndex = i;
                break;
            }
        }
        return itemIndex;
    }

    public boolean canObtainItem(Entity item, Entity npc) {

        boolean canObtain = false;

        Entity newItem = gp.eGenerator.getObject(item.name);

        // CHECK IF ITEM IS STACKABLE
        if(!Objects.equals(item.name, "FrontBackDoorOpen")) {
            if (newItem.stackable) {
                int index = searchItemInInventory(newItem.name);

                if (index != 999) {
                    if (!Objects.equals(npc.name, "Andrea")) {
                        inventory.get(index).amount++;
                    }
                    canObtain = true;
                } else { // New item so need to check vacancy in inventory
                    if (inventory.size() != maxInventorySize) {
                        if (!Objects.equals(npc.name, "Andrea")) {
                            inventory.add(newItem);
                        }
                        canObtain = true;
                    }
                }
            } else { //Not stackable so check vacancy in inventory
                if (inventory.size() != maxInventorySize) {
                    if (!Objects.equals(npc.name, "Andrea")) {
                        inventory.add(newItem);
                    }
                    canObtain = true;
                }
            }
        }
        return canObtain;
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up" -> {
                if (!attacking) {
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                }
                if (attacking) {
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) {
                        image = attackUp1;
                    }
                    if (spriteNum == 2) {
                        image = attackUp2;
                    }
                }
                if (guarding) {
                    image = guardUp;
                }
            }
            case "down" -> {
                if (!attacking) {
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                }
                if (attacking) {
                    if (spriteNum == 1) {
                        image = attackDown1;
                    }
                    if (spriteNum == 2) {
                        image = attackDown2;
                    }
                }
                if (guarding) {
                    image = guardDown;
                }
            }
            case "left" -> {
                if (!attacking) {
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                }
                if (attacking) {
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) {
                        image = attackLeft1;
                    }
                    if (spriteNum == 2) {
                        image = attackLeft2;
                    }
                }
                if (guarding) {
                    image = guardLeft;
                }
            }
            case "right" -> {
                if (!attacking) {
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                }
                if (attacking) {
                    if (spriteNum == 1) {
                        image = attackRight1;
                    }
                    if (spriteNum == 2) {
                        image = attackRight2;
                    }
                }
                if (guarding) {
                    image = guardRight;
                }
            }
        }

        if (transparent) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }

        if (drawing) {
            g2.drawImage(image, tempScreenX, tempScreenY, null);
        }

        //RESET alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        //DEBUG
//      g2.setFont(new Font("Arial", Font.BOLD, 26));
//      g2.setColor(Color.BLACK);
//      g2.drawString("Invincible: " + invincibleCounter, 10, 400); //- UNCOMMENT TO DISPLAY INVINCIBILITY COUNTER
        //END DEBUG

    }
}
