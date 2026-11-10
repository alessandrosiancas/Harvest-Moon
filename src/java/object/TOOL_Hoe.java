package object;

import entity.Entity;
import entity.Player;
import main.GamePanel;

public class TOOL_Hoe extends Entity {

    public TOOL_Hoe(GamePanel gp) {

        super(gp);

        name = "Hoe";
        amount = 1;
        down2 = setup("/objects/hoe");
        description = "> Zappa\nCon questo oggetto\npuoi arare il terreno su\ncui piantare dei semi.";

    }

    public void use(Player player) {
        player.doing = true;
        player.usingHoe = true;
        player.energy--;
    }

}