package techTree;

import java.io.Serializable;
import java.util.HashSet;

import engine.sprites.entities.player.UI.PlayerConstructionManager;

public class ResearchNode implements Serializable {
	private static final long serialVersionUID = -8000307235101390546L;
	
	private boolean isUnlocked = false;
	private String researchName;
	private String[] researchIds;
	
	private int researchPointCost;
	
	private PlayerConstructionManager constructManager;
	private ResearchManager researchManager;
	
	private HashSet<ResearchNode> nextNodes;

	public ResearchNode(String researchName, String[] researchIds, int researchPointCost, PlayerConstructionManager constructManager, ResearchManager researchManager) {
		this.researchName = researchName;
		this.researchIds = researchIds;
		this.researchPointCost = researchPointCost;
		this.nextNodes = new HashSet<>();
		this.constructManager = constructManager;
		this.researchManager = researchManager;
	}
	
	public boolean addNext(ResearchNode node) {
		return nextNodes.add(node);
	}
	
	public boolean removeNext(ResearchNode node) {
		return nextNodes.remove(node);
	}

	public boolean isUnlocked() {
		return isUnlocked;
	}

	public void setUnlocked(boolean isUnlocked) {
		this.isUnlocked = isUnlocked;
	}

	public String[] getResearchIds() {
		return researchIds;
	}

	public int getResearchPointCost() {
		return researchPointCost;
	}

	public PlayerConstructionManager getConstructManager() {
		return constructManager;
	}

	public ResearchManager getResearchManager() {
		return researchManager;
	}

	public HashSet<ResearchNode> getNextNodes() {
		return nextNodes;
	}

	public String getResearchName() {
		return researchName;
	}
}
