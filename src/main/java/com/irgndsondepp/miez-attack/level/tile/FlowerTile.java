package com.irgndsondepp.clone.level.tile;

import com.irgndsondepp.clone.graphics.Screen;
import com.irgndsondepp.clone.graphics.Sprite;

public class FlowerTile extends Tile {

	public FlowerTile(Sprite sprite) {
		super(sprite);
	}

	public void render(int x, int y, Screen screen) {
		// Tile wird an der entsprechenden Stelle auf den Bildschirm gemalt
		screen.renderTile(x << 4, y << 4, this);
	}

}
