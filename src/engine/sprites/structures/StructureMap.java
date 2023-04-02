package engine.sprites.structures;

import java.io.Serializable;

import engine.sprites.structures.conveyors.ConveyorBelt;
import engine.sprites.structures.conveyors.ConveyorBeltDirection;
import math.Vector2;

public class StructureMap implements Serializable {
	private static final long serialVersionUID = -5228704977309459469L;

	private Vector2 size;

	private StructureSprite[][] map;
	private boolean[][] occupiedMap;

	public StructureMap(Vector2 size) {
		this.size = size;
		this.map = new StructureSprite[(int) size.getX()][(int) size.getY()];
		this.occupiedMap = new boolean[(int) size.getX()][(int) size.getY()];
	}

	public boolean tryAddStructureAtLocation(StructureSprite structure, Vector2 locationOnStructMap) {
		if (map[(int) locationOnStructMap.getX()][(int) locationOnStructMap.getY()] == null && checkForOccupiedSpaces(structure, locationOnStructMap)) {
			map[(int) locationOnStructMap.getX()][(int) locationOnStructMap.getY()] = structure;
			
			structure.setLocation(new Vector2(locationOnStructMap.getX() * 32f, locationOnStructMap.getY() * 32f));
			
			for (int i = (int) locationOnStructMap.getX(); i < locationOnStructMap.getX() + structure.getTileSizeUnits().getX(); i++) {
				for (int j = (int) locationOnStructMap.getY(); j < locationOnStructMap.getY() + structure.getTileSizeUnits().getY(); j++) {
					try {
						occupiedMap[i][j] = true;
					} catch(ArrayIndexOutOfBoundsException e) {}
				}
			}

			return true;
		}

		return false;
	}

	private boolean checkForOccupiedSpaces(StructureSprite structure, Vector2 locationOnStructMap) {
		System.out.println(structure + "\n" + locationOnStructMap);
		for (int i = (int) locationOnStructMap.getX(); i < locationOnStructMap.getX() + structure.getTileSizeUnits().getX(); i++) {
			for (int j = (int) locationOnStructMap.getY(); j < locationOnStructMap.getY() + structure.getTileSizeUnits().getY(); j++) {
				try {
					if (occupiedMap[i][j])
						return false;
				} catch(ArrayIndexOutOfBoundsException e) {}
			}
		}

		return true;
	}
}
