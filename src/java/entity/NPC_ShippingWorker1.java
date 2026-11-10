package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_ShippingWorker1 extends Entity {

    public NPC_ShippingWorker1(GamePanel gp) {

        super(gp);

        direction = "down";
        speed = 1;
        setCharacterHitbox();
        getImage();
        setDialogue();

    }

    public void getImage() {

        up1 = setup("/npc/shipping_workers/sw1_up1");
        up2 = setup("/npc/shipping_workers/sw1_up2");
        up3 = setup("/npc/shipping_workers/sw1_up3");
        down1 = setup("/npc/shipping_workers/sw1_down1");
        down2 = setup("/npc/shipping_workers/sw1_down2");
        down3 = setup("/npc/shipping_workers/sw1_down3");
        left1 = setup("/npc/shipping_workers/sw1_left1");
        left2 = setup("/npc/shipping_workers/sw1_left2");
        left3 = setup("/npc/shipping_workers/sw1_left3");
        right1 = setup("/npc/shipping_workers/sw1_right1");
        right2 = setup("/npc/shipping_workers/sw1_right2");
        right3 = setup("/npc/shipping_workers/sw1_right3");
    }

    public void setDialogue() {

        dialogues[0] = "Hey! Se ti servono soldi\nio posso aiutarti!";
        dialogues[1] = "Hai qualcosa da vendere?";

    }

    public void setAction() {

        actionLockCounter++;

        if (actionLockCounter == 120) {

            Random random = new Random();
            int i = random.nextInt(100) + 1; // 1-100

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

    public void speak() {

        super.speak();

        if (dialogueIndex == 2) {
            gp.ui.dialogueCategory = 1;
        }

        if (dialogues[dialogueIndex] == null)
            dialogueIndex = 0;
    }
}