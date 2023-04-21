package engine.rendering;

import javax.swing.JFrame;

import engine.Game;
import math.Vector2;

public class GameWindow {
	private static GameWindow instance;

	private JFrame frame;
	private GamePanel panel;

	private Vector2 size;

	private GameWindow(Vector2 size) {
		this.size = size;

		this.frame = Game.getInstance().getScreenManager().getFullScreenWindow();
		this.frame.setResizable(false);
		this.panel = new GamePanel(size);

		this.frame.add(panel);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setVisible(true);
	}

	public void repaint() {
		frame.repaint();
	}

	public static GameWindow getInstance() {
		if (instance != null)
			return instance;
		return null;
	}

	public static GameWindow initiateInstance(Vector2 size) {
		if (instance == null)
			instance = new GameWindow(size);
		return instance;
	}

	public JFrame getFrame() {
		return frame;
	}

	public GamePanel getPanel() {
		return panel;
	}

	public Vector2 getSize() {
		return size;
	}
}
