package engine.rendering;

import engine.Game;
import engine.input.IMouseMotionEventListener;
import engine.input.IMouseWheelEventListener;
import engine.input.InputManager;
import math.MathUtilities;
import math.Vector2;

public class Camera implements IMouseWheelEventListener, IMouseMotionEventListener {

	private Vector2 location;
	private Vector2 resolution;

	private boolean mouseZoomEnabled;

	private double cameraZoomScale;

	public Camera(Vector2 location) {
		this.mouseZoomEnabled = true;
		this.cameraZoomScale = 1d;

		this.location = location;

		InputManager.addMouseWheelListener(this);
		InputManager.addMouseMotionListener(this);
	}

	@Override
	public void wheelMoved(int notches) {
		if (mouseZoomEnabled) {
			setCameraZoomScale(cameraZoomScale + (notches / 10d));
		}
	}

	public double getCameraZoomScale() {
		return cameraZoomScale;
	}

	public void setCameraZoomScale(double cameraZoomScale) {
		this.cameraZoomScale = MathUtilities.clamp(cameraZoomScale, 0.75d, 2d);
	}

	public Vector2 getLocation() {
		return location;
	}

	public void setLocation(Vector2 location) {
		this.location = location;
	}

	public boolean isMouseZoomEnabled() {
		return mouseZoomEnabled;
	}

	public void setMouseZoomEnabled(boolean mouseZoomEnabled) {
		this.mouseZoomEnabled = mouseZoomEnabled;
	}

	public static Vector2 screenToWorldCoordinates(Vector2 screenCoordinates) {
		Vector2 delta = screenDeltaByScale();
		
		Vector2 temp = screenCoordinates;
		temp = temp.div((float) Game.getInstance().getCurrentWorld().getPlayer().getCamera().getCameraZoomScale());
		return temp.add(delta.sub(Game.getInstance().getCurrentWorld().getPlayer().getCamera().getLocation().mul(-1)));
	}

	@Override
	public void mouseMoved(Vector2 newLocation) {
		System.out.println("Moved to " + newLocation);
		System.out.println("Moved to " + Camera.screenToWorldCoordinates(newLocation) + " world");
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
