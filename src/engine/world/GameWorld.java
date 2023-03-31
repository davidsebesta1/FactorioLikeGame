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
		this.tiles = new TileMap(10, 10);

		this.player = Player.instantiatePlayer(new File("rockground.png"));

		this.background = Sprite.instantiateSprite(new File("background.jpg"), new Vector2(0, 0), 0f);

		
		for(int i = 0; i < tiles.getSize().getX(); i++) {
			for(int j = 0; j < tiles.getSize().getY(); j++) {
				this.tiles.tryCreateAtLocation(i, j, new File("ground1.png"));
			}
		}
		
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
