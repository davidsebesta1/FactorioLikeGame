package engine.sprites.structures.drills;

import engine.Game;
import engine.physics.CollisionLayers;
import engine.rendering.textures.Texture;
import engine.rendering.textures.TextureLibrary;
import engine.sprites.objects.Item;
import engine.sprites.objects.minable.Coal;
import engine.sprites.ores.CoalOre;
import engine.sprites.ores.OreMap;
import engine.sprites.ores.OreSprite;
import engine.sprites.structures.StructureMap;
import engine.sprites.structures.StructureSprite;
import engine.sprites.structures.conveyors.ConveyorBelt;
import engine.time.DeltaTime;
import math.Vector2;

public class BasicDrill extends StructureSprite {
	private static final long serialVersionUID = 6419053635390734429L;

	protected static final double DEFAULT_MINING_TIME = 3d;
	protected double elapsedTime;

	protected Item minedItem;

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

		if (elapsedTime >= DEFAULT_MINING_TIME) {
			System.out.println("Checked for mining");
			tryMineResource();
			System.out.println(minedItem);
			elapsedTime = 0;
		}
	}

	protected void tryMineResource() {
//		OreMap oreMap = Game.getInstance().getCurrentWorld().getOreMap();
//		StructureMap structMap = Game.getInstance().getCurrentWorld().getStructureMap();

		OreSprite ore = tryGetMinableResource();

		if (ore != null && ore.getOreAmount() > 0) {
			System.out.println(ore.getOreAmount());
			minedItem = resolveMinedItemType(ore);
			minedItem.setVisible(false);
		}
		
		if(minedItem != null) {
			checkForBeltAndAddResource(ore);
		}

	}

	protected void checkForBeltAndAddResource(OreSprite ore) {
		ConveyorBelt belt = null;
		for (StructureSprite csrct : Game.getInstance().getCurrentWorld().getStructureMap().getAdjacentStructures(StructureMap.worldToStructureMapCoordinate(location))) {
			if (csrct instanceof ConveyorBelt && ((ConveyorBelt) csrct).getItem() == null) {
				belt = (ConveyorBelt) csrct;
				break;
			}
		}

		if (belt != null && belt.getItem() == null) {
			addMinedResourceToBelt(belt, minedItem, ore);
		}
	}
	
	public Item resolveMinedItemType(OreSprite sprite) {
		if(sprite instanceof CoalOre) return Coal.instantiateCoal(TextureLibrary.retrieveTexture("coal"), Vector2.zero);
		
		return null;
	}

	protected void addMinedResourceToBelt(ConveyorBelt belt, Item item, OreSprite ore) {
		if(belt != null && item != null) {
			minedItem.setVisible(true);
			belt.setItem(item);
			ore.tryRemoveResource();
			minedItem = null;
		}
	}

	protected OreSprite tryGetMinableResource() {
		OreMap oreMap = Game.getInstance().getCurrentWorld().getOreMap();

		for (int i = (int) location.getX(); i < (int) (location.getX() + tileSizeUnits.getX() * 32); i = i + 32) {
			for (int j = (int) location.getY(); j < (int) (location.getY() + tileSizeUnits.getY() * 32); j = j + 32) {
				if (oreMap.getOreAtWorldLocation(new Vector2(i, j)) != null)
					return oreMap.getOreAtWorldLocation(new Vector2(i, j));
			}
		}

		return null;
	}

}
