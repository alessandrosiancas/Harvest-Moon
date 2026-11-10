package object;

import entity.Entity;
import entity.Player;
import main.GamePanel;

public class OBJ_Fence extends Entity {

    GamePanel gp;

    public OBJ_Fence(GamePanel gp) {

        super(gp);

        this.gp = gp;

        name = "Fence";
        // spriteNum = 1;
        down2 = setup("/objects/fence");
        description = "> Staccionata\nUsala per delimitare o\ndecorare la tua fattoria!";
        amount = 1;
        isStackable = true;
        isCollectible = true;
        collision = true;

    }

    public void use(Player player) {
        player.doing = true;
        player.placing = true;
    }

    public Entity copy() {

        OBJ_Fence f = new OBJ_Fence(gp);
        f.name = this.name;

        f.down2 = this.down2;
        f.description = this.description;
        f.amount = this.amount;
        f.isStackable = this.isStackable;
        f.isCollectible = this.isCollectible;
        f.collision = this.collision;

        return f;
    }

}