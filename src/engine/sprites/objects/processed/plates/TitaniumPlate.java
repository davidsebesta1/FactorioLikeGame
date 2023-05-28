package engine.sprites.objects.processed.plates;

import engine.rendering.textures.Texture;
import engine.sprites.objects.Item;
import math.Vector2;

/**
 * Titanium plate
 * @author David Šebesta
 * @see Item
 */
public class TitaniumPlate extends Item{
	private static final long serialVersionUID = 6798974267071815412L;

	private TitaniumPlate(Texture texture, Vector2 location, double zDepth) {
		super(texture, location, zDepth);
	}
	
	public static TitaniumPlate instantiateTitaniumPlate(Texture texture, Vector2 location) {
		try {
			return new TitaniumPlate(texture, location, 0.72d);
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
		if (!(obj instanceof TitaniumPlate)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "TitaniumPlate []";
	}
	
	public static String ID() {
		return "titaniumPlate";
	}
	
	@Override
	public String getID() {
		return ID();
	}
}
