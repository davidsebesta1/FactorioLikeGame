package engine.sprites.objects.minable;

import engine.rendering.textures.Texture;
import math.Vector2;

public class Platinium extends OreItem {
	private static final long serialVersionUID = -2199255378219085074L;

	private Platinium(Texture texture, Vector2 location, double zDepth) {
		super(texture, location, zDepth);
	}
	
	public static Platinium instantiatePlatinium(Texture texture, Vector2 location) {
		try {
			return new Platinium(texture, location, 0.72d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public static String ID() {
		return "platiniumItem";
	}
	
	@Override
	public String getID() {
		return ID();
	}

}
