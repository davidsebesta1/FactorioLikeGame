package engine.sprites.objects.processed.plates;

import engine.rendering.textures.Texture;
import engine.sprites.objects.Item;
import math.Vector2;

/**
 * Aluminium plate
 * @author David Šebesta
 * @see Item
 */
public class AluminiumPlate extends Item {
	private static final long serialVersionUID = -9165651479722004259L;

	private AluminiumPlate(Texture texture, Vector2 location, double zDepth) {
		super(texture, location, zDepth);
	}
	
	public static AluminiumPlate instantiateAluminiumPlate(Texture texture, Vector2 location) {
		try {
			return new AluminiumPlate(texture, location, 0.72d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

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
		if (!(obj instanceof AluminiumPlate)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "AluminiumPlate []";
	}
	
	public static String ID() {
		return "aluminiumPlate";
	}
	
	@Override
	public String getID() {
		return ID();
	}
}
