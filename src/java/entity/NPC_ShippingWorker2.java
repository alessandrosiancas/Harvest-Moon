package entity;

import java.util.Random;

import main.GamePanel;
import object.BAG_Seed;

public class NPC_ShippingWorker2 extends Entity {

    public NPC_ShippingWorker2(GamePanel gp) {

        super(gp);

        direction = "down";
        speed = 1;

        setCharacterHitbox();
        getImage();
        setDialogue();
        setItems();

    }

    public void getImage() {

        up1 = setup("/npc/shipping_workers/sw2_up1");
        up2 = setup("/npc/shipping_workers/sw2_up2");
        up3 = setup("/npc/shipping_workers/sw2_up3");
        down1 = setup("/npc/shipping_workers/sw2_down1");
        down2 = setup("/npc/shipping_workers/sw2_down2");
        down3 = setup("/npc/shipping_workers/sw2_down3");
        left1 = setup("/npc/shipping_workers/sw2_left1");
        left2 = setup("/npc/shipping_workers/sw2_left2");
        left3 = setup("/npc/shipping_workers/sw2_left3");
        right1 = setup("/npc/shipping_workers/sw2_right1");
        right2 = setup("/npc/shipping_workers/sw2_right2");
        right3 = setup("/npc/shipping_workers/sw2_right3");
    }

    public void setDialogue() {

        dialogues[0] = "Ciao! Ogni volta che ti servono\ndei semi puoi sempre venire\nda me.";
        dialogues[1] = "Vuoi acquistare qualcosa?";

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

        // > Da aggiornare nel caso in cui siano necessari altre azioni/dialoghi
        // ...

        super.speak();

        if (dialogueIndex == 2) {
            gp.ui.dialogueCategory = 1;
        }

        if (dialogues[dialogueIndex] == null)
            dialogueIndex = 0;

        gp.ui.npc = this;
    }

    public void setItems() {
        inventory.add(new BAG_Seed(gp, "turnip"));
        inventory.add(new BAG_Seed(gp, "potato"));
        inventory.add(new BAG_Seed(gp, "tomato"));
        inventory.add(new BAG_Seed(gp, "corn"));

        for (int i = 0; i < inventory.size(); i++)
            inventory.get(i).amount = 10;
    }
}