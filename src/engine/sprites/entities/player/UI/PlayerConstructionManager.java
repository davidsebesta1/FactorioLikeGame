package engine.sprites.entities.player.UI;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import engine.Game;
import engine.rendering.textures.TextureLibrary;
import math.Vector2;

public class PlayerConstructionManager {

	private HashMap<StructureTypeButton, HashSet<StructureButton>> categoryAndAvailableStructures;
	private ArrayList<Vector2> rightRectangleSlotLocations;

	private HashSet<StructureButton> currentlyShowStructButtons;

	private Vector2 location;

	private Color backgroundInvColor = new Color(120, 120, 120, 200);

	public PlayerConstructionManager(Vector2 location) {
		super();
		this.location = location;
		this.categoryAndAvailableStructures = new HashMap<>();
		this.rightRectangleSlotLocations = new ArrayList<>();
		this.currentlyShowStructButtons = new HashSet<>();

		initializeRightRectangleLocations();
		initializeLeftRectangleButtons();
		
		test( getStructTypeButtonByIdentifier("TransportStructures"));

	}

	public void test(StructureTypeButton button) {
		if(button != null) {
			categoryAndAvailableStructures.get(button).add(new StructureButton("basicDrill", new Vector2(0,0), new Vector2(32, 32), this, TextureLibrary.retrieveTexture("testStructIcon")));
		}
	}

