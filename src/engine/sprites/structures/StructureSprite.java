package engine.sprites.structures;

import engine.rendering.textures.Texture;
import engine.sprites.Sprite;
import math.Vector2;

public class StructureSprite extends Sprite {
	private static final long serialVersionUID = 5574047065458702506L;

	private Vector2 tileSize;

	private StructureSprite(Texture texture, Vector2 location, float zDepth) {
		super(texture, location, zDepth);
		this.tileSize = new Vector2(texture.getImage().getWidth() / 32f, texture.getImage().getHeight() / 32f);
	}

	public Vector2 getTileSize() {
		return tileSize;
	}
}
