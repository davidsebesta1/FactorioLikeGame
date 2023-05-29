package engine.sprites;

import java.awt.Rectangle;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

import engine.Game;
import engine.rendering.optimalization.Chunk;
import engine.rendering.textures.Texture;
import engine.rendering.textures.TextureLibrary;
import math.Vector2;

/**
 * Sprite is a basic abstract class that provides functionality to the whole game.
 * @author David Šebesta
 *
 */
public abstract class Sprite implements Comparable<Sprite>, ISpriteBehaviour, Serializable, Cloneable {
	private static final long serialVersionUID = 2893665038957303083L;

	/**
	 * Texture of a sprite
	 */
	protected transient Texture texture;
	/**
	 * Its texture name
	 * @see Texture
	 */
	protected String textureName;
	/**
	 * Z-depth of a sprite (1-closest, 0-furtherest)
	 */
	protected double zDepth;

	/**
	 * World location
	 */
	protected Vector2 location;
	/**
	 * World size
	 */
	protected Vector2 size;

	/**
	 * Is sprite visible
	 */
	protected boolean isVisible;
	
	/**
	 * Currently assigned chunk
	 * @see Chunk
	 * @see ChunkManager
	 */
	protected transient Chunk currentChunk;

	/**
	 * Protected class constructor
	 * @param texture
	 * @param location
	 * @param zDepth
	 */
	protected Sprite(Texture texture, Vector2 location, double zDepth) {
		super();

		this.texture = texture;
		this.textureName = Sprite.getTextureNameByObject(texture);
		this.size = new Vector2(texture.getImage().getWidth(), texture.getImage().getHeight());

		this.zDepth = zDepth;
		this.location = location;
		this.isVisible = true;

		SpriteManager.add(this);
	}

	public Chunk getCurrentChunk() {
		return currentChunk;
	}

	public void setCurrentChunk(Chunk currentChunk) {
		this.currentChunk = currentChunk;
	}

	public double getzDepth() {
		return zDepth;
	}

	public void setzDepth(double zDepth) {
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
		return Objects.hash(isVisible, location, size, textureName, zDepth);
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
		return isVisible == other.isVisible && Objects.equals(location, other.location) && Objects.equals(size, other.size) && Objects.equals(textureName, other.textureName);
	}

	public static String getTextureNameByObject(Texture value) {
		for (Map.Entry<String, Texture> entry : TextureLibrary.getLibrary().entrySet()) {
			if (entry.getValue().equals(value)) {
				return entry.getKey();
			}
		}
		return null;
	}

	/**
	 * Returns bounds of a sprite as a Rectangle object
	 * @return Rectangle bounds
	 * @see Rectangle
	 */
	public Rectangle getBoundsAsRectangle() {
		return new Rectangle((int) this.location.getX(), (int) this.location.getY(), (int) this.size.getX(), (int) this.size.getY());
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

	/**
	 * Method used to clear references of this sprite
	 */
	public void destroy() {
		try {
			SpriteManager.remove(this);
			Game.getInstance().getCurrentWorld().getChunkManager().forceRemoveSprite(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onMouseClicked() {
		// Have to be overridden
	}

	public static String ID() {
		return "defaultSprite";
	}
}