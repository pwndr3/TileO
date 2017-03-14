package ca.mcgill.ecse223.tileo.controller;

import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.model.*;
import ca.mcgill.ecse223.tileo.model.Game.Mode;
import ca.mcgill.ecse223.tileo.view.PopUpManager;
import ca.mcgill.ecse223.tileo.view.TileOPlayUI;

import java.util.List;

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
		
		return possibleMoves;
	}

	// TODO Add land method to tile class.
	public void land(Tile tile) throws InvalidInputException {
		if (game.indexOfTile(tile) == -1)
			throw new InvalidInputException("This tile does not exist in the current game.");
		tile.land();
	}

	public List<Tile> playRollDieActionCard() throws InvalidInputException {
		Deck deck = game.getDeck();
		ActionCard currentCard = deck.getCurrentCard();

		if (!(currentCard instanceof RollDieActionCard))
			throw new InvalidInputException("The current card is not a Roll Die Action Card");
		
		currentCard = (RollDieActionCard) deck.getCurrentCard();

		// Roll The Die method returns possible tiles for player to move to.
		List<Tile> tiles = rollDie();
		
		return tiles;
	}

	// TODO See if number of connections in spare pile need to be altered.
	// PlaceConnection is a new method in game class whose code is shared by add
	// connection in design mode.
	public void playAddConnectionActionCard(Tile tile1, Tile tile2) throws InvalidInputException {

		// Game currentGame = TileOApplication.getTileO().getCurrentGame();

		// Check if connection pieces are still available and if tiles exist.
		if (game.getCurrentConnectionPieces() == 0)
			throw new InvalidInputException("There are no more connection pieces in the spare pile");

		Deck deck = game.getDeck();
		ActionCard currentCard = deck.getCurrentCard();

		// Check if action card is a Connect Tiles Action Card.
		if (!(currentCard instanceof ConnectTilesActionCard))
			throw new InvalidInputException("The current card is not a Connect Tiles Action Card");

		game.placeConnection(tile1, tile2);

		Player currentPlayer = game.getCurrentPlayer();
		int indexOfCurrentPlayer = game.indexOfPlayer(currentPlayer);

		// If current player is last player, make next player the first player.
		if (indexOfCurrentPlayer == (game.numberOfPlayers() - 1))
			game.setCurrentPlayer(game.getPlayer(0));

		// Make it next player's turn.
		else
			game.setCurrentPlayer(game.getPlayer(indexOfCurrentPlayer + 1));
	}

	// TODO see if number of connections must be increased after a connection is
	// added.
	public void playRemoveConnectionActionActionCard(Connection connection) throws InvalidInputException {
		// Get info about the current game
		Deck deck = game.getDeck();
		ActionCard currentCard = deck.getCurrentCard();

		// Check if action card is a Remove Connection Action Card.
		if (!(currentCard instanceof RemoveConnectionActionCard))
			throw new InvalidInputException("The current card is not a Remove Connection Action Card");
		
		currentCard = (RemoveConnectionActionCard) deck.getCurrentCard();

		// This function removes the connection.
		game.removeConnection(connection);

		Player currentPlayer = game.getCurrentPlayer();

		int indexOfCurrentPlayer = game.indexOfPlayer(currentPlayer);

		// If current player is last player, make the first player the current
		// player.
		if (indexOfCurrentPlayer == (game.numberOfPlayers() - 1)) {
			game.setCurrentPlayer(game.getPlayer(0));
		}
		// Set next player as current player.
		else {
			game.setCurrentPlayer(game.getPlayer(indexOfCurrentPlayer + 1));
		}
	}

	public void teleport(Tile tile) throws InvalidInputException {
		// Get info about the current game
		TileO tileo = TileOApplication.getTileO();
		Game game = tileo.getCurrentGame();
		Deck deck = game.getDeck();
		// Exception
		if (!(deck.getCurrentCard() instanceof TeleportActionCard)) {
			throw new InvalidInputException("Action card is not a teleport action card");
		}

		TeleportActionCard playedCard = (TeleportActionCard) deck.getCurrentCard();

		// Play the card
		playedCard.play(tile);
	}

	private Game game;
	private TileOPlayUI ui;
}
