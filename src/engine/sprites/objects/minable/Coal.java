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

}
