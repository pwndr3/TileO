/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import ca.mcgill.ecse223.tileo.model.Game.Mode;

// line 68 "../../../../../main.ump"
public class RollDieActionCard extends ActionCard
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RollDieActionCard(String aInstructions, Deck aDeck)
  {
    super(aInstructions, aDeck);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public List<Tile> play(){
    TileO tileO = TileOApplication.getTileO();
    Game currentGame = tileO.getCurrentGame();
    Die die = currentGame.getDie();
    int number = die.roll();

    Player currentPlayer = currentGame.getCurrentPlayer();
    List<Tile> tiles;
    tiles = currentPlayer.getPossibleMoves(number);

    return tiles;

  }

  public Mode getActionCardGameMode() {
    return Mode.GAME_ROLLDIEACTIONCARD;
  }


  public void delete()
  {
    super.delete();
  }

}
