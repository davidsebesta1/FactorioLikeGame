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
import java.util.Objects;

import engine.Game;
import engine.sprites.Sprite;
import math.Vector2;

public class Chunk {

	private ArrayList<Sprite> sprites;
	private Vector2 location;
	private Vector2 size;

	private BufferedImage buffer;
	
	private Rectangle rectangle;

	public Chunk(Vector2 location, Vector2 size) {
		sprites = new ArrayList<>();
		this.location = location;
		this.size = size;
		
		this.rectangle = new Rectangle((int) location.getX(),(int) location.getY(),(int) size.getX(),(int) size.getY());

		GraphicsConfiguration gfxConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		buffer = gfxConfig.createCompatibleImage((int) size.getX(), (int) size.getY(), Transparency.BITMASK);
	}
	
	public void sort() {
		Collections.sort(sprites);
	}

	public boolean tryAddSprite(Sprite sprite) {
		return sprites.add(sprite);
	}

	public boolean tryRemoveSprite(Sprite sprite) {
		return sprites.remove(sprite);
	}

	public void cacheImage() {
		Graphics g = buffer.getGraphics();
		Graphics2D g2d = (Graphics2D) g;

		g2d.clearRect(0, 0, buffer.getWidth(), buffer.getHeight());
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

		for (Sprite sprite : sprites) {
				if (sprite.getTexture().getImage() != null && sprite.isVisible()) {
					g2d.drawImage(sprite.getTexture().getImage(), (int) (sprite.getLocation().getX() - location.getX()),
							(int) (sprite.getLocation().getY() - location.getY()), null);
					
					if(!isFullyDrawnSprite(sprite)) {
						System.out.println(sprite + " aaaaaaa");
						Game.getInstance().getCurrentWorld().getChunkManager().fixBetweenChunkSprite(sprite);
					}
				}
		}
		
		g2d.dispose();
		g.dispose();
	}
	
	public boolean isFullyDrawnSprite(Sprite sprite) {
		// Compare the dimensions of the source image with the dimensions of the drawn image
	    BufferedImage sourceImage = sprite.getTexture().getImage();
	    int sourceWidth = sourceImage.getWidth();
	    int sourceHeight = sourceImage.getHeight();

	    int drawnWidth = (int) (sprite.getLocation().getX() - location.getX() + sourceWidth);
	    int drawnHeight = (int) (sprite.getLocation().getY() - location.getY() + sourceHeight);

	    return !(drawnWidth <= 0 || drawnWidth > buffer.getWidth() || drawnHeight <= 0 || drawnHeight > buffer.getHeight());
	}

	public BufferedImage getBuffer() {
		return buffer;
	}

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

	@Override
	public String toString() {
		return "Chunk [location=" + location + ", size=" + size + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(buffer, location, size);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chunk other = (Chunk) obj;
		return Objects.equals(buffer, other.buffer) && Objects.equals(location, other.location)
				&& Objects.equals(size, other.size) && Objects.equals(sprites, other.sprites);
	}
}
