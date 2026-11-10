package object;

import entity.Entity;
import main.GamePanel;

public class CROP_Corn extends Entity {

    GamePanel gp;

    public CROP_Corn(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Corn";
        isStackable = true;
        collision = true;
        isCollectible = true;
        canSell = true;
        amount = 1;
        price = 23;
        down2 = setup("/objects/corn");
        description = "> Mais\nPotresti mangiarlo, oppure\nvenderlo!";
    }

    public Entity copy() {

        CROP_Corn corn = new CROP_Corn(gp);
        corn.name = this.name;

        corn.down2 = this.down2;
        corn.description = this.description;
        corn.amount = this.amount;
        corn.isStackable = this.isStackable;
        corn.isCollectible = this.isCollectible;
        corn.collision = this.collision;
        corn.canSell = this.canSell;
        corn.price = this.price;

        return corn;
    }

}