package object;

import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class PLOT_SeedGrowing extends Entity {

    BufferedImage[] dryPlot;
    BufferedImage[] wateredPlot;

    public PLOT_SeedGrowing(GamePanel gp, boolean watered, String seedType) {
        super(gp);

        name = "Seed_Growing_Plot";
        isPlot = true;

        growthStage = 0;
        maxGrowStages = 2;

        this.watered = watered;
        this.seedType = seedType;

        if (seedType.equals("tomato") || seedType.equals("corn"))
            maxGrowStages = 4;

        dryPlot = new BufferedImage[maxGrowStages];
        wateredPlot = new BufferedImage[maxGrowStages];

        getImages();

        if (watered)
            down2 = wateredPlot[growthStage];
        else
            down2 = dryPlot[growthStage];

    }

    public void getImages() {
        for (int i = 1; i <= maxGrowStages; i++) {
            dryPlot[i - 1] = setup("/objects/dry_" + seedType + "_grow" + i);
            wateredPlot[i - 1] = setup("/objects/watered_" + seedType + "_grow" + i);
        }
    }

    public void update() {

        if (growthStage < maxGrowStages) {
            if (watered)
                down2 = wateredPlot[growthStage];
            else
                down2 = dryPlot[growthStage];
        } else {
            if (watered)
                down2 = wateredPlot[maxGrowStages - 1];
            else
                down2 = dryPlot[maxGrowStages - 1];
        }
    }

    public void grow() {

        if (growthStage < maxGrowStages && watered)
            growthStage++;

        System.out.println(seedType + " | Grow Stage raggiunto: " + growthStage + " | Max: " + maxGrowStages);

    }

}