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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import engine.Game;
import engine.sprites.Sprite;
import engine.sprites.entities.player.UI.StructureButton;
import engine.sprites.entities.player.UI.StructureTypeButton;
import engine.sprites.tiles.TileSprite;
import engine.world.mainmenu.Button;
import main.Log;
import math.MathUtilities;
import math.Vector2;


/**
 * InputManager is a static class that provides input handling and logging. A single instance of this class is created on program launch.
 * @author David Šebesta
 * @version 1.0.0
 */
public class InputManager {
	private static Vector2 directionalInput;
	private static ArrayList<IMouseWheelEventListener> mouseWheelListeners;
	private static ArrayList<IMouseActionEventListener> mouseActionListeners;
	private static ArrayList<IMouseMotionEventListener> mouseMotionListeners;

	private static int wheelChange = 0;

	private static boolean runLMBPressed = false;
	private static boolean runRMBPressed = false;
	private static boolean runMMBPressed = false;
	private static Vector2 pressedPosition = new Vector2(0, 0);

	private static boolean runLMBReleased = false;
	private static boolean runRMBReleased = false;
	private static boolean runMMBReleased = false;
	private static Vector2 releasedPosition = new Vector2(0, 0);

	private static Vector2 motionVector = Vector2.zero;

	private static boolean inputPaused;

