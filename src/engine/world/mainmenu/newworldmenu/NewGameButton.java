package engine.world.mainmenu.newworldmenu;

import engine.rendering.textures.Texture;
import engine.world.mainmenu.Button;
import engine.world.mainmenu.MainMenuButtonManager;
import main.Log;
import math.Vector2;

/**
 * Button that have usage of showing new game buttons menu
 * @author David Šebesta
 * @see Button
 */
public class NewGameButton extends Button {
	
	public NewGameButton(MainMenuButtonManager manager, Vector2 location, Vector2 size, String text) {
		super(manager, location, size, text);
	}

	public NewGameButton(MainMenuButtonManager manager, Vector2 location, Vector2 size, Texture texture) {
		super(manager, location, size, texture);
	}

	public NewGameButton(MainMenuButtonManager manager, Vector2 location, Vector2 size) {
		super(manager, location, size);
	}

	@Override
	public void mousePrimaryPressed(Vector2 screenCoordinate) {
		Log.info("Showed the new game menu");
		owner.showMenu(MainMenuButtonManager.MENU_NEWSAVE);
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
		if (!(obj instanceof NewGameButton)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "NewGameButton []";
	}
}
