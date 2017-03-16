/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;

import java.util.*;
import ca.mcgill.ecse223.tileo.application.TileOApplication;

import java.io.Serializable;

public class ActionTile extends Tile implements Serializable {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// ActionTile Attributes
	private int inactivityPeriod;
	private int turnsUntilActive;

	private static final long serialVersionUID = 1298712387189L;

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// ActionTile State Machines
	public enum ActionTileStatus {
		Active, Inactive
	}

	private ActionTileStatus actionTileStatus;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public ActionTile(int aX, int aY, Game aGame, int aInactivityPeriod) {
		super(aX, aY, aGame);
		inactivityPeriod = aInactivityPeriod;
		turnsUntilActive = 0;
		setActionTileStatus(ActionTileStatus.Active);
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public String getActionTileStatusFullName() {
		String answer = actionTileStatus.toString();
		return answer;
	}

	public ActionTileStatus getActionTileStatus() {
		return actionTileStatus;
	}

	public boolean deactivate() {
		boolean wasEventProcessed = false;

		ActionTileStatus aActionTileStatus = actionTileStatus;
		switch (aActionTileStatus) {
		case Active:
			if (getInactivityPeriod() > 0) {
				setTurnsUntilActive(getInactivityPeriod() + 1);
				setActionTileStatus(ActionTileStatus.Inactive);
				wasEventProcessed = true;
				break;
			}
			break;
		default:
			// Other states do respond to this event
		}

		return wasEventProcessed;
	}

	public boolean takeTurn() {
		boolean wasEventProcessed = false;

		ActionTileStatus aActionTileStatus = actionTileStatus;
		switch (aActionTileStatus) {
		case Inactive:
			if (getTurnsUntilActive() > 1) {
				// line 13 "../../../../../ActionTile.ump"
				setTurnsUntilActive(getTurnsUntilActive() - 1);
				setActionTileStatus(ActionTileStatus.Inactive);
				wasEventProcessed = true;
				break;
			}
			if (getTurnsUntilActive() <= 1) {
				// line 17 "../../../../../ActionTile.ump"
				setTurnsUntilActive(0);
				setActionTileStatus(ActionTileStatus.Active);
				wasEventProcessed = true;
				break;
			}
			break;
		default:
			// Other states do respond to this event
		}

		return wasEventProcessed;
	}

	private void setActionTileStatus(ActionTileStatus aActionTileStatus) {
		actionTileStatus = aActionTileStatus;
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public void land() {
		Game currentGame = TileOApplication.getTileO().getCurrentGame();
		Player currentPlayer = currentGame.getCurrentPlayer();
		currentPlayer.setCurrentTile(this);

		setHasBeenVisited(true);
	}

	public boolean setTurnsUntilActive(int aTurnsUntilActive) {
		boolean wasSet = false;
		turnsUntilActive = aTurnsUntilActive;
		wasSet = true;
		return wasSet;
	}

	public int getInactivityPeriod() {
		return inactivityPeriod;
	}

	public int getTurnsUntilActive() {
		return turnsUntilActive;
	}

	public void delete() {
		super.delete();
	}

	public String toString() {
		String outputString = "";
		return super.toString() + "[" + "inactivityPeriod" + ":" + getInactivityPeriod() + "," + "turnsUntilActive"
				+ ":" + getTurnsUntilActive() + "]" + outputString;
	}
}