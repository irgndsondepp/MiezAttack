package com.irgndsondepp.clone.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * the mouse class handles user input via mouse
 * @author Robert
 *
 */
public class Mouse implements MouseListener, MouseMotionListener {

	private static int mouseX = -1;
	private static int mouseY = -1;
	private static int mouseB = -1;
		
	/**
	 * get the x coordinate of the mouse
	 * @return
	 */
	public static int getX() {
		return mouseX;
	}
	
	/**
	 * get the y coordinate of the mouse
	 * @return
	 */
	public static int getY() {
		return mouseY;
	}
	
	/**
	 * get the pressed mouse button
	 * @return
	 */
	public static int getB() {
		return mouseB;
	}

	/**
	 * check if the mouse was dragged and refresh coordinates
	 */
	public void mouseDragged(MouseEvent e) {
		mouseX=e.getX();
		mouseY=e.getY();

	}

	/**
	 * check if the mouse was moved and refresh coordinates
	 */
	public void mouseMoved(MouseEvent e) {
		
		mouseX = e.getX();
		mouseY = e.getY();
		

	}

	/**
	 * check for mouse clicks
	 */
	public void mouseClicked(MouseEvent e) {
		

	}

	/**
	 * check if the mouse entered a specific area
	 */
	public void mouseEntered(MouseEvent e) {

	}

	/**
	 * check if the mouse exited a specific area
	 */
	public void mouseExited(MouseEvent e) {

	}

	/**
	 * set the pressed mouse button
	 */
	public void mousePressed(MouseEvent e) {
		mouseB = e.getButton();

	}

	/**
	 * set the pressed mouse button to neutral
	 */
	public void mouseReleased(MouseEvent e) {
		mouseB = -1;

	}

}
