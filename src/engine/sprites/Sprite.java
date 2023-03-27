package engine.sprites;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.util.Objects;

import javax.imageio.ImageIO;

import engine.rendering.SpriteManager;
import engine.sprites.entities.Player;
import math.Vector2;

public class Sprite implements Comparable<Sprite>, SpriteBehaviour, Serializable {
	private static final long serialVersionUID = 2893665038957303083L;
	protected BufferedImage image;
	protected float zDepth;
	
	protected Vector2 location;
	protected Vector2 size;
	
	protected boolean isVisible;
	
	protected Sprite(BufferedImage image, Vector2 location, float zDepth) {
		super();
		
		this.image = image;
		this.size = new Vector2(image.getWidth(), image.getHeight());
		
		this.zDepth = zDepth;
		this.location = location;
		this.isVisible = true;
		
		SpriteManager.add(this);
	}
	
	public static Sprite instantiateSprite(File file, Vector2 location, float zDepth) {
		try {
			BufferedImage image = ImageIO.read(file);
			if(zDepth < 0) throw new IllegalArgumentException("Z-Depth must be greater or equal to zero");
			
			return new Sprite(image, location, zDepth);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public float getzDepth() {
		return zDepth;
	}

	public void setzDepth(float zDepth) {
		this.zDepth = zDepth;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public Vector2 getLocation() {
		return location;
	}

	public void setLocation(Vector2 location) {
		this.location = location;
	}

	public Vector2 getSize() {
		return size;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	@Override
	public int compareTo(Sprite o) {
		if(this.zDepth == o.getzDepth()) return 0;
		else if(this.zDepth > o.getzDepth()) return 1;
		else return -1;
	}

	@Override
	public int hashCode() {
		return Objects.hash(zDepth, isVisible, location, size);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sprite other = (Sprite) obj;
		return Float.floatToIntBits(zDepth) == Float.floatToIntBits(other.zDepth);
	}

	@Override
	public String toString() {
		return "Sprite [image=" + image + ", zDepth=" + zDepth + "]";
	}
	

	@Override
	public void start() {
		
	}
	

	@Override
	public void update() {
	}
	
	public void destroy() {
		try {
			SpriteManager.getSprites().remove(this);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}