package main;

import entity.Entity;
import entity.PlayerDummy;

import java.awt.*;
import java.util.Objects;

public class CutsceneManager {

    GamePanel gp;
    Graphics2D g2;
    public int sceneNum;
    public int scenePhase;

    //Scene Number
    public final int NA = 0;
    public final int trampolineCar = 1;
    public final int asianOnPhone = 2;
    public final int andreaOnPhone = 3;
    public int spriteNum = 0;

    public CutsceneManager(GamePanel gp) {
        this.gp = gp;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        switch (sceneNum) {
            case trampolineCar -> scene_trampolineCar();
//            case asianOnPhone -> scene_asianOnPhone();
            case andreaOnPhone -> scene_andreaOnPhone();
        }
    }

    public void scene_andreaOnPhone() {
        if (!gp.player.tempXYDirectionSetYet) {
            gp.player.tempPlayerWorldX = gp.player.worldX;
            gp.player.tempPlayerWorldY = gp.player.worldY;
            gp.player.tempPlayerDirection = gp.player.direction;
            gp.player.tempXYDirectionSetYet = true;
        }

        if (scenePhase == 0) {
            gp.player.drawing = false;

            for (int i = 0; i < gp.npc[gp.currentMap].length; i++) { //add player character to cutscene
                if (gp.npc[gp.currentMap][i] == null) {
                    gp.npc[gp.currentMap][i] = new PlayerDummy(gp);
                    gp.npc[gp.currentMap][i].worldX = gp.player.tempPlayerWorldX;
                    gp.npc[gp.currentMap][i].worldY = gp.player.tempPlayerWorldY + 30;
                    gp.npc[gp.currentMap][i].direction = gp.player.tempPlayerDirection;
                    break;
                }
            }

            gp.player.worldX = 13 * gp.tileSize;
            gp.player.worldY = 11 * gp.tileSize;

            scenePhase++;
        } else if (scenePhase == 1) {
            gp.player.worldY -= 2;

            if (gp.player.worldY <= 6 * gp.tileSize) {
                scenePhase++;
            }
        } else if (scenePhase == 2) {
            for (int i = 0; i < gp.obj[gp.currentMap].length; i++) {
                if (gp.obj[gp.currentMap][i] != null && Objects.equals(gp.obj[gp.currentMap][i].name, "TelephoneHall")) {
//                    gp.obj[gp.currentMap][i].dialogueSet = gp.player.missionState;
                    gp.ui.npc = gp.obj[gp.currentMap][i];
                }
            }
            gp.ui.drawDialogueScreen(0);
        } else if (scenePhase == 3) {
            sceneNum = NA;
            scenePhase = 0;
            gp.player.exitingFromCutScene = true;
            gp.player.playerDummyToBeRemoved = true;
            gp.gameState = gp.playState;
        }
    }

//    public void scene_asianOnPhone() {
//        if (!gp.player.tempXYDirectionSetYet) {
//            gp.player.tempPlayerWorldX = gp.player.worldX;
//            gp.player.tempPlayerWorldY = gp.player.worldY;
//            gp.player.tempPlayerDirection = gp.player.direction;
//            gp.player.tempXYDirectionSetYet = true;
//        }
//
//        if (scenePhase == 0) {
//            gp.player.drawing = false;
//            gp.player.worldX = 55 * gp.tileSize;
//            gp.player.worldY = 7 * gp.tileSize;
//            if (gp.player.missionState == MissionStates.NOT_GET_PAID_FOR_OLD_COOKER) {
//                gp.aSetter.setObjectAfterStart(OBJ_TruckTipCooker.OBJ_NAME, gp.currentMap, 62, 2, false);
//                gp.aSetter.setNPCAfterStart("TipDude", gp.currentMap, 62, 3);
//            }
//            scenePhase++;
//        } else if (scenePhase == 1) {
//            if (gp.player.missionState == MissionStates.NOT_GET_PAID_FOR_OLD_COOKER) {
//                for (int i = 0; i < gp.npc[gp.currentMap].length; i++) {
//                    if (gp.npc[gp.currentMap][i] != null && Objects.equals(gp.npc[gp.currentMap][i].name, "TipDude")) {
//                        if (!gp.npc[gp.currentMap][i].tempWorldYSetYet) {
//                            gp.npc[gp.currentMap][i].tempWorldY = gp.npc[gp.currentMap][i].worldY;
//                            gp.npc[gp.currentMap][i].tempWorldYSetYet = true;
//                        }
//                        gp.npc[gp.currentMap][i].direction = "down";
//                        gp.npc[gp.currentMap][i].worldY += 2;
//                        switchWalkImageCutscene(gp.npc[gp.currentMap][i]);
//                        if (gp.npc[gp.currentMap][i].worldY > 6 * gp.tileSize) {
//                            for (int j = 0; j < gp.npc[gp.currentMap].length; j++) {
//                                if (gp.npc[gp.currentMap][j] != null && Objects.equals(gp.npc[gp.currentMap][j].name, "Merchant")) {
//                                    gp.npc[gp.currentMap][j].direction = "up";
//                                }
//                            }
//                        }
//                        if (gp.npc[gp.currentMap][i].worldY > 8 * gp.tileSize + 10) {
//                            gp.npc[gp.currentMap][i].tempWorldY = 0;
//                            gp.npc[gp.currentMap][i].tempWorldYSetYet = false;
//                            scenePhase++;
//                        }
//                    }
//                }
//            } else {
//                scenePhase++;
//            }
//        } else if (scenePhase == 2) {
//            if (gp.player.missionState == MissionStates.NOT_GET_PAID_FOR_OLD_COOKER) {
//                for (int i = 0; i < gp.npc[gp.currentMap].length; i++) {
//                    if (gp.npc[gp.currentMap][i] != null && Objects.equals(gp.npc[gp.currentMap][i].name, "TipDude")) {
//                        gp.npc[gp.currentMap][i].dialogueSet = 0;
//                        gp.ui.npc = gp.npc[gp.currentMap][i];
//                    }
//                }
//                gp.ui.drawDialogueScreen(0);
//            } else {
//                scenePhase++;
//            }
//        } else if (scenePhase == 3) {
//            if (gp.player.missionState == MissionStates.NOT_GET_PAID_FOR_OLD_COOKER) {
//                for (int i = 0; i < gp.npc[gp.currentMap].length; i++) {
//                    if (gp.npc[gp.currentMap][i] != null && Objects.equals(gp.npc[gp.currentMap][i].name, "TipDude")) {
//                        if (!gp.npc[gp.currentMap][i].tempWorldYSetYet) {
//                            gp.npc[gp.currentMap][i].tempWorldY = gp.npc[gp.currentMap][i].worldY;
//                            gp.npc[gp.currentMap][i].tempWorldYSetYet = true;
//                        }
//                        gp.npc[gp.currentMap][i].direction = "up";
//                        gp.npc[gp.currentMap][i].worldY -= 2;
//                        switchWalkImageCutscene(gp.npc[gp.currentMap][i]);
//                    }
//                    if (gp.npc[gp.currentMap][i] != null && gp.npc[gp.currentMap][i].worldY < 2 * gp.tileSize) {
//                        gp.npc[gp.currentMap][i].direction = "down";
//                        gp.npc[gp.currentMap][i].tempWorldYSetYet = false;
//                        scenePhase++;
//                    }
//                }
//            } else {
//                scenePhase++;
//            }
//        } else if (scenePhase == 4) {
//            for (int i = 0; i < gp.obj[gp.currentMap].length; i++) {
//                if (gp.obj[gp.currentMap][i] != null && Objects.equals(gp.obj[gp.currentMap][i].name, "TelephoneHall")) {
//                    gp.obj[gp.currentMap][i].dialogueSet = gp.player.missionState;
//                    gp.ui.npc = gp.obj[gp.currentMap][i];
//                }
//            }
//            gp.ui.drawDialogueScreen(0);
//        } else if (scenePhase == 5) {
//            if (gp.player.missionState == MissionStates.NOT_GET_PAID_FOR_OLD_COOKER) {
//                for (int i = 0; i < gp.npc[gp.currentMap].length; i++) {
//                    if (gp.npc[gp.currentMap][i] != null && Objects.equals(gp.npc[gp.currentMap][i].name, "Merchant")) {
//                        if (!gp.npc[gp.currentMap][i].tempWorldYSetYet) {
//                            gp.npc[gp.currentMap][i].tempWorldY = gp.npc[gp.currentMap][i].worldY;
//                            gp.npc[gp.currentMap][i].tempWorldYSetYet = true;
//                        }
//                        gp.npc[gp.currentMap][i].direction = "down";
//                        gp.npc[gp.currentMap][i].worldY += 4;
//                        switchWalkImageCutscene(gp.npc[gp.currentMap][i]);
//                        gp.player.worldY += 2;
//                        if (gp.npc[gp.currentMap][i].worldY > 21 * gp.tileSize) {
//                            gp.npc[gp.currentMap][i].tempWorldY = 0;
//                            gp.npc[gp.currentMap][i].tempWorldYSetYet = false;
//                            gp.npc[gp.currentMap][i] = null;
//                            for (int j = 0; j < gp.npc[gp.currentMap].length; j++) {
//                                if (gp.npc[gp.currentMap][j] != null && Objects.equals(gp.npc[gp.currentMap][j].name, "TipDude")) {
//                                    gp.npc[gp.currentMap][j] = null;
//                                    break;
//                                }
//                            }
//                            scenePhase++;
//                        }
//                    }
//                }
//            } else {
//                scenePhase++;
//            }
//        } else if (scenePhase == 6) {
//            if (gp.player.missionState == MissionStates.DRAG_COOKER_TO_BINS) {
//                gp.aSetter.removeCutSceneObjectFromMap(OBJ_Tutorial_TileSelectorMarker.OBJ_NAME, gp.currentMap);
//                gp.aSetter.removeCutSceneObjectFromMap(OBJ_Tutorial_Arrow_Right.OBJ_NAME, gp.currentMap);
//            }
//            if (gp.player.missionState == MissionStates.NOT_GET_PAID_FOR_OLD_COOKER) {
//                gp.aSetter.removeCutSceneObjectFromMap(OBJ_TruckTipCooker.OBJ_NAME, gp.currentMap);
//            }
//
//            sceneNum = NA;
//            scenePhase = 0;
//            gp.player.exitingFromCutScene = true;
//            gp.gameState = gp.playState;
//        }
//    }

