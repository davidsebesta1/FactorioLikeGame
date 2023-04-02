package engine.world;

import java.io.File;

import engine.sprites.Sprite;
import engine.sprites.entities.Player;
import engine.sprites.objects.minable.Coal;
import engine.sprites.structures.CoreModule;
import engine.sprites.structures.StructureMap;
import engine.sprites.structures.conveyors.ConveyorBelt;
import engine.sprites.structures.conveyors.ConveyorBeltDirection;
import engine.sprites.tiles.TileMap;
import math.Vector2;

public class GameWorld {
	private Vector2 size;
	private TileMap tiles;
	private StructureMap structureMap;

	private Sprite background;

	private Player player;

	public GameWorld(Vector2 size) {
		this.size = size;
		this.tiles = new TileMap(10, 10);
		this.structureMap = new StructureMap(new Vector2(10,10));

		this.player = Player.instantiatePlayer(new File("rockground.png"));

		this.background = Sprite.instantiateSprite(new File("background.jpg"), new Vector2(0, 0), 0f);

		
		for(int i = 0; i < tiles.getSize().getX(); i++) {
			for(int j = 0; j < tiles.getSize().getY(); j++) {
				this.tiles.tryCreateAtLocation(i, j, new File("ground1.png"));
			}
		}
		
		
		structureMap.tryAddStructureAtLocation(CoreModule.instantiateCoreModule(new File("coreModule.png"), new Vector2(0, 0)), new Vector2(2, 2));
		ConveyorBelt belt = ConveyorBelt.instantiateConveyorBelt(new File("beltUP.png"), new Vector2(0, 0), ConveyorBeltDirection.UP);
		ConveyorBelt belt2 = ConveyorBelt.instantiateConveyorBelt(new File("beltUP.png"), new Vector2(0, 0), ConveyorBeltDirection.UP);
		ConveyorBelt belt3 = ConveyorBelt.instantiateConveyorBelt(new File("beltUP.png"), new Vector2(0, 0), ConveyorBeltDirection.UP);
		structureMap.tryAddStructureAtLocation(belt,new Vector2(5, 6));
		structureMap.tryAddStructureAtLocation(belt2,new Vector2(5, 7));
		structureMap.tryAddStructureAtLocation(belt3,new Vector2(5, 8));
		
		belt2.setNext(belt);
		belt3.setNext(belt2);
		
		belt3.setItem(Coal.instantiateCoal(new File("coal.png"), Vector2.zero));
		
	}

	public Sprite getBackground() {
		return background;
	}

	public void setBackground(Sprite background) {
		this.background = background;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Vector2 getSize() {
		return size;
	}

	public TileMap getTiles() {
		return tiles;
	}

	public StructureMap getStructureMap() {
		return structureMap;
	}
}
