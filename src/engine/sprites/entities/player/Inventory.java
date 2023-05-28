package engine.sprites.entities.player;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Inventory is a class that is able to hold items inside it independend of a player, this means it is possible to use inventory for structures.
 * @author David Šebesta
 * @see Player
 */
public class Inventory implements Serializable{
	private static final long serialVersionUID = -3016736630296458291L;
	private HashMap<String, Integer> IDAndAmount;

	/**
	 * Class constructor
	 */
	public Inventory() {
		this.IDAndAmount = new HashMap<>();
	}
	
	/**
	 * Attempts to add a positive amount of items to inventory
	 * @param ID
	 * @param amount
	 * @return True if item is added, otherwise false
	 */
	public synchronized boolean tryAddItemToInventory(String ID, int amount) {
		if(amount <= 0) return false;
		if(ID == null) return false;
		
		if(IDAndAmount.keySet().contains(ID)) {
			IDAndAmount.replace(ID, IDAndAmount.get(ID), IDAndAmount.get(ID) + amount);
			return true;
		}
		
		
		IDAndAmount.put(ID, amount);
		return true;
	}
	
	/**
	 * Attempts to remove a positive amount of existing items from player inventory
	 * @param ID
	 * @param amount
	 * @return True if items has been sucessfully removed, otherwise false
	 */
	public synchronized boolean tryRemoveItemFromInventory(String ID, int amount) {
		if(ID != null && amount > 0 && IDAndAmount.keySet().contains(ID)) {
			if(IDAndAmount.get(ID) - amount >= 0) {
				IDAndAmount.replace(ID, IDAndAmount.get(ID), IDAndAmount.get(ID) - amount);
				
				if(IDAndAmount.get(ID) == 0) IDAndAmount.remove(ID);
				return true;
			} else {
				return false;
			}
		}
		
		return false;
	}
	
	/**
	 * Removes whole hashmap from inventory
	 * @param map
	 * @return True if success
	 */
	public synchronized boolean removeStuffFromInventory(HashMap<String, Integer> map) {
		boolean allRemoved = false;
		for(Map.Entry<String, Integer> entry : map.entrySet()) {
			if(tryRemoveItemFromInventory(entry.getKey(), entry.getValue())) {
				allRemoved = true;
			} else {
				allRemoved = false;
			}
		}
		
		return allRemoved;
	}
	
	/**
	 * Check for existing item in inventory
	 * @param id
	 * @return True if item exists, otherwise false
	 */
	public synchronized boolean containsItemWithID(String id) {
		for(String key : IDAndAmount.keySet()) {
			if(key.equals(id)) return true;
		}
		
		return false;
	}
	
	/**
	 * Returns a amount of items in the inventory
	 * @param ID
	 * @return Amount of items in the inventory. Returns 0 if no items are found.
	 */
	public int getItemAmount(String ID) {
		if(ID != null && IDAndAmount.keySet().contains(ID)) return IDAndAmount.get(ID);
		return 0;
	}
	
	/**
	 * Returns if inventory is empty
	 * @return True if is empty, otherwise false.
	 */
	public boolean isEmpty() {
		return IDAndAmount.isEmpty();
	}
	
	/**
	 * Returns item name at specified index
	 * @param index
	 * @return Item name as String
	 */
	public String getIDAtIndex(int index) {
		if(index < 0 || index > IDAndAmount.keySet().size()) return null;
		return (String) IDAndAmount.keySet().toArray()[index];
	}

	@Override
	public int hashCode() {
		return Objects.hash(IDAndAmount);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Inventory)) {
			return false;
		}
		Inventory other = (Inventory) obj;
		return Objects.equals(IDAndAmount, other.IDAndAmount);
	}

	@Override
	public String toString() {
		return "Inventory [IDAndAmount=" + IDAndAmount + "]";
	}
}
