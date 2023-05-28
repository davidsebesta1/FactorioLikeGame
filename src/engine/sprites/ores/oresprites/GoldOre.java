package engine.sprites.ores.oresprites;

import engine.rendering.textures.Texture;
import engine.sprites.ores.OreSprite;
import math.Vector2;

/**
 * Gold ore
 * @author David Šebesta
 * @see OreSprite
 *
 */
public class GoldOre extends OreSprite {
	private static final long serialVersionUID = 6998022177436243482L;
	
	private GoldOre(Texture texture, Vector2 location, double zDepth, int oreAmount, double miningHardness) {
		super(texture, location, zDepth, oreAmount, miningHardness);
		// TODO Auto-generated constructor stub
	}

	public static GoldOre instantiateGoldOre(Texture texture, Vector2 location, int oreAmount) {
		try {
			return new GoldOre(texture, location, 0.3d, oreAmount, 0.85d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String ID() {
		return "goldOre";
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
		if (!(obj instanceof GoldOre)) {
			return false;
		}
		return true;
	}
	
	

}
