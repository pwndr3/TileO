/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import java.util.*;

// line 40 "../../../../../main.ump"
public class WinTile extends Tile
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //WinTile Associations
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public WinTile(integer aX, integer aY, Game aGame, Game aGame)
  {
    super(aX, aY, aGame);
    if (aGame == null || aGame.getWinTile() != null)
    {
      throw new RuntimeException("Unable to create WinTile due to aGame");
    }
    game = aGame;
  }

  public WinTile(integer aX, integer aY, Deck aDeckForGame, Die aDieForGame, Deck aDeckForGame, Die aDieForGame)
  {
    super(aX, aY, aGame);
    game = new Game(aDeckForGame, this, aDieForGame);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public Game getGame()
  {
    return game;
  }

  public void delete()
  {
    Game existingGame = game;
    game = null;
    if (existingGame != null)
    {
      existingGame.delete();
    }
    super.delete();
  }

}