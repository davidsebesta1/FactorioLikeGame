package engine.sprites.structures;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.ArrayList;

import engine.Game;
import engine.sprites.structures.conveyors.ConveyorBelt;
import engine.sprites.structures.conveyors.ConveyorBeltManager;
import engine.sprites.tiles.TileSprite;
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
			structure.setLocation(new Vector2(locationOnStructMap.getX() * SPRITE_SIZE, locationOnStructMap.getY() * SPRITE_SIZE));

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
			
			if(Game.getInstance().getCurrentWorld() != null) {
				Game.getInstance().getCurrentWorld().getChunkManager().assignChunk(structure);
				Game.getInstance().getCurrentWorld().getChunkManager().fixBetweenChunkSprite(structure);
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

		for (int i = (int) locationOnStructMap.getX(); i < locationOnStructMap.getX()
				+ structure.getTileSizeUnits().getX(); i++) {
			for (int j = (int) locationOnStructMap.getY(); j < locationOnStructMap.getY()
					+ structure.getTileSizeUnits().getY(); j++) {
				if (occupiedMap[i][j])
					return false; // any occupied field found within coordinates and destination, return false
			}
		}

		return true;
	}

	public Vector2 getMapLocationBySprite(StructureSprite sprite) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j].equals(sprite))
					return map[i][j].getLocation().div(32);
			}
		}

		return null;
	}

	public boolean tryToRemove(StructureSprite sprite) {
		return tryToRemove(
				new Vector2(sprite.getLocation().getX() / SPRITE_SIZE, sprite.getLocation().getY() / SPRITE_SIZE));
	}

	public boolean tryToRemove(Vector2 location) {
		if (map[(int) location.getX()][(int) location.getY()] != null) {
			map[(int) location.getX()][(int) location.getY()] = null;
			return true;
		}

		return false;
	}

	public static Vector2 worldToStructureMapCoordinate(Vector2 worldLocation) {
		if (worldLocation.getX() > 0 && worldLocation.getY() > 0)
			return new Vector2(worldLocation.getX() / 32, worldLocation.getY() / 32);
		throw new InvalidParameterException( "World location cannot be negative value for two dimensionl array conversion");
	}

	public ArrayList<StructureSprite> getAdjacentStructures(Vector2 locationOnStructMap) {
		ArrayList<StructureSprite> adjacentStructures = new ArrayList<>();
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {

				// Skip the center and corner tiles
				if ((i == 0 && j == 0) || (i != 0 && j != 0)) {
					continue;
				}

				int checkX = (int) (locationOnStructMap.getX() + i);
				int checkY = (int) (locationOnStructMap.getY() + j);

				if (checkX >= 0 && checkX < map.length && checkY >= 0 && checkY < map[0].length) {
					StructureSprite adjacent = map[checkX][checkY];
					if (adjacent != null) {
						adjacentStructures.add(adjacent);
					}
				}
			}
		}
		return adjacentStructures;
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
