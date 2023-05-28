package engine.rendering.optimalization;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

import engine.sprites.Background;
import engine.sprites.Sprite;
import engine.sprites.entities.player.Player;
import math.Vector2;

/**
 * Chunk manager is class responsible for handling chunk updates and their functionalities around player.
 * @author David Šebesta
 * @see Chunk
 */
public class ChunkManager {
	
	/**
	 * Chunk size in units (pixels pretty much)
	 */
	public static final int CHUNK_SIZE = 256;
	private Chunk[][] chunkMap;

	private ConcurrentHashMap<Chunk, Boolean> updateQueue;

	private HashSet<Chunk> activeChunks;

	private Player player;

	/**
	 * Class constructor
	 * @param mapSize
	 * @param player
	 */
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

	/**
	 * Resolves where sprites are located (in which chunk bounds) and then updates all chunks to they are drawn
	 * @param list to add
	 */
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

	/**
	 * Adds chunk to the update queue right before drawing frame to the screen
	 * @param chunk
	 */
	public void addChunkToUpdateQueue(Chunk chunk) {
		updateQueue.put(chunk, true);
	}

	/**
	 * Tries to update all chunks in update queue parallel
	 */
	public void runUpdateQueue() {
//		for (Chunk chunk : updateQueue.keySet()) {
//			chunk.cacheImage();
//		}
		
		updateQueue.keySet().parallelStream().forEach(Chunk::cacheImage);
		updateQueue.clear();
	}

	/**
	 * Resolves all chunk stuff (adding / removing sprites)
	 */
	public void resolveAll() {
		for (int i = 0; i < chunkMap.length; i++) {
			for (int j = 0; j < chunkMap[i].length; j++) {
				if(chunkMap[i][j].needsResolve()) chunkMap[i][j].resolveFrame();
			}
		}
	}
	
	/**
	 * Updates assigned chunk of a sprite based on its location. Background and player cannot be assigned.
	 * @param sprite
	 */
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

	/**
	 * Adds sprite to chunk based on its location. Background and player cannot be assigned.
	 * @param sprite
	 */
	public void addSpriteToChunks(Sprite sprite) {
		if (sprite instanceof Background || sprite instanceof Player)
			return;
		assignChunk(sprite);
	}

	/**
	 * Assigns chunk first
	 * @param sprite
	 */
	public void assignChunk(Sprite sprite) {
		if(sprite == null) return;
		
		if (sprite instanceof Background || sprite instanceof Player) return;
		if(isInBounds((int) (sprite.getLocation().getX()/CHUNK_SIZE), (int) (sprite.getLocation().getY()/CHUNK_SIZE))) {
			Chunk c = chunkMap[(int) (sprite.getLocation().getX()/CHUNK_SIZE)][(int) (sprite.getLocation().getY()/CHUNK_SIZE)];
			c.tryAddSprite(sprite);
			sprite.setCurrentChunk(c);
			addChunkToUpdateQueue(c);
		}
	}

	/**
	 * Returns chunk in which is sprite in paramaters
	 * @param sprite
	 * @return chunk, if sprite is not assigned to a chunk, returns null
	 */
	public Chunk getChunkBySprite(Sprite sprite) {
		if (sprite != null) {
			return sprite.getCurrentChunk();
		}

		return null;
	}

	/**
	 * Attempts to fix drawing a sprite that is on the edge of chunk
	 * @param sprite
	 */
	public void fixBetweenChunkSprite(Sprite sprite) {
		if(sprite != null) {
			Chunk chunk = sprite.getCurrentChunk();

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

	/**
	 * Returns surrounding chunks of a chunk specified
	 * @param chunk
	 * @return surrounding chunks
	 */
	public ArrayList<Chunk> surroundingChunks(Chunk chunk) {
		 ArrayList<Chunk> toReturn = new ArrayList<>();
		    int x = (int) getChunkMapCoordinate(chunk).getX() / 256;
		    int y = (int) getChunkMapCoordinate(chunk).getY() / 256;
		    if (x > 0) toReturn.add(chunkMap[x-1][y]);
		    if (x < chunkMap.length-1) toReturn.add(chunkMap[x+1][y]);
		    if (y > 0) toReturn.add(chunkMap[x][y-1]);
		    if (y < chunkMap[0].length-1) toReturn.add(chunkMap[x][y+1]);
		    return toReturn;
	}

	/**
	 * Returns 2d array coordinate of a chunk from world coordinates of a chunk
	 * @param chunk
	 * @return
	 */
	public Vector2 getChunkMapCoordinate(Chunk chunk) {
		return chunk.getLocation().div(CHUNK_SIZE);
	}

	/**
	 * Force add a sprite to a chunk, no matter if it is inside it or not
	 * @param chunk
	 * @param spite
	 */
	public void forceAddSpriteToChunk(Chunk chunk, Sprite spite) {
		chunk.tryAddSprite(spite);
	}

	/**
	 * Force remove sprite from a chunk
	 * @param sprite
	 */
	public synchronized void forceRemoveSprite(Sprite sprite) {
		for (int i = 0; i < chunkMap.length; i++) {
			for (int j = 0; j < chunkMap[i].length; j++) {
				if (chunkMap[i][j].tryRemoveSprite(sprite)) {
					addChunkToUpdateQueue(chunkMap[i][j]);
				}
			}
		}
	}

	/**
	 * Assign chunk to a sprites within provided hashset
	 * @param list
	 */
	public void assignChunk(HashSet<Sprite> list) {
		for (Sprite sprite : list) {
			if (sprite instanceof Background || sprite instanceof Player)
				continue;
			assignChunk(sprite);
		}
	}

	/**
	 * Updates which chunks are near to the player position and are rendered to a screen
	 */
	public void updateActiveChunks() {
		
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
