package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_BigRock extends Entity {
    public OBJ_BigRock(GamePanel gp) {
        super(gp);

        collision = true;
        name = "Big_Rock";
        down2 = setup("/objects/big_rock");
        hitbox.x = 3;
        hitbox.y = 0;
        hitbox.width = 90;
        hitbox.height = 84;

        life = 5;
    }
}
