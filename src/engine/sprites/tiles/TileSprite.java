package engine.sprites.tiles;

import java.io.File;

import engine.Game;
import engine.rendering.textures.Texture;
import engine.sprites.Sprite;
import engine.sprites.SpriteManager;
import math.Vector2;

public class TileSprite extends Sprite {
	private static final long serialVersionUID = -1961159418183745510L;

	private TileSprite(Texture texture, Vector2 location) {
		super(texture, location, 0.1f);
	}

	public static TileSprite instantiateTileSprite(File file, Vector2 location) {
		try {
			Texture texture = Texture.createTexture(file);
			return new TileSprite(texture, location);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public static TileSprite instantiateTileSprite(Texture texture, Vector2 location) {
		try {
			return new TileSprite(texture, location);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	@Override
	public void onMouseClicked() {
		this.destroy();
	}

	@Override
	public String toString() {
		return "TileSprite [location=" + location + ", size=" + size + ", getzDepth()=" + getzDepth()
				+ ", getTexture()=" + getTexture() + ", getLocation()=" + getLocation() + ", getSize()=" + getSize()
				+ ", isVisible()=" + isVisible() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ ", getClass()=" + getClass() + "]";
	}
	
	@Override
	public void destroy() {
		try {
			SpriteManager.remove(this);
			Game.getInstance().getCurrentWorld().getTiles().tryToRemove(this);

		} catch (Exception e) {
			e.printStackTrace();
		}
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
	
	
	
	

}
