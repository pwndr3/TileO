/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;

// line 45 "../../../../../main.ump"
public class ActionCard
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ActionCard Associations
  private Deck deck;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ActionCard(Deck aDeck)
  {
    boolean didAddDeck = setDeck(aDeck);
    if (!didAddDeck)
    {
      throw new RuntimeException("Unable to create actionCard due to deck");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public Deck getDeck()
  {
    return deck;
  }

  public boolean setDeck(Deck aDeck)
  {
    boolean wasSet = false;
    //Must provide deck to actionCard
    if (aDeck == null)
    {
      return wasSet;
    }

    //deck already at maximum (32)
    if (aDeck.numberOfActionCards() >= Deck.maximumNumberOfActionCards())
    {
      return wasSet;
    }
    
    Deck existingDeck = deck;
    deck = aDeck;
    if (existingDeck != null && !existingDeck.equals(aDeck))
    {
      boolean didRemove = existingDeck.removeActionCard(this);
      if (!didRemove)
      {
        deck = existingDeck;
        return wasSet;
      }
    }
    deck.addActionCard(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Deck placeholderDeck = deck;
    this.deck = null;
    placeholderDeck.removeActionCard(this);
  }

}