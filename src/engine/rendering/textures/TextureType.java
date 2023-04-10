package engine.rendering.textures;

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
