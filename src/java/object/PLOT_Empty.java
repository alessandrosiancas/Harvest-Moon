package object;

import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class PLOT_Empty extends Entity {

    BufferedImage dryPlot;
    BufferedImage wateredPlot;

    public PLOT_Empty(GamePanel gp, boolean watered) {
        super(gp);

        name = "Empty_Plot";
        isPlot = true;

        getImages();

        this.watered = watered;

        if (watered)
            down2 = wateredPlot;
        else
            down2 = dryPlot;
    }

    public void getImages() {
        dryPlot = setup("/objects/empty_plot");
        wateredPlot = setup("/objects/watered_empty_plot");
    }

    public void update() {
        if (watered)
            down2 = wateredPlot;
        else
            down2 = dryPlot;
    }

}
