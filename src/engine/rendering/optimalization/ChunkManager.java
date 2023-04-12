package engine.rendering.optimalization;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import engine.sprites.Background;
import engine.sprites.Sprite;
import engine.sprites.entities.Player;
import math.Vector2;

public class ChunkManager {
	private float chunkSize;
	private Chunk[][] chunkMap;

	private HashSet<Chunk> updateQueue;

	private CopyOnWriteArrayList<Chunk> activeChunks;

	private Player player;

	public ChunkManager(float chunkSize, Vector2 mapSize, Player player) {
		this.chunkSize = chunkSize;
		this.chunkMap = new Chunk[(int) (mapSize.getX() / chunkSize)][(int) (mapSize.getY() / chunkSize)];
		this.player = player;
		this.activeChunks = new CopyOnWriteArrayList<>();
		this.updateQueue = new HashSet<>();

		for (int i = 0; i < chunkMap.length; i++) {
			for (int j = 0; j < chunkMap[i].length; j++) {
				chunkMap[i][j] = new Chunk(new Vector2(i * chunkSize, j * chunkSize),
						new Vector2(chunkSize, chunkSize));
			}
		}
	}

	public void initialAssigning(ArrayList<Sprite> list) {
		for (Sprite sprite : list) {
			if (sprite instanceof Background || sprite instanceof Player)
				continue;
			assignChunk(sprite);
		}

		for (int i = 0; i < chunkMap.length; i++) {
			for (int j = 0; j < chunkMap[i].length; j++) {
				chunkMap[i][j].cacheImage();
			}
		}
	}

	public boolean addChunkToUpdateQueue(Chunk chunk) {
		return updateQueue.add(chunk);
	}

	public void runUpdateQueue() {
		for (Chunk chunk : updateQueue) {
			chunk.sort();
			chunk.cacheImage();
		}

		updateQueue.clear();
	}

	public void updateSpriteChunk(Sprite sprite) {
		if (sprite instanceof Background || sprite instanceof Player)
			return;
		for (int i = 0; i < chunkMap.length; i++) {
			for (int j = 0; j < chunkMap[i].length; j++) {
				if (chunkMap[i][j].tryRemoveSprite(sprite)) {
					assignChunk(sprite);
					addChunkToUpdateQueue(chunkMap[i][j]);
					return;
				}
			}
		}

	}

	public void addSpriteToChunks(Sprite sprite) {
		if (sprite instanceof Background || sprite instanceof Player)
			return;
		assignChunk(sprite);
	}

	public void assignChunk(Sprite sprite) {
		for (int i = 0; i < chunkMap.length; i++) {
			for (int j = 0; j < chunkMap[i].length; j++) {
				if (sprite instanceof Background || sprite instanceof Player) continue;
				if (chunkMap[i][j].isWithinChunk(sprite)) {
					chunkMap[i][j].tryAddSprite(sprite);
					addChunkToUpdateQueue(chunkMap[i][j]);
					return;
				}
			}
		}
	}
	
	public Chunk getChunkBySprite(Sprite sprite) {
		if(sprite != null) {
			for (int i = 0; i < chunkMap.length; i++) {
				for (int j = 0; j < chunkMap[i].length; j++) {
					if (chunkMap[i][j].isWithinChunk(sprite)) {
						return chunkMap[i][j];
					}
				}
			}
		}
		
		return null;
	}
	
	public void fixBetweenChunkSprite(Sprite sprite) {
		Chunk chunk = getChunkBySprite(sprite);
		
		ArrayList<Rectangle> chunkRects = new ArrayList<>();
		
		for(Chunk c : surroundingChunks(chunk)) {
			chunkRects.add(c.getRectangle());
		}
		
		//chunk if sprite bounds rect and chunk bounds rect collide, if they do, add him forcefully to the chunk rendering
		
		
	}
	
	public ArrayList<Chunk> surroundingChunks(Chunk chunk){
		ArrayList<Chunk> toReturn = new ArrayList<>();
		Vector2 currentChunkCoordinate = getChunkMapCoordinate(chunk);
		for(int i = (int) currentChunkCoordinate.getX(); i < (int) (currentChunkCoordinate.getX() + 2); i++) {
			for(int j = (int) currentChunkCoordinate.getY(); j < (int)(currentChunkCoordinate.getY() + 2); j++) {
				if(isInBounds(j, i) && chunkMap[i][j] != chunk) toReturn.add(chunkMap[i][j]);
			}
		}
		
		return toReturn;
	}
	
	public Vector2 getChunkMapCoordinate(Chunk chunk) {
		return chunk.getLocation().div(chunkSize);
	}
	
	public void forceAddSpriteToChunk(Chunk chunk, Sprite spite) {
		chunk.tryAddSprite(spite);
	}

	public void assignChunk(List<Sprite> list) {
		for (Sprite sprite : list) {
			if (sprite instanceof Background || sprite instanceof Player) continue;
			assignChunk(sprite);
		}
	}

	public void updateActiveChunks() {
		activeChunks.clear();

		for (int i = 0; i < chunkMap.length; i++) {
			for (int j = 0; j < chunkMap[i].length; j++) {
//				if (chunkMap[i][j].getSprites().isEmpty()) continue;

//				System.out.println(i + "" + j + " mag " + (chunkMap[i][j].getLocation().sub(player.getLocation()).magnitude()) + " " + player.getLocation());
				if (chunkMap[i][j].getCenterLocation().sub(player.getLocation()).magnitude() <= 1024)
					activeChunks.add(chunkMap[i][j]);
			}
		}

	}

	public float getChunkSize() {
		return chunkSize;
	}

	public Chunk[][] getChunkMap() {
		return chunkMap;
	}

	public CopyOnWriteArrayList<Chunk> getActiveChunks() {
		return activeChunks;
	}
	
	public boolean isInBounds(int x, int y) {
		return x >= 0 && y >= 0 && x < chunkMap.length && y < chunkMap[x].length;
	}


}
