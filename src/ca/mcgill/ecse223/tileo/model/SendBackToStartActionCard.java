/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-09e69a5 modeling language!*/

package ca.mcgill.ecse223.tileo.model;

import ca.mcgill.ecse223.tileo.model.Game.Mode;
import ca.mcgill.ecse223.tileo.application.TileOApplication;

import java.io.Serializable;

public class SendBackToStartActionCard extends ActionCard implements Serializable {
	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	/**
		 * 
		 */
	private static final long serialVersionUID = -2340601400060949267L;

	public SendBackToStartActionCard(String aInstructions, Deck aDeck) {
		super(aInstructions, aDeck);
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public Mode getActionCardGameMode() {
		return Mode.GAME_SENDBACKTOSTARTACTIONCARD;
	}

	public void delete() {
		super.delete();
	}
}
