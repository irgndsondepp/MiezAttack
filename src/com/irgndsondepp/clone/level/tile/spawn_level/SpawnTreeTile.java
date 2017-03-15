package com.irgndsondepp.clone.level.tile.spawn_level;

import com.irgndsondepp.clone.graphics.Sprite;
import com.irgndsondepp.clone.level.tile.Tile;

public class SpawnTreeTile extends Tile {
	public SpawnTreeTile(Sprite sprite) {
		super(sprite);
		isSolid = true;
		isBreakable = true;
	}	
}
