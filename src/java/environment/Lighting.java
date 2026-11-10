package environment;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Lighting {
    GamePanel gp;
    BufferedImage darknessFilter;
    public int dayCounter;
    public float filterAlpha = 0f;

    public int numberDays = 1;
    public boolean dayPassed = false;

    public final int day = 0; // giorno
    public final int dusk = 1; // tramonto
    public final int night = 2; // notte
    public final int dawn = 3; // alba
    public int dayState = day;
    public final int dayDuration = 3600; // 2 minuti

    public Lighting(GamePanel gp) {
        darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) darknessFilter.getGraphics(); // tutto ciò che si disegna viene filtrato
        Area screenArea = new Area(new Rectangle2D.Double(0, 0, gp.screenWidth, gp.screenHeight));
        g2d.setColor(new Color(0, 0, 0.1f, 0.6f));
        g2d.fill(screenArea);
        g2d.dispose();
    }

    public void update() {

        if (dayState == day) {
            dayPassed = false;
            dayCounter++;

            if (dayCounter > dayDuration) {
                dayState = dusk;
                dayCounter = 0;
            }
        }

        if (dayState == dusk) {
            filterAlpha += 0.001f;

            if (filterAlpha > 1f) {
                filterAlpha = 1f;
                dayState = night;
            }
        }

        if (dayState == night) {
            dayCounter++;
            if (dayCounter > dayDuration) {
                dayState = dawn;
                dayCounter = 0;
            }
        }

        if (dayState == dawn) {
            filterAlpha -= 0.001f;

            if (filterAlpha < 0) {
                filterAlpha = 0f;
                dayState = day;
                numberDays++;
                dayPassed = true;
                System.out.println("Scocca il giorno dopo!");
            }
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
        g2d.drawImage(darknessFilter, 0, 0, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
