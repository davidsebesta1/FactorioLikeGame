package engine.rendering;

import java.util.ArrayList;
import java.util.Collections;

import engine.Sprite;

public abstract class SpriteManager {
	private static ArrayList<Sprite> spriteList = new ArrayList<>();
	
	private SpriteManager() {}
	
	public static synchronized void add(Sprite sprite) {
		spriteList.add(sprite);
		
		//sort by z-depth
		Collections.sort(spriteList);
	}
	
	public static ArrayList<Sprite> getSprites(){
		return spriteList;
	}
}
