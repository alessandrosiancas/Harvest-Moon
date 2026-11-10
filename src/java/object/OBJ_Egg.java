package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Egg extends Entity {

    GamePanel gp;

    public OBJ_Egg(GamePanel gp) {
        super(gp);
        this.gp = gp;

        // ale sistema le hitbox pls
        hitbox.x = 4 * gp.scale;
        hitbox.y = 3 * gp.scale;
        hitbox.height = 11 * gp.scale;
        hitbox.width = 8 * gp.scale;

        name = "Egg";
        down2 = setup("/objects/egg");
        description = "> Uovo\nUn uovo prodotto dalle\ntue galline";

        amount = 1;
        collision = true;
        isStackable = true;
        isCollectible = true;

        price = 10;
        canSell = true;
    }

    public Entity copy() {

        OBJ_Egg e = new OBJ_Egg(gp);
        e.name = this.name;

        e.down2 = this.down2;
        e.description = this.description;
        e.amount = this.amount;
        e.isStackable = this.isStackable;
        e.isCollectible = this.isCollectible;
        e.collision = this.collision;
        e.canSell = this.canSell;
        e.price = this.price;

        return e;
    }
}
