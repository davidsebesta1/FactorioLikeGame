package engine.world;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Objects;
import java.util.Random;

import engine.rendering.textures.TextureLibrary;
import engine.sprites.ores.oresprites.AluminiumOre;
import engine.sprites.ores.oresprites.CPPowderOre;
import engine.sprites.ores.oresprites.CopperOre;
import engine.sprites.ores.oresprites.GoldOre;
import engine.sprites.ores.oresprites.LithiumOre;
import engine.sprites.ores.oresprites.NickelOre;
import engine.sprites.ores.oresprites.PlatiniumOre;
import engine.sprites.ores.oresprites.TitaniumOre;
import main.Log;
import math.Vector2;
import notmycode.PerlinNoise;


/**
 * A actual game world with terrain, player and such.
 * @author David
 *
 */
public class GameWorld extends World {
	private static final long serialVersionUID = -8887930684910426859L;
	
	private PerlinNoise perlinNoise = new PerlinNoise();
	private PerlinNoise perlinNoiseTitanium = new PerlinNoise(15);
	private PerlinNoise perlinNoiseCopper = new PerlinNoise(21);
	private PerlinNoise perlinNoiseAluminium = new PerlinNoise(23);
	private PerlinNoise perlinNoiseGold = new PerlinNoise(25);
	private PerlinNoise perlinNoiseLithium = new PerlinNoise(27);
	private PerlinNoise perlinNoiseNickel = new PerlinNoise(28);
	private PerlinNoise perlinNoisePlatinium = new PerlinNoise(31);
	private PerlinNoise perlinNoiseCP = new PerlinNoise(35);

	private static final double MIN_VALUE_TERRAIN = 0.5;

	private static final double MIN_VALUE_TITANIUM = 0.50;
	private static final double MIN_VALUE_COPPER = 0.55;
	private static final double MIN_VALUE_ALUMINIUM = 0.52;
	private static final double MIN_VALUE_GOLD = 0.63;
	private static final double MIN_VALUE_LITHIUM = 0.64;
	private static final double MIN_VALUE_NICKEL = 0.60;
	private static final double MIN_VALUE_PLATINIUM = 0.88;
	private static final double MIN_VALUE_CP = 0.55;
	
	private File saveFile;


	/**
	 * Class constructor
	 * @param size
	 */
	public GameWorld(Vector2 size) {
		super(size);
		this.saveFile = new File("saves/world1");

		generateTerrain();

		this.player.setShowMainMenu(false);
	}

