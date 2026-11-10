package object;

import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class PLOT_Seed extends Entity {

    BufferedImage dryPlot;
    BufferedImage wateredPlot;

    public PLOT_Seed(GamePanel gp, boolean watered, String seedType) {
        super(gp);

        name = "Seed_Plot";
        isPlot = true;

        getImages();

        this.watered = watered;
        this.seedType = seedType;

        if (watered)
            down2 = wateredPlot;
        else
            down2 = dryPlot;
    }

    public void getImages() {
        dryPlot = setup("/objects/seed_plot");
        wateredPlot = setup("/objects/watered_seed_plot");
    }

    public void update() {
        if (watered)
            down2 = wateredPlot;
        else
            down2 = dryPlot;
    }

}