package engine.sprites.structures.conveyors;

import math.Vector2;

public enum ConveyorBeltDirection {
	DOWN(0, 1), 
	RIGHT(1, 0), 
	UP(0, -1), 
	LEFT(-1, 0);

	private final int deltaX;
	private final int deltaY;

	private ConveyorBeltDirection(int deltaX, int deltaY) {
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}

	public int getDeltaX() {
		return deltaX;
	}

	public int getDeltaY() {
		return deltaY;
	}
	
	public Vector2 getDelta() {
		return new Vector2(deltaX, deltaY);
	}
}
