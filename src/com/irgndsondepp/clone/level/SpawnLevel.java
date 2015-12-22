package com.irgndsondepp.clone.level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.irgndsondepp.clone.Game;
import com.irgndsondepp.clone.entity.mob.Chaser;
import com.irgndsondepp.clone.entity.mob.Dummy;
import com.irgndsondepp.clone.level.tile.Tile;

/**
 * creates a SpawnLevel
 * 
 * @author Robert
 *
 */
public class SpawnLevel extends Level {

	/**
	 * creates a SpawnLevel loaded from a *.png image file
	 * 
	 * @param path
	 *            path to *.png file as string
	 * @param game
	 *            the game object
	 */
	public SpawnLevel(String path, Game game) {

		super(path, game);
	}

	/**
	 * loads the level image stored in a *.png file and adds enemies
	 */
	protected void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(SpawnLevel.class
					.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			this.width = w;
			this.height = h;
			tiles = new int[w * h];
			image.getRGB(0, 0, w, h, tiles, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Exception! Could not load SpawnLevel File!");
		}

		this.tile_size = this.getTile(0, 0).size;

		// add Dummys
		for (int i = 0; i < (int) (0.9 * game.startingMiezen); i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			while (this.getTile(x, y).solid()) {
				x = random.nextInt(width);
				y = random.nextInt(height);
			}
			add(new Dummy(x, y));
		}

		// add chasers off screen from the player
		for (int i = 0; i < (int) (0.1 * game.startingMiezen); i++) {
			int px = 50 * tile_size;
			int py = 50 * tile_size;
			int x = random.nextInt(this.width);
			int y = random.nextInt(this.height);
			while ((getTile(x, y).solid() || ((x * tile_size >= px - Game.width)
					&& (x * tile_size <= px + Game.width)
					&& (y * tile_size >= py - Game.height) && (y * tile_size <= py
					+ Game.height)))) {
				x = random.nextInt(this.width);
				y = random.nextInt(this.height);
			}
			add(new Chaser(x * tile_size, y * tile_size));
		}

	}

	/**
	 * set the Tile at x,y position in level coordinates as destroyed if it is
	 * solid
	 * 
	 * @param x
	 *            is x coordinate
	 * @param y
	 *            is y coordinate
	 */
	public void destroyTile(int x, int y) {
		// check the color of the tile and if there is a solid object
		// destroy it
		int col = getTileAtPosition(x, y);
		if (col == Tile.col_spawn_rock) {
			setTileAtPosition(x, y, Tile.col_spawn_road);
			levelDestroyed++;
		}
		if (col == Tile.col_spawn_tree) {
			setTileAtPosition(x, y, Tile.col_spawn_grass);
			levelDestroyed++;
		}
	}
}
