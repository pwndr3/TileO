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
    public void land(Tile tile) throws InvalidInputException{
        //karine
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

    //TODO Finish exception for whether tiles already have a connection.
    //TODO See if number of connections in spare pile need to be altered.
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

    public boolean removeConnectionAction(Connection connection) throws InvalidInputException{
        //Get info about the current game
        TileO tileo = TileOApplication.getTileO();
        Game game = tileo.getCurrentGame();
        Deck deck = game.getDeck();
        RemoveConnectionActionCard card = deck.getCurrentCard();
        play(connection);

        if(game.indexOfPlayer(currentPlayer) == game.numberOfPlayers()){
            game.setCurrentPlayer(player.getWithNumber(0));
        }
        else{
            game.setCurrentPlayer(game.getPlayer(game.indexOfPlayers(currentPlayer) + 1));
        }
        // Check if card is last card, if so set the current card to top of deck, if not set it to the next card
        if(deck.indexOfCard(playedCard) == deck.numberOfCards()){
            deck.shuffle();
        }
        else{
            deck.setCurrentCard(deck.getCard(deck.indexOfCard(playedCard) + 1));
        }
        return game.setMode(Mode.GAME);
    }

    }
    public boolean teleport(Tile tile) throws InvalidInputException {
        //Get info about the current game
        TileO tileo = TileOApplication.getTileO();
        Game game = tileo.getCurrentGame();
        Deck deck = game.getDeck();
        //Exception
        if(!(deck.getCurrentCard() instanceof TeleportActionCard)){
            throw new InvalidInputException("Card is not a telport action card.")
        }

        TeleportActionCard playedCard = deck.getCurrentCard();

        // Play the card
        playedCard.play(tile);

        // Check if card is last card, if so set the current card to top of deck, if not set it to the next card
        if(deck.indexOfCard(playedCard) == deck.numberOfCards()){
            deck.shuffle();
        }
        else
            deck.setCurrentCard(deck.getCard(deck.indexOfCard(playedCard) + 1));
    }
        return game.setMode(Mode.GAME); //not sure about the set mode input
}
