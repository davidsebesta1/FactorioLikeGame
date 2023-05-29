package engine.world.mainmenu.newworldmenu;

import engine.Game;
import engine.rendering.textures.Texture;
import engine.world.GameWorld;
import engine.world.mainmenu.Button;
import engine.world.mainmenu.MainMenuButtonManager;
import main.Log;
import math.Vector2;

/**
 * Button that has a functionality of creating and loading new world
 * @author David Šebesta
 * @see Button
 */
public class NewStandardGameButton extends Button {

	public NewStandardGameButton(MainMenuButtonManager manager, Vector2 location, Vector2 size, Texture texture) {
		super(manager, location, size, texture);
	}

	public NewStandardGameButton(MainMenuButtonManager manager, Vector2 location, Vector2 size) {
		super(manager, location, size);
	}

	public NewStandardGameButton(MainMenuButtonManager manager, Vector2 location, Vector2 size, String text) {
		super(manager, location, size, text);
	}
	
	@Override
	public void mousePrimaryPressed(Vector2 screenCoordinate) {
		Log.info("Created new world");
		Game.getInstance().setCurrentWorld(new GameWorld(new Vector2(4096d, 4096d)));
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
		if (!(obj instanceof NewStandardGameButton)) {
			return false;
		}
		return true;
	}
	
	

}
