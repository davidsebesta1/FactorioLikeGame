package engine.sprites.structures.drills;

import engine.Game;
import engine.physics.CollisionLayers;
import engine.rendering.textures.Texture;
import engine.sprites.ores.OreMap;
import engine.sprites.structures.StructureMap;
import engine.sprites.structures.StructureSprite;
import engine.time.DeltaTime;
import math.Vector2;

public class BasicDrill extends StructureSprite{
	private static final long serialVersionUID = 6419053635390734429L;
	
	protected static final double DEFAULT_MINING_TIME = 10d;
	protected double elapsedTime;

	protected BasicDrill(Texture texture, Vector2 location, double zDepth) {
		super(texture, location, zDepth);
		this.collisionLayer = CollisionLayers.STRUCTURE;
		this.elapsedTime = 0;
	}

	public static double getDefaultMiningTime() {
		return DEFAULT_MINING_TIME;
	}

	public static BasicDrill instantiateBasicDrill(Texture texture, Vector2 location) {
		try {
			return new BasicDrill(texture, location, 0.7f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void update() {
		elapsedTime += DeltaTime.getDeltaTime();
		
		if(elapsedTime >= DEFAULT_MINING_TIME) {
			System.out.println("Checked for mining");
			elapsedTime = 0;
		}
	}
	
	protected void tryMineResource() {
		OreMap oreMap = Game.getInstance().getCurrentWorld().getOreMap();
		StructureMap structMap = Game.getInstance().getCurrentWorld().getStructureMap();
	}
	
	protected boolean mineableResourceAvailable() {
		OreMap oreMap = Game.getInstance().getCurrentWorld().getOreMap();
		
		for(int i = (int) location.getX(); i < (int) (location.getX() + tileSizeUnits.getX() * 32); i = i + 32) {
			for(int j = (int) location.getY(); j < (int) (location.getY() + tileSizeUnits.getY() * 32); j = j + 32) {
				if(oreMap.getOreAtWorldLocation(new Vector2(i, j)) != null ) return true;
			}
		}
		
		return false;
	}
	
	
		
	

}
