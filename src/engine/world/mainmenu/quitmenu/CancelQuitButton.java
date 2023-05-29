package engine.world.mainmenu.quitmenu;

import engine.rendering.textures.Texture;
import engine.world.mainmenu.Button;
import engine.world.mainmenu.MainMenuButtonManager;
import main.Log;
import math.Vector2;

/**
 * Button that cancels the quit sequence and returns to the main menu
 * @author David Šebesta
 * @see Button
 *
 */
public class CancelQuitButton extends Button {

	public CancelQuitButton(MainMenuButtonManager manager, Vector2 location, Vector2 size, Texture texture) {
		super(manager, location, size, texture);
	}

	public CancelQuitButton(MainMenuButtonManager manager, Vector2 location, Vector2 size) {
		super(manager, location, size);
	}

	public CancelQuitButton(MainMenuButtonManager manager, Vector2 location, Vector2 size, String text) {
		super(manager, location, size, text);
	}
	
	@Override
	public void mousePrimaryPressed(Vector2 screenCoordinate) {
		owner.showMenu(MainMenuButtonManager.MENU_MAIN);
		Log.info("Player cancelled quit");
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
		if (!(obj instanceof CancelQuitButton)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "CancelQuitButton []";
	}
	
	

}
