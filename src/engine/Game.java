package engine;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Toolkit;

import engine.input.InputManager;
import engine.physics.PhysicsManager;
import engine.rendering.GameWindow;
import engine.rendering.optimalization.ChunkManager;
import engine.rendering.textures.TextureLibrary;
import engine.rendering.textures.TextureType;
import engine.sprites.Background;
import engine.sprites.SpriteManager;
import engine.sprites.entities.player.Player;
import engine.sprites.ores.OreMap;
import engine.sprites.structures.StructureMap;
import engine.sprites.tiles.TileMap;
import engine.time.DeltaTime;
import engine.world.GameWorld;
import main.Log;
import math.Vector2;
import notmycode.ScreenManager;

public class Game implements Runnable {

	private static Game instance;

	private ScreenManager screen;
	private GameWorld currentWorld;
	private GameWindow window;
	private Vector2 resolution;

	private TextureLibrary tl;

	private static final int TARGET_FPS = 144;

	private long lastLoopTime = System.nanoTime();
	private double unprocessedTime = 0;

	private int framesPerSecond = 0;

	private boolean isRunning;

	private static final DisplayMode POSSIBLE_MODES[] = { new DisplayMode(1920, 1080, 16, 0)};

	private Game() {
		isRunning = true;
		instance = this;

		// INITALIZE FIRST
		InputManager.initialize();
		Log.initilize();
		tl = new TextureLibrary(); // textures
		tl.loadAllTextures("textures/tile", TextureType.OPAQUE); // folder name as param + texture type
		tl.loadAllTextures("textures/structures", TextureType.OPAQUE);
		tl.loadAllTextures("textures/ores", TextureType.BITMASK);
		tl.loadAllTextures("textures/items", TextureType.BITMASK);
		tl.loadAllTextures("textures/icons", TextureType.OPAQUE);
		tl.loadAllTextures("textures/UIElements", TextureType.TRANSPARENT);
		tl.loadAllTextures("textures", TextureType.OPAQUE);

		// AND SECOND
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		resolution = new Vector2((int) screenSize.getWidth(), (int) screenSize.getHeight());
		double size = 1024d;
		currentWorld = new GameWorld(new Vector2(size, size));

		// WINDOW AS LAST
		screen = new ScreenManager();
		DisplayMode displayMode = screen.findFirstCompatibleMode(POSSIBLE_MODES);
		screen.setFullScreen(displayMode);
		window = GameWindow.initiateInstance(resolution);
		
		window.getPanel().init();

		// FINALLY GAME LOOP
		Thread thread = new Thread(this);
		thread.start();
		thread.setName("GameLoopThread");
		thread.setPriority(Thread.MAX_PRIORITY);
	}

	@Override
	public void run() {
		while (isRunning) {
			long now = System.nanoTime();
			long elapsedTime = now - lastLoopTime;
			lastLoopTime = now;

			unprocessedTime += elapsedTime / 1000000000.0; // Convert to seconds

			//Add sprites from queue to actual list
			SpriteManager.frameSpriteSynchronization();
			PhysicsManager.framePhysicsSpriteSync();
			
			getCurrentWorld().getChunkManager().resolveAll();
			
			//Click, mouse moved, etc events
			InputManager.runAllEvents();
			
			//Update delta time
			double deltaTime = 1.0 / TARGET_FPS;
			DeltaTime.updateDeltaTime(deltaTime);
			

			//Update updatable sprites
			while (unprocessedTime >= (1.0 / TARGET_FPS)) {
				// Update game logic
				SpriteManager.updateAllSprites();

				unprocessedTime -= (1.0 / TARGET_FPS);
			}

			// Resolve collisions for physics sprites
			PhysicsManager.resolveCollisions();

			// Update chunks
			getCurrentWorld().getChunkManager().updateActiveChunks();
			getCurrentWorld().getChunkManager().runUpdateQueue();

			// Render the scene
			window.repaint();

			// Frame rate lock
			long timeToSleep = (long) ((1000.0 / TARGET_FPS) - (System.nanoTime() - lastLoopTime) / 1000000.0);
			if (timeToSleep > 0) {
				try {
					Thread.sleep(timeToSleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
	}

	public static Game getInstance() {
		if (instance != null) return instance;
		return initializeInstance();
	}

	public static Game initializeInstance() {
		if (instance == null)
			return new Game();
		return instance;
	}

	public GameWindow getWindow() {
		return window;
	}

	public Vector2 getResolution() {
		return resolution;
	}

	public GameWorld getCurrentWorld() {
		return currentWorld;
	}

	public void setCurrentWorld(GameWorld currentWorld) {
		this.currentWorld = currentWorld;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public int getFramesPerSecond() {
		return framesPerSecond;
	}

	public void setFramesPerSecond(int framesPerSecond) {
		this.framesPerSecond = framesPerSecond;
	}

	public ScreenManager getScreenManager() {
		return screen;
	}

	public Background getBackground() {
		return currentWorld.getBackground();
	}

	public Player getPlayer() {
		return currentWorld.getPlayer();
	}

	public TileMap getTileMap() {
		return currentWorld.getTileMap();
	}

	public StructureMap getStructureMap() {
		return currentWorld.getStructureMap();
	}

	public ChunkManager getChunkManager() {
		return currentWorld.getChunkManager();
	}

	public OreMap getOreMap() {
		return currentWorld.getOreMap();
	}
}
