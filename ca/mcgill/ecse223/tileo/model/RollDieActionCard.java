/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import ca.mcgill.ecse223.tileo.model.Game.Mode;
import java.io.Serializable;

import java.io.Serializable;

// line 68 "../../../../../main.ump"
public class RollDieActionCard extends ActionCard implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------
  private static final long serialVersionUID = 3487593045723L;
  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RollDieActionCard(String aInstructions, Deck aDeck)
  {
    super(aInstructions, aDeck);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public Mode getActionCardGameMode() {
    return Mode.GAME_ROLLDIEACTIONCARD;
  }


  public void delete()
  {
    super.delete();
  }

}
