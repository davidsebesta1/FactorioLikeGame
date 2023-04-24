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
			
			if(sprite != null) {
				Vector2 locOnMap = StructureMap.worldToStructureMapCoordinate(this.location);
				System.out.println(Game.getInstance().getCurrentWorld().getStructureMap().tryAddStructureAtLocation(sprite, locOnMap));
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
		Game.getInstance().getCurrentWorld().getTiles().tryToRemove(this);
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

	@Override
	public String ID() {
		return "tileSprite";
	}
	
	
	
	

}
