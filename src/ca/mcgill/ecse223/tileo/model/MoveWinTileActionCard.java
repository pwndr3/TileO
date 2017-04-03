/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;

import ca.mcgill.ecse223.tileo.model.Game.Mode;
import ca.mcgill.ecse223.tileo.application.TileOApplication;

import java.io.Serializable;

public class MoveWinTileActionCard extends ActionCard implements Serializable {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	/**
	 * 
	 */
	private static final long serialVersionUID = -6821101642421801142L;

	public MoveWinTileActionCard(String aInstructions, Deck aDeck) {
		super(aInstructions, aDeck);
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public Mode getActionCardGameMode() {
		return Mode.GAME_MOVEWINTILECARD;
	}

	public void delete() {
		super.delete();
	}
}
