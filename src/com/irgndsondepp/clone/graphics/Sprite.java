package com.irgndsondepp.clone.graphics;

/**
 * the sprite class. a sprite has an integer array pixels with the colors of the
 * sprite pixels, x and y values for its position in the spritesheet, the sheet
 * itself, width and height values and a size (which will be -1 if the sprite is
 * not square)
 * 
 * @author Robert
 *
 */
public class Sprite {

	public final int SIZE;
	public int[] pixels;
	private int x, y;
	private int width, height;
	protected SpriteSheet sheet;

	public static Sprite grass = new Sprite(16, 0, 0, SpriteSheet.tiles);
	public static Sprite rock = new Sprite(16, 1, 0, SpriteSheet.tiles);
	public static Sprite flower = new Sprite(16, 2, 0, SpriteSheet.tiles);
	public static Sprite tree = new Sprite(16, 3, 0, SpriteSheet.tiles);

	// Spawn_grass Sprites
	public static Sprite spawn_grass = new Sprite(16, 0, 0,
			SpriteSheet.spawn_level);
	public static Sprite spawn_grass_road_t = new Sprite(16, 0, 1,
			SpriteSheet.spawn_level);
	public static Sprite spawn_grass_road_tl = new Sprite(16, 0, 2,
			SpriteSheet.spawn_level);
	public static Sprite spawn_grass_road_l = new Sprite(16, 0, 3,
			SpriteSheet.spawn_level);
	public static Sprite spawn_grass_road_bl = new Sprite(16, 0, 4,
			SpriteSheet.spawn_level);
	public static Sprite spawn_grass_road_b = new Sprite(16, 0, 5,
			SpriteSheet.spawn_level);
	public static Sprite spawn_grass_road_br = new Sprite(16, 0, 6,
			SpriteSheet.spawn_level);
	public static Sprite spawn_grass_road_r = new Sprite(16, 0, 7,
			SpriteSheet.spawn_level);
	public static Sprite spawn_grass_road_tr = new Sprite(16, 0, 8,
			SpriteSheet.spawn_level);
	public static Sprite spawn_grass_road_tlr = new Sprite(16, 0, 9,
			SpriteSheet.spawn_level);
	public static Sprite spawn_grass_road_tbl = new Sprite(16, 0, 10,
			SpriteSheet.spawn_level);
	public static Sprite spawn_grass_road_tbr = new Sprite(16, 0, 11,
			SpriteSheet.spawn_level);
	public static Sprite spawn_grass_road_tb = new Sprite(16, 0, 12,
			SpriteSheet.spawn_level);
	public static Sprite spawn_grass_road_blr = new Sprite(16, 0, 13,
			SpriteSheet.spawn_level);
	public static Sprite spawn_grass_road_lr = new Sprite(16, 0, 14,
			SpriteSheet.spawn_level);
	public static Sprite spawn_grass_road_all = new Sprite(16, 0, 15,
			SpriteSheet.spawn_level);

	// Spawn_rock Sprites
	public static Sprite spawn_rock = new Sprite(16, 1, 0,
			SpriteSheet.spawn_level);
	public static Sprite spawn_rock_road_t = new Sprite(16, 1, 1,
			SpriteSheet.spawn_level);
	public static Sprite spawn_rock_road_tl = new Sprite(16, 1, 2,
			SpriteSheet.spawn_level);
	public static Sprite spawn_rock_road_l = new Sprite(16, 1, 3,
			SpriteSheet.spawn_level);
	public static Sprite spawn_rock_road_bl = new Sprite(16, 1, 4,
			SpriteSheet.spawn_level);
	public static Sprite spawn_rock_road_b = new Sprite(16, 1, 5,
			SpriteSheet.spawn_level);
	public static Sprite spawn_rock_road_br = new Sprite(16, 1, 6,
			SpriteSheet.spawn_level);
	public static Sprite spawn_rock_road_r = new Sprite(16, 1, 7,
			SpriteSheet.spawn_level);
	public static Sprite spawn_rock_road_tr = new Sprite(16, 1, 8,
			SpriteSheet.spawn_level);
	public static Sprite spawn_rock_road_tlr = new Sprite(16, 1, 9,
			SpriteSheet.spawn_level);
	public static Sprite spawn_rock_road_tbl = new Sprite(16, 1, 10,
			SpriteSheet.spawn_level);
	public static Sprite spawn_rock_road_tbr = new Sprite(16, 1, 11,
			SpriteSheet.spawn_level);
	public static Sprite spawn_rock_road_tb = new Sprite(16, 1, 12,
			SpriteSheet.spawn_level);
	public static Sprite spawn_rock_road_blr = new Sprite(16, 1, 13,
			SpriteSheet.spawn_level);
	public static Sprite spawn_rock_road_lr = new Sprite(16, 1, 14,
			SpriteSheet.spawn_level);
	public static Sprite spawn_rock_road_all = new Sprite(16, 1, 15,
			SpriteSheet.spawn_level);

	// Spawn_flower Sprites
	public static Sprite spawn_flower = new Sprite(16, 2, 0,
			SpriteSheet.spawn_level);

	// Spawn_tree Sprites
	public static Sprite spawn_tree = new Sprite(16, 3, 0,
			SpriteSheet.spawn_level);

