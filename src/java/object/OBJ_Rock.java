package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Rock extends Entity {
    public OBJ_Rock(GamePanel gp) {
        super(gp);

        collision = true;
        name = "Rock";
        down2 = setup("/objects/rock");

        life = 2;
    }
}
