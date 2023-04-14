package engine.physics;

import java.awt.Rectangle;
import java.util.Objects;

import engine.sprites.PhysicsSprite;
import engine.sprites.Sprite;
import math.Vector2;

public class BoundingBox extends Rectangle {
	private static final long serialVersionUID = 7056174845995895247L;

	private PhysicsSprite owningSprite;
	private boolean isStatic;

	public BoundingBox(PhysicsSprite owningSprite, boolean isStatic, Vector2 location, Vector2 size) {
		super((int) location.getX(), (int) location.getY(), (int) size.getX(), (int) size.getY());
		this.owningSprite = owningSprite;
		this.isStatic = isStatic;
	}

	public boolean doCollideWith(BoundingBox box) {
		if (owningSprite.checkCollisionLayer(box.owningSprite) && this.intersects(box)) {
			return true;
		}
		return false;
	}

	public void setLocation(Vector2 location) {
		this.x = (int) location.getX();
		this.y = (int) location.getY();
	}

	public Vector2 getLocationAsVector() {
		return new Vector2(this.x, this.y);
	}

	public Sprite getOwningSprite() {
		return owningSprite;
	}

	public void setOwningSprite(PhysicsSprite owningSprite) {
		this.owningSprite = owningSprite;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(isStatic);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof BoundingBox)) {
			return false;
		}
		BoundingBox other = (BoundingBox) obj;
		return isStatic == other.isStatic;
	}
}
