package environment;

import main.GamePanel;

public class Seasons {
    GamePanel gp;

    public String currentSeason = "Spring";
    public final int monthDays = 5;

    public Seasons(GamePanel gp) {
        this.gp = gp;
    }

    public void changeSeason(String newSeason) {
        currentSeason = newSeason;

        switch ((currentSeason)) {
            case "Spring":
                gp.mapM.mapImages[0] = gp.mapM.loadMapImage("/map/farm_spring.png",
                        gp.worldCol[0] * gp.tileSize, gp.worldRow[0] * gp.tileSize, 0);
                gp.mapM.mapImages[1] = gp.mapM.loadMapImage("/map/village_spring.png",
                        gp.worldCol[1] * gp.tileSize, gp.worldRow[1] * gp.tileSize, 0);

                break;

            case "Summer":
                gp.mapM.mapImages[0] = gp.mapM.loadMapImage("/map/farm_summer.png",
                        gp.worldCol[0] * gp.tileSize, gp.worldRow[0] * gp.tileSize, 0);
                gp.mapM.mapImages[1] = gp.mapM.loadMapImage("/map/village_summer.png",
                        gp.worldCol[1] * gp.tileSize, gp.worldRow[1] * gp.tileSize, 0);

                break;
        }
    }

    public void update() {
        if (gp.eManager.lighting.numberDays == monthDays && currentSeason.equals("Spring")) {
            gp.eManager.lighting.numberDays = 1;
            changeSeason("Summer");
        } else if (gp.eManager.lighting.numberDays == 3 && currentSeason.equals("Summer")) {
            gp.eManager.lighting.numberDays = 1;
            changeSeason("Spring");
        }
    }
}
