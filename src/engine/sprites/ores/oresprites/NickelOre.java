package engine.sprites.ores.oresprites;

import engine.rendering.textures.Texture;
import engine.sprites.ores.OreSprite;
import math.Vector2;

/**
 * Nickel ore
 * @author David Šebesta
 * @see OreSprite
 *
 */
public class NickelOre extends OreSprite {
	private static final long serialVersionUID = -8582286441638894140L;
	
	private NickelOre(Texture texture, Vector2 location, double zDepth, int oreAmount, double miningHardness) {
		super(texture, location, zDepth, oreAmount, miningHardness);
	}

	public static NickelOre instantiateNickelOre(Texture texture, Vector2 location, int oreAmount) {
		try {
			return new NickelOre(texture, location, 0.3d, oreAmount, 1.2d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String ID() {
		return "nickelOre";
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
		if (!(obj instanceof NickelOre)) {
			return false;
		}
		return true;
	}
	
	

}
