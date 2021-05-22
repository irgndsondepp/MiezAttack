package com.irgndsondepp.clone.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * create a spritesheet from a png file. a spritesheet has a width and height, a
 * size (if it is squared, otherwise size=-1) and an integer array pixels with
 * the hexcode color values extracted from the graphic.
 * 
 * @author Robert
 *
 */
public class SpriteSheet {

	private String path;
	public final int SIZE;
	public final int WIDTH, HEIGHT;
	public int[] pixels;

	public static SpriteSheet tiles = new SpriteSheet(
			"/textures/spritesheets/spritesheet.png", 256);
	public static SpriteSheet spawn_level = new SpriteSheet(
			"/textures/spritesheets/spawn_level.png", 256);
	public static SpriteSheet projectiles = new SpriteSheet(
			"/textures/spritesheets/projectiles/projectiles.png", 48);

	// Cats
	public static SpriteSheet cat = new SpriteSheet(
			"/textures/characters/cat.png", 80, 48);
	public static SpriteSheet cat_down = new SpriteSheet(cat, 2, 0, 1, 3, 16);
	public static SpriteSheet cat_up = new SpriteSheet(cat, 0, 0, 1, 3, 16);
	public static SpriteSheet cat_left = new SpriteSheet(cat, 4, 0, 1, 3, 16);
	public static SpriteSheet cat_right = new SpriteSheet(cat, 1, 0, 1, 3, 16);
	public static SpriteSheet cat_idle = new SpriteSheet(cat, 3, 0, 1, 3, 16);

	public static SpriteSheet burning_cat = new SpriteSheet(
			"/textures/characters/burning_cat.png", 64, 48);
	public static SpriteSheet burning_cat_down = new SpriteSheet(burning_cat,
			2, 0, 1, 3, 16);
	public static SpriteSheet burning_cat_up = new SpriteSheet(burning_cat, 0,
			0, 1, 3, 16);
	public static SpriteSheet burning_cat_left = new SpriteSheet(burning_cat,
			3, 0, 1, 3, 16);
	public static SpriteSheet burning_cat_right = new SpriteSheet(burning_cat,
			1, 0, 1, 3, 16);

	public static SpriteSheet cathulhu = new SpriteSheet(
			"/textures/characters/cathulhu_new.png", 128, 96);
	public static SpriteSheet cathulhu_down = new SpriteSheet(cathulhu, 2, 0,
			1, 3, 32);
	public static SpriteSheet cathulhu_up = new SpriteSheet(cathulhu, 0, 0, 1,
			3, 32);
	public static SpriteSheet cathulhu_left = new SpriteSheet(cathulhu, 3, 0,
			1, 3, 32);
	public static SpriteSheet cathulhu_right = new SpriteSheet(cathulhu, 1, 0,
			1, 3, 32);

	// Player animations
	public static SpriteSheet player = new SpriteSheet(
			"/textures/characters/player.png", 160, 96);
	public static SpriteSheet player_down = new SpriteSheet(player, 2, 0, 1, 3,
			32);
	public static SpriteSheet player_up = new SpriteSheet(player, 0, 0, 1, 3,
			32);
	public static SpriteSheet player_left = new SpriteSheet(player, 3, 0, 1, 3,
			32);
	public static SpriteSheet player_right = new SpriteSheet(player, 1, 0, 1,
			3, 32);
	public static SpriteSheet player_death = new SpriteSheet(player, 4, 0, 1,
			3, 32);

	public static SpriteSheet player_action = new SpriteSheet(
			"/textures/characters/player_action.png", 128, 96);
	public static SpriteSheet player_down_action = new SpriteSheet(
			player_action, 2, 0, 1, 3, 32);
	public static SpriteSheet player_up_action = new SpriteSheet(player_action,
			0, 0, 1, 3, 32);
	public static SpriteSheet player_left_action = new SpriteSheet(
			player_action, 3, 0, 1, 3, 32);
	public static SpriteSheet player_right_action = new SpriteSheet(
			player_action, 1, 0, 1, 3, 32);

	public static SpriteSheet player_gun = new SpriteSheet(
			"/textures/characters/player_gun.png", 128, 96);
	public static SpriteSheet player_down_gun = new SpriteSheet(player_gun, 2,
			0, 1, 3, 32);
	public static SpriteSheet player_up_gun = new SpriteSheet(player_gun, 0, 0,
			1, 3, 32);
	public static SpriteSheet player_left_gun = new SpriteSheet(player_gun, 3,
			0, 1, 3, 32);
	public static SpriteSheet player_right_gun = new SpriteSheet(player_gun, 1,
			0, 1, 3, 32);

	// Game Over Screen

	public static SpriteSheet mauled = new SpriteSheet(
			"/textures/spritesheets/mauled.png", 300, 168);
	public static SpriteSheet mauledBig = new SpriteSheet(
			"/textures/spritesheets/mauledBig.png", 600, 338);

