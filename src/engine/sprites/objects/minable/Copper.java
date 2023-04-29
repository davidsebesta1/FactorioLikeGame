package engine.sprites.objects.minable;

import engine.rendering.textures.Texture;
import math.Vector2;

public class Copper extends OreItem {
	private static final long serialVersionUID = 6342528931742707739L;

	private Copper(Texture texture, Vector2 location, double zDepth) {
		super(texture, location, zDepth);
	}
	
	public static Copper instantiateCopper(Texture texture, Vector2 location) {
		try {
			return new Copper(texture, location, 0.72d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public static String ID() {
		return "copperItem";
	}
	
	@Override
	public String getID() {
		return ID();
	}

}
