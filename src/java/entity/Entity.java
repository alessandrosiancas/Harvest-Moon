package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {

    GamePanel gp;

    // PLAYER POSITION AND SPEED
    public int gridX, gridY;
    public int speed;

    // ENTITY SPRITES
    public BufferedImage up1, up2, up3,
            down1, down2, down3,
            left1, left2, left3,
            right1, right2, right3;
    public String direction = "down";

    // ENTITY ANIMATION
    public int spriteCounter = 0;
    public int spriteNum = 2;
    public int maxSpriteNum = 3;

    // DEFAULT TILE HITBOX
    public Rectangle hitbox = new Rectangle(0, 0, 48, 48);
    public int hitboxDefaultX, hitboxDefaultY;

    // ATTACK AREA
    public Rectangle hitArea = new Rectangle(0, 0, 0, 0);

    // COLLECTIBLE
    public boolean isCollectible = false;

    // PLOT PARAMETERS
    public boolean isPlot = false;
    public boolean watered;
    public String seedType;
    public int growthStage;
    public int maxGrowStages;

    // OBJECTS LIFE
    public int life = 1;

    // ENTITY DEFAULT COLLISION SETTING
    public boolean collisionOn = false;

    // NPC AI ON MOVEMENT
    public int actionLockCounter = 0;

    // DIALOGUES
    public String dialogues[] = new String[20];
    public int dialogueIndex = 0;

    // INVENTORY
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;

    // OBJECTS ATTRIBUTES
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public boolean isStackable = false;
    public int amount = 0;
    public String description = "";
    public boolean canSell = false;
    public int price;
    public boolean dropped = false;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() {
    }

    public void dropItem(Entity droppedItem, int map) {
        for (int i = 0; i < gp.obj[map].length; i++) {
            if (gp.obj[map][i] == null) {
                gp.obj[map][i] = droppedItem;
                gp.obj[map][i].gridX = gridX;
                gp.obj[map][i].gridY = gridY + gp.tileSize;
                dropped = true;
                break;
            }
        }
    }

    public void setCharacterHitbox() {

        // SETTING A DIFFERENT HITBOX FOR CHARACTERS
        hitbox.x = 9;
        hitbox.y = 36;
        hitboxDefaultX = hitbox.x;
        hitboxDefaultY = hitbox.y;
        hitbox.width = 30;
        hitbox.height = 33;
    }

    public void use(Player player) {

    }

    public void grow() {

    }

    public void speak() {
        if (dialogues[dialogueIndex] != null) {
            gp.ui.currentDialogue = dialogues[dialogueIndex];
            dialogueIndex++;
        }

        switch (gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }

    public void update() {

        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkPlayer(this);

        // NPC MOVEMENT
        if (collisionOn == false) {

            switch (direction) {
                case "up":
                    gridY -= speed;
                    break;
                case "down":
                    gridY += speed;
                    break;
                case "left":
                    gridX -= speed;
                    break;
                case "right":
                    gridX += speed;
                    break;
            }
        }

        // NPC ANIMATION
        spriteCounter++;
        if (spriteCounter > 8) {
            spriteNum++;
            if (maxSpriteNum == 2) {
                if (spriteNum > 2)
                    spriteNum = 1;
            } else {
                if (spriteNum > 3)
                    spriteNum = 1;
            }

            spriteCounter = 0;
        }
    }

    // ENTITY DRAWING
    public void draw(Graphics2D g2d) {

        BufferedImage image = null;

        int screenX = gridX - gp.player.gridX + gp.player.screenX;
        int screenY = gridY - gp.player.gridY + gp.player.screenY;

        // Limiti per il draw a schermo
        boolean upLimit = gridY + down2.getHeight() > gp.player.gridY - gp.player.screenY;
        boolean downLimit = gridY < gp.player.gridY + gp.screenHeight - gp.player.screenY;
        boolean leftLimit = gridX + down2.getWidth() > gp.player.gridX - gp.player.screenX;
        boolean rightLimit = gridX < gp.player.gridX + gp.screenWidth - gp.player.screenX;

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

        if (upLimit && downLimit && leftLimit && rightLimit) {

            switch (direction) {
                case "up":
                    if (spriteNum == 1)
                        image = up1;
                    if (spriteNum == 2)
                        image = up2;
                    if (maxSpriteNum == 3 && spriteNum == 3)
                        image = up3;
                    break;
                case "down":
                    if (spriteNum == 1)
                        image = down1;
                    if (spriteNum == 2)
                        image = down2;
                    if (maxSpriteNum == 3 && spriteNum == 3)
                        image = down3;
                    break;
                case "left":
                    if (spriteNum == 1)
                        image = left1;
                    if (spriteNum == 2)
                        image = left2;
                    if (maxSpriteNum == 3 && spriteNum == 3)
                        image = left3;
                    break;
                case "right":
                    if (spriteNum == 1)
                        image = right1;
                    if (spriteNum == 2)
                        image = right2;
                    if (maxSpriteNum == 3 && spriteNum == 3)
                        image = right3;
                    break;
            }

            g2d.drawImage(image, screenX, screenY, null);
        }

    }

    public BufferedImage setup(String imagePath) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {

            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            int width = image.getWidth() * gp.scale;
            int height = image.getHeight() * gp.scale;
            image = uTool.scaleImage(image, width, height);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return image;
    }

    public Entity copy() {
        return null;
    }
}