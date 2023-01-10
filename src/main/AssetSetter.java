package main;

import entity.*;
import object.OBJ_Cupboard2;

import java.util.Objects;
import java.util.Random;

public class AssetSetter {
    final GamePanel gp;

    //INDICES FOR INSTANCES OF MONSTERS (can add NPC and OBJ equivalents later if required)
    public int monsterNumber = 0;
    public int mapNumTotal;
    public int boneX;
    public int boneY;
    public int choppedChickenX;
    public int choppedChickenY;
    public int choppedChickenPhoebeX;
    public int choppedChickenPhoebeY;
    public int choppedChickenPipX;
    public int choppedChickenPipY;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObjectAfterStart(String name, int mapNum, int x, int y, boolean isItARandomMonsterDrop) { //finds first available slot in object array if setting object after start of game
        int startX = x - 2;
        int startY = y - 2;
        int thisTile = gp.tileM.mapTileNum[mapNum][x][y];
        Entity testEntity = new OBJ_Cupboard2(gp);

        int count = 0;
        for (int i = 0; i < gp.obj[gp.currentMap].length; i++) {
            if (gp.obj[mapNum][i] != null) {
                count++; //at end of loop, count will show the index of the next available slot in the array
            } else {
                break;
            }
        }

        int i = count;
        if (!gp.tileM.tile[thisTile].collision && !gp.player.checkIfObjectInWay(testEntity)) {
            switch (name) { //chooses object
                //case OBJ_TruckTipCooker.OBJ_NAME -> gp.obj[mapNum][i] = new OBJ_TruckTipCooker(gp);
            }

            gp.obj[mapNum][i].worldX = x * gp.tileSize;
            gp.obj[mapNum][i].worldY = y * gp.tileSize;
        } else if (isItARandomMonsterDrop) {
            testEntity.worldX = startX;
            testEntity.worldY = startY;
            while (gp.tileM.tile[thisTile].collision || gp.player.checkIfObjectInWay(testEntity) || (testEntity.worldX < 11 || testEntity.worldX > 60) || (testEntity.worldY < 5 || testEntity.worldY > 19)) {
                testEntity.worldX++;
                if (testEntity.worldX - startX > 4) {
                    testEntity.worldY++;
                    testEntity.worldX = startX;
                }
                if (testEntity.worldY - startY > 4) {
                    break;
                }
                thisTile = gp.tileM.mapTileNum[mapNum][testEntity.worldX][testEntity.worldY];
            }
            testEntity.worldX = testEntity.worldX * gp.tileSize;
            testEntity.worldY = testEntity.worldY * gp.tileSize;
            //noinspection LoopStatementThatDoesntLoop
            for (int j = 0; j < gp.obj[1].length; //noinspection UnusedAssignment
                 j++) {
                switch (name) { //chooses object
                    //case OBJ_Tutorial_Arrow_Right.OBJ_NAME -> gp.obj[mapNum][i] = new OBJ_Tutorial_Arrow_Right(gp);
                }

                gp.obj[mapNum][j].worldX = testEntity.worldX;
                gp.obj[mapNum][j].worldY = testEntity.worldY;
                break;
            }
        }
    }

    public void setNPCAfterStart(String name, int mapNum, int x, int y) {

        int count = 0;
        for (int i = 0; i < gp.npc[1].length; i++) {
            if (gp.npc[gp.currentMap][i] != null) {
                count++; //at end of loop, count will show the index of the last object in the array
            } else {
                break;
            }
        }

        int i = count;
        switch (name) { //chooses npc
            //case "Andrea" -> gp.npc[mapNum][i] = new NPC_Andrea(gp);
        }

        gp.npc[mapNum][i].worldX = x * gp.tileSize;
        gp.npc[mapNum][i].worldY = y * gp.tileSize;
    }

    public void setObject() {
        int mapNum = 0;
        int i = 0;

//        gp.obj[mapNum][i] = new OBJ_InsideDoorSideways(gp);
//        gp.obj[mapNum][i].worldX = 22 * gp.tileSize;
//        gp.obj[mapNum][i].worldY = 17 * gp.tileSize;
//        i++;

//        mapNum = 1;  //ADD OBJECTS TO NEXT MAP LIKE THIS

//        gp.obj[mapNum][i] = new OBJ_InsideDoorSideways(gp);
//        gp.obj[mapNum][i].worldX = 23 * gp.tileSize;
//        gp.obj[mapNum][i].worldY = 23 * gp.tileSize;
//        i++;
    }

