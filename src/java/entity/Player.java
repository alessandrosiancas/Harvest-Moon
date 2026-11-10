package entity;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import main.KeyHandler;
import object.*;
import main.GamePanel;

public class Player extends Entity {

    public KeyHandler keyH;

    // POSITION ON SCREEN
    public int screenX;
    public int screenY;
    public final int defaultScreenX;
    public final int defaultScreenY;

    // CLAMPING ON SCREEN
    public boolean clampUp, clampDown, clampLeft, clampRight;

    // SPAWN VALUES
    public final int spawnX = 8;
    public final int spawnY = 21;

    // TARGET TILE
    public Point targetTile = null;

    // PLAYER ACTIONS SPRITES
    public BufferedImage pickupUp1, pickupUp2, pickupUp3,
            pickupDown1, pickupDown2, pickupDown3,
            pickupLeft1, pickupLeft2, pickupLeft3,
            pickupRight1, pickupRight2, pickupRight3;
    public BufferedImage holdUp1, holdUp2, holdUp3,
            holdDown1, holdDown2, holdDown3,
            holdLeft1, holdLeft2, holdLeft3,
            holdRight1, holdRight2, holdRight3;
    public BufferedImage axeUp1, axeUp2, axeUp3,
            axeDown1, axeDown2, axeDown3,
            axeLeft1, axeLeft2, axeLeft3,
            axeRight1, axeRight2, axeRight3;
    public BufferedImage scytheUp1, scytheUp2, scytheUp3,
            scytheDown1, scytheDown2, scytheDown3,
            scytheLeft1, scytheLeft2, scytheLeft3,
            scytheRight1, scytheRight2, scytheRight3;
    public BufferedImage hoeUp1, hoeUp2, hoeUp3,
            hoeDown1, hoeDown2, hoeDown3,
            hoeLeft1, hoeLeft2, hoeLeft3,
            hoeRight1, hoeRight2, hoeRight3;
    public BufferedImage hammerUp1, hammerUp2, hammerUp3, hammerUp4,
            hammerDown1, hammerDown2, hammerDown3,
            hammerLeft1, hammerLeft2, hammerLeft3,
            hammerRight1, hammerRight2, hammerRight3;
    public BufferedImage wateringcanUp1, wateringcanUp2,
            wateringcanDown1, wateringcanDown2,
            wateringcanLeft1, wateringcanLeft2,
            wateringcanRight1, wateringcanRight2;
    public BufferedImage sowUp1, sowUp2, sowUp3,
            sowDown1, sowDown2, sowDown3,
            sowLeft1, sowLeft2, sowLeft3,
            sowRight1, sowRight2, sowRight3;

    // PLAYER STATUS
    public int maxEnergy = 100;
    public int energy;
    public int gold = 250;

    // INVENTORY
    public int hotbarIndex = 0; // first slot
    public Entity currentItem;
    public BufferedImage objectInHand = null;

    // HOTBAR
    public final int hotbarSize = 5;
    public int hotbarStartIndex = 0; // first 5 slots
    public int hotbarSelected = 0; // selected slot

