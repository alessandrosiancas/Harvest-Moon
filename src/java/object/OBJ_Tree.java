package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Tree extends Entity {

    public OBJ_Tree(GamePanel gp) {
        super(gp);

        name = "Tree";
        down2 = setup("/objects/tree");

        hitbox.x = 3;
        hitbox.y = 0;
        hitbox.width = 90;
        hitbox.height = 84;

        collision = true;
        life = 3;
    }
}
