package object;

import entity.Entity;
import entity.Player;
import main.GamePanel;

public class TOOL_WateringCan extends Entity {
    public TOOL_WateringCan(GamePanel gp) {
        super(gp);

        name = "Watering Can";
        amount = 1;
        down2 = setup("/objects/watering_can");
        description = "> Innaffiatoio\nQuesto oggetto ti permette\ndi dare acqua alle tue\npiante.";
    }

    public void use(Player player) {
        player.doing = true;
        player.usingWateringCan = true;
        player.energy--;
    }
}