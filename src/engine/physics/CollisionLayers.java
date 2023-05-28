package engine.physics;

/**
 * Enum class providing basic CollisionLayers, if the other Enum is within constructor of the other layer, they do collide and will activate onCollision methods.
 * @author David Šebest
 */
public enum CollisionLayers {
	MAPEDGE(),
	STRUCTURE(MAPEDGE),
	ITEM(MAPEDGE, STRUCTURE), 
	PLAYER(MAPEDGE);

	private final CollisionLayers[] collide;

	private CollisionLayers(CollisionLayers... cols) {
		this.collide = cols;
	}

	public CollisionLayers[] getCollide() {
		return collide;
	}
}
