package ca.mcgill.ecse223.tileo.view;

import javax.swing.*;

import ca.mcgill.ecse223.tileo.view.TileUI.State;

public class ConnectionUI extends JToggleButton {
	private static final long serialVersionUID = 2470222051170835904L;

	public enum State { EXIST, NONEXIST }
	public enum Type {HORIZONTAL, VERTICAL }
	
	public ConnectionUI(Type aType) {
		setPreferredSize(new java.awt.Dimension(10, 10));
		setSelected(true);
		
		setVisible(false);
		currentState = State.NONEXIST;
		type = aType;
	}
	
	public ConnectionUI(Type aType, int aX, int aY) {
		setPreferredSize(new java.awt.Dimension(10, 10));
		
		visible = false;
		currentState = State.NONEXIST;
		type = aType;
		
		x = aX;
		y = aY;
	}
	
	public void setVisible(boolean makeVisible) {
		visible = makeVisible;
		super.setVisible(makeVisible);
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
	
	public void setUIX(int aX) {
		x = aX;
	}
	
	public void setUIY(int aY) {
		y = aY;
	}
	
	public int getUIX() {
		return x;
	}
	
	public int getUIY() {
		return y;
	}
	//
	
	//
	private boolean visible;
	private Type type;
	private State currentState;
	
	int x;
	int y;
}
