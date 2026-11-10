package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import entity.Entity;
import object.*;

public class Config {
    GamePanel gp;

    public Config(GamePanel gp) {
        this.gp = gp;
    }

    public void saveConfig() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));
            // posizione giocatore
            bw.write(String.valueOf(gp.player.gridX));
            bw.newLine();
            bw.write(String.valueOf(gp.player.gridY));

            // ora della giornata
            bw.newLine();
            bw.write(String.valueOf(gp.eManager.lighting.dayState));

            // numero di giorni
            bw.newLine();
            bw.write(String.valueOf(gp.eManager.lighting.numberDays));

            // stagione
            bw.newLine();
            bw.write(gp.eManager.seasons.currentSeason);

            // numero di mappa
            bw.newLine();
            bw.write(String.valueOf(gp.currentMap));

            // oro
            bw.newLine();
            bw.write(String.valueOf(gp.player.gold));

            // energia
            bw.newLine();
            bw.write(String.valueOf(gp.player.energy));

            // grandezza inventario
            bw.newLine();
            bw.write(String.valueOf(gp.player.inventory.size()));

            // inventario
            for (int i = 0; i < gp.player.inventory.size(); i++) {
                bw.newLine();
                bw.write(gp.player.inventory.get(i).name);
                if (gp.player.inventory.get(i).name.equals("Seed_Bag")) {
                    bw.write(" " + gp.player.inventory.get(i).seedType);
                }
                if (gp.player.inventory.get(i).isStackable) {
                    bw.write(" " + gp.player.inventory.get(i).amount);
                }
            }

            bw.close();

            // oggetti sulla mappa
            bw = new BufferedWriter(new FileWriter("configObjects.txt"));
            for (int i = 0; i < gp.obj[0].length; i++) {
                bw.newLine();
                if (gp.obj[0][i] != null) {
                    bw.write(gp.obj[0][i].name + " ");
                    bw.write(String.valueOf(gp.obj[0][i].gridX / gp.tileSize) + " ");
                    bw.write(String.valueOf(gp.obj[0][i].gridY / gp.tileSize));
                    if (gp.obj[0][i] instanceof PLOT_Seed) {
                        bw.write(" " + gp.obj[0][i].seedType);
                    }
                    if (gp.obj[0][i] instanceof PLOT_SeedGrowing) {
                        bw.write(" " + gp.obj[0][i].seedType + " " + String.valueOf(gp.obj[0][i].growthStage));
                    }
                }
            }
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadConfig() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));
            String temp = br.readLine();
            // posizione giocatore
            gp.player.gridX = Integer.parseInt(temp);
            temp = br.readLine();
            gp.player.gridY = Integer.parseInt(temp);

            // ora della giornata
            temp = br.readLine();
            gp.eManager.lighting.dayState = Integer.parseInt(temp);

            // numero di giorni
            temp = br.readLine();
            gp.eManager.lighting.numberDays = Integer.parseInt(temp);

            // stagione
            temp = br.readLine();
            gp.eManager.seasons.currentSeason = temp;

            // numero di mappa
            temp = br.readLine();
            gp.currentMap = Integer.parseInt(temp);

            // oro
            temp = br.readLine();
            gp.player.gold = Integer.parseInt(temp);

            // energia
            temp = br.readLine();
            gp.player.energy = Integer.parseInt(temp);

            // grandezza inventario
            temp = br.readLine();
            int inventorySize = Integer.parseInt(temp);

            // inventario
            gp.player.inventory.clear();

            for (int i = 0; i < inventorySize; i++) {
                temp = br.readLine();

                if (temp.contains(" ")) {
                    String[] parts = temp.trim().split("\\s+");
                    // Trova una Seed_BAG
                    if (parts.length == 3) {
                        Entity obj = new BAG_Seed(gp, parts[1]);
                        obj.amount = Integer.parseInt(parts[2]);
                        gp.player.inventory.add(obj);
                    }
                    if (parts.length == 2) {
                        Entity obj = null;
                        switch (parts[0]) {
                            case "Fence":
                                obj = new OBJ_Fence(gp);
                                obj.amount = Integer.parseInt(parts[1]);
                                break;
                            case "Turnip":
                                obj = new CROP_Turnip(gp);
                                obj.amount = Integer.parseInt(parts[1]);
                                break;
                            case "Tomato":
                                obj = new CROP_Tomato(gp);
                                obj.amount = Integer.parseInt(parts[1]);
                                break;
                            case "Corn":
                                obj = new CROP_Corn(gp);
                                obj.amount = Integer.parseInt(parts[1]);
                                break;
                            case "Potato":
                                obj = new CROP_Potato(gp);
                                obj.amount = Integer.parseInt(parts[1]);
                                break;

                            case "Egg":
                                obj = new OBJ_Egg(gp);
                                obj.amount = Integer.parseInt(parts[1]);
                                break;

                        }
                        if (obj != null)
                            gp.player.inventory.add(obj);
                    }
                }

                switch (temp) {
                    case "Axe":
                        gp.player.inventory.add(new TOOL_Axe(gp));
                        break;

                    case "Hoe":
                        gp.player.inventory.add(new TOOL_Hoe(gp));
                        break;

                    case "Scythe":
                        gp.player.inventory.add(new TOOL_Scythe(gp));
                        break;

                    case "Watering Can":
                        gp.player.inventory.add(new TOOL_WateringCan(gp));
                        break;

                    case "Hammer":
                        gp.player.inventory.add(new TOOL_Hammer(gp));
                        break;
                }
            }

            br.close();

            gp.aSetter.setObjects("configObjects.txt", 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
