package engine.input;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import engine.Game;
import math.Vector2;

public class InputManager {
	private static Vector2 directionalInput;

	private static boolean inputPaused;

	private InputManager() {
		inputPaused = false;
		directionalInput = new Vector2(0, 0);

		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
				if (e.getID() == KeyEvent.KEY_PRESSED) {
					if (!inputPaused) {
						switch (e.getKeyCode()) {
						case KeyEvent.VK_W:
							directionalInput.setY(-1);
							break;
						case KeyEvent.VK_S:
							directionalInput.setY(1);
							break;
						case KeyEvent.VK_A:
							directionalInput.setX(-1);
							break;
						case KeyEvent.VK_D:
							directionalInput.setX(1);
							break;
						}
					}
				} else if (e.getID() == KeyEvent.KEY_RELEASED) {
					if (!inputPaused) {
						switch (e.getKeyCode()) {
						case KeyEvent.VK_W:
						case KeyEvent.VK_S:
							directionalInput.setY(0);
							break;
						case KeyEvent.VK_A:
						case KeyEvent.VK_D:
							directionalInput.setX(0);
							break;
						}
					}
				}
				return false;
			}
		});
	}

	public static Vector2 getDirectionalInput() {
		return directionalInput;
	}

	public static void initialize() {
		new InputManager();
	}

}
