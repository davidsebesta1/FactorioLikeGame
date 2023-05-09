package engine.rendering.optimalization;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

import engine.sprites.Background;
import engine.sprites.Sprite;
import engine.sprites.entities.player.Player;
import math.Vector2;

public class ChunkManager {
	public static final int CHUNK_SIZE = 256;
	private Chunk[][] chunkMap;

	private ConcurrentHashMap<Chunk, Boolean> updateQueue;

	private HashSet<Chunk> activeChunks;

	private Player player;

	public ChunkManager(Vector2 mapSize, Player player) {
		this.chunkMap = new Chunk[(int) (mapSize.getX() / CHUNK_SIZE)][(int) (mapSize.getY() / CHUNK_SIZE)];
		this.player = player;
		this.activeChunks = new HashSet<>();
		this.updateQueue = new ConcurrentHashMap<>();

		for (int i = 0; i < chunkMap.length; i++) {
			for (int j = 0; j < chunkMap[i].length; j++) {
				chunkMap[i][j] = new Chunk(new Vector2(i * CHUNK_SIZE, j * CHUNK_SIZE), new Vector2(CHUNK_SIZE, CHUNK_SIZE));
			}
		}
	}

	public void initialAssigning(ArrayList<Sprite> list) {
		for (Sprite sprite : list) {
			if (sprite instanceof Background || sprite instanceof Player) continue;
			assignChunk(sprite);
		}

		for (int i = 0; i < chunkMap.length; i++) {
			for (int j = 0; j < chunkMap[i].length; j++) {
				chunkMap[i][j].cacheImage();
			}
		}
	}

	public void addChunkToUpdateQueue(Chunk chunk) {
		updateQueue.put(chunk, true);
	}

	public void runUpdateQueue() {
//		for (Chunk chunk : updateQueue.keySet()) {
//			chunk.cacheImage();
//		}
		
		updateQueue.keySet().parallelStream().forEach(Chunk::cacheImage);
		updateQueue.clear();
	}

	public void resolveAll() {
		for (int i = 0; i < chunkMap.length; i++) {
			for (int j = 0; j < chunkMap[i].length; j++) {
				if(chunkMap[i][j].needsResolve()) chunkMap[i][j].resolveFrame();
			}
		}
	}


//	public void updateSpriteChunk(Sprite sprite) {
//		if (sprite == null || sprite instanceof Background || sprite instanceof Player) return;
//		for (int i = 0; i < chunkMap.length; i++) {
//			for (int j = 0; j < chunkMap[i].length; j++) {
//				if (chunkMap[i][j].tryRemoveSprite(sprite)) {
//					assignChunk(sprite);
//					addChunkToUpdateQueue(chunkMap[i][j]);
//				}
//			}
//		}
//	}
	
	public void updateSpriteChunk(Sprite sprite) {
	    if (sprite == null || sprite instanceof Background || sprite instanceof Player) return;
	    
	    // Calculate the range of chunks that the sprite might be present in
	    int chunkSize = (int) getChunkSize();
	    int startX = Math.max(0, (int) ((sprite.getLocation().getX() - sprite.getSize().getX()) / chunkSize));
	    int startY = Math.max(0, (int) ((sprite.getLocation().getY() - sprite.getSize().getY()) / chunkSize));
	    int endX = Math.min(chunkMap.length - 1, (int) ((sprite.getLocation().getX() + sprite.getSize().getX()) / chunkSize));
	    int endY = Math.min(chunkMap[0].length - 1, (int) ((sprite.getLocation().getY() + sprite.getSize().getY()) / chunkSize));
	    
	    // Update the chunks in parallel
	    IntStream.rangeClosed(startX, endX)
	        .parallel()
	        .forEach(i -> IntStream.rangeClosed(startY, endY)
	            .parallel()
	            .forEach(j -> {
	                if (chunkMap[i][j].tryRemoveSprite(sprite)) {
	                    assignChunk(sprite);
	                    addChunkToUpdateQueue(chunkMap[i][j]);
	                }
	            })
	        );
	}

	public void addSpriteToChunks(Sprite sprite) {
		if (sprite instanceof Background || sprite instanceof Player)
			return;
		assignChunk(sprite);
	}

	public void assignChunk(Sprite sprite) {
		if(sprite == null) return;
//		for (int i = 0; i < chunkMap.length; i++) {
//			for (int j = 0; j < chunkMap[i].length; j++) {
//				if (sprite instanceof Background || sprite instanceof Player) continue;
//				if (chunkMap[i][j].isWithinChunk(sprite)) {
//					chunkMap[i][j].tryAddSprite(sprite);
//					addChunkToUpdateQueue(chunkMap[i][j]);
//					return;
//				}
//			}
//		}
		
		if (sprite instanceof Background || sprite instanceof Player) return;
		if(isInBounds((int) (sprite.getLocation().getX()/CHUNK_SIZE), (int) (sprite.getLocation().getY()/CHUNK_SIZE))) {
			Chunk c = chunkMap[(int) (sprite.getLocation().getX()/CHUNK_SIZE)][(int) (sprite.getLocation().getY()/CHUNK_SIZE)];
			c.tryAddSprite(sprite);
			sprite.setCurrentChunk(c);
			addChunkToUpdateQueue(c);
			
//			if(sprite instanceof Item) {
//				fixBetweenChunkSprite(sprite);
//			}

		}
	}

