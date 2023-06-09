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
import engine.world.World;
import engine.world.mainmenu.MainMenuWorld;
import main.Log;
import math.Vector2;
import notmycode.ScreenManager;

/**
 * A game object itself, a core of the game. This class does everything from loading textures, handling frames to managing game loop.
 * @author David Šebesta
 *
 */
public class Game implements Runnable {

	private static Game instance;

	private ScreenManager screen;
	private World currentWorld;
	private GameWindow window;
	private Vector2 resolution;

	private TextureLibrary tl;

	private static final int TARGET_FPS = 144;

	private long lastLoopTime = System.nanoTime();
	private double unprocessedTime = 0;

	private int framesPerSecond = 0;

	private boolean isRunning;

	private static final DisplayMode POSSIBLE_MODES[] = { new DisplayMode(1920, 1080, 16, 0), new DisplayMode(3840, 2160, 16, 0), new DisplayMode(1366, 768, 16, 0), new DisplayMode(1600, 800, 16, 0), new DisplayMode(1920, 1090, 8, 0), new DisplayMode(1536, 864, 8, 0) };

	/**
	 * Class constructor
	 */
	private Game() {
		isRunning = true;
		instance = this;

		// INITALIZE FIRST
		Log.initilize();
		InputManager.initialize();
		tl = new TextureLibrary(); // textures
		tl.loadAllTextures("textures/tile", TextureType.OPAQUE); // folder name as param + texture type
		tl.loadAllTextures("textures/structures", TextureType.OPAQUE);
		tl.loadAllTextures("textures/ores", TextureType.BITMASK);
		tl.loadAllTextures("textures/items", TextureType.BITMASK);
		tl.loadAllTextures("textures/icons", TextureType.OPAQUE);
		tl.loadAllTextures("textures/icons/typeIcons", TextureType.BITMASK);
		tl.loadAllTextures("textures/UIElements", TextureType.TRANSPARENT);
		tl.loadAllTextures("textures", TextureType.OPAQUE);

		Log.info("Loaded all textures");

		// AND SECOND
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		resolution = new Vector2((int) screenSize.getWidth(), (int) screenSize.getHeight());
		double size = 4096d;
		currentWorld = new MainMenuWorld(new Vector2(size, size));

		// WINDOW AS LAST
		screen = new ScreenManager();
		DisplayMode displayMode = screen.findFirstCompatibleMode(POSSIBLE_MODES);
		screen.setFullScreen(displayMode);
		window = GameWindow.initiateInstance(resolution);

		window.getPanel().init();

		Log.info("Initialized window");

		// FINALLY GAME LOOP
		Thread thread = new Thread(this);
		thread.start();
		thread.setName("GameLoopThread");
		thread.setPriority(Thread.MAX_PRIORITY);

		Log.info("Started game thread");
	}

	/**
	 * Game loop
	 */
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

	/**
	 * Returns a instance
	 * @return Instance of Game
	 */
	public static Game getInstance() {
		if (instance != null)
			return instance;
		return initializeInstance();
	}

	/**
	 * Initializes the instance
	 * @return Instance of Game
	 */
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

	public World getCurrentWorld() {
		return currentWorld;
	}

	public void setCurrentWorld(World currentWorld) {
		SpriteManager.clearAll();
		PhysicsManager.clearAll();

		this.currentWorld = currentWorld;

		getCurrentWorld().getChunkManager().resolveAll();
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
