package com.irgndsondepp.clone.entity.mob;

import com.irgndsondepp.clone.Game;
import com.irgndsondepp.clone.audio.Sound;
import com.irgndsondepp.clone.entity.Crosshair;
import com.irgndsondepp.clone.entity.Entity;
import com.irgndsondepp.clone.entity.Item;
import com.irgndsondepp.clone.entity.MedKit;
import com.irgndsondepp.clone.entity.guns.Gun;
import com.irgndsondepp.clone.entity.projectile.DudeProjectile;
import com.irgndsondepp.clone.entity.projectile.Projectile;
import com.irgndsondepp.clone.entity.spawner.ParticleSpawner;
import com.irgndsondepp.clone.graphics.AnimatedSprite;
import com.irgndsondepp.clone.graphics.Screen;
import com.irgndsondepp.clone.graphics.SpriteSheet;
import com.irgndsondepp.clone.input.Keyboard;
import com.irgndsondepp.clone.input.Mouse;
import com.irgndsondepp.clone.level.Level;

/**
 * class Player Creates a player.
 * 
 * @author Robert
 *
 */
public class Player extends Mob {

	// Player Variables

	private Keyboard input;
	private Crosshair crosshair = new Crosshair(60, 60);

	// Status Variables
	private boolean walking = false;
	private boolean dead = false;
	private boolean action = false;
	private boolean invincible = false;
	private int invincibilityTimer;
	private int invincibilityCounter;
	private final int invincibilitySeconds = 2;
	private int itemCooldown = 0;
	private final int cooldownTime = 30;
	private int stunTimer;
	private int stunTimeMax;
	private boolean stunned;

