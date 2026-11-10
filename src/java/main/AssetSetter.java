package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import entity.Entity;
import entity.NPC_Chicken;
import entity.NPC_ChurchBoy;
import entity.NPC_ShippingWorker1;
import entity.NPC_ShippingWorker2;
import object.*;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setNPC() {

        int mapNum;
        int i;

        // SHOP
        mapNum = 5;
        i = 0;
        gp.npc[mapNum][i] = new NPC_ShippingWorker1(gp);
        gp.npc[mapNum][i].gridX = gp.tileSize * 1;
        gp.npc[mapNum][i].gridY = gp.tileSize * 9;
        i++;
        gp.npc[mapNum][i] = new NPC_ShippingWorker2(gp);
        gp.npc[mapNum][i].gridX = gp.tileSize * 9;
        gp.npc[mapNum][i].gridY = gp.tileSize * 4;

        // CHICKEN COOP
        mapNum = 3;
        i = 0;
        gp.npc[mapNum][i] = new NPC_Chicken(gp);
        gp.npc[mapNum][i].gridX = gp.tileSize * 10;
        gp.npc[mapNum][i].gridY = gp.tileSize * 10;
        i++;
        gp.npc[mapNum][i] = new NPC_Chicken(gp);
        gp.npc[mapNum][i].gridX = gp.tileSize * 4;
        gp.npc[mapNum][i].gridY = gp.tileSize * 6;
        i++;

        // CHURCH
        mapNum = 4;
        i = 0;
        gp.npc[mapNum][i] = new NPC_ChurchBoy(gp);
        gp.npc[mapNum][i].gridX = gp.tileSize * 7;
        gp.npc[mapNum][i].gridY = gp.tileSize * 14;
    }

    public void setObjects(String s, int mapID) {

        try {
            // InputStream is = getClass().getResourceAsStream(s);
            // BufferedReader br = new BufferedReader(new InputStreamReader(is));
            BufferedReader br = new BufferedReader(new FileReader(s));
            String line = br.readLine();
            int i = 0;

            while ((line = br.readLine()) != null) {
                if (line.isEmpty())
                    continue;
                String[] parts = line.split(" ");
                String name = parts[0];
                int col = Integer.parseInt(parts[1]);
                int row = Integer.parseInt(parts[2]);

                Entity obj = null;

                if (parts.length == 4) {
                    obj = new PLOT_Seed(gp, false, parts[3]);
                }
                if (parts.length == 5) {
                    obj = new PLOT_SeedGrowing(gp, false, parts[3]);
                    obj.growthStage = Integer.parseInt(parts[4]);
                    obj.update();
                }

                switch (name) {
                    case "Fence":
                        obj = new OBJ_Fence(gp);
                        break;
                    case "Tree":
                        obj = new OBJ_Tree(gp);
                        break;
                    case "Empty_Plot":
                        obj = new PLOT_Empty(gp, false);
                        break;
                    case "Corn":
                        obj = new CROP_Corn(gp);
                        break;
                    case "Tomato":
                        obj = new CROP_Tomato(gp);
                        break;
                    case "Turnip":
                        obj = new CROP_Turnip(gp);
                        break;
                    case "Potato":
                        obj = new CROP_Potato(gp);
                        break;
                    case "Rock":
                        obj = new OBJ_Rock(gp);
                        break;
                    case "Big_Rock":
                        obj = new OBJ_BigRock(gp);
                        break;
                    case "Grass":
                        obj = new OBJ_Grass(gp);
                        break;
                }

                if (obj != null) {
                    obj.gridX = col * gp.tileSize;
                    obj.gridY = row * gp.tileSize;
                    gp.obj[mapID][i] = obj;
                    i++;
                }

            }
            br.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

}
