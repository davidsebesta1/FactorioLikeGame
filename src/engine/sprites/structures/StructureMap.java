package engine.sprites.structures;

import java.io.Serializable;

import engine.sprites.structures.conveyors.ConveyorBelt;
import engine.sprites.structures.conveyors.ConveyorBeltDirection;
import engine.sprites.structures.conveyors.ConveyorBeltManager;
import math.Vector2;

public class StructureMap implements Serializable {
	private static final long serialVersionUID = -5228704977309459469L;

	// Accesible instance, only one per map
	private static StructureMap instance;

	// Variables
	private Vector2 size;

	private StructureSprite[][] map;
	private boolean[][] occupiedMap;

	private static final double SPRITE_SIZE = 32d;

	public StructureMap(Vector2 size) {
		instance = this;
		this.size = size;
		this.map = new StructureSprite[(int) size.getX()][(int) size.getY()];
		this.occupiedMap = new boolean[(int) size.getX()][(int) size.getY()];
	}

	public boolean tryAddStructureAtLocation(StructureSprite structure, Vector2 locationOnStructMap) {
		if (!isInBounds((int) locationOnStructMap.getX(), (int) locationOnStructMap.getY()))
			return false;

		// Valid checks
		if (map[(int) locationOnStructMap.getX()][(int) locationOnStructMap.getY()] == null
				&& checkForOccupiedSpaces(structure, locationOnStructMap)) {
			map[(int) locationOnStructMap.getX()][(int) locationOnStructMap.getY()] = structure;

			// Set location based on tile size * location
			structure.setLocation(
					new Vector2(locationOnStructMap.getX() * SPRITE_SIZE, locationOnStructMap.getY() * SPRITE_SIZE));

			for (int i = (int) locationOnStructMap.getX(); i < locationOnStructMap.getX()
					+ structure.getTileSizeUnits().getX(); i++) {
				for (int j = (int) locationOnStructMap.getY(); j < locationOnStructMap.getY()
						+ structure.getTileSizeUnits().getY(); j++) {
					occupiedMap[i][j] = true; // Update occupied map
				}
			}

			// If it is instance of conveyor belt, add it to the manager
			if (structure instanceof ConveyorBelt) {
				ConveyorBeltManager.addBelt((ConveyorBelt) structure);
			}

			return true;
		}

		return false;
	}

	public StructureSprite getStructureAtLocation(Vector2 locationOnStructMap) {
		if (!isInBounds((int) locationOnStructMap.getX(), (int) locationOnStructMap.getY()))
			return null;
		return map[(int) locationOnStructMap.getX()][(int) locationOnStructMap.getY()];
	}

	public Vector2 getLocationByStructure(StructureSprite sprite) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == sprite) {
					return new Vector2(i, j);
				}
			}
		}

		return null;
	}

	public StructureSprite getStructureAtWorldLocation(Vector2 worldCoordinates) {
		if (!isInBounds((int) (worldCoordinates.getX() / SPRITE_SIZE), (int) (worldCoordinates.getY() / SPRITE_SIZE)))
			return null;

		return map[(int) (worldCoordinates.getX() / SPRITE_SIZE)][(int) (worldCoordinates.getY() / SPRITE_SIZE)];
	}

	private boolean checkForOccupiedSpaces(StructureSprite structure, Vector2 locationOnStructMap) {
		if (!isInBounds((int) locationOnStructMap.getX(), (int) locationOnStructMap.getY()))
			return false;

		for (int i = (int) locationOnStructMap.getX(); i < locationOnStructMap.getX() + structure.getTileSizeUnits().getX(); i++) {
			for (int j = (int) locationOnStructMap.getY(); j < locationOnStructMap.getY() + structure.getTileSizeUnits().getY(); j++) {
				if (occupiedMap[i][j])
					return false; // any occupied field found within coordinates and destination, return false
			}
		}

		return true;
	}

	public boolean isInBounds(int x, int y) {
		return x >= 0 && y >= 0 && x < map.length && y < map[x].length;
	}

	public Vector2 getSize() {
		return size;
	}

	public StructureSprite[][] getMap() {
		return map;
	}

	public boolean[][] getOccupiedMap() {
		return occupiedMap;
	}

	public static StructureMap getInstance() {
		return instance;
	}
}
