package engine.sprites.structures;

import java.awt.image.BufferedImage;

import engine.sprites.Sprite;
import math.Vector2;

public class StructureSprite extends Sprite {
	private static final long serialVersionUID = 5574047065458702506L;

	private StructureSprite(BufferedImage image, Vector2 location, float zDepth) {
		super(image, location, zDepth);
	}

}
