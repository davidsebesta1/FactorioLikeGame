package engine.sprites.objects;

import engine.physics.CollisionLayers;
import engine.rendering.textures.Texture;
import engine.sprites.PhysicsSprite;
import math.Vector2;

public abstract class Item extends PhysicsSprite {
	private static final long serialVersionUID = 379059532463440235L;
	protected static final transient String ID = "defaultitem";

	protected Item(Texture texture, Vector2 location, float zDepth) {
		super(texture, location, zDepth);
		this.collisionLayer = CollisionLayers.ITEM;
	}
	
	

}
