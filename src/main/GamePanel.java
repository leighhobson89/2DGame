package main;

import ai.PathFinder;
import data.SaveLoad;
import entity.Entity;
import entity.Player;
import environment.EnvironmentManager;
import tile.Map;
import tile.TileManager;
import tile_interactive.InteractiveTile;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class GamePanel extends JPanel implements Runnable {

    //SCREEN SETTINGS
    final int originalTileSize = 16; //16x16 tile size
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; //actual size 48
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; //960 px
    public final int screenHeight = tileSize * maxScreenRow; //576 px
    public final boolean musicSetToPlayFromStart = true; //Change to true to play music from start

    //WORLD SETTINGS
    public final int maxWorldCol = 20;
    public final int maxWorldRow = 60;
    public int currentMap = 0;

    //FOR FULL SCREEN
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    public Graphics2D g2;
    public boolean fullScreenOn = false;

    //FPS
    final int FPS = 60;

    //SYSTEM
    public final TileManager tileM = new TileManager(this);
    public final KeyHandler keyH = new KeyHandler(this);
    final Sound music = new Sound();
    public final Sound sfx = new Sound();
    public final Sound sfxPhone = new Sound();
    public final CollisionChecker cChecker = new CollisionChecker(this);
    public final AssetSetter aSetter = new AssetSetter(this);
    public final UI ui = new UI(this);
    public final EventHandler eHandler = new EventHandler(this);
    final Config config = new Config(this);
    public final PathFinder pFinder = new PathFinder(this);
    public final EnvironmentManager eManager = new EnvironmentManager(this);
    final SaveLoad saveLoad = new SaveLoad(this);
    final Map map = new Map(this);
    public final EntityGenerator eGenerator = new EntityGenerator(this);
    public final CutsceneManager csManager = new CutsceneManager(this);
    Thread gameThread;

    //ENTITY AND OBJECT
    public final Player player = new Player(this,keyH);
    public final Entity[][] obj = new Entity[currentMap][150];
    public Entity[][] npc = new Entity[currentMap][10];
    public Entity[][] monster = new Entity[currentMap][30];
    public final InteractiveTile[][] iTile = new InteractiveTile[currentMap][100];
    public final Entity[][] projectile = new Entity[currentMap][20];
    public final ArrayList<Entity> particleList = new ArrayList<>();
    public final ArrayList<Entity> entityList = new ArrayList<>();
    public final ArrayList<Entity> tempEntityList = new ArrayList<>();

    //GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionsState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;
    public final int sleepState = 9;
    public final int mapState = 10;
    public final int quizState = 11;
    public final int cutSceneState = 12;

    public int quizSubState;
    public final int mumsChair = 0;
    public final int dadQuiz = 1;

    public GamePanel() throws IOException, FontFormatException { //constructor
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setNPC();
        aSetter.setObject();
        eManager.setup();

//      //DEBUG TO SKIP AHEAD IN MISSIONS - COMMENT FOR NORMAL GAME FROM START
//      //CHANGE VALUES AND REMEMBER TO ADD PREVIOUS MISSIONS TO MISSION-LIST IF STARTING FURTHER ON

//      //END OF DEBUG

        //playMusic(0, false);
        if (!musicSetToPlayFromStart) {
            keyH.musicPlaying = false;
            stopMusic();
        }
        gameState = titleState;
        //FULL SCREEN STUFF
        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB); //blank image as big as screen
        g2 = (Graphics2D)tempScreen.getGraphics(); //attach g2 draw to this new blank image 'canvas'
        if (fullScreenOn) {
            setFullScreen();
        }
    }

    public void resetGame(boolean restart) {
        if (!keyH.musicPlaying) {
            playMusic(0, false);
        }
        player.resetCounter();
        player.setDefaultPositions();
        player.restoreStatus();
        aSetter.setNPC();

        if (restart) {
            //player.setDefaultValues();
            aSetter.setObject();
            eManager.lighting.resetDay();
            keyH.musicPlaying = true;
        }
    }

    public void setFullScreen() {
        //GET LOCAL SCREEN DEVICE
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        gd.setFullScreenWindow(main.Main.window);

        //GET FULL SCREEN WIDTH AND HEIGHT
        screenWidth2 = main.Main.window.getWidth();
        screenHeight2 = main.Main.window.getHeight();
    }

    public void startGameThread() {
        gameThread = new Thread(this); //instantiate gameThread
        gameThread.start();
    }

    @Override
    public void run() { //update screen, redraw it according to the FPS set

        @SuppressWarnings("IntegerDivisionInFloatingPointContext") double drawInterval = 1000000000 / FPS; // 0.016667 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            //1 UPDATE screen
            update();
            //2 DRAW screen
//            repaint(); //this is for old draw implementation prior to full screen functionality
            drawToTempScreen(); // draw to buffered image canvas
            drawToScreen(); // draw to JPanel
            drawCount++;

            if (timer >= 1000000000) { //output FPS in console
                System.out.println("FPS: " + drawCount);

                drawCount = 0;
                timer = 0;
            }

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000; //convert nano to milli

                if (remainingTime < 0) { //if no sleep required due to taking longer than permitted time to redraw, then handle this
                    remainingTime = 0;
                }

                //noinspection BusyWait
                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void update() {
        //MUSIC
//        if (music.length <= music.clip.getMicrosecondPosition() && gameState != pauseState && keyH.musicPlaying) {
//            playMusic(0, false);
//        }
        if (gameState == playState) {
            //PLAYER
            player.update();
            //NPC
//            for (int i = 0; i < npc[currentMap].length; i++) {
//                if (npc[currentMap][i] != null) {
//                    npc[currentMap][i].update();
//                }
//            }
            //MONSTER
//            for (int i = 0; i < monster[currentMap].length; i++) {
//                if (monster[currentMap][i] != null) {
//                    if (monster[currentMap][i].alive && !monster[currentMap][i].dying) {
//                        monster[currentMap][i].update();
//                    }
//                    if (!monster[currentMap][i].alive) {
//                        monster[currentMap][i].checkDrop();
//                        monster[currentMap][i] = null;
//                    }
//                }
//            }
            //PROJECTILE
//            for (int i = 0; i < projectile[currentMap].length; i++) {
//                if (projectile[currentMap][i] != null) {
//                    if (projectile[currentMap][i].alive) {
//                        projectile[currentMap][i].update();
//                    }
//                    if (!projectile[currentMap][i].alive) {
//                        projectile[currentMap][i] = null;
//                    }
//                }
//            }
            //PARTICLE
//            for (int i = 0; i < particleList.size(); i++) {
//                if (particleList.get(i) != null) {
//                    if (particleList.get(i).alive) {
//                        particleList.get(i).update();
//                    }
//                    if (!particleList.get(i).alive) {
//                        particleList.remove(i);
//                        break;
//                    }
//                }
//            }
            //INTERACTIVE TILE
//            for (int i = 0; i < iTile[currentMap].length; i++) {
//                if (iTile[currentMap][i] != null) {
//                    iTile[currentMap][i].update();
//                }
//            }

            //OBJECT
//            for (int i = 0; i < obj[currentMap].length; i++) {
//                if (obj[currentMap][i] != null && obj[currentMap][i].isUpdateable) {
//                    obj[currentMap][i].update();
//                }
//            }
        }
        if (!player.exitingFromCutScene) {
            //eManager.update();
        }
    }

    public void drawToTempScreen() { //FULL SCREEN STUFF
        //DEBUG TEXT CODE
        long drawStart = 0;
        if (keyH.showDebugText) {
            drawStart = System.nanoTime();
        }
        //END OF DEBUG DISPLAY CODE

        //TITLE SCREEN
        if (gameState == titleState) {
            ui.draw(g2);
        }

        // MAP SCREEN
        else if (gameState == mapState) {
            map.drawFullMapScreen(g2);
        }
        //OTHERS
        else {
            //TILE
            tileM.draw(g2); //bottom layer first

            //INTERACTIVE TILE
            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].draw(g2);
                }
            }

            //OBJECTS
            for (int i = 0; i < obj[currentMap].length; i++) {
                if (obj[currentMap][i] != null) {
                    if (!obj[currentMap][i].drawAbovePlayer) {
                        obj[currentMap][i].draw(g2);
                    }
                }
            }

            //ADD ENTITIES TO THE LIST
            //PLAYER
            entityList.add(player);
            player.playerDrawnThisCycle = true; // set variable to draw tiles above player

            if (!tempEntityList.contains(player)) {
                tempEntityList.add(player);
            }
            //MONSTER
            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    entityList.add(monster[currentMap][i]);
                }
            }
            //NPC
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    entityList.add(npc[currentMap][i]);
                    if (!tempEntityList.contains(npc[currentMap][i])) {
                        tempEntityList.add(entityList.get(i));
                    }
                }
            }

            //PROJECTILE
            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currentMap][i] != null) {
                    entityList.add(projectile[currentMap][i]);
                }
            }
            //PARTICLE
            for (Entity entity : particleList) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }


            //SORT
            entityList.sort(Comparator.comparingInt(e -> e.worldY));

            //DRAW ENTITIES
            for (Entity entity : entityList) {
                entity.draw(g2);
            }

            for (int i = 0; i < obj[1].length; i++) { //draw objects above player i.e. tree top of garden
                if (obj[currentMap][i] != null && obj[currentMap][i].drawAbovePlayer) {
                    obj[currentMap][i].draw(g2);
                }
            }

            tileM.draw(g2); // draw tiles that need to be above player

            //EMPTY ENTITY LIST
            entityList.clear();

            //ENVIRONMENT
            eManager.draw(g2);

            //MINI MAP
            map.drawMiniMap(g2);

            //CUTSCENE
            csManager.draw(g2);

            //UI
            ui.draw(g2);
        }

        //DEBUG CODE
        if (keyH.showDebugText) {
            tileM.drawPathFinderTrack = true; // switches on pathfinder track
            for (Entity entity : tempEntityList) {
                drawCollisionBoxOnEntity(g2, entity);
            }
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.WHITE);
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 42F));
            int x = 10;
            int y = 400;
            int lineHeight = 35;

            //DRAW WORLD POSITION DATA AND DRAW SPEED
            g2.drawString("WorldX: " + player.worldX, x, y); y += lineHeight;
            g2.drawString("WorldY: " + player.worldY, x, y); y += lineHeight;
            g2.drawString("Col: " + (player.worldX + player.solidArea.x)/tileSize, x, y); y += lineHeight;
            g2.drawString("Row: " + (player.worldY + player.solidArea.y)/tileSize, x, y); y += lineHeight;
            g2.drawString("Draw Time: " + passed, x, y); y += lineHeight;
            g2.drawString("Current Map: " + currentMap, x, y);
            //END OF DEBUG DISPLAY CODE
        } else {
            tileM.drawPathFinderTrack = false;
        }
    }

    public void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }

    public void playMusic(long position, boolean pausing) {
        int file;
        int ran = new Random().nextInt(100);
        if (ran < 33 && music.lastSong != 0) {
            file = 0;
        } else if (ran < 66  && music.lastSong != 34) {
            file = 34;
        } else {
            if(music.lastSong != 35) {
                file = 35;
            } else {
                file = 0;
            }

        }
        music.lastSong = file;
        music.setFile(file);
        music.length = music.clip.getMicrosecondLength();
        music.play(position, pausing);
    }

    public void stopMusic() {
        music.stop(false, 0);
    }

    public long pauseMusic() {
        return music.stop(true, 0);
    }

    public void playSFX(int i) {
        sfx.setFile(i);
        sfx.play(0, false);
    }

    public void loopSFX(int i) {
        if (i == 28) {
            sfxPhone.setFile(i);
            sfxPhone.loop(i);
        } else {
            sfx.setFile(i);
            sfx.loop(i);
        }
    }

    public void stopSFX(int phone) {
        if (phone == 28) {
            sfxPhone.stop(false, 28);
        } else {
            sfx.stop(false, 0);
        }

    }

    public void clearMonsters() {
        monster = new Entity[currentMap][20];
    }

    //DEBUG
    public void drawCollisionBoxOnEntity(Graphics2D g2, Entity entity) {
        int screenX = player.screenX;
        int screenY = player.screenY;
        if (entity.type == entity.type_player) {
            g2.drawRect(screenX + player.solidArea.x, screenY + player.solidArea.y, player.solidArea.width, player.solidArea.height); // - UNCOMMENT TO DISPLAY COLLISION RECTANGLE ON PLAYER
        } else if (entity.type == entity.type_npc) {
            g2.drawRect(entity.worldX + entity.solidArea.x, entity.worldY + entity.solidArea.y, entity.solidArea.width, entity.solidArea.height); // - UNCOMMENT TO DISPLAY COLLISION RECTANGLE ON PLAYER
        }
    }
    //END OF DEBUG
}
