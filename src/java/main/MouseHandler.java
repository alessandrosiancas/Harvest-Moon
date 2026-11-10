package main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import entity.Entity;
import entity.NPC_ShippingWorker1;
import entity.NPC_ShippingWorker2;

public class MouseHandler extends MouseAdapter {

    GamePanel gp;

    public MouseHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void mousePressed(MouseEvent me) {

        // TITLE SCREEN
        if (gp.gameState == gp.titleState && me.getButton() == MouseEvent.BUTTON1) {
            int mx = me.getX();
            int my = me.getY();

            if (gp.ui.startButton.contains(mx, my)) {
                gp.aSetter.setObjects("objects.txt", 0);
                gp.gameState = gp.playState;
                gp.playMusic(0);
            }
            if (gp.ui.continueButton.contains(mx, my)) {
                gp.config.loadConfig();
                gp.gameState = gp.playState;
                if (gp.currentMap == 0 || gp.currentMap == 2 || gp.currentMap == 3)
                    gp.playMusic(0);
                else
                    gp.playMusic(2);

            }
        }

        // GAME RUNNING
        else if (gp.gameState == gp.playState) {
            if (me.getButton() == MouseEvent.BUTTON1) {
                int objIndex = gp.cChecker.checkObject(gp.player, true);

                // PICKING UP
                if (objIndex != 999 && gp.obj[gp.currentMap][objIndex].isCollectible && !gp.player.doing) {
                    gp.player.pickupObject(objIndex);
                }

                // USING TOOL
                else if (gp.player.currentItem != null && !gp.player.doing && gp.player.energy > 0) {
                    gp.player.currentItem.use(gp.player);
                }

                // NO ENERGY
                else if (gp.player.energy == 0) {
                    gp.ui.currentDialogue = "Non hai piu' energia!";
                    gp.gameState = gp.dialogueState;
                }

            }
            if (me.getButton() == MouseEvent.BUTTON3) {
                int entityIndex = gp.cChecker.checkEntity(gp.player, gp.npc);
                gp.player.interactNPC(entityIndex);
            }
        }

        // GAME PAUSED
        else if (gp.gameState == gp.pauseState) {

            int mx = me.getX();
            int my = me.getY();

            if (gp.ui.saveButton.contains(mx, my)) {
                gp.config.saveConfig();
            }
            if (gp.ui.settingsButton.contains(mx, my)) {
                // TODO - INCOMPLETO
            }
            if (gp.ui.exitButton.contains(mx, my)) {
                gp.gameState = gp.exitState;
                gp.ui.toExit = true;
            }
        }

        // INVENTORY
        else if (gp.gameState == gp.inventoryState || gp.gameState == gp.tradeState) {
            if (gp.ui.tradeState != 2)
                for (int i = 0; i < gp.ui.slotRect.length; i++) {
                    if (gp.ui.slotRect[i] != null && gp.ui.slotRect[i].contains(me.getX(), me.getY())) {
                        if (i < gp.player.inventory.size()) { // slot occupato
                            if (gp.ui.selectedIndex == -1) {
                                // Primo click: selezione
                                gp.ui.selectedIndex = i;
                            } else {
                                // Secondo click: swap
                                if (i != gp.ui.selectedIndex) {
                                    gp.player.swapItems(gp.ui.selectedIndex, i);
                                    gp.player.updateCurrentItem();
                                }
                                // Reset
                                gp.ui.selectedIndex = -1;
                            }
                        }
                    }
                }

            // ONLY ON TRADING (BUYING)
            if (gp.gameState == gp.tradeState && gp.ui.tradeState == 1) {
                for (int i = 0; i < gp.ui.tradeSlotRect.length; i++) {
                    if (gp.ui.tradeSlotRect[i] != null && gp.ui.tradeSlotRect[i].contains(me.getX(), me.getY()))
                        if (i < gp.ui.npc.inventory.size() && gp.ui.tradeSelectedIndex == -1)
                            gp.ui.tradeSelectedIndex = i;

                    if (gp.ui.tradeSelectedIndex != -1) {
                        for (int j = 0; j < gp.ui.options.length; j++) {
                            if (gp.ui.options[j] != null &&
                                    gp.ui.options[j].contains(me.getX(), me.getY()) &&
                                    me.getButton() == MouseEvent.BUTTON1) {

                                if (j == 0) {
                                    // Compra e rimani nel trade state
                                    int itemSelected = gp.ui.tradeSelectedIndex;
                                    System.out.println("Indice trovato: " + itemSelected);
                                    Entity item = gp.ui.npc.inventory.get(itemSelected);
                                    gp.player.buy(item);
                                    gp.ui.tradeSelectedIndex = -1;
                                } else if (j == 1)
                                    gp.ui.tradeSelectedIndex = -1;
                                // Reset
                                gp.ui.optionHovered = -1;

                            }
                        }
                    }
                }
            }

            // ONLY ON TRADING (SELLING)
            else if (gp.gameState == gp.tradeState && gp.ui.tradeState == 2) {
                for (int i = 0; i < gp.ui.slotRect.length; i++) {
                    if (gp.ui.slotRect[i] != null && gp.ui.slotRect[i].contains(me.getX(), me.getY())) {
                        if (i < gp.player.inventory.size()) { // slot occupato
                            if (gp.ui.selectedIndex == -1) {
                                gp.ui.selectedIndex = i;
                            } else {
                                gp.ui.selectedIndex = -1;
                            }
                        }
                    }
                }

                for (int i = 0; i < gp.ui.options.length; i++) {
                    if (gp.ui.options[i] != null &&
                            gp.ui.options[i].contains(me.getX(), me.getY()) &&
                            me.getButton() == MouseEvent.BUTTON1) {
                        if (i == 0) {
                            if (gp.ui.itemSellingAmount > 0) {
                                gp.ui.itemSellingAmount--;
                            }
                        } else if (i == 1) {
                            if (gp.ui.itemSellingAmount < 10) {
                                gp.ui.itemSellingAmount++;
                            }
                        } else if (i == 2) {
                            gp.player.sell(gp.player.inventory.get(gp.ui.selectedIndex),
                                    gp.ui.itemSellingAmount);
                        }
                        // Reset
                        gp.ui.optionHovered = -1;
                    }
                }
            }
        }

        // DIALOGUES
        else if (gp.gameState == gp.dialogueState) {
            if (me.getButton() == MouseEvent.BUTTON1) {
                int mx = me.getX();
                int my = me.getY();
                for (int i = 0; i < gp.ui.options.length; i++) {
                    if (gp.ui.options[i] != null &&
                            gp.ui.options[i].contains(mx, my)) {

                        int entityIndex = gp.cChecker.checkEntity(gp.player, gp.npc);
                        // > Dialogo standard con NPC
                        if (entityIndex != 999 && gp.ui.dialogueCategory == 0) {
                            if (i == 0) {
                                gp.player.interactNPC(entityIndex);
                            } else if (i == 1) {
                                gp.gameState = gp.playState;
                                gp.npc[gp.currentMap][entityIndex].dialogueIndex = 0;
                            }
                        }
                        // > Dialogo interattivo per TRADE
                        else if (entityIndex != 999 && gp.ui.dialogueCategory == 1) {
                            if (i == 0) {
                                if (gp.npc[gp.currentMap][entityIndex] instanceof NPC_ShippingWorker1)
                                    gp.ui.tradeState = 2;
                                else if (gp.npc[gp.currentMap][entityIndex] instanceof NPC_ShippingWorker2)
                                    gp.ui.tradeState = 1;
                                gp.gameState = gp.tradeState;
                            } else if (i == 1) {
                                gp.ui.tradeState = 0;
                                gp.ui.dialogueCategory = 0;
                                gp.npc[gp.currentMap][entityIndex].dialogueIndex = 0;
                            }
                        }
                        // > Finestra generale di dialogo per AVVISI
                        else if (entityIndex == 999 && gp.ui.dialogueCategory == 0) {
                            if (i == 0) {
                                gp.gameState = gp.playState;
                            } else if (i == 1) {
                                gp.gameState = gp.playState;
                            }
                        }

                        // Reset
                        gp.ui.optionHovered = -1;
                    }
                }
            }
        }

        // EXIT
        else if (gp.gameState == gp.exitState) {
            for (int i = 0; i < gp.ui.options.length; i++) {
                if (gp.ui.options[i] != null &&
                        gp.ui.options[i].contains(me.getX(), me.getY()) &&
                        me.getButton() == MouseEvent.BUTTON1) {
                    if (i == 0) {
                        if (gp.ui.tradeState == 1 || gp.ui.tradeState == 2) {
                            gp.gameState = gp.playState;
                            gp.ui.dialogueCategory = 0;
                        } else if (gp.ui.toExit)
                            System.exit(0);
                    } else if (i == 1) {
                        if (gp.ui.tradeState == 1) {
                            gp.ui.tradeState = 1;
                            gp.gameState = gp.tradeState;
                        } else if (gp.ui.tradeState == 2) {
                            gp.gameState = gp.tradeState;
                        } else if (gp.ui.toExit) {
                            gp.gameState = gp.pauseState;
                            gp.ui.toExit = false;
                        }

                    }
                    // Reset
                    gp.ui.optionHovered = -1;
                }
            }
        }

    }

