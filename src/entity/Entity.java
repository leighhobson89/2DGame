package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Entity {

    final GamePanel gp;

    public BufferedImage dyingImage, up1, up2, up3, down1, down2, down3, left1, left2, left3, right1, right2, right3, down1_red, down1_purple, dadDown1, phoebeRight2, phoebeLeft1;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2, guardUp, guardDown, guardLeft, guardRight;
    public BufferedImage image, image2, image3, image4, image5;
    public BufferedImage up1Guitar, up2Guitar, down1Guitar, down2Guitar, left1Guitar, left2Guitar, right1Guitar, right2Guitar;
    public BufferedImage up1Standard, up2Standard, down1Standard, down2Standard, left1Standard, left2Standard, right1Standard, right2Standard;
    public Rectangle solidArea = new Rectangle(8, 16, 32, 32);
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collision = false;
    public final String[][] dialogueText = new String[100][20];
    public Entity attacker;
    public Entity linkedEntity;
    public final int[][] downstairsPositions = {{0,46,15},{0,58,17},{0,12,10},{0,15,19},{0,17,11},{0,18,13},{0,20,11},{0,23,15},{0,24,15},{0,28,11}}; //0 address is stairway for downstairs
    public final int[][] upstairsPositions = {{1,18,11},{1,19,16},{1,25,15},{1,27,16}}; //0 address is stairway for upstairs

    //STATE
    public int worldX, worldY;
    public final int phoneNormalWorldX = 816;
    public final int livingRoomEntranceTopX = 18;
    public final int livingRoomEntranceTopY = 12;
    public final int livingRoomEntranceSideX = 22;
    public final int livingRoomEntranceSideY = 16;
    public String direction = "right";
    public int spriteNum = 1;
    public int dialogueSet = 61;
    public int dialogueIndex = 0;
    public boolean collisionOn = false;
    public boolean invincible;
    public boolean newMonster;
    public boolean attacking;
    public boolean alive = true;
    public boolean dying;
    boolean hpBarOn = false;
    public int boneIndex;
    public int chickenIndex;
    public int thrownChickenCount = 0;
    public String itemToThrow = "";
    public boolean onPath = false;
    public boolean knockBack = false;
    public String knockBackDirection;
    public boolean guarding = false;
    public boolean transparent = false;
    public boolean offBalance = false;
    public Entity loot;
    public boolean opened = false;
//    public int missionState = MissionStates.BETWEEN_MISSIONS;
    public List<Integer> missionList = new ArrayList<>();
    public boolean readyForNextPhoneMission;
    public boolean nextMissionIsPhoneMission;
    public int missionToSet = 1;
    public int missionSubstate = 0;
    public final Random rand = new Random();
    public boolean repeatSfx = true;
    public int andreaTempGoalCol;
    public int andreaTempGoalRow;
    public boolean goesTransparentWhenHit;
    public boolean goesTransparentWhenStoodOnBookHut;
    public boolean goesTransparentWhenStoodOnToolHut;
    public boolean goesTransparentWhenInGarage;
    public boolean goesTransparentWhenInHouse;
    public boolean insideBookShed;
    public boolean insideToolShed;
    public boolean isUpdateable;
    public boolean pipChickenEaten;
    public boolean phoebeChickenEaten;
    public boolean startCounterPhoebeEatingChicken;
    public boolean startCounterPipEatingChicken;
    public boolean phoneRinging;
    public boolean showerAlreadyRan;
    public boolean showerCounterStart;
    public int waterTileCount;
    public int blockWoodState; //for upstairs correct image
    public int backGateState;
    public int bookHutState = 0;
    public int toolHutState = 0;
    public int collisionType;
    public boolean currentDialogueFinished;
    public boolean stainRemoverUsed;
    public boolean toolHutKeyDropped;
    public boolean tvIsOff = true;
    public boolean bucketFull;
    public int waspNestState;
    public boolean pillsInProcess;
    public boolean lightPillsInProcess;
    public boolean npcCanWalkOnWhenFollowing;
    public boolean inLivingRoom;
    public int dadOption;
    public boolean dadHasGuitar;
    public boolean dadPlayingGuitar;
    public boolean musicCentreOn;
    public boolean justEnteredLivingRoom;
    public boolean andreaSafe;
    public boolean withinView = true; //watch
    public boolean followingPlayer;
    public boolean offMap;
    public boolean transitionStairs;
    public boolean drawing = true;
    public int tempPlayerWorldX;
    public int tempPlayerWorldY;
    public String tempPlayerDirection;
    public boolean exitingFromCutScene;
    public boolean playerDummyToBeRemoved;
    public boolean tempXYDirectionSetYet;
    public boolean transitionDueToPills;
    public boolean tempWorldYSetYet;
    public int tempWorldY;
    public boolean playerDrawnThisCycle;
    public boolean drawAbovePlayer;
    public boolean insideGarage;
    public boolean insideHouse = true;
    public boolean enterExitHouseEventFlag;
    public boolean enterExitGarageEventFlag;

    //COUNTER
    public int spriteCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    int dyingCounter = 0;
    int hpBarCounter = 0;
    public int shotAvailableCounter = 0;
    int knockBackCounter = 0;
    public int guardCounter = 0;
    public int offBalanceCounter = 0;
    public int missionEndingCounter = 0;
    public int randomCounter; //used for random time in between missions before phone rings
    public int buzzCounter = 0;
    public int phoebeEatingChickenCounter;
    public int pipEatingChickenCounter;
    public int showerCounter = 0;
    public int quizScoreCount = 0;
    public int pillsCounter = 0;
    public int lightPillsCounter = 0;
    public int carCountDown;
    public int carCountUp;
    public int transitionCounter = 0;
    public int cutSceneCounter = 0;

    //CHARACTER ATTRIBUTES
    public String name;
    public String displayName;
    public int speed;
    public int defaultSpeed;
    public int boostSpeed;
    public boolean isWeapon;
    public boolean isArmour;
    public boolean isProjectile;
    public boolean pillsConsumableNow;
    public int maxStress;
    public int stressLevel;
    public int maxMana;
    public int mana;
    public int boneCount;
    public int choppedChickenCount;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public int motion1_duration;
    public int motion2_duration;
    public Entity currentWeapon;
    public Entity currentArmour;
    public Entity currentLight;
    public Entity currentProjectile;
    public Projectile projectile;
    public int weedCount;
    public boolean setShovelFlag;
    public int timesPassedOut;
    public boolean dizzyFlag;
    public boolean speedBoost;
    public boolean firstTimePickUpBone;
    public boolean savedWithAWeaponEquipped;
    public boolean savedWithAnArmourEquipped;
    public boolean savedWithAProjectileEquipped;
    public boolean haveBoneResource;
    public boolean haveChoppedChickenResource;
    public int tvRemoteIndex;
    public boolean hasOutsideDoorsKey;
    public int timeToBeOffMap;
    public boolean callingNPCBack;

    //ITEM ATTRIBUTES
    public final ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;
    public int value;
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public int useCost;
    public int price;
    public int knockBackPower = 0;
    public boolean stackable;
    public boolean isSaleable;
    public int amount = 1;
    public int lightRadius; //for different objects affecting lighting
    public boolean isScaledUpObject;

    //WORLD ATTRIBUTES
    public static final int MAX_WORLD_X_COORDINATE = 69;
    public static final int MAX_WORLD_Y_COORDINATE = 24;
    public static final int MIN_WORLD_X_COORDINATE = 0;
    public static final int MIN_WORLD_Y_COORDINATE = 0;

    //MONSTER ATTRIBUTES
    public int monsterMaxStress;
    public int spiderCount;
    public int chaseSpeed;
    public boolean damageJustReceived;

    //TYPE
    public int type;
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_short_weapon = 3;
    public final int type_long_weapon = 4;
    public final int type_armour = 5;
    public final int type_consumable = 6;
    public final int type_pickupOnly = 7;
    public final int type_gardeningShovel = 8;
    public final int type_obstacle = 9;
    public final int type_light = 10;
    public final int type_axe = 11;
    public final int type_mop = 12;
    public final int type_switchable_interactive_tile = 13;
    public final int type_tv_remote = 14;
    public final int type_hut = 15;
    public final int type_flaming_aerosol = 16;
    public final int type_bucket = 17;
    public final int type_music_device_dad = 18;
    public final int type_closeable_door = 19;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }
    public int getLeftX() {
        return worldX + solidArea.x;
    }
    public int getRightX() {
        return worldX + solidArea.x + solidArea.width;
    }
    public int getTopY() {
        return worldY + solidArea.y;
    }
    public int getBottomY() {
        return worldY + solidArea.y + solidArea.height;
    }
    public int getCol() {
        return (worldX + solidArea.x)/gp.tileSize;
    }
    public int getRow() {
        return (worldY + solidArea.y)/gp.tileSize;
    }

    public int getCenterX() {
        int centerX = 0;
        if (left1 != null) {
            centerX = worldX + left1.getWidth()/2;
        }
        return centerX;
    }
    public int getCenterY() {
        int centerY = 0;
        if (up1 != null) {
            centerY = worldY + up1.getHeight()/2;
        }
        return centerY;
    }
    public int getXdistance(Entity target) {
        return Math.abs(getCenterX() - target.getCenterX());
    }
    public int getYdistance(Entity target) {
        return Math.abs(getCenterY() - target.getCenterY());
    }
    public int getTileDistance(Entity target) {
        return (getXdistance(target) + getYdistance(target)) /gp.tileSize;
    }
    public int getGoalCol(Entity target) {
        return (target.worldX + target.solidArea.x)/gp.tileSize;
    }
    public int getGoalRow(Entity target) {
        return (target.worldY + target.solidArea.y)/gp.tileSize;
    }
    public void resetCounter() {
        spriteCounter = 0;
        actionLockCounter = 0;
        invincibleCounter = 0;
        dyingCounter = 0;
        hpBarCounter = 0;
        shotAvailableCounter = 0;
        knockBackCounter = 0;
        guardCounter = 0;
        offBalanceCounter = 0;
        missionEndingCounter = 0;
        randomCounter = setRandomCounter();
        pipEatingChickenCounter = 0;
        phoebeEatingChickenCounter = 0;
        showerCounter = 0;
        pillsCounter = 0;
        carCountDown = 0;
        carCountUp = 0;
        transitionCounter = 0;
        cutSceneCounter = 0;
    }
    //public void setLoot(Entity loot) {}
    public void move(String direction) {}
    public void setAction(int goalCol, int goalRow) {
        //overridden in specific entity class
    }

    public void getImage() {

    }

    public void getInitialImage() {

    }
    public void damageReaction() {
        //overridden in specific monster class
    }
    public void speak() {
        //overridden in specific entity class
    }

    public void handleTransparencyOfWallsAndObjectsInHouse(Entity object) {
        object.transparent = gp.player.insideHouse;
    }

    public void handleTransparencyOfWallsAndObjectsInGarage(Entity object) {
        if (gp.player.insideGarage) {
            object.transparent = true;
            for (int i = 0; i < gp.obj[gp.currentMap].length; i++) {
                if (gp.obj[gp.currentMap][i] != null && Objects.equals(gp.obj[gp.currentMap][i].name, "Garden Shovel")) {
                    gp.player.changeOtherObjectImage("Garden Shovel", 45, 8, 2);
                }
            }
        } else {
            object.transparent = false;
            for (int i = 0; i < gp.obj[gp.currentMap].length; i++) {
                if (gp.obj[gp.currentMap][i] != null && Objects.equals(gp.obj[gp.currentMap][i].name, "Garden Shovel")) {
                    gp.player.changeOtherObjectImage("Garden Shovel", 45, 8, 1);
                }
            }
        }
    }

    public void facePlayer() {
        switch (gp.player.direction) {
            case "up" -> direction = "down";
            case "down" -> direction = "up";
            case "left" -> direction = "right";
            case "right" -> direction = "left";
        }
    }
    public void startDialogue(Entity entity, int setNum) {
        gp.gameState = gp.dialogueState;
        gp.ui.npc = entity;
        dialogueSet = setNum;
    }

    //DEBUG - CAN CHANGE THIS TO SPEED UP TELEPHONE RINGING
    public int setRandomCounter() {
        //noinspection ConstantConditions
        if (this.gp.player != null) {
            return gp.player.rand.nextInt(300) + 400; //3800 + 1200 for normal game
        }
        return 0;
    }

    public int chooseRandomDialogueFromSet(String entityAffected, String action) {
        Random random = new Random();
        int bound;
        int randSet = 0;

        if (Objects.equals(action, "HitWithWeaponOrProjectile")) {
            if ("Dad".equals(entityAffected)) {
                bound = 4;
                randSet = random.nextInt(bound) + 4;
            }
        } else if (Objects.equals(action, "NormalChat")) {
            if ("Dad".equals(entityAffected)) {
                bound = 60; //increase if more random dialogues added
                randSet = random.nextInt(bound);
            }
        }
        return randSet;
    }

    public void interact() {
    }

    public boolean use(Entity entity) {
        return false;
    }
    public void checkDrop() {

    }

    public boolean checkIfObjectInWay(Entity entity) {
        boolean objectInWay = false;
        int objCol;
        int objRow;
        int entCol;
        int entRow;
        for (int i = 0; i < gp.obj[1].length; i++) {
            if (gp.obj[gp.currentMap][i] != null) {
                objCol = gp.obj[gp.currentMap][i].worldX / gp.tileSize;
                objRow = gp.obj[gp.currentMap][i].worldY / gp.tileSize;
                entCol = entity.worldX;
                entRow = entity.worldY;
                if (objCol == entCol && objRow == entRow) {
                    objectInWay = true;
                    break;
                }
            }
        }
        return objectInWay;
    }

    public void dropItem(Entity droppedItem, int tileX, int tileY) {
        int startX = tileX - 2;
        int startY = tileY - 2;
        boolean canDrop = true;
        int thisTile = gp.tileM.mapTileNum[gp.currentMap][tileX][tileY];

        if (!gp.tileM.tile[thisTile].collision && !checkIfObjectInWay(this)) {
            for (int i = 0; i < gp.obj[1].length; i++) {
                if(gp.obj[gp.currentMap][i] == null) {
                    gp.obj[gp.currentMap][i] = droppedItem;
                    if (Objects.equals(droppedItem.name, "StressBolt")) {
                        gp.obj[gp.currentMap][i].worldX = worldX + 20; //Get the dead monster's worldX
                        gp.obj[gp.currentMap][i].worldY = worldY + 20;
                    } else {
                        gp.obj[gp.currentMap][i].worldX = worldX;
                        gp.obj[gp.currentMap][i].worldY = worldY;
                    }
                    break;
                }
            }
        } else {
            this.worldX = startX;
            this.worldY = startY;
            while (gp.tileM.tile[thisTile].collision || checkIfObjectInWay(this) || (this.worldX < 11 || this.worldX > 60) || (this.worldY < 5 || this.worldY > 19)) {
                this.worldX++;
                if (this.worldX - startX > 4) {
                    this.worldY++;
                    this.worldX = startX;
                }
                if (this.worldY - startY > 4) {
                    canDrop = false;
                    break;
                }
                thisTile = gp.tileM.mapTileNum[gp.currentMap][this.worldX][this.worldY];
            }
            this.worldX = this.worldX * gp.tileSize;
            this.worldY = this.worldY * gp.tileSize;
            for (int i = 0; i < gp.obj[1].length; i++) {
                if (gp.obj[gp.currentMap][i] == null && canDrop) {
                    gp.obj[gp.currentMap][i] = droppedItem;
                    if (Objects.equals(droppedItem.name, "StressBolt")) {
                        gp.obj[gp.currentMap][i].worldX = worldX + 20; //Get the dead monster's worldX
                        gp.obj[gp.currentMap][i].worldY = worldY + 20;
                    } else {
                        gp.obj[gp.currentMap][i].worldX = worldX;
                        gp.obj[gp.currentMap][i].worldY = worldY;
                    }
                    break;
                }
                else if (!canDrop) {
                    if (Objects.equals(droppedItem.name, "ToolHutKey")) {
                        gp.player.toolHutKeyDropped = false;
                    }
                }
            }
        }
    }

    public Color getParticleColor() {
        return null;
    }
    public int getParticleSize() {
        return 0;
    }
    public int getParticleSpeed() {
        return 0;
    }
    public int getParticleMaxLife() {
        return 0;
    }

    public void generateParticle(Entity generator, Entity target) {
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2, -1);
        Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 2, -1);
        Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -2, 1);
        Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 2, 1);

        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);
    }

    public void checkCollision() {
        collisionOn = false;
        boolean tileState = gp.cChecker.checkTile(this);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.iTile);
        if (newMonster && tileState) {
            collisionOn = false;
        }
        if (newMonster && !tileState) {
            newMonster = false;
            collisionOn = true;
        }
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if(this.type == type_monster && contactPlayer) {
            damagePlayer(attack);
        }
    }

    public void setNullImages(Entity entity) {
        entity.up1 = null;
        entity.up2 = null;
        entity.down1 = null;
        entity.down2 = null;
        entity.left1 = null;
        entity.left2 = null;
        entity.right1 = null;
        entity.right2 = null;
    }

    public void update() {

        Entity npc;
        Entity monster;

        //player enters or exits garage
        for (int i = 0; i < gp.monster[gp.currentMap].length; i++) { //make monster invisible if player in garage and they aren't etc
            monster = gp.monster[gp.currentMap][i];
            if (monster != null && gp.player.enterExitGarageEventFlag) {
                if (gp.player.insideGarage) { //player in garage
                    if (!monster.insideGarage) { //monster not in garage
                        setNullImages(monster);
                    } else { //monster in garage
                        monster.getImage();
                    }
                } else { //player not in garage
                    if (monster.insideGarage || monster.insideHouse) {
                        setNullImages(monster);
                    } else {
                        monster.getImage();
                    }
                }
            }
        }

        for (int i = 0; i < gp.npc[gp.currentMap].length; i++) { //make npc invisible if player in garage and they aren't etc
            npc = gp.npc[gp.currentMap][i];
            if (npc != null && gp.player.enterExitGarageEventFlag) {
                if (gp.player.insideGarage) { //player in garage
                    if (!npc.insideGarage) { //npc not in garage
                        setNullImages(npc);
                    } else { //npc in garage
                        npc.getImage();
                    }
                } else { //player not in garage
                    if (npc.insideGarage || npc.insideHouse) {
                        setNullImages(npc);
                    } else {
                        npc.getImage();
                    }
                }
                if (Objects.equals(npc.name, "OldCooker") || Objects.equals(npc.name, "Andrea") || Objects.equals(npc.name, "Merchant")) {
                    npc.getImage();
                }
            }
        }
        gp.player.enterExitGarageEventFlag = false;

        //player enters or exits house
        for (int i = 0; i < gp.monster[gp.currentMap].length; i++) { //make monster invisible if player in house and they aren't etc
            monster = gp.monster[gp.currentMap][i];
            if (monster != null && gp.player.enterExitHouseEventFlag) {
                if (gp.player.insideHouse) { //player in house
                    if (!monster.insideHouse) { //monster not in house
                        setNullImages(monster);
                    } else { //monster in house
                        monster.getImage();
                    }
                } else { //player not in house
                    if (monster.insideHouse || monster.insideGarage) {
                        setNullImages(monster);
                    } else {
                        monster.getImage();
                    }
                }
            }
        }

        for (int i = 0; i < gp.npc[gp.currentMap].length; i++) { //make npc invisible if player in house and they aren't etc
            npc = gp.npc[gp.currentMap][i];
            if (npc != null && gp.player.enterExitHouseEventFlag) {
                if (gp.player.insideHouse) { //player in house
                    if (!npc.insideHouse) { //npc not in house
                        setNullImages(npc);
                    } else { //npc in house
                        npc.getImage();
                    }
                } else { //player not in house
                    if (npc.insideHouse || npc.insideGarage) {
                        setNullImages(npc);
                    } else {
                        npc.getImage();
                    }
                }
                if (Objects.equals(npc.name, "OldCooker") || Objects.equals(npc.name, "Andrea") || Objects.equals(npc.name, "Merchant")) {
                    npc.getImage();
                }
            }
        }
        gp.player.enterExitHouseEventFlag = false;

        //npc enters or exits garage
        for (int i = 0; i < gp.npc[gp.currentMap].length; i++) { //make monster invisible if player in garage and they aren't etc
            npc = gp.npc[gp.currentMap][i];
            if (npc != null && npc.enterExitGarageEventFlag) {
                if (gp.player.insideGarage) { //player in garage
                    if (!npc.insideGarage) { //monster not in garage
                        setNullImages(npc);
                    } else { //monster in garage
                        npc.getImage();
                    }
                } else { //player not in garage
                    if (npc.insideGarage) {
                        setNullImages(npc);
                    } else {
                        npc.getImage();
                    }
                }
                npc.enterExitGarageEventFlag = false;
            }
        }

        //npc enters or exits house
        for (int i = 0; i < gp.npc[gp.currentMap].length; i++) { //make monster invisible if player in house and they aren't etc
            npc = gp.npc[gp.currentMap][i];
            if (npc != null && npc.enterExitHouseEventFlag) {
                if (gp.player.insideHouse) { //player in house
                    if (!npc.insideHouse) { //monster not in house
                        setNullImages(npc);
                    } else { //monster in house
                        npc.getImage();
                    }
                } else { //player not in house
                    if (npc.insideHouse) {
                        setNullImages(npc);
                    } else {
                        npc.getImage();
                    }
                }
                npc.enterExitHouseEventFlag = false;
            }
        }

        //monster enters or exits garage
        for (int i = 0; i < gp.monster[gp.currentMap].length; i++) { //make monster invisible if player in garage and they aren't etc
            monster = gp.monster[gp.currentMap][i];
            if (monster != null && monster.enterExitGarageEventFlag) {
                if (gp.player.insideGarage) { //player in garage
                    if (!monster.insideGarage) { //monster not in garage
                        setNullImages(monster);
                    } else { //monster in garage
                        monster.getImage();
                    }
                } else { //player not in garage
                    if (monster.insideGarage) {
                        setNullImages(monster);
                    } else {
                        monster.getImage();
                    }
                }
                monster.enterExitGarageEventFlag = false;
            }
        }

        //monster enters or exits house
        for (int i = 0; i < gp.monster[gp.currentMap].length; i++) { //make monster invisible if player in house and they aren't etc
            monster = gp.monster[gp.currentMap][i];
            if (monster != null && monster.enterExitHouseEventFlag) {
                if (gp.player.insideHouse) { //player in house
                    if (!monster.insideHouse) { //monster not in house
                        setNullImages(monster);
                    } else { //monster in house
                        monster.getImage();
                    }
                } else { //player not in house
                    if (monster.insideHouse) {
                        setNullImages(monster);
                    } else {
                        monster.getImage();
                    }
                }
                monster.enterExitHouseEventFlag = false;
            }
        }

        for (int i = 0; i < gp.npc[gp.currentMap].length; i++) { //sets npcs when player not on map
            if (gp.npc[gp.currentMap][i] != null) {
                gp.npc[gp.currentMap][i].withinView = false;
                gp.npc[gp.currentMap][i].followingPlayer = false;
            }
        }
        //

        int iTileIndex;
        if (gp.player.bookHutState == 1) {
            iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
            gp.player.switchInteractiveTile(iTileIndex);
        }

        if (gp.player.startCounterPhoebeEatingChicken) {
            gp.player.phoebeEatingChickenCounter++;
            if (gp.player.phoebeEatingChickenCounter >= 2000) {
                for (int i = 0; i < gp.obj[1].length; i++) {
                    if (gp.obj[gp.currentMap][i] != null) {
                        if (Objects.equals(gp.obj[gp.currentMap][i].name, "Chopped Chicken Phoebe")) {
                            gp.obj[gp.currentMap][i] = null;
                            gp.player.phoebeChickenEaten = true;
                            gp.player.missionSubstate++;
                        }
                    }
                }
            }
        }
        if (gp.player.startCounterPipEatingChicken) {
            gp.player.pipEatingChickenCounter++;
            if (gp.player.pipEatingChickenCounter >= 2000) {
                for (int i = 0; i < gp.obj[1].length; i++) {
                    if (gp.obj[gp.currentMap][i] != null) {
                        if (Objects.equals(gp.obj[gp.currentMap][i].name, "Chopped Chicken Pip")) {
                            gp.obj[gp.currentMap][i] = null;
                            gp.player.pipChickenEaten = true;
                            gp.player.missionSubstate++;
                        }
                    }
                }
            }
        }
        if (!isUpdateable) {
            if (knockBack) {
                checkCollision();

                if (collisionOn) {
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
            } else {
                setAction(andreaTempGoalCol, andreaTempGoalRow);
                checkCollision();

                //IF COLLISION IS FALSE, ENTITY CAN MOVE
                if (!collisionOn && !Objects.equals(this.name, "OldCooker")) {
                    switch (direction) {
                        case "up" -> worldY -= speed;
                        case "down" -> worldY += speed;
                        case "left" -> worldX -= speed;
                        case "right" -> worldX += speed;
                    }
                }
                spriteCounter++;
                if (spriteCounter > 24) { //walking speed of animation
                    if (spriteNum == 1) {
                        spriteNum = 2;
                    } else if (spriteNum == 2) {
                        spriteNum = 1;
                    }
                    spriteCounter = 0;
                }
            }

            if (shotAvailableCounter < 30) { //bug fix for close encounter projectile duplication
                shotAvailableCounter++;
            }

            if (offBalance) {
                offBalanceCounter++;
                if (offBalanceCounter > 60) {
                    offBalance = false;
                    offBalanceCounter = 0;
                }
            }
        }
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

//        public void checkAttackOrNot(int rate, int straight, int horizontal) { //for monsters that attack with weapon
//            boolean targetInRange = false;
//            int xDis = getXdistance(gp.player);
//            int yDis = getYdistance(gp.player);
//
//            switch (direction) {
//                case "up":
//                    if (gp.player.getCenterY() < getCenterY() && yDis < straight && xDis < horizontal) {
//                        targetInRange = true;
//                    }
//                    break;
//                case "down":
//                    if (gp.player.getCenterY() > getCenterY() && yDis < straight && xDis < horizontal) {
//                        targetInRange = true;
//                    }
//                    break;
//                case "left":
//                    if (gp.player.getCenterX() < getCenterX() && xDis < straight && yDis < horizontal) {
//                        targetInRange = true;
//                    }
//                    break;
//                case "right":
//                    if (gp.player.getCenterX() > getCenterX() && xDis < straight && yDis < horizontal) {
//                        targetInRange = true;
//                    }
//                    break;
//            }
//
//            if (targetInRange) {
//                // Check if it initiates an attack
//                int i = new Random().nextInt(rate);
//                if (i == 0) {
//                    attacking = true;
//                    spriteNum = 1;
//                    spriteCounter = 0;
//                    shotAvailableCounter = 0;
//                }
//            }
//        }

//    public void checkShootOrNot(int rate, int shotInterval) {
//                    int i = new Random().nextInt(rate) + 1;
//            if (i == 0 && !projectile.alive && shotAvailableCounter == shotInterval) {
//                projectile.set(worldX, worldY, direction, true, this);
//
//                //CHECK VACANCY
//                for (int j = 0; j < gp.projectile[1].length; j++) {
//                    if (gp.projectile[gp.currentMap][j] == null) {
//                        gp.projectile[gp.currentMap][j] = projectile;
//                        break;
//                    }
//                }
//                shotAvailableCounter = 0;
//            }
//    }

    public boolean checkStartsChasingOrNot(Entity target, int distance, int rate, boolean damagedJustReceived) {
        if (getTileDistance(target) < distance) {
            int i = new Random().nextInt(rate);
            if (i == 0 && damagedJustReceived) {
                speed = chaseSpeed;
                onPath = true;
            }
            damagedJustReceived = false;
        }
        return damagedJustReceived;
    }

    public void checkStopsChasingOrNot(Entity target, int distance, int rate) {
        if (getTileDistance(target) > distance) {
            int i = new Random().nextInt(rate);
            if (i == 0) {
                speed = defaultSpeed;
                onPath = false;
            }
        }
    }

    public void getRandomDirection() {
        actionLockCounter++;

        if (actionLockCounter == 60) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; //pick up a number from 1 to 100

            if (i <= 25) {direction = "up";}
            if (i > 25 && i <= 50) {direction = "down";}
            if (i > 50 && i <= 75) {direction = "left";}
            if (i > 75) {direction = "right";}
            actionLockCounter = 0;
        }
    }

    public String getOppositeDirection(String direction) {

        return switch (direction) {
            case "up" -> "down";
            case "down" -> "up";
            case "left" -> "right";
            case "right" -> "left";
            default -> "";
        };
    }

//    public void attacking() {
//        spriteCounter++; //spriteCounter is a timer for how long to show each sprite within the current frame of animation
//
//        if (spriteCounter <= motion1_duration) {
//            spriteNum = 1;
//        }
//        if (spriteCounter > motion1_duration && spriteCounter <= motion2_duration) {
//            spriteNum = 2;
//
//            //Save the current worldX, worldY, solidArea
//            int currentWorldX = worldX;
//            int currentWorldY = worldY;
//            int solidAreaWidth = solidArea.width;
//            int solidAreaHeight = solidArea.height;
//
//            //Adjust the player's worldX/Y for the attackArea
//            switch (direction) {
//                case "up" -> worldY -= attackArea.height;
//                case "down" -> worldY += attackArea.height;
//                case "left" -> worldX -= attackArea.width;
//                case "right" -> worldX += attackArea.width;
//            }
//
//            // attackArea becomes solidArea
//            solidArea.width = attackArea.width;
//            solidArea.height = attackArea.height;
//
//            if (type == type_monster) {
//                if (gp.cChecker.checkPlayer(this)) {
//                    damagePlayer(attack);
//                }
//            } else { //Player
//                // Check monster collision with the updated worldX, worldY and solidArea
//                int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
//                int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
//                int objIndex = gp.cChecker.checkEntity(this, gp.obj);
//                gp.player.damageMonster(monsterIndex, this, attack, currentWeapon.knockBackPower);
//                gp.player.hitNPC(npcIndex);
//                gp.player.damageObject(objIndex);
//
//                //INTERACTIVE TILE COLLISION CHECKER
//                int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
//                gp.player.damageInteractiveTile(iTileIndex);
//
//                int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
//                gp.player.damageProjectile(projectileIndex);
//            }
//
//            // After checking collision, restore the original data
//            worldX = currentWorldX;
//            worldY = currentWorldY;
//            solidArea.width = solidAreaWidth;
//            solidArea.height = solidAreaHeight;
//
//        }
//        if (spriteCounter > motion2_duration) {
//            spriteNum = 1;
//            spriteCounter = 0;
//            attacking = false;
//        }
//    }

    public void damagePlayer(int attack) {
        if (Objects.equals(this.name, "Pip") || Objects.equals(this.name, "Phoebe")) {
            collisionOn = false;
        } else if (!gp.player.invincible && !this.dying) {

            int damage = attack - gp.player.defense;

            //Setup guard direction
            String canGuardDirection = getOppositeDirection(direction);

            if (gp.player.guarding && gp.player.direction.equals(canGuardDirection)) { //if successful guard block
                //PARRY
                if (gp.player.guardCounter < 10) { //this number can be up to 60 - easier to parry the higher the number
                    damage = 0;
//                    gp.playSFX(PARRY SFX); //add sound fx for parry
                    setKnockBack(this, gp.player, knockBackPower);
                    offBalance = true;
                    spriteCounter -= 60;
                } else {
                    //NORMAL GUARD
                    damage /= 3; //damage reduced to a third
//                  gp.playSFX(GUARDING/BLOCKING SFX); //add sound fx for guard/block
                }
            } else { //not guarding
                if (damage > 0) {
                    gp.ui.addMessage("The " + this.name + " got you! Your stress increases by " + damage + "!");
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
            }
            if (damage != 0) {
                gp.player.transparent = true;
                setKnockBack(gp.player, this, knockBackPower);
            }

            gp.player.stressLevel += damage;
            gp.player.pillsConsumableNow = gp.player.stressLevel >= gp.player.STRESS_LEVEL_NEEDED_TO_CONSUME_PILLS;
            gp.player.invincible = true;

            gp.player.checkIfPassOutFromStress(); //gameover method - can pass out from stress twice, next time game over
        }
    }

    public void setKnockBack(Entity target, Entity attacker, int knockbackPower) {
        this.attacker = attacker;
        target.knockBackDirection = attacker.direction;
        target.speed += knockbackPower;
        target.knockBack = true;
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (isScaledUpObject || (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
                && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY)) {

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
                        tempScreenY = screenY - up1.getHeight();
                        if (spriteNum == 1) {
                            image = attackUp1;
                        }
                        if (spriteNum == 2) {
                            image = attackUp2;
                        }
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
                        tempScreenX = screenX - left1.getWidth();
                        if (spriteNum == 1) {
                            image = attackLeft1;
                        }
                        if (spriteNum == 2) {
                            image = attackLeft2;
                        }
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
                }
            }

            //Monster HP bar
            if(type == type_monster && hpBarOn) {

                double oneScale = (double)gp.tileSize/monsterMaxStress;
                double hpBarValue = gp.tileSize - (oneScale * stressLevel);

                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX-2, screenY-17, gp.tileSize+4, 14);
                g2.setColor(new Color(255,0,30));
                g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);

                hpBarCounter++;

                if (hpBarCounter < 300) {
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }

            if (invincible) {
                hpBarOn = true;
                hpBarCounter = 0;
                if (this.goesTransparentWhenHit) {
                    changeAlpha(g2, 0.4F);
                }
            }
            if (dying) {
                image = dyingImage;
                dyingAnimation(g2);
            }
            if ((this.goesTransparentWhenStoodOnBookHut || this.goesTransparentWhenStoodOnToolHut) && this.transparent) {
                changeAlpha(g2, 0.6F);
            }

            if (this.goesTransparentWhenInGarage && this.transparent) {
                changeAlpha(g2, 0.3F);
            }

            if (this.goesTransparentWhenInHouse && this.transparent) {
                changeAlpha(g2, 0.1F);
            }

            if (this.drawing) {
                g2.drawImage(image, tempScreenX, tempScreenY, null);
            }

            changeAlpha(g2, 1F);
        }
    }

    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;

        int i = 10; //speed of flashing

        if (dyingCounter <= i) {changeAlpha(g2, 0f);}
        if (dyingCounter > i && dyingCounter <= i*2) {changeAlpha(g2, 1f);}
        if (dyingCounter > i*2 && dyingCounter <= i*3) {changeAlpha(g2, 0f);}
        if (dyingCounter > i*3 && dyingCounter <= i*4) {changeAlpha(g2, 1f);}
        if (dyingCounter > i*4 && dyingCounter <= i*5) {changeAlpha(g2, 0f);}
        if (dyingCounter > i*5 && dyingCounter <= i*6) {changeAlpha(g2, 1f);}
        if (dyingCounter > i*6 && dyingCounter <= i*7) {changeAlpha(g2, 0f);}
        if (dyingCounter > i*7 && dyingCounter <= i*8) {changeAlpha(g2, 1f);}
        if(dyingCounter > i*8) {
            dying = false;
            alive = false;
        }
    }
    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            image = uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void searchPath(int goalCol, int goalRow) {

        int startCol = (worldX + solidArea.x)/gp.tileSize;
        int startRow = (worldY + solidArea.y)/gp.tileSize;

        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);

        if (gp.pFinder.search()) {
            //Next worldX & worldY
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

            // Entity's solidArea position
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "up";
            } else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "down";
            } else if (enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
                //left or right
                if (enLeftX > nextX) {
                    direction = "left";
                }
                if (enLeftX < nextX) {
                    direction = "right";
                }
            }
            else if (enTopY > nextY && enLeftX > nextX) {
                // up or left
                direction = "up";
                checkCollision();
                if (collisionOn) {
                    direction = "left";
                }
            }
            else if (enTopY > nextY && enLeftX < nextX) {
                // up or right
                direction = "up";
                checkCollision();
                if (collisionOn) {
                    direction = "right";
                }
            }
            else if (enTopY < nextY && enLeftX > nextX) {
                // down or left
                direction = "down";
                checkCollision();
                if (collisionOn) {
                    direction = "left";
                }
            }
            else if (enTopY < nextY && enLeftX < nextX) {
                // down or left
                direction = "down";
                checkCollision();
                if (collisionOn) {
                    direction = "right";
                }
            }

            //If reaches the goal, stop the search
            int nextCol = gp.pFinder.pathList.get(0).col;
            int nextRow = gp.pFinder.pathList.get(0).row;

            if (nextCol == goalCol && nextRow == goalRow && !Objects.equals(this.name, "Dad")) {
                onPath = false;
            }
        }
    }

    public int getDetected(Entity user, Entity[][] target, String targetName) { //detect object next to user ie opening doors
        int index = 999;

        //Check the surrounding object
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();

        switch (user.direction) {
            case "up" -> nextWorldY = user.getTopY() - gp.player.speed;
            case "down" -> nextWorldY = user.getBottomY() + gp.player.speed;
            case "left" -> nextWorldX = user.getLeftX() - gp.player.speed;
            case "right" -> nextWorldX = user.getRightX() + gp.player.speed;
        }

        int col = nextWorldX/gp.tileSize;
        int row = nextWorldY/gp.tileSize;

        for (int i = 0; i < target[1].length; i++) {
            if (target[gp.currentMap][i] != null) {
                if (target[gp.currentMap][i].getCol() == col &&
                target[gp.currentMap][i].getRow() == row &&
                target[gp.currentMap][i].name.equals(targetName)) {
                index = i;
                break;
                }
            }
        }
        return index;
    }

    public void turnEntityAround(Entity entity) {
        //IF ENTITY REACHES EDGE OF WORLD, TURN THEM AROUND BEFORE ARRAY OUT OF BOUNDS ERROR
        entity.direction = entity.getOppositeDirection(entity.direction);
    }

    public boolean checkEdgeOfMap(Entity entity) {
        int worldX = entity.worldX/gp.tileSize;
        int worldY = entity.worldY/gp.tileSize;

        return (worldX == MAX_WORLD_X_COORDINATE && entity.direction.equals("right"))
                || (worldY == MAX_WORLD_Y_COORDINATE && entity.direction.equals("down"))
                || (worldX == MIN_WORLD_X_COORDINATE && entity.direction.equals("left"))
                || (worldY == MIN_WORLD_Y_COORDINATE && entity.direction.equals("up"));
    }

    public void setNewMissionState(boolean readyForNextPhoneMission, int missionState, int missionToSet) {
//        if (readyForNextPhoneMission && missionState == MissionStates.BETWEEN_MISSIONS) {
//            switch (missionToSet) {
////                case 1 -> gp.player.missionState = MissionStates.WEEDING_MISSION;
////                case 2 -> gp.player.missionState = MissionStates.SELL_DADS_ELECTRIC_GUITAR_TO_THE_MERCHANT;
//            }
//            gp.player.readyForNextPhoneMission = false;
//        }
    }

    public void AndreaLeaveSetup(Entity npc) {
        npc.speed = 1;
        npc.solidArea = new Rectangle(8, 16,32,32);
        if (Objects.equals(npc.name, "Andrea")) {
            npc.onPath = true;
        }
        npc.setAction(gp.player.andreaTempGoalCol, gp.player.andreaTempGoalRow);
    }

    public int checkIfObjectOnMap(String object) {
        int count = 0;
        for (int i = 0; i < gp.obj[1].length; i++) {
            if (gp.obj[gp.currentMap][i] != null) {
                if (Objects.equals(gp.obj[gp.currentMap][i].name, object)) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean checkIfPlayerHasMissionItem(ArrayList<Entity> inventory, int missionState, int missionSubState) {
        boolean playerHasRequiredItem = false;
        for (Entity entity : inventory) {
//            switch (missionState) {
//                case MissionStates.CHOP_CHICKEN_FOR_DOGS:
//                    if (Objects.equals(entity.name, "Chicken")) {
//                        playerHasRequiredItem = true;
//                    }
//                    break;
//            }
        }
        return playerHasRequiredItem;
    }

    public boolean checkIfPlayerHasItem(ArrayList<Entity> inventory, String itemToCheck) {
        boolean playerHasRequiredItem = false;
        for (Entity entity : inventory) {
            if (Objects.equals(entity.name, itemToCheck)) {
                playerHasRequiredItem = true;
                break;
            }
        }
        return playerHasRequiredItem;
    }

    public void changeOtherObjectImage(String name, int worldX, int worldY, int image) {
        int objectWorldX;
        int objectWorldY;
        for (int i = 0; i < gp.obj[1].length; i++) {
            if (gp.obj[gp.currentMap][i] != null && Objects.equals(gp.obj[gp.currentMap][i].name, name)) {
                objectWorldX = gp.obj[gp.currentMap][i].worldX/gp.tileSize;
                objectWorldY = gp.obj[gp.currentMap][i].worldY/gp.tileSize;
                if (objectWorldX == worldX && objectWorldY == worldY) {
                    switch (image) {
                        case 1 -> gp.obj[gp.currentMap][i].down1 = gp.obj[gp.currentMap][i].image;
                        case 2 -> gp.obj[gp.currentMap][i].down1 = gp.obj[gp.currentMap][i].image2;
                        case 3 -> gp.obj[gp.currentMap][i].down1 = gp.obj[gp.currentMap][i].image3;
                    }
                }
            }
        }
    }
}
