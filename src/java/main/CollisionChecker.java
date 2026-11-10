package main;

import java.awt.Point;

import entity.Entity;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {

        // HITBOX POINTS
        int entityLeftWorldX = entity.gridX + entity.hitbox.x;
        int entityRightWorldX = entity.gridX + entity.hitbox.x + entity.hitbox.width;
        int entityTopWorldY = entity.gridY + entity.hitbox.y;
        int entityBottomWorldY = entity.gridY + entity.hitbox.y + entity.hitbox.height;

        // HITBOX COL AND ROW TILE
        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        // TILES TO CHECK
        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.tileMap[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.tileMap[gp.currentMap][entityRightCol][entityTopRow];

                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.tileMap[gp.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.tileMap[gp.currentMap][entityRightCol][entityBottomRow];

                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.tileMap[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.tileMap[gp.currentMap][entityLeftCol][entityBottomRow];

                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.tileMap[gp.currentMap][entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.tileMap[gp.currentMap][entityRightCol][entityBottomRow];

                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
        }

    }

    public Point getTilePosition(Entity entity) {
        int x = entity.gridX + 24;
        int y = entity.gridY + 48;
        int col = x / gp.tileSize;
        int row = y / gp.tileSize;

        switch (entity.direction) {
            case "up":
                row -= 1;
                break;
            case "down":
                row += 1;
                break;
            case "left":
                col -= 1;
                break;
            case "right":
                col += 1;
                break;
        }

        return new Point(col, row);
    }

    // COLLISION WITH OBJECTS
    // > Il metodo restituisce l'indice nell'array dell'oggetto nella currentMap
    public int checkObject(Entity entity, boolean player) {

        int index = 999;

        for (int i = 0; i < gp.obj[gp.currentMap].length; i++) {

            if (gp.obj[gp.currentMap][i] != null) {

                // Check distance
                int dx = Math.abs(entity.gridX - gp.obj[gp.currentMap][i].gridX);
                int dy = Math.abs(entity.gridY - gp.obj[gp.currentMap][i].gridY);

                if (dx > gp.tileSize * 2 || dy > gp.tileSize * 2)
                    continue;

                // Get entity's solidArea position
                entity.hitbox.x = entity.gridX + entity.hitbox.x;
                entity.hitbox.y = entity.gridY + entity.hitbox.y;

                // Get the object's solidArea position
                gp.obj[gp.currentMap][i].hitbox.x = gp.obj[gp.currentMap][i].gridX + gp.obj[gp.currentMap][i].hitbox.x;
                gp.obj[gp.currentMap][i].hitbox.y = gp.obj[gp.currentMap][i].gridY + gp.obj[gp.currentMap][i].hitbox.y;

                switch (entity.direction) {
                    case "up":
                        entity.hitbox.y -= entity.speed;
                        if (entity.hitbox.intersects(gp.obj[gp.currentMap][i].hitbox)) {
                            if (gp.obj[gp.currentMap][i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.hitbox.y += entity.speed;
                        if (entity.hitbox.intersects(gp.obj[gp.currentMap][i].hitbox)) {
                            if (gp.obj[gp.currentMap][i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.hitbox.x -= entity.speed;
                        if (entity.hitbox.intersects(gp.obj[gp.currentMap][i].hitbox)) {
                            if (gp.obj[gp.currentMap][i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.hitbox.x += entity.speed;
                        if (entity.hitbox.intersects(gp.obj[gp.currentMap][i].hitbox)) {
                            if (gp.obj[gp.currentMap][i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                }
                entity.hitbox.x = entity.hitboxDefaultX;
                entity.hitbox.y = entity.hitboxDefaultY;
                gp.obj[gp.currentMap][i].hitbox.x = gp.obj[gp.currentMap][i].hitboxDefaultX;
                gp.obj[gp.currentMap][i].hitbox.y = gp.obj[gp.currentMap][i].hitboxDefaultY;
            }
        }

        return index;
    }

    // COLLISION BETWEEN ENTITIES
    public int checkEntity(Entity entity, Entity[][] target) {

        int index = 999;

        for (int i = 0; i < target[1].length; i++) {

            if (target[gp.currentMap][i] != null) {

                // Check distance
                int dx = Math.abs(entity.gridX - target[gp.currentMap][i].gridX);
                int dy = Math.abs(entity.gridY - target[gp.currentMap][i].gridY);

                if (dx > gp.tileSize * 2 || dy > gp.tileSize * 2)
                    continue;

                // Get entity's solidArea position
                entity.hitbox.x = entity.gridX + entity.hitbox.x;
                entity.hitbox.y = entity.gridY + entity.hitbox.y;

                // Get the object's solidArea position
                target[gp.currentMap][i].hitbox.x = target[gp.currentMap][i].gridX + target[gp.currentMap][i].hitbox.x;
                target[gp.currentMap][i].hitbox.y = target[gp.currentMap][i].gridY + target[gp.currentMap][i].hitbox.y;

                switch (entity.direction) {
                    case "up":
                        entity.hitbox.y -= entity.speed;
                        if (entity.hitbox.intersects(target[gp.currentMap][i].hitbox)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "down":
                        entity.hitbox.y += entity.speed;
                        if (entity.hitbox.intersects(target[gp.currentMap][i].hitbox)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "left":
                        entity.hitbox.x -= entity.speed;
                        if (entity.hitbox.intersects(target[gp.currentMap][i].hitbox)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "right":
                        entity.hitbox.x += entity.speed;
                        if (entity.hitbox.intersects(target[gp.currentMap][i].hitbox)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                }
                entity.hitbox.x = entity.hitboxDefaultX;
                entity.hitbox.y = entity.hitboxDefaultY;
                target[gp.currentMap][i].hitbox.x = target[gp.currentMap][i].hitboxDefaultX;
                target[gp.currentMap][i].hitbox.y = target[gp.currentMap][i].hitboxDefaultY;
            }
        }

        return index;
    }

    // COLLISION BETWEEN ENTITY AND PLAYER
    public void checkPlayer(Entity entity) {

        // Get entity's solidArea position
        entity.hitbox.x = entity.gridX + entity.hitbox.x;
        entity.hitbox.y = entity.gridY + entity.hitbox.y;

        // Get the object's solidArea position
        gp.player.hitbox.x = gp.player.gridX + gp.player.hitbox.x;
        gp.player.hitbox.y = gp.player.gridY + gp.player.hitbox.y;

        switch (entity.direction) {
            case "up":
                entity.hitbox.y -= entity.speed;
                if (entity.hitbox.intersects(gp.player.hitbox)) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entity.hitbox.y += entity.speed;
                if (entity.hitbox.intersects(gp.player.hitbox)) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entity.hitbox.x -= entity.speed;
                if (entity.hitbox.intersects(gp.player.hitbox)) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entity.hitbox.x += entity.speed;
                if (entity.hitbox.intersects(gp.player.hitbox)) {
                    entity.collisionOn = true;
                }
                break;
        }
        entity.hitbox.x = entity.hitboxDefaultX;
        entity.hitbox.y = entity.hitboxDefaultY;
        gp.player.hitbox.x = gp.player.hitboxDefaultX;
        gp.player.hitbox.y = gp.player.hitboxDefaultY;
    }

}