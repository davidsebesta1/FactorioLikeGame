package engine.sprites.ores;

import engine.rendering.textures.Texture;
import math.Vector2;

public class CoalOre extends OreSprite{
	private static final long serialVersionUID = -4315002113331971973L;

	protected CoalOre(Texture texture, Vector2 location, double zDepth, int oreAmount) {
		super(texture, location, zDepth, oreAmount);
	}

	public static CoalOre instantiateSprite(Texture texture, Vector2 worldLocation, int oreAmount) {
		try {
			return new CoalOre(texture, worldLocation, 0.5d, oreAmount);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	@Override
	public String ID() {
		return "coalOre";
	}
	

}
