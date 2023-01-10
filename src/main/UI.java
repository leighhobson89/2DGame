package main;

import entity.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

public class UI {
    final GamePanel gp;
    Graphics2D g2;
    public Font maruMonica, maruMonica_40;
    BufferedImage bolt_full, bolt_half, bolt_blank, squeakyToyFull, squeakyToyEmpty, coin;
    //public boolean messageOn = false;
    public final ArrayList<String> message = new ArrayList<>();
    final ArrayList<Integer> messageCounter = new ArrayList<>();
    //public boolean gameFinished = false;
    public String currentDialogue;
    public int commandNum = 0;
    public int titleScreenState = 0; //0: FIRST TITLE SCREEN 1: SECOND SCREEN
    public String colorOutfit = "brown";
    public String outfitChosen = "";
    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;
    public int subState = 0;
    int counter = 0;
    public Entity npc;
    int charIndex = 0;
    String combinedText = "";

    //MISSION STUFF
    Entity inventoryItem;

    public UI(GamePanel gp) throws IOException, FontFormatException {
        this.gp = gp;

        InputStream iS = getClass().getResourceAsStream("/fonts/x12y16pxMaruMonica.ttf");
        assert iS != null;
        //maruMonica = Font.createFont(Font.TRUETYPE_FONT,iS);
        //maruMonica_40 = maruMonica.deriveFont(40F);

        //CREATE HUD OBJECT

    }

    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        //g2.setFont(maruMonica_40);
        //g2.setColor(Color.WHITE);

        //TITLE STATE
        if (gp.gameState == gp.titleState) {
            //drawTitleScreen();
            //drawMessage();
        }
        //PLAY STATE
        if (gp.gameState == gp.playState) {
            drawPlayerLife();
            drawMapAreaName(gp.currentMap);
            drawMessage();
        }
        //PAUSE STATE
        if (gp.gameState == gp.pauseState) {
            drawPlayerLife();
            drawMapAreaName(gp.currentMap);
            drawPauseScreen();
        }
        //DIALOGUE STATE
        if (gp.gameState == gp.dialogueState) {
            drawPlayerLife();
            drawMapAreaName(gp.currentMap);
            drawDialogueScreen(0);
        }
        //CHARACTER STATE
        if (gp.gameState == gp.characterState) {
            drawCharacterScreen();
            drawInventory(gp.player, true);
        }
        //OPTIONS STATE
        if (gp.gameState == gp.optionsState) {
            drawOptionsScreen();
        }
        //GAME OVER STATE
        if (gp.gameState == gp.gameOverState) {
            drawGameOverScreen();
        }
        //TRANSITION STATE
        if (gp.gameState == gp.transitionState) {
            drawTransition(gp.player.transitionStairs);
        }
        //TRADE STATE
        if (gp.gameState == gp.tradeState) {
            drawTradeScreen();
        }
        //SLEEP STATE
        if (gp.gameState == gp.sleepState) {
            drawSleepScreen();
        }

