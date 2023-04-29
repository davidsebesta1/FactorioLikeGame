package engine.sprites.entities.player.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import engine.Game;
import engine.input.IMouseActionEventListener;
import engine.input.InputManager;
import engine.rendering.textures.Texture;
import engine.rendering.textures.TextureLibrary;
import engine.sprites.entities.player.Inventory;
import engine.sprites.objects.Item;
import engine.sprites.structures.StructureSprite;
import engine.sprites.structures.command.CoreModule;
import math.Vector2;

public class PlayerConstructionManager implements IMouseActionEventListener {

	private HashMap<StructureTypeButton, HashSet<StructureButton>> categoryAndAvailableStructures;
	private ArrayList<Vector2> rightRectangleSlotLocations;

	private HashSet<StructureButton> currentlyShowStructButtons;

	private HashMap<String, StructureSprite> structureIdentifierTemplate;

	private Vector2 location;

	private Color backgroundInvColor = new Color(120, 120, 120, 200);

	private StructureButton currentlySelected = null;

	public PlayerConstructionManager(Vector2 location) {
		super();
		this.location = location;
		this.categoryAndAvailableStructures = new HashMap<>();
		this.rightRectangleSlotLocations = new ArrayList<>();
		this.currentlyShowStructButtons = new HashSet<>();
		this.structureIdentifierTemplate = new HashMap<>();

		InputManager.addMouseActionListener(this);

		initializeStructButtons();
		initializeStructTypeButtons();

		registerStructures();

		showCorrespondingStructButton(getStructTypeButtonByIdentifier("CommandStructures"));

	}

	private void registerStructures() {
		registerStructure("CommandStructures", "coreModule", TextureLibrary.retrieveTexture("testStructIcon"), CoreModule.instantiateCoreModule(TextureLibrary.retrieveTexture("coreModule"), Vector2.templateSpawn));
	}

	private void initializeStructTypeButtons() {
		//LEFT RECTANGLE
		Vector2 loc = new Vector2(0, Game.getInstance().getResolution().getY());

		int upperRectHeight = (int) (256.0 / 5.0);

		// draw bottom rectangles
		int bottomRectY = (int) loc.getY() - 254 + upperRectHeight + 2;

		int pointX = (int) loc.getX() + 3;
		int pointY = bottomRectY + 7;
		int pointSpacing = 35;

		ArrayList<Rectangle> tempRect = new ArrayList<>();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				tempRect.add(new Rectangle(pointX, pointY, 0, 0));

				pointX += pointSpacing;
			}
			pointY += pointSpacing * 1;
			pointX = (int) loc.getX() + 3;
		}

		for (int i = 0; i < tempRect.size(); i++) {
			switch (i) {
			case 0:
				categoryAndAvailableStructures.put(new StructureTypeButton("CommandStructures", new Vector2(tempRect.get(i).getX(), tempRect.get(i).getY()), new Vector2(32, 32), this, TextureLibrary.retrieveTexture("testTypeIcon")), new HashSet<>());
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

	private void initializeStructButtons() {
		//RIGHT RECTANGLE
		Vector2 loc2 = new Vector2(0, Game.getInstance().getResolution().getY());

		int upperRectHeight2 = (int) (256.0 / 5.0);

		// draw bottom rectangles
		int bottomRectY2 = (int) loc2.getY() - 254 + upperRectHeight2 + 2;

		int pointX2 = (int) loc2.getX() + 80;
		int pointY2 = bottomRectY2 + 7;
		int pointSpacing2 = 56;

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
		Vector2 loc = new Vector2(0, Game.getInstance().getResolution().getY() - 265);

		g2d.drawImage(TextureLibrary.retrieveTexture("inventoryBackground").getImage(), 0, (int) loc.getY(), null);

		for (Map.Entry<StructureTypeButton, HashSet<StructureButton>> entry : categoryAndAvailableStructures.entrySet()) {
			StructureTypeButton button = entry.getKey();
			HashSet<StructureButton> availableStructures = entry.getValue();
			g2d.drawImage(button.getTexture().getImage(), (int) button.getLocation().getX(), (int) button.getLocation().getY(), null);
			for (StructureButton structure : availableStructures) {
				if (structure.isVisible())
					g2d.drawImage(structure.getTexture().getImage(), (int) structure.getLocation().getX(), (int) structure.getLocation().getY(), null);
			}
		}

		if (currentlySelected != null) {
			Vector2 loc2 = new Vector2(20, loc.getY() + 23);
			Inventory inv = Game.getInstance().getCurrentWorld().getPlayer().getInventory();

			g2d.drawImage(currentlySelected.getTexture().getImage(), (int) loc2.getX(), (int) loc2.getY(), null);
			g2d.setFont(new Font("Eurostile", 1, 15));
			g2d.setColor(Color.white);
			g2d.drawString(currentlySelected.getDisplayName(), (int) loc2.getX() + 40, (int) loc2.getY() + 12);
			
			int resourceX = (int) loc2.getX() + 35;
			int resourceY =  (int) loc2.getY() + 10;
			for (Map.Entry<String, Integer> entry : currentlySelected.getResourceCost().entrySet()) {
				String itemID = entry.getKey();
				int amount = entry.getValue();
				g2d.drawImage(Item.getTextureByItemID(itemID).getImage(), resourceX, resourceY, null);
				g2d.drawString(inv.getItemAmount(itemID) + "/" + amount, resourceX + 30, resourceY + 20);
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
					System.out.println(structure);
				}

				return true;
			}

			return false;
		}
		return false;
	}

	public boolean registerStructure(String category, String name, Texture iconTexture, StructureSprite template) {
		StructureTypeButton categoryButton = getStructTypeButtonByIdentifier(category);
		if (categoryButton != null) {
			categoryAndAvailableStructures.get(categoryButton).add(new StructureButton(name, new Vector2(0, 0), new Vector2(32, 32), this, iconTexture, template));
			structureIdentifierTemplate.put(name, template);
		}
		return true;
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

	public StructureButton getCurrentlySelected() {
		return currentlySelected;
	}

	public void setCurrentlySelected(StructureButton currentlySelected) {
		this.currentlySelected = currentlySelected;

		System.out.println("Curr selected: " + currentlySelected);
	}

	@Override
	public void mousePrimaryPressed(Vector2 screenCoordinate) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePrimaryReleased(Vector2 screenCoordinate) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseSecondaryPressed(Vector2 screenCoordinate) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseSecondaryReleased(Vector2 screenCoordinate) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMiddlePressed(Vector2 screenCoordinate) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMiddleReleased(Vector2 screenCoordinate) {
		// TODO Auto-generated method stub

	}

}
