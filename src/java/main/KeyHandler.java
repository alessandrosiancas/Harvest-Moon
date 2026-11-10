package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); // return numero del tasto che è stato premuto

        if (code == KeyEvent.VK_W) { // se l'utente preme il tasto W (e analogamente)
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_ESCAPE) {
            // PAUSE
            if (gp.gameState == gp.playState) {
                gp.gameState = gp.pauseState;
            }
            // UNPAUSE
            else if (gp.gameState == gp.pauseState) {
                gp.gameState = gp.playState;
            }
            // EXIT MENU
            else if (gp.gameState == gp.tradeState) {
                gp.gameState = gp.exitState;
            }
        }
        if (code == KeyEvent.VK_E) {
            if (gp.gameState == gp.playState)
                gp.gameState = gp.inventoryState;
            else if (gp.gameState == gp.inventoryState)
                gp.gameState = gp.playState;
        }

        // DEBUG
        if (code == KeyEvent.VK_P) {
            if (!gp.ui.showPos)
                gp.ui.showPos = true;
            else if (gp.ui.showPos)
                gp.ui.showPos = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { // analogmanete per il metodo che rilascia i tasti
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }

}
