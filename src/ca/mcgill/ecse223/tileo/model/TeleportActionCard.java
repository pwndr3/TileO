/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import ca.mcgill.ecse223.tileo.model.Game.Mode;
import ca.mcgill.ecse223.tileo.application.TileOApplication;

import java.io.Serializable;

// line 80 "../../../../../main.ump"
public class TeleportActionCard extends ActionCard implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------
  private static final long serialVersionUID = 6483241928348L;
  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TeleportActionCard(String aInstructions, Deck aDeck)
  {
    super(aInstructions, aDeck);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public Mode getActionCardGameMode() {
    return Mode.GAME_TELEPORTACTIONCARD;
  }

  public void delete()
  {
    super.delete();
  }

  public void play(Tile tile){
      tile.land();
  }
}
