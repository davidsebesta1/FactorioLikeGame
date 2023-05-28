package engine.sprites.structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import engine.Game;
import engine.physics.BoundingBox;
import engine.rendering.textures.Texture;
import engine.sprites.PhysicsSprite;
import engine.sprites.entities.player.Inventory;
import engine.sprites.structures.conveyors.ConveyorBelt;
import engine.sprites.structures.conveyors.ConveyorBeltDirection;
import math.Vector2;

/**
 * Structure sprites is a abstract class that holds information about its instantiated structure object
 * @author David Šebesta
 * @see PhysicsSprite
 *
 */
public abstract class StructureSprite extends PhysicsSprite {
	private static final long serialVersionUID = 5574047065458702506L;

	protected Vector2 tileSizeUnits;

	protected HashMap<String, Integer> resourceCost;
	protected String displayName;
	
	protected boolean rotatable;

	/**
	 * Protected class constructor
	 * @param texture
	 * @param location
	 * @param zDepth
	 */
	protected StructureSprite(Texture texture, Vector2 location, double zDepth) {
		super(texture, location, zDepth);
		this.tileSizeUnits = new Vector2(texture.getImage().getWidth() / 32, texture.getImage().getHeight() / 32);

		this.setCollisionBox(new BoundingBox(this, true, location, this.getSize()));
		this.resourceCost = new HashMap<>();
		this.rotatable = false;
	}

	public Vector2 getTileSizeUnits() {
		return tileSizeUnits;
	}

	public HashMap<String, Integer> getResourceCost() {
		return resourceCost;
	}

	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Method to return surrounding belts
	 * @return Surrounding belts
	 */
	public ArrayList<ConveyorBelt> getSurroundingBelts() {
		ArrayList<ConveyorBelt> toReturn = new ArrayList<>();
		toReturn.add(getBeltToLeft());
		toReturn.add(getBeltToRight());
		toReturn.add(getBeltToUp());
		toReturn.add(getBeltToDown());

		return toReturn;
	}

	/**
	 * Returns a conveyor belt to the left (negative X)
	 * @return ConveyorBelt object
	 */
	public ConveyorBelt getBeltToLeft() {
		Vector2 leftLocation = this.location.sub(new Vector2(32, 0));

		StructureSprite left = Game.getInstance().getStructureMap().getStructureAtWorldLocation(leftLocation);
		if (left instanceof ConveyorBelt) {
			return (ConveyorBelt) left;
		}

		return null;
	}

	/**
	 * Returns a conveyor belt to the right (positive X)
	 * @return ConveyorBelt object
	 */
	public ConveyorBelt getBeltToRight() {
		Vector2 rightLocation = this.location.add(new Vector2(32, 0));

		StructureSprite right = Game.getInstance().getStructureMap().getStructureAtWorldLocation(rightLocation);
		if (right instanceof ConveyorBelt) {
			return (ConveyorBelt) right;
		}

		return null;
	}

	/**
	 * Returns a converyor belt up (negative Y)
	 * @return ConveyorBelt object
	 */
	public ConveyorBelt getBeltToUp() {
		Vector2 upLocation = this.location.sub(new Vector2(0, 32));

		StructureSprite up = Game.getInstance().getStructureMap().getStructureAtWorldLocation(upLocation);
		if (up instanceof ConveyorBelt) {
			return (ConveyorBelt) up;
		}

		return null;
	}

	/**
	 * Returns a converyor belt down (positive Y)
	 * @return ConveyorBelt object
	 */
	public ConveyorBelt getBeltToDown() {
		Vector2 downLocation = this.location.add(new Vector2(0, 32));

		StructureSprite down = Game.getInstance().getStructureMap().getStructureAtWorldLocation(downLocation);
		if (down instanceof ConveyorBelt) {
			return (ConveyorBelt) down;
		}

		return null;
	}

	/**
	 * Returns incoming belts to directly leads to the structure and items on it will collide with structure
	 * @return Incoming belts
	 */
	public ArrayList<ConveyorBelt> getIncomingBelts() {
		ArrayList<ConveyorBelt> toReturn = new ArrayList<>();

		ConveyorBelt left = getBeltToLeft();
		ConveyorBelt right = getBeltToRight();
		ConveyorBelt up = getBeltToUp();
		ConveyorBelt down = getBeltToDown();

		if (left != null && left.getDirection() == ConveyorBeltDirection.RIGHT)
			toReturn.add(left);
		if (right != null && right.getDirection() == ConveyorBeltDirection.LEFT)
			toReturn.add(right);
		if (up != null && up.getDirection() == ConveyorBeltDirection.DOWN)
			toReturn.add(up);
		if (down != null && down.getDirection() == ConveyorBeltDirection.UP)
			toReturn.add(down);

		return toReturn;
	}

	/**
	* Returns conveyor belts that directly leads away from conveyor belt
	* @return Outcomig belts
	*/
	public ArrayList<ConveyorBelt> getOutcomingBelts() {
		ArrayList<ConveyorBelt> toReturn = new ArrayList<>();

		ConveyorBelt left = getBeltToLeft();
		ConveyorBelt right = getBeltToRight();
		ConveyorBelt up = getBeltToUp();
		ConveyorBelt down = getBeltToDown();

		if (left != null && left.getDirection() == ConveyorBeltDirection.LEFT)
			toReturn.add(left);
		if (right != null && right.getDirection() == ConveyorBeltDirection.RIGHT)
			toReturn.add(right);
		if (up != null && up.getDirection() == ConveyorBeltDirection.UP)
			toReturn.add(up);
		if (down != null && down.getDirection() == ConveyorBeltDirection.DOWN)
			toReturn.add(down);

		return toReturn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(displayName, tileSizeUnits);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof StructureSprite)) {
			return false;
		}
		StructureSprite other = (StructureSprite) obj;
		return Objects.equals(displayName, other.displayName) && Objects.equals(tileSizeUnits, other.tileSizeUnits);
	}

	@Override
	public void enteredCollision(PhysicsSprite sprite) {
		//Left for override
	}

	/**
	 * Checks if specified inventory has stuff to build this structure
	 * @param inventory
	 * @return True if inventory has stuff to build this structure
	 */
	public boolean hasEnoughItemsForConstruct(Inventory inv) {
		boolean hasEnough = false;
		for (Map.Entry<String, Integer> entry : resourceCost.entrySet()) {
			String itemID = entry.getKey();
			int amount = entry.getValue();

			if (inv.getItemAmount(itemID) >= amount)
				hasEnough = true;
			else
				hasEnough = false;
		}

		return hasEnough;
	}
	
	public abstract StructureSprite createCopy(String[] args);
}
