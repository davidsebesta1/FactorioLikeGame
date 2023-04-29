package engine.sprites.objects.minable;

import engine.rendering.textures.Texture;
import math.Vector2;

public class CarbonPhosphorusPowder extends OreItem {
	private static final long serialVersionUID = 1763104079296350098L;

	public CarbonPhosphorusPowder(Texture texture, Vector2 location, double zDepth) {
		super(texture, location, zDepth);
		// TODO Auto-generated constructor stub
	}
	
	public static CarbonPhosphorusPowder instantiateCarbonPhosphorusPowder(Texture texture, Vector2 location) {
		try {
			return new CarbonPhosphorusPowder(texture, location, 0.72d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public static String ID() {
		return "carbonPhosphorusItem";
	}
	
	@Override
	public String getID() {
		return ID();
	}

}
