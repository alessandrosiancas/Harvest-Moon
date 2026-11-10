package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class MapManager {

    GamePanel gp;
    public BufferedImage[] mapImages;
    public int screenX, screenY;

    public MapManager(GamePanel gp) {
        this.gp = gp;

        // LOADING ALL MAPS
        mapImages = new BufferedImage[gp.maxMap];
        mapImages[0] = loadMapImage("/map/farm_spring.png",
                gp.worldCol[0] * gp.tileSize, gp.worldRow[0] * gp.tileSize, 0);
        mapImages[1] = loadMapImage("/map/village_spring.png",
                gp.worldCol[1] * gp.tileSize, gp.worldRow[1] * gp.tileSize, 1);
        mapImages[2] = loadMapImage("/map/jack_house.png",
                gp.worldCol[2] * gp.tileSize, gp.worldRow[2] * gp.tileSize, 2);
        mapImages[3] = loadMapImage("/map/chicken_coop.png",
                gp.worldCol[3] * gp.tileSize, gp.worldRow[3] * gp.tileSize, 3);
        mapImages[4] = loadMapImage("/map/church.png",
                gp.worldCol[4] * gp.tileSize, gp.worldRow[4] * gp.tileSize, 4);
        mapImages[5] = loadMapImage("/map/shop.png",
                gp.worldCol[5] * gp.tileSize, gp.worldRow[5] * gp.tileSize, 5);

        setDefaultPosition();
    }

    public BufferedImage loadMapImage(String path, int width, int height, int mapID) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage img = null;
        try {
            img = ImageIO.read(getClass().getResourceAsStream(path));
            img = uTool.scaleImage(img, width, height);
            return img;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setDefaultPosition() {

        screenX = gp.player.screenX - gp.player.gridX;
        screenY = gp.player.screenY - gp.player.gridY;

    }

    public void update() {

        // Default values
        screenX = gp.player.screenX - gp.player.gridX;
        screenY = gp.player.screenY - gp.player.gridY;

        if (!gp.player.collisionOn && gp.player.spriteNum != 2 && !gp.player.doing) {
            // Sopra o Sotto
            if (gp.player.clampUp || gp.player.clampDown) {
                switch (gp.player.direction) {
                    case "up":
                        screenY -= 4;
                        break;
                    case "down":
                        screenY += 4;
                        break;
                }
            }

            // Sinistra o Destra
            if (gp.player.clampLeft || gp.player.clampRight) {
                switch (gp.player.direction) {
                    case "left":
                        screenX -= 4;
                        break;
                    case "right":
                        screenX += 4;
                        break;

                }
            }
        }

    }

    public void draw(Graphics2D g2d) {
        // Calcola la porzione visibile della mappa
        int sx1 = -screenX; // punto di partenza sulla mappa
        int sy1 = -screenY;
        int sx2 = sx1 + gp.screenWidth; // punto finale sulla mappa
        int sy2 = sy1 + gp.screenHeight;

        // Coord sullo schermo: disegna sempre da (0,0) allo schermo
        int dx1 = 0;
        int dy1 = 0;
        int dx2 = gp.screenWidth;
        int dy2 = gp.screenHeight;

        // Disegna solo il pezzo visibile della mappa
        g2d.drawImage(
                mapImages[gp.currentMap],
                dx1, dy1, dx2, dy2,
                sx1, sy1, sx2, sy2,
                null);
    }
}
