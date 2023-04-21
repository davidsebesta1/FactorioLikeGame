package engine.sprites.entities.player.UI;

import java.util.ArrayList;
import java.util.Objects;

import engine.input.IMouseActionEventListener;
import engine.input.InputManager;
import engine.rendering.textures.Texture;
import math.Vector2;

public class StructureButton implements IMouseActionEventListener {
	private String structureID;

	private Vector2 location;
	private Vector2 size;
	private PlayerConstructionManager owner;
	
	private Texture texture;
	
	private boolean isVisible = true;

	private static ArrayList<StructureButton> buttons = new ArrayList<>();

	public StructureButton(String structureID, Vector2 location, Vector2 size, PlayerConstructionManager owner, Texture texture) {
		super();
		this.structureID = structureID;
		this.location = location;
		this.size = size;
		this.owner = owner;
		this.texture = texture;

		InputManager.addMouseActionListener(this);

		buttons.add(this);
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

	public void setSize(Vector2 size) {
		this.size = size;
	}

	public String getStructureID() {
		return structureID;
	}

	public void setStructureID(String structureID) {
		this.structureID = structureID;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public PlayerConstructionManager getOwner() {
		return owner;
	}

	public static ArrayList<StructureButton> getButtons() {
		return buttons;
	}

	@Override
	public void mousePressed(Vector2 screenCoordinate) {

	}

	@Override
	public void mouseReleased(Vector2 screenCoordinate) {
		// TODO Auto-generated method stub

	}

	@Override
	public int hashCode() {
		return Objects.hash(location, owner, size, structureID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StructureButton other = (StructureButton) obj;
		return Objects.equals(location, other.location) && Objects.equals(owner, other.owner)
				&& Objects.equals(size, other.size) && Objects.equals(structureID, other.structureID);
	}

	@Override
	public String toString() {
		return "StructureButton [structureID=" + structureID + "]";
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
}
