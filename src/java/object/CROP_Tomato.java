package object;

import entity.Entity;
import main.GamePanel;

public class CROP_Tomato extends Entity {

    GamePanel gp;

    public CROP_Tomato(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Tomato";
        isStackable = true;
        collision = true;
        isCollectible = true;
        canSell = true;
        amount = 1;
        price = 20;
        down2 = setup("/objects/tomato");
        description = "> Pomodoro\nPotresti mangiarlo, oppure\nvenderlo!";
    }

    public Entity copy() {

        CROP_Tomato tomato = new CROP_Tomato(gp);
        tomato.name = this.name;

        tomato.down2 = this.down2;
        tomato.description = this.description;
        tomato.amount = this.amount;
        tomato.isStackable = this.isStackable;
        tomato.isCollectible = this.isCollectible;
        tomato.collision = this.collision;
        tomato.canSell = this.canSell;
        tomato.price = this.price;

        return tomato;
    }

}
