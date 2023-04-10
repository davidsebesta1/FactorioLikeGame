package engine.sprites.ores;

import java.util.Objects;

import engine.rendering.textures.Texture;
import engine.sprites.Sprite;
import math.Vector2;

public class OreSprite extends Sprite {
	private static final long serialVersionUID = -805356985399189622L;

	protected int oreAmount;

	protected OreSprite(Texture texture, Vector2 location, double zDepth, int oreAmount) {
		super(texture, location, zDepth);
		this.oreAmount = oreAmount;
	}

	public static OreSprite instantiateSprite(Texture texture, Vector2 worldLocation, int oreAmount) {
		try {
			return new OreSprite(texture, worldLocation, 0.5d, oreAmount);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(oreAmount);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		OreSprite other = (OreSprite) obj;
		return oreAmount == other.oreAmount;
	}
}
