package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import entity.Entity;

public class UI {

    GamePanel gp;
    Graphics2D g2d;
    Font font;

    // WINDOW VALUES
    int frameX, frameY, frameWidth, frameHeight;

    // BUTTON HITBOX VALUES
    int buttonX, buttonY, buttonWidth, buttonHeight;

    // COLORS
    final Color blackBackground = new Color(0, 0, 0, 150);
    final Color slotColor = new Color(171, 171, 171, 100);
    final Color hoveredSlotColor = new Color(230, 230, 230, 100);
    final Color selectedSlotColor = new Color(255, 255, 255, 180);

    // TRANSITION
    public int transitionCounter = 0;

    // TITLE SCREEN FILES
    BufferedImage titleBackground, titleLogo, buttonStart, buttonContinue, titleCredits;
    BufferedImage buttonStartHighlated, buttonContinueHighlated;

    // TITLE BUTTONS
    public Rectangle startButton, continueButton;
    public boolean startHovered = false;
    public boolean continueHovered = false;

    // PAUSE BUTTONS
    public Rectangle saveButton, settingsButton, exitButton;
    public boolean saveHovered = false;
    public boolean settingsHovered = false;
    public boolean exitHovered = false;
    public boolean toExit = false;

    // INVENTORY
    Rectangle slotRect[] = new Rectangle[20];
    public int index = 0;
    public int slotHovered = -1;
    public Entity selectedItem = null; // item selected
    public int selectedIndex = -1; // item's index
    int slotSize;

    // TRADE & DIALOGUES
    public String currentDialogue = "";
    public Entity npc;
    Rectangle tradeSlotRect[] = new Rectangle[20];
    public int tradeIndex = 0;
    public int tradeSlotHovered = -1;
    public Entity tradeSelectedItem = null; // item selected on trade
    public int tradeSelectedIndex = -1;
    public int tradeState = 0;
    public Rectangle[] options;
    public int optionHovered = -1;
    public int dialogueCategory = 0;
    public int itemSellingAmount = 0;

    public boolean showPos = false;

    public UI(GamePanel gp) {

        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/font/ManaspaceRegular-ZJwZ.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        slotSize = gp.tileSize + 8;

        getTitleImage();

    }

    public void draw(Graphics2D g2d) {

        this.g2d = g2d;
        g2d.setFont(font);
        g2d.setColor(Color.WHITE);

        // TITLE PAGE
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        // GAME RUNNING
        else if (gp.gameState == gp.playState) {
            drawGameBar();

        }

        // GAME PAUSED
        else if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }

        // DIALOGUE
        else if (gp.gameState == gp.dialogueState) {
            drawDialogueScreen(dialogueCategory);
        }

        // INVENTORY
        else if (gp.gameState == gp.inventoryState) {
            drawInventoryScreen();
        }

        // TRADE SCREEN
        else if (gp.gameState == gp.tradeState) {
            drawTradeScreen();
        }

        // EXIT SCREEN
        else if (gp.gameState == gp.exitState) {
            drawExitMenu();
        }

        // SLEEPING
        else if (gp.gameState == gp.sleepState) {
            drawSleepScreen();
        }

