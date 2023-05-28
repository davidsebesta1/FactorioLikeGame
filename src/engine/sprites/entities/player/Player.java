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
import engine.world.mainmenu.MainMenuButtonManager;
import math.Vector2;

/**
 * Player is a inherited class from PhysicsSprites that determines position and drawing a screen.
 * @author David Šebesta
 * @see PhysicsSprite
 */
public class Player extends PhysicsSprite implements IMouseActionEventListener {
	private static final long serialVersionUID = -6407007599351991639L;
	private Camera camera;
	private boolean inputEnabled;
	
	private Inventory inventory;

	private PlayerConstructionManager constructManager;
	private MainMenuButtonManager menuManager;
	private boolean buildingModeEnabled;
	
	private boolean showBuildMenu;
	private boolean showMainMenu;

	/**
	 * A private class constructor
	 * @param texture
	 * @param location
	 * @param velocity
	 * @param zDepth
	 */
	private Player(Texture texture, Vector2 location, Vector2 velocity, float zDepth) {
		super(texture, location, zDepth);

		this.collisionLayer = CollisionLayers.PLAYER;
		this.camera = new Camera(new Vector2(0, 0));
		this.inventory = new Inventory();
		this.constructManager = new PlayerConstructionManager(new Vector2(0,0));
		this.menuManager = new MainMenuButtonManager();
		
		this.setVelocity(velocity);
		this.setCollisionBox(new BoundingBox(this, false, location, this.getSize()));

		this.buildingModeEnabled = false;
		this.inputEnabled = true;
		
		this.showBuildMenu = true;
		this.showMainMenu = true;
		
		InputManager.addMouseActionListener(this);
		SpriteManager.addUpdateSprite(this);
	}

	/**
	 * Instantiation method with file for texture
	 * @param file
	 * @return Player object
	 */
	public static Player instantiatePlayer(File file) {
		try {
			Texture texture = Texture.createTexture(file);
			return new Player(texture, Game.getInstance().getResolution().div(2), Vector2.zero, 0.8f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * Instantiation method with texture
	 * @param texture
	 * @return Player object
	 */
	public static Player instantiatePlayer(Texture texture) {
		try {
			return new Player(texture, Game.getInstance().getResolution().div(2), Vector2.zero, 0.8f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * Update method for handling user input and mouse motions.
	 */
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

	public boolean canShowBuildMenu() {
		return showBuildMenu;
	}

	public boolean canShowMainMenu() {
		return showMainMenu;
	}

	public void setShowMainMenu(boolean showMainMenu) {
		this.showMainMenu = showMainMenu;
	}

	public void setShowBuildMenu(boolean showBuildMenu) {
		this.showBuildMenu = showBuildMenu;
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

	/**
	 * Overrided collision method
	 */
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

	public MainMenuButtonManager getMenuManager() {
		return menuManager;
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
