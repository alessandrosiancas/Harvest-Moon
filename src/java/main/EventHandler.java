package main;

public class EventHandler {

    GamePanel gp;
    EventRect eventRect[][][];

    // CHANGING MAP PARAMETERS
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gp) {
        this.gp = gp;
        int mapID = gp.currentMap;
        eventRect = new EventRect[gp.maxMap][gp.worldCol[mapID]][gp.worldRow[mapID]];

        int map = 0;
        int col = 0;
        int row = 0;
        while (map < gp.maxMap && col < gp.worldCol[mapID] && row < gp.worldRow[mapID]) {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 14;
            eventRect[map][col][row].y = 14;
            eventRect[map][col][row].width = 20;
            eventRect[map][col][row].height = 20;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if (col == gp.worldCol[mapID]) {
                col = 0;
                row++;

                // create event rectangle for each map
                if (row == gp.worldRow[mapID]) {
                    row = 0;
                    map++;
                }
            }
        }

    }

    public void checkEvent() {
        // check if the player character is more than 1 tile away from the last event
        int xDistance = Math.abs(gp.player.gridX - previousEventX);
        int yDistance = Math.abs(gp.player.gridY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > gp.tileSize) {
            canTouchEvent = true;
        }

        if (canTouchEvent) {

            // FROM FARM MAP TO VILLAGE
            if (hit(0, 0, 26, "left") || hit(0, 0, 27, "left") || hit(0, 0, 28, "left") || hit(0, 0, 29, "left")) {
                teleport(1, 47, 25); // cambio di mappa
                gp.stopMusic();
                gp.playMusic(2);
            }

            // FROM VILLAGE TO FARM MAP
            else if (hit(1, 47, 26, "right") || hit(1, 47, 27, "right")) {
                teleport(0, 0, 27);
                gp.stopMusic();
                gp.playMusic(0);
            }

            // FROM FARM TO HOUSE
            else if (hit(0, 8, 21, "up")) {
                teleport(2, 8, 11);
            }

            // FROM HOUSE TO FARM
            else if (hit(2, 7, 11, "down") || hit(2, 8, 11, "down")) {
                teleport(0, 8, 20);
            }

            // FROM FARM TO COOP
            else if (hit(0, 28, 22, "up")) {
                teleport(3, 8, 12);
            }

            // FROM COOP TO MAP
            else if (hit(3, 7, 12, "down") || hit(3, 8, 12, "down")) {
                teleport(0, 28, 22);
            }

            // FROM VILLAGE TO SHOP
            else if (hit(1, 37, 14, "up")) {
                teleport(5, 8, 12);
            }

            // FROM SHOP TO VILLAGE
            else if (hit(5, 8, 12, "down") || hit(5, 9, 12, "down")) {
                teleport(1, 37, 14);
            }

            // FROM VILLAGE TO CHURCH
            else if (hit(1, 22, 9, "up") || hit(1, 23, 9, "up")) {
                teleport(4, 7, 27);
            }

            // FROM CHURCH TO VILLAGE
            else if (hit(4, 6, 28, "down") || hit(4, 7, 28, "down") || hit(4, 8, 28, "down")) {
                teleport(1, 22, 9);
            }

            // CARTELLI NELLA FATTORIA
            else if (hit(0, 24, 9, "up")) {
                gp.ui.currentDialogue = "Il vecchio giardino di tuo nonno";
                gp.gameState = gp.dialogueState;
            } else if (hit(0, 7, 30, "up")) {
                gp.ui.currentDialogue = "Puoi vendere le tue colture ad un\nlavoratore nel negozio\ndel villaggio";
                gp.gameState = gp.dialogueState;
            } else if (hit(0, 5, 29, "down")) {
                gp.ui.currentDialogue = "Coltiva le tue piante qui, oppure\nin qualsiasi parte della tua\nfattoria";
                gp.gameState = gp.dialogueState;
            } else if (hit(0, 26, 30, "up")) {
                gp.ui.currentDialogue = "Ancora in costruzione...";
                gp.gameState = gp.dialogueState;
            } else if (hit(0, 20, 22, "up")) {
                gp.ui.currentDialogue = "Al momento non accessibile";
                gp.gameState = gp.dialogueState;
            } else if (hit(0, 15, 16, "up")) {
                gp.ui.currentDialogue = "Puoi abbattere i tronchi con\nl'ascia ma attento... servono tre\ncolpi per buttarlo giu'!";
                gp.gameState = gp.dialogueState;
            } else if (hit(0, 15, 21, "up")) {
                gp.ui.currentDialogue = "Puoi togliere le erbacce con\nla falce e i sassi con il martello";
                gp.gameState = gp.dialogueState;
            } else if (hit(0, 23, 23, "up")) {
                gp.ui.currentDialogue = "I tuoi animali producono ogni\ngiorno, con una possibilita' del\n50%";
                gp.gameState = gp.dialogueState;
            } else if (hit(1, 9, 9, "up") || hit(1, 9, 54, "up") || hit(1, 17, 54, "up") || hit(1, 25, 54, "up")
                    || hit(1, 37, 55, "up") || hit(1, 36, 38, "up")) {
                gp.ui.currentDialogue = "Provi ad aprire la porta\nma e' chiusa a chiave...";
                gp.gameState = gp.dialogueState;
            }

            else if (hit(2, 3, 4, "left")) {
                gp.gameState = gp.sleepState;
                gp.player.energy = gp.player.maxEnergy;
            }

        }
    }

    public boolean hit(int map, int col, int row, String reqDirection) {

        boolean hit = false;
        if (map == gp.currentMap) {
            gp.player.hitbox.x = gp.player.gridX + gp.player.hitbox.x;
            gp.player.hitbox.y = gp.player.gridY + gp.player.hitbox.y;
            eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;

            if (gp.player.hitbox.intersects(eventRect[map][col][row])) {
                if (gp.player.direction.contentEquals(reqDirection) ||
                        reqDirection.contentEquals("any")) {
                    hit = true;

                    previousEventX = gp.player.gridX;
                    previousEventY = gp.player.gridY;
                }
            }

            gp.player.hitbox.x = gp.player.hitboxDefaultX;
            gp.player.hitbox.y = gp.player.hitboxDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }
        return hit;
    }

    public void teleport(int map, int spawnCol, int spawnRow) {

        // UPDATING MAPID
        gp.currentMap = map;

        // LOADING SPAWN
        gp.player.gridX = gp.tileSize * spawnCol;
        gp.player.gridY = gp.tileSize * spawnRow;

        // > Per non far ripetere l'evento a meno di 1 tile da esso
        gp.eHandler.previousEventX = gp.player.gridX;
        gp.eHandler.previousEventY = gp.player.gridY;
        gp.eHandler.canTouchEvent = false;

    }
}
