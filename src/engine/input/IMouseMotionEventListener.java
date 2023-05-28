package engine.input;

import math.Vector2;

/**
 * IMouseActionEventListener is created for sprite that may require input once the mouse moved. The InputManager class then handles the input accordingly and fires appropriate events.
 * @see InputManager
 * @author David Šebesta
 * @version 1.0.0
 */
public interface IMouseMotionEventListener {
	public abstract void mouseMoved(Vector2 newLocation);
}
