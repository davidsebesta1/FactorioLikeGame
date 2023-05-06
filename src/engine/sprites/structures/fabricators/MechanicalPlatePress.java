package engine.sprites.structures.fabricators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

import engine.physics.CollisionLayers;
import engine.rendering.textures.Texture;
import engine.rendering.textures.TextureLibrary;
import engine.sprites.PhysicsSprite;
import engine.sprites.entities.player.Inventory;
import engine.sprites.objects.Item;
import engine.sprites.objects.processed.plates.AluminiumPlate;
import engine.sprites.objects.processed.plates.CopperPlate;
import engine.sprites.objects.processed.plates.TitaniumPlate;
import engine.sprites.structures.StructureSprite;
import engine.sprites.structures.conveyors.ConveyorBelt;
import engine.time.DeltaTime;
import math.Vector2;

public class MechanicalPlatePress extends StructureSprite{
	private static final long serialVersionUID = -2983381922038946838L;
	private double elapsedTime;
	
	private final Inventory storage;
	
	private static final double PRESS_TIME = 5;
	
	private final String[] allowIn = new String[] {"titaniumItem", "aluminiumItem", "copperItem"};
	
	private final Random random = new Random();

	private MechanicalPlatePress(Texture texture, Vector2 location, double zDepth) {
		super(texture, location, zDepth);
		this.storage = new Inventory();
		this.collisionLayer = CollisionLayers.STRUCTURE;
		
		this.resourceCost = new HashMap<>();
		this.displayName = "Mechanical Plate Press";
		this.resourceCost.put("titaniumItem", 0);
	}
	
	public static MechanicalPlatePress instantiateManualPlatePress(Texture texture, Vector2 location) {
		try {
			return new MechanicalPlatePress(texture, location, 0.7f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	@Override
	public void enteredCollision(PhysicsSprite sprite) {
		if (sprite instanceof Item) {
		    Item item = (Item) sprite;
		    System.out.println(checkForValidItemIn(item.getID()));
		    boolean add = tryAddItemToInventory(item.getID(), 1);
		    System.out.println(add);
		    if(checkForValidItemIn(item.getID()) && add) {
		    	item.destroy();
		    	System.out.println(storage);
		    }
		}
	}
	
	@Override
	public void update() {
		if(!storage.isEmpty()){
			elapsedTime += DeltaTime.getDeltaTime();
			
			if(elapsedTime >= PRESS_TIME) {
				processPlate();
				trySendPlate();
				elapsedTime = 0;
			}
		}
	}
	
	private void processPlate() {
		if(storage.getItemAmount("titaniumItem") > 0) {
			addPlateRemoveItem("titaniumItem", "titaniumPlate");
		} else if (storage.getItemAmount("aluminiumItem") > 0) {
			addPlateRemoveItem("aluminiumItem", "aluminiumPlate");
		} else if (storage.getItemAmount("copperItem") > 0) {
			addPlateRemoveItem("copperItem", "copperPlate");
		}
		
	}
	
	private void addPlateRemoveItem(String itemID, String plateID) {
		storage.tryAddItemToInventory(plateID, 1);
		storage.tryRemoveItemFromInventory(itemID, 1);
	}
	
	private void trySendPlate() {
		ArrayList<ConveyorBelt> belts = getOutcomingBelts();
		if(!belts.isEmpty()) {
			
			if(storage.getItemAmount("titaniumPlate") > 0) {
				int dir = random.nextInt(belts.size());
				belts.get(dir).setItem(TitaniumPlate.instantiateTitaniumPlate(TextureLibrary.retrieveTexture("titaniumPlate"), location));
				storage.tryRemoveItemFromInventory("titaniumPlate", 1);
			} else if(storage.getItemAmount("aluminiumPlate") > 0) {
				int dir = random.nextInt(belts.size());
				belts.get(dir).setItem(AluminiumPlate.instantiateAluminiumPlate(TextureLibrary.retrieveTexture("aluminiumPlate"), location));
				storage.tryRemoveItemFromInventory("aluminiumPlate", 1);
			} else if(storage.getItemAmount("copperPlate") > 0) {
				int dir = random.nextInt(belts.size());
				belts.get(dir).setItem(CopperPlate.instantiateCopperPlate(TextureLibrary.retrieveTexture("copperPlate"), location));
				storage.tryRemoveItemFromInventory("copperPlate", 1);
			}
		}
	}
	
	
	
	public boolean checkForValidItemIn(String id) {
		for(String allowed : allowIn) {
			if(id.equals(allowed)) return true;
		}
		
		return false;
	}
	

	public boolean tryAddItemToInventory(String ID, int amount) {
		if(storage.getItemAmount(ID) >= 5) return false;
		return storage.tryAddItemToInventory(ID, amount);
	}

	public boolean tryRemoveItemFromInventory(String ID, int amount) {
		return storage.tryRemoveItemFromInventory(ID, amount);
	}

	public boolean removeStuffFromInventory(HashMap<String, Integer> map) {
		return storage.removeStuffFromInventory(map);
	}

	public int getItemAmount(String ID) {
		return storage.getItemAmount(ID);
	}

	@Override
	public int hashCode() {
		final int prime = 33;
		int result = super.hashCode();
		result = prime * result + Objects.hash(storage);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof MechanicalPlatePress)) {
			return false;
		}
		MechanicalPlatePress other = (MechanicalPlatePress) obj;
		return Objects.equals(storage, other.storage);
	}

	

	@Override
	public String toString() {
		return "MechanicalPlatePress [storage=" + storage + "] " + collisionBox;
	}

	@Override
	public StructureSprite createCopy(String[] args) {
		return instantiateManualPlatePress(TextureLibrary.retrieveTexture("mechanicalPlatePress"), Vector2.templateSpawn);
	}
	
	public static String ID() {
		return "mechanicalPlatePress";
	}

	public String getID() {
		return ID();
	}
}
