package engine.sprites.objects;

import engine.rendering.textures.Texture;
import engine.sprites.Sprite;
import math.Vector2;

public class Item extends Sprite {
	private static final long serialVersionUID = 379059532463440235L;

	private Item(Texture texture, Vector2 location, float zDepth) {
		super(texture, location, zDepth);
	}

}
