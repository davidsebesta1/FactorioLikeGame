package engine.sprites.structures;

import java.io.File;

import engine.rendering.textures.Texture;
import math.Vector2;

public class CoreModule extends StructureSprite {
	private static final long serialVersionUID = 5792682450616901356L;

	private CoreModule(Texture texture, Vector2 location, float zDepth) {
		super(texture, location, zDepth);
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
}
