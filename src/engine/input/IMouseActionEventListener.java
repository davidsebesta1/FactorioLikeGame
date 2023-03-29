package engine.input;

import math.Vector2;

public interface IMouseActionEventListener {
	public void mousePressed(Vector2 screenCoordinate);
	public void mouseReleased(Vector2 screenCoordinate);
}
