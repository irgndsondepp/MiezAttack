package com.irgndsondepp.clone.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.irgndsondepp.clone.Game;
import com.irgndsondepp.clone.audio.Sound;
import com.irgndsondepp.clone.entity.Entity;
import com.irgndsondepp.clone.entity.Explosion;
import com.irgndsondepp.clone.entity.Item;
import com.irgndsondepp.clone.entity.MedKit;
import com.irgndsondepp.clone.entity.guns.Gun;
import com.irgndsondepp.clone.entity.guns.HandGun;
import com.irgndsondepp.clone.entity.guns.MachineGun;
import com.irgndsondepp.clone.entity.guns.RocketLauncher;
import com.irgndsondepp.clone.entity.guns.RocketShotgun;
import com.irgndsondepp.clone.entity.guns.Shotgun;
import com.irgndsondepp.clone.entity.mob.Boss;
import com.irgndsondepp.clone.entity.mob.BurningCat;
import com.irgndsondepp.clone.entity.mob.BurningChaser;
import com.irgndsondepp.clone.entity.mob.Cathulhu;
import com.irgndsondepp.clone.entity.mob.Chaser;
import com.irgndsondepp.clone.entity.mob.Dummy;
import com.irgndsondepp.clone.entity.mob.Mob;
import com.irgndsondepp.clone.entity.mob.Player;
import com.irgndsondepp.clone.entity.particle.Particle;
import com.irgndsondepp.clone.entity.projectile.CatProjectile;
import com.irgndsondepp.clone.entity.projectile.DudeProjectile;
import com.irgndsondepp.clone.entity.projectile.GunProjectile;
import com.irgndsondepp.clone.entity.projectile.Projectile;
import com.irgndsondepp.clone.entity.projectile.RocketProjectile;
import com.irgndsondepp.clone.graphics.Screen;
import com.irgndsondepp.clone.graphics.Sprite;
import com.irgndsondepp.clone.graphics.menu.HighscoreScreen;
import com.irgndsondepp.clone.level.tile.Tile;

/**
 * class Level creates a new Level
 * 
 * @author Robert
 *
 */
public class Level {

	// Level Variables
	protected int width, height;
	protected int[] tiles;
	protected int tile_size;
	protected final Random random = new Random();

	// Kill Counter & Miez Attack Variables
	public int killCounter = 0;
	public int penaltyCounter = 0;
	private boolean miezAttack = false;
	private int miezAttackCounter = 0;
	private int miezenInLevel = 0;
	private int multiKills = 0;
	private int multiKillTimer = 0;
	private final int multiKillMaxSeconds = 3;
	private boolean multiKillStreakRunning = false;
	private boolean messageShowing = false;
	private final int messageTimerMaximum = 2 * Game.framerate;
	private int messageTimer = 0;
	private int highestKillStreak = 0;

	// Timer for Player Death Screen
	private int idleDeathTimer = 0;
	private final int idleDeathTimerMax = 5 * 60;

	// Count the destructible Object
	private int levelDestructibles = 0;
	protected int levelDestroyed = 0;

	// Level changes if a boss is present
	protected Boss boss = null;

	// has access to game
	protected Game game;

	// Lists of Entities, Projectiles, Particles, Mobs, Players and Items
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Particle> particles = new ArrayList<Particle>();
	private List<Mob> mobs = new ArrayList<Mob>();
	private List<Player> players = new ArrayList<Player>();
	private List<Item> items = new ArrayList<Item>();

	// public static Level spawn_level = new SpawnLevel("/levels/spawn.png");

	/**
	 * Level constructor for a new random level
	 * 
	 * @param width
	 *            Width of the level in Tiles
	 * @param height
	 *            Height of the level in Tiles
	 * @param game
	 *            the running game
	 */
	public Level(int width, int height, Game game) {
		this.game = game;
		this.width = width;
		this.height = height;
		this.tiles = new int[width * height];

		generateLevel();
		setDestructibles();
	}

	/**
	 * Level constructor loading a level from a small graphic
	 * 
	 * @param path
	 *            Path to the *.png file
	 * @param game
	 *            the running game
	 */
	public Level(String path, Game game) {
		this.game = game;
		loadLevel(path);

		setDestructibles();

	}

	/**
	 * Creates a random level and gets the Size of the tiles
	 */
	protected void generateLevel() {
		// TODO nothing here
		this.tile_size = this.getTile(0, 0).size;
	}

