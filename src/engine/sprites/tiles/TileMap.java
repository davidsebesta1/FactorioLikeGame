package engine.sprites.tiles;

import java.io.File;
import java.io.Serializable;

import engine.rendering.textures.Texture;
import engine.sprites.Sprite;
import math.Vector2;

/**
 * Tile map holds a 2d array of TileSprite, it is used to manipulate with game world.
 * @author David Šebesta
 * @see TileSprite
 *
 */
public class TileMap implements Serializable {
	private static final long serialVersionUID = 459971329294318553L;
	private TileSprite[][] map;
	private Vector2 size;

	private static final double SPRITE_SIZE = 32d;

	/**
	 * Class constructor
	 * @param width
	 * @param height
	 */
	public TileMap(int width, int height) {
		this.map = new TileSprite[width][height];
		this.size = new Vector2(width, height);
	}

	/**
	 * Returns a sprite at specified coordinates
	 * @param x
	 * @param y
	 * @return Null if coordinates are out of bounds, otherwise a Sprite
	 */
	public Sprite getAtLocation(int x, int y) {
		if (!isInBounds(x, y))
			return null;

		return map[x][y];
	}

	/**
	 * Attempts to create a tilesprite with specified file texture
	 * @param x
	 * @param y
	 * @param file
	 * @return If the tilesprite has been sucessfully created
	 */
	public boolean tryCreateAtLocation(int x, int y, File file) {
		if (!isInBounds(x, y))
			return false;

		if (map[x][y] == null) {
			map[x][y] = TileSprite.instantiateTileSprite(file, new Vector2(x * SPRITE_SIZE, y * SPRITE_SIZE));
			return true;
		}

		return false;
	}

	/**
	 * Attempts to create a tilesprite with specified texture
	 * @param x
	 * @param y
	 * @param texture
	 * @return If the tilesprite has been sucessfully created
	 */
	public boolean tryCreateAtLocation(int x, int y, Texture texture) {
		if (!isInBounds(x, y))
			return false;

		if (map[x][y] == null) {
			map[x][y] = TileSprite.instantiateTileSprite(texture, new Vector2(x * SPRITE_SIZE, y * SPRITE_SIZE));
			return true;
		}

		return false;
	}

	/**
	 * Attempts to place existing tilesprite object at specified coordinates
	 * @param x
	 * @param y
	 * @param sprite
	 * @return If the tilesprite has been sucessfully added
	 */
	public boolean tryPlaceAtLocation(int x, int y, TileSprite sprite) {
		if (!isInBounds(x, y))
			return false;

		if (map[x][y] != null) {
			map[x][y] = sprite;
			return true;
		}
		return false;
	}

	/**
	 * Force place a sprite at specified coordinates, may throw a exception.
	 * @param x
	 * @param y
	 * @param sprite
	 */
	public void forcePlaceAtLocation(int x, int y, TileSprite sprite) {
		map[x][y] = sprite;
	}

	/**
	 * Attempts to remove a sprite at specified coordinates
	 * @param x
	 * @param y
	 * @return True if successful, false otherwise
	 */
	public boolean tryToRemove(int x, int y) {
		if (!isInBounds(x, y))
			return false;

		if (map[x][y] != null) {
			map[x][y] = null;
			return true;
		}
		return false;
	}

	/**
	 * Attempts to remove a sprite in parameters
	 * @param sprite
	 * @return True if successful, false otherwise
	 */
	public boolean tryToRemove(TileSprite sprite) {
		return tryToRemove((int) (sprite.getLocation().getX() / SPRITE_SIZE), (int) (sprite.getLocation().getY()
				/ SPRITE_SIZE));
	}

	public Vector2 getSize() {
		return size;
	}

	public TileSprite[][] getMap() {
		return map;
	}

	/**
	 * Checks if the specified coordinates are within bounds
	 * @param x
	 * @param y
	 * @return True if they are within bounds, otherwise false
	 */
	public boolean isInBounds(int x, int y) {
		return x >= 0 && y >= 0 && x < map.length && y < map[x].length;
	}
}
