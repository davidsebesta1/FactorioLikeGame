package engine.sprites;

/**
 * Interface for all sprites
 * @author David
 * @see Sprite
 *
 */
public interface ISpriteBehaviour {
	public abstract void start();
	public abstract void update();
	public abstract void onMouseClicked();
	public static String ID() {
		return null;
	}
}
