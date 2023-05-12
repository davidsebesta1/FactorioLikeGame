package engine.world.mainmenu.quitmenu;

import engine.rendering.textures.Texture;
import engine.world.mainmenu.Button;
import engine.world.mainmenu.MainMenuButtonManager;
import math.Vector2;

public class FinalizeQuitButton extends Button {

	public FinalizeQuitButton(MainMenuButtonManager manager, Vector2 location, Vector2 size, Texture texture) {
		super(manager, location, size, texture);
	}

	public FinalizeQuitButton(MainMenuButtonManager manager, Vector2 location, Vector2 size) {
		super(manager, location, size);
	}

	public FinalizeQuitButton(MainMenuButtonManager manager, Vector2 location, Vector2 size, String text) {
		super(manager, location, size, text);
	}
	
	@Override
	public void mousePrimaryPressed(Vector2 screenCoordinate) {
		System.exit(0);
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
		if (!(obj instanceof FinalizeQuitButton)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "FinalizeQuitButton []";
	}
	
	
	
	

}
