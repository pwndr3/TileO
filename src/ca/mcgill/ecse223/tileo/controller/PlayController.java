package ca.mcgill.ecse223.tileo.controller;

import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.model.*;
import ca.mcgill.ecse223.tileo.model.Game.Mode;
import ca.mcgill.ecse223.tileo.view.PopUpManager;
import ca.mcgill.ecse223.tileo.view.TileOPlayUI;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlayController {

	public PlayController(TileOPlayUI aUi, Game aGame) {
		game = aGame;
		ui = aUi;
	}

	public void startGame() throws Exception {
		Deck deck = game.getDeck();
		List<Player> allPlayers = game.getPlayers();

		if (!game.hasWinTile()) {
			throw new InvalidInputException("No win tile in the game");
		}
		for (int k = 0; k < game.numberOfPlayers(); k++) {
			Player player = allPlayers.get(k);
			if (!player.hasStartingTile()) {
				throw new InvalidInputException("One or more players do not have a starting position.");
			}
		}

		TileOApplication.getTileO().setCurrentGame(game);

		List<Tile> tiles = game.getTiles();
		for (int i = 0; i < tiles.size(); i++) {
			Tile checkTile = tiles.get(i);
			checkTile.setHasBeenVisited(false);
		}
		for (int j = 0; j < game.numberOfPlayers(); j++) {
			Player thisPlayer = allPlayers.get(j);
			Tile startingTile = thisPlayer.getStartingTile();
			thisPlayer.setCurrentTile(startingTile);
			startingTile.setHasBeenVisited(true);
		}
		game.setCurrentPlayer(allPlayers.get(0));
		
		deck.shuffle();
	}

	public ActionCard getTopCard() throws Exception {
		Deck deck = game.getDeck();
		ActionCard actionCard = deck.getCurrentCard();

		int index = deck.indexOfCard(actionCard);
		// Return to the beginning of the deck when the counter reaches the end
		// of the deck
		ActionCard nextTopCard;
		int numOfCards = deck.numberOfCards();

		if (index + 1 < numOfCards) {
			nextTopCard = deck.getCard(index + 1);
		} else {
			deck.shuffle();
			nextTopCard = deck.getCard(0);
		}

		deck.setCurrentCard(nextTopCard);

		return nextTopCard;
	}

	public List<Tile> rollDie() {
		int rolledNumber = game.rollDie();
		
		new PopUpManager(ui).rollDie(rolledNumber);
		
		Player currentPlayer = game.getCurrentPlayer();
		List<Tile> possibleMoves = currentPlayer.getPossibleMoves(rolledNumber);
		Set<Tile> uniqueSet = new HashSet<>();
		uniqueSet.addAll(possibleMoves);
		possibleMoves.clear();
		possibleMoves.addAll(uniqueSet);
		Tile currTile = currentPlayer.getCurrentTile();
		possibleMoves.remove(currTile);
		
		return possibleMoves;
	}

	public void land(Tile tile) throws InvalidInputException {
		if (game.indexOfTile(tile) == -1)
			throw new InvalidInputException("This tile does not exist in the current game.");
		
		tile.land();
	}

	public List<Tile> playRollDieActionCard() {
		// Roll The Die method returns possible tiles for player to move to.
		List<Tile> tiles = rollDie();
		
		return tiles;
	}

	public void playAddConnectionActionCard(Tile tile1, Tile tile2) {
		game.placeConnection(tile1, tile2);
		
		if(game.getCurrentConnectionPieces() > 0)
			game.setCurrentConnectionPieces(game.getCurrentConnectionPieces()-1);
	}

	public void playRemoveConnectionActionCard(Tile tile1, Tile tile2) {
		List<Connection> conns = tile1.getConnections();

		for (Connection conn : conns) {
			List<Tile> tiles = conn.getTiles();
			{
				for (Tile tile : tiles) {
					if (tile == tile2) {
						conn.delete();
						
						if(game.getCurrentConnectionPieces() < 32)
							game.setCurrentConnectionPieces(game.getCurrentConnectionPieces()+1);
						
						return;
					}
				}
			}
		}
	}

	public void playTeleportActionCard(Tile tile) {
		TeleportActionCard playedCard = (TeleportActionCard) game.getDeck().getCurrentCard();

		// Play the card
		playedCard.play(tile);
	}
	
	public void playLoseTurnActionCard() {
		Player player = game.getCurrentPlayer();
		player.loseTurns(1);
		nextTurn();
	}
	
	public void nextTurn() {
		ui.update();
		game.determineNextPlayer(ui);
		ui.update();
	}
	
	public void saveGame(String gameName) {
		TileOApplication.save();
	}

	private Game game;
	private TileOPlayUI ui;
}