        // DEBUG
        if (showPos)
            drawPosDebug();

    }

    public void drawTitleScreen() {

        buttonX = centerX(buttonContinue.getWidth(), gp.screenWidth);
        buttonY = 380;
        buttonWidth = 103 * gp.scale;
        buttonHeight = 14 * gp.scale;
        continueButton = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);

        buttonX = centerX(buttonStart.getWidth(), gp.screenWidth);
        buttonY = 430;
        buttonWidth = 67 * gp.scale;
        buttonHeight = 14 * gp.scale;
        startButton = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);

        g2d.drawImage(titleBackground, -1, -1, null);

        g2d.drawImage(titleLogo, centerX(titleLogo.getWidth(), gp.screenWidth), 80, null);

        // CONTINUE BUTTON
        if (continueHovered == true) {
            g2d.drawImage(buttonContinueHighlated, centerX(buttonContinue.getWidth(), gp.screenWidth), 380, null);
        } else {
            g2d.drawImage(buttonContinue, centerX(buttonContinue.getWidth(), gp.screenWidth), 380, null);
        }

        // START BUTTON
        if (startHovered == true) {
            g2d.drawImage(buttonStartHighlated, centerX(buttonStart.getWidth(), gp.screenWidth), 430, null);
        } else {
            g2d.drawImage(buttonStart, centerX(buttonStart.getWidth(), gp.screenWidth), 430, null);
        }

        // CREDITS
        g2d.drawImage(titleCredits, centerX(titleCredits.getWidth(), gp.screenWidth), 545, null);
    }

    public void drawPauseScreen() {

        // BLACK LAYER
        drawBackLayer();

        // WINDOW
        frameWidth = gp.screenWidth - (gp.tileSize * 6);
        frameHeight = gp.tileSize * 6;
        frameX = centerX(frameWidth, gp.screenWidth);
        frameY = (gp.screenHeight - frameHeight) / 2;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        g2d.setColor(Color.WHITE);
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 40F));

        // TITLE
        String s = "GIOCO IN PAUSA";
        g2d.drawString(s, centerStringX(s, gp.screenWidth), frameY + 65);

        // SUBTITLE
        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 20F));
        s = "(ESC per riprendere il gioco)";
        g2d.drawString(s, centerStringX(s, gp.screenWidth), frameY + 93);

        // SAVE BUTTON
        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 30F));
        s = "Salva Partita";
        buttonX = centerStringX(s, gp.screenWidth) - 5;
        buttonY = frameY + 114;
        buttonWidth = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getWidth() + 10;
        buttonHeight = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getHeight() + 10;
        saveButton = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
        if (saveHovered)
            g2d.setColor(hoveredSlotColor);
        g2d.drawString(s, buttonX + 5, buttonY + 30);
        g2d.setColor(Color.WHITE);

        // SETTINGS BUTTON
        s = "Impostazioni";
        buttonX = centerStringX(s, gp.screenWidth) - 5;
        buttonY = frameY + 169;
        buttonWidth = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getWidth() + 10;
        buttonHeight = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getHeight() + 10;
        settingsButton = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
        if (settingsHovered)
            g2d.setColor(hoveredSlotColor);
        g2d.drawString(s, buttonX + 5, buttonY + 30);
        g2d.setColor(Color.WHITE);

        // EXIT BUTTON
        s = "Esci";
        buttonX = centerStringX(s, gp.screenWidth) - 5;
        buttonY = frameY + 224;
        buttonWidth = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getWidth() + 10;
        buttonHeight = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getHeight() + 10;
        exitButton = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
        if (exitHovered)
            g2d.setColor(hoveredSlotColor);
        g2d.drawString(s, buttonX + 5, buttonY + 30);
        g2d.setColor(Color.WHITE);
    }

    public void drawDialogueScreen(int category) {

        // WINDOW
        frameX = gp.tileSize / 2;
        frameY = gp.screenHeight - ((gp.tileSize / 2) * 8);
        frameWidth = gp.screenWidth - gp.tileSize;
        frameHeight = (gp.tileSize / 2) * 7;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // TEXT
        g2d.setColor(Color.WHITE);
        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 30F));
        frameX += gp.tileSize / 2;
        frameY += gp.tileSize;

        for (String line : currentDialogue.split("\n")) {
            g2d.drawString(line, frameX, frameY);
            frameY += 40;
        }
        if (category == 0) {
            String[] optionsStr = { "Continua", "Esci" };
            drawOptions(optionsStr);
        } else if (category == 1) {
            String[] optionsStr = { "Si", "No" };
            drawOptions(optionsStr);
        }
    }

    public void drawOptions(String[] optionsStr) {

        int dim = optionsStr.length;
        options = new Rectangle[dim];

        // Registra la String più lunga graficamente
        // Registra l'altezza della finestra
        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 25F));
        int maxWidth = 0;
        int maxHeight = 0;
        for (int i = 0; i < optionsStr.length; i++) {
            String s = optionsStr[i];
            int mw = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
            int mh = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getHeight();
            if (mw > maxWidth)
                maxWidth = mw;
            maxHeight += mh;
        }

        // DIALOGUE WINDOW VALUES
        int dFrameX = gp.tileSize / 2;
        int dFrameY = gp.screenHeight - ((gp.tileSize / 2) * 8);
        int dFrameWidth = gp.screenWidth - gp.tileSize;

        // ANSWER WINDOW VALUES
        frameWidth = maxWidth + gp.tileSize;
        frameHeight = maxHeight + gp.tileSize;
        frameX = dFrameX + dFrameWidth - frameWidth;
        frameY = dFrameY - frameHeight;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        for (int i = 0; i < dim; i++) {
            String s = optionsStr[i];
            buttonX = frameX + 20;
            buttonY = 20 + frameY + i * 35;
            buttonWidth = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
            buttonHeight = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getHeight();
            options[i] = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
            g2d.setColor(i == optionHovered ? hoveredSlotColor : Color.WHITE);
            g2d.drawString(s, buttonX, buttonY + 20);
        }
    }

    public void drawGameBar() {

        frameWidth = 322;
        frameHeight = 70;
        frameX = centerX(frameWidth, gp.screenWidth);
        frameY = gp.screenHeight - frameHeight - 5;

        g2d.setColor(blackBackground);
        g2d.fillRoundRect(frameX, frameY, frameWidth, frameHeight, 20, 20);

        int slotXstart = frameX + 7;
        int slotX = slotXstart;
        int slotY = frameY + 7;

        for (int i = 0; i < 5; i++) {
            if (i != 0)
                slotX += slotSize + 7;
            g2d.setColor(i == gp.player.hotbarSelected ? hoveredSlotColor : slotColor);
            g2d.fillRoundRect(slotX, slotY, slotSize, slotSize, 15, 15);

            int invIndex = gp.player.hotbarStartIndex + i;
            if (invIndex < gp.player.inventory.size()) {
                Entity item = gp.player.inventory.get(invIndex);
                g2d.drawImage(item.down2, slotX + 4, slotY + 4, null);

                // Mostra la quantità
                if (item.isStackable && item.amount > 1) { // disegna solo se > 1
                    drawAmount(slotX, slotY, item.amount);
                }
            }
        }
        slotX = slotXstart;
    }

    public void drawInventoryScreen() {

        // BACKGROUND
        drawBackLayer();

        // INVENTORY
        drawInventory();

        // ITEM DESCRIPTION
        if (slotHovered >= 0 && slotHovered < gp.player.inventory.size()) {
            index = slotHovered;
            drawDescription(gp.player);
        }

        // STATS
        drawStats();
    }

    public void drawInventory() {
        // INVENTORY FRAME
        frameX = 360;
        frameY = 50;
        frameWidth = 380;
        frameHeight = 369;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // TITLES
        g2d.setFont(g2d.getFont().deriveFont(20F));
        g2d.drawString("> Inventario", frameX + 18, frameY + 35);
        g2d.drawString("> Barra rapida", frameX + 18, frameY + 278);

        // SETTING SLOT VALUES

        int cols = 5;

        // HOTBAR (1x5)
        int hotbarStartX = frameX + 20;
        int hotbarStartY = 343;

        for (int i = 0; i < 5; i++) {
            int x = hotbarStartX + i * (slotSize + 15);
            int y = hotbarStartY;
            if (i < slotRect.length)
                slotRect[i] = new Rectangle(x, y, slotSize, slotSize);

            g2d.setColor(i == selectedIndex ? selectedSlotColor : i == slotHovered ? hoveredSlotColor : slotColor);
            g2d.fillRoundRect(x, y, slotSize, slotSize, 15, 15);
        }

        // INVENTORY GRID (3x5)
        int invStartX = frameX + 20;
        int invStartY = frameY + 50;
        int slotIndex = 5;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 5; col++) {
                int x = invStartX + col * (slotSize + 15);
                int y = invStartY + row * (slotSize + 15);
                if (slotIndex < slotRect.length)
                    slotRect[slotIndex] = new Rectangle(x, y, slotSize, slotSize);

                g2d.setColor(slotIndex == selectedIndex ? selectedSlotColor
                        : slotIndex == slotHovered ? hoveredSlotColor : slotColor);
                g2d.fillRoundRect(x, y, slotSize, slotSize, 15, 15);

                slotIndex++;
            }
        }

        // ITEMS
        // Hotbar items
        for (int i = 0; i < 5 && i < gp.player.inventory.size(); i++) {
            Entity item = gp.player.inventory.get(i);
            int x = hotbarStartX + i * (slotSize + 15);
            int y = hotbarStartY;
            g2d.drawImage(item.down2, x + 4, y + 4, null);
            if (item.isStackable && item.amount > 1)
                drawAmount(x, y, item.amount);
        }

        // Inventory items
        for (int i = 5; i < gp.player.inventory.size(); i++) {
            Entity item = gp.player.inventory.get(i);
            int idx = i - 5;
            int row = idx / cols;
            int col = idx % cols;
            int x = invStartX + col * (slotSize + 15);
            int y = invStartY + row * (slotSize + 15);
            g2d.drawImage(item.down2, x + 4, y + 4, null);
            if (item.isStackable && item.amount > 1)
                drawAmount(x, y, item.amount);
        }

    }

    public void drawAmount(int slotX, int slotY, int hasItem) {

        int x = slotX + gp.tileSize - 7;
        int y = slotY + gp.tileSize + 3;
        g2d.setFont(g2d.getFont().deriveFont(16F));
        g2d.setColor(Color.WHITE);
        String quantity = Integer.toString(hasItem);
        g2d.drawString(quantity, x, y);
    }

    public void drawDescription(Entity entity) {

        // WINDOW
        frameX = 360;
        frameY = 430;
        frameWidth = 380;
        frameHeight = 150;

        // TEXT
        int textX = frameX + 18;
        int textY = frameY + 35;
        g2d.setFont(g2d.getFont().deriveFont(20F));

        if (index < entity.inventory.size()) {
            drawSubWindow(frameX, frameY, frameWidth, frameHeight);
            g2d.setColor(Color.WHITE);
            for (String line : entity.inventory.get(index).description.split("\n")) {
                g2d.drawString(line, textX, textY);
                textY += 25;
            }
        }

    }

    public void drawStats() {

        frameX = 28;
        frameY = 120;
        frameWidth = 280;
        frameHeight = 370;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        g2d.setFont(g2d.getFont().deriveFont(20F));

        String title = "> Player's Name";
        int x = frameX + 18;
        int y = frameY + 35;
        g2d.drawString(title, x, y);

        title = "> Oro";
        y += 200;
        g2d.drawString(title, x, y);
        y += 30;
        title = Integer.toString(gp.player.gold);
        g2d.drawString(title, x, y);

        title = "> Energia";
        y += 50;
        g2d.drawString(title, x, y);
        title = gp.player.energy + "/" + gp.player.maxEnergy;
        y += 30;
        g2d.drawString(title, x, y);

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = uTool.scaleImage(gp.player.down2, 64, 96);
        x = centerX(image.getWidth(), frameWidth) + frameX;
        y = frameY + 70;
        Color c = new Color(171, 171, 171, 100);
        g2d.setColor(c);
        g2d.fillRoundRect(x - 10, y - 10, 84, 126, 15, 15);
        g2d.drawImage(image, x, y, null);

    }

    public void drawTradeScreen() {
        if (tradeState == 1)
            tradeBuy();
        else if (tradeState == 2)
            tradeSell();
        else
            gp.gameState = gp.playState;
    }

    public void tradeBuy() {

        drawBackLayer();
        frameX = 28;
        frameY = 50;
        frameWidth = 309;
        frameHeight = 369;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // TITLES
        g2d.setFont(g2d.getFont().deriveFont(20F));
        g2d.drawString("> Negozio", frameX + 18, frameY + 35);
        g2d.drawString("Hai " + gp.player.gold + " di oro", frameX + 18, frameY + 65);

        // DRAWING SELLER INVENTORY ON LEFT
        int invStartX = frameX + 20;
        int invStartY = frameY + 80;

        for (int i = 0; i < npc.inventory.size(); i++) {
            int x = invStartX;
            int y = invStartY + i * (slotSize + 15);

            // Creates hoveredArea
            if (i < tradeSlotRect.length)
                tradeSlotRect[i] = new Rectangle(x, y, slotSize, slotSize);

            // Creates slots
            g2d.setColor(i == tradeSelectedIndex ? selectedSlotColor
                    : i == tradeSlotHovered ? hoveredSlotColor : slotColor);
            g2d.fillRoundRect(x, y, slotSize, slotSize, 15, 15);

            // Draws items
            Entity item = npc.inventory.get(i);
            g2d.drawImage(item.down2, x + 4, y + 4, null);

            // Draw amount
            if (item.isStackable && item.amount > 1)
                drawAmount(x, y, item.amount);

            // Draws Price
            int itemPrice = item.price;
            String s = "";
            switch (item.seedType) {
                case "turnip":
                    s = "Semi di Rapa";
                    break;
                case "potato":
                    s = "Semi di Patata";
                    break;
                case "tomato":
                    s = "Semi di Pomodoro";
                    break;
                case "corn":
                    s = "Semi di Mais";
                    break;
            }
            g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 18F));
            g2d.drawString(s, x + 71, y + 20);
            s = "Prezzo: " + itemPrice;
            g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 18F));
            g2d.drawString(s, x + 71, y + 45);

        }

        // SELLER ITEMS DESCRIPTION
        if (tradeSlotHovered >= 0 && tradeSlotHovered < npc.inventory.size()) {
            index = tradeSlotHovered;
            drawDescription(npc);
        }

        // DRAWING INVENTORY ON RIGHT
        drawInventory();

        // INVENTORY ITEMS DESCRIPTION
        if (slotHovered >= 0 && slotHovered < gp.player.inventory.size()) {
            index = slotHovered; // il tuo drawDescription usa "index"
            drawDescription(gp.player);
        }

        // CONFIRMATION
        if (tradeSelectedIndex != -1) {
            frameX = 28;
            frameY = 430;
            frameWidth = 309;
            frameHeight = 150;
            drawSubWindow(frameX, frameY, frameWidth, frameHeight);
            String s = "Confermi?";
            g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 25F));
            g2d.drawString(s, frameX + 18, frameY + 40);

            options = new Rectangle[2];
            int i = 0;
            s = ">Si'";
            buttonX = frameX + 40;
            buttonY = frameY + 60;
            buttonWidth = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
            buttonHeight = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getHeight();
            options[i] = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
            g2d.setColor(i == optionHovered ? hoveredSlotColor : Color.WHITE);
            g2d.drawString(s, buttonX, buttonY + 20);

            i++;
            s = ">No";
            buttonY = frameY + 100;
            buttonWidth = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
            buttonHeight = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getHeight();
            options[i] = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
            g2d.setColor(i == optionHovered ? hoveredSlotColor : Color.WHITE);
            g2d.drawString(s, buttonX, buttonY + 20);
        }
    }

    public void tradeSell() {

        // --- PARTE GRAFICA ---
        drawBackLayer();

        frameX = 28;
        frameY = 50;
        frameWidth = 309;
        frameHeight = 369;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        g2d.setFont(g2d.getFont().deriveFont(20F));
        g2d.drawString("> Vendita", frameX + 18, frameY + 35);
        g2d.drawString("Hai " + gp.player.gold + " di oro", frameX + 18, frameY + 65);

        int slotX = frameX + (frameWidth - slotSize) / 2;
        int slotY = frameY + 110;

        g2d.setColor(slotColor);
        g2d.fillRoundRect(slotX, slotY, slotSize, slotSize, 15, 15);

        g2d.setColor(Color.WHITE);
        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 20F));
        String s = "Prezzo: ";
        g2d.drawString(s, frameX + 28, frameY + 220);

        s = "Quantita' (max 10): ";
        g2d.drawString(s, frameX + 28, frameY + 290);

        drawInventory();

        // --- FINE PARTE GRAFICA ---

        // --- PARTE GRAFICA DINAMICA ---

        options = new Rectangle[4];
        s = "<";
        g2d.setFont(g2d.getFont().deriveFont(50F));
        frameX = 28;
        frameY = 50;
        frameWidth = 309;
        frameHeight = 369;
        buttonWidth = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
        buttonHeight = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getHeight();
        buttonX = frameX + 50;
        buttonY = frameY + 290;
        options[0] = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
        g2d.setColor(optionHovered == 0 ? hoveredSlotColor : Color.WHITE);
        g2d.drawString(s, buttonX, buttonY + 45);

        s = ">";
        g2d.setFont(g2d.getFont().deriveFont(50F));
        buttonWidth = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
        buttonHeight = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getHeight();
        buttonX = frameX + frameWidth - 50 - buttonWidth;
        buttonY = frameY + 290;
        options[1] = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
        g2d.setColor(optionHovered == 1 ? hoveredSlotColor : Color.WHITE);
        g2d.drawString(s, buttonX, buttonY + 45);

        s = Integer.toString(itemSellingAmount);
        g2d.setFont(g2d.getFont().deriveFont(40F));
        buttonX = frameX + centerStringX(s, frameWidth);
        g2d.setColor(Color.WHITE);
        g2d.drawString(s, buttonX, buttonY + 45);

        if (selectedIndex != -1) {
            if (selectedIndex < gp.player.inventory.size()) {
                BufferedImage sellingItem = gp.player.inventory.get(selectedIndex).down2;
                g2d.drawImage(sellingItem, slotX + 4, slotY + 4, null);

                // CONFIRMATION
                frameX = 28;
                frameY = 430;
                frameWidth = 309;
                frameHeight = 150;
                drawSubWindow(frameX, frameY, frameWidth, frameHeight);

                if (gp.player.inventory.get(selectedIndex).canSell) {

                    int itemPrice = gp.player.inventory.get(selectedIndex).price * itemSellingAmount;
                    s = Integer.toString(itemPrice);
                    buttonX = centerStringX(s, frameWidth) + 28;
                    buttonY = 310;
                    g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 40F));
                    g2d.setColor(Color.WHITE);
                    g2d.drawString(s, buttonX, buttonY);

                    s = "Confermi?";
                    g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 25F));
                    g2d.drawString(s, frameX + 18, frameY + 40);

                    s = ">Si'";
                    buttonX = frameX + 40;
                    buttonY = frameY + 60;
                    buttonWidth = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
                    buttonHeight = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getHeight();
                    options[2] = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
                    g2d.setColor(optionHovered == 2 ? hoveredSlotColor : Color.WHITE);
                    g2d.drawString(s, buttonX, buttonY + 20);

                    s = ">No";
                    buttonY = frameY + 100;
                    buttonWidth = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
                    buttonHeight = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getHeight();
                    options[3] = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
                    g2d.setColor(optionHovered == 3 ? hoveredSlotColor : Color.WHITE);
                    g2d.drawString(s, buttonX, buttonY + 20);

                } else {
                    g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 25F));
                    s = "Non puoi vendere";
                    g2d.drawString(s, frameX + 20, frameY + 45);
                    s = "questo oggetto!";
                    g2d.drawString(s, frameX + 20, frameY + 75);
                }
            } else {
                selectedIndex = -1;
            }
        }
    }

    public void drawExitMenu() {
        drawBackLayer();

        frameX = gp.tileSize * 4;
        frameY = gp.tileSize * 5;
        frameWidth = gp.screenWidth - gp.tileSize * 8;
        frameHeight = gp.screenHeight - gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        g2d.setColor(Color.WHITE);
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 35F));
        String s = "Vuoi uscire?";
        g2d.drawString(s, centerStringX(s, gp.screenWidth), frameY + 50);

        options = new Rectangle[2];

        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 35F));
        int i = 0;

        s = "Si'";

        buttonX = frameX + gp.tileSize * 2;
        buttonY = frameY + 75;
        buttonWidth = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
        buttonHeight = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getHeight();
        options[i] = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
        g2d.setColor(i == optionHovered ? hoveredSlotColor : Color.WHITE);
        g2d.drawString(s, buttonX, buttonY + 30);
        i++;

        s = "No ";
        buttonX = frameX + frameWidth - gp.tileSize * 2 - 40;
        buttonY = frameY + 75;
        buttonWidth = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
        buttonHeight = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getHeight();
        options[i] = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
        g2d.setColor(i == optionHovered ? hoveredSlotColor : Color.WHITE);
        g2d.drawString(s, buttonX, buttonY + 30);
    }

    public void drawSleepScreen() {
        transitionCounter++;

        if (transitionCounter < 120) {
            gp.eManager.lighting.filterAlpha += 0.01f;
            if (gp.eManager.lighting.filterAlpha > 1f) {
                gp.eManager.lighting.filterAlpha = 1f;
            }
        }
        if (transitionCounter >= 120) {
            gp.eManager.lighting.filterAlpha -= 0.01f;
            if (gp.eManager.lighting.filterAlpha <= 0) {
                System.out.println("E' giorno!");
                gp.eManager.lighting.filterAlpha = 0f;
                System.out.println("dayPassed prima: " + gp.eManager.lighting.dayPassed);
                gp.eManager.lighting.dayPassed = true;
                transitionCounter = 0;
                gp.eManager.lighting.dayCounter = 0;
                gp.eManager.lighting.dayState = gp.eManager.lighting.day;
                System.out.println("dayPassed dopo: " + gp.eManager.lighting.dayPassed);
                gp.gameState = gp.playState;
                gp.eManager.lighting.numberDays++;
            }
        }
    }

    public void drawSubWindow(int x, int y, int width, int height) {

        g2d.setColor(blackBackground);
        g2d.fillRoundRect(x, y, width, height, 35, 35);

        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);

    }

    public void drawBackLayer() {

        g2d.setColor(blackBackground);
        g2d.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
    }

    public int centerStringX(String text, int widthArea) {

        int length = (int) g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        int x = (widthArea - length) / 2;

        return x;
    }

    public int centerX(int width, int widthArea) {
        // int imageWidth = image.getWidth();
        int x = (widthArea - width) / 2;
        return x;
    }

    public BufferedImage setup(String imagePath, int width, int height) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return image;
    }

    public void getTitleImage() {

        titleBackground = setup("/titlescreen/title_screen_background", 768, 768);
        titleLogo = setup("/titlescreen/title_logo", 196 * gp.scale, 91 * gp.scale);
        titleCredits = setup("/titlescreen/title_credits", 191 * gp.scale, 22 * gp.scale);
        buttonStart = setup("/titlescreen/start_button", 67 * gp.scale, 14 * gp.scale);
        buttonContinue = setup("/titlescreen/continue_button", 103 * gp.scale, 14 * gp.scale);
        // buttonHowtoplay = setup("/titlescreen/howtoplay_button", 132, 14);

        buttonStartHighlated = setup("/titlescreen/highlated_start_button", 67 * gp.scale, 14 * gp.scale);
        buttonContinueHighlated = setup("/titlescreen/highlated_continue_button", 103 * gp.scale, 14 * gp.scale);
    }

    public void drawPosDebug() {
        // DEBUG
        g2d.setFont(new Font("Arial", Font.PLAIN, 20));
        g2d.setColor(Color.WHITE);
        int y = 550;
        g2d.drawString("gridX: " + gp.player.gridX, 10, y);
        y += 20;
        g2d.drawString("gridY:" + gp.player.gridY, 10, y);
        y += 20;
        g2d.drawString("col: " + (gp.player.gridX + gp.player.hitbox.x) / gp.tileSize, 10, y);
        y += 20;
        g2d.drawString("row: " + (gp.player.gridY + gp.player.hitbox.y) / gp.tileSize, 10, y);
    }
}
