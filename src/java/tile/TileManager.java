package tile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import main.GamePanel;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][][] tileMap; // 3D: mapID -> colonna -> riga

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];

        // TILEMAP IDS
        tileMap = new int[gp.maxMap][][];

        // TILEMAP COLS AND ROWS
        for (int i = 0; i < gp.maxMap; i++) {
            tileMap[i] = new int[gp.worldCol[i]][gp.worldRow[i]];
        }

        // TILE INFO
        getTileInfo();

        // Caricamento griglie da file
        loadGridMap("/map/farm_grid.txt", gp.worldCol[0], gp.worldRow[0], 0);
        loadGridMap("/map/village_grid.txt", gp.worldCol[1], gp.worldRow[1], 1);
        loadGridMap("/map/jack_house_grid.txt", gp.worldCol[2], gp.worldRow[2], 2);
        loadGridMap("/map/chicken_coop_grid.txt", gp.worldCol[3], gp.worldRow[3], 3);
        loadGridMap("/map/church_grid.txt", gp.worldCol[4], gp.worldCol[4], 4);
        loadGridMap("/map/shop_grid.txt", gp.worldCol[5], gp.worldCol[5], 5);
    }

    private void getTileInfo() {
        // attraversabile
        tile[0] = new Tile();

        // collisione
        tile[1] = new Tile();
        tile[1].collision = true;

        // terreno arabile
        tile[2] = new Tile();
        tile[2].isFarmable = true;

    }

    // Carica la griglia di una mappa
    public void loadGridMap(String path, int maxWorldCol, int maxWorldRow, int mapID) {
        try {
            InputStream is = getClass().getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for (int row = 0; row < maxWorldRow; row++) {
                String line = br.readLine();
                if (line == null)
                    break; // protezione

                String[] numbers = line.trim().split("\\s+");

                for (int col = 0; col < maxWorldCol; col++) {
                    int num = Integer.parseInt(numbers[col]);
                    tileMap[mapID][col][row] = num;
                }
            }

            br.close();
        } catch (Exception e) {
            System.out.println("Errore caricamento mappa: " + path);
            e.printStackTrace();
        }
    }

}