    public void setNPC() {
        int mapNum = 0; //DOWNSTAIRS
        int i = 0;

//        gp.npc[mapNum][i] = new NPC_Dad(gp);
//        gp.npc[mapNum][i].worldX = gp.tileSize*18;
//        gp.npc[mapNum][i].worldY = gp.tileSize*14;
//        gp.npc[mapNum][i].getImage();
//        i++;

//        mapNum = 1;  //UPSTAIRS
//        i = 0;

//        gp.npc[mapNum][i] = new NPC_Dad(gp);
//        gp.npc[mapNum][i].worldX = gp.tileSize*50;
//        gp.npc[mapNum][i].worldY = gp.tileSize*5;
//        gp.npc[mapNum][i].getImage();
//        i++;
    }

    public int setMonster(String type, int monsterNumber, int x, int y, int mapNum, boolean randomizeLocation) {
        switch (type) {
//            case "Spider" -> {
//                gp.monster[mapNum][monsterNumber] = new MON_Spider(gp);
//                gp.monster[mapNum][monsterNumber].newMonster = true;
//            }
        }
        if (randomizeLocation) { // sets monster in any square up to 2 tiles away from player in any direction but never on the player
            Random rand = new Random();
            int randX = 0;
            int randY = 0;
            while (randX == 0 && randY == 0) {
                randX = rand.nextInt(4) - 2;
                randY = rand.nextInt(4) - 2;
                gp.monster[mapNum][monsterNumber].worldX = gp.tileSize * (x + randX);
                gp.monster[mapNum][monsterNumber].worldY = gp.tileSize * (y + randY);
            }

        } else { // will place monster exactly at specified x and y co-ordinates
            gp.monster[mapNum][monsterNumber].worldX = gp.tileSize * x;
            gp.monster[mapNum][monsterNumber].worldY = gp.tileSize * y;
        }

        if (gp.monster[mapNum][monsterNumber].worldX/gp.tileSize > 16 && gp.monster[mapNum][monsterNumber].worldX/gp.tileSize < 30) {
            if (gp.monster[mapNum][monsterNumber].worldY/gp.tileSize > 9 && gp.monster[mapNum][monsterNumber].worldY/gp.tileSize < 20) {
                gp.monster[mapNum][monsterNumber].insideHouse = true;
                if (gp.player.insideHouse) {
                    gp.monster[mapNum][monsterNumber].getImage();
                }
            } else {
                gp.monster[mapNum][monsterNumber].insideHouse = false;
                if (!gp.player.insideHouse) {
                    gp.monster[mapNum][monsterNumber].getImage();
                }
            }
        } else {
            gp.monster[mapNum][monsterNumber].insideHouse = false;
            if (!gp.player.insideHouse) {
                gp.monster[mapNum][monsterNumber].getImage();
            }
        }

        monsterNumber++; //monster counter increments so that next call of method adds to next slot in monster array

        return monsterNumber;
    }

    public int setInteractiveTilesAfterStart(int missionTrigger) {
        int count = 0;
        int colToAdd;
        int rowToAdd;
        int mapNum = 0;

//        if (missionTrigger == MissionStates.DRAG_COOKER_TO_BINS) {
////          mapNum = 0; // already set above
//            colToAdd = 60;
//            rowToAdd = 12;
//            for (int i = 0; i < gp.iTile[mapNum].length; i++) {
//                if (gp.iTile[mapNum][i] == null) {
//                    gp.iTile[mapNum][i] = new IT_CookerTile(gp, colToAdd, rowToAdd); i++; count++;
//                }
//            }
//        }
        return count;
    }

    public void setInteractiveTile() {
        int mapNum = 0;
        int i = 0;

//        gp.iTile[mapNum][i] = new IT_WeedTile(gp, 41, 11); i++; weedCount++;

//        mapNum = 1;

//        gp.iTile[mapNum][i] = new IT_WeedTile(gp, 41, 11); i++;
    }

    public void removeCutSceneObjectFromMap(String entityName, int mapNum) {
        for (int i = 0; i < gp.obj[mapNum].length; i++) {
            if (gp.obj[mapNum][i] != null && Objects.equals(gp.obj[mapNum][i].name, entityName)) {
                gp.obj[mapNum][i] = null;
            }
        }
    }
}
