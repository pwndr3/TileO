/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;

// line 69 "../../../../../main.ump"
public class Die
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Die Associations
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Die(Game aGame)
  {
    if (aGame == null || aGame.getDie() != null)
    {
      throw new RuntimeException("Unable to create Die due to aGame");
    }
    game = aGame;
  }

  public Die(Deck aDeckForGame, WinTile aWinTileForGame)
  {
    game = new Game(aDeckForGame, aWinTileForGame, this);
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
  }

}