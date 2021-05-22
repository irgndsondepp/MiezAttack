package com.irgndsondepp.clone.level.tile;

import com.irgndsondepp.clone.graphics.Screen;
import com.irgndsondepp.clone.graphics.Sprite;

public class VoidTile extends Tile {

	public VoidTile(Sprite sprite) {
		super(sprite);
	}

	public void render(int x, int y, Screen screen) {
		// removed because of jittering -> drawn as black, but still acts as
		// solid
		// screen.renderTile(x * size, y * size, this);
	}

	public boolean solid() {
		return true;
	}

}
