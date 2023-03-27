package engine.sprites.tiles;

import java.io.Serializable;

import engine.sprites.Sprite;

public class TileMap implements Serializable {
	private static final long serialVersionUID = 459971329294318553L;
	private Sprite[][] map;
	
	public TileMap(int width, int height) {
		map = new Sprite[width][height];
	}
	
	public Sprite getAtLocation(int x, int y) {
		return map[x][y];
	}
	
	public boolean tryPlaceAtLocation(int x, int y, Sprite sprite) {
		if(map[x][y] != null) {
			map[x][y] = sprite;
			return true;
		}
		return false;
	}
	
	public void forcePlaceAtLocation(int x, int y, Sprite sprite) {
		map[x][y] = sprite;
	}
	
	public boolean tryToRemove(int x, int y) {
		if(map[x][y] != null) {
			map[x][y] = null;
			return true;
		}
		return false;
	}
	
	public boolean tryToRemove(Sprite sprite) {
		return tryToRemove((int) sprite.getLocation().getX(),(int) sprite.getLocation().getY());
	}
}