	// Spawn_Road Sprites
	public static Sprite spawn_road = new Sprite(16, 4, 0,
			SpriteSheet.spawn_level);

	public static Sprite voidSprite = new Sprite(16, 0x002378);

	// Default Sprites
	public static Sprite dummy_default = new Sprite(16, 0, 0,
			SpriteSheet.cat_idle);
	public static Sprite cathulhu_default = new Sprite(32, 1, 0,
			SpriteSheet.cathulhu);

	// Items
	public static Sprite med_kit = new Sprite(16, 0, 0, SpriteSheet.items);
	public static Sprite full_bullet = new Sprite(16, 1, 0, SpriteSheet.items);
	public static Sprite spent_bullet = new Sprite(16, 2, 0, SpriteSheet.items);

	// Guns
	public static Sprite handgun = new Sprite(16, 0, 0, SpriteSheet.guns);
	public static Sprite shotgun = new Sprite(16, 1, 0, SpriteSheet.guns);
	public static Sprite machine_gun = new Sprite(16, 2, 0, SpriteSheet.guns);
	public static Sprite rocket_launcher = new Sprite(16, 3, 0,
			SpriteSheet.guns);
	public static Sprite rocket_shotgun = new Sprite(16, 4, 0, SpriteSheet.guns);

	// Projectiles
	public static Sprite projectile_dude = new Sprite(16, 0, 1,
			SpriteSheet.projectiles);
	public static Sprite projectile_cat = new Sprite(16, 1, 0,
			SpriteSheet.projectiles);
	public static Sprite projectile_gun = new Sprite(16, 2, 0,
			SpriteSheet.projectiles);

	// Particles
	public static Sprite particle_normal = new Sprite(2, 0xffffffff);

	// Menu
	public static Sprite menuSelectionCursor = new Sprite(32, 2, 0,
			SpriteSheet.cursors);
	public static Sprite crosshairCursor = new Sprite(16, 0, 0,
			SpriteSheet.cursors1);
	public static Sprite weaponBox = new Sprite(16, 1, 0, SpriteSheet.cursors1);

	/**
	 * create a sprite
	 * 
	 * @param sheet
	 *            is the spritesheet containing the sprite
	 * @param width
	 *            is the width
	 * @param height
	 *            is the height of the sprite
	 */
	protected Sprite(SpriteSheet sheet, int width, int height) {
		// if the sprite is square use sprite size, otherwise use width and
		// height
		if (width == height)
			SIZE = width;
		else
			SIZE = -1;
		this.width = width;
		this.height = height;
		this.sheet = sheet;
	}

	/**
	 * create a square sprite
	 * 
	 * @param size
	 *            is the length of the edges
	 * @param x
	 *            is the x position
	 * @param y
	 *            is the y position in the
	 * @param sheet
	 *            spritesheet
	 */
	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		this.x = x * size;
		this.y = y * size;
		this.width = size;
		this.height = size;
		this.sheet = sheet;
		load();

	}

	/**
	 * create a mirrored sprite
	 * 
	 * @param size
	 *            is the length of the edges in pixels
	 * @param x
	 *            is the x position
	 * @param y
	 *            is the y position in
	 * @param sheet
	 *            the spritesheet
	 * @param yMirror
	 *            if true, the sprite will be mirrored, otherwise the original
	 *            picture is used
	 */
	public Sprite(int size, int x, int y, SpriteSheet sheet, boolean yMirror) {
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		this.x = x * size;
		this.y = y * size;
		this.width = size;
		this.height = size;
		this.sheet = sheet;
		load();

	}

	/**
	 * create a rectangular sprite, filled with a color
	 * 
	 * @param width
	 *            the width in pixels
	 * @param height
	 *            the height in pixels
	 * @param color
	 *            the color
	 */
	public Sprite(int width, int height, int color) {
		SIZE = -1;
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		setColor(color);
	}

	/**
	 * return the width of the sprite
	 * 
	 * @return
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * return the height of the sprite
	 * 
	 * @return
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * create a square sprite filled with a color
	 * 
	 * @param size
	 * @param color
	 */
	public Sprite(int size, int color) {
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		this.x = x * size;
		this.y = y * size;
		this.width = size;
		this.height = size;
		setColor(color);
	}

	/**
	 * create a sprite out of an array of pixels
	 * 
	 * @param pixels
	 *            an integer array with hexcolor values
	 * @param width
	 *            the width
	 * @param height
	 *            the height of the sprite
	 */
	public Sprite(int[] pixels, int width, int height) {
		if (width == height)
			SIZE = width;
		else
			SIZE = -1;
		this.width = width;
		this.height = height;
		this.pixels = pixels;

	}

	/**
	 * sets the color of all the pixels in the sprite pixels array to a single
	 * color
	 * 
	 * @param color
	 */
	private void setColor(int color) {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = color;
		}

	}

	/**
	 * fill the pixel array with the values extracted from the spritesheet
	 */
	private void load() {
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				// sprite im spritesheet suchen
				pixels[x + y * this.width] = sheet.pixels[(this.x + x)
						+ (this.y + y) * sheet.WIDTH];
			}

		}
	}

}
