package engine.sprites.tiles;

import java.io.File;

import engine.Game;
import engine.rendering.textures.Texture;
import engine.sprites.Sprite;
import engine.sprites.entities.player.Player;
import engine.sprites.entities.player.UI.StructureButton;
import engine.sprites.structures.StructureMap;
import engine.sprites.structures.StructureSprite;
import engine.sprites.structures.conveyors.ConveyorBelt;
import main.Log;
import math.Vector2;

/**
 * Extended version of a Sprite used to hold information about game world and structures, ores and such on it.
 * @author David Šebesta
 * @see TileMap
 */
public class TileSprite extends Sprite {
	private static final long serialVersionUID = -1961159418183745510L;

	/**
	 * Private constructor
	 * @param texture
	 * @param location
	 */
	private TileSprite(Texture texture, Vector2 location) {
		super(texture, location, 0.1d);
	}

	/**
	 * Static instantiation method with specified file
	 * @param file
	 * @param location
	 * @return TileSprite object
	 */
	public static TileSprite instantiateTileSprite(File file, Vector2 location) {
		try {
			Texture texture = Texture.createTexture(file);
			return new TileSprite(texture, location);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	/**
	 * Static instatiation method with specified texture
	 * @param texture
	 * @param location
	 * @return TileSprite object
	 */
	public static TileSprite instantiateTileSprite(Texture texture, Vector2 location) {
		try {
			return new TileSprite(texture, location);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	/**
	 * Overriden method for placing structures
	 * @see PlayerConstructionManager
	 */
	@Override
	public void onMouseClicked() {
		Player player =  Game.getInstance().getCurrentWorld().getPlayer();
		StructureButton template = player.getConstructManager().getCurrentlySelected();
		if(template != null) {
			
			StructureSprite spritetemplate = template.getTemplateStructure();
			
			StructureSprite sprite = null;
			if(spritetemplate instanceof ConveyorBelt) {
				sprite = ((ConveyorBelt) spritetemplate).createCopy(new String[] {((ConveyorBelt) spritetemplate).getDirection().getName()});
			} else {
				sprite = spritetemplate.createCopy(null);
			}
			
			
			
			if(sprite != null && template.getTemplateStructure().hasEnoughItemsForConstruct(player.getInventory())) {
				Vector2 locOnMap = StructureMap.worldToStructureMapCoordinate(this.location);
				boolean validConstruct = Game.getInstance().getCurrentWorld().getStructureMap().tryAddStructureAtLocation(sprite, locOnMap);
				if(validConstruct) {
					player.getInventory().removeStuffFromInventory(template.getTemplateStructure().getResourceCost());
					
					Log.info("Created new structure " + sprite.getDisplayName() + " at " + locOnMap);
				}
			}
		}
	}
	
	@Override
	public String toString() {
		return "TileSprite [location=" + location + ", size=" + size + ", getzDepth()=" + getzDepth()
				+ ", getTexture()=" + getTexture() + ", getLocation()=" + getLocation()
				+ ", isVisible()=" + isVisible() +", toString()=" + super.toString();
	}
	
	@Override
	public void destroy() {
		super.destroy();
		Game.getInstance().getTileMap().tryToRemove(this);
	}

	

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof TileSprite)) {
			return false;
		}
		return true;
	}

	public static String ID() {
		return "tileSprite";
	}
	
	
	
	

}
