package engine.physics;

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
