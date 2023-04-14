package engine.sprites.structures.command;

import java.io.File;

import engine.physics.CollisionLayers;
import engine.rendering.textures.Texture;
import engine.sprites.structures.StructureSprite;
import math.Vector2;

public class CoreModule extends StructureSprite {
	private static final long serialVersionUID = 5792682450616901356L;

	private CoreModule(Texture texture, Vector2 location, double zDepth) {
		super(texture, location, zDepth);
		this.collisionLayer = CollisionLayers.STRUCTURE;
	}

	public static CoreModule instantiateCoreModule(File file, Vector2 location) {
		try {
			Texture texture = Texture.createTexture(file);
			return new CoreModule(texture, location, 0.7f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static CoreModule instantiateCoreModule(Texture texture, Vector2 location) {
		try {
			return new CoreModule(texture, location, 0.7f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}

	@Override
	public String ID() {
		return "coreModule";
	}

	
	
}