    @Override
    public void mouseMoved(MouseEvent me) {
        // TILE BUTTONS
        if (gp.gameState == gp.titleState) {
            int mx = me.getX();
            int my = me.getY();

            gp.ui.startHovered = gp.ui.startButton.contains(mx, my);
            gp.ui.continueHovered = gp.ui.continueButton.contains(mx, my);

        }

        // PAUSE GAME BUTTONS
        else if (gp.gameState == gp.pauseState) {
            int mx = me.getX();
            int my = me.getY();

            gp.ui.saveHovered = gp.ui.saveButton.contains(mx, my);
            gp.ui.settingsHovered = gp.ui.settingsButton.contains(mx, my);
            gp.ui.exitHovered = gp.ui.exitButton.contains(mx, my);

        }

        // INVENTORY & TRADING SLOTS
        else if (gp.gameState == gp.inventoryState
                || (gp.gameState == gp.tradeState && (gp.ui.tradeState == 1 || gp.ui.tradeState == 2))) {
            int mx = me.getX();
            int my = me.getY();

            gp.ui.slotHovered = -1;
            for (int i = 0; i < gp.ui.slotRect.length; i++)
                if (gp.ui.slotRect[i] != null && gp.ui.slotRect[i].contains(mx, my))
                    gp.ui.slotHovered = i;

            // ONLY ON TRADESTATE
            if (gp.gameState == gp.tradeState) {
                if (gp.ui.tradeState == 1) {
                    gp.ui.tradeSlotHovered = -1;
                    // TO HOVER SLOT
                    for (int i = 0; i < gp.ui.tradeSlotRect.length; i++)
                        if (gp.ui.tradeSlotRect[i] != null && gp.ui.tradeSlotRect[i].contains(mx, my))
                            gp.ui.tradeSlotHovered = i;

                    if (gp.ui.tradeSelectedIndex != -1) {
                        gp.ui.optionHovered = -1;
                        for (int i = 0; i < gp.ui.options.length; i++)
                            if (gp.ui.options[i] != null && gp.ui.options[i].contains(mx, my))
                                gp.ui.optionHovered = i;
                    }
                } else if (gp.ui.tradeState == 2) {
                    gp.ui.optionHovered = -1;
                    for (int i = 0; i < gp.ui.options.length; i++)
                        if (gp.ui.options[i] != null && gp.ui.options[i].contains(mx, my))
                            gp.ui.optionHovered = i;
                }
            }
        }

        // ANSWER HOVERING
        else if (gp.gameState == gp.exitState // menu di uscita
                || gp.gameState == gp.dialogueState) { // dialogo
            int mx = me.getX();
            int my = me.getY();

            gp.ui.optionHovered = -1;
            for (int i = 0; i < gp.ui.options.length; i++)
                if (gp.ui.options[i] != null && gp.ui.options[i].contains(mx, my))
                    gp.ui.optionHovered = i;
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mwe) {
        if (gp.gameState == gp.playState) {

            int notches = mwe.getWheelRotation(); // negativo: rotella su, positivo: giù
            gp.player.hotbarSelected += notches;

            if (gp.player.hotbarSelected < 0)
                gp.player.hotbarSelected = gp.player.hotbarSize - 1;
            if (gp.player.hotbarSelected >= gp.player.hotbarSize)
                gp.player.hotbarSelected = 0;
        }
        gp.player.updateCurrentItem();
    }

}
