package engine.sprites.structures.fabricators;

import java.util.HashMap;
import java.util.Objects;

import engine.rendering.textures.Texture;
import engine.sprites.PhysicsSprite;
import engine.sprites.entities.player.Inventory;
import engine.sprites.objects.Item;
import engine.sprites.structures.StructureSprite;
import engine.time.DeltaTime;
import math.Vector2;

public class ManualPlatePress extends StructureSprite{
	private static final long serialVersionUID = -2983381922038946838L;
	private double elapsedTime;
	
	private final Inventory storage;
	
	private static final double PRESS_TIME = 5;
	
	private final String[] allowIn = new String[] {"titaniumItem", "aluminiumItem", "copperItem"};

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
		switch(storage.getIDAtIndex(0)) {
			case "titaniumItem":
				
				break;
			case "aluminiumItem":
				
				break;
			case "copperItem":
				
				break;
		}
	}
	
	private void trySendPlate() {
		
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
