package engine.sprites.ores.oresprites;

import engine.rendering.textures.Texture;
import engine.sprites.ores.OreSprite;
import math.Vector2;

public class PlatiniumOre extends OreSprite {
	private static final long serialVersionUID = -5439209363275529538L;

	private PlatiniumOre(Texture texture, Vector2 location, double zDepth, int oreAmount, double miningHardness) {
		super(texture, location, zDepth, oreAmount, miningHardness);
		// TODO Auto-generated constructor stub
	}

	public PlatiniumOre instantiatePlatiniumOre(Texture texture, Vector2 location, int oreAmount) {
		try {
			return new PlatiniumOre(texture, location, 0.3d, oreAmount, 1.3d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String ID() {
		return "platiniumOre";
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
		if (!(obj instanceof PlatiniumOre)) {
			return false;
		}
		return true;
	}
	
	

}
