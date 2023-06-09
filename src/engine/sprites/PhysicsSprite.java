package engine.sprites;

import java.util.Objects;

import engine.physics.BoundingBox;
import engine.physics.CollisionLayers;
import engine.physics.PhysicsManager;
import engine.rendering.textures.Texture;
import math.Vector2;

/**
 * Physics sprite is a abstract class for sprites that can implement collisions handling with specified bounding box and collision layer. It can also have its own velocity which is updates each frame.
 * @author David Šebesta
 *
 */
public abstract class PhysicsSprite extends Sprite implements IPhysicsBehaviour {
	private static final long serialVersionUID = -3155369270101122877L;

	protected Vector2 velocity;

	protected BoundingBox collisionBox;
	
	protected CollisionLayers collisionLayer;

	/**
	 * Protected constructor
	 * @param texture
	 * @param location
	 * @param zDepth
	 */
	protected PhysicsSprite(Texture texture, Vector2 location, double zDepth) {
		super(texture, location, zDepth);
		this.collisionBox = null;
		this.velocity = Vector2.zero;

		SpriteManager.addUpdateSprite(this);
		PhysicsManager.addPhysicsSprite(this);
	}
	
	/**
	 * Checking for collision layer, if two sprites can collide
	 * @param other
	 * @return True if they can collide, otherwise false
	 */
	public boolean checkCollisionLayer(PhysicsSprite other) {
		if(this.collisionLayer == other.collisionLayer) return false;
		if(this.collisionLayer == null || other.collisionLayer == null) return false;
		
		for(CollisionLayers layr : other.collisionLayer.getCollide()) {
			if(layr == this.collisionLayer) {
				return true;
				
			}
			
		}
		
		return false;
	}

	@Override
	public void setLocation(Vector2 newLocation) {
		this.location = newLocation;
		
		if(collisionBox != null) collisionBox.setLocation(newLocation);
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public BoundingBox getCollisionBox() {
		return collisionBox;
	}

	public void setCollisionBox(BoundingBox collisionBox) {
		this.collisionBox = collisionBox;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result;
		result = prime + Objects.hash(collisionBox, collisionLayer, velocity);
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
		PhysicsSprite other = (PhysicsSprite) obj;
		return Objects.equals(collisionBox, other.collisionBox) && collisionLayer == other.collisionLayer
				&& Objects.equals(velocity, other.velocity);
	}

	@Override
	public void enteredCollision(PhysicsSprite sprite) {
		// Left for override
	}

	public CollisionLayers getLayer() {
		return collisionLayer;
	}

	public void setLayer(CollisionLayers layer) {
		this.collisionLayer = layer;
	}
	
	/**
	 * Clear references
	 */
	@Override
	public void destroy() {
		super.destroy();
		
		PhysicsManager.removePhysicsSprite(this);
		SpriteManager.removeUpdateSprite(this);
	}

	public static String ID() {
		return "physicsSprite";
		
	}
}
