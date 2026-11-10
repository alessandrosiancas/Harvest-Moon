package object;

import entity.Entity;
import main.GamePanel;

public class CROP_Turnip extends Entity {

    GamePanel gp;

    public CROP_Turnip(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Turnip";
        isStackable = true;
        collision = true;
        isCollectible = true;
        canSell = true;
        amount = 1;
        price = 20;
        down2 = setup("/objects/turnip");
        description = "> Rapa\nPotresti mangiarla, oppure\nvenderla!";
    }

    public Entity copy() {

        CROP_Turnip turnip = new CROP_Turnip(gp);
        turnip.name = this.name;

        turnip.down2 = this.down2;
        turnip.description = this.description;
        turnip.amount = this.amount;
        turnip.isStackable = this.isStackable;
        turnip.isCollectible = this.isCollectible;
        turnip.collision = this.collision;
        turnip.canSell = this.canSell;
        turnip.price = this.price;

        return turnip;
    }

}