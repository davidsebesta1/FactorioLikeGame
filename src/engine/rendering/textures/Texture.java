package engine.rendering.textures;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

/**
 * Texture is a class holding several images of a image. Its also able to generated rotated versions of a image. Each texture is hold within TextureLibrary. Each texture has its own TextureType.
 * @see TextureLibrary
 * @see TextureType
 * @author David Šebesta
 */
public class Texture {
	
	/**
	 * Actual image
	 */
	private BufferedImage image;
	
	/*
	 * Transparent image for placing structures
	 */
	private BufferedImage validPlacementImage;
	
	/**
	 * Red transparent image for placing structures
	 */
	private BufferedImage invalidPlacementImage;
	
	/**
	 * Its TextureType
	 * @see TextureType
	 */
	private TextureType type;

	/**
	 * Class constructor, cannot be called directly
	 * @param file
	 * @param type
	 */
	private Texture(File file, TextureType type) {
		try {
			// Load the image
			BufferedImage imageTemp = ImageIO.read(file);

			// Create a new image with the desired type
			GraphicsConfiguration gfxConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
			this.image = gfxConfig.createCompatibleImage(imageTemp.getWidth(), imageTemp.getHeight(), type.getId());
			this.image.setAccelerationPriority(1);
			this.type = type;

			// Draw the original image onto the new image
			Graphics2D g2d = image.createGraphics();
			g2d.drawImage(imageTemp, 0, 0, null);
			g2d.dispose();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Class constructor, cannot be called directly
	 * @param image
	 */
	private Texture(BufferedImage imageTemp) {
		// Create a new image with the desired type
		GraphicsConfiguration gfxConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		this.image = gfxConfig.createCompatibleImage(imageTemp.getWidth(), imageTemp.getHeight(), Transparency.OPAQUE);
		this.image.setAccelerationPriority(1);

		// Draw the original image onto the new image
		Graphics2D g2d = image.createGraphics();
		g2d.drawImage(imageTemp, 0, 0, null);
		g2d.dispose();
	}
	
	/**
	 * Class constructor, cannot be called directly-
	 * @param imageTemp
	 * @param type
	 */
	private Texture(BufferedImage imageTemp, TextureType type) {
		// Create a new image with the desired type
		GraphicsConfiguration gfxConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		this.image = gfxConfig.createCompatibleImage(imageTemp.getWidth(), imageTemp.getHeight(), type.getId());
		this.image.setAccelerationPriority(1);

		// Draw the original image onto the new image
		Graphics2D g2d = image.createGraphics();
		g2d.drawImage(imageTemp, 0, 0, null);
		g2d.dispose();
	}

	/**
	 * Static method to create a texture from specified file, specified file is OPAQUE by default.
	 * @param file
	 * @return Texture
	 */
	public static Texture createTexture(File file) {
		if (file.exists()) {
			return new Texture(file, TextureType.OPAQUE);
		}
		throw new IllegalArgumentException("Specified texture file not found");
	}
	

	/**
	 * Static method to create a texture from specified file and specified texture file
	 * @param file
	 * @param type
	 * @return Texture
	 */
	public static Texture createTexture(File file, TextureType type) {
		if (file.exists()) {
			return new Texture(file, type);
		}
		throw new IllegalArgumentException("Specified texture file not found");
	}

	public BufferedImage getImage() {
		return image;
	}

	/**
	 * Created graphics accerelated image
	 * @return VolatileImage
	 */
	public VolatileImage getAccerelatedImage() {
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		VolatileImage volatileImage = gc.createCompatibleVolatileImage(image.getWidth(), image.getHeight(),
				image.getTransparency());
		Graphics2D g = volatileImage.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();

		return volatileImage;
	}
	
	/**
	 * Returns and caches transparent version of a image
	 * @return transparent image
	 */
	public BufferedImage getValidPlacementVersion() {
		if(validPlacementImage != null) return validPlacementImage;
		
		validPlacementImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = validPlacementImage.createGraphics();

		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
		
		return validPlacementImage;
	}
	
	/**
	 * Returns and caches red transparent version of a image
	 * @return red transparent image
	 */
	public BufferedImage getInvalidPlacementVersion() {
		if(invalidPlacementImage != null) return invalidPlacementImage;
		
		invalidPlacementImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = invalidPlacementImage.createGraphics();
		
		g2d.drawImage(getValidPlacementVersion(), 0, 0, null);
		g2d.dispose();
		
		int width = invalidPlacementImage.getWidth();
		int height = invalidPlacementImage.getHeight();

		for (int x = 0; x < width; x++) {
		    for (int y = 0; y < height; y++) {
		        int rgb = invalidPlacementImage.getRGB(x, y);
		        int r = (rgb >> 16) & 0xff; // extract the red component
		        int g = (rgb >> 8) & 0xff; // extract the green component
		        int b = rgb & 0xff; // extract the blue component

		        // add 50 to the red component
		        r = Math.min(r + 50, 255);

		        int newRGB = (r << 16) | (g << 8) | b; // combine the modified components
		        invalidPlacementImage.setRGB(x, y, newRGB); // set the new pixel color
		    }
		}
		
		return validPlacementImage;
	}
	
	/**
	 * Returns a rotated image. Image is rotated by double in piRadions. Eg: pi/2 is 90 degrees.
	 * @param source
	 * @param piRadians
	 * @return rotated texture
	 */
	public static Texture rotateImage(Texture source, double piRadians) {
		BufferedImage src = source.getImage();
	    int width = src.getWidth();
	    int height = src.getHeight();

	    BufferedImage rotatedImage;
	    if(src.getType() == 0) {
	    	 rotatedImage = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);
	    } else {
	    	 rotatedImage = new BufferedImage(height, width, src.getType());
	    }

	    Graphics2D g2d = rotatedImage.createGraphics();
	    g2d.translate((height - width) / 2d, (height - width) / 2d);
	    g2d.rotate(piRadians, height / 2d, width / 2d);
	    g2d.drawRenderedImage(src, null);

	    return new Texture(rotatedImage, source.type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(image);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Texture other = (Texture) obj;
		return Objects.equals(image, other.image);
	}
	
	
}
