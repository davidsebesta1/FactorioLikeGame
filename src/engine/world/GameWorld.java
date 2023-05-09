package engine.world;

import java.awt.image.BufferedImage;
import java.util.Random;

import engine.rendering.optimalization.ChunkManager;
import engine.rendering.textures.TextureLibrary;
import engine.sprites.Background;
import engine.sprites.entities.player.Player;
import engine.sprites.ores.OreMap;
import engine.sprites.ores.oresprites.CopperOre;
import engine.sprites.ores.oresprites.TitaniumOre;
import engine.sprites.structures.StructureMap;
import engine.sprites.tiles.TileMap;
import math.Vector2;
import notmycode.PerlinNoise;

public class GameWorld {
	private Vector2 size;
	private TileMap tiles;

	private StructureMap structureMap;
	private OreMap oreMap;

	private Background background;

	private Player player;

	private ChunkManager chunkManager;
	
	private PerlinNoise perlinNoise = new PerlinNoise();
	private PerlinNoise perlinNoiseTitanium= new PerlinNoise(15);
	private PerlinNoise perlinNoiseCopper = new PerlinNoise(21);
	
	private static final double MIN_VALUE_TERRAIN = 0.5;
	private static final double MIN_VALUE_ORE = 0.45;

	public GameWorld(Vector2 size) {
		this.size = size;
		this.tiles = new TileMap((int) (size.getX() / 32), (int) (size.getY() / 32));
		this.structureMap = new StructureMap(size.div(32));

		this.player = Player.instantiatePlayer(TextureLibrary.retrieveTexture("rockground"));

		this.background = Background.instantiateBackground(TextureLibrary.retrieveTexture("background"), new Vector2(0, 0), 0f);

		this.chunkManager = new ChunkManager(size, player);
		this.oreMap = new OreMap(size.div(32));

		Random random = new Random(7584);
		Random random2 = new Random(5144);
		
		//Create noise
		BufferedImage noiseImage = new BufferedImage((int) tiles.getSize().getX(), (int) tiles.getSize().getY(), BufferedImage.TYPE_INT_RGB);
//		for (int y = 0; y < noiseImage.getWidth(); y++) {
//			for (int x = 0; x < noiseImage.getHeight(); x++) {
//				double value = OpenSimplexNoise.smoothNoise(x, y, noise, noiseSize) + OpenSimplexNoise.smoothNoise(x, y, noiseLow, noiseSizeLow) + OpenSimplexNoise.smoothNoise(x, y, noiseLow, 10);
//				int rgb = 0x010101 * (int) ((value + 1) * 127.5);
//				noiseImage.setRGB(x, y, rgb);
//			}
//		}
		
		for (int y = 0; y < noiseImage.getWidth(); y++) {
			for (int x = 0; x < noiseImage.getHeight(); x++) {
				double value = perlinNoise.noise(x, y, 0, 35);
				int rgb = 0x010101 * (int) ((value + 1) * 127.5);
				noiseImage.setRGB(x, y, rgb);
			}
		}
		
		//Fix single gaps in terrain
		fixGaps(noiseImage);
		
		//Create tile sprites based on noise
		for (int y = 0; y < noiseImage.getWidth(); y++) {
			for (int x = 0; x < noiseImage.getHeight(); x++) {
				int rgb = noiseImage.getRGB(x, y);
				double val = ((rgb >> 16) & 0xFF) / 255.0;

				if (val > MIN_VALUE_TERRAIN) {
					int rndIndex = random.nextInt(3) + 1;

					int rndVariation = 0;
					if (random.nextInt() >= 9)
						rndVariation = random2.nextInt(4);

					this.tiles.tryCreateAtLocation(x, y, TextureLibrary.retrieveTexture("asteroidGround" + rndIndex + "" + rndVariation));
					
					if (perlinNoiseTitanium.noise(x, y, 0, 20) > MIN_VALUE_ORE && this.tiles.getAtLocation(x, y) != null) {
						
						switch(random.nextInt(4)) {
							case 0:
								this.oreMap.tryAddOreAtLocation(TitaniumOre.instantiateTitaniumOre(TextureLibrary.retrieveTexture("titaniumOre0"), Vector2.oreSpawn, 50), new Vector2(x,y));
								break;
							
							case 1:
								this.oreMap.tryAddOreAtLocation(TitaniumOre.instantiateTitaniumOre(TextureLibrary.retrieveTexture("titaniumOre1"), Vector2.oreSpawn, 50), new Vector2(x,y));
								break;
								
							case 2:
								this.oreMap.tryAddOreAtLocation(TitaniumOre.instantiateTitaniumOre(TextureLibrary.retrieveTexture("titaniumOre2"), Vector2.oreSpawn, 50), new Vector2(x,y));
								break;
								
							case 3:
								this.oreMap.tryAddOreAtLocation(TitaniumOre.instantiateTitaniumOre(TextureLibrary.retrieveTexture("titaniumOre3"), Vector2.oreSpawn, 50), new Vector2(x,y));
								break;
						}
					}
					
					if (perlinNoiseCopper.noise(x, y, 0, 20) > MIN_VALUE_ORE && this.tiles.getAtLocation(x, y) != null) {
						this.oreMap.tryAddOreAtLocation(CopperOre.instantiateCopperOre(TextureLibrary.retrieveTexture("copperOre"), Vector2.oreSpawn, 50), new Vector2(x,y));
					}
				}
			}
		}
	}
	
	public void fixGaps(BufferedImage noiseImage) {
		for (int y = 0; y < noiseImage.getWidth(); y++) {
			for (int x = 0; x < noiseImage.getHeight(); x++) {
				double valLeft = 0;
				double valRight = 0;
				double valUp = 0;
				double valDown = 0;
				
				if(isInBounds(x - 1, y, noiseImage)) {
					int rgb = noiseImage.getRGB(x - 1, y);
					double val = ((rgb >> 16) & 0xFF) / 255.0;
					valLeft = val;
				}
				
				if(isInBounds(x + 1, y, noiseImage)) {
					int rgb = noiseImage.getRGB(x + 1, y);
					double val = ((rgb >> 16) & 0xFF) / 255.0;
					valRight = val;
				}
				
				if(isInBounds(x, y - 1, noiseImage)) {
					int rgb = noiseImage.getRGB(x, y - 1);
					double val = ((rgb >> 16) & 0xFF) / 255.0;
					valUp = val;
				}
				
				if(isInBounds(x, y + 1, noiseImage)) {
					int rgb = noiseImage.getRGB(x, y + 1);
					double val = ((rgb >> 16) & 0xFF) / 255.0;
					valDown = val;
				}
				
				if(valLeft <= MIN_VALUE_TERRAIN && valRight <= MIN_VALUE_TERRAIN && valUp <= MIN_VALUE_TERRAIN && valDown <= MIN_VALUE_TERRAIN) {
					noiseImage.setRGB(x, y, 0);
				} else if(valLeft >= MIN_VALUE_TERRAIN && valRight >= MIN_VALUE_TERRAIN && valUp >= MIN_VALUE_TERRAIN && valDown >= MIN_VALUE_TERRAIN) {
					noiseImage.setRGB(x, y, noiseImage.getRGB(x - 1, y));
				}
				
				
			}
		}
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
