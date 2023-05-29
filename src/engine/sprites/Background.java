package engine.sprites;

import engine.rendering.textures.Texture;
import math.Vector2;

/**
 * A sprite used for background
 * @author David Šebesta
 *
 */
public class Background extends Sprite {
	private static final long serialVersionUID = 4930066769715408810L;
	

	/**
	 * Private constructor
	 * @param texture
	 * @param location
	 * @param zDepth
	 */
	private Background(Texture texture, Vector2 location, float zDepth) {
		super(texture, location, zDepth);
		
	}

	/**
	 * Static instatiation method with specified texture
	 * @param texture
	 * @param location
	 * @param zDepth
	 * @return Background object
	 */
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
