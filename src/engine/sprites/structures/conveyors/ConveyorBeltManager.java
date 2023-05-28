package engine.sprites.structures.conveyors;

import java.util.ArrayList;

import engine.sprites.structures.StructureMap;
import engine.sprites.structures.StructureSprite;
import math.Vector2;

/**
 * A static manager class for updating and managing conveyor belts.
 * @author David Šebesta
 *
 */
public class ConveyorBeltManager {

	private static ArrayList<ConveyorBelt> belts = new ArrayList<>();

	/**
	 * Private constructor
	 */
	private ConveyorBeltManager() {
	}

	/**
	 * Attempts to add a conveyor belt to a list
	 * @param belt
	 * @return True if added, otherwise false
	 */
	public static boolean addBelt(ConveyorBelt belt) {
		boolean result = belts.add(belt);
		updateConnections();
		return result;
	}

	/**
	 * Attempts to remove conveyor belt from a list
	 * @param belt
	 * @return True if removed, otherwise false
	 */
	public static boolean removeBelt(ConveyorBelt belt) {
		return belts.remove(belt);
	}

	/**
	 * Updates all connections of a conveyor belts
	 */
	public static void updateConnections() {
		for (ConveyorBelt belt : belts) {
			updateConnection(belt);

		}

	}

	/**
	 * Updates connection to other belts of a specified conveyor belt
	 * @param belt
	 */
	public static void updateConnection(ConveyorBelt belt) {
		StructureSprite[][] map = StructureMap.getInstance().getMap();
		Vector2 loc = StructureMap.getInstance().getLocationByStructure(belt);

		int x = (int) loc.getX();
		int y = (int) loc.getY();

		// check every dir, and then update connection, call update connection for that
		// belt too

		switch (belt.getDirection()) {
		case DOWN:
			if (StructureMap.getInstance().isInBounds(x, y + 1) && map[x][y + 1] instanceof ConveyorBelt) {
				ConveyorBelt temp = (ConveyorBelt) map[x][y + 1];
				belt.setNext(temp);

			}

			if (StructureMap.getInstance().isInBounds(x, y - 1) && map[x][y - 1] instanceof ConveyorBelt) {
				ConveyorBelt temp = (ConveyorBelt) map[x][y - 1];
				temp.setNext(belt);

			}
			break;
		case UP:
			if (StructureMap.getInstance().isInBounds(x, y - 1) && map[x][y - 1] instanceof ConveyorBelt) {
				ConveyorBelt temp = (ConveyorBelt) map[x][y - 1];
				belt.setNext(temp);

			}

			if (StructureMap.getInstance().isInBounds(x, y + 1) && map[x][y + 1] instanceof ConveyorBelt) {
				ConveyorBelt temp = (ConveyorBelt) map[x][y + 1];
				temp.setNext(belt);

			}
			break;
		case LEFT:
			if (StructureMap.getInstance().isInBounds(x - 1, y) && map[x - 1][y] instanceof ConveyorBelt) {
				ConveyorBelt temp = (ConveyorBelt) map[x - 1][y];
				belt.setNext(temp);

			}

			if (StructureMap.getInstance().isInBounds(x + 1, y) && map[x + 1][y] instanceof ConveyorBelt) {
				ConveyorBelt temp = (ConveyorBelt) map[x + 1][y];
				temp.setNext(belt);

			}
			break;
		case RIGHT:
			if (StructureMap.getInstance().isInBounds(x + 1, y) && map[x + 1][y] instanceof ConveyorBelt) {
				ConveyorBelt temp = (ConveyorBelt) map[x + 1][y];
				belt.setNext(temp);

			}

			if (StructureMap.getInstance().isInBounds(x - 1, y) && map[x - 1][y] instanceof ConveyorBelt) {
				ConveyorBelt temp = (ConveyorBelt) map[x - 1][y];
				temp.setNext(belt);

			}
			break;
		}
	}

	public static ArrayList<ConveyorBelt> getBelts() {
		return belts;
	}

}
