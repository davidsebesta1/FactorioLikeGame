package engine.physics;

import java.util.ArrayList;

import engine.sprites.PhysicsSprite;

/**
 * Physics manager is a class providing management of physics sprite, each PhysicsSprite is registering inside PhysicsManager and added next frame. Only one instance is created per game.
 * @author David Šebesta
 */
public class PhysicsManager {
	private static ArrayList<PhysicsSprite> physicsSprites = new ArrayList<>();
	
	private static ArrayList<PhysicsSprite> toAdd = new ArrayList<>();
	private static ArrayList<PhysicsSprite> toRemove = new ArrayList<>();

	/**
	 * Class consturctor
	 */
	private PhysicsManager() {
	}
	
	/**
	 * Method for synchronizing sprites in order to prevent ConcurrentModificationException
	 * @see ConcurrentModificationException
	 */
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
	
	/**
	 * Clear all physics sprites
	 */
	public static synchronized void clearAll() {
		physicsSprites.clear();
	}

	/**
	 * Adds a physics sprite to update queue
	 * @param sprite
	 */
	public static void addPhysicsSprite(PhysicsSprite sprite) {
		if (sprite != null) {
			toAdd.add(sprite);
		}
	}

	/**
	 * Adds physics sprite to remove queue
	 * @param sprite
	 */
	public static void removePhysicsSprite(PhysicsSprite sprite) {
		if (sprite != null) {
			toRemove.add(sprite);
		}
	}
	
	/**
	 * Resolves collision of all physics sprite that do have collision box
	 */
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

	/**
	 * Returns all physics sprites
	 * @return all physics sprites
	 */
	public static ArrayList<PhysicsSprite> getPhysicsSprites() {
		return physicsSprites;
	}

}
