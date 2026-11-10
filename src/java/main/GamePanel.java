package main;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import entity.Entity;
import entity.Player;
import environment.EnvironmentManager;
import object.OBJ_BigRock;
import object.OBJ_Tree;
import object.PLOT_Empty;
import object.PLOT_Seed;
import object.PLOT_SeedGrowing;
import tile.MapManager;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

    // ORIGINAL TILE SIZE (16X16)
    final int originalTileSize = 16;

    // ORIGINAL CHARACTER SIZE (16X24)
    final int originalCharacterWidth = 16;
    final int originalCharacterHeight = 24;

    // SCALE UNIT
    public final int scale = 3;

    // TILE AND CHARACTER SCALING
    public final int tileSize = originalTileSize * scale; // 48x48
    public final int characterWidth = originalCharacterWidth * scale; // 48
    public final int characterHeight = originalCharacterHeight * scale; // 72

    // SCREEN SETTING
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 13;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 624 pixels

    // WORLD'S GRID SETTING
    public final int maxMap = 10;
    public int currentMap = 0;
    public int[] worldCol = new int[maxMap];
    public int[] worldRow = new int[maxMap];
    public int[] worldWidth = new int[maxMap];
    public int[] worldHeight = new int[maxMap];

    // FPS
    final int FPS = 60;

    // COMMANDS SETTING
    KeyHandler keyH = new KeyHandler(this);
    MouseHandler mouseH = new MouseHandler(this);

    // SOUND
    Sound music = new Sound();
    Sound soundEffect = new Sound();

    // ENTITIES CREATION AND ORDERING
    public Player player = new Player(this, keyH);
    public Entity obj[][] = new Entity[maxMap][200];
    public Entity npc[][] = new Entity[maxMap][10];
    ArrayList<Entity> entityList = new ArrayList<>();

    // GAME SETTINGS
    public CollisionChecker cChecker;
    public AssetSetter aSetter;
    public EventHandler eHandler;
    public EnvironmentManager eManager;

    // USER INTERFACE
    public UI ui = new UI(this);

    // GAME THREAD
    Thread gameThread;

    // WORLD AND GRID
    public MapManager mapM;
    public TileManager tileM;

    // SAVING
    Config config = new Config(this);

    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int inventoryState = 4;
    public final int tradeState = 5;
    public final int exitState = 6;
    public final int sleepState = 7;

    public GamePanel() {
        // Window creation
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);

        // Improving rendering performances
        this.setDoubleBuffered(true);

        // Adding command listeners
        this.addKeyListener(keyH);
        this.addMouseListener(mouseH);
        this.addMouseMotionListener(mouseH);
        this.addMouseWheelListener(mouseH);

        this.setFocusable(true);

    }

    public void setupGame() {

        // WORLD SETTINGS
        setAllWorldSettings();

        // SETTINGS
        cChecker = new CollisionChecker(this);
        aSetter = new AssetSetter(this);
        eHandler = new EventHandler(this);
        eManager = new EnvironmentManager(this);

        // WORLD MAP
        mapM = new MapManager(this);
        tileM = new TileManager(this);

        // LOADING STUFF
        aSetter.setNPC();
        eManager.setup();

        gameState = titleState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS; // 0.01666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval; // nanoTime >> timeMillis

        while (gameThread != null) {

            // Update information
            update();
            // Draw the screen with updated information
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {

        if (gameState == playState) {

            // Player updating
            player.update();

            mapM.update();

            // Npc updating
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }
            }

            // Environment updating
            eManager.update();

        }
        if (gameState == pauseState) {
            // Game Paused
        }

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // TITLE SCREEN
        if (gameState == titleState) {
            ui.draw(g2d);
        }

        // OTHERS
        else {

            // WORLD DRAWING
            mapM.draw(g2d);

            // ADDING ENTITIES TO THE LIST
            entityList.add(player);

            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null)
                    entityList.add(npc[currentMap][i]);
            }

            for (int i = 0; i < obj[1].length; i++) {
                if (obj[currentMap][i] != null)
                    entityList.add(obj[currentMap][i]);
            }

            // SORTING ENTITIES
            Collections.sort(entityList, new Comparator<Entity>() {

                @Override
                public int compare(Entity e1, Entity e2) {

                    // Verifico che almeno una delle due entità sia un oggetto
                    // da disegnare sotto il player a priori
                    // In base al numero restituito, viene disegnata sopra o sotto
                    if (e1 instanceof PLOT_Empty && !(e2 instanceof PLOT_Empty))
                        return -1;
                    if (!(e1 instanceof PLOT_Empty) && e2 instanceof PLOT_Empty)
                        return 1;
                    if (e1 instanceof PLOT_Seed && !(e2 instanceof PLOT_Seed))
                        return -1;
                    if (!(e1 instanceof PLOT_Seed) && e2 instanceof PLOT_Seed)
                        return 1;
                    if (e1 instanceof PLOT_SeedGrowing && !(e2 instanceof PLOT_SeedGrowing))
                        return -1;
                    if (!(e1 instanceof PLOT_SeedGrowing) && e2 instanceof PLOT_SeedGrowing)
                        return 1;
                    if (e1 instanceof OBJ_BigRock && !(e2 instanceof OBJ_BigRock))
                        return -1;
                    if (!(e1 instanceof OBJ_BigRock) && e2 instanceof OBJ_BigRock)
                        return 1;
                    if (e1 instanceof OBJ_Tree && !(e2 instanceof OBJ_Tree))
                        return -1;
                    if (!(e1 instanceof OBJ_Tree) && e2 instanceof OBJ_Tree)
                        return 1;

                    // Per ordinare le entità in generale
                    int bottom1 = e1.gridY + e1.down2.getHeight();
                    int bottom2 = e2.gridY + e2.down2.getHeight();
                    int result = Integer.compare(bottom1, bottom2);

                    return result;
                }

            });

            // DRAWING ENTITIES
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2d);
            }

            // CLEARING ENTITY LIST
            entityList.clear();

            // ENVIRONMENT
            eManager.draw(g2d);

            // DRAWING UI
            ui.draw(g2d);

        }

        g2d.dispose();
    }

    public int getEmptyObjIndex() {
        for (int i = 0; i < obj[currentMap].length; i++) {
            if (obj[currentMap][i] == null) {
                return i; // trova il primo slot libero
            }
        }
        return -1; // niente slot libero disponibile
    }

    public void setAllWorldSettings() {
        worldCol[0] = 64;
        worldRow[0] = 64;
        worldCol[1] = 48;
        worldRow[1] = 64;
        worldCol[2] = 16;
        worldRow[2] = 13;
        worldCol[3] = 16;
        worldRow[3] = 14;
        worldCol[4] = 15;
        worldRow[4] = 29;
        worldCol[5] = 16;
        worldRow[5] = 13;

        for (int i = 0; i < maxMap; i++) {
            worldWidth[i] = worldCol[i] * tileSize;
            worldHeight[i] = worldRow[i] * tileSize;
        }
    }

    public void playMusic(int i) {

        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {

        music.stop();
    }

    public void playSoundEffect(int i) {
        soundEffect.setFile(i);
        soundEffect.play();
    }
}