	/**
	 * get the amount of destructible objects in the level
	 */
	private void setDestructibles() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// if there is a rock or tree tile in the level increase the
				// amount
				if (tiles[x + y * width] == Tile.col_spawn_rock
						|| tiles[x + y * width] == Tile.col_spawn_tree) {
					levelDestructibles++;
				}
			}
		}
	}

	/**
	 * Method will be overridden by extended classes, should load the level
	 * image into an int array
	 * 
	 * @param path
	 */
	protected void loadLevel(String path) {

	}

	/**
	 * Level update() method, updates all the entities contained in the
	 * different lists
	 */
	public void update() {
		clear();

		// stuff only happens when player is aliver

		// killing Spree message
		if (messageShowing) {
			messageTimer++;
		}
		if (messageTimer < messageTimerMaximum) {
			messageTimer = 0;
			messageShowing = false;
		}

		// get the amount of cats in the level
		miezenInLevel = mobs.size();

		// keep the music on loop while the player is alive
		if (!getClientPlayer().isDead()) {
			if (!Sound.level_music.isPlaying()) {
				Sound.level_music.playSound();
			}
		} else {
			idleDeathTimer++;
			messageShowing = false;
			// if the player is dead -> stop the music
			if (Sound.level_music.isPlaying()) {
				Sound.level_music.stop();

			}
			// increase the timer for the game over screen
			if (idleDeathTimer > idleDeathTimerMax) {

				// create a new highscore screen with the players kills,
				// highest
				// kill streak and amount of level destroyed
				game.setMenuScreen(new HighscoreScreen(
						width,
						height,
						killCounter,
						highestKillStreak,
						(int) (100 * ((double) levelDestroyed / (double) levelDestructibles)),
						game));

			}

		}
		if (!getClientPlayer().isDead()) {

			// check for miez attack
			if (miezAttack) {
				// increase the counter for the message shown in game
				if (miezAttackCounter < 180) {
					miezAttackCounter++;
				} else {
					miezAttackCounter = 0;
					miezAttack = false;
				}
			}

			// update the kill streak timer and abort if longer than the maximum
			// of
			// seconds
			if (multiKillStreakRunning) {
				multiKillTimer++;
				if (multiKillTimer >= multiKillMaxSeconds * Game.framerate) {
					// reset all Variables
					multiKillStreakRunning = false;
					checkNewHighestKills();
					multiKillTimer = 0;
					multiKills = 0;
				}
			}

			// Update all the lists, meaning every object in the level
			for (int i = 0; i < entities.size(); i++) {
				entities.get(i).update();
			}
			for (int i = 0; i < items.size(); i++) {
				items.get(i).update();
			}
			for (int i = 0; i < projectiles.size(); i++) {
				projectiles.get(i).update();
			}
			for (int i = 0; i < particles.size(); i++) {
				particles.get(i).update();
			}
			for (int i = 0; i < mobs.size(); i++) {
				mobs.get(i).update();
			}
		}
		for (int i = 0; i < players.size(); i++) {
			players.get(i).update();
		}

	}

	/**
	 * handles the miezAttack logic
	 */
	private void miezAttack() {
		// increase the miez attack level (penalty) if 10 cats were killed
		if (killCounter % 10 == 0) {

			// play a meow
			Sound.cat_meow1.playSound();

			// increase the miez attack level
			if (penaltyCounter < 10) {
				penaltyCounter++;
			}

			// change present cats in chaser cats according to penalty value
			for (int i = 0; i < penaltyCounter; i++) {
				boolean newCat = false;
				for (int j = 0; j < mobs.size(); j++) {

					// only change to chaser if it hasn't been done before
					if (!newCat) {

						// if it isn't already a chaser spawn a new chaser
						if (!(mobs.get(j) instanceof Chaser)) {
							newCat = true;
							Mob m = mobs.get(j);
							int x = m.getX();
							int y = m.getY();

							// add chaser in Tile coordinates
							this.add(new Chaser(x, y));

							// remove the old cat
							m.remove();
						}
					}
				}
			}

			// start adding new Cats after miez attack level 10, if there are
			// not more than 300
			if (penaltyCounter >= 1 && miezenInLevel <= 300) {
				for (int i = 0; i < 15; i++) {
					spawnDummy();
				}

			}

			// if not already present, add Cathulhu every 100 kills

			miezAttack = true;

		}
		if ((boss == null) && (killCounter % 100 == 0)) {
			// try to randomly place the cats somewhere they can move
			boss = spawnBoss();
			add(boss);
		}

	}

	/**
	 * check if a new highest kill streak has been reached and set the variable
	 * accordingly
	 */
	private void checkNewHighestKills() {
		if (multiKills > highestKillStreak) {
			highestKillStreak = multiKills;
		}

	}

	/*
	 * private void time() {
	 * 
	 * }
	 */

	/**
	 * Check if an object will collide with a solid tile in the next move (used
	 * for projectiles)
	 * 
	 * @param x
	 *            the actual x coordinate
	 * @param y
	 *            the actual y coordinate
	 * @param xchange
	 *            the change in x direction
	 * @param ychange
	 *            the change in y direction
	 * @param size
	 *            the size of the object
	 * @return true if the tile at the position is solid, else return false
	 */
	public boolean tileCollision(double x, double y, double xchange,
			double ychange, int size) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			// very complex hitboxing
			double xt = ((x + xchange) + (c % 2 * (size / 2)) - 4) / tile_size;
			double yt = ((y + ychange) + (c / 2 * (size / 10)) + 6) / tile_size;
			// get the solid attribute of the right tile
			if (getTile((int) xt, (int) yt).solid())
				solid = true;

		}
		return solid;
	}

	/**
	 * remove destroyed objects from their lists, also has the logic for kills
	 * and killing sprees, as well as spawning items
	 */
	private void clear() {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).isRemoved()) {
				entities.remove(i);
			}
		}
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).isRemoved()) {
				items.remove(i);
			}
		}
		for (int i = 0; i < projectiles.size(); i++) {
			if (projectiles.get(i).isRemoved()) {
				projectiles.remove(i);
			}
		}
		for (int i = 0; i < particles.size(); i++) {
			if (particles.get(i).isRemoved()) {
				particles.remove(i);
			}
		}

		for (int i = 0; i < mobs.size(); i++) {
			Mob m = mobs.get(i);
			if (m.isRemoved()) {

				// logic for killing sprees
				if (m.getHP() <= 0) {
					if (!(m instanceof BurningCat)) {
						killCounter++;
						// should check if cats have to be spawned
						miezAttack();

						// Updates the killing spree
						multiKills++;
						multiKillStreakRunning = true;
						// reset the timer to accumulate kills
						multiKillTimer = 0;

						// spawn a specific item if Cathulhu is defeated
						if (m instanceof Boss) {
							this.add(new MedKit(boss.getX(), boss.getY(), 50));
							// defeat Cathulhu
							boss = null;
						} else {
							// 1:100 chance to get a RocketShotgun
							int r = random.nextInt(100);
							if (r == 0) {
								this.add(new RocketShotgun(m.getX(), m.getY()));
							} else {
								// add items randomly (20% chance)
								r = random.nextInt(25);
								if (r == 0) {
									this.add(new HandGun(m.getX(), m.getY()));
								}
								if (r == 1) {
									this.add(new Shotgun(m.getX(), m.getY()));
								}
								if (r == 2) {
									this.add(new MachineGun(m.getX(), m.getY()));
								}
								if (r == 3) {
									this.add(new RocketLauncher(m.getX(), m
											.getY()));
								}
								if (r == 4) {
									this.add(new MedKit(m.getX(), m.getY(), 25));
								}
							}

							// Heal the player if multiple kills are achieved,
							// also
							// start the message showing counter
							if (multiKills == 5) {
								Sound.brutal.playSound();
								getClientPlayer().getHealed(5);
								messageShowing = true;
								messageTimer = messageTimerMaximum;
							}
							if (multiKills == 10) {
								Sound.awesome.playSound();
								getClientPlayer().getHealed(10);
								messageShowing = true;
								messageTimer = messageTimerMaximum;
							}
							if (multiKills == 15) {
								Sound.incredible.playSound();
								getClientPlayer().getHealed(15);
								messageShowing = true;
								messageTimer = messageTimerMaximum;
							}
							if (multiKills == 20) {
								Sound.barbaric.playSound();
								getClientPlayer().getHealed(25);
								messageShowing = true;
								messageTimer = messageTimerMaximum;
							}
							if (multiKills >= 25 && multiKills % 5 == 0) {

								// only give the massive heal on exactly 25
								// accumulated kills, else heal less
								if (multiKills == 25) {
									getClientPlayer().getHealed(50);
									Sound.psycho.playSound();
									messageShowing = true;
									messageTimer = messageTimerMaximum;
								} else {
									getClientPlayer().getHealed(5);
								}
							}
						}
					}

					// add burning cats, if they were burned by a fireball and
					// are not cathulhu
					if (m instanceof Dummy && !(m instanceof Cathulhu)) {
						if (((Dummy) m).isBurned()) {
							if (m instanceof Chaser) {
								this.add(new BurningChaser(m.getX(), m.getY()));
							} else {
								this.add(new BurningCat(m.getX(), m.getY()));
							}
						}

					}
				}
				// remove from the list
				mobs.remove(i);
			}
		}

		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).isRemoved()) {
				players.remove(i);
			}
		}
	}

	/**
	 * render the level step by step: 1)background 2) items 3) projectiles 4)
	 * mobs 5) players 6) entities 7) particles 8) ui
	 * 
	 * @param xScroll
	 *            the x coordinate of the top left pixel
	 * @param yScroll
	 *            the y coordinate of the top left pixel
	 * @param screen
	 *            the screen
	 */
	@SuppressWarnings({ "static-access" })
	public void render(int xScroll, int yScroll, Screen screen) {
		// change the current offset
		screen.setOffset(xScroll, yScroll);

		// Bitwise Operation für /16 -> pixel -> tiles
		// set the corners of the screen
		int x0 = xScroll / tile_size;
		int x1 = (xScroll + screen.width + tile_size) / tile_size;
		int y0 = yScroll / tile_size;
		int y1 = (yScroll + screen.height + tile_size) / tile_size;

		// render all the visible tiles
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				getTile(x, y).render(x, y, screen);

			}
		}

		// render all items
		for (int i = 0; i < items.size(); i++) {
			items.get(i).render(screen);
		}

		// render all projectiles
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(screen);

		}

		// render all mobs
		for (int i = 0; i < mobs.size(); i++) {
			mobs.get(i).render(screen);

		}

		// render all players
		for (int i = 0; i < players.size(); i++) {
			players.get(i).render(screen);

		}

		// render all entities
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(screen);
		}

		// render all particles
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).render(screen);

		}

		// show the weapon box with the player weapon if equipped and player
		// alive
		Player p = getClientPlayer();
		if (boss != null) {
			screen.renderHealthBar(boss.getHP(), boss.getMaxHP());
		}
		if ((p.getGun() != null) && !(p.isDead())) {
			Gun gun = p.getGun();
			int xdraw = game.width - (gun.getSprite().SIZE * gun.getScale())
					- 15;
			int ydraw = 15;
			screen.renderScaledSprite(xdraw, ydraw, gun.getScale(),
					Sprite.weaponBox, false);
			screen.renderItem(xdraw, ydraw, gun, false);

			xdraw -= Sprite.full_bullet.SIZE;
			ydraw += (gun.getSprite().SIZE) * gun.getScale()
					- Sprite.full_bullet.SIZE;
			// render full bullets
			double nTotalShots = gun.getShotsTotal();
			double nFullBullets = gun.getShotsLeft();
			double nEmptyBullets = nTotalShots - nFullBullets;

			if (nTotalShots > 10) {
				double nBulletsRatio = 10.f / nTotalShots;
				nTotalShots = nTotalShots * nBulletsRatio;
				nFullBullets = nFullBullets * nBulletsRatio;
				nEmptyBullets = nEmptyBullets * nBulletsRatio;

			}
			int fullBullets = (int) nFullBullets;
			int emptyBullets = (int) nEmptyBullets;

			if ((fullBullets + emptyBullets) < nTotalShots) {
				while ((fullBullets + emptyBullets) < nTotalShots) {
					fullBullets++;
				}
			} else if ((fullBullets + emptyBullets) > nTotalShots) {
				while ((fullBullets + emptyBullets > nTotalShots)) {
					emptyBullets--;
				}
			}

			for (int i = 0; i < fullBullets; i++) {
				screen.renderScaledSprite(xdraw, ydraw, 1, Sprite.full_bullet,
						false);
				xdraw -= Sprite.full_bullet.SIZE;
			}
			for (int j = 0; j < emptyBullets; j++) {
				screen.renderScaledSprite(xdraw, ydraw, 1, Sprite.spent_bullet,
						false);
				xdraw -= Sprite.spent_bullet.SIZE;
			}
		}
		if (getClientPlayer().isDead()) {
			// show the game over screen if the player is dead
			int speed = 3;
			screen.fadeToBlack(idleDeathTimer, idleDeathTimerMax, speed);
			p.render(screen);
			if (game.width < 600) {
				gameOverScreen.gameOver.render(screen);
			} else {
				gameOverScreen.gameOverBig.render(screen);
			}
		}
	}

	/**
	 * add entities to their fitting lists
	 * 
	 * @param e
	 *            is an entity
	 */
	public void add(Entity e) {
		// initialize the entity for this level
		e.init(this);

		// add to lists accordingly
		if (e instanceof Particle) {
			particles.add((Particle) e);
		} else if (e instanceof Projectile) {
			projectiles.add((Projectile) e);
		} else if (e instanceof Player) {
			players.add((Player) e);
		} else if (e instanceof Mob) {
			mobs.add((Mob) e);
		} else if (e instanceof Item) {
			items.add((Item) e);
		} else {
			entities.add(e);
		}
	}

	/**
	 * Return the Tile from the position in the loaded level graphic
	 * 
	 * @param x
	 *            x coordinate in tiles
	 * @param y
	 *            y coordinate in tiles
	 * @return
	 */
	public Tile getTile(int x, int y) {

		// if out of bounds, return a void Tile
		if (x < 0 || y < 0 || x >= width || y >= height) {
			return Tile.voidTile;
		}

		// return different tiles depending on their neighbours
		// tiles are determined by the color from the loaded graphic at the
		// position in the array

		// grass=0x00ff00
		// flower=0xffff00
		// rock=0xff000000
		// tree=0xffff0000

		if (tiles[x + y * width] == Tile.col_spawn_grass) {
			boolean top = false;
			boolean bottom = false;
			boolean left = false;
			boolean right = false;

			if (getTileColor(x, y - 1) == Tile.col_spawn_road) {
				top = true;
			}
			if (getTileColor(x, y + 1) == Tile.col_spawn_road) {
				bottom = true;
			}
			if (getTileColor(x - 1, y) == Tile.col_spawn_road) {
				left = true;
			}
			if (getTileColor(x + 1, y) == Tile.col_spawn_road) {
				right = true;
			}
			if (top) {
				if (left) {
					if (right) {
						if (bottom) {
							return Tile.spawn_grass_road_all;
						} else {
							return Tile.spawn_grass_road_tlr;
						}
					} else
						return Tile.spawn_grass_road_tl;
				} else if (right) {
					if (bottom) {
						return Tile.spawn_grass_road_tbr;
					} else {
						return Tile.spawn_grass_road_tr;
					}
				}
				if (bottom) {
					return Tile.spawn_grass_road_tb;
				} else {
					return Tile.spawn_grass_road_t;
				}
			}
			if (bottom) {
				if (left) {
					if (right) {
						return Tile.spawn_grass_road_blr;
					} else {
						return Tile.spawn_grass_road_bl;
					}
				} else if (right) {
					return Tile.spawn_grass_road_br;
				} else
					return Tile.spawn_grass_road_b;
			}
			if (left) {
				if (right) {
					return Tile.spawn_grass_road_lr;
				} else {
					return Tile.spawn_grass_road_l;
				}
			}
			if (right) {
				return Tile.spawn_grass_road_r;
			}
			return Tile.spawn_grass;
		}
		if (tiles[x + y * width] == Tile.col_spawn_rock) {
			boolean top = false;
			boolean bottom = false;
			boolean left = false;
			boolean right = false;
			if (getTileColor(x, y - 1) == Tile.col_spawn_road) {
				top = true;
			}
			if (getTileColor(x, y + 1) == Tile.col_spawn_road) {
				bottom = true;
			}
			if (getTileColor(x - 1, y) == Tile.col_spawn_road) {
				left = true;
			}
			if (getTileColor(x + 1, y) == Tile.col_spawn_road) {
				right = true;
			}
			if (top) {
				if (left) {
					if (right) {
						if (bottom) {
							return Tile.spawn_rock_road_all;
						} else {
							return Tile.spawn_rock_road_tlr;
						}
					} else
						return Tile.spawn_rock_road_tl;
				} else if (right) {
					if (bottom) {
						return Tile.spawn_rock_road_tbr;
					} else {
						return Tile.spawn_rock_road_tr;
					}
				}
				if (bottom) {
					return Tile.spawn_rock_road_tb;
				} else {
					return Tile.spawn_rock_road_t;
				}
			}
			if (bottom) {
				if (left) {
					if (right) {
						return Tile.spawn_rock_road_blr;
					} else {
						return Tile.spawn_rock_road_bl;
					}
				} else if (right) {
					return Tile.spawn_rock_road_br;
				} else
					return Tile.spawn_rock_road_b;
			}
			if (left) {
				if (right) {
					return Tile.spawn_rock_road_lr;
				} else {
					return Tile.spawn_rock_road_l;
				}
			}
			if (right) {
				return Tile.spawn_rock_road_r;
			}
			return Tile.spawn_rock;

		}
		if (tiles[x + y * width] == Tile.col_spawn_flower)
			return Tile.spawn_flower;
		if (tiles[x + y * width] == Tile.col_spawn_tree)
			return Tile.spawn_tree;
		if (tiles[x + y * width] == Tile.col_spawn_road)
			return Tile.spawn_road;
		return Tile.voidTile;
	}

	/**
	 * get the tile color from the loaded graphic array
	 * 
	 * @param x
	 *            x coordinate in tiles
	 * @param y
	 *            y coordinate in tiles
	 * @return int color of the tile array entry
	 */
	public int getTileColor(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) {
			return -1;
		} else {
			return tiles[x + y * width];
		}
	}

	/**
	 * check mobs colliding with each other
	 * 
	 * @param mob
	 *            is a mob entity
	 * @param x
	 *            actual x coordinate
	 * @param xchange
	 *            change in x direction
	 * @param y
	 *            actual y coordinate
	 * @param ychange
	 *            change in y direction
	 * @return true of the sprites overlap, false else
	 */
	public boolean mobCollision(Mob mob, int x, int xchange, int y, int ychange) {
		boolean solid = false;
		int size = mob.getSpriteSize() / 2;
		int xl = x + xchange - (size - mob.getLeftEdge());
		int xr = x + xchange + (size - mob.getRightEdge());
		int yt = y + ychange - (size - mob.getTopEdge());
		int yb = y + ychange + (size - mob.getBottomEdge());
		for (int i = 0; i < mobs.size(); i++) {
			Mob m = (Mob) mobs.get(i);
			if (m != mob) {
				// get the hitbox for every mob
				int msize = m.getSpriteSize() / 2;
				int mLeftEdge = m.getX() - (msize - m.getLeftEdge());
				int mRightEdge = m.getX() + (msize - m.getRightEdge());
				int mTopEdge = m.getY() - (msize - m.getTopEdge());
				int mBottomEdge = m.getY() + (msize - m.getBottomEdge());

				// compare hitboxes
				if ((((xl < mRightEdge) && (xr > mRightEdge)) || ((xr > mLeftEdge) && (xl < mLeftEdge)))
						&& (((yt <= mBottomEdge) && (yb >= mBottomEdge)) || ((yb >= mTopEdge) && (yt <= mTopEdge)))) {
					if (!(mob instanceof Player)) {
						// player doesn't get stuck on cats
						solid = true;
					} else if ((m instanceof Chaser) && (mob instanceof Player)) {

						// only hit the player if he is not invincible and play
						// sounds
						if (!(((Player) mob).isInvincible())) {
							m.playSoundHit();
							mob.getHit(((Chaser) m).getMaulDamage());
							// get caught shortly
							solid = true;
						}

					}

				}
			}

		}

		return solid;
	}

	/**
	 * check if projectile collides with a player
	 * 
	 * @param p
	 *            is a projectile
	 * @param x
	 *            is x coordinate of projectile
	 * @param y
	 *            is y coordinate of projectile
	 * @return true if player is shot, else false
	 */
	public boolean playerShot(Projectile p, int x, int y) {
		boolean solid = false;
		for (int i = 0; i < players.size(); i++) {

			Player m = players.get(i);

			// try to account for sprite size
			int xm = (int) m.getX();
			int ym = (int) m.getY();
			int msize = m.getSpriteSize() / 2;
			int mle = m.getLeftEdge();
			int mre = m.getRightEdge();
			int mte = m.getTopEdge();
			int mbe = m.getBottomEdge();

			if (((xm + (msize + mre)) >= x) && (xm - (msize + mle) < x)
					&& ((ym + (msize + mbe)) >= y)
					&& ((ym - (msize + mte)) < y)) {
				solid = true;

				// only hit the player if he is not invincible and play
				// sounds
				if (!(m.isInvincible()) && (p instanceof CatProjectile)) {
					int n = random.nextInt(3);
					if (n == 0) {
						Sound.cat_meow1.playSound();
					}
					if (n == 1) {
						Sound.cat_meow2.playSound();
					}
					if (n == 2) {
						Sound.cat_hiss.playSound();
					}
					m.getHit(p.getDamage());

				}

			}

		}

		return solid;
	}

	/**
	 * check if a mob is shot by a projectile and returns hit mob
	 * 
	 * @param p
	 *            is a projectile
	 * @param x
	 *            is x coordinate of projectile
	 * @param y
	 *            is y coordinate of projectile
	 * @return the hit mob (used in rocketprojectile)
	 */
	public Mob mobShot(Projectile p, int x, int y) {
		boolean solid = false;
		Mob returnedMob = null;
		for (int i = 0; i < mobs.size(); i++) {
			if (!solid) {
				// player projectiles should not hit the player
				if (p instanceof DudeProjectile
						&& mobs.get(i) instanceof Player) {
					solid = false;
				} else {
					Mob m = (Mob) mobs.get(i);

					// only collide if the mob is alive
					if (!m.isRemoved()) {
						int xm = (int) m.getX();
						int ym = (int) m.getY();
						int msize = m.getSpriteSize() / 2;
						int mle = m.getLeftEdge();
						int mre = m.getRightEdge();
						int mte = m.getTopEdge();
						int mbe = m.getBottomEdge();

						if (((xm + (msize + mre)) >= x)
								&& (xm - (msize + mle) < x)
								&& ((ym + (msize + mbe)) >= y)
								&& ((ym - (msize + mte)) < y)) {
							solid = true;
							returnedMob = m;

							if (p instanceof RocketProjectile) {
								m.getHit(2 * p.getDamage());
							} else {
								m.getHit(p.getDamage());
							}

							// set dying mobs as burned if hit by fire ball
							if ((p instanceof DudeProjectile)
									&& !(p instanceof RocketProjectile)
									&& !(p instanceof GunProjectile)
									&& (m.getHP() <= 0) && (m instanceof Dummy)) {
								((Dummy) m).setBurned();
							}
						}
					}
				}
			}

		}

		return returnedMob;
	}

	/*
	 * public boolean projectileCollision(Mob mob, int x0, int x1, int y0, int
	 * y1) { boolean hit = false; for (int i = 0; i < projectiles.size(); i++) {
	 * Projectile p = projectiles.get(i); int px = (int) p.getX(); int py =
	 * (int) p.getY(); if ((px > x0) && (px < x1) && (py > y0) && (py < y1)) {
	 * hit = true;
	 * 
	 * mob.getHit(p.getDamage()); p.remove(); add(new ParticleSpawner(px, py,
	 * 50, 50, this)); } }
	 * 
	 * return hit; }
	 */

	/**
	 * return the first player in the players list
	 * 
	 * @return Player first player
	 */
	public Player getClientPlayer() {
		if (players.size() != 0) {
			return players.get(0);
		} else {
			return null;
		}
	}

	/**
	 * check if miez attack
	 * 
	 * @return boolean
	 */
	public boolean isMiezAttack() {
		if (miezAttack) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * return the timer for miez attack
	 * 
	 * @return int miezAttackCounter
	 */
	public int getMiezAttackCounter() {
		return miezAttackCounter;
	}

	/**
	 * return the penalty counter
	 * 
	 * @return int penaltyCounter
	 */
	public int getMiezAttackLevel() {
		return penaltyCounter;
	}

	/**
	 * get the amount of cats in the level
	 * 
	 * @return int miezenInLevel
	 */
	public int getMiezen() {
		return miezenInLevel;
	}

	/**
	 * returns the kill streak
	 * 
	 * @return int multiKills
	 */
	public int getMultiKillCounter() {
		return multiKills;
	}

	/**
	 * check if kill streak is running
	 * 
	 * @return boolean multiKillStreakRunning
	 */
	public boolean isKillStreakRunning() {
		return multiKillStreakRunning;
	}

	/**
	 * return the multi kill timer
	 * 
	 * @return int multiKillTimer
	 */
	public int getMultiKillTimer() {
		return multiKillTimer;
	}

	/**
	 * check if there is an item at the position in the level
	 * 
	 * @param x
	 *            is x coordinate of player
	 * @param y
	 *            is y coordinate of player
	 * @return true if there is an item, else false
	 */
	public Entity itemCollision(int x, int y) {
		boolean isItem = false;
		for (int i = 0; i < items.size(); i++) {
			if (!isItem) {
				Item g = items.get(i);
				int xg = g.getX();
				int yg = g.getY();
				int size = getClientPlayer().getSpriteSize()
						* getClientPlayer().getScale();
				if (((x - size) <= xg) && ((x + size) >= xg)
						&& ((y + size) >= yg) && ((y - size) <= yg)) {
					isItem = true;
					return g;
				}
			}
		}
		return null;
	}

	/**
	 * relays the screenwidth of the game
	 * 
	 * @return
	 */
	public int getScreenWidth() {
		return game.getScreenWidth();
	}

	/**
	 * relays the screenheight of the game
	 * 
	 * @return
	 */
	public int getScreenHeight() {
		return game.getScreenHeight();
	}

	/**
	 * explode stuff caught in an explosion
	 * 
	 * @param e
	 */
	public void explodeStuff(Explosion e) {

		int ex = e.getX();
		int ey = e.getY();
		int er = e.getRadius();

		// explode mobs
		for (int i = 0; i < mobs.size(); i++) {
			Mob m = mobs.get(i);
			int mx = m.getX();
			int my = m.getY();
			if ((mx >= (ex - er)) && (mx <= (ex + er)) && (my >= (ey - er))
					&& (my <= (ey + er))) {
				m.getHit(e.getDamage());
			}
		}

		// explode players, players only take a third of the damage
		for (int i = 0; i < players.size(); i++) {
			Player p = players.get(i);
			int px = p.getX();
			int py = p.getY();
			if ((px >= (ex - er)) && (px <= (ex + er)) && (py >= (ey - er))
					&& (py <= (ey + er))) {
				p.getHit(e.getDamage() / 3);
			}
		}

		// explode environment
		// use only half of the radius for environment
		er = er / 2;
		for (int yc = ey - er; yc < ey + er; yc++) {
			for (int xc = ex - er; xc < ex + er; xc++) {

				destroyTile((int) (xc / tile_size), (int) (yc / tile_size));
			}

		}
	}

	/**
	 * returns if a message is showing
	 * 
	 * @return boolean messageShowing
	 */
	public boolean isMessageShowing() {
		return messageShowing;
	}

	/**
	 * gets the color of the tile loaded from the graphic
	 * 
	 * @param x
	 *            is x coordinate in tiles
	 * @param y
	 *            is y coordinate in tiles
	 * @return int color of pixel at x,y in tiles array
	 */
	public int getTileAtPosition(int x, int y) {
		if (x > 0 && y > 0 && x + y * width < tiles.length) {
			return tiles[x + y * width];
		} else
			return -1;
	}

	/**
	 * sets the color of the tile in the tiles array
	 * 
	 * @param x
	 *            is x coordinate in tiles
	 * @param y
	 *            is y coordinate in tiles
	 * @param col
	 *            is the color in hexcode
	 */
	public void setTileAtPosition(int x, int y, int col) {
		if (x > 0 && y > 0 && x + y * width < tiles.length) {
			tiles[x + y * width] = col;
		}
	}

	/**
	 * set the Tile at x,y position in tile coordinates as destroyed if it is
	 * solid
	 * 
	 * @param x
	 *            is x coordinate in tiles
	 * @param y
	 *            is y coordinate in tiles
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

	/**
	 * end the player death animation prematurely by setting the timer finished
	 */
	public void setIdleDeathTimerFinished() {
		idleDeathTimer = idleDeathTimerMax;
	}

	/**
	 * spawns a Dummy at a random position, that is not solid and off screen
	 * from the player
	 */
	private void spawnDummy() {
		if (getClientPlayer() != null) {
			int px = getClientPlayer().getX();
			int py = getClientPlayer().getY();
			int x = random.nextInt(this.width);
			int y = random.nextInt(this.height);
			while ((getTile(x, y).solid() || ((x * tile_size - tile_size >= px
					- Game.width / 2)
					&& (x * tile_size + tile_size <= px + Game.width / 2)
					&& (y * tile_size - tile_size >= py - Game.height / 2) && (y
					* tile_size + tile_size <= py + Game.height / 2)))) {
				x = random.nextInt(this.width);
				y = random.nextInt(this.height);
			}
		}
	}

	/**
	 * spawns a Chaser at a random position, that is not solid and off screen
	 * from the player
	 */
	protected void spawnChaser() {
		if (getClientPlayer() != null) {
			int px = getClientPlayer().getX();
			int py = getClientPlayer().getY();
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
	 * spawns a Boss at a random position off screen from the player -> boss can
	 * smash through solid objects
	 * 
	 * @return
	 */
	private Boss spawnBoss() {
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		boss = null;
		if (getClientPlayer() != null) {
			Player p = getClientPlayer();
			int px = p.getX();
			int py = p.getY();
			while ((x * tile_size >= px - Game.width)
					&& (x * tile_size <= px + Game.width)
					&& (y * tile_size >= py - Game.height)
					&& (y * tile_size <= py + Game.height)) {
				x = random.nextInt(width);
				y = random.nextInt(height);
			}
			boss = new Cathulhu(x * tile_size, y * tile_size);
		}

		return boss;
	}

}
