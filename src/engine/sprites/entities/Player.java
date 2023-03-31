package engine.sprites.entities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;

import javax.imageio.ImageIO;

import engine.Game;
import engine.input.IMouseActionEventListener;
import engine.input.InputManager;
import engine.rendering.Camera;
import engine.rendering.textures.Texture;
import engine.sprites.Sprite;
import engine.sprites.SpriteManager;
import engine.time.DeltaTime;
import math.Vector2;

public class Player extends Sprite implements IMouseActionEventListener {
	private static final long serialVersionUID = -6407007599351991639L;
	private Camera camera;

	private Vector2 velocity;

	private boolean inputEnabled;

	private Player(Texture texture, Vector2 location, Vector2 velocity, float zDepth) {
		super(texture, location, zDepth);

		this.camera = new Camera(new Vector2(0, 0));

		this.setVelocity(velocity);

		this.inputEnabled = true;

		InputManager.addMouseActionListener(this);

		SpriteManager.addUpdateSprite(this);
	}

	public static Player instantiatePlayer(File file) {
		try {
			Texture texture = Texture.createTexture(file);
			return new Player(texture, Game.getInstance().getResolution().div(2), Vector2.zero, 0.8f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public static Player instantiatePlayer(File file, float alpha) {
		try {
			Texture texture = Texture.createTexture(file, alpha);
			return new Player(texture, Game.getInstance().getResolution().div(2), Vector2.zero, 0.8f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public void update() {
		if (inputEnabled) {
			velocity = InputManager.getDirectionalInput().mul(100f * (float) DeltaTime.getDeltaTime());

			location = location.add(velocity);

			camera.setLocation(camera.getLocation().add(velocity));
		}
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

	public Camera getCamera() {
		return camera;
	}

	public boolean isInputEnabled() {
		return inputEnabled;
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

	@Override
	public void mousePressed(Vector2 screenCoordinate) {
		System.out.println(screenCoordinate);
		System.out.println(Camera.screenToWorldCoordinates(screenCoordinate));

	}

	@Override
	public void mouseReleased(Vector2 screenCoordinate) {
		// Unused

	}

}