	private void initializeLeftRectangleButtons() {
		//LEFT RECTANGLE
		Vector2 loc = new Vector2(0, Game.getInstance().getResolution().getY());

		int upperRectHeight = (int) (256.0 / 5.0);

		// draw bottom rectangles
		int bottomRectHeight = (int) (256.0 * 0.98 - upperRectHeight);
		int bottomRectY = (int) loc.getY() - 254 + upperRectHeight + 2;

		int pointX = (int) loc.getX() + 9;
		int pointY = bottomRectY + 7;
		int pointSpacing = 56;

		ArrayList<Rectangle> tempRect = new ArrayList<>();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				tempRect.add(new Rectangle(pointX, pointY, 0, 0));

				pointX += pointSpacing;
			}
			pointY += pointSpacing;
			pointX = (int) loc.getX() + 9;
		}

		for (int i = 0; i < tempRect.size(); i++) {
			switch (i) {
			case 0:
				categoryAndAvailableStructures.put(new StructureTypeButton("CommmandStructures", new Vector2(tempRect.get(i).getX(), tempRect.get(i).getY()), new Vector2(32, 32), this, TextureLibrary.retrieveTexture("testTypeIcon")), new HashSet<>());
				break;
			case 1:
				categoryAndAvailableStructures.put(new StructureTypeButton("TransportStructures", new Vector2(tempRect.get(i).getX(), tempRect.get(i).getY()), new Vector2(32, 32), this, TextureLibrary.retrieveTexture("testTypeIcon")), new HashSet<>());
				break;
			case 2:
				categoryAndAvailableStructures.put(new StructureTypeButton("MiningStructures", new Vector2(tempRect.get(i).getX(), tempRect.get(i).getY()), new Vector2(32, 32), this, TextureLibrary.retrieveTexture("testTypeIcon")), new HashSet<>());
				break;
			case 3:
				categoryAndAvailableStructures.put(new StructureTypeButton("FactoryStructures", new Vector2(tempRect.get(i).getX(), tempRect.get(i).getY()), new Vector2(32, 32), this, TextureLibrary.retrieveTexture("testTypeIcon")), new HashSet<>());
				break;
			case 4:
				categoryAndAvailableStructures.put(new StructureTypeButton("PowerStructures", new Vector2(tempRect.get(i).getX(), tempRect.get(i).getY()), new Vector2(32, 32), this, TextureLibrary.retrieveTexture("testTypeIcon")), new HashSet<>());
				break;
			case 5:
				categoryAndAvailableStructures.put(new StructureTypeButton("OtherStructures", new Vector2(tempRect.get(i).getX(), tempRect.get(i).getY()), new Vector2(32, 32), this, TextureLibrary.retrieveTexture("testTypeIcon")), new HashSet<>());
				break;
			}
		}
		
		
	}

	public StructureTypeButton getStructTypeButtonByIdentifier(String identifier) {
		for (StructureTypeButton btn : categoryAndAvailableStructures.keySet()) {
			if (btn.getType().equals(identifier))
				return btn;
		}

		return null;
	}

	private void initializeRightRectangleLocations() {
		//RIGHT RECTANGLE
		Vector2 loc2 = new Vector2(0, Game.getInstance().getResolution().getY());

		int upperRectHeight2 = (int) (256.0 / 5.0);

		// draw bottom rectangles
		int bottomRectHeight2 = (int) (256.0 * 0.98 - upperRectHeight2);
		int bottomRectY2 = (int) loc2.getY() - 254 + upperRectHeight2 + 2;

		int pointX2 = (int) loc2.getX() + 120;
		int pointY2 = bottomRectY2 + 7;
		int pointSpacing2 = 56;

		ArrayList<Rectangle> tempRect2 = new ArrayList<>();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				rightRectangleSlotLocations.add(new Vector2(pointX2, pointY2));

				pointX2 += pointSpacing2;
			}
			pointY2 += pointSpacing2;
			pointX2 = (int) loc2.getX() + 120;
		}
	}

	public void paint(Graphics2D g2d) {
		Vector2 loc = new Vector2(0, Game.getInstance().getResolution().getY());

		// draw background rectangle
		g2d.setColor(backgroundInvColor);
		g2d.fillRect((int) loc.getX(), (int) loc.getY() - 256, 216, 256);

		// draw upper rectangle
		int upperRectHeight = (int) (256.0 / 5.0);
		g2d.setColor(Color.GRAY);
		g2d.fillRect((int) loc.getX() + 2, (int) loc.getY() - 254, 212, upperRectHeight);

		// draw bottom rectangles
		int bottomRectHeight = (int) (256.0 * 0.98 - upperRectHeight);
		int bottomRectY = (int) loc.getY() - 254 + upperRectHeight + 2;
		g2d.setColor(Color.DARK_GRAY);
		g2d.fillRect((int) loc.getX() + 2, bottomRectY, 105, bottomRectHeight);
		g2d.fillRect((int) loc.getX() + 109, bottomRectY, 105, bottomRectHeight);

		// draw points for images

		for (Map.Entry<StructureTypeButton, HashSet<StructureButton>> entry : categoryAndAvailableStructures.entrySet()) {
			StructureTypeButton button = entry.getKey();
			HashSet<StructureButton> availableStructures = entry.getValue();
			g2d.drawImage(button.getTexture().getImage(), (int) button.getLocation().getX(), (int) button.getLocation().getY(), null);
			for (StructureButton structure : availableStructures) {
				if (structure.isVisible())
					g2d.drawImage(structure.getTexture().getImage(), (int) structure.getLocation().getX(), (int) structure.getLocation().getY(), null);
			}
		}
	}

	public boolean showCorrespondingStructButton(StructureTypeButton button) {
		if (button != null) {
			HashSet<StructureButton> toShow = categoryAndAvailableStructures.get(button);

			for (StructureButton structure : currentlyShowStructButtons) {
				structure.setVisible(false);
			}

			currentlyShowStructButtons.clear();

			if (!toShow.isEmpty()) {
				int index = 0;
				for (StructureButton structure : toShow) {
					structure.setVisible(true);
					structure.setLocation(rightRectangleSlotLocations.get(index));
					currentlyShowStructButtons.add(structure);
					index++;
				}

				return true;
			}

			return false;
		}
		return false;
	}

	public Vector2 getLocation() {
		return location;
	}

	public void setLocation(Vector2 location) {
		this.location = location;
	}

	public HashMap<StructureTypeButton, HashSet<StructureButton>> getCategoryAndAvailableStructures() {
		return categoryAndAvailableStructures;
	}

	public Color getBackgroundInvColor() {
		return backgroundInvColor;
	}

}
