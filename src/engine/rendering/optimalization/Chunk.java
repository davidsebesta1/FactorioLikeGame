package engine.rendering.optimalization;

import java.util.ArrayList;

import engine.sprites.Sprite;
import math.Vector2;

public class Chunk {
	
	private ArrayList<Sprite> sprites;
	private Vector2 location;
	private Vector2 size;

	public Chunk(Vector2 location, Vector2 size) {
		sprites = new ArrayList<>();
		this.location = location;
		this.size = size;
	}
	
	public boolean tryAddSprite(Sprite sprite) {
		return sprites.add(sprite);
	}
	
	public boolean tryRemoveSprite(Sprite sprite) {
		return sprites.remove(sprite);
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

	@Override
	public String toString() {
		return "Chunk [location=" + location + ", size=" + size + "]";
	}
	
	
}
