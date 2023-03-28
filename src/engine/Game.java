package engine;

import java.awt.Dimension;
import java.awt.Toolkit;

import engine.rendering.GameWindow;
import engine.sprites.entities.Player;
import engine.time.DeltaTime;
import engine.world.GameWorld;
import math.Vector2;

public class Game implements Runnable {

	private static Game instance;
	private GameWorld currentWorld;
	private GameWindow window;
	private Vector2 resolution;


	private boolean isRunning;

	private Game() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		resolution = new Vector2((int) screenSize.getWidth(), (int) screenSize.getHeight());

		window = GameWindow.initiateInstance(resolution, true);
		currentWorld = new GameWorld(resolution);

		isRunning = true;

		Thread thread = new Thread(this);
		thread.start();

	}

	@Override
	public void run() {
		while (isRunning) {
			DeltaTime.updateDeltaTime();
			gameLoop();
		}
	}

	private void gameLoop() {
		window.repaint();
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
