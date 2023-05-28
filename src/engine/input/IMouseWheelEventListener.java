package engine.input;

/**
 * IMouseActionEventListener is created for sprite that may require input from user once mouse wheel moved. The InputManager class then handles the input accordingly and fires appropriate events.
 * @see InputManager
 * @author David Šebesta
 * @version 1.0.0
 */
public interface IMouseWheelEventListener {
	public void wheelMoved(int notches);
}
