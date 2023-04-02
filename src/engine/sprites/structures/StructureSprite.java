package engine.sprites.structures;

import java.io.File;
import java.util.Objects;

import engine.physics.BoundingBox;
import engine.rendering.textures.Texture;
import engine.sprites.PhysicsSprite;
import math.Vector2;

public class StructureSprite extends PhysicsSprite {
	private static final long serialVersionUID = 5574047065458702506L;

	protected Vector2 tileSizeUnits;

	protected StructureSprite(Texture texture, Vector2 location, float zDepth) {
		super(texture, location, zDepth);
		this.tileSizeUnits = new Vector2(texture.getImage().getWidth() / 32f, texture.getImage().getHeight() / 32f);
		
		this.setCollisionBox(new BoundingBox(this, true, location, this.getSize()));
	}
	
	public static StructureSprite instantiateStructure(File file, Vector2 location) {
		try {
			Texture texture = Texture.createTexture(file);
			return new StructureSprite(texture, location, 0.7f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public Vector2 getTileSizeUnits() {
		return tileSizeUnits;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(tileSizeUnits);
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
		StructureSprite other = (StructureSprite) obj;
		return Objects.equals(tileSizeUnits, other.tileSizeUnits);
	}
	
	@Override
	public void enteredCollision(PhysicsSprite sprite) {
		//TODO stuff
	}
}
