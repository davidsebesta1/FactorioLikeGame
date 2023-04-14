package engine.world;

import java.util.Random;

import engine.rendering.optimalization.ChunkManager;
import engine.rendering.textures.TextureLibrary;
import engine.sprites.Background;
import engine.sprites.Sprite;
import engine.sprites.SpriteManager;
import engine.sprites.entities.player.Player;
import engine.sprites.objects.minable.Coal;
import engine.sprites.ores.CoalOre;
import engine.sprites.ores.OreMap;
import engine.sprites.structures.StructureMap;
import engine.sprites.structures.command.CoreModule;
import engine.sprites.structures.conveyors.ConveyorBelt;
import engine.sprites.structures.conveyors.ConveyorBeltDirection;
import engine.sprites.structures.drills.BasicDrill;
import engine.sprites.tiles.TileMap;
import math.Vector2;

public class GameWorld {
	private Vector2 size;
	private TileMap tiles;
	
	private StructureMap structureMap;
	private OreMap oreMap;

	private Background background;

	private Player player;
	
	private ChunkManager chunkManager;

	public GameWorld(Vector2 size) {
		this.size = size;
		this.tiles = new TileMap((int) (size.getX() / 32), (int) (size.getY() / 32));
		this.structureMap = new StructureMap(size.div(32));
		this.oreMap = new OreMap(size.div(32));

		this.player = Player.instantiatePlayer(TextureLibrary.retrieveTexture("rockground"));

		this.background = Background.instantiateBackground(TextureLibrary.retrieveTexture("background"), new Vector2(0, 0), 0f);
		
		this.chunkManager = new ChunkManager(256, size, player);

		
		Random random = new Random(7584);
		Random random2 = new Random(5144);
		
		for(int i = 0; i < tiles.getSize().getX(); i++) {
			for(int j = 0; j < tiles.getSize().getY(); j++) {
				int rndIndex = random.nextInt(3) + 1;
				
				int rndVariation = 0;
				if(random.nextInt() >= 9) rndVariation = random2.nextInt(4);
				
				this.tiles.tryCreateAtLocation(i, j, TextureLibrary.retrieveTexture("asteroidGround" + rndIndex + "" + rndVariation));
				
			}
		}
		
		
		structureMap.tryAddStructureAtLocation(CoreModule.instantiateCoreModule(TextureLibrary.retrieveTexture("coreModule"), new Vector2(0, 0)), new Vector2(2, 2));
		
		ConveyorBelt belt = ConveyorBelt.instantiateConveyorBelt(TextureLibrary.retrieveTexture("beltRIGHT"), new Vector2(0, 0), ConveyorBeltDirection.RIGHT);
		ConveyorBelt belt1 = ConveyorBelt.instantiateConveyorBelt(TextureLibrary.retrieveTexture("beltRIGHT"), new Vector2(0, 0), ConveyorBeltDirection.RIGHT);
		ConveyorBelt belt2 = ConveyorBelt.instantiateConveyorBelt(TextureLibrary.retrieveTexture("beltUP"), new Vector2(0, 0), ConveyorBeltDirection.UP);
		ConveyorBelt belt3 = ConveyorBelt.instantiateConveyorBelt(TextureLibrary.retrieveTexture("beltUP"), new Vector2(0, 0), ConveyorBeltDirection.UP);
		
		structureMap.tryAddStructureAtLocation(belt,new Vector2(4, 7));
		structureMap.tryAddStructureAtLocation(belt1,new Vector2(5, 7));
		structureMap.tryAddStructureAtLocation(belt2,new Vector2(6, 7));
		structureMap.tryAddStructureAtLocation(belt3,new Vector2(6, 6));
		
		BasicDrill drill1 = BasicDrill.instantiateBasicDrill(TextureLibrary.retrieveTexture("basicDrill"), new Vector2(0,0));
		structureMap.tryAddStructureAtLocation(drill1, new Vector2(7, 9));
		
		oreMap.tryAddOreAtLocation(CoalOre.instantiateSprite(TextureLibrary.retrieveTexture("coalOre"), new Vector2(0, 0), 5), new Vector2(4,8));
	
		
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

	public TileMap getTiles() {
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
