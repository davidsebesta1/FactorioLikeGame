package engine.sprites.structures.command;

import java.io.File;

import engine.Game;
import engine.physics.CollisionLayers;
import engine.rendering.textures.Texture;
import engine.rendering.textures.TextureLibrary;
import engine.sprites.PhysicsSprite;
import engine.sprites.objects.Item;
import engine.sprites.structures.StructureSprite;
import math.Vector2;

/**
 * Core Module is a main module in this game, providing access to collecting resources.
 * @author David Šebesta
 *
 */
public class CoreModule extends StructureSprite {
	private static final long serialVersionUID = 5792682450616901356L;

	private CoreModule(Texture texture, Vector2 location, double zDepth) {
		super(texture, location, zDepth);
		this.collisionLayer = CollisionLayers.STRUCTURE;
		
		this.displayName = "Core Module";
		this.resourceCost.put("titaniumItem", 2);
	}

	/**
	 * Instantiate method with file for texture
	 * @param file
	 * @param location
	 * @return CoreModule object
	 */
	public static CoreModule instantiateCoreModule(File file, Vector2 location) {
		try {
			Texture texture = Texture.createTexture(file);
			return new CoreModule(texture, location, 0.7f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * Instantiate method with texture object
	 * @param texture
	 * @param location
	 * @return CoreModule object
	 */
	public static CoreModule instantiateCoreModule(Texture texture, Vector2 location) {
		try {
			return new CoreModule(texture, location, 0.7f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}

	public static String ID() {
		return "coreModule";
	}
	
	public String getID() {
		return ID();
	}
	
	/**
	 * Overriden method to handle collisions with items.
	 */
	@Override
	public void enteredCollision(PhysicsSprite sprite) {
		if (sprite instanceof Item) {
		    Item item = (Item) sprite;
		    String itemId = item.getID();
		    
		    Game.getInstance().getCurrentWorld().getPlayer().tryAddItemToInventory(itemId, 1);
		    System.out.println(Game.getInstance().getCurrentWorld().getPlayer().getItemAmount(itemId) + itemId);
		    
		    item.destroy();
		}
	}

	/**
	 * Created a copy of this object
	 */
	@Override
	public StructureSprite createCopy(String[] args) {
		return instantiateCoreModule(TextureLibrary.retrieveTexture("coreModule"), Vector2.templateSpawn);
	}
}
