package engine.sprites.objects.minable;

import engine.rendering.textures.Texture;
import math.Vector2;

public class Nickel extends OreItem {
	private static final long serialVersionUID = 6181779958739971397L;

	public Nickel(Texture texture, Vector2 location, double zDepth) {
		super(texture, location, zDepth);
		// TODO Auto-generated constructor stub
	}
	
	public static Nickel instantiateNickel(Texture texture, Vector2 location) {
		try {
			return new Nickel(texture, location, 0.72d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public static String ID() {
		return "nickelItem";
	}
	
	@Override
	public String getID() {
		return ID();
	}

}
