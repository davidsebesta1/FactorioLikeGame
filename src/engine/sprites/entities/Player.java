package engine.sprites.entities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;

import javax.imageio.ImageIO;

import engine.rendering.SpriteManager;
import engine.sprites.Sprite;
import math.Vector2;

public class Player extends Sprite {
	private static final long serialVersionUID = -6407007599351991639L;
	private Vector2 velocity;

	private boolean inputEnabled;

	private Player(BufferedImage image, Vector2 location, Vector2 velocity, float zDepth) {
		super(image, location, zDepth);
		this.setVelocity(velocity);

		SpriteManager.addUpdateSprite(this);
	}

	public static Player instantiatePlayer(File file, Vector2 location) {
		try {
			BufferedImage image = ImageIO.read(file);
			return new Player(image, location, new Vector2(0, 0), 0.8f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public boolean inputEnabled() {
		return inputEnabled;
	}

	public void setInputEnabled(boolean inputEnabled) {
		this.inputEnabled = inputEnabled;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(inputEnabled, velocity);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		return inputEnabled == other.inputEnabled && Objects.equals(velocity, other.velocity);
	}

}
