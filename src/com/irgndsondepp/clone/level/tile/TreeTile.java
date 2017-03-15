package com.irgndsondepp.clone.level.tile;

import com.irgndsondepp.clone.graphics.Sprite;

public class TreeTile extends Tile {

	public TreeTile(Sprite sprite) {
		super(sprite);
		isSolid = true;
		isBreakable = true;
	}
}
