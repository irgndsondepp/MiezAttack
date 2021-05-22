package com.irgndsondepp.clone.entity.particle;

import com.irgndsondepp.clone.entity.Entity;
import com.irgndsondepp.clone.graphics.Screen;
import com.irgndsondepp.clone.graphics.Sprite;

/**
 * creates a particle at a specific location. a particle has also a z-value and
 * kinda jumps
 * 
 * @author Robert
 *
 */
public class Particle extends Entity {

	private int life;

	protected double xx, yy, zz;
	protected double xa, ya, za;

	/**
	 * creates a particle at x,y which will stay alive for a specific time
	 * 
	 * @param x
	 *            is the x coordinate
	 * @param y
	 *            is the y coordinate
	 * @param life
	 *            is the life time in frames
	 */
	public Particle(int x, int y, int life) {
		sprite = Sprite.particle_normal;
		this.life = life + random.nextInt(20);
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		this.zz = 10.0 + random.nextFloat();
		this.xa = random.nextGaussian();
		this.ya = random.nextGaussian();
	}

	/**
	 * creates a particle at x,y which will stay alive for a specific time and
	 * have a different color than the standard one
	 * 
	 * @param x
	 *            is the x coordinate
	 * @param y
	 *            is the y coordinate
	 * @param life
	 *            is the life time in frames
	 * @param col
	 *            is the color (in hexcode)
	 */
	public Particle(int x, int y, int life, int col) {
		sprite = new Sprite(2, 2, col);
		this.life = life + random.nextInt(40);
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		this.zz = (float) this.life / 5 + random.nextFloat();
		this.xa = random.nextGaussian();
		this.ya = random.nextGaussian();
	}

	/**
	 * updates the particle location and removes it if the life time is reached
	 */
	public void update() {

		if (zz < 0) {
			zz = 0;
			za *= -0.5;
			xa *= 0.5;
			ya *= 0.5;
		}
		this.za -= 0.15;
		this.xx += xa;
		this.yy += ya;
		this.zz += za;

		life -= 1;
		if (life < 0) {
			this.remove();
		}
	}

	/**
	 * renders the particle on screen
	 */
	public void render(Screen screen) {
		screen.renderSprite((int) xx, (int) yy - (int) zz, sprite, true);
	}

}
