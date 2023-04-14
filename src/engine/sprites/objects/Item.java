package engine.sprites.objects;

import java.util.Objects;

import engine.physics.BoundingBox;
import engine.physics.CollisionLayers;
import engine.rendering.textures.Texture;
import engine.sprites.PhysicsSprite;
import engine.sprites.structures.conveyors.ConveyorBelt;
import math.Vector2;

public abstract class Item extends PhysicsSprite {
	private static final long serialVersionUID = 379059532463440235L;
	protected static final transient String ID = "defaultitem";
	
	protected ConveyorBelt beltAssigned;

	protected Item(Texture texture, Vector2 location, float zDepth) {
		super(texture, location, zDepth);
		this.collisionLayer = CollisionLayers.ITEM;
		this.setCollisionBox(new BoundingBox(this, false, location, this.getSize()));
	}

	public ConveyorBelt getBeltAssigned() {
		return beltAssigned;
	}

	public void setBeltAssigned(ConveyorBelt beltAssigned) {
		this.beltAssigned = beltAssigned;
	}
	
	@Override
	public void destroy() {
		super.destroy();
		
		if(beltAssigned != null) {
			beltAssigned.setItem(null);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(beltAssigned);
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
		Item other = (Item) obj;
		return Objects.equals(beltAssigned, other.beltAssigned);
	}
}
