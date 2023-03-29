package engine.sprites.structures;

import java.io.Serializable;

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

	public boolean tryAddStructureAtLocation(StructureSprite structure, Vector2 location) {
		if (map[(int) location.getX()][(int) location.getY()] != null && checkForOccupiedSpaces(structure, location)) {
			map[(int) location.getX()][(int) location.getY()] = structure;
			return true;
		}

		return false;
	}

	private boolean checkForOccupiedSpaces(StructureSprite structure, Vector2 location) {
		for (int i = (int) location.getX(); i < location.getX() + structure.getSize().getX(); i++) {
			for (int j = (int) location.getY(); i < structure.getSize().getY(); i++) {
				if (occupiedMap[i][j])
					return false;
			}
		}

		return true;
	}
}
