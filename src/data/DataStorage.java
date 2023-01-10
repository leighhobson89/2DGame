package data;

import entity.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataStorage implements Serializable {

    //PLAYER STATS
    int level;
    int maxStress;
    int stressLevel;
    int maxMana;
    int mana;
    int strength;
    int dexterity;
    int exp;
    int nextLevelExp;
    int coin;
    String colorOutfit;
    String outfitChosen;
    int weedCount;
    int timesPassedOut;
    boolean pillsConsumableNow;
    int boneIndex;
    int chickenIndex;
    boolean musicPlaying;
    boolean savedWithAWeaponEquipped;
    boolean savedWithAnArmourEquipped;
    boolean savedWithBoneEquipped;
    boolean savedWithChoppedChickenEquipped;
    int spiderCount;
    int missionState;
    int missionSubState;
    public List<Integer> missionList = new ArrayList<>();
    //public List<Entity> npcList = new ArrayList<Entity>();
    boolean readyForNextPhoneMission;
    int missionToSet;
    int playerWorldX;
    int playerWorldY;
    int currentMap;
    int otherMap;
    boolean setShovelFlag;
    boolean repeatSfx;
    int dialogueSet;
    int choppedChickenCount;
    int thrownChickenCount = 0;
    Entity currentProjectile;
    String itemToThrow;
    boolean startCounterPipEatingChicken;
    boolean startCounterPhoebeEatingChicken;
    int pipEatingChickenCounter;
    int phoebeEatingChickenCounter;
    boolean pipChickenEaten;
    boolean phoebeChickenEaten;
    boolean phoneRinging;
    boolean nextMissionIsPhoneMission;
    boolean showerAlreadyRan;
    boolean showerCounterStart;
    int showerCounter;
    int waterTileCount;
    int backGateState;
    int blockWoodState;
    int bookHutState;
    int toolHutState;
    int quizScoreCount;
    boolean stainRemoverUsed;
    boolean toolHutKeyDropped;
    boolean bucketFull;
    int waspNestState;
    boolean pillsInProcess;
    int pillsCounter;
    boolean lightPillsInProcess;
    int lightPillsCounter;
    int carCountDown;
    int carCountUp;
    boolean npcCanWalkOnWhenFollowing;
    boolean inLivingRoom;
    boolean dadHasGuitar;
    boolean dadPlayingGuitar;
    boolean musicCentreOn;
    int dadOption;
    boolean andreaSafe;
    boolean hasOutsideDoorsKey;
    boolean insideHouse;

    //PLAYER INVENTORY
    final ArrayList<String> itemNames = new ArrayList<>();
    final ArrayList<Integer> itemAmounts = new ArrayList<>();
    int currentWeaponSlot;
    int currentArmourSlot;
    int currentProjectileSlot;

    //OBJECTS ON MAP
    String[][] mapObjectNames;
    int[][] mapObjectWorldX;
    int[][] mapObjectWorldY;
    String[][] mapObjectLootNames; //Only needed if loot functionality implemented
    boolean[][] mapObjectOpened;

    //MONSTERS ON MAP
    String[][] mapMonsterNames;
    int[][] mapMonsterWorldX;
    int[][] mapMonsterWorldY;
    int[][] mapMonsterHealth;
    String[][] mapMonsterDirection;

    //INTERACTIVE TILES STATUS ON MAP
    String [][] mapITileNames;
    int[][] mapITileWorldX;
    int[][] mapITileWorldY;

    //NPCS ON MAP
    String[][] mapNpcNames;
    int[][] mapNpcWorldX;
    int[][] mapNpcWorldY;
    String[][] mapNpcDirection;
    boolean[][] mapNpcOffMap = new boolean[10][100];
    boolean[][] mapNpcWithinView = new boolean[10][100];
    boolean[][] mapNpcFollowingPlayer = new boolean[10][100];
    boolean[][] mapNpcInHouse = new boolean[10][100];
    boolean[][] mapMonsterInHouse = new boolean[10][100];
}
