package engine.collisions;

import java.util.ArrayList;

public class CollisionManager {
	private static ArrayList<BoundingBox> boxCollisions = new ArrayList<>();

	private CollisionManager() {
	}

	public static void addBoundingBox(BoundingBox boundingBox) {
		if (boundingBox != null)
			boxCollisions.add(boundingBox);
	}

	public static void removeBoundingBox(BoundingBox boundingBox) {
		if (boundingBox != null)
			boxCollisions.remove(boundingBox);
	}
	
	public static void resolveCollisions() {
		for (BoundingBox boundingBox : boxCollisions) {
			for (BoundingBox boundingBox2 : boxCollisions) {
				if(!boundingBox.equals(boundingBox2)) {
					
				}
			}
		}
	}
	
	
}
