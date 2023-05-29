package engine.world.mainmenu;

import java.util.Objects;

import engine.input.IMouseActionEventListener;
import engine.rendering.textures.Texture;
import math.Vector2;

/**
 * A button class used for input detection
 * @author David Šebesta
 *
 */
public abstract class Button implements IMouseActionEventListener{
	
	/**
	 * Owning manager
	 */
	protected MainMenuButtonManager owner;
	
	//variables
	protected Vector2 location;
	protected Vector2 size;
	
	protected Texture texture;
	protected String text;
	
	protected boolean isVisible = false;

	/**
	 * Class constructor with specified texture
	 * @param manager
	 * @param location
	 * @param size
	 * @param texture
	 */
	public Button(MainMenuButtonManager manager, Vector2 location, Vector2 size, Texture texture) {
		super();
		this.owner = manager;
		this.location = location;
		this.size = size;
		this.texture = texture;
		this.text = "";
	}
	
	/**
	 * Class constructor
	 * @param manager
	 * @param location
	 * @param size
	 */
	public Button(MainMenuButtonManager manager,Vector2 location, Vector2 size) {
		super();
		this.owner = manager;
		this.location = location;
		this.size = size;
		this.text = "";
	}
	
	/**
	 * Class constructor with specified string
	 * @param manager
	 * @param location
	 * @param size
	 * @param text
	 */
	public Button(MainMenuButtonManager manager,Vector2 location, Vector2 size, String text) {
		super();
		this.owner = manager;
		this.location = location;
		this.size = size;
		this.text = text;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public Vector2 getLocation() {
		return location;
	}

	public Vector2 getSize() {
		return size;
	}

	public Texture getTexture() {
		return texture;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public int hashCode() {
		return Objects.hash(isVisible, location, size);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Button)) {
			return false;
		}
		Button other = (Button) obj;
		return isVisible == other.isVisible && Objects.equals(location, other.location)
				&& Objects.equals(size, other.size);
	}

	@Override
	public String toString() {
		return "Button [location=" + location + ", size=" + size + ", texture=" + texture + ", isVisible=" + isVisible
				+ "]";
	}

	@Override
	public void mousePrimaryPressed(Vector2 screenCoordinate) {

	}

	@Override
	public void mousePrimaryReleased(Vector2 screenCoordinate) {

	}

	@Override
	public void mouseSecondaryPressed(Vector2 screenCoordinate) {

	}

	@Override
	public void mouseSecondaryReleased(Vector2 screenCoordinate) {

	}

	@Override
	public void mouseMiddlePressed(Vector2 screenCoordinate) {

	}

	@Override
	public void mouseMiddleReleased(Vector2 screenCoordinate) {

	}

}
