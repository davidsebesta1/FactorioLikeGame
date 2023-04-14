package engine.sprites.entities.player;

import java.io.Serializable;
import java.util.HashMap;

public class PlayerInventory implements Serializable{
	private static final long serialVersionUID = -3016736630296458291L;
	private HashMap<String, Integer> IDAndAmount;

	public PlayerInventory() {
		this.IDAndAmount = new HashMap<>();
	}
	
	public synchronized boolean tryAddItemToInventory(String ID, int amount) {
		if(ID != null && amount >  0 && IDAndAmount.keySet().contains(ID)) {
			IDAndAmount.replace(ID, IDAndAmount.get(ID), IDAndAmount.get(ID) + amount);
			return false;
		}
		
		IDAndAmount.put(ID, amount);
		return true;
	}
	
	public synchronized boolean tryRemoveItemFromInventory(String ID, int amount) {
		if(ID != null && amount >  0 && IDAndAmount.keySet().contains(ID)) {
			if(IDAndAmount.get(ID) - amount > 0) {
				IDAndAmount.replace(ID, IDAndAmount.get(ID), IDAndAmount.get(ID) - amount);
				return true;
			} else {
				return false;
			}
		}
		
		return false;
	}
	
	public synchronized int getItemAmount(String ID) {
		if(ID != null && IDAndAmount.keySet().contains(ID)) return IDAndAmount.get(ID);
		return 0;
	}

}
