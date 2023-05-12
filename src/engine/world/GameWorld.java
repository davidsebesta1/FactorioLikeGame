package engine.world;

import java.awt.image.BufferedImage;
import java.util.Random;

import engine.rendering.textures.TextureLibrary;
import engine.sprites.ores.oresprites.CopperOre;
import engine.sprites.ores.oresprites.TitaniumOre;
import math.Vector2;
import notmycode.PerlinNoise;

public class GameWorld extends World {
	
	private PerlinNoise perlinNoise = new PerlinNoise();
	private PerlinNoise perlinNoiseTitanium = new PerlinNoise(15);
	private PerlinNoise perlinNoiseCopper = new PerlinNoise(21);

	private static final double MIN_VALUE_TERRAIN = 0.5;
	
	private static final double MIN_VALUE_TITANIUM = 0.45;
	private static final double MIN_VALUE_COPPER= 0.45;

	public GameWorld(Vector2 size) {
		super(size);
		
		generateTerrain();
	}
	
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

					this.tiles.tryCreateAtLocation(x, y, TextureLibrary.retrieveTexture("asteroidGround" + rndIndex + "" + rndVariation));

					if (perlinNoiseTitanium.noise(x, y, 0, 20) > MIN_VALUE_TITANIUM && this.tiles.getAtLocation(x, y) != null) {
						this.oreMap.tryAddOreAtLocation(TitaniumOre.instantiateTitaniumOre(TextureLibrary.retrieveTexture("titaniumOre" + random.nextInt(4)), Vector2.oreSpawn, 50), new Vector2(x, y));
					}

					if (perlinNoiseCopper.noise(x, y, 0, 20) > MIN_VALUE_COPPER && this.tiles.getAtLocation(x, y) != null) {
						this.oreMap.tryAddOreAtLocation(CopperOre.instantiateCopperOre(TextureLibrary.retrieveTexture("copperOre" + random.nextInt(4)), Vector2.oreSpawn, 50), new Vector2(x,y));
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

}
