/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import ca.mcgill.ecse223.tileo.model.Game.Mode;
import java.io.Serializable;

// line 72 "../../../../../main.ump"
public class ConnectTilesActionCard extends ActionCard implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ConnectTilesActionCard(String aInstructions, Deck aDeck)
  {
    super(aInstructions, aDeck);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public Mode getActionCardGameMode() {
    return Mode.GAME_CONNECTTILESACTIONCARD;
  }

  public void play(Tile tile1, Tile tile2) {
    //Created "placeConnection" method in game class that is used by ConnectTiles Action Card as well.
    currentGame.placeConnection(tile1, tile2);
  }

  public void delete()
  {
    super.delete();
  }

}
