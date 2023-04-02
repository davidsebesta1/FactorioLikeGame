package engine.sprites.objects;

import engine.rendering.textures.Texture;
import engine.sprites.PhysicsSprite;
import math.Vector2;

public class Item extends PhysicsSprite {
	private static final long serialVersionUID = 379059532463440235L;

	protected Item(Texture texture, Vector2 location, float zDepth) {
		super(texture, location, zDepth);
	}

}
