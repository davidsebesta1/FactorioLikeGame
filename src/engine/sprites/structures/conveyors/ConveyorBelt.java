package engine.sprites.structures.conveyors;

import java.io.File;
import java.util.Objects;

import engine.Game;
import engine.physics.PhysicsManager;
import engine.rendering.textures.Texture;
import engine.sprites.objects.Item;
import engine.sprites.structures.StructureSprite;
import engine.time.DeltaTime;
import math.Vector2;

public class ConveyorBelt extends StructureSprite {
	private static final long serialVersionUID = -7264927604884014679L;

	private ConveyorBeltDirection direction;

	private Item item;

	private ConveyorBelt next;

	private boolean itemIsMoving;

	private static final double TRANSPORT_SPEED = 20f;

	public ConveyorBelt(Texture texture, Vector2 location, double zDepth, ConveyorBeltDirection dir) {
		super(texture, location, zDepth);
		this.direction = dir;
		this.collisionBox = null;
		this.itemIsMoving = false;

		PhysicsManager.removePhysicsSprite(this);
	}

	public static ConveyorBelt instantiateConveyorBelt(File file, Vector2 location, ConveyorBeltDirection dir) {
		try {
			Texture texture = Texture.createTexture(file);
			return new ConveyorBelt(texture, location, 0.7d, dir);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static ConveyorBelt instantiateConveyorBelt(Texture texture, Vector2 location, ConveyorBeltDirection dir) {
		try {
			return new ConveyorBelt(texture, location, 0.7d, dir);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public ConveyorBeltDirection getDirection() {
		return direction;
	}

	public void setDirection(ConveyorBeltDirection direction) {
		this.direction = direction;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;

		if (item != null) {
			item.setBeltAssigned(this);
			item.setLocation(location);
		}
	}

	public ConveyorBelt getNext() {
		return next;
	}

	public void setNext(ConveyorBelt next) {
		this.next = next;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(direction, next);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConveyorBelt other = (ConveyorBelt) obj;
		return direction == other.direction && Objects.equals(item, other.item) && Objects.equals(next, other.next);
	}

	@Override
	public void update() {
		if (item != null && next != null) {
			if (next.itemIsMoving || (!next.itemIsMoving && next.item == null)) {
				itemIsMoving = true;
				item.setLocation(item.getLocation().add(new Vector2(direction.getDeltaX(), direction.getDeltaY())
						.mul(TRANSPORT_SPEED * DeltaTime.getDeltaTime())));

				switch (direction) {
				case UP:
					if (item.getLocation().getY() < next.getLocation().getY()) {
						next.setItem(this.item);
						this.item = null;
					}
					break;
				case DOWN:
					if (item.getLocation().getY() > next.getLocation().getY()) {
						next.setItem(this.item);
						this.item = null;
					}
					break;
				case LEFT:
					if (item.getLocation().getX() < next.getLocation().getX()) {
						next.setItem(this.item);
						this.item = null;
					}
					break;
				case RIGHT:
					if (item.getLocation().getX() > next.getLocation().getX()) {
						next.setItem(this.item);
						this.item = null;
					}
					break;
				}

				Game.getInstance().getCurrentWorld().getChunkManager().updateSpriteChunk(item);
			}
		} else if (item != null && next == null) {
			item.setLocation(location.add(direction.getDelta()));
		} else {
			itemIsMoving = false;
		}

	}

	public static double getTransportSpeed() {
		return TRANSPORT_SPEED;
	}

	@Override
	public String toString() {
		return "ConveyorBelt [direction=" + direction + ", item=" + item + ", next=" + next + ", tileSizeUnits="
				+ tileSizeUnits + ", location=" + location + "]";
	}

	public static String ID() {
		return "conveyorBelt";
	}

}