	/**
	 * A class constructor which created instances of key event dispatchers and other disptachers to track down user input.
	 */
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
							Game.getInstance().getPlayer().setShowMainMenu(!Game.getInstance().getPlayer().canShowMainMenu());
							break;
						case KeyEvent.VK_R:
							Game.getInstance().getPlayer().getConstructManager().tryRotateCurrentlySelected();
							break;
							
//						case KeyEvent.VK_F5:
//							Game.getInstance().getCurrentWorld().SaveWorld();
//							break;
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
		Log.info("Initialized input manager");
	}

	public static void runAllEvents() {
		if (wheelChange != 0) {
			fireMouseWheelMoved(wheelChange);
			wheelChange = 0;
		}

		if (runLMBPressed) {
			fireMousePrimaryPressed(pressedPosition);
			runLMBPressed = false;
		}

		if (runLMBReleased) {
			fireMousePrimaryReleased(releasedPosition);
			runLMBReleased = false;
		}

		if (runRMBPressed) {
			fireMouseSecondaryPressed(pressedPosition);
			runRMBPressed = false;
		}

		if (runRMBReleased) {
			fireMouseSecondaryReleased(releasedPosition);
			runRMBReleased = false;
		}

		if (runMMBPressed) {
			//todo, useless anyways
			runMMBPressed = false;
		}

		if (runMMBReleased) {
			//todo, uselsss anyways
			runMMBReleased = false;
		}

		if (motionVector != Vector2.zero) {
			fireMouseMotion(motionVector);
			motionVector = Vector2.zero;
		}
	}

	/**
	 * Static function to add new mouse wheel listener
	 * @param IMouseWheelEventListener listener
	 * @see IMouseWheelEventListener
	 */
	public static void addMouseWheelListener(IMouseWheelEventListener listener) {
		if (listener != null)
			mouseWheelListeners.add(listener);
	}

	/**
	 * Static function to add new mouse action (click) listener
	 * @param IMouseActionEventListener listener
	 * @see IMouseActionEventListener
	 */
	public static void addMouseActionListener(IMouseActionEventListener listener) {
		if (listener != null)
			mouseActionListeners.add(listener);
	}
	
	/**
	 * Static function to add new mouse motion listener
	 * @param IMouseMotionEventListener listener
	 * @see IMouseMotionEventListener
	 */
	public static void addMouseMotionListener(IMouseMotionEventListener listener) {
		if (listener != null)
			mouseMotionListeners.add(listener);
	}
	/**
	 * Private functions that fires a event on all listeners which listen to it
	 * @param amount of notching of wheel moved
	 */
	private static void fireMouseWheelMoved(int notches) {
		for (IMouseWheelEventListener iMouseListener : mouseWheelListeners) {
			iMouseListener.wheelMoved(notches);
		}
	}

	/**
	 * Private function that fires a event on all listeners which listen to it
	 * @param Vector2 location on screen
	 */
	private static void fireMousePrimaryPressed(Vector2 location) {
		if (!checkForMainMenuUIClick(location)) {
			if (!checkForInventoryUIClick(location)) {
				checkForTileClick(MathUtilities.screenToWorldCoordinates(location));
			}
		}

	}

	private static void fireMousePrimaryReleased(Vector2 location) {
		//TODO since I dont use it
	}

	/**
	 * Reset for construction manager
	 * @param location where player clicked
	 * @see PlayerConstructionManager
	 */
	private static void fireMouseSecondaryPressed(Vector2 location) {
		Game.getInstance().getCurrentWorld().getPlayer().getConstructManager().setCurrentlySelected(null);

	}

	private static void fireMouseSecondaryReleased(Vector2 location) {
		//TODO since I dont use it
	}

	/**
	 * Private function that fires a event on all listeners which listen to it
	 * @param Vector2 location on screen
	 */
	public static void fireMouseMotion(Vector2 location) {
		for (IMouseMotionEventListener iMouseListener : mouseMotionListeners) {
			iMouseListener.mouseMoved(location);
		}
	}

	/**
	 * Checking if any TileSprite on a grid has been clicked on. If yes, fire mouse clicked on it.
	 * @param worldCoordinates of a possible tile click
	 * @return boolean if any tile has been clicked on
	 */
	private static boolean checkForTileClick(Vector2 worldCoordinates) {
		TileSprite[][] tileSprites = Game.getInstance().getCurrentWorld().getTileMap().getMap();

		for (int i = 0; i < tileSprites.length; i++) {
			for (int j = 0; j < tileSprites[i].length; j++) {
				if (isWithinBounds(worldCoordinates, tileSprites[i][j])) {
					Log.info("Coords: " + worldCoordinates + " sprite: " + tileSprites[i][j]);
					tileSprites[i][j].onMouseClicked();
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Checks for all inventory buttons if they have been clicked on and fires mouse pressed event if true.
	 * @param screen coordinates of a click
	 * @return
	 */
	private static boolean checkForInventoryUIClick(Vector2 clickCoords) {
		if(!Game.getInstance().getPlayer().canShowBuildMenu()) return false;
		
		HashMap<StructureTypeButton, HashSet<StructureButton>> all = Game.getInstance().getCurrentWorld().getPlayer().getConstructManager().getCategoryAndAvailableStructures();

		for (Map.Entry<StructureTypeButton, HashSet<StructureButton>> entry : all.entrySet()) {
			StructureTypeButton button = entry.getKey();
			HashSet<StructureButton> availableStructures = entry.getValue();
			if (isWithinBounds(clickCoords, button) && button.isVisible()) {
				button.mousePrimaryPressed(clickCoords);
				Log.info("Clicked on typebutton " + button);
				return true;
			}
			for (StructureButton structure : availableStructures) {
				if (isWithinBounds(clickCoords, structure) && structure.isVisible()) {
					Game.getInstance().getPlayer().setBuildingModeEnabled(true);
					structure.mousePrimaryPressed(clickCoords);
					Log.info("Clicked on structurebutton " + structure);

					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Checks for main menu UI clicks and fire mouse events.
	 * @param screen coordinates of a click
	 * @return If any button clicked
	 */
	private static boolean checkForMainMenuUIClick(Vector2 coordinates) {
		if(!Game.getInstance().getPlayer().canShowMainMenu()) return false;
		
		ArrayList<Button> buttons = Game.getInstance().getPlayer().getMenuManager().getButtons();

		for (Button button : buttons) {
			if (isWithinBounds(coordinates, button) && button.isVisible()) {
				button.mousePrimaryPressed(coordinates);

				System.out.println(button);
				Log.info("Clicked on menu button " + button);
				return true;
			}
		}

		return false;
	}

	/**
	 * Checks if sprite is within world coordinates inside paramaters
	 * @param worldCoordinates
	 * @param sprite
	 * @return if the sprite is within worldCoordinates
	 */
	private static boolean isWithinBounds(Vector2 worldCoordinates, Sprite sprite) {
		if (sprite != null) {
			return worldCoordinates.getX() >= sprite.getLocation().getX()
					&& worldCoordinates.getX() < sprite.getLocation().getX() + sprite.getSize().getX()
					&& worldCoordinates.getY() >= sprite.getLocation().getY()
					&& worldCoordinates.getY() < sprite.getLocation().getY() + sprite.getSize().getY();
		}

		return false;
	}
	/**
	 * Checks if StructureTypeButton is within world coordinates inside paramaters
	 * @param worldCoordinates
	 * @param StructureTypeButton
	 * @return if the StructureTypeButton is within worldCoordinates
	 */
	private static boolean isWithinBounds(Vector2 worldCoordinates, StructureTypeButton sprite) {
		if (sprite != null) {
			return worldCoordinates.getX() >= sprite.getLocation().getX()
					&& worldCoordinates.getX() < sprite.getLocation().getX() + sprite.getSize().getX()
					&& worldCoordinates.getY() >= sprite.getLocation().getY()
					&& worldCoordinates.getY() < sprite.getLocation().getY() + sprite.getSize().getY();
		}

		return false;
	}
	/**
	 * Checks if StructureButton is within world coordinates inside paramaters
	 * @param worldCoordinates
	 * @param StructureButton
	 * @return if the StructureButton is within worldCoordinates
	 */
	private static boolean isWithinBounds(Vector2 worldCoordinates, StructureButton sprite) {
		if (sprite != null) {
			return worldCoordinates.getX() >= sprite.getLocation().getX()
					&& worldCoordinates.getX() < sprite.getLocation().getX() + sprite.getSize().getX()
					&& worldCoordinates.getY() >= sprite.getLocation().getY()
					&& worldCoordinates.getY() < sprite.getLocation().getY() + sprite.getSize().getY();
		}

		return false;
	}

	/**
	 * Checks if Button is within world coordinates inside paramaters
	 * @param worldCoordinates
	 * @param Button
	 * @return if the Button is within worldCoordinates
	 */
	private static boolean isWithinBounds(Vector2 worldCoordinates, Button sprite) {
		if (sprite != null) {
			return worldCoordinates.getX() >= sprite.getLocation().getX()
					&& worldCoordinates.getX() < sprite.getLocation().getX() + sprite.getSize().getX()
					&& worldCoordinates.getY() >= sprite.getLocation().getY()
					&& worldCoordinates.getY() < sprite.getLocation().getY() + sprite.getSize().getY();
		}

		return false;
	}

	public static Vector2 getMotionVector() {
		return motionVector;
	}

	public static void setMotionVector(Vector2 motionVector) {
		InputManager.motionVector = motionVector;
	}

	public static boolean isInputPaused() {
		return inputPaused;
	}

	public static void setInputPaused(boolean inputPaused) {
		InputManager.inputPaused = inputPaused;
	}

	public static void setWheelChange(int wheelChange) {
		InputManager.wheelChange = wheelChange;
	}

	public static void setRunLMBPressed(boolean runLMBPressed) {
		InputManager.runLMBPressed = runLMBPressed;
	}

	public static void setRunRMBPressed(boolean runRMBPressed) {
		InputManager.runRMBPressed = runRMBPressed;
	}

	public static void setRunMMBPressed(boolean runMMBPressed) {
		InputManager.runMMBPressed = runMMBPressed;
	}

	public static void setPressedPosition(Vector2 pressedPosition) {
		InputManager.pressedPosition = pressedPosition;
	}

	public static void setRunLMBReleased(boolean runLMBReleased) {
		InputManager.runLMBReleased = runLMBReleased;
	}

	public static void setRunRMBReleased(boolean runRMBReleased) {
		InputManager.runRMBReleased = runRMBReleased;
	}

	public static void setRunMMBReleased(boolean runMMBReleased) {
		InputManager.runMMBReleased = runMMBReleased;
	}

	public static void setReleasedPosition(Vector2 releasedPosition) {
		InputManager.releasedPosition = releasedPosition;
	}

}
