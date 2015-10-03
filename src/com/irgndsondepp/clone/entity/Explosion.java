package com.irgndsondepp.clone.entity;

import com.irgndsondepp.clone.audio.Sound;
import com.irgndsondepp.clone.graphics.AnimatedSprite;
import com.irgndsondepp.clone.graphics.Screen;
import com.irgndsondepp.clone.graphics.SpriteSheet;
import com.irgndsondepp.clone.level.Level;

/**
 * creates an explosion. an explosion is a subclass of entity with a fixed x and
 * y position and a default animation. also a life time, after which it is
 * removed. explosions also have a fixed radius to explode mobs.
 * 
 * @author Robert
 *
 */
public class Explosion extends Entity {

	private AnimatedSprite animSprite = new AnimatedSprite(
			SpriteSheet.explosion, 32, 32, 3);
	private int life = 30;
	private int damage;
	private final int radius = 75;
	public final int SPRITE_SIZE = animSprite.SIZE;

	/**
	 * create an explosion.
	 * 
	 * @param x
	 *            is the x position in the level
	 * @param y
	 *            is the y position in the level
	 * @param damage
	 *            is the damage (because mobs are exploded by the explosion and
	 *            not by the rocket projectile)
	 * @param level
	 *            the level
	 */
	public Explosion(int x, int y, int damage, Level level) {
		this.x = x;
		this.y = y;
		this.damage = damage;
		this.init(level);
		Sound.explosion.playSound();
		level.explodeStuff(this);
	}

	/**
	 * update the life and the animation. if the end of the lifetime is reached,
	 * the object is removed.
	 */
	public void update() {
		animSprite.update();
		life--;
		if (life < 0) {
			this.remove();
		}
	}

	/**
	 * renders the object on the screen using the renderSprite method
	 */
	public void render(Screen screen) {
		screen.renderSprite(x - SPRITE_SIZE / 2, y - SPRITE_SIZE / 2,
				animSprite.getSprite(), true);

	}

	/**
	 * returns the damage value
	 * 
	 * @return
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * returns the radius value
	 * 
	 * @return
	 */
	public int getRadius() {
		return radius;
	}

}
