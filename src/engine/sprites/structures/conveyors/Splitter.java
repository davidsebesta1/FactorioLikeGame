package engine.sprites.structures.conveyors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

import engine.physics.CollisionLayers;
import engine.rendering.textures.Texture;
import engine.rendering.textures.TextureLibrary;
import engine.sprites.objects.Item;
import engine.sprites.structures.StructureSprite;
import engine.time.DeltaTime;
import math.Vector2;

/**
 * Splitter is a class that splits incoming items to outcoming belts equally.
 * @author David Šebesta
 *
 */
public class Splitter extends StructureSprite {
	private static final long serialVersionUID = -10749747737659406L;

	private int lastPointer = 0;
	private double time = 0;

	private Queue<Item> queuedItems;

	private static final double SPLIT_TIME = 0.1d;

	private Splitter(Texture texture, Vector2 location, double zDepth) {
		super(texture, location, zDepth);
		this.collisionLayer = CollisionLayers.STRUCTURE;

		this.queuedItems = new LinkedList<>();

		this.resourceCost = new HashMap<>();
		this.displayName = "Splitter";
		this.resourceCost.put("titaniumItem", 0);
	}

	public static Splitter instantiateSplitter(Texture texture, Vector2 location) {
		try {
			return new Splitter(texture, location, 0.7f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public StructureSprite createCopy(String[] args) {
		return instantiateSplitter(TextureLibrary.retrieveTexture("splitter"), Vector2.templateSpawn);
	}

	/**
	 * Function method
	 */
	@Override
	public void update() {
		time += DeltaTime.getDeltaTime();

		if (time >= SPLIT_TIME) {
			time = 0;
			ArrayList<ConveyorBelt> in = getIncomingBelts();

			for (ConveyorBelt belt : in) {
				if (belt.getItem() != null && !queuedItems.contains(belt.getItem())) {
					queuedItems.add(belt.getItem());
				}
			}

			Item item = queuedItems.poll();
			if (item != null) {
				ArrayList<ConveyorBelt> out = getOutcomingBelts();

				if (!out.isEmpty()) {
					if (lastPointer == out.size() - 1) {
						item.clearBeltReference();
						out.get(lastPointer).setItem(item);

						lastPointer = 0;
					} else {
						item.clearBeltReference();
						out.get(lastPointer).setItem(item);

						lastPointer++;
					}

				}
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 33;
		int result = super.hashCode();
		result = prime * result + Objects.hash(lastPointer);
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
		if (!(obj instanceof Splitter)) {
			return false;
		}
		Splitter other = (Splitter) obj;
		return lastPointer == other.lastPointer;
	}
}
