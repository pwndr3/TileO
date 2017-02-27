package ca.mcgill.ecse223.tileo.controller;

import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.model.*;
import ca.mcgill.ecse223.tileo.model.Game.Mode;

import java.util.List;

public class PlayController extends Controller {

    public boolean startGame(Game game) throws InvalidInputException {
        //alex
    }
    public boolean getTopCard(){
        //alex
    }
<<<<<<< HEAD
    public public List<Tile> roll() {
        aGame=getCurrentGame();
=======
    
    //TODO Add roll() method; Add getPossibleMoves method.
    public public List<Tile> rollDie() {
        Game currentGame = TileOApplication.getCurrentGame();
        Die die = currentGame.getDie();
        int rolledNumber = die.roll();
        Player currentPlayer = currentGame.getCurrentPlayer();
        List <Tile> possibleMoves = currentPlayer.getPossibleMoves(rolledNumber);
        return possibleMoves;
>>>>>>> 65721fb7987da9d12809164ff02275d55bc14509
    }
    public boolean land(Tile tile) throws InvalidInputException{
        //karine
    }
    	//TODO check if below method works.
    	//TODO Ask if index of cards start at 0 or 1?
    	public List<Tile> playRollDieActionCard() throws InvalidInputException{
    		
    		Game currentGame = TileOApplication.getCurrentGame();
    		Deck deck = currentGame.getDeck();
    		ActionCard currentCard = deck.getCurrentCard();
    	
    		if(currentCard instanceof RollDieActionCard == false)
    			throw new InvalidInputException("The current card is not a Roll Die Action Card");
    		
    		//Play method returns possible tiles for player to move to.
    		List<Tile> tiles = currentCard.play();
    		
    		int indexOfRollDieActionCard = deck.indexOfCard(currentCard);
    		//If the Roll Die Action Card was the last card in deck, shuffle deck and set the first card to the current card.
    		if(indexOfRollDieActionCard == 32){
    			deck.shuffle();
    			currentCard = deck.getCard(1);
    			deck.setCurrentCard(currentCard);
    		}
    		else{
    			currentCard = deck.getCard(indexOfRollDieActionCard+1);
    			deck.setCurrentCard(currentCard);
    		}
    		
    		currentGame.setMode(Mode.GAME);
    		return tiles;
    	}
    	
	//TODO Finish exception for whether tiles already have a connection.
    //TODO Ask if players index starts at 1 or 0?
    public boolean playAddConnectionActionCard(Tile tile1, Tile tile2) throws InvalidInputException{
    	Game currentGame = TileOApplication.getCurrentGame();
		
    	//Check if connection pieces are still available and if tiles are adjacent.
		if(currentGame.getCurrentConnectionPieces() == 0)
			throw new InvalidInputException("There are no more connection pieces in the spare pile");
		
		else if((tile1.getY() - tile2.getY()) > 1 || (tile1.getY() - tile2.getY()) < -1 || (tile1.getX() - tile2.getX()) > 1 || (tile1.getX() - tile2.getX()) < -1){
			throw new InvalidInputException("The tiles are not adjacent");
		}
		
		else if((currentGame.indexOfTile(tile1) == -1)|| (currentGame.indexOfTile(tile2) == -1))
			throw new InvalidInputException("One or both of the tiles do not exist");
		
		Deck deck = currentGame.getDeck();
		ActionCard currentCard = deck.getCurrentCard();
		
		//Check if action card is a Connect Tiles Action Card.
		if(currentCard instanceof ConnectTilesActionCard == false)
			throw new InvalidInputException("The current card is not a Connect Tiles Action Card");
		
		Connection connectionPiece = currentGame.addConnection();
		connectionPiece.addTile(tile1);
		connectionPiece.addTile(tile2);
		
		Player currentPlayer = currentGame.getCurrentPlayer();
		int indexOfCurrentPlayer = currentGame.indexOfPlayer(currentPlayer);
		
		//If current player is last player, make next player the first player.
		if (indexOfCurrentPlayer == currentGame.numberOfPlayers())
			currentGame.setCurrentPlayer(currentGame.getPlayer(1));
		
		//Make it next player's turn.
		else
			currentGame.setCurrentPlayer(currentGame.getPlayer(indexOfCurrentPlayer+1));
    
		int indexOfConnectTilesActionCard = deck.indexOfCard(currentCard);
		
		//If the Connect Tiles Action Card was the last card in deck, shuffle deck and set the first card to the current card.
		if(indexOfConnectTilesActionCard == 32){
			deck.shuffle();
			currentCard = deck.getCard(1);
			deck.setCurrentCard(currentCard);
		}
		else{
			currentCard = deck.getCard(indexOfConnectTilesActionCard+1);
			deck.setCurrentCard(currentCard);
		}
		
		currentGame.setMode(Mode.GAME);
    }
    
    public boolean removeConnectionAction(Connection connection) throws InvalidInputException{
        //andre
    }
    public boolean teleport(Tile tile) throws InvalidPositionException {
        //christos
    }
}
