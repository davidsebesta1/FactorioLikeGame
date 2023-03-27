package engine.sprites.objects;

import java.awt.image.BufferedImage;

import engine.sprites.Sprite;
import math.Vector2;

public class Item extends Sprite {
	private static final long serialVersionUID = 379059532463440235L;

	private Item(BufferedImage image, Vector2 location, float zDepth) {
		super(image, location, zDepth);
	}

}
