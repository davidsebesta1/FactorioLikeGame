package math;

import engine.Game;
import engine.sprites.entities.player.Player;

/**
 * Math utilities is a static class providing a static methods to handle math stuff.
 * @author David Šebesta
 *
 */
public class MathUtilities {

	/**
	 * Private constructor
	 */
	private MathUtilities() {
	}

	/**
	 * Clamp method clamps a value between minimum and maximum values provided
	 * @param value
	 * @param min
	 * @param max
	 * @return A clamped value
	 */
	public static double clamp(double value, double min, double max) {
		if (value < min) {
			return min;
		} else if (value > max) {
			return max;
		} else {
			return value;
		}
	}
	
	/**
	 * Lerp method linearly interpolates between A and B values using alpha value.
	 * @param a
	 * @param b
	 * @param alpha
	 * @return Interpolated value
	 */
	public static double lerp(double a, double b, double alpha) {
		return a + alpha * (b - a);
	}
	
	/**
	 * Conversion from screen to world coordinates
	 * @param screenCoordinates
	 * @return Converted coordinates
	 */
	public static Vector2 screenToWorldCoordinates(Vector2 screenCoordinates) {
		Vector2 delta = screenDeltaByScale();

		Vector2 temp = screenCoordinates;
		temp = temp.div((float) Game.getInstance().getCurrentWorld().getPlayer().getCamera().getCameraZoomScale());
		return temp.add(delta.sub(Game.getInstance().getCurrentWorld().getPlayer().getCamera().getLocation().mul(-1)));
	}

	/**
	 * Conversion from world to screen coordinates
	 * @param worldCoordinates
	 * @return Converted coordinates
	 */
	public static Vector2 worldToScreenCoordinates(Vector2 worldCoordinates) {
		Vector2 delta = screenDeltaByScale();
		Vector2 temp = worldCoordinates
				.sub(delta.sub(Game.getInstance().getCurrentWorld().getPlayer().getCamera().getLocation().mul(-1)));
		temp = temp.mul((float) Game.getInstance().getCurrentWorld().getPlayer().getCamera().getCameraZoomScale());
		return temp;
	}

	/**
	 * Returns a coordinates by scale (zoom) of a player camera
	 * @return Scaled Vector2
	 */
	public static Vector2 screenDeltaByScale() {
		double zoomScale = Game.getInstance().getCurrentWorld().getPlayer().getCamera().getCameraZoomScale();
		int currentWidth = (int) Game.getInstance().getWindow().getSize().getX();
		int currentHeight = (int) Game.getInstance().getWindow().getSize().getY();

		// Calculate the size at 1.0x scale
		double baseWidth = (currentWidth / zoomScale);
		double baseHeight = (currentHeight / zoomScale);

		// Calculate the remaining distance to move in each direction
		double deltaX = (currentWidth - baseWidth) / 2;
		double deltaY = (currentHeight - baseHeight) / 2;

		return new Vector2(deltaX, deltaY);
	}
	
	/**
	 * Rounds a Vector2 to a nearest 32 multiplier number.
	 * @param screenCoordinate
	 * @return Rounded value
	 */
	public static Vector2 roundTo32(Vector2 screenCoordinate) {
		int roundedNumberX = (int) (Math.floor(screenCoordinate.getX() / 32.0) * 32);
		int roundedNumberY = (int) (Math.ceil(screenCoordinate.getY() / 32.0) * 32);
		
		return new Vector2(roundedNumberX, roundedNumberY);
	}
	
	/**
	 * Rounds number a grid values for game usage
	 * @param screenCoordinates
	 * @return Rounded value
	 */
	public static Vector2 roundToGrid(Vector2 screenCoordinates) {
		// Calculate player's position on the grid
		Player plr = Game.getInstance().getPlayer();
		int xOffset = (int) (plr.getLocation().getX() % 32);
		int yOffset = (int) (plr.getLocation().getY() % 32);
		
		return (roundTo32(screenCoordinates).sub(new Vector2(xOffset, yOffset)));
		
	}
}
