package engine;

import java.awt.Dimension;
import java.awt.Toolkit;

import engine.input.InputManager;
import engine.physics.PhysicsManager;
import engine.rendering.GameWindow;
import engine.rendering.textures.TextureLibrary;
import engine.sprites.SpriteManager;
import engine.time.DeltaTime;
import engine.world.GameWorld;
import main.Log;
import math.Vector2;

public class Game implements Runnable {

	private static Game instance;
	private GameWorld currentWorld;
	private GameWindow window;
	private Vector2 resolution;

	private TextureLibrary tl;

	private final int TARGET_FPS = 60;
	private final long OPTIMAL_TIME = 1000000000 / TARGET_FPS; // One billion nanoseconds per second

	private long lastLoopTime = System.nanoTime();
	private double unprocessedTime = 0;

	private int framesPerSecond = 0;

	private boolean isRunning;

	private Game() {
		isRunning = true;
		instance = this;
		
//		System.setProperty("sun.java2d.opengl", "true");

		// INITALIZE FIRST
		InputManager.initialize();
		Log.initilize();
		tl = new TextureLibrary(); // textures
		tl.loadAllTextures("textures"); // folder name as param

		// AND SECOND
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		resolution = new Vector2((int) screenSize.getWidth(), (int) screenSize.getHeight());
		float size = 1024f;
		currentWorld = new GameWorld(new Vector2(size, size));

		// WINDOW AS LAST
		window = GameWindow.initiateInstance(resolution, true);

		// FINALLY GAME LOOP
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		long lastFpsTime = System.nanoTime();
		int fps = 0;

		while (isRunning) {
			long now = System.nanoTime();
			long elapsedTime = now - lastLoopTime;
			lastLoopTime = now;

			unprocessedTime += elapsedTime / 1000000000.0; // Convert to seconds

			double deltaTime = 1.0 / TARGET_FPS;
			DeltaTime.updateDeltaTime(deltaTime);
			
			while (unprocessedTime >= (1.0 / TARGET_FPS)) {
				// Update game logic
				SpriteManager.updateAllSprites();

				unprocessedTime -= (1.0 / TARGET_FPS);
			}

			// Resolve collisions for physics sprites
			PhysicsManager.resolveCollisions();
			
			
			//Update chunks
			Game.getInstance().getCurrentWorld().getChunkManager().updateActiveChunks();

			// Render the scene
			window.repaint();

			fps++;

			// Update and print FPS once per second
			if (System.nanoTime() - lastFpsTime >= 1000000000) { // One second has elapsed
				framesPerSecond = fps;
				fps = 0;
				lastFpsTime = System.nanoTime();
			}

			long sleepTime = (OPTIMAL_TIME - (System.nanoTime() - lastLoopTime)) / 1000000; // Convert to milliseconds
			if (sleepTime > 0) {
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
				}
			}
		}
	}

	public static Game getInstance() {
		if (instance != null)
			return instance;
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
}
