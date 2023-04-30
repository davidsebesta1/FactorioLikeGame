package engine.sprites.objects.processed.plates;

import engine.rendering.textures.Texture;
import engine.sprites.objects.Item;
import math.Vector2;

public class CopperPlate extends Item {
	private static final long serialVersionUID = 6053517717212748133L;

	private CopperPlate(Texture texture, Vector2 location, double zDepth) {
		super(texture, location, zDepth);
	}

	public static CopperPlate instantiateCopperPlate(Texture texture, Vector2 location) {
		try {
			return new CopperPlate(texture, location, 0.72d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public String toString() {
		return "CopperPlate []";
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
		if (!(obj instanceof CopperPlate)) {
			return false;
		}
		return true;
	}

	public static String ID() {
		return "copperPlate";
	}

	@Override
	public String getID() {
		return ID();
	}

}