    // ACTIONS
    public boolean hitRegistered = false;
    public boolean doing = false;
    public boolean pickingup = false;
    public boolean holding = false;
    public boolean usingAxe = false;
    public boolean usingScythe = false;
    public boolean usingHoe = false;
    public boolean usingHammer = false;
    public boolean usingWateringCan = false;
    public boolean sowing = false;
    public boolean placing = false;

    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);

        this.keyH = keyH;

        // SETTING DEFAULT SCREEN POS VALUES
        defaultScreenX = gp.screenWidth / 2 - (gp.characterWidth / 2);
        defaultScreenY = gp.screenHeight / 2 - (gp.characterHeight / 2);

        // CENTERING PLAYER ON SCREEN
        screenX = defaultScreenX;
        screenY = defaultScreenY;

        setCharacterHitbox();
        setDefaultValues();

        hitArea.width = 36;
        hitArea.height = 36;

        getPlayerWalkImages(); // 3 sprites
        getPlayerPickupImages(); // 3 sprites
        getPlayerAxeImages(); // 3 sprites (4)
        getPlayerScytheImages(); // 3 sprites (4)
        getPlayerHoeImages(); // 3 sprites (4)
        getPlayerHammerImages(); // 3 sprites (4)
        getPlayerWateringCanImages(); // 2 sprites
        getPlayerSowingImages();

        setItems();
    }

    public void setDefaultValues() {
        // SPAWN
        gridX = gp.tileSize * spawnX;
        gridY = gp.tileSize * spawnY;

        // MOVEMENT
        speed = 4;
        direction = "down";

        // ENERGY
        energy = maxEnergy;

        // INVENTORY
        currentItem = new TOOL_Axe(gp);
    }

    // ------------------------------- //
    // METODI PER RICAVARE GLI SPRITES //
    // ------------------------------- //
    public void getPlayerWalkImages() {

        up1 = setup("/player/Jack_up1");
        up2 = setup("/player/Jack_up2");
        up3 = setup("/player/Jack_up3");
        down1 = setup("/player/Jack_down1");
        down2 = setup("/player/Jack_down2");
        down3 = setup("/player/Jack_down3");
        left1 = setup("/player/Jack_left1");
        left2 = setup("/player/Jack_left2");
        left3 = setup("/player/Jack_left3");
        right1 = setup("/player/Jack_right1");
        right2 = setup("/player/Jack_right2");
        right3 = setup("/player/Jack_right3");
    }

    public void getPlayerPickupImages() {
        pickupUp1 = setup("/player/Jack_pickup_up1");
        pickupUp2 = setup("/player/Jack_pickup_up2");
        pickupUp3 = setup("/player/Jack_pickup_up3");
        pickupDown1 = setup("/player/Jack_pickup_down1");
        pickupDown2 = setup("/player/Jack_pickup_down2");
        pickupDown3 = setup("/player/Jack_pickup_down3");
        pickupLeft1 = setup("/player/Jack_pickup_left1");
        pickupLeft2 = setup("/player/Jack_pickup_left2");
        pickupLeft3 = setup("/player/Jack_pickup_left3");
        pickupRight1 = setup("/player/Jack_pickup_right1");
        pickupRight2 = setup("/player/Jack_pickup_right2");
        pickupRight3 = setup("/player/Jack_pickup_right3");
    }

    public void getPlayerAxeImages() {
        axeUp1 = setup("/player/Jack_axe_up1");
        axeUp2 = setup("/player/Jack_axe_up2");
        axeUp3 = setup("/player/Jack_axe_up3");
        axeDown1 = setup("/player/Jack_axe_down1");
        axeDown2 = setup("/player/Jack_axe_down2");
        axeDown3 = setup("/player/Jack_axe_down3");
        axeLeft1 = setup("/player/Jack_axe_left1");
        axeLeft2 = setup("/player/Jack_axe_left2");
        axeLeft3 = setup("/player/Jack_axe_left3");
        axeRight1 = setup("/player/Jack_axe_right1");
        axeRight2 = setup("/player/Jack_axe_right2");
        axeRight3 = setup("/player/Jack_axe_right3");
    }

    public void getPlayerScytheImages() {
        scytheUp1 = setup("/player/Jack_scythe_up1");
        scytheUp2 = setup("/player/Jack_scythe_up2");
        scytheUp3 = setup("/player/Jack_scythe_up3");
        scytheDown1 = setup("/player/Jack_scythe_down1");
        scytheDown2 = setup("/player/Jack_scythe_down2");
        scytheDown3 = setup("/player/Jack_scythe_down3");
        scytheLeft1 = setup("/player/Jack_scythe_left1");
        scytheLeft2 = setup("/player/Jack_scythe_left2");
        scytheLeft3 = setup("/player/Jack_scythe_left3");
        scytheRight1 = setup("/player/Jack_scythe_right1");
        scytheRight2 = setup("/player/Jack_scythe_right2");
        scytheRight3 = setup("/player/Jack_scythe_right3");
    }

    public void getPlayerHoeImages() {
        hoeUp1 = setup("/player/Jack_hoe_up1");
        hoeUp2 = setup("/player/Jack_hoe_up2");
        hoeUp3 = setup("/player/Jack_hoe_up3");
        hoeDown1 = setup("/player/Jack_hoe_down1");
        hoeDown2 = setup("/player/Jack_hoe_down2");
        hoeDown3 = setup("/player/Jack_hoe_down3");
        hoeLeft1 = setup("/player/Jack_hoe_left1");
        hoeLeft2 = setup("/player/Jack_hoe_left2");
        hoeLeft3 = setup("/player/Jack_hoe_left3");
        hoeRight1 = setup("/player/Jack_hoe_right1");
        hoeRight2 = setup("/player/Jack_hoe_right2");
        hoeRight3 = setup("/player/Jack_hoe_right3");
    }

    public void getPlayerHammerImages() {
        hammerUp1 = setup("/player/Jack_hammer_up1");
        hammerUp2 = setup("/player/Jack_hammer_up2");
        hammerUp3 = setup("/player/Jack_hammer_up3");
        hammerDown1 = setup("/player/Jack_hammer_down1");
        hammerDown2 = setup("/player/Jack_hammer_down2");
        hammerDown3 = setup("/player/Jack_hammer_down3");
        hammerLeft1 = setup("/player/Jack_hammer_left1");
        hammerLeft2 = setup("/player/Jack_hammer_left2");
        hammerLeft3 = setup("/player/Jack_hammer_left3");
        hammerRight1 = setup("/player/Jack_hammer_right1");
        hammerRight2 = setup("/player/Jack_hammer_right2");
        hammerRight3 = setup("/player/Jack_hammer_right3");
    }

    public void getPlayerWateringCanImages() {
        wateringcanUp1 = setup("/player/Jack_wateringcan_up1");
        wateringcanUp2 = setup("/player/Jack_wateringcan_up2");
        wateringcanDown1 = setup("/player/Jack_wateringcan_down1");
        wateringcanDown2 = setup("/player/Jack_wateringcan_down2");
        wateringcanLeft1 = setup("/player/Jack_wateringcan_left1");
        wateringcanLeft2 = setup("/player/Jack_wateringcan_left2");
        wateringcanRight1 = setup("/player/Jack_wateringcan_right1");
        wateringcanRight2 = setup("/player/Jack_wateringcan_right2");

    }

    public void getPlayerSowingImages() {
        sowUp1 = setup("/player/Jack_sow_up1");
        sowUp2 = setup("/player/Jack_sow_up2");
        sowUp3 = setup("/player/Jack_sow_up3");
        sowDown1 = setup("/player/Jack_sow_down1");
        sowDown2 = setup("/player/Jack_sow_down2");
        sowDown3 = setup("/player/Jack_sow_down3");
        sowLeft1 = setup("/player/Jack_sow_left1");
        sowLeft2 = setup("/player/Jack_sow_left2");
        sowLeft3 = setup("/player/Jack_sow_left3");
        sowRight1 = setup("/player/Jack_sow_right1");
        sowRight2 = setup("/player/Jack_sow_right2");
        sowRight3 = setup("/player/Jack_sow_right3");
    }

    // ------------------------------- //

    public void setItems() {
        inventory.add(currentItem);
        inventory.add(new TOOL_Scythe(gp));
        inventory.add(new TOOL_Hoe(gp));
        inventory.add(new TOOL_WateringCan(gp));
        inventory.add(new TOOL_Hammer(gp));

        // > Semi per iniziare
        BAG_Seed turnipSeed = new BAG_Seed(gp, "turnip");
        turnipSeed.amount = 5;
        BAG_Seed potatoSeed = new BAG_Seed(gp, "potato");
        potatoSeed.amount = 5;
        inventory.add(turnipSeed);
        inventory.add(potatoSeed);

    }

    public void swapItems(int index1, int index2) {
        if (index1 < inventory.size() && index2 < inventory.size()) {
            Entity temp = inventory.get(index1);
            inventory.set(index1, inventory.get(index2));
            inventory.set(index2, temp);
        }
    }

    public void updateCurrentItem() {
        int index = hotbarStartIndex + hotbarSelected;
        if (index < inventory.size()) {
            currentItem = inventory.get(index);
        } else {
            currentItem = null;
        }
    }

    public void update() {

        // Controllo il disegno a schermo
        updateScreenPos();

        // Controllo se ci sono oggetti da rimuovere
        removeFromInventory();

        if (pickingup) {

            pickup();

        } else if (usingScythe) {

            scythe();

        } else if (usingHoe) {

            hoe();

        } else if (usingAxe) {

            axe();

        } else if (usingHammer) {

            hammer();

        } else if (usingWateringCan) {

            water();

        } else if (sowing) {

            sow();

        } else if (placing) {
            place();
        } else if (keyH.upPressed == true || keyH.downPressed == true ||
                keyH.leftPressed == true || keyH.rightPressed == true) {
            if (keyH.upPressed == true) {
                direction = "up";
            } else if (keyH.downPressed == true) {
                direction = "down";
            } else if (keyH.leftPressed == true) {
                direction = "left";
            } else if (keyH.rightPressed == true) {
                direction = "right";
            }

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            gp.cChecker.checkObject(this, true);

            // CHECK ENTITY COLLISION
            gp.cChecker.checkEntity(this, gp.npc);

            // CHECK EVENT
            gp.eHandler.checkEvent();

            // MOVEMENT ON GRID
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

            // ANIMATION
            spriteCounter++;
            if (spriteNum == 2)
                spriteNum = 1;
            if (spriteCounter > 13) {
                if (spriteNum == 1) {
                    spriteNum = 3;
                } else if (spriteNum == 3) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        } else
            // STAYING SPRITE
            spriteNum = 2;
    }

    public void updateScreenPos() {

        clampUp = gridY <= defaultScreenY;
        clampDown = gridY >= gp.worldHeight[gp.currentMap] - (screenY + gp.characterHeight);
        clampLeft = gridX <= defaultScreenX;
        clampRight = gridX >= gp.worldWidth[gp.currentMap] - (screenX + gp.characterWidth);

        screenX = defaultScreenX;
        screenY = defaultScreenY;

        // Sopra
        if (clampUp) {
            screenY = gridY;
        }
        // Sotto
        else if (clampDown) {
            screenY = gridY - (gp.worldHeight[gp.currentMap] - gp.screenHeight);
        }

        // Sinistra
        if (clampLeft) {
            screenX = gridX;
        }
        // Destra
        else if (clampRight) {
            screenX = gridX - (gp.worldWidth[gp.currentMap] - gp.screenWidth);
        }

    }

    public void pickup() {

        spriteCounter++;

        if (spriteCounter <= 12)
            spriteNum = 1;
        if (spriteCounter > 12 && spriteCounter <= 24)
            spriteNum = 2;
        if (spriteCounter > 24 && spriteCounter <= 39)
            spriteNum = 3;
        if (spriteCounter > 39) {
            spriteNum = 2;
            spriteCounter = 0;
            pickingup = false;
            doing = false;
            objectInHand = null;
        }
    }

    public void place() {
        Point tilePos = gp.cChecker.getTilePosition(this);
        int col = tilePos.x;
        int row = tilePos.y;
        String iteminHand = currentItem.name;

        if (gp.tileM.tileMap[gp.currentMap][col][row] == 2
                && iteminHand.equals("Fence")) {

            for (int i = 0; i < gp.obj[gp.currentMap].length; i++) {
                if (gp.obj[gp.currentMap][i] == null) {

                    Entity fenceToPlace = currentItem.copy();
                    fenceToPlace.gridX = col * gp.tileSize;
                    fenceToPlace.gridY = row * gp.tileSize;
                    fenceToPlace.amount = 1;

                    gp.obj[gp.currentMap][i] = fenceToPlace;
                    gp.tileM.tileMap[gp.currentMap][col][row] = 1;
                    currentItem.amount--;
                    removeFromInventory();
                    break;
                }
            }

        }

        placing = false;
        doing = false;
    }

    public void axe() {

        spriteCounter++;

        if (spriteCounter <= 10)
            spriteNum = 1;
        if (spriteCounter > 10 && spriteCounter <= 20) {
            spriteNum = 2;

            // Salvo posizione e hitbox
            int currentWorldX = gridX;
            int currentWorldY = gridY;
            int solidAreaWidth = hitbox.width;
            int solidAreaHeight = hitbox.height;

            // Sposto la posizione in relazione alla direzione
            switch (direction) {
                case "up":
                    gridY -= hitArea.height;
                    break;
                case "down":
                    gridY += hitArea.height;
                    break;
                case "left":
                    gridX -= hitArea.width;
                    break;
                case "right":
                    gridX += hitArea.width;
                    break;
            }

            // Il colpo è la hitbox
            hitbox.width = hitArea.width;
            hitbox.height = hitArea.height;

            if (!hitRegistered) {

                // Controllo l'oggetto colpito
                int objIndex = gp.cChecker.checkObject(this, true);

                if (objIndex != 999) {
                    if (gp.obj[gp.currentMap][objIndex] != null
                            && gp.obj[gp.currentMap][objIndex].name.equals("Tree")) {

                        // Diminuisco la vita dell'albero
                        gp.obj[gp.currentMap][objIndex].life--;

                        // Suono
                        gp.playSoundEffect(1);

                        // Se la vita scende a zero, rimuovo l'albero
                        if (gp.obj[gp.currentMap][objIndex].life <= 0) {
                            gp.obj[gp.currentMap][objIndex] = null;
                        }
                    }
                }
                hitRegistered = true;
            }

            // Resetto i parametri
            gridX = currentWorldX;
            gridY = currentWorldY;
            hitbox.width = solidAreaWidth;
            hitbox.height = solidAreaHeight;

        }
        if (spriteCounter > 20 && spriteCounter <= 30)
            spriteNum = 3;
        if (spriteCounter > 30) {
            spriteNum = 2;
            spriteCounter = 0;
            usingAxe = false;
            doing = false;
            hitRegistered = false;
        }
    }

    // INCOMPLETO
    public void scythe() {

        spriteCounter++;

        if (spriteCounter <= 10)
            spriteNum = 1;
        if (spriteCounter > 10 && spriteCounter <= 20) {
            spriteNum = 2;

            // Salvo posizione e hitbox
            int currentWorldX = gridX;
            int currentWorldY = gridY;
            int solidAreaWidth = hitbox.width;
            int solidAreaHeight = hitbox.height;

            // Sposto la posizione in relazione alla direzione
            switch (direction) {
                case "up":
                    gridY -= hitArea.height;
                    break;
                case "down":
                    gridY += hitArea.height;
                    break;
                case "left":
                    gridX -= hitArea.width;
                    break;
                case "right":
                    gridX += hitArea.width;
                    break;
            }

            // Il colpo è la hitbox
            hitbox.width = hitArea.width;
            hitbox.height = hitArea.height;

            if (!hitRegistered) {

                // Controllo l'oggetto colpito
                int objIndex = gp.cChecker.checkObject(this, true);

                if (objIndex != 999) {
                    if (gp.obj[gp.currentMap][objIndex] != null
                            && gp.obj[gp.currentMap][objIndex].name.equals("Grass")) {

                        // Diminuisco la vita dell'albero
                        gp.obj[gp.currentMap][objIndex].life--;

                        // Se la vita scende a zero, rimuovo l'albero
                        if (gp.obj[gp.currentMap][objIndex].life <= 0) {
                            gp.obj[gp.currentMap][objIndex] = null;
                        }
                    }
                }
                hitRegistered = true;
            }

            // Resetto i parametri
            gridX = currentWorldX;
            gridY = currentWorldY;
            hitbox.width = solidAreaWidth;
            hitbox.height = solidAreaHeight;
        }
        if (spriteCounter > 20 && spriteCounter <= 30)
            spriteNum = 3;
        if (spriteCounter > 30) {
            spriteNum = 2;
            spriteCounter = 0;
            usingScythe = false;
            doing = false;
            hitRegistered = false;
        }
    }

    public void hoe() {

        spriteCounter++;

        if (spriteCounter <= 10)
            spriteNum = 1;
        if (spriteCounter > 10 && spriteCounter <= 20) {
            spriteNum = 2;

            // Salvo posizione e hitbox
            int currentWorldX = gridX;
            int currentWorldY = gridY;
            int solidAreaWidth = hitbox.width;
            int solidAreaHeight = hitbox.height;

            // Controllo l'oggetto colpito, prima di alterare i valori
            Point tilePos = gp.cChecker.getTilePosition(this);

            // Sposto la posizione in relazione alla direzione
            switch (direction) {
                case "up":
                    gridY -= hitArea.height;
                    break;
                case "down":
                    gridY += hitArea.height;
                    break;
                case "left":
                    gridX -= hitArea.width;
                    break;
                case "right":
                    gridX += hitArea.width;
                    break;
            }

            // Il colpo è la hitbox
            hitbox.width = hitArea.width;
            hitbox.height = hitArea.height;

            if (!hitRegistered) {

                // Ricavo la posizione con colonna e riga
                int col = tilePos.x;
                int row = tilePos.y;

                // Ricavo l'indice
                int tileIndex = gp.tileM.tileMap[gp.currentMap][col][row];

                // Qui creo il nuovo oggetto se tileIndex = 2
                if (tileIndex == 2) {
                    int index = gp.getEmptyObjIndex();
                    // System.out.println("Indice vuoto trovato: " + index);
                    if (index != -1) {

                        // Creo il plot vuoto e lo piazzo
                        gp.obj[gp.currentMap][index] = new PLOT_Empty(gp, false);
                        gp.obj[gp.currentMap][index].gridX = col * gp.tileSize;
                        gp.obj[gp.currentMap][index].gridY = row * gp.tileSize;

                        // La tile si considera solo attraversabile ora
                        gp.tileM.tileMap[gp.currentMap][col][row] = 0;
                    }
                }
                hitRegistered = true;
            }

            // Resetto i parametri
            gridX = currentWorldX;
            gridY = currentWorldY;
            hitbox.width = solidAreaWidth;
            hitbox.height = solidAreaHeight;

        }
        if (spriteCounter > 20 && spriteCounter <= 30)
            spriteNum = 3;
        if (spriteCounter > 30) {
            spriteNum = 2;
            spriteCounter = 0;
            usingHoe = false;
            doing = false;
            hitRegistered = false;
        }
    }

    // INCOMPLETO
    public void hammer() {

        spriteCounter++;

        if (spriteCounter <= 10)
            spriteNum = 1;
        if (spriteCounter > 10 && spriteCounter <= 20) {
            spriteNum = 2;

            // Salvo posizione e hitbox
            int currentWorldX = gridX;
            int currentWorldY = gridY;
            int solidAreaWidth = hitbox.width;
            int solidAreaHeight = hitbox.height;

            // Sposto la posizione in relazione alla direzione
            switch (direction) {
                case "up":
                    gridY -= hitArea.height;
                    break;
                case "down":
                    gridY += hitArea.height;
                    break;
                case "left":
                    gridX -= hitArea.width;
                    break;
                case "right":
                    gridX += hitArea.width;
                    break;
            }

            // Il colpo è la hitbox
            hitbox.width = hitArea.width;
            hitbox.height = hitArea.height;

            if (!hitRegistered) {

                // Controllo l'oggetto colpito
                int objIndex = gp.cChecker.checkObject(this, true);

                if (objIndex != 999) {
                    if (gp.obj[gp.currentMap][objIndex] != null
                            && (gp.obj[gp.currentMap][objIndex].name.equals("Rock")
                                    || gp.obj[gp.currentMap][objIndex].name.equals("Big_Rock"))) {

                        // Diminuisco la vita dell'albero
                        gp.obj[gp.currentMap][objIndex].life--;

                        // Suono
                        gp.playSoundEffect(1);

                        // Se la vita scende a zero, rimuovo l'albero
                        if (gp.obj[gp.currentMap][objIndex].life <= 0) {
                            gp.obj[gp.currentMap][objIndex] = null;
                        }
                    }
                }
                hitRegistered = true;
            }

            // Resetto i parametri
            gridX = currentWorldX;
            gridY = currentWorldY;
            hitbox.width = solidAreaWidth;
            hitbox.height = solidAreaHeight;
        }
        if (spriteCounter > 20 && spriteCounter <= 30)
            spriteNum = 3;
        if (spriteCounter > 30) {
            spriteNum = 2;
            spriteCounter = 0;
            usingHammer = false;
            doing = false;
            hitRegistered = false;
        }
    }

    public void water() {

        spriteCounter++;

        if (spriteCounter <= 12)
            spriteNum = 1;
        if (spriteCounter > 12 && spriteCounter <= 36) {
            spriteNum = 2;

            // Salvo posizione e hitbox
            int currentWorldX = gridX;
            int currentWorldY = gridY;
            int solidAreaWidth = hitbox.width;
            int solidAreaHeight = hitbox.height;

            // Controllo l'oggetto colpito, prima di alterare i valori
            Point tilePos = gp.cChecker.getTilePosition(this);

            // Sposto la posizione in relazione alla direzione
            switch (direction) {
                case "up":
                    gridY -= hitArea.height;
                    break;
                case "down":
                    gridY += hitArea.height;
                    break;
                case "left":
                    gridX -= hitArea.width;
                    break;
                case "right":
                    gridX += hitArea.width;
                    break;
            }

            // Il colpo è la hitbox
            hitbox.width = hitArea.width;
            hitbox.height = hitArea.height;

            if (!hitRegistered) {

                // Ricavo la posizione con colonna e riga
                int col = tilePos.x;
                int row = tilePos.y;

                // Scorrimento dell'array
                for (int i = 0; i < gp.obj[gp.currentMap].length; i++) {
                    Entity obj = gp.obj[gp.currentMap][i];
                    if (obj != null) {
                        // > Verifico:
                        // - Che agisco su un plot
                        // - Che non sia già irrigato
                        // - Posizione corretta

                        boolean correctPos = col == obj.gridX / gp.tileSize &&
                                row == obj.gridY / gp.tileSize;

                        if (obj.isPlot && !obj.watered && correctPos) {
                            obj.watered = true;
                            obj.update();
                            gp.obj[gp.currentMap][i] = obj;
                        }

                    }
                }

                hitRegistered = true;
            }

            // Resetto i parametri
            gridX = currentWorldX;
            gridY = currentWorldY;
            hitbox.width = solidAreaWidth;
            hitbox.height = solidAreaHeight;

        }
        if (spriteCounter > 36) {
            spriteNum = 2;
            spriteCounter = 0;
            usingWateringCan = false;
            doing = false;
            hitRegistered = false;
        }
    }

    public void sow() {

        spriteCounter++;

        if (spriteCounter <= 12)
            spriteNum = 1;
        if (spriteCounter > 12 && spriteCounter <= 36) {
            spriteNum = 2;

            // Salvo posizione e hitbox
            int currentWorldX = gridX;
            int currentWorldY = gridY;
            int solidAreaWidth = hitbox.width;
            int solidAreaHeight = hitbox.height;

            // Controllo l'oggetto colpito, prima di alterare i valori
            Point tilePos = gp.cChecker.getTilePosition(this);

            // Sposto la posizione in relazione alla direzione
            switch (direction) {
                case "up":
                    gridY -= hitArea.height;
                    break;
                case "down":
                    gridY += hitArea.height;
                    break;
                case "left":
                    gridX -= hitArea.width;
                    break;
                case "right":
                    gridX += hitArea.width;
                    break;
            }

            // Il colpo è la hitbox
            hitbox.width = hitArea.width;
            hitbox.height = hitArea.height;

            if (!hitRegistered) {

                // Ricavo la posizione con colonna e riga
                int col = tilePos.x;
                int row = tilePos.y;

                // Scorrimento dell'array
                for (int i = 0; i < gp.obj[gp.currentMap].length; i++) {
                    Entity obj = gp.obj[gp.currentMap][i];
                    if (obj != null) {
                        // > Verifico:
                        // - Plot giusto
                        // - Posizione corretta
                        if (obj.name.equals("Empty_Plot") &&
                                col == obj.gridX / gp.tileSize &&
                                row == obj.gridY / gp.tileSize) {

                            Boolean watered = obj.watered;
                            String seedType = gp.player.currentItem.seedType;
                            System.out.println("Seme piantato: " + seedType);
                            gp.obj[gp.currentMap][i] = new PLOT_Seed(gp, watered, seedType);
                            gp.obj[gp.currentMap][i].gridX = obj.gridX;
                            gp.obj[gp.currentMap][i].gridY = obj.gridY;
                            gp.player.currentItem.amount--;

                        }
                    }
                }

                removeFromInventory();

                hitRegistered = true;
            }

            // Resetto i parametri
            gridX = currentWorldX;
            gridY = currentWorldY;
            hitbox.width = solidAreaWidth;
            hitbox.height = solidAreaHeight;

        }
        if (spriteCounter > 36) {
            spriteNum = 2;
            spriteCounter = 0;
            sowing = false;
            doing = false;
            hitRegistered = false;
        }
    }

    public void pickupObject(int index) {
        if (index != 999) {
            Entity obj = gp.obj[gp.currentMap][index];
            int col = obj.gridX / gp.tileSize;
            int row = obj.gridY / gp.tileSize;

            if (addToInventory(obj.copy())) {
                objectInHand = gp.obj[gp.currentMap][index].down2;
                gp.player.pickingup = true;
                gp.player.doing = true;
                gp.obj[gp.currentMap][index] = null; // rimuovi dalla mappa solo se preso
                if (obj.name.equals("Fence"))
                    gp.tileM.tileMap[gp.currentMap][col][row] = 2;
            } else {
                gp.ui.currentDialogue = "Inventario pieno!";
                gp.gameState = gp.dialogueState;
            }
        }
    }

    public boolean addToInventory(Entity obj) {
        // Se è stackable, cerco se esiste già
        if (obj.isStackable) {
            for (int i = 0; i < inventory.size(); i++) {
                if (inventory.get(i).name.equals(obj.name)) {
                    inventory.get(i).amount += obj.amount;

                    return true;
                }
            }
        }

        // Se non è stackable o non esiste ancora, controllo se c'è spazio libero
        if (inventory.size() < maxInventorySize) {
            inventory.add(obj);
            return true;
        }

        // Altrimenti inventario pieno
        return false;
    }

    public void buy(Entity item) {

        Entity temp = new Entity(gp);

        switch (item.seedType) {
            case "turnip":
                temp = new BAG_Seed(gp, item.seedType);
                break;
            case "potato":
                temp = new BAG_Seed(gp, item.seedType);
                break;
            case "tomato":
                temp = new BAG_Seed(gp, item.seedType);
                break;
            case "corn":
                temp = new BAG_Seed(gp, item.seedType);
                break;
        }

        temp.amount = item.amount;

        if (gold >= item.price) {
            if (addToInventory(temp)) {
                gold -= item.price;
                System.out.println("Acquisto effettuato!");
            } else {
                System.out.println("Non hai spazio!");
                gp.ui.currentDialogue = "Ops! Non hai piu' spazio!";
                gp.gameState = gp.dialogueState;
            }
        } else {
            System.out.println("Non hai denaro");
            gp.ui.currentDialogue = "Non hai abbastanza oro! Prova a\nvendere qualcosa.";
            gp.gameState = gp.dialogueState;
        }
    }

    public void sell(Entity entity, int sellingAmount) {

        Entity temp = new Entity(gp);

        switch (entity.name) {
            case "Turnip":
                temp = new CROP_Turnip(gp);
                temp.amount = entity.amount;
                break;
            case "Potato":
                temp = new CROP_Potato(gp);
                temp.amount = entity.amount;
                break;
            case "Tomato":
                temp = new CROP_Tomato(gp);
                temp.amount = entity.amount;
                break;
            case "Corn":
                temp = new CROP_Corn(gp);
                temp.amount = entity.amount;
                break;
            case "Egg":
                temp = new OBJ_Egg(gp);
                temp.amount = entity.amount;
                break;
        }

        if (temp.amount >= sellingAmount) {
            entity.amount -= sellingAmount;
            gold += entity.price * sellingAmount;
            removeFromInventory();
        } else {
            gp.ui.currentDialogue = "Non hai abbastanza oggetti!";
            gp.ui.dialogueCategory = 0;
            gp.gameState = gp.dialogueState;
            gp.ui.optionHovered = -1;
        }

    }

    public void removeFromInventory() {

        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).amount <= 0) {
                inventory.remove(i);
            }
        }

    }

    public void interactNPC(int i) {

        if (i != 999) {
            gp.gameState = gp.dialogueState;
            gp.npc[gp.currentMap][i].speak();
        }
    }

    public void draw(Graphics2D g2d) {

        // SPRITES' OFFSETS
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        // OBJECTS IN HAND OFFSETS
        int objScreenX = screenX;
        int objScreenY = screenY;

        // SELECTING ANIMATION FRAME
        switch (direction) {
            case "up":
                if (!doing) {
                    if (spriteNum == 1)
                        image = up1;
                    if (spriteNum == 2)
                        image = up2;
                    if (spriteNum == 3)
                        image = up3;
                }
                if (pickingup) {
                    if (spriteNum == 1) {
                        tempScreenY -= 3;
                        objScreenY += 18;
                        image = pickupUp1;
                    }
                    if (spriteNum == 2) {
                        tempScreenY -= 3;
                        image = pickupUp2;
                    }
                    if (spriteNum == 3) {
                        tempScreenY -= 3;
                        objScreenY -= 21;
                        image = pickupUp3;
                    }
                }
                if (usingScythe) {
                    if (spriteNum == 1) {
                        tempScreenX -= 24;
                        tempScreenY -= 15;
                        image = scytheUp1;
                    }
                    if (spriteNum == 2) {
                        tempScreenX -= 24;
                        tempScreenY -= 18;
                        image = scytheUp2;
                    }
                    if (spriteNum == 3) {
                        tempScreenX -= 24;
                        tempScreenY -= 15;
                        image = scytheUp3;
                    }
                }
                if (usingHoe) {
                    if (spriteNum == 1) {
                        tempScreenX -= 27;
                        tempScreenY -= 51;
                        image = hoeUp1;
                    }
                    if (spriteNum == 2) {
                        tempScreenX -= 27;
                        tempScreenY -= 54;
                        image = hoeUp2;
                    }
                    if (spriteNum == 3) {
                        tempScreenX -= 27;
                        tempScreenY -= 54;
                        image = hoeUp3;
                    }
                }
                if (usingAxe) {
                    if (spriteNum == 1) {
                        tempScreenX -= 3;
                        tempScreenY -= 30;
                        image = axeUp1;
                    }
                    if (spriteNum == 2) {
                        tempScreenX -= 3;
                        tempScreenY -= 57;
                        image = axeUp2;
                    }
                    if (spriteNum == 3) {
                        tempScreenX -= 3;
                        tempScreenY -= 9;
                        image = axeUp3;
                    }
                }
                if (usingHammer) {
                    if (spriteNum == 1) {
                        tempScreenX -= 3;
                        tempScreenY -= 6;
                        image = hammerUp1;
                    }
                    if (spriteNum == 2) {
                        tempScreenX -= 3;
                        tempScreenY -= 57;
                        image = hammerUp2;
                    }
                    if (spriteNum == 3) {
                        tempScreenY -= 9;
                        image = hammerUp3;
                    }
                }
                if (usingWateringCan) {
                    if (spriteNum == 1) {
                        tempScreenX -= 3;
                        tempScreenY += 3;
                        image = wateringcanUp1;
                    }
                    if (spriteNum == 2) {
                        tempScreenX -= 3;
                        tempScreenY -= 3;
                        image = wateringcanUp2;
                    }
                }
                if (sowing) {
                    if (spriteNum == 1) {
                        tempScreenY += 3;
                        image = sowUp1;
                    }
                    if (spriteNum == 2) {

                        image = sowUp2;
                    }
                    if (spriteNum == 3) {
                        tempScreenX -= 3;
                        // tempScreenY += 3;
                        image = sowUp3;
                    }
                }
                break;
            case "down":
                if (!doing) {
                    if (spriteNum == 1)
                        image = down1;
                    if (spriteNum == 2)
                        image = down2;
                    if (spriteNum == 3)
                        image = down3;
                }
                if (pickingup) {
                    if (spriteNum == 1) {
                        tempScreenY += 3;
                        objScreenY += 48;
                        image = pickupDown1;
                    }
                    if (spriteNum == 2) {
                        tempScreenY -= 3;
                        image = pickupDown2;
                    }
                    if (spriteNum == 3) {
                        tempScreenY -= 3;
                        objScreenY -= 21;
                        image = pickupDown3;
                    }
                }
                if (usingScythe) {
                    if (spriteNum == 1) {
                        tempScreenX -= 24;
                        tempScreenY -= 3;
                        image = scytheDown1;
                    }
                    if (spriteNum == 2) {
                        tempScreenX -= 24;
                        tempScreenY += 6;
                        image = scytheDown2;
                    }
                    if (spriteNum == 3) {
                        tempScreenX -= 24;
                        tempScreenY += 3;
                        image = scytheDown3;
                    }
                }
                if (usingHoe) {
                    if (spriteNum == 1) {
                        tempScreenX -= 24;
                        tempScreenY -= 18;
                        image = hoeDown1;
                    }
                    if (spriteNum == 2) {
                        tempScreenX -= 24;
                        tempScreenY += 6;
                        image = hoeDown2;
                    }
                    if (spriteNum == 3) {
                        tempScreenX -= 24;
                        tempScreenY += 9;
                        image = hoeDown3;
                    }
                }
                if (usingAxe) {
                    if (spriteNum == 1) {
                        tempScreenY -= 27;
                        image = axeDown1;
                    }
                    if (spriteNum == 2) {
                        tempScreenY -= 18;
                        image = axeDown2;
                    }
                    if (spriteNum == 3) {
                        tempScreenY += 12;
                        image = axeDown3;
                    }
                }
                if (usingHammer) {
                    if (spriteNum == 1) {
                        tempScreenY -= 9;
                        image = hammerDown1;
                    }
                    if (spriteNum == 2) {
                        tempScreenY -= 6;
                        image = hammerDown2;
                    }
                    if (spriteNum == 3) {
                        tempScreenY += 12;
                        image = hammerDown3;
                    }
                }
                if (usingWateringCan) {
                    if (spriteNum == 1) {
                        image = wateringcanDown1;
                    }
                    if (spriteNum == 2) {
                        image = wateringcanDown2;
                    }
                }
                if (sowing) {
                    if (spriteNum == 1) {
                        tempScreenX += 3;
                        tempScreenY += 6;
                        image = sowDown1;
                    }
                    if (spriteNum == 2) {
                        tempScreenY += 6;
                        image = sowDown2;
                    }
                    if (spriteNum == 3) {
                        tempScreenY += 6;
                        image = sowDown3;
                    }
                }
                break;
            case "left":
                if (!doing) {
                    if (spriteNum == 1)
                        image = left1;
                    if (spriteNum == 2)
                        image = left2;
                    if (spriteNum == 3)
                        image = left3;
                }
                if (pickingup) {
                    if (spriteNum == 1) {
                        tempScreenY -= 3;
                        objScreenX -= 33;
                        objScreenY += 21;
                        image = pickupLeft1;
                    }
                    if (spriteNum == 2) {
                        tempScreenY -= 3;
                        objScreenX -= 33;
                        objScreenY += 6;
                        image = pickupLeft2;
                    }
                    if (spriteNum == 3) {
                        tempScreenY -= 3;
                        objScreenY -= 21;
                        image = pickupLeft3;
                    }
                }
                if (usingScythe) {
                    if (spriteNum == 1) {
                        tempScreenX -= 33;
                        tempScreenY -= 12;
                        image = scytheLeft1;
                    }
                    if (spriteNum == 2) {
                        tempScreenX -= 39;
                        tempScreenY -= 12;
                        image = scytheLeft2;
                    }
                    if (spriteNum == 3) {
                        tempScreenX -= 36;
                        tempScreenY -= 12;
                        image = scytheLeft3;
                    }
                }
                if (usingHoe) {
                    if (spriteNum == 1) {
                        tempScreenX -= 15;
                        tempScreenY -= 24;
                        image = hoeLeft1;
                    }
                    if (spriteNum == 2) {
                        tempScreenX -= 33;
                        tempScreenY -= 15;
                        image = hoeLeft2;
                    }
                    if (spriteNum == 3) {
                        tempScreenX -= 33;
                        tempScreenY -= 15;
                        image = hoeLeft3;
                    }
                }
                if (usingAxe) {
                    if (spriteNum == 1) {
                        tempScreenY -= 27;
                        image = axeLeft1;
                    }
                    if (spriteNum == 2) {
                        tempScreenX -= 54;
                        tempScreenY -= 30;
                        image = axeLeft2;
                    }
                    if (spriteNum == 3) {
                        tempScreenX -= 42;
                        tempScreenX -= 9;
                        image = axeLeft3;
                    }
                }
                if (usingHammer) {
                    if (spriteNum == 1) {
                        tempScreenY -= 21;
                        image = hammerLeft1;
                    }
                    if (spriteNum == 2) {
                        tempScreenX -= 36;
                        tempScreenY -= 15;
                        image = hammerLeft2;
                    }
                    if (spriteNum == 3) {
                        tempScreenX -= 36;
                        tempScreenY -= 3;
                        image = hammerLeft3;
                    }
                }
                if (usingWateringCan) {
                    if (spriteNum == 1) {
                        tempScreenX -= 21;
                        tempScreenY += 3;
                        image = wateringcanLeft1;
                    }
                    if (spriteNum == 2) {
                        tempScreenX -= 33;
                        tempScreenY -= 9;
                        image = wateringcanLeft2;
                    }
                }
                if (sowing) {
                    if (spriteNum == 1) {
                        tempScreenX -= 9;
                        image = sowLeft1;
                    }
                    if (spriteNum == 2) {
                        tempScreenX -= 9;
                        tempScreenY -= 3;
                        image = sowLeft2;
                    }
                    if (spriteNum == 3) {
                        tempScreenX -= 12;
                        tempScreenY -= 3;
                        image = sowLeft3;
                    }
                }
                break;
            case "right":
                if (!doing) {
                    if (spriteNum == 1)
                        image = right1;
                    if (spriteNum == 2)
                        image = right2;
                    if (spriteNum == 3)
                        image = right3;
                }
                if (pickingup) {
                    if (spriteNum == 1) {
                        tempScreenY -= 3;
                        objScreenX += 33;
                        objScreenY += 21;
                        image = pickupRight1;
                    }
                    if (spriteNum == 2) {
                        tempScreenY -= 3;
                        objScreenX += 33;
                        objScreenY += 6;
                        image = pickupRight2;
                    }
                    if (spriteNum == 3) {
                        tempScreenY -= 3;
                        objScreenY -= 21;
                        image = pickupRight3;
                    }
                }
                if (usingScythe) {
                    if (spriteNum == 1) {
                        tempScreenX -= 15;
                        tempScreenY -= 12;
                        image = scytheRight1;
                    }
                    if (spriteNum == 2) {
                        tempScreenX -= 9;
                        tempScreenY -= 12;
                        image = scytheRight2;
                    }
                    if (spriteNum == 3) {
                        tempScreenX -= 12;
                        tempScreenY -= 12;
                        image = scytheRight3;
                    }
                }
                if (usingHoe) {
                    if (spriteNum == 1) {
                        tempScreenX -= 33;
                        tempScreenY -= 24;
                        image = hoeRight1;
                    }
                    if (spriteNum == 2) {
                        tempScreenX -= 15;
                        tempScreenY -= 15;
                        image = hoeRight2;
                    }
                    if (spriteNum == 3) {
                        tempScreenX -= 15;
                        tempScreenY -= 15;
                        image = hoeRight3;
                    }
                }
                if (usingAxe) {
                    if (spriteNum == 1) {
                        tempScreenX -= 12;
                        tempScreenY -= 27;
                        image = axeRight1;
                    }
                    if (spriteNum == 2) {
                        tempScreenX += 6;
                        tempScreenY -= 30;
                        image = axeRight2;
                    }
                    if (spriteNum == 3) {
                        tempScreenX += 9;
                        image = axeRight3;
                    }
                }
                if (usingHammer) {
                    if (spriteNum == 1) {
                        tempScreenX -= 12;
                        tempScreenY -= 21;
                        image = hammerRight1;
                    }
                    if (spriteNum == 2) {
                        tempScreenX += 6;
                        tempScreenY -= 15;
                        image = hammerRight2;
                    }
                    if (spriteNum == 3) {
                        tempScreenX += 6;
                        tempScreenY -= 3;
                        image = hammerRight3;
                    }
                }
                if (usingWateringCan) {
                    if (spriteNum == 1) {
                        tempScreenX += 3;
                        tempScreenY += 3;
                        image = wateringcanRight1;
                    }
                    if (spriteNum == 2) {
                        tempScreenX += 3;
                        tempScreenY -= 9;
                        image = wateringcanRight2;
                    }
                }
                if (sowing) {
                    if (spriteNum == 1) {
                        tempScreenX += 9;
                        image = sowRight1;
                    }
                    if (spriteNum == 2) {
                        tempScreenX -= 15;
                        tempScreenY -= 3;
                        image = sowRight2;
                    }
                    if (spriteNum == 3) {
                        tempScreenX -= 12;
                        tempScreenY -= 3;
                        image = sowRight3;
                    }
                }
                break;
        }

        if (pickingup && objectInHand != null && direction.equals("up"))
            g2d.drawImage(objectInHand, objScreenX, objScreenY, null);

        // DRAWING ANIMATION
        g2d.drawImage(image, tempScreenX, tempScreenY, null);

        if (pickingup && objectInHand != null && !direction.equals("up"))
            g2d.drawImage(objectInHand, objScreenX, objScreenY, null);
    }

}