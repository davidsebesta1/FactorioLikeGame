package engine.sprites;

import java.io.File;
import java.io.Serializable;
import java.util.Objects;

import engine.rendering.textures.Texture;
import math.Vector2;

public class Sprite implements Comparable<Sprite>, ISpriteBehaviour, Serializable {
	private static final long serialVersionUID = 2893665038957303083L;
	protected Texture texture;
	protected float zDepth;

	protected Vector2 location;
	protected Vector2 size;

	protected boolean isVisible;

	protected Sprite(Texture texture, Vector2 location, float zDepth) {
		super();

		this.texture = texture;
		this.size = new Vector2(texture.getImage().getWidth(), texture.getImage().getHeight());

		this.zDepth = zDepth;
		this.location = location;
		this.isVisible = true;

		SpriteManager.add(this);
	}

	public static Sprite instantiateSprite(File file, Vector2 location, float zDepth) {
		try {
			Texture texture = Texture.createTexture(file);
			if (zDepth < 0)
				throw new IllegalArgumentException("Z-Depth must be greater or equal to zero");

			return new Sprite(texture, location, zDepth);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public float getzDepth() {
		return zDepth;
	}

	public void setzDepth(float zDepth) {
		this.zDepth = zDepth;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public Vector2 getLocation() {
		return location;
	}
	
	public Vector2 getCenterLocation() {
		return location.add(size.div(2));
	}

	public void setLocation(Vector2 location) {
		this.location = location;
	}

	public Vector2 getSize() {
		return size;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	@Override
	public int compareTo(Sprite o) {
		if (this.zDepth == o.getzDepth())
			return 0;
		else if (this.zDepth > o.getzDepth())
			return 1;
		else
			return -1;
	}

	@Override
	public int hashCode() {
		return Objects.hash(isVisible, location, size, texture, zDepth);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sprite other = (Sprite) obj;
		return isVisible == other.isVisible && Objects.equals(location, other.location)
				&& Objects.equals(size, other.size) && Objects.equals(texture, other.texture)
				&& Float.floatToIntBits(zDepth) == Float.floatToIntBits(other.zDepth);
	}

	@Override
	public String toString() {
		return "Sprite [texture=" + texture + ", zDepth=" + zDepth + "]";
	}

	@Override
	public void start() {
		// Have to be overridden
	}

	@Override
	public void update() {
		// Have to be overridden
	}

	public void destroy() {
		try {
			SpriteManager.getSprites().remove(this);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onMouseClicked() {
		// Have to be overridden
	}
}