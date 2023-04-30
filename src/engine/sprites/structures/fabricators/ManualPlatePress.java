package engine.sprites.structures.fabricators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

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

public class ManualPlatePress extends StructureSprite{
	private static final long serialVersionUID = -2983381922038946838L;
	private double elapsedTime;
	
	private final Inventory storage;
	
	private static final double PRESS_TIME = 5;
	
	private final String[] allowIn = new String[] {"titaniumItem", "aluminiumItem", "copperItem"};
	
	private final Random random = new Random();

	private ManualPlatePress(Texture texture, Vector2 location, double zDepth) {
		super(texture, location, zDepth);
		this.storage = new Inventory();
		
		this.resourceCost = new HashMap<>();
		this.displayName = "Plate Press";
		this.resourceCost.put("titaniumItem", 30);
	}
	
	public static ManualPlatePress instantiateManualPlatePress(Texture texture, Vector2 location) {
		try {
			return new ManualPlatePress(texture, location, 0.7f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	@Override
	public void enteredCollision(PhysicsSprite sprite) {
		if (sprite instanceof Item) {
		    Item item = (Item) sprite;
		    if(checkForValidItemIn(item.getID()) && tryAddItemToInventory(item.getID(), 1)) item.destroy();
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
				int dir = random.nextInt(belts.size() - 1);
				belts.get(dir).setItem(TitaniumPlate.instantiateTitaniumPlate(TextureLibrary.retrieveTexture("titaniumPlate"), location));
				storage.tryRemoveItemFromInventory("titaniumPlate", 1);
			} else if(storage.getItemAmount("aluminiumPlate") > 0) {
				int dir = random.nextInt(belts.size() - 1);
				belts.get(dir).setItem(AluminiumPlate.instantiateAluminiumPlate(TextureLibrary.retrieveTexture("aluminiumPlate"), location));
				storage.tryRemoveItemFromInventory("aluminiumPlate", 1);
			} else if(storage.getItemAmount("copperPlate") > 0) {
				int dir = random.nextInt(belts.size() - 1);
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
		if (!(obj instanceof ManualPlatePress)) {
			return false;
		}
		ManualPlatePress other = (ManualPlatePress) obj;
		return Objects.equals(storage, other.storage);
	}

	@Override
	public String toString() {
		return "ManualPlatePress [storage=" + storage + "]";
	}
}
