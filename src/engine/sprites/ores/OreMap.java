package engine.sprites.ores;

import java.io.Serializable;

import math.Vector2;

public class OreMap implements Serializable {
	static final long serialVersionUID = -1626860176557345238L;

	// Accesible instance, only one per map
	private static OreMap instance;

	// Variables
	private Vector2 size;

	private OreSprite[][] map;

	public OreMap(Vector2 size) {
		instance = this;
		this.size = size;
		this.map = new OreSprite[(int) size.getX()][(int) size.getY()];
	}

	public boolean tryAddOreAtLocation(OreSprite structure, Vector2 locationOnStructMap) {
		if (!isInBounds((int) locationOnStructMap.getX(), (int) locationOnStructMap.getY())) return false;

		// Valid checks
		if (map[(int) locationOnStructMap.getX()][(int) locationOnStructMap.getY()] == null) {
			map[(int) locationOnStructMap.getX()][(int) locationOnStructMap.getY()] = structure;

			// Set location based on tile size * location
			structure.setLocation(new Vector2(locationOnStructMap.getX() * 32f, locationOnStructMap.getY() * 32f));

			return true;
		}

		return false;
	}

	public boolean tryRemoveOreAtLocation(Vector2 locationOnStructMap) {
		if (!isInBounds((int) locationOnStructMap.getX(), (int) locationOnStructMap.getY())) return false;

		if (map[(int) locationOnStructMap.getX()][(int) locationOnStructMap.getY()] != null) {
			map[(int) locationOnStructMap.getX()][(int) locationOnStructMap.getY()] = null;

			return true;
		}

		return false;
	}

	public OreSprite getOreAtWorldLocation(Vector2 worldCoordinates) {
		if (!isInBounds((int) worldCoordinates.getX() / 32, (int) worldCoordinates.getY() / 32))
			return null;

		return map[(int) (worldCoordinates.getX() / 32f)][(int) (worldCoordinates.getY() / 32f)];
	}
	
	public OreSprite getOreAtMapLocation(Vector2 locationOnStructMap) {
		if(!isInBounds((int) locationOnStructMap.getX(), (int) locationOnStructMap.getY())) return null;
		return map[(int)locationOnStructMap.getX()][(int)locationOnStructMap.getY()];
	}

	public boolean isInBounds(int x, int y) {
		return x >= 0 && y >= 0 && x < map.length && y < map[x].length;
	}

}
