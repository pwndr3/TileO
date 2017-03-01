/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import java.util.*;
import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.model.Game.Mode;


// line 46 "../../../../../main.ump"
public class NormalTile extends Tile
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

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
        Game currentGame= TileOApplication.getGame();
        Player currentPlayer = currentGame.getCurrentPlayer();
        currentPlayer.setCurrentTile(tile);
        if(currentGame.indexOfPlayer(currentPlayer) == (currentGame.numberOfPlayer() - 1)) {
            currentGame.setCurrentPlayer(currentGame.getPlayer(0));}
        else {
            currentGame.setCurrentPlayer(currentGame.getPlayer(currentGame.indexOfPlayer(currentPlayer) + 1));

        }
        tile.setHasBeenVisited(true);

        try{
            currentGame.setMode(Mode.GAME);
        }catch(RuntimeException e)

        {
            throw new InvalidInputException(e.getMessage());
        }
    }



    public void delete()
  {
    super.delete();
  }

}
