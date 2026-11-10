package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Grass extends Entity {
    public OBJ_Grass(GamePanel gp) {
        super(gp);

        collision = true;
        name = "Grass";
        down2 = setup("/objects/grass");
        collision = true;

        life = 1;
    }
}
