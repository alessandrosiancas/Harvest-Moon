package object;

import entity.Entity;
import main.GamePanel;

public class CROP_Potato extends Entity {

    GamePanel gp;

    public CROP_Potato(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Potato";
        isStackable = true;
        collision = true;
        isCollectible = true;
        canSell = true;
        amount = 1;
        price = 18;
        down2 = setup("/objects/potato");
        description = "> Patata\nPotresti mangiarla, oppure\nvenderla!";
    }

    public Entity copy() {

        CROP_Potato potato = new CROP_Potato(gp);
        potato.name = this.name;

        potato.down2 = this.down2;
        potato.description = this.description;
        potato.amount = this.amount;
        potato.isStackable = this.isStackable;
        potato.isCollectible = this.isCollectible;
        potato.collision = this.collision;
        potato.canSell = this.canSell;
        potato.price = this.price;

        return potato;
    }

}