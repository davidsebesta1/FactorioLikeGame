package techTree;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class ResearchTree implements Serializable {
	private static final long serialVersionUID = -5423056034485389043L;
	
	private ResearchNode rootNode;

	public ResearchTree(ResearchNode startNode) {
		this.rootNode = startNode;
	}
	
	public void initialize() {
		
	}
	
	public ResearchNode findResearchNodeByName(String identifier) {
        if (rootNode == null) {
            return null;
        }

        Queue<ResearchNode> queue = new LinkedList<>();
        Set<ResearchNode> visited = new HashSet<>();

        queue.offer(rootNode);
        visited.add(rootNode);

        while (!queue.isEmpty()) {
            ResearchNode currNode = queue.poll();
            
            if(currNode.getResearchName().equals(identifier)) return currNode;
            for (ResearchNode nextNode : currNode.getNextNodes()) {
                if (!visited.contains(nextNode)) {
                    queue.offer(nextNode);
                    visited.add(nextNode);
                }
            }
        }
        
        return null;
	}
}
