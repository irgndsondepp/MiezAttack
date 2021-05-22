package com.irgndsondepp.clone.entity;

import com.irgndsondepp.clone.graphics.Sprite;

/**
 * creates a medkit object
 * 
 * @author Robert
 *
 */
public class MedKit extends Item {

	private int healingValue;

	/**
	 * the medkit is created at (x,y) and has a value it heals the player
	 * 
	 * @param x
	 * @param y
	 * @param healingValue
	 */
	public MedKit(int x, int y, int healingValue) {
		super(x, y, Sprite.med_kit);
		this.healingValue = healingValue;
	}

	/**
	 * returns the healing value of the med kit
	 * 
	 * @return
	 */
	public int getHealingValue() {

		return healingValue;
	}

	/**
	 * returns the healing value and removes the object
	 * 
	 * @return
	 */
	public int getHealed() {
		remove();
		return healingValue;
	}

}
