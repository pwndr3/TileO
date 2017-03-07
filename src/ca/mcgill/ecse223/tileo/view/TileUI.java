package ca.mcgill.ecse223.tileo.view;

import javax.swing.*;

public class TileUI extends JToggleButton {
	private static final long serialVersionUID = -6618880778915250080L;

	public enum State { NORMAL, WIN, ACTION, PLAYER1, PLAYER2, PLAYER3, PLAYER4, VISITED }
	
	public TileUI() {
		setFont(new java.awt.Font("Lucida Grande", 0, 8));
		setPreferredSize(new java.awt.Dimension(35, 35));
		
		visible = true;
		currentState = State.NORMAL;
	}
	
	public TileUI(int aX, int aY) {
		setFont(new java.awt.Font("Lucida Grande", 0, 8));
		setPreferredSize(new java.awt.Dimension(35, 35));
		
		visible = true;
		currentState = State.NORMAL;
		
		x = aX;
		y = aY;
	}
	
	public void setVisible(boolean makeVisible) {
		visible = makeVisible;
		super.setVisible(visible);
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public State getCurrentState() {
		return currentState;
	}
	
	//Position-related
	public void setPosition(int aX, int aY) {
		x = aX;
		y = aY;
	}
	//
	
	//
	private boolean visible;
	private State currentState;
	
	int x;
	int y;
}
