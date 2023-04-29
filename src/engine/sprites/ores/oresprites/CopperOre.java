package engine.sprites.ores.oresprites;

import engine.rendering.textures.Texture;
import engine.sprites.ores.OreSprite;
import math.Vector2;

public class CopperOre extends OreSprite {
	private static final long serialVersionUID = -5097230824359270971L;
	
	private CopperOre(Texture texture, Vector2 location, double zDepth, int oreAmount, double miningHardness) {
		super(texture, location, zDepth, oreAmount, miningHardness);
	}

	public CopperOre instantiateCopperOre(Texture texture, Vector2 location, int oreAmount) {
		try {
			return new CopperOre(texture, location, 0.3d, oreAmount, 0.9d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String ID() {
		return "copperOre";
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
		if (!(obj instanceof CopperOre)) {
			return false;
		}
		return true;
	}
	
	

}
