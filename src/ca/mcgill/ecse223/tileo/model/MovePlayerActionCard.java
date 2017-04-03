/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;

import ca.mcgill.ecse223.tileo.model.Game.Mode;
import ca.mcgill.ecse223.tileo.application.TileOApplication;

import java.io.Serializable;

// line 80 "../../../../../main.ump"
public class MovePlayerActionCard extends ActionCard implements Serializable {

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	/**
	 * 
	 */
	private static final long serialVersionUID = -3033605628466604982L;

	public MovePlayerActionCard(String aInstructions, Deck aDeck) {
		super(aInstructions, aDeck);
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public Mode getActionCardGameMode() {
		return Mode.GAME_MOVEPLAYERCARD;
	}

	public void delete() {
		super.delete();
	}
}
