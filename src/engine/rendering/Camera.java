package engine.rendering;

import engine.Game;
import engine.input.IMouseWheelEventListener;
import engine.input.InputManager;
import math.MathUtilities;
import math.Vector2;

public class Camera implements IMouseWheelEventListener {
	
	private Vector2 location;
	private Vector2 resolution;

	private boolean mouseZoomEnabled;

	private double cameraZoomScale;

	public Camera(Vector2 location) {
		this.mouseZoomEnabled = true;
		this.cameraZoomScale = 1d;
		
		this.location = location;

		InputManager.addMouseWheelListener(this);
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
		Vector2 temp = screenCoordinates.sub(Game.getInstance().getCurrentWorld().getPlayer().getCamera().getLocation().mul(-1));
		return temp.div((float) Game.getInstance().getCurrentWorld().getPlayer().getCamera().getCameraZoomScale());
	}
}
