package engine.sprites.structures;

import java.io.Serializable;

import engine.sprites.structures.conveyors.ConveyorBelt;
import engine.sprites.structures.conveyors.ConveyorBeltDirection;
import engine.sprites.structures.conveyors.ConveyorBeltManager;
import math.Vector2;

public class StructureMap implements Serializable {
	private static final long serialVersionUID = -5228704977309459469L;
	
	//Accesible instance, only one per map
	private static StructureMap instance;
	
	//Variables
	private Vector2 size;

	private StructureSprite[][] map;
	private boolean[][] occupiedMap;

	public StructureMap(Vector2 size) {
		instance = this;
		this.size = size;
		this.map = new StructureSprite[(int) size.getX()][(int) size.getY()];
		this.occupiedMap = new boolean[(int) size.getX()][(int) size.getY()];
	}

	public boolean tryAddStructureAtLocation(StructureSprite structure, Vector2 locationOnStructMap) {
		
		//Valid checks
		if (map[(int) locationOnStructMap.getX()][(int) locationOnStructMap.getY()] == null && checkForOccupiedSpaces(structure, locationOnStructMap)) {
			map[(int) locationOnStructMap.getX()][(int) locationOnStructMap.getY()] = structure;
			
			//Set location based on tile size * location
			structure.setLocation(new Vector2(locationOnStructMap.getX() * 32f, locationOnStructMap.getY() * 32f));
			
			for (int i = (int) locationOnStructMap.getX(); i < locationOnStructMap.getX() + structure.getTileSizeUnits().getX(); i++) {
				for (int j = (int) locationOnStructMap.getY(); j < locationOnStructMap.getY() + structure.getTileSizeUnits().getY(); j++) {
					try {
						occupiedMap[i][j] = true; // Update occupied map
					} catch(ArrayIndexOutOfBoundsException e) {} // lazy to make out of bounds check
				}
			}
			
			//If it is instance of conveyor belt, add it to the manager
			if(structure instanceof ConveyorBelt) {
				ConveyorBeltManager.addBelt((ConveyorBelt) structure);
			}

			return true;
		}

		return false;
	}
	
	public StructureSprite getStructureAtLocation(Vector2 locationOnStructMap) {
		return map[(int)locationOnStructMap.getX()][(int)locationOnStructMap.getY()];
	}
	
	public Vector2 getLocationByStructure(StructureSprite sprite) {
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				if(map[i][j] == sprite) {
					return new Vector2(i, j);
				}
			}
		}
		
		return null;
	}
	
	public StructureSprite getStructureAtWorldLocation(Vector2 worldCoordinates) {
		return map[(int)(worldCoordinates.getX() / 32f)][(int)(worldCoordinates.getY() / 32f)];
	}

	private boolean checkForOccupiedSpaces(StructureSprite structure, Vector2 locationOnStructMap) {
		for (int i = (int) locationOnStructMap.getX(); i < locationOnStructMap.getX() + structure.getTileSizeUnits().getX(); i++) {
			for (int j = (int) locationOnStructMap.getY(); j < locationOnStructMap.getY() + structure.getTileSizeUnits().getY(); j++) {
				try {
					if (occupiedMap[i][j]) return false; // any occupied field found within coordinates and destination, return false
				} catch(ArrayIndexOutOfBoundsException e) {} // lazy for out of bounds check
			}
		}

		return true;
	}
	
	public boolean isInBounds(int x, int y) {
	    return x >= 0 && y >= 0 && x < map.length && y < map[0].length;
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
