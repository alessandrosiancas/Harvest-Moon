package object;

import entity.Entity;
import entity.Player;
import main.GamePanel;

public class TOOL_Hammer extends Entity {

    public TOOL_Hammer(GamePanel gp) {
        super(gp);

        name = "Hammer";
        amount = 1;
        down2 = setup("/objects/hammer");
        description = "> Martello\nQuesto oggetto ti permette\ndi sbarazzarti\ndei massi piu' grossi!";
    }

    public void use(Player player) {
        player.doing = true;
        player.usingHammer = true;
        player.energy--;
    }
}
