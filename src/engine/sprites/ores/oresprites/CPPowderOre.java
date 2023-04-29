package engine.sprites.ores.oresprites;

import engine.rendering.textures.Texture;
import engine.sprites.ores.OreSprite;
import math.Vector2;

public class CPPowderOre extends OreSprite {
	private static final long serialVersionUID = 5913383213187637566L;

	private CPPowderOre(Texture texture, Vector2 location, double zDepth, int oreAmount, double miningHardness) {
		super(texture, location, zDepth, oreAmount, miningHardness);
	}

	public CPPowderOre instantiateCPPowderOre(Texture texture, Vector2 location, int oreAmount) {
		try {
			return new CPPowderOre(texture, location, 0.3d, oreAmount, 1.05d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String ID() {
		return "CPPowderOre";
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
		if (!(obj instanceof CPPowderOre)) {
			return false;
		}
		return true;
	}
	
	

}
