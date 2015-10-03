package com.irgndsondepp.clone.level.tile.spawn_level;

import com.irgndsondepp.clone.graphics.Screen;
import com.irgndsondepp.clone.graphics.Sprite;
import com.irgndsondepp.clone.level.tile.Tile;

public class SpawnRoadTile extends Tile {

	public SpawnRoadTile(Sprite sprite) {
		super(sprite);
		// TODO Auto-generated constructor stub
	}
	
	public void render(int x, int y, Screen screen) {
		// Tile wird an der entsprechenden Stelle auf den Bildschirm gemalt
		screen.renderTile(x << 4, y << 4, this);
	}

}
