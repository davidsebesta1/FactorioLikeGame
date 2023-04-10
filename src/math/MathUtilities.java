package math;

import engine.Game;

public class MathUtilities {

	private MathUtilities() {
	}

	public static double clamp(double value, double min, double max) {
		if (value < min) {
			return min;
		} else if (value > max) {
			return max;
		} else {
			return value;
		}
	}
	
	public static double lerp(double a, double b, double alpha) {
		return a + alpha * (b - a);
	}
	
	public static Vector2 screenToWorldCoordinates(Vector2 screenCoordinates) {
		Vector2 delta = screenDeltaByScale();

		Vector2 temp = screenCoordinates;
		temp = temp.div((float) Game.getInstance().getCurrentWorld().getPlayer().getCamera().getCameraZoomScale());
		return temp.add(delta.sub(Game.getInstance().getCurrentWorld().getPlayer().getCamera().getLocation().mul(-1)));
	}

	public static Vector2 worldToScreenCoordinates(Vector2 worldCoordinates) {
		Vector2 delta = screenDeltaByScale();
		Vector2 temp = worldCoordinates
				.sub(delta.sub(Game.getInstance().getCurrentWorld().getPlayer().getCamera().getLocation().mul(-1)));
		temp = temp.mul((float) Game.getInstance().getCurrentWorld().getPlayer().getCamera().getCameraZoomScale());
		return temp;
	}

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
}
