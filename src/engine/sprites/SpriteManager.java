package engine.sprites;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import engine.Game;

public class SpriteManager {
	
	//Main lists
	private static ArrayList<Sprite> spriteList = new ArrayList<>();
	private static HashSet<Sprite> updateSprites = new HashSet<>();

	//Helper lists for sync and concurrent mod. exception avoid
	private static HashSet<Sprite> spriteAddQueue = new HashSet<>();
	private static HashSet<Sprite> spriteUpdateAddQueue = new HashSet<>();

	private static HashSet<Sprite> spriteRemoveQueue = new HashSet<>();
	private static HashSet<Sprite> spriteUpdateRemoveQueue = new HashSet<>();

	private SpriteManager() {}

	public static synchronized void add(Sprite sprite) {
		if (sprite != null) {
			spriteAddQueue.add(sprite);
		}
	}
	
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

	public static synchronized void remove(Sprite sprite) {
		if (sprite != null) {
			spriteRemoveQueue.add(sprite);
		}
	}

	public static synchronized void addUpdateSprite(Sprite sprite) {
		if (sprite != null)
			spriteUpdateAddQueue.add(sprite);
	}

	public static synchronized void removeUpdateSprite(Sprite sprite) {
		if (sprite != null)
			spriteUpdateRemoveQueue.add(sprite);
	}

	public static synchronized List<Sprite> getSprites() {
		return spriteList;
	}

	public static synchronized Set<Sprite> getUpdatableSprites() {
		return updateSprites;
	}

//	public static synchronized void updateAllSprites() {
//		for (Sprite sprite : updateSprites) {
//			sprite.update();
//		}
//	}
	
	public static void updateAllSprites() {
	    updateSprites.parallelStream().forEach(Sprite::update);
	}
}
