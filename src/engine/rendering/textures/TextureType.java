package engine.rendering.textures;

/**
 * There are 3 types of a image, a OPAQUE = no transparent pixels, BITMASK = either fully opaque or fully transparent pixel and a TRANSPARENT =  semi transparent pixels possible.
 * @author David Šebesta
 * @see Texture
 *
 */
public enum TextureType {
	OPAQUE(1),
	BITMASK(2),
	TRANSPARENT(3);
	private final int id;
	
	private TextureType(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