	// Guns & Items
	public static SpriteSheet items = new SpriteSheet(
			"/textures/spritesheets/items.png", 80, 16);
	public static SpriteSheet guns = new SpriteSheet(
			"/textures/spritesheets/guns.png", 80, 16);

	// Explosion
	public static SpriteSheet explosionComplete = new SpriteSheet(
			"/textures/spritesheets/projectiles/explosion.png", 32, 96);
	public static SpriteSheet explosion = new SpriteSheet(explosionComplete, 0,
			0, 1, 3, 32);

	// Menu
	public static SpriteSheet cursors = new SpriteSheet("/menu/cursors.png",
			96, 32);
	public static SpriteSheet cursors1 = new SpriteSheet("/menu/cursors1.png",
			48, 16);
	public static SpriteSheet mainMenuBackground = new SpriteSheet(
			"/menu/mainMenuBackground.png", 300, 168);
	public static SpriteSheet mainMenuBackgroundBig = new SpriteSheet(
			"/menu/mainMenuBackgroundBig.png", 600, 338);

	public static SpriteSheet subMenuBackgroundBig = new SpriteSheet(
			"/menu/subMenuBackgroundBig.png", 600, 338);
	public static SpriteSheet subMenuBackground = new SpriteSheet(
			"/menu/subMenuBackground.png", 300, 168);

	private Sprite[] sprites;

	/**
	 * create a sub-spritesheet from an existing spritesheet. the pixel arrays
	 * are copied to another spritesheet.
	 * 
	 * @param sheet
	 *            is the existing spritesheet
	 * @param x
	 *            is the x value in sprites
	 * @param y
	 *            is the y value in sprites
	 * @param width
	 *            is the width of the new spritesheet in pixels
	 * @param height
	 *            is the height of the new spritesheet in pixels
	 * @param spriteSize
	 *            is the size of the sprites
	 */
	public SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height,
			int spriteSize) {
		int xx = x * spriteSize;
		int yy = y * spriteSize;
		int w = width * spriteSize;
		int h = height * spriteSize;
		if (width == height)
			SIZE = width;
		else
			SIZE = -1;
		WIDTH = w;
		HEIGHT = h;
		pixels = new int[w * h];
		for (int y0 = 0; y0 < h; y0++) {
			int yp = yy + y0;
			for (int x0 = 0; x0 < w; x0++) {
				int xp = xx + x0;
				pixels[x0 + y0 * w] = sheet.pixels[xp + yp * sheet.WIDTH];

			}
		}

		int frame = 0;

		sprites = new Sprite[width * height];

		for (int ya = 0; ya < height; ya++) {
			for (int xa = 0; xa < width; xa++) {
				int[] spritePixels = new int[spriteSize * spriteSize];
				for (int y0 = 0; y0 < spriteSize; y0++) {
					for (int x0 = 0; x0 < spriteSize; x0++) {
						// System.err.println(spritePixels.length + ", " +
						// pixels.length);
						spritePixels[x0 + y0 * spriteSize] = pixels[(x0 + xa
								* spriteSize)
								+ (y0 + ya * spriteSize) * WIDTH];

					}
				}
				Sprite sprite = new Sprite(spritePixels, spriteSize, spriteSize);
				sprites[frame] = sprite;
				frame++;
			}
		}

	}

	/**
	 * create a new squared spritesheet.
	 * 
	 * @param path
	 *            is the path to the image file
	 * @param size
	 *            is the width and height of the iamge file
	 */
	public SpriteSheet(String path, int size) {
		this.path = path;
		this.SIZE = size;
		WIDTH = size;
		HEIGHT = size;
		pixels = new int[size * size];
		loadImage();
	}

	/**
	 * create a new rectangular spritesheet.
	 * 
	 * @param path
	 *            is the path to the image file
	 * @param width
	 *            is the width in pixels
	 * @param height
	 *            is the height in pixels
	 */
	public SpriteSheet(String path, int width, int height) {
		this.path = path;
		SIZE = -1;
		WIDTH = width;
		HEIGHT = height;
		pixels = new int[WIDTH * HEIGHT];
		loadImage();
	}

	/**
	 * returns the sprites from a sub-spritesheet
	 * 
	 * @return
	 */
	public Sprite[] getSprites() {
		return sprites;
	}

	/**
	 * fill the pixel array with the colors from the graphic
	 */
	private void loadImage() {
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class
					.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * returns the pixel array
	 * 
	 * @return
	 */
	public int[] getPixels() {
		return pixels;
	}

	/**
	 * returns a single pixel at the position (x,y) in the spritesheet
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public int getPixel(int x, int y) {
		return pixels[y * WIDTH + x];
	}

}
