package engine.sprites.structures;

import java.awt.image.BufferedImage;

import engine.sprites.Sprite;
import math.Vector2;

public class StructureSprite extends Sprite {
	private static final long serialVersionUID = 5574047065458702506L;

	private Vector2 tileSize;

	private StructureSprite(BufferedImage image, Vector2 location, float zDepth) {
		super(image, location, zDepth);
		this.tileSize = new Vector2(image.getWidth() / 32f, image.getHeight() / 32f);
	}

	public Vector2 getTileSize() {
		return tileSize;
	}
}
