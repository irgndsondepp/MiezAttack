package com.irgndsondepp.clone.entity.mob;

import com.irgndsondepp.clone.audio.Sound;
import com.irgndsondepp.clone.entity.spawner.ParticleSpawner;
import com.irgndsondepp.clone.graphics.AnimatedSprite;
import com.irgndsondepp.clone.graphics.Screen;
import com.irgndsondepp.clone.graphics.Sprite;
import com.irgndsondepp.clone.graphics.SpriteSheet;

/**
 * create a dummy cat, that is non agressive. a dummy cat has an animated sprite
 * and can be burned.
 * 
 * @author Robert
 *
 */
public class Dummy extends Mob {

	protected AnimatedSprite down = new AnimatedSprite(SpriteSheet.cat_down,
			16, 16, 3);
	protected AnimatedSprite up = new AnimatedSprite(SpriteSheet.cat_up, 16,
			16, 3);
	protected AnimatedSprite left = new AnimatedSprite(SpriteSheet.cat_left,
			16, 16, 3);
	protected AnimatedSprite right = new AnimatedSprite(SpriteSheet.cat_right,
			16, 16, 3);
	protected AnimatedSprite idle = new AnimatedSprite(SpriteSheet.cat_idle,
			16, 16, 3);

	protected AnimatedSprite animSprite = idle;

	protected int time = 0;
	protected int xa = 0;
	protected int ya = 0;
	private boolean burned = false;

	/**
	 * create a dummy cat at (x,y) in tile coordinates in the level
	 * 
	 * @param x
	 * @param y
	 */
	public Dummy(int x, int y) {
		this.x = x * 16;
		this.y = y * 16;
		speed = 1;
		sprite = Sprite.dummy_default;
		scale = 1;
		spriteSize = sprite.SIZE * scale;
		idle.setFrameRate(20);
		maxHitpoints = 20;
		hitpoints = maxHitpoints;

		// annoying as fuck
		// this.soundGetHit = Sound.cat_meow2;
		this.soundDie = Sound.cat_die;
		this.soundHit = Sound.cat_meow1;

		// create Hitbox
		leftEdge = 4;
		rightEdge = 4;
		topEdge = spriteSize / 2;
		bottomEdge = 0;
	}

	/**
	 * randomly move the cat, or remove the cat if it is dead
	 */
	public void update() {
		time++;

		if (this.hitpoints <= 0) {
			this.remove();
			Sound.cat_die.playSound();
			level.add(new ParticleSpawner(x, y, 50, 500, 0xff00ff, level));
		}

		if (time % (random.nextInt(50) + 30) == 0) {
			xa = random.nextInt(3) - 1;
			ya = random.nextInt(3) - 1;
			if (random.nextInt(5) == 0) {
				xa = 0;
				ya = 0;
			}
		}
		animSprite.update();
		if (xa < 0) {
			animSprite = left;
			dir = Direction.LEFT;
		} else if (xa > 0) {

			animSprite = right;
			dir = Direction.RIGHT;
		}

		if (ya < 0) {

			animSprite = up;
			dir = Direction.UP;
		} else if (ya > 0) {

			animSprite = down;
			dir = Direction.DOWN;
		}

		if (xa != 0 || ya != 0) {
			move(xa * speed, ya * speed);
			walking = true;
		} else {
			walking = false;
			animSprite = idle;
			dir = Direction.IDLE;
		}

	}

	/**
	 * render the cat on the screen using the renderMob method shifted half it's
	 * sprite to the left and half it's sprite up (centerd)
	 */
	public void render(Screen screen) {
		int xx = x - (spriteSize / 2);
		int yy = y - (spriteSize / 2);
		sprite = animSprite.getSprite();
		screen.renderMob(this, xx, yy);

	}

	/**
	 * set bruned as true
	 */
	public void setBurned() {
		burned = true;
	}

	/**
	 * get the value of the burned boolean
	 * 
	 * @return
	 */
	public boolean isBurned() {
		return burned;
	}

}
