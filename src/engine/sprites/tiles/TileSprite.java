package engine.sprites.tiles;

import java.awt.image.BufferedImage;

import engine.sprites.Sprite;
import math.Vector2;

public class TileSprite extends Sprite {
	private static final long serialVersionUID = -1961159418183745510L;

	private TileSprite(BufferedImage image, Vector2 location, float zDepth) {
		super(image, location, zDepth);
	}

}
