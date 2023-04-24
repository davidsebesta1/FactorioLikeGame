package engine.input;

import math.Vector2;

public interface IMouseActionEventListener {
	public abstract void mousePrimaryPressed(Vector2 screenCoordinate);
	public abstract void mousePrimaryReleased(Vector2 screenCoordinate);
	
	public abstract void mouseSecondaryPressed(Vector2 screenCoordinate);
	public abstract void mouseSecondaryReleased(Vector2 screenCoordinate);
	
	public abstract void mouseMiddlePressed(Vector2 screenCoordinate);
	public abstract void mouseMiddleReleased(Vector2 screenCoordinate);
}
