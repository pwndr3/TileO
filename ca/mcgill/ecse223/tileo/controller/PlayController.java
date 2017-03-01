package ca.mcgill.ecse223.tileo.controller;

import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.model.*;
import ca.mcgill.ecse223.tileo.model.Game.Mode;

import java.util.List;

public class PlayController extends Controller {

    public void startGame(Game selectedGame) throws InvalidInputException {
       TileOApplication.setCurrentGame(selectedGame);
           Deck deck = selectedGame.getDeck();
           List <Player> allPlayers = selectedGame.getPlayers();

           if(!(selectedGame.hasWinTile())){
               throw new InvalidInputException("No win tile in the game");
           }
           for(int k=0; k<=selectedGame.numberOfPlayers(); k++){
               Player player = allPlayers.get(k);
               if(!(player.hasStartingTile())){
                   throw new InvalidInputException("One or more players do not have a starting position.");
               }
           }
           if(deck.numberOfCards()>=32){
               throw new InvalidInputException("Too many card in the deck.");
           }

           deck.shuffle();     //make shuffle method
           List<Tile> tiles = selectedGame.getTiles();
           for(int i=0; i<tiles.size(); i++){
               Tile checkTile = tiles.get(i);
               checkTile.setHasBeenVisited(false);
           }
           for(int j=0; j<=selectedGame.numberOfPlayers(); j++){
               Player thisPlayer = allPlayers.get(j);
               Tile startingTile = thisPlayer.getStartingTile();
               thisPlayer.setCurrentTile(startingTile);
               startingTile.setHasBeenVisited(true);
           }
           selectedGame.setCurrentPlayer(allPlayers.get(0));
           selectedGame.setCurrentConnectionPieces(selectedGame.getCurrentConnectionPieces());
           selectedGame.setMode(Mode.GAME);
       }
    public boolean getTopCard(){
        //Get info about the current game
        TileO tileo = TileOApplication.getTileO();
        Game game = tileo.getCurrentGame();
        Deck deck = game.getDeck();
        ActionCard actionCard = deck.getCurrentCard();

        int index = deck.indexOfCard(actionCard);
        //Return to the beginning of the deck when the counter reaches the end of the deck
        ActionCard nextTopCard;
        if(index+1<=31){
            nextTopCard = deck.getCard(index+1);
            }
        else {
            nextTopCard = deck.getCard(0);
        }

        return deck.setCurrentCard(nextTopCard);
    }
    
    public List<Tile> rollDie () {
        //Added common "Roll The Die Method" in game class for this feature and RollTheDie Action Card.
        Game currentGame = TileOApplication.getCurrentGame();
        return currentGame.rollTheDie();
    }

    //TODO Add land method to tile class.
    public void land(Tile tile) throws InvalidInputException{
        Game currentGame = TileOApplication.getCurrentGame();

        if(currentGame.indexOfTile(tile)== -1) throw new InvalidInputException("This tile does not exist in the current game.");
        tile.land();
    }
    
    public List<Tile> playRollDieActionCard() throws InvalidInputException{

            Game currentGame = TileOApplication.getCurrentGame();
    		Deck deck = currentGame.getDeck();
    		ActionCard currentCard = deck.getCurrentCard();
    	
    		if(currentCard instanceof RollDieActionCard == false)
    			throw new InvalidInputException("The current card is not a Roll Die Action Card");
    		else
    			currentCard = (RollDieActionCard) deck.getCurrentCard();
    		
    		//Roll The Die method returns possible tiles for player to move to.
    		List<Tile> tiles = currentGame.rollTheDie();
    		
    		int indexOfRollDieActionCard = deck.indexOfCard(currentCard);
    		//If the Roll Die Action Card was the last card in deck, shuffle deck and set the first card to the current card.
    		if(indexOfRollDieActionCard == 31){
    			deck.shuffle();
    			currentCard = deck.getCard(0);
    			deck.setCurrentCard(currentCard);
    		}
    		else{
    			currentCard = deck.getCard(indexOfRollDieActionCard+1);
    			deck.setCurrentCard(currentCard);
    		}
    		
    		currentGame.setMode(Mode.GAME);
    		return tiles;
    }

