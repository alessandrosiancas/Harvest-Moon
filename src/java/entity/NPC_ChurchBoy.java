package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_ChurchBoy extends Entity {
    public NPC_ChurchBoy(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;
        setCharacterHitbox();
        getImage();
        setDialogue();

    }

    public void getImage() {

        up1 = setup("/npc/church_boy/cb_up1");
        up2 = setup("/npc/church_boy/cb_up2");
        up3 = setup("/npc/church_boy/cb_up3");
        down1 = setup("/npc/church_boy/cb_down1");
        down2 = setup("/npc/church_boy/cb_down2");
        down3 = setup("/npc/church_boy/cb_down3");
        left1 = setup("/npc/church_boy/cb_left1");
        left2 = setup("/npc/church_boy/cb_left2");
        left3 = setup("/npc/church_boy/cb_left3");
        right1 = setup("/npc/church_boy/cb_right1");
        right2 = setup("/npc/church_boy/cb_right2");
        right3 = setup("/npc/church_boy/cb_right3");
    }

    public void setDialogue() {

        dialogues[0] = "Ciao, tu devi essere il nuovo\ncontadino!";
        dialogues[1] = "Sono qui per darti qualche dritta\nsulla vita in campagna...";
        dialogues[2] = "Sai che in giro per la tua\nfattoria ci sono dei cartelli?";
        dialogues[3] = "Sono utili per capire le dinamiche\ndi gioco.";
        dialogues[4] = "Prima di tutto, puoi arare\nil terreno con la zappa.";
        dialogues[5] = "Vedo che hai gia' a disposizione\ndei semi nel tuo inventario.";
        dialogues[6] = "Una volta che hai arato il terreno,\npuoi piantare alcuni di quei semi.";
        dialogues[7] = "Rape e Patate in Primavera,\nMais e Pomodoro in Estate.";
        dialogues[8] = "Per farle crescere devi\ninnaffiarle tutti i gioreni!";
        dialogues[9] = "Puoi comprare altri semi al\nnegozio qui accanto";
        dialogues[10] = "oppure vendere i frutti del\ntuo raccolto";
        dialogues[11] = "Occupati anche di pulire la\nfattoria da sassi, tronchi\ned erbacce!";
        dialogues[12] = "Buona fortuna!";
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
        if (dialogues[dialogueIndex] == null)
            dialogueIndex = 0;
    }
}