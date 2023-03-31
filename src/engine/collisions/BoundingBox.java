package engine.collisions;

import java.awt.Rectangle;
import java.util.Objects;

import engine.sprites.Sprite;
import math.Vector2;

public class BoundingBox extends Rectangle {
	private static final long serialVersionUID = 7056174845995895247L;

	private Sprite owningSprite;
	private boolean isStatic;

	public BoundingBox(Sprite owningSprite, boolean isStatic, Vector2 location, Vector2 size) {
		super((int) location.getX(), (int) location.getY(), (int) size.getX(), (int) size.getY());
		this.owningSprite = owningSprite;
		this.isStatic = isStatic;
	}

	public Sprite getOwningSprite() {
		return owningSprite;
	}

	public void setOwningSprite(Sprite owningSprite) {
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
		result = prime * result + Objects.hash(owningSprite);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BoundingBox other = (BoundingBox) obj;
		return Objects.equals(owningSprite, other.owningSprite);
	}
}