	public Chunk getChunkBySprite(Sprite sprite) {
		if (sprite != null) {
			return sprite.getCurrentChunk();
		}

		return null;
	}

	public void fixBetweenChunkSprite(Sprite sprite) {
		if(sprite != null) {
			Chunk chunk = sprite.getCurrentChunk();

//			HashMap<Chunk, Rectangle> chunksAndRect = new HashMap<>();
//			for (Chunk c : surroundingChunks(chunk)) {
//				chunksAndRect.put(c, c.getRectangle());
//			}
//
//			//chunk if sprite bounds rect and chunk bounds rect collide, if they do, add him forcefully to the chunk rendering
//			for (Map.Entry<Chunk, Rectangle> entry : chunksAndRect.entrySet()) {
//				Chunk ch = entry.getKey();
//				Rectangle re = entry.getValue();
//
//				if (re.intersects(rect)) {
//					ch.tryAddSprite(sprite);
//					addChunkToUpdateQueue(ch);
//				}
//			}

			Vector2 secondPoint = sprite.getLocation().add(sprite.getSize());
			if(isInBounds((int) (secondPoint.getX()/CHUNK_SIZE), (int) (secondPoint.getY()/CHUNK_SIZE))) {
				Chunk chunk2 = chunkMap[(int) (secondPoint.getX()/CHUNK_SIZE)][(int) (secondPoint.getY()/CHUNK_SIZE)];
				if(!chunk.equals(chunk2)) {
					chunk2.tryAddSprite(sprite);
					addChunkToUpdateQueue(chunk2);
				}
			}
		}
	}

	public ArrayList<Chunk> surroundingChunks(Chunk chunk) {
//		ArrayList<Chunk> toReturn = new ArrayList<>();
//		Vector2 currentChunkCoordinate = getChunkMapCoordinate(chunk);
//		for (int i = (int) currentChunkCoordinate.getX(); i < (int) (currentChunkCoordinate.getX() + 2); i++) {
//			for (int j = (int) currentChunkCoordinate.getY(); j < (int) (currentChunkCoordinate.getY() + 2); j++) {
//				if (isInBounds(j, i) && chunkMap[i][j] != chunk)
//					toReturn.add(chunkMap[i][j]);
//			}
//		}
//
//		return toReturn;
		
		 ArrayList<Chunk> toReturn = new ArrayList<>();
		    int x = (int) getChunkMapCoordinate(chunk).getX() / 256;
		    int y = (int) getChunkMapCoordinate(chunk).getY() / 256;
		    if (x > 0) toReturn.add(chunkMap[x-1][y]);
		    if (x < chunkMap.length-1) toReturn.add(chunkMap[x+1][y]);
		    if (y > 0) toReturn.add(chunkMap[x][y-1]);
		    if (y < chunkMap[0].length-1) toReturn.add(chunkMap[x][y+1]);
		    return toReturn;
	}

	public Vector2 getChunkMapCoordinate(Chunk chunk) {
		return chunk.getLocation().div(CHUNK_SIZE);
	}

	public void forceAddSpriteToChunk(Chunk chunk, Sprite spite) {
		chunk.tryAddSprite(spite);
	}

	public synchronized void forceRemoveSprite(Sprite sprite) {
		for (int i = 0; i < chunkMap.length; i++) {
			for (int j = 0; j < chunkMap[i].length; j++) {
				if (chunkMap[i][j].tryRemoveSprite(sprite)) {
					addChunkToUpdateQueue(chunkMap[i][j]);
				}
			}
		}
	}

	public void assignChunk(Set<Sprite> list) {
		for (Sprite sprite : list) {
			if (sprite instanceof Background || sprite instanceof Player)
				continue;
			assignChunk(sprite);
		}
	}

	public void updateActiveChunks() {
//		activeChunks.clear();
//
//		for (int i = 0; i < chunkMap.length; i++) {
//			for (int j = 0; j < chunkMap[i].length; j++) {
//				if (chunkMap[i][j].getCenterLocation().sub(player.getLocation()).magnitude() <= 1300) activeChunks.add(chunkMap[i][j]);
//			}
//		}
		
		// Calculate the chunk coordinates within the active range of the player
		int playerChunkX = (int) Math.floor(player.getLocation().getX() / CHUNK_SIZE);
		int playerChunkY = (int) Math.floor(player.getLocation().getY() / CHUNK_SIZE);
		int chunkRange = 5; // Number of chunks to consider in each direction from the player

		int minX = Math.max(0, playerChunkX - chunkRange);
		int maxX = Math.min(chunkMap.length - 1, playerChunkX + chunkRange);
		int minY = Math.max(0, playerChunkY - chunkRange);
		int maxY = Math.min(chunkMap[0].length - 1, playerChunkY + chunkRange);

		// Retrieve the active chunks from the chunkMap
		activeChunks.clear();
		for (int i = minX; i <= maxX; i++) {
			for (int j = minY; j <= maxY; j++) {
				activeChunks.add(chunkMap[i][j]);
			}
		}

	}

	public float getChunkSize() {
		return CHUNK_SIZE;
	}

	public Chunk[][] getChunkMap() {
		return chunkMap;
	}

	public HashSet<Chunk> getActiveChunks() {
		return activeChunks;
	}

	public boolean isInBounds(int x, int y) {
		return x >= 0 && y >= 0 && x < chunkMap.length && y < chunkMap[x].length;
	}

}
