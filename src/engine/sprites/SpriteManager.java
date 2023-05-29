package engine.sprites;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import engine.Game;

/**
 * Static class SpriteManager provides management of a sprites, this includes adding them to the chunks, handling new sprites being added to the world and removing them. 
 * All sprites are added/removed one frame after they are added in method, this prevents ConcurrentModificationException being thrown by the game loop.
 * @author David Šebesta
 *
 */
public class SpriteManager {
	
	//Main lists
	private static ArrayList<Sprite> spriteList = new ArrayList<>();
	private static HashSet<Sprite> updateSprites = new HashSet<>();

	//Helper lists for sync and concurrent mod. exception avoid
	private static HashSet<Sprite> spriteAddQueue = new HashSet<>();
	private static HashSet<Sprite> spriteUpdateAddQueue = new HashSet<>();

	private static HashSet<Sprite> spriteRemoveQueue = new HashSet<>();
	private static HashSet<Sprite> spriteUpdateRemoveQueue = new HashSet<>();

	/**
	 * Private constructor
	 */
	private SpriteManager() {}

	/**
	 * Add sprite to the list, will be added next frame.
	 * @param sprite
	 */
	public static synchronized void add(Sprite sprite) {
		if (sprite != null) {
			spriteAddQueue.add(sprite);
		}
	}
	
	/**
	 * Clears both sprite lists
	 */
	public static synchronized void clearAll() {
		spriteList.clear();
		updateSprites.clear();
	}
	
	/**
	 * Adds a sorted sprite to the spritesList in order of Z-depth
	 * @param sprite
	 */
	private static synchronized void addSorted(Sprite sprite) {
	    int index = Collections.binarySearch(spriteList, sprite, new Comparator<Sprite>() {
	        public int compare(Sprite s1, Sprite s2) {
	            return (int) Math.round(s1.getzDepth() - s2.getzDepth());
	        }
	    });
	    if (index < 0) {
	        index = -(index + 1);
	    }
	    spriteList.add(index, sprite);
	}

	/**
	 * Synchronized awaiting sprites to be added to the list with actual list
	 */
	public static synchronized void frameSpriteSynchronization() {
		// Remove to remove update sprites
		if(!spriteUpdateRemoveQueue.isEmpty()) {
			updateSprites.removeAll(spriteUpdateRemoveQueue);
			spriteUpdateRemoveQueue.clear();
		}

		// Add updatable sprites
		if(!spriteUpdateAddQueue.isEmpty()) {
			updateSprites.addAll(spriteUpdateAddQueue);
			spriteUpdateAddQueue.clear();
		}
		
		// Remove to remove sprites
		if(!spriteRemoveQueue.isEmpty()) {
			spriteList.removeAll(spriteRemoveQueue);
			spriteRemoveQueue.clear();
		}
		
		// Add sprites
		if(!spriteAddQueue.isEmpty()) {
			for(Sprite sprite : spriteUpdateAddQueue) {
				addSorted(sprite);
			}
			Game.getInstance().getCurrentWorld().getChunkManager().assignChunk(spriteAddQueue);
			spriteAddQueue.clear();
			
		}

	}

	/**
	 * Removes sprite next frame
	 * @param sprite
	 */
	public static synchronized void remove(Sprite sprite) {
		if (sprite != null) {
			spriteRemoveQueue.add(sprite);
		}
	}

	/**
	 * Adds a sprite that has update() method called every frame
	 * @param update sprite
	 */
	public static synchronized void addUpdateSprite(Sprite sprite) {
		if (sprite != null)
			spriteUpdateAddQueue.add(sprite);
	}

	/**
	 * Removes a updatable sprite
	 * @param sprite
	 */
	public static synchronized void removeUpdateSprite(Sprite sprite) {
		if (sprite != null)
			spriteUpdateRemoveQueue.add(sprite);
	}

	/**
	 * Returns a List of sprites
	 * @return List of sprites
	 */
	public static synchronized List<Sprite> getSprites() {
		return spriteList;
	}

	/**
	 * Returns a list of updatable sprites
	 * @return List of updatable sprite
	 */
	public static synchronized Set<Sprite> getUpdatableSprites() {
		return updateSprites;
	}
	
	/**
	 * Updates all updatable sprites preferably in parallel
	 */
	public static void updateAllSprites() {
	    updateSprites.parallelStream().forEach(Sprite::update);
	}
}