	/**
	 * Generates a terrain and ores via perlinNoise, each tile has its own randomized variant (rotated) to prevent tiling.
	 * @see PerlinNoise
	 */
	private void generateTerrain() {
		Random random = new Random(7584);
		Random random2 = new Random(5144);

		//Create noise
		BufferedImage noiseImage = new BufferedImage((int) tiles.getSize().getX(), (int) tiles.getSize().getY(), BufferedImage.TYPE_INT_RGB);

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

					this.tiles.tryCreateAtLocation(x, y, TextureLibrary.retrieveTexture("asteroidGround" + rndIndex + ""
							+ rndVariation));

					if (perlinNoiseTitanium.noise(x, y, 0, 20) > MIN_VALUE_TITANIUM
							&& this.tiles.getAtLocation(x, y) != null) {
						this.oreMap.tryAddOreAtLocation(TitaniumOre.instantiateTitaniumOre(TextureLibrary.retrieveTexture("titaniumOre"
								+ random.nextInt(4)), Vector2.oreSpawn, 50 + random.nextInt(150)), new Vector2(x, y));
					}

					if (perlinNoiseCopper.noise(x, y, 0, 20) > MIN_VALUE_COPPER
							&& this.tiles.getAtLocation(x, y) != null) {
						this.oreMap.tryAddOreAtLocation(CopperOre.instantiateCopperOre(TextureLibrary.retrieveTexture("copperOre"
								+ random.nextInt(4)), Vector2.oreSpawn, 50 + random.nextInt(80)), new Vector2(x, y));
					}

					if (perlinNoiseAluminium.noise(x, y, 0, 21) > MIN_VALUE_ALUMINIUM
							&& this.tiles.getAtLocation(x, y) != null) {
						this.oreMap.tryAddOreAtLocation(AluminiumOre.instantiateAluminiumOre(TextureLibrary.retrieveTexture("aluminiumOre"
								+ random.nextInt(4)), Vector2.oreSpawn, 50 + random.nextInt(120)), new Vector2(x, y));
					}
					
					if (perlinNoiseGold.noise(x, y, 0, 25) > MIN_VALUE_GOLD
							&& this.tiles.getAtLocation(x, y) != null) {
						this.oreMap.tryAddOreAtLocation(GoldOre.instantiateGoldOre(TextureLibrary.retrieveTexture("goldOre"
								+ random.nextInt(4)), Vector2.oreSpawn, 50 + random.nextInt(5)), new Vector2(x, y));
					}
					
					if (perlinNoiseLithium.noise(x, y, 0, 28) > MIN_VALUE_LITHIUM
							&& this.tiles.getAtLocation(x, y) != null) {
						this.oreMap.tryAddOreAtLocation(LithiumOre.instantiateLithiumOre(TextureLibrary.retrieveTexture("lithiumOre"
								+ random.nextInt(4)), Vector2.oreSpawn, 50 + random.nextInt(5)), new Vector2(x, y));
					}
					
					if (perlinNoiseNickel.noise(x, y, 0, 30) > MIN_VALUE_NICKEL
							&& this.tiles.getAtLocation(x, y) != null) {
						this.oreMap.tryAddOreAtLocation(NickelOre.instantiateNickelOre(TextureLibrary.retrieveTexture("nickelOre"
								+ random.nextInt(4)), Vector2.oreSpawn, 50 + random.nextInt(20)), new Vector2(x, y));
					}
					
					if (perlinNoisePlatinium.noise(x, y, 0, 45) > MIN_VALUE_PLATINIUM
							&& this.tiles.getAtLocation(x, y) != null) {
						this.oreMap.tryAddOreAtLocation(PlatiniumOre.instantiatePlatiniumOre(TextureLibrary.retrieveTexture("platiniumOre"
								+ random.nextInt(4)), Vector2.oreSpawn, 50 + random.nextInt(10)), new Vector2(x, y));
					}
					
					if (perlinNoiseCP.noise(x, y, 0, 15) > MIN_VALUE_CP
							&& this.tiles.getAtLocation(x, y) != null) {
						this.oreMap.tryAddOreAtLocation(CPPowderOre.instantiateCPPowderOre(TextureLibrary.retrieveTexture("CPOre"
								+ random.nextInt(4)), Vector2.oreSpawn, 50 + random.nextInt(100)), new Vector2(x, y));
					}
				}
			}
		}
	}

	/**
	 * Fixes single gaps that are present in world generation so the world is smoother
	 * @param noiseImage
	 */
	public void fixGaps(BufferedImage noiseImage) {
		for (int y = 0; y < noiseImage.getWidth(); y++) {
			for (int x = 0; x < noiseImage.getHeight(); x++) {
				double valLeft = 0;
				double valRight = 0;
				double valUp = 0;
				double valDown = 0;

				if (isInBounds(x - 1, y, noiseImage)) {
					int rgb = noiseImage.getRGB(x - 1, y);
					double val = ((rgb >> 16) & 0xFF) / 255.0;
					valLeft = val;
				}

				if (isInBounds(x + 1, y, noiseImage)) {
					int rgb = noiseImage.getRGB(x + 1, y);
					double val = ((rgb >> 16) & 0xFF) / 255.0;
					valRight = val;
				}

				if (isInBounds(x, y - 1, noiseImage)) {
					int rgb = noiseImage.getRGB(x, y - 1);
					double val = ((rgb >> 16) & 0xFF) / 255.0;
					valUp = val;
				}

				if (isInBounds(x, y + 1, noiseImage)) {
					int rgb = noiseImage.getRGB(x, y + 1);
					double val = ((rgb >> 16) & 0xFF) / 255.0;
					valDown = val;
				}

				if (valLeft <= MIN_VALUE_TERRAIN && valRight <= MIN_VALUE_TERRAIN && valUp <= MIN_VALUE_TERRAIN
						&& valDown <= MIN_VALUE_TERRAIN) {
					noiseImage.setRGB(x, y, 0);
				} else if (valLeft >= MIN_VALUE_TERRAIN && valRight >= MIN_VALUE_TERRAIN && valUp >= MIN_VALUE_TERRAIN
						&& valDown >= MIN_VALUE_TERRAIN) {
					noiseImage.setRGB(x, y, noiseImage.getRGB(x - 1, y));
				}

			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 19;
		int result = super.hashCode();
		result = prime * result + Objects.hash(perlinNoise, perlinNoiseCopper, perlinNoiseTitanium);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof GameWorld)) {
			return false;
		}
		GameWorld other = (GameWorld) obj;
		return Objects.equals(perlinNoise, other.perlinNoise)
				&& Objects.equals(perlinNoiseCopper, other.perlinNoiseCopper)
				&& Objects.equals(perlinNoiseTitanium, other.perlinNoiseTitanium);
	}
	
	
	
	public File getSaveFile() {
		return saveFile;
	}

	public void setSaveFile(File saveFile) {
		this.saveFile = saveFile;
	}

	/**
	 * Not functional
	 */
	@Override
	public void SaveWorld() {
		try {
			FileOutputStream fileOut = new FileOutputStream(saveFile);
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
