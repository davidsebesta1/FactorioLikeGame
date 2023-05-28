package engine.sprites.objects.minable;

import engine.rendering.textures.Texture;
import math.Vector2;

/**
 * Gold class
 * @author David Šebesta
 * @see OreItem
 */
public class Gold extends OreItem {
	private static final long serialVersionUID = 4612895738823050439L;

	private Gold(Texture texture, Vector2 location, double zDepth) {
		super(texture, location, zDepth);
	}
	
	public static Gold instantiateGold(Texture texture, Vector2 location) {
		try {
			return new Gold(texture, location, 0.72d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public static String ID() {
		return "goldItem";
	}
	
	@Override
	public String getID() {
		return ID();
	}

}
