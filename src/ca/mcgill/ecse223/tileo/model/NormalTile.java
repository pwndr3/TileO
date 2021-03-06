/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import java.util.*;
import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.model.Game.Mode;
import ca.mcgill.ecse223.tileo.controller.InvalidInputException;
import ca.mcgill.ecse223.tileo.application.TileOApplication;

import java.io.Serializable;

// line 46 "../../../../../main.ump"
public class NormalTile extends Tile implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------
  private static final long serialVersionUID = 6483246234823L;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public NormalTile(int aX, int aY, Game aGame)
  {
    super(aX, aY, aGame);
  }

  //------------------------
  // INTERFACE
  //------------------------

    public void land(){
        Game currentGame = TileOApplication.getTileO().getCurrentGame();
        Player currentPlayer = currentGame.getCurrentPlayer();
        currentPlayer.setCurrentTile(this);
        
        setHasBeenVisited(true);
    }



    public void delete()
  {
    super.delete();
  }

}
