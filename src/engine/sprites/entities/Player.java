package engine.sprites.entities;

import java.io.File;
import java.util.Objects;

import javax.swing.GroupLayout.Group;

import engine.Game;
import engine.input.IMouseActionEventListener;
import engine.input.InputManager;
import engine.physics.BoundingBox;
import engine.rendering.Camera;
import engine.rendering.textures.Texture;
import engine.sprites.PhysicsSprite;
import engine.sprites.SpriteManager;
import engine.time.DeltaTime;
import math.Vector2;

public class Player extends PhysicsSprite implements IMouseActionEventListener {
	private static final long serialVersionUID = -6407007599351991639L;
	private Camera camera;

	private boolean inputEnabled;
	
	private boolean buildingModeEnabled;
	
	private Player(Texture texture, Vector2 location, Vector2 velocity, float zDepth) {
		super(texture, location, zDepth);

		this.camera = new Camera(new Vector2(0, 0));
		this.setVelocity(velocity);
		this.inputEnabled = true;
		this.buildingModeEnabled = false;
		this.setCollisionBox(new BoundingBox(this, false, location, this.getSize()));

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

			setLocation(location.add(velocity));

			camera.setLocation(camera.getLocation().add(velocity));
		}
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
	
	@Override
	public void enteredCollision(PhysicsSprite sprite) {
		
		while(collisionBox.doCollideWith(sprite.getCollisionBox())) {
			Vector2 out = sprite.getCenterLocation().sub(getCenterLocation());
			out.normalize();
			out.setX(Math.round(out.getX()));
			out.setY(Math.round(out.getY()));
			this.setLocation(location.sub(out));
			this.camera.setLocation(camera.getLocation().sub(out));
		}
	}

}
