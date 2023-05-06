package engine.sprites.entities.player;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Inventory implements Serializable{
	private static final long serialVersionUID = -3016736630296458291L;
	private HashMap<String, Integer> IDAndAmount;

	public Inventory() {
		this.IDAndAmount = new HashMap<>();
	}
	
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
	
	public synchronized boolean containsItemWithID(String id) {
		for(String key : IDAndAmount.keySet()) {
			if(key.equals(id)) return true;
		}
		
		return false;
	}
	
	public int getItemAmount(String ID) {
		if(ID != null && IDAndAmount.keySet().contains(ID)) return IDAndAmount.get(ID);
		return 0;
	}
	
	public boolean isEmpty() {
		return IDAndAmount.isEmpty();
	}
	
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
