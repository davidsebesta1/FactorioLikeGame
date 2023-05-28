package engine.input;

import math.Vector2;

/**
 * IMouseActionEventListener is created for sprite that may require input from user. The InputManager class then handles the input accordingly and fires appropriate events.
 * @see InputManager
 * @author David Šebesta
 * @version 1.0.0
 */
public interface IMouseActionEventListener {
	public abstract void mousePrimaryPressed(Vector2 screenCoordinate);
	public abstract void mousePrimaryReleased(Vector2 screenCoordinate);
	
	public abstract void mouseSecondaryPressed(Vector2 screenCoordinate);
	public abstract void mouseSecondaryReleased(Vector2 screenCoordinate);
	
	public abstract void mouseMiddlePressed(Vector2 screenCoordinate);
	public abstract void mouseMiddleReleased(Vector2 screenCoordinate);
}