    //TODO See if number of connections in spare pile need to be altered.
    //PlaceConnection is a new method in game class whose code is shared by add connection in design mode.
    public void playAddConnectionActionCard(Tile tile1, Tile tile2) throws InvalidInputException{
    	
    	Game currentGame = TileOApplication.getCurrentGame();
		
    	//Check if connection pieces are still available and if tiles exist.
		if(currentGame.getCurrentConnectionPieces() == 0)
			throw new InvalidInputException("There are no more connection pieces in the spare pile");
		
		Deck deck = currentGame.getDeck();
		ActionCard currentCard = deck.getCurrentCard();
		
		//Check if action card is a Connect Tiles Action Card.
		if(currentCard instanceof ConnectTilesActionCard == false)
			throw new InvalidInputException("The current card is not a Connect Tiles Action Card");
		
		currentGame.placeConnection(tile1, tile2);
		
		Player currentPlayer = currentGame.getCurrentPlayer();
		int indexOfCurrentPlayer = currentGame.indexOfPlayer(currentPlayer);
		
		//If current player is last player, make next player the first player.
		if (indexOfCurrentPlayer == (currentGame.numberOfPlayers()-1))
			currentGame.setCurrentPlayer(currentGame.getPlayer(0));
		
		//Make it next player's turn.
		else
			currentGame.setCurrentPlayer(currentGame.getPlayer(indexOfCurrentPlayer+1));
    
		int indexOfConnectTilesActionCard = deck.indexOfCard(currentCard);
		
		//If the Connect Tiles Action Card was the last card in deck, shuffle deck and set the first card to the current card.
		if(indexOfConnectTilesActionCard == 31){
			deck.shuffle();
			currentCard = deck.getCard(0);
			deck.setCurrentCard(currentCard);
		}
		else{
			currentCard = deck.getCard(indexOfConnectTilesActionCard+1);
			deck.setCurrentCard(currentCard);
		}
		
		currentGame.setMode(Mode.GAME);
    }
	
//TODO see if number of connections must be increased after a connection is added.
    public void playRemoveConnectionActionActionCard(Connection connection) throws InvalidInputException{
    	//Get info about the current game
        TileO tileo = TileOApplication.getTileO();
        Game currentGame = tileo.getCurrentGame();
        
        Deck deck = currentGame.getDeck();
        ActionCard currentCard = deck.getCurrentCard();
        
        //Check if action card is a Remove Connection Action Card.
      	if(currentCard instanceof RemoveConnectionActionCard == false)
      		throw new InvalidInputException("The current card is not a Remove Connection Action Card");
      	else
      		currentCard = (RemoveConnectionActionCard)deck.getCurrentCard();
      	
      	//This function removes the connection.
        currentGame.eliminateConnection(connection);
        
        Player currentPlayer = currentGame.getCurrentPlayer();
		int indexOfCurrentPlayer = currentGame.indexOfPlayer(currentPlayer);

		//If current player is last player, make the first player the current player.
        if(indexOfCurrentPlayer == (currentGame.numberOfPlayers() - 1)){
            currentGame.setCurrentPlayer(currentGame.getPlayer(0));
        }
        //Set next player as current player.
        else{
            currentGame.setCurrentPlayer(currentGame.getPlayer(indexOfCurrentPlayer + 1));
        }
        
        // If current card is last card, shuffle and set new top card as current card.
        if(deck.indexOfCard(deck.getCurrentCard()) == (deck.numberOfCards() - 1)){
            deck.shuffle();
            currentCard = deck.getCard(0);
            deck.setCurrentCard(currentCard);
        }
        //If current card is not last card in deck, make next card in deck the current card.
        else{
        	currentCard = deck.getCard((deck.indexOfCard(deck.getCurrentCard())+1));
            deck.setCurrentCard(currentCard);
        }
        currentGame.setMode(Mode.GAME);
    }

    public void teleport(Tile tile) throws InvalidInputException {
        //Get info about the current game
        TileO tileo = TileOApplication.getTileO();
        Game game = tileo.getCurrentGame();
        Deck deck = game.getDeck();
        //Exception
        if(!(deck.getCurrentCard() instanceof TeleportActionCard)){
            throw new InvalidInputException("Action card is not a teleport action card");
        }

        TeleportActionCard playedCard = (TeleportActionCard) deck.getCurrentCard();

        // Play the card
        playedCard.play(tile);

        // Check if card is last card, if so set the current card to top of deck, if not set it to the next card
        if(deck.indexOfCard(playedCard) == (deck.numberOfCards()-1)){
            deck.shuffle();
            deck.setCurrentCard(deck.getCard(0));
        }
        else
            deck.setCurrentCard(deck.getCard(deck.indexOfCard(playedCard) + 1));
        
        game.setMode(Mode.GAME);
    }
}