    private void switchWalkImageCutscene(Entity entity) {
        if (Math.abs(entity.worldY - entity.tempWorldY) > 45) {
            entity.tempWorldY = entity.worldY;
            switch(entity.direction) {
                case "up":
                    if (spriteNum == 0) {
                        spriteNum = 1;
                        entity.up1 = entity.up2;
                    } else if (spriteNum == 1) {
                        spriteNum = 0;
                        entity.up1 = entity.up3;
                    }
                    break;
                case "down":
                    if (spriteNum == 0) {
                        spriteNum = 1;
                        entity.down1 = entity.down2;
                    } else if (spriteNum == 1) {
                        spriteNum = 0;
                        entity.down1 = entity.down3;
                    }
                    break;
                case "left":
                    if (spriteNum == 0) {
                        spriteNum = 1;
                        entity.left1 = entity.left2;
                    } else if (spriteNum == 1) {
                        spriteNum = 0;
                        entity.left1 = entity.left3;
                    }
                    break;
                case "right":
                    if (spriteNum == 0) {
                        spriteNum = 1;
                        entity.right1 = entity.right2;
                    } else if (spriteNum == 1) {
                        spriteNum = 0;
                        entity.right1 = entity.right3;
                    }
                    break;
            }
        }
    }

    public void scene_trampolineCar() {
        if (!gp.player.tempXYDirectionSetYet) {
            gp.player.tempPlayerWorldX = 58 * gp.tileSize;
            gp.player.tempPlayerWorldY = 7 * gp.tileSize;
            gp.player.tempPlayerDirection = "left";
            gp.player.tempXYDirectionSetYet = true;
        }

        if (scenePhase == 0) {

            gp.player.drawing = false;
            gp.player.worldX = 55 * gp.tileSize;

            for (int i = 0; i < gp.npc[gp.currentMap].length; i++) { //add player character to cutscene
                if (gp.npc[gp.currentMap][i] == null) {
                    gp.npc[gp.currentMap][i] = new PlayerDummy(gp);
                    gp.npc[gp.currentMap][i].worldX = gp.player.tempPlayerWorldX;
                    gp.npc[gp.currentMap][i].worldY = gp.player.tempPlayerWorldY;
                    gp.npc[gp.currentMap][i].direction = gp.player.tempPlayerDirection;
                    scenePhase++;
                    break;
                }
            }

            for (int i = 0; i < gp.npc[gp.currentMap].length; i++) {
                if (gp.npc[gp.currentMap][i] != null && Objects.equals(gp.npc[gp.currentMap][i].name, "Dad")) {
                    gp.npc[gp.currentMap][i].worldX = 56 * gp.tileSize;
                    gp.npc[gp.currentMap][i].worldY = 6 * gp.tileSize;
                    gp.npc[gp.currentMap][i].direction = "down";
                    gp.ui.npc = gp.npc[gp.currentMap][i];
                    break;
                }
            }
        } else if (scenePhase == 1) {
            gp.player.worldY -= 2;

            if (gp.player.worldY <= 7 * gp.tileSize) {
                scenePhase++;
            }
        } else if (scenePhase == 2) {
            gp.ui.drawDialogueScreen(0);
        } else if (scenePhase == 3) {

            sceneNum = NA;
            scenePhase = 0;
            gp.player.missionSubstate = 3;
            gp.player.exitingFromCutScene = true;
            gp.player.playerDummyToBeRemoved = true;
            gp.gameState = gp.playState;
        }
    }
}
