package engine.sprites.objects.minable;

import engine.rendering.textures.Texture;
import math.Vector2;

/**
 * Lithium class
 * @author David Šebesta
 * @see OreItem
 */
public class Lithium extends OreItem {
	private static final long serialVersionUID = -7953227629711410755L;

	private Lithium(Texture texture, Vector2 location, double zDepth) {
		super(texture, location, zDepth);
	}
	
	public static Lithium instantiateLithium(Texture texture, Vector2 location) {
		try {
			return new Lithium(texture, location, 0.72d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public static String ID() {
		return "lithiumItem";
	}
	
	@Override
	public String getID() {
		return ID();
	}

}
