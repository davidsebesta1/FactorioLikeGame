package engine.sprites.ores.oresprites;

import engine.rendering.textures.Texture;
import engine.sprites.ores.OreSprite;
import math.Vector2;

public class AluminiumOre extends OreSprite {
	private static final long serialVersionUID = 8779965118263427564L;
	
	private AluminiumOre(Texture texture, Vector2 location, double zDepth, int oreAmount, double miningHardness) {
		super(texture, location, zDepth, oreAmount, miningHardness);
	}

	
	
	public AluminiumOre instantiateAluminiumOre(Texture texture, Vector2 location, int oreAmount) {
		try {
			return new AluminiumOre(texture, location, 0.3d, oreAmount, 0.95d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String ID() {
		return "aluminiumOre";
	}

}