	// Sprites
	// Default Sprites
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down,
			32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up, 32,
			32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left,
			32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right,
			32, 32, 3);
	private AnimatedSprite death = new AnimatedSprite(SpriteSheet.player_death,
			32, 32, 3);

	// Sprites for Weapon pickup or fireball casting
	private AnimatedSprite down_action = new AnimatedSprite(
			SpriteSheet.player_down_action, 32, 32, 3);
	private AnimatedSprite up_action = new AnimatedSprite(
			SpriteSheet.player_up_action, 32, 32, 3);
	private AnimatedSprite left_action = new AnimatedSprite(
			SpriteSheet.player_left_action, 32, 32, 3);
	private AnimatedSprite right_action = new AnimatedSprite(
			SpriteSheet.player_right_action, 32, 32, 3);

	// Player with a gun Sprite
	private AnimatedSprite down_gun = new AnimatedSprite(
			SpriteSheet.player_down_gun, 32, 32, 3);
	private AnimatedSprite up_gun = new AnimatedSprite(
			SpriteSheet.player_up_gun, 32, 32, 3);
	private AnimatedSprite left_gun = new AnimatedSprite(
			SpriteSheet.player_left_gun, 32, 32, 3);
	private AnimatedSprite right_gun = new AnimatedSprite(
			SpriteSheet.player_right_gun, 32, 32, 3);

	// Sprite Variable
	private AnimatedSprite animSprite = null;

	// Gun Variables
	private int fireRate;

	private Gun gun = null;

	// if you want guns
	// private Gun gun = new Shotgun(-900, -900);
	// private Gun gun = new RocketLauncher(-900, -900);

	// private Gun gun = new RocketLauncher(-1, -1);

	/**
	 * Creates a Player without x or y coordinate
	 * 
	 * @param input
	 *            is a keyboard
	 */
	public Player(Keyboard input) {
		init();
		this.input = input;
	}

	/**
	 * Creates a player at x,y (Tile coordinate)
	 * 
	 * @param x
	 *            in Tile Coordinates
	 * @param y
	 *            in Tile Coordinates
	 * @param input
	 *            is a keyboard
	 */
	public Player(int x, int y, Keyboard input) {
		this.x = x;
		this.y = y;
		this.input = input;
		init();
	}

	/**
	 * Initializes the player
	 */
	private void init() {
		dir = Direction.DOWN;
		sprite = down;
		animSprite = down;
		// player moves twice as fast
		speed = 2;
		scale = 1;
		spriteSize = sprite.SIZE;

		// Framerate should be higher (Std: 10)
		left_action.setFrameRate(5);
		right_action.setFrameRate(5);
		down_action.setFrameRate(5);
		up_action.setFrameRate(5);

		fireRate = DudeProjectile.FIRE_RATE;
		maxHitpoints = 100;
		hitpoints = maxHitpoints;
		stunTimeMax = Game.framerate / 3;

		// Framerate should be lower (Std: 10)
		death.setFrameRate(60);

		// add Sounds
		this.soundGetHit = Sound.player_hit;
		this.soundDie = Sound.player_mauled;
		this.soundHit = Sound.player_hit;

		// create hitbox
		leftEdge = 8 * scale;
		rightEdge = 8 * scale;
		// a bit less than half so cats can still maul him
		topEdge = spriteSize / 2;
		bottomEdge = 0 * scale;

	}

	/**
	 * this method overrides the entity method to initialize the crosshair
	 */
	public void init(Level level) {
		this.level = level;
		crosshair.init(level);
	}

	/**
	 * Player update method
	 */
	public void update() {

		// Don't pick up multiple items
		itemCooldown--;
		if (itemCooldown < 0) {
			itemCooldown = 0;
		}

		// take care of being stunned
		stunTimer--;
		if (stunTimer <= 0) {
			stunned = false;
			stunTimer = 0;
		}

		// Duh...basically only play the dying Sound once.
		if (hitpoints <= 0 && !(dead)) {
			die();
		}

		// Player is only able to move and other stuff if not dead
		if (!dead) {

			crosshair.update();

			getItem();

			clear();

			updateShooting();

			// Player should not take continuous damage from enemies
			if (invincible) {
				invincibilityTimer++;
				if (invincibilityTimer % 60 == 0) {
					invincibilityCounter++;
				}
				if (invincibilityCounter >= invincibilitySeconds) {
					// reset counters
					invincibilityCounter = 0;
					invincibilityTimer = 0;
					invincible = false;
				}
			}

			// Player should not be able to shoot infinitely fast (every update)
			if (fireRate > 0)
				fireRate--;

			// Direction variables
			int xa = 0, ya = 0;

			// Check keyboard inputs
			// if (input.left) {
			// xa--;
			//
			// // if player is shooting or picking up an item, or carrying a
			// // gun another sprite is shown
			// if (action) {
			// animSprite = left_action;
			// } else if (gun != null) {
			// animSprite = left_gun;
			// } else {
			// animSprite = left;
			// }
			// } else if (input.right) {
			// xa++;
			// if (action) {
			// animSprite = right_action;
			// } else if (gun != null) {
			// animSprite = right_gun;
			// } else {
			// animSprite = right;
			// }
			// }
			//
			// if (input.up) {
			// ya--;
			// if (action) {
			// animSprite = up_action;
			// } else if (gun != null) {
			// animSprite = up_gun;
			// } else {
			// animSprite = up;
			// }
			// } else if (input.down) {
			// ya++;
			// if (action) {
			// animSprite = down_action;
			// } else if (gun != null) {
			// animSprite = down_gun;
			// } else {
			// animSprite = down;
			// }
			// }

			if (input.left) {
				xa--;
			} else if (input.right) {
				xa++;
			}

			if (input.up) {
				ya--;
			} else if (input.down) {
				ya++;
			}

			// player animation is dependent on the crosshair position
			int dx = crosshair.getX() - Game.width / 2;
			int dy = crosshair.getY() - Game.height / 2;
			int d = (dx * dx) + (dy * dy);
			int squaredHeight = (Game.height / 2) * (Game.height / 2);

			if (d > squaredHeight || ((dx * dx) > (dy * dy))) {
				if (dx < 0) {
					// if player is shooting or picking up an item, or carrying
					// a
					// gun another sprite is shown
					dir = Direction.LEFT;
					if (action) {
						animSprite = left_action;
					} else if (gun != null) {
						animSprite = left_gun;
					} else {
						animSprite = left;
					}

				} else if (dx >= 0) {
					dir = Direction.RIGHT;
					if (action) {
						animSprite = right_action;
					} else if (gun != null) {
						animSprite = right_gun;
					} else {
						animSprite = right;
					}
				}
			} else if ((d <= squaredHeight) || ((dx * dx) < (dy * dy))) {
				if (dy < 0) {
					dir = Direction.UP;
					if (action) {
						animSprite = up_action;
					} else if (gun != null) {
						animSprite = up_gun;
					} else {
						animSprite = up;
					}

				} else if (dy >= 0) {
					dir = Direction.DOWN;
					if (action) {
						animSprite = down_action;
					} else if (gun != null) {
						animSprite = down_gun;
					} else {
						animSprite = down;
					}
				}
			}

			// player animation for walking is only updated if he is actually
			// walking
			if (walking && !action && !stunned) {
				animSprite.update();
			} else if (action) {

				// on the other hand the animation still has to be updated, if
				// he is picking up a gun or shooting fireballs, even if
				// standing still
				if (dir == Direction.DOWN) {
					animSprite = down_action;
				} else if (dir == Direction.UP) {
					animSprite = up_action;
				} else if (dir == Direction.LEFT) {
					animSprite = left_action;
				} else {
					animSprite = right_action;
				}
				animSprite.update();

				// get out of an action animation loop
				int f = animSprite.getFrame();
				if (f == 0 && animSprite.wasPlayed()) {
					action = false;
					up_action.reset();
					down_action.reset();
					left_action.reset();
					right_action.reset();
				}
			} else {
				// player standing still in the right direction
				animSprite.setFrame(0);
			}

			// checks for collision with other mobs and only moves player if no
			// collision is detected
			if (!level.mobCollision(this, x, xa, y, ya)) {
				if ((xa != 0 || ya != 0) && (!stunned)) {
					move(xa * speed, ya * speed);
					walking = true;
				} else {
					walking = false;
				}
			} else {
				stunned = true;
				stunTimer = stunTimeMax;
				walking = false;
			}

		} else {
			// if the player is dead, remove all controls except the enter key
			// and show the death
			// animation until frame==2 (lying down)
			animSprite = death;
			if (input.enter) {
				level.setIdleDeathTimerFinished();
			}
			if (animSprite.getFrame() < 2) {
				animSprite.update();
			}
		}

	}

	/**
	 * override move to keep the orientation to the crosshair
	 */
	public void move(int xchange, int ychange) {
		if (xchange != 0 && ychange != 0) {
			move(xchange, 0);
			move(0, ychange);
			return;
		}
		if (!collision(xchange, ychange)) {
			this.x += xchange;
			this.y += ychange;
		}

	}

	/**
	 * important variables set once the player is dead
	 */
	private void die() {

		hitpoints = 0;
		dead = true;
		// play the blood spatter sound
		Sound.player_mauled.playSound();
		// play the announcer sound
		Sound.mauled.playSound();
		// blood spraying from the middle of the sprite (size 32)
		level.add(new ParticleSpawner(x + 16, y + 16, 80, 1500, 0xff00ff, level));
	}

	/**
	 * clear removed projectiles from the projectile list method is overriden in
	 * case an experience system is added later
	 */
	private void clear() {
		for (int i = 0; i < projectiles.size(); i++) {
			Projectile p = projectiles.get(i);
			if (p.isRemoved()) {
				projectiles.remove(i);
			}
		}
	}

	/**
	 * get the mouse coordinates and do the math for the shoot() method if the
	 * cooldown period is over
	 */
	private void updateShooting() {

		// only shoot if the left mouse button is pressed and cooldown is over
		if (Mouse.getB() == 1 && fireRate <= 0) {
			// if player isn't carrying a gun, an action animation is shown
			if (gun == null) {
				action = true;
			}
			// get the difference mousepointer coordinates - screenmiddle
			double dx = crosshair.getX() - Game.width / 2;
			double dy = crosshair.getY() - Game.height / 2;

			// get the right angle
			double dir = Math.atan2(dy, dx);

			shoot(x, y, dir);

			// reset the cooldown, either default or with firerate of carried
			// gun
			if (gun != null) {
				fireRate = gun.getFireRate();
			} else {
				fireRate = DudeProjectile.FIRE_RATE;
			}
		}

	}

	/**
	 * pickup an item from the level if the right mouse button is pressed a
	 * collision detection for items will be performed and the player gets the
	 * first item found at his position
	 */
	private void getItem() {
		if (Mouse.getB() == 3 && (itemCooldown <= 0)) {
			Entity e = level.itemCollision(x, y);
			// if there is an item at the player location
			if (e != null) {
				// show pickup animation
				action = true;
				up_action.reset();
				down_action.reset();
				left_action.reset();
				right_action.reset();

				// reset cooldown timer / don't pick up multiple items with one
				// mouse click
				itemCooldown = cooldownTime;

				// if item is a gun, equip the gun
				if (e instanceof Gun) {
					if (this.gun != null) {
						this.gun.remove();
					}
					((Gun) e).setScale(3);
					this.gun = (Gun) e;

					// should be able to fire directly
					this.fireRate = 0;

					// play the weapon pickup sound
					Sound.handgun_pickup.playSound();
				} else if (e instanceof MedKit) {
					// heal with a medkit
					getHealed(((MedKit) e).getHealed());
					if (!Sound.healing.isPlaying()) {
						Sound.healing.playSound();
					}
				}
				// move the item to a point where it is never rendered
				((Item) e).setX(-9000);
				((Item) e).setY(-9000);
			}
		}
	}

	/**
	 * the player render method, player is shifted so the middle of the sprite
	 * is at x,y
	 */
	public void render(Screen screen) {
		// shift the coordinates, so the middle of the sprite is at x,y
		int xx = x - (spriteSize / 2);
		int yy = y - (spriteSize / 2);

		sprite = animSprite.getSprite();

		// if the player is invincible every tenth frame will be skipped to show
		// him blinking
		if (!invincible || !(invincibilityTimer % 10 == 0) || (dead)) {

			screen.renderMob(this, xx, yy);
		}
	}
	
	/**
	 * render the players crosshair if not dead
	 * @param screen
	 */
	public void renderCrosshair(Screen screen){
		if (!dead) {
			crosshair.render(screen);
		}
	}

	/**
	 * increase the hitpoints
	 * 
	 * @param amount
	 *            hitpoints healed
	 */
	public void getHealed(int amount) {
		hitpoints += amount;
		// hitpoints should never be bigger than the maximum
		if (hitpoints > maxHitpoints) {
			hitpoints = maxHitpoints;
		}
	}

	/**
	 * returns the player hitpoints
	 * 
	 * @return (int) hitpoints
	 */
	public int getHitpoints() {
		return (int) hitpoints;
	}

	/**
	 * remove amount of hitpoints from player hitpoints, set him invincible and
	 * spawn projectiles
	 * 
	 * @param dmg
	 *            amount of damage taken
	 */
	public void getHit(double dmg) {
		if (!invincible) {
			// set the player invincible
			invincible = true;
			// damage is removed from the hitpoints
			hitpoints -= dmg;
			// play the sound file
			if (soundGetHit != null && !(soundGetHit.isPlaying())) {
				soundGetHit.playSound();
			}
			// add the particleSpawner at the center of the player
			if (hitpoints >= 0) {
				level.add(new ParticleSpawner(x, y, 20, 200, 0xff00ff, level));
			}
		}
	}

	/**
	 * check if the player is invincible
	 * 
	 * @return boolean invincible
	 */
	public boolean isInvincible() {
		return invincible;
	}

	/**
	 * check if the player is dead
	 * 
	 * @return boolean dead
	 */
	public boolean isDead() {
		return dead;
	}

	/**
	 * spawn a projectile at the player location, type is dependent on gun
	 * 
	 * @param x0
	 *            x origin of projectile
	 * @param y0
	 *            y origin of projectile
	 * @param dir
	 *            the direction in form of an angle
	 */
	protected void shoot(int x0, int y0, double dir) {
		// if the player has a gun use the shoot() method of the gun
		if (this.gun != null) {
			int s = this.gun.shoot(x0, y0, dir, level);
			// if the gun is empty after the shot, unequip the gun and default
			// to fireball
			if (s <= 0) {
				fireRate = DudeProjectile.FIRE_RATE;
				gun = null;
			}
		} else {
			// if the player has no gun, spawn a fireball projectile
			Projectile p = new DudeProjectile(x0, y0, dir);
			projectiles.add(p);
			level.add(p);

			// and play the fireball sound
			Sound.fireball.playSound();
		}
	}

	/**
	 * returns the equipped gun of the player
	 * 
	 * @return Gun gun object of the player
	 */
	public Gun getGun() {
		return gun;
	}

}
