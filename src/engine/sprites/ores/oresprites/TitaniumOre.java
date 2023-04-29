package engine.sprites.ores.oresprites;

import engine.rendering.textures.Texture;
import engine.sprites.ores.OreSprite;
import math.Vector2;

public class TitaniumOre extends OreSprite {
	private static final long serialVersionUID = -1643749667967483986L;

	private TitaniumOre(Texture texture, Vector2 location, double zDepth, int oreAmount, double miningHardness) {
		super(texture, location, zDepth, oreAmount, miningHardness);
	}

	public TitaniumOre instantiateTitaniumOre(Texture texture, Vector2 location, int oreAmount) {
		try {
			return new TitaniumOre(texture, location, 0.3d, oreAmount, 1d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String ID() {
		return "titaniumOre";
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
		if (!(obj instanceof TitaniumOre)) {
			return false;
		}
		return true;
	}
	
	


}
