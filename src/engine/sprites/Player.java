package engine.sprites;

import java.awt.image.BufferedImage;
import java.io.File;

import math.Vector2;

public class Player extends Sprite {
	private static final long serialVersionUID = -6407007599351991639L;
	private Vector2 velocity;

	private Player(BufferedImage image, Vector2 location, Vector2 velocity, float zDepth) {
		super(image, location, zDepth);
		this.velocity = velocity;
	}
	
	public static Player instantiatePlayer(File file, Vector2 location, Vector2 velocity, float zDepth) {
		Player temp = (Player) Sprite.instantiateSprite(file, location, zDepth);
		temp.velocity = velocity;
		
		return temp;
	}
}
