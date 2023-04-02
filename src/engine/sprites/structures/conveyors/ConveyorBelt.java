package engine.sprites.structures.conveyors;

import java.io.File;
import java.util.Objects;

import engine.physics.PhysicsManager;
import engine.rendering.textures.Texture;
import engine.sprites.objects.Item;
import engine.sprites.structures.CoreModule;
import engine.sprites.structures.StructureSprite;
import engine.time.DeltaTime;
import math.Vector2;

public class ConveyorBelt extends StructureSprite {
	private static final long serialVersionUID = -7264927604884014679L;

	private ConveyorBeltDirection direction;

	private Item item;

	private ConveyorBelt next;

	private static final float TRANSPORT_SPEED = 5f;

	public ConveyorBelt(Texture texture, Vector2 location, float zDepth, ConveyorBeltDirection dir) {
		super(texture, location, zDepth);
		this.direction = dir;
		this.collisionBox = null;

		PhysicsManager.removePhysicsSprite(this);
	}

	public static ConveyorBelt instantiateConveyorBelt(File file, Vector2 location, ConveyorBeltDirection dir) {
		try {
			Texture texture = Texture.createTexture(file);
			return new ConveyorBelt(texture, location, 0.7f, dir);
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

		item.setLocation(location);
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
		result = prime * result + Objects.hash(direction, item, TRANSPORT_SPEED);
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
		return direction == other.direction && Objects.equals(item, other.item);
	}

	@Override
	public void update() {
		if (item != null && next != null) {
			item.setLocation(item.getLocation().add(new Vector2(direction.getDeltaX(), direction.getDeltaY()).mul((float) (TRANSPORT_SPEED * DeltaTime.getDeltaTime()))));

			switch (direction) {
			case UP:
				if (item.getLocation().getY() < next.getLocation().getY()) {
					next.setItem(this.item);
					this.item = null;
					System.out.println("woo");
				}
				break;
			case DOWN: //finish this and auto detect next

				break;
			case LEFT:

				break;
			case RIGHT:

				break;
			default:
				break;
			}

		}
	}

	public static float getTransportSpeed() {
		return TRANSPORT_SPEED;
	}

}
