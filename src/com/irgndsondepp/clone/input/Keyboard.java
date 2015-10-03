package com.irgndsondepp.clone.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * the keyboard class handles user input from the keyboard
 * @author Robert
 *
 */
public class Keyboard implements KeyListener {

	private boolean[] keys = new boolean[120];
	public boolean up, down, left, right, exit, enter;

	/**
	 * the update method checks for pressed keys and relates them to different actions
	 */
	public void update() {
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		enter = keys[KeyEvent.VK_ENTER] || keys[KeyEvent.VK_SPACE];
		exit = keys[KeyEvent.VK_ESCAPE];
	}

	/**
	 * check for pressed keys
	 */
	public void keyPressed(KeyEvent e) {

		keys[e.getKeyCode()] = true;

	}

	/**
	 * check for released keys
	 */
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;

	}

	/**
	 * check for typed keys
	 */
	public void keyTyped(KeyEvent e) {

	}

}
