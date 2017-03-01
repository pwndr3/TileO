/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;

import java.util.*;
import ca.mcgill.ecse223.tileo.application.TileOApplication;

import java.io.Serializable;

// line 39 "../../../../../main.ump"
public class ActionTile extends Tile
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ActionTile Attributes
  private int inactivityPeriod;
  private int turnsUntilActive;
  
  private static final long serialVersionUID = 1298712387189L;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ActionTile(int aX, int aY, Game aGame, int aInactivityPeriod)
  {
    super(aX, aY, aGame);
    inactivityPeriod = aInactivityPeriod;
    turnsUntilActive = 0;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void land(){
    TileO tileo = TileOApplication.getTileO();
    Game currentGame= tileo.getCurrentGame();
    Player currentPlayer = currentGame.getCurrentPlayer();
    currentPlayer.setCurrentTile(this);

    currentGame.setCurrentPlayer(currentGame.getPlayer(currentGame.indexOfPlayer(currentPlayer)+1));
    this.setHasBeenVisited(true);
    Deck deck = currentGame.getDeck();
    ActionCard currentCard = deck.getCurrentCard();
    currentGame.setMode(currentCard.getActionCardGameMode());
  }



  public boolean setTurnsUntilActive(int aTurnsUntilActive)
  {
    boolean wasSet = false;
    turnsUntilActive = aTurnsUntilActive;
    wasSet = true;
    return wasSet;
  }

  public int getInactivityPeriod()
  {
    return inactivityPeriod;
  }

  public int getTurnsUntilActive()
  {
    return turnsUntilActive;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+
            "inactivityPeriod" + ":" + getInactivityPeriod()+ "," +
            "turnsUntilActive" + ":" + getTurnsUntilActive()+ "]"
     + outputString;
  }
}
