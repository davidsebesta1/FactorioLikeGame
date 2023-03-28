package engine.world;

import java.io.File;

import engine.sprites.Sprite;
import engine.sprites.entities.Player;
import engine.sprites.tiles.TileMap;
import engine.sprites.tiles.TileSprite;
import math.Vector2;

public class GameWorld {
	private Vector2 size;
	private TileMap tiles;

	private Sprite background;

	private Player player;

	public GameWorld(Vector2 size) {
		this.size = size;

		tiles = new TileMap((int) size.getX(), (int) size.getY());

		player = Player.instantiatePlayer(null, new Vector2(0, 0));

		background = Sprite.instantiateSprite(new File("background.jpg"), new Vector2(0, 0), 0f);

		tiles.tryCreateAtLocation(5, 5, new File("rockground.png"));
	}

	public Sprite getBackground() {
		return background;
	}

	public void setBackground(Sprite background) {
		this.background = background;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Vector2 getSize() {
		return size;
	}

	public TileMap getTiles() {
		return tiles;
	}
}
