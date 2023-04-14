package engine.sprites.objects.minable;

import java.io.File;

import engine.rendering.textures.Texture;
import engine.sprites.objects.Item;
import engine.sprites.structures.conveyors.ConveyorBelt;
import engine.sprites.structures.conveyors.ConveyorBeltDirection;
import math.Vector2;

public class Coal extends Item {
	private static final long serialVersionUID = -7622693955395046911L;

	public Coal(Texture texture, Vector2 location, float zDepth) {
		super(texture, location, zDepth);
	}

	public static Coal instantiateCoal(File file, Vector2 location) {
		try {
			Texture texture = Texture.createTexture(file);
			return new Coal(texture, location, 0.72f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public static Coal instantiateCoal(Texture texture, Vector2 location) {
		try {
			return new Coal(texture, location, 0.72f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public String toString() {
		return "Coal []";
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}

	@Override
	public String ID() {
		return "coalItem";
	}
	
	

}
