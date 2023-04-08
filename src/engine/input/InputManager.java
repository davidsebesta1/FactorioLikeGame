package engine.input;

import java.awt.AWTEvent;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.Iterator;

import engine.Game;
import engine.rendering.Camera;
import engine.sprites.tiles.TileSprite;
import math.MathUtilities;
import math.Vector2;

public class InputManager {
	private static Vector2 directionalInput;
	private static ArrayList<IMouseWheelEventListener> mouseWheelListeners;
	private static ArrayList<IMouseActionEventListener> mouseActionListeners;
	private static ArrayList<IMouseMotionEventListener> mouseMotionListeners;

	private static boolean inputPaused;

	private InputManager() {
		inputPaused = false;
		directionalInput = new Vector2(0, 0);

		mouseWheelListeners = new ArrayList<>();
		mouseActionListeners = new ArrayList<>();
		mouseMotionListeners = new ArrayList<>();

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

		MouseMotionListener globalMouseMotionListener = new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				fireMouseMotion(new Vector2(e.getX(), e.getY()));
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// Unused

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

		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
			@Override
			public void eventDispatched(AWTEvent event) {
				if (event instanceof MouseEvent) {
					MouseEvent mouseEvent = (MouseEvent) event;
					if (mouseEvent.getID() == MouseEvent.MOUSE_MOVED) {
						globalMouseMotionListener.mouseMoved((MouseEvent) event);
					}
				}
			}
		}, AWTEvent.MOUSE_MOTION_EVENT_MASK);
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
		if (listener != null)
			mouseActionListeners.add(listener);
	}

	public static void addMouseMotionListener(IMouseMotionEventListener listener) {
		if (listener != null)
			mouseMotionListeners.add(listener);
	}

	private void fireMouseWheelMoved(int notches) {
		for (IMouseWheelEventListener iMouseListener : mouseWheelListeners) {
			iMouseListener.wheelMoved(notches);
		}
	}

	public static void fireMousePressed(Vector2 location) {
		for (IMouseActionEventListener iMouseListener : mouseActionListeners) {
			iMouseListener.mousePressed(location);
		}

		checkForTileClick(MathUtilities.screenToWorldCoordinates(location));
	}

	public static void fireMouseReleased(Vector2 location) {
		for (IMouseActionEventListener iMouseListener : mouseActionListeners) {
			iMouseListener.mouseReleased(location);
		}
	}

	public static void fireMouseMotion(Vector2 location) {
		for (IMouseMotionEventListener iMouseListener : mouseMotionListeners) {
			iMouseListener.mouseMoved(location);
		}
	}

	private static void checkForTileClick(Vector2 worldCoordinates) {
		TileSprite[][] tileSprites = Game.getInstance().getCurrentWorld().getTiles().getMap();

		for (int i = 0; i < tileSprites.length; i++) {
			for (int j = 0; j < tileSprites[i].length; j++) {
				if (isWithinTileBounds(worldCoordinates, tileSprites[i][j])) {
					System.out.println("Coords: " + worldCoordinates + " sprite: " + tileSprites[i][j]);
					tileSprites[i][j].onMouseClicked();
					return;
				}
			}
		}
	}

	private static boolean isWithinTileBounds(Vector2 worldCoordinates, TileSprite sprite) {
		if (sprite != null) {
			return worldCoordinates.getX() >= sprite.getLocation().getX()
					&& worldCoordinates.getX() < sprite.getLocation().getX() + sprite.getSize().getX()
					&& worldCoordinates.getY() >= sprite.getLocation().getY()
					&& worldCoordinates.getY() < sprite.getLocation().getY() + sprite.getSize().getY();
		}

		return false;
	}

}
