package engine.sprites.entities.player;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.io.File;
import java.util.Objects;

import engine.Game;
import engine.input.IMouseActionEventListener;
import engine.input.InputManager;
import engine.physics.BoundingBox;
import engine.physics.CollisionLayers;
import engine.rendering.Camera;
import engine.rendering.textures.Texture;
import engine.sprites.PhysicsSprite;
import engine.sprites.SpriteManager;
import engine.sprites.entities.player.UI.PlayerConstructionManager;
import engine.time.DeltaTime;
import math.Vector2;

public class Player extends PhysicsSprite implements IMouseActionEventListener {
	private static final long serialVersionUID = -6407007599351991639L;
	private Camera camera;
	private boolean inputEnabled;
	
	private Inventory inventory;

	private PlayerConstructionManager constructManager;
	private boolean buildingModeEnabled;

	private Player(Texture texture, Vector2 location, Vector2 velocity, float zDepth) {
		super(texture, location, zDepth);

		this.collisionLayer = CollisionLayers.PLAYER;
		this.camera = new Camera(new Vector2(0, 0));
		this.inventory = new Inventory();
		this.constructManager = new PlayerConstructionManager(new Vector2(0,0));
		
		this.setVelocity(velocity);
		this.setCollisionBox(new BoundingBox(this, false, location, this.getSize()));

		this.buildingModeEnabled = false;
		this.inputEnabled = true;
		
		InputManager.addMouseActionListener(this);
		SpriteManager.addUpdateSprite(this);
	}

	public static Player instantiatePlayer(File file) {
		try {
			Texture texture = Texture.createTexture(file);
			return new Player(texture, Game.getInstance().getResolution().div(2), Vector2.zero, 0.8f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static Player instantiatePlayer(Texture texture) {
		try {
			return new Player(texture, Game.getInstance().getResolution().div(2), Vector2.zero, 0.8f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public void update() {
		if (inputEnabled) {
			velocity = InputManager.getDirectionalInput().mul(100 * DeltaTime.getDeltaTime());

			setLocation(location.add(velocity));

			camera.setLocation(camera.getLocation().add(velocity));
			
			Game.getInstance().getCurrentWorld().getBackground().setLocation(camera.getLocation());
			
			PointerInfo a = MouseInfo.getPointerInfo();
			Point b = a.getLocation();
			int x = (int) b.getX();
			int y = (int) b.getY();
			InputManager.fireMouseMotion(new Vector2(x,y));
		}
	}

	public boolean inputEnabled() {
		return inputEnabled;
	}

	public void setInputEnabled(boolean inputEnabled) {
		this.inputEnabled = inputEnabled;
	}

	public Camera getCamera() {
		return camera;
	}

	public boolean isInputEnabled() {
		return inputEnabled;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(inputEnabled, velocity);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		return inputEnabled == other.inputEnabled && Objects.equals(velocity, other.velocity);
	}

	@Override
	public void mousePrimaryPressed(Vector2 screenCoordinate) {
//		System.out.println(screenCoordinate);
//		System.out.println(Camera.screenToWorldCoordinates(screenCoordinate));

	}

	@Override
	public void mousePrimaryReleased(Vector2 screenCoordinate) {
		// Unused

	}

	@Override
	public void enteredCollision(PhysicsSprite sprite) {
		while (sprite.getCollisionBox().doCollideWith(this.collisionBox)) {
			Vector2 out = sprite.getCenterLocation().sub(getCenterLocation());
			out.normalize();
			out.setX(Math.round(out.getX()));
			out.setY(Math.round(out.getY()));
			this.setLocation(location.sub(out));
			this.camera.setLocation(camera.getLocation().sub(out));
		}
	}

	public static String ID() {
		return "playerEntity";
	}

	public boolean tryAddItemToInventory(String ID, int amount) {
		return inventory.tryAddItemToInventory(ID, amount);
	}

	public boolean tryRemoveItemFromInventory(String ID, int amount) {
		return inventory.tryRemoveItemFromInventory(ID, amount);
	}

	public int getItemAmount(String ID) {
		return inventory.getItemAmount(ID);
	}

	public boolean isBuildingModeEnabled() {
		return buildingModeEnabled;
	}

	public void setBuildingModeEnabled(boolean buildingModeEnabled) {
		this.buildingModeEnabled = buildingModeEnabled;
		
		if(!this.buildingModeEnabled) {
			constructManager.setCurrentlySelected(null);
		}
	}

	@Override
	public String toString() {
		return inventory.toString();
	}

	public Inventory getInventory() {
		return inventory;
	}

	public PlayerConstructionManager getConstructManager() {
		return constructManager;
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
