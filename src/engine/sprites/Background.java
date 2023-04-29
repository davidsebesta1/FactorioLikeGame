package engine.sprites;

import engine.rendering.textures.Texture;
import math.Vector2;

public class Background extends Sprite {
	private static final long serialVersionUID = 4930066769715408810L;
	

	public Background(Texture texture, Vector2 location, float zDepth) {
		super(texture, location, zDepth);
		
	}

	public static Background instantiateBackground(Texture texture, Vector2 location, float zDepth) {
		try {
			if (zDepth < 0)
				throw new IllegalArgumentException("Z-Depth must be greater or equal to zero");

			return new Background(texture, location, zDepth);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public static String ID() {
		return "backgroundSprite";
	}

}
