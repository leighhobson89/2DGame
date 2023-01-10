package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {

    final GamePanel gp;
    public final Tile[] tile;
    public final int[][][] mapTileNum;
    public boolean drawPathFinderTrack;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[200];
        mapTileNum = new int[gp.currentMap][gp.maxWorldCol][gp.maxWorldRow];

        //getTileImage();
        //loadMap("/maps/world01.txt", 0);
    }

    public void getTileImage() {

        setup(0, "lawn1", false, false);
        setup(1, "brick1", true, false);
        setup(2, "carpetLounge", false, false);
        setup(3, "carpetStairsLanding", false, false);
        setup(4, "carpetDining", false, false);
        setup(5, "carpetKitchen", false, false);
        setup(6, "internalWall1", true, false);
        setup(7, "lawn2", false, false);
        setup(8, "brick2", true, false);
        setup(9, "carpetBathroom", false, false);
        setup(10, "carpetMumsRoom", false, false);
        setup(11, "carpetSpareRoom", false, false);
        setup(12, "driveWayStones", false, false);
        setup(13, "garageBlock", true, false);
        setup(14, "garageFloor", false, false);
        setup(15, "pathHorizontal", false, false);
        setup(16, "pathVertical", false, false);
        setup(17, "rockery", true, false);
        setup(18, "hedge", true, false);
        setup(19, "fence1", true, false);
        setup(22, "sofaTop", false, false);
        setup(23, "sofaBottom", true, false);
        setup(24, "tableTL", true, false);
        setup(25, "tableTR", true, false);
        setup(26, "tableBL", false, false);
        setup(27, "tableBR", true, false);
        setup(28, "tableLivingRoomLeft", true, false);
        setup(29, "tableLivingRoomRight", true, false);
        setup(30, "cupboard1HorLounge", true, false);
        setup(31, "cupboard1VerLounge", true, false);
        setup(32, "cupboard1HorHall", true, false);
        setup(33, "tvLounge", true, false);
        setup(34, "cupboard2HorLounge", true, false);
        setup(35, "roadKerb", false, false);
        setup(36, "road", false, false);
        setup(37, "tree1Tile", true, false);
        setup(38, "tree2Tile", true, false);
        setup(39, "rockery2", true, false);
        setup(40, "rockery3", true, false);
        setup(41, "fireplaceTop", true, false);
        setup(42, "fireplaceBottom", true, false);
        setup(43, "roadKerb2", false, false);
        setup(44, "fishTankL", true, false);
        setup(45, "fishTankR", true, false);
        setup(46, "kitCup1", true, false);
        setup(47, "kitCup2", true, false);
        setup(48, "kitCup3", true, false);
        setup(49, "kitCup4", true, false);
        setup(50, "kitCup5", true, false);
        setup(51, "kitCup6", true, false);
        setup(52, "kitCup7", true, false);
        setup(53, "kitCup8", true, false);
        setup(54, "mumsChairTile", false, false);
        setup(55, "rockery4", true, false);
        setup(56, "rockery5", true, false);
        setup(57, "rockery6", true, false);
        setup(58, "rockery2", false, false);
        setup(59, "rockery3", false, false);
        setup(60, "StairsLanding", false, false);
        setup(61, "carpetBannister", false, false);
        setup(62, "StairsDownstairs", false, false);
        setup(63, "hallLoungeBorder", false, false);
        setup(64, "KitchenLoungeBorder", false, false);
        setup(65, "hallKitchenBorder", false, false);
        setup(66, "path1", false, false);
        setup(67, "pathRockeryTopAndBottom", false, false);
        setup(68, "pathRockeryTopAndLeft", false, false);
        setup(69, "pathRockeryTopBorder", false, false);
        setup(70, "pathRockeryTopAndRight", false, false);
        setup(71, "pathRockeryLeft", false, false);
        setup(72, "lawnRockeryRight", false, false);
        setup(73, "lawnRockeryLeft", false, false);
        setup(74, "lawnRockeryTopRight", false, false);
        setup(75, "lawnRockeryTopLeft", false, false);
        setup(76, "pathRockeryBottomBorder", false, false);
        setup(77, "frontGateOnPath", true, false);
        setup(78, "frontGateOnHedge", true, false);
        setup(79, "hedgeHorizontal", true, false);
        setup(80, "hedgeCorner", true, false);
        setup(81, "lawnPlain", false, false);
        setup(82, "lawnLeaves1", false, false);
        setup(83, "lawnLeaves2", false, false);
        setup(84, "lawnLeaves3", false, false);
        setup(85, "lawnLeaves4", false, false);
        setup(86, "rockeryShrub", true, false);
        setup(87, "pathLawnTop", false, false);
        setup(88, "lawnMiddleLeaves1", false, false);
        setup(89, "lawnMiddleLeaves2", false, false);
        setup(90, "pathLawnBottom", false, false);
        setup(91, "pathLawnRight", false, false);
        setup(92, "fence2", true, false);
        setup(93, "fenceVertical", true, false);
        setup(94, "bathroomFloor", false, false);
        setup(95, "fenceVertical2", true, false);
        setup(96, "bookHutBackLeft", true, false);
        setup(97, "bookHutBackCentre", true, false);
        setup(98, "bookHutBackRight", true, false);
        setup(99, "bookhut1_left", false, false);
        setup(100, "bookhut1_right", false, false);
        setup(101, "bookhut2_left", false, false);
        setup(102, "bookhut2_right", false, false);
        setup(103, "bookhut3_left", false, false);
        setup(104, "bookhut3_right", false, false);
        setup(105, "path2", false, false);
        setup(106, "toolHutBackLeft", true, false);
        setup(107, "toolHutBackCentre", true, false);
        setup(108, "toolHutBackRight", true, false);
        setup(109, "carpetBaldy1", false, false);
        setup(110, "carpetBaldy2", false, false);
        setup(111, "stairsLandingBathroomBorder", false, false);
        setup(112, "spareRoomLandingBorder", false, false);
        setup(113, "landingLeighBedroomBorder", false, false);
        setup(114, "landingDadBedroomBorder", false, false);
        setup(115, "carpetStairsLandingMat", false, false);
        setup(116, "newBrickBottomLeft", true, false);
        setup(117, "newBrickBottomRight", true, false);
        setup(118, "newBrickTopLeft", true, false);
        setup(119, "newBrickTopRight", true, false);
        setup(120, "newBrickHorizontal", true, false);
        setup(121, "newBrickVertical", true, false);
        setup(122, "newInternalCornerTopLeft", true, false);
        setup(123, "newInternalCornerTopRight", true, false);
        setup(124, "newInternalCornerBottomLeft", true, false);
        setup(125, "newInternalCornerBottomRight", true, false);
        setup(126, "newInternalBottomEnd", true, false);
        setup(127, "newInternalTopEnd", true, false);
        setup(128, "newInternalLeftEnd", true, false);
        setup(129, "newInternalRightEnd", true, false);
        setup(130, "newInternalMiddleHorizontal", true, false);
        setup(131, "newInternalMiddleVertical", true, false);
        setup(132, "newInternalLeftEndShadow", true, false);
        setup(133, "newInternalRightEndShadow", true, false);
        setup(134, "newInternalTTop", true, false);
        setup(135, "newInternalTBottom", true, false);
        setup(136, "newInternalShortHorizontal", true, false);
        setup(137, "newInternalRightEnd2", true, false);
        setup(138, "workTop1", true, false);
        setup(139, "workTop2", true, false);
        setup(140, "workTop3", true, false);
        setup(141, "workTop4", true, false);
        setup(142, "workTop5", true, false);
        setup(143, "workTop6", true, false);
        setup(144, "fridgeTop", true, false);
        setup(145, "cookerTop", true, false);
        setup(146, "diningCupboardLeft", true, false);
        setup(147, "diningCupboardRight", true, false);
        setup(148, "diningCupboardMiddle", true, false);
        setup(149, "garageBigDoor", true, false);
    }

    public void setup(int index, String imageName, boolean collision, boolean drawAbovePlayer) {

        UtilityTool uTool = new UtilityTool();

        try {

            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + imageName + ".png")));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
            tile[index].drawAbovePlayer = drawAbovePlayer;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath, int map) {
        try {
            InputStream is = getClass().getResourceAsStream((filePath));
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

                String line = br.readLine();

                while (col < gp.maxWorldCol) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[map][col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) { //draw tiles
        int worldCol = 0;
        int worldRow = 0;


        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (!tile[tileNum].drawAbovePlayer && !gp.player.playerDrawnThisCycle) { //under player
                if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
                        && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                        && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                        && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                    g2.drawImage(tile[tileNum].image, screenX, screenY, null);
                }
            } else if (tile[tileNum].drawAbovePlayer && gp.player.playerDrawnThisCycle) { //above player
                if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
                        && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                        && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                        && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                    g2.drawImage(tile[tileNum].image, screenX, screenY, null);
                }
            }

            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
        gp.player.playerDrawnThisCycle = false;
        //DEBUG RELATED CODE
        if (drawPathFinderTrack) { //draw path if debug is switched on
            g2.setColor(new Color(255,0,0,70));

            for (int i = 0; i <gp.pFinder.pathList.size(); i++) {

                int worldX = gp.pFinder.pathList.get(i).col * gp.tileSize;
                int worldY = gp.pFinder.pathList.get(i).row * gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
            }
        }
        //END OF DEBUG RELATED CODE
    }
}
