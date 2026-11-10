package entity;

import java.util.Random;

import main.GamePanel;
import object.OBJ_Egg;

public class NPC_Chicken extends Entity {
    public NPC_Chicken(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;
        hitbox.x = 2;
        hitbox.y = 2;
        hitbox.width = 44;
        hitbox.height = 46;
        maxSpriteNum = 2;
        getImage();
    }

    public void getImage() {
        up1 = setup("/npc/chicken/chicken_up1");
        up2 = setup("/npc/chicken/chicken_up2");
        down1 = setup("/npc/chicken/chicken_down1");
        down2 = setup("/npc/chicken/chicken_down2");
        left1 = setup("/npc/chicken/chicken_left1");
        left2 = setup("/npc/chicken/chicken_left2");
        right1 = setup("/npc/chicken/chicken_right1");
        right2 = setup("/npc/chicken/chicken_right2");
    }

    public void setAction() {
        actionLockCounter++;
        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; // numero casuale da 1 a 100

            if (i <= 25) {
                direction = "up";
            }

            if (i > 25 && i <= 50) {
                direction = "down";
            }

            if (i > 50 && i <= 75) {
                direction = "left";
            }

            if (i > 75 && i <= 100) {
                direction = "right";
            }
            actionLockCounter = 0;
        }

    }

    public void checkDrop(int map) {
        // ogni giorno le galline hanno il 50% di probabilità di fare un uovo
        int i = new Random().nextInt(100) + 1;
        if (i <= 50) {
            dropItem(new OBJ_Egg(gp), map);
        }
    }
}
