package ca.mcgill.ecse223.tileo.view;

import javax.swing.*;

import ca.mcgill.ecse223.tileo.view.TileUI.State;

public class ConnectionUI extends JToggleButton {
	private static final long serialVersionUID = 2470222051170835904L;

	public enum LifeState { EXIST, NOTEXIST }
	public enum Type {HORIZONTAL, VERTICAL }
	
	public ConnectionUI(Type aType) {
		setPreferredSize(new java.awt.Dimension(10, 10));
		setSelected(true);
		
		setVisible(false);
		currentState = LifeState.NOTEXIST;
		type = aType;
	}
	
	public ConnectionUI(Type aType, int aX, int aY) {
		setPreferredSize(new java.awt.Dimension(10, 10));
		
		visible = false;
		currentState = LifeState.NOTEXIST;
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
	
	public LifeState getLifeState() {
		return currentState;
	}
	
	public void show() {
		setVisible(true);
	}
	
	public void hide() {
		setVisible(false);
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
	
	//Backup/restore UI state
	public void saveUIState() {
		previousUIState = currentUIState = ((getBackground() == (new java.awt.Color(0, 0, 0)) ? 1 : 0) << 1) | (isSelected() ? 1 : 0);
	}
	
	public int getUIState() {
		return currentUIState;
	}
	
	public void restoreUIState() {
		currentUIState = previousUIState;
		applyUIState();
	}
	
	private void applyUIState() {
		//Black
		if((currentUIState >> 1 & 0x1) == 1) {
			setBackground(new java.awt.Color(0,0,0));
		} else {
			setBackground(null);
		}
		
		setSelected((currentUIState & 0x1) == 1);
	}
	//
	private boolean visible;
	private Type type;
	private LifeState currentState;
	
	private int currentUIState;
	private int previousUIState;
	
	int x;
	int y;
}
