package ca.mcgill.ecse223.tileo.view;

import javax.swing.*;

public class TileUI extends JToggleButton {
	private static final long serialVersionUID = -6618880778915250080L;

	public enum State { NORMAL, WIN, ACTION, PLAYER1, PLAYER2, PLAYER3, PLAYER4, VISITED }
	public enum LifeState { EXIST, NOTEXIST }
	
	public TileUI() {
		setFont(new java.awt.Font("Lucida Grande", 0, 8));
		setPreferredSize(new java.awt.Dimension(35, 35));
		
		visible = true;
		currentState = State.NORMAL;
		currentLifeState = LifeState.EXIST;
	}
	
	public TileUI(int aX, int aY) {
		setFont(new java.awt.Font("Lucida Grande", 0, 8));
		setPreferredSize(new java.awt.Dimension(35, 35));
		
		visible = true;
		currentState = State.NORMAL;
		
		x = aX;
		y = aY;
	}
	
	public void makeExist(boolean exist) {
		if(exist) {
			currentLifeState = LifeState.EXIST;
		} else {
			currentLifeState = LifeState.NOTEXIST;
		}
	}
	
	public LifeState getLifeState() {
		return currentLifeState;
	}
	
	public void show() {
		setVisible(true);
	}
	
	public void hide() {
		setVisible(false);
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
	
	//Backup/restore UI state
	public void saveUIState() {
		previousUIState = currentUIState = (isVisible() ? 1 : 0) << 1 + (isSelected() ? 1 : 0);
	}
	
	public int getUIState() {
		return currentUIState;
	}
	
	public void restoreUIState() {
		currentUIState = previousUIState;
		applyUIState();
	}
	
	private void applyUIState() {
		//TODO : Color
		setVisible((currentUIState >> 1 & 0x1) == 1);
		setSelected((currentUIState & 0x1) == 1);
	}
	//
	private boolean visible;
	private State currentState;
	private LifeState currentLifeState;
	
	private int currentUIState;
	private int previousUIState;
	
	int x;
	int y;
}