        //QUIZ STATE
        if (gp.gameState == gp.quizState) {
            drawQuizScreen(gp.quizSubState);
        }
    }

    public void drawGameOverScreen() {
        g2.setColor(new Color(0,0,0, 150)); // half black half transparent
        g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

        text = "Game Over";
        //Shadow
        g2.setColor(Color.BLACK);
        x = getXforCenteredText(text);
        y = gp.tileSize*4;
        g2.drawString(text, x, y);
        //Main
        g2.setColor(Color.WHITE);
        g2.drawString(text, x-4, y-4);

        //Retry
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x = getXforCenteredText(text);
        y += gp.tileSize*4;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x-40, y);
        }
        //Back to the title screen
        text = "Quit";
        x = getXforCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x-40, y);
        }
    }

    public void drawMapAreaName(int currentMap) {

        String text;
        int x = gp.tileSize*15;
        int y = gp.tileSize/2;

        drawSubWindow(x + 15, y - 17, gp.tileSize*4 + 20, gp.tileSize + 25);

        switch (currentMap) {
            case 0 -> {
                text = "Downstairs";
                g2.drawString(text, x + 40, y + 33);
            }
            case 1 -> {
                text = "Upstairs";
                g2.drawString(text, x + 65, y + 33);
            }
        }

    }

    public void drawPlayerLife() {

        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;

//        g2.setColor(Color.black);
//        g2.fillRoundRect(x - 5, y - 10, gp.tileSize*4, gp.tileSize*2, 10, 10);
        drawSubWindow(x - 15, y - 17, gp.tileSize*4 + 20, gp.tileSize + 25); //height when mana displayed should be ((gp.tileSize*2) + 15)

        //DRAW MAX LIFE
        while (i < gp.player.maxStress /2) {
            g2.drawImage(bolt_blank, x, y, null);
            i++;
            x += gp.tileSize*0.8;
        }

        x = gp.tileSize/2;
        i = 0;

        //DRAW CURRENT LIFE
        while(i < gp.player.stressLevel) {
            g2.drawImage(bolt_half, x, y, null);
            i++;
            if (i < gp.player.stressLevel) {
                g2.drawImage(bolt_full, x, y, null);
            }
            i++;
            x += gp.tileSize*0.8;
        }

//        //DRAW MAX MANA
//        x = (gp.tileSize/2) - 15;
//        y = gp.tileSize + 20;
//        i = 0;
//        while (i < gp.player.maxMana) {
//            g2.drawImage(squeakyToyEmpty, x, y, null);
//            i++;
//            x += 35;
//        }
//
//        //DRAW CURRENT MANA
//        x = (gp.tileSize/2) - 15;
//        y = gp.tileSize + 20;
//        i = 0;
//        while (i < gp.player.mana) {
//            g2.drawImage(squeakyToyFull, x, y, null);
//            i++;
//            x += 35;
//        }
    }

    public void drawMessage() {
        int messageX = gp.tileSize/2 - 15;
        int messageY = gp.tileSize * 3;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 28F));

        for (int i = 0; i < message.size(); i++) {
            if (message.get(i) != null) {
                g2.setColor(Color.BLACK);
                g2.drawString(message.get(i), messageX + 2, messageY + 2);
                g2.setColor(Color.WHITE);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) +1; // messageCounter++ but for ArrayList you have to type like this
                messageCounter.set(i, counter); // set the counter to the array
                messageY += 50;

                if (messageCounter.get(i) > 180) {
                    //noinspection SuspiciousListRemoveInLoop
                    message.remove(i);
                    //noinspection SuspiciousListRemoveInLoop
                    messageCounter.remove(i);
                }
            }
        }
    }

    public void drawTitleScreen() {

        if (titleScreenState == 0) {
            g2.setColor(new Color (218, 217, 132));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            //TITLE NAME
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
            String text = "Mums Adventure";
            int x = getXforCenteredText(text);
            int y = gp.tileSize*3;

            //SHADOW
            g2.setColor(new Color (168, 168, 103));
            g2.drawString(text, x+5, y+5);
            //MAIN COLOR
            g2.setColor(Color.BLACK);
            g2.drawString(text, x, y);

            //MUM IMAGE
            x = gp.screenWidth/2 - gp.tileSize*2;
            y += gp.tileSize;
            g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);

            //DAD IMAGE
            x = gp.screenWidth/2;
            g2.drawImage(gp.player.dadDown1, x, y, gp.tileSize*2, gp.tileSize*2, null);

            //DOG LEFT IMAGE // CHANGE FOR PIP
            x = gp.screenWidth/2 - gp.tileSize*3 - 20;
            y = (gp.tileSize*9)/2 + 20;
            g2.drawImage(gp.player.phoebeRight2, x, y, gp.tileSize*2, gp.tileSize*2, null);

            //DOG RIGHT IMAGE
            x = gp.screenWidth - 420;
            g2.drawImage(gp.player.phoebeLeft1, x, y, gp.tileSize*2, gp.tileSize*2, null);

            //MENU
            g2.setFont((g2.getFont().deriveFont(Font.BOLD, 48F)));

            text = "NEW GAME";
            x = getXforCenteredText(text);
            y += (gp.tileSize*3.5) - 20;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "LOAD GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "INSTRUCTIONS";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "QUIT";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 3) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        }
        else if (titleScreenState == 1) {
            g2.setColor(new Color (218, 217, 132));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            g2.setColor(Color.BLACK);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 42F));

            String text = "SELECT YOUR OUTFIT!";
            int x = getXforCenteredText(text);
            int y = gp.tileSize*3;
            g2.drawString(text, x, y);

            text = "Red";
            x = getXforCenteredText(text) - gp.tileSize*4;
            y = gp.tileSize*9;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x-gp.tileSize, y);
                x = gp.screenWidth/2 - (gp.tileSize*2)/2;
                y = gp.tileSize*5;
                g2.drawImage(gp.player.down1_red, x, y, gp.tileSize*2, gp.tileSize*2, null);
                colorOutfit = "red";
            }

            text = "Brown";
            x = getXforCenteredText(text);
            y = gp.tileSize*9;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x-gp.tileSize, y);
                x = gp.screenWidth/2 - (gp.tileSize*2)/2;
                y = gp.tileSize*5;
                g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);
                colorOutfit = "brown";
            }

            text = "Purple";
            x = getXforCenteredText(text) + gp.tileSize*4;
            y = gp.tileSize*9;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x-gp.tileSize, y);
                x = gp.screenWidth/2 - (gp.tileSize*2)/2;
                y = gp.tileSize*5;
                g2.drawImage(gp.player.down1_purple, x, y, gp.tileSize*2, gp.tileSize*2, null);
                colorOutfit = "purple";
            }

            text = "Back";
            x = getXforCenteredText(text);
            y = gp.tileSize*11;
            g2.drawString(text, x, y);
            if (commandNum == 3) {
                g2.drawString(">", x-gp.tileSize, y);
                colorOutfit = "brown";
            }

            x = gp.screenWidth/2 - (gp.tileSize*2)/2;
            y = gp.tileSize*5;
            switch (colorOutfit) {
                case "red" -> g2.drawImage(gp.player.down1_red, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
                case "brown" -> g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
                case "purple" -> g2.drawImage(gp.player.down1_purple, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
            }
        } else if (titleScreenState == 2) {
            g2.setColor(new Color (218, 217, 132));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            g2.setColor(Color.BLACK);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));

            String text = "INSTRUCTIONS";
            int x = getXforCenteredText(text);
            int y = gp.tileSize*2;
            g2.setColor(new Color (168, 168, 103)); //SHADOW
            g2.drawString(text, x+4, y+4);
            g2.setColor(Color.BLACK); //TEXT
            g2.drawString(text, x, y);

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 42F));

            text = "You are Mum, a lonely character in an enclosed";
            x = getXforCenteredText(text);
            y = gp.tileSize*4;
            g2.drawString(text, x, y);

            text = "world.  You have to go through your daily";
            x = getXforCenteredText(text);
            y = gp.tileSize*5;
            g2.drawString(text, x, y);

            text = "life, tolerating stress, anguish, and routine.";
            x = getXforCenteredText(text);
            y = gp.tileSize*6;
            g2.drawString(text, x, y);

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 58F));

            text = "Will you survive another day?";
            x = gp.screenWidth/2 - 240;
            y = gp.tileSize*8 + 40;
            g2.setColor(new Color (168, 168, 103)); //SHADOW
            g2.drawString(text, x+4, y+4);
            g2.setColor(Color.BLACK); //TEXT
            g2.drawString(text, x, y);

            x = gp.tileSize - 20;
            y = gp.tileSize*6 + 35;
            g2.drawImage(gp.player.right2, x, y, gp.tileSize*4, gp.tileSize*4, null);

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 42F));

            text = "Back";
            x = getXforCenteredText(text) - gp.tileSize*3;
            y = gp.tileSize*11;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "Next";
            x = getXforCenteredText(text) + gp.tileSize*3;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        } else if (titleScreenState == 3) {
            g2.setColor(new Color (218, 217, 132));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            g2.setColor(Color.BLACK);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));

            String text = "CONTROLS";
            int x = getXforCenteredText(text);
            int y = gp.tileSize*2;
            g2.setColor(new Color (168, 168, 103)); //SHADOW
            g2.drawString(text, x+4, y+4);
            g2.setColor(Color.BLACK); //TEXT
            g2.drawString(text, x, y);

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 42F));

            x = gp.screenWidth - gp.tileSize*5;
            g2.drawImage(gp.player.left1, x, y, gp.tileSize*4, gp.tileSize*4, null);

            text = "- Use the arrow keys to move around.";
            x = 15;
            y = gp.tileSize*4;
            g2.drawString(text, x, y);

            text = "- Use ENTER to interact with the world.";
            y = gp.tileSize*5;
            g2.drawString(text, x, y);

            text = "- Use SPACE to attack or perform actions.";
            y = gp.tileSize*6;
            g2.drawString(text, x, y);

            text = "- Look for things to do by talking to people.";
            y = gp.tileSize*7;
            g2.drawString(text, x, y);

            text = "- Check options screen with ESC for full list of controls.";
            y = gp.tileSize*8;
            g2.drawString(text, x, y);

            text = "- Try and survive another day of misery!";
            y = gp.tileSize*9;
            g2.drawString(text, x, y);

            text = "Back";
            x = getXforCenteredText(text) - gp.tileSize*3;
            y = gp.tileSize*11;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "Finish";
            x = getXforCenteredText(text) + gp.tileSize*3;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        }


    }
    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen(int layout) {
        int x = 0;
        int y = 0;
        int width = 0;
        int height = 0;

        switch (layout) {
            case 0 -> {
                //WINDOW
                x = gp.tileSize * 2;
                y = gp.tileSize * 4;
                width = gp.screenWidth - (gp.tileSize * 4);
                height = gp.tileSize * 4;
            }
            case 1 -> {
                x = gp.tileSize * 3;
                y = gp.tileSize / 2;
                width = gp.screenWidth - (gp.tileSize * 6);
                height = gp.tileSize * 4;
            }
            case 2 -> {
                x = gp.tileSize * 3;
                y = gp.tileSize / 2;
                width = gp.screenWidth - (gp.tileSize * 6);
                height = gp.tileSize * 2;
            }
        }

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        x += gp.tileSize;
        y += gp.tileSize;

        if (npc != null) {
            if (npc.dialogueText[npc.dialogueSet][npc.dialogueIndex] != null) {

                char[] characters = npc.dialogueText[npc.dialogueSet][npc.dialogueIndex].toCharArray();

                if (charIndex < characters.length) {
                    String s = String.valueOf(characters[charIndex]);
                    combinedText = combinedText + s;
                    currentDialogue = combinedText;

                    charIndex++;
                }

                if (gp.keyH.enterPressed) {

                    gp.player.repeatSfx = true;
                    charIndex = 0;
                    combinedText = "";
                    gp.stopSFX(0);

                    if (gp.gameState == gp.dialogueState || gp.gameState == gp.cutSceneState) {
                        npc.dialogueIndex++;
                        gp.keyH.enterPressed = false;
                    }
                }
            } else {
                if (gp.gameState == gp.dialogueState) {
                    gp.gameState = gp.playState;
                } else if (gp.gameState == gp.cutSceneState) {
                    gp.csManager.scenePhase++;
                }

                npc.dialogueIndex = 0;
                npc.currentDialogueFinished = true;
            }
        }

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }

    }
    public void drawCharacterScreen() {

        //CREATE A FRAME
        final int frameX = gp.tileSize*2;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize*5;
        final int frameHeight = gp.tileSize*10;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //TEXT
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(32F));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize - 10;
        final int lineHeight = 35;

        //NAMES
        g2.drawString("Level", textX, textY); textY += lineHeight;
        g2.drawString("Stress", textX, textY); textY += lineHeight;
        g2.drawString("Strength", textX, textY); textY += lineHeight;
        g2.drawString("Dexterity", textX, textY); textY += lineHeight;
        g2.drawString("Attack", textX, textY); textY += lineHeight;
        g2.drawString("Defence", textX, textY); textY += lineHeight;
        g2.drawString("Exp", textX, textY); textY += lineHeight;
        g2.drawString("Next Level", textX, textY); textY += lineHeight;
        g2.drawString("Coin", textX, textY); textY += lineHeight + 10;
        g2.drawString("Weapon", textX, textY); textY += lineHeight + 15;
        g2.drawString("Armour", textX, textY); textY += lineHeight + 15;
        g2.drawString("Projectile", textX, textY);

        //VALUES
        int tailX = (frameX + frameWidth) - 30;
        // Reset textY
        textY = frameY + gp.tileSize - 10;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = gp.player.stressLevel + "/" + gp.player.maxStress;
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

