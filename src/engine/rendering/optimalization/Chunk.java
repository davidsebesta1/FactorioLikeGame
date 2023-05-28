package engine.rendering.optimalization;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;

import engine.sprites.Sprite;
import math.Vector2;

/**
 * Chunk is a A x A size object that this managed by ChunkManager, chunk class privides optimalization tot he game by redrawing chunk only if any sprites within has moved, this results in less drawing each frame.
 * @see ChunkManager
 * @author David Šebesta
 */
public class Chunk {
	private ArrayList<Sprite> sprites;

	private Vector2 location;
	private Vector2 size;

	private BufferedImage buffer;

	private Rectangle rectangle;

	private boolean needResolve;

	/**
	 * A class constructor
	 * @param location
	 * @param size
	 */
	public Chunk(Vector2 location, Vector2 size) {
		this.sprites = new ArrayList<>();
		this.location = location;
		this.size = size;
		this.needResolve = false;

		this.rectangle = new Rectangle((int) location.getX(), (int) location.getY(), (int) size.getX(), (int) size.getY());

		GraphicsConfiguration gfxConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		buffer = gfxConfig.createCompatibleImage((int) size.getX(), (int) size.getY(), Transparency.BITMASK);
	}

	/**
	 * Resolves chunk stuff for frame
	 */
	public synchronized void resolveFrame() {
		cacheImage();
		this.needResolve = false;
	}

	/**
	 * Adds sprites sorted by Z-depth
	 * @param spriteList
	 */
	private synchronized void addAllSorted(Set<Sprite> spriteList) {
		for (Sprite sprite : spriteList) {
			if(sprites.contains(sprite)) return;
			
			int index = Collections.binarySearch(sprites, sprite, new Comparator<Sprite>() {
				public int compare(Sprite s1, Sprite s2) {
					if (s1.getzDepth() > s2.getzDepth())
						return 1;
					else
						return -1;
				}
			});
			if (index < 0) {
				index = -(index + 1);
			}
			
			sprites.add(index, sprite);
		}
	}

	/**
	 * Adds a sprite sorted by z-depth
	 * @param sprite
	 * @return successfully added sprite
	 */
	private synchronized boolean addSorted(Sprite sprite) {
		if(!sprites.contains(sprite)) {
			int index = Collections.binarySearch(sprites, sprite, new Comparator<Sprite>() {
				public int compare(Sprite s1, Sprite s2) {
					if (s1.getzDepth() > s2.getzDepth())
						return 1;
					else
						return -1;
				}
			});
			if (index < 0) {
				index = -(index + 1);
			}
			sprites.add(index, sprite);
			return true;
		}
		return false;
	}

	/**
	 * Tries to add chunk to the sprite, also makes sure that chunks needs to be resolved
	 * @param sprite
	 * @return false if sprite is in chunk arraylist, otherwise true
	 */
	public synchronized boolean tryAddSprite(Sprite sprite) {
		needResolve = true;
		return addSorted(sprite);
	}

	/**
	 * Tries to remove sprite from chunk rendering
	 * @param sprite
	 * @return true if sprite has been sucessfully removed from chunk
	 */
	public synchronized boolean tryRemoveSprite(Sprite sprite) {
		needResolve = true;
		return sprites.remove(sprite);
	}

	/**
	 * Method that cache image to a buffered image for rendering
	 */
	public void cacheImage() {
		Graphics g = buffer.getGraphics();
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

		for (Sprite sprite : sprites) {
			if (sprite.getTexture().getImage() != null && sprite.isVisible()) {
				g2d.drawImage(sprite.getTexture().getImage(), (int) (sprite.getLocation().getX()- location.getX()), (int) (sprite.getLocation().getY() - location.getY()), null);
			}
		}

		g2d.dispose();
		g.dispose();
	}

	/**
	 * Checks if sprite has been fully drawn (not out of bounds, etc)
	 * @param sprite
	 * @return true if sprite has been fully drawn, otherwise false
	 */
	public boolean isFullyDrawnSprite(Sprite sprite) {
		// Compare the dimensions of the source image with the dimensions of the drawn image
		BufferedImage sourceImage = sprite.getTexture().getImage();
		int sourceWidth = sourceImage.getWidth();
		int sourceHeight = sourceImage.getHeight();

		int drawnWidth = (int) (sprite.getLocation().getX() - location.getX() + sourceWidth);
		int drawnHeight = (int) (sprite.getLocation().getY() - location.getY() + sourceHeight);

		return !(drawnWidth <= 0 || drawnWidth > buffer.getWidth() || drawnHeight <= 0
				|| drawnHeight > buffer.getHeight());
	}

	public BufferedImage getBuffer() {
		return buffer;
	}

	/**
	 * Checks if sprites left upper corner (its location) is within this chunk borders
	 * @param sprite
	 * @return returns true if sprite is within chunk bounds (its left upper corner)
	 */
	public boolean isWithinChunk(Sprite sprite) {
		// Calculate the bounds of the chunk
		int chunkX = (int) (location.getX());
		int chunkY = (int) (location.getY());
		int chunkWidth = (int) size.getX();
		int chunkHeight = (int) size.getY();

		// Calculate the bounds of the sprite
		int spriteX = (int) sprite.getLocation().getX();
		int spriteY = (int) sprite.getLocation().getY();
		int spriteWidth = (int) sprite.getSize().getX();
		int spriteHeight = (int) sprite.getSize().getY();

		// Check if the sprite is within the chunk bounds
		boolean isWithinXBounds = spriteX + spriteWidth > chunkX && spriteX < chunkX + chunkWidth;
		boolean isWithinYBounds = spriteY + spriteHeight > chunkY && spriteY < chunkY + chunkHeight;
		return isWithinXBounds && isWithinYBounds;
	}

	public ArrayList<Sprite> getSprites() {
		return sprites;
	}

	public Vector2 getLocation() {
		return location;
	}

	public Vector2 getCenterLocation() {
		return location.add(size.div(2f));
	}

	public Vector2 getSize() {
		return size;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public boolean needsResolve() {
		return needResolve;
	}

	@Override
	public String toString() {
		return "Chunk [location=" + location + ", size=" + size + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(location, size);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Chunk)) {
			return false;
		}
		Chunk other = (Chunk) obj;
		return Objects.equals(location, other.location) && Objects.equals(size, other.size);
	}
}
