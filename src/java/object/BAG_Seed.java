package object;

import java.awt.image.BufferedImage;

import entity.Entity;
import entity.Player;
import main.GamePanel;

public class BAG_Seed extends Entity {

    BufferedImage turnip, potato, tomato, corn;
    String turnipDescription = "> Semi di rapa\nPiantali e fai crescere\nle tue rape!";
    String potatoDescription = "> Semi di patata\nPiantali e fai crescere\nle tue patate!";
    String tomatoDescription = "> Semi di pomodoro\nPiantali e fai crescere\ni tuoi pomodori!";
    String cornDescription = "> Semi di mais\nPiantali e fai crescere\nle tue pannocchie!";

    public BAG_Seed(GamePanel gp, String seedType) {
        super(gp);

        name = "Seed_Bag";
        isStackable = true;
        amount = 1;

        getImages();

        this.seedType = seedType;

        switch (seedType) {
            case "turnip":
                down2 = turnip;
                name = "Turnip_" + name;
                description = turnipDescription;
                price = 20;
                break;
            case "potato":
                down2 = potato;
                name = "Potato_" + name;
                description = potatoDescription;
                price = 10;
                break;
            case "tomato":
                down2 = tomato;
                name = "Tomato_" + name;
                description = tomatoDescription;
                price = 35;
                break;
            case "corn":
                down2 = corn;
                name = "Corn_" + name;
                description = cornDescription;
                price = 50;
                break;
        }

    }

    public void getImages() {
        turnip = setup("/objects/turnip_seed_bag");
        potato = setup("/objects/potato_seed_bag");
        tomato = setup("/objects/tomato_seed_bag");
        corn = setup("/objects/corn_seed_bag");
    }

    public void use(Player player) {
        player.doing = true;
        player.sowing = true;
    }

}