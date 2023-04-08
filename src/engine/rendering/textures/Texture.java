package engine.rendering.textures;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

public class Texture {
	private BufferedImage image;
	private boolean isOpaque;
	private float alpha;

	private Texture(File file) {
		try {
			// Load the image
			BufferedImage imageTemp = ImageIO.read(file);

			// Create a new image with the desired type
			this.image = new BufferedImage(imageTemp.getWidth(), imageTemp.getHeight(), BufferedImage.TYPE_INT_RGB);
			this.image.setAccelerationPriority(1);
			this.isOpaque = true;
			this.alpha = 1f;

			// Draw the original image onto the new image
			Graphics2D g2d = image.createGraphics();
			g2d.drawImage(imageTemp, 0, 0, null);
			g2d.dispose();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Texture(File file, float alpha) {
		try {
			// Load the image
			BufferedImage imageTemp = ImageIO.read(file);

			// Create a new image with the desired type
			this.image = new BufferedImage(imageTemp.getWidth(), imageTemp.getHeight(), BufferedImage.TYPE_INT_ARGB);
			this.image.setAccelerationPriority(1);
			this.isOpaque = false;
			this.alpha = alpha;

			// Draw the original image onto the new image
			Graphics2D g2d = image.createGraphics();
			g2d.drawImage(imageTemp, 0, 0, null);
			g2d.dispose();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Texture(BufferedImage imageTemp, float alpha) {
		// Create a new image with the desired type
		this.image = new BufferedImage(imageTemp.getWidth(), imageTemp.getHeight(), BufferedImage.TYPE_INT_ARGB);
		this.image.setAccelerationPriority(1);
		this.isOpaque = false;
		this.alpha = alpha;

		// Draw the original image onto the new image
		Graphics2D g2d = image.createGraphics();
		g2d.drawImage(imageTemp, 0, 0, null);
		g2d.dispose();
	}

	public static Texture createTexture(File file) {
		if (file.exists()) {
			return new Texture(file);
		}
		throw new IllegalArgumentException("Specified texture file not found");
	}
	
	public static Texture createTexture(File file, float alpha) {
		if (file.exists()) {
			return new Texture(file, alpha);
		}
		throw new IllegalArgumentException("Specified texture file not found");
	}

	public BufferedImage getImage() {
		return image;
	}

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
	
	public static Texture rotateImage(Texture source, double piRadians) {
		BufferedImage src = source.getImage();
	    int width = src.getWidth();
	    int height = src.getHeight();

	    BufferedImage rotatedImage = new BufferedImage(height, width, src.getType());

	    Graphics2D g2d = rotatedImage.createGraphics();
	    g2d.translate((height - width) / 2d, (height - width) / 2d);
	    g2d.rotate(piRadians, height / 2d, width / 2d);
	    g2d.drawRenderedImage(src, null);

	    return new Texture(rotatedImage, source.getAlpha());
	}

	public boolean isOpaque() {
		return isOpaque;
	}

	public void setOpaque(boolean isOpaque) {
		this.isOpaque = isOpaque;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
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
