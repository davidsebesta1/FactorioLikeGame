package engine.sprites;

public interface ISpriteBehaviour {
	public abstract void start();
	public abstract void update();
	public abstract void onMouseClicked();
	public static String ID() {
		return null;
	}
}
