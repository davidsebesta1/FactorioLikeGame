package engine.sprites.entities;

import java.awt.image.BufferedImage;

import engine.sprites.Sprite;
import math.Vector2;

public class Player extends Sprite {
	private static final long serialVersionUID = -6407007599351991639L;
	private Vector2 velocity;

	private Player(BufferedImage image, Vector2 location, Vector2 velocity, float zDepth) {
		super(image, location, zDepth);
		this.setVelocity(velocity);
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
}
