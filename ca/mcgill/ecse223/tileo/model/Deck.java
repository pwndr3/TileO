/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import java.util.*;

// line 78 "../../../../../main.ump"
public class Deck
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Deck Associations
  private Game game;
  private List<ActionCard> actionCards;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Deck(Game aGame)
  {
    if (aGame == null || aGame.getDeck() != null)
    {
      throw new RuntimeException("Unable to create Deck due to aGame");
    }
    game = aGame;
    actionCards = new ArrayList<ActionCard>();
  }

  public Deck(WinTile aWinTileForGame, Die aDieForGame)
  {
    game = new Game(this, aWinTileForGame, aDieForGame);
    actionCards = new ArrayList<ActionCard>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public Game getGame()
  {
    return game;
  }

  public ActionCard getActionCard(int index)
  {
    ActionCard aActionCard = actionCards.get(index);
    return aActionCard;
  }

  public List<ActionCard> getActionCards()
  {
    List<ActionCard> newActionCards = Collections.unmodifiableList(actionCards);
    return newActionCards;
  }

  public int numberOfActionCards()
  {
    int number = actionCards.size();
    return number;
  }

  public boolean hasActionCards()
  {
    boolean has = actionCards.size() > 0;
    return has;
  }

  public int indexOfActionCard(ActionCard aActionCard)
  {
    int index = actionCards.indexOf(aActionCard);
    return index;
  }

  public static int minimumNumberOfActionCards()
  {
    return 0;
  }

  public static int maximumNumberOfActionCards()
  {
    return 32;
  }

  public ActionCard addActionCard()
  {
    if (numberOfActionCards() >= maximumNumberOfActionCards())
    {
      return null;
    }
    else
    {
      return new ActionCard(this);
    }
  }

  public boolean addActionCard(ActionCard aActionCard)
  {
    boolean wasAdded = false;
    if (actionCards.contains(aActionCard)) { return false; }
    if (numberOfActionCards() >= maximumNumberOfActionCards())
    {
      return wasAdded;
    }

    Deck existingDeck = aActionCard.getDeck();
    boolean isNewDeck = existingDeck != null && !this.equals(existingDeck);
    if (isNewDeck)
    {
      aActionCard.setDeck(this);
    }
    else
    {
      actionCards.add(aActionCard);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeActionCard(ActionCard aActionCard)
  {
    boolean wasRemoved = false;
    //Unable to remove aActionCard, as it must always have a deck
    if (!this.equals(aActionCard.getDeck()))
    {
      actionCards.remove(aActionCard);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addActionCardAt(ActionCard aActionCard, int index)
  {  
    boolean wasAdded = false;
    if(addActionCard(aActionCard))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfActionCards()) { index = numberOfActionCards() - 1; }
      actionCards.remove(aActionCard);
      actionCards.add(index, aActionCard);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveActionCardAt(ActionCard aActionCard, int index)
  {
    boolean wasAdded = false;
    if(actionCards.contains(aActionCard))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfActionCards()) { index = numberOfActionCards() - 1; }
      actionCards.remove(aActionCard);
      actionCards.add(index, aActionCard);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addActionCardAt(aActionCard, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Game existingGame = game;
    game = null;
    if (existingGame != null)
    {
      existingGame.delete();
    }
    while (actionCards.size() > 0)
    {
      ActionCard aActionCard = actionCards.get(actionCards.size() - 1);
      aActionCard.delete();
      actionCards.remove(aActionCard);
    }
    
  }

}