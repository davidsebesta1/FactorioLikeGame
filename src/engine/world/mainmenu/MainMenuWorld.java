package engine.world.mainmenu;

import engine.rendering.textures.TextureLibrary;
import engine.world.World;
import math.Vector2;

public class MainMenuWorld extends World {
	private static final long serialVersionUID = 4894625521169007370L;

	public MainMenuWorld(Vector2 size) {
		super(size);

		this.player.setTexture(TextureLibrary.retrieveTexture("blank"));
		this.player.setBuildingModeEnabled(false);
		this.player.setShowBuildMenu(false);
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
		if (!(obj instanceof MainMenuWorld)) {
			return false;
		}
		return true;
	}

	@Override
	public void SaveWorld() {
		throw new UnsupportedOperationException("Cannot save main menu world");
	}

}
