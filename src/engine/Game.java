package engine;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;

import engine.input.InputManager;
import engine.rendering.GameWindow;
import engine.rendering.SpriteManager;
import engine.sprites.Sprite;
import engine.time.DeltaTime;
import engine.world.GameWorld;
import math.Vector2;

public class Game implements Runnable {

	private static Game instance;
	private GameWorld currentWorld;
	private GameWindow window;
	private Vector2 resolution;

	private final int TARGET_FPS = 60;
	private final long OPTIMAL_TIME = 1000000000 / TARGET_FPS; // One billion nanoseconds per second

	private long lastLoopTime = System.nanoTime();
	private double unprocessedTime = 0;

	ArrayList<Sprite> arr = new ArrayList<>();

	private boolean isRunning;

	private Game() {
		isRunning = true;
		instance = this;

		// INITALIZE FIRST
		InputManager.initialize();

		// AND SECOND
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		resolution = new Vector2((int) screenSize.getWidth(), (int) screenSize.getHeight());
		currentWorld = new GameWorld(resolution);

		// WINDOW AS LAST
		window = GameWindow.initiateInstance(resolution, true);

		// FINALLY GAME LOOP
		Thread thread = new Thread(this);
		thread.start();

	}

	@Override
	public void run() {
		while (isRunning) {
			long now = System.nanoTime();
			long elapsedTime = now - lastLoopTime;
			lastLoopTime = now;

			unprocessedTime += elapsedTime / 1000000000.0; // Convert to seconds

			while (unprocessedTime >= (1.0 / TARGET_FPS)) {
				// Update game logic
				double deltaTime = 1.0 / TARGET_FPS;
				DeltaTime.updateDeltaTime(deltaTime);
				SpriteManager.updateAllSprites();

				unprocessedTime -= (1.0 / TARGET_FPS);
			}

			// Render the scene
			window.repaint();

			long sleepTime = (OPTIMAL_TIME - (System.nanoTime() - lastLoopTime)) / 1000000; // Convert to milliseconds
			if (sleepTime > 0) {
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {}
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
}
