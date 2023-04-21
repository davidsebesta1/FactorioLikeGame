package engine.sprites;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import engine.Game;

public class SpriteManager {
	
	//Main lists
	private static ArrayList<Sprite> spriteList = new ArrayList<>();
	private static ArrayList<Sprite> updateSprites = new ArrayList<>();

	
	//Helper lists for sync and concurrent mod exception avoid
	private static ArrayList<Sprite> spriteAddQueue = new ArrayList<>();
	private static ArrayList<Sprite> spriteUpdateAddQueue = new ArrayList<>();

	private static ArrayList<Sprite> spriteRemoveQueue = new ArrayList<>();
	private static ArrayList<Sprite> spriteUpdateRemoveQueue = new ArrayList<>();

	private SpriteManager() {
	}

	public static synchronized void add(Sprite sprite) {
		if (sprite != null) {
			spriteAddQueue.add(sprite);
		}
	}

	public static synchronized void frameSpriteSynchronization() {

		// Remove to remove sprites
		if(!spriteRemoveQueue.isEmpty()) {
			spriteList.removeAll(spriteRemoveQueue);
			spriteRemoveQueue.clear();
		}

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
		
		// Add sprites
		if(!spriteAddQueue.isEmpty()) {
			spriteList.addAll(spriteAddQueue);
			Game.getInstance().getCurrentWorld().getChunkManager().assignChunk(spriteAddQueue);
			spriteAddQueue.clear();
			
			// sort by z-depth
			Collections.sort(spriteList);
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

	public static synchronized List<Sprite> getUpdatableSprites() {
		return updateSprites;
	}

	public static synchronized void updateAllSprites() {
		for (Sprite sprite : updateSprites) {
			sprite.update();
		}
	}
}
