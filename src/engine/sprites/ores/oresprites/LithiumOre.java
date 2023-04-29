package engine.sprites.ores.oresprites;

import engine.rendering.textures.Texture;
import engine.sprites.ores.OreSprite;
import math.Vector2;

public class LithiumOre extends OreSprite {
	private static final long serialVersionUID = -1202506759023891949L;
	
	private LithiumOre(Texture texture, Vector2 location, double zDepth, int oreAmount, double miningHardness) {
		super(texture, location, zDepth, oreAmount, miningHardness);
	}

	public LithiumOre instantiateLithiumOre(Texture texture, Vector2 location, int oreAmount) {
		try {
			return new LithiumOre(texture, location, 0.3d, oreAmount, 1.1d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String ID() {
		return "lithiumOre";
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
		if (!(obj instanceof LithiumOre)) {
			return false;
		}
		return true;
	}
	
	

}
