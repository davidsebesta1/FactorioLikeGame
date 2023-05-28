package engine.sprites.objects.minable;

import engine.rendering.textures.Texture;
import math.Vector2;

/**
 * Titanium class
 * @author David Šebesta
 * @see OreItem
 */
public class Titanium extends OreItem {
	private static final long serialVersionUID = -8537086888336033953L;

	protected Titanium(Texture texture, Vector2 location, double zDepth) {
		super(texture, location, zDepth);
	}
	
	public static Titanium instantiateTitanium(Texture texture, Vector2 location) {
		try {
			return new Titanium(texture, location, 0.72d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	@Override
	public String toString() {
		return "Titanium []";
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof Titanium)) {
			return false;
		}
		return true;
	}

	public static String ID() {
		return "titaniumItem";
	}
	
	@Override
	public String getID() {
		return ID();
	}

}
