package engine.sprites.ores;

import java.util.Objects;

import engine.rendering.textures.Texture;
import engine.sprites.Sprite;
import math.Vector2;

public abstract class OreSprite extends Sprite {
	private static final long serialVersionUID = -805356985399189622L;

	protected int oreAmount;
	
	//Multiplier for mining time
	protected final double miningHardness;

	protected OreSprite(Texture texture, Vector2 location, double zDepth, int oreAmount, double miningHardness) {
		super(texture, location, zDepth);
		this.oreAmount = oreAmount;
		this.miningHardness = miningHardness;
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

	public int getOreAmount() {
		return oreAmount;
	}
	
	public boolean tryRemoveResource() {
		oreAmount--;
		
		if(oreAmount < 0) {
			oreAmount = 0;
			return false;
		}
		
		return true;
	}
	
	public static String ID() {
		return "oreSprite";
	}
}
