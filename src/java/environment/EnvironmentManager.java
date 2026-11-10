package environment;

import java.awt.Graphics2D;

import entity.Entity;
import entity.NPC_Chicken;
import main.GamePanel;
import object.*;

public class EnvironmentManager {
    GamePanel gp;
    public Lighting lighting;
    public Seasons seasons;

    public EnvironmentManager(GamePanel gp) {
        this.gp = gp;

    }

    public void setup() {
        lighting = new Lighting(gp);
        seasons = new Seasons(gp);
    }

    public void update() {

        // GROWING PLANTS
        if (lighting.dayPassed) {
            System.out.println("Sei dentro");
            for (int i = 0; i < gp.obj[0].length; i++) {
                Entity obj = gp.obj[0][i];
                // Verifico:
                // - che non sia null
                // - che sia un terreno seminato o germoglio
                // - che sia stato innaffiato
                if (obj != null) {
                    if (obj.name.equals("Seed_Plot") && obj.watered) {
                        String seedType = obj.seedType;
                        gp.obj[0][i] = new PLOT_SeedGrowing(gp, false, seedType);
                        gp.obj[0][i].gridX = obj.gridX;
                        gp.obj[0][i].gridY = obj.gridY;
                    } else if (obj.name.equals("Seed_Growing_Plot") && obj.watered) {
                        obj.grow();
                        if (obj.growthStage < obj.maxGrowStages)
                            obj.watered = false;
                        obj.update();
                    }
                    if (obj.name.equals("Seed_Growing_Plot") && obj.growthStage == obj.maxGrowStages && obj.watered) {
                        switch (obj.seedType) {
                            case "turnip":
                                gp.obj[0][i] = new CROP_Turnip(gp);
                                gp.obj[0][i].gridX = obj.gridX;
                                gp.obj[0][i].gridY = obj.gridY;
                                break;
                            case "potato":
                                gp.obj[0][i] = new CROP_Potato(gp);
                                gp.obj[0][i].gridX = obj.gridX;
                                gp.obj[0][i].gridY = obj.gridY;
                                break;
                            case "tomato":
                                gp.obj[0][i] = new CROP_Tomato(gp);
                                gp.obj[0][i].gridX = obj.gridX;
                                gp.obj[0][i].gridY = obj.gridY;
                                break;
                            case "corn":
                                gp.obj[0][i] = new CROP_Corn(gp);
                                gp.obj[0][i].gridX = obj.gridX;
                                gp.obj[0][i].gridY = obj.gridY;
                                break;
                        }

                        // Resetta la zona ad arabile
                        int col = gp.obj[0][i].gridX / gp.tileSize;
                        int row = gp.obj[0][i].gridY / gp.tileSize;
                        gp.tileM.tileMap[0][col][row] = 2;
                    }
                }
            }
        }

        // CHICKEN DROP
        for (int i = 0; i < gp.npc[3].length; i++) {
            if (lighting.dayPassed && gp.npc[3][i] != null && gp.npc[3][i] instanceof NPC_Chicken
                    && gp.npc[3][i].dropped == false) {
                ((NPC_Chicken) gp.npc[3][i]).checkDrop(3);

            }
        }

        // DAY / NIGHT
        lighting.update();

        // SEASONS
        seasons.update();
    }

    public void draw(Graphics2D g2d) {
        lighting.draw(g2d);
    }
}