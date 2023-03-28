package engine.sprites.tiles;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import engine.sprites.Sprite;
import math.Vector2;

public class TileSprite extends Sprite {
	private static final long serialVersionUID = -1961159418183745510L;

	private TileSprite(BufferedImage image, Vector2 location) {
		super(image, location, 0.1f);
	}

	public static TileSprite instantiateSprite(File file, Vector2 location) {
		try {
			BufferedImage image = ImageIO.read(file);
			return new TileSprite(image, location);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}
