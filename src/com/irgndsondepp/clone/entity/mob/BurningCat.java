package com.irgndsondepp.clone.entity.mob;

import com.irgndsondepp.clone.audio.Sound;
import com.irgndsondepp.clone.entity.spawner.ParticleSpawner;
import com.irgndsondepp.clone.graphics.AnimatedSprite;
import com.irgndsondepp.clone.graphics.Screen;
import com.irgndsondepp.clone.graphics.Sprite;
import com.irgndsondepp.clone.graphics.SpriteSheet;

/**
 * the burning cat class extending dummy
 * 
 * @author Robert
 *
 */
public class BurningCat extends Dummy {

	protected AnimatedSprite down = new AnimatedSprite(
			SpriteSheet.burning_cat_down, 16, 16, 3);
	protected AnimatedSprite up = new AnimatedSprite(
			SpriteSheet.burning_cat_up, 16, 16, 3);
	protected AnimatedSprite left = new AnimatedSprite(
			SpriteSheet.burning_cat_left, 16, 16, 3);
	protected AnimatedSprite right = new AnimatedSprite(
			SpriteSheet.burning_cat_right, 16, 16, 3);

	/**
	 * a burning cat is a non agressive dummy cat, with a short life time
	 * running around randomly. it's created at (x,y) in the level. the life
	 * time is given by an amount of hitpoints, which will decrease
	 * automatically. a burning cat still creates a collision with projectiles
	 * and other cats
	 * 
	 * @param x
	 * @param y
	 */
	public BurningCat(int x, int y) {
		super(x, y);
		this.x = x;
		this.y = y;
		sprite = Sprite.dummy_default;
		int n = random.nextInt(4);
		if (n == 0) {
			this.animSprite = left;
			this.xa = -1;
		} else if (n == 1) {
			this.animSprite = right;
			this.xa = 1;
		} else if (n == 2) {
			this.animSprite = up;
			this.ya = -1;
		} else {
			this.animSprite = down;
			this.ya = 1;
		}

		this.hitpoints = 180;
	}
	
	public void render(Screen screen) {
		super.render(screen);
		screen.AddDynamicLighting(this.x, this.y, 30, 35);		
	}

	/**
	 * decrease the hitpoints and remove the cat if dead. otherwise randomly
	 * move the cat around
	 */
	public void update() {
		time++;
		hitpoints--;

		if (this.hitpoints <= 0) {
			this.remove();
			Sound.cat_die.playSound();
			level.add(new ParticleSpawner(x + 8, y - 8, 15, 150, 0xff00ff,
					level));
		}

		if (time % (random.nextInt(30) + 30) == 0) {
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
			move(xa, ya);
			walking = true;
		}
	}

}
