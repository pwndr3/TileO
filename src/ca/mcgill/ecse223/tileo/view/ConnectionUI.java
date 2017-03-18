package ca.mcgill.ecse223.tileo.view;

import java.awt.Insets;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import ca.mcgill.ecse223.tileo.view.TileUI.LifeState;
import ca.mcgill.ecse223.tileo.view.TileUI.State;

public class ConnectionUI extends JToggleButton {
	private static final long serialVersionUID = 2470222051170835904L;

	public enum LifeState { EXIST, NOTEXIST }
	public enum State { SHOW, HIDE }
	public enum Type { HORIZONTAL, VERTICAL }
	
	public ConnectionUI(Type aType) {
		setPreferredSize(new java.awt.Dimension(10, 10));
		setSelected(false);
		
		setVisible(false);
		currentLifeState = LifeState.NOTEXIST;
		currentState = State.SHOW;
		type = aType;
		
		/*if(type == Type.VERTICAL) {
			try {
				setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/vert.png"))));
			} catch (IOException e) {

			}
			setMargin(new Insets(0,0,0,0));
	        setContentAreaFilled(false);
		}
		
		else {
			try {
				setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/horiz.png"))));
			} catch (IOException e) {

			}
			setMargin(new Insets(0,0,0,0));
	        setContentAreaFilled(false);
		}*/
		
		saveUIState();
	}
	
	public ConnectionUI(Type aType, int aX, int aY) {
		setPreferredSize(new java.awt.Dimension(10, 10));
		
		currentLifeState = LifeState.NOTEXIST;
		currentState = State.SHOW;
		type = aType;
		
		x = aX;
		y = aY;
	}
	
	public LifeState getLifeState() {
		return currentLifeState;
	}
	
	public void setLifeState(LifeState state) {
		currentLifeState = state;
	}
	
	public State getState() {
		return currentState;
	}
	
	public void setState(State state) {
		currentState = state;
	}
	
	public void showUI() {
		setVisible(true);
	}
	
	public void hideUI() {
		setVisible(false);
		setSelected(false);
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
	public Type getType() {
		return type;
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
	private Type type;
	private LifeState currentLifeState;
	private State currentState;
	
	private int currentUIState;
	private int previousUIState;
	
	private int x;
	private int y;
}
