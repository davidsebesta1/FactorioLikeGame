package engine.rendering;

import java.util.ArrayList;
import java.util.Collections;

import engine.sprites.Sprite;

public abstract class SpriteManager {
	private static ArrayList<Sprite> spriteList = new ArrayList<>();
	private static ArrayList<Sprite> updateSprites = new ArrayList<>();

	private SpriteManager() {
	}

	public static synchronized void add(Sprite sprite) {
		if (sprite != null) {
			spriteList.add(sprite);

			// sort by z-depth
			Collections.sort(spriteList);
		}
	}

	public static void addUpdateSprite(Sprite sprite) {
		if (sprite != null)
			updateSprites.add(sprite);
	}

	public static ArrayList<Sprite> getSprites() {
		return spriteList;
	}

	public static ArrayList<Sprite> getUpdatableSprites() {
		return updateSprites;
	}
	
	public static void updateAllSprites() {
		for (Sprite sprite : updateSprites) {
			sprite.update();
		}
	}
}