//        value = gp.player.mana + "/" + gp.player.maxMana;
//        textX = getXForAlignToRightText(value, tailX);
//        g2.drawString(value, textX, textY);
//        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        if (gp.player.currentWeapon != null) {
            g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY-24, null);
        }
        textY += gp.tileSize;
        if (gp.player.currentArmour != null) {
            g2.drawImage(gp.player.currentArmour.down1, tailX - gp.tileSize, textY-22, null);
        }
        textY += gp.tileSize;
        if (gp.player.currentProjectile != null) {
            g2.drawImage(gp.player.currentProjectile.down1, tailX - gp.tileSize, textY-22, null);
        }
    }

    public void drawInventory(Entity entity, boolean cursor) {
        int frameX;
        int frameY;
        int frameWidth;
        int frameHeight;
        int slotCol;
        int slotRow;

        //FRAME
        if (entity == gp.player) {
            frameX = gp.tileSize*12;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize*6;
            frameHeight = gp.tileSize* 5;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        } else {
            frameX = gp.tileSize*2;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize*6;
            frameHeight = gp.tileSize* 5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //SLOT
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3;

        //DRAW PLAYER'S ITEMS
        for (int i = 0; i < entity.inventory.size(); i++) {

            //EQUIP CURSOR
            if ((entity.currentWeapon != null && Objects.equals(entity.inventory.get(i).name, entity.currentWeapon.name)) || (entity.currentArmour != null && Objects.equals(entity.inventory.get(i).name, entity.currentArmour.name)) || (entity.currentProjectile != null && Objects.equals(entity.inventory.get(i).name, entity.currentProjectile.name)) || ((entity.currentLight != null && Objects.equals(entity.inventory.get(i).name, entity.currentLight.name)) && !Objects.equals(entity.inventory.get(i).name, "Anti Brightness Pills"))) {
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);

            }
            g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);

            //DISPLAY AMOUNT
            if(entity == gp.player && entity.inventory.get(i).amount > 1) {
                g2.setFont(g2.getFont().deriveFont(32f));
                int amountX;
                int amountY;

                String s = "" + entity.inventory.get(i).amount;
                amountX = getXForAlignToRightText(s, slotX + 44);
                amountY = slotY + gp.tileSize;

                // DRAW SHADOW
                g2.setColor(new Color(60,60,60));
                g2.drawString(s, amountX, amountY);
                //NUMBER
                g2.setColor(Color.WHITE);
                g2.drawString(s, amountX - 3, amountY - 3);
            }

            slotX += slotSize;

            if (i == 4 || i == 9 || i == 14) {
                slotX = slotXstart;
                slotY += slotSize;
            }
        }

        //CURSOR
        if (cursor) {
            int cursorX = slotXstart + (slotSize * slotCol);
            int cursorY = slotYstart + (slotSize * slotRow);
            int cursorWidth = gp.tileSize;
            int cursorHeight = gp.tileSize;

            //DRAW CURSOR
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            // DESCRIPTION FRAME
            int dFrameY = frameY + frameHeight;
            int dFrameHeight = gp.tileSize * 3;

            //DRAW DESCRIPTION TEXT
            int textX = frameX + 20;
            int textY = dFrameY + gp.tileSize;
            g2.setFont(g2.getFont().deriveFont(28F));

            int itemIndex = getItemIndexOnSlot(slotCol, slotRow);

            if (entity.inventory.size() > 0) {
                if (itemIndex < entity.inventory.size()) {
                    drawSubWindow(frameX, dFrameY, frameWidth, dFrameHeight);
                    for (String line: entity.inventory.get(itemIndex).description.split("\n")) {
                        g2.drawString(line, textX, textY);
                        textY += 32;
                    }
                }
            }
        }
    }

    public void drawOptionsScreen() {
        g2.setColor(Color.WHITE);
        g2. setFont(g2.getFont().deriveFont(32F));

        // SUB WINDOW
        int frameX = gp.tileSize*6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize*8;
        int frameHeight = gp.tileSize*10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
            case 0 -> options_top(frameX, frameY);
            case 1 -> options_fullScreenNotification(frameX, frameY);
            case 2 -> options_control(frameX, frameY);
            case 3 -> options_endGameConfirmation(frameX, frameY);
        }

        gp.keyH.enterPressed = false;
    }
    public void options_top(int frameX, int frameY) {
        int textX;
        int textY;

        //TITLE
        String text = "Options";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        //FULLSCREEN
        textX = frameX + gp.tileSize;
        textY += gp.tileSize*2;
        g2.drawString("Full Screen", textX, textY);
        if(commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed) {
                gp.playSFX(11);
                gp.fullScreenOn = !gp.fullScreenOn;
                subState = 1;
            }
        }

        //MUSIC
        textY += gp.tileSize;
        g2.drawString("Music", textX, textY);
        if(commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
        }

        //SFX
        textY += gp.tileSize;
        g2.drawString("Sound FX", textX, textY);
        if(commandNum == 2) {
            g2.drawString(">", textX - 25, textY);
        }

        //CONTROL
        textY += gp.tileSize;
        g2.drawString("Controls", textX, textY);
        if(commandNum == 3) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                gp.playSFX(11);
                subState = 2;
                commandNum = 0;
            }
        }

        //END GAME
        textY += gp.tileSize;
        g2.drawString("End Game", textX, textY);
        if(commandNum == 4) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                gp.playSFX(11);
                subState = 3;
                commandNum = 0;
            }
        }

        //BACK
        textY += gp.tileSize*2;
        g2.drawString("Back", textX, textY);
        if(commandNum == 5) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                gp.playSFX(11);
                gp.gameState = gp.playState;
                commandNum = 0;
            }
        }

        //FULL SCREEN CHECKBOX
        textX = frameX + (int) (gp.tileSize*4.5);
        textY = frameY + gp.tileSize*2 + 28;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 24, 24);
        if(gp.fullScreenOn) {
            g2.fillRect(textX + 4, textY + 4, 17, 16);
        }

        //MUSIC VOLUME
        textY += gp.tileSize;
        g2.drawRect(textX,textY, 120, 24); // 120/5 = 24
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        //SFX VOLUME
        textY += gp.tileSize;
        g2.drawRect(textX,textY, 120, 24); // 120/5 = 24
        volumeWidth = 24 * gp.sfx.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        gp.config.saveConfig(); //saves option for loading back next time game starts
    }

    public void options_fullScreenNotification(int frameX, int frameY) {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize*3;

        currentDialogue = "The change will take\neffect after restarting\nthe game.";

        for (String line: currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // BACK
        textY = frameY + gp.tileSize*9;
        g2.drawString("Back", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                gp.playSFX(11);
                subState = 0;
            }
        }
    }

    public void options_control(int frameX, int frameY) {
        int textX;
        int textY;

        //TITLE
        String text = "Control";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Move", textX, textY); textY += gp.tileSize;
        g2.drawString("Confirm / Attack", textX, textY); textY += gp.tileSize;
        g2.drawString("Throw", textX, textY); textY += gp.tileSize;
        g2.drawString("Character Screen", textX, textY); textY += gp.tileSize;
        g2.drawString("Pause", textX, textY); textY += gp.tileSize;
        g2.drawString("Music On / Off", textX, textY); textY += gp.tileSize;
        g2.drawString("Options", textX, textY);

        textX = (int) (frameX + gp.tileSize*5.5);
        textY = frameY + gp.tileSize*2 + 3;
        g2.drawString("ARROWS", textX, textY); textY += gp.tileSize;
        g2.drawString("ENTER", textX, textY); textY += gp.tileSize;
        g2.drawString("T", textX, textY); textY += gp.tileSize;
        g2.drawString("C", textX, textY); textY += gp.tileSize;
        g2.drawString("P", textX, textY); textY += gp.tileSize;
        g2.drawString("S", textX, textY); textY += gp.tileSize;
        g2.drawString("ESC", textX, textY);

        // BACK
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize*9;
        g2.drawString("Back", textX, textY);
        if (commandNum == 0){
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                gp.playSFX(11);
                subState = 0;
                commandNum = 3;
            }
        }
    }

    public void options_endGameConfirmation(int frameX, int frameY) {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize*3;

        currentDialogue = "Quit the game and\nreturn to the title screen?";

        for (String line: currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        //YES
        String text = "Yes";
        textX = getXforCenteredText(text);
        textY += gp.tileSize*3;
        g2.drawString(text, textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                gp.playSFX(11);
                subState = 0;
                gp.ui.titleScreenState = 0;
                gp.ui.commandNum = 0;
                gp.gameState = gp.titleState;
                gp.resetGame(true);
            }
        }
        //NO
        text = "No";
        textX = getXforCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                gp.playSFX(11);
                subState = 0;
                commandNum = 4;
            }
        }
    }

