/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import ca.mcgill.ecse223.tileo.model.Game.Mode;
import java.io.Serializable;

// line 76 "../../../../../main.ump"
public class RemoveConnectionActionCard extends ActionCard implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RemoveConnectionActionCard(String aInstructions, Deck aDeck)
  {
    super(aInstructions, aDeck);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public Mode getActionCardGameMode() {
    return Mode.GAME_REMOVECONNECTIONACTIONCARD;
  }

  public void play(Connection connection){
    public void removeConnection(Connection aConnection) {
      //Below is method in game class that removes connection.
      game.eliminateConnection(aConnection);
    }
  }

  public void delete()
  {
    super.delete();
  }

}
