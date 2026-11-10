package object;

import entity.Entity;
import entity.Player;
import main.GamePanel;

public class TOOL_Scythe extends Entity {

    public TOOL_Scythe(GamePanel gp) {

        super(gp);

        name = "Scythe";
        amount = 1;
        down2 = setup("/objects/scythe");
        description = "> Falce\nUna vecchia falce\nutile per togliere le\nerbacce.";

    }

    public void use(Player player) {
        player.doing = true;
        player.usingScythe = true;
        player.energy--;
    }
}
