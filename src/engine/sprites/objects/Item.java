package engine.sprites.objects;

import java.util.Objects;

import engine.physics.BoundingBox;
import engine.physics.CollisionLayers;
import engine.rendering.textures.Texture;
import engine.rendering.textures.TextureLibrary;
import engine.sprites.PhysicsSprite;
import engine.sprites.structures.conveyors.ConveyorBelt;
import math.Vector2;

/**
 * Item is a class that is able to be used on a conveyorbelt
 * @author David Šebesta
 * @see ConveyorBelt
 */
public abstract class Item extends PhysicsSprite {
	private static final long serialVersionUID = 379059532463440235L;
	protected static final transient String ID = "defaultitem";
	
	protected ConveyorBelt beltAssigned;

	/**
	 * Class constuctor
	 * @param texture
	 * @param location
	 * @param zDepth
	 */
	protected Item(Texture texture, Vector2 location, double zDepth) {
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
	
	public void clearBeltReference() {
		if(this.beltAssigned != null) {
			this.beltAssigned.setItem(null);
		}
		
		this.beltAssigned = null;
	}
	
	/**
	 * Cleaning up references
	 */
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

	public static String ID() {
		return ID;
	}
	
	public String getID() {
		return ID;
	}
	
	public static Texture retriveTextureByID(String ID) {
		switch(ID) {
		case "coal": return TextureLibrary.retrieveTexture("coal");
		case "titanium": return TextureLibrary.retrieveTexture("titanium");
		case "titaniumPlate": return TextureLibrary.retrieveTexture("titaniumPlate");
		default: return TextureLibrary.retrieveTexture("unknownTexture");
		}
	}
}
