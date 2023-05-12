package engine.world.mainmenu;

import engine.rendering.textures.TextureLibrary;
import engine.world.World;
import math.Vector2;

public class MainMenuWorld extends World {

	public MainMenuWorld(Vector2 size) {
		super(size);
		
		this.player.setTexture(TextureLibrary.retrieveTexture("blank"));
		this.player.setBuildingModeEnabled(false);
		this.player.setShowBuildMenu(false);
	}

}
