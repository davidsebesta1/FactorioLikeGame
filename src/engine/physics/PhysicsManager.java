package engine.physics;

import java.util.ArrayList;

import engine.sprites.PhysicsSprite;

public class PhysicsManager {
	private static ArrayList<PhysicsSprite> physicsSprites = new ArrayList<>();
	
	private static ArrayList<PhysicsSprite> toAdd = new ArrayList<>();
	private static ArrayList<PhysicsSprite> toRemove = new ArrayList<>();

	private PhysicsManager() {
	}
	
	public static synchronized void framePhysicsSpriteSync() {
		if(!toRemove.isEmpty()) {
			physicsSprites.removeAll(toRemove);
			toRemove.clear();
		}
		
		
		if(!toAdd.isEmpty()) {
			physicsSprites.addAll(toAdd);
			toAdd.clear();
		}
		
	}

	public static void addPhysicsSprite(PhysicsSprite sprite) {
		if (sprite != null) {
			toAdd.add(sprite);
		}
	}

	public static void removePhysicsSprite(PhysicsSprite sprite) {
		if (sprite != null) {
			toRemove.add(sprite);
		}
	}

	public static void resolveCollisions() {
	    for (int i = 0; i < physicsSprites.size(); i++) {
	        PhysicsSprite sprite = physicsSprites.get(i);
	        for (int j = 0; j < physicsSprites.size(); j++) {
	            PhysicsSprite sprite2 = physicsSprites.get(j);
	            if (sprite.getCollisionBox() != null && sprite2.getCollisionBox() != null && sprite.getCollisionBox().doCollideWith(sprite2.getCollisionBox())) {
	                sprite.enteredCollision(sprite2);
	                sprite2.enteredCollision(sprite);
	            }
	        }
	    }
	}

	public static ArrayList<PhysicsSprite> getPhysicsSprites() {
		return physicsSprites;
	}

}
