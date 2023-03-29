package engine.input;

import java.awt.AWTEvent;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import math.Vector2;

public class InputManager {
	private static Vector2 directionalInput;
	private static ArrayList<IMouseWheelEventListener> mouseWheelListeners;
	static ArrayList<IMouseActionEventListener> mouseActionListeners;

	private static boolean inputPaused;

	private InputManager() {
		inputPaused = false;
		directionalInput = new Vector2(0, 0);

		mouseWheelListeners = new ArrayList<>();
		mouseActionListeners = new ArrayList<>();

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
						case KeyEvent.VK_ESCAPE:
							System.exit(0);
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

		MouseWheelListener globalListener = new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				int notches = e.getWheelRotation();

				if (notches != 0) {
					fireMouseWheelMoved(notches);
				}
			}
		};

		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
			@Override
			public void eventDispatched(AWTEvent event) {
				if (event instanceof MouseEvent) {
					MouseEvent mouseEvent = (MouseEvent) event;
					if (mouseEvent.getID() == MouseEvent.MOUSE_WHEEL) {
						globalListener.mouseWheelMoved((MouseWheelEvent) event);
					}
				}
			}
		}, AWTEvent.MOUSE_WHEEL_EVENT_MASK);
	}

	public static Vector2 getDirectionalInput() {
		return directionalInput;
	}

	public static void initialize() {
		new InputManager();
	}

	public static void addMouseWheelListener(IMouseWheelEventListener listener) {
		if (listener != null)
			mouseWheelListeners.add(listener);
	}
	
	public static void addMouseActionListener(IMouseActionEventListener listener) {
		if(listener != null) mouseActionListeners.add(listener);
	}

	private void fireMouseWheelMoved(int notches) {
		for (IMouseWheelEventListener iMouseListener : mouseWheelListeners) {
			iMouseListener.wheelMoved(notches);
		}
	}
	
	public static void fireMousePressed(Vector2 location) {
		for(IMouseActionEventListener iMouseListener : mouseActionListeners) {
			iMouseListener.mousePressed(location);
		}
	}
	
	public static void fireMouseReleased(Vector2 location) {
		for(IMouseActionEventListener iMouseListener : mouseActionListeners) {
			iMouseListener.mouseReleased(location);
		}
	}


}
