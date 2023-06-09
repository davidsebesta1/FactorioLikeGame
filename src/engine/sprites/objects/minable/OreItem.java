package engine.sprites.objects.minable;

import engine.rendering.textures.Texture;
import engine.sprites.objects.Item;
import math.Vector2;

/**
 * OreItem is a class that extends Item to distinguish them easily
 * @author David Šebesta
 * @see OreItem
 */
public abstract class OreItem extends Item {
	private static final long serialVersionUID = 3079476889384055313L;

	protected OreItem(Texture texture, Vector2 location, double zDepth) {
		super(texture, location, zDepth);
	}

	
}
