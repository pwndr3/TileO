/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;

// line 12 "../../../../../main.ump"
public class Player
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Player Attributes
  private String pieceColor;
  private boolean canMove;

  //Player Associations
  private Tile currentTile;
  private Tile startingTile;
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(String aPieceColor, Tile aCurrentTile, Tile aStartingTile, Game aGame)
  {
    pieceColor = aPieceColor;
    canMove = true;
    if (!setCurrentTile(aCurrentTile))
    {
      throw new RuntimeException("Unable to create Player due to aCurrentTile");
    }
    if (!setStartingTile(aStartingTile))
    {
      throw new RuntimeException("Unable to create Player due to aStartingTile");
    }
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create player due to game");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPieceColor(String aPieceColor)
  {
    boolean wasSet = false;
    pieceColor = aPieceColor;
    wasSet = true;
    return wasSet;
  }

  public boolean setCanMove(boolean aCanMove)
  {
    boolean wasSet = false;
    canMove = aCanMove;
    wasSet = true;
    return wasSet;
  }

  public String getPieceColor()
  {
    return pieceColor;
  }

  public boolean getCanMove()
  {
    return canMove;
  }

  public Tile getCurrentTile()
  {
    return currentTile;
  }

  public Tile getStartingTile()
  {
    return startingTile;
  }

  public Game getGame()
  {
    return game;
  }

  public boolean setCurrentTile(Tile aNewCurrentTile)
  {
    boolean wasSet = false;
    if (aNewCurrentTile != null)
    {
      currentTile = aNewCurrentTile;
      wasSet = true;
    }
    return wasSet;
  }

  public boolean setStartingTile(Tile aNewStartingTile)
  {
    boolean wasSet = false;
    if (aNewStartingTile != null)
    {
      startingTile = aNewStartingTile;
      wasSet = true;
    }
    return wasSet;
  }

  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    //Must provide game to player
    if (aGame == null)
    {
      return wasSet;
    }

    //game already at maximum (4)
    if (aGame.numberOfPlayers() >= Game.maximumNumberOfPlayers())
    {
      return wasSet;
    }
    
    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      boolean didRemove = existingGame.removePlayer(this);
      if (!didRemove)
      {
        game = existingGame;
        return wasSet;
      }
    }
    game.addPlayer(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    currentTile = null;
    startingTile = null;
    Game placeholderGame = game;
    this.game = null;
    placeholderGame.removePlayer(this);
  }


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+
            "pieceColor" + ":" + getPieceColor()+ "," +
            "canMove" + ":" + getCanMove()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "currentTile = "+(getCurrentTile()!=null?Integer.toHexString(System.identityHashCode(getCurrentTile())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "startingTile = "+(getStartingTile()!=null?Integer.toHexString(System.identityHashCode(getStartingTile())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null")
     + outputString;
  }
}