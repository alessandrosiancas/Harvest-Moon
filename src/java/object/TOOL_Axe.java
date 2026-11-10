package object;

import entity.Entity;
import entity.Player;
import main.GamePanel;

public class TOOL_Axe extends Entity {
    public TOOL_Axe(GamePanel gp) {
        super(gp);

        name = "Axe";
        amount = 1;
        down2 = setup("/objects/axe");
        description = "> Ascia\nUna vecchia ascia\nma ancora funzionante.";
    }

    public void use(Player player) {
        player.doing = true;
        player.usingAxe = true;
        player.energy--;
    }

}