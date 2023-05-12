package engine.world.mainmenu.loadworldmenu;

import engine.rendering.textures.Texture;
import engine.world.mainmenu.Button;
import engine.world.mainmenu.MainMenuButtonManager;
import math.Vector2;

public class LoadGameButton extends Button {

	public LoadGameButton(MainMenuButtonManager manager, Vector2 location, Vector2 size, Texture texture) {
		super(manager, location, size, texture);
	}

	public LoadGameButton(MainMenuButtonManager manager, Vector2 location, Vector2 size) {
		super(manager, location, size);
	}

	public LoadGameButton(MainMenuButtonManager manager, Vector2 location, Vector2 size, String text) {
		super(manager, location, size, text);
	}
	
	@Override
	public void mousePrimaryPressed(Vector2 screenCoordinate) {
		owner.showMenu(MainMenuButtonManager.MENU_LOADSAVE);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof LoadGameButton)) {
			return false;
		}
		return true;
	}
}
