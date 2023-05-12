package engine.world;

import java.awt.image.BufferedImage;

import engine.rendering.optimalization.ChunkManager;
import engine.rendering.textures.TextureLibrary;
import engine.sprites.Background;
import engine.sprites.entities.player.Player;
import engine.sprites.ores.OreMap;
import engine.sprites.structures.StructureMap;
import engine.sprites.tiles.TileMap;
import math.Vector2;

public class World {
	protected Vector2 size;
	protected TileMap tiles;

	protected StructureMap structureMap;
	protected OreMap oreMap;

	protected Background background;

	protected Player player;

	protected ChunkManager chunkManager;

	protected World(Vector2 size) {
		this.size = size;
		this.tiles = new TileMap((int) (size.getX() / 32), (int) (size.getY() / 32));
		this.structureMap = new StructureMap(size.div(32));

		this.player = Player.instantiatePlayer(TextureLibrary.retrieveTexture("rockground"));

		this.background = Background.instantiateBackground(TextureLibrary.retrieveTexture("background"), new Vector2(0, 0), 0f);

		this.chunkManager = new ChunkManager(size, player);
		this.oreMap = new OreMap(size.div(32));
		
		this.player.tryAddItemToInventory("titaniumItem", 5);
	}

	public boolean isInBounds(int x, int y, BufferedImage noiseImage) {
		return x >= 0 && y >= 0 && x < noiseImage.getWidth() && y < noiseImage.getHeight();
	}

	public Background getBackground() {
		return background;
	}

	public void setBackground(Background background) {
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

	public TileMap getTileMap() {
		return tiles;
	}

	public StructureMap getStructureMap() {
		return structureMap;
	}

	public ChunkManager getChunkManager() {
		return chunkManager;
	}

	public OreMap getOreMap() {
		return oreMap;
	}
}
