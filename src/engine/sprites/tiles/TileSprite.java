package engine.sprites.tiles;

import java.io.File;

import engine.Game;
import engine.rendering.textures.Texture;
import engine.sprites.Sprite;
import engine.sprites.entities.player.UI.StructureButton;
import engine.sprites.structures.StructureMap;
import engine.sprites.structures.StructureSprite;
import math.Vector2;

public class TileSprite extends Sprite {
	private static final long serialVersionUID = -1961159418183745510L;

	private TileSprite(Texture texture, Vector2 location) {
		super(texture, location, 0.1d);
	}

	public static TileSprite instantiateTileSprite(File file, Vector2 location) {
		try {
			Texture texture = Texture.createTexture(file);
			return new TileSprite(texture, location);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public static TileSprite instantiateTileSprite(Texture texture, Vector2 location) {
		try {
			return new TileSprite(texture, location);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	@Override
	public void onMouseClicked() {
		StructureButton template = Game.getInstance().getCurrentWorld().getPlayer().getConstructManager().getCurrentlySelected();
		if(template != null) {
			StructureSprite sprite = null;
			try {
				sprite = (StructureSprite) template.getTemplateStructure().clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			
			if(sprite != null && template.getTemplateStructure().hasEnoughItemsForConstruct(Game.getInstance().getCurrentWorld().getPlayer().getInventory())) {
				Vector2 locOnMap = StructureMap.worldToStructureMapCoordinate(this.location);
				boolean validConstruct = Game.getInstance().getCurrentWorld().getStructureMap().tryAddStructureAtLocation(sprite, locOnMap);
				System.out.println(validConstruct + " aaaaa");
				if(validConstruct) {
					Game.getInstance().getCurrentWorld().getPlayer().getInventory().removeStuffFromInventory(template.getTemplateStructure().getResourceCost());
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
