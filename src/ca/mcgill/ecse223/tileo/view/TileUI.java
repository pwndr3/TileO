package ca.mcgill.ecse223.tileo.view;

import javax.swing.*;

public class TileUI extends JToggleButton {
	private static final long serialVersionUID = -6618880778915250080L;

	public enum State { NORMAL, WIN, ACTION, PLAYER1, PLAYER2, PLAYER3, PLAYER4, VISITED }
	public enum LifeState { EXIST, NOTEXIST }
	
	public TileUI() {
		setFont(new java.awt.Font("Lucida Grande", 0, 8));
		setPreferredSize(new java.awt.Dimension(35, 35));
		
		currentState = State.NORMAL;
		currentLifeState = LifeState.EXIST;
		
		saveUIState();
	}
	
	public TileUI(int aX, int aY) {
		setFont(new java.awt.Font("Lucida Grande", 0, 8));
		setPreferredSize(new java.awt.Dimension(35, 35));
		
		currentState = State.NORMAL;
		
		x = aX;
		y = aY;
		
		saveUIState();
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
	
	public void showUI() {
		setVisible(true);
	}
	
	public void hideUI() {
		setVisible(false);
	}
	
	public State getCurrentState() {
		return currentState;
	}
	
	//Position-related
	public void setPosition(int aX, int aY) {
		x = aX;
		y = aY;
	}
	
	public int getUIX() {
		return x;
	}
	
	public int getUIY() {
		return y;
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
	private State currentState;
	private LifeState currentLifeState;
	
	private int currentUIState;
	private int previousUIState;
	
	private int x;
	private int y;
}
