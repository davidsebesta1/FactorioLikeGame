package engine.sprites.entities.player.UI;

import java.util.ArrayList;
import java.util.Objects;

import engine.input.IMouseActionEventListener;
import engine.input.InputManager;
import engine.rendering.textures.Texture;
import math.Vector2;

public class StructureTypeButton implements IMouseActionEventListener {
	private String type;

	private Vector2 location;
	private Vector2 size;
	private PlayerConstructionManager owner;
	
	private Texture texture;
	
	private boolean isVisible = true;

	private static ArrayList<StructureTypeButton> allTypes = new ArrayList<>();

	public StructureTypeButton(String type, Vector2 location, Vector2 size, PlayerConstructionManager owner, Texture texture) {
		super();
		this.type = type;
		this.location = location;
		this.size = size;
		this.owner = owner;
		this.texture = texture;

		InputManager.addMouseActionListener(this);

		allTypes.add(this);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public static ArrayList<StructureTypeButton> getAllTypes() {
		return allTypes;
	}
	
	

	public Texture getTexture() {
		return texture;
	}

	@Override
	public void mousePrimaryPressed(Vector2 screenCoordinate) {
		System.out.println("You clicked " + this);
		owner.showCorrespondingStructButton(this);

	}

	@Override
	public void mousePrimaryReleased(Vector2 screenCoordinate) {
		// TODO Auto-generated method stub

	}

	@Override
	public int hashCode() {
		return Objects.hash(location, owner, size, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StructureTypeButton other = (StructureTypeButton) obj;
		return Objects.equals(location, other.location) && Objects.equals(owner, other.owner)
				&& Objects.equals(size, other.size) && Objects.equals(type, other.type);
	}

	@Override
	public String toString() {
		return "StructureTypeButton [type=" + type + "]";
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public PlayerConstructionManager getOwner() {
		return owner;
	}

	@Override
	public void mouseSecondaryPressed(Vector2 screenCoordinate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseSecondaryReleased(Vector2 screenCoordinate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMiddlePressed(Vector2 screenCoordinate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMiddleReleased(Vector2 screenCoordinate) {
		// TODO Auto-generated method stub
		
	}
}
