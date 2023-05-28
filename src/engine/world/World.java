package engine.world;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

import engine.rendering.optimalization.ChunkManager;
import engine.rendering.textures.TextureLibrary;
import engine.sprites.Background;
import engine.sprites.entities.player.Player;
import engine.sprites.ores.OreMap;
import engine.sprites.structures.StructureMap;
import engine.sprites.tiles.TileMap;
import main.Log;
import math.Vector2;

public class World implements Serializable {
	private static final long serialVersionUID = 451312218191408195L;

	protected Vector2 size;
	protected TileMap tiles;

	protected StructureMap structureMap;
	protected OreMap oreMap;

	protected Background background;

	protected Player player;

	protected transient ChunkManager chunkManager;

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

	@Override
	public int hashCode() {
		return Objects.hash(player, size);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof World)) {
			return false;
		}
		World other = (World) obj;
		return Objects.equals(player, other.player) && Objects.equals(size, other.size);
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

	public void SaveWorld() {
		try {
			FileOutputStream fileOut = new FileOutputStream("/saves/");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
			System.out.println("World Saved");
			Log.info("World saved");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}
}
