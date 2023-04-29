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
import math.MathUtilities;
import math.Vector2;

public class InputManager {
	private static Vector2 directionalInput;
	private static ArrayList<IMouseWheelEventListener> mouseWheelListeners;
	private static ArrayList<IMouseActionEventListener> mouseActionListeners;
	private static ArrayList<IMouseMotionEventListener> mouseMotionListeners;
	
	private static int wheelChange = 0;
	
	private static boolean runLMBPressed = false;
	private static boolean runRMBPressed = false;
	private static boolean runMMBPressed = false;
	private static Vector2 pressedPosition = new Vector2(0,0);
	
	private static boolean runLMBReleased = false;
	private static boolean runRMBReleased = false;
	private static boolean runMMBReleased = false;
	private static Vector2 releasedPosition = new Vector2(0,0);
	
	private static Vector2 motionVector = Vector2.zero;
	
	//TODO run click events once per frame

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
	
	public static void runAllEvents() {
		if(wheelChange != 0) {
			fireMouseWheelMoved(wheelChange);
			wheelChange = 0;
		}
		
		if(runLMBPressed) {
			fireMousePrimaryPressed(pressedPosition);
			runLMBPressed = false;
		}
		
		if(runLMBReleased) {
			fireMousePrimaryReleased(releasedPosition);
			runLMBReleased = false;
		}
		
		if(runRMBPressed) {
			fireMouseSecondaryPressed(pressedPosition);
			runRMBPressed = false;
		}
		
		if(runRMBReleased) {
			fireMouseSecondaryReleased(releasedPosition);
			runRMBReleased = false;
		}
		
		if(runMMBPressed) {
			//todo, useless anyways
			runMMBPressed = false;
		}
		
		if(runMMBReleased) {
			//todo, uselsss anyways
			runMMBReleased = false;
		}
		
		if(motionVector != Vector2.zero) {
			fireMouseMotion(motionVector);
			motionVector = Vector2.zero;
		}
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

	private static void fireMouseWheelMoved(int notches) {
		for (IMouseWheelEventListener iMouseListener : mouseWheelListeners) {
			iMouseListener.wheelMoved(notches);
		}
	}

	private static void fireMousePrimaryPressed(Vector2 location) {
		if(!checkForInventoryUIClick(location)) {
			checkForTileClick(MathUtilities.screenToWorldCoordinates(location));
		}
		
	}

	private static void fireMousePrimaryReleased(Vector2 location) {
		
	}
	
	private static void fireMouseSecondaryPressed(Vector2 location) {
		Game.getInstance().getCurrentWorld().getPlayer().getConstructManager().setCurrentlySelected(null);
		
	}

	private static void fireMouseSecondaryReleased(Vector2 location) {
		
	}

	private static void fireMouseMotion(Vector2 location) {
		for (IMouseMotionEventListener iMouseListener : mouseMotionListeners) {
			iMouseListener.mouseMoved(location);
		}
	}

	private static boolean checkForTileClick(Vector2 worldCoordinates) {
		TileSprite[][] tileSprites = Game.getInstance().getCurrentWorld().getTileMap().getMap();

		for (int i = 0; i < tileSprites.length; i++) {
			for (int j = 0; j < tileSprites[i].length; j++) {
				if (isWithinBounds(worldCoordinates, tileSprites[i][j])) {
					System.out.println("Coords: " + worldCoordinates + " sprite: " + tileSprites[i][j]);
					tileSprites[i][j].onMouseClicked();
					return true;
				}
			}
		}
		
		return false;
	}
	
	private static boolean checkForInventoryUIClick(Vector2 clickCoords) {
		HashMap<StructureTypeButton, HashSet<StructureButton>> all = Game.getInstance().getCurrentWorld().getPlayer().getConstructManager().getCategoryAndAvailableStructures();
		
		for (Map.Entry<StructureTypeButton, HashSet<StructureButton>> entry : all.entrySet()) {
		    StructureTypeButton button = entry.getKey();
		    HashSet<StructureButton> availableStructures = entry.getValue();
		    if(isWithinBounds(clickCoords, button) && button.isVisible()) {
		    	button.mousePrimaryPressed(clickCoords);
		    	System.out.println(button);
		    	return true;
		    }
		    for (StructureButton structure : availableStructures) {
		    	if(isWithinBounds(clickCoords, structure) && structure.isVisible()) {
			    	structure.mousePrimaryPressed(clickCoords);
			    	System.out.println(structure);
			    	return true;
			    }
		    }
		}
		
		return false;
	}

	private static boolean isWithinBounds(Vector2 worldCoordinates, Sprite sprite) {
		if (sprite != null) {
			return worldCoordinates.getX() >= sprite.getLocation().getX()
					&& worldCoordinates.getX() < sprite.getLocation().getX() + sprite.getSize().getX()
					&& worldCoordinates.getY() >= sprite.getLocation().getY()
					&& worldCoordinates.getY() < sprite.getLocation().getY() + sprite.getSize().getY();
		}

		return false;
	}
	
	private static boolean isWithinBounds(Vector2 worldCoordinates, StructureTypeButton sprite) {
		if (sprite != null) {
			return worldCoordinates.getX() >= sprite.getLocation().getX()
					&& worldCoordinates.getX() < sprite.getLocation().getX() + sprite.getSize().getX()
					&& worldCoordinates.getY() >= sprite.getLocation().getY()
					&& worldCoordinates.getY() < sprite.getLocation().getY() + sprite.getSize().getY();
		}

		return false;
	}
	
	private static boolean isWithinBounds(Vector2 worldCoordinates, StructureButton sprite) {
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