//    public void setupSwitchMaps() {
//        if (gp.player.transitionDueToPills) {
//            gp.player.transitionDueToPills = false;
//            gp.player.startDialogue(gp.player, 17);
//        } else {
//            gp.gameState = gp.playState;
//        }
//        gp.otherMap = gp.currentMap;
//        gp.currentMap = gp.eHandler.tempMap;
//        gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
//        gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
//        gp.eHandler.previousEventX = gp.player.worldX;
//        gp.eHandler.previousEventY = gp.player.worldY;
//
//        for (int i = 0; i < gp.npc[gp.currentMap].length; i++) {
//            if (gp.player.missionState == MissionStates.DRAG_COOKER_TO_BINS) { //reset movable object if change area, and it is not where it needs to be
//                if (gp.npc[gp.currentMap][i] != null && (Objects.equals(gp.npc[gp.currentMap][i].name, "OldCooker"))) {
//                    if (gp.npc[gp.currentMap][i].linkedEntity == null) {
//                        gp.npc[gp.currentMap][i].worldX = 39 * gp.tileSize;
//                        gp.npc[gp.currentMap][i].worldY = 8 * gp.tileSize;
//                    }
//                    break;
//                }
//            }
//        }
//    }

    public void drawTransition(boolean transitionStairs) {
        counter++;
        g2.setColor(new Color(0,0,0,counter*5));
        g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);

        if (counter == 50) {
            gp.player.transitionStairs = false;
            counter = 0;
            if (transitionStairs) {
                //setupSwitchMaps();
            } else {
                gp.gameState = gp.playState;
            }
        }
    }

    public void drawQuizScreen(int quizSubState) {
        if (quizSubState == gp.mumsChair) {
            mumChair_questionSelect();
        } else if (quizSubState == gp.dadQuiz) {
            dadQuiz_questionSelect(gp.player.missionSubstate);
        }
        gp.keyH.enterPressed = false;
    }

    public void mumChair_questionSelect() {
        if (subState == 0) {
            drawDialogueScreen(2);
        }

        //DRAW WINDOW
        int x = gp.tileSize * 4;
        int y = gp.tileSize * 5;
        int width = gp.tileSize * 15;
        int height = (int)(gp.tileSize * 2.5);

        drawSubWindow(x, y, width, height);

        //DRAW TEXTS
        x += gp.tileSize-15;
        y += gp.tileSize;

        if (subState == 0) {
            g2.drawString("Sit down to watch TV", x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - 24, y);
                if (gp.keyH.enterPressed) {
                    gp.playSFX(11);
                    subState = 1; // sit to watch tv
                }
            }
            y += gp.tileSize;
            g2.drawString("Sit down and relax, destress, and save the game!", x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - 24, y);
                if (gp.keyH.enterPressed) {
                    gp.playSFX(11);
                    subState = 2; // destressEvent
                }
            }
        } else if (subState == 2) { // destressEvent
            gp.eHandler.DestressEvent();
            subState = 0;
            commandNum = 0;
        }
    }

    public void dadQuiz_questionSelect(int missionSubstate) {
        if (subState == 0) {
            drawDialogueScreen(1);
        }

        //DRAW WINDOW
        int x = gp.tileSize;
        int y = gp.tileSize * 5;
        int width = gp.tileSize * 18;
        int height = (int)(gp.tileSize * 2.5);

        drawSubWindow(x, y, width, height);

        //DRAW TEXTS
        x += gp.tileSize-15;
        y += gp.tileSize;

        if (subState == 0) {
            switch (missionSubstate) {
                case 4 -> {
                    g2.drawString("Wipe it down carefully, cos you haven't bothered sealing it properly!", x, y);
                    if (commandNum == 0) {
                        g2.drawString(">", x - 24, y);
                        if (gp.keyH.enterPressed) {
                            gp.playSFX(11); //correctSound
                            subState = 1; // correctSubstate
                        }
                    }
                    y += gp.tileSize;
                    g2.drawString("Nothing, just get out, get dry, and get on with my day!", x, y);
                    if (commandNum == 1) {
                        g2.drawString(">", x - 24, y);
                        if (gp.keyH.enterPressed) {
                            gp.playSFX(11); //wrongSound
                            subState = 2; // wrongSubstate
                        }
                    }
                }
                case 5 -> {
                    g2.drawString("You've never wasted anything!", x, y);
                    if (commandNum == 0) {
                        g2.drawString(">", x - 24, y);
                        if (gp.keyH.enterPressed) {
                            gp.playSFX(11);
                            subState = 2;
                        }
                    }
                    y += gp.tileSize;
                    g2.drawString("That bloody camper van up there, another waste of money!", x, y);
                    if (commandNum == 1) {
                        g2.drawString(">", x - 24, y);
                        if (gp.keyH.enterPressed) {
                            gp.playSFX(11);
                            subState = 1;
                        }
                    }
                }
                case 6 -> {
                    g2.drawString("Stand aside, and let me get on with it!", x, y);
                    if (commandNum == 0) {
                        g2.drawString(">", x - 24, y);
                        if (gp.keyH.enterPressed) {
                            gp.playSFX(11);
                            subState = 2;
                        }
                    }
                    y += gp.tileSize;
                    g2.drawString("Hang about, obstructing everything I try to do!", x, y);
                    if (commandNum == 1) {
                        g2.drawString(">", x - 24, y);
                        if (gp.keyH.enterPressed) {
                            gp.playSFX(11);
                            subState = 1;
                        }
                    }
                }
                case 7 -> {
                    g2.drawString("You call them 'Chip Eaters'!", x, y);
                    if (commandNum == 0) {
                        g2.drawString(">", x - 24, y);
                        if (gp.keyH.enterPressed) {
                            gp.playSFX(11);
                            subState = 1;
                        }
                    }
                    y += gp.tileSize;
                    g2.drawString("No special name, just normal people enjoying!", x, y);
                    if (commandNum == 1) {
                        g2.drawString(">", x - 24, y);
                        if (gp.keyH.enterPressed) {
                            gp.playSFX(11);
                            subState = 2;
                        }
                    }
                }
                case 8 -> {
                    g2.drawString("Absolutely every chance, and I can't bloody wait!", x, y);
                    if (commandNum == 0) {
                        g2.drawString(">", x - 24, y);
                        if (gp.keyH.enterPressed) {
                            gp.playSFX(11);
                            subState = 2;
                        }
                    }
                    y += gp.tileSize;
                    g2.drawString("None at all, I'm destined to be miserable sat here forever!", x, y);
                    if (commandNum == 1) {
                        g2.drawString(">", x - 24, y);
                        if (gp.keyH.enterPressed) {
                            gp.playSFX(11);
                            subState = 1;
                        }
                    }
                }
            }
        } else if (subState == 1) { //correct answer
            npc.startDialogue(npc, 71);
            subState = 0;
            gp.player.missionSubstate++;
            gp.player.quizScoreCount++;
        } else if (subState == 2) { //correct answer
            npc.startDialogue(npc, 72);
            subState = 0;
            gp.player.missionSubstate++;
        }
    }

    public void drawTradeScreen() {
        switch (subState) {
            case 0 -> trade_select();
            case 1 -> trade_buy();
            case 2 -> trade_sell();
        }
        gp.keyH.enterPressed = false;
    }

    public void trade_select() { //dialogue options
        drawDialogueScreen(1);

        //DRAW WINDOW
        int x = gp.tileSize * 15;
        int y = gp.tileSize * 4;
        int width = gp.tileSize * 3;
        int height = (int)(gp.tileSize * 3.5);

        drawSubWindow(x, y, width, height);

        //DRAW TEXTS
        x += gp.tileSize;
        y += gp.tileSize;
        if (Objects.equals(npc.name, "Andrea")) {
            g2.drawString("Take", x, y);
        } else {
            g2.drawString("Buy", x, y);
        }
        if (commandNum == 0) {
            g2.drawString(">", x - 24, y);
            if (gp.keyH.enterPressed) {
                gp.playSFX(11);
                subState = 1;
            }
        }
        y += gp.tileSize;
        if (Objects.equals(npc.name, "Andrea")) {
            g2.drawString("Give", x, y);
        } else {
            g2.drawString("Sell", x, y);
        }
        if (commandNum == 1) {
            g2.drawString(">", x - 24, y);
            if (gp.keyH.enterPressed) {
                gp.playSFX(11);
                subState = 2;
            }
        }
        y += gp.tileSize;
        g2.drawString("Leave", x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - 24, y);
            if (gp.keyH.enterPressed) {
                gp.playSFX(11);
                commandNum = 0;
                if (Objects.equals(npc.name, "Andrea")) {
                    npc.startDialogue(npc, 3);
                } else {
                    npc.startDialogue(npc, 2);
                }
            }
        }

    }

    public void trade_buy() {
        //DRAW PLAYER INVENTORY
        drawInventory(gp.player, false);
        // DRAW NPC INVENTORY
        drawInventory(npc, true);

        //DRAW HINT WINDOW
        int x = gp.tileSize*2;
        int y = gp.tileSize*9;
        int width = gp.tileSize*6;
        int height = gp.tileSize*2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[Esc] Back", x + 24, y + 60);

        //DRAW PLAYER COIN WINDOW
        x = gp.tileSize*12;
        drawSubWindow(x, y, width, height);
        g2.drawString("Your Coin: " + gp.player.coin, x + 24, y + 60);

        //DRAW PRICE WINDOW
        int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
        if (itemIndex < npc.inventory.size()) {

            x = (int) (gp.tileSize*5.5);
            y = (int) (gp.tileSize*5.5);
            width = (int) (gp.tileSize*2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x + 10, y + 8, 32, 32, null);

            int price = npc.inventory.get(itemIndex).price;
            String text = "" + price;
            x = getXForAlignToRightText(text, gp.tileSize*8 - 20);
            g2.drawString(text, x, y + 34);

            //BUY AN ITEM
            if (gp.keyH.enterPressed) {
                if (npc.inventory.get(itemIndex).price > gp.player.coin) {
                    subState = 0;
                    npc.startDialogue(npc, 3);
                } else {
                    if (gp.player.canObtainItem(npc.inventory.get(itemIndex), npc) && !Objects.equals(npc.name, "Andrea")) {
                        gp.player.coin -= npc.inventory.get(itemIndex).price;
                        gp.playSFX(11);
                    } else if (Objects.equals(npc.inventory.get(itemIndex).name, "Coat off Amanda") && Objects.equals(npc.name, "Andrea")) {
                        subState = 0;
                        commandNum = 0;
                        npc.inventory.get(itemIndex).price = 50; //set up coat price for selling after freebie from Andrea
                        gp.player.inventory.add(npc.inventory.get(itemIndex));
                        npc.inventory.remove(npc.inventory.get(itemIndex));
                        gp.playSFX(11);
                        gp.player.missionSubstate++;
                        if (gp.player.missionSubstate == 3) {
                            gp.player.andreaTempGoalCol = 0;
                            gp.player.andreaTempGoalRow = 0;
                            npc.startDialogue(npc, 6);
                            gp.player.AndreaLeaveSetup(npc);
                            commandNum = 0;
                            subState = 0;
                        } else {
                            npc.startDialogue(npc, 9);
                        }

                    } else if (Objects.equals(npc.name, "Andrea") && !Objects.equals(npc.inventory.get(itemIndex).name, "Coat off Amanda")) {
                        subState = 0;
                        npc.startDialogue(npc, 4);
                    } else {
                        subState = 0;
                        npc.startDialogue(npc, 4);
                    }
                }
            }
        }
    }

    public void trade_sell() {
        boolean alreadyRemoved = false;
        //DRAW PLAYERS INVENTORY
        drawInventory(gp.player, true);

        int x;
        int y;
        int width;
        int height;

        //DRAW HINT WINDOW
        x = gp.tileSize*2;
        y = gp.tileSize*9;
        width = gp.tileSize*6;
        height = gp.tileSize*2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[Esc] Back", x + 24, y + 60);

        //DRAW PLAYER COIN WINDOW
        x = gp.tileSize*12;
        drawSubWindow(x, y, width, height);
        g2.drawString("Your Coin: " + gp.player.coin, x + 24, y + 60);

        //DRAW PRICE WINDOW
        int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
        if (itemIndex < gp.player.inventory.size()) {

            x = (int) (gp.tileSize*15.5);
            y = (int) (gp.tileSize*5.5);
            width = (int) (gp.tileSize*2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x + 10, y + 8, 32, 32, null);

            int price = (int) (gp.player.inventory.get(itemIndex).price*0.7); //selling price modifier
            String text = "" + price;
            x = getXForAlignToRightText(text, gp.tileSize*18 - 20);
            g2.drawString(text, x, y + 34);

            //SELL AN ITEM
            for (int i = 0; i < gp.player.inventory.size(); i++) {
                inventoryItem = gp.player.inventory.get(i);
                if (Objects.equals(inventoryItem.name, "Pip's Bone")) {
                    gp.player.boneIndex = i;
                } else if (Objects.equals(inventoryItem.name, "Chopped Chicken")) {
                    gp.player.chickenIndex = i;
                }
            }
        }
    }

    public void drawSleepScreen() {
        counter++;

        if (counter < 120) {
            gp.eManager.lighting.filterAlpha += 0.01f;
            if (gp.eManager.lighting.filterAlpha > 1f) {
                gp.eManager.lighting.filterAlpha = 1f;
            }
        }
        if (counter >= 120) {
            gp.eManager.lighting.filterAlpha -= 0.01f;
            if (gp.eManager.lighting.filterAlpha <= 0f) {
                gp.eManager.lighting.filterAlpha = 0f;
                counter = 0;
                gp.eManager.lighting.dayState = gp.eManager.lighting.day;
                gp.eManager.lighting.dayCounter = 0;
                gp.gameState = gp.dialogueState;
            }
        }
    }

    public int getItemIndexOnSlot(int slotCol, int slotRow) {
        return slotCol + (slotRow * 5);
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0,0,0, 120); //a - transparency value 0-255
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth/2 - length/2;
    }

    public int getXForAlignToRightText(String text, int tailX) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return tailX - length;
    }
}
