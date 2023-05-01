package engine.sprites.structures.conveyors;

import math.Vector2;

public enum ConveyorBeltDirection {
	DOWN(0, 1, "DOWN"), 
	RIGHT(1, 0, "RIGHT"), 
	UP(0, -1, "UP"), 
	LEFT(-1, 0, "LEFT");

	private final int deltaX;
	private final int deltaY;
	private final String name;

	private ConveyorBeltDirection(int deltaX, int deltaY, String name) {
		this.deltaX = deltaX;
		this.deltaY = deltaY;
		this.name = name;
	}

	public int getDeltaX() {
		return deltaX;
	}

	public int getDeltaY() {
		return deltaY;
	}

	public String getName() {
		return name;
	}

	public Vector2 getDelta() {
		return new Vector2(deltaX, deltaY);
	}
	
	
}
