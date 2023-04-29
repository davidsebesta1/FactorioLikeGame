package engine.sprites.objects.minable;

import engine.rendering.textures.Texture;
import math.Vector2;

public class Aluminium extends OreItem {
	private static final long serialVersionUID = 6864946137458708673L;

	protected Aluminium(Texture texture, Vector2 location, double zDepth) {
		super(texture, location, zDepth);
	}
	
	public static Aluminium instantiateAluminium(Texture texture, Vector2 location) {
		try {
			return new Aluminium(texture, location, 0.72d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public static String ID() {
		return "aluminiumItem";
	}
	
	@Override
	public String getID() {
		return ID();
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof Aluminium)) {
			return false;
		}
		return true;
	}
	
	

}
