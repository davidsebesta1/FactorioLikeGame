package engine.rendering;

import java.io.Serializable;

import engine.Game;
import engine.input.IMouseMotionEventListener;
import engine.input.IMouseWheelEventListener;
import engine.input.InputManager;
import math.MathUtilities;
import math.Vector2;

public class Camera implements IMouseWheelEventListener, IMouseMotionEventListener, Serializable {
	private static final long serialVersionUID = -985481655588932644L;

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
		this.cameraZoomScale = MathUtilities.clamp(cameraZoomScale, 1, 2d);
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

	@Override
	public void mouseMoved(Vector2 newLocation) {
	}

}
