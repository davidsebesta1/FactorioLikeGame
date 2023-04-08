package engine.sprites.tiles;

import java.io.File;
import java.io.Serializable;

import engine.rendering.textures.Texture;
import engine.sprites.Sprite;
import math.Vector2;

public class TileMap implements Serializable {
	private static final long serialVersionUID = 459971329294318553L;
	private TileSprite[][] map;
	private Vector2 size;

	private static final float SPRITE_SIZE = 32f;

	public TileMap(int width, int height) {
		this.map = new TileSprite[width][height];
		this.size = new Vector2(width, height);
	}

	public Sprite getAtLocation(int x, int y) {
		return map[x][y];
	}

	public boolean tryCreateAtLocation(int x, int y, File file) {
		if (map[x][y] == null) {
			map[x][y] = TileSprite.instantiateTileSprite(file, new Vector2(x * SPRITE_SIZE, y * SPRITE_SIZE));
			return true;
		}

		return false;
	}
	
	public boolean tryCreateAtLocation(int x, int y, Texture texture) {
		if (map[x][y] == null) {
			map[x][y] = TileSprite.instantiateTileSprite(texture, new Vector2(x * SPRITE_SIZE, y * SPRITE_SIZE));
			return true;
		}

		return false;
	}

	public boolean tryPlaceAtLocation(int x, int y, TileSprite sprite) {
		if (map[x][y] != null) {
			map[x][y] = sprite;
			return true;
		}
		return false;
	}

	public void forcePlaceAtLocation(int x, int y, TileSprite sprite) {
		map[x][y] = sprite;
	}

	public boolean tryToRemove(int x, int y) {
		if (map[x][y] != null) {
			map[x][y] = null;
			return true;
		}
		return false;
	}

	public boolean tryToRemove(TileSprite sprite) {
		return tryToRemove((int) (sprite.getLocation().getX() / SPRITE_SIZE),
				(int) (sprite.getLocation().getY() / SPRITE_SIZE));
	}

	public Vector2 getSize() {
		return size;
	}

	public TileSprite[][] getMap() {
		return map;
	}
}
